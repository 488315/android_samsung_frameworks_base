package com.android.systemui.qs.customize;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        updateSALog(SystemUIAnalytics.STATUS_QUICK_SETTINGS_REVERSE_SWIPE, settingsHelper.isPanelSplitReversed());
        barOrderInteractor.sendOrderStatusLog();
        barOrderInteractor.sendCollapsedRowStatusLog();
    }

    public static boolean isBarPhone() {
        return (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPanelColumns() {
        /*
            r4 = this;
            com.android.systemui.qs.SecQSPanelResourcePicker r0 = r4.resourcePicker
            com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r1 = r0.resourcePickHelper
            com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker r1 = r1.getTargetPicker()
            com.android.systemui.qs.SecQSPanelController r1 = r1.qsPanelController
            r2 = 0
            if (r1 != 0) goto Le
            goto L1d
        Le:
            com.android.systemui.qs.SecQSPanel$QSTileLayout r1 = r1.mTileLayout
            boolean r3 = r1 instanceof com.android.systemui.qs.SecTileChunkLayout
            if (r3 == 0) goto L17
            com.android.systemui.qs.SecTileChunkLayout r1 = (com.android.systemui.qs.SecTileChunkLayout) r1
            goto L18
        L17:
            r1 = r2
        L18:
            if (r1 == 0) goto L1d
            int r1 = r1.columns
            goto L1e
        L1d:
            r1 = -1
        L1e:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            if (r1 < 0) goto L25
            r2 = r3
        L25:
            if (r2 == 0) goto L2c
            int r4 = r2.intValue()
            return r4
        L2c:
            android.content.Context r4 = r4.context
            com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r0 = r0.resourcePickHelper
            com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker r0 = r0.getTargetPicker()
            int r4 = r0.getQsTileColumn(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.SecQSSettingEditResources.getPanelColumns():int");
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
