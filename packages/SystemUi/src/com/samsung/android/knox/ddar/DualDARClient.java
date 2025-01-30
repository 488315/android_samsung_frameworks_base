package com.samsung.android.knox.ddar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DualDARClient extends Service implements IDualDARClient {
    public static final String DUAL_DAR_SDK_VERSION_1_0_0 = "1.0.0";
    public static final int ERROR_FAILURE = -1;
    public static final int ERROR_NONE = 0;
    public static final int FEATURE_RESET_PASSWORD = 1000;
    private static final String TAG = "DualDARClient";

    private IBinder bindClient(IDualDARClient iDualDARClient) {
        return DualDARManager.getInstance(this).bindClient(iDualDARClient);
    }

    public static int getFileSystemLog(Context context, String str) {
        return DualDARManager.getInstance(context).getFileSystemLog(str) ? 0 : -1;
    }

    public List<Integer> getDualDARUsers() {
        return DualDARManager.getInstance(this).getDualDARUsers();
    }

    public String getInstalledClientLibraryVersion() {
        return DualDARManager.getInstance(this).getInstalledClientLibraryVersion();
    }

    public int installLibrary(String str, List<String> list, boolean z) {
        return DualDARManager.getInstance(this).installLibrary(str, list, z) ? 0 : -1;
    }

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean isSupported(int i);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return bindClient(this);
    }

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract void onClearResetPasswordToken(int i, long j);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onClientBringup();

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract void onDataLockStateChange(int i, boolean z);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onDualDARDestroyForUser(int i);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onDualDARSetupForUser(int i);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onPasswordAuth(int i, byte[] bArr);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onPasswordChange(int i, byte[] bArr, byte[] bArr2);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onResetPasswordWithToken(int i, byte[] bArr, long j, byte[] bArr2);

    @Override // com.samsung.android.knox.ddar.IDualDARClient
    public abstract boolean onSetResetPasswordToken(int i, byte[] bArr, long j, byte[] bArr2);

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public void setSecret(int i, Map<String, Byte[]> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Byte[]> entry : map.entrySet()) {
            byte[] bArr = new byte[entry.getValue().length];
            for (int i2 = 0; i2 < entry.getValue().length; i2++) {
                bArr[i2] = entry.getValue()[i2].byteValue();
                entry.getValue()[i2] = (byte) 0;
            }
            arrayList.add(new Secret(entry.getKey(), bArr));
        }
        map.clear();
        DualDARManager.getInstance(this).setSecret(i, arrayList);
    }
}
