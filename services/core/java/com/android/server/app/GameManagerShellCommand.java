package com.android.server.app;

import android.app.ActivityManager;
import android.app.IGameManagerService;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.os.ServiceManager;
import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GameManagerShellCommand extends ShellCommand {
    static {
        String.valueOf(0);
    }

    public static String gameModeIntToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2 ? i != 3 ? i != 4 ? "" : "custom" : "battery" : "performance"
                        : "standard"
                : "unsupported";
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1207633086:
                    if (str.equals("list-configs")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -729460415:
                    if (str.equals("list-modes")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 113762:
                    if (str.equals("set")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3357091:
                    if (str.equals("mode")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str.equals("reset")) {
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
                return runSetGameModeConfig(outPrintWriter);
            }
            if (c == 1) {
                return runResetGameModeConfig(outPrintWriter);
            }
            if (c == 2) {
                return runSetGameMode(outPrintWriter);
            }
            if (c == 3) {
                runListGameModes(outPrintWriter);
                return 0;
            }
            if (c != 4) {
                return handleDefaultCommands(str);
            }
            runListGameModeConfigs(outPrintWriter);
            return 0;
        } catch (Exception e) {
            outPrintWriter.println("Error: " + e);
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Game manager (game) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  downscale");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Deprecated. Please use `custom` command.",
                "  list-configs <PACKAGE_NAME>",
                "      Lists the current intervention configs of an app.",
                "  list-modes <PACKAGE_NAME>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Lists the current selected and available game modes of an app.",
                "  mode [--user <USER_ID>] [1|2|3|4|standard|performance|battery|custom]"
                    + " <PACKAGE_NAME>",
                "      Set app to run in the specified game mode, if supported.",
                "      --user <USER_ID>: apply for the given user,");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "                        the current user is used when unspecified.",
                "  set [intervention configs] <PACKAGE_NAME>",
                "      Set app to run at custom mode using provided intervention configs",
                "      Intervention configs consists of:");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --downscale [0.3|0.35|0.4|0.45|0.5|0.55|0.6|0.65",
                "                  |0.7|0.75|0.8|0.85|0.9|disable]: Set app to run at the",
                "                                                   specified scaling ratio.",
                "      --fps: Integer value to set app to run at the specified fps,");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "             if supported. 0 to disable.",
                "  reset [--mode [2|3|performance|battery] --user <USER_ID>] <PACKAGE_NAME>",
                "      Resets the game mode of the app to device configuration.",
                "      This should only be used to reset any override to non custom game mode");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      applied using the deprecated `set` command",
                "      --mode [2|3|performance|battery]: apply for the given mode,",
                "                                        resets all modes when unspecified.",
                "      --user <USER_ID>: apply for the given user,");
        outPrintWriter.println(
                "                        the current user is used when unspecified.");
    }

    public final void runListGameModeConfigs(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        String interventionList =
                ((GameManagerService) ServiceManager.getService("game"))
                        .getInterventionList(ActivityManager.getCurrentUser(), nextArgRequired);
        if (interventionList == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                    printWriter, "No interventions found for ", nextArgRequired);
            return;
        }
        printWriter.println(nextArgRequired + " interventions: " + interventionList);
    }

    public final void runListGameModes(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        int currentUser = ActivityManager.getCurrentUser();
        GameManagerService gameManagerService =
                (GameManagerService) ServiceManager.getService("game");
        String gameModeIntToString =
                gameModeIntToString(gameManagerService.getGameMode(nextArgRequired, currentUser));
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i : gameManagerService.getAvailableGameModes(nextArgRequired, currentUser)) {
            stringJoiner.add(gameModeIntToString(i));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(nextArgRequired);
        sb.append(" current mode: ");
        sb.append(gameModeIntToString);
        sb.append(", available game modes: [");
        sb.append(stringJoiner);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, "]", printWriter);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runResetGameModeConfig(PrintWriter printWriter) {
        boolean z;
        String str = null;
        String str2 = null;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                GameManagerService gameManagerService =
                        (GameManagerService) ServiceManager.getService("game");
                int parseInt =
                        str != null ? Integer.parseInt(str) : ActivityManager.getCurrentUser();
                if (str2 == null) {
                    gameManagerService.resetGameModeConfigOverride(nextArgRequired, parseInt, -1);
                    return 0;
                }
                String lowerCase = str2.toLowerCase(Locale.getDefault());
                lowerCase.getClass();
                switch (lowerCase.hashCode()) {
                    case -1480388560:
                        if (lowerCase.equals("performance")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case -331239923:
                        if (lowerCase.equals("battery")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 50:
                        if (lowerCase.equals("2")) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    case 51:
                        if (lowerCase.equals("3")) {
                            z = 3;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                    case true:
                        gameManagerService.resetGameModeConfigOverride(
                                nextArgRequired, parseInt, 2);
                        return 0;
                    case true:
                    case true:
                        gameManagerService.resetGameModeConfigOverride(
                                nextArgRequired, parseInt, 3);
                        return 0;
                    default:
                        printWriter.println("Invalid game mode: ".concat(str2));
                        return -1;
                }
            }
            if (nextOption.equals("--mode")) {
                if (str2 != null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, "Duplicate option '", nextOption, "'");
                    return -1;
                }
                str2 = getNextArgRequired();
            } else {
                if (!nextOption.equals("--user")) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, "Invalid option '", nextOption, "'");
                    return -1;
                }
                if (str != null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, "Duplicate option '", nextOption, "'");
                    return -1;
                }
                str = getNextArgRequired();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetGameMode(PrintWriter printWriter) {
        char c;
        String nextOption = getNextOption();
        String nextArgRequired =
                (nextOption == null || !nextOption.equals("--user")) ? null : getNextArgRequired();
        String nextArgRequired2 = getNextArgRequired();
        String nextArgRequired3 = getNextArgRequired();
        IGameManagerService asInterface =
                IGameManagerService.Stub.asInterface(ServiceManager.getServiceOrThrow("game"));
        int parseInt =
                nextArgRequired != null
                        ? Integer.parseInt(nextArgRequired)
                        : ActivityManager.getCurrentUser();
        boolean z = false;
        boolean z2 = false;
        for (int i : asInterface.getAvailableGameModes(nextArgRequired3, parseInt)) {
            if (i == 2) {
                z2 = true;
            } else if (i == 3) {
                z = true;
            }
        }
        String lowerCase = nextArgRequired2.toLowerCase();
        lowerCase.getClass();
        switch (lowerCase.hashCode()) {
            case -1480388560:
                if (lowerCase.equals("performance")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -331239923:
                if (lowerCase.equals("battery")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (lowerCase.equals("1")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (lowerCase.equals("2")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (lowerCase.equals("3")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (lowerCase.equals("4")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1312628413:
                if (lowerCase.equals("standard")) {
                    c = 7;
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
            case 4:
                if (!z2) {
                    printWriter.println(
                            "Game mode: "
                                    + nextArgRequired2
                                    + " not supported by "
                                    + nextArgRequired3);
                    return -1;
                }
                asInterface.setGameMode(nextArgRequired3, 2, parseInt);
                printWriter.println(
                        "Set game mode to `PERFORMANCE` for user `"
                                + parseInt
                                + "` in game `"
                                + nextArgRequired3
                                + "`");
                return 0;
            case 1:
            case 6:
                asInterface.setGameMode(nextArgRequired3, 4, parseInt);
                printWriter.println(
                        "Set game mode to `CUSTOM` for user `"
                                + parseInt
                                + "` in game `"
                                + nextArgRequired3
                                + "`");
                return 0;
            case 2:
            case 5:
                if (!z) {
                    printWriter.println(
                            "Game mode: "
                                    + nextArgRequired2
                                    + " not supported by "
                                    + nextArgRequired3);
                    return -1;
                }
                asInterface.setGameMode(nextArgRequired3, 3, parseInt);
                printWriter.println(
                        "Set game mode to `BATTERY` for user `"
                                + parseInt
                                + "` in game `"
                                + nextArgRequired3
                                + "`");
                return 0;
            case 3:
            case 7:
                asInterface.setGameMode(nextArgRequired3, 1, parseInt);
                printWriter.println(
                        "Set game mode to `STANDARD` for user `"
                                + parseInt
                                + "` in game `"
                                + nextArgRequired3
                                + "`");
                return 0;
            default:
                printWriter.println("Invalid game mode: ".concat(nextArgRequired2));
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetGameModeConfig(PrintWriter printWriter) {
        char c;
        String str = null;
        int i = 4;
        String str2 = null;
        String str3 = null;
        while (true) {
            int i2 = i;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    String nextArgRequired = getNextArgRequired();
                    int parseInt =
                            str != null ? Integer.parseInt(str) : ActivityManager.getCurrentUser();
                    GameManagerService gameManagerService =
                            (GameManagerService) ServiceManager.getService("game");
                    if (gameManagerService == null) {
                        printWriter.println("Failed to find GameManagerService on device");
                        return -1;
                    }
                    gameManagerService.setGameModeConfigOverride(
                            nextArgRequired, parseInt, i2, str2, str3);
                    printWriter.println(
                            OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                                    DirEncryptService$$ExternalSyntheticOutline0.m(
                                            parseInt,
                                            "Set custom mode intervention config for user `",
                                            "` in game `",
                                            nextArgRequired,
                                            "` as: `downscaling-ratio: "),
                                    str3,
                                    ";fps-override: ",
                                    str2,
                                    "`"));
                    return 0;
                }
                switch (nextOption.hashCode()) {
                    case 43000649:
                        if (nextOption.equals("--fps")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333227331:
                        if (nextOption.equals("--mode")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1807206472:
                        if (nextOption.equals("--downscale")) {
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
                        if (str2 != null) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter, "Duplicate option '", nextOption, "'");
                            return -1;
                        }
                        String nextArgRequired2 = getNextArgRequired();
                        try {
                            Integer.parseInt(nextArgRequired2);
                            str2 = nextArgRequired2;
                            break;
                        } catch (NumberFormatException unused) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter, "Invalid frame rate: '", nextArgRequired2, "'");
                            return -1;
                        }
                    case 1:
                        break;
                    case 2:
                        if (str != null) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter, "Duplicate option '", nextOption, "'");
                            return -1;
                        }
                        str = getNextArgRequired();
                        break;
                    case 3:
                        if (str3 != null) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter, "Duplicate option '", nextOption, "'");
                            return -1;
                        }
                        String nextArgRequired3 = getNextArgRequired();
                        if ("disable".equals(nextArgRequired3)) {
                            nextArgRequired3 = "-1";
                        } else {
                            try {
                                Float.parseFloat(nextArgRequired3);
                            } catch (NumberFormatException unused2) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        printWriter,
                                        "Invalid scaling ratio '",
                                        nextArgRequired3,
                                        "'");
                                return -1;
                            }
                        }
                        str3 = nextArgRequired3;
                        break;
                    default:
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "Invalid option '", nextOption, "'");
                        return -1;
                }
            }
            i = Integer.parseInt(getNextArgRequired());
        }
    }
}
