package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.util.kotlin.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SharedNotificationContainerViewModel$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SharedNotificationContainerViewModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SharedNotificationContainerViewModel$$ExternalSyntheticLambda0(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = sharedNotificationContainerViewModel;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$1;
        SharedNotificationContainerViewModel sharedNotificationContainerViewModel = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = SceneContainerFlag.$r8$clinit;
                StateFlow stateFlow = (StateFlow) sharedNotificationContainerViewModel.keyguardInteractor.notificationContainerBounds$delegate.getValue();
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$bounds$2$1(null), Utils.Companion.sample(sharedNotificationContainerViewModel.interactor.topPosition, sharedNotificationContainerViewModel.keyguardTransitionInteractor.isInTransition, ((ShadeInteractorImpl) sharedNotificationContainerViewModel.shadeInteractor).baseShadeInteractor.getQsExpansion()));
                SharedNotificationContainerViewModel$bounds$2$2 sharedNotificationContainerViewModel$bounds$2$2 = new SharedNotificationContainerViewModel$bounds$2$2(sharedNotificationContainerViewModel, null);
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(sharedNotificationContainerViewModel.isOnLockscreenWithoutShade, stateFlow, sharedNotificationContainerViewModel.paddingTopDimen, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, sharedNotificationContainerViewModel$bounds$2$2);
                SharingStarted.Companion.getClass();
                return sharedNotificationContainerViewModel.dumpValue(FlowKt.stateIn(combine, (CoroutineScope) obj, SharingStarted.Companion.Lazily, new NotificationContainerBounds(0.0f, 0.0f, false, 7, null)), "bounds");
            default:
                CommunalSceneInteractor communalSceneInteractor = sharedNotificationContainerViewModel.communalSceneInteractor;
                communalSceneInteractor.getClass();
                RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                int i2 = SceneContainerFlag.$r8$clinit;
                communalSceneInteractor.onSceneAboutToChangeListener.remove((SharedNotificationContainerViewModel$aboutToTransitionToHub$1$callback$1) obj);
                return Unit.INSTANCE;
        }
    }
}
