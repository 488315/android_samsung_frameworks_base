package com.android.systemui.dreams;

import com.android.systemui.complication.Complication;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda1(DreamOverlayStateController dreamOverlayStateController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStateController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStateController dreamOverlayStateController = this.f$0;
                Complication complication = (Complication) this.f$1;
                if (((HashSet) dreamOverlayStateController.mComplications).add(complication)) {
                    String obj = complication.toString();
                    DreamLogger dreamLogger = dreamOverlayStateController.mLogger;
                    dreamLogger.getClass();
                    DreamLogger$$ExternalSyntheticLambda0 dreamLogger$$ExternalSyntheticLambda0 = new DreamLogger$$ExternalSyntheticLambda0(5);
                    LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, dreamLogger$$ExternalSyntheticLambda0, null);
                    obtain.setStr1(obj);
                    dreamLogger.getBuffer().commit(obtain);
                    dreamOverlayStateController.notifyCallbacksLocked(new DreamOverlayStateController$$ExternalSyntheticLambda0(3));
                    break;
                }
                break;
            default:
                this.f$0.notifyCallbacksLocked((Consumer) this.f$1);
                break;
        }
    }
}
