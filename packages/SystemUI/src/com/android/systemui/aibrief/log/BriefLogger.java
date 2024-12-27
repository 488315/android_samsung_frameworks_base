package com.android.systemui.aibrief.log;

import android.util.Log;
import com.android.systemui.aibrief.log.dagger.AiBriefLog;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BriefLogger {
    public static final String TAG = "[BriefLog]";
    private final LogBuffer logBuffer;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BriefLogger(@AiBriefLog LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void d(String str, String str2) {
        Log.d(str, "[BriefLog]_".concat(str2));
        this.logBuffer.log(str, LogLevel.DEBUG, str2, null);
    }

    public final void e(String str, String str2) {
        Log.e(str, "[BriefLog]_".concat(str2));
        this.logBuffer.log(str, LogLevel.ERROR, str2, null);
    }

    public final void w(String str, String str2) {
        Log.w(str, "[BriefLog]_".concat(str2));
        this.logBuffer.log(str, LogLevel.WARNING, str2, null);
    }
}
