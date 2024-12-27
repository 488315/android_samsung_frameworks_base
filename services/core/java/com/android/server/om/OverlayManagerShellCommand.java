package com.android.server.om;

import android.content.Context;
import android.content.om.FabricatedOverlay;
import android.content.om.IOverlayManager;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.util.TypedValue;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverlayManagerShellCommand extends ShellCommand {
    public static final Map TYPE_MAP = Map.of("color", 28, "string", 3, "drawable", -1);
    public final Context mContext;
    public final IOverlayManager mInterface;

    public OverlayManagerShellCommand(Context context, IOverlayManager iOverlayManager) {
        this.mContext = context;
        this.mInterface = iOverlayManager;
    }

    public static void printListOverlay(PrintWriter printWriter, OverlayInfo overlayInfo) {
        int i = overlayInfo.state;
        String str = i != 2 ? (i == 3 || i == 6) ? "[x]" : "---" : "[ ]";
        printWriter.println(str + " " + overlayInfo.getOverlayIdentifier());
    }

    public final void addOverlayValue(
            FabricatedOverlay.Builder builder, String str, String str2, String str3, String str4) {
        String lowerCase = str2.toLowerCase(Locale.getDefault());
        Map map = TYPE_MAP;
        int intValue =
                map.containsKey(lowerCase)
                        ? ((Integer) map.get(lowerCase)).intValue()
                        : lowerCase.startsWith("0x")
                                ? Integer.parseUnsignedInt(lowerCase.substring(2), 16)
                                : Integer.parseUnsignedInt(lowerCase);
        if (intValue == 3) {
            builder.setResourceValue(str, intValue, str3, str4);
        } else if (intValue < 0) {
            builder.setResourceValue(str, openFileForSystem(str3, "r"), str4);
        } else {
            builder.setResourceValue(
                    str,
                    intValue,
                    str3.startsWith("0x")
                            ? Integer.parseUnsignedInt(str3.substring(2), 16)
                            : Integer.parseUnsignedInt(str3),
                    str4);
        }
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1361113425:
                    if (str.equals("set-priority")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1298848381:
                    if (str.equals("enable")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1097094790:
                    if (str.equals("lookup")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -794624300:
                    if (str.equals("enable-exclusive")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1671308008:
                    if (str.equals("disable")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2016903117:
                    if (str.equals("fabricate")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
            }
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, errPrintWriter);
            return -1;
        } catch (IllegalArgumentException e2) {
            errPrintWriter.println("Error: " + e2.getMessage());
            return -1;
        }
        return handleDefaultCommands(str);
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Overlay manager (overlay) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  dump [--verbose] [--user USER_ID] [[FIELD] PACKAGE[:NAME]]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Print debugging information about the overlay manager.",
                "    With optional parameters PACKAGE and NAME, limit output to the specified",
                "    overlay or target. With optional parameter FIELD, limit output to",
                "    the corresponding SettingsItem field. Field names are all lower case");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    and omit the m prefix, i.e. 'userid' for SettingsItem.mUserId.",
                "  list [--user USER_ID] [PACKAGE[:NAME]]",
                "    Print information about target and overlay packages.",
                "    Overlay packages are printed in priority order. With optional");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    parameters PACKAGE and NAME, limit output to the specified overlay or",
                "    target.",
                "  enable [--user USER_ID] PACKAGE[:NAME]",
                "    Enable overlay within or owned by PACKAGE with optional unique NAME.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  disable [--user USER_ID] PACKAGE[:NAME]",
                "    Disable overlay within or owned by PACKAGE with optional unique NAME.",
                "  enable-exclusive [--user USER_ID] [--category] PACKAGE",
                "    Enable overlay within or owned by PACKAGE and disable all other overlays");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    for its target package. If the --category option is given, only disables",
                "    other overlays in the same category.",
                "  set-priority [--user USER_ID] PACKAGE PARENT|lowest|highest",
                "    Change the priority of the overlay to be just higher than");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    the priority of PARENT If PARENT is the special keyword",
                "    'lowest', change priority of PACKAGE to the lowest priority.",
                "    If PARENT is the special keyword 'highest', change priority of",
                "    PACKAGE to the highest priority.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  lookup [--user USER_ID] [--verbose] PACKAGE-TO-LOAD PACKAGE:TYPE/NAME",
                "    Load a package and print the value of a given resource",
                "    applying the current configuration and enabled overlays.",
                "    For a more fine-grained alternative, use 'idmap2 lookup'.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  fabricate [--user USER_ID] [--target-name OVERLAYABLE] --target PACKAGE",
                "            --name NAME [--file FILE] ",
                "            PACKAGE:TYPE/NAME ENCODED-TYPE-ID|TYPE-NAME ENCODED-VALUE",
                "    Create an overlay from a single resource. Caller must be root. Example:");
        outPrintWriter.println("      fabricate --target android --name LighterGray \\");
        outPrintWriter.println("                android:color/lighter_gray 0x1c 0xffeeeeee");
    }

    public final int runEnableDisable(boolean z) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInterface.commit(
                        new OverlayManagerTransaction.Builder()
                                .setEnabled(
                                        OverlayIdentifier.fromString(getNextArgRequired()), z, i)
                                .build());
                return 0;
            }
            if (!nextOption.equals("--user")) {
                errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runEnableExclusive() {
        PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = false;
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                return z
                        ? !this.mInterface.setEnabledExclusiveInCategory(nextArgRequired, i) ? 1 : 0
                        : !this.mInterface.setEnabledExclusive(nextArgRequired, true, i) ? 1 : 0;
            }
            if (nextOption.equals("--category")) {
                z = true;
            } else {
                if (!nextOption.equals("--user")) {
                    errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return 1;
                }
                i = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x01de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runFabricate() {
        /*
            Method dump skipped, instructions count: 596
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.om.OverlayManagerShellCommand.runFabricate():int");
    }

    public final int runList() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                if (nextArg != null) {
                    List overlayInfosForTarget =
                            this.mInterface.getOverlayInfosForTarget(nextArg, i);
                    if (overlayInfosForTarget.isEmpty()) {
                        OverlayInfo overlayInfo = this.mInterface.getOverlayInfo(nextArg, i);
                        if (overlayInfo != null) {
                            printListOverlay(outPrintWriter, overlayInfo);
                        }
                        return 0;
                    }
                    outPrintWriter.println(nextArg);
                    int size = overlayInfosForTarget.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        printListOverlay(
                                outPrintWriter, (OverlayInfo) overlayInfosForTarget.get(i2));
                    }
                    return 0;
                }
                Map allOverlays = this.mInterface.getAllOverlays(i);
                for (String str : allOverlays.keySet()) {
                    outPrintWriter.println(str);
                    List list = (List) allOverlays.get(str);
                    int size2 = list.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        printListOverlay(outPrintWriter, (OverlayInfo) list.get(i3));
                    }
                    outPrintWriter.println();
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runLookup() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                Matcher matcher = Pattern.compile("(.*?):(.*?)/(.*?)").matcher(nextArgRequired2);
                if (!matcher.matches()) {
                    errPrintWriter.println(
                            "Error: bad resource name, doesn't match package:type/name");
                    return 1;
                }
                try {
                    Resources resourcesForApplication =
                            this.mContext
                                    .createContextAsUser(UserHandle.of(i), 0)
                                    .getPackageManager()
                                    .getResourcesForApplication(nextArgRequired);
                    AssetManager assets = resourcesForApplication.getAssets();
                    try {
                        assets.setResourceResolutionLoggingEnabled(true);
                        try {
                            try {
                                TypedValue typedValue = new TypedValue();
                                resourcesForApplication.getValue(
                                        nextArgRequired2, typedValue, false);
                                CharSequence coerceToString = typedValue.coerceToString();
                                String lastResourceResolution = assets.getLastResourceResolution();
                                resourcesForApplication.getValue(
                                        nextArgRequired2, typedValue, true);
                                CharSequence coerceToString2 = typedValue.coerceToString();
                                if (z) {
                                    outPrintWriter.println(lastResourceResolution);
                                }
                                if (coerceToString.equals(coerceToString2)) {
                                    outPrintWriter.println(coerceToString);
                                } else {
                                    outPrintWriter.println(
                                            ((Object) coerceToString)
                                                    + " -> "
                                                    + ((Object) coerceToString2));
                                }
                                assets.setResourceResolutionLoggingEnabled(false);
                                return 0;
                            } catch (Resources.NotFoundException unused) {
                                int identifier =
                                        resourcesForApplication.getIdentifier(
                                                matcher.group(3),
                                                matcher.group(2),
                                                matcher.group(1));
                                if (identifier == 0) {
                                    throw new Resources.NotFoundException();
                                }
                                TypedArray obtainTypedArray =
                                        resourcesForApplication.obtainTypedArray(identifier);
                                if (z) {
                                    outPrintWriter.println(assets.getLastResourceResolution());
                                }
                                TypedValue typedValue2 = new TypedValue();
                                for (int i2 = 0; i2 < obtainTypedArray.length(); i2++) {
                                    obtainTypedArray.getValue(i2, typedValue2);
                                    outPrintWriter.println(typedValue2.coerceToString());
                                }
                                obtainTypedArray.recycle();
                                assets.setResourceResolutionLoggingEnabled(false);
                                return 0;
                            }
                        } catch (Resources.NotFoundException unused2) {
                            errPrintWriter.println(
                                    "Error: failed to get the resource " + nextArgRequired2);
                            assets.setResourceResolutionLoggingEnabled(false);
                            return 1;
                        }
                    } catch (Throwable th) {
                        assets.setResourceResolutionLoggingEnabled(false);
                        throw th;
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                    errPrintWriter.println(
                            String.format(
                                    "Error: failed to get resources for package %s for user %d",
                                    nextArgRequired, Integer.valueOf(i)));
                    return 1;
                }
            }
            if (nextOption.equals("--user")) {
                i = UserHandle.parseUserArg(getNextArgRequired());
            } else {
                if (!nextOption.equals("--verbose")) {
                    errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    return 1;
                }
                z = true;
            }
        }
    }

    public final int runSetPriority() {
        PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                return "highest".equals(nextArgRequired2)
                        ? !this.mInterface.setHighestPriority(nextArgRequired, i) ? 1 : 0
                        : "lowest".equals(nextArgRequired2)
                                ? !this.mInterface.setLowestPriority(nextArgRequired, i) ? 1 : 0
                                : !this.mInterface.setPriority(nextArgRequired, nextArgRequired2, i)
                                        ? 1
                                        : 0;
            }
            if (!nextOption.equals("--user")) {
                errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return 1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }
}
