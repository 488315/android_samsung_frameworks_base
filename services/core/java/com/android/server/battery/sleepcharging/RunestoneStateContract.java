package com.android.server.battery.sleepcharging;

import android.net.Uri;

public abstract class RunestoneStateContract {
    public static final Uri CONTENT_URI;

    static {
        Uri parse = Uri.parse("content://com.samsung.android.rubin.state");
        CONTENT_URI = parse;
        Uri.withAppendedPath(parse, "enabled");
    }
}
