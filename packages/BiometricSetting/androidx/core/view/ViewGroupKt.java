package androidx.core.view;

import android.view.ViewGroup;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewGroup.kt */
/* loaded from: classes.dex */
public final class ViewGroupKt {
    public static final ViewGroupKt$children$1 getChildren(ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return new ViewGroupKt$children$1(viewGroup);
    }
}
