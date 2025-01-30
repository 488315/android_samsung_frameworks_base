package com.android.server.enterprise.profile;

import android.app.admin.DevicePolicyManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.DumpUtils;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.profile.IProfilePolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class ProfilePolicyService extends IProfilePolicy.Stub implements EnterpriseServiceCallback {
  public Context mContext;
  public DevicePolicyManager mDevicePolicyManager;
  public EnterpriseDeviceManager mEDM = null;
  public EdmStorageProvider mEdmStorageProvider;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public ProfilePolicyService(Context context) {
    this.mEdmStorageProvider = null;
    this.mDevicePolicyManager = null;
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
  }

  public boolean setRestrictionPolicy(ContextInfo contextInfo, String str, boolean z) {
    boolean putValuesNoUpdate;
    Log.d("ProfilePolicyService", "setRestrictionPolicy");
    if (contextInfo == null || str == null) {
      Log.e("ProfilePolicyService", "bad arguments. Cannnot be null");
      return false;
    }
    ContextInfo enforceSecurityPermission =
        enforceSecurityPermission(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      List<UserHandle> policyManagedProfiles =
          this.mDevicePolicyManager.getPolicyManagedProfiles(new UserHandle(0));
      int i = enforceSecurityPermission.mCallerUid;
      if (i == 1000) {
        int i2 = -1;
        for (UserHandle userHandle : policyManagedProfiles) {
          String str2 = "";
          try {
            str2 =
                this.mDevicePolicyManager
                    .getProfileOwnerAsUser(new UserHandle(userHandle.getIdentifier()))
                    .getPackageName();
            i2 =
                this.mContext
                    .getPackageManager()
                    .getPackageUidAsUser(str2, userHandle.getIdentifier());
          } catch (PackageManager.NameNotFoundException e) {
            Log.e("ProfilePolicyService", "Invalid admin package name : " + str2 + ". " + e);
          }
        }
        i = i2;
      }
      if (i == -1) {
        Log.e("ProfilePolicyService", "Invalid admin UID");
        return false;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      ContentValues contentValues = new ContentValues();
      contentValues.put("adminUid", Integer.valueOf(enforceSecurityPermission.mCallerUid));
      contentValues.put("ProfilePolicyPORestrictionsProperty", str);
      contentValues.put("adminUid", Integer.valueOf(i));
      if (this.mEdmStorageProvider.getCount("ProfilePolicyPORestrictions", contentValues) > 0) {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("ProfilePolicyPORestrictionsValue", Integer.valueOf(z ? 1 : 0));
        putValuesNoUpdate =
            this.mEdmStorageProvider.putValues(
                "ProfilePolicyPORestrictions", contentValues2, contentValues);
      } else {
        contentValues.put("ProfilePolicyPORestrictionsValue", Integer.valueOf(z ? 1 : 0));
        putValuesNoUpdate =
            this.mEdmStorageProvider.putValuesNoUpdate(
                "ProfilePolicyPORestrictions", contentValues);
      }
      if (!putValuesNoUpdate) {
        Log.e("ProfilePolicyService", "setRestrictionPolicy failed.");
      } else {
        sendPolicyUpdateIntent(str, z ? 1 : 0, contextInfo.mContainerId);
      }
      Log.d("ProfilePolicyService", "setRestrictionPolicy result : " + putValuesNoUpdate);
      return putValuesNoUpdate;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean getRestrictionPolicy(ContextInfo contextInfo, String str) {
    Log.d("ProfilePolicyService", "getRestrictionPolicy");
    if (str == null) {
      Log.e("ProfilePolicyService", "bad arguments. Cannnot be null");
      return false;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("ProfilePolicyPORestrictionsProperty", str);
    return this.mEdmStorageProvider.getInt(
            "ProfilePolicyPORestrictions", "ProfilePolicyPORestrictionsValue", contentValues)
        == 1;
  }

  public final void sendPolicyUpdateIntent(String str, int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Intent intent = new Intent("com.samsung.android.knox.profilepolicy.intent.action.update");
      intent.setFlags(1073741824);
      intent.putExtra("restrictionName", str);
      intent.putExtra("restrictionAllowed", i);
      intent.putExtra(KnoxCustomManagerService.CONTAINER_ID_ZERO, i2);
      this.mContext.sendBroadcastAsUser(intent, new UserHandle(-1));
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo, List list) {
    return EnterpriseDeviceManager.getInstance(this.mContext)
        .enforceActiveAdminPermissionByContext(contextInfo, list);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "ProfilePolicyService", printWriter)) {
      printWriter.println("ProfilePolicy : ");
      printWriter.println(
          "restriction_property_calendar_share_to_owner: "
              + getRestrictionPolicy(null, "restriction_property_calendar_share_to_owner"));
      printWriter.println(
          "restriction_property_screencapture_save_to_owner: "
              + getRestrictionPolicy(null, "restriction_property_screencapture_save_to_owner"));
      printWriter.println(
          "restriction_property_move_files_to_profile: "
              + getRestrictionPolicy(null, "restriction_property_move_files_to_profile"));
      printWriter.println(
          "restriction_property_move_files_to_owner: "
              + getRestrictionPolicy(null, "restriction_property_move_files_to_owner"));
    }
  }
}
