package androidx.window.layout;

import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class WindowLayoutInfo {
    public final List displayFeatures;

    public WindowLayoutInfo(List<? extends FoldingFeature> list) {
        this.displayFeatures = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !WindowLayoutInfo.class.equals(obj.getClass())) {
            return false;
        }
        return Intrinsics.areEqual(this.displayFeatures, ((WindowLayoutInfo) obj).displayFeatures);
    }

    public final int hashCode() {
        return this.displayFeatures.hashCode();
    }

    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(this.displayFeatures, ", ", "WindowLayoutInfo{ DisplayFeatures[", "] }", null, 56);
    }
}
