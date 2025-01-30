package com.android.systemui.biometrics;

import android.os.Bundle;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface AuthBiometricViewAdapter {
    View asView();

    void cancelAnimation();

    boolean isCoex();

    void onAuthenticationFailed(int i, String str);

    void onAuthenticationSucceeded(int i);

    void onDialogAnimatedIn(boolean z);

    void onError(int i, String str);

    void onHelp(int i, String str);

    void onOrientationChanged();

    void onSaveState(Bundle bundle);

    void requestLayout();

    void restoreState(Bundle bundle);

    void startTransitionToCredentialUI();
}
