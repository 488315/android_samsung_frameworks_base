package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.shared.model.WhenToStartHub;
import com.android.systemui.dock.DockManagerExtensionsKt;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalAutoOpenInteractor$shouldAutoOpen$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalAutoOpenInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalAutoOpenInteractor$shouldAutoOpen$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ CommunalAutoOpenInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CommunalAutoOpenInteractor communalAutoOpenInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = communalAutoOpenInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.Z$0 ? this.this$0.posturingInteractor.postured : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WhenToStartHub.values().length];
            try {
                iArr[WhenToStartHub.WHILE_CHARGING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WhenToStartHub.WHILE_DOCKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WhenToStartHub.WHILE_CHARGING_AND_POSTURED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WhenToStartHub.NEVER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAutoOpenInteractor$shouldAutoOpen$1(CommunalAutoOpenInteractor communalAutoOpenInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAutoOpenInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAutoOpenInteractor$shouldAutoOpen$1 communalAutoOpenInteractor$shouldAutoOpen$1 = new CommunalAutoOpenInteractor$shouldAutoOpen$1(this.this$0, continuation);
        communalAutoOpenInteractor$shouldAutoOpen$1.L$0 = obj;
        return communalAutoOpenInteractor$shouldAutoOpen$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAutoOpenInteractor$shouldAutoOpen$1) create((WhenToStartHub) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = WhenMappings.$EnumSwitchMapping$0[((WhenToStartHub) this.L$0).ordinal()];
        if (i == 1) {
            return this.this$0.batteryInteractor.isDevicePluggedIn;
        }
        if (i == 2) {
            BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
            CommunalAutoOpenInteractor communalAutoOpenInteractor = this.this$0;
            return booleanFlowOperators.allOf(communalAutoOpenInteractor.batteryInteractor.isDevicePluggedIn, DockManagerExtensionsKt.retrieveIsDocked(communalAutoOpenInteractor.dockManager));
        }
        if (i == 3) {
            CommunalAutoOpenInteractor communalAutoOpenInteractor2 = this.this$0;
            return LatestConflatedKt.flatMapLatestConflated(communalAutoOpenInteractor2.batteryInteractor.isDevicePluggedIn, new AnonymousClass1(communalAutoOpenInteractor2, null));
        }
        if (i == 4) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        throw new NoWhenBranchMatchedException();
    }
}
