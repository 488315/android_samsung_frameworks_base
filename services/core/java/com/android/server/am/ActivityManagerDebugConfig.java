package com.android.server.am;

import android.util.LogWriter;

import java.io.PrintWriter;
import java.io.Writer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ActivityManagerDebugConfig {
    public static final PrintWriter LOG_WRITER_INFO =
            new PrintWriter((Writer) new LogWriter(4, "ActivityManager"));
}
