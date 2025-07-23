package com.android.systemui.plugins.keyguardstatusview;

import android.animation.Animator;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginKeyguardStatusCallback {
    @VersionCheck(version = 3021)
    ArrayList<View> getShortCutAreaViews();

    boolean isDozing();

    boolean isKeyguardState();

    @VersionCheck(version = 3020)
    void onMusicItemExpaned(boolean z);

    void setFullScreenMode(boolean z, long j);

    @VersionCheck(version = 1019)
    void setFullScreenMode(boolean z, long j, Animator.AnimatorListener animatorListener);

    void setMusicShown(boolean z);

    @VersionCheck(version = VolteConstants.ErrorCode.CALL_HAS_BEEN_TRANSFERRED_TO_ANOTHER_DEVICE)
    void setNowBarExpandMode(boolean z, long j, Animator.AnimatorListener animatorListener);

    @VersionCheck(version = VolteConstants.ErrorCode.CALL_HAS_BEEN_TRANSFERRED_TO_ANOTHER_DEVICE)
    void setNowBarVisibility(boolean z);

    @VersionCheck(version = 3022)
    void showOneCardAnimation(boolean z);

    void startActivity(PendingIntent pendingIntent);

    void startActivity(Intent intent, boolean z, int i);

    void userActivity();
}
