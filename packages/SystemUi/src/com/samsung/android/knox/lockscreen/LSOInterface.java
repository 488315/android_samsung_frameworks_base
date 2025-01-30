package com.samsung.android.knox.lockscreen;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.lockscreen.ILockscreenOverlay;
import java.security.AccessControlException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOInterface implements ServiceConnection {
    public static final String TAG = "LSO_LSOInterface";
    public static LSOInterface gLockscreenOverlay;
    public static final Object mSync = new Object();
    public final Context mContext;
    public ContextInfo mContextInfo;
    public ILockscreenOverlay mLSOService;

    public LSOInterface(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static LSOInterface getInstance(Context context) {
        LSOInterface lSOInterface;
        synchronized (mSync) {
            if (gLockscreenOverlay == null) {
                gLockscreenOverlay = new LSOInterface(new ContextInfo(Process.myUid()), context.getApplicationContext());
            }
            lSOInterface = gLockscreenOverlay;
        }
        return lSOInterface;
    }

    public final boolean canConfigure(int i) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return false;
        }
        try {
            return this.mLSOService.canConfigure(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(TAG, "Caller does not have required permissions", e2);
            return false;
        }
    }

    public final LSOItemData getData(int i) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return null;
        }
        try {
            return this.mLSOService.getData(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return null;
        }
    }

    public final LSOAttributeSet getPreferences() {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return null;
        }
        try {
            return this.mLSOService.getPreferences(this.mContextInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Unhandled exception", e2);
            return null;
        }
    }

    public final ILockscreenOverlay getService() {
        if (this.mLSOService == null) {
            this.mLSOService = ILockscreenOverlay.Stub.asInterface(ServiceManager.getService("lockscreen_overlay"));
        }
        return this.mLSOService;
    }

    public final boolean isConfigured(int i) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return false;
        }
        try {
            return this.mLSOService.isConfigured(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return false;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mLSOService = ILockscreenOverlay.Stub.asInterface(iBinder);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.mLSOService = null;
    }

    public final void reset() {
        resetData(0);
    }

    public final void resetData(int i) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
        } else {
            if (!canConfigure(1)) {
                throw new AccessControlException("Calling admin is not allowed to reset Lockscreen Overlay.");
            }
            try {
                this.mLSOService.resetData(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            }
        }
    }

    public final void resetWallpaper() {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
        } else {
            if (!canConfigure(2)) {
                throw new AccessControlException("Calling admin is not allowed to reset wallpaper");
            }
            try {
                this.mLSOService.resetWallpaper(this.mContextInfo);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            }
        }
    }

    public final int setData(LSOItemData lSOItemData, int i) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return -5;
        }
        try {
            return this.mLSOService.setData(this.mContextInfo, lSOItemData, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return -2;
        }
    }

    public final int setPreferences(LSOAttributeSet lSOAttributeSet) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return -5;
        }
        try {
            return this.mLSOService.setPreferences(this.mContextInfo, lSOAttributeSet);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return -2;
        } catch (SecurityException e2) {
            Log.e(TAG, "SecurityException exception", e2);
            throw new SecurityException("No Active Admins or MDM Permission to calling Package");
        } catch (Exception e3) {
            Log.e(TAG, "Unhandled exception", e3);
            return -2;
        }
    }

    public final int setWallpaper(String str, ParcelFileDescriptor parcelFileDescriptor) {
        if (getService() == null) {
            Log.e(TAG, "LSO Service is not yet ready!!!");
            return -5;
        }
        try {
            return this.mLSOService.setWallpaper(this.mContextInfo, str, parcelFileDescriptor);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking with Lockscreen customization service", e);
            return -2;
        }
    }

    public final LSOItemData getData() {
        return getData(1);
    }

    public final int setData(LSOItemData lSOItemData) {
        return setData(lSOItemData, 1);
    }

    public static LSOInterface getInstance(ContextInfo contextInfo, Context context) {
        LSOInterface lSOInterface;
        synchronized (mSync) {
            if (gLockscreenOverlay == null) {
                gLockscreenOverlay = new LSOInterface(contextInfo, context.getApplicationContext());
            }
            lSOInterface = gLockscreenOverlay;
        }
        return lSOInterface;
    }

    public final void resetData() {
        resetData(1);
    }
}
