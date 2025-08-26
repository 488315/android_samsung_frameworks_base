package com.android.systemui.media.mediaoutput.compose;

import android.content.Intent;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda21 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaCardKt$$ExternalSyntheticLambda21(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((MediaInteraction) this.f$0).openCpApp((String) this.f$1);
                break;
            default:
                ((AudioPathInteraction) this.f$0).goToApp((Intent) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
