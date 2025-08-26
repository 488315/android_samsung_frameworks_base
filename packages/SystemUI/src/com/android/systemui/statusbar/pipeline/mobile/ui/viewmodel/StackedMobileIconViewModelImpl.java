package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class StackedMobileIconViewModelImpl extends ExclusiveActivatable implements StackedMobileIconViewModel {
    public final State dualSim$delegate;
    public final Hydrator hydrator;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconViewModelFlow;
    public final State isIconVisible$delegate;
    public final State isStackable$delegate;
    public final State networkTypeIcon$delegate;

    public interface Factory {
        StackedMobileIconViewModelImpl create();
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StackedMobileIconViewModelImpl.this.onActivated(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StackedMobileIconViewModelImpl(MobileIconsViewModel mobileIconsViewModel) {
        Hydrator hydrator = new Hydrator("StackedMobileIconViewModel", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        this.isStackable$delegate = hydrator.hydratedStateOf("isStackable", Boolean.FALSE, mobileIconsViewModel.isStackable);
        StackedMobileIconViewModelImpl$iconViewModelFlow$1 stackedMobileIconViewModelImpl$iconViewModelFlow$1 = new StackedMobileIconViewModelImpl$iconViewModelFlow$1(null);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconsViewModel.mobileSubViewModels, mobileIconsViewModel.activeMobileDataSubscriptionId, stackedMobileIconViewModelImpl$iconViewModelFlow$1);
        this.iconViewModelFlow = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.dualSim$delegate = hydrator.hydratedStateOf("dualSim", null, FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1(null)));
        this.networkTypeIcon$delegate = hydrator.hydratedStateOf("networkTypeIcon", null, FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2(null)));
        this.isIconVisible$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StackedMobileIconViewModelImpl stackedMobileIconViewModelImpl = this.f$0;
                return Boolean.valueOf(((Boolean) ((SnapshotMutableStateImpl) stackedMobileIconViewModelImpl.isStackable$delegate).getValue()).booleanValue() && stackedMobileIconViewModelImpl.getDualSim() != null);
            }
        });
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final StackedMobileIconViewModel.DualSim getDualSim() {
        return (StackedMobileIconViewModel.DualSim) ((SnapshotMutableStateImpl) this.dualSim$delegate).getValue();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final Icon.Resource getNetworkTypeIcon() {
        return (Icon.Resource) ((SnapshotMutableStateImpl) this.networkTypeIcon$delegate).getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.label = 1;
            if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
