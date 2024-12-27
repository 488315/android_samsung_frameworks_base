package com.android.server.notification.sec.runestone;

import android.net.Uri;

public abstract class RunestoneStateContract {
    public static final Uri ENABLE_CONTENT_URI =
            Uri.withAppendedPath(Uri.parse("content://com.samsung.android.rubin.state"), "enabled");
}
