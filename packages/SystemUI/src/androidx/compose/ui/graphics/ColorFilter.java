package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public class ColorFilter {
    public static final Companion Companion = new Companion(null);
    public final android.graphics.ColorFilter nativeColorFilter;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: tint-xETnrds$default, reason: not valid java name */
        public static BlendModeColorFilter m465tintxETnrds$default(Companion companion, long j) {
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
