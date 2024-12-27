package com.android.systemui.battery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.view.View;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BatteryMeterViewController extends ViewController {
    public float mAdditionalScaleFactorForSpecificBatteryView;
    public float mAodScaleFactor;
    public final BatteryController mBatteryController;
    public final AnonymousClass3 mBatteryStateChangeCallback;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final ContentResolver mContentResolver;
    public final FeatureFlags mFeatureFlags;
    public boolean mIgnoreTunerUpdates;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public boolean mIsSubscribedForTunerUpdates;
    public final StatusBarLocation mLocation;
    public final Handler mMainHandler;
    public final SettingObserver mSettingObserver;
    private SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public final SlimIndicatorVisibilityHelper mSlimIndicatorVisibilityHelper;
    public final String mSlotBattery;
    public final AnonymousClass2 mTunable;
    public final TunerService mTunerService;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.battery.BatteryMeterViewController$1, reason: invalid class name */
    public final class AnonymousClass1 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
            float f = batteryMeterViewController.mIndicatorScaleGardener.getLatestScaleModel(batteryMeterViewController.getContext()).ratio;
            StatusBarLocation statusBarLocation = batteryMeterViewController.mLocation;
            if (statusBarLocation != null && statusBarLocation == StatusBarLocation.AOD) {
                f *= batteryMeterViewController.mAodScaleFactor;
            }
            float f2 = batteryMeterViewController.mAdditionalScaleFactorForSpecificBatteryView;
            if (f2 != 0.0f) {
                f = f2;
            }
            ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).mRatio = f;
            BatteryMeterView batteryMeterView = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
            batteryMeterView.getClass();
            Flags.newStatusBarIcons();
            batteryMeterView.scaleBatteryMeterViewsLegacy();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.battery.BatteryMeterViewController$4, reason: invalid class name */
    class AnonymousClass4 implements UserTracker.Callback {
        public AnonymousClass4() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BatteryMeterViewController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.battery.BatteryMeterViewController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    View view;
                    SettingsHelper settingsHelper;
                    View view2;
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    view = ((ViewController) batteryMeterViewController).mView;
                    BatteryMeterView batteryMeterView = (BatteryMeterView) view;
                    settingsHelper = batteryMeterViewController.mSettingsHelper;
                    boolean isShowBatteryPercentInStatusBar = settingsHelper.isShowBatteryPercentInStatusBar();
                    batteryMeterView.mShowPercentSamsungSetting = isShowBatteryPercentInStatusBar;
                    batteryMeterView.mSamsungDrawable.showPercentSetting = isShowBatteryPercentInStatusBar;
                    view2 = ((ViewController) batteryMeterViewController).mView;
                    ((BatteryMeterView) view2).updateShowPercent();
                }
            }, 3000L);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final BatteryController mBatteryController;
        public final ConfigurationController mConfigurationController;
        public final ContentResolver mContentResolver;
        public final FeatureFlags mFeatureFlags;
        public final IndicatorScaleGardener mIndicatorScaleGardener;
        public final Handler mMainHandler;
        private SettingsHelper mSettingsHelper;
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
        public final TunerService mTunerService;
        public final UserTracker mUserTracker;

        public Factory(UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, BatteryController batteryController, SettingsHelper settingsHelper, IndicatorScaleGardener indicatorScaleGardener, SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mUserTracker = userTracker;
            this.mConfigurationController = configurationController;
            this.mTunerService = tunerService;
            this.mMainHandler = handler;
            this.mContentResolver = contentResolver;
            this.mFeatureFlags = featureFlags;
            this.mBatteryController = batteryController;
            this.mSettingsHelper = settingsHelper;
            this.mIndicatorScaleGardener = indicatorScaleGardener;
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }

        public final BatteryMeterViewController create(View view, StatusBarLocation statusBarLocation) {
            return new BatteryMeterViewController((BatteryMeterView) view, statusBarLocation, this.mUserTracker, this.mConfigurationController, this.mTunerService, this.mMainHandler, this.mContentResolver, this.mFeatureFlags, this.mBatteryController, this.mSettingsHelper, this.mIndicatorScaleGardener, this.mSlimIndicatorViewMediator);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updateShowPercent();
            if (TextUtils.equals(uri.getLastPathSegment(), "battery_estimates_last_update_time")) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updatePercentText();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SlimIndicatorVisibilityHelper implements SlimIndicatorViewSubscriber {
        public String mTicketName;

        public /* synthetic */ SlimIndicatorVisibilityHelper(BatteryMeterViewController batteryMeterViewController, int i) {
            this();
        }

        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
            boolean isHiddenBatteryIcon = ((SlimIndicatorViewMediatorImpl) batteryMeterViewController.mSlimIndicatorViewMediator).isHiddenBatteryIcon();
            if (((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).mBatteryIconView != null) {
                ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).mBatteryIconView.setVisibility(isHiddenBatteryIcon ? 8 : 0);
            }
        }

        private SlimIndicatorVisibilityHelper() {
            this.mTicketName = null;
        }
    }

    /* renamed from: $r8$lambda$-WsMaTglXwEXTMBLRXrucn6xXKE, reason: not valid java name */
    public static void m887$r8$lambda$WsMaTglXwEXTMBLRXrucn6xXKE(BatteryMeterViewController batteryMeterViewController) {
        BatteryMeterView batteryMeterView = (BatteryMeterView) batteryMeterViewController.mView;
        boolean isShowBatteryPercentInStatusBar = batteryMeterViewController.mSettingsHelper.isShowBatteryPercentInStatusBar();
        batteryMeterView.mShowPercentSamsungSetting = isShowBatteryPercentInStatusBar;
        batteryMeterView.mSamsungDrawable.showPercentSetting = isShowBatteryPercentInStatusBar;
        ((BatteryMeterView) batteryMeterViewController.mView).updateShowPercent();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.battery.BatteryMeterViewController$2] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.battery.BatteryMeterViewController$3] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda1] */
    public BatteryMeterViewController(BatteryMeterView batteryMeterView, StatusBarLocation statusBarLocation, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, BatteryController batteryController, SettingsHelper settingsHelper, IndicatorScaleGardener indicatorScaleGardener, SlimIndicatorViewMediator slimIndicatorViewMediator) {
        super(batteryMeterView);
        this.mAdditionalScaleFactorForSpecificBatteryView = 0.0f;
        this.mConfigurationListener = new AnonymousClass1();
        this.mTunable = new TunerService.Tunable() { // from class: com.android.systemui.battery.BatteryMeterViewController.2
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                if ("icon_blacklist".equals(str)) {
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).setVisibility(StatusBarIconController.getIconHideList(batteryMeterViewController.getContext(), str2).contains(batteryMeterViewController.mSlotBattery) ? 8 : 0);
                }
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.battery.BatteryMeterViewController.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                printWriter.print(toString());
                StringBuilder sb = new StringBuilder(" location=");
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                sb.append(batteryMeterViewController.mLocation);
                printWriter.println(sb.toString());
                ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).dump(printWriter, strArr);
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, int i5) {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                if (batteryMeterView2.mIsDirectPowerMode != z3) {
                    batteryMeterView2.mIsDirectPowerMode = z3;
                    batteryMeterView2.updateShowPercent();
                }
                SamsungBatteryState samsungBatteryState = new SamsungBatteryState(i, z, z2, i2, i3, i4, z3, i5);
                BatteryMeterView batteryMeterView3 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                batteryMeterView3.getClass();
                batteryMeterView3.mLevel = samsungBatteryState.level;
                batteryMeterView3.updatePercentText();
                SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = batteryMeterView3.mSamsungDrawable;
                SamsungBatteryState samsungBatteryState2 = samsungBatteryMeterDrawable.batteryState;
                boolean z4 = !(samsungBatteryState2.level == samsungBatteryState.level && samsungBatteryState2.charging == samsungBatteryState.charging && samsungBatteryState2.pluggedIn == samsungBatteryState.pluggedIn && samsungBatteryState2.batteryHealth == samsungBatteryState.batteryHealth && samsungBatteryState2.batteryOnline == samsungBatteryState.batteryOnline && samsungBatteryState2.batteryStatus == samsungBatteryState.batteryStatus && samsungBatteryState2.isDirectPowerMode == samsungBatteryState.isDirectPowerMode);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onBatteryLevelChanged isSomethingChanged: ", "SamsungBatteryMeterDrawable", z4);
                if (z4) {
                    samsungBatteryMeterDrawable.batteryState = samsungBatteryState;
                    samsungBatteryMeterDrawable.resizeDrawable();
                    if (SamsungBatteryMeterDrawable.DEBUG) {
                        SamsungBatteryState samsungBatteryState3 = samsungBatteryMeterDrawable.batteryState;
                        StringBuilder sb = new StringBuilder("Level: ");
                        sb.append(samsungBatteryState3.level);
                        sb.append(", PluggedIn: ");
                        sb.append(samsungBatteryState3.pluggedIn);
                        sb.append(", Charging: ");
                        sb.append(samsungBatteryState3.charging);
                        sb.append(", BatteryHealth: ");
                        sb.append(samsungBatteryState3.batteryHealth);
                        sb.append(",BatteryStatus: ");
                        sb.append(samsungBatteryState3.batteryStatus);
                        sb.append(", BatteryOnline: ");
                        sb.append(samsungBatteryState3.batteryOnline);
                        sb.append(", IsDirectPowerMode: ");
                        sb.append(samsungBatteryState3.isDirectPowerMode);
                        sb.append(", miscEvent: ");
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBatteryLevelChanged - ", Anchor$$ExternalSyntheticOutline0.m(samsungBatteryState3.miscEvent, ",", sb), "SamsungBatteryMeterDrawable");
                    }
                    SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = samsungBatteryMeterDrawable.postInvalidateHandler;
                    int i6 = SamsungBatteryMeterDrawable.MSG_POST_INVALIDATE;
                    if (!samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i6)) {
                        samsungBatteryMeterDrawable.postInvalidateHandler.sendEmptyMessage(i6);
                    }
                }
                boolean z5 = batteryMeterView3.mPowerSaveEnabled;
                if (batteryMeterView3.mBatteryState.shouldShowChargingIcon() != samsungBatteryState.shouldShowChargingIcon() || batteryMeterView3.mPowerSaveEnabled != z5) {
                    batteryMeterView3.mBatteryIconView.requestLayout();
                }
                batteryMeterView3.mBatteryState = samsungBatteryState;
                batteryMeterView3.scaleBatteryMeterViewsLegacy();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryUnknownStateChanged(boolean z) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onIsBatteryDefenderChanged(boolean z) {
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView;
                boolean z2 = batteryMeterView2.mIsBatteryDefender != z;
                batteryMeterView2.mIsBatteryDefender = z;
                if (z2) {
                    batteryMeterView2.updateContentDescription();
                    Flags.newStatusBarIcons();
                    Flags.newStatusBarIcons();
                    batteryMeterView2.scaleBatteryMeterViewsLegacy();
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onIsIncompatibleChargingChanged(boolean z) {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                if (((FeatureFlagsClassicRelease) batteryMeterViewController.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.INCOMPATIBLE_CHARGING_BATTERY_ICON)) {
                    BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                    boolean z2 = batteryMeterView2.mIsIncompatibleCharging != z;
                    batteryMeterView2.mIsIncompatibleCharging = z;
                    if (z2) {
                        Flags.newStatusBarIcons();
                        batteryMeterView2.isCharging();
                        throw null;
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onPowerSaveChanged(boolean z) {
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView;
                if (z == batteryMeterView2.mPowerSaveEnabled) {
                    return;
                }
                batteryMeterView2.mPowerSaveEnabled = z;
                SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = batteryMeterView2.mSamsungDrawable;
                samsungBatteryMeterDrawable.powerSaveEnabled = z;
                samsungBatteryMeterDrawable.resizeDrawable();
                SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = samsungBatteryMeterDrawable.postInvalidateHandler;
                int i = SamsungBatteryMeterDrawable.MSG_POST_INVALIDATE;
                if (!samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i)) {
                    samsungBatteryMeterDrawable.postInvalidateHandler.sendEmptyMessage(i);
                }
                batteryMeterView2.scaleBatteryMeterViewsLegacy();
                SamsungBatteryState samsungBatteryState = batteryMeterView2.mBatteryState;
                if (samsungBatteryState.shouldShowChargingIcon() == samsungBatteryState.shouldShowChargingIcon() && batteryMeterView2.mPowerSaveEnabled == z) {
                    return;
                }
                batteryMeterView2.mBatteryIconView.requestLayout();
            }
        };
        this.mUserChangedCallback = new AnonymousClass4();
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                BatteryMeterViewController.m887$r8$lambda$WsMaTglXwEXTMBLRXrucn6xXKE(BatteryMeterViewController.this);
            }
        };
        this.mAodScaleFactor = 1.15f;
        this.mLocation = statusBarLocation;
        this.mUserTracker = userTracker;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mMainHandler = handler;
        this.mContentResolver = contentResolver;
        this.mFeatureFlags = featureFlags;
        this.mBatteryController = batteryController;
        BatteryMeterView batteryMeterView2 = (BatteryMeterView) this.mView;
        Objects.requireNonNull(batteryController);
        batteryMeterView2.mBatteryEstimateFetcher = new Object(batteryController) { // from class: com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ BatteryController f$0;
        };
        ((BatteryMeterView) this.mView).mDisplayShieldEnabled = getContext().getResources().getBoolean(R.bool.flag_battery_shield_icon);
        this.mSlotBattery = getResources().getString(17043121);
        this.mSettingObserver = new SettingObserver(handler);
        this.mSettingsHelper = settingsHelper;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mSlimIndicatorVisibilityHelper = new SlimIndicatorVisibilityHelper(this, 0);
        if (statusBarLocation == StatusBarLocation.QS) {
            ((BatteryMeterView) this.mView).getClass();
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        if (!this.mIsSubscribedForTunerUpdates && !this.mIgnoreTunerUpdates) {
            this.mTunerService.addTunable(this.mTunable, "icon_blacklist");
            this.mIsSubscribedForTunerUpdates = true;
        }
        ((BatteryControllerImpl) this.mBatteryController).addCallback(this.mBatteryStateChangeCallback);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_STATUS_BAR_BATTERY_PERCENT));
        BatteryMeterView batteryMeterView = (BatteryMeterView) this.mView;
        boolean isShowBatteryPercentInStatusBar = this.mSettingsHelper.isShowBatteryPercentInStatusBar();
        batteryMeterView.mShowPercentSamsungSetting = isShowBatteryPercentInStatusBar;
        batteryMeterView.mSamsungDrawable.showPercentSetting = isShowBatteryPercentInStatusBar;
        this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("battery_estimates_last_update_time"), false, this.mSettingObserver);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, new HandlerExecutor(this.mMainHandler));
        String str = "BatteryMeterViewController";
        if (((BatteryMeterView) this.mView).getTag() != null) {
            str = ((BatteryMeterView) this.mView).getTag().toString() + "BatteryMeterViewController";
        }
        SlimIndicatorVisibilityHelper slimIndicatorVisibilityHelper = this.mSlimIndicatorVisibilityHelper;
        if (str == null) {
            slimIndicatorVisibilityHelper.getClass();
        } else {
            slimIndicatorVisibilityHelper.mTicketName = str;
            ((SlimIndicatorViewMediatorImpl) BatteryMeterViewController.this.mSlimIndicatorViewMediator).registerSubscriber(str, slimIndicatorVisibilityHelper);
        }
        ((BatteryMeterView) this.mView).updateShowPercent();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        if (this.mIsSubscribedForTunerUpdates) {
            this.mTunerService.removeTunable(this.mTunable);
            this.mIsSubscribedForTunerUpdates = false;
        }
        ((BatteryControllerImpl) this.mBatteryController).removeCallback(this.mBatteryStateChangeCallback);
        ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
        this.mContentResolver.unregisterContentObserver(this.mSettingObserver);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
        SlimIndicatorVisibilityHelper slimIndicatorVisibilityHelper = this.mSlimIndicatorVisibilityHelper;
        String str = slimIndicatorVisibilityHelper.mTicketName;
        if (str == null) {
            return;
        }
        ((SlimIndicatorViewMediatorImpl) BatteryMeterViewController.this.mSlimIndicatorViewMediator).unregisterSubscriber(str);
    }

    public final void setAodScaleFactor() {
        StatusBarLocation statusBarLocation = this.mLocation;
        if (statusBarLocation == null || statusBarLocation != StatusBarLocation.AOD) {
            return;
        }
        this.mAodScaleFactor = 1.0f;
        if (this.mView != 0) {
            this.mConfigurationListener.onDensityOrFontScaleChanged();
        }
    }
}
