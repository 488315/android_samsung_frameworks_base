package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class CommunalAppWidgetHost extends AppWidgetHost {
    public final SharedFlowImpl _appWidgetIdToRemove;
    public final ReadonlySharedFlow appWidgetIdToRemove;
    public final CoroutineScope backgroundScope;
    public final Logger logger;
    public final Set observers;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Observer {
    }

    /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHost$deleteAppWidgetId$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $appWidgetId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, Continuation continuation) {
            super(2, continuation);
            this.$appWidgetId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalAppWidgetHost.this.new AnonymousClass1(this.$appWidgetId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetHost communalAppWidgetHost = CommunalAppWidgetHost.this;
            Set set = communalAppWidgetHost.observers;
            int i = this.$appWidgetId;
            synchronized (set) {
                Iterator it = communalAppWidgetHost.observers.iterator();
                while (it.hasNext()) {
                    CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) ((Observer) it.next());
                    communalWidgetHost.appWidgetHost.removeListener(i);
                    StateFlowImpl stateFlowImpl = communalWidgetHost._appWidgetProviders;
                    LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
                    linkedHashMap.remove(Integer.valueOf(i));
                    stateFlowImpl.updateState(null, linkedHashMap);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHost$onAppWidgetRemoved$1, reason: invalid class name and case insensitive filesystem */
    final class C08471 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $appWidgetId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08471(int i, Continuation continuation) {
            super(2, continuation);
            this.$appWidgetId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalAppWidgetHost.this.new C08471(this.$appWidgetId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Logger logger = CommunalAppWidgetHost.this.logger;
                CommunalAppWidgetHost$onAppWidgetRemoved$1$$ExternalSyntheticLambda0 communalAppWidgetHost$onAppWidgetRemoved$1$$ExternalSyntheticLambda0 = new CommunalAppWidgetHost$onAppWidgetRemoved$1$$ExternalSyntheticLambda0();
                int i2 = this.$appWidgetId;
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalAppWidgetHost$onAppWidgetRemoved$1$$ExternalSyntheticLambda0, null);
                logMessageObtain.setInt1(i2);
                logger.getBuffer().commit(logMessageObtain);
                SharedFlowImpl sharedFlowImpl = CommunalAppWidgetHost.this._appWidgetIdToRemove;
                Integer num = new Integer(this.$appWidgetId);
                this.label = 1;
                if (sharedFlowImpl.emit(num, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHost$startListening$1, reason: invalid class name and case insensitive filesystem */
    final class C08481 extends SuspendLambda implements Function2 {
        int label;

        public C08481(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalAppWidgetHost.this.new C08481(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08481) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetHost communalAppWidgetHost = CommunalAppWidgetHost.this;
            synchronized (communalAppWidgetHost.observers) {
                Iterator it = communalAppWidgetHost.observers.iterator();
                while (it.hasNext()) {
                    ((CommunalWidgetHost) ((Observer) it.next())).refreshProviders();
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHost$stopListening$1, reason: invalid class name and case insensitive filesystem */
    final class C08491 extends SuspendLambda implements Function2 {
        int label;

        public C08491(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalAppWidgetHost.this.new C08491(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08491) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetHost communalAppWidgetHost = CommunalAppWidgetHost.this;
            synchronized (communalAppWidgetHost.observers) {
                Iterator it = communalAppWidgetHost.observers.iterator();
                while (it.hasNext()) {
                    CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) ((Observer) it.next());
                    StateFlowImpl stateFlowImpl = communalWidgetHost._appWidgetProviders;
                    Iterator it2 = ((Map) stateFlowImpl.getValue()).keySet().iterator();
                    while (it2.hasNext()) {
                        communalWidgetHost.appWidgetHost.removeListener(((Number) it2.next()).intValue());
                    }
                    stateFlowImpl.setValue(MapsKt__MapsKt.emptyMap());
                }
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public CommunalAppWidgetHost(Context context, CoroutineScope coroutineScope, int i, LogBuffer logBuffer, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        super(context, i);
        this.backgroundScope = coroutineScope;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "CommunalAppWidgetHost");
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._appWidgetIdToRemove = sharedFlowImplMutableSharedFlow$default;
        this.appWidgetIdToRemove = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        this.observers = new LinkedHashSet();
    }

    @Override // android.appwidget.AppWidgetHost
    public final int allocateAppWidgetId() {
        int iAllocateAppWidgetId = super.allocateAppWidgetId();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$allocateAppWidgetId$1$1(this, iAllocateAppWidgetId, null), 7);
        return iAllocateAppWidgetId;
    }

    @Override // android.appwidget.AppWidgetHost
    public final void deleteAppWidgetId(int i) {
        super.deleteAppWidgetId(i);
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new AnonymousClass1(i, null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void onAppWidgetRemoved(int i) {
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new C08471(i, null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void startListening() {
        super.startListening();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new C08481(null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void stopListening() {
        super.stopListening();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new C08491(null), 7);
    }
}
