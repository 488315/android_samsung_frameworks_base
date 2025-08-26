package com.android.systemui.deviceentry.domain.interactor;

import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class BiometricMessageInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricMessageInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricMessageInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, BiometricMessageInteractor biometricMessageInteractor) {
        super(3, continuation);
        this.this$0 = biometricMessageInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricMessageInteractor$special$$inlined$flatMapLatest$2 biometricMessageInteractor$special$$inlined$flatMapLatest$2 = new BiometricMessageInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        biometricMessageInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        biometricMessageInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return biometricMessageInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Pair pair = (Pair) this.L$1;
            boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
            if (zBooleanValue && zBooleanValue2) {
                final BiometricMessageInteractor$special$$inlined$map$3 biometricMessageInteractor$special$$inlined$map$3 = this.this$0.coExFaceAcquisitionMsgIdsToShow;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1

                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
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
                                final Set set = (Set) obj;
                                Function1 function1 = 
                                /*  JADX ERROR: Method code generation error
                                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0036: CONSTRUCTOR (r6v2 'function1' kotlin.jvm.functions.Function1) = (r5v1 'set' java.util.Set A[DONT_INLINE]) A[DECLARE_VAR, MD:(java.util.Set<java.lang.Integer>):void (m)] (LINE:55) call: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$1$1.<init>(java.util.Set):void type: CONSTRUCTOR in method: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$1$1, state: NOT_LOADED
                                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                    	... 21 more
                                    */
                                /*
                                    this = this;
                                    boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L44
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    java.util.Set r5 = (java.util.Set) r5
                                    com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$1$1 r6 = new com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$1$1
                                    r6.<init>(r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r6, r0)
                                    if (r4 != r1) goto L44
                                    return r1
                                L44:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$lambda$13$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object objCollect = biometricMessageInteractor$special$$inlined$map$3.collect(new AnonymousClass2(flowCollector2), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                } else {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = zBooleanValue2 ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Function1() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                            return Boolean.TRUE;
                        }
                    }) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Function1() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$filterConditionForFaceHelpMessages$4$3
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                            return Boolean.FALSE;
                        }
                    });
                }
                this.label = 1;
                if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
