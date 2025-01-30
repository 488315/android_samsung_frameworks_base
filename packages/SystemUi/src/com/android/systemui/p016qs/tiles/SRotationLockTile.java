package com.android.systemui.p016qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.p016qs.QSBackupRestoreManager;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.SecQSSwitchPreference;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.sec.ims.gls.GlsIntent;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SRotationLockTile extends SQSTileImpl implements BatteryController.BatteryStateChangeCallback, QsResetSettingsManager.DemoResetSettingsApplier {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final QSTileImpl.AnimationIcon mAutoToLandscape;
    public final QSTileImpl.AnimationIcon mAutoToPortrait;
    public final BatteryController mBatteryController;
    public final C22754 mCallback;
    public final RotationLockController mController;
    public final RotationLockDetailAdapter mDetailAdapter;
    public final QSTileImpl.AnimationIcon mLandscapeToAuto;
    public final PluginLockMediator mPluginLockMediator;
    public final QSTileImpl.AnimationIcon mPortraitToAuto;
    public final SensorPrivacyManager mPrivacyManager;
    public final Resources mResources;
    public final SharedPreferences.Editor mRotationLockTilePrefEditor;
    public boolean mRotationLocked;
    public final SRotationLockTile$$ExternalSyntheticLambda0 mSensorPrivacyChangedListener;
    public final C22732 mSetting;
    public final C22721 mSettingsCallback;
    public final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RotationLockDetailAdapter implements DetailAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public View mButtonOnNavigationBarOption;
        public SwitchCompat mButtonOnNavigationBarSwitch;
        public TextView mButtonOnNavigationBarTitle;
        public SecQSSwitchPreference mCallScreenOption;
        public SecQSSwitchPreference mHomeScreenOption;
        public SecQSSwitchPreference mLockScreenOption;
        public SwitchCompat mLockSwitch;
        public TextView mLockTitle;

        public /* synthetic */ RotationLockDetailAdapter(SRotationLockTile sRotationLockTile, int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0111  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0130  */
        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            boolean z;
            int i = SRotationLockTile.$r8$clinit;
            SRotationLockTile sRotationLockTile = SRotationLockTile.this;
            View inflate = LayoutInflater.from(sRotationLockTile.mContext).inflate(R.layout.sec_qs_detail_rotation, viewGroup, false);
            SystemUIAnalytics.sendScreenViewLog("QPD102");
            ViewGroup viewGroup2 = (ViewGroup) inflate;
            Context context2 = sRotationLockTile.mContext;
            SecQSSwitchPreference inflateSwitch = SecQSSwitchPreference.inflateSwitch(context2, viewGroup2);
            viewGroup2.addView(inflateSwitch);
            this.mHomeScreenOption = inflateSwitch;
            SecQSSwitchPreference inflateSwitch2 = SecQSSwitchPreference.inflateSwitch(context2, viewGroup2);
            viewGroup2.addView(inflateSwitch2);
            this.mLockScreenOption = inflateSwitch2;
            SecQSSwitchPreference inflateSwitch3 = SecQSSwitchPreference.inflateSwitch(context2, viewGroup2);
            viewGroup2.addView(inflateSwitch3);
            this.mCallScreenOption = inflateSwitch3;
            if (QpRune.QUICK_TABLET) {
                this.mHomeScreenOption.setVisibility(8);
                this.mLockScreenOption.setVisibility(8);
                this.mCallScreenOption.setVisibility(8);
            }
            final TextView textView = (TextView) this.mHomeScreenOption.findViewById(R.id.title);
            textView.setText(context2.getText(R.string.quick_settings_rotation_detail_home_screen));
            final SwitchCompat switchCompat = (SwitchCompat) this.mHomeScreenOption.findViewById(R.id.title_switch);
            SettingsHelper settingsHelper = sRotationLockTile.mSettingsHelper;
            switchCompat.setChecked(settingsHelper.isHomeScreenRotationAllowed());
            if (settingsHelper.isVoiceAssistantEnabled()) {
                switchCompat.setClickable(false);
            }
            this.mHomeScreenOption.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    switchCompat.setChecked(!r0.isChecked());
                }
            });
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.5
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z2) {
                    Context context3;
                    int i2;
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i3 = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    int i4 = SRotationLockTile.$r8$clinit;
                    ((SQSTileImpl) SRotationLockTile.this).mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Settings.Global.putInt(SRotationLockTile.this.mSettingsHelper.mContext.getContentResolver(), "sehome_portrait_mode_only", !z2 ? 1 : 0);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1010");
                            SRotationLockTile.this.mRotationLockTilePrefEditor.putBoolean("QPDS1010", z2);
                            SRotationLockTile.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder(" homeScreen rotate allowed : "), z2, "SRotationLockTile");
                        }
                    }, 30L);
                    SwitchCompat switchCompat2 = switchCompat;
                    SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                    if (z2) {
                        context3 = sRotationLockTile2.mContext;
                        i2 = R.string.switch_bar_on;
                    } else {
                        context3 = sRotationLockTile2.mContext;
                        i2 = R.string.switch_bar_off;
                    }
                    switchCompat2.announceForAccessibility(context3.getString(i2));
                }
            });
            switchCompat.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    switchCompat.setChecked(((SwitchCompat) view2).isChecked());
                }
            });
            if (DeviceState.shouldEnableKeyguardScreenRotation(context2)) {
                TextView textView2 = (TextView) this.mLockScreenOption.findViewById(R.id.title);
                this.mLockTitle = textView2;
                textView2.setText(context2.getText(R.string.quick_settings_rotation_detail_lock_screen));
                this.mLockSwitch = (SwitchCompat) this.mLockScreenOption.findViewById(R.id.title_switch);
                if (settingsHelper.isVoiceAssistantEnabled()) {
                    this.mLockSwitch.setClickable(false);
                }
                this.mLockSwitch.setChecked(settingsHelper.isLockScreenRotationAllowed());
                boolean z2 = LsRune.WALLPAPER_ROTATABLE_WALLPAPER;
                PluginLockMediator pluginLockMediator = sRotationLockTile.mPluginLockMediator;
                if (!z2 || WallpaperUtils.isSubDisplay() || ((PluginLockMediatorImpl) pluginLockMediator).isRotateMenuHide()) {
                    boolean z3 = WallpaperUtils.mIsUltraPowerSavingMode;
                    boolean z4 = WallpaperUtils.mIsEmergencyMode;
                    boolean isLiveWallpaperEnabled = settingsHelper.isLiveWallpaperEnabled(WallpaperUtils.isSubDisplay());
                    if (!z3 && !z4 && !isLiveWallpaperEnabled && (WallpaperUtils.isVideoWallpaper() || ((PluginLockMediatorImpl) pluginLockMediator).isRotateMenuHide())) {
                        z = false;
                        if (z) {
                            this.mLockScreenOption.setOnClickListener(null);
                            this.mLockScreenOption.setClickable(false);
                            this.mLockSwitch.setOnCheckedChangeListener(null);
                            this.mLockSwitch.setEnabled(false);
                            this.mLockTitle.setAlpha(0.4f);
                        } else {
                            this.mLockScreenOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.7
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    RotationLockDetailAdapter.this.mLockSwitch.setChecked(!r0.isChecked());
                                }
                            });
                            this.mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.8
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z5) {
                                    Context context3;
                                    int i2;
                                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                                    int i3 = RotationLockDetailAdapter.$r8$clinit;
                                    rotationLockDetailAdapter.getClass();
                                    int i4 = SRotationLockTile.$r8$clinit;
                                    ((SQSTileImpl) SRotationLockTile.this).mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SettingsHelper settingsHelper2 = SRotationLockTile.this.mSettingsHelper;
                                            boolean z6 = z5;
                                            Context context4 = settingsHelper2.mContext;
                                            if (DeviceState.shouldEnableKeyguardScreenRotation(context4)) {
                                                Settings.System.putIntForUser(context4.getContentResolver(), "lock_screen_allow_rotation", z6 ? 1 : 0, -2);
                                                settingsHelper2.mItemLists.get("lock_screen_allow_rotation").mIntValue = z6 ? 1 : 0;
                                            }
                                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1011");
                                            SRotationLockTile.this.mRotationLockTilePrefEditor.putBoolean("QPDS1010", z5);
                                            SRotationLockTile.this.mRotationLockTilePrefEditor.commit();
                                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder(" lockScreen is rotate allowed : "), z5, "SRotationLockTile");
                                        }
                                    }, 30L);
                                    RotationLockDetailAdapter rotationLockDetailAdapter2 = RotationLockDetailAdapter.this;
                                    SwitchCompat switchCompat2 = rotationLockDetailAdapter2.mLockSwitch;
                                    SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                                    if (z5) {
                                        context3 = sRotationLockTile2.mContext;
                                        i2 = R.string.switch_bar_on;
                                    } else {
                                        context3 = sRotationLockTile2.mContext;
                                        i2 = R.string.switch_bar_off;
                                    }
                                    switchCompat2.announceForAccessibility(context3.getString(i2));
                                }
                            });
                            this.mLockSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.9
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    RotationLockDetailAdapter.this.mLockSwitch.setChecked(((SwitchCompat) view2).isChecked());
                                }
                            });
                        }
                    }
                }
                z = true;
                if (z) {
                }
            } else {
                this.mLockScreenOption.setVisibility(8);
            }
            final TextView textView3 = (TextView) this.mCallScreenOption.findViewById(R.id.title);
            textView3.setText(context2.getText(R.string.quick_settings_rotation_detail_call_screen));
            final SwitchCompat switchCompat2 = (SwitchCompat) this.mCallScreenOption.findViewById(R.id.title_switch);
            if (settingsHelper.isVoiceAssistantEnabled()) {
                switchCompat2.setClickable(false);
            }
            switchCompat2.setChecked(settingsHelper.isCallScreenRotationAllowed());
            this.mCallScreenOption.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    switchCompat2.setChecked(!r0.isChecked());
                }
            });
            switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.11
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z5) {
                    Context context3;
                    int i2;
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i3 = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    int i4 = SRotationLockTile.$r8$clinit;
                    ((SQSTileImpl) SRotationLockTile.this).mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Settings.Global.putInt(SRotationLockTile.this.mSettingsHelper.mContext.getContentResolver(), "call_auto_rotation", z5 ? 1 : 0);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1012");
                            SRotationLockTile.this.mRotationLockTilePrefEditor.putBoolean("QPDS1012", z5);
                            SRotationLockTile.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder(" callScreen is rotate allowed :"), z5, "SRotationLockTile");
                        }
                    }, 30L);
                    SwitchCompat switchCompat3 = switchCompat2;
                    SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                    if (z5) {
                        context3 = sRotationLockTile2.mContext;
                        i2 = R.string.switch_bar_on;
                    } else {
                        context3 = sRotationLockTile2.mContext;
                        i2 = R.string.switch_bar_off;
                    }
                    switchCompat3.announceForAccessibility(context3.getString(i2));
                }
            });
            switchCompat2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    switchCompat2.setChecked(((SwitchCompat) view2).isChecked());
                }
            });
            if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
                View inflate2 = LayoutInflater.from(context2).inflate(R.layout.sec_qs_detail_rotation_navigation_button, viewGroup, false);
                viewGroup2.addView(inflate2);
                View findViewById = inflate2.findViewById(R.id.button_on_navigation_bar_option_parent);
                this.mButtonOnNavigationBarOption = findViewById;
                SecQSSwitchPreference inflateSwitch4 = SecQSSwitchPreference.inflateSwitch(context2, (ViewGroup) findViewById);
                ((ViewGroup) this.mButtonOnNavigationBarOption).addView(inflateSwitch4);
                inflateSwitch4.setClickable(false);
                this.mButtonOnNavigationBarOption = inflateSwitch4;
                TextView textView4 = (TextView) inflateSwitch4.findViewById(R.id.title);
                this.mButtonOnNavigationBarTitle = textView4;
                textView4.setText(R.string.quick_settings_manual_rotation_button_on_navigation_bar_title);
                this.mButtonOnNavigationBarOption.findViewById(R.id.title_summary).setVisibility(8);
                SwitchCompat switchCompat3 = (SwitchCompat) this.mButtonOnNavigationBarOption.findViewById(R.id.title_switch);
                this.mButtonOnNavigationBarSwitch = switchCompat3;
                switchCompat3.setChecked(settingsHelper.isNavigationBarRotateSuggestionEnabled());
                if (settingsHelper.isVoiceAssistantEnabled()) {
                    this.mButtonOnNavigationBarSwitch.setClickable(false);
                }
                updateButtonOnNavigationBarOption(!getToggleState().booleanValue());
            } else {
                inflate.findViewById(R.id.button_on_navigation_bar_option_parent).setVisibility(8);
            }
            this.mHomeScreenOption.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.13
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    String string;
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    if (!switchCompat.isEnabled()) {
                        SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                        int i2 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile2.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch);
                    } else if (switchCompat.isChecked()) {
                        SRotationLockTile sRotationLockTile3 = SRotationLockTile.this;
                        int i3 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile3.mContext.getString(R.string.switch_bar_on);
                    } else {
                        SRotationLockTile sRotationLockTile4 = SRotationLockTile.this;
                        int i4 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile4.mContext.getString(R.string.switch_bar_off);
                    }
                    accessibilityNodeInfo.setContentDescription(textView.getText().toString() + ", " + string + ", Switch");
                }
            });
            SecQSSwitchPreference secQSSwitchPreference = this.mLockScreenOption;
            final TextView textView5 = this.mLockTitle;
            final SwitchCompat switchCompat4 = this.mLockSwitch;
            secQSSwitchPreference.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.13
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    String string;
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    if (!switchCompat4.isEnabled()) {
                        SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                        int i2 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile2.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch);
                    } else if (switchCompat4.isChecked()) {
                        SRotationLockTile sRotationLockTile3 = SRotationLockTile.this;
                        int i3 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile3.mContext.getString(R.string.switch_bar_on);
                    } else {
                        SRotationLockTile sRotationLockTile4 = SRotationLockTile.this;
                        int i4 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile4.mContext.getString(R.string.switch_bar_off);
                    }
                    accessibilityNodeInfo.setContentDescription(textView5.getText().toString() + ", " + string + ", Switch");
                }
            });
            this.mCallScreenOption.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.13
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    String string;
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    if (!switchCompat2.isEnabled()) {
                        SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                        int i2 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile2.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch);
                    } else if (switchCompat2.isChecked()) {
                        SRotationLockTile sRotationLockTile3 = SRotationLockTile.this;
                        int i3 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile3.mContext.getString(R.string.switch_bar_on);
                    } else {
                        SRotationLockTile sRotationLockTile4 = SRotationLockTile.this;
                        int i4 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile4.mContext.getString(R.string.switch_bar_off);
                    }
                    accessibilityNodeInfo.setContentDescription(textView3.getText().toString() + ", " + string + ", Switch");
                }
            });
            View view2 = this.mButtonOnNavigationBarOption;
            final TextView textView6 = this.mButtonOnNavigationBarTitle;
            final SwitchCompat switchCompat5 = this.mButtonOnNavigationBarSwitch;
            view2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.13
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view22, AccessibilityNodeInfo accessibilityNodeInfo) {
                    String string;
                    super.onInitializeAccessibilityNodeInfo(view22, accessibilityNodeInfo);
                    if (!switchCompat5.isEnabled()) {
                        SRotationLockTile sRotationLockTile2 = SRotationLockTile.this;
                        int i2 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile2.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch);
                    } else if (switchCompat5.isChecked()) {
                        SRotationLockTile sRotationLockTile3 = SRotationLockTile.this;
                        int i3 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile3.mContext.getString(R.string.switch_bar_on);
                    } else {
                        SRotationLockTile sRotationLockTile4 = SRotationLockTile.this;
                        int i4 = SRotationLockTile.$r8$clinit;
                        string = sRotationLockTile4.mContext.getString(R.string.switch_bar_off);
                    }
                    accessibilityNodeInfo.setContentDescription(textView6.getText().toString() + ", " + string + ", Switch");
                }
            });
            return inflate;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final int getMetricsCategory() {
            return 123;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return null;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = SRotationLockTile.$r8$clinit;
            return SRotationLockTile.this.mContext.getString(R.string.quick_settings_rotation_lock_auto_rotate);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = SRotationLockTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) SRotationLockTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final void setToggleState(boolean z) {
            boolean isRotationLockTileBlocked = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isRotationLockTileBlocked();
            SRotationLockTile sRotationLockTile = SRotationLockTile.this;
            if (isRotationLockTileBlocked) {
                int i = SRotationLockTile.$r8$clinit;
                sRotationLockTile.showItPolicyToast();
                return;
            }
            sRotationLockTile.fireToggleStateChanged(z);
            sRotationLockTile.mController.setRotationLocked(!z);
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1008", GlsIntent.Extras.EXTRA_LOCATION, "auto rotate");
            if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
                updateButtonOnNavigationBarOption(!z);
            }
        }

        public final void updateButtonOnNavigationBarOption(boolean z) {
            if (this.mButtonOnNavigationBarOption == null || this.mButtonOnNavigationBarSwitch == null) {
                return;
            }
            if (BasicRune.NAVBAR_ENABLED_HARD_KEY) {
                NavBarStateManager navStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(0);
                if (!navStateManager.isGestureMode() && !navStateManager.isTaskBarEnabled(false)) {
                    z = false;
                }
            }
            if (z) {
                this.mButtonOnNavigationBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.14
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        Context context;
                        int i;
                        Settings.Global.putInt(SRotationLockTile.this.mSettingsHelper.mContext.getContentResolver(), "navigation_bar_rotate_suggestion_enabled", z2 ? 1 : 0);
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1009");
                        SRotationLockTile.this.mRotationLockTilePrefEditor.putBoolean("QPDS1009", z2);
                        SRotationLockTile.this.mRotationLockTilePrefEditor.commit();
                        RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                        SwitchCompat switchCompat = rotationLockDetailAdapter.mButtonOnNavigationBarSwitch;
                        SRotationLockTile sRotationLockTile = SRotationLockTile.this;
                        if (z2) {
                            context = sRotationLockTile.mContext;
                            i = R.string.switch_bar_on;
                        } else {
                            context = sRotationLockTile.mContext;
                            i = R.string.switch_bar_off;
                        }
                        switchCompat.announceForAccessibility(context.getString(i));
                    }
                });
                this.mButtonOnNavigationBarOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.15
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RotationLockDetailAdapter.this.mButtonOnNavigationBarSwitch.setChecked(!r0.isChecked());
                    }
                });
                this.mButtonOnNavigationBarSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.RotationLockDetailAdapter.16
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RotationLockDetailAdapter.this.mButtonOnNavigationBarSwitch.setChecked(((SwitchCompat) view).isChecked());
                    }
                });
                this.mButtonOnNavigationBarOption.setClickable(true);
                this.mButtonOnNavigationBarOption.findViewById(R.id.title).setAlpha(1.0f);
            } else {
                this.mButtonOnNavigationBarSwitch.setOnCheckedChangeListener(null);
                this.mButtonOnNavigationBarOption.setOnClickListener(null);
                this.mButtonOnNavigationBarOption.setClickable(false);
                this.mButtonOnNavigationBarOption.findViewById(R.id.title).setAlpha(0.4f);
            }
            this.mButtonOnNavigationBarSwitch.setEnabled(z);
        }

        private RotationLockDetailAdapter() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.tiles.SRotationLockTile$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.qs.tiles.SRotationLockTile$4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.tiles.SRotationLockTile$2] */
    public SRotationLockTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SettingsHelper settingsHelper, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings, PluginLockMediator pluginLockMediator) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(android.R.drawable.ic_perm_group_social_info);
        this.mAutoToPortrait = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_rotation_lock_auto_to_portrait, R.drawable.quick_panel_icon_rotation_lock_auto_to_portrait_010);
        this.mPortraitToAuto = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_rotation_lock_portrait_to_auto, R.drawable.quick_panel_icon_rotation_lock_portrait_to_auto_010);
        this.mAutoToLandscape = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_rotation_lock_auto_to_landscape, R.drawable.quick_panel_icon_rotation_lock_auto_to_landscape_010);
        this.mLandscapeToAuto = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_rotation_lock_landscape_to_auto, R.drawable.quick_panel_icon_rotation_lock_landscape_to_auto_010);
        this.mStateBeforeClick = new QSTile.BooleanState();
        Uri[] uriArr = {Settings.System.getUriFor("accelerometer_rotation")};
        ?? r1 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor("accelerometer_rotation"))) {
                    Log.d("SRotationLockTile", " OnChangedCallback : ");
                    SRotationLockTile.this.refreshState(null);
                }
            }
        };
        this.mSettingsCallback = r1;
        ?? r2 = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.4
            @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
            public final void onRotationLockStateChanged(boolean z) {
                Boolean valueOf = Boolean.valueOf(z);
                int i = SRotationLockTile.$r8$clinit;
                SRotationLockTile.this.refreshState(valueOf);
            }
        };
        this.mCallback = r2;
        this.mSensorPrivacyChangedListener = new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0
            public final void onSensorPrivacyChanged(int i, boolean z) {
                SRotationLockTile.this.refreshState(null);
            }
        };
        this.mController = rotationLockController;
        rotationLockController.getClass();
        rotationLockController.observe(((QSTileImpl) this).mLifecycle, r2);
        this.mPrivacyManager = sensorPrivacyManager;
        this.mBatteryController = batteryController;
        this.mDetailAdapter = new RotationLockDetailAdapter(this, 0);
        this.mSettingsHelper = settingsHelper;
        this.mResources = resources;
        this.mSetting = new SettingObserver(secureSettings, ((SQSTileImpl) this).mHandler, "camera_autorotate", qSHost.getUserContext().getUserId()) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.2
            @Override // com.android.systemui.p016qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                SRotationLockTile sRotationLockTile = SRotationLockTile.this;
                int i2 = SRotationLockTile.$r8$clinit;
                sRotationLockTile.handleRefreshState(null);
            }
        };
        batteryController.observe(((QSTileImpl) this).mLifecycle, this);
        this.mPluginLockMediator = pluginLockMediator;
        settingsHelper.registerCallback(r1, uriArr);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("quick_pref", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            this.mRotationLockTilePrefEditor = edit;
            if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
                edit.putBoolean("QPDS1009", settingsHelper.isNavigationBarRotateSuggestionEnabled());
            }
            edit.putBoolean("QPDS1010", settingsHelper.isHomeScreenRotationAllowed());
            edit.putBoolean("QPDS1011", settingsHelper.isLockScreenRotationAllowed());
            edit.putBoolean("QPDS1012", settingsHelper.isCallScreenRotationAllowed());
            edit.commit();
        }
        if (QpRune.QUICK_TABLET) {
            return;
        }
        ((QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class)).addCallback("AutoRotate", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.3
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                SRotationLockTile.this.getClass();
                return true;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                String str;
                String str2;
                String str3;
                String str4;
                int i = SRotationLockTile.$r8$clinit;
                StringBuilder sb = new StringBuilder("TAG::autorotate_rotationlock::");
                SRotationLockTile sRotationLockTile = SRotationLockTile.this;
                if (z) {
                    str = "" + sRotationLockTile.mController.isRotationLocked();
                    StringBuilder sb2 = new StringBuilder("");
                    SettingsHelper settingsHelper2 = sRotationLockTile.mSettingsHelper;
                    sb2.append(settingsHelper2.isHomeScreenRotationAllowed());
                    str3 = sb2.toString();
                    str4 = "" + settingsHelper2.isLockScreenRotationAllowed();
                    str2 = "" + settingsHelper2.isCallScreenRotationAllowed();
                } else {
                    sRotationLockTile.getClass();
                    str = null;
                    str2 = null;
                    str3 = null;
                    str4 = null;
                }
                AppOpItem$$ExternalSyntheticOutline0.m97m(sb, str, "::TAG::autorotate_homescreen::", str3, "::TAG::autorotate_lockscreen::");
                sb.append(str4);
                sb.append("::TAG::autorotate_voicecallscreen::");
                sb.append(str2);
                Log.d("SRotationLockTile", "getBackupData: " + sb.toString());
                return sb.toString();
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                int i = SRotationLockTile.$r8$clinit;
                SRotationLockTile sRotationLockTile = SRotationLockTile.this;
                sRotationLockTile.getClass();
                String[] split = str.split("::");
                Log.d("SRotationLockTile", "restoreData: ".concat(str));
                if (split.length > 1) {
                    if (split[0].equals("autorotate_rotationlock")) {
                        String str2 = split[1];
                        if (str2 == null) {
                            Log.w("SRotationLockTile", "restoredRotationLock is null");
                            return;
                        }
                        sRotationLockTile.mController.setRotationLocked(str2.equals("true"));
                    }
                    boolean equals = split[0].equals("autorotate_homescreen");
                    SettingsHelper settingsHelper2 = sRotationLockTile.mSettingsHelper;
                    if (equals) {
                        String str3 = split[1];
                        if (str3 == null) {
                            Log.w("SRotationLockTile", "restoredHomeScreenSetting is null");
                            return;
                        } else {
                            Settings.Global.putInt(settingsHelper2.mContext.getContentResolver(), "sehome_portrait_mode_only", !str3.equals("true") ? 1 : 0);
                        }
                    }
                    if (split[0].equals("autorotate_lockscreen")) {
                        String str4 = split[1];
                        if (str4 == null) {
                            Log.w("SRotationLockTile", "restoredLockScreenSetting is null");
                            return;
                        }
                        boolean equals2 = str4.equals("true");
                        Context context = settingsHelper2.mContext;
                        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
                            Settings.System.putIntForUser(context.getContentResolver(), "lock_screen_allow_rotation", equals2 ? 1 : 0, -2);
                            settingsHelper2.mItemLists.get("lock_screen_allow_rotation").mIntValue = equals2 ? 1 : 0;
                        }
                    }
                    if (split[0].equals("autorotate_voicecallscreen")) {
                        String str5 = split[1];
                        if (str5 == null) {
                            Log.w("SRotationLockTile", "restoredCallScreenRotateSetting is null");
                        } else {
                            Settings.Global.putInt(settingsHelper2.mContext.getContentResolver(), "call_auto_rotation", str5.equals("true") ? 1 : 0);
                        }
                    }
                }
            }
        });
    }

    public static boolean isCurrentOrientationLockPortrait(RotationLockController rotationLockController, Resources resources) {
        int rotationLockOrientation = rotationLockController.getRotationLockOrientation();
        return rotationLockOrientation == 0 ? resources.getConfiguration().orientation != 2 : rotationLockOrientation != 2;
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
    public final void applyDemoResetSetting() {
        Settings.System.putInt(this.mContext.getContentResolver(), "accelerometer_rotation", 1);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final void destroy() {
        super.destroy();
        this.mSettingsHelper.unregisterCallback(this.mSettingsCallback);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 123;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.indexsearch.Searchable
    public final ArrayList getSearchWords() {
        ArrayList arrayList = new ArrayList();
        Context context = this.mContext;
        arrayList.add(context.getString(R.string.sec_quick_settings_rotation_unlocked_label).trim().toLowerCase().replaceAll("\\n", " "));
        arrayList.add(context.getString(R.string.sec_quick_settings_rotation_locked_portrait_label).trim().toLowerCase());
        arrayList.add(context.getString(R.string.sec_quick_settings_rotation_locked_landscape_label).trim().toLowerCase());
        return arrayList;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return ((QSTile.BooleanState) this.mState).label;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.SQSTile
    public final String getTileMapKey() {
        return super.getTileMapKey();
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        Log.d("SRotationLockTile", " handleClick is called:++++ ");
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isRotationLockTileBlocked()) {
            showItPolicyToast();
            return;
        }
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("handleClick "), ((QSTile.BooleanState) this.mState).state, "SRotationLockTile");
        boolean z = this.mRotationLocked;
        this.mController.setRotationLocked(!z);
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleInitialize() {
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        Log.d("SRotationLockTile", " handleSecondaryClick is called:++++ ");
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isRotationLockTileBlocked()) {
            showItPolicyToast();
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RotationLockController rotationLockController = this.mController;
        boolean isRotationLocked = rotationLockController.isRotationLocked();
        boolean z = ((BatteryControllerImpl) this.mBatteryController).mPowerSave;
        boolean isSensorPrivacyEnabled = this.mPrivacyManager.isSensorPrivacyEnabled(2);
        Context context = this.mContext;
        if (!z && !isSensorPrivacyEnabled) {
            PackageManager packageManager = context.getPackageManager();
            String rotationResolverPackageName = packageManager.getRotationResolverPackageName();
            if (rotationResolverPackageName != null && packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0) {
                rotationLockController.isCameraRotationEnabled();
            }
        }
        booleanState.value = !isRotationLocked;
        booleanState.dualTarget = true;
        boolean isCurrentOrientationLockPortrait = isCurrentOrientationLockPortrait(rotationLockController, this.mResources);
        if (isRotationLocked) {
            i = isCurrentOrientationLockPortrait ? R.string.sec_quick_settings_rotation_locked_portrait_label : R.string.sec_quick_settings_rotation_locked_landscape_label;
            booleanState.icon = isCurrentOrientationLockPortrait ? this.mAutoToPortrait : this.mAutoToLandscape;
        } else {
            booleanState.icon = isCurrentOrientationLockPortrait ? this.mPortraitToAuto : this.mLandscapeToAuto;
            i = R.string.sec_quick_settings_rotation_unlocked_label;
        }
        booleanState.label = context.getString(i);
        booleanState.state = booleanState.value ? 2 : 1;
        StringBuffer stringBuffer = new StringBuffer();
        String string = context.getString(booleanState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(context.getString(R.string.sec_quick_settings_rotation_unlocked_label));
        stringBuffer.append(",");
        if (!booleanState.value) {
            stringBuffer.append(context.getString(isCurrentOrientationLockPortrait ? R.string.sec_accessibility_rotation_set_portrait : R.string.sec_accessibility_rotation_set_landscape));
            stringBuffer.append(",");
        }
        stringBuffer.append(string);
        booleanState.contentDescription = stringBuffer.toString();
        this.mRotationLocked = isRotationLocked;
        StringBuilder sb = new StringBuilder(" mRotationLocked: ");
        sb.append(this.mRotationLocked);
        sb.append(" handleUpdateState: ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(sb, booleanState.value, " orientation = ", isCurrentOrientationLockPortrait, "SRotationLockTile");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        setUserId(i);
        handleRefreshState(null);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.SQSTile
    public final void sendTileStatusLog() {
        String tileMapKey = super.getTileMapKey();
        int i = QSTileHost.TilesMap.SID_TILE_STATE;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(i, tileMapKey);
        boolean isCurrentOrientationLockPortrait = isCurrentOrientationLockPortrait(this.mController, this.mContext.getResources());
        if (id != null) {
            String str = getTileMapValue() == 1 ? "On" : isCurrentOrientationLockPortrait ? "portrait" : "landscape";
            SharedPreferences.Editor editor = this.mRotationLockTilePrefEditor;
            editor.putString(id, str);
            editor.commit();
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl
    public final boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.BooleanState) this.mState).value;
    }
}
