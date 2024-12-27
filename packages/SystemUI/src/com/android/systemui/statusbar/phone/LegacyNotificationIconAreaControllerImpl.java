package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.collection.ArrayMap;
import androidx.collection.IndexBasedArrayIterator;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class LegacyNotificationIconAreaControllerImpl implements NotificationIconAreaController, DarkIconDispatcher.DarkReceiver, StatusBarStateController.StateListener, NotificationWakeUpCoordinator.WakeUpListener, DemoMode {
    public boolean mAnimationsEnabled;
    public final Optional mBubblesOptional;
    public final KeyguardBypassController mBypassController;
    public final Context mContext;
    public final ContrastColorUtil mContrastColorUtil;
    public final DozeParameters mDozeParameters;
    public int mIconHPadding;
    public int mIconSize;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public boolean mIsNotificationDotOnlyOn;
    public int mKeyguardNotifIconTint;
    public NotificationIconContainer mKeyguardStatusBarIcons;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final NotificationMediaManager mMediaManager;
    public final View mNotificationIconArea;
    public final NotificationIconContainer mNotificationIcons;
    public final OngoingCallController mOngoingCallController;
    public int mPreviousStatusBarNotificationStyle;
    SettingsHelper.OnChangedCallback mSettingsCallback;
    final NotificationListener.NotificationSettingsListener mSettingsListener;
    public NotificationIconContainer mShelfIcons;
    public int mStatusBarNotificationStyle;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowController mStatusBarWindowController;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 mUpdateStatusBarIcons = new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4(this, 0);
    public int mIconTint = -1;
    public List mNotificationEntries = List.of();
    public final ArrayList mTintAreas = new ArrayList();
    public boolean mShowLowPriority = true;

    /* renamed from: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 implements NotificationListener.NotificationSettingsListener {
        public AnonymousClass1() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$3, reason: invalid class name */
    class AnonymousClass3 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass3() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            new Handler(Looper.getMainLooper()).post(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4(this, 1));
        }
    }

    public LegacyNotificationIconAreaControllerImpl(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, StatusBarStateController statusBarStateController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, NotificationMediaManager notificationMediaManager, NotificationListener notificationListener, DozeParameters dozeParameters, SectionStyleProvider sectionStyleProvider, Optional<Bubbles> optional, DemoModeController demoModeController, DarkIconDispatcher darkIconDispatcher, FeatureFlags featureFlags, StatusBarWindowController statusBarWindowController, ScreenOffAnimationController screenOffAnimationController, IndicatorScaleGardener indicatorScaleGardener, OngoingCallController ongoingCallController) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mSettingsListener = anonymousClass1;
        this.mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl.2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                boolean equals = uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl = LegacyNotificationIconAreaControllerImpl.this;
                if (!equals) {
                    if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION))) {
                        legacyNotificationIconAreaControllerImpl.mIsNotificationDotOnlyOn = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsDot();
                        return;
                    }
                    return;
                }
                legacyNotificationIconAreaControllerImpl.mStatusBarNotificationStyle = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle();
                legacyNotificationIconAreaControllerImpl.setAnimationsEnabled(true);
                if (legacyNotificationIconAreaControllerImpl.mPreviousStatusBarNotificationStyle == 2 && legacyNotificationIconAreaControllerImpl.mStatusBarNotificationStyle == 0) {
                    legacyNotificationIconAreaControllerImpl.setAnimationsEnabled(false);
                }
                legacyNotificationIconAreaControllerImpl.mPreviousStatusBarNotificationStyle = legacyNotificationIconAreaControllerImpl.mStatusBarNotificationStyle;
                legacyNotificationIconAreaControllerImpl.updateStatusBarIcons();
                legacyNotificationIconAreaControllerImpl.mNotificationIcons.resetViewStates();
                legacyNotificationIconAreaControllerImpl.mNotificationIcons.calculateIconXTranslations();
                legacyNotificationIconAreaControllerImpl.mNotificationIcons.applyIconStates();
                NotificationIconContainer notificationIconContainer = legacyNotificationIconAreaControllerImpl.mKeyguardStatusBarIcons;
                if (notificationIconContainer != null) {
                    notificationIconContainer.resetViewStates();
                    legacyNotificationIconAreaControllerImpl.mKeyguardStatusBarIcons.calculateIconXTranslations();
                    legacyNotificationIconAreaControllerImpl.mKeyguardStatusBarIcons.applyIconStates();
                }
                legacyNotificationIconAreaControllerImpl.applyNotificationIconsTint();
            }
        };
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mKeyguardUpdateMonitorCallback = anonymousClass3;
        this.mContrastColorUtil = ContrastColorUtil.getInstance(context);
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(this);
        this.mDozeParameters = dozeParameters;
        notificationWakeUpCoordinator.wakeUpListeners.add(this);
        this.mBypassController = keyguardBypassController;
        this.mBubblesOptional = optional;
        demoModeController.addCallback((DemoMode) this);
        this.mStatusBarWindowController = statusBarWindowController;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mOngoingCallController = ongoingCallController;
        notificationListener.getClass();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationListener.mSettingsListeners.add(anonymousClass1);
        reloadDimens(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.notification_icon_area, (ViewGroup) null);
        this.mNotificationIconArea = inflate;
        this.mNotificationIcons = (NotificationIconContainer) inflate.findViewById(R.id.notificationIcons);
        Utils.getColorAttrDefaultColor(context, R.attr.wallpaperTextColor, -1);
        darkIconDispatcher.addDarkReceiver(this);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION));
        this.mSettingsCallback.onChanged(Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
        this.mSettingsCallback.onChanged(Settings.System.getUriFor(SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION));
        this.mPreviousStatusBarNotificationStyle = this.mStatusBarNotificationStyle;
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(anonymousClass3);
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
    }

    public final void applyKeyguardNotifIconsTint() {
        if (this.mKeyguardStatusBarIcons != null) {
            for (int i = 0; i < this.mKeyguardStatusBarIcons.getChildCount(); i++) {
                StatusBarIconView statusBarIconView = (StatusBarIconView) this.mKeyguardStatusBarIcons.getChildAt(i);
                if (statusBarIconView.getWidth() != 0) {
                    updateTintForIcon(statusBarIconView, this.mKeyguardNotifIconTint);
                } else {
                    statusBarIconView.mLayoutRunnable = new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda0(this, statusBarIconView, 1);
                }
            }
        }
    }

    public final void applyNotificationIconsTint() {
        for (int i = 0; i < this.mNotificationIcons.getChildCount(); i++) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) this.mNotificationIcons.getChildAt(i);
            if (statusBarIconView.getWidth() != 0) {
                updateTintForIcon(statusBarIconView, this.mIconTint);
            } else {
                statusBarIconView.mLayoutRunnable = new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda0(this, statusBarIconView, 0);
            }
        }
        applyKeyguardNotifIconsTint();
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("notifications");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        if (this.mNotificationIconArea != null) {
            this.mNotificationIconArea.setVisibility("false".equals(bundle.getString("visible")) ? 4 : 0);
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final View getNotificationInnerAreaView() {
        return this.mNotificationIconArea;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        this.mTintAreas.clear();
        this.mTintAreas.addAll(arrayList);
        if (DarkIconDispatcher.isInAreas(arrayList, this.mNotificationIconArea)) {
            this.mIconTint = i;
        }
        applyNotificationIconsTint();
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        View view = this.mNotificationIconArea;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void onDensityOrFontScaleChanged(Context context) {
        reloadDimens(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((this.mIconHPadding * 2) + this.mIconSize, this.mStatusBarWindowController.mBarHeight);
        for (int i = 0; i < this.mNotificationIcons.getChildCount(); i++) {
            this.mNotificationIcons.getChildAt(i).setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
    public final void onFullyHiddenChanged(boolean z) {
        if (this.mBypassController.getBypassEnabled()) {
            return;
        }
        DozeParameters dozeParameters = this.mDozeParameters;
        if (dozeParameters.getAlwaysOn()) {
            dozeParameters.getDisplayNeedsBlanking();
        }
        Flags.FEATURE_FLAGS.getClass();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        boolean z = false;
        boolean z2 = this.mStatusBarStateController.getState() == 0;
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        if (this.mAnimationsEnabled && z2) {
            z = true;
        }
        notificationIconContainer.setAnimationsEnabled(z);
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void onThemeChanged() {
        Utils.getColorAttrDefaultColor(this.mContext, R.attr.wallpaperTextColor, -1);
    }

    public final void reloadDimens(Context context) {
        Resources resources = context.getResources();
        this.mIconSize = resources.getDimensionPixelSize(R.dimen.notification_icon_view_width);
        this.mIconSize = (int) (this.mIconSize * this.mIndicatorScaleGardener.getLatestScaleModel(this.mContext).ratio);
        this.mIconHPadding = resources.getDimensionPixelSize(R.dimen.status_bar_icon_horizontal_margin);
        resources.getDimensionPixelSize(R.dimen.shelf_appear_translation);
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
        boolean z2 = false;
        boolean z3 = this.mStatusBarStateController.getState() == 0;
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        if (this.mAnimationsEnabled && z3) {
            z2 = true;
        }
        notificationIconContainer.setAnimationsEnabled(z2);
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setIsolatedIconLocation(Rect rect, boolean z) {
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        if (!z) {
            notificationIconContainer.getClass();
            return;
        }
        notificationIconContainer.resetViewStates();
        notificationIconContainer.calculateIconXTranslations();
        notificationIconContainer.applyIconStates();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setKeyguardNotifIcon(NotificationIconContainer notificationIconContainer) {
        this.mKeyguardStatusBarIcons = notificationIconContainer;
        notificationIconContainer.mOnKeyguardStatusBar = true;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setKeyguardNotifIconTint(int i) {
        this.mKeyguardNotifIconTint = i;
        applyKeyguardNotifIconsTint();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setShelfIcons(NotificationIconContainer notificationIconContainer) {
        this.mShelfIcons = notificationIconContainer;
    }

    public boolean shouldShouldLowPriorityIcons() {
        return this.mShowLowPriority;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void showIconIsolated(StatusBarIconView statusBarIconView, boolean z) {
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        notificationIconContainer.getClass();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (z) {
            notificationIconContainer.mIsolatedIconForAnimation = statusBarIconView != null ? statusBarIconView : notificationIconContainer.mIsolatedIcon;
        }
        notificationIconContainer.mIsolatedIcon = statusBarIconView;
        notificationIconContainer.resetViewStates();
        notificationIconContainer.calculateIconXTranslations();
        notificationIconContainer.applyIconStates();
    }

    public final void updateIconsForLayout(Function function, NotificationIconContainer notificationIconContainer, boolean z, boolean z2, boolean z3, boolean z4) {
        int i;
        ArrayList arrayList = new ArrayList(this.mNotificationEntries.size());
        for (int i2 = 0; i2 < this.mNotificationEntries.size(); i2++) {
            NotificationEntry representativeEntry = ((ListEntry) this.mNotificationEntries.get(i2)).getRepresentativeEntry();
            if (representativeEntry != null && representativeEntry.row != null && ((!representativeEntry.mIsReaded || !z4) && ((!representativeEntry.mRanking.isAmbient() || z) && ((z2 || representativeEntry.mRanking.getImportance() >= 3) && ((!representativeEntry.isRowDismissed() || !z3) && ((z || !representativeEntry.shouldSuppressVisualEffect(32)) && ((!this.mBubblesOptional.isPresent() || !((BubbleController.BubblesImpl) ((Bubbles) this.mBubblesOptional.get())).isBubbleExpanded(representativeEntry.mKey)) && ((!this.mOngoingCallController.hasOngoingCall() || ((i = representativeEntry.mSbn.getNotification().extras.getInt("android.callType", -1)) != 2 && i != 1 && i != 3)) && (!representativeEntry.isOngoingAcitivty() || !representativeEntry.isPromotedState()))))))))) {
                StatusBarIconView statusBarIconView = (StatusBarIconView) function.apply(representativeEntry);
                if (statusBarIconView != null) {
                    arrayList.add(statusBarIconView);
                }
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < notificationIconContainer.getChildCount(); i3++) {
            View childAt = notificationIconContainer.getChildAt(i3);
            if ((childAt instanceof StatusBarIconView) && !arrayList.contains(childAt)) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) childAt;
                String groupKey = statusBarIconView2.mNotification.getGroupKey();
                int i4 = 0;
                boolean z5 = false;
                while (true) {
                    if (i4 >= arrayList.size()) {
                        break;
                    }
                    StatusBarIconView statusBarIconView3 = (StatusBarIconView) arrayList.get(i4);
                    if (statusBarIconView3.mIcon.icon.sameAs(statusBarIconView2.mIcon.icon) && statusBarIconView3.mNotification.getGroupKey().equals(groupKey)) {
                        if (z5) {
                            z5 = false;
                            break;
                        }
                        z5 = true;
                    }
                    i4++;
                }
                if (z5) {
                    ArrayList arrayList3 = (ArrayList) arrayMap.get(groupKey);
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                        arrayMap.put(groupKey, arrayList3);
                    }
                    arrayList3.add(statusBarIconView2.mIcon);
                }
                arrayList2.add(statusBarIconView2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        Iterator it = ((ArrayMap.KeySet) arrayMap.keySet()).iterator();
        while (true) {
            IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it;
            if (!indexBasedArrayIterator.hasNext()) {
                break;
            }
            String str = (String) indexBasedArrayIterator.next();
            if (((ArrayList) arrayMap.get(str)).size() != 1) {
                arrayList4.add(str);
            }
        }
        arrayMap.removeAll(arrayList4);
        int i5 = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationIconContainer.mReplacingIconsLegacy = arrayMap;
        int size = arrayList2.size();
        for (int i6 = 0; i6 < size; i6++) {
            notificationIconContainer.removeView((View) arrayList2.get(i6));
        }
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((this.mIconHPadding * 2) + this.mIconSize, this.mStatusBarWindowController.mBarHeight);
        for (int i7 = 0; i7 < arrayList.size(); i7++) {
            StatusBarIconView statusBarIconView4 = (StatusBarIconView) arrayList.get(i7);
            notificationIconContainer.removeTransientView(statusBarIconView4);
            if (statusBarIconView4.getParent() == null) {
                if (z3) {
                    statusBarIconView4.mOnDismissListener = this.mUpdateStatusBarIcons;
                }
                notificationIconContainer.addView(statusBarIconView4, i7, layoutParams);
            }
        }
        notificationIconContainer.mChangingViewPositions = true;
        int min = Math.min(notificationIconContainer.getChildCount(), arrayList.size());
        for (int i8 = 0; i8 < min; i8++) {
            View childAt2 = notificationIconContainer.getChildAt(i8);
            View view = (StatusBarIconView) arrayList.get(i8);
            if (childAt2 != view) {
                notificationIconContainer.removeView(view);
                notificationIconContainer.addView(view, i8);
            }
        }
        notificationIconContainer.mChangingViewPositions = false;
        int i9 = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        notificationIconContainer.mReplacingIconsLegacy = null;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateNotificationIcons(List list) {
        this.mNotificationEntries = list;
        Trace.beginSection("NotificationIconAreaController.updateNotificationIcons");
        updateStatusBarIcons();
        NotificationIconContainer notificationIconContainer = this.mShelfIcons;
        if (notificationIconContainer != null) {
            updateIconsForLayout(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(0), notificationIconContainer, true, true, false, false);
        }
        applyNotificationIconsTint();
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateStatusBarIcons() {
        if (this.mStatusBarNotificationStyle == 2) {
            this.mNotificationIcons.setVisibility(8);
        } else {
            this.mNotificationIcons.setVisibility(0);
            this.mNotificationIcons.setAlpha(1.0f);
            updateIconsForLayout(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(1), this.mNotificationIcons, false, this.mShowLowPriority, true, ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() == 0);
        }
        NotificationIconContainer notificationIconContainer = this.mKeyguardStatusBarIcons;
        if (notificationIconContainer != null) {
            if (!this.mIsNotificationDotOnlyOn) {
                notificationIconContainer.setVisibility(8);
            } else {
                notificationIconContainer.setVisibility(0);
                updateIconsForLayout(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(2), this.mKeyguardStatusBarIcons, false, this.mShowLowPriority, true, true);
            }
        }
    }

    public final void updateTintForIcon(StatusBarIconView statusBarIconView, int i) {
        Boolean.TRUE.equals(statusBarIconView.getTag(R.id.icon_is_pre_L));
        statusBarIconView.setStaticDrawableColor(NotificationUtils.isGrayscale(statusBarIconView, this.mContrastColorUtil) ? DarkIconDispatcher.getTint(this.mTintAreas, statusBarIconView, i) : 0);
        statusBarIconView.setDecorColor(i);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
    public final void onPulseExpansionAmountChanged() {
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setupAodIcons() {
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateAodNotificationIcons() {
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
    }
}
