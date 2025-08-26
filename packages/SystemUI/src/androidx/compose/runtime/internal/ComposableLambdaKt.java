package androidx.compose.runtime.internal;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScope;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeOwner;
import java.util.ArrayList;
import java.util.List;
import kotlin.Function;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ComposableLambdaKt {
    public static final Object lambdaKey = null;

    public static final int bitsForSlot(int i, int i2) {
        return i << (((i2 % 10) * 3) + 1);
    }

    public static final ComposableLambdaImpl rememberComposableLambda(int i, Function function, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.internal.rememberComposableLambda (ComposableLambda.kt:1366)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = new ComposableLambdaImpl(i, true, function);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) objRememberedValue;
        if (!Intrinsics.areEqual(composableLambdaImpl._block, function)) {
            boolean z = composableLambdaImpl._block == null;
            composableLambdaImpl._block = function;
            if (!z && composableLambdaImpl.tracked) {
                RecomposeScopeImpl recomposeScopeImpl = composableLambdaImpl.scope;
                if (recomposeScopeImpl != null) {
                    RecomposeScopeOwner recomposeScopeOwner = recomposeScopeImpl.owner;
                    if (recomposeScopeOwner != null) {
                        recomposeScopeOwner.invalidate(recomposeScopeImpl, null);
                    }
                    composableLambdaImpl.scope = null;
                }
                List list = composableLambdaImpl.scopes;
                if (list != null) {
                    ArrayList arrayList = (ArrayList) list;
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        RecomposeScopeImpl recomposeScopeImpl2 = (RecomposeScopeImpl) ((RecomposeScope) arrayList.get(i2));
                        RecomposeScopeOwner recomposeScopeOwner2 = recomposeScopeImpl2.owner;
                        if (recomposeScopeOwner2 != null) {
                            recomposeScopeOwner2.invalidate(recomposeScopeImpl2, null);
                        }
                    }
                    arrayList.clear();
                }
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return composableLambdaImpl;
    }

    public static final boolean replacableWith(RecomposeScope recomposeScope, RecomposeScopeImpl recomposeScopeImpl) {
        if (recomposeScope == null) {
            return true;
        }
        if (!(recomposeScope instanceof RecomposeScopeImpl)) {
            return false;
        }
        RecomposeScopeImpl recomposeScopeImpl2 = (RecomposeScopeImpl) recomposeScope;
        return !recomposeScopeImpl2.getValid() || recomposeScope.equals(recomposeScopeImpl) || Intrinsics.areEqual(recomposeScopeImpl2.anchor, recomposeScopeImpl.anchor);
    }
}
