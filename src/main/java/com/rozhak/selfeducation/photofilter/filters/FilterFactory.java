package com.rozhak.selfeducation.photofilter.filters;

import java.util.Optional;

import com.rozhak.selfeducation.photofilter.filters.impl.BlackWhiteFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.BlueFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.ColorFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.GreenFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.InvertFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.RedFilter;
import com.rozhak.selfeducation.photofilter.filters.impl.SepiaFilter;

public class FilterFactory {

	public Optional<? extends Filter> getFilter(Filters filtername) {
		Filter filter = null;
		switch (filtername) {
		case SEPIA:
			FilterGetter<SepiaFilter> sepia = SepiaFilter::new;
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
		case RGB_GREEN:
			FilterGetter<GreenFilter> rgbGreenFilter = GreenFilter::new;
			filter = rgbGreenFilter.getFilter();
			break;
		case RGB_BLUE:
			FilterGetter<BlueFilter> rgbBlueFilter = BlueFilter::new;
			filter = rgbBlueFilter.getFilter();
			break;
		case INVERT_FILTER:
			FilterGetter<InvertFilter> invertFilter = InvertFilter::new;
			filter = invertFilter.getFilter();
			break;
		case COLOR_FILTER:
			FilterGetter<ColorFilter> colorFilter = ColorFilter::new;
			filter = colorFilter.getFilter();
			break;
			
		}

		return Optional.ofNullable(filter);
	}

}
