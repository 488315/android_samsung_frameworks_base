package com.facebook.rebound;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public class BaseSpringSystem {
    public final SpringLooper mSpringLooper;
    public final Map mSpringRegistry = new HashMap();
    public final Set mActiveSprings = new CopyOnWriteArraySet();
    public final CopyOnWriteArraySet mListeners = new CopyOnWriteArraySet();
    public boolean mIdle = true;

    public BaseSpringSystem(SpringLooper springLooper) {
        if (springLooper == null) {
            throw new IllegalArgumentException("springLooper is required");
        }
        this.mSpringLooper = springLooper;
        springLooper.mSpringSystem = this;
    }

    public final void activateSpring(String str) {
        Spring spring = (Spring) ((HashMap) this.mSpringRegistry).get(str);
        if (spring == null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("springId ", str, " does not reference a registered spring"));
        }
        ((CopyOnWriteArraySet) this.mActiveSprings).add(spring);
        if (this.mIdle) {
            this.mIdle = false;
            this.mSpringLooper.start();
        }
    }

    public final Spring createSpring() {
        Spring spring = new Spring(this);
        HashMap map = (HashMap) this.mSpringRegistry;
        String str = spring.mId;
        if (map.containsKey(str)) {
            throw new IllegalArgumentException("spring is already registered");
        }
        ((HashMap) this.mSpringRegistry).put(str, spring);
        return spring;
    }
}
