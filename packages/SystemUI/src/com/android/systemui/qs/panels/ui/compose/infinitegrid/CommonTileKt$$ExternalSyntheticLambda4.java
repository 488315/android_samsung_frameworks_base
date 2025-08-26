package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

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
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((Density) obj).mo52roundToPx0680j_4(((Dp) ((Function0) this.f$0).invoke()).value));
            case 1:
                return Integer.valueOf(((Density) obj).mo52roundToPx0680j_4(((Dp) ((Function0) this.f$0).invoke()).value));
            default:
                DrawScope.m541drawRectnJ9OG0$default((DrawScope) obj, ((Color) ((State) this.f$0).getValue()).value, 0L, 0L, 0.0f, null, null, 0, 126);
                return Unit.INSTANCE;
        }
    }
}
