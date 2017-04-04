package com.rozhak.selfeducation.photofilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.rozhak.selfeducation.photofilter.filters.Filter;
import com.rozhak.selfeducation.photofilter.filters.FilterFactory;
import com.rozhak.selfeducation.photofilter.filters.Filters;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class FilterService {

	private static String NOT_ENOUGTH_DATA_PROVIDED = "Not enougth data was provided";
	private static String NO_FILTER_FOUND = "No filter was found by provided filter name";
	private static String ACCEPTABLE_IMAGE_FORMAT_JPG = "jpg";
	private static String ACCEPTABLE_IMAGE_FORMAT_PNG = "png";

	private static final Logger LOGGER = Logger.getLogger(FilterService.class);

	@POST
	@Path("/filter")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpeg,image/png" })
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("filterName") String filterName) {

		if (uploadedInputStream == null || fileDetail == null || filterName == null) {
			LOGGER.warn(NOT_ENOUGTH_DATA_PROVIDED);
			throw new WebApplicationException(new NotFullData(NOT_ENOUGTH_DATA_PROVIDED),
					Response.status(Response.Status.BAD_REQUEST).build());
		}
		Response res = null;

		String fileName = fileDetail.getFileName();
		String extention = FilenameUtils.getExtension(fileName);
		checkExtension(extention);

		try {
			LOGGER.info("Reading image " + fileName + " from request");
			BufferedImage inputImage = ImageIO.read(uploadedInputStream);

			LOGGER.info("Getting filter by name: " + filterName);
			Filters filterEnum = Filters.valueOf(filterName.toUpperCase());
			Optional<? extends Filter> maybeFilter = new FilterFactory().getFilter(filterEnum);

			if (!maybeFilter.isPresent()) {
				LOGGER.warn("Failed to find filter by name: " + filterName);
				throw new WrongFilterNameException(NO_FILTER_FOUND);
			}

			Filter filter = maybeFilter.get();
			LOGGER.info("processing image");
			BufferedImage outputImage = filter.processImage(inputImage);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			LOGGER.info("writing processed image to output stream");
			ImageIO.write(outputImage, extention, baos);
			ResponseBuilder response = Response.ok(baos.toByteArray());
			response.header("Content-Disposition", "attachment; filename=" + fileName);
			res = response.build();
		} catch (WrongFilterNameException e) {
			LOGGER.warn("Fillter was not found", e);
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		} catch (IOException e) {
			LOGGER.warn("Problems on server", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return res;

	}

	private void checkExtension(String extention) {
		if (!extention.equalsIgnoreCase(ACCEPTABLE_IMAGE_FORMAT_JPG)
				&& !extention.equalsIgnoreCase(ACCEPTABLE_IMAGE_FORMAT_PNG)) {
			LOGGER.warn("Unsupportet media type of input file");
			throw new WebApplicationException(Response.Status.UNSUPPORTED_MEDIA_TYPE);
		}
	}

	@SuppressWarnings("serial")
	private static class NotFullData extends RuntimeException {

		NotFullData(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	private static class WrongFilterNameException extends RuntimeException {

		WrongFilterNameException(String message) {
			super(message);
		}
	}

}