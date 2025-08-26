package com.android.systemui.qs;

import android.animation.ValueAnimator;
import android.view.animation.PathInterpolator;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TileChunkLayoutBarExpandHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator expandAnimator;
    public int initialBarHeight;
    public final TileChunkLayoutBar tileChunkLayoutBar;
    public boolean tracking;
    public float velocity;

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
            valueAnimator.cancel();
        }
        TileChunkLayoutBar tileChunkLayoutBar = this.tileChunkLayoutBar;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(tileChunkLayoutBar.mHeight, i);
        valueAnimatorOfFloat.setDuration((int) ((Math.abs(i - tileChunkLayoutBar.mHeight) / Math.abs(tileChunkLayoutBar.mContainerExpandedHeight - tileChunkLayoutBar.mContainerCollapsedHeight)) * 300));
        valueAnimatorOfFloat.setInterpolator(new PathInterpolator(0.43f, 0.43f, 0.17f, 1.0f));
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.TileChunkLayoutBarExpandHelper$setHeight$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = this.this$0;
                float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                int i2 = TileChunkLayoutBarExpandHelper.$r8$clinit;
                tileChunkLayoutBarExpandHelper.tileChunkLayoutBar.setContainerHeight((int) fFloatValue);
            }
        });
        valueAnimatorOfFloat.start();
        this.expandAnimator = valueAnimatorOfFloat;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setTracking(float f, boolean z) {
        boolean z2;
        boolean z3 = this.tracking;
        if (z3 == z) {
            return false;
        }
        this.velocity = f;
        TileChunkLayoutBar tileChunkLayoutBar = this.tileChunkLayoutBar;
        if (z) {
            this.initialBarHeight = tileChunkLayoutBar.mHeight;
        } else {
            if (z3) {
                if (Math.abs(f) > 500.0f) {
                    z2 = this.velocity > 0.0f;
                } else {
                    int i = tileChunkLayoutBar.mHeight;
                    int i2 = tileChunkLayoutBar.mContainerCollapsedHeight;
                    if (i - i2 > (tileChunkLayoutBar.mContainerExpandedHeight - i2) / 2.0f) {
                    }
                }
            } else if (!tileChunkLayoutBar.mIsExpanded) {
            }
            int i3 = z2 ? tileChunkLayoutBar.mContainerExpandedHeight : tileChunkLayoutBar.mContainerCollapsedHeight;
            z = tileChunkLayoutBar.mIsExpanded != z2;
            setHeight(i3);
        }
        this.tracking = z;
        return z;
    }
}
