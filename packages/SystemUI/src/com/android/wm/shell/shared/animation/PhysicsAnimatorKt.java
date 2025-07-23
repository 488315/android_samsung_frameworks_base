package com.android.wm.shell.shared.animation;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PhysicsAnimatorKt {
    public static final WeakHashMap animators = new WeakHashMap();
    public static final PhysicsAnimator.SpringConfig globalDefaultSpring = new PhysicsAnimator.SpringConfig(1500.0f, 0.5f);
    public static final PhysicsAnimator.FlingConfig globalDefaultFling = new PhysicsAnimator.FlingConfig(1.0f, -3.4028235E38f, Float.MAX_VALUE);
}
