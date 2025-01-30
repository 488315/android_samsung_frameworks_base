package com.android.systemui.controls.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.view.ActionIconView;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlViewHolder {
    public ActionIconView actionIcon;
    public LottieAnimationView animationView;
    public LinearLayout batteryLayout;
    public final Context context;
    public ControlsRuneWrapper controlsRuneWrapper;
    public ControlsUtil controlsUtil;
    public CustomBehavior customBehavior;
    public CustomControlActionCoordinator customControlActionCoordinator;
    public final ImageView icon;
    public final ViewGroup layout;
    public int layoutType;
    public ImageView overlayCustomIcon;
    public final TextView status;
    public ImageView statusIcon;
    public final TextView subtitle;
    public final TextView title;

    public CustomControlViewHolder(ViewGroup viewGroup, ImageView imageView, TextView textView, TextView textView2, TextView textView3) {
        this.layout = viewGroup;
        this.icon = imageView;
        this.status = textView;
        this.title = textView2;
        this.subtitle = textView3;
        this.context = viewGroup.getContext();
    }

    public static boolean isCustomBehavior(int i, ControlTemplate controlTemplate, int i2) {
        if (i == 1 && !Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
            if (controlTemplate instanceof ThumbnailTemplate) {
                return true;
            }
            if (i2 != 50) {
                if ((controlTemplate instanceof ToggleTemplate) || (controlTemplate instanceof StatelessTemplate) || (controlTemplate instanceof ToggleRangeTemplate)) {
                    return true;
                }
                if (controlTemplate instanceof TemperatureControlTemplate) {
                    return isCustomBehavior(i, ((TemperatureControlTemplate) controlTemplate).getTemplate(), i2);
                }
            }
        }
        return false;
    }

    public final Pair initClipLayerAndBaseLayer() {
        Context context = this.context;
        Drawable drawable = context.getResources().getDrawable(R.drawable.control_custom_background_ripple, context.getTheme());
        ViewGroup viewGroup = this.layout;
        viewGroup.setBackground(drawable);
        RippleDrawable rippleDrawable = (RippleDrawable) viewGroup.getBackground();
        rippleDrawable.mutate();
        return new Pair((ClipDrawable) rippleDrawable.findDrawableByLayerId(R.id.clip_layer), (GradientDrawable) rippleDrawable.findDrawableByLayerId(R.id.background));
    }

    public final void initialize(CustomControlActionCoordinator customControlActionCoordinator, ControlsUtil controlsUtil, int i, ControlsRuneWrapper controlsRuneWrapper) {
        ActionIconView actionIconView;
        ProgressBar progressBar;
        ImageView imageView;
        this.customControlActionCoordinator = customControlActionCoordinator;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        boolean z = BasicRune.CONTROLS_LAYOUT_TYPE;
        ViewGroup viewGroup = this.layout;
        if (z) {
            if (i == 1) {
                this.statusIcon = (ImageView) viewGroup.requireViewById(R.id.battery_icon);
                this.batteryLayout = (LinearLayout) viewGroup.findViewById(R.id.battery_layout);
            } else {
                if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                    this.actionIcon = new ActionIconView((ViewStub) viewGroup.requireViewById(R.id.right_top_viewstub));
                }
                if (BasicRune.CONTROLS_CUSTOM_STATUS) {
                    ViewStub viewStub = (ViewStub) viewGroup.requireViewById(R.id.status_icon_viewstub);
                    viewStub.setLayoutResource(R.layout.controls_status_icon_view);
                    this.statusIcon = (ImageView) viewStub.inflate();
                }
            }
            this.layoutType = i;
        }
        boolean z2 = BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON;
        if (z2) {
            this.overlayCustomIcon = (ImageView) viewGroup.requireViewById(R.id.overlay_custom_icon);
        }
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
            if (this.controlsUtil != null && ControlsUtil.isFoldDelta(this.context)) {
                Resources resources = viewGroup.getResources();
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_custom_icon_size_fold);
                float dimension = resources.getDimension(R.dimen.control_custom_text_size_fold);
                int i2 = this.layoutType;
                TextView textView = this.subtitle;
                if (i2 == 0) {
                    int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.control_action_icon_size_fold);
                    int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.control_status_icon_size_fold);
                    if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON && (actionIconView = this.actionIcon) != null) {
                        ControlsUtil.Companion.getClass();
                        ControlsUtil.Companion.setSize(actionIconView.actionIcon, dimensionPixelSize2, dimensionPixelSize2);
                        if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS && (progressBar = actionIconView.actionIconProgress) != null) {
                            ControlsUtil.Companion.setSize(progressBar, dimensionPixelSize2, dimensionPixelSize2);
                        }
                    }
                    ImageView imageView2 = this.statusIcon;
                    if (imageView2 != null) {
                        ControlsUtil.Companion.getClass();
                        ControlsUtil.Companion.setSize(imageView2, dimensionPixelSize3, dimensionPixelSize3);
                    }
                    textView.setTextSize(0, dimension);
                } else if (i2 == 1) {
                    int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.control_battery_icon_size_fold);
                    float dimension2 = resources.getDimension(R.dimen.control_battery_gauge_font_size_fold);
                    ImageView imageView3 = this.statusIcon;
                    if (imageView3 != null) {
                        ControlsUtil.Companion.getClass();
                        ControlsUtil.Companion.setSize(imageView3, dimensionPixelSize4, dimensionPixelSize4);
                    }
                    textView.setTextSize(0, dimension2);
                }
                ControlsUtil.Companion.getClass();
                ControlsUtil.Companion.setSize(this.icon, dimensionPixelSize, dimensionPixelSize);
                if ((this.controlsRuneWrapper != null && z2) && (imageView = this.overlayCustomIcon) != null) {
                    ControlsUtil.Companion.setSize(imageView, dimensionPixelSize, dimensionPixelSize);
                }
                this.status.setTextSize(0, dimension);
                this.title.setTextSize(0, dimension);
            }
        }
    }

    public static /* synthetic */ void getAnimationView$annotations() {
    }

    public static /* synthetic */ void getOverlayCustomIcon$annotations() {
    }

    public static /* synthetic */ void getStatusIcon$annotations() {
    }
}
