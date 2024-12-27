package com.android.systemui.dump;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;

public abstract class DumpHandlerKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public static final String[] PRIORITY_OPTIONS = {"CRITICAL", "NORMAL"};
    public static final String[] COMMANDS = {"bugreport-critical", "bugreport-normal", "buffers", "dumpables", "tables", "config", "help"};
}
