package com.facebook.rebound;

import android.view.Choreographer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SpringSystem extends BaseSpringSystem {
    private SpringSystem(SpringLooper springLooper) {
        super(springLooper);
    }

    public static SpringSystem create() {
        return new SpringSystem(new AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper(Choreographer.getInstance()));
    }
}
