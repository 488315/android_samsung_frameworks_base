package com.android.systemui.qs.pipeline.data.repository;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
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

public final class QSSettingsRestoredBroadcastRepository implements QSSettingsRestoredRepository {
    public static final Companion Companion = new Companion(null);
    public static final IntentFilter INTENT_FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    public static final List requiredExtras = CollectionsKt__CollectionsKt.listOf("setting_name", "previous_value", "new_value");
    public final DeviceProvisionedController deviceProvisionedController;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 onUserSetupChangedForSomeUser;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 restoreData;

    public final class Companion {
        private Companion() {
        }

        public static final void access$validateIntent(Companion companion, Intent intent) {
            companion.getClass();
            for (String str : QSSettingsRestoredBroadcastRepository.requiredExtras) {
                if (!intent.hasExtra(str)) {
                    throw new IllegalStateException(intent + " doesn't have " + str);
                }
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public QSSettingsRestoredBroadcastRepository(BroadcastDispatcher broadcastDispatcher, DeviceProvisionedController deviceProvisionedController, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.deviceProvisionedController = deviceProvisionedController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        QSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1 qSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1 = new QSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1(this, null);
        conflatedCallbackFlow.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), FlowConflatedKt.conflatedCallbackFlow(qSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1));
        final MutexImpl Mutex$default = MutexKt.Mutex$default();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, INTENT_FILTER, UserHandle.ALL, new Function2() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new Pair((Intent) obj, Integer.valueOf(((BroadcastReceiver) obj2).getSendingUserId()));
            }
        }, 12);
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:20:0x0089 A[Catch: all -> 0x0095, TryCatch #0 {all -> 0x0095, blocks: (B:18:0x007c, B:20:0x0089, B:26:0x0097), top: B:17:0x007c }] */
                /* JADX WARN: Removed duplicated region for block: B:23:0x00b2  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0097 A[Catch: all -> 0x0095, TRY_LEAVE, TryCatch #0 {all -> 0x0095, blocks: (B:18:0x007c, B:20:0x0089, B:26:0x0097), top: B:17:0x007c }] */
                /* JADX WARN: Removed duplicated region for block: B:31:0x004a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        r5 = 0
                        if (r2 == 0) goto L4a
                        if (r2 == r4) goto L34
                        if (r2 != r3) goto L2c
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto Lc3
                    L2c:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L34:
                        int r9 = r0.I$0
                        java.lang.Object r10 = r0.L$3
                        kotlinx.coroutines.sync.Mutex r10 = (kotlinx.coroutines.sync.Mutex) r10
                        java.lang.Object r2 = r0.L$2
                        android.content.Intent r2 = (android.content.Intent) r2
                        java.lang.Object r4 = r0.L$1
                        kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                        java.lang.Object r6 = r0.L$0
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1$2 r6 = (com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1.AnonymousClass2) r6
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L7c
                    L4a:
                        kotlin.ResultKt.throwOnFailure(r11)
                        kotlin.Pair r10 = (kotlin.Pair) r10
                        java.lang.Object r11 = r10.component1()
                        r2 = r11
                        android.content.Intent r2 = (android.content.Intent) r2
                        java.lang.Object r10 = r10.component2()
                        java.lang.Number r10 = (java.lang.Number) r10
                        int r10 = r10.intValue()
                        r0.L$0 = r9
                        kotlinx.coroutines.flow.FlowCollector r11 = r9.$this_unsafeFlow
                        r0.L$1 = r11
                        r0.L$2 = r2
                        kotlinx.coroutines.sync.Mutex r6 = r9.$mutex$inlined
                        r0.L$3 = r6
                        r0.I$0 = r10
                        r0.label = r4
                        java.lang.Object r4 = r6.lock(r5, r0)
                        if (r4 != r1) goto L77
                        return r1
                    L77:
                        r4 = r11
                        r8 = r6
                        r6 = r9
                        r9 = r10
                        r10 = r8
                    L7c:
                        java.lang.Integer r11 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r11.<init>(r9)     // Catch: java.lang.Throwable -> L95
                        java.util.Map r7 = r6.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        boolean r11 = r7.containsKey(r11)     // Catch: java.lang.Throwable -> L95
                        if (r11 != 0) goto L97
                        java.lang.Integer r11 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r11.<init>(r9)     // Catch: java.lang.Throwable -> L95
                        java.util.Map r9 = r6.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        r9.put(r11, r2)     // Catch: java.lang.Throwable -> L95
                        r9 = r5
                        goto Lad
                    L95:
                        r9 = move-exception
                        goto Lc6
                    L97:
                        java.util.Map r11 = r6.$firstIntent$inlined     // Catch: java.lang.Throwable -> L95
                        java.lang.Integer r7 = new java.lang.Integer     // Catch: java.lang.Throwable -> L95
                        r7.<init>(r9)     // Catch: java.lang.Throwable -> L95
                        java.lang.Object r11 = r11.remove(r7)     // Catch: java.lang.Throwable -> L95
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r11)     // Catch: java.lang.Throwable -> L95
                        android.content.Intent r11 = (android.content.Intent) r11     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository r6 = r6.$this_run$inlined     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.qs.pipeline.data.model.RestoreData r9 = com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository.access$processIntents(r6, r9, r11, r2)     // Catch: java.lang.Throwable -> L95
                    Lad:
                        r10.unlock(r5)
                        if (r9 == 0) goto Lc3
                        r0.L$0 = r5
                        r0.L$1 = r5
                        r0.L$2 = r5
                        r0.L$3 = r5
                        r0.label = r3
                        java.lang.Object r9 = r4.emit(r9, r0)
                        if (r9 != r1) goto Lc3
                        return r1
                    Lc3:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    Lc6:
                        r10.unlock(r5)
                        throw r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, Mutex$default, linkedHashMap, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4(null));
        Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$map$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:22:0x0075 A[Catch: all -> 0x009b, TRY_ENTER, TryCatch #0 {all -> 0x009b, blocks: (B:18:0x005e, B:19:0x006d, B:22:0x0075, B:25:0x008f, B:30:0x009d, B:31:0x00a5, B:33:0x00ab, B:35:0x00bb, B:36:0x00cc, B:38:0x00d2, B:40:0x00f0), top: B:17:0x005e }] */
                /* JADX WARN: Removed duplicated region for block: B:29:0x009d A[EDGE_INSN: B:29:0x009d->B:30:0x009d BREAK  A[LOOP:0: B:19:0x006d->B:27:0x006d], SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00ab A[Catch: all -> 0x009b, LOOP:1: B:31:0x00a5->B:33:0x00ab, LOOP_END, TryCatch #0 {all -> 0x009b, blocks: (B:18:0x005e, B:19:0x006d, B:22:0x0075, B:25:0x008f, B:30:0x009d, B:31:0x00a5, B:33:0x00ab, B:35:0x00bb, B:36:0x00cc, B:38:0x00d2, B:40:0x00f0), top: B:17:0x005e }] */
                /* JADX WARN: Removed duplicated region for block: B:38:0x00d2 A[Catch: all -> 0x009b, LOOP:2: B:36:0x00cc->B:38:0x00d2, LOOP_END, TryCatch #0 {all -> 0x009b, blocks: (B:18:0x005e, B:19:0x006d, B:22:0x0075, B:25:0x008f, B:30:0x009d, B:31:0x00a5, B:33:0x00ab, B:35:0x00bb, B:36:0x00cc, B:38:0x00d2, B:40:0x00f0), top: B:17:0x005e }] */
                /* JADX WARN: Removed duplicated region for block: B:43:0x0106 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:49:0x0046  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        Method dump skipped, instructions count: 270
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$8$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, Mutex$default, linkedHashMap, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        int i = FlowKt__MergeKt.$r8$clinit;
        Flow buffer$default = FlowKt.buffer$default(FlowKt.flowOn(FlowKt.merge(flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(flow2), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$2(null)), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$3(qSPipelineLogger, null))), coroutineDispatcher), 10);
        SharingStarted.Companion.getClass();
        new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.shareIn(buffer$default, coroutineScope, SharingStarted.Companion.Eagerly, 0), new QSSettingsRestoredBroadcastRepository$restoreData$2(qSPipelineLogger));
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
        List<String> split$default = StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6);
        TileSpec.Companion companion2 = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
        for (String str2 : split$default) {
            companion2.getClass();
            arrayList.add(TileSpec.Companion.create(str2));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (!Intrinsics.areEqual((TileSpec) next, TileSpec.Invalid.INSTANCE)) {
                arrayList2.add(next);
            }
        }
        return new RestoreData(tilesList, CollectionsKt___CollectionsKt.toSet(arrayList2), i);
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
