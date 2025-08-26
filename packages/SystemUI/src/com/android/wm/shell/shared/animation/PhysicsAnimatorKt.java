package com.android.wm.shell.shared.animation;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public abstract class PhysicsAnimatorKt {
    public static final WeakHashMap animators = new WeakHashMap();
    public static final PhysicsAnimator.SpringConfig globalDefaultSpring = new PhysicsAnimator.SpringConfig(1500.0f, 0.5f);
    public static final PhysicsAnimator.FlingConfig globalDefaultFling = new PhysicsAnimator.FlingConfig(1.0f, -3.4028235E38f, Float.MAX_VALUE);
}
