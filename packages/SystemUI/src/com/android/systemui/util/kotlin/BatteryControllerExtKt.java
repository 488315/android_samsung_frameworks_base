package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class BatteryControllerExtKt {

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$getBatteryLevel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_getBatteryLevel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_getBatteryLevel = batteryController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(BatteryController batteryController, BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1 batteryControllerExtKt$getBatteryLevel$1$batteryCallback$1) {
            ((BatteryControllerImpl) batteryController).removeCallback(batteryControllerExtKt$getBatteryLevel$1$batteryCallback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_getBatteryLevel, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1
                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
                    public void dump(PrintWriter printWriter, String[] strArr) {
                        printWriter.println(this);
                    }

                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3, int i6) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Integer.valueOf(i2));
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryUnknownStateChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onExtremeBatterySaverChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsBatteryDefenderChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsIncompatibleChargingChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onPowerSaveChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onWirelessChargingChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onReverseChanged(boolean z, int i2, String str) {
                    }
                };
                ((BatteryControllerImpl) this.$this_getBatteryLevel).addCallback(batteryStateChangeCallback);
                BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 = new BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(this.$this_getBatteryLevel, batteryStateChangeCallback, 0);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$getBatteryLevel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Integer num = new Integer(0);
                this.label = 1;
                if (flowCollector.emit(num, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isBatteryPowerSaveEnabled$1, reason: invalid class name and case insensitive filesystem */
    final class C11461 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isBatteryPowerSaveEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11461(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isBatteryPowerSaveEnabled = batteryController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(BatteryController batteryController, BatteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1 batteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1) {
            ((BatteryControllerImpl) batteryController).removeCallback(batteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11461 c11461 = new C11461(this.$this_isBatteryPowerSaveEnabled, continuation);
            c11461.L$0 = obj;
            return c11461;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1
                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
                    public void dump(PrintWriter printWriter, String[] strArr) {
                        printWriter.println(this);
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public void onPowerSaveChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(z));
                    }

                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3, int i6) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryUnknownStateChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onExtremeBatterySaverChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsBatteryDefenderChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsIncompatibleChargingChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onWirelessChargingChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onReverseChanged(boolean z, int i2, String str) {
                    }
                };
                ((BatteryControllerImpl) this.$this_isBatteryPowerSaveEnabled).addCallback(batteryStateChangeCallback);
                BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 = new BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(this.$this_isBatteryPowerSaveEnabled, batteryStateChangeCallback, 1);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C11461) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isBatteryPowerSaveEnabled$2, reason: invalid class name and case insensitive filesystem */
    final class C11472 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isBatteryPowerSaveEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11472(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isBatteryPowerSaveEnabled = batteryController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11472 c11472 = new C11472(this.$this_isBatteryPowerSaveEnabled, continuation);
            c11472.L$0 = obj;
            return c11472;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Boolean boolValueOf = Boolean.valueOf(((BatteryControllerImpl) this.$this_isBatteryPowerSaveEnabled).mPowerSave);
                this.label = 1;
                if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11472) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$1, reason: invalid class name and case insensitive filesystem */
    final class C11481 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isDevicePluggedIn;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11481(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isDevicePluggedIn = batteryController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(BatteryController batteryController, BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1 batteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1) {
            ((BatteryControllerImpl) batteryController).removeCallback(batteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11481 c11481 = new C11481(this.$this_isDevicePluggedIn, continuation);
            c11481.L$0 = obj;
            return c11481;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1
                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
                    public void dump(PrintWriter printWriter, String[] strArr) {
                        printWriter.println(this);
                    }

                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3, int i6) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(z));
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryUnknownStateChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onExtremeBatterySaverChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsBatteryDefenderChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsIncompatibleChargingChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onPowerSaveChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onWirelessChargingChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onReverseChanged(boolean z, int i2, String str) {
                    }
                };
                ((BatteryControllerImpl) this.$this_isDevicePluggedIn).addCallback(batteryStateChangeCallback);
                BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 = new BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(this.$this_isDevicePluggedIn, batteryStateChangeCallback, 2);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C11481) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$2, reason: invalid class name and case insensitive filesystem */
    final class C11492 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isDevicePluggedIn;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11492(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isDevicePluggedIn = batteryController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11492 c11492 = new C11492(this.$this_isDevicePluggedIn, continuation);
            c11492.L$0 = obj;
            return c11492;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Boolean boolValueOf = Boolean.valueOf(((BatteryControllerImpl) this.$this_isDevicePluggedIn).mPluggedIn);
                this.label = 1;
                if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11492) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isExtremePowerSaverEnabled$1, reason: invalid class name and case insensitive filesystem */
    final class C11501 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isExtremePowerSaverEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11501(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isExtremePowerSaverEnabled = batteryController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(BatteryController batteryController, BatteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1 batteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1) {
            ((BatteryControllerImpl) batteryController).removeCallback(batteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11501 c11501 = new C11501(this.$this_isExtremePowerSaverEnabled, continuation);
            c11501.L$0 = obj;
            return c11501;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1
                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
                    public void dump(PrintWriter printWriter, String[] strArr) {
                        printWriter.println(this);
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                    }

                    public void onExtremeBatterySaverChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(z));
                    }

                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3, int i6) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onBatteryUnknownStateChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsBatteryDefenderChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onIsIncompatibleChargingChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onPowerSaveChanged(boolean z) {
                    }

                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public /* bridge */ /* synthetic */ void onWirelessChargingChanged(boolean z) {
                    }

                    public /* bridge */ /* synthetic */ void onReverseChanged(boolean z, int i2, String str) {
                    }
                };
                ((BatteryControllerImpl) this.$this_isExtremePowerSaverEnabled).addCallback(batteryStateChangeCallback);
                BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 = new BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(this.$this_isExtremePowerSaverEnabled, batteryStateChangeCallback, 3);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C11501) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.BatteryControllerExtKt$isExtremePowerSaverEnabled$2, reason: invalid class name and case insensitive filesystem */
    final class C11512 extends SuspendLambda implements Function2 {
        final /* synthetic */ BatteryController $this_isExtremePowerSaverEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11512(BatteryController batteryController, Continuation continuation) {
            super(2, continuation);
            this.$this_isExtremePowerSaverEnabled = batteryController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11512 c11512 = new C11512(this.$this_isExtremePowerSaverEnabled, continuation);
            c11512.L$0 = obj;
            return c11512;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                this.$this_isExtremePowerSaverEnabled.getClass();
                Boolean bool = Boolean.FALSE;
                this.label = 1;
                if (flowCollector.emit(bool, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11512) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public static final Flow getBatteryLevel(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(null), FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(batteryController, null)));
    }

    public static final Flow isBatteryPowerSaveEnabled(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new C11472(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new C11461(batteryController, null)));
    }

    public static final Flow isDevicePluggedIn(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new C11492(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new C11481(batteryController, null)));
    }

    public static final Flow isExtremePowerSaverEnabled(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new C11512(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new C11501(batteryController, null)));
    }
}
