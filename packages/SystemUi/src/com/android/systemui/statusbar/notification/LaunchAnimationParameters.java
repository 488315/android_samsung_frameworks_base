package com.android.systemui.statusbar.notification;

import com.android.systemui.animation.LaunchAnimator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LaunchAnimationParameters extends LaunchAnimator.State {
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
