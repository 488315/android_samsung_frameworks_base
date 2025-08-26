package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.util.VelocityTracker1D;

/* loaded from: classes.dex */
final class MouseWheelVelocityTracker {
    public final VelocityTracker1D xVelocityTracker = new VelocityTracker1D(true);
    public final VelocityTracker1D yVelocityTracker = new VelocityTracker1D(true);
}
