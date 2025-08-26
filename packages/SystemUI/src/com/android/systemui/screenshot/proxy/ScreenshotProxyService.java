package com.android.systemui.screenshot.proxy;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleService;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeExpansionStateManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class ScreenshotProxyService extends LifecycleService {
    public final ActivityStarter activityStarter;
    public final ScreenshotProxyService$mBinder$1 mBinder = new ScreenshotProxyService$mBinder$1(this);
    public final ShadeExpansionStateManager mExpansionMgr;
    public final CoroutineDispatcher mMainDispatcher;

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
