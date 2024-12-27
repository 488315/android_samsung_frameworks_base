package com.android.server.enterprise.adapterlayer;

import android.app.ActivityManager;
import android.content.Context;
import android.os.UserManager;
import android.util.Log;

import com.android.server.enterprise.adapter.IPersonaManagerAdapter;

import com.samsung.android.knox.SemPersonaManager;

public final class PersonaManagerAdapter implements IPersonaManagerAdapter {
    public static PersonaManagerAdapter sInstance;
    public Context mContext;
    public SemPersonaManager mPersonaMgr;
    public UserManager mUserMgr;

    public final int getFocusedUserWithActivityManager() {
        try {
            return getPersonaManager() != null
                    ? getPersonaManager().getFocusedKnoxId()
                    : ((ActivityManager.RunningTaskInfo)
                                    ActivityManager.getService().getTasks(1).get(0))
                            .numRunning;
        } catch (Exception e) {
            Log.e(
                    "PersonaManagerAdapter",
                    "getFocusedUserWithActivityManager() failed. so return owner id",
                    e);
            return 0;
        }
    }

    public final SemPersonaManager getPersonaManager() {
        if (this.mPersonaMgr == null) {
            this.mPersonaMgr = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.mPersonaMgr;
    }
}
