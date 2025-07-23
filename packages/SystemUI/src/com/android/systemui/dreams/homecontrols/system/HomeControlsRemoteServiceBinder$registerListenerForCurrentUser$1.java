package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HomeControlsRemoteServiceBinder this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ HomeControlsRemoteServiceBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder, Continuation continuation) {
            super(3, continuation);
            this.this$0 = homeControlsRemoteServiceBinder;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
            anonymousClass1.L$0 = (ComponentName) obj;
            anonymousClass1.Z$0 = booleanValue;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
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
            int beginBroadcast = homeControlsRemoteServiceBinder$callbacks$1.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1(HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsRemoteServiceBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1 homeControlsRemoteServiceBinder$registerListenerForCurrentUser$1 = new HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1(this.this$0, continuation);
        homeControlsRemoteServiceBinder$registerListenerForCurrentUser$1.L$0 = obj;
        return homeControlsRemoteServiceBinder$registerListenerForCurrentUser$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsRemoteServiceBinder$registerListenerForCurrentUser$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        HomeControlsRemoteServiceBinder homeControlsRemoteServiceBinder = this.this$0;
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(homeControlsRemoteServiceBinder.homeControlsComponentInteractor.panelComponent, ((ControlsSettingsRepositoryImpl) homeControlsRemoteServiceBinder.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen, new AnonymousClass1(homeControlsRemoteServiceBinder, null)), coroutineScope);
        return Unit.INSTANCE;
    }
}
