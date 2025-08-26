package com.samsung.sesl.compose.component;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final /* synthetic */ class SwitchKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ float f$0;
    public final /* synthetic */ SeslSwitchColors f$1;
    public final /* synthetic */ Modifier f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ SwitchKt$$ExternalSyntheticLambda0(float f, SeslSwitchColors seslSwitchColors, Modifier modifier, boolean z, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = f;
        this.f$1 = seslSwitchColors;
        this.f$2 = modifier;
        this.f$3 = z;
        this.f$4 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                Modifier modifier = this.f$2;
                boolean z = this.f$3;
                SwitchKt.SeslOpenThemeSwitchTrack(this.f$0, this.f$1, modifier, z, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                Modifier modifier2 = this.f$2;
                boolean z2 = this.f$3;
                SwitchKt.SeslDefaultSwitchTrack(this.f$0, this.f$1, modifier2, z2, (Composer) obj, iUpdateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }
}
