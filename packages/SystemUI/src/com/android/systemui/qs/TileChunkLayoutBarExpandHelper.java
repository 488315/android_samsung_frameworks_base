package com.android.systemui.qs;

import android.animation.ValueAnimator;
import android.view.animation.PathInterpolator;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileChunkLayoutBarExpandHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator expandAnimator;
    public int initialBarHeight;
    public final TileChunkLayoutBar tileChunkLayoutBar;
    public boolean tracking;
    public float velocity;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TileChunkLayoutBarExpandHelper(TileChunkLayoutBar tileChunkLayoutBar) {
        this.tileChunkLayoutBar = tileChunkLayoutBar;
    }

    public final void forceToggleBar() {
        TileChunkLayoutBar tileChunkLayoutBar = this.tileChunkLayoutBar;
        if (tileChunkLayoutBar.mIsExpanded) {
            setHeight(tileChunkLayoutBar.mContainerCollapsedHeight);
        } else {
            setHeight(tileChunkLayoutBar.mContainerExpandedHeight);
        }
    }

    public final void setHeight(int i) {
        ValueAnimator valueAnimator = this.expandAnimator;
        if (valueAnimator != null && !this.tracking) {
            if (valueAnimator == null) {
                valueAnimator = null;
            }
            valueAnimator.cancel();
        }
        TileChunkLayoutBar tileChunkLayoutBar = this.tileChunkLayoutBar;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(tileChunkLayoutBar.mHeight, i);
        ofFloat.setDuration((int) ((Math.abs(i - tileChunkLayoutBar.mHeight) / Math.abs(tileChunkLayoutBar.mContainerExpandedHeight - tileChunkLayoutBar.mContainerCollapsedHeight)) * 300));
        ofFloat.setInterpolator(new PathInterpolator(0.43f, 0.43f, 0.17f, 1.0f));
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.TileChunkLayoutBarExpandHelper$setHeight$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = TileChunkLayoutBarExpandHelper.this;
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                int i2 = TileChunkLayoutBarExpandHelper.$r8$clinit;
                tileChunkLayoutBarExpandHelper.tileChunkLayoutBar.setContainerHeight((int) floatValue);
            }
        });
        ofFloat.start();
        this.expandAnimator = ofFloat;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0023, code lost:
    
        if (r5.velocity > 0.0f) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0027, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0038, code lost:
    
        if ((r6 - r0) > ((r2.mContainerExpandedHeight - r0) / 2.0f)) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003d, code lost:
    
        if (r2.mIsExpanded == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setTracking(float r6, boolean r7) {
        /*
            r5 = this;
            boolean r0 = r5.tracking
            r1 = 0
            if (r0 != r7) goto L6
            return r1
        L6:
            r5.velocity = r6
            com.android.systemui.qs.bar.TileChunkLayoutBar r2 = r5.tileChunkLayoutBar
            if (r7 == 0) goto L11
            int r6 = r2.mHeight
            r5.initialBarHeight = r6
            goto L4f
        L11:
            r3 = 1
            if (r0 == 0) goto L3b
            float r6 = java.lang.Math.abs(r6)
            r0 = 1140457472(0x43fa0000, float:500.0)
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 <= 0) goto L29
            float r6 = r5.velocity
            r0 = 0
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 <= 0) goto L27
        L25:
            r6 = r3
            goto L40
        L27:
            r6 = r1
            goto L40
        L29:
            int r6 = r2.mHeight
            int r0 = r2.mContainerCollapsedHeight
            int r6 = r6 - r0
            float r6 = (float) r6
            int r4 = r2.mContainerExpandedHeight
            int r4 = r4 - r0
            float r0 = (float) r4
            r4 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r4
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 <= 0) goto L27
            goto L25
        L3b:
            boolean r6 = r2.mIsExpanded
            if (r6 != 0) goto L27
            goto L25
        L40:
            if (r6 == 0) goto L45
            int r0 = r2.mContainerExpandedHeight
            goto L47
        L45:
            int r0 = r2.mContainerCollapsedHeight
        L47:
            boolean r2 = r2.mIsExpanded
            if (r2 == r6) goto L4c
            r1 = r3
        L4c:
            r5.setHeight(r0)
        L4f:
            r5.tracking = r7
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.TileChunkLayoutBarExpandHelper.setTracking(float, boolean):boolean");
    }
}
