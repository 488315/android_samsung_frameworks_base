package com.android.systemui.shade.display;

import android.view.MotionEvent;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.Lazy;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class StatusBarTouchShadeDisplayPolicy implements ShadeDisplayPolicy, ShadeExpansionIntent {
    public static final Companion Companion = new Companion(null);
    public static final long EXPANSION_INTENT_EXPIRY;
    public final StateFlow availableDisplayIds;
    public final CoroutineScope backgroundScope;
    public final StateFlowImpl currentDisplayId;
    public final StateFlowImpl displayId;
    public final AtomicReference latestIntent;
    public final Lazy notificationElement;
    public final Lazy qsShadeElement;
    public StandaloneCoroutine removalListener;
    public StandaloneCoroutine timeoutJob;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        EXPANSION_INTENT_EXPIRY = DurationKt.toDuration(2, DurationUnit.SECONDS);
    }

    public StatusBarTouchShadeDisplayPolicy(DisplayRepository displayRepository, CoroutineScope coroutineScope, Lazy lazy, Lazy lazy2) {
        this.backgroundScope = coroutineScope;
        this.qsShadeElement = lazy;
        this.notificationElement = lazy2;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this.currentDisplayId = stateFlowImplMutableStateFlow;
        this.availableDisplayIds = ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplayIds();
        this.latestIntent = new AtomicReference();
        this.displayId = stateFlowImplMutableStateFlow;
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final StateFlow getDisplayId() {
        return this.displayId;
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final String getName() {
        return "status_bar_latest_touch";
    }

    public final void onStatusBarTouched(MotionEvent motionEvent, int i) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        int displayId = motionEvent.getDisplayId();
        boolean zContains = ((Set) this.availableDisplayIds.getValue()).contains(Integer.valueOf(displayId));
        CoroutineScope coroutineScope = this.backgroundScope;
        if (zContains) {
            this.currentDisplayId.updateState(null, Integer.valueOf(displayId));
            if (this.removalListener == null) {
                this.removalListener = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new StatusBarTouchShadeDisplayPolicy$monitorDisplayRemovals$1(this, null), 6);
            }
        } else {
            ClockEventController$$ExternalSyntheticOutline0.m(displayId, "Got touch on unknown display ", "StatusBarTouchDisplayPolicy");
        }
        this.latestIntent.set((ShadeExpandedStateInteractor.ShadeElement) (motionEvent.getX() / ((float) i) < 0.5f ? this.notificationElement : this.qsShadeElement).get());
        StandaloneCoroutine standaloneCoroutine = this.timeoutJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.timeoutJob = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new StatusBarTouchShadeDisplayPolicy$updateExpansionIntent$1(this, null), 6);
    }
}
