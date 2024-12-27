package com.android.systemui.plugins.keyguardstatusview;

import android.animation.Animator;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
