package androidx.activity;

import android.window.BackEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BackEventCompat {
    public final float progress;
    public final int swipeEdge;
    public final float touchX;
    public final float touchY;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BackEventCompat(float f, float f2, float f3, int i) {
        this.touchX = f;
        this.touchY = f2;
        this.progress = f3;
        this.swipeEdge = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BackEventCompat{touchX=");
        sb.append(this.touchX);
        sb.append(", touchY=");
        sb.append(this.touchY);
        sb.append(", progress=");
        sb.append(this.progress);
        sb.append(", swipeEdge=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.swipeEdge, '}');
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public BackEventCompat(BackEvent backEvent) {
        Api34Impl api34Impl = Api34Impl.INSTANCE;
        api34Impl.getClass();
        float touchX = backEvent.getTouchX();
        api34Impl.getClass();
        float touchY = backEvent.getTouchY();
        api34Impl.getClass();
        float progress = backEvent.getProgress();
        api34Impl.getClass();
        this(touchX, touchY, progress, backEvent.getSwipeEdge());
    }
}
