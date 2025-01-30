package com.android.systemui.statusbar.phone.datausage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda15;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda17;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataUsageLabelManager {
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
    public boolean mPreviousVisibleWithoutAnimation = false;
    public float mPrvAlpha = -1.0f;
    public boolean mLabelAlphaAnimStarted = true;
    public int mInsetNavigationBarBottomHeight = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavSettingsHelper implements SettingsHelper.OnChangedCallback {
        public boolean IsNavigationBarGestureHintEnabled;
        public boolean IsNavigationBarGestureProtectionEnabled;
        public boolean IsNavigationBarHideKeyboardButtonEnabled;
        public final Uri[] SETTINGS_VALUE_LIST = {Settings.Secure.getUriFor("game_double_swipe_enable"), Settings.Global.getUriFor("navigation_bar_gesture_hint"), Settings.Global.getUriFor("navigation_bar_button_to_hide_keyboard")};
        public final SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);

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

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            DataUsageLabelManager dataUsageLabelManager = DataUsageLabelManager.this;
            dataUsageLabelManager.updateNavBarHeight(dataUsageLabelManager.mInsetNavigationBarBottomHeight);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        final int i = 0;
        final int i2 = 1;
        if (notificationPanelViewController.mDataUsageLabelParent == null) {
            notificationPanelViewController.mDataUsageLabelParent = new DataUsageLabelParent(new NotificationPanelViewController$$ExternalSyntheticLambda15(notificationPanelViewController, 1), new NotificationPanelViewController$$ExternalSyntheticLambda17(notificationPanelViewController, i), new DoubleSupplier() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda18
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    return NotificationPanelViewController.this.mQsController.mExpansionHeight;
                }
            }, new IntSupplier() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda19
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int i3 = i;
                    NotificationPanelViewController notificationPanelViewController2 = notificationPanelViewController;
                    switch (i3) {
                        case 0:
                            return notificationPanelViewController2.mQsController.mMinExpansionHeight;
                        default:
                            return notificationPanelViewController2.getMaxPanelHeight();
                    }
                }
            }, new NotificationPanelViewController$$ExternalSyntheticLambda17(notificationPanelViewController, 5), new IntSupplier() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda19
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int i3 = i2;
                    NotificationPanelViewController notificationPanelViewController2 = notificationPanelViewController;
                    switch (i3) {
                        case 0:
                            return notificationPanelViewController2.mQsController.mMinExpansionHeight;
                        default:
                            return notificationPanelViewController2.getMaxPanelHeight();
                    }
                }
            });
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
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
            
                if ((java.lang.Float.compare(1.0f, r8) == 0 || java.lang.Float.compare(0.0f, r8) == 0) != false) goto L18;
             */
            /* JADX WARN: Removed duplicated region for block: B:26:0x0072  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0075  */
            /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f = DataUsageLabelManager.this.mPrvAlpha;
                if (!(Float.compare(1.0f, f) == 0 || Float.compare(0.0f, f) == 0)) {
                    DataUsageLabelManager.this.getClass();
                }
                DataUsageLabelManager.this.updateLabelVisibility(true);
                DataUsageLabelManager dataUsageLabelManager = DataUsageLabelManager.this;
                float f2 = dataUsageLabelManager.mPrvAlpha;
                if (Float.compare(f2, animatedFraction) != 0) {
                    if (!(Float.compare(1.0f, animatedFraction) == 0 || Float.compare(0.0f, animatedFraction) == 0)) {
                        if (Float.compare(f2, animatedFraction) > 0) {
                            dataUsageLabelManager.mIsFadingIn = false;
                            dataUsageLabelManager.mIsFadingOut = true;
                        } else {
                            dataUsageLabelManager.mIsFadingIn = true;
                            dataUsageLabelManager.mIsFadingOut = false;
                        }
                        if (Float.compare(f2, animatedFraction) == 0) {
                            DataUsageLabelManager.this.mPrvAlpha = animatedFraction;
                            return;
                        }
                        return;
                    }
                }
                dataUsageLabelManager.mIsFadingIn = false;
                dataUsageLabelManager.mIsFadingOut = false;
                if (Float.compare(f2, animatedFraction) == 0) {
                }
            }
        }).start();
    }

    public final void onPanelConfigurationChanged(Configuration configuration) {
        int i = configuration.orientation;
        int i2 = this.mLastOrientation;
        if (i != i2 || configuration.densityDpi != this.mLastDensityDpi || this.mLastSemMobileKeyboardCovered != configuration.semMobileKeyboardCovered) {
            int i3 = 1;
            if (i != i2) {
                this.mLastOrientation = i;
                updateLabelVisibility(true);
            }
            this.mLastDensityDpi = configuration.densityDpi;
            this.mLastSemMobileKeyboardCovered = configuration.semMobileKeyboardCovered;
            ViewGroup parentViewGroup = this.mDataUsageLabelParent.getParentViewGroup();
            if (parentViewGroup != null) {
                parentViewGroup.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup, i3));
            }
        }
        updateLabelViewColor();
    }

    public final void updateLabelViewColor() {
        int i;
        DataUsageLabelView dataUsageLabelView = this.mLabelView;
        if (dataUsageLabelView == null) {
            return;
        }
        if (QpRune.QUICK_TABLET) {
            i = -1627389953;
        } else {
            Context context = this.mContext;
            if (DeviceState.isOpenTheme(context)) {
                int color = context.getColor(R.color.sec_qs_header_tint_color);
                i = Color.argb(-1711604993, Color.red(color), Color.green(color), Color.blue(color));
            } else {
                i = -1711604993;
            }
        }
        dataUsageLabelView.setTextColor(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLabelVisibility(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = QpRune.PANEL_DATA_USAGE_LABEL;
        boolean z6 = true;
        DataUsageLabelParent dataUsageLabelParent = this.mDataUsageLabelParent;
        if (z5) {
            if (((dataUsageLabelParent.mOnKeyguardStateSupplier.getAsBoolean() || dataUsageLabelParent.mExpansionHeightSupplier.getAsDouble() > ((double) dataUsageLabelParent.mMinExpansionHeightSupplier.getAsInt()) || dataUsageLabelParent.mFullyExpandedSupplier.getAsBoolean()) ? false : true) && this.mLastOrientation != 2) {
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.mQuickStarHelper.mSlimIndicatorViewMediator;
                if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                    if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled == 1) {
                        z4 = true;
                        if (!z4) {
                            z2 = true;
                            z3 = this.mPreviousVisible;
                            if (z3 == z2 || z) {
                                if (DEBUG && z3 != z2) {
                                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateLabelVisibility(forceUpdate:", z, ") preV:");
                                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m, this.mPreviousVisible, " >> newV:", z2, ", isFadingAnimationRunning()");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(m49m, !this.mIsFadingIn || this.mIsFadingOut, "DataUsageLabelManager");
                                }
                                if (!this.mIsFadingIn && !this.mIsFadingOut) {
                                    z6 = false;
                                }
                                if (z6 || z) {
                                    dataUsageLabelParent.getParentViewGroup().setVisibility(z2 ? 0 : 8);
                                }
                                this.mPreviousVisible = z2;
                            }
                            return;
                        }
                    }
                }
                z4 = false;
                if (!z4) {
                }
            }
        }
        z2 = false;
        z3 = this.mPreviousVisible;
        if (z3 == z2) {
        }
        if (DEBUG) {
            StringBuilder m49m2 = RowView$$ExternalSyntheticOutline0.m49m("updateLabelVisibility(forceUpdate:", z, ") preV:");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m2, this.mPreviousVisible, " >> newV:", z2, ", isFadingAnimationRunning()");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(m49m2, !this.mIsFadingIn || this.mIsFadingOut, "DataUsageLabelManager");
        }
        if (!this.mIsFadingIn) {
            z6 = false;
        }
        if (z6) {
        }
        dataUsageLabelParent.getParentViewGroup().setVisibility(z2 ? 0 : 8);
        this.mPreviousVisible = z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateNavBarHeight(int i) {
        boolean z;
        DataUsageLabelParent dataUsageLabelParent = this.mDataUsageLabelParent;
        ViewGroup parentViewGroup = dataUsageLabelParent.getParentViewGroup();
        if (parentViewGroup == null) {
            return;
        }
        int i2 = this.mInsetNavigationBarBottomHeight;
        int i3 = 0;
        int i4 = 1;
        NavSettingsHelper navSettingsHelper = this.mNavSettingsHelper;
        if (i2 == i) {
            SettingsHelper settingsHelper = navSettingsHelper.mSettingsHelper;
            if (settingsHelper != null) {
                boolean z2 = navSettingsHelper.IsNavigationBarGestureProtectionEnabled;
                boolean z3 = navSettingsHelper.IsNavigationBarGestureHintEnabled;
                boolean z4 = navSettingsHelper.IsNavigationBarHideKeyboardButtonEnabled;
                navSettingsHelper.IsNavigationBarGestureProtectionEnabled = BasicRune.NAVBAR_REMOTEVIEW && settingsHelper.mItemLists.get("game_double_swipe_enable").getIntValue() != 0;
                navSettingsHelper.IsNavigationBarGestureHintEnabled = settingsHelper.isNavigationBarGestureHintEnabled();
                boolean isNavigationBarHideKeyboardButtonEnabled = settingsHelper.isNavigationBarHideKeyboardButtonEnabled();
                navSettingsHelper.IsNavigationBarHideKeyboardButtonEnabled = isNavigationBarHideKeyboardButtonEnabled;
                if (navSettingsHelper.IsNavigationBarGestureProtectionEnabled != z2 || navSettingsHelper.IsNavigationBarGestureHintEnabled != z3 || isNavigationBarHideKeyboardButtonEnabled != z4) {
                    z = true;
                    if (!z) {
                        return;
                    }
                }
            }
            z = false;
            if (!z) {
            }
        }
        StringBuilder sb = new StringBuilder("updateNavBarHeight(");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mInsetNavigationBarBottomHeight, " >> ", i, ") ");
        sb.append(navSettingsHelper.getDumpText());
        Log.d("DataUsageLabelManager", sb.toString());
        this.mInsetNavigationBarBottomHeight = i;
        parentViewGroup.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup, i3));
        ViewGroup parentViewGroup2 = dataUsageLabelParent.getParentViewGroup();
        if (parentViewGroup2 != null) {
            parentViewGroup2.post(new DataUsageLabelManager$$ExternalSyntheticLambda0(this, parentViewGroup2, i4));
        }
    }
}
