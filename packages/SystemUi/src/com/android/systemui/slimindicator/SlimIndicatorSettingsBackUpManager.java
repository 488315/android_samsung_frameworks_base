package com.android.systemui.slimindicator;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SlimIndicatorSettingsBackUpManager {
    public final Context mContext;
    public String mPanelSwipeDirectlyPosition;
    public String mCachedBlackListDBValue = null;
    public int mPanelSwipeDirectlyArea = 0;
    public boolean mQsTileLayoutCustomMatrixEnabled = false;
    public int mQsTileLayoutCustomMatrixButtonWidth = -1;
    public boolean mNotificationApplyWallpaperThemeEnabled = false;
    public int mIndicatorClockDateFormat = 0;
    public boolean mIsBackup = false;
    public final SettingsListener mSettingsListener = new SettingsListener(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsListener implements SettingsHelper.OnChangedCallback {
        public final Uri[] mSettingsValueList;

        public /* synthetic */ SettingsListener(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager, int i) {
            this();
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri == null) {
                return;
            }
            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.slimindicator.SlimIndicatorSettingsBackUpManager$SettingsListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager = SlimIndicatorSettingsBackUpManager.this;
                    String logText = slimIndicatorSettingsBackUpManager.getLogText();
                    slimIndicatorSettingsBackUpManager.mCachedBlackListDBValue = Settings.Secure.getStringForUser(slimIndicatorSettingsBackUpManager.mContext.getContentResolver(), "icon_blacklist", KeyguardUpdateMonitor.getCurrentUser());
                    slimIndicatorSettingsBackUpManager.mPanelSwipeDirectlyArea = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("swipe_directly_to_quick_setting_area").getIntValue();
                    slimIndicatorSettingsBackUpManager.mPanelSwipeDirectlyPosition = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("swipe_directly_to_quick_setting_position").getStringValue();
                    slimIndicatorSettingsBackUpManager.mQsTileLayoutCustomMatrixEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isQSButtonGridPopupEnabled();
                    slimIndicatorSettingsBackUpManager.mQsTileLayoutCustomMatrixButtonWidth = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("quickstar_qs_tile_layout_custom_matrix_width").getIntValue();
                    slimIndicatorSettingsBackUpManager.mNotificationApplyWallpaperThemeEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isApplyWallpaperThemeToNotif();
                    slimIndicatorSettingsBackUpManager.mIndicatorClockDateFormat = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("quickstar_indicator_clock_date_format").getIntValue();
                    Log.d("[QuickStar]SlimIndicatorSettingsBackUpManager", "backUpValues(DONE) mIsBackup:" + slimIndicatorSettingsBackUpManager.mIsBackup + " [" + logText + "] >> [" + slimIndicatorSettingsBackUpManager.getLogText() + "]");
                    slimIndicatorSettingsBackUpManager.mIsBackup = true;
                }
            });
        }

        private SettingsListener() {
            this.mSettingsValueList = new Uri[]{Settings.Secure.getUriFor("icon_blacklist"), Settings.Global.getUriFor("swipe_directly_to_quick_setting_area"), Settings.Global.getUriFor("swipe_directly_to_quick_setting_position"), Settings.Global.getUriFor("quickstar_qs_tile_layout_custom_matrix"), Settings.Global.getUriFor("quickstar_qs_tile_layout_custom_matrix_width"), Settings.Global.getUriFor("notification_apply_wallpaper_theme"), Settings.Global.getUriFor("quickstar_indicator_clock_date_format")};
        }
    }

    public SlimIndicatorSettingsBackUpManager(Context context) {
        this.mContext = context;
    }

    public final String getLogText() {
        StringBuilder sb = new StringBuilder("[QuickStar]SlimIndicatorSettingsBackUpManager");
        sb.append(" (1)CachedBlackListDBValue:" + this.mCachedBlackListDBValue);
        sb.append(" (2-1)PanelSwipeDirectlyPosition:" + this.mPanelSwipeDirectlyPosition);
        sb.append(" (2-2)PanelSwipeDirectlyArea:" + this.mPanelSwipeDirectlyArea);
        sb.append(" (3-1)TileLayoutCustomMatrixEnabled:" + this.mQsTileLayoutCustomMatrixEnabled);
        sb.append(" (3-2)TileLayoutCustomMatrixButtonWidth:" + this.mQsTileLayoutCustomMatrixButtonWidth);
        sb.append(" (4)NotificationApplyWallpaperTheme:" + this.mNotificationApplyWallpaperThemeEnabled);
        sb.append(" (5)IndicatorClockDateFormat:" + this.mIndicatorClockDateFormat);
        return sb.toString();
    }

    public final void onPluginDisconnected() {
        SettingsListener settingsListener = this.mSettingsListener;
        settingsListener.getClass();
        if (Dependency.get(SettingsHelper.class) != null) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(settingsListener);
        }
        Log.d("[QuickStar]SlimIndicatorSettingsBackUpManager", "resetValues() " + getLogText());
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "icon_blacklist", SPluginSlimIndicatorModel.DB_KEY_DEFAULT_NULL, KeyguardUpdateMonitor.getCurrentUser());
        Settings.Global.putInt(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "swipe_directly_to_quick_setting_area", -1);
        Settings.Global.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "swipe_directly_to_quick_setting_position", null);
        Settings.Global.putInt(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "quickstar_qs_tile_layout_custom_matrix", 0);
        Settings.Global.putInt(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "quickstar_qs_tile_layout_custom_matrix_width", -1);
        Settings.Global.putInt(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "notification_apply_wallpaper_theme", 0);
        Settings.Global.putInt(((SettingsHelper) Dependency.get(SettingsHelper.class)).mContext.getContentResolver(), "quickstar_indicator_clock_date_format", 0);
    }
}
