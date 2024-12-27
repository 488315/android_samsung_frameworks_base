package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleService;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeExpansionStateManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotProxyService extends LifecycleService {
    public final ActivityStarter activityStarter;
    public final ScreenshotProxyService$mBinder$1 mBinder = new ScreenshotProxyService$mBinder$1(this);
    public final ShadeExpansionStateManager mExpansionMgr;
    public final CoroutineDispatcher mMainDispatcher;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotProxyService(ShadeExpansionStateManager shadeExpansionStateManager, CoroutineDispatcher coroutineDispatcher, ActivityStarter activityStarter) {
        this.mExpansionMgr = shadeExpansionStateManager;
        this.mMainDispatcher = coroutineDispatcher;
        this.activityStarter = activityStarter;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.d("ScreenshotProxyService", "onBind: " + intent);
        return this.mBinder;
    }
}
