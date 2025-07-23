package androidx.lifecycle;

import android.view.View;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewTreeLifecycleOwner {
    public static final LifecycleOwner get(View view) {
        return (LifecycleOwner) SequencesKt___SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(SequencesKt__SequencesKt.generateSequence(view, new Function1() { // from class: androidx.lifecycle.ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Object parent = ((View) obj).getParent();
                if (parent instanceof View) {
                    return (View) parent;
                }
                return null;
            }
        }), new Function1() { // from class: androidx.lifecycle.ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Object tag = ((View) obj).getTag(R.id.view_tree_lifecycle_owner);
                if (tag instanceof LifecycleOwner) {
                    return (LifecycleOwner) tag;
                }
                return null;
            }
        }));
    }

    public static final void set(LifecycleOwner lifecycleOwner, View view) {
        view.setTag(R.id.view_tree_lifecycle_owner, lifecycleOwner);
    }
}
