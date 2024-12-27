package com.android.server.media;

import android.media.MediaRoute2Info;
import android.os.UserHandle;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface DeviceRouteController {
    List getAvailableRoutes();

    MediaRoute2Info getSelectedRoute();

    void start(UserHandle userHandle);

    void stop();

    void transferTo(String str);

    boolean updateVolume(int i);
}
