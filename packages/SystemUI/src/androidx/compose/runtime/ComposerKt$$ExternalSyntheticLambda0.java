package androidx.compose.runtime;

import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final /* synthetic */ class ComposerKt$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        return Intrinsics.compare(((Invalidation) obj).location, ((Invalidation) obj2).location);
    }
}
