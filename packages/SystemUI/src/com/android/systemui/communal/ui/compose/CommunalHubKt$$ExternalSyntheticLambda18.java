package com.android.systemui.communal.ui.compose;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.widgets.SmartspaceAppWidgetHostView;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda18 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda18(RemoteViews.InteractionHandler interactionHandler, CommunalContentModel.Smartspace smartspace) {
        this.$r8$classId = 2;
        this.f$0 = interactionHandler;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((CommunalHubKt$$ExternalSyntheticLambda14) this.f$0).invoke((LazyGridScope) obj, null);
                return Unit.INSTANCE;
            case 1:
                ((SmartspaceAppWidgetHostView) obj).updateAppWidget(((CommunalContentModel.Smartspace) this.f$0).remoteViews);
                return Unit.INSTANCE;
            case 2:
                RemoteViews.InteractionHandler interactionHandler = (RemoteViews.InteractionHandler) this.f$0;
                SmartspaceAppWidgetHostView smartspaceAppWidgetHostView = new SmartspaceAppWidgetHostView((Context) obj);
                if (interactionHandler != null) {
                    smartspaceAppWidgetHostView.setInteractionHandler(interactionHandler);
                }
                return smartspaceAppWidgetHostView;
            case 3:
                BaseCommunalViewModel baseCommunalViewModel = (BaseCommunalViewModel) this.f$0;
                UniqueObjectHostView uniqueObjectHostView = baseCommunalViewModel.mediaHost.hostView;
                if (uniqueObjectHostView == null) {
                    uniqueObjectHostView = null;
                }
                uniqueObjectHostView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                UniqueObjectHostView uniqueObjectHostView2 = baseCommunalViewModel.mediaHost.hostView;
                if (uniqueObjectHostView2 != null) {
                    return uniqueObjectHostView2;
                }
                return null;
            default:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) ((State) this.f$0).getValue()).floatValue());
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda18(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
