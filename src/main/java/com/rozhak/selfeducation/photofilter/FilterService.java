package com.rozhak.selfeducation.photofilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

	@POST
	@Path("/filter")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpeg,image/png" })
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("filterName") String filterName) {

		Response res = null;
		try {
			String fileName = fileDetail.getFileName();
			String extension = FilenameUtils.getExtension(fileName);
			BufferedImage inputImage = ImageIO.read(uploadedInputStream);

			Filters filterEnum = Filters.valueOf(filterName.toUpperCase());
			Filter filter = new FilterFactory().getFilter(filterEnum);
			BufferedImage outputImage = filter.processImage(inputImage);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(outputImage, extension, baos);
			ResponseBuilder response = Response.ok(baos.toByteArray());
			response.header("Content-Disposition", "attachment; filename=" + fileName);
			res = response.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

}