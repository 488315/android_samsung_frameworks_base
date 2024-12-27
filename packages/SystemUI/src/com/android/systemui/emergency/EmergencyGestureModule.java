package com.android.systemui.emergency;

public final class EmergencyGestureModule {
    public static final EmergencyGestureModule INSTANCE = new EmergencyGestureModule();
    public static final String TAG = "EmergencyGestureModule";

    public interface EmergencyGestureIntentFactory {
    }

    private EmergencyGestureModule() {
    }
}
