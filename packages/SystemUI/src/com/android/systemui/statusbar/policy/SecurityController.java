package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* loaded from: classes3.dex */
public interface SecurityController extends CallbackController, Dumpable {

    public interface SecurityControllerCallback {
        void onStateChanged();
    }
}
