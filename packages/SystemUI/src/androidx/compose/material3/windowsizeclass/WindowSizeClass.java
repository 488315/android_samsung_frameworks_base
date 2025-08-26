package androidx.compose.material3.windowsizeclass;

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass;
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class WindowSizeClass {
    public static final Companion Companion = new Companion(null);
    public final int heightSizeClass;
    public final int widthSizeClass;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ WindowSizeClass(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && WindowSizeClass.class == obj.getClass()) {
            WindowSizeClass windowSizeClass = (WindowSizeClass) obj;
            WindowWidthSizeClass.Companion companion = WindowWidthSizeClass.Companion;
            if (this.widthSizeClass == windowSizeClass.widthSizeClass) {
                WindowHeightSizeClass.Companion companion2 = WindowHeightSizeClass.Companion;
                if (this.heightSizeClass == windowSizeClass.heightSizeClass) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        WindowWidthSizeClass.Companion companion = WindowWidthSizeClass.Companion;
        int iHashCode = Integer.hashCode(this.widthSizeClass) * 31;
        WindowHeightSizeClass.Companion companion2 = WindowHeightSizeClass.Companion;
        return Integer.hashCode(this.heightSizeClass) + iHashCode;
    }

    public final String toString() {
        return "WindowSizeClass(" + ((Object) WindowWidthSizeClass.m329toStringimpl(this.widthSizeClass)) + ", " + ((Object) WindowHeightSizeClass.m326toStringimpl(this.heightSizeClass)) + ')';
    }

    private WindowSizeClass(int i, int i2) {
        this.widthSizeClass = i;
        this.heightSizeClass = i2;
    }
}
