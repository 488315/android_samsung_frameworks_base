package androidx.activity;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackEventCompat {
    public final float progress;
    public final int swipeEdge;
    public final float touchX;
    public final float touchY;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BackEventCompat(android.window.BackEvent r5) {
        /*
            r4 = this;
            androidx.activity.Api34Impl r0 = androidx.activity.Api34Impl.INSTANCE
            r0.getClass()
            float r1 = r5.getTouchX()
            r0.getClass()
            float r2 = r5.getTouchY()
            r0.getClass()
            float r3 = r5.getProgress()
            r0.getClass()
            int r5 = r5.getSwipeEdge()
            r4.<init>(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.activity.BackEventCompat.<init>(android.window.BackEvent):void");
    }
}
