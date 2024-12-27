package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;

@FunctionalInterface
public interface MediaFilterCreator {
    MediaFilter newFilter(
            MediaFilterFactory mediaFilterFactory,
            MFDescriptor mFDescriptor,
            MediaFilter mediaFilter);
}
