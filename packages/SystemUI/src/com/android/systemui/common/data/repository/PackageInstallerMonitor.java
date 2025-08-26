package com.android.systemui.common.data.repository;

import android.content.pm.PackageInstaller;
import android.os.Handler;
import android.text.TextUtils;
import com.android.systemui.common.shared.model.PackageInstallSession;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PackageInstallerMonitor extends PackageInstaller.SessionCallback {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _installSessions;
    public final Handler bgHandler;
    public final ReadonlyStateFlow installSessionsForPrimaryUser;
    public final Logger logger;
    public final PackageInstaller packageInstaller;
    public final Map sessions = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public PackageInstallerMonitor(Handler handler, CoroutineScope coroutineScope, LogBuffer logBuffer, PackageInstaller packageInstaller) {
        this.bgHandler = handler;
        this.packageInstaller = packageInstaller;
        this.logger = new Logger(logBuffer, "PackageInstallerMonitor");
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        final StateFlow subscriptionCount = stateFlowImplMutableStateFlow.getSubscriptionCount();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1

            /* renamed from: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).intValue() > 0);
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
                Object objCollect = subscriptionCount.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new PackageInstallerMonitor$_installSessions$1$2(null)), new PackageInstallerMonitor$_installSessions$1$3(this, null)), coroutineScope);
        this._installSessions = stateFlowImplMutableStateFlow;
        this.installSessionsForPrimaryUser = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onBadgingChanged(int i) {
        Logger logger = this.logger;
        PackageInstallerMonitor$$ExternalSyntheticLambda0 packageInstallerMonitor$$ExternalSyntheticLambda0 = new PackageInstallerMonitor$$ExternalSyntheticLambda0(0);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$$ExternalSyntheticLambda0, null);
        logMessageObtain.setInt1(i);
        logger.getBuffer().commit(logMessageObtain);
        updateSession(i);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onCreated(int i) {
        Logger logger = this.logger;
        PackageInstallerMonitor$$ExternalSyntheticLambda0 packageInstallerMonitor$$ExternalSyntheticLambda0 = new PackageInstallerMonitor$$ExternalSyntheticLambda0(2);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$$ExternalSyntheticLambda0, null);
        logMessageObtain.setInt1(i);
        logger.getBuffer().commit(logMessageObtain);
        updateSession(i);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onFinished(int i, boolean z) {
        Logger logger = this.logger;
        PackageInstallerMonitor$$ExternalSyntheticLambda0 packageInstallerMonitor$$ExternalSyntheticLambda0 = new PackageInstallerMonitor$$ExternalSyntheticLambda0(1);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$$ExternalSyntheticLambda0, null);
        logMessageObtain.setInt1(i);
        logger.getBuffer().commit(logMessageObtain);
        synchronized (this.sessions) {
            this.sessions.remove(Integer.valueOf(i));
            updateInstallerSessionsFlow();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void updateInstallerSessionsFlow() {
        this._installSessions.setValue(CollectionsKt___CollectionsKt.toList(((LinkedHashMap) this.sessions).values()));
    }

    public final void updateSession(int i) {
        PackageInstaller.SessionInfo sessionInfo = this.packageInstaller.getSessionInfo(i);
        synchronized (this.sessions) {
            try {
                if (sessionInfo == null) {
                } else {
                    Companion.getClass();
                    PackageInstallSession packageInstallSession = TextUtils.isEmpty(sessionInfo.appPackageName) ? null : new PackageInstallSession(sessionInfo.sessionId, sessionInfo.appPackageName, sessionInfo.getAppIcon(), sessionInfo.getUser());
                    if (packageInstallSession != null) {
                        this.sessions.put(Integer.valueOf(i), packageInstallSession);
                    }
                }
                updateInstallerSessionsFlow();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onActiveChanged(int i, boolean z) {
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onProgressChanged(int i, float f) {
    }
}
