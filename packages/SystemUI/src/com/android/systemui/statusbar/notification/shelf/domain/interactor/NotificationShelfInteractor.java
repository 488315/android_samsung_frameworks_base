package com.android.systemui.statusbar.notification.shelf.domain.interactor;

import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class NotificationShelfInteractor {
    public final DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository;
    public final KeyguardRepository keyguardRepository;
    public final LockscreenShadeTransitionController keyguardTransitionController;
    public final PowerInteractor powerInteractor;
    public final ShadeControllerImpl shadeControllerImpl;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;

    /* renamed from: com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor$isShelfStatic$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0 && this.Z$1);
        }
    }

    public NotificationShelfInteractor(KeyguardRepository keyguardRepository, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, PowerInteractor powerInteractor, ShadeModeInteractor shadeModeInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, StatusBarStateControllerImpl statusBarStateControllerImpl, ShadeControllerImpl shadeControllerImpl) {
        this.keyguardRepository = keyguardRepository;
        this.deviceEntryFaceAuthRepository = deviceEntryFaceAuthRepository;
        this.powerInteractor = powerInteractor;
        this.keyguardTransitionController = lockscreenShadeTransitionController;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.shadeControllerImpl = shadeControllerImpl;
    }

    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShelfStatic() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardRepositoryImpl) this.keyguardRepository).isKeyguardShowing, ((DeviceEntryFaceAuthRepositoryImpl) this.deviceEntryFaceAuthRepository).isBypassEnabled, new AnonymousClass1(null));
    }
}
