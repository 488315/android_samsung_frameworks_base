package com.android.systemui.controls.ui;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Action;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ControlViewHolder {
    public static final int[] ATTR_DISABLED;
    public static final int[] ATTR_ENABLED;
    public static final Set FORCE_PANEL_DEVICES;
    public GradientDrawable baseLayer;
    public Behavior behavior;
    public final DelayableExecutor bgExecutor;
    public final CanUseIconPredicate canUseIconPredicate;
    public ClipDrawable clipLayer;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final int currentUserId;
    public ControlWithState cws;
    public final ImageView icon;
    public boolean isLoading;
    public ControlAction lastAction;
    public Dialog lastChallengeDialog;
    public final ViewGroup layout;
    public CharSequence nextStatusText;
    public final Function0 onDialogCancel;
    public final Lazy secControlViewHolder$delegate;
    public ValueAnimator stateAnimator;
    public final TextView status;
    public Animator statusAnimator;
    public final TextView subtitle;
    public final TextView title;
    public final float toggleBackgroundIntensity;
    public final DelayableExecutor uiExecutor;
    public final int uid;
    public Dialog visibleDialog;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FORCE_PANEL_DEVICES = SetsKt__SetsKt.setOf(49, 50);
        ATTR_ENABLED = new int[]{R.attr.state_enabled};
        ATTR_DISABLED = new int[]{-16842910};
    }

    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, int i, int i2) {
        this.layout = viewGroup;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.uid = i;
        this.currentUserId = i2;
        this.canUseIconPredicate = new CanUseIconPredicate(i2);
        this.toggleBackgroundIntensity = viewGroup.getContext().getResources().getFraction(com.android.systemui.R.fraction.controls_toggle_bg_intensity, 1, 1);
        this.icon = (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.icon);
        TextView textView = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.status);
        this.status = textView;
        this.nextStatusText = "";
        TextView textView2 = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.title);
        this.title = textView2;
        TextView textView3 = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.subtitle);
        this.subtitle = textView3;
        this.context = viewGroup.getContext();
        this.onDialogCancel = new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$onDialogCancel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.lastChallengeDialog = null;
                return Unit.INSTANCE;
            }
        };
        this.secControlViewHolder$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$secControlViewHolder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder controlViewHolder = ControlViewHolder.this;
                return new SecControlViewHolder(controlViewHolder.layout, controlViewHolder.icon, controlViewHolder.status, controlViewHolder.title, controlViewHolder.subtitle);
            }
        });
        Pair initClipLayerAndBaseLayer = getSecControlViewHolder().initClipLayerAndBaseLayer();
        this.clipLayer = (ClipDrawable) initClipLayerAndBaseLayer.getFirst();
        this.baseLayer = (GradientDrawable) initClipLayerAndBaseLayer.getSecond();
        textView.setSelected(true);
        viewGroup.measure(0, 0);
        int measuredWidth = viewGroup.getMeasuredWidth();
        int dimensionPixelSize = viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_control_padding);
        int dimensionPixelSize2 = ((measuredWidth - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_text_start_margin)) - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_text_end_margin)) - (dimensionPixelSize * 2);
        textView2.setMaxWidth(dimensionPixelSize2);
        textView3.setMaxWidth(dimensionPixelSize2);
        textView.setMaxWidth(dimensionPixelSize2);
        ControlsUtil.Companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView3, com.android.systemui.R.dimen.sec_control_text_size, 1.1f);
        ControlsUtil.Companion.updateFontSize(textView2, com.android.systemui.R.dimen.sec_control_text_size, 1.1f);
        ControlsUtil.Companion.updateFontSize(textView, com.android.systemui.R.dimen.sec_control_text_size, 1.1f);
    }

    public static Supplier findBehaviorClass$default(final ControlViewHolder controlViewHolder, int i, ControlTemplate controlTemplate, int i2) {
        controlViewHolder.getClass();
        return i != 1 ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new StatusBehavior();
            }
        } : controlTemplate.equals(ControlTemplate.NO_TEMPLATE) ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$2
            @Override // java.util.function.Supplier
            public final Object get() {
                return new TouchBehavior();
            }
        } : controlTemplate instanceof ThumbnailTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$4
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ThumbnailBehavior(ControlViewHolder.this.currentUserId);
            }
        } : i2 == 50 ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$5
            @Override // java.util.function.Supplier
            public final Object get() {
                return new TouchBehavior();
            }
        } : controlTemplate instanceof ToggleTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$6
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ToggleBehavior();
            }
        } : controlTemplate instanceof StatelessTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$7
            @Override // java.util.function.Supplier
            public final Object get() {
                return new TouchBehavior();
            }
        } : controlTemplate instanceof ToggleRangeTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$8
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ToggleRangeBehavior();
            }
        } : controlTemplate instanceof RangeTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$9
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ToggleRangeBehavior();
            }
        } : controlTemplate instanceof TemperatureControlTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$10
            @Override // java.util.function.Supplier
            public final Object get() {
                return new TemperatureControlBehavior();
            }
        } : new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$11
            @Override // java.util.function.Supplier
            public final Object get() {
                return new DefaultBehavior();
            }
        };
    }

    public final void action(ControlAction controlAction) {
        this.lastAction = controlAction;
        ControlWithState controlWithState = this.cws;
        ComponentName componentName = (controlWithState != null ? controlWithState : null).componentName;
        if (controlWithState == null) {
            controlWithState = null;
        }
        ControlInfo controlInfo = controlWithState.ci;
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.controlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) controlsControllerImpl.bindingController;
            if (controlsBindingControllerImpl.statefulControlSubscriber == null) {
                Log.w("ControlsBindingControllerImpl", "No actions can occur outside of an active subscription. Ignoring.");
                return;
            }
            ControlsProviderLifecycleManager retrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
            retrieveLifecycleManager.getClass();
            retrieveLifecycleManager.invokeOrQueue(retrieveLifecycleManager.new Action(controlInfo.controlId, controlAction));
        }
    }

    public final void animateStatusChange(boolean z, final Function0 function0) {
        Animator animator = this.statusAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            function0.invoke();
            return;
        }
        if (this.isLoading) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.status, "alpha", 0.45f);
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900L);
            ofFloat.start();
            this.statusAnimator = ofFloat;
            return;
        }
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.status, "alpha", 0.0f);
        ofFloat2.setDuration(200L);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                Function0.this.invoke();
            }
        });
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.status, "alpha", 1.0f);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(interpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(ofFloat2, ofFloat3);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ControlViewHolder.this.status.setAlpha(1.0f);
                ControlViewHolder.this.statusAnimator = null;
            }
        });
        animatorSet.start();
        this.statusAnimator = animatorSet;
    }

    public final void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i, final boolean z, boolean z2) {
        List listOf;
        ColorStateList color;
        ColorStateList customColor;
        int deviceType = (getControlStatus() == 1 || getControlStatus() == 0) ? getDeviceType() : -1000;
        RenderInfo.Companion companion = RenderInfo.Companion;
        Context context = this.context;
        ControlWithState controlWithState = this.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        ComponentName componentName = controlWithState.componentName;
        companion.getClass();
        final RenderInfo lookup = RenderInfo.Companion.lookup(context, componentName, deviceType, i);
        final ColorStateList colorStateList = this.context.getResources().getColorStateList(lookup.foreground, this.context.getTheme());
        final CharSequence charSequence = this.nextStatusText;
        ControlWithState controlWithState2 = this.cws;
        if (controlWithState2 == null) {
            controlWithState2 = null;
        }
        final Control control = controlWithState2.control;
        boolean z3 = Intrinsics.areEqual(charSequence, this.status.getText()) ? false : z2;
        animateStatusChange(z3, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code restructure failed: missing block: B:53:0x0148, code lost:
            
                if (r8 == null) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:82:0x01ac, code lost:
            
                if (r0 == null) goto L96;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:86:0x01c0  */
            /* JADX WARN: Type inference failed for: r0v22 */
            /* JADX WARN: Type inference failed for: r0v23, types: [com.airbnb.lottie.LottieAnimationView] */
            /* JADX WARN: Type inference failed for: r0v54 */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    Method dump skipped, instructions count: 570
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1.invoke():java.lang.Object");
            }
        });
        int color2 = this.context.getResources().getColor(com.android.systemui.R.color.sec_control_default_background, this.context.getTheme());
        if (z) {
            ControlWithState controlWithState3 = this.cws;
            Control control2 = (controlWithState3 != null ? controlWithState3 : null).control;
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf((control2 == null || (customColor = control2.getCustomColor()) == null) ? this.context.getResources().getColor(lookup.enabledBackground, this.context.getTheme()) : customColor.getColorForState(new int[]{R.attr.state_enabled}, customColor.getDefaultColor())), 255);
        } else {
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(color2), 0);
        }
        final int intValue = ((Number) listOf.get(0)).intValue();
        int intValue2 = ((Number) listOf.get(1)).intValue();
        if (this.behavior instanceof ToggleRangeBehavior) {
            color2 = ColorUtils.blendARGB(color2, intValue, this.toggleBackgroundIntensity);
        }
        final int i2 = color2;
        final Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            this.clipLayer.setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (!z3) {
                drawable.setAlpha(intValue2);
                if (drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setColor(intValue);
                }
                this.baseLayer.setColor(i2);
                this.layout.setAlpha(1.0f);
                return;
            }
            int defaultColor = (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) ? intValue : color.getDefaultColor();
            ColorStateList color3 = this.baseLayer.getColor();
            int defaultColor2 = color3 != null ? color3.getDefaultColor() : i2;
            final float alpha = this.layout.getAlpha();
            ValueAnimator ofInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), intValue2);
            final int i3 = defaultColor;
            final int i4 = defaultColor2;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    int intValue3 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    int blendARGB = ColorUtils.blendARGB(i3, intValue, valueAnimator2.getAnimatedFraction());
                    int blendARGB2 = ColorUtils.blendARGB(i4, i2, valueAnimator2.getAnimatedFraction());
                    float lerp = MathUtils.lerp(alpha, 1.0f, valueAnimator2.getAnimatedFraction());
                    ControlViewHolder controlViewHolder = this;
                    Drawable drawable2 = drawable;
                    Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                    controlViewHolder.getClass();
                    drawable2.setAlpha(intValue3);
                    if (drawable2 instanceof GradientDrawable) {
                        ((GradientDrawable) drawable2).setColor(blendARGB);
                    }
                    controlViewHolder.baseLayer.setColor(blendARGB2);
                    controlViewHolder.layout.setAlpha(lerp);
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ControlViewHolder.this.stateAnimator = null;
                }
            });
            ofInt.setDuration(700L);
            ofInt.setInterpolator(Interpolators.CONTROL_STATE);
            ofInt.start();
            this.stateAnimator = ofInt;
        }
    }

    public final Behavior bindBehavior(Behavior behavior, Supplier supplier, int i) {
        Behavior behavior2 = (Behavior) supplier.get();
        if (behavior == null || behavior.getClass() != behavior2.getClass()) {
            behavior2.initialize(this);
            this.layout.setAccessibilityDelegate(null);
            behavior = behavior2;
        }
        ControlWithState controlWithState = this.cws;
        behavior.bind(controlWithState != null ? controlWithState : null, i);
        return behavior;
    }

    public final int getControlStatus() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control = controlWithState.control;
        if (control != null) {
            return control.getStatus();
        }
        return 0;
    }

    public final ControlTemplate getControlTemplate() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control = controlWithState.control;
        ControlTemplate controlTemplate = control != null ? control.getControlTemplate() : null;
        return controlTemplate == null ? ControlTemplate.NO_TEMPLATE : controlTemplate;
    }

    public final int getDeviceType() {
        ControlWithState controlWithState = this.cws;
        Control control = (controlWithState != null ? controlWithState : null).control;
        if (control != null) {
            return control.getDeviceType();
        }
        if (controlWithState == null) {
            controlWithState = null;
        }
        return controlWithState.ci.deviceType;
    }

    public final SecControlViewHolder getSecControlViewHolder() {
        return (SecControlViewHolder) this.secControlViewHolder$delegate.getValue();
    }

    public final void setErrorStatus() {
        final String string = this.context.getResources().getString(com.android.systemui.R.string.controls_error_failed);
        animateStatusChange(true, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$setErrorStatus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.setStatusText(string, true);
                return Unit.INSTANCE;
            }
        });
    }

    public final void setStatusText(CharSequence charSequence, boolean z) {
        if (z) {
            this.status.setAlpha(1.0f);
            this.status.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    public final void updateContentDescription() {
        this.layout.setContentDescription(((Object) this.title.getText()) + " " + ((Object) this.subtitle.getText()) + " " + ((Object) this.status.getText()));
    }

    public final void updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        this.status.setEnabled(z);
        this.icon.setEnabled(z);
        SecControlViewHolder secControlViewHolder = getSecControlViewHolder();
        secControlViewHolder.title.setEnabled(z);
        secControlViewHolder.subtitle.setEnabled(z);
        this.status.setText(charSequence);
        updateContentDescription();
        this.status.setTextColor(colorStateList);
        if (control == null) {
            return;
        }
        Icon customIcon = control.getCustomIcon();
        Unit unit = null;
        if (customIcon != null) {
            if (!((Boolean) this.canUseIconPredicate.invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                this.icon.setImageIcon(customIcon);
                this.icon.setImageTintList(customIcon.getTintList());
                unit = Unit.INSTANCE;
            }
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (this.icon.getDrawable() == null || !(this.icon.getDrawable() instanceof StateListDrawable)) {
                    this.icon.setImageDrawable(drawable);
                }
                this.icon.setImageState(z ? ATTR_ENABLED : ATTR_DISABLED, true);
            } else {
                this.icon.setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                this.icon.setImageTintList(colorStateList);
            }
        }
    }

    public final boolean usePanel() {
        return FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) || getControlTemplate().equals(ControlTemplate.NO_TEMPLATE);
    }
}
