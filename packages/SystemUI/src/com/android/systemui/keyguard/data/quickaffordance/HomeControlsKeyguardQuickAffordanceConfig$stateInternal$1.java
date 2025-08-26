package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.R;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ControlsListingController $listingController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HomeControlsKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(ControlsListingController controlsListingController, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.$listingController = controlsListingController;
        this.this$0 = homeControlsKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1 homeControlsKeyguardQuickAffordanceConfig$stateInternal$1 = new HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(this.$listingController, this.this$0, continuation);
        homeControlsKeyguardQuickAffordanceConfig$stateInternal$1.L$0 = obj;
        return homeControlsKeyguardQuickAffordanceConfig$stateInternal$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.management.ControlsListingController$ControlsListingCallback, com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig = this.this$0;
            final ?? r1 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1$callback$1
                @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                public final void onServicesUpdated(List list) {
                    List allStructures;
                    Object visible;
                    HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig2 = homeControlsKeyguardQuickAffordanceConfig;
                    if (((ControlsController) homeControlsKeyguardQuickAffordanceConfig2.component.controlsController.orElse(null)) != null) {
                        Favorites.INSTANCE.getClass();
                        allStructures = Favorites.getAllStructures();
                    } else {
                        allStructures = null;
                    }
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ControlsComponent controlsComponent = homeControlsKeyguardQuickAffordanceConfig2.component;
                    boolean z = controlsComponent.featureEnabled;
                    boolean z2 = false;
                    boolean z3 = allStructures != null && (allStructures.isEmpty() ^ true);
                    if (!list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            } else if (((ControlsServiceInfo) it.next()).panelActivity != null) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                    boolean zIsEmpty = list.isEmpty();
                    controlsComponent.controlsTileResourceConfiguration.getClass();
                    ControlsComponent.Visibility visibility = !controlsComponent.featureEnabled ? ControlsComponent.Visibility.UNAVAILABLE : controlsComponent.lockPatternUtils.getStrongAuthForUser(((UserTrackerImpl) controlsComponent.userTracker).getUserHandle().getIdentifier()) == 1 ? ControlsComponent.Visibility.AVAILABLE_AFTER_UNLOCK : (((Boolean) controlsComponent.canShowWhileLockedSetting.$$delegate_0.getValue()).booleanValue() || controlsComponent.keyguardStateController.isUnlocked()) ? ControlsComponent.Visibility.AVAILABLE : ControlsComponent.Visibility.AVAILABLE_AFTER_UNLOCK;
                    if (z && ((z3 || z2) && !zIsEmpty && visibility == ControlsComponent.Visibility.AVAILABLE)) {
                        controlsComponent.controlsTileResourceConfiguration.getClass();
                        visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.controls_icon, new ContentDescription.Resource(R.string.quick_controls_title)), null, 2, null);
                    } else {
                        visible = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
                    }
                    ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope, visible, "HomeControlsKeyguardQuickAffordanceConfig");
                }
            };
            ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) this.$listingController;
            controlsListingControllerImpl.getClass();
            controlsListingControllerImpl.addCallback((ControlsListingController.ControlsListingCallback) r1);
            final ControlsListingController controlsListingController = this.$listingController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((ControlsListingControllerImpl) controlsListingController).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
