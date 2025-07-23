package com.samsung.android.knox;

import android.content.Context;
import android.content.IRCPInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemRemoteContentManager {
    public static final int ERROR = -333;
    private static final String TAG = "SemRemoteContentManager";
    ISemRemoteContentManager mService;

    public void registerRCPInterface(IRCPInterface iRCPInterface, int i) {
        if (this.mService != null) {
            try {
                Log.d(TAG, "registerRCPInterface(): My Context is " + this);
                this.mService.registerRCPInterface(iRCPInterface, i);
            } catch (RemoteException e) {
                Log.e(TAG, "registerRCPInterface: RemoteException trying to register rcpInterface", e);
                e.printStackTrace();
            }
        }
    }

    public SemRemoteContentManager(ISemRemoteContentManager iSemRemoteContentManager) {
        this.mService = iSemRemoteContentManager;
    }

    public IRCPInterface getRCPInterface() {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager == null) {
            return null;
        }
        try {
            return iSemRemoteContentManager.getRCPInterface();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException trying to get RCPInterface from getRCPInterface().", e);
            e.printStackTrace();
            return null;
        }
    }

    public int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.copyFileInternal(i, str, i2, str2);
        }
        return -1;
    }

    public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.moveFile(i, str, i2, str2);
        }
        return -1;
    }

    public boolean isFileExist(String str, int i) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.isFileExist(str, i);
        }
        return false;
    }

    public List<String> getFiles(String str, int i) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.getFiles(str, i);
        }
        return new ArrayList();
    }

    public Bundle getFileInfo(String str, int i) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.getFileInfo(str, i);
        }
        return new Bundle();
    }

    public int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        return iSemRemoteContentManager != null ? iSemRemoteContentManager.copyChunks(i, str, i2, str2, j, i3, j2, z) : ERROR;
    }

    public void cancelCopyChunks(long j) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            iSemRemoteContentManager.cancelCopyChunks(j);
        }
    }

    public boolean deleteFile(String str, int i) throws RemoteException {
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.deleteFile(str, i);
        }
        return false;
    }

    public Bundle exchangeData(Context context, int i, Bundle bundle) throws RemoteException {
        if (this.mService != null) {
            return this.mService.exchangeData(context.getPackageName(), i, bundle);
        }
        return new Bundle();
    }

    public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
        if (this.mService == null) {
            return -1;
        }
        Log.d(TAG, "copyFile: srcContainerId" + i + " srcFilePath" + str + " destContainerId" + i2 + " destFilePath" + str2);
        return this.mService.copyFile(i, str, i2, str2);
    }

    public long moveFiles(int i, Uri uri, int i2, int i3) throws RemoteException {
        if (uri == null) {
            Log.d(TAG, "moveFiles uri is null");
            return -1L;
        }
        if (i2 < 0) {
            Log.d(TAG, "moveFiles total fileCount is smaller than zero : " + i2);
            return -1L;
        }
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.moveUnlimitedFiles(i, uri, i2, i3);
        }
        return -1L;
    }

    public long moveFiles(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
        if (i < 0) {
            Log.d(TAG, "Invalid App Id : " + i);
            return -1L;
        }
        if (list == null || (list != null && list.size() == 0)) {
            Log.d(TAG, "invalid srcFilePaths");
            return -1L;
        }
        if (list2 == null || (list2 != null && list2.size() == 0)) {
            Log.d(TAG, "invalid destFilePaths");
            return -1L;
        }
        ISemRemoteContentManager iSemRemoteContentManager = this.mService;
        if (iSemRemoteContentManager != null) {
            return iSemRemoteContentManager.moveFilesForAppEx(i, list, list2, i2);
        }
        return -1L;
    }
}
