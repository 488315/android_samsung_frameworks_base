package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomTilePackageUpdatesRepositoryImpl implements CustomTilePackageUpdatesRepository {
    public static final Companion Companion = new Companion(null);
    public static final IntentFilter INTENT_FILTER;
    public final CoroutineContext backgroundCoroutineContext;
    public final Context context;
    public final Map perUserCache = new LinkedHashMap();
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        INTENT_FILTER = intentFilter;
    }

    public CustomTilePackageUpdatesRepositoryImpl(TileSpec.CustomTileSpec customTileSpec, Context context, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.context = context;
        this.tileScope = coroutineScope;
        this.backgroundCoroutineContext = coroutineContext;
    }

    public final Flow getPackageChangesForUser(UserHandle userHandle) {
        Flow flow;
        synchronized (this.perUserCache) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) this.perUserCache;
                Object obj = linkedHashMap.get(userHandle);
                if (obj == null) {
                    ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                    CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1 customTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1 = new CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1(this, userHandle, null);
                    conflatedCallbackFlow.getClass();
                    final Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(customTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1);
                    final Flow flow2 = new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ CustomTilePackageUpdatesRepositoryImpl this$0;

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                            /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, CustomTilePackageUpdatesRepositoryImpl customTilePackageUpdatesRepositoryImpl) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = customTilePackageUpdatesRepositoryImpl;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                                /*
                                    r5 = this;
                                    boolean r0 = r7 instanceof com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r7
                                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1$2$1
                                    r0.<init>(r7)
                                L18:
                                    java.lang.Object r7 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r7)
                                    goto L69
                                L27:
                                    java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                    java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                    r5.<init>(r6)
                                    throw r5
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r7)
                                    r7 = r6
                                    android.content.Intent r7 = (android.content.Intent) r7
                                    if (r7 != 0) goto L38
                                    goto L69
                                L38:
                                    java.lang.String r2 = r7.getAction()
                                    if (r2 == 0) goto L69
                                    android.content.IntentFilter r4 = com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl.INTENT_FILTER
                                    boolean r2 = r4.matchAction(r2)
                                    if (r2 != r3) goto L69
                                    java.lang.String r2 = "android.intent.extra.changed_component_name_list"
                                    java.lang.String[] r7 = r7.getStringArrayExtra(r2)
                                    if (r7 == 0) goto L69
                                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl r2 = r5.this$0
                                    com.android.systemui.qs.pipeline.shared.TileSpec$CustomTileSpec r2 = r2.tileSpec
                                    android.content.ComponentName r2 = r2.componentName
                                    java.lang.String r2 = r2.getPackageName()
                                    boolean r7 = kotlin.collections.ArraysKt___ArraysKt.contains(r7, r2)
                                    if (r7 != r3) goto L69
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                    java.lang.Object r5 = r5.emit(r6, r0)
                                    if (r5 != r1) goto L69
                                    return r1
                                L69:
                                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                    return r5
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    obj = FlowKt.shareIn(FlowKt.flowOn(new FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                            /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1$2$1, reason: invalid class name */
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
                                    boolean r0 = r6 instanceof com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L41
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    android.content.Intent r5 = (android.content.Intent) r5
                                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L41
                                    return r1
                                L41:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new CustomTilePackageUpdatesRepositoryImpl$getPackageChangesForUser$1$1$1(this, userHandle, null)), this.backgroundCoroutineContext), this.tileScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
                    linkedHashMap.put(userHandle, obj);
                }
                flow = (Flow) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        return flow;
    }
}
