package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda7 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda7(long j, int i) {
        this.$r8$classId = i;
        this.f$0 = j;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DrawScope drawScope = (DrawScope) obj;
                EditModeTileDefaults.INSTANCE.getClass();
                float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(EditModeTileDefaults.GridBackgroundCornerRadius);
                CornerRadius.Companion companion = CornerRadius.Companion;
                DrawScope.m543drawRoundRectuAw5IA$default(drawScope, this.f$0, 0L, 0L, (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (4294967295L & Float.floatToRawIntBits(fMo58toPx0680j_4)), null, 0.15f, IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber);
                break;
            case 1:
                DrawScope drawScope2 = (DrawScope) obj;
                EditModeTileDefaults.INSTANCE.getClass();
                float fMo58toPx0680j_42 = drawScope2.mo58toPx0680j_4(EditModeTileDefaults.GridBackgroundCornerRadius);
                CornerRadius.Companion companion2 = CornerRadius.Companion;
                DrawScope.m543drawRoundRectuAw5IA$default(drawScope2, this.f$0, 0L, 0L, (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (4294967295L & Float.floatToRawIntBits(fMo58toPx0680j_42)), null, 0.32f, IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber);
                break;
            default:
                DrawScope.m534drawCircleVaOC9Bg$default((DrawScope) obj, this.f$0, 0.0f, 0L, 0.0f, null, 0, 126);
                break;
        }
        return Unit.INSTANCE;
    }
}
