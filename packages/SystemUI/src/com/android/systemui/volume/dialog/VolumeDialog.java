package com.android.systemui.volume.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.ComponentDialog;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.volume.dialog.dagger.factory.VolumeDialogComponentFactory;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class VolumeDialog extends ComponentDialog {
    public final VolumeDialogComponentFactory componentFactory;
    public final VolumeDialogVisibilityInteractor visibilityInteractor;

    /* renamed from: com.android.systemui.volume.dialog.VolumeDialog$onCreate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return VolumeDialog.this.new AnonymousClass1((Continuation) obj3).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 = new VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1(null, "[Volume]dialog", VolumeDialog.this);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1, this) == coroutineSingletons) {
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

    public VolumeDialog(Context context, VolumeDialogComponentFactory volumeDialogComponentFactory, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
        super(context, R.style.Theme_SystemUI_Dialog_Volume);
        this.componentFactory = volumeDialogComponentFactory;
        this.visibilityInteractor = volumeDialogVisibilityInteractor;
        Window window = getWindow();
        window.getClass();
        window.addFlags(android.R.string.config_systemAutomotiveCluster);
        window.addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        window.setType(2020);
        window.setWindowAnimations(-1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle("VolumeDialog");
        window.setAttributes(attributes);
        window.setLayout(-2, -1);
        window.setGravity(8388613);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.volume_dialog);
        RepeatWhenAttachedKt.repeatWhenAttached(requireViewById(R.id.volume_dialog), EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
    }

    @Override // android.app.Dialog
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Object value;
        VolumeDialogVisibilityModel volumeDialogVisibilityModel;
        if (!isShowing() || motionEvent.getAction() != 4) {
            return false;
        }
        VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = this.visibilityInteractor;
        StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
        do {
            value = stateFlowImpl.getValue();
            volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) value;
            VolumeDialogVisibilityModel.Dismissed dismissed = new VolumeDialogVisibilityModel.Dismissed(1);
            if (volumeDialogVisibilityModel.getClass() != VolumeDialogVisibilityModel.Dismissed.class) {
                ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(dismissed), dismissed.hashCode());
                volumeDialogVisibilityModel = dismissed;
            }
        } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel));
        return true;
    }
}
