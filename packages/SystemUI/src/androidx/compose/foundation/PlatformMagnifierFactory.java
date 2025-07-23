package androidx.compose.foundation;

import android.view.View;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PlatformMagnifierFactory {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /* renamed from: create-nHHXs2Y, reason: not valid java name */
    PlatformMagnifier mo46createnHHXs2Y(View view, boolean z, long j, float f, float f2, boolean z2, Density density, float f3);

    boolean getCanUpdateZoom();
}
