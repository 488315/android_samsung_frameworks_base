package com.android.systemui.volume.dialog.settings.ui.binder;

import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.view.View;
import android.widget.ImageButton;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor;
import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonViewBinder implements ViewBinder {
    public final VolumeDialogViewModel dialogViewModel;
    public final VolumeDialogSettingsButtonViewModel viewModel;

    /* renamed from: com.android.systemui.volume.dialog.settings.ui.binder.VolumeDialogSettingsButtonViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageButton $button;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ImageButton imageButton, Continuation continuation) {
            super(2, continuation);
            this.$button = imageButton;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogSettingsButtonViewBinder.this.new AnonymousClass1(this.$button, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialogViewModel volumeDialogViewModel = VolumeDialogSettingsButtonViewBinder.this.dialogViewModel;
                View[] viewArr = {this.$button};
                this.label = 1;
                if (volumeDialogViewModel.addTouchableBounds(viewArr, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.settings.ui.binder.VolumeDialogSettingsButtonViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageButton $button;
        /* synthetic */ boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ImageButton imageButton, Continuation continuation) {
            super(2, continuation);
            this.$button = imageButton;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$button, continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$button.setVisibility(this.Z$0 ? 0 : 8);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.settings.ui.binder.VolumeDialogSettingsButtonViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageButton $button;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ImageButton imageButton, Continuation continuation) {
            super(2, continuation);
            this.$button = imageButton;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$button, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((Drawable) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$button.setImageDrawable((Drawable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    public VolumeDialogSettingsButtonViewBinder(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogSettingsButtonViewModel;
        this.dialogViewModel = volumeDialogViewModel;
    }

    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        ImageButton imageButton = (ImageButton) view.requireViewById(R.id.volume_dialog_settings);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(imageButton, null), 6);
        VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel = this.viewModel;
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogSettingsButtonViewModel.isVisible, new AnonymousClass2(imageButton, null)), coroutineScope);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogSettingsButtonViewModel.icon, new AnonymousClass3(imageButton, null)), coroutineScope);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.dialog.settings.ui.binder.VolumeDialogSettingsButtonViewBinder.bind.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                Object value;
                VolumeDialogVisibilityModel volumeDialogVisibilityModel;
                VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel2 = VolumeDialogSettingsButtonViewBinder.this.viewModel;
                VolumeDialogSettingsButtonInteractor volumeDialogSettingsButtonInteractor = volumeDialogSettingsButtonViewModel2.interactor;
                volumeDialogSettingsButtonInteractor.volumePanelGlobalStateInteractor.setVisible(true);
                VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = volumeDialogSettingsButtonInteractor.visibilityInteractor;
                StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
                do {
                    value = stateFlowImpl.getValue();
                    volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) value;
                    VolumeDialogVisibilityModel.Dismissed dismissed = new VolumeDialogVisibilityModel.Dismissed(5);
                    if (volumeDialogVisibilityModel.getClass() != VolumeDialogVisibilityModel.Dismissed.class) {
                        ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                        Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(dismissed), dismissed.hashCode());
                        volumeDialogVisibilityModel = dismissed;
                    }
                } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel));
                volumeDialogSettingsButtonViewModel2.uiEventLogger.log(VolumeDialogUiEvent.VOLUME_DIALOG_SETTINGS_CLICK);
            }
        });
    }
}
