package com.rozhak.selfeducation.photofilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

import org.apache.commons.io.FilenameUtils;

import com.rozhak.selfeducation.photofilter.filters.Filter;
import com.rozhak.selfeducation.photofilter.filters.FilterFactory;
import com.rozhak.selfeducation.photofilter.filters.Filters;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class FilterService {

	private String NOT_ENOUGTH_DATA_PROVIDED = "Not enougth data was provided";
	private String NO_FILTER_FOUND = "No filter was found by provided filter name";

	@POST
	@Path("/filter")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpeg,image/png" })
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("filterName") String filterName) {

		if (uploadedInputStream == null || fileDetail == null || filterName == null) {
			throw new WebApplicationException(new NotFullData(NOT_ENOUGTH_DATA_PROVIDED),
					Response.status(Response.Status.BAD_REQUEST).build());
		}
		Response res = null;

		String fileName = fileDetail.getFileName();
		String extention = FilenameUtils.getExtension(fileName);
		checkExtension(extention);
//		BufferedImage inputImage;

		try {
			BufferedImage inputImage = ImageIO.read(uploadedInputStream);

			Filters filterEnum = Filters.valueOf(filterName.toUpperCase());
			Optional<? extends Filter> maybeFilter = new FilterFactory().getFilter(filterEnum);

			if (!maybeFilter.isPresent()) {
				throw new WrongFilterNameException(NO_FILTER_FOUND);
			}
			Filter filter = maybeFilter.get();
			BufferedImage outputImage = filter.processImage(inputImage);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(outputImage, extention, baos);
			ResponseBuilder response = Response.ok(baos.toByteArray());
			response.header("Content-Disposition", "attachment; filename=" + fileName);
			res = response.build();
		} 
		catch (WrongFilterNameException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
		catch (Exception e) { 
			e.printStackTrace();
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
		return res;

	}

	private void checkExtension(String extention) {
		if (!extention.equalsIgnoreCase("png") && !extention.equalsIgnoreCase("jpg")) {
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