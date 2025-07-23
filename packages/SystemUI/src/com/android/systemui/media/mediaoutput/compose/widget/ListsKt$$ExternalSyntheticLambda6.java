package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ListsKt$$ExternalSyntheticLambda6 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ ListsKt$$ExternalSyntheticLambda6(String str, Modifier modifier, String str2, int i, int i2) {
        this.f$0 = str;
        this.f$1 = modifier;
        this.f$2 = str2;
        this.f$3 = i;
        this.f$4 = i2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                String str = this.f$2;
                ListsKt.TwoLineListItem(this.f$0, (Modifier) this.f$1, str, (Composer) obj, updateChangedFlags, this.f$4);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                String str2 = this.f$2;
                ListsKt.SecListItem((Function0) this.f$1, this.f$0, str2, (Composer) obj, updateChangedFlags2, this.f$4);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ ListsKt$$ExternalSyntheticLambda6(Function0 function0, String str, String str2, int i, int i2) {
        this.f$1 = function0;
        this.f$0 = str;
        this.f$2 = str2;
        this.f$3 = i;
        this.f$4 = i2;
    }
}
