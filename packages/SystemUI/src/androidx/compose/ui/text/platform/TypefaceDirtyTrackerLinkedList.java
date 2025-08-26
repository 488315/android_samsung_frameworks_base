package androidx.compose.ui.text.platform;

import androidx.compose.runtime.State;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class TypefaceDirtyTrackerLinkedList {
    public final Object initial;
    public final TypefaceDirtyTrackerLinkedList next;
    public final State resolveResult;

    public TypefaceDirtyTrackerLinkedList(State<? extends Object> state, TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList) {
        this.resolveResult = state;
        this.next = typefaceDirtyTrackerLinkedList;
        this.initial = state.getValue();
    }

    public final boolean isStaleResolvedFont() {
        if (this.resolveResult.getValue() != this.initial) {
            return true;
        }
        TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList = this.next;
        return typefaceDirtyTrackerLinkedList != null && typefaceDirtyTrackerLinkedList.isStaleResolvedFont();
    }

    public /* synthetic */ TypefaceDirtyTrackerLinkedList(State state, TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(state, (i & 2) != 0 ? null : typefaceDirtyTrackerLinkedList);
    }
}
