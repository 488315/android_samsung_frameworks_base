package com.android.server.battery.sleepcharging;

import android.net.Uri;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RunestoneStateContract {
    public static final Uri CONTENT_URI;

    static {
        Uri parse = Uri.parse("content://com.samsung.android.rubin.state");
        CONTENT_URI = parse;
        Uri.withAppendedPath(parse, "enabled");
    }
}
