package com.android.systemui.qs;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FgsManagerControllerImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FgsManagerControllerImpl f$0;

    public /* synthetic */ FgsManagerControllerImpl$$ExternalSyntheticLambda0(FgsManagerControllerImpl fgsManagerControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = fgsManagerControllerImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.resources.getStringArray(17236493);
            case 1:
                return this.f$0.resources.getStringArray(17236506);
            case 2:
                return this.f$0.dialog;
            default:
                FgsManagerControllerImpl fgsManagerControllerImpl = this.f$0;
                for (final FgsManagerController.OnDialogDismissedListener onDialogDismissedListener : fgsManagerControllerImpl.onDialogDismissedListeners) {
                    fgsManagerControllerImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 = (ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1) FgsManagerController.OnDialogDismissedListener.this;
                            foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1.getClass();
                            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1.$$this$conflatedCallbackFlow, Unit.INSTANCE, "ForegroundServicesRepositoryImpl");
                        }
                    });
                }
                return Unit.INSTANCE;
        }
    }
}
