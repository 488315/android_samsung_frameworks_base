package com.android.systemui.plugins.keyguardstatusview;

import android.animation.Animator;
import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginKeyguardStatusCallback {
    boolean isDozing();

    boolean isKeyguardState();

    void setFullScreenMode(boolean z, long j);

    @VersionCheck(version = 1019)
    void setFullScreenMode(boolean z, long j, Animator.AnimatorListener animatorListener);

    void setMusicShown(boolean z);

    void startActivity(PendingIntent pendingIntent);

    void startActivity(Intent intent, boolean z, int i);

    void userActivity();
}
