package com.android.p038wm.shell.animation;

import androidx.dynamicanimation.animation.SpringAnimation;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class PhysicsAnimator$startInternal$2 extends FunctionReferenceImpl implements Function0 {
    public PhysicsAnimator$startInternal$2(Object obj) {
        super(0, obj, SpringAnimation.class, NetworkAnalyticsConstants.DataPoints.OPEN_TIME, "start()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((SpringAnimation) this.receiver).start();
        return Unit.INSTANCE;
    }
}
