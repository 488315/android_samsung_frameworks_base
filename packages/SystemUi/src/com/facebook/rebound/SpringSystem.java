package com.facebook.rebound;

import android.view.Choreographer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SpringSystem extends BaseSpringSystem {
    private SpringSystem(SpringLooper springLooper) {
        super(springLooper);
    }

    public static SpringSystem create() {
        return new SpringSystem(new AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper(Choreographer.getInstance()));
    }
}
