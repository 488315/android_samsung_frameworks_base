package com.android.systemui.dump;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DumpHandlerKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public static final String[] PRIORITY_OPTIONS = {"CRITICAL", "NORMAL"};
    public static final String[] COMMANDS = {"bugreport-critical", "bugreport-normal", "buffers", "dumpables", "tables", "config", "help"};
}
