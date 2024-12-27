package com.android.systemui.statusbar.notification;

import com.android.systemui.animation.TransitionAnimator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LaunchAnimationParameters extends TransitionAnimator.State {
    public float linearProgress;
    public int notificationParentTop;
    public int parentStartClipTopAmount;
    public int parentStartRoundedTopClipping;
    public float progress;
    public int startClipTopAmount;
    public int startNotificationTop;
    public int startRoundedTopClipping;
    public float startTranslationZ;

    public /* synthetic */ LaunchAnimationParameters(int i, int i2, int i3, int i4, float f, float f2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, i4, (i5 & 16) != 0 ? 0.0f : f, (i5 & 32) != 0 ? 0.0f : f2);
    }

    public LaunchAnimationParameters(int i, int i2, int i3, int i4, float f, float f2) {
        super(i, i2, i3, i4, f, f2);
    }

    public LaunchAnimationParameters() {
        this(0, 0, 0, 0, 0.0f, 0.0f);
    }
}
