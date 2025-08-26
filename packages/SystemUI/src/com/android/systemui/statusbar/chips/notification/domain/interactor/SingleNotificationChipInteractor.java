package com.android.systemui.statusbar.chips.notification.domain.interactor;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.activity.data.repository.ActivityManagerRepository;
import com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SingleNotificationChipInteractor {
    public final StateFlowImpl _notificationModel;
    public final long creationTime;
    public final String extraLogTag;
    public final String key;
    public final Logger logger;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 notificationChip;

    public interface Factory {
    }

    public SingleNotificationChipInteractor(ActiveNotificationModel activeNotificationModel, long j, ActivityManagerRepository activityManagerRepository, LogBuffer logBuffer) {
        this.creationTime = j;
        String str = activeNotificationModel.key;
        this.key = str;
        int i = activeNotificationModel.uid;
        StatusBarChipLogTags.INSTANCE.getClass();
        Logger logger = new Logger(logBuffer, StringsKt__StringsKt.padEnd(20, "Notif"));
        this.logger = logger;
        String strM = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(hashCode(), "SingleNotifChipInteractor[key=", str, "][id=", "]");
        this.extraLogTag = strM;
        if (activeNotificationModel.promotedContent == null) {
            SingleNotificationChipInteractor$$ExternalSyntheticLambda0 singleNotificationChipInteractor$$ExternalSyntheticLambda0 = new SingleNotificationChipInteractor$$ExternalSyntheticLambda0(0);
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, singleNotificationChipInteractor$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(strM);
            logger.getBuffer().commit(logMessageObtain);
        }
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(activeNotificationModel);
        this._notificationModel = stateFlowImplMutableStateFlow;
        this.notificationChip = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, ((ActivityManagerRepositoryImpl) activityManagerRepository).createAppVisibilityFlow(i, logger, strM), new SingleNotificationChipInteractor$notificationChip$1(this, null));
    }
}
