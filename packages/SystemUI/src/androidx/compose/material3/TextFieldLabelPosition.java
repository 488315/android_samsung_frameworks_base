package androidx.compose.material3;

import androidx.compose.ui.Alignment;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldLabelPosition {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Above(androidx.compose.ui.Alignment.Horizontal r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
            /*
                r0 = this;
                r2 = r2 & 1
                if (r2 == 0) goto Lb
                androidx.compose.ui.Alignment$Companion r1 = androidx.compose.ui.Alignment.Companion
                r1.getClass()
                androidx.compose.ui.BiasAlignment$Horizontal r1 = androidx.compose.ui.Alignment.Companion.Start
            Lb:
                r0.<init>(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextFieldLabelPosition.Above.<init>(androidx.compose.ui.Alignment$Horizontal, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Attached(boolean r1, androidx.compose.ui.Alignment.Horizontal r2, androidx.compose.ui.Alignment.Horizontal r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
            /*
                r0 = this;
                r5 = r4 & 1
                if (r5 == 0) goto L5
                r1 = 0
            L5:
                r5 = r4 & 2
                if (r5 == 0) goto L10
                androidx.compose.ui.Alignment$Companion r2 = androidx.compose.ui.Alignment.Companion
                r2.getClass()
                androidx.compose.ui.BiasAlignment$Horizontal r2 = androidx.compose.ui.Alignment.Companion.Start
            L10:
                r4 = r4 & 4
                if (r4 == 0) goto L1b
                androidx.compose.ui.Alignment$Companion r3 = androidx.compose.ui.Alignment.Companion
                r3.getClass()
                androidx.compose.ui.BiasAlignment$Horizontal r3 = androidx.compose.ui.Alignment.Companion.Start
            L1b:
                r0.<init>(r1, r2, r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextFieldLabelPosition.Attached.<init>(boolean, androidx.compose.ui.Alignment$Horizontal, androidx.compose.ui.Alignment$Horizontal, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
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
