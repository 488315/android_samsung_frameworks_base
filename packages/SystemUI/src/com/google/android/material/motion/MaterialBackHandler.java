package com.google.android.material.motion;

import androidx.activity.BackEventCompat;

/* loaded from: classes4.dex */
public interface MaterialBackHandler {
    void cancelBackProgress();

    void handleBackInvoked();

    void startBackProgress(BackEventCompat backEventCompat);

    void updateBackProgress(BackEventCompat backEventCompat);
}
