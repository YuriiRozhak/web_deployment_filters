package com.rozhak.selfeducation.photofilter.filters;

@FunctionalInterface
public interface FilterGetter<T extends Filter> {

	public T getFilter();

}
