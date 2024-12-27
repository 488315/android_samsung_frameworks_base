package com.android.systemui.slimindicator;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;

public final class SlimIndicatorSettingsBackUpManager {
    public String mCachedBlackListDBValue = null;
    public int mSplitQuickPanelRatio = 0;
    public boolean mQsTileLayoutCustomMatrixEnabled = false;
    public int mQsTileLayoutCustomMatrixButtonWidth = -1;
    public boolean mNotificationApplyWallpaperThemeEnabled = false;
    public int mIndicatorClockDateFormat = 0;
    public boolean mIsBackup = false;
    public final SettingsListener mSettingsListener = new SettingsListener(this, 0);

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
            ((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.slimindicator.SlimIndicatorSettingsBackUpManager$SettingsListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager = SlimIndicatorSettingsBackUpManager.this;
                    String logText = slimIndicatorSettingsBackUpManager.getLogText();
                    slimIndicatorSettingsBackUpManager.mCachedBlackListDBValue = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getIconBlacklist();
                    slimIndicatorSettingsBackUpManager.mSplitQuickPanelRatio = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getPanelSplitRatio();
                    slimIndicatorSettingsBackUpManager.mQsTileLayoutCustomMatrixEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isQSButtonGridPopupEnabled();
                    slimIndicatorSettingsBackUpManager.mQsTileLayoutCustomMatrixButtonWidth = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getQSButtonGridWidth();
                    slimIndicatorSettingsBackUpManager.mNotificationApplyWallpaperThemeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isApplyWallpaperThemeToNotif();
                    slimIndicatorSettingsBackUpManager.mIndicatorClockDateFormat = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getQuickStarDateFormat();
                    Log.d("[QuickStar]SlimIndicatorSettingsBackUpManager", "backUpValues(DONE) mIsBackup:" + slimIndicatorSettingsBackUpManager.mIsBackup + " [" + logText + "] >> [" + slimIndicatorSettingsBackUpManager.getLogText() + "]");
                    slimIndicatorSettingsBackUpManager.mIsBackup = true;
                }
            });
        }

        private SettingsListener() {
            this.mSettingsValueList = new Uri[]{Settings.Secure.getUriFor("icon_blacklist"), Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_RATIO), Settings.Global.getUriFor(SettingsHelper.INDEX_QS_BUTTON_GRID_POPUP), Settings.Global.getUriFor(SettingsHelper.INDEX_QS_BUTTON_GRID_TILE_WIDTH), Settings.Global.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME), Settings.Global.getUriFor(SettingsHelper.INDEX_QUICKSTAR_DATE_FORMAT)};
        }
    }

    public SlimIndicatorSettingsBackUpManager(Context context) {
    }

    public final String getLogText() {
        StringBuilder sb = new StringBuilder("[QuickStar]SlimIndicatorSettingsBackUpManager");
        sb.append(" (1)CachedBlackListDBValue:" + this.mCachedBlackListDBValue);
        sb.append(" (2)SplitQuickPanelRatio:" + this.mSplitQuickPanelRatio);
        sb.append(" (3-1)TileLayoutCustomMatrixEnabled:" + this.mQsTileLayoutCustomMatrixEnabled);
        sb.append(" (3-2)TileLayoutCustomMatrixButtonWidth:" + this.mQsTileLayoutCustomMatrixButtonWidth);
        sb.append(" (4)NotificationApplyWallpaperTheme:" + this.mNotificationApplyWallpaperThemeEnabled);
        sb.append(" (5)IndicatorClockDateFormat:" + this.mIndicatorClockDateFormat);
        return sb.toString();
    }

    public final void onPluginDisconnected() {
        SettingsListener settingsListener = this.mSettingsListener;
        settingsListener.getClass();
        if (Dependency.sDependency.getDependencyInner(SettingsHelper.class) != null) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(settingsListener);
        }
        Log.d("[QuickStar]SlimIndicatorSettingsBackUpManager", "resetValues() " + getLogText());
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setIconBlacklist(SPluginSlimIndicatorModel.DB_KEY_DEFAULT_NULL);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setPanelSplitRatio(-1);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setQSButtonGridPopupEnabled(0);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setQSButtonGridWidth(-1);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setApplyWallpaperThemeToNotif(0);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setQuickStarDateFormat(0);
    }
}
