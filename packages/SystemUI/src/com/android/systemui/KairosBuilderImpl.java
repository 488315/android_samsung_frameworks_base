package com.android.systemui;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsLoop;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.IncrementalLoop;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateLoop;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.CompletableLazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class KairosBuilderImpl implements KairosBuilder {
    public List _builds = new ArrayList();
    public List _startables = new ArrayList();

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        List list = this._builds;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((KairosActivatable) it.next()).activate(buildScope);
        }
        this._builds = null;
        KairosBuilderImpl$$ExternalSyntheticLambda0 kairosBuilderImpl$$ExternalSyntheticLambda0 = new KairosBuilderImpl$$ExternalSyntheticLambda0(this);
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        buildScopeImpl.getClass();
        buildScopeImpl.deferAction(new BuildScopeImpl$$ExternalSyntheticLambda0(kairosBuilderImpl$$ExternalSyntheticLambda0, buildScopeImpl, 3));
    }

    public final Events buildEvents(final Function1 function1) {
        final EventsLoop eventsLoop = new EventsLoop();
        List list = this._builds;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        ((ArrayList) list).add(new KairosActivatable() { // from class: com.android.systemui.KairosBuilderImpl$buildEvents$1$1
            @Override // com.android.systemui.KairosActivatable
            public final void activate(BuildScope buildScope) {
                eventsLoop.setLoopback((Events) function1.mo781invoke(buildScope));
            }
        });
        return eventsLoop;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Incremental buildIncremental(final Function1 function1) {
        final IncrementalLoop incrementalLoop = new IncrementalLoop(null, 1, 0 == true ? 1 : 0);
        List list = this._builds;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        ((ArrayList) list).add(new KairosActivatable() { // from class: com.android.systemui.KairosBuilderImpl$buildIncremental$1$1
            @Override // com.android.systemui.KairosActivatable
            public final void activate(BuildScope buildScope) {
                Incremental incremental = (Incremental) function1.mo781invoke(buildScope);
                IncrementalLoop incrementalLoop2 = incrementalLoop;
                if (incremental == null) {
                    incrementalLoop2.getClass();
                    return;
                }
                CompletableLazy completableLazy = incrementalLoop2.deferred;
                if (completableLazy.isInitialized()) {
                    throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("IncrementalLoop("), incrementalLoop2.name, ").loopback has already been set.").toString());
                }
                completableLazy.setValue(incremental);
            }
        });
        return incrementalLoop;
    }

    public final State buildState(final Function1 function1) {
        final StateLoop stateLoop = new StateLoop();
        List list = this._builds;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        ((ArrayList) list).add(new KairosActivatable() { // from class: com.android.systemui.KairosBuilderImpl$buildState$1$1
            @Override // com.android.systemui.KairosActivatable
            public final void activate(BuildScope buildScope) {
                State state = (State) function1.mo781invoke(buildScope);
                StateLoop stateLoop2 = stateLoop;
                if (state == null) {
                    stateLoop2.getClass();
                    return;
                }
                CompletableLazy completableLazy = stateLoop2.deferred;
                if (completableLazy.isInitialized()) {
                    throw new IllegalStateException("StateLoop.loopback has already been set.");
                }
                completableLazy.setValue(state);
            }
        });
        return stateLoop;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(final Function1 function1) {
        List list = this._startables;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        ((ArrayList) list).add(new KairosActivatable() { // from class: com.android.systemui.KairosBuilderImpl.onActivated.1
            @Override // com.android.systemui.KairosActivatable
            public final void activate(BuildScope buildScope) {
                function1.mo781invoke(buildScope);
            }
        });
    }
}
