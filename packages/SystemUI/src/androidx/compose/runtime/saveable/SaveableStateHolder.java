package androidx.compose.runtime.saveable;

import androidx.compose.runtime.Composer;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface SaveableStateHolder {
    void SaveableStateProvider(Object obj, Function2 function2, Composer composer, int i);

    void removeState(Object obj);
}
