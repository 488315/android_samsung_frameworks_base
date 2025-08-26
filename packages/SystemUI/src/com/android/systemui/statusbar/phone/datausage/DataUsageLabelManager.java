package com.android.systemui.statusbar.phone.datausage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda12;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda13;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda15;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.function.DoubleSupplier;

/* loaded from: classes3.dex */
public class DataUsageLabelManager {
    public static final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final Context mContext;
    public final DataUsageLabelParent mDataUsageLabelParent;
    public boolean mIsFadingIn;
    public boolean mIsFadingOut;
    public DataUsageLabelView mLabelView;
    public final NavSettingsHelper mNavSettingsHelper;
    public final QuickStarHelper mQuickStarHelper;
    public int mLastDensityDpi = -1;
    public int mLastOrientation = -1;
    public int mLastSemMobileKeyboardCovered = -1;
    public boolean mPreviousVisible = false;
    public float mPrvAlpha = -1.0f;
    public boolean mLabelAlphaAnimStarted = true;
    public int mInsetNavigationBarBottomHeight = 0;

    public class NavSettingsHelper implements SettingsHelper.OnChangedCallback {
        public boolean IsNavigationBarGestureHintEnabled;
        public boolean IsNavigationBarGestureProtectionEnabled;
        public boolean IsNavigationBarHideKeyboardButtonEnabled;
        public final Uri[] SETTINGS_VALUE_LIST = {Settings.Secure.getUriFor(SettingsHelper.INDEX_GAME_DOUBLE_SWIPE_ENABLE), Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_GESTURE_HINT), Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD)};
        private SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);

        public NavSettingsHelper() {
        }

        public final String getDumpText() {
            StringBuilder sb = new StringBuilder("NavSettingsHelper(");
            sb.append("navGestureProtectionEnabled:" + this.IsNavigationBarGestureProtectionEnabled);
            sb.append(", navGestureHintEnabled:" + this.IsNavigationBarGestureHintEnabled);
            sb.append(", navHideKeyboardButtonEnabled:" + this.IsNavigationBarHideKeyboardButtonEnabled);
            sb.append(")");
            return sb.toString();
        }

        public final void onAttachedToWindow() {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (settingsHelper != null) {
                settingsHelper.registerCallback(this, this.SETTINGS_VALUE_LIST);
            }
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            DataUsageLabelManager dataUsageLabelManager = DataUsageLabelManager.this;
            dataUsageLabelManager.updateNavBarHeight(dataUsageLabelManager.mInsetNavigationBarBottomHeight);
        }

        public final void onDetachedFromWindow() {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (settingsHelper != null) {
                settingsHelper.unregisterCallback(this);
            }
        }

        public final boolean updateSettingsAndCheckChanges() {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (settingsHelper == null) {
                return false;
            }
            boolean z = this.IsNavigationBarGestureProtectionEnabled;
            boolean z2 = this.IsNavigationBarGestureHintEnabled;
            boolean z3 = this.IsNavigationBarHideKeyboardButtonEnabled;
            this.IsNavigationBarGestureProtectionEnabled = settingsHelper.isNavigationBarGestureProtectionEnabled();
            this.IsNavigationBarGestureHintEnabled = this.mSettingsHelper.isNavigationBarGestureHintEnabled();
            boolean zIsNavigationBarHideKeyboardButtonEnabled = this.mSettingsHelper.isNavigationBarHideKeyboardButtonEnabled();
            this.IsNavigationBarHideKeyboardButtonEnabled = zIsNavigationBarHideKeyboardButtonEnabled;
            return (this.IsNavigationBarGestureProtectionEnabled == z && this.IsNavigationBarGestureHintEnabled == z2 && zIsNavigationBarHideKeyboardButtonEnabled == z3) ? false : true;
        }
    }

    public final class QuickStarHelper implements SlimIndicatorViewSubscriber {
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

        public QuickStarHelper(SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }

        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            DataUsageLabelManager.this.updateLabelVisibility(false);
        }
    }

    public DataUsageLabelManager(final NotificationPanelViewController notificationPanelViewController, SlimIndicatorViewMediator slimIndicatorViewMediator) {
        if (notificationPanelViewController.mDataUsageLabelParent == null) {
            notificationPanelViewController.mDataUsageLabelParent = new DataUsageLabelParent(new NotificationPanelViewController$$ExternalSyntheticLambda12(notificationPanelViewController, 2), new NotificationPanelViewController$$ExternalSyntheticLambda13(notificationPanelViewController, 3), new DoubleSupplier() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda39
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    return notificationPanelViewController.mQsController.mExpansionHeight;
                }
            }, new NotificationPanelViewController$$ExternalSyntheticLambda15(notificationPanelViewController, 1), new NotificationPanelViewController$$ExternalSyntheticLambda13(notificationPanelViewController, 4), new NotificationPanelViewController$$ExternalSyntheticLambda15(notificationPanelViewController, 0));
        }
        DataUsageLabelParent dataUsageLabelParent = notificationPanelViewController.mDataUsageLabelParent;
        this.mDataUsageLabelParent = dataUsageLabelParent;
        NotificationPanelView notificationPanelView = (NotificationPanelView) dataUsageLabelParent.mPanelViewSupplier.get();
        this.mContext = notificationPanelView != null ? notificationPanelView.getContext() : null;
        this.mNavSettingsHelper = new NavSettingsHelper();
        this.mQuickStarHelper = new QuickStarHelper(slimIndicatorViewMediator);
    }

    public final void animateLabelAlpha(View view, boolean z) {
        if (view == null || view.animate() == null) {
            return;
        }
        view.animate().alpha(z ? 1.0f : 0.0f).setDuration(150L).setStartDelay(z ? 150L : 0L).setInterpolator(new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f)).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.1
            /* JADX WARN: Removed duplicated region for block: B:11:0x002a  */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f = DataUsageLabelManager.this.mPrvAlpha;
                if (Float.compare(1.0f, f) == 0 || Float.compare(0.0f, f) == 0) {
                    DataUsageLabelManager.this.updateLabelVisibility(true);
                } else {
                    DataUsageLabelManager.this.getClass();
                    if (Float.compare(1.0f, animatedFraction) == 0 || Float.compare(0.0f, animatedFraction) == 0) {
                    }
                }
                DataUsageLabelManager dataUsageLabelManager = DataUsageLabelManager.this;
                float f2 = dataUsageLabelManager.mPrvAlpha;
                if (Float.compare(f2, animatedFraction) == 0 || Float.compare(1.0f, animatedFraction) == 0 || Float.compare(0.0f, animatedFraction) == 0) {
                    dataUsageLabelManager.mIsFadingIn = false;
                    dataUsageLabelManager.mIsFadingOut = false;
                } else if (Float.compare(f2, animatedFraction) > 0) {
                    dataUsageLabelManager.mIsFadingIn = false;
                    dataUsageLabelManager.mIsFadingOut = true;
                } else {
                    dataUsageLabelManager.mIsFadingIn = true;
                    dataUsageLabelManager.mIsFadingOut = false;
                }
                if (Float.compare(f2, animatedFraction) == 0) {
                    return;
                }
                DataUsageLabelManager.this.mPrvAlpha = animatedFraction;
            }
        }).start();
    }

    public final void onPanelConfigurationChanged(Configuration configuration) {
        int i = configuration.orientation;
        int i2 = this.mLastOrientation;
        if (i != i2 || configuration.densityDpi != this.mLastDensityDpi || this.mLastSemMobileKeyboardCovered != configuration.semMobileKeyboardCovered) {
            if (i != i2) {
                this.mLastOrientation = i;
                updateLabelVisibility(true);
            }
            this.mLastDensityDpi = configuration.densityDpi;
            this.mLastSemMobileKeyboardCovered = configuration.semMobileKeyboardCovered;
            ViewGroup parentViewGroup = this.mDataUsageLabelParent.getParentViewGroup();
            if (parentViewGroup != null) {
                parentViewGroup.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup, 1));
            }
        }
        DataUsageLabelView dataUsageLabelView = this.mLabelView;
        if (dataUsageLabelView == null) {
            return;
        }
        dataUsageLabelView.setTextColor(this.mContext.getColor(R.color.sec_qs_security_footer_tint_color));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLabelVisibility(boolean z) {
        boolean z2;
        boolean z3 = QpRune.QUICK_DATA_USAGE_LABEL;
        DataUsageLabelParent dataUsageLabelParent = this.mDataUsageLabelParent;
        boolean z4 = true;
        if (z3) {
            dataUsageLabelParent.getClass();
            if (QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || SecPanelSplitHelper.isEnabled() || dataUsageLabelParent.mOnKeyguardStateSupplier.getAsBoolean() || dataUsageLabelParent.mExpansionHeightSupplier.getAsDouble() > dataUsageLabelParent.mMinExpansionHeightSupplier.getAsInt() || dataUsageLabelParent.mFullyExpandedSupplier.getAsBoolean()) {
                z2 = false;
            } else {
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.mQuickStarHelper.mSlimIndicatorViewMediator;
                if (!slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected || slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled != 1) {
                    z2 = true;
                }
            }
        }
        boolean z5 = this.mPreviousVisible;
        if (z5 != z2 || z) {
            if (DEBUG && z5 != z2) {
                StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updateLabelVisibility(forceUpdate:", ") preV:", z);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mPreviousVisible, " >> newV:", z2, ", isFadingAnimationRunning()");
                if (!this.mIsFadingIn && !this.mIsFadingOut) {
                    z4 = false;
                }
                ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, z4, "DataUsageLabelManager");
            }
            if ((!this.mIsFadingIn && !this.mIsFadingOut) || z) {
                dataUsageLabelParent.getParentViewGroup().setVisibility(z2 ? 0 : 8);
            }
            this.mPreviousVisible = z2;
        }
    }

    public final void updateNavBarHeight(int i) {
        ViewGroup parentViewGroup = this.mDataUsageLabelParent.getParentViewGroup();
        if (parentViewGroup == null) {
            return;
        }
        int i2 = this.mInsetNavigationBarBottomHeight;
        NavSettingsHelper navSettingsHelper = this.mNavSettingsHelper;
        if (i2 != i || navSettingsHelper.updateSettingsAndCheckChanges()) {
            StringBuilder sb = new StringBuilder("updateNavBarHeight(");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.mInsetNavigationBarBottomHeight, " >> ", i, ") ");
            sb.append(navSettingsHelper.getDumpText());
            Log.d("DataUsageLabelManager", sb.toString());
            this.mInsetNavigationBarBottomHeight = i;
            parentViewGroup.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup, 0));
            ViewGroup parentViewGroup2 = this.mDataUsageLabelParent.getParentViewGroup();
            if (parentViewGroup2 != null) {
                parentViewGroup2.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup2, 1));
            }
        }
    }
}
