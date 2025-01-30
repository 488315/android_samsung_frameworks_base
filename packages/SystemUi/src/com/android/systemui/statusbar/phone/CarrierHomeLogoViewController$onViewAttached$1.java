package com.android.systemui.statusbar.phone;

import android.telephony.SubscriptionManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1", m277f = "CarrierHomeLogoViewController.kt", m278l = {173}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CarrierHomeLogoViewController$onViewAttached$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CarrierHomeLogoViewController this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1", m277f = "CarrierHomeLogoViewController.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1 */
    final class C30011 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CarrierHomeLogoViewController this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$1", m277f = "CarrierHomeLogoViewController.kt", m278l = {175}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = carrierHomeLogoViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                    FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = carrierHomeLogoViewController.simStateChanged;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController.onViewAttached.1.1.1.1
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$2", m277f = "CarrierHomeLogoViewController.kt", m278l = {182}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super AnonymousClass2> continuation) {
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
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController.onViewAttached.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CarrierHomeLogoViewController carrierHomeLogoViewController2 = CarrierHomeLogoViewController.this;
                            carrierHomeLogoViewController2.getClass();
                            int slotIndex = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
                            CarrierLogoVisibilityManager carrierLogoVisibilityManager = carrierHomeLogoViewController2.carrierLogoVisibilityManager;
                            ServiceStateModel serviceStateModel = (ServiceStateModel) carrierLogoVisibilityManager.serviceStateHash.get(Integer.valueOf(slotIndex));
                            if (serviceStateModel != null) {
                                carrierLogoVisibilityManager.networkCondition = serviceStateModel.connected && !serviceStateModel.roaming;
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$3", m277f = "CarrierHomeLogoViewController.kt", m278l = {189}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$onViewAttached$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super AnonymousClass3> continuation) {
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
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController.onViewAttached.1.1.3.1
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
        public C30011(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super C30011> continuation) {
            super(2, continuation);
            this.this$0 = carrierHomeLogoViewController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C30011 c30011 = new C30011(this.this$0, continuation);
            c30011.L$0 = obj;
            return c30011;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C30011) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierHomeLogoViewController$onViewAttached$1(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super CarrierHomeLogoViewController$onViewAttached$1> continuation) {
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
            C30011 c30011 = new C30011(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c30011, this) == coroutineSingletons) {
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
