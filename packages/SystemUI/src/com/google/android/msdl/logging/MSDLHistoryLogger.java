package com.google.android.msdl.logging;

import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
public interface MSDLHistoryLogger {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);

        private Companion() {
        }
    }
}
