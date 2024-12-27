package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class DeviceEntryIconViewModel$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewModel$special$$inlined$flatMapLatest$3(Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel) {
        super(3, continuation);
        this.this$0 = deviceEntryIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewModel$special$$inlined$flatMapLatest$3 deviceEntryIconViewModel$special$$inlined$flatMapLatest$3 = new DeviceEntryIconViewModel$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        deviceEntryIconViewModel$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        deviceEntryIconViewModel$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return deviceEntryIconViewModel$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final DeviceEntryIconViewModel deviceEntryIconViewModel = this.this$0;
                final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = deviceEntryIconViewModel.iconType;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ DeviceEntryIconViewModel this$0;

                        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, DeviceEntryIconViewModel deviceEntryIconViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = deviceEntryIconViewModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L67
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.keyguard.ui.view.DeviceEntryIconView$IconType r5 = (com.android.systemui.keyguard.ui.view.DeviceEntryIconView.IconType) r5
                                int r6 = com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel.$r8$clinit
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r4.this$0
                                r6.getClass()
                                int[] r6 = com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel.WhenMappings.$EnumSwitchMapping$1
                                int r5 = r5.ordinal()
                                r5 = r6[r5]
                                if (r5 == r3) goto L5a
                                r6 = 2
                                if (r5 == r6) goto L5a
                                r6 = 3
                                if (r5 == r6) goto L57
                                r6 = 4
                                if (r5 != r6) goto L51
                                com.android.systemui.keyguard.ui.view.DeviceEntryIconView$AccessibilityHintType r5 = com.android.systemui.keyguard.ui.view.DeviceEntryIconView.AccessibilityHintType.NONE
                                goto L5c
                            L51:
                                kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                                r4.<init>()
                                throw r4
                            L57:
                                com.android.systemui.keyguard.ui.view.DeviceEntryIconView$AccessibilityHintType r5 = com.android.systemui.keyguard.ui.view.DeviceEntryIconView.AccessibilityHintType.ENTER
                                goto L5c
                            L5a:
                                com.android.systemui.keyguard.ui.view.DeviceEntryIconView$AccessibilityHintType r5 = com.android.systemui.keyguard.ui.view.DeviceEntryIconView.AccessibilityHintType.BOUNCER
                            L5c:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L67
                                return r1
                            L67:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$accessibilityDelegateHint$lambda$7$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, deviceEntryIconViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(DeviceEntryIconView.AccessibilityHintType.NONE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
