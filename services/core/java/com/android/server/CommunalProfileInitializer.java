package com.android.server;

import android.os.UserManager;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CommunalProfileInitializer {
    public final UserManagerInternal mUmi = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);

    public static void removeCommunalProfileIfPresent() {
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        int communalProfileId = userManagerInternal.getCommunalProfileId();
        if (communalProfileId == -10000) {
            return;
        }
        Slogf.d("CommunalProfileInitializer", "Removing existing Communal Profile, userId=%d", Integer.valueOf(communalProfileId));
        if (userManagerInternal.removeUserEvenWhenDisallowed(communalProfileId)) {
            return;
        }
        Slogf.e("CommunalProfileInitializer", "Failed to remove Communal Profile, userId=%d", Integer.valueOf(communalProfileId));
    }

    public final void init(TimingsTraceAndSlog timingsTraceAndSlog) {
        Slogf.i("CommunalProfileInitializer", "init())");
        timingsTraceAndSlog.traceBegin("createCommunalProfileIfNeeded");
        int communalProfileId = this.mUmi.getCommunalProfileId();
        if (communalProfileId != -10000) {
            Slogf.d("CommunalProfileInitializer", "Found existing Communal Profile, userId=%d", Integer.valueOf(communalProfileId));
        } else {
            Slogf.d("CommunalProfileInitializer", "Creating a new Communal Profile");
            try {
                Slogf.i("CommunalProfileInitializer", "Successfully created Communal Profile, userId=%d", Integer.valueOf(this.mUmi.createUserEvenWhenDisallowed(null, "android.os.usertype.profile.COMMUNAL", 0, null, null).id));
            } catch (UserManager.CheckedUserOperationException e) {
                Slogf.wtf("CommunalProfileInitializer", "Communal Profile creation failed", (Throwable) e);
            }
        }
        timingsTraceAndSlog.traceEnd();
    }
}
