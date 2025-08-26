package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
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
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class ChipTransitionHelper {
    public final SharedFlowImpl activityStoppedFromDialogEvent;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow wasActivityRecentlyStoppedFromDialog;

    /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper$createChipFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (OngoingActivityChipModel) obj;
            anonymousClass1.Z$0 = zBooleanValue;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.Z$0 ? new OngoingActivityChipModel.Inactive(false, null, 2, null) : (OngoingActivityChipModel) this.L$0;
        }
    }

    /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper$onActivityStoppedFromDialog$1, reason: invalid class name and case insensitive filesystem */
    final class C10531 extends SuspendLambda implements Function2 {
        int label;

        public C10531(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ChipTransitionHelper.this.new C10531(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10531) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlowImpl sharedFlowImpl = ChipTransitionHelper.this.activityStoppedFromDialogEvent;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (sharedFlowImpl.emit(unit, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public ChipTransitionHelper(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.activityStoppedFromDialogEvent = sharedFlowImplMutableSharedFlow$default;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(sharedFlowImplMutableSharedFlow$default, new ChipTransitionHelper$wasActivityRecentlyStoppedFromDialog$1(null));
        SharingStarted.Companion.getClass();
        this.wasActivityRecentlyStoppedFromDialog = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, SharingStarted.Companion.Lazily, Boolean.FALSE);
    }

    public final ReadonlyStateFlow createChipFlow(ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, this.wasActivityRecentlyStoppedFromDialog, new AnonymousClass1(null)), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
    }

    public final void onActivityStoppedFromDialog() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C10531(null), 7);
    }
}
