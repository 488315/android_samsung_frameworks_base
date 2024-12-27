package com.android.systemui.dump;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DumpHandlerKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public static final String[] PRIORITY_OPTIONS = {"CRITICAL", "NORMAL"};
    public static final String[] COMMANDS = {"bugreport-critical", "bugreport-normal", "buffers", "dumpables", "tables", "config", "help"};
}
