package com.android.server.knox.dar.ddar.core;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarCache;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.ddar.DualDARPolicy;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class DualDarDoManagerImpl {
  public final DarManagerService.Injector mInjector;
  public boolean mHasTokenSetForInner = false;
  public List mNonClearablePackages =
      new ArrayList(
          Arrays.asList(
              "com.android.settings",
              "com.google.android.gms",
              "com.samsung.android.knox.containercore",
              "com.google.android.providers.media.module",
              "com.google.android.apps.work.clouddpc",
              KnoxCustomManagerService.SBROWSER_CSC_PACKAGE_NAME,
              "com.google.android.webview"));

  public DualDarDoManagerImpl(DarManagerService.Injector injector) {
    this.mInjector = injector;
  }

  public boolean setDualDarInfo(int i, int i2) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("setDualDarInfo");
    if (this.mInjector.getUserManagerInternal() != null) {
      return this.mInjector.getUserManagerInternal().setDualDarInfo(i, i2);
    }
    return false;
  }

  public void setInnerAuthUserId(int i, int i2) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("setInnerAuthUserId");
    this.mInjector.getDarDatabaseCache().putInt(i2, "ddar.inner.auth.userid", i);
  }

  public int getInnerAuthUserId(int i) {
    return this.mInjector.getDarDatabaseCache().getInt(i, "ddar.inner.auth.userid", -10000);
  }

  public void setMainUserId(int i, int i2) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("setMainUserId");
    this.mInjector.getDarDatabaseCache().putInt(i2, "ddar.inner.main.userid", i);
  }

  public int getMainUserId(int i) {
    return this.mInjector.getDarDatabaseCache().getInt(i, "ddar.inner.main.userid", -10000);
  }

  public void addBlockedClearablePackages(int i, String str) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("addBlockedClearablePackages");
    String string =
        this.mInjector.getDarDatabaseCache().getString(i, "pkg_blocked_clearable", "empty");
    if (!string.equals("empty")) {
      this.mInjector
          .getDarDatabaseCache()
          .putString(i, "pkg_blocked_clearable", string + "," + str);
      return;
    }
    this.mInjector.getDarDatabaseCache().putString(i, "pkg_blocked_clearable", str);
  }

  public List getBlockedClearablePackages(int i) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("getBlockedClearablePackages");
    ArrayList arrayList = new ArrayList();
    String string =
        this.mInjector.getDarDatabaseCache().getString(i, "pkg_blocked_clearable", "empty");
    if (!string.equals("empty")) {
      arrayList.addAll(Arrays.asList(string.split(",")));
    }
    arrayList.addAll(this.mNonClearablePackages);
    return arrayList;
  }

  public List getPackageListForDualDarPolicy(String str) {
    this.mInjector.enforceCallerKnoxCoreOrSelf("getPackageListForDualDarPolicy");
    try {
      IPackageManager packageManager = AppGlobals.getPackageManager();
      if (packageManager != null) {
        return packageManager.getPackageListForDualDarPolicy(str);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList();
  }

  public boolean isInnerAuthRequired(int i) {
    if (!DualDarManager.isOnDeviceOwner(i)) {
      return false;
    }
    if (isInnerLayerLockedState()) {
      return true;
    }
    if (!this.mHasTokenSetForInner) {
      return false;
    }
    if (this.mInjector.getLockPatternUtils().hasPendingEscrowToken(getInnerAuthUserId(i))) {
      return true;
    }
    this.mHasTokenSetForInner = false;
    return false;
  }

  public final boolean isInnerLayerLockedState() {
    boolean dualDARLockstate =
        EnterprisePartitionManager.getInstance(this.mInjector.getContext()).getDualDARLockstate();
    Log.d("DualDarManagerImpl", "isInnerLayerLockedState - result : " + dualDARLockstate);
    return dualDARLockstate;
  }

  public void onSetResetPasswordToken(int i) {
    if (DualDarManager.isOnDeviceOwnerEnabled()
        && getInnerAuthUserId(0) == i
        && !this.mHasTokenSetForInner) {
      this.mHasTokenSetForInner = true;
      sendDpmStateChanged();
    }
  }

  public void onEscrowTokenActivated(long j, int i) {
    if (DualDarManager.isOnDeviceOwnerEnabled() && getInnerAuthUserId(0) == i) {
      Log.d(
          "DualDarManagerImpl",
          String.format("Token(%x) activated for user %d", Long.valueOf(j), Integer.valueOf(i)));
      if (this.mHasTokenSetForInner) {
        this.mHasTokenSetForInner = false;
        sendDpmStateChanged();
      }
    }
  }

  public void onClearResetPasswordToken(int i) {
    if (DualDarManager.isOnDeviceOwnerEnabled()
        && getInnerAuthUserId(0) == i
        && this.mHasTokenSetForInner) {
      this.mHasTokenSetForInner = false;
      sendDpmStateChanged();
    }
  }

  public int getPasswordMinimumLengthForInner() {
    if (DualDarManager.isOnDeviceOwnerEnabled()) {
      return ((Integer)
              this.mInjector
                  .getDualDARPolicyService()
                  .map(
                      new Function() { // from class:
                                       // com.android.server.knox.dar.ddar.core.DualDarDoManagerImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          Integer lambda$getPasswordMinimumLengthForInner$0;
                          lambda$getPasswordMinimumLengthForInner$0 =
                              DualDarDoManagerImpl.lambda$getPasswordMinimumLengthForInner$0(
                                  (IDualDARPolicy) obj);
                          return lambda$getPasswordMinimumLengthForInner$0;
                        }
                      })
                  .orElse(0))
          .intValue();
    }
    return 0;
  }

  public static /* synthetic */ Integer lambda$getPasswordMinimumLengthForInner$0(
      IDualDARPolicy iDualDARPolicy) {
    try {
      return Integer.valueOf(iDualDARPolicy.getPasswordMinimumLengthForInner(new ContextInfo()));
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public final void sendDpmStateChanged() {
    Binder.withCleanCallingIdentity(
        new FunctionalUtils
            .ThrowingRunnable() { // from class:
                                  // com.android.server.knox.dar.ddar.core.DualDarDoManagerImpl$$ExternalSyntheticLambda1
          public final void runOrThrow() {
            DualDarDoManagerImpl.this.lambda$sendDpmStateChanged$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendDpmStateChanged$1() {
    Intent intent = new Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
    intent.setFlags(1073741824);
    this.mInjector.getContext().sendBroadcast(intent);
  }

  public void dump(
      Context context, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    String str;
    IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
    indentingPrintWriter.println("DUALDAR Supported Version: " + DualDARPolicy.getDualDARVersion());
    boolean isDeviceOwner = this.mInjector.getLockPatternUtils().isDeviceOwner(0);
    boolean isOnDeviceOwnerEnabled = DualDarManager.isOnDeviceOwnerEnabled();
    int dualDARUser = PersonaServiceHelper.getDualDARUser();
    if (dualDARUser != -1) {
      indentingPrintWriter.println("DUALDAR user: " + dualDARUser);
      indentingPrintWriter.print("DUALDAR TYPE: ");
      int dualDARType = PersonaServiceHelper.getDualDARType(dualDARUser);
      if (dualDARType == -1) {
        indentingPrintWriter.println("DUALDAR_TYPE_NONE");
      } else if (dualDARType == 0) {
        indentingPrintWriter.println("DUALDAR_TYPE_PO_ONLY");
      } else if (dualDARType == 1) {
        indentingPrintWriter.println("DUALDAR_TYPE_DO_PO");
      } else if (dualDARType == 2) {
        indentingPrintWriter.println("DUALDAR_TYPE_DO_ONLY");
      }
      indentingPrintWriter.print("DUALDAR CRYPTO TYPE: ");
      if (SemPersonaManager.isDualDARNativeCrypto(dualDARUser)) {
        indentingPrintWriter.println("Native");
      } else {
        indentingPrintWriter.println(
            "Custom - "
                + DualDarCache.getInstance(context).get(dualDARUser, "KEY_CLIENT_PACKAGE_NAME"));
      }
      DevicePolicyManager devicePolicyManager =
          (DevicePolicyManager) context.getSystemService("device_policy");
      if (devicePolicyManager == null) {
        str = "";
      } else if (dualDARUser == 0) {
        str = devicePolicyManager.getDeviceOwner();
      } else {
        str = devicePolicyManager.getProfileOwnerNameAsUser(dualDARUser);
      }
      indentingPrintWriter.println("Admin Package: " + str);
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.println(
          "isDERestrictionEnforced: " + PersonaServiceHelper.isDERestrictionEnforced(dualDARUser));
      if (dualDARUser != 0) {
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(
            "Credential Type: "
                + this.mInjector.getLockPatternUtils().getCredentialTypeForUser(dualDARUser));
        indentingPrintWriter.decreaseIndent();
      }
      indentingPrintWriter.decreaseIndent();
    }
    if (isDeviceOwner) {
      indentingPrintWriter.println("");
      indentingPrintWriter.println("Device Owner enabled");
      StringBuilder sb = new StringBuilder();
      sb.append("DualDAR at DO: ");
      sb.append(isOnDeviceOwnerEnabled ? "Enabled" : "Not enabled");
      indentingPrintWriter.println(sb.toString());
    }
    if (isOnDeviceOwnerEnabled) {
      indentingPrintWriter.println("");
      indentingPrintWriter.println(
          "Data Lock Timeout : " + PersonaServiceHelper.isDataLockTimeoutEnabled(0));
      indentingPrintWriter.increaseIndent();
      State currentState = StateMachine.getCurrentState(0);
      if (currentState != null) {
        indentingPrintWriter.println("State - " + currentState.toString());
      }
      indentingPrintWriter.println("Outer Auth User 0");
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.println(
          "Credential Type: "
              + DarUtil.credentialTypeToString(
                  this.mInjector.getLockPatternUtils().getCredentialTypeForUser(0)));
      indentingPrintWriter.decreaseIndent();
      int innerAuthUserId = getInnerAuthUserId(0);
      int credentialTypeForUser =
          this.mInjector.getLockPatternUtils().getCredentialTypeForUser(innerAuthUserId);
      indentingPrintWriter.println("Inner Auth User " + innerAuthUserId);
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.println(
          "Credential Type: " + DarUtil.credentialTypeToString(credentialTypeForUser));
      indentingPrintWriter.println("Has Token Set: " + this.mHasTokenSetForInner);
      indentingPrintWriter.println(
          "Password Minimum Length: " + getPasswordMinimumLengthForInner());
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.println();
  }
}
