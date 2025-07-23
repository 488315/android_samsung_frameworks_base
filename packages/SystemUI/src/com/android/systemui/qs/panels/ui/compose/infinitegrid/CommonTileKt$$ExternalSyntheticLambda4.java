package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommonTileKt$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CommonTileKt$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((Density) obj).mo51roundToPx0680j_4(((Dp) ((Function0) this.f$0).invoke()).value));
            case 1:
                return Integer.valueOf(((Density) obj).mo51roundToPx0680j_4(((Dp) ((Function0) this.f$0).invoke()).value));
            default:
                DrawScope.m539drawRectnJ9OG0$default((DrawScope) obj, ((Color) ((State) this.f$0).getValue()).value, 0L, 0L, 0.0f, null, null, 0, 126);
                return Unit.INSTANCE;
        }
    }
}
