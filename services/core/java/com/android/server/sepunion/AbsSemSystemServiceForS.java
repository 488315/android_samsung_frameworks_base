package com.android.server.sepunion;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface AbsSemSystemServiceForS {
    void onUserStarting(int i);

    void onUserStopped(int i);

    void onUserStopping(int i);

    void onUserSwitching(int i, int i2);

    void onUserUnlocked(int i);

    void onUserUnlocking(int i);
}
