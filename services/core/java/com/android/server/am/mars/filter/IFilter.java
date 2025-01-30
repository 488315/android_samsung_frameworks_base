package com.android.server.am.mars.filter;

import android.content.Context;

/* loaded from: classes.dex */
public interface IFilter {
  void deInit();

  int filter(String str, int i, int i2, int i3);

  void init(Context context);
}
