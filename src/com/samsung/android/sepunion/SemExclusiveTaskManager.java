package com.samsung.android.sepunion;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import com.samsung.android.sepunion.ISemExclusiveTaskManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemExclusiveTaskManager {
    private static final String TAG = "SemExclusiveTaskManager";
    private static ISemExclusiveTaskManager mService;
    private static SemExclusiveTaskManager sInstance;
    static final Object sInstanceSync = new Object();
    private Context mContext;
    final int mUserId;

    public SemExclusiveTaskManager(Context context) {
        this.mContext = context;
        this.mUserId = -2;
    }

    public SemExclusiveTaskManager(Context context, ISemExclusiveTaskManager iSemExclusiveTaskManager, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    public static SemExclusiveTaskManager getInstance(Context context) {
        int i;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) != 0 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                    i = UserHandle.myUserId();
                    sInstance = new SemExclusiveTaskManager(context, null, i);
                }
                i = -2;
                sInstance = new SemExclusiveTaskManager(context, null, i);
            }
        }
        return sInstance;
    }

    private ISemExclusiveTaskManager getService() {
        if (mService == null) {
            mService = ISemExclusiveTaskManager.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_EXCLUSIVE_TASK));
        }
        return mService;
    }

    public List<String> getExclusiveTaskList(String str) {
        ISemExclusiveTaskManager service = getService();
        if (service != null) {
            try {
                return service.getExclusiveTaskList(str);
            } catch (RemoteException e) {
                android.util.Log.d(TAG, "Failed to call getExclusiveTaskList()", e);
            }
        }
        return new ArrayList();
    }
}
