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
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.container.KnoxContainerManager;

import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LockSettingsShellCommand extends ShellCommand {
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

    public final boolean checkCredential() {
        if (this.mLockPatternUtils.getCredentialTypeForUser(this.mCurrentUserId) == -1) {
            if (this.mOld.isEmpty()) {
                return true;
            }
            getOutPrintWriter().println("Old password provided but user has no password");
            return false;
        }
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
            if (checkCredential) {
                this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mCurrentUserId);
            } else {
                if (!this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(
                        this.mCurrentUserId)) {
                    this.mLockPatternUtils.reportFailedPasswordAttempt(this.mCurrentUserId);
                }
                getOutPrintWriter().println("Old password '" + this.mOld + "' didn't match");
            }
            return checkCredential;
        } catch (LockPatternUtils.RequestThrottledException unused) {
            getOutPrintWriter().println("Request throttled");
            return false;
        }
    }

    public final LockscreenCredential getOldCredential() {
        return TextUtils.isEmpty(this.mOld)
                ? LockscreenCredential.createNone()
                : this.mLockPatternUtils.isLockPasswordEnabled(this.mCurrentUserId)
                        ? LockPatternUtils.isQualityAlphabeticPassword(
                                        this.mLockPatternUtils.getKeyguardStoredPasswordQuality(
                                                this.mCurrentUserId))
                                ? LockscreenCredential.createPassword(this.mOld)
                                : LockscreenCredential.createPin(this.mOld)
                        : this.mLockPatternUtils.isLockPatternEnabled(this.mCurrentUserId)
                                ? LockscreenCredential.createPattern(
                                        LockPatternUtils.byteArrayToPattern(this.mOld.getBytes()))
                                : LockscreenCredential.createPassword(this.mOld);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0085 -> B:14:0x0086). Please report as a decompilation issue!!! */
    public final boolean isNewCredentialSufficient(LockscreenCredential lockscreenCredential) {
        boolean z;
        KnoxContainerManager knoxContainerManager;
        List validateCredential =
                PasswordMetrics.validateCredential(
                        this.mLockPatternUtils.getRequestedPasswordMetrics(this.mCurrentUserId),
                        this.mLockPatternUtils.getRequestedPasswordComplexity(this.mCurrentUserId),
                        lockscreenCredential);
        if (!validateCredential.isEmpty()) {
            getOutPrintWriter()
                    .println(
                            "New credential doesn't satisfy admin policies: "
                                    + validateCredential.get(0));
            return false;
        }
        if (!lockscreenCredential.isNone()) {
            return true;
        }
        int i = this.mCurrentUserId;
        try {
        } catch (SecurityException e) {
            getErrPrintWriter().println("Error while check isMultifactorAuthEnforced");
            Slog.d("ShellCommand", "SecurityException : " + e);
        }
        if (i == 0) {
            EnterpriseDeviceManager enterpriseDeviceManager =
                    EnterpriseDeviceManager.getInstance(this.mContext);
            if (enterpriseDeviceManager != null) {
                z =
                        enterpriseDeviceManager
                                .getPasswordPolicy()
                                .isMultifactorAuthenticationEnabled();
            }
            z = false;
        } else {
            EnterpriseKnoxManager enterpriseKnoxManager = EnterpriseKnoxManager.getInstance();
            if (enterpriseKnoxManager != null
                    && (knoxContainerManager =
                                    enterpriseKnoxManager.getKnoxContainerManager(this.mContext, i))
                            != null) {
                z = knoxContainerManager.getPasswordPolicy().isMultifactorAuthenticationEnabled();
            }
            z = false;
        }
        if (!z) {
            return true;
        }
        getOutPrintWriter()
                .println(
                        "New credential doesn't satisfy admin policies: password null does not meet"
                            + " multi-factor auth enforced");
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final int onCommand(String str) {
        char c;
        boolean z;
        char c2;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            this.mCurrentUserId = ActivityManager.getService().getCurrentUser().id;
            parseArgs();
            char c3 = 2;
            boolean z2 = true;
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
                            .println(
                                    "The device does not support lock screen - ignoring the"
                                        + " command.");
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
                this.mLockPatternUtils.removeCachedUnifiedChallenge(this.mCurrentUserId);
                getOutPrintWriter()
                        .println("Password cached removed for user " + this.mCurrentUserId);
                return 0;
            }
            if (c == 1) {
                String str2 = this.mNew;
                Slog.i(
                        "ShellCommand",
                        "Setting persist.sys.resume_on_reboot_provider_package to " + str2);
                this.mContext.enforcePermission(
                        "android.permission.BIND_RESUME_ON_REBOOT_SERVICE",
                        this.mCallingPid,
                        this.mCallingUid,
                        "ShellCommand");
                SystemProperties.set("persist.sys.resume_on_reboot_provider_package", str2);
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
                getOutPrintWriter()
                        .println(this.mLockPatternUtils.isLockScreenDisabled(this.mCurrentUserId));
                return 0;
            }
            if (c == 5) {
                boolean parseBoolean = Boolean.parseBoolean(this.mNew);
                this.mLockPatternUtils.setLockScreenDisabled(parseBoolean, this.mCurrentUserId);
                getOutPrintWriter().println("Lock screen disabled set to " + parseBoolean);
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
                case -840442044:
                    if (str.equals("unlock")) {
                        c3 = 6;
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
                        c3 = '\b';
                        break;
                    }
                    c3 = 65535;
                    break;
                case 3135262:
                    if (str.equals("fail")) {
                        c3 = 7;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 3327275:
                    if (str.equals("lock")) {
                        c3 = 5;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 94746189:
                    if (str.equals("clear")) {
                        c3 = 3;
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
                    z2 = runSetPattern();
                    break;
                case 1:
                    LockscreenCredential createPassword =
                            LockscreenCredential.createPassword(this.mNew);
                    if (!isNewCredentialSufficient(createPassword)) {
                        z = false;
                        z2 = z;
                        break;
                    } else {
                        this.mLockPatternUtils.setLockCredential(
                                createPassword, getOldCredential(), this.mCurrentUserId);
                        ProxyManager$$ExternalSyntheticOutline0.m(
                                getOutPrintWriter(),
                                this.mNew,
                                "'",
                                new StringBuilder("Password set to '"));
                        z = true;
                        z2 = z;
                    }
                case 2:
                    LockscreenCredential createPin = LockscreenCredential.createPin(this.mNew);
                    if (isNewCredentialSufficient(createPin)) {
                        this.mLockPatternUtils.setLockCredential(
                                createPin, getOldCredential(), this.mCurrentUserId);
                        ProxyManager$$ExternalSyntheticOutline0.m(
                                getOutPrintWriter(),
                                this.mNew,
                                "'",
                                new StringBuilder("Pin set to '"));
                        z = true;
                        z2 = z;
                        break;
                    }
                    z = false;
                    z2 = z;
                case 3:
                    LockscreenCredential createNone = LockscreenCredential.createNone();
                    if (!isNewCredentialSufficient(createNone)) {
                        z2 = false;
                        break;
                    } else {
                        this.mLockPatternUtils.setLockCredential(
                                createNone, getOldCredential(), this.mCurrentUserId);
                        getOutPrintWriter().println("Lock credential cleared");
                        break;
                    }
                case 4:
                    getOutPrintWriter().println("Lock credential verified successfully");
                    break;
                case 5:
                    if (!TextUtils.isEmpty(this.mType)) {
                        sendCommand("lock", this.mType);
                        break;
                    } else {
                        getErrPrintWriter().println("Please add lock type with --type option");
                        break;
                    }
                case 6:
                    runUnlock();
                    break;
                case 7:
                    runFailToUnlock();
                    break;
                case '\b':
                    sendCommand("reconnect-pf", "");
                    break;
                default:
                    getErrPrintWriter().println("Unknown command: ".concat(str));
                    break;
            }
            return z2 ? 0 : -1;
        } catch (Exception e) {
            getErrPrintWriter().println("Error while executing command: ".concat(str));
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("lockSettings service commands:");
            outPrintWriter.println("");
            outPrintWriter.println(
                    "NOTE: when a secure lock screen is set, most commands require the");
            outPrintWriter.println("--old <CREDENTIAL> option.");
            outPrintWriter.println("");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get-disabled [--user USER_ID]");
            outPrintWriter.println(
                    "    Prints true if the lock screen is completely disabled, i.e. set to None.");
            outPrintWriter.println("    Otherwise prints false.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-disabled [--user USER_ID] <true|false>");
            outPrintWriter.println(
                    "    Sets whether the lock screen is disabled. If the lock screen is secure,"
                        + " this");
            outPrintWriter.println(
                    "    has no immediate effect. I.e. this can only change between Swipe and"
                        + " None.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-pattern [--old <CREDENTIAL>] [--user USER_ID] <PATTERN>");
            outPrintWriter.println(
                    "    Sets a secure lock screen that uses the given PATTERN. PATTERN is a"
                        + " series");
            outPrintWriter.println("    of digits 1-9 that identify the cells of the pattern.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-pin [--old <CREDENTIAL>] [--user USER_ID] <PIN>");
            outPrintWriter.println("    Sets a secure lock screen that uses the given PIN.");
            outPrintWriter.println("");
            outPrintWriter.println(
                    "  set-password [--old <CREDENTIAL>] [--user USER_ID] <PASSWORD>");
            outPrintWriter.println("    Sets a secure lock screen that uses the given PASSWORD.");
            outPrintWriter.println("");
            outPrintWriter.println("  clear [--old <CREDENTIAL>] [--user USER_ID]");
            outPrintWriter.println("    Clears the lock credential.");
            outPrintWriter.println("");
            outPrintWriter.println("  verify [--old <CREDENTIAL>] [--user USER_ID]");
            outPrintWriter.println("    Verifies the lock credential.");
            outPrintWriter.println("");
            outPrintWriter.println("  remove-cache [--user USER_ID]");
            outPrintWriter.println("    Removes cached unified challenge for the managed profile.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-resume-on-reboot-provider-package <package_name>");
            outPrintWriter.println(
                    "    Sets the package name for server based resume on reboot service"
                        + " provider.");
            outPrintWriter.println("");
            outPrintWriter.println("  require-strong-auth [--user USER_ID] <reason>");
            outPrintWriter.println(
                    "    Requires strong authentication. The current supported reasons:");
            outPrintWriter.println("    STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN.");
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
            if (nextOption == null) {
                this.mNew = getNextArg();
                return;
            }
            if ("--old".equals(nextOption)) {
                this.mOld = getNextArgRequired();
            } else if ("--user".equals(nextOption)) {
                int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
                this.mCurrentUserId = parseUserArg;
                if (parseUserArg == -2) {
                    this.mCurrentUserId = ActivityManager.getCurrentUser();
                }
            } else {
                if (!"--type".equals(nextOption)) {
                    getErrPrintWriter().println("Unknown option: ".concat(nextOption));
                    throw new IllegalArgumentException();
                }
                this.mType = getNextArgRequired();
            }
        }
    }

    public final void runFailToUnlock() {
        if (this.mType.equals("finger") || this.mType.equals("face") || this.mType.equals("")) {
            sendCommand("fail", this.mType);
        } else {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unknown unlock type: "), this.mType, getErrPrintWriter());
        }
    }

    public final void runRequireStrongAuth() {
        String str = this.mNew;
        str.getClass();
        if (!str.equals("STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN")) {
            getErrPrintWriter().println("Unsupported reason: ".concat(str));
            return;
        }
        this.mCurrentUserId = -1;
        this.mLockPatternUtils.requireStrongAuth(32, -1);
        PrintWriter outPrintWriter = getOutPrintWriter();
        StringBuilder sb = new StringBuilder("Require strong auth for USER_ID ");
        sb.append(this.mCurrentUserId);
        sb.append(" because of ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, this.mNew, outPrintWriter);
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
        ProxyManager$$ExternalSyntheticOutline0.m(
                getOutPrintWriter(), this.mNew, "'", new StringBuilder("Pattern set to '"));
        return true;
    }

    public final void runUnlock() {
        if (this.mType.equals("finger") || this.mType.equals("face") || this.mType.equals("")) {
            sendCommand("unlock", this.mType);
        } else {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unknown unlock type: "), this.mType, getErrPrintWriter());
        }
    }

    public final void sendCommand(String str, String str2) {
        if (this.mCallback == null) {
            getErrPrintWriter().println("Not supported command: ".concat(str));
            return;
        }
        if ("true".equals(SystemProperties.get("ro.product_ship", "false"))) {
            getErrPrintWriter().println("Not supported ship build");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("command", str);
        bundle.putString("type", str2);
        try {
            this.mCallback.sendResult(bundle);
        } catch (RemoteException unused) {
            getErrPrintWriter().println("Failed command: ".concat(str));
        }
    }
}
