package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
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

/* loaded from: classes2.dex */
public final class CustomTileDefaultsRepositoryImpl implements CustomTileDefaultsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ReadonlySharedFlow defaults;
    public final SharedFlowImpl defaultsRequests;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this.defaultsRequests = sharedFlowImplMutableSharedFlow$default;
        final DistinctFlowImpl distinctFlowImplDistinctUntilChanged = FlowKt.distinctUntilChanged(sharedFlowImplMutableSharedFlow$default, new CustomTileDefaultsRepositoryImpl$$ExternalSyntheticLambda0());
        this.defaults = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$special$$inlined$map$1

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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x007a, code lost:
                
                    if (r7.emit(r2, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    UserHandle userHandle;
                    FlowCollector flowCollector;
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
                        CustomTileDefaultsRepositoryImpl.DefaultsRequest defaultsRequest = (CustomTileDefaultsRepositoryImpl.DefaultsRequest) obj;
                        UserHandle userHandle2 = defaultsRequest.user;
                        ComponentName componentName = defaultsRequest.componentName;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.L$1 = userHandle2;
                        anonymousClass1.label = 1;
                        int i3 = CustomTileDefaultsRepositoryImpl.$r8$clinit;
                        CustomTileDefaultsRepositoryImpl customTileDefaultsRepositoryImpl = this.this$0;
                        customTileDefaultsRepositoryImpl.getClass();
                        Object objWithContext = BuildersKt.withContext(customTileDefaultsRepositoryImpl.backgroundDispatcher, new CustomTileDefaultsRepositoryImpl$loadDefaults$2(customTileDefaultsRepositoryImpl, userHandle2, componentName, null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            obj2 = objWithContext;
                            userHandle = userHandle2;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    userHandle = (UserHandle) anonymousClass1.L$1;
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    CustomTileDefaultsRepositoryImpl.DefaultsResult defaultsResult = new CustomTileDefaultsRepositoryImpl.DefaultsResult(userHandle, (CustomTileDefaults) obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = distinctFlowImplDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
    }

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
