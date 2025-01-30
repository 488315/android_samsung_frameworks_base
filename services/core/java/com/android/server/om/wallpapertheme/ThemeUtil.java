package com.android.server.om.wallpapertheme;

import android.graphics.Color;

/* loaded from: classes2.dex */
public abstract class ThemeUtil {
  public static Integer adjustAlpha(float f, int i) {
    return Integer.valueOf(
        Color.argb(Math.round(Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i)));
  }
}
