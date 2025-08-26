package androidx.compose.material3;

import androidx.compose.foundation.MutatePriority;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* loaded from: classes.dex */
public interface TooltipState {
    void dismiss();

    boolean isVisible();

    void onDispose();

    Object show(MutatePriority mutatePriority, SuspendLambda suspendLambda);
}
