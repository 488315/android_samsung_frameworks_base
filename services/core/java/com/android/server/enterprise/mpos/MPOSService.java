package com.android.server.enterprise.mpos;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.samsung.android.knox.mpos.IMPOSService;
import com.samsung.android.knox.mpos.MposTZServiceConfig;
import com.samsung.android.knox.mpos.TACommandRequest;
import com.samsung.android.knox.mpos.TACommandResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import vendor.samsung.hardware.mpos.ISehMpos;
import vendor.samsung.hardware.mpos.ISehMpos$Stub$Proxy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MPOSService extends IMPOSService.Stub implements EnterpriseServiceCallback {
    public final Context context;
    public final Map mRegisteredTzNativeMap = new HashMap();
    public ISehMpos iSehMpos = null;

    public MPOSService(Context context) {
        Log.d("MPOSService", "start MPOSService: ");
        this.context = context;
    }

    public final boolean checkPermission() {
        int callingUid;
        try {
            callingUid = Binder.getCallingUid();
        } catch (Exception e) {
            Log.e("MPOSService", "checkSystemUid failed: " + e.toString());
        }
        if (UserHandle.isSameApp(callingUid, 1000)) {
            return true;
        }
        String nameForUid = this.context.getPackageManager().getNameForUid(callingUid);
        Log.d("MPOSService", "checkSystemUid: " + callingUid + ", name: " + nameForUid);
        ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(nameForUid, 0);
        if ("com.samsung.android.knox.mpos".equals(nameForUid) && applicationInfo.isSignedWithPlatformKey()) {
            return true;
        }
        Log.i("MPOSService", "checkSystemUid failed: callerUid: " + callingUid + ", name: " + nameForUid);
        return false;
    }

    public final synchronized boolean loadTa(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, MposTZServiceConfig mposTZServiceConfig) {
        if (!checkPermission()) {
            return false;
        }
        int fd = parcelFileDescriptor == null ? -1 : parcelFileDescriptor.getFd();
        Log.d("MPOSService", "loadTa: " + i + ", fdInt: " + fd + ", offset: " + j + ", len: " + j2);
        try {
            MposTZNative mposTZNative = (MposTZNative) ((HashMap) this.mRegisteredTzNativeMap).get(Integer.valueOf(i));
            if (mposTZNative == null) {
                mposTZNative = new MposTZNative(i, mposTZServiceConfig.maxSendCmdSize, mposTZServiceConfig.taTechnology, mposTZServiceConfig.rootName, mposTZServiceConfig.processName, mposTZServiceConfig.maxRecvRespSize);
                ((HashMap) this.mRegisteredTzNativeMap).put(Integer.valueOf(i), mposTZNative);
            }
            boolean loadTA = mposTZNative.loadTA(this.context, fd, j, j2);
            if (loadTA) {
                updateServiceHolder(true);
            }
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return loadTA;
        } finally {
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final synchronized TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) {
        Log.d("MPOSService", "processTACommand: " + i);
        if (!checkPermission()) {
            Log.i("MPOSService", "processTACommand: permission error");
            return null;
        }
        MposTZNative mposTZNative = (MposTZNative) ((HashMap) this.mRegisteredTzNativeMap).get(Integer.valueOf(i));
        if (mposTZNative != null) {
            return mposTZNative.processTACommand(tACommandRequest);
        }
        Log.e("MPOSService", "processTACommand fail cause tzNative null for " + i);
        return null;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
    }

    public final synchronized boolean unloadTa(int i) {
        Log.d("MPOSService", "unloadTa: " + i);
        if (!checkPermission()) {
            return false;
        }
        MposTZNative mposTZNative = (MposTZNative) ((HashMap) this.mRegisteredTzNativeMap).get(Integer.valueOf(i));
        if (mposTZNative == null) {
            Log.e("MPOSService", "unloadTa fail cause tzNative null for " + i);
            return false;
        }
        mposTZNative.unloadTA();
        ((HashMap) this.mRegisteredTzNativeMap).remove(Integer.valueOf(i));
        Log.i("MPOSService", "remaning map size: " + ((HashMap) this.mRegisteredTzNativeMap).size());
        if (((HashMap) this.mRegisteredTzNativeMap).size() <= 0) {
            updateServiceHolder(false);
        }
        return true;
    }

    public final void updateServiceHolder(boolean z) {
        try {
            boolean isDeclared = ServiceManager.isDeclared("vendor.samsung.hardware.mpos.ISehMpos/default");
            Log.i("MPOSService", "updateServiceHolder: " + isDeclared + ", " + z + ", " + this.iSehMpos);
            if (isDeclared) {
                ISehMpos iSehMpos = null;
                if (!z) {
                    this.iSehMpos = null;
                    return;
                }
                ISehMpos iSehMpos2 = this.iSehMpos;
                if (iSehMpos2 == null) {
                    IBinder waitForService = ServiceManager.waitForService("vendor.samsung.hardware.mpos.ISehMpos/default");
                    if (waitForService != null) {
                        IInterface queryLocalInterface = waitForService.queryLocalInterface(ISehMpos.DESCRIPTOR);
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof ISehMpos)) {
                            ISehMpos$Stub$Proxy iSehMpos$Stub$Proxy = new ISehMpos$Stub$Proxy();
                            iSehMpos$Stub$Proxy.mRemote = waitForService;
                            iSehMpos = iSehMpos$Stub$Proxy;
                        } else {
                            iSehMpos = (ISehMpos) queryLocalInterface;
                        }
                    }
                    iSehMpos2 = iSehMpos;
                }
                this.iSehMpos = iSehMpos2;
            }
        } catch (Exception e) {
            Log.i("MPOSService", "updateServiceHolder failed: " + e);
        }
    }
}
