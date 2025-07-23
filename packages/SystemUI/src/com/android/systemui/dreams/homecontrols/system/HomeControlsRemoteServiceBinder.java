package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsRemoteServiceBinder extends IHomeControlsRemoteProxy.Stub implements LifecycleOwner {
    public final /* synthetic */ LifecycleOwner $$delegate_0;
    public final CoroutineContext bgContext;
    public StandaloneCoroutine collectionJob;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final HomeControlsComponentInteractor homeControlsComponentInteractor;
    public final DreamLogger logger;
    public final HomeControlsRemoteServiceBinder$callbacks$1 callbacks = new RemoteCallbackList() { // from class: com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder$callbacks$1
        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface) {
            if (HomeControlsRemoteServiceBinder.this.callbackCount.decrementAndGet() == 0) {
                Logger.d$default(HomeControlsRemoteServiceBinder.this.logger, "Cancelling collection due to callback death", null, 2, null);
                StandaloneCoroutine standaloneCoroutine = HomeControlsRemoteServiceBinder.this.collectionJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                HomeControlsRemoteServiceBinder.this.collectionJob = null;
            }
        }
    };
    public final AtomicInteger callbackCount = new AtomicInteger(0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        HomeControlsRemoteServiceBinder create(LifecycleOwner lifecycleOwner);
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder$callbacks$1] */
    public HomeControlsRemoteServiceBinder(HomeControlsComponentInteractor homeControlsComponentInteractor, ControlsSettingsRepository controlsSettingsRepository, CoroutineContext coroutineContext, LogBuffer logBuffer, LifecycleOwner lifecycleOwner) {
        this.$$delegate_0 = lifecycleOwner;
        this.homeControlsComponentInteractor = homeControlsComponentInteractor;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.bgContext = coroutineContext;
        this.logger = new DreamLogger(logBuffer, "HomeControlsRemoteServiceBinder");
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.$$delegate_0.getLifecycle();
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy
    public final void registerListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener) {
        if (iOnControlsSettingsChangeListener == null) {
            return;
        }
        Logger.d$default(this.logger, "Register listener", null, 2, null);
        boolean register = register(iOnControlsSettingsChangeListener);
        if (register && this.callbackCount.getAndIncrement() == 0) {
            Logger.d$default(this.logger, "Starting collection", null, 2, null);
            this.collectionJob = BuildersKt.launch$default(LifecycleKt.getCoroutineScope(this.$$delegate_0.getLifecycle()), this.bgContext, null, new HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1(this, null), 2);
        } else if (register) {
            try {
                iOnControlsSettingsChangeListener.onControlsSettingsChanged((ComponentName) this.homeControlsComponentInteractor.panelComponent.$$delegate_0.getValue(), ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.$$delegate_0.getValue()).booleanValue());
            } catch (RemoteException e) {
                Log.e("HomeControlsRemoteServiceBinder", "Error notifying callback", e);
            }
        }
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy
    public final void unregisterListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener) {
        if (iOnControlsSettingsChangeListener == null) {
            return;
        }
        Logger.d$default(this.logger, "Unregister listener", null, 2, null);
        if (unregister(iOnControlsSettingsChangeListener) && this.callbackCount.decrementAndGet() == 0) {
            Logger.d$default(this.logger, "Cancelling collection due to unregister", null, 2, null);
            StandaloneCoroutine standaloneCoroutine = this.collectionJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            this.collectionJob = null;
        }
    }
}
