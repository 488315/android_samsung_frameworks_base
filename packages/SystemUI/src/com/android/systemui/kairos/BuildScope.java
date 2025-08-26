package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda4;
import com.android.systemui.kairos.internal.BuildScopeImpl$observe$handle$1;
import com.android.systemui.kairos.internal.Init;
import kotlin.Pair;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public interface BuildScope extends KairosScope, StateScope {

    public abstract class DefaultImpls {
        public static IncrementalInit applyLatestSpecForKey$default(BuildScope buildScope, IncrementalInit incrementalInit) {
            BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
            buildScopeImpl.getClass();
            Pair pairApplyLatestSpecForKey = buildScopeImpl.applyLatestSpecForKey(new EventsInit(new Init("patches", new IncrementalKt$$ExternalSyntheticLambda4(incrementalInit, 0))), buildScopeImpl.sampleDeferred(incrementalInit), null);
            return buildScopeImpl.stateScope.foldStateMapIncrementally((Events) pairApplyLatestSpecForKey.component1(), (DeferredValue) pairApplyLatestSpecForKey.component2());
        }

        public static /* synthetic */ BuildScopeImpl$observe$handle$1 observe$default(BuildScope buildScope, Events events, Function2 function2, int i) {
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            if ((i & 2) != 0) {
                function2 = new BuildScope$DefaultImpls$$ExternalSyntheticLambda0(0);
            }
            return ((BuildScopeImpl) buildScope).observe(events, emptyCoroutineContext, function2);
        }

        public static StateInit scanToState(BuildScopeImpl buildScopeImpl, Flow flow, Pair pair, BuildScope$DefaultImpls$$ExternalSyntheticLambda0 buildScope$DefaultImpls$$ExternalSyntheticLambda0) {
            FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(pair, flow, new BuildScope$scanToState$1(buildScope$DefaultImpls$$ExternalSyntheticLambda0));
            buildScopeImpl.getClass();
            return buildScopeImpl.stateScope.holdState(toEvents(buildScopeImpl, flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1), pair);
        }

        public static Events toEvents(BuildScope buildScope, Flow flow) {
            BuildScope$toEvents$1 buildScope$toEvents$1 = new BuildScope$toEvents$1(flow, null);
            BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
            buildScopeImpl.getClass();
            return BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda4(buildScopeImpl, 1), buildScope$toEvents$1);
        }

        public static StateInit toState(BuildScope buildScope, StateFlow stateFlow) {
            Object value = stateFlow.getValue();
            BuildScope$toState$1 buildScope$toState$1 = new BuildScope$toState$1(stateFlow, value, null);
            BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
            buildScopeImpl.getClass();
            return buildScopeImpl.stateScope.holdState(BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda4(buildScopeImpl, 1), buildScope$toState$1), value);
        }
    }
}
