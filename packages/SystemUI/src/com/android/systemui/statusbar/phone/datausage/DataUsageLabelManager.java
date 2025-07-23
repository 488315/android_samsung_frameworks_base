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
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda12;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda13;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda15;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.function.DoubleSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            boolean isNavigationBarHideKeyboardButtonEnabled = this.mSettingsHelper.isNavigationBarHideKeyboardButtonEnabled();
            this.IsNavigationBarHideKeyboardButtonEnabled = isNavigationBarHideKeyboardButtonEnabled;
            return (this.IsNavigationBarGestureProtectionEnabled == z && this.IsNavigationBarGestureHintEnabled == z2 && isNavigationBarHideKeyboardButtonEnabled == z3) ? false : true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int i = 1;
            notificationPanelViewController.mDataUsageLabelParent = new DataUsageLabelParent(new NotificationPanelViewController$$ExternalSyntheticLambda12(notificationPanelViewController, 2), new NotificationPanelViewController$$ExternalSyntheticLambda13(notificationPanelViewController, i), new DoubleSupplier() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda32
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    return NotificationPanelViewController.this.mQsController.mExpansionHeight;
                }
            }, new NotificationPanelViewController$$ExternalSyntheticLambda15(notificationPanelViewController, i), new NotificationPanelViewController$$ExternalSyntheticLambda13(notificationPanelViewController, 2), new NotificationPanelViewController$$ExternalSyntheticLambda15(notificationPanelViewController, 0));
        }
        DataUsageLabelParent dataUsageLabelParent = notificationPanelViewController.mDataUsageLabelParent;
        this.mDataUsageLabelParent = dataUsageLabelParent;
        this.mContext = dataUsageLabelParent.getViewContext();
        this.mNavSettingsHelper = new NavSettingsHelper();
        this.mQuickStarHelper = new QuickStarHelper(slimIndicatorViewMediator);
    }

    public final void animateLabelAlpha(View view, boolean z) {
        if (view == null || view.animate() == null) {
            return;
        }
        view.animate().alpha(z ? 1.0f : 0.0f).setDuration(150L).setStartDelay(z ? 150L : 0L).setInterpolator(new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f)).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.1
            /* JADX WARN: Code restructure failed: missing block: B:8:0x0028, code lost:
            
                if (java.lang.Float.compare(0.0f, r8) != 0) goto L12;
             */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onAnimationUpdate(android.animation.ValueAnimator r8) {
                /*
                    r7 = this;
                    float r8 = r8.getAnimatedFraction()
                    com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager r0 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.this
                    float r0 = r0.mPrvAlpha
                    r1 = 1065353216(0x3f800000, float:1.0)
                    int r2 = java.lang.Float.compare(r1, r0)
                    r3 = 0
                    r4 = 1
                    if (r2 == 0) goto L2a
                    int r0 = java.lang.Float.compare(r3, r0)
                    if (r0 != 0) goto L19
                    goto L2a
                L19:
                    com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager r0 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.this
                    r0.getClass()
                    int r0 = java.lang.Float.compare(r1, r8)
                    if (r0 == 0) goto L2a
                    int r0 = java.lang.Float.compare(r3, r8)
                    if (r0 != 0) goto L2f
                L2a:
                    com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager r0 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.this
                    r0.updateLabelVisibility(r4)
                L2f:
                    com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager r0 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.this
                    float r2 = r0.mPrvAlpha
                    int r5 = java.lang.Float.compare(r2, r8)
                    r6 = 0
                    if (r5 == 0) goto L57
                    int r1 = java.lang.Float.compare(r1, r8)
                    if (r1 == 0) goto L57
                    int r1 = java.lang.Float.compare(r3, r8)
                    if (r1 != 0) goto L47
                    goto L57
                L47:
                    int r1 = java.lang.Float.compare(r2, r8)
                    if (r1 <= 0) goto L52
                    r0.mIsFadingIn = r6
                    r0.mIsFadingOut = r4
                    goto L5b
                L52:
                    r0.mIsFadingIn = r4
                    r0.mIsFadingOut = r6
                    goto L5b
                L57:
                    r0.mIsFadingIn = r6
                    r0.mIsFadingOut = r6
                L5b:
                    int r0 = java.lang.Float.compare(r2, r8)
                    if (r0 != 0) goto L62
                    return
                L62:
                    com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager r7 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.this
                    r7.mPrvAlpha = r8
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.AnonymousClass1.onAnimationUpdate(android.animation.ValueAnimator):void");
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

    /* JADX WARN: Removed duplicated region for block: B:40:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLabelVisibility(boolean r9) {
        /*
            r8 = this;
            boolean r0 = com.android.systemui.QpRune.QUICK_DATA_USAGE_LABEL
            com.android.systemui.statusbar.phone.datausage.DataUsageLabelParent r1 = r8.mDataUsageLabelParent
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L50
            r1.getClass()
            boolean r0 = com.android.systemui.qs.animator.QsAnimatorState.isDetailOpening
            if (r0 != 0) goto L50
            boolean r0 = com.android.systemui.qs.animator.QsAnimatorState.isDetailShowing
            if (r0 == 0) goto L14
            goto L50
        L14:
            boolean r0 = com.android.systemui.shade.SecPanelSplitHelper.isEnabled()
            if (r0 != 0) goto L50
            java.util.function.BooleanSupplier r0 = r1.mOnKeyguardStateSupplier
            boolean r0 = r0.getAsBoolean()
            if (r0 != 0) goto L50
            java.util.function.DoubleSupplier r0 = r1.mExpansionHeightSupplier
            double r4 = r0.getAsDouble()
            java.util.function.IntSupplier r0 = r1.mMinExpansionHeightSupplier
            int r0 = r0.getAsInt()
            double r6 = (double) r0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 > 0) goto L50
            java.util.function.BooleanSupplier r0 = r1.mFullyExpandedSupplier
            boolean r0 = r0.getAsBoolean()
            if (r0 != 0) goto L50
            com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$QuickStarHelper r0 = r8.mQuickStarHelper
            com.android.systemui.slimindicator.SlimIndicatorViewMediator r0 = r0.mSlimIndicatorViewMediator
            com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl r0 = (com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl) r0
            com.android.systemui.slimindicator.SlimIndicatorPluginMediator r4 = r0.mPluginMediator
            boolean r4 = r4.mIsSPluginConnected
            if (r4 == 0) goto L4e
            com.android.systemui.slimindicator.SlimIndicatorCarrierCrew r0 = r0.mCarrierCrew
            int r0 = r0.mIsPanelCarrierDisabled
            if (r0 != r2) goto L4e
            goto L50
        L4e:
            r0 = r2
            goto L51
        L50:
            r0 = r3
        L51:
            boolean r4 = r8.mPreviousVisible
            if (r4 != r0) goto L59
            if (r9 == 0) goto L58
            goto L59
        L58:
            return
        L59:
            boolean r5 = com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.DEBUG
            if (r5 == 0) goto L80
            if (r4 == r0) goto L80
            java.lang.String r4 = "updateLabelVisibility(forceUpdate:"
            java.lang.String r5 = ") preV:"
            java.lang.StringBuilder r4 = androidx.slice.widget.RowView$$ExternalSyntheticOutline0.m(r4, r5, r9)
            boolean r5 = r8.mPreviousVisible
            java.lang.String r6 = " >> newV:"
            java.lang.String r7 = ", isFadingAnimationRunning()"
            com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(r4, r5, r6, r0, r7)
            boolean r5 = r8.mIsFadingIn
            if (r5 != 0) goto L7b
            boolean r5 = r8.mIsFadingOut
            if (r5 == 0) goto L7a
            goto L7b
        L7a:
            r2 = r3
        L7b:
            java.lang.String r5 = "DataUsageLabelManager"
            androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(r4, r2, r5)
        L80:
            boolean r2 = r8.mIsFadingIn
            if (r2 != 0) goto L88
            boolean r2 = r8.mIsFadingOut
            if (r2 == 0) goto L8a
        L88:
            if (r9 == 0) goto L96
        L8a:
            android.view.ViewGroup r9 = r1.getParentViewGroup()
            if (r0 == 0) goto L91
            goto L93
        L91:
            r3 = 8
        L93:
            r9.setVisibility(r3)
        L96:
            r8.mPreviousVisible = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager.updateLabelVisibility(boolean):void");
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
