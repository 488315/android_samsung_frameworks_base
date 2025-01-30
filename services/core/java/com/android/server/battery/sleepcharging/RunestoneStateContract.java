package com.android.server.battery.sleepcharging;

import android.net.Uri;

/* loaded from: classes.dex */
public abstract class RunestoneStateContract {
  public static final Uri CONTENT_URI;
  public static final Uri ENABLE_CONTENT_URI;

  static {
    Uri parse = Uri.parse("content://com.samsung.android.rubin.state");
    CONTENT_URI = parse;
    ENABLE_CONTENT_URI = Uri.withAppendedPath(parse, "enabled");
  }
}
