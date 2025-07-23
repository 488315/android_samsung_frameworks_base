package com.android.systemui.communal.ui.compose;

import android.widget.RemoteViews;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda5(CommunalContentModel.WidgetContent.DisabledWidget disabledWidget, BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, int i) {
        this.$r8$classId = 1;
        this.f$0 = disabledWidget;
        this.f$2 = baseCommunalViewModel;
        this.f$1 = modifier;
        this.f$3 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).intValue();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                CommunalHubKt.ObserveNewWidgetAddedEffect((List) this.f$0, (LazyGridState) this.f$1, (BaseCommunalViewModel) this.f$2, (Composer) obj, updateChangedFlags);
                break;
            case 1:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                BaseCommunalViewModel baseCommunalViewModel = (BaseCommunalViewModel) this.f$2;
                Modifier modifier = (Modifier) this.f$1;
                CommunalHubKt.DisabledWidgetPlaceholder((CommunalContentModel.WidgetContent.DisabledWidget) this.f$0, baseCommunalViewModel, modifier, (Composer) obj, updateChangedFlags2);
                break;
            case 2:
                ((Integer) obj2).getClass();
                int updateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                ContentScope contentScope = (ContentScope) this.f$0;
                Modifier modifier2 = (Modifier) this.f$1;
                CommunalHubKt.Umo((BaseCommunalViewModel) this.f$2, contentScope, modifier2, (Composer) obj, updateChangedFlags3);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags4 = RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1);
                CommunalHubKt.SmartspaceContent((RemoteViews.InteractionHandler) this.f$0, (CommunalContentModel.Smartspace) this.f$1, (Modifier) this.f$2, (Composer) obj, updateChangedFlags4);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda5(BaseCommunalViewModel baseCommunalViewModel, ContentScope contentScope, Modifier modifier, int i) {
        this.$r8$classId = 2;
        this.f$2 = baseCommunalViewModel;
        this.f$0 = contentScope;
        this.f$1 = modifier;
        this.f$3 = i;
    }

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda5(Object obj, Object obj2, Object obj3, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
        this.f$3 = i;
    }
}
