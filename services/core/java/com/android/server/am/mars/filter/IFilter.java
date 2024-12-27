package com.android.server.am.mars.filter;

import android.content.Context;

public interface IFilter {
    void deInit();

    int filter(int i, int i2, int i3, String str);

    void init(Context context);
}
