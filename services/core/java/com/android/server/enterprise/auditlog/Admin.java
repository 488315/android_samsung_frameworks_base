package com.android.server.enterprise.auditlog;

import android.app.admin.IDevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.log.AuditLogRulesInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Admin implements IObserver {
    public ParcelFileDescriptor mAdminOutputFile;
    public AuditLogRulesInfo mAuditLogRulesInfo = new AuditLogRulesInfo();
    public long mBegin;
    public Context mContext;
    public List mDeviceInfo;
    public Filter mDumpFilter;
    public EdmStorageProvider mEdmStorageProvider;
    public long mEnd;
    public volatile boolean mIsDumping;
    public boolean mIsProfileOwnerOfOrganizationOwnedDevice;
    public boolean mIsPseudoAdminOfOrganizationOwnedDevice;
    public LogWritter mLogWritter;
    public boolean mMdmLogging;
    public String mPackageName;
    public int mUid;

    public Admin(int i, Context context, String str) {
        this.mPackageName = null;
        this.mPackageName = str;
        this.mLogWritter = new LogWritter(i, context, str);
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mLogWritter.setObserver(this);
        this.mIsDumping = false;
        this.mMdmLogging = false;
        this.mDumpFilter = null;
        this.mContext = context;
        this.mBegin = 0L;
        this.mEnd = 0L;
        this.mUid = i;
        Log.d("Admin", "Admin uid = " + i);
        try {
            this.mIsPseudoAdminOfOrganizationOwnedDevice = this.mEdmStorageProvider.checkPseudoAdminForUid(i);
            Log.d("Admin", "mIsPseudoAdminOfOrganizationOwnedDevice = " + this.mIsPseudoAdminOfOrganizationOwnedDevice);
        } catch (SettingNotFoundException e) {
            Log.e("Admin", "mEdmStorageProvider.checkPseudoAdminForUid: error " + e.getMessage());
        }
        IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface != null) {
            try {
                this.mIsProfileOwnerOfOrganizationOwnedDevice = asInterface.isProfileOwnerOfOrganizationOwnedDeviceMDM(UserHandle.getUserId(i));
                Log.d("Admin", "mIsProfileOwnerOfOrganizationOwnedDevice = " + this.mIsProfileOwnerOfOrganizationOwnedDevice);
            } catch (RemoteException e2) {
                Log.e("Admin", "Error on calling isProfileOwnerOfOrganizationOwnedDeviceMDM : " + e2.getMessage());
            }
        }
    }

    public void write(String str) {
        this.mLogWritter.write(str);
    }

    public void setDeviceInfo(List list) {
        this.mDeviceInfo = list;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public boolean dump(long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
        if (this.mIsDumping) {
            return false;
        }
        this.mIsDumping = true;
        this.mBegin = j;
        this.mEnd = j2;
        this.mAdminOutputFile = parcelFileDescriptor;
        this.mLogWritter.setObserver(this);
        this.mLogWritter.setIsDumping(true, false);
        this.mLogWritter.swapFiles("swap");
        return this.mIsDumping;
    }

    public void setCriticalLogSize(int i) {
        this.mLogWritter.setCriticalLogSize(i);
    }

    public int getCriticalLogSize() {
        return this.mLogWritter.getCriticalLogSize();
    }

    public void setMaximumLogSize(int i) {
        this.mLogWritter.setMaximumLogSize(i);
    }

    public int getMaximumLogSize() {
        return this.mLogWritter.getMaximumLogSize();
    }

    public int getCurrentLogFileSize() {
        return this.mLogWritter.getCurrentLogFileSize();
    }

    public void deleteAllFiles() {
        this.mLogWritter.deleteAllFiles();
    }

    public void shutdown() {
        this.mLogWritter.shutdown();
    }

    public void setMDMLogging(boolean z) {
        this.mMdmLogging = z;
    }

    public void setBootCompleted(boolean z) {
        this.mLogWritter.setBootCompleted(z);
    }

    public int getUid() {
        return this.mUid;
    }

    public void setBufferSize(long j) {
        this.mLogWritter.setBufferLogSize(j);
    }

    public void createBubbleDirectory() {
        this.mLogWritter.createBubbleDirectory();
    }

    public void createBubbleFile() {
        this.mLogWritter.createBubbleFile();
    }

    public boolean isPseudoAdminOfOrganizationOwnedDevice() {
        return this.mIsPseudoAdminOfOrganizationOwnedDevice;
    }

    public boolean isProfileOwnerOfOrganizationOwnedDevice() {
        return this.mIsProfileOwnerOfOrganizationOwnedDevice;
    }

    @Override // com.android.server.enterprise.auditlog.IObserver
    public void notifyReadyToDump(boolean z) {
        ArrayList arrayList;
        if (!z || (arrayList = (ArrayList) this.mLogWritter.getDumpFilesList()) == null || ((PartialFileNode) arrayList.get(0)).getFile() == null) {
            return;
        }
        Dumper dumper = new Dumper(this.mBegin, this.mEnd, this.mAdminOutputFile, arrayList, this);
        Filter filter = this.mDumpFilter;
        if (filter != null) {
            dumper.setFilter(filter);
        }
        dumper.setDeviceInfo(this.mDeviceInfo);
        dumper.setPackageName(this.mPackageName);
        dumper.start();
        this.mLogWritter.setLastTimestamp();
    }

    @Override // com.android.server.enterprise.auditlog.IObserver
    public void notifyDumpFinished(boolean z, boolean z2) {
        EnterpriseDeviceManagerService enterpriseDeviceManagerService;
        this.mIsDumping = false;
        this.mLogWritter.setTypeOfDump(z2);
        this.mLogWritter.setIsDumping(false, z);
        int i = this.mUid;
        if (((IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class)).isLegacyContainer(UserHandle.getUserId(this.mUid))) {
            i = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, this.mUid);
        }
        int userId = UserHandle.getUserId(i);
        if (this.mIsPseudoAdminOfOrganizationOwnedDevice && (enterpriseDeviceManagerService = EnterpriseDeviceManagerService.getInstance()) != null) {
            userId = enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId();
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.DUMP_LOG_RESULT");
        String str = this.mPackageName;
        if (str != null) {
            intent.setPackage(str);
        }
        if (z) {
            intent.putExtra("com.samsung.android.knox.intent.extra.AUDIT_RESULT", 0);
        } else {
            InformFailure.getInstance().broadcastFailure("Dump failed! Sending Intent!", this.mPackageName);
            intent.putExtra("com.samsung.android.knox.intent.extra.AUDIT_RESULT", -2000);
        }
        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(userId), "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        try {
            String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
            Intent intent2 = new Intent(intent);
            intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
            if (this.mPackageName != null) {
                intent2.setPackage(kpuPackageName);
            }
            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(userId), "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getDumpState() {
        return this.mIsDumping;
    }

    public void setAuditLogRulesInfo(AuditLogRulesInfo auditLogRulesInfo) {
        this.mAuditLogRulesInfo = auditLogRulesInfo;
    }

    public AuditLogRulesInfo getAuditLogRulesInfo() {
        return this.mAuditLogRulesInfo;
    }

    public boolean setFilter(String str) {
        if (str != null) {
            Filter filter = new Filter();
            this.mDumpFilter = filter;
            return filter.setFilter(str);
        }
        this.mDumpFilter = null;
        return true;
    }
}
