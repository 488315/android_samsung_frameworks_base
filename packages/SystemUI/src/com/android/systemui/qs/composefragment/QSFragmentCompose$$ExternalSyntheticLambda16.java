package com.android.systemui.qs.composefragment;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda16 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragmentCompose f$0;
    public final /* synthetic */ ContentScope f$1;
    public final /* synthetic */ Modifier f$2;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda16(QSFragmentCompose qSFragmentCompose, ContentScope contentScope, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = qSFragmentCompose;
        this.f$1 = contentScope;
        this.f$2 = modifier;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        QSFragmentCompose qSFragmentCompose = this.f$0;
        Modifier modifier = this.f$2;
        ContentScope contentScope = this.f$1;
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                int i2 = QSFragmentCompose.$r8$clinit;
                qSFragmentCompose.QuickQuickSettingsElement(contentScope, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                int i3 = QSFragmentCompose.$r8$clinit;
                qSFragmentCompose.QuickSettingsElement(contentScope, (Modifier.Companion) modifier, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
        }
        return Unit.INSTANCE;
    }
}
