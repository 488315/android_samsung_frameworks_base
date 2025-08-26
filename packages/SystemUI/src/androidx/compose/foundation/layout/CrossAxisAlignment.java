package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class CrossAxisAlignment {
    public static final Companion Companion = new Companion(null);

    final class CenterCrossAxisAlignment extends CrossAxisAlignment {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new CenterCrossAxisAlignment();
        }

        private CenterCrossAxisAlignment() {
            super(null);
        }

        @Override // androidx.compose.foundation.layout.CrossAxisAlignment
        public final int align$foundation_layout(int i, LayoutDirection layoutDirection) {
            return i / 2;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class EndCrossAxisAlignment extends CrossAxisAlignment {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new EndCrossAxisAlignment();
        }

        private EndCrossAxisAlignment() {
            super(null);
        }

        @Override // androidx.compose.foundation.layout.CrossAxisAlignment
        public final int align$foundation_layout(int i, LayoutDirection layoutDirection) {
            if (layoutDirection == LayoutDirection.Ltr) {
                return i;
            }
            return 0;
        }
    }

    final class HorizontalCrossAxisAlignment extends CrossAxisAlignment {
        public final Alignment.Horizontal horizontal;

        public HorizontalCrossAxisAlignment(Alignment.Horizontal horizontal) {
            super(null);
            this.horizontal = horizontal;
        }

        @Override // androidx.compose.foundation.layout.CrossAxisAlignment
        public final int align$foundation_layout(int i, LayoutDirection layoutDirection) {
            return this.horizontal.align(0, i, layoutDirection);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof HorizontalCrossAxisAlignment) && Intrinsics.areEqual(this.horizontal, ((HorizontalCrossAxisAlignment) obj).horizontal);
        }

        public final int hashCode() {
            return this.horizontal.hashCode();
        }

        public final String toString() {
            return "HorizontalCrossAxisAlignment(horizontal=" + this.horizontal + ')';
        }
    }

    final class StartCrossAxisAlignment extends CrossAxisAlignment {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new StartCrossAxisAlignment();
        }

        private StartCrossAxisAlignment() {
            super(null);
        }

        @Override // androidx.compose.foundation.layout.CrossAxisAlignment
        public final int align$foundation_layout(int i, LayoutDirection layoutDirection) {
            if (layoutDirection == LayoutDirection.Ltr) {
                return 0;
            }
            return i;
        }
    }

    final class VerticalCrossAxisAlignment extends CrossAxisAlignment {
        public final Alignment.Vertical vertical;

        public VerticalCrossAxisAlignment(Alignment.Vertical vertical) {
            super(null);
            this.vertical = vertical;
        }

        @Override // androidx.compose.foundation.layout.CrossAxisAlignment
        public final int align$foundation_layout(int i, LayoutDirection layoutDirection) {
            return ((BiasAlignment.Vertical) this.vertical).align(0, i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof VerticalCrossAxisAlignment) && Intrinsics.areEqual(this.vertical, ((VerticalCrossAxisAlignment) obj).vertical);
        }

        public final int hashCode() {
            return this.vertical.hashCode();
        }

        public final String toString() {
            return "VerticalCrossAxisAlignment(vertical=" + this.vertical + ')';
        }
    }

    static {
        int i = CenterCrossAxisAlignment.$r8$clinit;
        int i2 = StartCrossAxisAlignment.$r8$clinit;
        int i3 = EndCrossAxisAlignment.$r8$clinit;
    }

    public /* synthetic */ CrossAxisAlignment(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract int align$foundation_layout(int i, LayoutDirection layoutDirection);

    private CrossAxisAlignment() {
    }
}
