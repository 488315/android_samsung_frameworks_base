package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Size;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class Brush {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: radialGradient-P_Vx-Ks$default, reason: not valid java name */
        public static RadialGradient m452radialGradientP_VxKs$default(Companion companion, List list, long j, float f) {
            TileMode.Companion.getClass();
            companion.getClass();
            return new RadialGradient(list, null, j, f, 0, null);
        }

        private Companion() {
        }
    }

    public /* synthetic */ Brush(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* renamed from: applyTo-Pq9zytI, reason: not valid java name */
    public abstract void mo451applyToPq9zytI(float f, long j, Paint paint);

    private Brush() {
        Size.Companion.getClass();
    }
}
