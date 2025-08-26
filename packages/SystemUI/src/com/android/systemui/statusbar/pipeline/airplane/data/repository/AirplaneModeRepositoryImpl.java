package com.android.systemui.statusbar.pipeline.airplane.data.repository;

import android.net.ConnectivityManager;
import android.os.Handler;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class AirplaneModeRepositoryImpl implements AirplaneModeRepository {
    public final CoroutineContext backgroundContext;
    public final Handler bgHandler;
    public final ConnectivityManager connectivityManager;
    public final GlobalSettings globalSettings;
    public final ReadonlyStateFlow isAirplaneMode;

    /* renamed from: com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl$setIsAirplaneMode$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isEnabled;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isEnabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AirplaneModeRepositoryImpl.this.new AnonymousClass2(this.$isEnabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AirplaneModeRepositoryImpl.this.connectivityManager.setAirplaneMode(this.$isEnabled);
            return Unit.INSTANCE;
        }
    }

    public AirplaneModeRepositoryImpl(ConnectivityManager connectivityManager, Handler handler, CoroutineContext coroutineContext, GlobalSettings globalSettings, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.connectivityManager = connectivityManager;
        this.bgHandler = handler;
        this.backgroundContext = coroutineContext;
        this.globalSettings = globalSettings;
        this.isAirplaneMode = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new AirplaneModeRepositoryImpl$isAirplaneMode$1(this, null))), tableLogBuffer, "", "isAirplaneMode", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    public final Object setIsAirplaneMode(boolean z, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundContext, new AnonymousClass2(z, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
