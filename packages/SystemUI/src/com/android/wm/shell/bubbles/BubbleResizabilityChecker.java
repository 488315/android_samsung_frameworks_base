package com.android.wm.shell.bubbles;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BubbleResizabilityChecker implements ResizabilityChecker {

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
        ActivityInfo activityInfoResolveActivityInfo = intent.resolveActivityInfo(packageManager, 0);
        if (activityInfoResolveActivityInfo == null) {
            Log.w("BubbleResizeChecker", "Unable to send as bubble: " + str + " couldn't find activity info for intent: " + intent);
            return false;
        }
        if (ActivityInfo.isResizeableMode(activityInfoResolveActivityInfo.resizeMode)) {
            return true;
        }
        Log.w("BubbleResizeChecker", "Unable to send as bubble: " + str + " activity is not resizable for intent: " + intent);
        return false;
    }
}
