package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class FooterActionsViewModelKt$FooterActionsViewModel$1 extends FunctionReferenceImpl implements Function2 {
    final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewModelKt$FooterActionsViewModel$1(FooterActionsInteractor footerActionsInteractor) {
        super(2, Intrinsics.Kotlin.class, "observeDeviceMonitoringDialogRequests", "FooterActionsViewModel$observeDeviceMonitoringDialogRequests(Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        this.$footerActionsInteractor = footerActionsInteractor;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        final Context context = (Context) obj;
        final FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
        Object collect = ((FooterActionsInteractorImpl) footerActionsInteractor).deviceMonitoringDialogRequests.collect(new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$observeDeviceMonitoringDialogRequests$2
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj3, Continuation continuation) {
                ((FooterActionsInteractorImpl) FooterActionsInteractor.this).showDeviceMonitoringDialog(context, null);
                return Unit.INSTANCE;
            }
        }, (Continuation) obj2);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
