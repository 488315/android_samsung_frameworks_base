package com.samsung.android.authnrservice.service;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.samsung.android.authnrservice.manager.ISemAuthnrService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemAuthnrService extends ISemAuthnrService.Stub {
    public Context mContext;

    public final boolean deleteFile(String str) {
        boolean deleteFileRec;
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "df denied");
        try {
            FileOperation fileOperation = FileOperation.getInstance();
            synchronized (fileOperation) {
                deleteFileRec = fileOperation.deleteFileRec("/data/.fido/" + str);
            }
            return deleteFileRec;
        } catch (Exception e) {
            AuthnrLog.e("SAS", "deleteFile failed : " + e.getMessage());
            return false;
        }
    }

    public final byte[] getDrkKeyHandle() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "gdkh denied");
        try {
            return DrkOperation.getInstance().getDrkKeyHandle();
        } catch (Exception e) {
            AuthnrLog.e("SAS", "getDrkKeyHandle failed : " + e.getMessage());
            return new byte[0];
        }
    }

    public final List getFiles(String str, String str2) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "gf denied");
        try {
            FileOperation.getInstance().getClass();
            return FileOperation.getFilesRec("/data/.fido/" + str, str2);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "getFiles failed : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public final List getMatchedFilePaths(String str, String str2) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "gmfp denied");
        try {
            FileOperation.getInstance().getClass();
            String str3 = "";
            String trim = str == null ? "" : str.trim();
            if (trim.endsWith("/")) {
                trim = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, trim);
            }
            String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("/data/.fido/", trim);
            if (str2 != null) {
                str3 = str2.trim();
            }
            return FileOperation.getFilesPaths(m.trim(), str3.trim());
        } catch (Exception e) {
            AuthnrLog.e("SAS", "getMatchedFilePaths failed : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public final int getVersion() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "gv denied");
        Log.i("SASvc_SAS", "version :" + FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
        return FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP;
    }

    public final byte[] getWrappedObject(byte[] bArr) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "gwo denied");
        try {
            return FingerprintOperation.getInstance(this.mContext).getWrappedObject(bArr);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "getWrappedObject failed : " + e.getMessage());
            return new byte[0];
        }
    }

    public final boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "i denied");
        AuthnrLog.e("SAS", "initialize not supported");
        if (Binder.getCallingPid() == Process.myPid() || parcelFileDescriptor == null) {
            return false;
        }
        try {
            parcelFileDescriptor.close();
            return false;
        } catch (IOException unused) {
            AuthnrLog.w("SAS", "failed to close");
            return false;
        }
    }

    public final boolean initializeDrk() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "id denied");
        try {
            return DrkOperation.getInstance().initialize(this.mContext);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "initializeDrk failed : " + e.getMessage());
            return false;
        }
    }

    public final boolean initializePreloadedTa(int i) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "ipt denied");
        AuthnrLog.e("SAS", "initializePreloadedTa not supported");
        return false;
    }

    public final boolean initializeWithPreloadedTa() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "iwpt denied");
        AuthnrLog.e("SAS", "initializeWithPreloadedTa not supported");
        return false;
    }

    public final byte[] process(byte[] bArr) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "p denied");
        AuthnrLog.e("SAS", "process not supported");
        return new byte[0];
    }

    public final byte[] processPreloadedTa(int i, byte[] bArr) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "ppt denied");
        AuthnrLog.e("SAS", "processPreloadedTa not supported");
        return new byte[0];
    }

    public final byte[] processWithPreloadedTa(byte[] bArr, String str) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "pwpt denied");
        AuthnrLog.e("SAS", "processWithPreloadedTa not supported");
        return new byte[0];
    }

    public final String readFile(String str) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "rf denied");
        try {
            FileOperation.getInstance().getClass();
            return FileOperation.readFile(str);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "readFile failed : " + e.getMessage());
            return "";
        }
    }

    public final boolean setChallenge(byte[] bArr) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "sc denied");
        try {
            return FingerprintOperation.getInstance(this.mContext).setChallenge(bArr);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "setChallenge failed : " + e.getMessage());
            return false;
        }
    }

    public final boolean terminate() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "t denied");
        AuthnrLog.e("SAS", "terminate not supported");
        return false;
    }

    public final boolean terminateDrk() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "td denied");
        try {
            return DrkOperation.getInstance().terminate();
        } catch (Exception e) {
            AuthnrLog.e("SAS", "terminateDrk failed : " + e.getMessage());
            return false;
        }
    }

    public final boolean terminatePreloadedTa(int i) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "tpt denied");
        AuthnrLog.e("SAS", "terminatePreloadedTa not supported");
        return false;
    }

    public final boolean terminateWithPreloadedTa() {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "twpt denied");
        AuthnrLog.e("SAS", "terminateWithPreloadedTa not supported");
        return false;
    }

    public final boolean writeFile(byte[] bArr, String str) {
        this.mContext.enforceCallingPermission("com.samsung.android.permission.REQUEST_AUTHNR_SERVICE", "wf denied");
        try {
            return FileOperation.getInstance().writeFile(bArr, str);
        } catch (Exception e) {
            AuthnrLog.e("SAS", "writeFile failed : " + e.getMessage());
            return false;
        }
    }
}
