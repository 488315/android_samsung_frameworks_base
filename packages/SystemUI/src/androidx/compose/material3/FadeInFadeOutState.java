package androidx.compose.material3;

import androidx.compose.runtime.RecomposeScopeImpl;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class FadeInFadeOutState<T> {
    public Object current = new Object();
    public final List items = new ArrayList();
    public RecomposeScopeImpl scope;
}
