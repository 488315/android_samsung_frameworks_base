package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.volume.dialog.ringer.ui.util.RingerDrawerConstraintsUtilsKt;
import com.android.systemui.volume.dialog.ringer.ui.util.VolumeDialogRingerDrawerTransitionListener;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonUiModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonViewModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerDrawerState;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModelState;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogRingerViewBinder implements ViewBinder {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final SpringForce colorSpringForce;
    public final VolumeDialogViewModel dialogViewModel;
    public final ArgbEvaluator rgbEvaluator;
    public final SpringForce roundnessSpringForce;
    public final VolumeDialogRingerDrawerViewModel viewModel;

    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
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

        /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateAndBindDrawerButtons$3$3, reason: invalid class name and collision with other inner class name */
        final class C06463 extends SuspendLambda implements Function2 {
            final /* synthetic */ Runnable $onAnimationEnd;
            final /* synthetic */ MotionLayout $this_animateAndBindDrawerButtons;
            final /* synthetic */ RingerViewModel $uiModel;
            final /* synthetic */ VolumeDialogRingerDrawerViewModel $viewModel;
            int label;
            final /* synthetic */ VolumeDialogRingerViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06463(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, Runnable runnable, Continuation continuation) {
                super(2, continuation);
                this.this$0 = volumeDialogRingerViewBinder;
                this.$this_animateAndBindDrawerButtons = motionLayout;
                this.$viewModel = volumeDialogRingerDrawerViewModel;
                this.$uiModel = ringerViewModel;
                this.$onAnimationEnd = runnable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C06463(this.this$0, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$uiModel, this.$onAnimationEnd, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06463) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        public AnonymousClass3(ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, ImageButton imageButton2, RingerButtonUiModel ringerButtonUiModel2, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, RingerViewModel ringerViewModel, int i, Function2 function2, int i2, MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, Runnable runnable, Continuation continuation) {
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
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$selectedButton, this.$selectedButtonUiModel, this.$unselectedButton, this.$unselectedButtonUiModel, this.this$0, this.$uiModel, this.$count, this.$onProgressChanged, this.$previousIndex, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$onAnimationEnd, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C06463(this.this$0, this.$this_animateAndBindDrawerButtons, this.$viewModel, this.$uiModel, this.$onAnimationEnd, null), 6);
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $ringerBackgroundView;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, Continuation continuation) {
            super(2, continuation);
            this.$ringerBackgroundView = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogRingerViewBinder.this.new AnonymousClass1(this.$ringerBackgroundView, continuation);
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
                VolumeDialogViewModel volumeDialogViewModel = VolumeDialogRingerViewBinder.this.dialogViewModel;
                View[] viewArr = {this.$ringerBackgroundView};
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

    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ReadWriteProperty $backgroundAnimationProgress$delegate;
        final /* synthetic */ float[] $bottomCornerRadii;
        final /* synthetic */ MotionLayout $drawerContainer;
        final /* synthetic */ View $ringerBackgroundView;
        final /* synthetic */ VolumeDialogRingerDrawerTransitionListener $ringerDrawerTransitionListener;
        final /* synthetic */ RingerButtonUiModel $selectedButtonUiModel;
        final /* synthetic */ RingerButtonUiModel $unselectedButtonUiModel;
        final /* synthetic */ View $view;
        final /* synthetic */ View $volumeDialogBackgroundView;
        int I$0;
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ VolumeDialogRingerViewBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(View view, MotionLayout motionLayout, View view2, float[] fArr, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, RingerButtonUiModel ringerButtonUiModel, RingerButtonUiModel ringerButtonUiModel2, VolumeDialogRingerDrawerTransitionListener volumeDialogRingerDrawerTransitionListener, View view3, ReadWriteProperty readWriteProperty, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$drawerContainer = motionLayout;
            this.$volumeDialogBackgroundView = view2;
            this.$bottomCornerRadii = fArr;
            this.this$0 = volumeDialogRingerViewBinder;
            this.$selectedButtonUiModel = ringerButtonUiModel;
            this.$unselectedButtonUiModel = ringerButtonUiModel2;
            this.$ringerDrawerTransitionListener = volumeDialogRingerDrawerTransitionListener;
            this.$ringerBackgroundView = view3;
            this.$backgroundAnimationProgress$delegate = readWriteProperty;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$view, this.$drawerContainer, this.$volumeDialogBackgroundView, this.$bottomCornerRadii, this.this$0, this.$selectedButtonUiModel, this.$unselectedButtonUiModel, this.$ringerDrawerTransitionListener, this.$ringerBackgroundView, this.$backgroundAnimationProgress$delegate, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((RingerViewModelState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x00e0, code lost:
        
            if (r0.animateAndBindDrawerButtons(r1, r9, r3, r10, r12, r15, r7, r16) == r11) goto L36;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0111  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0116  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x013c  */
        /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2$2] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            RingerViewModel ringerViewModel;
            int i;
            RingerViewModel ringerViewModel2;
            int i2;
            int i3;
            char c;
            int i4;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i5 = this.label;
            if (i5 == 0) {
                ResultKt.throwOnFailure(obj);
                RingerViewModelState ringerViewModelState = (RingerViewModelState) this.L$0;
                if (ringerViewModelState instanceof RingerViewModelState.Available) {
                    RingerViewModelState.Available available = (RingerViewModelState.Available) ringerViewModelState;
                    final RingerViewModel ringerViewModel3 = available.uiModel;
                    int i6 = this.$view.getContext().getResources().getBoolean(R.bool.volume_dialog_ringer_drawer_should_open_to_the_side) ? available.orientation : 1;
                    this.$drawerContainer.setVisibility(0);
                    ((GradientDrawable) this.$volumeDialogBackgroundView.getBackground()).setCornerRadii(this.$bottomCornerRadii);
                    RingerDrawerState ringerDrawerState = ringerViewModel3.drawerState;
                    if (ringerDrawerState instanceof RingerDrawerState.Initial) {
                        VolumeDialogRingerViewBinder volumeDialogRingerViewBinder = this.this$0;
                        MotionLayout motionLayout = this.$drawerContainer;
                        VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel = volumeDialogRingerViewBinder.viewModel;
                        RingerButtonUiModel ringerButtonUiModel = this.$selectedButtonUiModel;
                        RingerButtonUiModel ringerButtonUiModel2 = this.$unselectedButtonUiModel;
                        this.L$0 = ringerViewModel3;
                        this.I$0 = i6;
                        this.label = 1;
                        if (volumeDialogRingerViewBinder.animateAndBindDrawerButtons(motionLayout, volumeDialogRingerDrawerViewModel, ringerViewModel3, ringerButtonUiModel, ringerButtonUiModel2, new VolumeDialogRingerViewBinder$$ExternalSyntheticLambda1(0), null, this) != coroutineSingletons) {
                            ringerViewModel2 = ringerViewModel3;
                            i2 = i6;
                            this.$ringerDrawerTransitionListener.notifyProgressChangeEnabled = true;
                            VolumeDialogRingerViewBinder.access$closeDrawer(this.this$0, this.$drawerContainer, this.$ringerBackgroundView, ringerViewModel2.currentButtonIndex, i2);
                        }
                    } else if (ringerDrawerState instanceof RingerDrawerState.Closed) {
                        int i7 = ringerViewModel3.selectedButton.ringerMode;
                        int i8 = ((RingerDrawerState.Closed) ringerDrawerState).currentMode;
                        Set set = RingerMode.supportedRingerModes;
                        if (i7 == i8) {
                            final VolumeDialogRingerViewBinder volumeDialogRingerViewBinder2 = this.this$0;
                            final MotionLayout motionLayout2 = this.$drawerContainer;
                            VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel2 = volumeDialogRingerViewBinder2.viewModel;
                            RingerButtonUiModel ringerButtonUiModel3 = this.$selectedButtonUiModel;
                            RingerButtonUiModel ringerButtonUiModel4 = this.$unselectedButtonUiModel;
                            final ReadWriteProperty readWriteProperty = this.$backgroundAnimationProgress$delegate;
                            Function2 function2 = new Function2() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj2, Object obj3) {
                                    float fFloatValue = ((Float) obj2).floatValue();
                                    if (((Boolean) obj3).booleanValue()) {
                                        fFloatValue = 1.0f - fFloatValue;
                                    }
                                    readWriteProperty.setValue(null, VolumeDialogRingerViewBinder.$$delegatedProperties[0], Float.valueOf(fFloatValue));
                                    return Unit.INSTANCE;
                                }
                            };
                            final VolumeDialogRingerDrawerTransitionListener volumeDialogRingerDrawerTransitionListener = this.$ringerDrawerTransitionListener;
                            final View view = this.$ringerBackgroundView;
                            final int i9 = i6;
                            ?? r7 = new Runnable() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder.bind.2.2
                                @Override // java.lang.Runnable
                                public final void run() throws Throwable {
                                    RingerViewModel ringerViewModel4 = ringerViewModel3;
                                    if (ringerViewModel4.currentButtonIndex == ringerViewModel4.availableButtons.size() - 1) {
                                        volumeDialogRingerDrawerTransitionListener.notifyProgressChangeEnabled = false;
                                    } else {
                                        volumeDialogRingerDrawerTransitionListener.notifyProgressChangeEnabled = true;
                                    }
                                    VolumeDialogRingerViewBinder.access$closeDrawer(volumeDialogRingerViewBinder2, motionLayout2, view, ringerViewModel3.currentButtonIndex, i9);
                                }
                            };
                            this.label = 2;
                        }
                    } else {
                        if (!(ringerDrawerState instanceof RingerDrawerState.Open)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        VolumeDialogRingerViewBinder volumeDialogRingerViewBinder3 = this.this$0;
                        MotionLayout motionLayout3 = this.$drawerContainer;
                        VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel3 = volumeDialogRingerViewBinder3.viewModel;
                        RingerButtonUiModel ringerButtonUiModel5 = this.$selectedButtonUiModel;
                        RingerButtonUiModel ringerButtonUiModel6 = this.$unselectedButtonUiModel;
                        this.L$0 = ringerViewModel3;
                        this.I$0 = i6;
                        this.label = 3;
                        if (volumeDialogRingerViewBinder3.animateAndBindDrawerButtons(motionLayout3, volumeDialogRingerDrawerViewModel3, ringerViewModel3, ringerButtonUiModel5, ringerButtonUiModel6, new VolumeDialogRingerViewBinder$$ExternalSyntheticLambda1(0), null, this) != coroutineSingletons) {
                            ringerViewModel = ringerViewModel3;
                            i = i6;
                            if (ringerViewModel.currentButtonIndex != ringerViewModel.availableButtons.size() - 1) {
                            }
                            MotionLayout motionLayout4 = this.$drawerContainer;
                            View view2 = this.$ringerBackgroundView;
                            int i10 = R.id.volume_dialog_ringer_drawer_open;
                            ConstraintSet constraintSetCloneConstraintSet = motionLayout4.cloneConstraintSet(R.id.volume_dialog_ringer_drawer_open);
                            constraintSetCloneConstraintSet.setVisibility(view2.getId(), 0);
                            int i11 = 0;
                            while (r2.hasNext()) {
                            }
                            motionLayout4.updateState(i10, constraintSetCloneConstraintSet);
                            MotionScene.Transition transition = this.$drawerContainer.getTransition(R.id.close_to_open_transition);
                            transition.mDefaultInterpolator = -2;
                            transition.mDefaultInterpolatorString = null;
                            transition.mDefaultInterpolatorID = R.anim.volume_dialog_ringer_open;
                            this.$drawerContainer.transitionToState(R.id.volume_dialog_ringer_drawer_open);
                            View view3 = this.$ringerBackgroundView;
                            view3.setBackground(view3.getBackground().mutate());
                        }
                    }
                    return coroutineSingletons;
                }
                if (!(ringerViewModelState instanceof RingerViewModelState.Unavailable)) {
                    throw new NoWhenBranchMatchedException();
                }
                this.$drawerContainer.setVisibility(8);
                this.$volumeDialogBackgroundView.setBackgroundResource(R.drawable.volume_dialog_background);
            } else if (i5 == 1) {
                i2 = this.I$0;
                ringerViewModel2 = (RingerViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                this.$ringerDrawerTransitionListener.notifyProgressChangeEnabled = true;
                VolumeDialogRingerViewBinder.access$closeDrawer(this.this$0, this.$drawerContainer, this.$ringerBackgroundView, ringerViewModel2.currentButtonIndex, i2);
            } else if (i5 == 2) {
                ResultKt.throwOnFailure(obj);
            } else {
                if (i5 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i = this.I$0;
                ringerViewModel = (RingerViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                if (ringerViewModel.currentButtonIndex != ringerViewModel.availableButtons.size() - 1) {
                    this.$ringerDrawerTransitionListener.notifyProgressChangeEnabled = false;
                } else {
                    this.$ringerDrawerTransitionListener.notifyProgressChangeEnabled = true;
                }
                MotionLayout motionLayout42 = this.$drawerContainer;
                View view22 = this.$ringerBackgroundView;
                int i102 = R.id.volume_dialog_ringer_drawer_open;
                ConstraintSet constraintSetCloneConstraintSet2 = motionLayout42.cloneConstraintSet(R.id.volume_dialog_ringer_drawer_open);
                constraintSetCloneConstraintSet2.setVisibility(view22.getId(), 0);
                int i112 = 0;
                for (Object obj2 : ConvenienceExtensionsKt.getChildren(motionLayout42)) {
                    int i12 = i112 + 1;
                    if (i112 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    View view4 = (View) obj2;
                    if (view4.getId() != R.id.ringer_buttons_background) {
                        constraintSetCloneConstraintSet2.setAlpha(view4.getId(), 1.0f);
                        constraintSetCloneConstraintSet2.constrainWidth(view4.getId(), motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                        constraintSetCloneConstraintSet2.constrainHeight(view4.getId(), motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                        if (i == 1) {
                            if (i112 == 1) {
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 6, 0);
                            }
                            RingerDrawerConstraintsUtilsKt.setButtonPositionPortraitConstraints(constraintSetCloneConstraintSet2, motionLayout42, i112, view4);
                            if (i112 != motionLayout42.getChildCount() - 1) {
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 4, motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_buttons_spacing));
                                i3 = 0;
                            } else {
                                i3 = 0;
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 4, 0);
                            }
                            constraintSetCloneConstraintSet2.setMargin(view4.getId(), 7, i3);
                        } else if (i != 2) {
                            i3 = 0;
                        } else {
                            if (i112 == 1) {
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 6, motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_background_margin));
                            }
                            RingerDrawerConstraintsUtilsKt.setButtonPositionLandscapeConstraints(constraintSetCloneConstraintSet2, motionLayout42, i112, view4);
                            if (i112 != motionLayout42.getChildCount() - 1) {
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 7, motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_buttons_spacing));
                                i4 = 0;
                            } else {
                                i4 = 0;
                                constraintSetCloneConstraintSet2.setMargin(view4.getId(), 7, 0);
                            }
                            constraintSetCloneConstraintSet2.setMargin(view4.getId(), 4, i4);
                            i3 = i4;
                        }
                        c = 3;
                    } else {
                        i3 = 0;
                        constraintSetCloneConstraintSet2.constrainWidth(view4.getId(), i != 1 ? i != 2 ? 0 : (motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_background_margin) * 2) + ((motionLayout42.getChildCount() - 1) * motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size)) + ((motionLayout42.getChildCount() - 2) * motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_buttons_spacing)) : motionLayout42.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_width));
                        constraintSetCloneConstraintSet2.connect(view4.getId(), 4, motionLayout42.getId(), 4);
                        constraintSetCloneConstraintSet2.connect(view4.getId(), 6, motionLayout42.getChildAt(1).getId(), 6);
                        constraintSetCloneConstraintSet2.connect(view4.getId(), 7, motionLayout42.getId(), 7);
                        c = 3;
                        constraintSetCloneConstraintSet2.connect(view4.getId(), 3, motionLayout42.getChildAt(1).getId(), 3);
                    }
                    i112 = i12;
                    i102 = R.id.volume_dialog_ringer_drawer_open;
                }
                motionLayout42.updateState(i102, constraintSetCloneConstraintSet2);
                MotionScene.Transition transition2 = this.$drawerContainer.getTransition(R.id.close_to_open_transition);
                transition2.mDefaultInterpolator = -2;
                transition2.mDefaultInterpolatorString = null;
                transition2.mDefaultInterpolatorID = R.anim.volume_dialog_ringer_open;
                this.$drawerContainer.transitionToState(R.id.volume_dialog_ringer_drawer_open);
                View view32 = this.$ringerBackgroundView;
                view32.setBackground(view32.getBackground().mutate());
            }
            return Unit.INSTANCE;
        }
    }

    static {
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(VolumeDialogRingerViewBinder.class, "backgroundAnimationProgress", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference0Impl};
    }

    public VolumeDialogRingerViewBinder(VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogRingerDrawerViewModel;
        this.dialogViewModel = volumeDialogViewModel;
        SpringForce springForce = new SpringForce(1.0f);
        springForce.setStiffness(800.0f);
        springForce.setDampingRatio(0.6f);
        this.roundnessSpringForce = springForce;
        SpringForce springForce2 = new SpringForce(1.0f);
        springForce2.setStiffness(3800.0f);
        springForce2.setDampingRatio(1.0f);
        this.colorSpringForce = springForce2;
        this.rgbEvaluator = new ArgbEvaluator();
    }

    public static final Object access$animateTo(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, Function2 function2, SuspendLambda suspendLambda) {
        volumeDialogRingerViewBinder.getClass();
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(0.0f), 1.0f);
        springAnimation.mSpring = volumeDialogRingerViewBinder.roundnessSpringForce;
        SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(0.0f), 1.0f);
        springAnimation2.mSpring = volumeDialogRingerViewBinder.colorSpringForce;
        float cornerRadius = ((GradientDrawable) imageButton.getBackground()).getCornerRadius();
        float cornerRadius2 = ringerButtonUiModel.cornerRadius - ((GradientDrawable) imageButton.getBackground()).getCornerRadius();
        springAnimation.setMinimumVisibleChange(0.05f);
        springAnimation2.setMinimumVisibleChange(0.05f);
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new VolumeDialogRingerViewBinder$animateTo$3(springAnimation, springAnimation2, volumeDialogRingerViewBinder, imageButton, ringerButtonUiModel, function2, cornerRadius2, cornerRadius, null), suspendLambda);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public static final void access$closeDrawer(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, MotionLayout motionLayout, View view, int i, int i2) throws Throwable {
        Throwable th;
        volumeDialogRingerViewBinder.getClass();
        motionLayout.setTransition(R.id.close_to_open_transition);
        MotionScene.Transition transition = motionLayout.getTransition(R.id.close_to_open_transition);
        transition.mDefaultInterpolator = -2;
        Throwable th2 = null;
        transition.mDefaultInterpolatorString = null;
        transition.mDefaultInterpolatorID = R.anim.volume_dialog_ringer_close;
        ConstraintSet constraintSetCloneConstraintSet = motionLayout.cloneConstraintSet(R.id.volume_dialog_ringer_drawer_close);
        constraintSetCloneConstraintSet.setVisibility(view.getId(), 0);
        int i3 = 0;
        for (Object obj : ConvenienceExtensionsKt.getChildren(motionLayout)) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                Throwable th3 = th2;
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw th3;
            }
            View view2 = (View) obj;
            if (view2.getId() != R.id.ringer_buttons_background) {
                constraintSetCloneConstraintSet.setMargin(view2.getId(), 7, 0);
                constraintSetCloneConstraintSet.setMargin(view2.getId(), 4, 0);
                if (i2 != 1) {
                    th = th2;
                    if (i2 == 2) {
                        RingerDrawerConstraintsUtilsKt.setButtonPositionLandscapeConstraints(constraintSetCloneConstraintSet, motionLayout, i3, view2);
                        if (i != (motionLayout.getChildCount() - i3) - 1) {
                            constraintSetCloneConstraintSet.setAlpha(view2.getId(), 0.0f);
                            constraintSetCloneConstraintSet.constrainWidth(view2.getId(), (int) TypedValue.applyDimension(1, 1.0f, motionLayout.getContext().getResources().getDisplayMetrics()));
                        } else {
                            constraintSetCloneConstraintSet.connect(view2.getId(), 7, motionLayout.getId(), 7);
                            constraintSetCloneConstraintSet.setAlpha(view2.getId(), 1.0f);
                            constraintSetCloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                        }
                        constraintSetCloneConstraintSet.constrainHeight(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                    }
                } else {
                    th = th2;
                    RingerDrawerConstraintsUtilsKt.setButtonPositionPortraitConstraints(constraintSetCloneConstraintSet, motionLayout, i3, view2);
                    if (i != (motionLayout.getChildCount() - i3) - 1) {
                        constraintSetCloneConstraintSet.setAlpha(view2.getId(), 0.0f);
                        constraintSetCloneConstraintSet.constrainHeight(view2.getId(), (int) TypedValue.applyDimension(1, 1.0f, motionLayout.getContext().getResources().getDisplayMetrics()));
                    } else {
                        constraintSetCloneConstraintSet.setAlpha(view2.getId(), 1.0f);
                        constraintSetCloneConstraintSet.constrainHeight(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                    }
                    constraintSetCloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_drawer_button_size));
                }
            } else {
                th = th2;
                constraintSetCloneConstraintSet.constrainWidth(view2.getId(), motionLayout.getContext().getResources().getDimensionPixelSize(R.dimen.volume_dialog_width));
                constraintSetCloneConstraintSet.connect(view2.getId(), 4, motionLayout.getId(), 4);
                constraintSetCloneConstraintSet.connect(view2.getId(), 6, motionLayout.getChildAt((motionLayout.getChildCount() - i) - 1).getId(), 6);
                constraintSetCloneConstraintSet.connect(view2.getId(), 7, motionLayout.getId(), 7);
                constraintSetCloneConstraintSet.connect(view2.getId(), 3, motionLayout.getChildAt((motionLayout.getChildCount() - i) - 1).getId(), 3);
            }
            th2 = th;
            i3 = i4;
        }
        motionLayout.updateState(R.id.volume_dialog_ringer_drawer_close, constraintSetCloneConstraintSet);
        motionLayout.transitionToState(R.id.volume_dialog_ringer_drawer_close);
    }

    public static void bindButtons(MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, Runnable runnable, boolean z) {
        int size = ringerViewModel.availableButtons.size();
        List list = ringerViewModel.availableButtons;
        int size2 = list.size();
        for (int i = 0; i < size2; i++) {
            RingerButtonViewModel ringerButtonViewModel = (RingerButtonViewModel) list.get(i);
            ImageButton imageButton = (ImageButton) motionLayout.getChildAt(size - i);
            boolean z2 = ringerViewModel.drawerState instanceof RingerDrawerState.Open;
            if (i == ringerViewModel.currentButtonIndex) {
                if (!z2) {
                    ringerButtonViewModel = ringerViewModel.selectedButton;
                }
                bindDrawerButton(imageButton, ringerButtonViewModel, volumeDialogRingerDrawerViewModel, z2, true, z);
            } else {
                bindDrawerButton(imageButton, ringerButtonViewModel, volumeDialogRingerDrawerViewModel, z2, false, z);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void bindDrawerButton(ImageButton imageButton, final RingerButtonViewModel ringerButtonViewModel, final VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, boolean z, final boolean z2, boolean z3) {
        imageButton.setSelected(z2);
        String string = imageButton.getContext().getString(ringerButtonViewModel.contentDescriptionResId);
        imageButton.setImageResource(ringerButtonViewModel.imageResId);
        if (z2 && !z) {
            string = imageButton.getContext().getString(R.string.volume_ringer_drawer_closed_content_description, string);
        }
        imageButton.setContentDescription(string);
        if (z2 && !z3) {
            imageButton.setBackgroundResource(R.drawable.volume_drawer_selection_bg);
            imageButton.setColorFilter(imageButton.getContext().getColor(android.R.color.resolver_profile_tab_text));
            imageButton.setBackground(imageButton.getBackground().mutate());
        } else if (!z3) {
            imageButton.setBackgroundResource(R.drawable.volume_ringer_item_bg);
            imageButton.setColorFilter(imageButton.getContext().getColor(android.R.color.search_url_text_material_light));
            imageButton.setBackground(imageButton.getBackground().mutate());
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder.bindDrawerButton.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                volumeDialogRingerDrawerViewModel.m3218onRingerButtonClicked28s9KyU(ringerButtonViewModel.ringerMode, z2);
            }
        });
    }

    public final Object animateAndBindDrawerButtons(MotionLayout motionLayout, VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, RingerViewModel ringerViewModel, RingerButtonUiModel ringerButtonUiModel, RingerButtonUiModel ringerButtonUiModel2, Function2 function2, AnonymousClass2.RunnableC06472 runnableC06472, Continuation continuation) {
        int childCount = (motionLayout.getChildCount() - ringerViewModel.availableButtons.size()) - 1;
        int i = 0;
        if (childCount > 0) {
            motionLayout.removeViews(0, childCount);
        } else if (childCount < 0) {
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(motionLayout.getContext());
            int i2 = -childCount;
            for (int i3 = 0; i3 < i2; i3++) {
                layoutInflaterFrom.inflate(R.layout.volume_ringer_button, (ViewGroup) motionLayout, true);
                motionLayout.getChildAt(motionLayout.getChildCount() - 1).setId(View.generateViewId());
            }
        }
        RingerDrawerState ringerDrawerState = ringerViewModel.drawerState;
        if (ringerDrawerState instanceof RingerDrawerState.Closed) {
            RingerDrawerState.Closed closed = (RingerDrawerState.Closed) ringerDrawerState;
            int i4 = closed.currentMode;
            int i5 = closed.previousMode;
            Set set = RingerMode.supportedRingerModes;
            if (i4 != i5) {
                int size = ringerViewModel.availableButtons.size();
                ImageButton imageButton = (ImageButton) motionLayout.getChildAt(size - ringerViewModel.currentButtonIndex);
                Iterator it = ringerViewModel.availableButtons.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    if (((RingerButtonViewModel) it.next()).ringerMode == closed.previousMode) {
                        break;
                    }
                    i++;
                }
                int i6 = i;
                Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass3(imageButton, ringerButtonUiModel, (ImageButton) motionLayout.getChildAt(size - i6), ringerButtonUiModel2, this, ringerViewModel, size, function2, i6, motionLayout, volumeDialogRingerDrawerViewModel, runnableC06472, null), continuation);
                return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
            }
        }
        bindButtons(motionLayout, volumeDialogRingerDrawerViewModel, ringerViewModel, runnableC06472, false);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$$inlined$observable$1, kotlin.properties.ReadWriteProperty] */
    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        View viewRequireViewById = view.requireViewById(R.id.volume_dialog_background);
        final View viewRequireViewById2 = view.requireViewById(R.id.ringer_buttons_background);
        MotionLayout motionLayout = (MotionLayout) view.requireViewById(R.id.volume_ringer_drawer);
        RingerButtonUiModel.Companion companion = RingerButtonUiModel.Companion;
        Context context = view.getContext();
        companion.getClass();
        RingerButtonUiModel ringerButtonUiModel = new RingerButtonUiModel(context.getColor(android.R.color.search_url_text_material_light), context.getColor(android.R.color.suggestion_highlight_text), context.getResources().getDimensionPixelSize(R.dimen.volume_dialog_background_square_corner_radius));
        Context context2 = view.getContext();
        RingerButtonUiModel ringerButtonUiModel2 = new RingerButtonUiModel(context2.getColor(android.R.color.resolver_profile_tab_text), context2.getColor(android.R.color.secondary_text_inverse_when_activated_material), context2.getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_selected_button_background_radius));
        final int iM = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.volume_dialog_background_square_corner_radius);
        final int iM2 = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.volume_dialog_background_corner_radius);
        float f = iM2;
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, f, f, f, f};
        Delegates delegates = Delegates.INSTANCE;
        final Float fValueOf = Float.valueOf(0.0f);
        final ?? r0 = new ObservableProperty(fValueOf) { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$$inlined$observable$1
            @Override // kotlin.properties.ObservableProperty
            public final void afterChange(Object obj, Object obj2) {
                float fFloatValue = ((Number) obj2).floatValue();
                ((Number) obj).floatValue();
                View view2 = viewRequireViewById2;
                int i = iM2;
                int i2 = i - iM;
                KProperty[] kPropertyArr = VolumeDialogRingerViewBinder.$$delegatedProperties;
                this.getClass();
                ((GradientDrawable) view2.getBackground()).setCornerRadius(i - (fFloatValue * i2));
                view2.getBackground().invalidateSelf();
            }
        };
        VolumeDialogRingerDrawerTransitionListener volumeDialogRingerDrawerTransitionListener = new VolumeDialogRingerDrawerTransitionListener(new Function1() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f2 = (Float) obj;
                f2.floatValue();
                setValue(null, VolumeDialogRingerViewBinder.$$delegatedProperties[0], f2);
                return Unit.INSTANCE;
            }
        });
        motionLayout.setTransitionListener(volumeDialogRingerDrawerTransitionListener);
        viewRequireViewById.setBackground(viewRequireViewById.getBackground().mutate());
        viewRequireViewById2.setBackground(viewRequireViewById2.getBackground().mutate());
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(viewRequireViewById2, null), 6);
        CoroutineTracingKt.launchInTraced(FlowKt.mapLatest(this.viewModel.ringerViewModel, new AnonymousClass2(view, motionLayout, viewRequireViewById, fArr, this, ringerButtonUiModel2, ringerButtonUiModel, volumeDialogRingerDrawerTransitionListener, viewRequireViewById2, r0, null)), coroutineScope);
    }
}
