package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTileDefaultsRepositoryImpl implements CustomTileDefaultsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ReadonlySharedFlow defaults;
    public final SharedFlowImpl defaultsRequests;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultsResult {
        public final CustomTileDefaults data;
        public final UserHandle user;

        public DefaultsResult(UserHandle userHandle, CustomTileDefaults customTileDefaults) {
            this.user = userHandle;
            this.data = customTileDefaults;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DefaultsResult)) {
                return false;
            }
            DefaultsResult defaultsResult = (DefaultsResult) obj;
            return Intrinsics.areEqual(this.user, defaultsResult.user) && Intrinsics.areEqual(this.data, defaultsResult.data);
        }

        public final int hashCode() {
            return this.data.hashCode() + (this.user.hashCode() * 31);
        }

        public final String toString() {
            return "DefaultsResult(user=" + this.user + ", data=" + this.data + ")";
        }
    }

    static {
        new Companion(null);
    }

    public CustomTileDefaultsRepositoryImpl(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this.defaultsRequests = MutableSharedFlow$default;
        final DistinctFlowImpl distinctUntilChanged = FlowKt.distinctUntilChanged(MutableSharedFlow$default, new CustomTileDefaultsRepositoryImpl$$ExternalSyntheticLambda0());
        this.defaults = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CustomTileDefaultsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CustomTileDefaultsRepositoryImpl customTileDefaultsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = customTileDefaultsRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x007a, code lost:
                
                    if (r7.emit(r2, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        r5 = 0
                        if (r2 == 0) goto L3f
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L7d
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$1
                        android.os.UserHandle r6 = (android.os.UserHandle) r6
                        java.lang.Object r7 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L69
                    L3f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsRequest r7 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl.DefaultsRequest) r7
                        android.os.UserHandle r8 = r7.user
                        android.content.ComponentName r7 = r7.componentName
                        kotlinx.coroutines.flow.FlowCollector r2 = r6.$this_unsafeFlow
                        r0.L$0 = r2
                        r0.L$1 = r8
                        r0.label = r4
                        int r4 = com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl.$r8$clinit
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl r6 = r6.this$0
                        r6.getClass()
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$loadDefaults$2 r4 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$loadDefaults$2
                        r4.<init>(r6, r8, r7, r5)
                        kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
                        java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r4, r0)
                        if (r6 != r1) goto L65
                        goto L7c
                    L65:
                        r7 = r8
                        r8 = r6
                        r6 = r7
                        r7 = r2
                    L69:
                        com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults r8 = (com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults) r8
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsResult r2 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsResult
                        r2.<init>(r6, r8)
                        r0.L$0 = r5
                        r0.L$1 = r5
                        r0.label = r3
                        java.lang.Object r6 = r7.emit(r2, r0)
                        if (r6 != r1) goto L7d
                    L7c:
                        return r1
                    L7d:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultsRequest {
        public final ComponentName componentName;
        public final boolean force;
        public final UserHandle user;

        public DefaultsRequest(UserHandle userHandle, ComponentName componentName, boolean z) {
            this.user = userHandle;
            this.componentName = componentName;
            this.force = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DefaultsRequest)) {
                return false;
            }
            DefaultsRequest defaultsRequest = (DefaultsRequest) obj;
            return Intrinsics.areEqual(this.user, defaultsRequest.user) && Intrinsics.areEqual(this.componentName, defaultsRequest.componentName) && this.force == defaultsRequest.force;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.force) + ((this.componentName.hashCode() + (this.user.hashCode() * 31)) * 31);
        }

        public final String toString() {
            UserHandle userHandle = this.user;
            ComponentName componentName = this.componentName;
            StringBuilder sb = new StringBuilder("DefaultsRequest(user=");
            sb.append(userHandle);
            sb.append(", componentName=");
            sb.append(componentName);
            sb.append(", force=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.force, ")");
        }

        public /* synthetic */ DefaultsRequest(UserHandle userHandle, ComponentName componentName, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(userHandle, componentName, (i & 4) != 0 ? false : z);
        }
    }
}
