package com.android.systemui.shade.domain.interactor;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.Display;
import android.window.WindowContext;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.ShadeDisplayChangeLatencyTracker;
import com.android.systemui.shade.ShadeTraceLogger;
import com.android.systemui.shade.data.repository.MutableShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.display.ShadeExpansionIntent;
import com.android.systemui.shade.display.StatusBarTouchShadeDisplayPolicy;
import com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor;
import com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.row.NotificationRebindingTracker;
import com.android.systemui.statusbar.notification.stack.NotificationStackRebindingHider;
import com.android.systemui.statusbar.notification.stack.NotificationStackRebindingHiderImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationForwarder;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class ShadeDisplaysInteractor implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public static final long TIMEOUT;
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final CoroutineScope bgScope;
    public final ConfigurationForwarder configForwarder;
    public final ConfigurationRepository configurationRepository;
    public final StateFlowImpl displayId;
    public final LogBuffer logBuffer;
    public final CoroutineContext mainThreadContext;
    public final NotificationRebindingTracker notificationRebindingTracker;
    public final NotificationStackRebindingHider notificationStackRebindingHider;
    public final WindowContext shadeContext;
    public final ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker;
    public final ShadeExpandedStateInteractor shadeExpandedInteractor;
    public final ShadeExpansionIntent shadeExpansionIntent;
    public final MutableShadeDisplaysRepository shadePositionRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C04801 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ ShadeDisplaysInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04801(ShadeDisplaysInteractor shadeDisplaysInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeDisplaysInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04801 c04801 = new C04801(this.this$0, continuation);
                c04801.I$0 = ((Number) obj).intValue();
                return c04801;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04801) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    int i2 = this.I$0;
                    ShadeDisplaysInteractor shadeDisplaysInteractor = this.this$0;
                    this.label = 1;
                    if (ShadeDisplaysInteractor.access$moveShadeWindowTo(shadeDisplaysInteractor, i2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeDisplaysInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShadeDisplaysInteractor shadeDisplaysInteractor = ShadeDisplaysInteractor.this;
                ReadonlyStateFlow readonlyStateFlow = ((ShadeDisplaysRepositoryImpl) shadeDisplaysInteractor.shadePositionRepository).pendingDisplayId;
                C04801 c04801 = new C04801(shadeDisplaysInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, c04801, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1, reason: invalid class name and case insensitive filesystem */
    final class C10421 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C10421(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            ShadeDisplaysInteractor shadeDisplaysInteractor = ShadeDisplaysInteractor.this;
            Companion companion = ShadeDisplaysInteractor.Companion;
            return shadeDisplaysInteractor.waitForNotificationsRebinding(this);
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        TIMEOUT = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    public ShadeDisplaysInteractor(MutableShadeDisplaysRepository mutableShadeDisplaysRepository, WindowContext windowContext, ConfigurationRepository configurationRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, ShadeExpandedStateInteractor shadeExpandedStateInteractor, ShadeExpansionIntent shadeExpansionIntent, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationRebindingTracker notificationRebindingTracker, NotificationStackRebindingHider notificationStackRebindingHider, ConfigurationForwarder configurationForwarder, LogBuffer logBuffer) {
        this.shadePositionRepository = mutableShadeDisplaysRepository;
        this.shadeContext = windowContext;
        this.configurationRepository = configurationRepository;
        this.bgScope = coroutineScope;
        this.mainThreadContext = coroutineContext;
        this.shadeDisplayChangeLatencyTracker = shadeDisplayChangeLatencyTracker;
        this.shadeExpandedInteractor = shadeExpandedStateInteractor;
        this.shadeExpansionIntent = shadeExpansionIntent;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.notificationRebindingTracker = notificationRebindingTracker;
        this.notificationStackRebindingHider = notificationStackRebindingHider;
        this.configForwarder = configurationForwarder;
        this.logBuffer = logBuffer;
        this.displayId = ((ShadeDisplaysRepositoryImpl) mutableShadeDisplaysRepository).displayId;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0102, code lost:
    
        if (r10.waitForNotificationsRebinding(r0) != r1) goto L51;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r12v15, types: [kotlin.jvm.functions.Function0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$collapseAndExpandShadeIfNeeded(ShadeDisplaysInteractor shadeDisplaysInteractor, int i, ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0 shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0, ContinuationImpl continuationImpl) throws Throwable {
        ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1 shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1;
        ShadeExpandedStateInteractor.ShadeElement shadeElement;
        ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0 shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda02;
        int i2;
        ShadeDisplaysInteractor shadeDisplaysInteractor2;
        int i3;
        shadeDisplaysInteractor.getClass();
        if (continuationImpl instanceof ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1) {
            shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1 = (ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1) continuationImpl;
            int i4 = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = i4 - Integer.MIN_VALUE;
            } else {
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1 = new ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1(shadeDisplaysInteractor, continuationImpl);
            }
        }
        Object obj = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label;
        if (i5 == 0) {
            ResultKt.throwOnFailure(obj);
            shadeElement = (ShadeExpandedStateInteractor.ShadeElement) ((ShadeExpandedStateInteractorImpl) shadeDisplaysInteractor.shadeExpandedInteractor).currentlyExpandedElement.getValue();
            shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda02 = shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0;
            if (shadeElement != null) {
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0 = shadeDisplaysInteractor;
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$1 = shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0;
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$2 = shadeElement;
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$0 = i;
                shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = 1;
                shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda02 = shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0;
                if (shadeElement.collapse() != obj2) {
                }
                return obj2;
            }
            return Unit.INSTANCE;
        }
        if (i5 == 1) {
            i = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$0;
            ShadeExpandedStateInteractor.ShadeElement shadeElement2 = (ShadeExpandedStateInteractor.ShadeElement) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$2;
            ?? r12 = (Function0) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$1;
            ShadeDisplaysInteractor shadeDisplaysInteractor3 = (ShadeDisplaysInteractor) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0;
            ResultKt.throwOnFailure(obj);
            shadeElement = shadeElement2;
            shadeDisplaysInteractor = shadeDisplaysInteractor3;
            shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda02 = r12;
        } else {
            if (i5 == 2) {
                i3 = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$1;
                i = shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$0;
                shadeDisplaysInteractor2 = (ShadeDisplaysInteractor) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0;
                ResultKt.throwOnFailure(obj);
                i2 = i3;
                shadeDisplaysInteractor = shadeDisplaysInteractor2;
                if (i2 != 0) {
                    if (shadeDisplaysInteractor.activeNotificationsInteractor.getAreAnyNotificationsPresentValue()) {
                        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0 = shadeDisplaysInteractor;
                        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$1 = null;
                        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$2 = null;
                        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = 3;
                        Object objWithContext = BuildersKt.withContext(shadeDisplaysInteractor.bgScope.getCoroutineContext(), new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2(i, shadeDisplaysInteractor, null), shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1);
                        if (objWithContext != obj2) {
                            objWithContext = Unit.INSTANCE;
                        }
                        if (objWithContext != obj2) {
                            shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0 = shadeDisplaysInteractor;
                            shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = 4;
                        }
                        return obj2;
                    }
                    ((NotificationStackRebindingHiderImpl) shadeDisplaysInteractor.notificationStackRebindingHider).setVisible(true, true);
                }
                return Unit.INSTANCE;
            }
            if (i5 != 3) {
                if (i5 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                shadeDisplaysInteractor = (ShadeDisplaysInteractor) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0;
                ResultKt.throwOnFailure(obj);
                ((NotificationStackRebindingHiderImpl) shadeDisplaysInteractor.notificationStackRebindingHider).setVisible(true, true);
                return Unit.INSTANCE;
            }
            shadeDisplaysInteractor = (ShadeDisplaysInteractor) shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0;
            ResultKt.throwOnFailure(obj);
            shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0 = shadeDisplaysInteractor;
            shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = 4;
        }
        boolean areAnyNotificationsPresentValue = shadeDisplaysInteractor.activeNotificationsInteractor.getAreAnyNotificationsPresentValue();
        i2 = 0;
        NotificationStackRebindingHider notificationStackRebindingHider = shadeDisplaysInteractor.notificationStackRebindingHider;
        if (areAnyNotificationsPresentValue) {
            ((NotificationStackRebindingHiderImpl) notificationStackRebindingHider).setVisible(false, false);
            i2 = 1;
        } else {
            ((NotificationStackRebindingHiderImpl) notificationStackRebindingHider).setVisible(true, false);
        }
        shadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda02.invoke();
        ShadeExpandedStateInteractor.ShadeElement shadeElement3 = (ShadeExpandedStateInteractor.ShadeElement) ((StatusBarTouchShadeDisplayPolicy) shadeDisplaysInteractor.shadeExpansionIntent).latestIntent.getAndSet(null);
        if (shadeElement3 != null) {
            shadeElement = shadeElement3;
        }
        if (shadeElement == null) {
            if (i2 != 0) {
            }
            return Unit.INSTANCE;
        }
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$0 = shadeDisplaysInteractor;
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$1 = null;
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.L$2 = null;
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$0 = i;
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.I$1 = i2;
        shadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1.label = 2;
        if (shadeElement.expand() != obj2) {
            shadeDisplaysInteractor2 = shadeDisplaysInteractor;
            i3 = i2;
            i2 = i3;
            shadeDisplaysInteractor = shadeDisplaysInteractor2;
            if (i2 != 0) {
            }
            return Unit.INSTANCE;
        }
        return obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$moveShadeWindowTo(ShadeDisplaysInteractor shadeDisplaysInteractor, int i, ContinuationImpl continuationImpl) {
        ShadeDisplaysInteractor$moveShadeWindowTo$1 shadeDisplaysInteractor$moveShadeWindowTo$1;
        Ref$IntRef ref$IntRef;
        final int i2 = 1;
        shadeDisplaysInteractor.getClass();
        if (continuationImpl instanceof ShadeDisplaysInteractor$moveShadeWindowTo$1) {
            shadeDisplaysInteractor$moveShadeWindowTo$1 = (ShadeDisplaysInteractor$moveShadeWindowTo$1) continuationImpl;
            int i3 = shadeDisplaysInteractor$moveShadeWindowTo$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                shadeDisplaysInteractor$moveShadeWindowTo$1.label = i3 - Integer.MIN_VALUE;
            } else {
                shadeDisplaysInteractor$moveShadeWindowTo$1 = new ShadeDisplaysInteractor$moveShadeWindowTo$1(shadeDisplaysInteractor, continuationImpl);
            }
        }
        Object obj = shadeDisplaysInteractor$moveShadeWindowTo$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = shadeDisplaysInteractor$moveShadeWindowTo$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            LogLevel logLevel = LogLevel.DEBUG;
            final int i5 = 0;
            Function1 function1 = new Function1() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    switch (i5) {
                        case 0:
                            ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                            return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move shade window to display with id ");
                        case 1:
                            ShadeDisplaysInteractor.Companion companion2 = ShadeDisplaysInteractor.Companion;
                            return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move the shade to a display (", ") it was already in.");
                        default:
                            ShadeDisplaysInteractor.Companion companion3 = ShadeDisplaysInteractor.Companion;
                            return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Unable to move the shade window from display ", " to ");
                    }
                }
            };
            LogBuffer logBuffer = shadeDisplaysInteractor.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("ShadeDisplaysInteractor", logLevel, function1, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            ShadeTraceLogger shadeTraceLogger = ShadeTraceLogger.INSTANCE;
            if (Trace.isEnabled()) {
                TrackTracer trackTracer = ShadeTraceLogger.t;
                Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "moveShadeWindowTo(displayId=", ")"));
            }
            ref$IntRef = new Ref$IntRef();
            ref$IntRef.element = -1;
            try {
                Display display = shadeDisplaysInteractor.shadeContext.getDisplay();
                if (display == null) {
                    throw new IllegalStateException("Current shade display is null");
                }
                int displayId = display.getDisplayId();
                ref$IntRef.element = displayId;
                if (displayId == i) {
                    LogMessage logMessageObtain2 = logBuffer.obtain("ShadeDisplaysInteractor", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            LogMessage logMessage = (LogMessage) obj2;
                            switch (i2) {
                                case 0:
                                    ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                                    return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move shade window to display with id ");
                                case 1:
                                    ShadeDisplaysInteractor.Companion companion2 = ShadeDisplaysInteractor.Companion;
                                    return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move the shade to a display (", ") it was already in.");
                                default:
                                    ShadeDisplaysInteractor.Companion companion3 = ShadeDisplaysInteractor.Companion;
                                    return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Unable to move the shade window from display ", " to ");
                            }
                        }
                    }, null);
                    ((LogMessageImpl) logMessageObtain2).int1 = ref$IntRef.element;
                    logBuffer.commit(logMessageObtain2);
                    return Unit.INSTANCE;
                }
                CoroutineContext coroutineContext = shadeDisplaysInteractor.mainThreadContext;
                ShadeDisplaysInteractor$moveShadeWindowTo$6 shadeDisplaysInteractor$moveShadeWindowTo$6 = new ShadeDisplaysInteractor$moveShadeWindowTo$6(shadeDisplaysInteractor, i, null);
                shadeDisplaysInteractor$moveShadeWindowTo$1.L$0 = shadeDisplaysInteractor;
                shadeDisplaysInteractor$moveShadeWindowTo$1.L$1 = ref$IntRef;
                shadeDisplaysInteractor$moveShadeWindowTo$1.I$0 = i;
                shadeDisplaysInteractor$moveShadeWindowTo$1.label = 1;
                if (BuildersKt.withContext(coroutineContext, shadeDisplaysInteractor$moveShadeWindowTo$6, shadeDisplaysInteractor$moveShadeWindowTo$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (IllegalStateException e) {
                e = e;
                LogBuffer logBuffer2 = shadeDisplaysInteractor.logBuffer;
                final int i6 = 2;
                LogMessage logMessageObtain3 = logBuffer2.obtain("ShadeDisplaysInteractor", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        LogMessage logMessage = (LogMessage) obj2;
                        switch (i6) {
                            case 0:
                                ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move shade window to display with id ");
                            case 1:
                                ShadeDisplaysInteractor.Companion companion2 = ShadeDisplaysInteractor.Companion;
                                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move the shade to a display (", ") it was already in.");
                            default:
                                ShadeDisplaysInteractor.Companion companion3 = ShadeDisplaysInteractor.Companion;
                                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Unable to move the shade window from display ", " to ");
                        }
                    }
                }, e);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain3;
                logMessageImpl.int1 = ref$IntRef.element;
                logMessageImpl.int2 = i;
                logBuffer2.commit(logMessageObtain3);
                return Unit.INSTANCE;
            }
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = shadeDisplaysInteractor$moveShadeWindowTo$1.I$0;
            Ref$IntRef ref$IntRef2 = (Ref$IntRef) shadeDisplaysInteractor$moveShadeWindowTo$1.L$1;
            ShadeDisplaysInteractor shadeDisplaysInteractor2 = (ShadeDisplaysInteractor) shadeDisplaysInteractor$moveShadeWindowTo$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (IllegalStateException e2) {
                ref$IntRef = ref$IntRef2;
                shadeDisplaysInteractor = shadeDisplaysInteractor2;
                e = e2;
                LogBuffer logBuffer22 = shadeDisplaysInteractor.logBuffer;
                final int i62 = 2;
                LogMessage logMessageObtain32 = logBuffer22.obtain("ShadeDisplaysInteractor", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        LogMessage logMessage = (LogMessage) obj2;
                        switch (i62) {
                            case 0:
                                ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move shade window to display with id ");
                            case 1:
                                ShadeDisplaysInteractor.Companion companion2 = ShadeDisplaysInteractor.Companion;
                                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Trying to move the shade to a display (", ") it was already in.");
                            default:
                                ShadeDisplaysInteractor.Companion companion3 = ShadeDisplaysInteractor.Companion;
                                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Unable to move the shade window from display ", " to ");
                        }
                    }
                }, e);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain32;
                logMessageImpl2.int1 = ref$IntRef.element;
                logMessageImpl2.int2 = i;
                logBuffer22.commit(logMessageObtain32);
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    public final void errorLog(String str) {
        LogBuffer.log$default(this.logBuffer, "ShadeDisplaysInteractor", LogLevel.ERROR, str);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        this.shadeContext.registerComponentCallbacks(new ComponentCallbacks() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$listenForWindowContextConfigChanges$1
            @Override // android.content.ComponentCallbacks
            public final void onConfigurationChanged(Configuration configuration) {
                ((ConfigurationControllerImpl) this.this$0.configForwarder).onConfigurationChanged(configuration);
            }

            @Override // android.content.ComponentCallbacks
            public final void onLowMemory() {
            }
        });
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 6);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitForNotificationsRebinding(ContinuationImpl continuationImpl) throws Throwable {
        C10421 c10421;
        long j;
        int iNextInt;
        String str;
        Throwable th;
        int i;
        long j2;
        if (continuationImpl instanceof C10421) {
            c10421 = (C10421) continuationImpl;
            int i2 = c10421.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c10421.label = i2 - Integer.MIN_VALUE;
            } else {
                c10421 = new C10421(continuationImpl);
            }
        }
        Object obj = c10421.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c10421.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            ShadeTraceLogger.INSTANCE.getClass();
            TrackTracer trackTracer = ShadeTraceLogger.t;
            j = trackTracer.traceTag;
            iNextInt = ThreadLocalRandom.current().nextInt();
            String str2 = trackTracer.trackName;
            Trace.asyncTraceForTrackBegin(j, str2, "waiting for notifications rebinding to finish", iNextInt);
            try {
                long j3 = TIMEOUT;
                ShadeDisplaysInteractor$waitForNotificationsRebinding$2$1 shadeDisplaysInteractor$waitForNotificationsRebinding$2$1 = new ShadeDisplaysInteractor$waitForNotificationsRebinding$2$1(this, null);
                c10421.L$0 = this;
                c10421.L$1 = str2;
                c10421.J$0 = j;
                c10421.I$0 = iNextInt;
                c10421.label = 1;
                Object objWithTimeoutOrNull = TimeoutKt.withTimeoutOrNull(DelayKt.m3469toDelayMillisLRDsOJo(j3), shadeDisplaysInteractor$waitForNotificationsRebinding$2$1, c10421);
                if (objWithTimeoutOrNull == coroutineSingletons) {
                    return coroutineSingletons;
                }
                str = str2;
                obj = objWithTimeoutOrNull;
            } catch (Throwable th2) {
                str = str2;
                th = th2;
                i = iNextInt;
                j2 = j;
                Trace.asyncTraceForTrackEnd(j2, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c10421.I$0;
            j2 = c10421.J$0;
            str = (String) c10421.L$1;
            ShadeDisplaysInteractor shadeDisplaysInteractor = (ShadeDisplaysInteractor) c10421.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                j = j2;
                iNextInt = i;
                this = shadeDisplaysInteractor;
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j2, str, i);
                throw th;
            }
        }
        try {
            if (((Integer) obj) == null) {
                this.errorLog("Timed out while waiting for inflations to finish");
                Unit unit = Unit.INSTANCE;
            }
            Trace.asyncTraceForTrackEnd(j, str, iNextInt);
            return Unit.INSTANCE;
        } catch (Throwable th4) {
            th = th4;
            i = iNextInt;
            j2 = j;
            Trace.asyncTraceForTrackEnd(j2, str, i);
            throw th;
        }
    }
}
