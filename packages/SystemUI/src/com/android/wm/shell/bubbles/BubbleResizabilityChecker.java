package com.android.wm.shell.bubbles;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleResizabilityChecker implements ResizabilityChecker {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final boolean isResizableActivity(Intent intent, PackageManager packageManager, String str) {
        if (intent == null) {
            Log.w("BubbleResizeChecker", "Unable to send as bubble: " + str + " null intent");
            return false;
        }
        ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(packageManager, 0);
        if (resolveActivityInfo == null) {
            Log.w("BubbleResizeChecker", "Unable to send as bubble: " + str + " couldn't find activity info for intent: " + intent);
            return false;
        }
        if (ActivityInfo.isResizeableMode(resolveActivityInfo.resizeMode)) {
            return true;
        }
        Log.w("BubbleResizeChecker", "Unable to send as bubble: " + str + " activity is not resizable for intent: " + intent);
        return false;
    }
}
