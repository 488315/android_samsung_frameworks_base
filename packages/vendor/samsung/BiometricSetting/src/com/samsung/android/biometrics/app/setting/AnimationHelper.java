package com.samsung.android.biometrics.app.setting;

import android.os.SystemClock;
import android.view.animation.PathInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public final class AnimationHelper {
    public final boolean mIsRepeat;
    public final float mLastPos;
    public final ArrayList mSet;
    public long mTimestamp;

    public AnimationHelper(boolean z) {
        this(z, 1.0f);
    }

    public final void addTrack(Item item) {
        this.mSet.add(item);
    }

    public final float getPos() {
        Iterator it = this.mSet.iterator();
        long j = 0;
        while (it.hasNext()) {
            j += ((Item) it.next()).length;
        }
        float f = this.mLastPos;
        if (j == 0) {
            return f;
        }
        long max = Math.max(0L, SystemClock.elapsedRealtime() - this.mTimestamp);
        if (max >= j && !this.mIsRepeat) {
            return f;
        }
        long j2 = max - ((max / j) * j);
        Iterator it2 = this.mSet.iterator();
        while (it2.hasNext()) {
            Item item = (Item) it2.next();
            long j3 = item.length;
            if (j2 <= j3) {
                PathInterpolator pathInterpolator = item.interpolator;
                if (pathInterpolator == null) {
                    return item.defValue;
                }
                float interpolation = pathInterpolator.getInterpolation(j2 / j3);
                return item.isReverse ? 1.0f - interpolation : interpolation;
            }
            j2 -= j3;
        }
        return f;
    }

    public final void start() {
        this.mTimestamp = SystemClock.elapsedRealtime();
    }

    public AnimationHelper(boolean z, float f) {
        this.mSet = new ArrayList();
        this.mIsRepeat = z;
        this.mLastPos = f;
    }

    public final class Item {
        public final float defValue;
        public final PathInterpolator interpolator;
        public final boolean isReverse;
        public final long length;

        public Item(long j, PathInterpolator pathInterpolator) {
            this.isReverse = false;
            this.defValue = RecyclerView.DECELERATION_RATE;
            this.length = j;
            this.interpolator = pathInterpolator;
        }

        public Item(long j, float f) {
            this.isReverse = false;
            this.length = j;
            this.defValue = f;
        }

        public Item(long j, PathInterpolator pathInterpolator, int i) {
            this.defValue = RecyclerView.DECELERATION_RATE;
            this.length = j;
            this.interpolator = pathInterpolator;
            this.isReverse = true;
        }
    }
}
