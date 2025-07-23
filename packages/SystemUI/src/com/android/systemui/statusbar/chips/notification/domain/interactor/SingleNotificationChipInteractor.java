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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleNotificationChipInteractor {
    public final StateFlowImpl _notificationModel;
    public final long creationTime;
    public final String extraLogTag;
    public final String key;
    public final Logger logger;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 notificationChip;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(hashCode(), "SingleNotifChipInteractor[key=", str, "][id=", "]");
        this.extraLogTag = m;
        if (activeNotificationModel.promotedContent == null) {
            SingleNotificationChipInteractor$$ExternalSyntheticLambda0 singleNotificationChipInteractor$$ExternalSyntheticLambda0 = new SingleNotificationChipInteractor$$ExternalSyntheticLambda0(0);
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, singleNotificationChipInteractor$$ExternalSyntheticLambda0, null);
            obtain.setStr1(m);
            logger.getBuffer().commit(obtain);
        }
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(activeNotificationModel);
        this._notificationModel = MutableStateFlow;
        this.notificationChip = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow, ((ActivityManagerRepositoryImpl) activityManagerRepository).createAppVisibilityFlow(i, logger, m), new SingleNotificationChipInteractor$notificationChip$1(this, null));
    }
}
