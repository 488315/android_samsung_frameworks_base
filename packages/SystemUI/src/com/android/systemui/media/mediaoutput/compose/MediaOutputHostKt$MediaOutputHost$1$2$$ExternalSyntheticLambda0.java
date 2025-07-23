package com.android.systemui.media.mediaoutput.compose;

import androidx.navigation.NavHostController;
import com.android.systemui.media.mediaoutput.compose.common.DismissCallback;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavHostController f$0;
    public final /* synthetic */ DismissCallback f$1;

    public /* synthetic */ MediaOutputHostKt$MediaOutputHost$1$2$$ExternalSyntheticLambda0(NavHostController navHostController, DismissCallback dismissCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = navHostController;
        this.f$1 = dismissCallback;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                if (!this.f$0.popBackStack()) {
                    ((Feature) this.f$1).animateDismiss();
                }
                break;
            default:
                if (!this.f$0.popBackStack()) {
                    Feature feature = (Feature) this.f$1;
                    Function0 function0 = feature.dismissCallback;
                    if (function0 != null) {
                        function0.invoke();
                    }
                    feature.setState(MediaOutputState.StateInfo.Dismissed);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
