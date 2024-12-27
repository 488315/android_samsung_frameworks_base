package com.samsung.android.knox;

import android.content.Context;
import android.content.IRCPInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SemRemoteContentManager {
    public static final int ERROR = -333;
    private static final String TAG = "SemRemoteContentManager";
    ISemRemoteContentManager mService;

    public void registerRCPInterface(IRCPInterface rcpInterface, int userId) {
        if (this.mService != null) {
            try {
                Log.d(TAG, "registerRCPInterface(): My Context is " + this);
                this.mService.registerRCPInterface(rcpInterface, userId);
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "registerRCPInterface: RemoteException trying to register rcpInterface",
                        e);
                e.printStackTrace();
            }
        }
    }

    public SemRemoteContentManager(ISemRemoteContentManager service) {
        this.mService = service;
    }

    public IRCPInterface getRCPInterface() {
        if (this.mService != null) {
            try {
                return this.mService.getRCPInterface();
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException trying to get RCPInterface from getRCPInterface().", e);
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public int copyFileInternal(
            int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
            throws RemoteException {
        if (this.mService != null) {
            return this.mService.copyFileInternal(
                    srcContainerId, srcFilePath, destContainerId, destFilePath);
        }
        return -1;
    }

    public int moveFile(
            int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
            throws RemoteException {
        if (this.mService != null) {
            return this.mService.moveFile(
                    srcContainerId, srcFilePath, destContainerId, destFilePath);
        }
        return -1;
    }

    public boolean isFileExist(String path, int containerId) throws RemoteException {
        if (this.mService != null) {
            return this.mService.isFileExist(path, containerId);
        }
        return false;
    }

    public List<String> getFiles(String path, int containerId) throws RemoteException {
        if (this.mService != null) {
            return this.mService.getFiles(path, containerId);
        }
        return new ArrayList();
    }

    public Bundle getFileInfo(String path, int containerId) throws RemoteException {
        if (this.mService != null) {
            return this.mService.getFileInfo(path, containerId);
        }
        return new Bundle();
    }

    public int copyChunks(
            int srcContainerId,
            String srcFilePath,
            int destContainerId,
            String destFilePath,
            long offset,
            int length,
            long sessionId,
            boolean deleteSrc)
            throws RemoteException {
        if (this.mService != null) {
            return this.mService.copyChunks(
                    srcContainerId,
                    srcFilePath,
                    destContainerId,
                    destFilePath,
                    offset,
                    length,
                    sessionId,
                    deleteSrc);
        }
        return ERROR;
    }

    public void cancelCopyChunks(long sessionId) throws RemoteException {
        if (this.mService != null) {
            this.mService.cancelCopyChunks(sessionId);
        }
    }

    public boolean deleteFile(String path, int containerId) throws RemoteException {
        if (this.mService != null) {
            return this.mService.deleteFile(path, containerId);
        }
        return false;
    }

    public Bundle exchangeData(Context ctx, int userId, Bundle bundle) throws RemoteException {
        if (this.mService != null) {
            String pkgName = ctx.getPackageName();
            return this.mService.exchangeData(pkgName, userId, bundle);
        }
        return new Bundle();
    }

    public int copyFile(
            int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
            throws RemoteException {
        if (this.mService != null) {
            Log.d(
                    TAG,
                    "copyFile: srcContainerId"
                            + srcContainerId
                            + " srcFilePath"
                            + srcFilePath
                            + " destContainerId"
                            + destContainerId
                            + " destFilePath"
                            + destFilePath);
            return this.mService.copyFile(
                    srcContainerId, srcFilePath, destContainerId, destFilePath);
        }
        return -1;
    }

    public long moveFiles(int requestApp, Uri uri, int fileCount, int containerID)
            throws RemoteException {
        if (uri == null) {
            Log.d(TAG, "moveFiles uri is null");
            return -1L;
        }
        if (fileCount < 0) {
            Log.d(TAG, "moveFiles total fileCount is smaller than zero : " + fileCount);
            return -1L;
        }
        if (this.mService != null) {
            return this.mService.moveUnlimitedFiles(requestApp, uri, fileCount, containerID);
        }
        return -1L;
    }

    public long moveFiles(
            int requestApp, List<String> srcFilePaths, List<String> destFilePaths, int containerId)
            throws RemoteException {
        if (requestApp < 0) {
            Log.d(TAG, "Invalid App Id : " + requestApp);
            return -1L;
        }
        if (srcFilePaths == null || (srcFilePaths != null && srcFilePaths.size() == 0)) {
            Log.d(TAG, "invalid srcFilePaths");
            return -1L;
        }
        if (destFilePaths == null || (destFilePaths != null && destFilePaths.size() == 0)) {
            Log.d(TAG, "invalid destFilePaths");
            return -1L;
        }
        if (this.mService != null) {
            return this.mService.moveFilesForAppEx(
                    requestApp, srcFilePaths, destFilePaths, containerId);
        }
        return -1L;
    }
}
