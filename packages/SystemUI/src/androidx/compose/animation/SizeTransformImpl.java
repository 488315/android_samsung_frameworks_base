package androidx.compose.animation;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SizeTransformImpl implements SizeTransform {
    public final boolean clip;
    public final Function2 sizeAnimationSpec;

    public SizeTransformImpl(boolean z, Function2 function2) {
        this.clip = z;
        this.sizeAnimationSpec = function2;
    }

    public /* synthetic */ SizeTransformImpl(boolean z, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, function2);
    }
}
