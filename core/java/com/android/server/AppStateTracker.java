package com.android.server;

public interface AppStateTracker {
    public static final String TAG = "AppStateTracker";

    public interface BackgroundRestrictedAppListener {
        void updateBackgroundRestrictedForUidPackage(int i, String str, boolean z);
    }

    void addBackgroundRestrictedAppListener(
            BackgroundRestrictedAppListener backgroundRestrictedAppListener);

    boolean isAppBackgroundRestricted(int i, String str);
}
