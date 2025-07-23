package androidx.compose.foundation;

import android.content.Context;
import android.widget.EdgeEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EdgeEffectWrapper {
    public EdgeEffect bottomEffect;
    public EdgeEffect bottomEffectNegation;
    public final Context context;
    public final int glowColor;
    public EdgeEffect leftEffect;
    public EdgeEffect leftEffectNegation;
    public EdgeEffect rightEffect;
    public EdgeEffect rightEffectNegation;
    public long size;
    public EdgeEffect topEffect;
    public EdgeEffect topEffectNegation;

    public EdgeEffectWrapper(Context context, int i) {
        this.context = context;
        this.glowColor = i;
        IntSize.Companion.getClass();
        this.size = 0L;
    }

    public static boolean isAnimating(EdgeEffect edgeEffect) {
        if (edgeEffect == null) {
            return false;
        }
        return !edgeEffect.isFinished();
    }

    public static boolean isStretched(EdgeEffect edgeEffect) {
        if (edgeEffect == null) {
            return false;
        }
        EdgeEffectCompat.INSTANCE.getClass();
        return !(EdgeEffectCompat.getDistanceCompat(edgeEffect) == 0.0f);
    }

    public final EdgeEffect createEdgeEffect(Orientation orientation) {
        EdgeEffect edgeEffect;
        EdgeEffectCompat edgeEffectCompat = EdgeEffectCompat.INSTANCE;
        Context context = this.context;
        edgeEffectCompat.getClass();
        Api31Impl.INSTANCE.getClass();
        try {
            edgeEffect = new EdgeEffect(context, null);
        } catch (Throwable unused) {
            edgeEffect = new EdgeEffect(context);
        }
        edgeEffect.setColor(this.glowColor);
        long j = this.size;
        IntSize.Companion.getClass();
        if (!IntSize.m861equalsimpl0(j, 0L)) {
            if (orientation == Orientation.Vertical) {
                long j2 = this.size;
                edgeEffect.setSize((int) (j2 >> 32), (int) (j2 & 4294967295L));
            } else {
                long j3 = this.size;
                edgeEffect.setSize((int) (j3 & 4294967295L), (int) (j3 >> 32));
            }
        }
        return edgeEffect;
    }

    public final EdgeEffect getOrCreateBottomEffect() {
        EdgeEffect edgeEffect = this.bottomEffect;
        if (edgeEffect != null) {
            return edgeEffect;
        }
        EdgeEffect createEdgeEffect = createEdgeEffect(Orientation.Vertical);
        this.bottomEffect = createEdgeEffect;
        return createEdgeEffect;
    }

    public final EdgeEffect getOrCreateLeftEffect() {
        EdgeEffect edgeEffect = this.leftEffect;
        if (edgeEffect != null) {
            return edgeEffect;
        }
        EdgeEffect createEdgeEffect = createEdgeEffect(Orientation.Horizontal);
        this.leftEffect = createEdgeEffect;
        return createEdgeEffect;
    }

    public final EdgeEffect getOrCreateRightEffect() {
        EdgeEffect edgeEffect = this.rightEffect;
        if (edgeEffect != null) {
            return edgeEffect;
        }
        EdgeEffect createEdgeEffect = createEdgeEffect(Orientation.Horizontal);
        this.rightEffect = createEdgeEffect;
        return createEdgeEffect;
    }

    public final EdgeEffect getOrCreateTopEffect() {
        EdgeEffect edgeEffect = this.topEffect;
        if (edgeEffect != null) {
            return edgeEffect;
        }
        EdgeEffect createEdgeEffect = createEdgeEffect(Orientation.Vertical);
        this.topEffect = createEdgeEffect;
        return createEdgeEffect;
    }
}
