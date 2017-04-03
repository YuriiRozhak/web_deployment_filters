package com.rozhak.selfeducation.photofilter.filters;

import java.util.Optional;

import com.rozhak.selfeducation.photofilter.filters.impl.BlackWhiteFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.BlueFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.GreenFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.RedFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.Sepia;

public class FilterFactory {

	public Optional<? extends Filter> getFilter(Filters filtername) {
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
		case RGB_RED:
			FilterGetter<RedFilter> rgbRedFilter = RedFilter::new;
			filter = rgbRedFilter.getFilter();
			break;
		case RGB_BLUE:
			FilterGetter<GreenFilter> rgbGreenFilter = GreenFilter::new;
			filter = rgbGreenFilter.getFilter();
			break;
		case RGB_GREEN:
			FilterGetter<BlueFilter> rgbBlueFilter = BlueFilter::new;
			filter = rgbBlueFilter.getFilter();
			break;
		}

		return Optional.ofNullable(filter);
	}

}
