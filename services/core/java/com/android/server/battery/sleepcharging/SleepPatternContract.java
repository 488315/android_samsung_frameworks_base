package com.android.server.battery.sleepcharging;

import android.net.Uri;
import android.provider.BaseColumns;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SleepPatternContract {
    public static final Uri AUTHORITY_URI =
            Uri.parse("content://com.samsung.android.rubin.persona.sleeppattern");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class SleepPatternInfo implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(SleepPatternContract.AUTHORITY_URI, "sleep_pattern_info");
    }
}
