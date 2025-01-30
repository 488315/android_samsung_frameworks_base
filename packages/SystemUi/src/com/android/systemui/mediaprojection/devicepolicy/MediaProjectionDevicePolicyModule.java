package com.android.systemui.mediaprojection.devicepolicy;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionDevicePolicyModule {
    public static UserHandle workProfileUserHandle(UserTracker userTracker) {
        Object obj;
        Iterator it = ((UserTrackerImpl) userTracker).getUserProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((UserInfo) obj).isManagedProfile()) {
                break;
            }
        }
        UserInfo userInfo = (UserInfo) obj;
        if (userInfo != null) {
            return userInfo.getUserHandle();
        }
        return null;
    }
}
