package com.android.systemui.statusbar.chips.uievents;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarChipsUiEventLogger {
    public static final Companion Companion = new Companion(null);
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final UiEventLogger logger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public StatusBarChipsUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final Object hydrateUiEventLogging(FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new StatusBarChipsUiEventLogger$hydrateUiEventLogging$2(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
