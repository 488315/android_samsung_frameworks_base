package com.android.systemui.qs.tiles.detail;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.EdmMonitor;
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

/* loaded from: classes2.dex */
public class RotationLockDetailAdapter implements DetailAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mButtonOnNavigationBarOption;
    public SwitchCompat mButtonOnNavigationBarSwitch;
    public TextView mButtonOnNavigationBarTitle;
    public SecQSSwitchPreference mCallScreenOption;
    public SwitchCompat mCallSwitch;
    public TextView mCallTitle;
    public final Context mContext;
    public final RotationLockController mController;
    public final Handler mHandler;
    public SecQSSwitchPreference mHomeScreenOption;
    public SwitchCompat mHomeSwitch;
    public TextView mHomeTitle;
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
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_rotation, viewGroup, false);
        SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_ROTATE_DETAIL);
        ViewGroup viewGroup2 = (ViewGroup) viewInflate;
        SecQSSwitchPreference secQSSwitchPreferenceInflateSwitch = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(secQSSwitchPreferenceInflateSwitch);
        this.mHomeScreenOption = secQSSwitchPreferenceInflateSwitch;
        SecQSSwitchPreference secQSSwitchPreferenceInflateSwitch2 = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(secQSSwitchPreferenceInflateSwitch2);
        this.mLockScreenOption = secQSSwitchPreferenceInflateSwitch2;
        SecQSSwitchPreference secQSSwitchPreferenceInflateSwitch3 = SecQSSwitchPreference.inflateSwitch(this.mContext, viewGroup2);
        viewGroup2.addView(secQSSwitchPreferenceInflateSwitch3);
        this.mCallScreenOption = secQSSwitchPreferenceInflateSwitch3;
        if (QpRune.QUICK_TABLET) {
            this.mHomeScreenOption.setVisibility(8);
            this.mLockScreenOption.setVisibility(8);
            this.mCallScreenOption.setVisibility(8);
        }
        TextView textView = (TextView) this.mHomeScreenOption.findViewById(R.id.title);
        this.mHomeTitle = textView;
        textView.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_home_screen));
        SwitchCompat switchCompat = (SwitchCompat) this.mHomeScreenOption.findViewById(R.id.title_switch);
        this.mHomeSwitch = switchCompat;
        switchCompat.setChecked(this.mSettingsHelper.isHomeScreenRotationAllowed());
        if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
            this.mHomeSwitch.setClickable(false);
        }
        updateHomeScreenOption(getToggleState().booleanValue());
        if (!DeviceState.shouldEnableKeyguardScreenRotation(this.mContext)) {
            this.mLockScreenOption.setVisibility(8);
        }
        TextView textView2 = (TextView) this.mLockScreenOption.findViewById(R.id.title);
        this.mLockTitle = textView2;
        textView2.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_lock_screen));
        this.mLockSwitch = (SwitchCompat) this.mLockScreenOption.findViewById(R.id.title_switch);
        if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
            this.mLockSwitch.setClickable(false);
        }
        this.mLockSwitch.setChecked(this.mSettingsHelper.isLockScreenRotationAllowed());
        updateLockScreenOption(getToggleState().booleanValue());
        TextView textView3 = (TextView) this.mCallScreenOption.findViewById(R.id.title);
        this.mCallTitle = textView3;
        textView3.setText(this.mContext.getText(R.string.quick_settings_rotation_detail_call_screen));
        this.mCallSwitch = (SwitchCompat) this.mCallScreenOption.findViewById(R.id.title_switch);
        if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
            this.mCallSwitch.setClickable(false);
        }
        this.mCallSwitch.setChecked(this.mSettingsHelper.isCallScreenRotationAllowed());
        updateVoiceCallScreenOption(getToggleState().booleanValue());
        if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
            View viewInflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_rotation_navigation_button, viewGroup, false);
            viewGroup2.addView(viewInflate2);
            View viewFindViewById = viewInflate2.findViewById(R.id.button_on_navigation_bar_option_parent);
            this.mButtonOnNavigationBarOption = viewFindViewById;
            SecQSSwitchPreference secQSSwitchPreferenceInflateSwitch4 = SecQSSwitchPreference.inflateSwitch(this.mContext, (ViewGroup) viewFindViewById);
            ((ViewGroup) this.mButtonOnNavigationBarOption).addView(secQSSwitchPreferenceInflateSwitch4);
            secQSSwitchPreferenceInflateSwitch4.setClickable(false);
            this.mButtonOnNavigationBarOption = secQSSwitchPreferenceInflateSwitch4;
            TextView textView4 = (TextView) secQSSwitchPreferenceInflateSwitch4.findViewById(R.id.title);
            this.mButtonOnNavigationBarTitle = textView4;
            textView4.setText(R.string.quick_settings_manual_rotation_button_on_navigation_bar_title);
            this.mButtonOnNavigationBarOption.findViewById(R.id.title_summary).setVisibility(8);
            SwitchCompat switchCompat2 = (SwitchCompat) this.mButtonOnNavigationBarOption.findViewById(R.id.title_switch);
            this.mButtonOnNavigationBarSwitch = switchCompat2;
            switchCompat2.setChecked(this.mSettingsHelper.isNavigationBarRotateSuggestionEnabled());
            if (this.mSettingsHelper.isVoiceAssistantEnabled()) {
                this.mButtonOnNavigationBarSwitch.setClickable(false);
            }
            updateButtonOnNavigationBarOption(!getToggleState().booleanValue());
        } else {
            viewInflate.findViewById(R.id.button_on_navigation_bar_option_parent).setVisibility(8);
        }
        SecQSSwitchPreference secQSSwitchPreference = this.mHomeScreenOption;
        final TextView textView5 = this.mHomeTitle;
        final SwitchCompat switchCompat3 = this.mHomeSwitch;
        secQSSwitchPreference.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                String string = !switchCompat3.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat3.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(textView5.getText().toString());
                accessibilityNodeInfo.setClassName("android.widget.Switch");
                accessibilityNodeInfo.setStateDescription(string);
            }
        });
        SecQSSwitchPreference secQSSwitchPreference2 = this.mLockScreenOption;
        final TextView textView6 = this.mLockTitle;
        final SwitchCompat switchCompat4 = this.mLockSwitch;
        secQSSwitchPreference2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                String string = !switchCompat4.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat4.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(textView6.getText().toString());
                accessibilityNodeInfo.setClassName("android.widget.Switch");
                accessibilityNodeInfo.setStateDescription(string);
            }
        });
        SecQSSwitchPreference secQSSwitchPreference3 = this.mCallScreenOption;
        final TextView textView7 = this.mCallTitle;
        final SwitchCompat switchCompat5 = this.mCallSwitch;
        secQSSwitchPreference3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                String string = !switchCompat5.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat5.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(textView7.getText().toString());
                accessibilityNodeInfo.setClassName("android.widget.Switch");
                accessibilityNodeInfo.setStateDescription(string);
            }
        });
        View view2 = this.mButtonOnNavigationBarOption;
        final TextView textView8 = this.mButtonOnNavigationBarTitle;
        final SwitchCompat switchCompat6 = this.mButtonOnNavigationBarSwitch;
        view2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view22, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view22, accessibilityNodeInfo);
                String string = !switchCompat6.isEnabled() ? RotationLockDetailAdapter.this.mContext.getString(R.string.sec_accessibility_rotation_disabled_switch) : switchCompat6.isChecked() ? RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_on) : RotationLockDetailAdapter.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(textView8.getText().toString());
                accessibilityNodeInfo.setClassName("android.widget.Switch");
                accessibilityNodeInfo.setStateDescription(string);
            }
        });
        FontSizeUtils.updateFontSize(this.mHomeTitle, R.dimen.sec_qs_detail_category_item_text_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mLockTitle, R.dimen.sec_qs_detail_category_item_text_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mCallTitle, R.dimen.sec_qs_detail_category_item_text_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mButtonOnNavigationBarTitle, R.dimen.sec_qs_detail_category_item_text_size, 0.8f, 1.3f);
        return viewInflate;
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

    public final boolean isWallpaperRotatable() {
        boolean z = LsRune.WALLPAPER_ROTATABLE_WALLPAPER;
        PluginLockMediator pluginLockMediator = this.mPluginLockMediator;
        if (z && !WallpaperUtils.isSubDisplay() && !pluginLockMediator.isRotateMenuHide()) {
            return true;
        }
        boolean z2 = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z3 = WallpaperUtils.mIsEmergencyMode;
        if (z2 || z3) {
            return true;
        }
        boolean zIsVideoWallpaper = WallpaperUtils.isVideoWallpaper(this.mContext);
        boolean z4 = WallpaperManager.getInstance(this.mContext).semGetWallpaperType(WallpaperUtils.sCurrentWhich) == 3 && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).isPluginWallpaperRequired(WallpaperUtils.sCurrentWhich) && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).containsVideo(WallpaperUtils.sCurrentWhich);
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isVideoWallpaper: ", " ,isMultiPackVideoWallpaper: ", "RotationLockDetailAdapter", zIsVideoWallpaper, z4);
        return (zIsVideoWallpaper || z4 || pluginLockMediator.isRotateMenuHide()) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setToggleState(boolean z) {
        boolean z2;
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            z2 = !edmMonitor.mSettingsChangesAllowed;
        }
        SRotationLockTile sRotationLockTile = this.mRotationLockTile;
        if (z2) {
            sRotationLockTile.showItPolicyToast();
            return;
        }
        sRotationLockTile.fireToggleStateChanged(z);
        boolean z3 = !z;
        this.mController.setRotationLocked("SRotationLockTile#setToggleState", z3);
        updateHomeScreenOption(z);
        updateLockScreenOption(z);
        updateVoiceCallScreenOption(z);
        if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
            updateButtonOnNavigationBarOption(z3);
        }
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_DETAIL_SWITCH, "location", "auto rotate");
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

    public final void updateHomeScreenOption(boolean z) {
        SwitchCompat switchCompat;
        SecQSSwitchPreference secQSSwitchPreference = this.mHomeScreenOption;
        if (secQSSwitchPreference == null || (switchCompat = this.mHomeSwitch) == null) {
            return;
        }
        if (z) {
            secQSSwitchPreference.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RotationLockDetailAdapter.this.mHomeSwitch.setChecked(!r0.isChecked());
                }
            });
            this.mHomeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.6
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z2) {
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RotationLockDetailAdapter.this.mSettingsHelper.setHomeScreenRotationAllowed(z2);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_HOME_SCREEN_SWITCH);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, z2);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" homeScreen rotate allowed : "), z2, "RotationLockDetailAdapter");
                        }
                    }, 30L);
                    RotationLockDetailAdapter rotationLockDetailAdapter2 = RotationLockDetailAdapter.this;
                    rotationLockDetailAdapter2.mHomeSwitch.announceForAccessibility(rotationLockDetailAdapter2.mContext.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            this.mHomeSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (!(view instanceof SwitchCompat)) {
                        Log.e("RotationLockDetailAdapter", "homeSwitch View is not an instance of SwitchCompat");
                    } else {
                        RotationLockDetailAdapter.this.mHomeSwitch.setChecked(((SwitchCompat) view).isChecked());
                    }
                }
            });
            this.mHomeScreenOption.setClickable(true);
            this.mHomeScreenOption.findViewById(R.id.title).setAlpha(1.0f);
        } else {
            switchCompat.setOnCheckedChangeListener(null);
            this.mHomeScreenOption.setOnClickListener(null);
            this.mHomeScreenOption.findViewById(R.id.title).setAlpha(0.4f);
        }
        this.mHomeSwitch.setEnabled(z);
    }

    public final void updateLockScreenOption(boolean z) {
        if (this.mLockSwitch == null || this.mLockScreenOption == null) {
            return;
        }
        if (z && isWallpaperRotatable()) {
            this.mLockScreenOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RotationLockDetailAdapter.this.mLockSwitch.setChecked(!r0.isChecked());
                }
            });
            this.mLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.9
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z2) {
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RotationLockDetailAdapter.this.mSettingsHelper.setLockScreenRotationAllowed(z2);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_LOCK_SCREEN_SWITCH);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, z2);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" lockScreen is rotate allowed : "), z2, "RotationLockDetailAdapter");
                        }
                    }, 30L);
                    RotationLockDetailAdapter rotationLockDetailAdapter2 = RotationLockDetailAdapter.this;
                    rotationLockDetailAdapter2.mLockSwitch.announceForAccessibility(rotationLockDetailAdapter2.mContext.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            this.mLockSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (!(view instanceof SwitchCompat)) {
                        Log.e("RotationLockDetailAdapter", "mLockSwitch View is not an instance of SwitchCompat");
                    } else {
                        RotationLockDetailAdapter.this.mLockSwitch.setChecked(((SwitchCompat) view).isChecked());
                    }
                }
            });
            this.mLockScreenOption.setClickable(true);
            this.mLockScreenOption.findViewById(R.id.title).setAlpha(1.0f);
        } else {
            this.mLockScreenOption.setOnClickListener(null);
            this.mLockSwitch.setOnCheckedChangeListener(null);
            this.mLockTitle.setAlpha(0.4f);
        }
        this.mLockSwitch.setEnabled(z && isWallpaperRotatable());
    }

    public final void updateVoiceCallScreenOption(boolean z) {
        if (z) {
            this.mCallScreenOption.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RotationLockDetailAdapter.this.mCallSwitch.setChecked(!r0.isChecked());
                }
            });
            this.mCallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.12
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, final boolean z2) {
                    final RotationLockDetailAdapter rotationLockDetailAdapter = RotationLockDetailAdapter.this;
                    int i = RotationLockDetailAdapter.$r8$clinit;
                    rotationLockDetailAdapter.getClass();
                    rotationLockDetailAdapter.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            RotationLockDetailAdapter.this.mSettingsHelper.setCallScreenRotationAllowed(z2);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ROTATION_DETAIL_CALL_SCREEN_SWITCH);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH, z2);
                            RotationLockDetailAdapter.this.mRotationLockTilePrefEditor.commit();
                            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" callScreen is rotate allowed :"), z2, "RotationLockDetailAdapter");
                        }
                    }, 30L);
                    RotationLockDetailAdapter rotationLockDetailAdapter2 = RotationLockDetailAdapter.this;
                    rotationLockDetailAdapter2.mCallSwitch.announceForAccessibility(rotationLockDetailAdapter2.mContext.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            this.mCallSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter.13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (!(view instanceof SwitchCompat)) {
                        Log.e("RotationLockDetailAdapter", "callSwitch View is not an instance of SwitchCompat");
                    } else {
                        RotationLockDetailAdapter.this.mCallSwitch.setChecked(((SwitchCompat) view).isChecked());
                    }
                }
            });
            this.mCallScreenOption.setClickable(true);
            this.mCallScreenOption.findViewById(R.id.title).setAlpha(1.0f);
        } else {
            this.mCallSwitch.setOnCheckedChangeListener(null);
            this.mCallScreenOption.setOnClickListener(null);
            this.mCallScreenOption.findViewById(R.id.title).setAlpha(0.4f);
        }
        this.mCallSwitch.setEnabled(z);
    }
}
