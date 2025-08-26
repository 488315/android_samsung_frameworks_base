package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda12 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Modifier f$2;
    public final /* synthetic */ Object f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda12(boolean z, Object obj, Modifier modifier, Object obj2, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = z;
        this.f$1 = obj;
        this.f$2 = modifier;
        this.f$3 = obj2;
        this.f$4 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                Modifier modifier = this.f$2;
                ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) this.f$3;
                CommunalHubKt.ToolbarButton(this.f$0, (Function0) this.f$1, modifier, composableLambdaImpl, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                Modifier modifier2 = this.f$2;
                WidgetConfigurator widgetConfigurator = (WidgetConfigurator) this.f$3;
                CommunalHubKt.WidgetConfigureButton(this.f$0, (CommunalContentModel.WidgetContent.Widget) this.f$1, modifier2, widgetConfigurator, (Composer) obj, iUpdateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }
}
