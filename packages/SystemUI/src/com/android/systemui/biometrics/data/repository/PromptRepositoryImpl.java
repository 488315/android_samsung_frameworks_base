package com.android.systemui.biometrics.data.repository;

import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PromptRepositoryImpl implements PromptRepository {
    public final StateFlowImpl _challenge;
    public final PromptRepositoryImpl$special$$inlined$map$2 _isConfirmationRequired;
    public final StateFlowImpl _opPackageName;
    public final StateFlowImpl _promptInfo;
    public final StateFlowImpl _promptKind;
    public final StateFlowImpl _requestId;
    public final StateFlowImpl _userId;
    public final AuthController authController;
    public final ReadonlyStateFlow challenge;
    public final FaceSettingsRepository faceSettings;
    public final Flow isConfirmationRequired;
    public final ReadonlyStateFlow opPackageName;
    public final ReadonlyStateFlow promptInfo;
    public final ReadonlyStateFlow promptKind;
    public final ReadonlyStateFlow requestId;
    public final ReadonlyStateFlow userId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public PromptRepositoryImpl(FaceSettingsRepository faceSettingsRepository, AuthController authController) {
        this.faceSettings = faceSettingsRepository;
        this.authController = authController;
        FlowConflatedKt.conflatedCallbackFlow(new PromptRepositoryImpl$isShowing$1(this, null));
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._promptInfo = stateFlowImplMutableStateFlow;
        this.promptInfo = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._challenge = stateFlowImplMutableStateFlow2;
        this.challenge = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        final StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._userId = stateFlowImplMutableStateFlow3;
        this.userId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._requestId = stateFlowImplMutableStateFlow4;
        this.requestId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(PromptKind.None.INSTANCE);
        this._promptKind = stateFlowImplMutableStateFlow5;
        this.promptKind = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(null);
        this._opPackageName = stateFlowImplMutableStateFlow6;
        this.opPackageName = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PromptRepositoryImpl this$0;

                /* renamed from: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, PromptRepositoryImpl promptRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = promptRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj3 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj3);
                        final Integer num = (Integer) obj;
                        final FaceSettingsRepositoryImpl faceSettingsRepositoryImpl = (FaceSettingsRepositoryImpl) this.this$0.faceSettings;
                        if (num != null) {
                            ConcurrentHashMap concurrentHashMap = faceSettingsRepositoryImpl.userSettings;
                            final Function1 function1 = 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0040: CONSTRUCTOR (r4v0 'function1' kotlin.jvm.functions.Function1) = 
                                  (r6v1 'num' java.lang.Integer A[DONT_INLINE])
                                  (r7v4 'faceSettingsRepositoryImpl' com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl A[DONT_INLINE])
                                 A[DECLARE_VAR, MD:(java.lang.Integer, com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl):void (m)] (LINE:65) call: com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$$ExternalSyntheticLambda0.<init>(java.lang.Integer, com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl):void type: CONSTRUCTOR in method: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes.dex
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
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$$ExternalSyntheticLambda0, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                	... 27 more
                                */
                            /*
                                this = this;
                                boolean r0 = r7 instanceof com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L62
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                java.lang.Integer r6 = (java.lang.Integer) r6
                                com.android.systemui.biometrics.data.repository.PromptRepositoryImpl r7 = r5.this$0
                                com.android.systemui.biometrics.data.repository.FaceSettingsRepository r7 = r7.faceSettings
                                com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl r7 = (com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl) r7
                                if (r6 == 0) goto L52
                                java.util.concurrent.ConcurrentHashMap r2 = r7.userSettings
                                com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$$ExternalSyntheticLambda0 r4 = new com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$$ExternalSyntheticLambda0
                                r4.<init>(r6, r7)
                                com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$sam$java_util_function_Function$0 r7 = new com.android.systemui.biometrics.data.repository.FaceSettingsRepositoryImpl$sam$java_util_function_Function$0
                                r7.<init>(r4)
                                java.lang.Object r6 = r2.computeIfAbsent(r6, r7)
                                r6.getClass()
                                com.android.systemui.biometrics.data.repository.FaceUserSettingsRepository r6 = (com.android.systemui.biometrics.data.repository.FaceUserSettingsRepository) r6
                                goto L57
                            L52:
                                r7.getClass()
                                com.android.systemui.biometrics.data.repository.FaceUserSettingsRepositoryImpl$Empty r6 = com.android.systemui.biometrics.data.repository.FaceUserSettingsRepositoryImpl.Empty.INSTANCE
                            L57:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r6, r0)
                                if (r5 != r1) goto L62
                                return r1
                            L62:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = stateFlowImplMutableStateFlow3.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }), new PromptRepositoryImpl$special$$inlined$flatMapLatest$1(null)));
                ?? r1 = new Flow() { // from class: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$2

                    /* renamed from: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                                PromptInfo promptInfo = (PromptInfo) obj;
                                Boolean boolValueOf = Boolean.valueOf(promptInfo != null ? promptInfo.isConfirmationRequested() : false);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = stateFlowImplMutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                this._isConfirmationRequired = r1;
                this.isConfirmationRequired = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(r1, flowDistinctUntilChanged, new PromptRepositoryImpl$isConfirmationRequired$1(null)));
            }
        }
