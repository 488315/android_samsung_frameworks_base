package androidx.compose.material3.windowsizeclass;

import androidx.compose.ui.unit.Dp;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowWidthSizeClass implements Comparable<WindowWidthSizeClass> {
    public static final List AllSizeClassList;
    public static final Set AllSizeClasses;
    public static final Set DefaultSizeClasses;
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Medium = 1;
    public static final int Expanded = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: breakpoint-fhkHA5s, reason: not valid java name */
        public static float m329breakpointfhkHA5s(int i) {
            if (i == WindowWidthSizeClass.Expanded) {
                float f = 840;
                Dp.Companion companion = Dp.Companion;
                return f;
            }
            if (i == WindowWidthSizeClass.Medium) {
                float f2 = VolteConstants.ErrorCode.BUSY_EVERYWHERE;
                Dp.Companion companion2 = Dp.Companion;
                return f2;
            }
            float f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
            return f3;
        }

        private Companion() {
        }
    }

    static {
        int i = 1;
        int i2 = 2;
        int i3 = 0;
        DefaultSizeClasses = ArraysKt___ArraysKt.toSet(new WindowWidthSizeClass[]{new WindowWidthSizeClass(i3), new WindowWidthSizeClass(i), new WindowWidthSizeClass(i2)});
        List asList = Arrays.asList(new WindowWidthSizeClass(i2), new WindowWidthSizeClass(i), new WindowWidthSizeClass(i3));
        AllSizeClassList = asList;
        AllSizeClasses = CollectionsKt___CollectionsKt.toSet(asList);
    }

    private /* synthetic */ WindowWidthSizeClass(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ WindowWidthSizeClass m327boximpl(int i) {
        return new WindowWidthSizeClass(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m328toStringimpl(int i) {
        return "WindowWidthSizeClass.".concat(i == 0 ? "Compact" : i == Medium ? "Medium" : i == Expanded ? "Expanded" : "");
    }

    @Override // java.lang.Comparable
    public final int compareTo(WindowWidthSizeClass windowWidthSizeClass) {
        int i = windowWidthSizeClass.value;
        int i2 = this.value;
        Companion.getClass();
        float m329breakpointfhkHA5s = Companion.m329breakpointfhkHA5s(i2);
        float m329breakpointfhkHA5s2 = Companion.m329breakpointfhkHA5s(i);
        Dp.Companion companion = Dp.Companion;
        return Float.compare(m329breakpointfhkHA5s, m329breakpointfhkHA5s2);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WindowWidthSizeClass) && this.value == ((WindowWidthSizeClass) obj).value;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m328toStringimpl(this.value);
    }
}
