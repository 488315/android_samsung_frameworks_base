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
public final class WindowHeightSizeClass implements Comparable<WindowHeightSizeClass> {
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

        /* renamed from: breakpoint-sr04XMo, reason: not valid java name */
        public static float m326breakpointsr04XMo(int i) {
            if (i == WindowHeightSizeClass.Expanded) {
                float f = 900;
                Dp.Companion companion = Dp.Companion;
                return f;
            }
            if (i == WindowHeightSizeClass.Medium) {
                float f2 = VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE;
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
        DefaultSizeClasses = ArraysKt___ArraysKt.toSet(new WindowHeightSizeClass[]{new WindowHeightSizeClass(i3), new WindowHeightSizeClass(i), new WindowHeightSizeClass(i2)});
        List asList = Arrays.asList(new WindowHeightSizeClass(i2), new WindowHeightSizeClass(i), new WindowHeightSizeClass(i3));
        AllSizeClassList = asList;
        AllSizeClasses = CollectionsKt___CollectionsKt.toSet(asList);
    }

    private /* synthetic */ WindowHeightSizeClass(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ WindowHeightSizeClass m324boximpl(int i) {
        return new WindowHeightSizeClass(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m325toStringimpl(int i) {
        return "WindowHeightSizeClass.".concat(i == 0 ? "Compact" : i == Medium ? "Medium" : i == Expanded ? "Expanded" : "");
    }

    @Override // java.lang.Comparable
    public final int compareTo(WindowHeightSizeClass windowHeightSizeClass) {
        int i = windowHeightSizeClass.value;
        int i2 = this.value;
        Companion.getClass();
        float m326breakpointsr04XMo = Companion.m326breakpointsr04XMo(i2);
        float m326breakpointsr04XMo2 = Companion.m326breakpointsr04XMo(i);
        Dp.Companion companion = Dp.Companion;
        return Float.compare(m326breakpointsr04XMo, m326breakpointsr04XMo2);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WindowHeightSizeClass) && this.value == ((WindowHeightSizeClass) obj).value;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m325toStringimpl(this.value);
    }
}
