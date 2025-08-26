package com.android.systemui.qs.composefragment;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007a  */
    @Override // kotlin.jvm.functions.Function0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke() {
        boolean z;
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Boolean bool = (Boolean) ((SnapshotMutableStateImpl) ((QSFragmentCompose) obj).notificationScrimClippingParams.isEnabled$delegate).getValue();
                bool.booleanValue();
                return bool;
            case 1:
                return (NotificationScrimClipParams) ((SnapshotMutableStateImpl) ((QSFragmentCompose) obj).notificationScrimClippingParams.params$delegate).getValue();
            case 2:
                QSFragmentCompose qSFragmentCompose = (QSFragmentCompose) obj;
                if (qSFragmentCompose.scrollState.getCanScrollForward()) {
                    QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
                    if (!((Boolean) (qSFragmentComposeViewModel != null ? qSFragmentComposeViewModel : null).isQsFullyExpanded$delegate.getValue()).booleanValue()) {
                    }
                } else {
                    z = qSFragmentCompose.isCustomizing();
                }
                return Boolean.valueOf(z);
            case 3:
                QSFragmentComposeViewModel qSFragmentComposeViewModel2 = ((QSFragmentCompose) obj).viewModel;
                return (QSFragmentComposeViewModel.QSExpansionState) (qSFragmentComposeViewModel2 != null ? qSFragmentComposeViewModel2 : null).expansionState$delegate.getValue();
            case 4:
                QSFragmentCompose qSFragmentCompose2 = (QSFragmentCompose) obj;
                QS.ScrollListener scrollListener = (QS.ScrollListener) qSFragmentCompose2.scrollListener.getValue();
                if (scrollListener != null) {
                    scrollListener.onQsPanelScrollChanged(qSFragmentCompose2.scrollState.getValue());
                }
                Consumer consumer = (Consumer) qSFragmentCompose2.collapsedMediaVisibilityChangedListener.getValue();
                if (consumer != null) {
                    QSFragmentComposeViewModel qSFragmentComposeViewModel3 = qSFragmentCompose2.viewModel;
                    consumer.accept(Boolean.valueOf((qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null).getQqsMediaVisible()));
                }
                return Unit.INSTANCE;
            default:
                int i = QSFragmentCompose.$r8$clinit;
                ((Runnable) obj).run();
                return Boolean.TRUE;
        }
    }
}
