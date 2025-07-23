package androidx.compose.ui;

import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Alignment {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final BiasAlignment TopStart = new BiasAlignment(-1.0f, -1.0f);
        public static final BiasAlignment TopCenter = new BiasAlignment(0.0f, -1.0f);
        public static final BiasAlignment TopEnd = new BiasAlignment(1.0f, -1.0f);
        public static final BiasAlignment CenterStart = new BiasAlignment(-1.0f, 0.0f);
        public static final BiasAlignment Center = new BiasAlignment(0.0f, 0.0f);
        public static final BiasAlignment CenterEnd = new BiasAlignment(1.0f, 0.0f);
        public static final BiasAlignment BottomStart = new BiasAlignment(-1.0f, 1.0f);
        public static final BiasAlignment BottomCenter = new BiasAlignment(0.0f, 1.0f);
        public static final BiasAlignment BottomEnd = new BiasAlignment(1.0f, 1.0f);
        public static final BiasAlignment.Vertical Top = new BiasAlignment.Vertical(-1.0f);
        public static final BiasAlignment.Vertical CenterVertically = new BiasAlignment.Vertical(0.0f);
        public static final BiasAlignment.Vertical Bottom = new BiasAlignment.Vertical(1.0f);
        public static final BiasAlignment.Horizontal Start = new BiasAlignment.Horizontal(-1.0f);
        public static final BiasAlignment.Horizontal CenterHorizontally = new BiasAlignment.Horizontal(0.0f);
        public static final BiasAlignment.Horizontal End = new BiasAlignment.Horizontal(1.0f);

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Horizontal {
        int align(int i, int i2, LayoutDirection layoutDirection);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Vertical {
    }

    /* renamed from: align-KFBX0sM, reason: not valid java name */
    long mo352alignKFBX0sM(long j, long j2, LayoutDirection layoutDirection);
}
