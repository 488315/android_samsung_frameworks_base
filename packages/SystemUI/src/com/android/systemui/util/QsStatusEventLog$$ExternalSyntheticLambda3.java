package com.android.systemui.util;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import java.util.LinkedHashMap;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class QsStatusEventLog$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QsStatusEventLog$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = ((SecPanelSAStatusLogInteractor) obj).repository;
                String valueOf = String.valueOf(((Number) secPanelSAStatusLogRepository._openQuickPanelFromStatusBarInShade.getValue()).longValue());
                StateFlowImpl stateFlowImpl = secPanelSAStatusLogRepository._openQuickPanelFromStatusBarInKeyguard;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_SEPARATE, "indicator_excluding_lockscreen", valueOf, "indicator_lockscreen", String.valueOf(((Number) stateFlowImpl.getValue()).longValue()));
                StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogRepository._openQuickPanelFromWipeDownInShade;
                String valueOf2 = String.valueOf(((Number) stateFlowImpl2.getValue()).longValue());
                StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogRepository._openQuickPanelFromWipeDownInKeyguard;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_SEPARATE, "swipe_down_on_the_notification_panel_excluding_lockscreen", valueOf2, "swipe_down_on_the_notification_panel_lockscreen", String.valueOf(((Number) stateFlowImpl3.getValue()).longValue()));
                StateFlowImpl stateFlowImpl4 = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwipingInShade;
                String valueOf3 = String.valueOf(((Number) stateFlowImpl4.getValue()).longValue());
                StateFlowImpl stateFlowImpl5 = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwipingInKeyguard;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_SEPARATE, "swipe_left_on_the_notification_panel_excluding_lockscreen", valueOf3, "swipe_left_on_the_notification_panel_lockscreen", String.valueOf(((Number) stateFlowImpl5.getValue()).longValue()));
                secPanelSAStatusLogRepository._openQuickPanelFromStatusBarInShade.updateState(null, 0L);
                stateFlowImpl.updateState(null, 0L);
                stateFlowImpl2.updateState(null, 0L);
                stateFlowImpl3.updateState(null, 0L);
                stateFlowImpl4.updateState(null, 0L);
                stateFlowImpl5.updateState(null, 0L);
                break;
            case 1:
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository2 = ((SecPanelSAStatusLogInteractor) obj).repository;
                String valueOf4 = String.valueOf(((Number) secPanelSAStatusLogRepository2._openQuickPanelFrom1DepthStatusBarInShade.getValue()).longValue());
                StateFlowImpl stateFlowImpl6 = secPanelSAStatusLogRepository2._openQuickPanelFrom1DepthStatusBarInKeyguard;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_TOGETHER, "QPN001 indicator_excluding_lockscreen", valueOf4, "QPN001 indicator_lockscreen", String.valueOf(((Number) stateFlowImpl6.getValue()).longValue()));
                StateFlowImpl stateFlowImpl7 = secPanelSAStatusLogRepository2._openQuickPanelFrom1DepthEtcInShade;
                long longValue = ((Number) stateFlowImpl7.getValue()).longValue();
                StateFlowImpl stateFlowImpl8 = secPanelSAStatusLogRepository2._openQuickPanelFrom1DepthEtcInKeyguard;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_TOGETHER, "QPN001 etc", String.valueOf(((Number) stateFlowImpl8.getValue()).longValue() + longValue));
                StateFlowImpl stateFlowImpl9 = secPanelSAStatusLogRepository2._openQuickPanelFrom2Depth;
                String valueOf5 = String.valueOf(((Number) stateFlowImpl9.getValue()).longValue());
                StateFlowImpl stateFlowImpl10 = secPanelSAStatusLogRepository2._openQuickPanelFrom2Depth2Finger;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_TOGETHER, "QPP101 swipe_down", valueOf5, "QPP101 two_finger", String.valueOf(((Number) stateFlowImpl10.getValue()).longValue()));
                secPanelSAStatusLogRepository2._openQuickPanelFrom1DepthStatusBarInShade.updateState(null, 0L);
                stateFlowImpl6.updateState(null, 0L);
                stateFlowImpl7.updateState(null, 0L);
                stateFlowImpl8.updateState(null, 0L);
                stateFlowImpl9.updateState(null, 0L);
                stateFlowImpl10.updateState(null, 0L);
                break;
            case 2:
                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) obj;
                secPanelSAStatusLogInteractor.getClass();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository3 = secPanelSAStatusLogInteractor.repository;
                linkedHashMap.put("indicator_lockscreen", String.valueOf(((Number) secPanelSAStatusLogRepository3._openNotificationPanelFromStatusbarInKeyguard.getValue()).longValue()));
                StateFlowImpl stateFlowImpl11 = secPanelSAStatusLogRepository3._openNotificationPanelFromStatusbarInShade;
                linkedHashMap.put("indicator_excluding_lockscreen", String.valueOf(((Number) stateFlowImpl11.getValue()).longValue()));
                StateFlowImpl stateFlowImpl12 = secPanelSAStatusLogRepository3._openNotificationPanelFromSwipeDownInKeyguard;
                linkedHashMap.put("swipe_down_from_the_quick_settings_panel_lockscreen", String.valueOf(((Number) stateFlowImpl12.getValue()).longValue()));
                StateFlowImpl stateFlowImpl13 = secPanelSAStatusLogRepository3._openNotificationPanelFromSwipeDownInShade;
                linkedHashMap.put("swipe_down_from_the_quick_settings_panel_excluding_lockscreen", String.valueOf(((Number) stateFlowImpl13.getValue()).longValue()));
                StateFlowImpl stateFlowImpl14 = secPanelSAStatusLogRepository3._openNotificationPanelFromSwipeRightInKeyguard;
                linkedHashMap.put("swipe_from_the_quick_settings_panel_lockscreen", String.valueOf(((Number) stateFlowImpl14.getValue()).longValue()));
                StateFlowImpl stateFlowImpl15 = secPanelSAStatusLogRepository3._openNotificationPanelFromSwipeRightInShade;
                linkedHashMap.put("swipe_from_the_quick_settings_panel_excluding_lockscreen", String.valueOf(((Number) stateFlowImpl15.getValue()).longValue()));
                StateFlowImpl stateFlowImpl16 = secPanelSAStatusLogRepository3._openNotificationPanelFromKeyguard;
                linkedHashMap.put(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD, String.valueOf(((Number) stateFlowImpl16.getValue()).longValue()));
                StateFlowImpl stateFlowImpl17 = secPanelSAStatusLogRepository3._openNotificationPanelFromHomescreen;
                linkedHashMap.put("homescreen", String.valueOf(((Number) stateFlowImpl17.getValue()).longValue()));
                StateFlowImpl stateFlowImpl18 = secPanelSAStatusLogRepository3._openNotificationPanelFromHun;
                linkedHashMap.put("HUN", String.valueOf(((Number) stateFlowImpl18.getValue()).longValue()));
                StateFlowImpl stateFlowImpl19 = secPanelSAStatusLogRepository3._openNotificationPanelFromEtc;
                linkedHashMap.put("etc", String.valueOf(((Number) stateFlowImpl19.getValue()).longValue()));
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_NOTIFICATION_PANEL, SystemUIAnalytics.EID_QPNE_OPEN_NOTIFICATION_PANEL_SEPARATE, linkedHashMap);
                secPanelSAStatusLogRepository3._openNotificationPanelFromStatusbarInKeyguard.updateState(null, 0L);
                stateFlowImpl11.updateState(null, 0L);
                stateFlowImpl12.updateState(null, 0L);
                stateFlowImpl13.updateState(null, 0L);
                stateFlowImpl14.updateState(null, 0L);
                stateFlowImpl15.updateState(null, 0L);
                stateFlowImpl16.updateState(null, 0L);
                stateFlowImpl17.updateState(null, 0L);
                stateFlowImpl18.updateState(null, 0L);
                stateFlowImpl19.updateState(null, 0L);
                break;
            default:
                ((QsStatusEventLog) obj).checkWeeklyStatus();
                break;
        }
    }
}
