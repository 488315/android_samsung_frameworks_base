package com.android.systemui.bouncer.domain.interactor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.UserHandle;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.model.SimPukInputModel;
import com.android.systemui.bouncer.data.repository.SimBouncerRepository;
import com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.util.PluralMessageFormaterKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class SimBouncerInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _bouncerMessageChanged;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl bouncerMessageChanged;
    public final ReadonlyStateFlow errorDialogMessage;
    public final EuiccManager euiccManager;
    public final ReadonlyStateFlow isAnySimSecure;
    public final ReadonlyStateFlow isLockedEsim;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SimBouncerRepository repository;
    public final Resources resources;
    public final ReadonlyStateFlow subId;
    public final TelephonyManager telephonyManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor$disableEsim$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SubscriptionInfo $activeSubscription;
        final /* synthetic */ PendingIntent $callbackIntent;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SubscriptionInfo subscriptionInfo, PendingIntent pendingIntent, Continuation continuation) {
            super(2, continuation);
            this.$activeSubscription = subscriptionInfo;
            this.$callbackIntent = pendingIntent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SimBouncerInteractor.this.new AnonymousClass1(this.$activeSubscription, this.$callbackIntent, continuation);
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
            EuiccManager euiccManager = SimBouncerInteractor.this.euiccManager;
            if (euiccManager != null) {
                euiccManager.switchToSubscription(-1, this.$activeSubscription.getPortIndex(), this.$callbackIntent);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor$resetSimPukUserInput$1, reason: invalid class name and case insensitive filesystem */
    final class C08171 extends SuspendLambda implements Function2 {
        int label;

        public C08171(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08171(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return new C08171((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(5000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            System.gc();
            System.runFinalization();
            System.gc();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor$verifySimPin$1, reason: invalid class name and case insensitive filesystem */
    final class C08181 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08181(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            SimBouncerInteractor simBouncerInteractor = SimBouncerInteractor.this;
            int i = SimBouncerInteractor.$r8$clinit;
            return simBouncerInteractor.verifySimPin(null, this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor$verifySimPuk$1, reason: invalid class name and case insensitive filesystem */
    final class C08191 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08191(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            SimBouncerInteractor simBouncerInteractor = SimBouncerInteractor.this;
            int i = SimBouncerInteractor.$r8$clinit;
            return simBouncerInteractor.verifySimPuk(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public SimBouncerInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SimBouncerRepository simBouncerRepository, TelephonyManager telephonyManager, Resources resources, KeyguardUpdateMonitor keyguardUpdateMonitor, EuiccManager euiccManager, MobileConnectionsRepository mobileConnectionsRepository) {
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = simBouncerRepository;
        this.telephonyManager = telephonyManager;
        this.resources = resources;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.euiccManager = euiccManager;
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerRepository;
        this.subId = simBouncerRepositoryImpl.subscriptionId;
        this.isAnySimSecure = FlowKt.stateIn(mobileConnectionsRepository.isAnySimSecure(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(mobileConnectionsRepository.getIsAnySimSecure()));
        this.isLockedEsim = simBouncerRepositoryImpl.isLockedEsim;
        this.errorDialogMessage = simBouncerRepositoryImpl.errorDialogMessage;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._bouncerMessageChanged = sharedFlowImplMutableSharedFlow$default;
        this.bouncerMessageChanged = sharedFlowImplMutableSharedFlow$default;
    }

    public final void disableEsim() {
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) this.repository;
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) simBouncerRepositoryImpl.activeSubscriptionInfo.$$delegate_0.getValue();
        if (subscriptionInfo == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(((Number) simBouncerRepositoryImpl.subscriptionId.$$delegate_0.getValue()).intValue(), "No active subscription with subscriptionId: ", "BouncerSimInteractor");
            return;
        }
        Intent intent = new Intent("com.android.keyguard.disable_esim");
        intent.setPackage(this.applicationContext.getPackageName());
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new AnonymousClass1(subscriptionInfo, PendingIntent.getBroadcastAsUser(this.applicationContext, 0, intent, 167772160, UserHandle.SYSTEM), null), 5);
    }

    public final String getPinPasswordErrorMessage(int i) throws Resources.NotFoundException {
        String string;
        if (i == 0) {
            string = this.resources.getString(R.string.kg_password_wrong_pin_code_pukked);
            string.getClass();
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(this.resources, R.string.kg_password_default_pin_message, i);
        } else {
            string = this.resources.getString(R.string.kg_sim_pin_instructions);
            string.getClass();
        }
        return Intrinsics.areEqual(((SimBouncerRepositoryImpl) this.repository).isLockedEsim.$$delegate_0.getValue(), Boolean.TRUE) ? this.resources.getString(R.string.kg_sim_lock_esim_instructions, string) : string;
    }

    public final String getPukPasswordErrorMessage(int i, boolean z) throws Resources.NotFoundException {
        String string;
        if (i == 0) {
            string = this.resources.getString(R.string.kg_password_wrong_puk_code_dead);
            string.getClass();
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(this.resources, R.string.kg_password_wrong_puk_code, i);
        } else {
            string = this.resources.getString(R.string.kg_password_puk_failed);
            string.getClass();
        }
        return z ? this.resources.getString(R.string.kg_sim_lock_esim_instructions, string) : string;
    }

    public final void resetSimPukUserInput() {
        SimBouncerRepository.setSimPukUserInput$default(this.repository, null, 3);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new C08171(null), 5);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c2, code lost:
    
        if (r11.emit(null, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00da, code lost:
    
        if (r13.emit(r11, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ef, code lost:
    
        if (r11.emit(null, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0106, code lost:
    
        if (r12.emit(r11, r0) == r1) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object verifySimPin(String str, ContinuationImpl continuationImpl) {
        C08181 c08181;
        int iIntValue;
        Object objWithContext;
        int result;
        if (continuationImpl instanceof C08181) {
            c08181 = (C08181) continuationImpl;
            int i = c08181.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08181.label = i - Integer.MIN_VALUE;
            } else {
                c08181 = new C08181(continuationImpl);
            }
        }
        Object obj = c08181.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08181.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            iIntValue = ((Number) ((SimBouncerRepositoryImpl) this.repository).subscriptionId.$$delegate_0.getValue()).intValue();
            if (str.length() < 4 || str.length() > 8) {
                SharedFlowImpl sharedFlowImpl = this._bouncerMessageChanged;
                String string = this.resources.getString(R.string.kg_invalid_sim_pin_hint);
                c08181.label = 1;
            } else {
                SimBouncerInteractor$verifySimPin$result$1 simBouncerInteractor$verifySimPin$result$1 = new SimBouncerInteractor$verifySimPin$result$1(this, iIntValue, str, null);
                c08181.L$0 = this;
                c08181.I$0 = iIntValue;
                c08181.label = 2;
                objWithContext = BuildersKt.withContext(this.backgroundDispatcher, simBouncerInteractor$verifySimPin$result$1, c08181);
                if (objWithContext != coroutineSingletons) {
                    PinResult pinResult = (PinResult) objWithContext;
                    result = pinResult.getResult();
                    if (result != 0) {
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        if (i2 != 2) {
            if (i2 == 3) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i2 == 4) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i2 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        int i3 = c08181.I$0;
        SimBouncerInteractor simBouncerInteractor = (SimBouncerInteractor) c08181.L$0;
        ResultKt.throwOnFailure(obj);
        iIntValue = i3;
        this = simBouncerInteractor;
        objWithContext = obj;
        PinResult pinResult2 = (PinResult) objWithContext;
        result = pinResult2.getResult();
        if (result != 0) {
            this.keyguardUpdateMonitor.reportSimUnlocked(iIntValue);
            SharedFlowImpl sharedFlowImpl2 = this._bouncerMessageChanged;
            c08181.L$0 = null;
            c08181.label = 3;
        } else {
            if (result != 1) {
                return Unit.INSTANCE;
            }
            if (pinResult2.getAttemptsRemaining() <= 2) {
                SimBouncerRepository simBouncerRepository = this.repository;
                ((SimBouncerRepositoryImpl) simBouncerRepository).simVerificationErrorMessage.setValue(this.getPinPasswordErrorMessage(pinResult2.getAttemptsRemaining()));
                SharedFlowImpl sharedFlowImpl3 = this._bouncerMessageChanged;
                c08181.L$0 = null;
                c08181.label = 4;
            } else {
                SharedFlowImpl sharedFlowImpl4 = this._bouncerMessageChanged;
                String pinPasswordErrorMessage = this.getPinPasswordErrorMessage(pinResult2.getAttemptsRemaining());
                c08181.L$0 = null;
                c08181.label = 5;
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x009a, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00db, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ee, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x011d, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0165, code lost:
    
        if (r4.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0198, code lost:
    
        if (r4.emit(null, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01be, code lost:
    
        if (r4.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01d2, code lost:
    
        if (r4.emit(null, r6) == r7) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object verifySimPuk(String str, ContinuationImpl continuationImpl) {
        C08191 c08191;
        int i;
        int result;
        SimBouncerInteractor simBouncerInteractor = this;
        if (continuationImpl instanceof C08191) {
            c08191 = (C08191) continuationImpl;
            int i2 = c08191.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c08191.label = i2 - Integer.MIN_VALUE;
            } else {
                c08191 = simBouncerInteractor.new C08191(continuationImpl);
            }
        }
        C08191 c081912 = c08191;
        Object obj = c081912.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (c081912.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                SimBouncerRepository simBouncerRepository = simBouncerInteractor.repository;
                SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerRepository;
                SimPukInputModel simPukInputModel = simBouncerRepositoryImpl._simPukInputModel;
                String str2 = simPukInputModel.enteredSimPuk;
                int iIntValue = ((Number) simBouncerRepositoryImpl.subscriptionId.$$delegate_0.getValue()).intValue();
                SharedFlowImpl sharedFlowImpl = simBouncerInteractor._bouncerMessageChanged;
                if (str2 != null) {
                    String str3 = simPukInputModel.enteredSimPin;
                    if (str3 != null) {
                        if (!Intrinsics.areEqual(simBouncerRepositoryImpl._simPukInputModel.enteredSimPin, str)) {
                            simBouncerRepositoryImpl.simVerificationErrorMessage.setValue(simBouncerInteractor.resources.getString(R.string.kg_invalid_confirm_pin_hint));
                            SimBouncerRepository.setSimPukUserInput$default(simBouncerRepository, str2, 2);
                            String string = simBouncerInteractor.resources.getString(R.string.kg_puk_enter_pin_hint);
                            c081912.label = 5;
                            break;
                        } else {
                            SimBouncerInteractor$verifySimPuk$result$1 simBouncerInteractor$verifySimPuk$result$1 = new SimBouncerInteractor$verifySimPuk$result$1(simBouncerInteractor, iIntValue, str2, str3, null);
                            c081912.L$0 = simBouncerInteractor;
                            c081912.I$0 = iIntValue;
                            c081912.label = 6;
                            Object objWithContext = BuildersKt.withContext(simBouncerInteractor.backgroundDispatcher, simBouncerInteractor$verifySimPuk$result$1, c081912);
                            if (objWithContext != coroutineSingletons) {
                                obj = objWithContext;
                                i = iIntValue;
                                PinResult pinResult = (PinResult) obj;
                                simBouncerInteractor.resetSimPukUserInput();
                                result = pinResult.getResult();
                                SharedFlowImpl sharedFlowImpl2 = simBouncerInteractor._bouncerMessageChanged;
                                if (result != 0) {
                                    simBouncerInteractor.keyguardUpdateMonitor.reportSimUnlocked(i);
                                    c081912.L$0 = null;
                                    c081912.label = 7;
                                    break;
                                } else if (result == 1) {
                                    int attemptsRemaining = pinResult.getAttemptsRemaining();
                                    SimBouncerRepository simBouncerRepository2 = simBouncerInteractor.repository;
                                    if (attemptsRemaining > 2) {
                                        String pukPasswordErrorMessage = simBouncerInteractor.getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), Intrinsics.areEqual(((SimBouncerRepositoryImpl) simBouncerRepository2).isLockedEsim.$$delegate_0.getValue(), Boolean.TRUE));
                                        c081912.L$0 = null;
                                        c081912.label = 9;
                                        break;
                                    } else {
                                        SimBouncerRepositoryImpl simBouncerRepositoryImpl2 = (SimBouncerRepositoryImpl) simBouncerRepository2;
                                        simBouncerRepositoryImpl2.simVerificationErrorMessage.setValue(simBouncerInteractor.getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), Intrinsics.areEqual(simBouncerRepositoryImpl2.isLockedEsim.$$delegate_0.getValue(), Boolean.TRUE)));
                                        c081912.L$0 = null;
                                        c081912.label = 8;
                                        break;
                                    }
                                } else {
                                    String string2 = simBouncerInteractor.resources.getString(R.string.kg_password_puk_failed);
                                    c081912.L$0 = null;
                                    c081912.label = 10;
                                    break;
                                }
                            }
                        }
                    } else {
                        int length = str.length();
                        if (4 <= length && length < 9) {
                            simBouncerRepositoryImpl.getClass();
                            simBouncerRepositoryImpl._simPukInputModel = new SimPukInputModel(str2, str);
                            String string3 = simBouncerInteractor.resources.getString(R.string.kg_enter_confirm_pin_hint);
                            c081912.label = 3;
                            break;
                        } else {
                            String string4 = simBouncerInteractor.resources.getString(R.string.kg_invalid_sim_pin_hint);
                            c081912.label = 4;
                            break;
                        }
                    }
                } else if (str.length() < 8) {
                    String string5 = simBouncerInteractor.resources.getString(R.string.kg_invalid_sim_puk_hint);
                    c081912.label = 2;
                    break;
                } else {
                    SimBouncerRepository.setSimPukUserInput$default(simBouncerRepository, str, 2);
                    String string6 = simBouncerInteractor.resources.getString(R.string.kg_puk_enter_pin_hint);
                    c081912.label = 1;
                    break;
                }
                return coroutineSingletons;
            case 1:
            case 2:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 3:
            case 4:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 5:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 6:
                i = c081912.I$0;
                simBouncerInteractor = (SimBouncerInteractor) c081912.L$0;
                ResultKt.throwOnFailure(obj);
                PinResult pinResult2 = (PinResult) obj;
                simBouncerInteractor.resetSimPukUserInput();
                result = pinResult2.getResult();
                SharedFlowImpl sharedFlowImpl22 = simBouncerInteractor._bouncerMessageChanged;
                if (result != 0) {
                }
                return coroutineSingletons;
            case 7:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 8:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 9:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 10:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
