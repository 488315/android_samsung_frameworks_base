package com.android.systemui.controls.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
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
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecControlViewHolder {
    public ControlsActionButton actionIcon;
    public LottieAnimationView animationView;
    public LinearLayout batteryLayout;
    public final Context context;
    public ControlsUtil controlsUtil;
    public final ImageView icon;
    public final ViewGroup layout;
    public int layoutType;
    public ImageView overlayCustomIcon;
    public SecBehavior secBehavior;
    public SecControlActionCoordinator secControlActionCoordinator;
    public final TextView status;
    public ImageView statusIcon;
    public final TextView subtitle;
    public final TextView title;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecControlViewHolder(ViewGroup viewGroup, ImageView imageView, TextView textView, TextView textView2, TextView textView3) {
        this.layout = viewGroup;
        this.icon = imageView;
        this.status = textView;
        this.title = textView2;
        this.subtitle = textView3;
        this.context = viewGroup.getContext();
    }

    public static boolean isSecBehavior(int i, ControlTemplate controlTemplate, int i2) {
        if (i != 1 || controlTemplate.equals(ControlTemplate.NO_TEMPLATE)) {
            return false;
        }
        if (!(controlTemplate instanceof ThumbnailTemplate)) {
            if (i2 == 50) {
                return false;
            }
            if (!(controlTemplate instanceof ToggleTemplate) && !(controlTemplate instanceof StatelessTemplate) && !(controlTemplate instanceof ToggleRangeTemplate)) {
                if (controlTemplate instanceof TemperatureControlTemplate) {
                    return isSecBehavior(i, ((TemperatureControlTemplate) controlTemplate).getTemplate(), i2);
                }
                return false;
            }
        }
        return true;
    }

    public final Pair initClipLayerAndBaseLayer() {
        this.layout.setBackground(this.context.getResources().getDrawable(R.drawable.control_ripple_bg, this.context.getTheme()));
        RippleDrawable rippleDrawable = (RippleDrawable) this.layout.getBackground();
        rippleDrawable.mutate();
        return new Pair((ClipDrawable) rippleDrawable.findDrawableByLayerId(R.id.clip_layer), (GradientDrawable) rippleDrawable.findDrawableByLayerId(R.id.background));
    }

    public final void initialize(SecControlActionCoordinator secControlActionCoordinator, ControlsUtil controlsUtil, int i) {
        this.secControlActionCoordinator = secControlActionCoordinator;
        this.controlsUtil = controlsUtil;
        if (i == 1) {
            this.statusIcon = (ImageView) this.layout.requireViewById(R.id.battery_icon);
            this.batteryLayout = (LinearLayout) this.layout.findViewById(R.id.battery_layout);
        } else {
            this.actionIcon = new ControlsActionButton((ViewStub) this.layout.requireViewById(R.id.right_top_viewstub));
            ViewStub viewStub = (ViewStub) this.layout.requireViewById(R.id.status_icon_viewstub);
            viewStub.setLayoutResource(R.layout.controls_status_icon_view);
            this.statusIcon = (ImageView) viewStub.inflate();
        }
        this.layoutType = i;
        this.overlayCustomIcon = (ImageView) this.layout.requireViewById(R.id.overlay_custom_icon);
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD && this.controlsUtil != null && ControlsUtil.isFoldDelta(this.context)) {
            Resources resources = this.layout.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_icon_size_fold);
            float dimension = resources.getDimension(R.dimen.control_text_size_fold);
            int i2 = this.layoutType;
            if (i2 == 0) {
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.control_action_button_size_fold);
                int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.control_status_icon_size_fold);
                ControlsActionButton controlsActionButton = this.actionIcon;
                if (controlsActionButton != null) {
                    ImageView imageView = controlsActionButton.actionIcon;
                    if (imageView != null) {
                        ControlsUtil.Companion.getClass();
                        ControlsUtil.Companion.setSize(imageView, dimensionPixelSize2, dimensionPixelSize2);
                    }
                    ProgressBar progressBar = controlsActionButton.actionIconProgress;
                    if (progressBar != null) {
                        ControlsUtil.Companion.getClass();
                        ControlsUtil.Companion.setSize(progressBar, dimensionPixelSize2, dimensionPixelSize2);
                    }
                }
                ImageView imageView2 = this.statusIcon;
                if (imageView2 != null) {
                    ControlsUtil.Companion.getClass();
                    ControlsUtil.Companion.setSize(imageView2, dimensionPixelSize3, dimensionPixelSize3);
                }
                this.subtitle.setTextSize(0, dimension);
            } else if (i2 == 1) {
                int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.control_battery_icon_size_fold);
                float dimension2 = resources.getDimension(R.dimen.control_battery_gauge_font_size_fold);
                ImageView imageView3 = this.statusIcon;
                if (imageView3 != null) {
                    ControlsUtil.Companion.getClass();
                    ControlsUtil.Companion.setSize(imageView3, dimensionPixelSize4, dimensionPixelSize4);
                }
                this.subtitle.setTextSize(0, dimension2);
            }
            ControlsUtil.Companion companion = ControlsUtil.Companion;
            ImageView imageView4 = this.icon;
            companion.getClass();
            ControlsUtil.Companion.setSize(imageView4, dimensionPixelSize, dimensionPixelSize);
            ImageView imageView5 = this.overlayCustomIcon;
            if (imageView5 != null) {
                ControlsUtil.Companion.setSize(imageView5, dimensionPixelSize, dimensionPixelSize);
            }
            this.status.setTextSize(0, dimension);
            this.title.setTextSize(0, dimension);
        }
    }

    public static /* synthetic */ void getAnimationView$annotations() {
    }

    public static /* synthetic */ void getOverlayCustomIcon$annotations() {
    }

    public static /* synthetic */ void getStatusIcon$annotations() {
    }
}
