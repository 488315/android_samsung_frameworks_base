package com.samsung.sesl.compose.component;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SwitchKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ float f$0;
    public final /* synthetic */ SeslSwitchColors f$1;
    public final /* synthetic */ InteractionSource f$2;
    public final /* synthetic */ Modifier f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ SwitchKt$$ExternalSyntheticLambda1(float f, SeslSwitchColors seslSwitchColors, InteractionSource interactionSource, Modifier modifier, boolean z, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = f;
        this.f$1 = seslSwitchColors;
        this.f$2 = interactionSource;
        this.f$3 = modifier;
        this.f$4 = z;
        this.f$5 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1);
                Modifier modifier = this.f$3;
                boolean z = this.f$4;
                SwitchKt.SeslOpenThemeSwitchThumb(this.f$0, this.f$1, this.f$2, modifier, z, (Composer) obj, updateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1);
                Modifier modifier2 = this.f$3;
                boolean z2 = this.f$4;
                SwitchKt.SeslDefaultSwitchThumb(this.f$0, this.f$1, this.f$2, modifier2, z2, (Composer) obj, updateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }
}
