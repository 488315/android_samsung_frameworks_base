package com.android.systemui.volume.dialog.ui.binder;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewBinder$bind$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ ViewGroup $root;
    int label;
    final /* synthetic */ VolumeDialogViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewBinder$bind$5(VolumeDialogViewBinder volumeDialogViewBinder, ViewGroup viewGroup, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogViewBinder;
        this.$root = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogViewBinder$bind$5(this.this$0, this.$root, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogViewBinder$bind$5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final VolumeDialogViewBinder volumeDialogViewBinder = this.this$0;
            final ViewTreeObserver viewTreeObserver = this.$root.getViewTreeObserver();
            this.label = 1;
            volumeDialogViewBinder.getClass();
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            final ViewTreeObserver.OnComputeInternalInsetsListener onComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$listenToComputeInternalInsets$2$listener$1
                public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                    VolumeDialogViewModel volumeDialogViewModel = VolumeDialogViewBinder.this.viewModel;
                    internalInsetsInfo.getClass();
                    for (View view : volumeDialogViewModel.touchableBoundsViews) {
                        Rect rect = new Rect();
                        internalInsetsInfo.setTouchableInsets(3);
                        view.getBoundsInWindow(rect, false);
                        internalInsetsInfo.touchableRegion.op(rect, Region.Op.UNION);
                    }
                }
            };
            viewTreeObserver.addOnComputeInternalInsetsListener(onComputeInternalInsetsListener);
            cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$listenToComputeInternalInsets$2$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    viewTreeObserver.removeOnComputeInternalInsetsListener(onComputeInternalInsetsListener);
                    return Unit.INSTANCE;
                }
            });
            Object result = cancellableContinuationImpl.getResult();
            if (result != coroutineSingletons) {
                result = Unit.INSTANCE;
            }
            if (result == coroutineSingletons) {
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
