package com.android.systemui.keyguard.ui.binder;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Animatable2;
import android.os.VibrationEffect;
import android.util.Size;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import androidx.core.animation.Animator;
import androidx.core.animation.CycleInterpolator;
import androidx.core.animation.ObjectAnimator;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardBottomAreaViewBinder {
    public static final Companion Companion = new Companion(null);
    public static final KeyguardBottomAreaViewBinder INSTANCE = new KeyguardBottomAreaViewBinder();
    public final String TAG = "KeyguardBottomAreaViewBinder";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Binding {
        void destroy();

        void onConfigurationChanged();

        boolean shouldConstrainToTopOfLockIcon();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ConfigurationBasedDimensions {
        public final Size buttonSizePx;
        public final int defaultBurnInPreventionYOffsetPx;

        public ConfigurationBasedDimensions(int i, Size size) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.buttonSizePx = size;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && Intrinsics.areEqual(this.buttonSizePx, configurationBasedDimensions.buttonSizePx);
        }

        public final int hashCode() {
            return this.buttonSizePx.hashCode() + (Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31);
        }

        public final String toString() {
            return "ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=" + this.defaultBurnInPreventionYOffsetPx + ", buttonSizePx=" + this.buttonSizePx + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class OnClickListener implements View.OnClickListener {
        public final FalsingManager falsingManager;
        public final KeyguardQuickAffordanceViewModel viewModel;

        public OnClickListener(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, FalsingManager falsingManager) {
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.falsingManager = falsingManager;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel;
            String str;
            if (this.falsingManager.isFalseTap(1) || (str = (keyguardQuickAffordanceViewModel = this.viewModel).configKey) == null) {
                return;
            }
            Function1 function1 = keyguardQuickAffordanceViewModel.onClicked;
            Expandable.Companion.getClass();
            function1.invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(str, new Expandable$Companion$fromView$1(view), this.viewModel.slotId));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class OnLongClickListener implements View.OnLongClickListener {
        public final FalsingManager falsingManager;
        public final KeyguardQuickAffordanceOnTouchListener onTouchListener;
        public final VibratorHelper vibratorHelper;
        public final KeyguardQuickAffordanceViewModel viewModel;

        public OnLongClickListener(FalsingManager falsingManager, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, VibratorHelper vibratorHelper, KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener) {
            this.falsingManager = falsingManager;
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.vibratorHelper = vibratorHelper;
            this.onTouchListener = keyguardQuickAffordanceOnTouchListener;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            VibrationEffect vibrationEffect;
            FalsingManager falsingManager = this.falsingManager;
            if (falsingManager != null && falsingManager.isFalseLongTap(2)) {
                return true;
            }
            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = this.viewModel;
            String str = keyguardQuickAffordanceViewModel.configKey;
            if (str != null) {
                Function1 function1 = keyguardQuickAffordanceViewModel.onClicked;
                Expandable.Companion.getClass();
                function1.invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(str, new Expandable$Companion$fromView$1(view), this.viewModel.slotId));
                VibratorHelper vibratorHelper = this.vibratorHelper;
                if (vibratorHelper != null) {
                    if (this.viewModel.isActivated) {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Activated;
                    } else {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Deactivated;
                    }
                    vibratorHelper.vibrate(vibrationEffect);
                }
            }
            this.onTouchListener.cancel();
            return true;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClickUseDefaultHapticFeedback(View view) {
            return false;
        }
    }

    public static final void access$updateButton(KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, final ImageView imageView, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, FalsingManager falsingManager, final Function1 function1, final VibratorHelper vibratorHelper) {
        ColorStateList colorStateList;
        keyguardBottomAreaViewBinder.getClass();
        if (!keyguardQuickAffordanceViewModel.isVisible) {
            imageView.setVisibility(4);
            return;
        }
        if (imageView.getVisibility() != 0) {
            imageView.setVisibility(0);
            if (keyguardQuickAffordanceViewModel.animateReveal) {
                imageView.setAlpha(0.0f);
                imageView.setTranslationY(imageView.getHeight() / 2.0f);
                imageView.animate().alpha(1.0f).translationY(0.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setDuration(250L).start();
            }
        }
        IconViewBinder.INSTANCE.getClass();
        Icon icon = keyguardQuickAffordanceViewModel.icon;
        IconViewBinder.bind(icon, imageView);
        Object drawable = imageView.getDrawable();
        Animatable2 animatable2 = drawable instanceof Animatable2 ? (Animatable2) drawable : null;
        if (animatable2 != null) {
            Icon.Resource resource = icon instanceof Icon.Resource ? (Icon.Resource) icon : null;
            if (resource != null) {
                animatable2.start();
                Object tag = imageView.getTag();
                int i = resource.res;
                if (Intrinsics.areEqual(tag, Integer.valueOf(i))) {
                    animatable2.stop();
                } else {
                    imageView.setTag(Integer.valueOf(i));
                }
            }
        }
        boolean z = keyguardQuickAffordanceViewModel.isActivated;
        imageView.setActivated(z);
        imageView.getDrawable().setTint(Utils.getColorAttrDefaultColor(imageView.getContext(), z ? R.^attr-private.materialColorOnSecondaryContainer : R.^attr-private.materialColorOnTertiary, 0));
        boolean z2 = keyguardQuickAffordanceViewModel.isSelected;
        if (z2) {
            colorStateList = null;
        } else {
            colorStateList = Utils.getColorAttr(z ? R.^attr-private.materialColorSecondary : R.^attr-private.materialColorSurfaceContainerLowest, imageView.getContext());
        }
        imageView.setBackgroundTintList(colorStateList);
        imageView.animate().scaleX(z2 ? 1.23f : 1.0f).scaleY(z2 ? 1.23f : 1.0f).start();
        boolean z3 = keyguardQuickAffordanceViewModel.isClickable;
        imageView.setClickable(z3);
        if (!z3) {
            imageView.setOnLongClickListener(null);
            imageView.setOnClickListener(null);
            imageView.setOnTouchListener(null);
        } else if (keyguardQuickAffordanceViewModel.useLongPress) {
            KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener = new KeyguardQuickAffordanceOnTouchListener(imageView, keyguardQuickAffordanceViewModel, function1, vibratorHelper, falsingManager);
            imageView.setOnTouchListener(keyguardQuickAffordanceOnTouchListener);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButton$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1.this.invoke(Integer.valueOf(com.android.systemui.R.string.keyguard_affordance_press_too_short));
                    float dimensionPixelSize = imageView.getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_affordance_shake_amplitude);
                    float f = 2;
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, "translationX", (-dimensionPixelSize) / f, dimensionPixelSize / f);
                    KeyguardBottomAreaVibrations.INSTANCE.getClass();
                    ofFloat.m788setDuration(Duration.m2538getInWholeMillisecondsimpl(KeyguardBottomAreaVibrations.ShakeAnimationDuration));
                    ofFloat.mInterpolator = new CycleInterpolator(5.0f);
                    final ImageView imageView2 = imageView;
                    ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButton$2$onClick$$inlined$doOnEnd$1
                        @Override // androidx.core.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            imageView2.setTranslationX(0.0f);
                        }

                        @Override // androidx.core.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                        }

                        @Override // androidx.core.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                        }

                        @Override // androidx.core.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                        }
                    });
                    ofFloat.start();
                    VibratorHelper vibratorHelper2 = vibratorHelper;
                    if (vibratorHelper2 != null) {
                        vibratorHelper2.vibrate(KeyguardBottomAreaVibrations.Shake);
                    }
                }
            });
            imageView.setOnLongClickListener(new OnLongClickListener(falsingManager, keyguardQuickAffordanceViewModel, vibratorHelper, keyguardQuickAffordanceOnTouchListener));
        } else {
            if (falsingManager == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            imageView.setOnClickListener(new OnClickListener(keyguardQuickAffordanceViewModel, falsingManager));
        }
        imageView.setSelected(z2);
    }

    public static final Object access$updateButtonAlpha(KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, final View view, final ChannelFlowTransformLatest channelFlowTransformLatest, ChannelFlowTransformLatest channelFlowTransformLatest2, Continuation continuation) {
        keyguardBottomAreaViewBinder.getClass();
        Object collect = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel r5 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel) r5
                        boolean r5 = r5.isDimmed
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
            }
        }, channelFlowTransformLatest2, new KeyguardBottomAreaViewBinder$updateButtonAlpha$3(null)).collect(new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$updateButtonAlpha$4
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                view.setAlpha(((Number) obj).floatValue());
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public static ConfigurationBasedDimensions loadFromResources(View view) {
        return new ConfigurationBasedDimensions(view.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.default_burn_in_prevention_offset), new Size(view.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_affordance_fixed_width), view.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_affordance_fixed_height)));
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$4] */
    public final KeyguardBottomAreaViewBinder$bind$4 bind(final ViewGroup viewGroup, final KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, VibratorHelper vibratorHelper, ActivityStarter activityStarter, Function1 function1) {
        View findViewById = viewGroup.findViewById(com.android.systemui.R.id.ambient_indication_container);
        ImageView imageView = (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.start_button);
        ImageView imageView2 = (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.end_button);
        View requireViewById = viewGroup.requireViewById(com.android.systemui.R.id.overlay_container);
        final LaunchableLinearLayout launchableLinearLayout = (LaunchableLinearLayout) viewGroup.requireViewById(com.android.systemui.R.id.keyguard_settings_button);
        imageView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                int safeInsetBottom = displayCutout != null ? displayCutout.getSafeInsetBottom() : 0;
                int dimension = (int) view.getResources().getDimension(com.android.systemui.R.dimen.keyguard_affordance_vertical_offset);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                int i = marginLayoutParams2 != null ? marginLayoutParams2.leftMargin : 0;
                ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
                int i2 = marginLayoutParams3 != null ? marginLayoutParams3.topMargin : 0;
                ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams4 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : null;
                marginLayoutParams.setMargins(i, i2, marginLayoutParams4 != null ? marginLayoutParams4.rightMargin : 0, dimension + safeInsetBottom);
                view.setLayoutParams(marginLayoutParams);
                return WindowInsets.CONSUMED;
            }
        });
        imageView2.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                int safeInsetBottom = displayCutout != null ? displayCutout.getSafeInsetBottom() : 0;
                int dimension = (int) view.getResources().getDimension(com.android.systemui.R.dimen.keyguard_affordance_vertical_offset);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                int i = marginLayoutParams2 != null ? marginLayoutParams2.leftMargin : 0;
                ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
                int i2 = marginLayoutParams3 != null ? marginLayoutParams3.topMargin : 0;
                ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams4 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : null;
                marginLayoutParams.setMargins(i, i2, marginLayoutParams4 != null ? marginLayoutParams4.rightMargin : 0, dimension + safeInsetBottom);
                view.setLayoutParams(marginLayoutParams);
                return WindowInsets.CONSUMED;
            }
        });
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (LaunchableLinearLayout.this.getVisibility() != 0) {
                    return false;
                }
                Rect rect = new Rect();
                LaunchableLinearLayout.this.getHitRect(rect);
                if (rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    return false;
                }
                keyguardBottomAreaViewModel.longPressViewModel.interactor.hideMenu();
                return false;
            }
        });
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(loadFromResources(viewGroup));
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1 keyguardBottomAreaViewBinder$bind$disposableHandle$1 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1(this, activityStarter, keyguardBottomAreaViewModel, imageView, falsingManager, function1, vibratorHelper, imageView2, requireViewById, findViewById, MutableStateFlow, launchableLinearLayout, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        final RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached = RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, keyguardBottomAreaViewBinder$bind$disposableHandle$1);
        return new Binding() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$4
            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public final void destroy() {
                repeatWhenAttached.dispose();
            }

            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public final void onConfigurationChanged() {
                ViewGroup viewGroup2 = viewGroup;
                KeyguardBottomAreaViewBinder.Companion companion = KeyguardBottomAreaViewBinder.Companion;
                this.getClass();
                ((StateFlowImpl) MutableStateFlow.this).updateState(null, KeyguardBottomAreaViewBinder.loadFromResources(viewGroup2));
            }

            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public final boolean shouldConstrainToTopOfLockIcon() {
                return ((KeyguardRepositoryImpl) keyguardBottomAreaViewModel.bottomAreaInteractor.repository).keyguardUpdateMonitor.isUdfpsSupported();
            }
        };
    }
}
