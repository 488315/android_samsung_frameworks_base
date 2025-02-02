package com.samsung.android.biometrics.app.setting;

import android.os.SystemClock;
import android.view.animation.PathInterpolator;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class AnimationHelper {
    private final boolean mIsRepeat;
    private final float mLastPos;
    private final ArrayList<Item> mSet;
    private long mTimestamp;

    public AnimationHelper(boolean z) {
        this(z, 1.0f);
    }

    public final void addTrack(Item item) {
        this.mSet.add(item);
    }

    public final float getPos() {
        ArrayList<Item> arrayList = this.mSet;
        Iterator<Item> it = arrayList.iterator();
        long j = 0;
        while (it.hasNext()) {
            j += it.next().length;
        }
        long max = Math.max(0L, SystemClock.elapsedRealtime() - this.mTimestamp);
        float f = this.mLastPos;
        if (max >= j && !this.mIsRepeat) {
            return f;
        }
        long j2 = max - ((max / j) * j);
        Iterator<Item> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Item next = it2.next();
            long j3 = next.length;
            if (j2 <= j3) {
                PathInterpolator pathInterpolator = next.interpolator;
                if (pathInterpolator == null) {
                    return next.defValue;
                }
                float interpolation = pathInterpolator.getInterpolation(j2 / j3);
                return next.isReverse ? 1.0f - interpolation : interpolation;
            }
            j2 -= j3;
        }
        return f;
    }

    public final void start() {
        this.mTimestamp = SystemClock.elapsedRealtime();
    }

    public AnimationHelper(boolean z, float f) {
        this.mSet = new ArrayList<>();
        this.mIsRepeat = z;
        this.mLastPos = f;
    }

    public static class Item {
        public float defValue;
        public PathInterpolator interpolator;
        public boolean isReverse;
        public long length;

        public Item(long j, PathInterpolator pathInterpolator) {
            this.isReverse = false;
            this.defValue = 0.0f;
            this.length = j;
            this.interpolator = pathInterpolator;
        }

        public Item(long j, float f) {
            this.isReverse = false;
            this.length = j;
            this.defValue = f;
        }

        public Item(long j, PathInterpolator pathInterpolator, int i) {
            this.defValue = 0.0f;
            this.length = j;
            this.interpolator = pathInterpolator;
            this.isReverse = true;
        }
    }
}
