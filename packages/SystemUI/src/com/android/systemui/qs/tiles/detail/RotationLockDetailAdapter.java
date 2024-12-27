package com.android.systemui.qs.tiles.detail;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSSwitchPreference;
import com.android.systemui.qs.tiles.SRotationLockTile;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RotationLockDetailAdapter implements DetailAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mButtonOnNavigationBarOption;
    public SwitchCompat mButtonOnNavigationBarSwitch;
    public TextView mButtonOnNavigationBarTitle;
    public SecQSSwitchPreference mCallScreenOption;
    public final Context mContext;
    public final RotationLockController mController;
    public final Handler mHandler;
    public SecQSSwitchPreference mHomeScreenOption;
    public SecQSSwitchPreference mLockScreenOption;
    public SwitchCompat mLockSwitch;
    public TextView mLockTitle;
    public final PluginLockMediator mPluginLockMediator;
    public final SRotationLockTile mRotationLockTile;
    public final SharedPreferences.Editor mRotationLockTilePrefEditor;
    private final SettingsHelper mSettingsHelper;

    public RotationLockDetailAdapter(Context context, Handler handler, SettingsHelper settingsHelper, RotationLockController rotationLockController, PluginLockMediator pluginLockMediator, SharedPreferences.Editor editor, SRotationLockTile sRotationLockTile) {
        this.mHandler = handler;
        this.mContext = context;
        this.mRotationLockTile = sRotationLockTile;
        this.mSettingsHelper = settingsHelper;
        this.mController = rotationLockController;
        this.mPluginLockMediator = pluginLockMediator;
        this.mRotationLockTilePrefEditor = editor;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_rotation, viewGroup, false);
        SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_ROTATE_DETAIL);
        ViewGroup viewGroup2 = (ViewGroup) inflate;
        SecQSSwitchPreference inflateSwitch = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(inflateSwitch);
        this.mHomeScreenOption = inflateSwitch;
        SecQSSwitchPreference inflateSwitch2 = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(inflateSwitch2);
        this.mLockScreenOption = inflateSwitch2;
        SecQSSwitchPreference inflateSwitch3 = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(inflateSwitch3);
        this.mCallScreenOption = inflateSwitch3;
        if (QpRune.QUICK_TABLET) {
            this.mHomeScreenOption.setVisibility(8);
            this.mLockScreenOption.setVisibility(8);
            this.mCallScreenOption.setVisibility(8);
        }
        final TextView textView = (TextView) this.mHomeScreenOption.findViewById(R.id.title);
        textView.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_home_screen));
        final SwitchCompat switchCompat = (SwitchCompat) this.mHomeScreenOption.findViewById(R.id.title_switch);
        switchCompat.setChecked(this.mSettingsHelper.isHomeScreenRotationAllowed());
        if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
            switchCompat.setClickable(false);
        }
        this.mHomeScreenOption.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switchCompat.setChecked(!r0.isChecked());
            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
                final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                int i = RotationLockDetailAdapter.$r8$clinit;
                rotationLockDetailAdapter.getClass();
                rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        RotationLockDetailAdapter.this.mSettingsHelper.setHomeScreenRotationAllowed(z);
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_HOME_SCREEN_SWITCH);
                        RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, z);
                        RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" homeScreen rotate allowed : "), z, "RotationLockDetailAdapter");
                    }
                }, 30L);
                switchCompat.announceForAccessibility(RotationLockDetailAdapter.this.mContext.getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
            }
        });
        switchCompat.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                if (!(view2 instanceof SwitchCompat)) {
                    Log.e("RotationLockDetailAdapter", "homeSwitch View is not an instance of SwitchCompat");
                } else {
                    switchCompat.setChecked(((SwitchCompat) view2).isChecked());
                }
            }
        });
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext)) {
            TextView textView2 = (TextView) this.mLockScreenOption.findViewById(R.id.title);
            this.mLockTitle = textView2;
            textView2.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_lock_screen));
            this.mLockSwitch = (SwitchCompat) this.mLockScreenOption.findViewById(R.id.title_switch);
            if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
                this.mLockSwitch.setClickable(false);
            }
            this.mLockSwitch.setChecked(this.mSettingsHelper.isLockScreenRotationAllowed());
            boolean z = LsRune.WALLPAPER_ROTATABLE_WALLPAPER;
            PluginLockMediator pluginLockMediator = this.mPluginLockMediator;
            if (!z || WallpaperUtils.isSubDisplay() || pluginLockMediator.isRotateMenuHide()) {
                boolean z2 = WallpaperUtils.mIsUltraPowerSavingMode;
                boolean z3 = WallpaperUtils.mIsEmergencyMode;
                if (!z2 && !z3 && (WallpaperUtils.isVideoWallpaper(this.mContext) || ((WallpaperManager.getInstance(this.mContext).semGetWallpaperType(WallpaperUtils.sCurrentWhich) == 3 && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).isPluginWallpaperRequired(WallpaperUtils.sCurrentWhich) && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).containsVideo(WallpaperUtils.sCurrentWhich)) || pluginLockMediator.isRotateMenuHide()))) {
                    this.mLockScreenOption.setOnClickListener(null);
                    this.mLockScreenOption.setClickable(false);
                    this.mLockSwitch.setOnCheckedChangeListener(null);
                    this.mLockSwitch.setEnabled(false);
                    this.mLockTitle.setAlpha(0.4f);
                }
            }
            this.mLockScreenOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    RotationLockDetailAdapter.this.mLockSwitch.setChecked(!r0.isChecked());
                }
            });
            this.mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.8
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z4) {
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RotationLockDetailAdapter.this.mSettingsHelper.setLockScreenRotationAllowed(z4);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_LOCK_SCREEN_SWITCH);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, z4);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" lockScreen is rotate allowed : "), z4, "RotationLockDetailAdapter");
                        }
                    }, 30L);
                    RotationLockDetailAdapter rotationLockDetailAdapter2 = RotationLockDetailAdapter.this;
                    rotationLockDetailAdapter2.mLockSwitch.announceForAccessibility(rotationLockDetailAdapter2.mContext.getString(z4 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            this.mLockSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (!(view2 instanceof SwitchCompat)) {
                        Log.e("RotationLockDetailAdapter", "mLockSwitch View is not an instance of SwitchCompat");
                    } else {
                        RotationLockDetailAdapter.this.mLockSwitch.setChecked(((SwitchCompat) view2).isChecked());
                    }
                }
            });
        } else {
            this.mLockScreenOption.setVisibility(8);
        }
        final TextView textView3 = (TextView) this.mCallScreenOption.findViewById(R.id.title);
        textView3.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_call_screen));
        final SwitchCompat switchCompat2 = (SwitchCompat) this.mCallScreenOption.findViewById(R.id.title_switch);
        if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
            switchCompat2.setClickable(false);
        }
        switchCompat2.setChecked(this.mSettingsHelper.isCallScreenRotationAllowed());
        this.mCallScreenOption.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switchCompat2.setChecked(!r0.isChecked());
            }
        });
        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.11
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, final boolean z4) {
                final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                int i = RotationLockDetailAdapter.$r8$clinit;
                rotationLockDetailAdapter.getClass();
                rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        RotationLockDetailAdapter.this.mSettingsHelper.setCallScreenRotationAllowed(z4);
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_CALL_SCREEN_SWITCH);
                        RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH, z4);
                        RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" callScreen is rotate allowed :"), z4, "RotationLockDetailAdapter");
                    }
                }, 30L);
                switchCompat2.announceForAccessibility(RotationLockDetailAdapter.this.mContext.getString(z4 ? R.string.switch_bar_on : R.string.switch_bar_off));
            }
        });
        switchCompat2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                if (!(view2 instanceof SwitchCompat)) {
                    Log.e("RotationLockDetailAdapter", "callSwitch View is not an instance of SwitchCompat");
                } else {
                    switchCompat2.setChecked(((SwitchCompat) view2).isChecked());
                }
            }
        });
        if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
            View inflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_rotation_navigation_button, viewGroup, false);
            viewGroup2.addView(inflate2);
            View findViewById = inflate2.findViewById(R.id.button_on_navigation_bar_option_parent);
            this.mButtonOnNavigationBarOption = findViewById;
            SecQSSwitchPreference inflateSwitch4 = SecQSSwitchPreference.inflateSwitch(this.mContext, (ViewGroup) findViewById);
            ((ViewGroup) this.mButtonOnNavigationBarOption).addView(inflateSwitch4);
            inflateSwitch4.setClickable(false);
            this.mButtonOnNavigationBarOption = inflateSwitch4;
            TextView textView4 = (TextView) inflateSwitch4.findViewById(R.id.title);
            this.mButtonOnNavigationBarTitle = textView4;
            textView4.setText(R.string.quick_settings_manual_rotation_button_on_navigation_bar_title);
            this.mButtonOnNavigationBarOption.findViewById(R.id.title_summary).setVisibility(8);
            SwitchCompat switchCompat3 = (SwitchCompat) this.mButtonOnNavigationBarOption.findViewById(R.id.title_switch);
            this.mButtonOnNavigationBarSwitch = switchCompat3;
            switchCompat3.setChecked(this.mSettingsHelper.isNavigationBarRotateSuggestionEnabled());
            if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
                this.mButtonOnNavigationBarSwitch.setClickable(false);
            }
            updateButtonOnNavigationBarOption(!getToggleState().booleanValue());
        } else {
            inflate.findViewById(R.id.button_on_navigation_bar_option_parent).setVisibility(8);
        }
        this.mHomeScreenOption.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.13
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setContentDescription(textView.getText().toString() + ", " + (!switchCompat.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off)) + ", Switch");
            }
        });
        SecQSSwitchPreference secQSSwitchPreference = this.mLockScreenOption;
        final TextView textView5 = this.mLockTitle;
        final SwitchCompat switchCompat4 = this.mLockSwitch;
        secQSSwitchPreference.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.13
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setContentDescription(textView5.getText().toString() + ", " + (!switchCompat4.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat4.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off)) + ", Switch");
            }
        });
        this.mCallScreenOption.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.13
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setContentDescription(textView3.getText().toString() + ", " + (!switchCompat2.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat2.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off)) + ", Switch");
            }
        });
        View view2 = this.mButtonOnNavigationBarOption;
        final TextView textView6 = this.mButtonOnNavigationBarTitle;
        final SwitchCompat switchCompat5 = this.mButtonOnNavigationBarSwitch;
        view2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.13
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view22, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view22, accessibilityNodeInfo);
                accessibilityNodeInfo.setContentDescription(textView6.getText().toString() + ", " + (!switchCompat5.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat5.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off)) + ", Switch");
            }
        });
        return inflate;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 123;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.quick_settings_rotation_lock_auto_rotate);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return Boolean.valueOf(((QSTile.BooleanState) this.mRotationLockTile.mState).value);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
        boolean isRotationLockTileBlocked = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isRotationLockTileBlocked();
        SRotationLockTile sRotationLockTile = this.mRotationLockTile;
        if (isRotationLockTileBlocked) {
            sRotationLockTile.showItPolicyToast();
            return;
        }
        sRotationLockTile.fireToggleStateChanged(z);
        this.mController.setRotationLocked("SRotationLockTile#setToggleState", !z);
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_DETAIL_SWITCH, "location", "auto rotate");
        if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
            updateButtonOnNavigationBarOption(!z);
        }
    }

    public final void updateButtonOnNavigationBarOption(boolean z) {
        if (this.mButtonOnNavigationBarOption == null || this.mButtonOnNavigationBarSwitch == null) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED_HARD_KEY) {
            NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class)).getNavStateManager();
            if (!navBarStateManagerImpl.isGestureMode() && !navBarStateManagerImpl.isTaskBarEnabled(false)) {
                z = false;
            }
        }
        if (z) {
            this.mButtonOnNavigationBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.14
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    RotationLockDetailAdapter.this.mSettingsHelper.setNavigationBarRotateSuggestion(z2);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED);
                    RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED, z2);
                    RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                    RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    rotationLockDetailAdapter.mButtonOnNavigationBarSwitch.announceForAccessibility(rotationLockDetailAdapter.mContext.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            this.mButtonOnNavigationBarOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.15
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RotationLockDetailAdapter.this.mButtonOnNavigationBarSwitch.setChecked(!r0.isChecked());
                }
            });
            this.mButtonOnNavigationBarSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.16
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (!(view instanceof SwitchCompat)) {
                        Log.e("RotationLockDetailAdapter", "mButtonOnNavigationBarSwitch View is not an instance of SwitchCompat");
                    } else {
                        RotationLockDetailAdapter.this.mButtonOnNavigationBarSwitch.setChecked(((SwitchCompat) view).isChecked());
                    }
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
}
