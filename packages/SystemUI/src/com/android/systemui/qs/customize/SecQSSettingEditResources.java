package com.android.systemui.qs.customize;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecTileChunkLayout;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SecQSSettingEditResources {
    public static final int REMOVE_ICON_ID;
    public final ActivityStarter activityStarter;
    public final Executor bgExecutor;
    public final Context context;
    public final SharedPreferences.Editor editor;
    public boolean isCurrentTopEdit;
    public final Executor mainExecutor;
    public final QSHost qqsTileHost;
    public final SecQSPanelResourcePicker resourcePicker;
    private final SettingsHelper settingsHelper;
    public SecQSCustomizerTileAdapter tileFullAdapter;
    public final QSHost tileHost;
    public SecQSCustomizerTileAdapter tileTopAdapter;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        REMOVE_ICON_ID = View.generateViewId();
    }

    public SecQSSettingEditResources(Context context, ActivityStarter activityStarter, SecQSPanelResourcePicker secQSPanelResourcePicker, QSHost qSHost, QSHost qSHost2, UserTracker userTracker, Executor executor, Executor executor2, SettingsHelper settingsHelper, TunerService tunerService, BarOrderInteractor barOrderInteractor) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.resourcePicker = secQSPanelResourcePicker;
        this.tileHost = qSHost;
        this.qqsTileHost = qSHost2;
        this.userTracker = userTracker;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        this.settingsHelper = settingsHelper;
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        this.editor = editorEdit;
        boolean zIsPanelSplit = settingsHelper.isPanelSplit();
        if (editorEdit != null) {
            editorEdit.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, zIsPanelSplit ? "view separately" : "view all");
            editorEdit.apply();
        }
        updateSALog(SystemUIAnalytics.STATUS_SHOW_BRIGHTNESS_ON_TOP, !Intrinsics.areEqual(tunerService.getValue("brightness_on_top"), "0"));
        updateSALog(SystemUIAnalytics.STATUS_SHOW_DEVICES_AND_MEDIA, !Intrinsics.areEqual(tunerService.getValue("qspanel_media_quickcontrol_bar_available"), "0"));
        updateSALog(SystemUIAnalytics.STATUS_SHOW_MULTISIM_INFO, !Intrinsics.areEqual(tunerService.getValue("multi_sim_bar_show_on_qspanel"), "0"));
        updateSALog(SystemUIAnalytics.STATUS_QUICK_SETTINGS_REVERSE_SWIPE, settingsHelper.isPanelSplitReversed());
        barOrderInteractor.sendOrderStatusLog();
        barOrderInteractor.sendCollapsedRowStatusLog();
    }

    public static boolean isBarPhone() {
        return (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getPanelColumns() {
        int i;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        SecQSPanelController secQSPanelController = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().qsPanelController;
        if (secQSPanelController == null) {
            i = -1;
        } else {
            SecQSPanel.QSTileLayout qSTileLayout = secQSPanelController.mTileLayout;
            SecTileChunkLayout secTileChunkLayout = qSTileLayout instanceof SecTileChunkLayout ? (SecTileChunkLayout) qSTileLayout : null;
            if (secTileChunkLayout != null) {
                i = secTileChunkLayout.columns;
            }
        }
        Integer numValueOf = i >= 0 ? Integer.valueOf(i) : null;
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQsTileColumn(this.context);
    }

    public final boolean isPhoneLandscape() {
        return (!((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() || DisplayMetrics.DENSITY_DEVICE_STABLE <= 213) && this.context.getResources().getConfiguration().orientation == 2;
    }

    public final void updateSALog(String str, boolean z) {
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putString(str, z ? "1" : "0");
            editor.apply();
        }
    }
}
