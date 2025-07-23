package com.facebook.rebound;

import android.os.SystemClock;
import android.view.Choreographer;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.facebook.rebound.Spring;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper extends SpringLooper {
    public final Choreographer mChoreographer;
    public final AnonymousClass1 mFrameCallback = new Choreographer.FrameCallback() { // from class: com.facebook.rebound.AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            double d;
            long j2;
            Iterator it;
            BaseSpringSystem baseSpringSystem;
            double d2;
            Spring.PhysicsState physicsState;
            double d3;
            boolean z;
            boolean z2;
            boolean z3;
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper androidSpringLooperFactory$ChoreographerAndroidSpringLooper = AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this;
            if (!androidSpringLooperFactory$ChoreographerAndroidSpringLooper.mStarted || androidSpringLooperFactory$ChoreographerAndroidSpringLooper.mSpringSystem == null) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper androidSpringLooperFactory$ChoreographerAndroidSpringLooper2 = AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this;
            BaseSpringSystem baseSpringSystem2 = androidSpringLooperFactory$ChoreographerAndroidSpringLooper2.mSpringSystem;
            double d4 = uptimeMillis - androidSpringLooperFactory$ChoreographerAndroidSpringLooper2.mLastTime;
            Iterator it2 = baseSpringSystem2.mListeners.iterator();
            if (it2.hasNext()) {
                throw FragmentManager$$ExternalSyntheticOutline0.m(it2);
            }
            Iterator it3 = ((CopyOnWriteArraySet) baseSpringSystem2.mActiveSprings).iterator();
            while (it3.hasNext()) {
                Spring spring = (Spring) it3.next();
                if (spring.isAtRest() && spring.mWasAtRest) {
                    ((CopyOnWriteArraySet) baseSpringSystem2.mActiveSprings).remove(spring);
                } else {
                    double d5 = d4 / 1000.0d;
                    boolean isAtRest = spring.isAtRest();
                    if (!isAtRest || !spring.mWasAtRest) {
                        if (d5 > 0.064d) {
                            d5 = 0.064d;
                        }
                        spring.mTimeAccumulator += d5;
                        SpringConfig springConfig = spring.mSpringConfig;
                        double d6 = springConfig.tension;
                        double d7 = springConfig.friction;
                        Spring.PhysicsState physicsState2 = spring.mCurrentState;
                        double d8 = physicsState2.position;
                        d = d4;
                        double d9 = physicsState2.velocity;
                        Spring.PhysicsState physicsState3 = spring.mTempState;
                        double d10 = physicsState3.position;
                        j2 = uptimeMillis;
                        double d11 = d8;
                        double d12 = physicsState3.velocity;
                        double d13 = d10;
                        it = it3;
                        baseSpringSystem = baseSpringSystem2;
                        double d14 = d9;
                        while (true) {
                            d2 = spring.mTimeAccumulator;
                            physicsState = spring.mPreviousState;
                            if (d2 < 0.001d) {
                                break;
                            }
                            double d15 = d2 - 0.001d;
                            spring.mTimeAccumulator = d15;
                            if (d15 < 0.001d) {
                                physicsState.position = d11;
                                physicsState.velocity = d14;
                            }
                            double d16 = spring.mEndValue;
                            double d17 = ((d16 - d13) * d6) - (d7 * d14);
                            double d18 = (d17 * 0.001d * 0.5d) + d14;
                            double d19 = ((d16 - (((d14 * 0.001d) * 0.5d) + d11)) * d6) - (d7 * d18);
                            double d20 = (d19 * 0.001d * 0.5d) + d14;
                            double d21 = ((d16 - (((d18 * 0.001d) * 0.5d) + d11)) * d6) - (d7 * d20);
                            double d22 = (d20 * 0.001d) + d11;
                            double d23 = (d21 * 0.001d) + d14;
                            d11 = ((((d18 + d20) * 2.0d) + d14 + d23) * 0.16666666666666666d * 0.001d) + d11;
                            d14 += (((d19 + d21) * 2.0d) + d17 + (((d16 - d22) * d6) - (d7 * d23))) * 0.16666666666666666d * 0.001d;
                            d13 = d22;
                            d12 = d23;
                        }
                        physicsState3.position = d13;
                        physicsState3.velocity = d12;
                        physicsState2.position = d11;
                        physicsState2.velocity = d14;
                        if (d2 > 0.0d) {
                            double d24 = d2 / 0.001d;
                            d3 = 0.0d;
                            double d25 = 1.0d - d24;
                            physicsState2.position = (physicsState.position * d25) + (d11 * d24);
                            physicsState2.velocity = (physicsState.velocity * d25) + (d14 * d24);
                        } else {
                            d3 = 0.0d;
                        }
                        if (spring.isAtRest()) {
                            if (d6 > d3) {
                                physicsState2.position = spring.mEndValue;
                            } else {
                                spring.mEndValue = physicsState2.position;
                            }
                            spring.setVelocity(d3);
                            z = true;
                        } else {
                            z = isAtRest;
                        }
                        if (spring.mWasAtRest) {
                            spring.mWasAtRest = false;
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z) {
                            spring.mWasAtRest = true;
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        Iterator it4 = spring.mListeners.iterator();
                        while (it4.hasNext()) {
                            SpringListener springListener = (SpringListener) it4.next();
                            if (z2) {
                                springListener.onSpringActivate(spring);
                            }
                            springListener.onSpringUpdate(spring);
                            if (z3) {
                                springListener.onSpringAtRest(spring);
                            }
                        }
                        baseSpringSystem2 = baseSpringSystem;
                        d4 = d;
                        uptimeMillis = j2;
                        it3 = it;
                    }
                }
                j2 = uptimeMillis;
                it = it3;
                baseSpringSystem = baseSpringSystem2;
                d = d4;
                baseSpringSystem2 = baseSpringSystem;
                d4 = d;
                uptimeMillis = j2;
                it3 = it;
            }
            long j3 = uptimeMillis;
            BaseSpringSystem baseSpringSystem3 = baseSpringSystem2;
            if (((CopyOnWriteArraySet) baseSpringSystem3.mActiveSprings).isEmpty()) {
                baseSpringSystem3.mIdle = true;
            }
            Iterator it5 = baseSpringSystem3.mListeners.iterator();
            if (it5.hasNext()) {
                throw FragmentManager$$ExternalSyntheticOutline0.m(it5);
            }
            if (baseSpringSystem3.mIdle) {
                baseSpringSystem3.mSpringLooper.stop();
            }
            AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper androidSpringLooperFactory$ChoreographerAndroidSpringLooper3 = AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper.this;
            androidSpringLooperFactory$ChoreographerAndroidSpringLooper3.mLastTime = j3;
            androidSpringLooperFactory$ChoreographerAndroidSpringLooper3.mChoreographer.postFrameCallback(androidSpringLooperFactory$ChoreographerAndroidSpringLooper3.mFrameCallback);
        }
    };
    public long mLastTime;
    public boolean mStarted;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.facebook.rebound.AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper$1] */
    public AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper(Choreographer choreographer) {
        this.mChoreographer = choreographer;
    }

    @Override // com.facebook.rebound.SpringLooper
    public final void start() {
        if (this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mLastTime = SystemClock.uptimeMillis();
        Choreographer choreographer = this.mChoreographer;
        AnonymousClass1 anonymousClass1 = this.mFrameCallback;
        choreographer.removeFrameCallback(anonymousClass1);
        this.mChoreographer.postFrameCallback(anonymousClass1);
    }

    @Override // com.facebook.rebound.SpringLooper
    public final void stop() {
        this.mStarted = false;
        this.mChoreographer.removeFrameCallback(this.mFrameCallback);
    }
}
