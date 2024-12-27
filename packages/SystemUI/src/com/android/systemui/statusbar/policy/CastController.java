package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface CastController extends CallbackController, Dumpable {

    public interface Callback {
        void onCastDevicesChanged();
    }

    public final class CastDevice {
        public String name;
        public int state = 0;
        public Object tag;
    }
}
