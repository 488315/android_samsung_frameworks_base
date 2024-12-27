package com.android.systemui.statusbar.phone.logo;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CarrierHomeLogoViewController$onViewAttached$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CarrierHomeLogoViewController this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CarrierHomeLogoViewController this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02061 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02061(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = carrierHomeLogoViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02061(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02061) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                    FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = carrierHomeLogoViewController.simStateChanged;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CarrierHomeLogoViewController carrierHomeLogoViewController2 = CarrierHomeLogoViewController.this;
                            CarrierHomeLogoViewController.access$updateSimTypes(carrierHomeLogoViewController2);
                            carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = carrierHomeLogoViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                    CarrierHomeLogoViewController$special$$inlined$map$1 carrierHomeLogoViewController$special$$inlined$map$1 = carrierHomeLogoViewController.serviceStateChanged;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CarrierHomeLogoViewController carrierHomeLogoViewController2 = CarrierHomeLogoViewController.this;
                            carrierHomeLogoViewController2.getClass();
                            boolean z = false;
                            CarrierInfraMediator carrierInfraMediator = carrierHomeLogoViewController2.carrierInfraMediator;
                            int i2 = carrierHomeLogoViewController2.slotId;
                            boolean areEqual = Intrinsics.areEqual(carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]), "ORANGE");
                            CarrierLogoVisibilityManager carrierLogoVisibilityManager = carrierHomeLogoViewController2.carrierLogoVisibilityManager;
                            if (areEqual) {
                                ServiceStateModel serviceStateModel = (ServiceStateModel) carrierLogoVisibilityManager.serviceStateHash.get(Integer.valueOf(SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId())));
                                if (serviceStateModel != null) {
                                    if (serviceStateModel.connected && !serviceStateModel.roaming) {
                                        z = true;
                                    }
                                    carrierLogoVisibilityManager.networkCondition = z;
                                }
                            } else {
                                List<SubscriptionInfo> completeActiveSubscriptionInfoList = carrierHomeLogoViewController2.subscriptionManager.getCompleteActiveSubscriptionInfoList();
                                ArrayList arrayList = new ArrayList();
                                for (Object obj3 : completeActiveSubscriptionInfoList) {
                                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj3;
                                    Object obj4 = carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]);
                                    boolean areEqual2 = Intrinsics.areEqual(obj4, "SKT");
                                    SimCardInfoUtil simCardInfoUtil = carrierHomeLogoViewController2.simCardInfoUtil;
                                    if (areEqual2) {
                                        if (SimType.SKT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                            arrayList.add(obj3);
                                        }
                                    } else if (Intrinsics.areEqual(obj4, "KTT")) {
                                        if (SimType.KT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                            arrayList.add(obj3);
                                        }
                                    } else if (Intrinsics.areEqual(obj4, "LGT") && SimType.LGT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                        arrayList.add(obj3);
                                    }
                                }
                                if (!arrayList.isEmpty()) {
                                    ListIterator listIterator = arrayList.listIterator();
                                    while (listIterator.hasNext()) {
                                        ServiceStateModel serviceStateModel2 = (ServiceStateModel) carrierLogoVisibilityManager.serviceStateHash.get(Integer.valueOf(SubscriptionManager.getSlotIndex(((SubscriptionInfo) listIterator.next()).getSubscriptionId())));
                                        boolean z2 = (serviceStateModel2 == null || !serviceStateModel2.connected || serviceStateModel2.roaming) ? false : true;
                                        carrierLogoVisibilityManager.networkCondition = z2;
                                        if (z2) {
                                            break;
                                        }
                                    }
                                } else {
                                    carrierLogoVisibilityManager.networkCondition = false;
                                }
                            }
                            carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (carrierHomeLogoViewController$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = carrierHomeLogoViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                    Flow flow = carrierHomeLogoViewController.spnUpdated;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CarrierHomeLogoViewController carrierHomeLogoViewController2 = CarrierHomeLogoViewController.this;
                            CarrierHomeLogoViewController.access$updateSimTypes(carrierHomeLogoViewController2);
                            carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = carrierHomeLogoViewController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C02061(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierHomeLogoViewController$onViewAttached$1(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
        super(3, continuation);
        this.this$0 = carrierHomeLogoViewController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CarrierHomeLogoViewController$onViewAttached$1 carrierHomeLogoViewController$onViewAttached$1 = new CarrierHomeLogoViewController$onViewAttached$1(this.this$0, (Continuation) obj3);
        carrierHomeLogoViewController$onViewAttached$1.L$0 = (LifecycleOwner) obj;
        return carrierHomeLogoViewController$onViewAttached$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
