package com.android.systemui.common.data.repository;

import android.content.pm.PackageInstaller;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.PackageInstallSession;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PackageInstallerMonitor extends PackageInstaller.SessionCallback {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _installSessions;
    public final Handler bgHandler;
    public final ReadonlyStateFlow installSessionsForPrimaryUser;
    public final Logger logger;
    public final PackageInstaller packageInstaller;
    public final Map sessions = new LinkedHashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PackageInstallerMonitor(Handler handler, CoroutineScope coroutineScope, LogBuffer logBuffer, PackageInstaller packageInstaller) {
        this.bgHandler = handler;
        this.packageInstaller = packageInstaller;
        this.logger = new Logger(logBuffer, "PackageInstallerMonitor");
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        final SubscriptionCountStateFlow subscriptionCount = MutableStateFlow.getSubscriptionCount();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        boolean r0 = r6 instanceof com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        if (r5 <= 0) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.data.repository.PackageInstallerMonitor$_installSessions$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new PackageInstallerMonitor$_installSessions$1$2(null)), new PackageInstallerMonitor$_installSessions$1$3(this, null)), coroutineScope);
        this._installSessions = MutableStateFlow;
        this.installSessionsForPrimaryUser = FlowKt.asStateFlow(MutableStateFlow);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onBadgingChanged(int i) {
        Logger logger = this.logger;
        PackageInstallerMonitor$onBadgingChanged$1 packageInstallerMonitor$onBadgingChanged$1 = new Function1() { // from class: com.android.systemui.common.data.repository.PackageInstallerMonitor$onBadgingChanged$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "session badging changed ");
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$onBadgingChanged$1, null);
        obtain.setInt1(i);
        logger.getBuffer().commit(obtain);
        updateSession(i);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onCreated(int i) {
        Logger logger = this.logger;
        PackageInstallerMonitor$onCreated$1 packageInstallerMonitor$onCreated$1 = new Function1() { // from class: com.android.systemui.common.data.repository.PackageInstallerMonitor$onCreated$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "session created ");
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$onCreated$1, null);
        obtain.setInt1(i);
        logger.getBuffer().commit(obtain);
        updateSession(i);
    }

    @Override // android.content.pm.PackageInstaller.SessionCallback
    public final void onFinished(int i, boolean z) {
        Logger logger = this.logger;
        PackageInstallerMonitor$onFinished$1 packageInstallerMonitor$onFinished$1 = new Function1() { // from class: com.android.systemui.common.data.repository.PackageInstallerMonitor$onFinished$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "session finished ");
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, packageInstallerMonitor$onFinished$1, null);
        obtain.setInt1(i);
        logger.getBuffer().commit(obtain);
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
                    this.sessions.remove(Integer.valueOf(i));
                } else {
                    Integer valueOf = Integer.valueOf(i);
                    Map map = this.sessions;
                    Companion.getClass();
                    map.put(valueOf, new PackageInstallSession(sessionInfo.sessionId, sessionInfo.appPackageName, sessionInfo.getAppIcon(), sessionInfo.getUser()));
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
