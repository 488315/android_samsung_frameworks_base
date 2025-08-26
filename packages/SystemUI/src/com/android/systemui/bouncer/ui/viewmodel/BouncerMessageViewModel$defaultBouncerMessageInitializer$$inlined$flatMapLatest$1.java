package com.android.systemui.bouncer.ui.viewmodel;

import android.content.res.Resources;
import android.telephony.SubscriptionInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
public final class BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1(Continuation continuation, BouncerMessageViewModel bouncerMessageViewModel) {
        super(3, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 = new BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.L$1 = obj2;
        return bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowCombine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) this.L$1;
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Sim.INSTANCE)) {
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                final SharedFlowImpl sharedFlowImpl = bouncerMessageViewModel.resetToDefault;
                flowCombine = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1

                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ BouncerMessageViewModel this$0;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, BouncerMessageViewModel bouncerMessageViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = bouncerMessageViewModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:48:0x010d A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) throws Resources.NotFoundException {
                            AnonymousClass1 anonymousClass1;
                            MessageViewModel messageViewModel;
                            String str;
                            String string;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                ((Boolean) obj).getClass();
                                SimBouncerInteractor simBouncerInteractor = this.this$0.simBouncerInteractor;
                                SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerInteractor.repository;
                                Boolean bool = (Boolean) simBouncerRepositoryImpl.isLockedEsim.$$delegate_0.getValue();
                                boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
                                boolean zBooleanValue2 = ((Boolean) simBouncerRepositoryImpl.isSimPukLocked.$$delegate_0.getValue()).booleanValue();
                                if (((Number) simBouncerRepositoryImpl.subscriptionId.$$delegate_0.getValue()).intValue() == -1) {
                                    Log.e("BouncerSimInteractor", "Trying to get default message from unknown sub id");
                                    string = "";
                                } else {
                                    int activeModemCount = simBouncerInteractor.telephonyManager.getActiveModemCount();
                                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) simBouncerRepositoryImpl.activeSubscriptionInfo.$$delegate_0.getValue();
                                    CharSequence displayName = subscriptionInfo != null ? subscriptionInfo.getDisplayName() : null;
                                    String string2 = (activeModemCount >= 2 || !zBooleanValue2) ? activeModemCount < 2 ? simBouncerInteractor.resources.getString(R.string.kg_sim_pin_instructions) : (TextUtils.isEmpty(displayName) || !zBooleanValue2) ? !TextUtils.isEmpty(displayName) ? simBouncerInteractor.resources.getString(R.string.kg_sim_pin_instructions_multi, displayName) : zBooleanValue2 ? simBouncerInteractor.resources.getString(R.string.kg_puk_enter_puk_hint) : simBouncerInteractor.resources.getString(R.string.kg_sim_pin_instructions) : simBouncerInteractor.resources.getString(R.string.kg_puk_enter_puk_hint_multi, displayName) : simBouncerInteractor.resources.getString(R.string.kg_puk_enter_puk_hint);
                                    if (zBooleanValue) {
                                        string = simBouncerInteractor.resources.getString(R.string.kg_sim_lock_esim_instructions, string2);
                                    } else {
                                        str = string2;
                                        messageViewModel = new MessageViewModel(str, null, false, 6, null);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(messageViewModel, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                }
                                str = string;
                                messageViewModel = new MessageViewModel(str, null, false, 6, null);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(messageViewModel, anonymousClass1) == coroutineSingletons) {
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = sharedFlowImpl.collect(new AnonymousClass2(flowCollector2, bouncerMessageViewModel), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            } else if (authenticationMethodModel.isSecure) {
                BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
                flowCombine = FlowKt.combine(bouncerMessageViewModel2.deviceUnlockedInteractor.deviceEntryRestrictionReason, bouncerMessageViewModel2.lockoutMessage, bouncerMessageViewModel2.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer, bouncerMessageViewModel2.resetToDefault, bouncerMessageViewModel2.authenticationInteractor.failedAuthenticationAttempts, new BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(bouncerMessageViewModel2, authenticationMethodModel, null));
            } else {
                flowCombine = EmptyFlow.INSTANCE;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutineSingletons) {
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
