package com.android.systemui.battery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BatteryMeterViewController extends ViewController {
    public float mAdditionalScaleFactorForSpecificBatteryView;
    public final BatteryController mBatteryController;
    public final C10503 mBatteryStateChangeCallback;
    public final ConfigurationController mConfigurationController;
    public final C10481 mConfigurationListener;
    public final ContentResolver mContentResolver;
    public boolean mIgnoreTunerUpdates;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public boolean mIsSubscribedForTunerUpdates;
    public final Handler mMainHandler;
    public final SettingObserver mSettingObserver;
    public final SettingsHelper mSettingsHelper;
    public final BatteryMeterViewController$$ExternalSyntheticLambda0 mSettingsListener;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public final SlimIndicatorVisibilityHelper mSlimIndicatorVisibilityHelper;
    public final String mSlotBattery;
    public final C10492 mTunable;
    public final TunerService mTunerService;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.battery.BatteryMeterViewController$4 */
    public final class C10514 implements UserTracker.Callback {
        public C10514() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BatteryMeterViewController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.battery.BatteryMeterViewController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    ((BatteryMeterView) batteryMeterViewController.mView).mShowPercentSamsungSetting = batteryMeterViewController.mSettingsHelper.isShowBatteryPercentInStatusBar();
                    ((BatteryMeterView) batteryMeterViewController.mView).updateShowPercent();
                }
            }, 3000L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            ((BatteryMeterView) BatteryMeterViewController.this.mView).updateShowPercent();
            if (TextUtils.equals(uri.getLastPathSegment(), "battery_estimates_last_update_time")) {
                ((BatteryMeterView) BatteryMeterViewController.this.mView).updatePercentText();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SlimIndicatorVisibilityHelper implements SlimIndicatorViewSubscriber {
        public String mTicketName;

        public /* synthetic */ SlimIndicatorVisibilityHelper(BatteryMeterViewController batteryMeterViewController, int i) {
            this();
        }

        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) batteryMeterViewController.mSlimIndicatorViewMediator;
            String iconBlacklist = slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist();
            boolean z = slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_BATTERY_ICON);
            View view = batteryMeterViewController.mView;
            if (((BatteryMeterView) view).mBatteryIconView != null) {
                ((BatteryMeterView) view).mBatteryIconView.setVisibility(z ? 8 : 0);
            }
        }

        private SlimIndicatorVisibilityHelper() {
            this.mTicketName = null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.battery.BatteryMeterViewController$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.battery.BatteryMeterViewController$2] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.battery.BatteryMeterViewController$3] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda0] */
    public BatteryMeterViewController(BatteryMeterView batteryMeterView, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, BatteryController batteryController, SettingsHelper settingsHelper, SlimIndicatorViewMediator slimIndicatorViewMediator, IndicatorScaleGardener indicatorScaleGardener) {
        super(batteryMeterView);
        this.mAdditionalScaleFactorForSpecificBatteryView = 0.0f;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.battery.BatteryMeterViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                float f = batteryMeterViewController.mIndicatorScaleGardener.getLatestScaleModel(batteryMeterViewController.getContext()).ratio;
                float f2 = batteryMeterViewController.mAdditionalScaleFactorForSpecificBatteryView;
                if (f2 != 0.0f) {
                    f = f2;
                }
                View view = batteryMeterViewController.mView;
                ((BatteryMeterView) view).mRatio = f;
                ((BatteryMeterView) view).scaleBatteryMeterViews();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        this.mTunable = new TunerService.Tunable() { // from class: com.android.systemui.battery.BatteryMeterViewController.2
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                if ("icon_blacklist".equals(str)) {
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    ((BatteryMeterView) batteryMeterViewController.mView).setVisibility(StatusBarIconController.getIconHideList(batteryMeterViewController.getContext(), str2).contains(batteryMeterViewController.mSlotBattery) ? 8 : 0);
                }
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.battery.BatteryMeterViewController.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) BatteryMeterViewController.this.mView;
                batteryMeterView2.mCharging = z;
                batteryMeterView2.mLevel = i;
                batteryMeterView2.updatePercentText();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryUnknownStateChanged(boolean z) {
                ((BatteryMeterView) BatteryMeterViewController.this.mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onIsBatteryDefenderChanged(boolean z) {
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) BatteryMeterViewController.this.mView;
                boolean z2 = batteryMeterView2.mIsBatteryDefender != z;
                batteryMeterView2.mIsBatteryDefender = z;
                if (z2) {
                    batteryMeterView2.updateContentDescription();
                    batteryMeterView2.scaleBatteryMeterViews();
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onPowerSaveChanged(boolean z) {
                ((BatteryMeterView) BatteryMeterViewController.this.mView).getClass();
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3) {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                BatteryMeterView batteryMeterView2 = (BatteryMeterView) batteryMeterViewController.mView;
                if (batteryMeterView2.mIsDirectPowerMode != z3) {
                    batteryMeterView2.mIsDirectPowerMode = z3;
                    batteryMeterView2.updateShowPercent();
                }
                SamsungBatteryState samsungBatteryState = new SamsungBatteryState(i, z, z2, i2, i3, i4, z3);
                LegacySamsungBatteryMeterDrawable legacySamsungBatteryMeterDrawable = ((BatteryMeterView) batteryMeterViewController.mView).mSamsungDrawable;
                SamsungBatteryState samsungBatteryState2 = legacySamsungBatteryMeterDrawable.batteryState;
                int i5 = samsungBatteryState2.level;
                int i6 = samsungBatteryState.level;
                boolean z4 = samsungBatteryState.isDirectPowerMode;
                int i7 = samsungBatteryState.batteryOnline;
                int i8 = samsungBatteryState.batteryStatus;
                int i9 = samsungBatteryState.batteryHealth;
                boolean z5 = samsungBatteryState.pluggedIn;
                boolean z6 = samsungBatteryState.charging;
                boolean z7 = !(i5 == i6 && samsungBatteryState2.charging == z6 && samsungBatteryState2.pluggedIn == z5 && samsungBatteryState2.batteryHealth == i9 && samsungBatteryState2.batteryOnline == i7 && samsungBatteryState2.batteryStatus == i8 && samsungBatteryState2.isDirectPowerMode == z4);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onBatteryLevelChanged isSomethingChanged: ", z7, "SamsungBatteryMeterDrawable");
                if (z7) {
                    legacySamsungBatteryMeterDrawable.batteryState = samsungBatteryState;
                    if (LegacySamsungBatteryMeterDrawable.DEBUG) {
                        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("Level: ", i6, ", PluggedIn: ", z5, ", Charging: ");
                        m76m.append(z6);
                        m76m.append(", BatteryHealth: ");
                        m76m.append(i9);
                        m76m.append(",BatteryStatus: ");
                        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m76m, i8, ", BatteryOnline: ", i7, ", IsDirectPowerMode: ");
                        AbstractC0000x2c234b15.m3m("onBatteryLevelChanged - ", AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(m76m, z4, ","), "SamsungBatteryMeterDrawable");
                    }
                    LegacySamsungBatteryMeterDrawable$postInvalidateHandler$1 legacySamsungBatteryMeterDrawable$postInvalidateHandler$1 = legacySamsungBatteryMeterDrawable.postInvalidateHandler;
                    int i10 = LegacySamsungBatteryMeterDrawable.MSG_POST_INVALIDATE;
                    if (legacySamsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i10)) {
                        return;
                    }
                    legacySamsungBatteryMeterDrawable.postInvalidateHandler.sendEmptyMessage(i10);
                }
            }
        };
        this.mUserChangedCallback = new C10514();
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                ((BatteryMeterView) batteryMeterViewController.mView).mShowPercentSamsungSetting = batteryMeterViewController.mSettingsHelper.isShowBatteryPercentInStatusBar();
                ((BatteryMeterView) batteryMeterViewController.mView).updateShowPercent();
            }
        };
        this.mUserTracker = userTracker;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mMainHandler = handler;
        this.mContentResolver = contentResolver;
        this.mBatteryController = batteryController;
        BatteryMeterView batteryMeterView2 = (BatteryMeterView) this.mView;
        Objects.requireNonNull(batteryController);
        batteryMeterView2.mBatteryEstimateFetcher = new BatteryMeterViewController$$ExternalSyntheticLambda1(batteryController);
        ((BatteryMeterView) this.mView).mDisplayShieldEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.BATTERY_SHIELD_ICON);
        this.mSlotBattery = getResources().getString(17042907);
        this.mSettingObserver = new SettingObserver(handler);
        this.mSettingsHelper = settingsHelper;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mSlimIndicatorVisibilityHelper = new SlimIndicatorVisibilityHelper(this, 0);
        this.mIndicatorScaleGardener = indicatorScaleGardener;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        if (!this.mIsSubscribedForTunerUpdates && !this.mIgnoreTunerUpdates) {
            this.mTunerService.addTunable(this.mTunable, "icon_blacklist");
            this.mIsSubscribedForTunerUpdates = true;
        }
        ((BatteryControllerImpl) this.mBatteryController).addCallback(this.mBatteryStateChangeCallback);
        BatteryMeterView batteryMeterView = (BatteryMeterView) this.mView;
        if (batteryMeterView.getResources().getBoolean(R.bool.use_work_around_for_battery_level) && "ShadeHeaderController".equals(batteryMeterView.getTag())) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            batteryMeterView.mBatteryIconView.setLayerType(1, paint);
        } else {
            batteryMeterView.mBatteryIconView.setLayerType(1, null);
        }
        Uri[] uriArr = {Settings.System.getUriFor("display_battery_percentage")};
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.registerCallback(this.mSettingsListener, uriArr);
        ((BatteryMeterView) this.mView).mShowPercentSamsungSetting = settingsHelper.isShowBatteryPercentInStatusBar();
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
}
