package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda7 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SessionController f$0;

    public /* synthetic */ MediaCardKt$$ExternalSyntheticLambda7(SessionController sessionController, int i) {
        this.$r8$classId = i;
        this.f$0 = sessionController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SessionController sessionController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SessionController.Companion companion = SessionController.Companion;
                sessionController.execute(32L, 0L);
                break;
            case 1:
                SessionController.Companion companion2 = SessionController.Companion;
                sessionController.execute(2L, 0L);
                break;
            case 2:
                SessionController.Companion companion3 = SessionController.Companion;
                sessionController.execute(4L, 0L);
                break;
            case 3:
                SessionController.Companion companion4 = SessionController.Companion;
                sessionController.execute(8L, 0L);
                break;
            default:
                SessionController.Companion companion5 = SessionController.Companion;
                sessionController.execute(16L, 0L);
                break;
        }
        return Unit.INSTANCE;
    }
}
