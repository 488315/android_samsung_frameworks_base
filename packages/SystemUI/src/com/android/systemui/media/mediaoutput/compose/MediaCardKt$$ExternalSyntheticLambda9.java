package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda9 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                MediaCardKt.MediaDeviceControlArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 1:
                ((Integer) obj2).getClass();
                MediaCardKt.MediaDeviceControlArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 2:
                ((Integer) obj2).getClass();
                MediaCardKt.ProgressArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 3:
                ((Integer) obj2).getClass();
                MediaCardKt.ProgressArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 4:
                ((Integer) obj2).getClass();
                MediaCardKt.TitleArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 5:
                ((Integer) obj2).getClass();
                MediaCardKt.TitleArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            case 6:
                ((Integer) obj).getClass();
                break;
            case 7:
                ((Integer) obj2).getClass();
                MediaCardKt.ControlArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
            default:
                ((Integer) obj2).getClass();
                MediaCardKt.ControlArea(RecomposeScopeImplKt.updateChangedFlags(1), (Composer) obj);
                break;
        }
        return Unit.INSTANCE;
    }
}
