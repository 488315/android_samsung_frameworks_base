package com.android.bouncer.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SecBouncerContentKt$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BouncerOverlayContentViewModel f$0;
    public final /* synthetic */ Modifier f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SecBouncerContentKt$$ExternalSyntheticLambda3(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = bouncerOverlayContentViewModel;
        this.f$1 = modifier;
        this.f$2 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                SecBouncerContentKt.PinLayout(this.f$0, (Modifier.Companion) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            case 1:
                SecBouncerContentKt.PasswordLayout(this.f$0, (Modifier.Companion) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            case 2:
                SecBouncerContentKt.PatternLayout(this.f$0, (Modifier.Companion) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            default:
                SecBouncerContentKt.SecOutputArea(this.f$0, this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
