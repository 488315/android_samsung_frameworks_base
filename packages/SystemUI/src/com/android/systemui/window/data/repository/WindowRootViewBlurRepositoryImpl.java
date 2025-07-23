package com.android.systemui.window.data.repository;

import android.view.CrossWindowBlurListeners;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowRootViewBlurRepositoryImpl implements WindowRootViewBlurRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl blurRequestedByShade = StateFlowKt.MutableStateFlow(0);
    public final Executor executor;
    public final StateFlowImpl isBlurOpaque;
    public final ReadonlyStateFlow isBlurSupported;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public WindowRootViewBlurRepositoryImpl(CrossWindowBlurListeners crossWindowBlurListeners, Executor executor, CoroutineScope coroutineScope) {
        this.executor = executor;
        Boolean bool = Boolean.FALSE;
        this.isBlurOpaque = StateFlowKt.MutableStateFlow(bool);
        this.isBlurSupported = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new WindowRootViewBlurRepositoryImpl$isBlurSupported$1(crossWindowBlurListeners, this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), bool);
    }
}
