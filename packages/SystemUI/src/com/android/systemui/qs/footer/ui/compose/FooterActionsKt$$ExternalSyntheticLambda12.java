package com.android.systemui.qs.footer.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class FooterActionsKt$$ExternalSyntheticLambda12 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Modifier f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ FooterActionsKt$$ExternalSyntheticLambda12(Object obj, boolean z, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = z;
        this.f$2 = modifier;
        this.f$3 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                FooterActionsKt.IconButton(RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1), composer, this.f$2, (Function0) this.f$0, this.f$1);
                break;
            case 1:
                FooterActionsKt.IconButton(RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1), composer, this.f$2, (Function0) this.f$0, this.f$1);
                break;
            default:
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                FooterActionsKt.IconButton((FooterActionsButtonViewModel) this.f$0, this.f$1, this.f$2, composer, iUpdateChangedFlags);
                break;
        }
        return Unit.INSTANCE;
    }
}
