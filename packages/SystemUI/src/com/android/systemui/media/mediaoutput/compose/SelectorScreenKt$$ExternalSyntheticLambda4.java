package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.entity.EntityString;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SelectorScreenKt$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ EntityString f$1;

    public /* synthetic */ SelectorScreenKt$$ExternalSyntheticLambda4(Object obj, EntityString entityString, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = entityString;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((Function1) this.f$0).mo779invoke(((DeviceSessionController) ((DeviceSession) this.f$1)).getId());
                break;
            case 1:
                ((MediaSession) this.f$0).execute(((MediaAction) this.f$1).id, 0L);
                break;
            default:
                ((Function1) this.f$0).mo779invoke(((MediaSession) this.f$1).getId());
                break;
        }
        return Unit.INSTANCE;
    }
}
