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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

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
            if (this.this$0.callbackCount.decrementAndGet() == 0) {
                Logger.d$default(this.this$0.logger, "Cancelling collection due to callback death", null, 2, null);
                StandaloneCoroutine standaloneCoroutine = this.this$0.collectionJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                this.this$0.collectionJob = null;
            }
        }
    };
    public final AtomicInteger callbackCount = new AtomicInteger(0);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        HomeControlsRemoteServiceBinder create(LifecycleOwner lifecycleOwner);
    }

    /* renamed from: com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1$1, reason: invalid class name and collision with other inner class name */
        final class C01961 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ HomeControlsRemoteServiceBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01961(HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder, Continuation continuation) {
                super(3, continuation);
                this.this$0 = homeControlsRemoteServiceBinder;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                C01961 c01961 = new C01961(this.this$0, (Continuation) obj3);
                c01961.L$0 = (ComponentName) obj;
                c01961.Z$0 = zBooleanValue;
                return c01961.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ComponentName componentName = (ComponentName) this.L$0;
                boolean z = this.Z$0;
                HomeControlsRemoteServiceBinder$callbacks$1 homeControlsRemoteServiceBinder$callbacks$1 = this.this$0.callbacks;
                int iBeginBroadcast = homeControlsRemoteServiceBinder$callbacks$1.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    try {
                        try {
                            ((IOnControlsSettingsChangeListener) homeControlsRemoteServiceBinder$callbacks$1.getBroadcastItem(i)).onControlsSettingsChanged(componentName, z);
                        } catch (RemoteException e) {
                            Log.e("HomeControlsRemoteServiceBinder", "Error notifying callback", e);
                        }
                    } catch (Throwable th) {
                        homeControlsRemoteServiceBinder$callbacks$1.finishBroadcast();
                        throw th;
                    }
                }
                homeControlsRemoteServiceBinder$callbacks$1.finishBroadcast();
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = HomeControlsRemoteServiceBinder.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder = HomeControlsRemoteServiceBinder.this;
            FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(homeControlsRemoteServiceBinder.homeControlsComponentInteractor.panelComponent, ((ControlsSettingsRepositoryImpl) homeControlsRemoteServiceBinder.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen, new C01961(homeControlsRemoteServiceBinder, null)), coroutineScope);
            return Unit.INSTANCE;
        }
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
        boolean zRegister = register(iOnControlsSettingsChangeListener);
        if (zRegister && this.callbackCount.getAndIncrement() == 0) {
            Logger.d$default(this.logger, "Starting collection", null, 2, null);
            this.collectionJob = BuildersKt.launch$default(LifecycleKt.getCoroutineScope(this.$$delegate_0.getLifecycle()), this.bgContext, null, new AnonymousClass1(null), 2);
        } else if (zRegister) {
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
