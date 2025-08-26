package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class TwoWayConverterImpl<T, V extends AnimationVector> implements TwoWayConverter<T, V> {
    public final Function1 convertFromVector;
    public final Function1 convertToVector;

    public TwoWayConverterImpl(Function1 function1, Function1 function12) {
        this.convertToVector = function1;
        this.convertFromVector = function12;
    }
}
