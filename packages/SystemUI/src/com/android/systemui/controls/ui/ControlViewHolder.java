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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.CustomControl;
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
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Action;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.ui.SecRenderInfo;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.SafeIconLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
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
    public final ControlViewHolder$$ExternalSyntheticLambda0 onDialogCancel;
    public final SafeIconLoader safeIconLoader;
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        FORCE_PANEL_DEVICES = ArraysKt___ArraysKt.toSet(new Integer[]{49, 50});
        ATTR_ENABLED = new int[]{R.attr.state_enabled};
        ATTR_DISABLED = new int[]{-16842910};
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.controls.ui.ControlViewHolder$$ExternalSyntheticLambda0] */
    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, int i, int i2, SafeIconLoader safeIconLoader) throws Resources.NotFoundException {
        this.layout = viewGroup;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.uid = i;
        this.currentUserId = i2;
        this.safeIconLoader = safeIconLoader;
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
        final int i3 = 0;
        this.onDialogCancel = new Function0(this) { // from class: com.android.systemui.controls.ui.ControlViewHolder$$ExternalSyntheticLambda0
            public final /* synthetic */ ControlViewHolder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder controlViewHolder = this.f$0;
                switch (i3) {
                    case 0:
                        controlViewHolder.lastChallengeDialog = null;
                        return Unit.INSTANCE;
                    default:
                        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                        return new SecControlViewHolder(controlViewHolder.layout, controlViewHolder.icon, controlViewHolder.status, controlViewHolder.title, controlViewHolder.subtitle);
                }
            }
        };
        final int i4 = 1;
        this.secControlViewHolder$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.controls.ui.ControlViewHolder$$ExternalSyntheticLambda0
            public final /* synthetic */ ControlViewHolder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder controlViewHolder = this.f$0;
                switch (i4) {
                    case 0:
                        controlViewHolder.lastChallengeDialog = null;
                        return Unit.INSTANCE;
                    default:
                        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                        return new SecControlViewHolder(controlViewHolder.layout, controlViewHolder.icon, controlViewHolder.status, controlViewHolder.title, controlViewHolder.subtitle);
                }
            }
        });
        Pair pairInitClipLayerAndBaseLayer = getSecControlViewHolder().initClipLayerAndBaseLayer();
        this.clipLayer = (ClipDrawable) pairInitClipLayerAndBaseLayer.getFirst();
        this.baseLayer = (GradientDrawable) pairInitClipLayerAndBaseLayer.getSecond();
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
        } : Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE) ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$2
            @Override // java.util.function.Supplier
            public final Object get() {
                return new TouchBehavior();
            }
        } : controlTemplate instanceof ThumbnailTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$4
            @Override // java.util.function.Supplier
            public final Object get() {
                ControlViewHolder controlViewHolder2 = this.this$0;
                return new ThumbnailBehavior(controlViewHolder2.currentUserId, controlViewHolder2.safeIconLoader);
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
            ControlsProviderLifecycleManager controlsProviderLifecycleManagerRetrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
            String str = controlInfo.controlId;
            controlsProviderLifecycleManagerRetrieveLifecycleManager.getClass();
            controlsProviderLifecycleManagerRetrieveLifecycleManager.invokeOrQueue(controlsProviderLifecycleManagerRetrieveLifecycleManager.new Action(str, controlAction));
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
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.status, "alpha", 0.45f);
            objectAnimatorOfFloat.setRepeatMode(2);
            objectAnimatorOfFloat.setRepeatCount(-1);
            objectAnimatorOfFloat.setDuration(500L);
            objectAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
            objectAnimatorOfFloat.setStartDelay(900L);
            objectAnimatorOfFloat.start();
            this.statusAnimator = objectAnimatorOfFloat;
            return;
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.status, "alpha", 0.0f);
        objectAnimatorOfFloat2.setDuration(200L);
        Interpolator interpolator = Interpolators.LINEAR;
        objectAnimatorOfFloat2.setInterpolator(interpolator);
        objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                function0.invoke();
            }
        });
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.status, "alpha", 1.0f);
        objectAnimatorOfFloat3.setDuration(200L);
        objectAnimatorOfFloat3.setInterpolator(interpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                this.this$0.status.setAlpha(1.0f);
                this.this$0.statusAnimator = null;
            }
        });
        animatorSet.start();
        this.statusAnimator = animatorSet;
    }

    public final void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i, final boolean z, boolean z2) throws Resources.NotFoundException {
        List listAsList;
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
        final RenderInfo renderInfoLookup = RenderInfo.Companion.lookup(context, componentName, deviceType, i);
        final ColorStateList colorStateList = this.context.getResources().getColorStateList(renderInfoLookup.foreground, this.context.getTheme());
        final CharSequence charSequence = this.nextStatusText;
        ControlWithState controlWithState2 = this.cws;
        if (controlWithState2 == null) {
            controlWithState2 = null;
        }
        final Control control = controlWithState2.control;
        boolean z3 = Intrinsics.areEqual(charSequence, this.status.getText()) ? false : z2;
        animateStatusChange(z3, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:62:0x013c  */
            /* JADX WARN: Removed duplicated region for block: B:78:0x017d  */
            /* JADX WARN: Removed duplicated region for block: B:92:0x01a1  */
            /* JADX WARN: Removed duplicated region for block: B:97:0x01ae  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke() throws Resources.NotFoundException {
                Drawable drawable;
                LinearLayout linearLayout;
                Unit unit;
                ControlsActionButton controlsActionButton;
                ImageView imageView;
                Unit unit2;
                Integer num;
                CharSequence charSequence2 = charSequence;
                ColorStateList colorStateList2 = colorStateList;
                Control control2 = control;
                Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                RenderInfo renderInfo = renderInfoLookup;
                Drawable drawable2 = renderInfo.icon;
                ControlViewHolder controlViewHolder = this.f$0;
                boolean z4 = z;
                controlViewHolder.updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z4, charSequence2, drawable2, colorStateList2, control2);
                SecControlViewHolder secControlViewHolder = controlViewHolder.getSecControlViewHolder();
                CustomControl customControl = control2 != null ? control2.getCustomControl() : null;
                Drawable drawable3 = ((SecRenderInfo) renderInfo.secRenderInfo$delegate.getValue()).actionIcon;
                int controlStatus = controlViewHolder.getControlStatus();
                ControlTemplate controlTemplate = controlViewHolder.getControlTemplate();
                int deviceType2 = controlViewHolder.getDeviceType();
                SecRenderInfo.Companion companion2 = SecRenderInfo.Companion;
                Context context2 = controlViewHolder.context;
                CustomControl customControl2 = control2 != null ? control2.getCustomControl() : null;
                companion2.getClass();
                if (customControl2 == null || (num = (Integer) SecRenderInfoKt.statusIconResourceMap.get(Integer.valueOf(customControl2.getStatusIconType()))) == null) {
                    drawable = null;
                } else {
                    int iIntValue = num.intValue();
                    SparseArray sparseArray = SecRenderInfo.statusIconDrawableMap;
                    drawable = (Drawable) sparseArray.get(iIntValue);
                    if (drawable == null) {
                        drawable = context2.getResources().getDrawable(iIntValue, context2.getTheme());
                        sparseArray.set(iIntValue, drawable);
                    }
                }
                secControlViewHolder.getClass();
                if (customControl != null) {
                    ColorStateList statusTextColor = customControl.getStatusTextColor();
                    if (statusTextColor != null) {
                        secControlViewHolder.status.setTextColor(statusTextColor);
                    }
                    if (customControl.getUseCustomIconWithoutPadding()) {
                        secControlViewHolder.icon.setPadding(0, 0, 0, 0);
                    }
                    if (!customControl.getUseCustomIconWithoutShadowBg() && (secControlViewHolder.context.getResources().getConfiguration().uiMode & 48) == 16 && z4) {
                        Drawable drawable4 = secControlViewHolder.context.getResources().getDrawable(com.android.systemui.R.drawable.control_icon_shadow_bg, secControlViewHolder.context.getTheme());
                        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD && secControlViewHolder.controlsUtil != null && ControlsUtil.isFoldDelta(secControlViewHolder.context)) {
                            int dimensionPixelSize = secControlViewHolder.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_icon_size_fold);
                            BitmapDrawable bitmapDrawable = drawable4 instanceof BitmapDrawable ? (BitmapDrawable) drawable4 : null;
                            if (bitmapDrawable != null) {
                                drawable4 = new BitmapDrawable(secControlViewHolder.context.getResources(), Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), dimensionPixelSize, dimensionPixelSize, true));
                            }
                        }
                        secControlViewHolder.icon.setBackground(drawable4);
                    }
                    secControlViewHolder.icon.setAlpha(1.0f);
                    if (!(secControlViewHolder.layoutType == 1) && (controlsActionButton = secControlViewHolder.actionIcon) != null) {
                        if (SecControlViewHolder.isSecBehavior(controlStatus, controlTemplate, deviceType2)) {
                            ImageView imageView2 = controlsActionButton.actionIcon;
                            Icon actionIcon = customControl.getActionIcon();
                            if (actionIcon == null) {
                                if (imageView2 != null) {
                                    imageView2.setVisibility(0);
                                    if (drawable3 instanceof StateListDrawable) {
                                        if (imageView2.getDrawable() == null || !(imageView2.getDrawable() instanceof StateListDrawable)) {
                                            imageView2.setImageDrawable(drawable3);
                                        }
                                        imageView2.setImageState(z4 ? ControlViewHolder.ATTR_ENABLED : ControlViewHolder.ATTR_DISABLED, true);
                                    } else {
                                        imageView2.setImageDrawable(drawable3);
                                    }
                                    Unit unit3 = Unit.INSTANCE;
                                }
                                CharSequence text = secControlViewHolder.title.getText();
                                controlsActionButton.subTitle = secControlViewHolder.subtitle.getText();
                                controlsActionButton.title = text;
                                controlsActionButton.updateContentDescription();
                                imageView = controlsActionButton.actionIcon;
                                if (imageView != null) {
                                    imageView.setVisibility(0);
                                }
                            } else {
                                if (imageView2 != null) {
                                    imageView2.setImageIcon(actionIcon);
                                    unit2 = Unit.INSTANCE;
                                } else {
                                    unit2 = null;
                                }
                                if (unit2 == null) {
                                }
                                CharSequence text2 = secControlViewHolder.title.getText();
                                controlsActionButton.subTitle = secControlViewHolder.subtitle.getText();
                                controlsActionButton.title = text2;
                                controlsActionButton.updateContentDescription();
                                imageView = controlsActionButton.actionIcon;
                                if (imageView != null) {
                                }
                            }
                        } else {
                            ImageView imageView3 = controlsActionButton.actionIcon;
                            if (imageView3 != null) {
                                imageView3.setVisibility(8);
                            }
                        }
                    }
                    if (secControlViewHolder.layoutType != 1) {
                        ImageView imageView4 = secControlViewHolder.statusIcon;
                        if (imageView4 != null) {
                            if (!Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
                                imageView4.setVisibility(8);
                            } else if (drawable != null) {
                                imageView4.setImageDrawable(drawable);
                                imageView4.setVisibility(0);
                            }
                        }
                    } else if (z4) {
                        Icon customStatusIcon = customControl.getCustomStatusIcon();
                        if (customStatusIcon == null) {
                            ImageView imageView5 = secControlViewHolder.statusIcon;
                            if (imageView5 != null) {
                                imageView5.setImageIcon(null);
                                Unit unit4 = Unit.INSTANCE;
                            }
                            linearLayout = secControlViewHolder.batteryLayout;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(0);
                            }
                        } else {
                            ImageView imageView6 = secControlViewHolder.statusIcon;
                            if (imageView6 != null) {
                                imageView6.setImageIcon(customStatusIcon);
                                unit = Unit.INSTANCE;
                            } else {
                                unit = null;
                            }
                            if (unit == null) {
                            }
                            linearLayout = secControlViewHolder.batteryLayout;
                            if (linearLayout != null) {
                            }
                        }
                    } else {
                        LinearLayout linearLayout2 = secControlViewHolder.batteryLayout;
                        if (linearLayout2 != null) {
                            linearLayout2.setVisibility(8);
                        }
                    }
                    if (secControlViewHolder.layoutType != 1) {
                        secControlViewHolder.animationView = secControlViewHolder.controlsUtil != null ? ControlsUtil.updateLottieIcon(secControlViewHolder.context, secControlViewHolder.icon, secControlViewHolder.layout, secControlViewHolder.animationView, customControl.getCustomIconAnimationJson(), customControl.getCustomIconAnimationJsonCache(), customControl.getCustomIconAnimationStartFrame(), customControl.getCustomIconAnimationEndFrame(), customControl.getCustomIconAnimationRepeatCount()) : null;
                    }
                    Icon overlayCustomIcon = customControl.getOverlayCustomIcon();
                    if (overlayCustomIcon != null) {
                        ImageView imageView7 = secControlViewHolder.overlayCustomIcon;
                        if (imageView7 != null) {
                            imageView7.setImageIcon(overlayCustomIcon);
                        }
                        ImageView imageView8 = secControlViewHolder.overlayCustomIcon;
                        if (imageView8 != null) {
                            imageView8.setVisibility(0);
                        }
                    } else {
                        ImageView imageView9 = secControlViewHolder.overlayCustomIcon;
                        if (imageView9 != null) {
                            imageView9.setVisibility(8);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        });
        int color2 = this.context.getResources().getColor(com.android.systemui.R.color.sec_control_default_background, this.context.getTheme());
        if (z) {
            ControlWithState controlWithState3 = this.cws;
            Control control2 = (controlWithState3 != null ? controlWithState3 : null).control;
            listAsList = Arrays.asList(Integer.valueOf((control2 == null || (customColor = control2.getCustomColor()) == null) ? this.context.getResources().getColor(renderInfoLookup.enabledBackground, this.context.getTheme()) : customColor.getColorForState(new int[]{R.attr.state_enabled}, customColor.getDefaultColor())), 255);
        } else {
            listAsList = Arrays.asList(Integer.valueOf(color2), 0);
        }
        final int iIntValue = ((Number) listAsList.get(0)).intValue();
        int iIntValue2 = ((Number) listAsList.get(1)).intValue();
        if (this.behavior instanceof ToggleRangeBehavior) {
            color2 = ColorUtils.blendARGB(color2, iIntValue, this.toggleBackgroundIntensity);
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
                drawable.setAlpha(iIntValue2);
                if (drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setColor(iIntValue);
                }
                this.baseLayer.setColor(i2);
                this.layout.setAlpha(1.0f);
                return;
            }
            int defaultColor = (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) ? iIntValue : color.getDefaultColor();
            ColorStateList color3 = this.baseLayer.getColor();
            int defaultColor2 = color3 != null ? color3.getDefaultColor() : i2;
            final float alpha = this.layout.getAlpha();
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), iIntValue2);
            final int i3 = defaultColor;
            final int i4 = defaultColor2;
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    int iIntValue3 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    int iBlendARGB = ColorUtils.blendARGB(i3, iIntValue, valueAnimator2.getAnimatedFraction());
                    int iBlendARGB2 = ColorUtils.blendARGB(i4, i2, valueAnimator2.getAnimatedFraction());
                    float fLerp = MathUtils.lerp(alpha, 1.0f, valueAnimator2.getAnimatedFraction());
                    ControlViewHolder controlViewHolder = this;
                    Drawable drawable2 = drawable;
                    Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                    controlViewHolder.getClass();
                    drawable2.setAlpha(iIntValue3);
                    if (drawable2 instanceof GradientDrawable) {
                        ((GradientDrawable) drawable2).setColor(iBlendARGB);
                    }
                    controlViewHolder.baseLayer.setColor(iBlendARGB2);
                    controlViewHolder.layout.setAlpha(fLerp);
                }
            });
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    this.this$0.stateAnimator = null;
                }
            });
            valueAnimatorOfInt.setDuration(700L);
            valueAnimatorOfInt.setInterpolator(Interpolators.CONTROL_STATE);
            valueAnimatorOfInt.start();
            this.stateAnimator = valueAnimatorOfInt;
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
        ControlTemplate controlTemplate;
        ControlWithState controlWithState = this.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control = controlWithState.control;
        return (control == null || (controlTemplate = control.getControlTemplate()) == null) ? ControlTemplate.NO_TEMPLATE : controlTemplate;
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

    public final void setErrorStatus() throws Resources.NotFoundException {
        final String string = this.context.getResources().getString(com.android.systemui.R.string.controls_error_failed);
        animateStatusChange(true, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                this.f$0.setStatusText(string, true);
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
        if (customIcon != null) {
            if (!((Boolean) this.canUseIconPredicate.mo781invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                SafeIconLoader safeIconLoader = this.safeIconLoader;
                Drawable drawableLoadDrawableCheckingUriGrant = customIcon.loadDrawableCheckingUriGrant(safeIconLoader.serviceContext, safeIconLoader.iUriGrantsManager, safeIconLoader.serviceUid, safeIconLoader.packageName);
                this.icon.setImageDrawable(drawableLoadDrawableCheckingUriGrant);
                this.icon.setImageTintList(customIcon.getTintList());
                if (drawableLoadDrawableCheckingUriGrant != null) {
                    return;
                }
            }
        }
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
        Unit unit = Unit.INSTANCE;
    }

    public final boolean usePanel() {
        return FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) || Intrinsics.areEqual(getControlTemplate(), ControlTemplate.NO_TEMPLATE);
    }
}
