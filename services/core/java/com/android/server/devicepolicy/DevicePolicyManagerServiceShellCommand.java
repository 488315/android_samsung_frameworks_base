package com.android.server.devicepolicy;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicySafetyChecker;
import android.content.ComponentName;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.UserHandle;
import com.android.internal.util.Preconditions;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DevicePolicyManagerServiceShellCommand extends ShellCommand {
    public ComponentName mComponent;
    public final DevicePolicyManagerService mService;
    public boolean mSetDoOnly;
    public int mUserId = 0;

    public DevicePolicyManagerServiceShellCommand(DevicePolicyManagerService devicePolicyManagerService) {
        Objects.requireNonNull(devicePolicyManagerService);
        this.mService = devicePolicyManagerService;
    }

    public static int printAndGetSize(PrintWriter printWriter, Collection collection, String str) {
        if (collection.isEmpty()) {
            printWriter.printf("no %ss\n", str);
            return 0;
        }
        int size = collection.size();
        printWriter.printf("%d %s%s:\n", Integer.valueOf(size), str, size == 1 ? "" : "s");
        return size;
    }

    public static void showHelp(PrintWriter printWriter) {
        printWriter.printf("  help\n", new Object[0]);
        printWriter.printf("    Prints this help text.\n\n", new Object[0]);
        printWriter.printf("  %s <OPERATION_ID>\n", "is-operation-safe");
        printWriter.printf("    Checks if the give operation is safe \n\n", new Object[0]);
        printWriter.printf("  %s <REASON_ID>\n", "is-operation-safe-by-reason");
        printWriter.printf("    Checks if the operations are safe for the given reason\n\n", new Object[0]);
        printWriter.printf("  %s <OPERATION_ID> <REASON_ID>\n", "set-operation-safe");
        printWriter.printf("    Emulates the result of the next call to check if the given operation is safe \n\n", new Object[0]);
        printWriter.printf("  %s\n", "list-owners");
        printWriter.printf("    Lists the device / profile owners per user \n\n", new Object[0]);
        printWriter.printf("  %s\n", "list-policy-exempt-apps");
        printWriter.printf("    Lists the apps that are exempt from policies\n\n", new Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", "set-active-admin", "--user");
        printWriter.printf("    Sets the given component as active admin for an existing user.\n\n", new Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current *EXPERIMENTAL* ] [ %s ]<COMPONENT>\n", "set-device-owner", "--user", "--device-owner-only");
        printWriter.printf("    Sets the given component as active admin, and its package as device owner.\n\n", new Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", "set-profile-owner", "--user");
        printWriter.printf("    Sets the given component as active admin and profile owner for an existing user.\n\n", new Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", "remove-active-admin", "--user");
        printWriter.printf("    Disables an active admin, the admin must have declared android:testOnly in the application in its manifest. This will also remove device and profile owners.\n\n", new Object[0]);
        printWriter.printf("  %s\n", "clear-freeze-period-record");
        printWriter.printf("    Clears framework-maintained record of past freeze periods that the device went through. For use during feature development to prevent triggering restriction on setting freeze periods.\n\n", new Object[0]);
        printWriter.printf("  %s\n", "force-network-logs");
        printWriter.printf("    Makes all network logs available to the DPC and triggers DeviceAdminReceiver.onNetworkLogsAvailable() if needed.\n\n", new Object[0]);
        printWriter.printf("  %s\n", "force-security-logs");
        printWriter.printf("    Makes all security logs available to the DPC and triggers DeviceAdminReceiver.onSecurityLogsAvailable() if needed.\n\n", new Object[0]);
        printWriter.printf("  %s [ %s <USER_ID> | current ] <COMPONENT>\n", "mark-profile-owner-on-organization-owned-device", "--user");
        printWriter.printf("    Marks the profile owner of the given user as managing an organization-owneddevice. That will give it access to device identifiers (such as serial number, IMEI and MEID), as well as other privileges.\n\n", new Object[0]);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            boolean z = true;
            int i = -1;
            switch (str.hashCode()) {
                case -2077120112:
                    if (str.equals("force-network-logs")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1819296492:
                    if (str.equals("list-policy-exempt-apps")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1791908857:
                    if (str.equals("set-device-owner")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1073491921:
                    if (str.equals("list-owners")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -905136898:
                    if (str.equals("set-operation-safe")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -898547037:
                    if (str.equals("is-operation-safe-by-reason")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -776610703:
                    if (str.equals("remove-active-admin")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -577127626:
                    if (str.equals("is-operation-safe")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -536624985:
                    if (str.equals("clear-freeze-period-record")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 547934547:
                    if (str.equals("set-active-admin")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 639813476:
                    if (str.equals("set-profile-owner")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1325530298:
                    if (str.equals("force-security-logs")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 1509758184:
                    if (str.equals("mark-profile-owner-on-organization-owned-device")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int parseInt = Integer.parseInt(getNextArgRequired());
                    DevicePolicySafetyChecker devicePolicySafetyChecker = this.mService.mSafetyChecker;
                    int unsafeOperationReason = devicePolicySafetyChecker == null ? -1 : devicePolicySafetyChecker.getUnsafeOperationReason(parseInt);
                    if (unsafeOperationReason != -1) {
                        z = false;
                    }
                    outPrintWriter.printf("Operation %s is %s. Reason: %s\n", DevicePolicyManager.operationToString(parseInt), z ? "SAFE" : "UNSAFE", DevicePolicyManager.operationSafetyReasonToString(unsafeOperationReason));
                    outPrintWriter.close();
                    return 0;
                case 1:
                    int parseInt2 = Integer.parseInt(getNextArgRequired());
                    outPrintWriter.printf("Operations affected by %s are %s\n", DevicePolicyManager.operationSafetyReasonToString(parseInt2), this.mService.isSafeOperation(parseInt2) ? "SAFE" : "UNSAFE");
                    outPrintWriter.close();
                    return 0;
                case 2:
                    int parseInt3 = Integer.parseInt(getNextArgRequired());
                    int parseInt4 = Integer.parseInt(getNextArgRequired());
                    this.mService.setNextOperationSafety(parseInt3, parseInt4);
                    outPrintWriter.printf("Next call to check operation %s will return %s\n", DevicePolicyManager.operationToString(parseInt3), DevicePolicyManager.operationSafetyReasonToString(parseInt4));
                    outPrintWriter.close();
                    return 0;
                case 3:
                    runListOwners(outPrintWriter);
                    outPrintWriter.close();
                    return 0;
                case 4:
                    List listPolicyExemptApps = this.mService.listPolicyExemptApps();
                    int printAndGetSize = printAndGetSize(outPrintWriter, listPolicyExemptApps, "policy exempt app");
                    if (printAndGetSize != 0) {
                        for (int i2 = 0; i2 < printAndGetSize; i2++) {
                            outPrintWriter.printf("  %d: %s\n", Integer.valueOf(i2), (String) ((ArrayList) listPolicyExemptApps).get(i2));
                        }
                    }
                    outPrintWriter.close();
                    return 0;
                case 5:
                    parseArgs();
                    this.mService.setActiveAdmin(this.mComponent, true, this.mUserId);
                    outPrintWriter.printf("Success: Active admin set to component %s\n", this.mComponent.flattenToShortString());
                    outPrintWriter.close();
                    return 0;
                case 6:
                    runSetDeviceOwner(outPrintWriter);
                    outPrintWriter.close();
                    return 0;
                case 7:
                    runSetProfileOwner(outPrintWriter);
                    outPrintWriter.close();
                    return 0;
                case '\b':
                    parseArgs();
                    this.mService.forceRemoveActiveAdmin(this.mComponent, this.mUserId);
                    outPrintWriter.printf("Success: Admin removed %s\n", this.mComponent);
                    outPrintWriter.close();
                    return 0;
                case '\t':
                    this.mService.clearSystemUpdatePolicyFreezePeriodRecord();
                    outPrintWriter.printf("Success\n", new Object[0]);
                    outPrintWriter.close();
                    return 0;
                case '\n':
                    while (true) {
                        long forceNetworkLogs = this.mService.forceNetworkLogs();
                        if (forceNetworkLogs == 0) {
                            outPrintWriter.printf("Success\n", new Object[0]);
                            outPrintWriter.close();
                            return 0;
                        }
                        outPrintWriter.printf("We have to wait for %d milliseconds...\n", Long.valueOf(forceNetworkLogs));
                        SystemClock.sleep(forceNetworkLogs);
                    }
                case 11:
                    while (true) {
                        long forceSecurityLogs = this.mService.forceSecurityLogs();
                        if (forceSecurityLogs == 0) {
                            outPrintWriter.printf("Success\n", new Object[0]);
                            outPrintWriter.close();
                            return 0;
                        }
                        outPrintWriter.printf("We have to wait for %d milliseconds...\n", Long.valueOf(forceSecurityLogs));
                        SystemClock.sleep(forceSecurityLogs);
                    }
                case '\f':
                    parseArgs();
                    this.mService.setProfileOwnerOnOrganizationOwnedDevice(this.mComponent, this.mUserId, true);
                    outPrintWriter.printf("Success\n", new Object[0]);
                    outPrintWriter.close();
                    return 0;
                default:
                    if (handleDefaultCommands(str) == 0) {
                        i = 0;
                    } else {
                        outPrintWriter.printf("Usage: \n", new Object[0]);
                        showHelp(outPrintWriter);
                    }
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return i;
            }
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

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.printf("DevicePolicyManager Service (device_policy) commands:\n\n", new Object[0]);
            showHelp(outPrintWriter);
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
                String nextArgRequired = getNextArgRequired();
                ComponentName unflattenFromString = ComponentName.unflattenFromString(nextArgRequired);
                if (unflattenFromString == null) {
                    throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Invalid component ", nextArgRequired));
                }
                this.mComponent = unflattenFromString;
                return;
            }
            if ("--user".equals(nextOption)) {
                int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
                this.mUserId = parseUserArg;
                if (parseUserArg == -2) {
                    this.mUserId = ActivityManager.getCurrentUser();
                }
            } else {
                if (!"--device-owner-only".equals(nextOption)) {
                    throw new IllegalArgumentException("Unknown option: ".concat(nextOption));
                }
                this.mSetDoOnly = true;
            }
        }
    }

    public final void runListOwners(PrintWriter printWriter) {
        DevicePolicyManagerService devicePolicyManagerService = this.mService;
        Preconditions.checkCallAuthorization(devicePolicyManagerService.hasCallingOrSelfPermission("android.permission.MANAGE_DEVICE_ADMINS"));
        DevicePolicyManagerService.Injector injector = devicePolicyManagerService.mInjector;
        DevicePolicyManagerService$$ExternalSyntheticLambda33 devicePolicyManagerService$$ExternalSyntheticLambda33 = new DevicePolicyManagerService$$ExternalSyntheticLambda33(devicePolicyManagerService, 4);
        injector.getClass();
        List list = (List) Binder.withCleanCallingIdentity(devicePolicyManagerService$$ExternalSyntheticLambda33);
        int printAndGetSize = printAndGetSize(printWriter, list, "owner");
        if (printAndGetSize == 0) {
            return;
        }
        for (int i = 0; i < printAndGetSize; i++) {
            OwnerShellData ownerShellData = (OwnerShellData) list.get(i);
            printWriter.printf("User %2d: admin=%s", Integer.valueOf(ownerShellData.userId), ownerShellData.admin.flattenToShortString());
            if (ownerShellData.isDeviceOwner) {
                printWriter.print(",DeviceOwner");
            }
            if (ownerShellData.isProfileOwner) {
                printWriter.print(",ProfileOwner");
            }
            if (ownerShellData.isManagedProfileOwner) {
                printWriter.printf(",ManagedProfileOwner(parentUserId=%d)", Integer.valueOf(ownerShellData.parentUserId));
            }
            if (ownerShellData.isAffiliated) {
                printWriter.print(",Affiliated");
            }
            printWriter.println();
        }
    }

    public final void runSetDeviceOwner(PrintWriter printWriter) {
        parseArgs();
        boolean z = false;
        try {
            this.mService.setActiveAdmin(this.mComponent, false, this.mUserId);
            z = true;
        } catch (IllegalArgumentException unused) {
            printWriter.printf("%s was already an admin for user %d. No need to set it again.\n", this.mComponent.flattenToShortString(), Integer.valueOf(this.mUserId));
        }
        try {
            if (this.mService.setDeviceOwner(this.mComponent, this.mUserId, true ^ this.mSetDoOnly)) {
                this.mService.setUserProvisioningState(3, this.mUserId);
                printWriter.printf("Success: Device owner set to package %s\n", this.mComponent.flattenToShortString());
                printWriter.printf("Active admin set to component %s\n", this.mComponent.flattenToShortString());
            } else {
                throw new RuntimeException("Can't set package " + this.mComponent + " as device owner.");
            }
        } catch (Exception e) {
            if (z) {
                this.mService.removeActiveAdmin(this.mComponent, this.mUserId);
            }
            throw e;
        }
    }

    public final void runSetProfileOwner(PrintWriter printWriter) {
        parseArgs();
        this.mService.setActiveAdmin(this.mComponent, true, this.mUserId);
        try {
            if (this.mService.setProfileOwner(this.mComponent, this.mUserId)) {
                this.mService.setUserProvisioningState(3, this.mUserId);
                printWriter.printf("Success: Active admin and profile owner set to %s for user %d\n", this.mComponent.flattenToShortString(), Integer.valueOf(this.mUserId));
            } else {
                throw new RuntimeException("Can't set component " + this.mComponent.flattenToShortString() + " as profile owner for user " + this.mUserId);
            }
        } catch (Exception e) {
            this.mService.removeActiveAdmin(this.mComponent, this.mUserId);
            throw e;
        }
    }
}
