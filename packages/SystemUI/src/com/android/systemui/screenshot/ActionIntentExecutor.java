package com.android.systemui.screenshot;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.screenshot.proxy.ScreenshotProxy;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActionIntentExecutor {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DisplayTracker displayTracker;
    public final CoroutineDispatcher mainDispatcher;
    public final ScreenshotProxy screenshotProxy;

    public ActionIntentExecutor(Context context, ActivityManagerWrapper activityManagerWrapper, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ScreenshotProxy screenshotProxy, DisplayTracker displayTracker) {
        this.context = context;
        this.activityManagerWrapper = activityManagerWrapper;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.screenshotProxy = screenshotProxy;
        this.displayTracker = displayTracker;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x010b, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r4, r5, r2) == r3) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x010e, code lost:
    
        r2 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0169, code lost:
    
        if (r0 == r3) goto L55;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object launchIntent(android.content.Intent r17, android.os.UserHandle r18, boolean r19, android.app.ActivityOptions r20, android.app.ExitTransitionCoordinator r21, kotlin.coroutines.jvm.internal.ContinuationImpl r22) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ActionIntentExecutor.launchIntent(android.content.Intent, android.os.UserHandle, boolean, android.app.ActivityOptions, android.app.ExitTransitionCoordinator, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void launchIntentAsync(Intent intent, UserHandle userHandle) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new ActionIntentExecutor$launchIntentAsync$1(this, intent, userHandle, false, null, null, null), 6);
    }
}
