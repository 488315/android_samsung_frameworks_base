package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* loaded from: classes3.dex */
public interface CastController extends CallbackController, Dumpable {

    public interface Callback {
        void onCastDevicesChanged();
    }
}
