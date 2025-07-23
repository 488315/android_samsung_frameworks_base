package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda6;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.qs.bar.BrightnessMediaDevicesBar;
import com.android.systemui.qs.bar.MediaDevicesBar;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewUtil;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class LegacyQsOpenAnimator extends SecQSImplAnimatorBase implements OnHeadsUpChangedListener {
    public final BarController mBarController;
    public TouchAnimator mBrightnessBarAnimator;
    public BrightnessMediaDevicesBar mBrightnessMediaDevicesBar;
    public MotionLayout mCarrierAndSystemIconContainer;
    public TouchAnimator mCarrierIconAnimator;
    public final Context mContext;
    public float mFadingSpan;
    public SecQuickStatusBarHeader mHeader;
    public TouchAnimator mHeaderDateSettingAnimator;
    public View mHeaderDateSettingContainer;
    public final HeadsUpManager mHeadsUpManager;
    public boolean mHeadsUpPinned;
    public TouchAnimator mMediaDeviceBarAnimator;
    public final NotificationPanelViewController mPanelViewController;
    public TouchAnimator mQQSAnimator;
    public final SecQuickQSPanelController mQuickQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQsUiDisplayModeInteractor mSecQsUiDisplayModeInteractor;
    public final ShadeHeaderController mShadeHeaderController;
    public final QSAnimViewProvider mViewProvider;
    public final int mYDiff;
    public final ArrayList mAnimContents = new ArrayList();
    public final PathInterpolator mInterpolator = new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f);
    public final int[] mLoc = new int[2];
    public final ConfigurationState mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ASSET_SEQ, ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.THEME_SEQ, ConfigurationState.ConfigurationField.UI_MODE));
    public float mPanelExpansion = 1.0f;

    public LegacyQsOpenAnimator(Context context, BarController barController, HeadsUpManager headsUpManager, SecQSPanelResourcePicker secQSPanelResourcePicker, SecQuickQSPanelController secQuickQSPanelController, ShadeHeaderController shadeHeaderController, NotificationPanelViewController notificationPanelViewController, QSAnimViewProvider qSAnimViewProvider, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.mContext = context;
        this.mBarController = barController;
        this.mYDiff = context.getResources().getDimensionPixelSize(R.dimen.qs_open_anim_y_diff);
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpPinned = ((HeadsUpManagerImpl) headsUpManager).mHasPinnedNotification;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mQuickQsPanelController = secQuickQSPanelController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mPanelViewController = notificationPanelViewController;
        this.mViewProvider = qSAnimViewProvider;
        this.mSecQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
    }

    public static void getRelativePositionInt(View view, int[] iArr) {
        if ((view instanceof NotificationsQuickSettingsContainer) || view == null) {
            return;
        }
        iArr[0] = view.getLeft() + iArr[0];
        iArr[1] = view.getTop() + iArr[1];
        getRelativePositionInt((View) view.getParent(), iArr);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        if (!isThereNoView() || z) {
            ArrayList arrayList = this.mAnimContents;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                View view = (View) obj;
                view.setAlpha(1.0f);
                view.setTranslationY(0.0f);
                view.setVisibility(0);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        ((HeadsUpManagerImpl) this.mHeadsUpManager).removeListener(this);
        this.mAnimContents.clear();
        this.mQs = null;
        this.mCarrierAndSystemIconContainer = null;
        this.mHeader = null;
        this.mHeaderDateSettingContainer = null;
        this.mBrightnessMediaDevicesBar = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsOpenAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, this.mAnimContents);
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final float getEndDelay(View view, int i) {
        int width = view.getWidth() / 2;
        int[] iArr = this.mLoc;
        iArr[0] = width;
        iArr[1] = 0;
        getRelativePositionInt(view, iArr);
        int i2 = iArr[1] + i;
        float maxPanelHeight = this.mPanelViewController.getMaxPanelHeight();
        float f = ((maxPanelHeight - i2) - this.mFadingSpan) / maxPanelHeight;
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            f = 0.0f;
        }
        return Math.max(0.0f, f);
    }

    public final float getStartDelay(View view) {
        int width = view.getWidth() / 2;
        int[] iArr = this.mLoc;
        iArr[0] = width;
        iArr[1] = 0;
        getRelativePositionInt(view, iArr);
        int i = iArr[1];
        return Math.max(0.0f, ((i - this.mYDiff) / this.mPanelViewController.getMaxPanelHeight()) - 0.1f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        return super.isThereNoView() || SecPanelSplitHelper.isEnabled();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            updateAnimators();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpPinnedModeChanged(boolean z) {
        MotionLayout motionLayout;
        this.mHeadsUpPinned = z;
        if (SecPanelSplitHelper.isEnabled() || (motionLayout = this.mCarrierAndSystemIconContainer) == null || !this.mHeadsUpPinned || this.mPanelExpansion == 1.0f) {
            return;
        }
        motionLayout.setAlpha(0.0f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        QsAnimatorState.panelExpanded = false;
        if (isThereNoView()) {
            return;
        }
        clearAnimationState();
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        ShadeRepository shadeRepository;
        if (isThereNoView()) {
            return;
        }
        if (this.mHeaderDateSettingContainer != null && (shadeRepository = this.mShadeRepository) != null && ((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
            this.mHeaderDateSettingContainer.setAlpha(1.0f);
            this.mHeaderDateSettingContainer.setTranslationY(0.0f);
        }
        if (QsAnimatorState.state != 1) {
            updatePanelExpansion(shadeExpansionChangeEvent.fraction);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelOpened() {
        QsAnimatorState.panelExpanded = true;
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        super.onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        if (this.mPanelSplitEnabled) {
            return;
        }
        updateAnimators();
        onQsClipBoundChanged(1.0f);
    }

    public final void onQsClipBoundChanged(float f) {
        boolean z;
        TouchAnimator touchAnimator;
        if (isThereNoView() || !this.mAnimatorsInitialiezed || Float.isNaN(f) || (z = this.mHeadsUpPinned)) {
            isThereNoView();
            Float.isNaN(f);
            ShadeRepository shadeRepository = this.mShadeRepository;
            if (shadeRepository != null) {
                ((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue();
                ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue();
                return;
            }
            return;
        }
        if (z) {
            f = 1.0f;
        }
        Log.d("QsOpenAnimator", "panelExpandedFraction : " + f);
        if (!this.mPanelViewController.isExpanded()) {
            f = 0.0f;
        }
        float f2 = f >= 0.0f ? f : 0.0f;
        float f3 = f2 <= 1.0f ? f2 : 1.0f;
        ShadeRepository shadeRepository2 = this.mShadeRepository;
        if (shadeRepository2 != null && !((Boolean) ((ShadeRepositoryImpl) shadeRepository2).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue() && (touchAnimator = this.mCarrierIconAnimator) != null) {
            touchAnimator.setPosition(f3);
        }
        TouchAnimator touchAnimator2 = this.mHeaderDateSettingAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f3);
        }
        TouchAnimator touchAnimator3 = this.mQQSAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f3);
        }
        TouchAnimator touchAnimator4 = this.mBrightnessBarAnimator;
        if (touchAnimator4 != null) {
            touchAnimator4.setPosition(f3);
        }
        TouchAnimator touchAnimator5 = this.mMediaDeviceBarAnimator;
        if (touchAnimator5 != null) {
            touchAnimator5.setPosition(f3);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        View view;
        QsAnimatorState.state = i;
        if (!isThereNoView()) {
            int i2 = i == 1 ? 4 : 0;
            QSAnimView qSAnimView = this.mViewProvider.get(QSAnimViewProvider.ViewType.ROOT_VIEW);
            if (qSAnimView != null) {
                Log.d("QsOpenAnimator", "onStateChanged, set NonInterceptingScrollView visibility: ".concat(i2 == 0 ? "VISIBLE" : "INVISIBLE"));
                qSAnimView.setVisibility(i2);
            }
        }
        if (i == 1 && (view = this.mHeaderDateSettingContainer) != null) {
            view.setAlpha(1.0f);
            this.mHeaderDateSettingContainer.setTranslationY(0.0f);
        } else if (i == 2 || i == 0) {
            onQsClipBoundChanged(1.0f);
            return;
        }
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        if (SecPanelSplitHelper.isEnabled()) {
            return;
        }
        clearAnimationState();
        MotionLayout motionLayout = this.mCarrierAndSystemIconContainer;
        if (motionLayout != null) {
            motionLayout.setAlpha(0.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
        } else {
            this.mQs = (QSImpl) qs;
            ((HeadsUpManagerImpl) this.mHeadsUpManager).addListener(this);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQsExpansionPosition(float f) {
        if (isThereNoView()) {
            return;
        }
        if (f == 1.0f && this.mPanelExpansion != 1.0f) {
            onQsClipBoundChanged(1.0f);
        }
        QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setTransitionToFullShadeAmount(float f) {
        ShadeRepository shadeRepository;
        if (isThereNoView() || QsAnimatorState.state != 1) {
            return;
        }
        if (this.mHeaderDateSettingContainer != null && (shadeRepository = this.mShadeRepository) != null && ((Boolean) ((ShadeRepositoryImpl) shadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
            this.mHeaderDateSettingContainer.setAlpha(1.0f);
            this.mHeaderDateSettingContainer.setTranslationY(0.0f);
        }
        updatePanelExpansion(f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        float endDelay;
        MediaDevicesBar mediaDevicesBar;
        View view;
        BrightnessBar brightnessBar;
        View view2;
        if (isThereNoView()) {
            return;
        }
        this.mAnimContents.clear();
        this.mFadingSpan = this.mResourcePicker.getTileIconSize(this.mContext) * 3;
        this.mCarrierAndSystemIconContainer = this.mShadeHeaderController.header;
        View view3 = null;
        SecQuickStatusBarHeader secQuickStatusBarHeader = this.mQs.getView() instanceof ViewGroup ? ViewUtil.getSecQuickStatusBarHeader((ViewGroup) this.mQs.getView()) : null;
        this.mHeader = secQuickStatusBarHeader;
        this.mHeaderDateSettingContainer = secQuickStatusBarHeader != null ? secQuickStatusBarHeader.findViewById(R.id.quick_qs_date_buttons).findViewWithTag("open_anim") : null;
        View findViewWithTag = ((View) this.mQuickQsPanelController.mTileLayout).findViewWithTag("open_anim");
        BrightnessMediaDevicesBar brightnessMediaDevicesBar = (BrightnessMediaDevicesBar) ((BarItemImpl) this.mBarController.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda6(BarType.BRIGHTNESS_MEDIA_DEVICES, 1)).findFirst().orElse(null));
        this.mBrightnessMediaDevicesBar = brightnessMediaDevicesBar;
        View findViewWithTag2 = (brightnessMediaDevicesBar == null || (brightnessBar = brightnessMediaDevicesBar.mBrightnessBar) == null || (view2 = brightnessBar.mBarRootView) == null) ? null : view2.findViewWithTag("open_anim");
        BrightnessMediaDevicesBar brightnessMediaDevicesBar2 = this.mBrightnessMediaDevicesBar;
        if (brightnessMediaDevicesBar2 != null && (mediaDevicesBar = brightnessMediaDevicesBar2.mMediaDevicesBar) != null && (view = mediaDevicesBar.mBarRootView) != null) {
            view3 = view.findViewWithTag("open_anim");
        }
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mCarrierAndSystemIconContainer, "alpha", 0.0f, 1.0f);
        MotionLayout motionLayout = this.mCarrierAndSystemIconContainer;
        float f = -this.mYDiff;
        builder.addFloat(motionLayout, "translationY", f, 0.0f);
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.mSecQsUiDisplayModeInteractor;
        builder.mStartDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : getStartDelay(this.mCarrierAndSystemIconContainer);
        if (secQsUiDisplayModeInteractor.isTablet()) {
            endDelay = 0.0f;
        } else {
            MotionLayout motionLayout2 = this.mCarrierAndSystemIconContainer;
            endDelay = getEndDelay(motionLayout2, motionLayout2.getHeight());
        }
        builder.mEndDelay = endDelay;
        builder.mInterpolator = this.mInterpolator;
        this.mCarrierIconAnimator = builder.build();
        this.mAnimContents.add(this.mCarrierAndSystemIconContainer);
        if (this.mHeaderDateSettingContainer != null) {
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(this.mHeaderDateSettingContainer, "alpha", 0.0f, 1.0f);
            builder2.addFloat(this.mHeaderDateSettingContainer, "translationY", f, 0.0f);
            builder2.mStartDelay = getStartDelay(this.mHeaderDateSettingContainer);
            View view4 = this.mHeaderDateSettingContainer;
            builder2.mEndDelay = getEndDelay(view4, view4.getHeight());
            builder2.mInterpolator = this.mInterpolator;
            this.mHeaderDateSettingAnimator = builder2.build();
            this.mAnimContents.add(this.mHeaderDateSettingContainer);
        }
        TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
        builder3.addFloat(findViewWithTag, "alpha", 0.0f, 1.0f);
        builder3.addFloat(findViewWithTag, "translationY", f, 0.0f);
        builder3.mStartDelay = getStartDelay(findViewWithTag);
        builder3.mEndDelay = getEndDelay(findViewWithTag, findViewWithTag.getHeight());
        builder3.mInterpolator = this.mInterpolator;
        this.mQQSAnimator = builder3.build();
        if (findViewWithTag2 != null) {
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(findViewWithTag2, "alpha", 0.0f, 1.0f);
            builder4.addFloat(findViewWithTag2, "translationY", f, 0.0f);
            builder4.mStartDelay = getStartDelay(findViewWithTag2);
            builder4.mEndDelay = getEndDelay(findViewWithTag2, findViewWithTag2.getHeight());
            builder4.mInterpolator = this.mInterpolator;
            this.mBrightnessBarAnimator = builder4.build();
            this.mAnimContents.add(findViewWithTag2);
        }
        if (view3 != null) {
            TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
            builder5.addFloat(view3, "alpha", 0.0f, 1.0f);
            builder5.addFloat(view3, "translationY", f, 0.0f);
            builder5.mStartDelay = getStartDelay(view3);
            builder5.mEndDelay = getEndDelay(view3, view3.getHeight());
            builder5.mInterpolator = this.mInterpolator;
            this.mMediaDeviceBarAnimator = builder5.build();
            this.mAnimContents.add(view3);
        }
        this.mAnimatorsInitialiezed = true;
        onQsClipBoundChanged(this.mPanelExpansion);
    }

    public final void updatePanelExpansion(float f) {
        QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
        float f2 = this.mPanelExpansion;
        if (f2 > 0.0f && this.mHeadsUpPinned) {
            this.mHeadsUpPinned = false;
        }
        if (f2 != f || f == 0.0f || f == 1.0f) {
            onQsClipBoundChanged(f);
            this.mPanelExpansion = f;
        }
    }
}
