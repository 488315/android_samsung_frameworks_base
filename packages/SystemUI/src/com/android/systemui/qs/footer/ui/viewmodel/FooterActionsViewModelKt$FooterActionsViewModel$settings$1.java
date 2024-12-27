package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Intent;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class FooterActionsViewModelKt$FooterActionsViewModel$settings$1 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewModelKt$FooterActionsViewModel$settings$1(FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor) {
        super(1, Intrinsics.Kotlin.class, "onSettingsButtonClicked", "FooterActionsViewModel$onSettingsButtonClicked(Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Lcom/android/systemui/animation/Expandable;)V", 0);
        this.$falsingManager = falsingManager;
        this.$footerActionsInteractor = footerActionsInteractor;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Expandable expandable = (Expandable) obj;
        FalsingManager falsingManager = this.$falsingManager;
        FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
        if (!falsingManager.isFalseTap(1)) {
            FooterActionsInteractorImpl footerActionsInteractorImpl = (FooterActionsInteractorImpl) footerActionsInteractor;
            boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) footerActionsInteractorImpl.deviceProvisionedController).isCurrentUserSetup();
            ActivityStarter activityStarter = footerActionsInteractorImpl.activityStarter;
            if (isCurrentUserSetup) {
                footerActionsInteractorImpl.metricsLogger.action(VolteConstants.ErrorCode.NOT_ACCEPTABLE);
                activityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, expandable.activityTransitionController(33));
            } else {
                activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$showSettings$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                });
            }
        }
        return Unit.INSTANCE;
    }
}
