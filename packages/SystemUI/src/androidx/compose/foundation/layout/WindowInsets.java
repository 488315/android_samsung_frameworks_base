package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface WindowInsets {
    public static final Companion Companion = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
    }

    int getBottom(Density density);

    int getLeft(Density density, LayoutDirection layoutDirection);

    int getRight(Density density, LayoutDirection layoutDirection);

    int getTop(Density density);
}
