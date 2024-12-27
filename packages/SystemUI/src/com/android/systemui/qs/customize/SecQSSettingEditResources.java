package com.android.systemui.qs.customize;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecTileChunkLayout;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecQSSettingEditResources {
    public static final int REMOVE_ICON_ID;
    public final ActivityStarter activityStarter;
    public final Executor bgExecutor;
    public final Context context;
    public final SharedPreferences.Editor editor;
    public boolean isCurrentTopEdit;
    public final Executor mainExecutor;
    public final SecQSPanelResourcePicker resourcePicker;
    private final SettingsHelper settingsHelper;
    public SecQSCustomizerTileAdapter tileFullAdapter;
    public final QSTileHost tileHost;
    public SecQSCustomizerTileAdapter tileTopAdapter;
    public final UserTracker userTracker;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        REMOVE_ICON_ID = View.generateViewId();
    }

    public SecQSSettingEditResources(Context context, ActivityStarter activityStarter, SecQSPanelResourcePicker secQSPanelResourcePicker, QSTileHost qSTileHost, UserTracker userTracker, Executor executor, Executor executor2, SettingsHelper settingsHelper, TunerService tunerService) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.resourcePicker = secQSPanelResourcePicker;
        this.tileHost = qSTileHost;
        this.userTracker = userTracker;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        this.settingsHelper = settingsHelper;
        SharedPreferences.Editor edit = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        this.editor = edit;
        boolean isPanelSplit = settingsHelper.isPanelSplit();
        if (edit != null) {
            edit.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, isPanelSplit ? "view separately" : "view all");
            edit.apply();
        }
        updateSALog(SystemUIAnalytics.STATUS_SHOW_BRIGHTNESS_ON_TOP, !Intrinsics.areEqual(tunerService.getValue("brightness_on_top"), "0"));
        updateSALog(SystemUIAnalytics.STATUS_SHOW_DEVICES_AND_MEDIA, !Intrinsics.areEqual(tunerService.getValue("qspanel_media_quickcontrol_bar_available"), "0"));
        updateSALog(SystemUIAnalytics.STATUS_SHOW_MULTISIM_INFO, !Intrinsics.areEqual(tunerService.getValue("multi_sim_bar_show_on_qspanel"), "0"));
    }

    public final int getPanelColumns() {
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        SecQSPanelController secQSPanelController = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().qsPanelController;
        int i = -1;
        if (secQSPanelController != null) {
            SecQSPanel.QSTileLayout qSTileLayout = secQSPanelController.mTileLayout;
            SecTileChunkLayout secTileChunkLayout = qSTileLayout instanceof SecTileChunkLayout ? (SecTileChunkLayout) qSTileLayout : null;
            if (secTileChunkLayout != null) {
                i = secTileChunkLayout.columns;
            }
        }
        Integer valueOf = i >= 0 ? Integer.valueOf(i) : null;
        if (valueOf != null) {
            return valueOf.intValue();
        }
        return secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQsTileColumn(this.context);
    }

    public final void updateSALog(String str, boolean z) {
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putString(str, z ? "1" : "0");
            editor.apply();
        }
    }
}
