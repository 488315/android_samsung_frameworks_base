package com.android.systemui.battery;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
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
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class BatteryMeterViewController extends ViewController {
    public float mAdditionalScaleFactorForSpecificBatteryView;
    public float mAodScaleFactor;
    public final BatteryController mBatteryController;
    public final AnonymousClass3 mBatteryStateChangeCallback;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final FeatureFlags mFeatureFlags;
    public boolean mIgnoreTunerUpdates;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public boolean mIsSubscribedForTunerUpdates;
    public final StatusBarLocation mLocation;
    public final Handler mMainHandler;
    private SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public final SlimIndicatorVisibilityHelper mSlimIndicatorVisibilityHelper;
    public final String mSlotBattery;
    public final AnonymousClass2 mTunable;
    public final TunerService mTunerService;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    /* renamed from: com.android.systemui.battery.BatteryMeterViewController$1, reason: invalid class name */
    public class AnonymousClass1 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() throws Resources.NotFoundException {
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
            BatteryMeterView batteryMeterView = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
            batteryMeterView.mRatio = f;
            batteryMeterView.removeView(batteryMeterView.mBatteryIconView);
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = new SamsungBatteryMeterDrawable(batteryMeterView.getContext());
            batteryMeterView.mSamsungDrawable = samsungBatteryMeterDrawable;
            samsungBatteryMeterDrawable.shouldShowGrayIcon = batteryMeterView.mIsGrayColor;
            samsungBatteryMeterDrawable.setShowPercentSetting(batteryMeterView.mShowPercentSamsungSetting);
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable2 = batteryMeterView.mSamsungDrawable;
            samsungBatteryMeterDrawable2.powerSaveEnabled = batteryMeterView.mPowerSaveEnabled;
            samsungBatteryMeterDrawable2.resizeDrawable();
            SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = samsungBatteryMeterDrawable2.postInvalidateHandler;
            int i = SamsungBatteryMeterDrawable.MSG_POST_INVALIDATE;
            if (!samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i)) {
                samsungBatteryMeterDrawable2.postInvalidateHandler.sendEmptyMessage(i);
            }
            batteryMeterView.mSamsungDrawable.onBatteryLevelChanged(batteryMeterView.mBatteryState);
            if (batteryMeterView.mIsDarkReceiverRegistered) {
                batteryMeterView.onDarkChanged(batteryMeterView.mDarkLastAreas, batteryMeterView.mDarkIntensity, batteryMeterView.mDarkTint);
            }
            batteryMeterView.mSamsungDrawable.postInvalidate();
            batteryMeterView.mBatteryIconView.setImageDrawable(batteryMeterView.mSamsungDrawable);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
            marginLayoutParams.setMargins(0, 0, 0, batteryMeterView.getResources().getDimensionPixelOffset(R.dimen.battery_margin_bottom));
            batteryMeterView.addView(batteryMeterView.mBatteryIconView, marginLayoutParams);
            ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).scaleBatteryMeterViewsLegacy();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() throws Resources.NotFoundException {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                onDensityOrFontScaleChanged();
            }
        }
    }

    /* renamed from: com.android.systemui.battery.BatteryMeterViewController$4, reason: invalid class name */
    class AnonymousClass4 implements UserTracker.Callback {
        public AnonymousClass4() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BatteryMeterViewController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.battery.BatteryMeterViewController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    BatteryMeterView batteryMeterView = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                    boolean zIsShowBatteryPercentInStatusBar = batteryMeterViewController.mSettingsHelper.isShowBatteryPercentInStatusBar();
                    batteryMeterView.mShowPercentSamsungSetting = zIsShowBatteryPercentInStatusBar;
                    batteryMeterView.mSamsungDrawable.setShowPercentSetting(zIsShowBatteryPercentInStatusBar);
                    ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).updateShowPercent();
                }
            }, 3000L);
        }
    }

    public class Factory {
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

    public final class SettingObserver extends ContentObserver {
        public SettingObserver(BatteryMeterViewController batteryMeterViewController, Handler handler) {
            super(handler);
        }
    }

    public class SlimIndicatorVisibilityHelper implements SlimIndicatorViewSubscriber {
        public String mTicketName;

        public /* synthetic */ SlimIndicatorVisibilityHelper(BatteryMeterViewController batteryMeterViewController, int i) {
            this();
        }

        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
            boolean zIsHiddenBatteryIcon = ((SlimIndicatorViewMediatorImpl) batteryMeterViewController.mSlimIndicatorViewMediator).isHiddenBatteryIcon();
            if (((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).mBatteryIconView != null) {
                ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).mBatteryIconView.setVisibility(zIsHiddenBatteryIcon ? 8 : 0);
            }
        }

        private SlimIndicatorVisibilityHelper() {
            this.mTicketName = null;
        }
    }

    /* renamed from: $r8$lambda$-WsMaTglXwEXTMBLRXrucn6xXKE, reason: not valid java name */
    public static void m1017$r8$lambda$WsMaTglXwEXTMBLRXrucn6xXKE(BatteryMeterViewController batteryMeterViewController) throws Resources.NotFoundException {
        BatteryMeterView batteryMeterView = (BatteryMeterView) batteryMeterViewController.mView;
        boolean zIsShowBatteryPercentInStatusBar = batteryMeterViewController.mSettingsHelper.isShowBatteryPercentInStatusBar();
        batteryMeterView.mShowPercentSamsungSetting = zIsShowBatteryPercentInStatusBar;
        batteryMeterView.mSamsungDrawable.setShowPercentSetting(zIsShowBatteryPercentInStatusBar);
        ((BatteryMeterView) batteryMeterViewController.mView).updateShowPercent();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.battery.BatteryMeterViewController$2] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.battery.BatteryMeterViewController$3] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda1] */
    public BatteryMeterViewController(BatteryMeterView batteryMeterView, StatusBarLocation statusBarLocation, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, final BatteryController batteryController, SettingsHelper settingsHelper, IndicatorScaleGardener indicatorScaleGardener, SlimIndicatorViewMediator slimIndicatorViewMediator) {
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
                printWriter.print(super.toString());
                StringBuilder sb = new StringBuilder(" location=");
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                sb.append(batteryMeterViewController.mLocation);
                printWriter.println(sb.toString());
                ((BatteryMeterView) ((ViewController) batteryMeterViewController).mView).dump(printWriter, strArr);
            }

            /* JADX WARN: Type inference failed for: r10v2, types: [com.android.systemui.battery.BatteryMeterView$1] */
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, int i5) throws Resources.NotFoundException {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                if (batteryMeterView2.mIsDirectPowerMode != z3) {
                    batteryMeterView2.mIsDirectPowerMode = z3;
                    batteryMeterView2.updateShowPercent();
                }
                final SamsungBatteryState samsungBatteryState = new SamsungBatteryState(i, z, z2, i2, i3, i4, z3, i5);
                final BatteryMeterView batteryMeterView3 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                BatteryMeterView.AnonymousClass1 anonymousClass1 = batteryMeterView3.mInvalidateRunnable;
                if (anonymousClass1 != null && batteryMeterView3.mUpdateBatteryStateHandler.hasCallbacks(anonymousClass1)) {
                    batteryMeterView3.mUpdateBatteryStateHandler.removeCallbacks(batteryMeterView3.mInvalidateRunnable);
                }
                batteryMeterView3.mInvalidateRunnable = new Runnable() { // from class: com.android.systemui.battery.BatteryMeterView.1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        BatteryMeterView batteryMeterView4 = BatteryMeterView.this;
                        batteryMeterView4.mLevel = samsungBatteryState.level;
                        batteryMeterView4.updatePercentText();
                        BatteryMeterView.this.mSamsungDrawable.onBatteryLevelChanged(samsungBatteryState);
                        BatteryMeterView batteryMeterView5 = BatteryMeterView.this;
                        SamsungBatteryState samsungBatteryState2 = samsungBatteryState;
                        boolean z4 = batteryMeterView5.mPowerSaveEnabled;
                        if (batteryMeterView5.mBatteryState.shouldShowChargingIcon() != samsungBatteryState2.shouldShowChargingIcon() || batteryMeterView5.mPowerSaveEnabled != z4) {
                            batteryMeterView5.mBatteryIconView.requestLayout();
                        }
                        BatteryMeterView batteryMeterView6 = BatteryMeterView.this;
                        batteryMeterView6.mBatteryState = samsungBatteryState;
                        batteryMeterView6.scaleBatteryMeterViewsLegacy();
                    }
                };
                batteryMeterView3.mUpdateBatteryStateHandler.postDelayed(batteryMeterView3.mInvalidateRunnable, (batteryMeterView3.mBatteryState.shouldShowChargingIcon() || !samsungBatteryState.shouldShowChargingIcon()) ? 0L : SystemStatusAnimationScheduler.DEBOUNCE_DELAY_CONST);
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryUnknownStateChanged(boolean z) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onIsBatteryDefenderChanged(boolean z) throws Resources.NotFoundException {
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView;
                boolean z2 = batteryMeterView2.mIsBatteryDefender != z;
                batteryMeterView2.mIsBatteryDefender = z;
                if (z2) {
                    batteryMeterView2.updateContentDescription();
                    batteryMeterView2.scaleBatteryMeterViewsLegacy();
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onIsIncompatibleChargingChanged(boolean z) {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                if (((FeatureFlagsClassicRelease) batteryMeterViewController.mFeatureFlags).isEnabled(Flags.INCOMPATIBLE_CHARGING_BATTERY_ICON)) {
                    BatteryMeterView batteryMeterView2 = (BatteryMeterView) ((ViewController) batteryMeterViewController).mView;
                    boolean z2 = batteryMeterView2.mIsIncompatibleCharging != z;
                    batteryMeterView2.mIsIncompatibleCharging = z;
                    if (z2) {
                        batteryMeterView2.isCharging();
                        throw null;
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onPowerSaveChanged(boolean z) throws Resources.NotFoundException {
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
            public final void onChanged(Uri uri) throws Resources.NotFoundException {
                BatteryMeterViewController.m1017$r8$lambda$WsMaTglXwEXTMBLRXrucn6xXKE(this.f$0);
            }
        };
        this.mAodScaleFactor = 1.15f;
        this.mLocation = statusBarLocation;
        this.mUserTracker = userTracker;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mMainHandler = handler;
        this.mFeatureFlags = featureFlags;
        this.mBatteryController = batteryController;
        BatteryMeterView batteryMeterView2 = (BatteryMeterView) this.mView;
        Objects.requireNonNull(batteryController);
        batteryMeterView2.mBatteryEstimateFetcher = new Object() { // from class: com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda1
        };
        this.mSlotBattery = getResources().getString(17043263);
        new SettingObserver(this, handler);
        this.mSettingsHelper = settingsHelper;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mSlimIndicatorVisibilityHelper = new SlimIndicatorVisibilityHelper(this, 0);
        if (statusBarLocation == StatusBarLocation.QS) {
            ((BatteryMeterView) this.mView).mBatteryIconView.setBackgroundResource(R.drawable.background_with_shadow);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        if (!this.mIsSubscribedForTunerUpdates && !this.mIgnoreTunerUpdates) {
            this.mTunerService.addTunable(this.mTunable, "icon_blacklist");
            this.mIsSubscribedForTunerUpdates = true;
        }
        ((BatteryControllerImpl) this.mBatteryController).addCallback(this.mBatteryStateChangeCallback);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_STATUS_BAR_BATTERY_PERCENT));
        BatteryMeterView batteryMeterView = (BatteryMeterView) this.mView;
        boolean zIsShowBatteryPercentInStatusBar = this.mSettingsHelper.isShowBatteryPercentInStatusBar();
        batteryMeterView.mShowPercentSamsungSetting = zIsShowBatteryPercentInStatusBar;
        batteryMeterView.mSamsungDrawable.setShowPercentSetting(zIsShowBatteryPercentInStatusBar);
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
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
        SlimIndicatorVisibilityHelper slimIndicatorVisibilityHelper = this.mSlimIndicatorVisibilityHelper;
        String str = slimIndicatorVisibilityHelper.mTicketName;
        if (str == null) {
            return;
        }
        ((SlimIndicatorViewMediatorImpl) BatteryMeterViewController.this.mSlimIndicatorViewMediator).unregisterSubscriber(str);
    }

    public final void setAodBatteryColorAlpha(float f, int i, int i2) {
        T t = this.mView;
        if (t != 0) {
            BatteryMeterView batteryMeterView = (BatteryMeterView) t;
            batteryMeterView.mBatteryIconLightModeAlpha = f;
            batteryMeterView.mBatteryIconDarkModeAlpha = f;
            batteryMeterView.mLightModeFillColor = i;
            batteryMeterView.mDarkModeFillColor = i;
            batteryMeterView.mLightModeBackgroundColor = i2;
            batteryMeterView.mDarkModeBackgroundColor = i2;
            batteryMeterView.onDarkChangedLegacy(new ArrayList(), 0.0f, i);
            batteryMeterView.mBatteryIconView.setAlpha(f);
        }
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
