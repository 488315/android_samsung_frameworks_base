package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
                ((BaseCommunalViewModel) this.f$0).setSelectedKey(((CommunalContentModel.WidgetContent.Widget) this.f$1).key);
                break;
            default:
                String str = ((CommunalContentModel.WidgetContent.Widget) this.f$1).key;
                BaseCommunalViewModel baseCommunalViewModel = (BaseCommunalViewModel) this.f$0;
                baseCommunalViewModel.setSelectedKey(str);
                baseCommunalViewModel.onOpenWidgetEditor(false);
                break;
        }
        return Unit.INSTANCE;
    }
}
