package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.graphics.drawable.GradientDrawable;
import android.widget.ImageButton;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonUiModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $count;
    final /* synthetic */ Runnable $onAnimationEnd;
    final /* synthetic */ Function2 $onProgressChanged;
    final /* synthetic */ int $previousIndex;
    final /* synthetic */ ImageButton $selectedButton;
    final /* synthetic */ RingerButtonUiModel $selectedButtonUiModel;
    final /* synthetic */ MotionLayout $this_animateAndBindDrawerButtons;
    final /* synthetic */ RingerViewModel $uiModel;
    final /* synthetic */ ImageButton $unselectedButton;
    final /* synthetic */ RingerButtonUiModel $unselectedButtonUiModel;
    final /* synthetic */ VolumeDialogRingerDrawerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogRingerViewBinder this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $count;
        final /* synthetic */ Function2 $onProgressChanged;
        final /* synthetic */ ImageButton $selectedButton;
        final /* synthetic */ RingerButtonUiModel $selectedButtonUiModel;
        final /* synthetic */ RingerViewModel $uiModel;
        int label;
        final /* synthetic */ VolumeDialogRingerViewBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, RingerViewModel ringerViewModel, int i, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.this$0 = volumeDialogRingerViewBinder;
            this.$selectedButton = imageButton;
            this.$selectedButtonUiModel = ringerButtonUiModel;
            this.$uiModel = ringerViewModel;
            this.$count = i;
            this.$onProgressChanged = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$selectedButton, this.$selectedButtonUiModel, this.$uiModel, this.$count, this.$onProgressChanged, continuation);
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
                VolumeDialogRingerViewBinder volumeDialogRingerViewBinder = this.this$0;
                ImageButton imageButton = this.$selectedButton;
                RingerButtonUiModel ringerButtonUiModel = this.$selectedButtonUiModel;
                Function2 volumeDialogRingerViewBinder$$ExternalSyntheticLambda1 = this.$uiModel.currentButtonIndex == this.$count - 1 ? this.$onProgressChanged : new VolumeDialogRingerViewBinder$$ExternalSyntheticLambda1(1);
                this.label = 1;
                if (VolumeDialogRingerViewBinder.access$animateTo(volumeDialogRingerViewBinder, imageButton, ringerButtonUiModel, volumeDialogRingerViewBinder$$ExternalSyntheticLambda1, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $count;
        final /* synthetic */ Function2 $onProgressChanged;
        final /* synthetic */ int $previousIndex;
        final /* synthetic */ ImageButton $unselectedButton;
        final /* synthetic */ RingerButtonUiModel $unselectedButtonUiModel;
        int label;
        final /* synthetic */ VolumeDialogRingerViewBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, int i, int i2, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.this$0 = volumeDialogRingerViewBinder;
            this.$unselectedButton = imageButton;
            this.$unselectedButtonUiModel = ringerButtonUiModel;
            this.$previousIndex = i;
            this.$count = i2;
            this.$onProgressChanged = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$unselectedButton, this.$unselectedButtonUiModel, this.$previousIndex, this.$count, this.$onProgressChanged, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialogRingerViewBinder volumeDialogRingerViewBinder = this.this$0;
                ImageButton imageButton = this.$unselectedButton;
                RingerButtonUiModel ringerButtonUiModel = this.$unselectedButtonUiModel;
                Function2 volumeDialogRingerViewBinder$$ExternalSyntheticLambda1 = this.$previousIndex == this.$count - 1 ? this.$onProgressChanged : new VolumeDialogRingerViewBinder$$ExternalSyntheticLambda1(2);
                this.label = 1;
                if (VolumeDialogRingerViewBinder.access$animateTo(volumeDialogRingerViewBinder, imageButton, ringerButtonUiModel, volumeDialogRingerViewBinder$$ExternalSyntheticLambda1, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $onAnimationEnd;
        final /* synthetic */ MotionLayout $this_animateAndBindDrawerButtons;
        final /* synthetic */ RingerViewModel $uiModel;
        final /* synthetic */ VolumeDialogRingerDrawerViewModel $viewModel;
        int label;
        final /* synthetic */ VolumeDialogRingerViewBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.this$0 = volumeDialogRingerViewBinder;
            this.$this_animateAndBindDrawerButtons = motionLayout;
            this.$viewModel = volumeDialogRingerDrawerViewModel;
            this.$uiModel = ringerViewModel;
            this.$onAnimationEnd = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$uiModel, this.$onAnimationEnd, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(300L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            VolumeDialogRingerViewBinder volumeDialogRingerViewBinder = this.this$0;
            MotionLayout motionLayout = this.$this_animateAndBindDrawerButtons;
            VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel = this.$viewModel;
            RingerViewModel ringerViewModel = this.$uiModel;
            Runnable runnable = this.$onAnimationEnd;
            KProperty[] kPropertyArr = VolumeDialogRingerViewBinder.$$delegatedProperties;
            volumeDialogRingerViewBinder.getClass();
            VolumeDialogRingerViewBinder.bindButtons(motionLayout, volumeDialogRingerDrawerViewModel, ringerViewModel, runnable, true);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3(ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, ImageButton imageButton2, RingerButtonUiModel ringerButtonUiModel2, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, RingerViewModel ringerViewModel, int i, Function2 function2, int i2, MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, Runnable runnable, Continuation continuation) {
        super(2, continuation);
        this.$selectedButton = imageButton;
        this.$selectedButtonUiModel = ringerButtonUiModel;
        this.$unselectedButton = imageButton2;
        this.$unselectedButtonUiModel = ringerButtonUiModel2;
        this.this$0 = volumeDialogRingerViewBinder;
        this.$uiModel = ringerViewModel;
        this.$count = i;
        this.$onProgressChanged = function2;
        this.$previousIndex = i2;
        this.$this_animateAndBindDrawerButtons = motionLayout;
        this.$viewModel = volumeDialogRingerDrawerViewModel;
        this.$onAnimationEnd = runnable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3 volumeDialogRingerViewBinder$animateAndBindDrawerButtons$3 = new VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3(this.$selectedButton, this.$selectedButtonUiModel, this.$unselectedButton, this.$unselectedButtonUiModel, this.this$0, this.$uiModel, this.$count, this.$onProgressChanged, this.$previousIndex, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$onAnimationEnd, continuation);
        volumeDialogRingerViewBinder$animateAndBindDrawerButtons$3.L$0 = obj;
        return volumeDialogRingerViewBinder$animateAndBindDrawerButtons$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        int cornerRadius = (int) ((GradientDrawable) this.$selectedButton.getBackground()).getCornerRadius();
        RingerButtonUiModel ringerButtonUiModel = this.$selectedButtonUiModel;
        if (cornerRadius != ringerButtonUiModel.cornerRadius) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, this.$selectedButton, ringerButtonUiModel, this.$uiModel, this.$count, this.$onProgressChanged, null), 6);
        }
        int cornerRadius2 = (int) ((GradientDrawable) this.$unselectedButton.getBackground()).getCornerRadius();
        RingerButtonUiModel ringerButtonUiModel2 = this.$unselectedButtonUiModel;
        if (cornerRadius2 != ringerButtonUiModel2.cornerRadius) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$unselectedButton, ringerButtonUiModel2, this.$previousIndex, this.$count, this.$onProgressChanged, null), 6);
        }
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$uiModel, this.$onAnimationEnd, null), 6);
    }
}
