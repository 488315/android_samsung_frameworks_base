package com.android.systemui.media.mediaoutput.compose.material;

import android.view.DisplayCutout;
import androidx.compose.ui.focus.FocusProperties;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ContainerBoxKt$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((DisplayCutout) obj).getBoundingRectTop().bottom);
            default:
                ((FocusProperties) obj).setCanFocus(false);
                return Unit.INSTANCE;
        }
    }
}
