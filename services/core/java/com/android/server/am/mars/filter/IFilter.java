package com.android.server.am.mars.filter;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IFilter {
    void deInit();

    int filter(int i, int i2, int i3, String str);

    void init(Context context);
}
