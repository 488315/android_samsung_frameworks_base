package com.android.systemui.statusbar.policy;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.settings.brightness.BrightnessAnimationIcon;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderView;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import com.android.systemui.settings.brightness.SecBrightnessSliderView;
import com.android.systemui.settings.brightness.ToggleSeekBar;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.RangesKt___RangesKt;

public final class SecBrightnessMirrorController {
    public BrightnessAnimationIcon brightnessIcon;
    public FrameLayout brightnessMirror;
    public int brightnessMirrorHeight;
    public int brightnessMirrorWidth;
    public Context context;
    public final QuickPanelBlur quickPanelBlur = new QuickPanelBlur();
    public final QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = new QuickTileBrightnessMirrorDummyView();
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.SecBrightnessMirrorController$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public BrightnessSliderController toggleSliderController;

    public final SecQSPanelResourcePicker getResourcePicker() {
        return (SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue();
    }

    public final void hideMirror() {
        SecBrightnessSliderController secBrightnessSliderController;
        SecQpBlurController secQpBlurController;
        QuickPanelBlur quickPanelBlur = this.quickPanelBlur;
        if (quickPanelBlur != null && (secQpBlurController = (SecQpBlurController) quickPanelBlur.blurController$delegate.getValue()) != null) {
            secQpBlurController.setBrightnessMirrorVisible(false);
        }
        BrightnessSliderController brightnessSliderController = this.toggleSliderController;
        SecBrightnessSliderController secBrightnessSliderController2 = brightnessSliderController != null ? brightnessSliderController.mSecBrightnessSliderController : null;
        if (secBrightnessSliderController2 != null) {
            QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = this.quickTileBrightnessMirrorDummyView;
            secBrightnessSliderController2.isExpanded = quickTileBrightnessMirrorDummyView != null ? quickTileBrightnessMirrorDummyView.expanded : false;
        }
        if (brightnessSliderController == null || (secBrightnessSliderController = brightnessSliderController.mSecBrightnessSliderController) == null) {
            return;
        }
        ValueAnimator valueAnimator = secBrightnessSliderController.thumbAnimator;
        secBrightnessSliderController.isThumbShowing = false;
        valueAnimator.reverse();
    }

    public final void reinflate(FrameLayout frameLayout, final BrightnessSliderController brightnessSliderController) {
        this.brightnessMirror = frameLayout;
        final Context context = frameLayout.getContext();
        this.context = context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.policy.SecBrightnessMirrorController$toInitIconResources$1
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessAnimationIcon brightnessAnimationIcon = new BrightnessAnimationIcon((LottieAnimationView) BrightnessSliderController.this.getRootView().findViewById(R.id.brightness_icon));
                brightnessAnimationIcon.init(context);
                this.brightnessIcon = brightnessAnimationIcon;
            }
        }, 100L);
        this.toggleSliderController = brightnessSliderController;
    }

    public final void setLocationAndSize(View view) {
        this.brightnessMirrorWidth = view.getWidth();
        this.brightnessMirrorHeight = view.getHeight();
        updateLayout();
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int width = (view.getWidth() / 2) + iArr[0];
        int height = (view.getHeight() / 2) + iArr[1];
        FrameLayout frameLayout = this.brightnessMirror;
        if (frameLayout != null) {
            frameLayout.setTranslationX(0.0f);
            frameLayout.setTranslationY(0.0f);
            frameLayout.getLocationInWindow(iArr);
            int width2 = (frameLayout.getWidth() / 2) + iArr[0];
            int height2 = (frameLayout.getHeight() / 2) + iArr[1];
            frameLayout.setTranslationX(width - width2);
            frameLayout.setTranslationY(height - height2);
        }
    }

    public final void showMirror() {
        SecBrightnessSliderController secBrightnessSliderController;
        SecBrightnessSliderController secBrightnessSliderController2;
        Context context;
        QSTileView qSTileView;
        ArrayList arrayList;
        SecQpBlurController secQpBlurController;
        QuickPanelBlur quickPanelBlur = this.quickPanelBlur;
        if (quickPanelBlur != null && (secQpBlurController = (SecQpBlurController) quickPanelBlur.blurController$delegate.getValue()) != null) {
            secQpBlurController.setBrightnessMirrorVisible(true);
        }
        BrightnessSliderController brightnessSliderController = this.toggleSliderController;
        QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = this.quickTileBrightnessMirrorDummyView;
        if (brightnessSliderController != null && (context = this.context) != null) {
            context.getResources();
            View findViewById = brightnessSliderController.getRootView().findViewById(R.id.brightness_tile_layout);
            LinearLayout linearLayout = findViewById instanceof LinearLayout ? (LinearLayout) findViewById : null;
            if (linearLayout != null) {
                linearLayout.setVisibility(4);
                int size = (quickTileBrightnessMirrorDummyView == null || (arrayList = quickTileBrightnessMirrorDummyView.tiles) == null) ? 0 : arrayList.size();
                linearLayout.removeAllViews();
                IntProgressionIterator it = RangesKt___RangesKt.until(0, size).iterator();
                while (it.hasNext) {
                    int nextInt = it.nextInt();
                    if (quickTileBrightnessMirrorDummyView != null && (qSTileView = ((SecQSPanelControllerBase.TileRecord) quickTileBrightnessMirrorDummyView.tiles.get(nextInt)).tileView) != null) {
                        TextView textView = new TextView(this.context);
                        textView.setText("Dummy text");
                        ViewGroup.LayoutParams layoutParams = qSTileView.getLayoutParams();
                        layoutParams.width = qSTileView.getWidth();
                        textView.setLayoutParams(layoutParams);
                        linearLayout.addView(textView);
                    }
                }
            }
        }
        BrightnessSliderController brightnessSliderController2 = this.toggleSliderController;
        SecBrightnessSliderController secBrightnessSliderController3 = brightnessSliderController2 != null ? brightnessSliderController2.mSecBrightnessSliderController : null;
        if (secBrightnessSliderController3 != null) {
            secBrightnessSliderController3.isExpanded = quickTileBrightnessMirrorDummyView != null ? quickTileBrightnessMirrorDummyView.expanded : false;
        }
        if (brightnessSliderController2 != null && (secBrightnessSliderController2 = brightnessSliderController2.mSecBrightnessSliderController) != null) {
            BrightnessSliderView brightnessSliderView = secBrightnessSliderController2.view;
            SecBrightnessSliderView secBrightnessSliderView = brightnessSliderView.mSecBrightnessSliderView;
            ToggleSeekBar slider = secBrightnessSliderView != null ? secBrightnessSliderView.getSlider() : null;
            boolean z = secBrightnessSliderController2.isExpanded;
            Lazy lazy = secBrightnessSliderController2.resourcePicker$delegate;
            secBrightnessSliderController2.thumbThreshold = (slider == null || slider.getWidth() == 0) ? 26 : (slider.getMax() * (z ? ((SecQSPanelResourcePicker) lazy.getValue()).getBrightnessBarExpandedHeight(brightnessSliderView.getContext()) : ((SecQSPanelResourcePicker) lazy.getValue()).getBrightnessBarHeight(brightnessSliderView.getContext()))) / slider.getWidth();
        }
        BrightnessSliderController brightnessSliderController3 = this.toggleSliderController;
        if (brightnessSliderController3 == null || (secBrightnessSliderController = brightnessSliderController3.mSecBrightnessSliderController) == null) {
            return;
        }
        ValueAnimator valueAnimator = secBrightnessSliderController.thumbAnimator;
        secBrightnessSliderController.isThumbShowing = true;
        valueAnimator.start();
    }

    public final void updateLayout() {
        Context context;
        BrightnessSliderController brightnessSliderController = this.toggleSliderController;
        if (brightnessSliderController == null || (context = this.context) == null) {
            return;
        }
        Resources resources = context.getResources();
        View findViewById = brightnessSliderController.getRootView().findViewById(R.id.brightness_bar_container);
        LinearLayout.LayoutParams layoutParams = null;
        LinearLayout linearLayout = findViewById instanceof LinearLayout ? (LinearLayout) findViewById : null;
        if (linearLayout != null) {
            ViewGroup.LayoutParams layoutParams2 = linearLayout.getLayoutParams();
            layoutParams2.width = this.brightnessMirrorWidth;
            layoutParams2.height = getResourcePicker().getBrightnessBarContainerHeight(linearLayout.getContext());
            linearLayout.setLayoutParams(layoutParams2);
        }
        FrameLayout frameLayout = this.brightnessMirror;
        if (frameLayout != null) {
            ViewGroup.LayoutParams layoutParams3 = frameLayout.getLayoutParams();
            layoutParams3.width = this.brightnessMirrorWidth;
            layoutParams3.height = this.brightnessMirrorHeight;
            frameLayout.setLayoutParams(layoutParams3);
        }
        QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = this.quickTileBrightnessMirrorDummyView;
        boolean z = quickTileBrightnessMirrorDummyView != null ? quickTileBrightnessMirrorDummyView.expanded : false;
        int brightnessBarExpandedHeight = z ? getResourcePicker().getBrightnessBarExpandedHeight(context) : getResourcePicker().getBrightnessBarHeight(context);
        View findViewById2 = brightnessSliderController.getRootView().findViewById(R.id.brightness_detail);
        ImageView imageView = findViewById2 instanceof ImageView ? (ImageView) findViewById2 : null;
        if (imageView != null) {
            imageView.setVisibility(z ? 8 : 0);
        }
        View findViewById3 = brightnessSliderController.getRootView().findViewById(R.id.slider_container);
        RelativeLayout relativeLayout = findViewById3 instanceof RelativeLayout ? (RelativeLayout) findViewById3 : null;
        if (relativeLayout != null) {
            Intrinsics.checkNotNull(resources);
            getResourcePicker().resourcePickHelper.getTargetPicker().getClass();
            Boolean valueOf = Boolean.valueOf(z);
            ViewGroup.LayoutParams layoutParams4 = relativeLayout.getLayoutParams();
            LinearLayout.LayoutParams layoutParams5 = layoutParams4 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams4 : null;
            if (layoutParams5 != null) {
                layoutParams5.width = -1;
                layoutParams5.height = brightnessBarExpandedHeight;
                if (valueOf.equals(Boolean.TRUE)) {
                    layoutParams5.weight = 1.0f;
                } else {
                    layoutParams5.weight = 0.0f;
                }
                layoutParams5.topMargin = 0;
            } else {
                layoutParams5 = null;
            }
            relativeLayout.setLayoutParams(layoutParams5);
        }
        View findViewById4 = brightnessSliderController.getRootView().findViewById(R.id.slider_container);
        RelativeLayout relativeLayout2 = findViewById4 instanceof RelativeLayout ? (RelativeLayout) findViewById4 : null;
        if (relativeLayout2 != null) {
            relativeLayout2.setPadding(0, relativeLayout2.getPaddingTop(), 0, relativeLayout2.getPaddingBottom());
        }
        View findViewById5 = brightnessSliderController.getRootView().findViewById(R.id.brightness_tile_layout);
        LinearLayout linearLayout2 = findViewById5 instanceof LinearLayout ? (LinearLayout) findViewById5 : null;
        if (linearLayout2 != null) {
            Intrinsics.checkNotNull(resources);
            ViewGroup.LayoutParams layoutParams6 = linearLayout2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams7 = layoutParams6 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams6 : null;
            if (layoutParams7 != null) {
                layoutParams7.width = getResourcePicker().getBrightnessBarExpandedHeight(linearLayout2.getContext());
                layoutParams7.height = getResourcePicker().getBrightnessBarExpandedHeight(linearLayout2.getContext());
                layoutParams7.weight = 0.0f;
                layoutParams7.setMarginStart(resources.getDimensionPixelSize(R.dimen.brightness_slider_end_margin));
                layoutParams7.gravity = 17;
                layoutParams = layoutParams7;
            }
            linearLayout2.setLayoutParams(layoutParams);
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.brightness_tile_horizontal_between_margin);
            int childCount = linearLayout2.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {
                View childAt = linearLayout2.getChildAt(i);
                LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams8.setMarginEnd(dimensionPixelSize);
                childAt.setLayoutParams(layoutParams8);
            }
        }
        SecBrightnessSliderController secBrightnessSliderController = brightnessSliderController.mSecBrightnessSliderController;
        if (secBrightnessSliderController != null) {
            secBrightnessSliderController.updateSliderHeight(brightnessBarExpandedHeight);
        }
    }
}
