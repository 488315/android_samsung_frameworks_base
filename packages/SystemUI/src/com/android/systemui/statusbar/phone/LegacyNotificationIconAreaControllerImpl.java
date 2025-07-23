package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.headsup.shared.StatusBarNoHunBehavior;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.bubbles.Bubbles;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LegacyNotificationIconAreaControllerImpl implements NotificationIconAreaController, DarkIconDispatcher.DarkReceiver, StatusBarStateController.StateListener, NotificationWakeUpCoordinator.WakeUpListener, DemoMode {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    SettingsHelper.OnChangedCallback mSettingsCallback;
    final NotificationListener.NotificationSettingsListener mSettingsListener;
    public SecShelfNotificationIconContainer mShelfIcons;
    public int mStatusBarNotificationStyle;
    public final StatusBarStateController mStatusBarStateController;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 mUpdateStatusBarIcons = new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4(this, 0);
    public int mIconTint = -1;
    public List mNotificationEntries = Collections.EMPTY_LIST;
    public final ArrayList mTintAreas = new ArrayList();
    public boolean mShowLowPriority = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements NotificationListener.NotificationSettingsListener {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$3, reason: invalid class name */
    class AnonymousClass3 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass3() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            new Handler(Looper.getMainLooper()).post(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4(this, 1));
        }
    }

    public LegacyNotificationIconAreaControllerImpl(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, StatusBarStateController statusBarStateController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, NotificationMediaManager notificationMediaManager, NotificationListener notificationListener, DozeParameters dozeParameters, SectionStyleProvider sectionStyleProvider, Optional<Bubbles> optional, DemoModeController demoModeController, DarkIconDispatcher darkIconDispatcher, FeatureFlags featureFlags, ScreenOffAnimationController screenOffAnimationController, IndicatorScaleGardener indicatorScaleGardener, OngoingCallController ongoingCallController) {
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
        this.mMediaManager = notificationMediaManager;
        this.mDozeParameters = dozeParameters;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        notificationWakeUpCoordinator.wakeUpListeners.add(this);
        this.mBypassController = keyguardBypassController;
        this.mBubblesOptional = optional;
        demoModeController.addCallback((DemoMode) this);
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
        if (this.mShelfIcons != null) {
            for (int i2 = 0; i2 < this.mShelfIcons.getChildCount(); i2++) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) this.mShelfIcons.getChildAt(i2);
                if (statusBarIconView2.getWidth() != 0) {
                    updateTintForIcon(statusBarIconView2, this.mShelfIcons.mShelfIconColor);
                } else {
                    statusBarIconView2.mLayoutRunnable = new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda0(this, statusBarIconView2, 2);
                }
            }
        }
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
    public final void dump(PrintWriter printWriter) {
        String str;
        printWriter.println("NotificationIconAreaController state:");
        int childCount = this.mNotificationIcons.getChildCount();
        printWriter.println("  noti icons: " + childCount + String.format("  tintColor=0x%08x", Integer.valueOf(this.mIconTint)));
        if (this.mStatusBarNotificationStyle != 0) {
            MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder(" statusbar notification style : "), this.mStatusBarNotificationStyle, printWriter);
        }
        for (int i = 0; i < childCount; i++) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) this.mNotificationIcons.getChildAt(i);
            boolean isGrayscale = NotificationUtils.isGrayscale(statusBarIconView, this.mContrastColorUtil);
            boolean isInAreas = DarkIconDispatcher.isInAreas(this.mTintAreas, statusBarIconView);
            printWriter.println("    [" + i + "] icon=" + statusBarIconView + " / getVisibleState " + statusBarIconView.mVisibleState + "/ getIconAppearAmount " + statusBarIconView.mIconAppearAmount + " / getDotAppearAmount " + statusBarIconView.mDotAppearAmount + " / getAlpha " + statusBarIconView.getAlpha() + " / getVisibility " + statusBarIconView.getVisibility() + " / getTranslationX " + statusBarIconView.getTranslationX());
            StringBuilder sb = new StringBuilder(" colorize=");
            sb.append(isGrayscale);
            sb.append(" isInAreas=");
            sb.append(isInAreas);
            sb.append(String.format("  color=0x%08x", Integer.valueOf(statusBarIconView.mDrawableColor)));
            printWriter.println(sb.toString());
            StatusBarIcon statusBarIcon = statusBarIconView.mIcon;
            if (statusBarIcon != null) {
                Icon icon = statusBarIcon.icon;
                String valueOf = String.valueOf(icon);
                if (icon != null && icon.getType() == 2) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(valueOf, " / ");
                    Context context = this.mContext;
                    String resPackage = icon.getResPackage();
                    int resId = icon.getResId();
                    if (resPackage != null) {
                        try {
                            context = context.createPackageContext(resPackage, 0);
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                    try {
                        str = context.getResources().getResourceName(resId);
                    } catch (Resources.NotFoundException unused2) {
                        str = "<name unknown>";
                    }
                    m.append(str);
                    ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, " iconStr=", m.toString());
                }
            }
        }
        printWriter.println("  ");
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final View getNotificationInnerAreaView() {
        return this.mNotificationIconArea;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final int getShowingIconCount() {
        return this.mNotificationIcons.getChildCount();
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
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((this.mIconHPadding * 2) + this.mIconSize, SystemBarUtils.getStatusBarHeight(this.mContext));
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
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        updateAnimations$1();
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
        updateAnimations$1();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setIsolatedIconLocation(Rect rect, boolean z) {
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        notificationIconContainer.getClass();
        int i = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (z) {
            notificationIconContainer.resetViewStates();
            notificationIconContainer.calculateIconXTranslations();
            notificationIconContainer.applyIconStates();
        }
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
    public final void setShelfIcons(SecShelfNotificationIconContainer secShelfNotificationIconContainer) {
        this.mShelfIcons = secShelfNotificationIconContainer;
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

    public final void updateAnimations$1() {
        boolean z = false;
        boolean z2 = this.mStatusBarStateController.getState() == 0;
        NotificationIconContainer notificationIconContainer = this.mNotificationIcons;
        if (this.mAnimationsEnabled && z2) {
            z = true;
        }
        notificationIconContainer.setAnimationsEnabled(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0054, code lost:
    
        if (r10 != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        if (r6.mIsReaded != false) goto L61;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIconsForLayout(java.util.function.Function r17, com.android.systemui.statusbar.phone.NotificationIconContainer r18, boolean r19, boolean r20, boolean r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl.updateIconsForLayout(java.util.function.Function, com.android.systemui.statusbar.phone.NotificationIconContainer, boolean, boolean, boolean, boolean):void");
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateNotificationIcons(List list) {
        LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl;
        this.mNotificationEntries = list;
        Trace.beginSection("NotificationIconAreaController.updateNotificationIcons");
        updateStatusBarIcons();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
        if (secShelfNotificationIconContainer == null) {
            legacyNotificationIconAreaControllerImpl = this;
        } else {
            legacyNotificationIconAreaControllerImpl = this;
            legacyNotificationIconAreaControllerImpl.updateIconsForLayout(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(0), secShelfNotificationIconContainer, true, true, false, false);
        }
        legacyNotificationIconAreaControllerImpl.applyNotificationIconsTint();
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
            if (this.mIsNotificationDotOnlyOn) {
                notificationIconContainer.setVisibility(0);
                updateIconsForLayout(new LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(2), this.mKeyguardStatusBarIcons, false, this.mShowLowPriority, true, true);
            } else {
                notificationIconContainer.setVisibility(8);
            }
        }
        applyNotificationIconsTint();
    }

    public final void updateTintForIcon(StatusBarIconView statusBarIconView, int i) {
        Boolean.TRUE.equals(statusBarIconView.getTag(R.id.icon_is_pre_L));
        statusBarIconView.setStaticDrawableColor(NotificationUtils.isGrayscale(statusBarIconView, this.mContrastColorUtil) ? DarkIconDispatcher.getTint(this.mTintAreas, statusBarIconView, i) : 0);
        statusBarIconView.setDecorColor(i);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void setupAodIcons() {
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    public final void updateAodNotificationIcons() {
    }
}
