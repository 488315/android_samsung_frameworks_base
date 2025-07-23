package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SelectorScreenKt$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Modifier f$0;
    public final /* synthetic */ SessionController f$1;
    public final /* synthetic */ Function1 f$2;

    public /* synthetic */ SelectorScreenKt$$ExternalSyntheticLambda5(Modifier modifier, SessionController sessionController, Function1 function1, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = modifier;
        this.f$1 = sessionController;
        this.f$2 = function1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                SelectorScreenKt.DeviceListItem(this.f$0, (DeviceSession) this.f$1, this.f$2, composer, updateChangedFlags);
                break;
            default:
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                SelectorScreenKt.SessionListItem(this.f$0, (MediaSession) this.f$1, this.f$2, composer, updateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }
}
