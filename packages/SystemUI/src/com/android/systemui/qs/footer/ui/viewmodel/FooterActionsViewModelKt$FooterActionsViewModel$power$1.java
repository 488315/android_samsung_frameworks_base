package com.android.systemui.qs.footer.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class FooterActionsViewModelKt$FooterActionsViewModel$power$1 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;
    final /* synthetic */ GlobalActionsDialogLite $globalActionsDialogLite;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewModelKt$FooterActionsViewModel$power$1(FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor, GlobalActionsDialogLite globalActionsDialogLite) {
        super(1, Intrinsics.Kotlin.class, "onPowerButtonClicked", "FooterActionsViewModel$onPowerButtonClicked(Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Lcom/android/systemui/globalactions/GlobalActionsDialogLite;Lcom/android/systemui/animation/Expandable;)V", 0);
        this.$falsingManager = falsingManager;
        this.$footerActionsInteractor = footerActionsInteractor;
        this.$globalActionsDialogLite = globalActionsDialogLite;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Expandable expandable = (Expandable) obj;
        FalsingManager falsingManager = this.$falsingManager;
        FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
        GlobalActionsDialogLite globalActionsDialogLite = this.$globalActionsDialogLite;
        if (!falsingManager.isFalseTap(1)) {
            ((FooterActionsInteractorImpl) footerActionsInteractor).uiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_OPEN_QS);
            globalActionsDialogLite.showOrHideDialog(expandable);
        }
        return Unit.INSTANCE;
    }
}
