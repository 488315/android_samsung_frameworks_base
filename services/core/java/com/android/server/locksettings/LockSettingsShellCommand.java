package com.android.server.locksettings;

import android.app.ActivityManager;
import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes2.dex */
public class LockSettingsShellCommand extends ShellCommand {
  public IRemoteCallback mCallback;
  public final int mCallingPid;
  public final int mCallingUid;
  public final Context mContext;
  public int mCurrentUserId;
  public final LockPatternUtils mLockPatternUtils;
  public String mOld = "";
  public String mNew = "";
  public String mType = "";

  public LockSettingsShellCommand(
      LockPatternUtils lockPatternUtils, Context context, int i, int i2) {
    this.mLockPatternUtils = lockPatternUtils;
    this.mCallingPid = i;
    this.mCallingUid = i2;
    this.mContext = context;
  }

  /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
  public int onCommand(String str) {
    char c;
    char c2;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    try {
      this.mCurrentUserId = ActivityManager.getService().getCurrentUser().id;
      parseArgs();
      char c3 = 3;
      boolean z = true;
      if (!this.mLockPatternUtils.hasSecureLockScreen()) {
        switch (str.hashCode()) {
          case -1473704173:
            if (str.equals("get-disabled")) {
              c2 = 1;
              break;
            }
            c2 = 65535;
            break;
          case 3198785:
            if (str.equals("help")) {
              c2 = 0;
              break;
            }
            c2 = 65535;
            break;
          case 75288455:
            if (str.equals("set-disabled")) {
              c2 = 2;
              break;
            }
            c2 = 65535;
            break;
          case 1062640281:
            if (str.equals("set-resume-on-reboot-provider-package")) {
              c2 = 3;
              break;
            }
            c2 = 65535;
            break;
          default:
            c2 = 65535;
            break;
        }
        if (c2 != 0 && c2 != 1 && c2 != 2 && c2 != 3) {
          getErrPrintWriter()
              .println("The device does not support lock screen - ignoring the command.");
          return -1;
        }
      }
      switch (str.hashCode()) {
        case -1957541639:
          if (str.equals("remove-cache")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case -1473704173:
          if (str.equals("get-disabled")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        case -868666698:
          if (str.equals("require-strong-auth")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 3198785:
          if (str.equals("help")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case 75288455:
          if (str.equals("set-disabled")) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case 1062640281:
          if (str.equals("set-resume-on-reboot-provider-package")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      if (c == 0) {
        runRemoveCache();
        return 0;
      }
      if (c == 1) {
        runSetResumeOnRebootProviderPackage();
        return 0;
      }
      if (c == 2) {
        runRequireStrongAuth();
        return 0;
      }
      if (c == 3) {
        onHelp();
        return 0;
      }
      if (c == 4) {
        runGetDisabled();
        return 0;
      }
      if (c == 5) {
        runSetDisabled();
        return 0;
      }
      if (!checkCredential()) {
        return -1;
      }
      switch (str.hashCode()) {
        case -2044327643:
          if (str.equals("set-pattern")) {
            c3 = 0;
            break;
          }
          c3 = 65535;
          break;
        case -1473704173:
          if (str.equals("get-disabled")) {
            c3 = 5;
            break;
          }
          c3 = 65535;
          break;
        case -840442044:
          if (str.equals("unlock")) {
            c3 = 7;
            break;
          }
          c3 = 65535;
          break;
        case -819951495:
          if (str.equals("verify")) {
            c3 = 4;
            break;
          }
          c3 = 65535;
          break;
        case -48642004:
          if (str.equals("reconnect-pf")) {
            c3 = '\t';
            break;
          }
          c3 = 65535;
          break;
        case 3135262:
          if (str.equals("fail")) {
            c3 = '\b';
            break;
          }
          c3 = 65535;
          break;
        case 3327275:
          if (str.equals("lock")) {
            c3 = 6;
            break;
          }
          c3 = 65535;
          break;
        case 94746189:
          if (str.equals("clear")) {
            break;
          }
          c3 = 65535;
          break;
        case 1021333414:
          if (str.equals("set-password")) {
            c3 = 1;
            break;
          }
          c3 = 65535;
          break;
        case 1983832490:
          if (str.equals("set-pin")) {
            c3 = 2;
            break;
          }
          c3 = 65535;
          break;
        default:
          c3 = 65535;
          break;
      }
      switch (c3) {
        case 0:
          z = runSetPattern();
          break;
        case 1:
          z = runSetPassword();
          break;
        case 2:
          z = runSetPin();
          break;
        case 3:
          z = runClear();
          break;
        case 4:
          runVerify();
          break;
        case 5:
          runGetDisabled();
          break;
        case 6:
          runLock();
          break;
        case 7:
          runUnlock();
          break;
        case '\b':
          runFailToUnlock();
          break;
        case '\t':
          runReconnectPluginFaceWidget();
          break;
        default:
          getErrPrintWriter().println("Unknown command: " + str);
          break;
      }
      return z ? 0 : -1;
    } catch (Exception e) {
      getErrPrintWriter().println("Error while executing command: " + str);
      e.printStackTrace(getErrPrintWriter());
      return -1;
    }
  }

  public final void runVerify() {
    getOutPrintWriter().println("Lock credential verified successfully");
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    try {
      outPrintWriter.println("lockSettings service commands:");
      outPrintWriter.println("");
      outPrintWriter.println(
          "NOTE: when lock screen is set, all commands require the --old <CREDENTIAL> argument.");
      outPrintWriter.println("");
      outPrintWriter.println("  help");
      outPrintWriter.println("    Prints this help text.");
      outPrintWriter.println("");
      outPrintWriter.println("  get-disabled [--old <CREDENTIAL>] [--user USER_ID]");
      outPrintWriter.println("    Checks whether lock screen is disabled.");
      outPrintWriter.println("");
      outPrintWriter.println("  set-disabled [--old <CREDENTIAL>] [--user USER_ID] <true|false>");
      outPrintWriter.println("    When true, disables lock screen.");
      outPrintWriter.println("");
      outPrintWriter.println("  set-pattern [--old <CREDENTIAL>] [--user USER_ID] <PATTERN>");
      outPrintWriter.println(
          "    Sets the lock screen as pattern, using the given PATTERN to unlock.");
      outPrintWriter.println("");
      outPrintWriter.println("  set-pin [--old <CREDENTIAL>] [--user USER_ID] <PIN>");
      outPrintWriter.println("    Sets the lock screen as PIN, using the given PIN to unlock.");
      outPrintWriter.println("");
      outPrintWriter.println("  set-password [--old <CREDENTIAL>] [--user USER_ID] <PASSWORD>");
      outPrintWriter.println(
          "    Sets the lock screen as password, using the given PASSOWRD to unlock.");
      outPrintWriter.println("");
      outPrintWriter.println("  clear [--old <CREDENTIAL>] [--user USER_ID]");
      outPrintWriter.println("    Clears the lock credentials.");
      outPrintWriter.println("");
      outPrintWriter.println("  verify [--old <CREDENTIAL>] [--user USER_ID]");
      outPrintWriter.println("    Verifies the lock credentials.");
      outPrintWriter.println("");
      outPrintWriter.println("  remove-cache [--user USER_ID]");
      outPrintWriter.println("    Removes cached unified challenge for the managed profile.");
      outPrintWriter.println("");
      outPrintWriter.println("  set-resume-on-reboot-provider-package <package_name>");
      outPrintWriter.println(
          "    Sets the package name for server based resume on reboot service provider.");
      outPrintWriter.println("");
      outPrintWriter.println("  require-strong-auth [--user USER_ID] <reason>");
      outPrintWriter.println(
          "    Requires the strong authentication. The current supported reasons:"
              + " STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN.");
      outPrintWriter.println("");
      outPrintWriter.close();
    } catch (Throwable th) {
      if (outPrintWriter != null) {
        try {
          outPrintWriter.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public final void parseArgs() {
    while (true) {
      String nextOption = getNextOption();
      if (nextOption != null) {
        if ("--old".equals(nextOption)) {
          this.mOld = getNextArgRequired();
        } else if ("--user".equals(nextOption)) {
          int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
          this.mCurrentUserId = parseUserArg;
          if (parseUserArg == -2) {
            this.mCurrentUserId = ActivityManager.getCurrentUser();
          }
        } else if ("--type".equals(nextOption)) {
          this.mType = getNextArgRequired();
        } else {
          getErrPrintWriter().println("Unknown option: " + nextOption);
          throw new IllegalArgumentException();
        }
      } else {
        this.mNew = getNextArg();
        return;
      }
    }
  }

  public final LockscreenCredential getOldCredential() {
    if (TextUtils.isEmpty(this.mOld)) {
      return LockscreenCredential.createNone();
    }
    if (this.mLockPatternUtils.isLockPasswordEnabled(this.mCurrentUserId)) {
      if (LockPatternUtils.isQualityAlphabeticPassword(
          this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mCurrentUserId))) {
        return LockscreenCredential.createPassword(this.mOld);
      }
      return LockscreenCredential.createPin(this.mOld);
    }
    if (this.mLockPatternUtils.isLockPatternEnabled(this.mCurrentUserId)) {
      return LockscreenCredential.createPattern(
          LockPatternUtils.byteArrayToPattern(this.mOld.getBytes()));
    }
    return LockscreenCredential.createPassword(this.mOld);
  }

  public final boolean runSetPattern() {
    LockscreenCredential createPattern =
        LockscreenCredential.createPattern(
            LockPatternUtils.byteArrayToPattern(this.mNew.getBytes()));
    if (!isNewCredentialSufficient(createPattern)) {
      return false;
    }
    this.mLockPatternUtils.setLockCredential(
        createPattern, getOldCredential(), this.mCurrentUserId);
    getOutPrintWriter().println("Pattern set to '" + this.mNew + "'");
    return true;
  }

  public final boolean runSetPassword() {
    LockscreenCredential createPassword = LockscreenCredential.createPassword(this.mNew);
    if (!isNewCredentialSufficient(createPassword)) {
      return false;
    }
    this.mLockPatternUtils.setLockCredential(
        createPassword, getOldCredential(), this.mCurrentUserId);
    getOutPrintWriter().println("Password set to '" + this.mNew + "'");
    return true;
  }

  public final boolean runSetPin() {
    LockscreenCredential createPin = LockscreenCredential.createPin(this.mNew);
    if (!isNewCredentialSufficient(createPin)) {
      return false;
    }
    this.mLockPatternUtils.setLockCredential(createPin, getOldCredential(), this.mCurrentUserId);
    getOutPrintWriter().println("Pin set to '" + this.mNew + "'");
    return true;
  }

  public final boolean runSetResumeOnRebootProviderPackage() {
    String str = this.mNew;
    Slog.i("ShellCommand", "Setting persist.sys.resume_on_reboot_provider_package to " + str);
    this.mContext.enforcePermission(
        "android.permission.BIND_RESUME_ON_REBOOT_SERVICE",
        this.mCallingPid,
        this.mCallingUid,
        "ShellCommand");
    SystemProperties.set("persist.sys.resume_on_reboot_provider_package", str);
    return true;
  }

  public final boolean runRequireStrongAuth() {
    String str = this.mNew;
    str.hashCode();
    if (str.equals("STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN")) {
      this.mCurrentUserId = -1;
      this.mLockPatternUtils.requireStrongAuth(32, -1);
      getOutPrintWriter()
          .println(
              "Require strong auth for USER_ID "
                  + this.mCurrentUserId
                  + " because of "
                  + this.mNew);
      return true;
    }
    getErrPrintWriter().println("Unsupported reason: " + str);
    return false;
  }

  public final boolean runClear() {
    LockscreenCredential createNone = LockscreenCredential.createNone();
    if (!isNewCredentialSufficient(createNone)) {
      return false;
    }
    this.mLockPatternUtils.setLockCredential(createNone, getOldCredential(), this.mCurrentUserId);
    getOutPrintWriter().println("Lock credential cleared");
    return true;
  }

  public final boolean isNewCredentialSufficient(LockscreenCredential lockscreenCredential) {
    List validatePassword;
    PasswordMetrics requestedPasswordMetrics =
        this.mLockPatternUtils.getRequestedPasswordMetrics(this.mCurrentUserId);
    int requestedPasswordComplexity =
        this.mLockPatternUtils.getRequestedPasswordComplexity(this.mCurrentUserId);
    if (lockscreenCredential.isPassword() || lockscreenCredential.isPin()) {
      validatePassword =
          PasswordMetrics.validatePassword(
              requestedPasswordMetrics,
              requestedPasswordComplexity,
              lockscreenCredential.isPin(),
              lockscreenCredential.getCredential());
    } else {
      validatePassword =
          PasswordMetrics.validatePasswordMetrics(
              requestedPasswordMetrics,
              requestedPasswordComplexity,
              new PasswordMetrics(lockscreenCredential.isPattern() ? 1 : -1));
    }
    if (!validatePassword.isEmpty()) {
      getOutPrintWriter()
          .println("New credential doesn't satisfy admin policies: " + validatePassword.get(0));
      return false;
    }
    if (lockscreenCredential.isNone()) {
      if (isMultifactorAuthEnforced(this.mCurrentUserId)) {
        getOutPrintWriter()
            .println(
                "New credential doesn't satisfy admin policies: password null does not meet"
                    + " multi-factor auth enforced");
        return false;
      }
      if (DualDarManager.isOnDeviceOwnerEnabled()) {
        getOutPrintWriter()
            .println(
                "New credential doesn't satisfy admin policies: password null does not meet DualDAR"
                    + " on Device Owner Enabled");
        return false;
      }
    }
    return true;
  }

  public final void runSetDisabled() {
    boolean parseBoolean = Boolean.parseBoolean(this.mNew);
    this.mLockPatternUtils.setLockScreenDisabled(parseBoolean, this.mCurrentUserId);
    getOutPrintWriter().println("Lock screen disabled set to " + parseBoolean);
  }

  public final void runGetDisabled() {
    getOutPrintWriter().println(this.mLockPatternUtils.isLockScreenDisabled(this.mCurrentUserId));
  }

  public final boolean checkCredential() {
    if (this.mLockPatternUtils.getCredentialTypeForUser(this.mCurrentUserId) != -1) {
      if (this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(this.mCurrentUserId)) {
        getOutPrintWriter().println("Profile uses unified challenge");
        return false;
      }
      try {
        boolean checkCredential =
            this.mLockPatternUtils.checkCredential(
                getOldCredential(),
                this.mCurrentUserId,
                (LockPatternUtils.CheckCredentialProgressCallback) null);
        if (!checkCredential) {
          if (!this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(this.mCurrentUserId)) {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mCurrentUserId);
          }
          getOutPrintWriter().println("Old password '" + this.mOld + "' didn't match");
        } else {
          this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mCurrentUserId);
        }
        return checkCredential;
      } catch (LockPatternUtils.RequestThrottledException unused) {
        getOutPrintWriter().println("Request throttled");
        return false;
      }
    }
    if (this.mOld.isEmpty()) {
      return true;
    }
    getOutPrintWriter().println("Old password provided but user has no password");
    return false;
  }

  public final void runRemoveCache() {
    this.mLockPatternUtils.removeCachedUnifiedChallenge(this.mCurrentUserId);
    getOutPrintWriter().println("Password cached removed for user " + this.mCurrentUserId);
  }

  public LockSettingsShellCommand setCallback(IRemoteCallback iRemoteCallback) {
    this.mCallback = iRemoteCallback;
    return this;
  }

  public final void sendCommand(String str, String str2) {
    if (this.mCallback == null) {
      getErrPrintWriter().println("Not supported command: " + str);
      return;
    }
    if (isShipBuild()) {
      getErrPrintWriter().println("Not supported ship build");
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString(KnoxVpnFirewallHelper.CMD, str);
    bundle.putString("type", str2);
    try {
      this.mCallback.sendResult(bundle);
    } catch (RemoteException unused) {
      getErrPrintWriter().println("Failed command: " + str);
    }
  }

  public final void runUnlock() {
    if (!this.mType.equals("finger") && !this.mType.equals("face") && !this.mType.equals("")) {
      getErrPrintWriter().println("Unknown unlock type: " + this.mType);
      return;
    }
    sendCommand("unlock", this.mType);
  }

  public final void runLock() {
    if (TextUtils.isEmpty(this.mType)) {
      getErrPrintWriter().println("Please add lock type with --type option");
    } else {
      sendCommand("lock", this.mType);
    }
  }

  public final void runFailToUnlock() {
    if (!this.mType.equals("finger") && !this.mType.equals("face") && !this.mType.equals("")) {
      getErrPrintWriter().println("Unknown unlock type: " + this.mType);
      return;
    }
    sendCommand("fail", this.mType);
  }

  public final void runReconnectPluginFaceWidget() {
    sendCommand("reconnect-pf", "");
  }

  public final boolean isShipBuild() {
    return "true".equals(SystemProperties.get("ro.product_ship", "false"));
  }

  public final boolean isMultifactorAuthEnforced(int i) {
    KnoxContainerManager knoxContainerManager;
    try {
      if (i == 0) {
        EnterpriseDeviceManager enterpriseDeviceManager =
            EnterpriseDeviceManager.getInstance(this.mContext);
        if (enterpriseDeviceManager != null) {
          return enterpriseDeviceManager.getPasswordPolicy().isMultifactorAuthenticationEnabled();
        }
        return false;
      }
      EnterpriseKnoxManager enterpriseKnoxManager = EnterpriseKnoxManager.getInstance();
      if (enterpriseKnoxManager == null
          || (knoxContainerManager =
                  enterpriseKnoxManager.getKnoxContainerManager(this.mContext, i))
              == null) {
        return false;
      }
      return knoxContainerManager.getPasswordPolicy().isMultifactorAuthenticationEnabled();
    } catch (SecurityException e) {
      getErrPrintWriter().println("Error while check isMultifactorAuthEnforced");
      Slog.d("ShellCommand", "SecurityException : " + e);
      return false;
    }
  }
}
