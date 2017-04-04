package com.rozhak.selfeducation.photofilter;

import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

public interface FilterService {

	 static final String NOT_ENOUGTH_DATA_PROVIDED = "Not enougth data was provided";
	 static final String NO_FILTER_FOUND = "No filter was found by provided filter name";
	 static final String ACCEPTABLE_IMAGE_FORMAT_JPG = "jpg";
	 static final String ACCEPTABLE_IMAGE_FORMAT_PNG = "png";
	
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("filterName") String filterName);
	
	public default void checkExtension(String extention) {
		if (!extention.equalsIgnoreCase(ACCEPTABLE_IMAGE_FORMAT_JPG)
				&& !extention.equalsIgnoreCase(ACCEPTABLE_IMAGE_FORMAT_PNG)) {
			throw new WebApplicationException(Response.Status.UNSUPPORTED_MEDIA_TYPE);
		}
	}
}
