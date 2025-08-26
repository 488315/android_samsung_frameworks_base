package androidx.compose.ui.layout;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public interface BeyondBoundsLayout {

    public interface BeyondBoundsScope {
        boolean getHasMoreContent();
    }

    public final class LayoutDirection {
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final int Before = 1;
        public static final int After = 2;
        public static final int Left = 3;
        public static final int Right = 4;
        public static final int Above = 5;
        public static final int Below = 6;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof LayoutDirection) {
                return this.value == ((LayoutDirection) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            int i = Before;
            int i2 = this.value;
            return i2 == i ? "Before" : i2 == After ? "After" : i2 == Left ? SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT : i2 == Right ? SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT : i2 == Above ? "Above" : i2 == Below ? "Below" : "invalid LayoutDirection";
        }
    }
}
