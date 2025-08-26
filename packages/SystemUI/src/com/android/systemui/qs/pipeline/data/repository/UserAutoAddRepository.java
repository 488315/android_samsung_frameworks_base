package com.android.systemui.qs.pipeline.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class UserAutoAddRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _autoAdded;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final SharedFlowImpl changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    public final QSPipelineLogger logger;
    public final SecureSettings secureSettings;
    public final int userId;

    public interface ChangeAction {
        Set apply(Set set);
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        UserAutoAddRepository create(int i);
    }

    public final class MarkTile implements ChangeAction {
        public final TileSpec tileSpec;

        public MarkTile(TileSpec tileSpec) {
            this.tileSpec = tileSpec;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.add(this.tileSpec);
            return mutableSet;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MarkTile) && Intrinsics.areEqual(this.tileSpec, ((MarkTile) obj).tileSpec);
        }

        public final int hashCode() {
            return this.tileSpec.hashCode();
        }

        public final String toString() {
            return "MarkTile(tileSpec=" + this.tileSpec + ")";
        }
    }

    public final class RestoreTiles implements ChangeAction {
        public final RestoreData restoredData;

        public RestoreTiles(RestoreData restoreData) {
            this.restoredData = restoreData;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            return SetsKt___SetsKt.plus(set, (Iterable) this.restoredData.restoredAutoAddedTiles);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RestoreTiles) && Intrinsics.areEqual(this.restoredData, ((RestoreTiles) obj).restoredData);
        }

        public final int hashCode() {
            return this.restoredData.hashCode();
        }

        public final String toString() {
            return "RestoreTiles(restoredData=" + this.restoredData + ")";
        }
    }

    public final class UnmarkTile implements ChangeAction {
        public final TileSpec tileSpec;

        public UnmarkTile(TileSpec tileSpec) {
            this.tileSpec = tileSpec;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.remove(this.tileSpec);
            return mutableSet;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UnmarkTile) && Intrinsics.areEqual(this.tileSpec, ((UnmarkTile) obj).tileSpec);
        }

        public final int hashCode() {
            return this.tileSpec.hashCode();
        }

        public final String toString() {
            return "UnmarkTile(tileSpec=" + this.tileSpec + ")";
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$autoAdded$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return UserAutoAddRepository.this.autoAdded(this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$autoAdded$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = UserAutoAddRepository.this.new AnonymousClass3((Continuation) obj3);
            anonymousClass3.L$0 = (Set) obj;
            anonymousClass3.L$1 = (ChangeAction) obj2;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Set set = (Set) this.L$0;
            ChangeAction changeAction = (ChangeAction) this.L$1;
            Set setApply = changeAction.apply(set);
            UserAutoAddRepository userAutoAddRepository = UserAutoAddRepository.this;
            if (changeAction instanceof RestoreTiles) {
                QSPipelineLogger qSPipelineLogger = userAutoAddRepository.logger;
                qSPipelineLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(10);
                LogBuffer logBuffer = qSPipelineLogger.tileAutoAddLogBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("QSAutoAddableLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = setApply.toString();
                logMessageImpl.int1 = userAutoAddRepository.userId;
                logBuffer.commit(logMessageObtain);
            }
            return setApply;
        }
    }

    public UserAutoAddRepository(int i, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userId = i;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
    }

    public static final Object access$store(UserAutoAddRepository userAutoAddRepository, Set set, Continuation continuation) throws Throwable {
        userAutoAddRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object objWithContext = BuildersKt.withContext(userAutoAddRepository.bgDispatcher, new UserAutoAddRepository$store$2(userAutoAddRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$store$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00d4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object autoAdded(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        UserAutoAddRepository userAutoAddRepository;
        Flow flow;
        UserAutoAddRepository userAutoAddRepository2;
        UserAutoAddRepository userAutoAddRepository3;
        UserAutoAddRepository userAutoAddRepository4;
        StateFlow stateFlow;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objStateIn = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStateIn);
            if (this._autoAdded == null) {
                SharedFlowImpl sharedFlowImpl = this.changeEvents;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = this;
                anonymousClass1.L$2 = sharedFlowImpl;
                anonymousClass1.label = 1;
                Object objWithContext = BuildersKt.withContext(this.bgDispatcher, new UserAutoAddRepository$load$2(this, null), anonymousClass1);
                if (objWithContext != coroutineSingletons) {
                    userAutoAddRepository = this;
                    flow = sharedFlowImpl;
                    objStateIn = objWithContext;
                    userAutoAddRepository2 = userAutoAddRepository;
                }
                return coroutineSingletons;
            }
            stateFlow = this._autoAdded;
            if (stateFlow == null) {
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                userAutoAddRepository3 = (UserAutoAddRepository) anonymousClass1.L$1;
                userAutoAddRepository4 = (UserAutoAddRepository) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objStateIn);
                StateFlow stateFlow2 = (StateFlow) objStateIn;
                userAutoAddRepository4.getClass();
                CoroutineTracingKt.launchTraced$default(userAutoAddRepository4.applicationScope, userAutoAddRepository4.bgDispatcher, null, new UserAutoAddRepository$startFlowCollections$1(stateFlow2, userAutoAddRepository4, null), 5);
                userAutoAddRepository3._autoAdded = stateFlow2;
                this = userAutoAddRepository4;
                stateFlow = this._autoAdded;
                if (stateFlow == null) {
                    return null;
                }
                return stateFlow;
            }
            flow = (Flow) anonymousClass1.L$2;
            userAutoAddRepository2 = (UserAutoAddRepository) anonymousClass1.L$1;
            userAutoAddRepository = (UserAutoAddRepository) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objStateIn);
        }
        QSPipelineLogger qSPipelineLogger = userAutoAddRepository.logger;
        qSPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(14);
        LogBuffer logBuffer = qSPipelineLogger.tileAutoAddLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSAutoAddableLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = ((Set) objStateIn).toString();
        logMessageImpl.int1 = userAutoAddRepository.userId;
        logBuffer.commit(logMessageObtain);
        Flow flowFlowOn = FlowKt.flowOn(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(objStateIn, flow, userAutoAddRepository.new AnonymousClass3(null)), userAutoAddRepository.bgDispatcher);
        anonymousClass1.L$0 = userAutoAddRepository;
        anonymousClass1.L$1 = userAutoAddRepository2;
        anonymousClass1.L$2 = null;
        anonymousClass1.label = 2;
        objStateIn = FlowKt.stateIn(flowFlowOn, userAutoAddRepository.applicationScope, anonymousClass1);
        if (objStateIn != coroutineSingletons) {
            userAutoAddRepository3 = userAutoAddRepository2;
            userAutoAddRepository4 = userAutoAddRepository;
            StateFlow stateFlow22 = (StateFlow) objStateIn;
            userAutoAddRepository4.getClass();
            CoroutineTracingKt.launchTraced$default(userAutoAddRepository4.applicationScope, userAutoAddRepository4.bgDispatcher, null, new UserAutoAddRepository$startFlowCollections$1(stateFlow22, userAutoAddRepository4, null), 5);
            userAutoAddRepository3._autoAdded = stateFlow22;
            this = userAutoAddRepository4;
            stateFlow = this._autoAdded;
            if (stateFlow == null) {
            }
        }
        return coroutineSingletons;
    }
}
