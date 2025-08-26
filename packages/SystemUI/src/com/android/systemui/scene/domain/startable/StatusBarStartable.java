package com.android.systemui.scene.domain.startable;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.deviceconfig.domain.interactor.DeviceConfigInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.navigation.domain.interactor.NavigationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class StatusBarStartable implements CoreStartable {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final CoroutineDispatcher backgroundDispatcher;
    public final DeviceConfigInteractor deviceConfigInteractor;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final IBinder disableToken = new Binder();
    public final NavigationInteractor navigationInteractor;
    public final PowerInteractor powerInteractor;
    public final SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor;
    public final SceneInteractor sceneInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final IStatusBarService statusBarService;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.scene.domain.startable.StatusBarStartable$onBootCompleted$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return StatusBarStartable.this.new AnonymousClass1(continuation);
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
            try {
                StatusBarStartable statusBarStartable = StatusBarStartable.this;
                statusBarStartable.statusBarService.disableForUser(0, statusBarStartable.disableToken, statusBarStartable.applicationContext.getPackageName(), StatusBarStartable.this.selectedUserInteractor.getSelectedUserId());
            } catch (RemoteException e) {
                Log.d("StatusBarStartable", "Failed to clear flags", e);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public StatusBarStartable(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Context context, SelectedUserInteractor selectedUserInteractor, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, DeviceConfigInteractor deviceConfigInteractor, NavigationInteractor navigationInteractor, AuthenticationInteractor authenticationInteractor, PowerInteractor powerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, IStatusBarService iStatusBarService) {
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.applicationContext = context;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sceneInteractor = sceneInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneContainerOcclusionInteractor = sceneContainerOcclusionInteractor;
        this.deviceConfigInteractor = deviceConfigInteractor;
        this.navigationInteractor = navigationInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.powerInteractor = powerInteractor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.statusBarService = iStatusBarService;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new AnonymousClass1(null), 5);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
