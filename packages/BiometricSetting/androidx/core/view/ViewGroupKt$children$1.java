package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewGroup.kt */
/* loaded from: classes.dex */
public final class ViewGroupKt$children$1 {
    final /* synthetic */ ViewGroup $this_children;

    ViewGroupKt$children$1(ViewGroup viewGroup) {
        this.$this_children = viewGroup;
    }

    public final Iterator<View> iterator() {
        ViewGroup viewGroup = this.$this_children;
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return new ViewGroupKt$iterator$1(viewGroup);
    }
}
