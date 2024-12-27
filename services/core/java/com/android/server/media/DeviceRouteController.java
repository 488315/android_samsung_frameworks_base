package com.android.server.media;

import android.media.MediaRoute2Info;
import android.os.UserHandle;

import java.util.List;

public interface DeviceRouteController {
    List getAvailableRoutes();

    MediaRoute2Info getSelectedRoute();

    void start(UserHandle userHandle);

    void stop();

    void transferTo(String str);

    boolean updateVolume(int i);
}
