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
import androidx.collection.ArrayMap;
import androidx.collection.IndexBasedArrayIterator;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
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
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    /* renamed from: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements NotificationListener.NotificationSettingsListener {
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

    public LegacyNotificationIconAreaControllerImpl(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, StatusBarStateController statusBarStateController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, NotificationMediaManager notificationMediaManager, NotificationListener notificationListener, DozeParameters dozeParameters, SectionStyleProvider sectionStyleProvider, Optional<Bubbles> optional, DemoModeController demoModeController, DarkIconDispatcher darkIconDispatcher, FeatureFlags featureFlags, ScreenOffAnimationController screenOffAnimationController, IndicatorScaleGardener indicatorScaleGardener, OngoingCallController ongoingCallController) throws Resources.NotFoundException {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mSettingsListener = anonymousClass1;
        this.mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl.2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) throws Resources.NotFoundException {
                boolean zEquals = uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NOTIFICATION_STYLE));
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl = LegacyNotificationIconAreaControllerImpl.this;
                if (!zEquals) {
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
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.notification_icon_area, (ViewGroup) null);
        this.mNotificationIconArea = viewInflate;
        this.mNotificationIcons = (NotificationIconContainer) viewInflate.findViewById(R.id.notificationIcons);
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x026b  */
    @Override // com.android.systemui.statusbar.phone.NotificationIconAreaController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        int i;
        String resourceName;
        LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl;
        String resourceName2;
        LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl2 = this;
        String str9 = "  ";
        printWriter.println("  ");
        printWriter.println("NotificationIconAreaController state:");
        int childCount = legacyNotificationIconAreaControllerImpl2.mNotificationIcons.getChildCount();
        StringBuilder sb = new StringBuilder("  statusbar noti icons: ");
        sb.append(childCount);
        String str10 = "  tintColor=0x%08x";
        sb.append(String.format("  tintColor=0x%08x", Integer.valueOf(legacyNotificationIconAreaControllerImpl2.mIconTint)));
        printWriter.println(sb.toString());
        MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  statusbar notification style : "), legacyNotificationIconAreaControllerImpl2.mStatusBarNotificationStyle, printWriter);
        int i2 = 0;
        while (true) {
            str = " / getAlpha ";
            str2 = str10;
            str3 = str9;
            str4 = "] icon=";
            str5 = " iconStr=";
            if (i2 >= childCount) {
                break;
            }
            int i3 = childCount;
            StatusBarIconView statusBarIconView = (StatusBarIconView) legacyNotificationIconAreaControllerImpl2.mNotificationIcons.getChildAt(i2);
            boolean zIsGrayscale = NotificationUtils.isGrayscale(statusBarIconView, legacyNotificationIconAreaControllerImpl2.mContrastColorUtil);
            boolean zIsInAreas = DarkIconDispatcher.isInAreas(legacyNotificationIconAreaControllerImpl2.mTintAreas, statusBarIconView);
            printWriter.println("    [" + i2 + "] icon=" + statusBarIconView + " / getVisibleState " + statusBarIconView.mVisibleState + "/ getIconAppearAmount " + statusBarIconView.mIconAppearAmount + " / getDotAppearAmount " + statusBarIconView.mDotAppearAmount + " / getAlpha " + statusBarIconView.getAlpha() + " / getVisibility " + statusBarIconView.getVisibility() + " / getTranslationX " + statusBarIconView.getTranslationX());
            StringBuilder sb2 = new StringBuilder(" colorize=");
            sb2.append(zIsGrayscale);
            sb2.append(" isInAreas=");
            sb2.append(zIsInAreas);
            sb2.append(String.format("  color=0x%08x", Integer.valueOf(statusBarIconView.mDrawableColor)));
            printWriter.println(sb2.toString());
            StatusBarIcon statusBarIcon = statusBarIconView.mIcon;
            if (statusBarIcon != null) {
                Icon icon = statusBarIcon.icon;
                String strValueOf = String.valueOf(icon);
                if (icon == null || icon.getType() != 2) {
                    legacyNotificationIconAreaControllerImpl = this;
                } else {
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strValueOf, " / ");
                    legacyNotificationIconAreaControllerImpl = this;
                    Context contextCreatePackageContext = legacyNotificationIconAreaControllerImpl.mContext;
                    String resPackage = icon.getResPackage();
                    int resId = icon.getResId();
                    if (resPackage != null) {
                        try {
                            contextCreatePackageContext = contextCreatePackageContext.createPackageContext(resPackage, 0);
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                    try {
                        resourceName2 = contextCreatePackageContext.getResources().getResourceName(resId);
                    } catch (Resources.NotFoundException unused2) {
                        resourceName2 = "<name unknown>";
                    }
                    sbM.append(resourceName2);
                    ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, str5, sbM.toString());
                }
            }
            i2++;
            legacyNotificationIconAreaControllerImpl2 = legacyNotificationIconAreaControllerImpl;
            str10 = str2;
            str9 = str3;
            childCount = i3;
        }
        LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl3 = legacyNotificationIconAreaControllerImpl2;
        printWriter.println(str3);
        int childCount2 = legacyNotificationIconAreaControllerImpl3.mKeyguardStatusBarIcons.getChildCount();
        String str11 = " / ";
        StringBuilder sb3 = new StringBuilder("  keyguard noti icons: ");
        sb3.append(childCount2);
        String str12 = "  color=0x%08x";
        sb3.append(String.format(str2, Integer.valueOf(legacyNotificationIconAreaControllerImpl3.mIconTint)));
        printWriter.println(sb3.toString());
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  keyguard notification style is Dot: "), legacyNotificationIconAreaControllerImpl3.mIsNotificationDotOnlyOn, printWriter);
        int i4 = 0;
        while (i4 < childCount2) {
            StatusBarIconView statusBarIconView2 = (StatusBarIconView) legacyNotificationIconAreaControllerImpl3.mKeyguardStatusBarIcons.getChildAt(i4);
            boolean zIsGrayscale2 = NotificationUtils.isGrayscale(statusBarIconView2, legacyNotificationIconAreaControllerImpl3.mContrastColorUtil);
            int i5 = childCount2;
            boolean zIsInAreas2 = DarkIconDispatcher.isInAreas(legacyNotificationIconAreaControllerImpl3.mTintAreas, statusBarIconView2);
            StringBuilder sb4 = new StringBuilder("    [");
            sb4.append(i4);
            sb4.append(str4);
            sb4.append(statusBarIconView2);
            sb4.append(" / getVisibleState ");
            String str13 = str4;
            sb4.append(statusBarIconView2.mVisibleState);
            sb4.append("/ getIconAppearAmount ");
            sb4.append(statusBarIconView2.mIconAppearAmount);
            sb4.append(" / getDotAppearAmount ");
            sb4.append(statusBarIconView2.mDotAppearAmount);
            sb4.append(str);
            sb4.append(statusBarIconView2.getAlpha());
            sb4.append(" / getVisibility ");
            sb4.append(statusBarIconView2.getVisibility());
            sb4.append(" / getTranslationX ");
            sb4.append(statusBarIconView2.getTranslationX());
            printWriter.println(sb4.toString());
            StringBuilder sb5 = new StringBuilder(" colorize=");
            sb5.append(zIsGrayscale2);
            sb5.append(" isInAreas=");
            sb5.append(zIsInAreas2);
            String str14 = str12;
            sb5.append(String.format(str14, Integer.valueOf(statusBarIconView2.mDrawableColor)));
            printWriter.println(sb5.toString());
            StatusBarIcon statusBarIcon2 = statusBarIconView2.mIcon;
            if (statusBarIcon2 != null) {
                Icon icon2 = statusBarIcon2.icon;
                String strValueOf2 = String.valueOf(icon2);
                if (icon2 == null || icon2.getType() != 2) {
                    legacyNotificationIconAreaControllerImpl3 = this;
                    str6 = str;
                    str7 = str5;
                    str8 = str11;
                    i = i4;
                } else {
                    String str15 = str11;
                    StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strValueOf2, str15);
                    legacyNotificationIconAreaControllerImpl3 = this;
                    Context contextCreatePackageContext2 = legacyNotificationIconAreaControllerImpl3.mContext;
                    i = i4;
                    String resPackage2 = icon2.getResPackage();
                    str6 = str;
                    int resId2 = icon2.getResId();
                    if (resPackage2 != null) {
                        str8 = str15;
                        try {
                            contextCreatePackageContext2 = contextCreatePackageContext2.createPackageContext(resPackage2, 0);
                        } catch (PackageManager.NameNotFoundException unused3) {
                        }
                    } else {
                        str8 = str15;
                    }
                    try {
                        resourceName = contextCreatePackageContext2.getResources().getResourceName(resId2);
                    } catch (Resources.NotFoundException unused4) {
                        resourceName = "<name unknown>";
                    }
                    sbM2.append(resourceName);
                    String string = sbM2.toString();
                    str7 = str5;
                    ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, str7, string);
                }
            }
            i4 = i + 1;
            str5 = str7;
            childCount2 = i5;
            str4 = str13;
            str11 = str8;
            str = str6;
            str12 = str14;
        }
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
    public final void onDensityOrFontScaleChanged(Context context) throws Resources.NotFoundException {
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

    public final void reloadDimens(Context context) throws Resources.NotFoundException {
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
    public final void setIsolatedIconLocation(Rect rect, boolean z) throws Resources.NotFoundException {
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
    public final void showIconIsolated(StatusBarIconView statusBarIconView, boolean z) throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconsForLayout(Function function, NotificationIconContainer notificationIconContainer, boolean z, boolean z2, boolean z3, boolean z4) {
        int i;
        ArrayList arrayList = new ArrayList(this.mNotificationEntries.size());
        for (int i2 = 0; i2 < this.mNotificationEntries.size(); i2++) {
            NotificationEntry representativeEntry = ((PipelineEntry) this.mNotificationEntries.get(i2)).getRepresentativeEntry();
            if (representativeEntry != null && representativeEntry.row != null) {
                if (z4) {
                    if (representativeEntry.getAttachedNotifChildren() != null) {
                        ArrayList arrayList2 = (ArrayList) representativeEntry.getAttachedNotifChildren();
                        int size = arrayList2.size();
                        boolean z5 = true;
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj = arrayList2.get(i3);
                            i3++;
                            z5 = z5 && ((NotificationEntry) obj).mIsReaded;
                        }
                        if (z5) {
                        }
                    } else if (representativeEntry.mIsReaded) {
                    }
                } else if ((!representativeEntry.mRanking.isAmbient() || z) && ((z2 || representativeEntry.mRanking.getImportance() >= 3) && ((!representativeEntry.isRowDismissed() || !z3) && ((z || !representativeEntry.shouldSuppressVisualEffect(32)) && ((!this.mBubblesOptional.isPresent() || !((BubbleController.BubblesImpl) ((Bubbles) this.mBubblesOptional.get())).isBubbleExpanded(representativeEntry.mKey)) && ((!this.mOngoingCallController.hasOngoingCall() || ((i = representativeEntry.mSbn.getNotification().extras.getInt("android.callType", -1)) != 2 && i != 1 && i != 3)) && ((!representativeEntry.isOngoingActivity() || !representativeEntry.isPromotedState()) && !representativeEntry.isInsignificant()))))))) {
                    StatusBarIconView statusBarIconView = (StatusBarIconView) function.apply(representativeEntry);
                    if (statusBarIconView != null) {
                        arrayList.add(statusBarIconView);
                    }
                }
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < notificationIconContainer.getChildCount(); i4++) {
            View childAt = notificationIconContainer.getChildAt(i4);
            if ((childAt instanceof StatusBarIconView) && !arrayList.contains(childAt)) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) childAt;
                String groupKey = statusBarIconView2.mNotification.getGroupKey();
                int i5 = 0;
                boolean z6 = false;
                while (true) {
                    if (i5 >= arrayList.size()) {
                        break;
                    }
                    StatusBarIconView statusBarIconView3 = (StatusBarIconView) arrayList.get(i5);
                    if (statusBarIconView3.mIcon.icon.sameAs(statusBarIconView2.mIcon.icon) && statusBarIconView3.mNotification.getGroupKey().equals(groupKey)) {
                        if (z6) {
                            z6 = false;
                            break;
                        }
                        z6 = true;
                    }
                    i5++;
                }
                if (z6) {
                    ArrayList arrayList4 = (ArrayList) arrayMap.get(groupKey);
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList();
                        arrayMap.put(groupKey, arrayList4);
                    }
                    arrayList4.add(statusBarIconView2.mIcon);
                }
                arrayList3.add(statusBarIconView2);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        Iterator it = ((ArrayMap.KeySet) arrayMap.keySet()).iterator();
        while (true) {
            IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it;
            if (!indexBasedArrayIterator.hasNext()) {
                break;
            }
            String str = (String) indexBasedArrayIterator.next();
            if (((ArrayList) arrayMap.get(str)).size() != 1) {
                arrayList5.add(str);
            }
        }
        arrayMap.removeAll(arrayList5);
        int i6 = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationIconContainer.mReplacingIconsLegacy = arrayMap;
        int size2 = arrayList3.size();
        for (int i7 = 0; i7 < size2; i7++) {
            notificationIconContainer.removeView((View) arrayList3.get(i7));
        }
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((this.mIconHPadding * 2) + this.mIconSize, SystemBarUtils.getStatusBarHeight(this.mContext));
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            StatusBarIconView statusBarIconView4 = (StatusBarIconView) arrayList.get(i8);
            notificationIconContainer.removeTransientView(statusBarIconView4);
            if (statusBarIconView4.getParent() == null) {
                if (z3) {
                    statusBarIconView4.mOnDismissListener = this.mUpdateStatusBarIcons;
                }
                notificationIconContainer.addView(statusBarIconView4, i8, layoutParams);
            }
        }
        notificationIconContainer.mChangingViewPositions = true;
        int iMin = Math.min(notificationIconContainer.getChildCount(), arrayList.size());
        for (int i9 = 0; i9 < iMin; i9++) {
            View childAt2 = notificationIconContainer.getChildAt(i9);
            View view = (StatusBarIconView) arrayList.get(i9);
            if (childAt2 != view) {
                notificationIconContainer.removeView(view);
                notificationIconContainer.addView(view, i9);
            }
        }
        notificationIconContainer.mChangingViewPositions = false;
        int i10 = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        notificationIconContainer.mReplacingIconsLegacy = null;
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
