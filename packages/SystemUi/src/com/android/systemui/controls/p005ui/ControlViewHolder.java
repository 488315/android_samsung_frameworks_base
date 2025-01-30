package com.android.systemui.controls.p005ui;

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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
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
import com.android.systemui.controls.p005ui.CustomRenderInfo;
import com.android.systemui.controls.p005ui.RenderInfo;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.p005ui.view.ActionIconView;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlViewHolder {
    public static final int[] ATTR_DISABLED;
    public static final int[] ATTR_ENABLED;
    public static final Set FORCE_PANEL_DEVICES;
    public GradientDrawable baseLayer;
    public Behavior behavior;
    public final DelayableExecutor bgExecutor;
    public final CanUseIconPredicate canUseIconPredicate;
    public final ImageView chevronIcon;
    public ClipDrawable clipLayer;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final int currentUserId;
    public final Lazy customControlViewHolder$delegate;
    public ControlWithState cws;
    public final ImageView icon;
    public boolean isLoading;
    public ControlAction lastAction;
    public Dialog lastChallengeDialog;
    public final ViewGroup layout;
    public CharSequence nextStatusText;
    public final Function0 onDialogCancel;
    public ValueAnimator stateAnimator;
    public final TextView status;
    public Animator statusAnimator;
    public final TextView subtitle;
    public final TextView title;
    public final float toggleBackgroundIntensity;
    public final DelayableExecutor uiExecutor;
    public final int uid;
    public boolean userInteractionInProgress;
    public Dialog visibleDialog;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        this.chevronIcon = z ? null : (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.chevron_icon);
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
        this.customControlViewHolder$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$customControlViewHolder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder controlViewHolder = ControlViewHolder.this;
                return new CustomControlViewHolder(controlViewHolder.layout, controlViewHolder.icon, controlViewHolder.status, controlViewHolder.title, controlViewHolder.subtitle);
            }
        });
        if (z) {
            Pair initClipLayerAndBaseLayer = getCustomControlViewHolder().initClipLayerAndBaseLayer();
            this.clipLayer = (ClipDrawable) initClipLayerAndBaseLayer.getFirst();
            this.baseLayer = (GradientDrawable) initClipLayerAndBaseLayer.getSecond();
        } else {
            LayerDrawable layerDrawable = (LayerDrawable) viewGroup.getBackground();
            layerDrawable.mutate();
            this.clipLayer = (ClipDrawable) layerDrawable.findDrawableByLayerId(com.android.systemui.R.id.clip_layer);
            this.baseLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(com.android.systemui.R.id.background);
        }
        textView.setSelected(true);
        if (z) {
            viewGroup.measure(0, 0);
            int measuredWidth = ((viewGroup.getMeasuredWidth() - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_text_start_margin)) - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_text_end_margin)) - (viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_padding) * 2);
            textView2.setMaxWidth(measuredWidth);
            textView3.setMaxWidth(measuredWidth);
            textView.setMaxWidth(measuredWidth);
            ControlsUtil.Companion.getClass();
            ControlsUtil.Companion.updateFontSize(textView3, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
            ControlsUtil.Companion.updateFontSize(textView2, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
            ControlsUtil.Companion.updateFontSize(textView, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
        }
    }

    public final void action(ControlAction controlAction) {
        this.lastAction = controlAction;
        ComponentName componentName = getCws().componentName;
        ControlInfo controlInfo = getCws().f249ci;
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
        boolean z2 = this.isLoading;
        TextView textView = this.status;
        if (z2) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "alpha", 0.45f);
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900L);
            ofFloat.start();
            this.statusAnimator = ofFloat;
            return;
        }
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f);
        ofFloat2.setDuration(200L);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                Function0.this.invoke();
            }
        });
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 1.0f);
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

    /* renamed from: applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m119x3918d5b8(int i, final boolean z, boolean z2) {
        List listOf;
        ColorStateList color;
        ColorStateList customColor;
        int deviceType = (getControlStatus() == 1 || getControlStatus() == 0) ? getDeviceType() : -1000;
        RenderInfo.Companion companion = RenderInfo.Companion;
        ComponentName componentName = getCws().componentName;
        companion.getClass();
        Context context = this.context;
        final RenderInfo lookup = RenderInfo.Companion.lookup(context, componentName, deviceType, i);
        final ColorStateList colorStateList = context.getResources().getColorStateList(lookup.foreground, context.getTheme());
        final CharSequence charSequence = this.nextStatusText;
        final Control control = getCws().control;
        boolean z3 = (Intrinsics.areEqual(charSequence, this.status.getText()) || BasicRune.CONTROLS_SAMSUNG_STYLE) ? false : z2;
        animateStatusChange(z3, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code restructure failed: missing block: B:100:0x01ed, code lost:
            
                if (r0 == null) goto L116;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:104:0x0200  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
            /* JADX WARN: Type inference failed for: r0v14 */
            /* JADX WARN: Type inference failed for: r0v15, types: [com.airbnb.lottie.LottieAnimationView] */
            /* JADX WARN: Type inference failed for: r0v39 */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke() {
                Drawable drawable;
                int[] iArr;
                int[] iArr2;
                Unit unit;
                ImageView imageView;
                LinearLayout linearLayout;
                Unit unit2;
                ActionIconView actionIconView;
                Unit unit3;
                boolean z4;
                boolean z5;
                ColorStateList statusTextColor;
                Integer num;
                ControlViewHolder.this.m120xcd94ff85(z, charSequence, lookup.icon, colorStateList, control);
                if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    CustomControlViewHolder customControlViewHolder = ControlViewHolder.this.getCustomControlViewHolder();
                    Control control2 = control;
                    CustomControl customControl = control2 != null ? control2.getCustomControl() : null;
                    boolean z6 = z;
                    Drawable drawable2 = ((CustomRenderInfo) lookup.customRenderInfo$delegate.getValue()).actionIcon;
                    int controlStatus = ControlViewHolder.this.getControlStatus();
                    ControlTemplate controlTemplate = ControlViewHolder.this.getControlTemplate();
                    int deviceType2 = ControlViewHolder.this.getDeviceType();
                    int[] iArr3 = ControlViewHolder.ATTR_ENABLED;
                    int[] iArr4 = ControlViewHolder.ATTR_DISABLED;
                    CustomRenderInfo.Companion companion2 = CustomRenderInfo.Companion;
                    Context context2 = ControlViewHolder.this.context;
                    Control control3 = control;
                    CustomControl customControl2 = control3 != null ? control3.getCustomControl() : null;
                    companion2.getClass();
                    if (customControl2 == null || (num = (Integer) CustomRenderInfoKt.statusIconResourceMap.get(Integer.valueOf(customControl2.getStatusIconType()))) == null) {
                        drawable = null;
                    } else {
                        int intValue = num.intValue();
                        SparseArray sparseArray = CustomRenderInfo.statusIconDrawableMap;
                        drawable = (Drawable) sparseArray.get(intValue);
                        if (drawable == null) {
                            drawable = context2.getResources().getDrawable(intValue, context2.getTheme());
                            sparseArray.set(intValue, drawable);
                        }
                    }
                    customControlViewHolder.getClass();
                    if (customControl != null) {
                        boolean z7 = BasicRune.CONTROLS_CUSTOM_STATUS;
                        if (z7 && (statusTextColor = customControl.getStatusTextColor()) != null) {
                            customControlViewHolder.status.setTextColor(statusTextColor);
                        }
                        boolean z8 = BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING;
                        ImageView imageView2 = customControlViewHolder.icon;
                        if (z8 && customControl.getUseCustomIconWithoutPadding()) {
                            imageView2.setPadding(0, 0, 0, 0);
                        }
                        boolean z9 = BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG;
                        Context context3 = customControlViewHolder.context;
                        if (z9 && !customControl.getUseCustomIconWithoutShadowBg() && (context3.getResources().getConfiguration().uiMode & 48) == 16 && z6) {
                            Drawable drawable3 = context3.getResources().getDrawable(com.android.systemui.R.drawable.custom_icon_shadow_background, context3.getTheme());
                            if (customControlViewHolder.controlsRuneWrapper != null) {
                                z4 = true;
                                if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
                                    z5 = true;
                                    if (z5) {
                                        if (customControlViewHolder.controlsUtil != null && ControlsUtil.isFoldDelta(context3) == z4) {
                                            int dimensionPixelSize = context3.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_icon_size_fold);
                                            BitmapDrawable bitmapDrawable = drawable3 instanceof BitmapDrawable ? (BitmapDrawable) drawable3 : null;
                                            if (bitmapDrawable != null) {
                                                iArr = iArr3;
                                                iArr2 = iArr4;
                                                drawable3 = new BitmapDrawable(context3.getResources(), Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), dimensionPixelSize, dimensionPixelSize, true));
                                                imageView2.setBackground(drawable3);
                                            }
                                        }
                                    }
                                    iArr = iArr3;
                                    iArr2 = iArr4;
                                    imageView2.setBackground(drawable3);
                                }
                            } else {
                                z4 = true;
                            }
                            z5 = false;
                            if (z5) {
                            }
                            iArr = iArr3;
                            iArr2 = iArr4;
                            imageView2.setBackground(drawable3);
                        } else {
                            iArr = iArr3;
                            iArr2 = iArr4;
                        }
                        imageView2.setAlpha(z6 ? context3.getResources().getFloat(com.android.systemui.R.dimen.controls_custom_icon_alpha_on) : context3.getResources().getFloat(com.android.systemui.R.dimen.controls_custom_icon_alpha_off));
                        if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                            if (!(BasicRune.CONTROLS_LAYOUT_TYPE && customControlViewHolder.layoutType == 1) && (actionIconView = customControlViewHolder.actionIcon) != null) {
                                boolean isCustomBehavior = CustomControlViewHolder.isCustomBehavior(controlStatus, controlTemplate, deviceType2);
                                ImageView imageView3 = actionIconView.actionIcon;
                                if (isCustomBehavior) {
                                    Icon actionIcon = customControl.getActionIcon();
                                    if (actionIcon != null) {
                                        imageView3.setImageIcon(actionIcon);
                                        unit3 = Unit.INSTANCE;
                                    } else {
                                        unit3 = null;
                                    }
                                    if (unit3 == null) {
                                        imageView3.setVisibility(0);
                                        if (drawable2 instanceof StateListDrawable) {
                                            if (imageView3.getDrawable() == null || !(imageView3.getDrawable() instanceof StateListDrawable)) {
                                                imageView3.setImageDrawable(drawable2);
                                            }
                                            imageView3.setImageState(z6 ? iArr : iArr2, true);
                                        } else {
                                            imageView3.setImageDrawable(drawable2);
                                        }
                                    }
                                    CharSequence text = customControlViewHolder.title.getText();
                                    actionIconView.subTitle = customControlViewHolder.subtitle.getText();
                                    actionIconView.title = text;
                                    actionIconView.updateContentDescription();
                                    imageView3.setVisibility(0);
                                } else {
                                    imageView3.setVisibility(8);
                                }
                            }
                        }
                        if (z7) {
                            if (!(BasicRune.CONTROLS_LAYOUT_TYPE && customControlViewHolder.layoutType == 1)) {
                                unit = null;
                                ImageView imageView4 = customControlViewHolder.statusIcon;
                                if (imageView4 != null) {
                                    if (!Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
                                        imageView4.setVisibility(8);
                                    } else if (drawable != null) {
                                        imageView4.setImageDrawable(drawable);
                                        imageView4.setVisibility(0);
                                    }
                                }
                            } else if (z6) {
                                Icon customStatusIcon = customControl.getCustomStatusIcon();
                                if (customStatusIcon != null) {
                                    ImageView imageView5 = customControlViewHolder.statusIcon;
                                    if (imageView5 != null) {
                                        imageView5.setImageIcon(customStatusIcon);
                                        unit2 = Unit.INSTANCE;
                                    } else {
                                        unit2 = null;
                                    }
                                }
                                ImageView imageView6 = customControlViewHolder.statusIcon;
                                if (imageView6 != null) {
                                    unit = null;
                                    imageView6.setImageIcon(null);
                                    Unit unit4 = Unit.INSTANCE;
                                    linearLayout = customControlViewHolder.batteryLayout;
                                    if (linearLayout != null) {
                                        linearLayout.setVisibility(0);
                                    }
                                }
                                unit = null;
                                linearLayout = customControlViewHolder.batteryLayout;
                                if (linearLayout != null) {
                                }
                            } else {
                                unit = null;
                                LinearLayout linearLayout2 = customControlViewHolder.batteryLayout;
                                if (linearLayout2 != null) {
                                    linearLayout2.setVisibility(8);
                                }
                            }
                        } else {
                            unit = null;
                        }
                        if (BasicRune.CONTROLS_LOTTIE_ICON_ANIMATION) {
                            if (!(BasicRune.CONTROLS_LAYOUT_TYPE && customControlViewHolder.layoutType == 1)) {
                                customControlViewHolder.animationView = customControlViewHolder.controlsUtil != null ? ControlsUtil.updateLottieIcon(customControlViewHolder.context, customControlViewHolder.icon, customControlViewHolder.layout, customControlViewHolder.animationView, customControl.getCustomIconAnimationJson(), customControl.getCustomIconAnimationJsonCache(), customControl.getCustomIconAnimationStartFrame(), customControl.getCustomIconAnimationEndFrame(), customControl.getCustomIconAnimationRepeatCount()) : unit;
                            }
                        }
                        if (customControlViewHolder.controlsRuneWrapper != null && BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON) {
                            Icon overlayCustomIcon = customControl.getOverlayCustomIcon();
                            if (overlayCustomIcon != null) {
                                ImageView imageView7 = customControlViewHolder.overlayCustomIcon;
                                if (imageView7 != null) {
                                    imageView7.setImageIcon(overlayCustomIcon);
                                }
                                ImageView imageView8 = customControlViewHolder.overlayCustomIcon;
                                if (imageView8 != null) {
                                    imageView8.setVisibility(0);
                                }
                                unit = Unit.INSTANCE;
                            }
                            if (unit == null && (imageView = customControlViewHolder.overlayCustomIcon) != null) {
                                imageView.setVisibility(8);
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        });
        boolean z4 = BasicRune.CONTROLS_SAMSUNG_STYLE;
        int color2 = z4 ? context.getResources().getColor(com.android.systemui.R.color.control_custom_default_background, context.getTheme()) : context.getResources().getColor(com.android.systemui.R.color.control_default_background, context.getTheme());
        if (z) {
            Control control2 = getCws().control;
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf((control2 == null || (customColor = control2.getCustomColor()) == null) ? context.getResources().getColor(lookup.enabledBackground, context.getTheme()) : customColor.getColorForState(new int[]{R.attr.state_enabled}, customColor.getDefaultColor())), 255);
        } else {
            listOf = z4 ? CollectionsKt__CollectionsKt.listOf(Integer.valueOf(color2), 0) : CollectionsKt__CollectionsKt.listOf(Integer.valueOf(context.getResources().getColor(com.android.systemui.R.color.control_default_background, context.getTheme())), 0);
        }
        final int intValue = ((Number) listOf.get(0)).intValue();
        int intValue2 = ((Number) listOf.get(1)).intValue();
        final int blendARGB = this.behavior instanceof ToggleRangeBehavior ? ColorUtils.blendARGB(color2, intValue, this.toggleBackgroundIntensity) : color2;
        final Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            this.clipLayer.setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ViewGroup viewGroup = this.layout;
            if (!z3) {
                drawable.setAlpha(intValue2);
                if (drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setColor(intValue);
                }
                this.baseLayer.setColor(blendARGB);
                viewGroup.setAlpha(1.0f);
                return;
            }
            int defaultColor = (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) ? intValue : color.getDefaultColor();
            ColorStateList color3 = this.baseLayer.getColor();
            int defaultColor2 = color3 != null ? color3.getDefaultColor() : blendARGB;
            final float alpha = viewGroup.getAlpha();
            ValueAnimator ofInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), intValue2);
            final int i2 = defaultColor;
            final int i3 = defaultColor2;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    int intValue3 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    int blendARGB2 = ColorUtils.blendARGB(i2, intValue, valueAnimator2.getAnimatedFraction());
                    int blendARGB3 = ColorUtils.blendARGB(i3, blendARGB, valueAnimator2.getAnimatedFraction());
                    float lerp = MathUtils.lerp(alpha, 1.0f, valueAnimator2.getAnimatedFraction());
                    ControlViewHolder controlViewHolder = this;
                    Drawable drawable2 = drawable;
                    Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                    controlViewHolder.getClass();
                    drawable2.setAlpha(intValue3);
                    if (drawable2 instanceof GradientDrawable) {
                        ((GradientDrawable) drawable2).setColor(blendARGB2);
                    }
                    controlViewHolder.baseLayer.setColor(blendARGB3);
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
        behavior.bind(getCws(), i);
        return behavior;
    }

    public final Supplier findBehaviorClass(int i, ControlTemplate controlTemplate, int i2, int i3) {
        if (i != 1) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new StatusBehavior();
                }
            };
        }
        if (Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new TouchBehavior();
                }
            };
        }
        boolean z = BasicRune.CONTROLS_LAYOUT_TYPE;
        if (z) {
            if (z && i3 == 1) {
                return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new DefaultBehavior();
                    }
                };
            }
        }
        return controlTemplate instanceof ThumbnailTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$4
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

    public final int getControlStatus() {
        Control control = getCws().control;
        if (control != null) {
            return control.getStatus();
        }
        return 0;
    }

    public final ControlTemplate getControlTemplate() {
        Control control = getCws().control;
        ControlTemplate controlTemplate = control != null ? control.getControlTemplate() : null;
        return controlTemplate == null ? ControlTemplate.NO_TEMPLATE : controlTemplate;
    }

    public final CustomControlViewHolder getCustomControlViewHolder() {
        return (CustomControlViewHolder) this.customControlViewHolder$delegate.getValue();
    }

    public final ControlWithState getCws() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState != null) {
            return controlWithState;
        }
        return null;
    }

    public final int getDeviceType() {
        Control control = getCws().control;
        return control != null ? control.getDeviceType() : getCws().f249ci.deviceType;
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
            TextView textView = this.status;
            textView.setAlpha(1.0f);
            textView.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    public final void updateContentDescription() {
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        TextView textView = this.status;
        TextView textView2 = this.subtitle;
        TextView textView3 = this.title;
        ViewGroup viewGroup = this.layout;
        if (z) {
            viewGroup.setContentDescription(((Object) textView2.getText()) + " " + ((Object) textView3.getText()) + " " + ((Object) textView.getText()));
            return;
        }
        viewGroup.setContentDescription(((Object) textView3.getText()) + " " + ((Object) textView2.getText()) + " " + ((Object) textView.getText()));
    }

    /* renamed from: updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m120xcd94ff85(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        Icon customIcon;
        TextView textView = this.status;
        textView.setEnabled(z);
        ImageView imageView = this.icon;
        imageView.setEnabled(z);
        boolean z2 = BasicRune.CONTROLS_SAMSUNG_STYLE;
        ImageView imageView2 = this.chevronIcon;
        if (!z2 && imageView2 != null) {
            imageView2.setEnabled(z);
        }
        if (z2) {
            CustomControlViewHolder customControlViewHolder = getCustomControlViewHolder();
            customControlViewHolder.title.setEnabled(z);
            customControlViewHolder.subtitle.setEnabled(z);
        }
        textView.setText(charSequence);
        updateContentDescription();
        textView.setTextColor(colorStateList);
        if (z2 && control == null) {
            return;
        }
        Unit unit = null;
        if (control != null && (customIcon = control.getCustomIcon()) != null) {
            if (!((Boolean) this.canUseIconPredicate.invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                imageView.setImageIcon(customIcon);
                imageView.setImageTintList(customIcon.getTintList());
                unit = Unit.INSTANCE;
            }
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (imageView.getDrawable() == null || !(imageView.getDrawable() instanceof StateListDrawable)) {
                    imageView.setImageDrawable(drawable);
                }
                imageView.setImageState(z ? ATTR_ENABLED : ATTR_DISABLED, true);
            } else {
                imageView.setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                imageView.setImageTintList(colorStateList);
            }
        }
        if (imageView2 == null) {
            return;
        }
        imageView2.setImageTintList(imageView.getImageTintList());
    }

    public final boolean usePanel() {
        return FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) || Intrinsics.areEqual(getControlTemplate(), ControlTemplate.NO_TEMPLATE);
    }
}
