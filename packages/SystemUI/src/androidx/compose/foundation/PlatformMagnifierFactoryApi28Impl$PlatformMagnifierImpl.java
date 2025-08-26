package androidx.compose.foundation;

import android.widget.Magnifier;
import androidx.compose.ui.unit.IntSize;

/* loaded from: classes.dex */
public class PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl implements PlatformMagnifier {
    public final Magnifier magnifier;

    public PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl(Magnifier magnifier) {
        this.magnifier = magnifier;
    }

    /* renamed from: getSize-YbymL2g, reason: not valid java name */
    public final long m48getSizeYbymL2g() {
        long width = (this.magnifier.getWidth() << 32) | (this.magnifier.getHeight() & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return width;
    }

    @Override // androidx.compose.foundation.PlatformMagnifier
    /* renamed from: update-Wko1d7g */
    public void mo46updateWko1d7g(long j, long j2, float f) {
        this.magnifier.show(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
    }
}
