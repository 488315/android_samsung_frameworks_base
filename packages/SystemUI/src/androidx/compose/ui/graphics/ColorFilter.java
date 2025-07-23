package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ColorFilter {
    public static final Companion Companion = new Companion(null);
    public final android.graphics.ColorFilter nativeColorFilter;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: tint-xETnrds$default, reason: not valid java name */
        public static BlendModeColorFilter m463tintxETnrds$default(Companion companion, long j) {
            BlendMode.Companion.getClass();
            int i = BlendMode.SrcIn;
            companion.getClass();
            return new BlendModeColorFilter(j, i, (DefaultConstructorMarker) null);
        }

        private Companion() {
        }
    }

    public ColorFilter(android.graphics.ColorFilter colorFilter) {
        this.nativeColorFilter = colorFilter;
    }
}
