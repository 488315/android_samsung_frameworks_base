package com.android.server.enterprise.auditlog;

import android.app.admin.IDevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.auditlog.LogWritter.LooperThread;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.log.AuditLogRulesInfo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Admin {
    public ParcelFileDescriptor mAdminOutputFile;
    public AuditLogRulesInfo mAuditLogRulesInfo = new AuditLogRulesInfo();
    public long mBegin;
    public final Context mContext;
    public List mDeviceInfo;
    public Filter mDumpFilter;
    public final EdmStorageProvider mEdmStorageProvider;
    public long mEnd;
    public volatile boolean mIsDumping;
    public final boolean mIsProfileOwnerOfOrganizationOwnedDevice;
    public final boolean mIsPseudoAdminOfOrganizationOwnedDevice;
    public final LogWritter mLogWritter;
    public final String mPackageName;
    public final int mUid;

    public Admin(Context context, String str, int i) {
        this.mPackageName = null;
        this.mPackageName = str;
        LogWritter logWritter = new LogWritter();
        logWritter.mCircularBuffer = new CircularBuffer(context, str, i);
        LogWritter.LooperThread looperThread = logWritter.new LooperThread();
        logWritter.mLooperThread = looperThread;
        looperThread.start();
        this.mLogWritter = logWritter;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        logWritter.mObserver = this;
        this.mIsDumping = false;
        this.mDumpFilter = null;
        this.mContext = context;
        this.mBegin = 0L;
        this.mEnd = 0L;
        this.mUid = i;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Admin uid = ", "Admin");
        try {
            boolean checkPseudoAdminForUid = edmStorageProvider.checkPseudoAdminForUid(i);
            this.mIsPseudoAdminOfOrganizationOwnedDevice = checkPseudoAdminForUid;
            Log.d("Admin", "mIsPseudoAdminOfOrganizationOwnedDevice = " + checkPseudoAdminForUid);
        } catch (SettingNotFoundException e) {
            Log.e("Admin", "mEdmStorageProvider.checkPseudoAdminForUid: error " + e.getMessage());
        }
        IDevicePolicyManager asInterface =
                IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface != null) {
            try {
                boolean isProfileOwnerOfOrganizationOwnedDeviceMDM =
                        asInterface.isProfileOwnerOfOrganizationOwnedDeviceMDM(
                                UserHandle.getUserId(i));
                this.mIsProfileOwnerOfOrganizationOwnedDevice =
                        isProfileOwnerOfOrganizationOwnedDeviceMDM;
                Log.d(
                        "Admin",
                        "mIsProfileOwnerOfOrganizationOwnedDevice = "
                                + isProfileOwnerOfOrganizationOwnedDeviceMDM);
            } catch (RemoteException e2) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e2,
                        new StringBuilder(
                                "Error on calling isProfileOwnerOfOrganizationOwnedDeviceMDM : "),
                        "Admin");
            }
        }
    }

    public final void deleteAllFiles() {
        CircularBuffer circularBuffer = this.mLogWritter.mCircularBuffer;
        if (circularBuffer.mCurrentNode != null) {
            synchronized (circularBuffer.mLock) {
                circularBuffer.mCurrentNode.closeFile();
                circularBuffer.mCurrentNode.delete();
            }
        }
        synchronized (circularBuffer.mDumpList) {
            try {
                Iterator it = circularBuffer.mDumpList.iterator();
                while (it.hasNext()) {
                    ((PartialFileNode) it.next()).delete();
                    it.remove();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        CircularBuffer.deleteDirectory(new File(circularBuffer.mAdminDirectoryPath));
        CircularBuffer.deleteDirectory(
                new File(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                circularBuffer.mUid,
                                new StringBuilder("/data/system/"),
                                "_bubble/bubbleFile")));
        CircularBuffer.deleteDirectory(
                new File(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                circularBuffer.mUid,
                                new StringBuilder("/data/system/"),
                                "_bubble")));
    }

    public final boolean dump(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        if (this.mIsDumping) {
            return false;
        }
        this.mIsDumping = true;
        this.mBegin = j;
        this.mEnd = j2;
        this.mAdminOutputFile = parcelFileDescriptor;
        LogWritter logWritter = this.mLogWritter;
        logWritter.mObserver = this;
        logWritter.setIsDumping(true, false);
        LogWritter logWritter2 = this.mLogWritter;
        logWritter2.getClass();
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("swap", "swap");
        message.setData(bundle);
        logWritter2.mLooperThread.mHandler.sendMessage(message);
        return this.mIsDumping;
    }

    public final void notifyDumpFinished(boolean z, boolean z2) {
        this.mIsDumping = false;
        this.mLogWritter.mCircularBuffer.mTypeOfDump = z2;
        this.mLogWritter.setIsDumping(false, z);
        int i = this.mUid;
        IPersonaManagerAdapter iPersonaManagerAdapter =
                (IPersonaManagerAdapter)
                        AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
        int userId = UserHandle.getUserId(this.mUid);
        ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
        if (SemPersonaManager.isSecureFolderId(userId)) {
            i = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, this.mUid);
        }
        int userId2 = UserHandle.getUserId(i);
        if (this.mIsPseudoAdminOfOrganizationOwnedDevice) {
            int i2 = EnterpriseDeviceManagerService.$r8$clinit;
            EnterpriseDeviceManagerService enterpriseDeviceManagerService =
                    (EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance;
            if (enterpriseDeviceManagerService != null) {
                userId2 = enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId();
            }
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.DUMP_LOG_RESULT");
        String str = this.mPackageName;
        if (str != null) {
            intent.setPackage(str);
        }
        if (z) {
            intent.putExtra("com.samsung.android.knox.intent.extra.AUDIT_RESULT", 0);
        } else {
            InformFailure.getInstance()
                    .broadcastFailure("Dump failed! Sending Intent!", this.mPackageName);
            intent.putExtra("com.samsung.android.knox.intent.extra.AUDIT_RESULT", -2000);
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
        this.mContext.sendBroadcastAsUser(
                intent,
                new UserHandle(userId2),
                "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        try {
            String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
            Intent intent2 = new Intent(intent);
            intent2.putExtra(
                    "com.samsung.android.knox.intent.extra.ADMIN_UID",
                    this.mContext
                            .getPackageManager()
                            .getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
            if (this.mPackageName != null) {
                intent2.setPackage(kpuPackageName);
            }
            this.mContext.sendBroadcastAsUser(
                    intent2,
                    new UserHandle(userId2),
                    "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setBootCompleted(boolean z) {
        CircularBuffer circularBuffer = this.mLogWritter.mCircularBuffer;
        circularBuffer.mIsBootCompleted = z;
        if (circularBuffer.mIsBootCompleted) {
            synchronized (circularBuffer.mPendingIntentErrors) {
                try {
                    Iterator it = circularBuffer.mPendingIntentErrors.iterator();
                    while (it.hasNext()) {
                        InformFailure.getInstance()
                                .broadcastFailure(
                                        (Exception) it.next(), circularBuffer.mPackageName);
                    }
                } finally {
                }
            }
        }
    }
}
