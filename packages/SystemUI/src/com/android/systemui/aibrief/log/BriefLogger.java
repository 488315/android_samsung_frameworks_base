package com.android.systemui.aibrief.log;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.aibrief.log.dagger.AiBriefLog;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BriefLogger {
    public static final String TAG = "[BriefLog]";
    private final LogBuffer logBuffer;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BriefLogger(@AiBriefLog LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void d(String str, String str2) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("[BriefLog]_", str2, str);
        LogBuffer.log$default(this.logBuffer, str, LogLevel.DEBUG, str2);
    }

    public final void e(String str, String str2) {
        Log.e(str, "[BriefLog]_" + str2);
        LogBuffer.log$default(this.logBuffer, str, LogLevel.ERROR, str2);
    }

    public final void w(String str, String str2) {
        MotionLayout$$ExternalSyntheticOutline0.m("[BriefLog]_", str2, str);
        LogBuffer.log$default(this.logBuffer, str, LogLevel.WARNING, str2);
    }
}
