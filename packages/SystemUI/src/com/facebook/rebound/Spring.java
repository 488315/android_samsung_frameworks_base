package com.facebook.rebound;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public class Spring {
    public static int ID;
    public final PhysicsState mCurrentState;
    public double mEndValue;
    public final String mId;
    public final PhysicsState mPreviousState;
    public SpringConfig mSpringConfig;
    public final BaseSpringSystem mSpringSystem;
    public final PhysicsState mTempState;
    public boolean mWasAtRest = true;
    public double mRestSpeedThreshold = 0.005d;
    public double mDisplacementFromRestThreshold = 0.005d;
    public final CopyOnWriteArraySet mListeners = new CopyOnWriteArraySet();
    public double mTimeAccumulator = 0.0d;

    public class PhysicsState {
        public double position;
        public double velocity;

        private PhysicsState() {
        }
    }

    public Spring(BaseSpringSystem baseSpringSystem) {
        this.mCurrentState = new PhysicsState();
        this.mPreviousState = new PhysicsState();
        this.mTempState = new PhysicsState();
        if (baseSpringSystem == null) {
            throw new IllegalArgumentException("Spring cannot be created outside of a BaseSpringSystem");
        }
        this.mSpringSystem = baseSpringSystem;
        StringBuilder sb = new StringBuilder("spring:");
        int i = ID;
        ID = i + 1;
        sb.append(i);
        this.mId = sb.toString();
        SpringConfig springConfig = SpringConfig.defaultConfig;
        if (springConfig == null) {
            throw new IllegalArgumentException("springConfig is required");
        }
        this.mSpringConfig = springConfig;
    }

    public final void addListener(SpringListener springListener) {
        this.mListeners.add(springListener);
    }

    public final boolean isAtRest() {
        PhysicsState physicsState = this.mCurrentState;
        if (Math.abs(physicsState.velocity) <= this.mRestSpeedThreshold) {
            return Math.abs(this.mEndValue - physicsState.position) <= this.mDisplacementFromRestThreshold || this.mSpringConfig.tension == 0.0d;
        }
        return false;
    }

    public final void removeAllListeners() {
        this.mListeners.clear();
    }

    public final void setAtRest() {
        PhysicsState physicsState = this.mCurrentState;
        double d = physicsState.position;
        this.mEndValue = d;
        this.mTempState.position = d;
        physicsState.velocity = 0.0d;
    }

    public final void setCurrentValue(double d) {
        this.mCurrentState.position = d;
        this.mSpringSystem.activateSpring(this.mId);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((SpringListener) it.next()).onSpringUpdate(this);
        }
        setAtRest();
    }

    public final void setEndValue(double d) {
        if (this.mEndValue == d && isAtRest()) {
            return;
        }
        double d2 = this.mCurrentState.position;
        this.mEndValue = d;
        this.mSpringSystem.activateSpring(this.mId);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((SpringListener) it.next()).onSpringEndStateChange(this);
        }
    }

    public final void setVelocity(double d) {
        PhysicsState physicsState = this.mCurrentState;
        if (d == physicsState.velocity) {
            return;
        }
        physicsState.velocity = d;
        this.mSpringSystem.activateSpring(this.mId);
    }
}
