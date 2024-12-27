package com.samsung.android.sume.core.filter;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public interface MediaFilterGroup extends MediaFilter {
    MediaFilterGroup addFilter(List<MediaFilter> list);

    boolean removeFilter(MediaFilter... mediaFilterArr);

    boolean replaceFilter(MediaFilter mediaFilter, MediaFilter mediaFilter2);

    default MediaFilterGroup addFilter(MediaFilter... filters) {
        addFilter(Arrays.asList(filters));
        return this;
    }
}
