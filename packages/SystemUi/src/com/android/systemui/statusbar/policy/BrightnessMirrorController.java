package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.qp.util.AnimationUtils;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BrightnessMirrorController implements CallbackController {
    public final AnimationUtils mAnimationUtils;
    public final SecQpBlurController mBlurController;
    public ImageView mBrightnessDetailIcon;
    public LottieAnimationView mBrightnessIcon;
    public FrameLayout mBrightnessMirror;
    public int mBrightnessMirrorHeight;
    public int mBrightnessMirrorWidth;
    public final NotificationShadeDepthController mDepthController;
    public boolean mExpanded;
    public float mIconAnimationValue;
    public final ShadeViewController mNotificationPanel;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SettingsHelper mSettingsHelper;
    public RelativeLayout mSliderContainer;
    public final NotificationShadeWindowView mStatusBarWindow;
    public LinearLayout mTileLayout;
    public BrightnessSliderController mToggleSliderController;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final Consumer mVisibilityCallback;
    public final ArraySet mBrightnessMirrorListeners = new ArraySet();
    public final int[] mInt2Cache = new int[2];
    public final ArrayList tilesOnMirror = new ArrayList();

    public BrightnessMirrorController(NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, NotificationShadeDepthController notificationShadeDepthController, BrightnessSliderController.Factory factory, Consumer<Boolean> consumer, SecQpBlurController secQpBlurController) {
        this.mStatusBarWindow = notificationShadeWindowView;
        this.mToggleSliderFactory = factory;
        FrameLayout frameLayout = (FrameLayout) notificationShadeWindowView.findViewById(R.id.brightness_mirror_container);
        this.mBrightnessMirror = frameLayout;
        BrightnessSliderController create = factory.create(frameLayout.getContext(), this.mBrightnessMirror);
        create.init();
        this.mBrightnessMirror.addView(create.mView, -1, -1);
        this.mToggleSliderController = create;
        this.mNotificationPanel = shadeViewController;
        this.mDepthController = notificationShadeDepthController;
        ((NotificationPanelViewController) shadeViewController).mPanelAlphaEndAction = new Runnable() { // from class: com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessMirrorController.this.mBrightnessMirror.setVisibility(4);
            }
        };
        this.mAnimationUtils = (AnimationUtils) Dependency.get(AnimationUtils.class);
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        initResources();
        this.mVisibilityCallback = consumer;
        if (QpRune.QUICK_PANEL_BLUR) {
            this.mBlurController = secQpBlurController;
        }
        updateLayout();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        BrightnessBar.C20902 c20902 = (BrightnessBar.C20902) obj;
        Objects.requireNonNull(c20902);
        this.mBrightnessMirrorListeners.add(c20902);
    }

    public final void hideMirror() {
        this.mVisibilityCallback.accept(Boolean.FALSE);
        ((NotificationPanelViewController) this.mNotificationPanel).setAlpha(255, true);
        NotificationShadeDepthController.DepthAnimation depthAnimation = this.mDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != 0) {
            depthAnimation.pendingRadius = 0;
            depthAnimation.springAnimation.animateToFinalPosition(0);
        }
        int i = 0;
        while (true) {
            ArraySet arraySet = this.mBrightnessMirrorListeners;
            if (i >= arraySet.size()) {
                break;
            }
            ((BrightnessBar.C20902) arraySet.valueAt(i)).getClass();
            i++;
        }
        View view = this.mToggleSliderController.mView;
        ((BrightnessSliderView) view).mIsMirror = false;
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) view;
        boolean z = !this.mExpanded;
        if (brightnessSliderView.mIsCollapsed != z) {
            brightnessSliderView.mIsCollapsed = z;
            brightnessSliderView.updateSliderResources();
            brightnessSliderView.setDualSeekBarResources(false);
        }
        if (QpRune.QUICK_PANEL_BLUR) {
            this.mBlurController.setBrightnessMirrorVisible(false);
        }
    }

    public final void initResources() {
        View view = this.mToggleSliderController.mView;
        LottieAnimationView lottieAnimationView = (LottieAnimationView) view.findViewById(R.id.brightness_icon);
        this.mBrightnessIcon = lottieAnimationView;
        if (lottieAnimationView != null) {
            Resources resources = this.mBrightnessMirror.getContext().getResources();
            LottieAnimationView lottieAnimationView2 = this.mBrightnessIcon;
            this.mAnimationUtils.getClass();
            lottieAnimationView2.setAnimation("brightness_icon_85.json");
            FrameLayout frameLayout = this.mBrightnessMirror;
            if (frameLayout != null && (DeviceState.isOpenTheme(frameLayout.getContext()) || this.mSettingsHelper.isColorThemeEnabled$1())) {
                this.mBrightnessIcon.addValueCallback(new KeyPath("normal", "**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(resources.getColor(R.color.animated_brightness_icon_color, null))));
            }
        }
        this.mTileLayout = (LinearLayout) view.findViewById(R.id.brightness_tile_layout);
        this.mSliderContainer = (RelativeLayout) view.findViewById(R.id.slider_container);
        this.mBrightnessDetailIcon = (ImageView) view.findViewById(R.id.brightness_detail);
    }

    public final void reinflate() {
        View findViewById;
        FrameLayout frameLayout = this.mBrightnessMirror;
        NotificationShadeWindowView notificationShadeWindowView = this.mStatusBarWindow;
        int indexOfChild = notificationShadeWindowView.indexOfChild(frameLayout);
        notificationShadeWindowView.removeView(this.mBrightnessMirror);
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.sec_brightness_mirror_container, (ViewGroup) notificationShadeWindowView, false);
        this.mBrightnessMirror = frameLayout2;
        BrightnessSliderController create = this.mToggleSliderFactory.create(frameLayout2.getContext(), this.mBrightnessMirror);
        create.init();
        this.mBrightnessMirror.addView(create.mView, -1, -1);
        this.mToggleSliderController = create;
        notificationShadeWindowView.addView(this.mBrightnessMirror, indexOfChild);
        updateLayout();
        int i = 0;
        while (true) {
            ArraySet arraySet = this.mBrightnessMirrorListeners;
            if (i >= arraySet.size()) {
                break;
            }
            BrightnessBar.C20902 c20902 = (BrightnessBar.C20902) arraySet.valueAt(i);
            c20902.getClass();
            Uri uri = BrightnessBar.EMERGENCY_MODE_URI;
            BrightnessBar.this.updateBrightnessMirror();
            i++;
        }
        initResources();
        boolean isEmergencyMode = this.mSettingsHelper.isEmergencyMode();
        FrameLayout frameLayout3 = this.mBrightnessMirror;
        if (frameLayout3 != null && (findViewById = frameLayout3.findViewById(R.id.brightness_detail_container)) != null) {
            findViewById.setVisibility(isEmergencyMode ? 8 : 0);
        }
        updateLayout();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mBrightnessMirrorListeners.remove((BrightnessBar.C20902) obj);
    }

    public final void setLocationAndSize(View view) {
        int[] iArr = this.mInt2Cache;
        view.getLocationInWindow(iArr);
        this.mBrightnessMirrorWidth = view.getWidth();
        this.mBrightnessMirrorHeight = view.getHeight();
        updateLayout();
        int width = (view.getWidth() / 2) + iArr[0];
        int height = (view.getHeight() / 2) + iArr[1];
        this.mBrightnessMirror.setTranslationX(0.0f);
        this.mBrightnessMirror.setTranslationY(0.0f);
        this.mBrightnessMirror.getLocationInWindow(iArr);
        int width2 = (this.mBrightnessMirror.getWidth() / 2) + iArr[0];
        int height2 = (this.mBrightnessMirror.getHeight() / 2) + iArr[1];
        this.mBrightnessMirror.setTranslationX(width - width2);
        this.mBrightnessMirror.setTranslationY(height - height2);
    }

    public final void showMirror() {
        View view = this.mToggleSliderController.mView;
        ((BrightnessSliderView) view).mIsMirror = true;
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) view;
        boolean z = !this.mExpanded;
        if (brightnessSliderView.mIsCollapsed != z) {
            brightnessSliderView.mIsCollapsed = z;
            brightnessSliderView.updateSliderResources();
            brightnessSliderView.setDualSeekBarResources(false);
        }
        if (this.mExpanded) {
            this.mBrightnessDetailIcon.setColorFilter(this.mBrightnessMirror.getContext().getColor(R.color.tw_check_box_tint));
        } else {
            this.mBrightnessDetailIcon.setColorFilter(this.mBrightnessMirror.getContext().getColor(R.color.tw_check_box_tint_collapsed));
        }
        this.mBrightnessMirror.setVisibility(0);
        this.mVisibilityCallback.accept(Boolean.TRUE);
        ((NotificationPanelViewController) this.mNotificationPanel).setAlpha(0, true);
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        int blurRadiusOfRatio = (int) notificationShadeDepthController.blurUtils.blurRadiusOfRatio(1.0f);
        NotificationShadeDepthController.DepthAnimation depthAnimation = notificationShadeDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != blurRadiusOfRatio) {
            depthAnimation.pendingRadius = blurRadiusOfRatio;
            depthAnimation.springAnimation.animateToFinalPosition(blurRadiusOfRatio);
        }
        if (this.mExpanded) {
            this.mTileLayout.setVisibility(4);
            ArrayList arrayList = this.tilesOnMirror;
            int size = arrayList.size();
            this.mTileLayout.removeAllViews();
            for (int i = 0; i < size; i++) {
                QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tileView;
                TextView textView = new TextView(this.mBrightnessMirror.getContext());
                textView.setText("Dummy text");
                textView.setLayoutParams(qSTileView.getLayoutParams());
                textView.getLayoutParams().width = qSTileView.getWidth();
                this.mTileLayout.addView(textView);
            }
        } else {
            this.mTileLayout.setVisibility(8);
        }
        if (QpRune.QUICK_PANEL_BLUR) {
            this.mBlurController.setBrightnessMirrorVisible(true);
        }
    }

    public final void updateIconSize(int i) {
        LottieAnimationView lottieAnimationView;
        if (this.mBrightnessMirror == null || (lottieAnimationView = this.mBrightnessIcon) == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lottieAnimationView.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.mBrightnessIcon.setLayoutParams(layoutParams);
        ImageView imageView = (ImageView) this.mBrightnessMirror.findViewById(R.id.brightness_detail);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams2.width = i;
        layoutParams2.height = i;
        imageView.setLayoutParams(layoutParams2);
    }

    public final void updateLayout() {
        FrameLayout frameLayout = this.mBrightnessMirror;
        if (frameLayout != null) {
            Context context = frameLayout.getContext();
            ViewGroup.LayoutParams layoutParams = this.mBrightnessMirror.getLayoutParams();
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qs_header_bar_side_padding);
            boolean z = QpRune.QUICK_TABLET;
            if (z || this.mBrightnessMirror.getResources().getConfiguration().orientation != 2) {
                if (this.mExpanded) {
                    RelativeLayout relativeLayout = this.mSliderContainer;
                    relativeLayout.setPadding(dimensionPixelSize, relativeLayout.getPaddingTop(), dimensionPixelSize, this.mSliderContainer.getPaddingBottom());
                } else {
                    RelativeLayout relativeLayout2 = this.mSliderContainer;
                    relativeLayout2.setPadding(0, relativeLayout2.getPaddingTop(), 0, this.mSliderContainer.getPaddingBottom());
                }
                ((LinearLayout) this.mToggleSliderController.mView.findViewById(R.id.brightness_bar_container)).setOrientation(1);
            } else {
                ((LinearLayout) this.mToggleSliderController.mView.findViewById(R.id.brightness_bar_container)).setOrientation(0);
                if (this.mExpanded) {
                    ((LinearLayout) this.mToggleSliderController.mView.findViewById(R.id.brightness_bar_container)).setGravity(16);
                    RelativeLayout relativeLayout3 = this.mSliderContainer;
                    relativeLayout3.setPadding(dimensionPixelSize, relativeLayout3.getPaddingTop(), dimensionPixelSize, this.mSliderContainer.getPaddingBottom());
                } else {
                    RelativeLayout relativeLayout4 = this.mSliderContainer;
                    relativeLayout4.setPadding(0, relativeLayout4.getPaddingTop(), 0, this.mSliderContainer.getPaddingBottom());
                }
            }
            layoutParams.width = this.mBrightnessMirrorWidth;
            layoutParams.height = this.mBrightnessMirrorHeight;
            Context context2 = this.mBrightnessMirror.getContext();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mSliderContainer.getLayoutParams();
            this.mResourcePicker.getClass();
            layoutParams2.height = SecQSPanelResourcePicker.getBrightnessBarHeight(context2);
            int i = -1;
            layoutParams2.width = (z || SecQSPanelResourcePicker.isPortrait(context2)) ? -1 : context2.getResources().getDimensionPixelSize(R.dimen.brightness_bar_width);
            this.mSliderContainer.setLayoutParams(layoutParams2);
            Context context3 = this.mBrightnessMirror.getContext();
            context3.getResources();
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mTileLayout.getLayoutParams();
            if (!z && !SecQSPanelResourcePicker.isPortrait(context3)) {
                i = -2;
            }
            layoutParams3.width = i;
            layoutParams3.height = SecQSPanelResourcePicker.getBrightnessTileLayoutHeight(context3);
            layoutParams3.setMarginEnd(SecQSPanelResourcePicker.getBrightnessTileLayoutRightMargin(context3));
            Resources resources = context3.getResources();
            layoutParams3.bottomMargin = z ? resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_bottom_margin_tablet) : resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_bottom_margin);
            this.mTileLayout.setLayoutParams(layoutParams3);
            if (this.mTileLayout.getChildCount() > 1) {
                View childAt = this.mTileLayout.getChildAt(0);
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams4.setMarginEnd(SecQSPanelResourcePicker.getBrightnessTileLayoutBetweenMargin(context3));
                childAt.setLayoutParams(layoutParams4);
            }
            this.mBrightnessMirror.setLayoutParams(layoutParams);
            updateIconSize(SecQSPanelResourcePicker.getBrightnessIconSize(context));
            int brightnessIconSize = SecQSPanelResourcePicker.getBrightnessIconSize(context);
            int brightnessIconSize2 = SecQSPanelResourcePicker.getBrightnessIconSize(context);
            FrameLayout frameLayout2 = this.mBrightnessMirror;
            if (frameLayout2 != null) {
                RelativeLayout relativeLayout5 = (RelativeLayout) frameLayout2.findViewById(R.id.brightness_detail_container);
                RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) relativeLayout5.getLayoutParams();
                layoutParams5.width = brightnessIconSize;
                layoutParams5.height = brightnessIconSize2;
                relativeLayout5.setLayoutParams(layoutParams5);
            }
        }
        BrightnessSliderController brightnessSliderController = this.mToggleSliderController;
        if (brightnessSliderController != null) {
            brightnessSliderController.updateSliderHeight();
        }
    }
}
