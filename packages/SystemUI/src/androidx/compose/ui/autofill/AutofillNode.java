package androidx.compose.ui.autofill;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.geometry.Rect;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AutofillNode {
    public static final Companion Companion;
    public static final Companion lock;
    public final List autofillTypes;
    public final Rect boundingBox;
    public final Function1 onFill;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        lock = companion;
    }

    public AutofillNode(List list, Rect rect, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EmptyList.INSTANCE : list, (i & 2) != 0 ? null : rect, function1);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AutofillNode)) {
            return false;
        }
        AutofillNode autofillNode = (AutofillNode) obj;
        return Intrinsics.areEqual(this.autofillTypes, autofillNode.autofillTypes) && Intrinsics.areEqual(this.boundingBox, autofillNode.boundingBox) && this.onFill == autofillNode.onFill;
    }

    public final int hashCode() {
        int iHashCode = this.autofillTypes.hashCode() * 31;
        Rect rect = this.boundingBox;
        int iHashCode2 = (iHashCode + (rect != null ? rect.hashCode() : 0)) * 31;
        Function1 function1 = this.onFill;
        return iHashCode2 + (function1 != null ? function1.hashCode() : 0);
    }

    public AutofillNode(List<? extends AutofillType> list, Rect rect, Function1 function1) {
        this.autofillTypes = list;
        this.boundingBox = rect;
        this.onFill = function1;
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        Companion.getClass();
        synchronized (lock) {
        }
    }
}
