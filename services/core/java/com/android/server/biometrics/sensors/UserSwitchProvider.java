package com.android.server.biometrics.sensors;

public interface UserSwitchProvider {
    StartUserClient getStartUserClient(int i);

    StopUserClient getStopUserClient(int i);
}
