package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomTileDefaultsRepositoryImpl implements CustomTileDefaultsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ReadonlySharedFlow defaults;
    public final SharedFlowImpl defaultsRequests;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        CustomTileDefaultsRepositoryImpl$defaults$1 customTileDefaultsRepositoryImpl$defaults$1 = new Function2() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                CustomTileDefaultsRepositoryImpl.DefaultsRequest defaultsRequest = (CustomTileDefaultsRepositoryImpl.DefaultsRequest) obj2;
                return Boolean.valueOf(defaultsRequest.force ? false : ((CustomTileDefaultsRepositoryImpl.DefaultsRequest) obj).equals(defaultsRequest));
            }
        };
        Function1 function1 = FlowKt__DistinctKt.defaultKeySelector;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, customTileDefaultsRepositoryImpl$defaults$1);
        final DistinctFlowImpl distinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(MutableSharedFlow$default, function1, customTileDefaultsRepositoryImpl$defaults$1);
        this.defaults = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CustomTileDefaultsRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:19:0x007c A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
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
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        r5 = 0
                        if (r2 == 0) goto L3f
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L7d
                    L2b:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L33:
                        java.lang.Object r7 = r0.L$1
                        android.os.UserHandle r7 = (android.os.UserHandle) r7
                        java.lang.Object r8 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L69
                    L3f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsRequest r8 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl.DefaultsRequest) r8
                        android.os.UserHandle r9 = r8.user
                        android.content.ComponentName r8 = r8.componentName
                        kotlinx.coroutines.flow.FlowCollector r2 = r7.$this_unsafeFlow
                        r0.L$0 = r2
                        r0.L$1 = r9
                        r0.label = r4
                        int r4 = com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl.$r8$clinit
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl r7 = r7.this$0
                        r7.getClass()
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$loadDefaults$2 r4 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$loadDefaults$2
                        r4.<init>(r7, r9, r8, r5)
                        kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
                        java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r4, r0)
                        if (r7 != r1) goto L65
                        return r1
                    L65:
                        r8 = r2
                        r6 = r9
                        r9 = r7
                        r7 = r6
                    L69:
                        com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r9 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults) r9
                        com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsResult r2 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$DefaultsResult
                        r2.<init>(r7, r9)
                        r0.L$0 = r5
                        r0.L$1 = r5
                        r0.label = r3
                        java.lang.Object r7 = r8.emit(r2, r0)
                        if (r7 != r1) goto L7d
                        return r1
                    L7d:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.force, ")");
        }

        public /* synthetic */ DefaultsRequest(UserHandle userHandle, ComponentName componentName, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(userHandle, componentName, (i & 4) != 0 ? false : z);
        }
    }
}
