package com.facebook.rebound;

import android.view.Choreographer;

/* loaded from: classes3.dex */
public class SpringSystem extends BaseSpringSystem {
    private SpringSystem(SpringLooper springLooper) {
        super(springLooper);
    }

    public static SpringSystem create() {
        return new SpringSystem(new AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper(Choreographer.getInstance()));
    }
}
