package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.android.systemui.qs.shared.model.TileCategory;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda10 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda10(int i, int i2, Object obj, Object obj2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                EditTileKt.EditGridHeader(RecomposeScopeImplKt.updateChangedFlags(49), composer, (ComposableLambdaImpl) this.f$1, (Modifier.Companion) this.f$0);
                break;
            case 1:
                EditTileKt.CategoryHeader((TileCategory) this.f$0, (Modifier) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(49));
                break;
            case 2:
                EditTileKt.EditGridCenteredText((String) this.f$1, (Modifier.Companion) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                EditTileKt.EditModeTopBar((Function0) this.f$0, (Function0) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda10(String str, Modifier.Companion companion, int i) {
        this.$r8$classId = 2;
        this.f$1 = str;
        this.f$0 = companion;
    }
}
