package com.android.server.locales;

import android.app.ActivityManager;
import android.app.ILocaleManager;
import android.app.LocaleConfig;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.PrintWriter;

public final class LocaleManagerShellCommand extends ShellCommand {
    public final ILocaleManager mBinderService;

    public LocaleManagerShellCommand(ILocaleManager iLocaleManager) {
        this.mBinderService = iLocaleManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onCommand(String str) {
        char c;
        char c2;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -843437997:
                if (str.equals("set-app-localeconfig")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -232514593:
                if (str.equals("get-app-localeconfig")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 819706294:
                if (str.equals("get-app-locales")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1730458818:
                if (str.equals("set-app-locales")) {
                    c = 3;
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
                String nextArg = getNextArg();
                if (nextArg == null) {
                    getErrPrintWriter().println("Error: no package specified");
                    return -1;
                }
                int currentUser = ActivityManager.getCurrentUser();
                LocaleConfig localeConfig = null;
                while (true) {
                    LocaleList localeList = null;
                    while (true) {
                        String nextOption = getNextOption();
                        if (nextOption == null) {
                            if (localeList != null) {
                                try {
                                    localeConfig = new LocaleConfig(localeList);
                                } catch (RemoteException e) {
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            "Remote Exception: ", e, getOutPrintWriter());
                                }
                            }
                            this.mBinderService.setOverrideLocaleConfig(
                                    nextArg, currentUser, localeConfig);
                            return 0;
                        }
                        if (nextOption.equals("--user")) {
                            currentUser = UserHandle.parseUserArg(getNextArgRequired());
                        } else {
                            if (!nextOption.equals("--locales")) {
                                throw new IllegalArgumentException(
                                        "Unknown option: ".concat(nextOption));
                            }
                            String nextArg2 = getNextArg();
                            if (nextArg2 == null) {
                                break;
                            }
                            if (nextArg2.equals("empty")) {
                                localeList = LocaleList.getEmptyLocaleList();
                            } else {
                                if (nextArg2.startsWith(
                                        PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                                    throw new IllegalArgumentException(
                                            "Unknown locales: ".concat(nextArg2));
                                }
                                localeList = LocaleList.forLanguageTags(nextArg2);
                            }
                        }
                    }
                }
            case 1:
                String nextArg3 = getNextArg();
                if (nextArg3 == null) {
                    getErrPrintWriter().println("Error: no package specified");
                    return -1;
                }
                int currentUser2 = ActivityManager.getCurrentUser();
                String nextOption2 = getNextOption();
                if (nextOption2 != null) {
                    if (!"--user".equals(nextOption2)) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextOption2));
                    }
                    currentUser2 = UserHandle.parseUserArg(getNextArgRequired());
                }
                try {
                    LocaleConfig overrideLocaleConfig =
                            this.mBinderService.getOverrideLocaleConfig(nextArg3, currentUser2);
                    if (overrideLocaleConfig == null) {
                        getOutPrintWriter()
                                .println(
                                        "LocaleConfig for "
                                                + nextArg3
                                                + " for user "
                                                + currentUser2
                                                + " is null");
                    } else {
                        LocaleList supportedLocales = overrideLocaleConfig.getSupportedLocales();
                        if (supportedLocales == null) {
                            getOutPrintWriter()
                                    .println(
                                            "Locales within the LocaleConfig for "
                                                    + nextArg3
                                                    + " for user "
                                                    + currentUser2
                                                    + " are null");
                        } else {
                            getOutPrintWriter()
                                    .println(
                                            "Locales within the LocaleConfig for "
                                                    + nextArg3
                                                    + " for user "
                                                    + currentUser2
                                                    + " are ["
                                                    + supportedLocales.toLanguageTags()
                                                    + "]");
                        }
                    }
                } catch (RemoteException e2) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            "Remote Exception: ", e2, getOutPrintWriter());
                }
                return 0;
            case 2:
                PrintWriter errPrintWriter = getErrPrintWriter();
                String nextArg4 = getNextArg();
                if (nextArg4 == null) {
                    errPrintWriter.println("Error: no package specified");
                    return -1;
                }
                int currentUser3 = ActivityManager.getCurrentUser();
                String nextOption3 = getNextOption();
                if (nextOption3 != null) {
                    if (!"--user".equals(nextOption3)) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextOption3));
                    }
                    currentUser3 = UserHandle.parseUserArg(getNextArgRequired());
                }
                try {
                    LocaleList applicationLocales =
                            this.mBinderService.getApplicationLocales(nextArg4, currentUser3);
                    getOutPrintWriter()
                            .println(
                                    "Locales for "
                                            + nextArg4
                                            + " for user "
                                            + currentUser3
                                            + " are ["
                                            + applicationLocales.toLanguageTags()
                                            + "]");
                } catch (RemoteException e3) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            "Remote Exception: ", e3, getOutPrintWriter());
                } catch (IllegalArgumentException unused) {
                    getOutPrintWriter()
                            .println("Unknown package " + nextArg4 + " for userId " + currentUser3);
                }
                return 0;
            case 3:
                PrintWriter errPrintWriter2 = getErrPrintWriter();
                String nextArg5 = getNextArg();
                if (nextArg5 == null) {
                    errPrintWriter2.println("Error: no package specified");
                    return -1;
                }
                int currentUser4 = ActivityManager.getCurrentUser();
                LocaleList emptyLocaleList = LocaleList.getEmptyLocaleList();
                while (true) {
                    boolean z = false;
                    while (true) {
                        String nextOption4 = getNextOption();
                        if (nextOption4 == null) {
                            try {
                                this.mBinderService.setApplicationLocales(
                                        nextArg5, currentUser4, emptyLocaleList, z);
                            } catch (RemoteException e4) {
                                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                        "Remote Exception: ", e4, getOutPrintWriter());
                            } catch (IllegalArgumentException unused2) {
                                getOutPrintWriter()
                                        .println(
                                                "Unknown package "
                                                        + nextArg5
                                                        + " for userId "
                                                        + currentUser4);
                            }
                            return 0;
                        }
                        switch (nextOption4.hashCode()) {
                            case 835076901:
                                if (nextOption4.equals("--delegate")) {
                                    c2 = 0;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1333469547:
                                if (nextOption4.equals("--user")) {
                                    c2 = 1;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1724392377:
                                if (nextOption4.equals("--locales")) {
                                    c2 = 2;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                c2 = 65535;
                                break;
                        }
                        switch (c2) {
                            case 0:
                                String nextArg6 = getNextArg();
                                if (nextArg6 == null) {
                                    break;
                                }
                                if (!nextArg6.startsWith(
                                        PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                                    z = Boolean.parseBoolean(nextArg6);
                                    break;
                                } else {
                                    throw new IllegalArgumentException(
                                            "Unknown source: ".concat(nextArg6));
                                }
                            case 1:
                                currentUser4 = UserHandle.parseUserArg(getNextArgRequired());
                                break;
                            case 2:
                                String nextArg7 = getNextArg();
                                if (nextArg7 == null) {
                                    emptyLocaleList = LocaleList.getEmptyLocaleList();
                                    break;
                                } else if (!nextArg7.startsWith(
                                        PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                                    emptyLocaleList = LocaleList.forLanguageTags(nextArg7);
                                    break;
                                } else {
                                    throw new IllegalArgumentException(
                                            "Unknown locales: ".concat(nextArg7));
                                }
                            default:
                                throw new IllegalArgumentException(
                                        "Unknown option: ".concat(nextOption4));
                        }
                    }
                }
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Locale manager (locale) shell commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println(
                "  set-app-locales <PACKAGE_NAME> [--user <USER_ID>] [--locales"
                    + " <LOCALE_INFO>][--delegate <FROM_DELEGATE>]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Set the locales for the specified app.",
                "      --user <USER_ID>: apply for the given user, the current user is used when"
                    + " unspecified.",
                "      --locales <LOCALE_INFO>: The language tags of locale to be included as a"
                    + " single String separated by commas.",
                "                 eg. en,en-US,hi ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "                 Empty locale list is used when unspecified.",
                "      --delegate <FROM_DELEGATE>: The locales are set from a delegate, the value"
                    + " could be true or false. false is the default when unspecified.",
                "  get-app-locales <PACKAGE_NAME> [--user <USER_ID>]",
                "      Get the locales for the specified app.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --user <USER_ID>: get for the given user, the current user is used when"
                    + " unspecified.",
                "  set-app-localeconfig <PACKAGE_NAME> [--user <USER_ID>] [--locales"
                    + " <LOCALE_INFO>]",
                "      Set the override LocaleConfig for the specified app.",
                "      --user <USER_ID>: apply for the given user, the current user is used when"
                    + " unspecified.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --locales <LOCALE_INFO>: The language tags of locale to be included as a"
                    + " single String separated by commas.",
                "                 eg. en,en-US,hi ",
                "                 Empty locale list is used when typing a 'empty' word",
                "                 NULL is used when unspecified.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-app-localeconfig <PACKAGE_NAME> [--user <USER_ID>]",
                "      Get the locales within the override LocaleConfig for the specified app.",
                "      --user <USER_ID>: get for the given user, the current user is used when"
                    + " unspecified.");
    }
}
