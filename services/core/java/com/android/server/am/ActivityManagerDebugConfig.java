package com.android.server.am;

import android.util.LogWriter;

import java.io.PrintWriter;
import java.io.Writer;

public abstract class ActivityManagerDebugConfig {
    public static final PrintWriter LOG_WRITER_INFO =
            new PrintWriter((Writer) new LogWriter(4, "ActivityManager"));
}
