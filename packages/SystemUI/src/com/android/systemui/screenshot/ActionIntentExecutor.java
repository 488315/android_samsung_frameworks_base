package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.screenshot.proxy.SystemUiProxy;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActionIntentExecutor {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DisplayTracker displayTracker;
    public final CoroutineDispatcher mainDispatcher;
    public final SystemUiProxy systemUiProxy;

    public ActionIntentExecutor(Context context, ActivityManagerWrapper activityManagerWrapper, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SystemUiProxy systemUiProxy, DisplayTracker displayTracker) {
        this.context = context;
        this.activityManagerWrapper = activityManagerWrapper;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.systemUiProxy = systemUiProxy;
        this.displayTracker = displayTracker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object launchIntent(android.content.Intent r19, android.os.UserHandle r20, boolean r21, android.app.ActivityOptions r22, android.app.ExitTransitionCoordinator r23, kotlin.coroutines.Continuation r24) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ActionIntentExecutor.launchIntent(android.content.Intent, android.os.UserHandle, boolean, android.app.ActivityOptions, android.app.ExitTransitionCoordinator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void launchIntentAsync(Intent intent, UserHandle userHandle, boolean z, ActivityOptions activityOptions, ExitTransitionCoordinator exitTransitionCoordinator) {
        BuildersKt.launch$default(this.applicationScope, EmptyCoroutineContext.INSTANCE, null, new ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1("ActionIntentExecutor#launchIntentAsync", null, this, intent, userHandle, z, activityOptions, exitTransitionCoordinator), 2);
    }
}
