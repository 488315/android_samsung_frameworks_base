package com.android.systemui.util;

import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                String valueOf = String.valueOf(((Number) secPanelSAStatusLogRepository._openQuickPanelFromWipeDown.getValue()).longValue());
                StateFlowImpl stateFlowImpl = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwiping;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_SEPARATE, "From wipe down", valueOf, "From horizontal swiping", String.valueOf(((Number) stateFlowImpl.getValue()).longValue()));
                secPanelSAStatusLogRepository._openQuickPanelFromWipeDown.updateState(null, 0L);
                stateFlowImpl.updateState(null, 0L);
                break;
            case 1:
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository2 = ((SecPanelSAStatusLogInteractor) obj).repository;
                String valueOf2 = String.valueOf(((Number) secPanelSAStatusLogRepository2._horizontalSwipingToQuickPanel.getValue()).longValue());
                StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogRepository2._horizontalSwipingToNotificationPanel;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_HORIZONTALSWIPING, "Quick panel", valueOf2, "Notification panel", String.valueOf(((Number) stateFlowImpl2.getValue()).longValue()));
                secPanelSAStatusLogRepository2._horizontalSwipingToQuickPanel.updateState(null, 0L);
                stateFlowImpl2.updateState(null, 0L);
                break;
            case 2:
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository3 = ((SecPanelSAStatusLogInteractor) obj).repository;
                String valueOf3 = String.valueOf(((Number) secPanelSAStatusLogRepository3._openQuickPanelFrom1Depth.getValue()).longValue());
                StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogRepository3._openQuickPanelFrom2Depth;
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_OPEN_QUICK_PANEL_TOGETHER, SystemUIAnalytics.SID_QUICKPANEL_OPENED, valueOf3, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED, String.valueOf(((Number) stateFlowImpl3.getValue()).longValue()));
                secPanelSAStatusLogRepository3._openQuickPanelFrom1Depth.updateState(null, 0L);
                stateFlowImpl3.updateState(null, 0L);
                break;
            default:
                ((QsStatusEventLog) obj).checkWeeklyStatus();
                break;
        }
    }
}
