package com.android.systemui.dreams.homecontrols.system;

import android.content.Intent;
import android.os.IBinder;
import androidx.lifecycle.LifecycleService;
import com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder;
import com.android.systemui.log.core.Logger;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsRemoteService extends LifecycleService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy binder$delegate;

    public HomeControlsRemoteService(final HomeControlsRemoteServiceBinder.Factory factory) {
        this.binder$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteService$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = HomeControlsRemoteService.$r8$clinit;
                return HomeControlsRemoteServiceBinder.Factory.this.create(this);
            }
        });
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final IBinder onBind(Intent intent) {
        super.onBind(intent);
        return (HomeControlsRemoteServiceBinder) this.binder$delegate.getValue();
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder = (HomeControlsRemoteServiceBinder) this.binder$delegate.getValue();
        Logger.d$default(homeControlsRemoteServiceBinder.logger, "Service destroyed", null, 2, null);
        homeControlsRemoteServiceBinder.callbacks.kill();
        homeControlsRemoteServiceBinder.callbackCount.set(0);
        StandaloneCoroutine standaloneCoroutine = homeControlsRemoteServiceBinder.collectionJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        homeControlsRemoteServiceBinder.collectionJob = null;
    }
}
