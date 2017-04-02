package com.rozhak.selfeducation.photofilter.filters;

import com.rozhak.selfeducation.photofilter.filters.impl.BlackWhiteFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.Sepia;

public class FilterFactory {

	public Filter getFilter(Filters filtername) throws ClassNotFoundException {
		Filter filter = null;
		switch (filtername) {
		case SEPIA:
			FilterGetter<Sepia> sepia = Sepia::new;
			filter = sepia.getFilter();
			break;
		case BLACK_WHITE:
			FilterGetter<BlackWhiteFilter> blackWhiteFilter = BlackWhiteFilter::new;
			filter = blackWhiteFilter.getFilter();
			break;
		}
		return filter;
	}

}
