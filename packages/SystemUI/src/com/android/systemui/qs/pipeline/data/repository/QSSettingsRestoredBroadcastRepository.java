package com.android.systemui.qs.pipeline.data.repository;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSSettingsRestoredBroadcastRepository implements QSSettingsRestoredRepository {
    public static final Companion Companion = new Companion(null);
    public static final IntentFilter INTENT_FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    public static final List requiredExtras = Arrays.asList("setting_name", "previous_value", "new_value");
    public final DeviceProvisionedController deviceProvisionedController;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 onUserSetupChangedForSomeUser;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 restoreData;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$validateIntent(Companion companion, Intent intent) {
            companion.getClass();
            for (String str : QSSettingsRestoredBroadcastRepository.requiredExtras) {
                if (!intent.hasExtra(str)) {
                    throw new IllegalStateException(intent + " doesn't have " + str);
                }
            }
        }

        private Companion() {
        }
    }

    public QSSettingsRestoredBroadcastRepository(BroadcastDispatcher broadcastDispatcher, DeviceProvisionedController deviceProvisionedController, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.deviceProvisionedController = deviceProvisionedController;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), FlowConflatedKt.conflatedCallbackFlow(new QSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1(this, null)));
        this.onUserSetupChangedForSomeUser = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        final MutexImpl Mutex$default = MutexKt.Mutex$default();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, INTENT_FILTER, UserHandle.ALL, new QSSettingsRestoredBroadcastRepository$$ExternalSyntheticLambda0(), 12);
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L63
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        kotlin.Pair r6 = (kotlin.Pair) r6
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$Companion r2 = com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository.Companion
                        java.lang.Object r6 = r6.getFirst()
                        android.content.Intent r6 = (android.content.Intent) r6
                        r2.getClass()
                        java.lang.String r2 = "setting_name"
                        java.lang.String r6 = r6.getStringExtra(r2)
                        java.lang.String r2 = "sysui_qs_tiles"
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r2 != 0) goto L58
                        java.lang.String r2 = "qs_auto_tiles"
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L63
                    L58:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L63
                        return r1
                    L63:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
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

                public AnonymousClass2(FlowCollector flowCollector, Mutex mutex, Map map, QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mutex$inlined = mutex;
                    this.$firstIntent$inlined = map;
                    this.$this_run$inlined = qSSettingsRestoredBroadcastRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:25:0x00c0, code lost:
                
                    if (r4.emit(r8, r0) == r1) goto L31;
                 */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0089 A[Catch: all -> 0x0095, TryCatch #0 {all -> 0x0095, blocks: (B:19:0x007c, B:21:0x0089, B:27:0x0097), top: B:18:0x007c }] */
                /* JADX WARN: Removed duplicated region for block: B:24:0x00b2  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0097 A[Catch: all -> 0x0095, TRY_LEAVE, TryCatch #0 {all -> 0x0095, blocks: (B:19:0x007c, B:21:0x0089, B:27:0x0097), top: B:18:0x007c }] */
                /* JADX WARN: Removed duplicated region for block: B:32:0x0049  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L49
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto Lc3
                    L2b:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L33:
                        int r8 = r0.I$0
                        java.lang.Object r9 = r0.L$3
                        kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
                        java.lang.Object r2 = r0.L$2
                        android.content.Intent r2 = (android.content.Intent) r2
                        java.lang.Object r4 = r0.L$1
                        kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                        java.lang.Object r5 = r0.L$0
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2 r5 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1.AnonymousClass2) r5
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L7b
                    L49:
                        kotlin.ResultKt.throwOnFailure(r10)
                        kotlin.Pair r9 = (kotlin.Pair) r9
                        java.lang.Object r10 = r9.component1()
                        r2 = r10
                        android.content.Intent r2 = (android.content.Intent) r2
                        java.lang.Object r9 = r9.component2()
                        java.lang.Number r9 = (java.lang.Number) r9
                        int r9 = r9.intValue()
                        r0.L$0 = r8
                        kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                        r0.L$1 = r10
                        r0.L$2 = r2
                        kotlinx.coroutines.sync.Mutex r5 = r8.$mutex$inlined
                        r0.L$3 = r5
                        r0.I$0 = r9
                        r0.label = r4
                        java.lang.Object r4 = r5.lock(r0)
                        if (r4 != r1) goto L76
                        goto Lc2
                    L76:
                        r4 = r5
                        r5 = r8
                        r8 = r9
                        r9 = r4
                        r4 = r10
                    L7b:
                        r10 = 0
                        java.lang.Integer r6 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r6.<init>(r8)     // Catch: java.lang.Throwable -> L95
                        java.util.Map r7 = r5.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        boolean r6 = r7.containsKey(r6)     // Catch: java.lang.Throwable -> L95
                        if (r6 != 0) goto L97
                        java.lang.Integer r6 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r6.<init>(r8)     // Catch: java.lang.Throwable -> L95
                        java.util.Map r8 = r5.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        r8.put(r6, r2)     // Catch: java.lang.Throwable -> L95
                        r8 = r10
                        goto Lad
                    L95:
                        r8 = move-exception
                        goto Lc6
                    L97:
                        java.util.Map r6 = r5.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        java.lang.Integer r7 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r7.<init>(r8)     // Catch: java.lang.Throwable -> L95
                        java.lang.Object r6 = r6.remove(r7)     // Catch: java.lang.Throwable -> L95
                        r6.getClass()     // Catch: java.lang.Throwable -> L95
                        android.content.Intent r6 = (android.content.Intent) r6     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository r5 = r5.$this_run$inlined     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.qs.pipeline.data.model.RestoreData r8 = com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository.access$processIntents(r5, r8, r6, r2)     // Catch: java.lang.Throwable -> L95
                    Lad:
                        r9.unlock(r10)
                        if (r8 == 0) goto Lc3
                        r0.L$0 = r10
                        r0.L$1 = r10
                        r0.L$2 = r10
                        r0.L$3 = r10
                        r0.label = r3
                        java.lang.Object r8 = r4.emit(r8, r0)
                        if (r8 != r1) goto Lc3
                    Lc2:
                        return r1
                    Lc3:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    Lc6:
                        r9.unlock(r10)
                        throw r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, Mutex$default, linkedHashMap, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4(null));
        Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
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

                public AnonymousClass2(FlowCollector flowCollector, Mutex mutex, Map map, QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mutex$inlined = mutex;
                    this.$firstIntent$inlined = map;
                    this.$this_run$inlined = qSSettingsRestoredBroadcastRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:43:0x0105, code lost:
                
                    if (r10.emit(r4, r0) != r1) goto L41;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:44:0x0107, code lost:
                
                    return r1;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:51:0x005a, code lost:
                
                    if (r11.lock(r0) == r1) goto L40;
                 */
                /* JADX WARN: Removed duplicated region for block: B:23:0x0076 A[Catch: all -> 0x009c, TRY_ENTER, TryCatch #0 {all -> 0x009c, blocks: (B:19:0x005f, B:20:0x006e, B:23:0x0076, B:26:0x0090, B:31:0x009e, B:32:0x00a6, B:34:0x00ac, B:36:0x00bc, B:37:0x00cd, B:39:0x00d3, B:41:0x00f1), top: B:18:0x005f }] */
                /* JADX WARN: Removed duplicated region for block: B:30:0x009e A[EDGE_INSN: B:30:0x009e->B:31:0x009e BREAK  A[LOOP:0: B:20:0x006e->B:28:0x006e], SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:34:0x00ac A[Catch: all -> 0x009c, LOOP:1: B:32:0x00a6->B:34:0x00ac, LOOP_END, TryCatch #0 {all -> 0x009c, blocks: (B:19:0x005f, B:20:0x006e, B:23:0x0076, B:26:0x0090, B:31:0x009e, B:32:0x00a6, B:34:0x00ac, B:36:0x00bc, B:37:0x00cd, B:39:0x00d3, B:41:0x00f1), top: B:18:0x005f }] */
                /* JADX WARN: Removed duplicated region for block: B:39:0x00d3 A[Catch: all -> 0x009c, LOOP:2: B:37:0x00cd->B:39:0x00d3, LOOP_END, TryCatch #0 {all -> 0x009c, blocks: (B:19:0x005f, B:20:0x006e, B:23:0x0076, B:26:0x0090, B:31:0x009e, B:32:0x00a6, B:34:0x00ac, B:36:0x00bc, B:37:0x00cd, B:39:0x00d3, B:41:0x00f1), top: B:18:0x005f }] */
                /* JADX WARN: Removed duplicated region for block: B:50:0x0045  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        Method dump skipped, instructions count: 271
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, Mutex$default, linkedHashMap, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        int i = FlowKt__MergeKt.$r8$clinit;
        Flow buffer$default = FlowKt.buffer$default(FlowKt.flowOn(FlowKt.merge(flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(flow2), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$2(null)), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$3(qSPipelineLogger, null))), coroutineDispatcher), 10, 2);
        SharingStarted.Companion.getClass();
        this.restoreData = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.shareIn(buffer$default, coroutineScope, SharingStarted.Companion.Eagerly, 0), new QSSettingsRestoredBroadcastRepository$restoreData$2(qSPipelineLogger));
    }

    public static final RestoreData access$processIntents(QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository, int i, Intent intent, Intent intent2) {
        Pair pair;
        qSSettingsRestoredBroadcastRepository.getClass();
        Companion companion = Companion;
        Companion.access$validateIntent(companion, intent);
        Companion.access$validateIntent(companion, intent2);
        String stringExtra = intent.getStringExtra("setting_name");
        String stringExtra2 = intent2.getStringExtra("setting_name");
        if (Intrinsics.areEqual(stringExtra, "sysui_qs_tiles") && Intrinsics.areEqual(stringExtra2, "qs_auto_tiles")) {
            pair = new Pair(intent, intent2);
        } else {
            if (!Intrinsics.areEqual(stringExtra, "qs_auto_tiles") || !Intrinsics.areEqual(stringExtra2, "sysui_qs_tiles")) {
                throw new IllegalStateException("Wrong intents (" + intent + ", " + intent2 + ")");
            }
            pair = new Pair(intent2, intent);
        }
        Intent intent3 = (Intent) pair.component1();
        Intent intent4 = (Intent) pair.component2();
        String stringExtra3 = intent3.getStringExtra("new_value");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        tilesSettingConverter.getClass();
        List tilesList = TilesSettingConverter.toTilesList(stringExtra3);
        String stringExtra4 = intent4.getStringExtra("new_value");
        String str = stringExtra4 != null ? stringExtra4 : "";
        tilesSettingConverter.getClass();
        return new RestoreData(tilesList, TilesSettingConverter.toTilesSet(str), i);
    }

    public static final RestoreData access$processSingleIntent(QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository, int i, Intent intent) {
        qSSettingsRestoredBroadcastRepository.getClass();
        Companion.access$validateIntent(Companion, intent);
        if (Intrinsics.areEqual(intent.getStringExtra("setting_name"), "sysui_qs_tiles")) {
            String stringExtra = intent.getStringExtra("new_value");
            if (stringExtra == null) {
                stringExtra = "";
            }
            TilesSettingConverter.INSTANCE.getClass();
            return new RestoreData(TilesSettingConverter.toTilesList(stringExtra), EmptySet.INSTANCE, i);
        }
        throw new IllegalStateException("Single intent restored for user " + i + " is not tiles: " + intent);
    }
}
