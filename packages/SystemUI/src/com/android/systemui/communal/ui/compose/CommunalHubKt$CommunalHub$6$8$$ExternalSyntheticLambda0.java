package com.android.systemui.communal.ui.compose;

import androidx.compose.material3.SheetState;
import androidx.compose.runtime.State;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Integer num;
        switch (this.$r8$classId) {
            case 0:
                final SheetState sheetState = (SheetState) this.f$1;
                StandaloneCoroutine launch$default = BuildersKt.launch$default((CoroutineScope) this.f$0, null, null, new CommunalHubKt$CommunalHub$6$8$1$1$1(sheetState, null), 3);
                final BaseCommunalViewModel baseCommunalViewModel = (BaseCommunalViewModel) this.f$2;
                launch$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        if (!SheetState.this.isVisible()) {
                            ((CommunalEditModeViewModel) baseCommunalViewModel).communalInteractor.setDisclaimerDismissed();
                        }
                        return Unit.INSTANCE;
                    }
                });
                break;
            case 1:
                BuildersKt.launch$default((CoroutineScope) this.f$0, null, null, new CommunalHubKt$WidgetConfigureButton$1$1$1$1((WidgetConfigurator) this.f$1, (CommunalContentModel.WidgetContent.Widget) this.f$2, null), 3);
                break;
            default:
                String str = (String) ((State) this.f$0).getValue();
                ContentListState contentListState = (ContentListState) this.f$1;
                if (str != null) {
                    ListIterator listIterator = contentListState.list.listIterator();
                    int i = 0;
                    while (true) {
                        if (!listIterator.hasNext()) {
                            i = -1;
                        } else if (!Intrinsics.areEqual(((CommunalContentModel) listIterator.next()).getKey(), str)) {
                            i++;
                        }
                    }
                    num = Integer.valueOf(i);
                } else {
                    num = null;
                }
                if (num != null) {
                    contentListState.onRemove(num.intValue());
                    ContentListState.onSaveList$default(contentListState);
                    ((BaseCommunalViewModel) this.f$2).setSelectedKey(null);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
