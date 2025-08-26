package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.animation.Expandable;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class ToolbarViewModel$powerButtonViewModel$2 extends FunctionReferenceImpl implements Function1 {
    public ToolbarViewModel$powerButtonViewModel$2(Object obj) {
        super(1, obj, ToolbarViewModel.class, "onPowerButtonClicked", "onPowerButtonClicked(Lcom/android/systemui/animation/Expandable;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        GlobalActionsDialogLite globalActionsDialogLite;
        Expandable expandable = (Expandable) obj;
        ToolbarViewModel toolbarViewModel = (ToolbarViewModel) this.receiver;
        if (!toolbarViewModel.falsingInteractor.manager.isFalseTap(1) && (globalActionsDialogLite = (GlobalActionsDialogLite) ((SnapshotMutableStateImpl) toolbarViewModel.globalActionsDialogLite$delegate).getValue()) != null) {
            FooterActionsInteractorImpl footerActionsInteractorImpl = (FooterActionsInteractorImpl) toolbarViewModel.footerActionsInteractor;
            footerActionsInteractorImpl.uiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_OPEN_QS);
            globalActionsDialogLite.showOrHideDialog(expandable, footerActionsInteractorImpl.context.getDisplayId());
        }
        return Unit.INSTANCE;
    }
}
