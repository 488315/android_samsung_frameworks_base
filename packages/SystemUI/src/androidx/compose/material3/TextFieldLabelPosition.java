package androidx.compose.material3;

import androidx.compose.ui.Alignment;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class TextFieldLabelPosition {

    public final class Above extends TextFieldLabelPosition {
        public final Alignment.Horizontal alignment;

        public Above() {
            this(null, 1, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Above)) {
                return false;
            }
            return Intrinsics.areEqual(this.alignment, ((Above) obj).alignment);
        }

        public final int hashCode() {
            return this.alignment.hashCode();
        }

        public final String toString() {
            return "Above(alignment=" + this.alignment + ')';
        }

        public Above(Alignment.Horizontal horizontal) {
            super(null);
            this.alignment = horizontal;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Above(Alignment.Horizontal horizontal, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 1) != 0) {
                Alignment.Companion.getClass();
                horizontal = Alignment.Companion.Start;
            }
            this(horizontal);
        }
    }

    public final class Attached extends TextFieldLabelPosition {
        public final boolean alwaysMinimize;
        public final Alignment.Horizontal expandedAlignment;
        public final Alignment.Horizontal minimizedAlignment;

        public Attached() {
            this(false, null, null, 7, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Attached)) {
                return false;
            }
            Attached attached = (Attached) obj;
            return this.alwaysMinimize == attached.alwaysMinimize && Intrinsics.areEqual(this.minimizedAlignment, attached.minimizedAlignment) && Intrinsics.areEqual(this.expandedAlignment, attached.expandedAlignment);
        }

        public final int hashCode() {
            return this.expandedAlignment.hashCode() + ((this.minimizedAlignment.hashCode() + (Boolean.hashCode(this.alwaysMinimize) * 31)) * 31);
        }

        public final String toString() {
            return "Attached(alwaysMinimize=" + this.alwaysMinimize + ", minimizedAlignment=" + this.minimizedAlignment + ", expandedAlignment=" + this.expandedAlignment + ')';
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Attached(boolean z, Alignment.Horizontal horizontal, Alignment.Horizontal horizontal2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            z = (i & 1) != 0 ? false : z;
            if ((i & 2) != 0) {
                Alignment.Companion.getClass();
                horizontal = Alignment.Companion.Start;
            }
            if ((i & 4) != 0) {
                Alignment.Companion.getClass();
                horizontal2 = Alignment.Companion.Start;
            }
            this(z, horizontal, horizontal2);
        }

        public Attached(boolean z, Alignment.Horizontal horizontal, Alignment.Horizontal horizontal2) {
            super(null);
            this.alwaysMinimize = z;
            this.minimizedAlignment = horizontal;
            this.expandedAlignment = horizontal2;
        }
    }

    public /* synthetic */ TextFieldLabelPosition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private TextFieldLabelPosition() {
    }
}
