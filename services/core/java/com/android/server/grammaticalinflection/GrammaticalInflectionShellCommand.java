package com.android.server.grammaticalinflection;

import android.app.ActivityManager;
import android.app.GrammaticalInflectionManager;
import android.app.IGrammaticalInflectionManager;
import android.content.AttributionSource;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.util.SparseArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GrammaticalInflectionShellCommand extends ShellCommand {
    public static final SparseArray GRAMMATICAL_GENDER_MAP;
    public final AttributionSource mAttributionSource;
    public final IGrammaticalInflectionManager mBinderService;

    static {
        SparseArray sparseArray = new SparseArray();
        GRAMMATICAL_GENDER_MAP = sparseArray;
        sparseArray.put(0, "Not specified (0)");
        sparseArray.put(1, "Neuter (1)");
        sparseArray.put(2, "Feminine (2)");
        sparseArray.put(3, "Masculine (3)");
    }

    public GrammaticalInflectionShellCommand(
            IGrammaticalInflectionManager iGrammaticalInflectionManager,
            AttributionSource attributionSource) {
        this.mBinderService = iGrammaticalInflectionManager;
        this.mAttributionSource = attributionSource;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        if (str.equals("set-system-grammatical-gender")) {
            int currentUser = ActivityManager.getCurrentUser();
            while (true) {
                int i = 0;
                while (true) {
                    String nextOption = getNextOption();
                    if (nextOption == null) {
                        try {
                            this.mBinderService.setSystemWideGrammaticalGender(i, currentUser);
                        } catch (RemoteException e) {
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    "Remote Exception: ", e, getOutPrintWriter());
                        }
                        return 0;
                    }
                    switch (nextOption) {
                        case "-g":
                        case "--grammaticalGender":
                            String nextArg = getNextArg();
                            if (nextArg != null) {
                                i = Integer.parseInt(nextArg);
                                if (GrammaticalInflectionManager.VALID_GRAMMATICAL_GENDER_VALUES
                                        .contains(Integer.valueOf(i))) {
                                    break;
                                }
                            } else {
                                break;
                            }
                            break;
                        case "--user":
                            currentUser = UserHandle.parseUserArg(getNextArgRequired());
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Unknown option: ".concat(nextOption));
                    }
                }
            }
        } else {
            if (!str.equals("get-system-grammatical-gender")) {
                return handleDefaultCommands(str);
            }
            int currentUser2 = ActivityManager.getCurrentUser();
            while (true) {
                String nextOption2 = getNextOption();
                if (nextOption2 == null) {
                    try {
                        getOutPrintWriter()
                                .println(
                                        (String)
                                                GRAMMATICAL_GENDER_MAP.get(
                                                        this.mBinderService
                                                                .getSystemGrammaticalGender(
                                                                        this.mAttributionSource,
                                                                        currentUser2)));
                    } catch (RemoteException e2) {
                        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                "Remote Exception: ", e2, getOutPrintWriter());
                    }
                    return 0;
                }
                if (!nextOption2.equals("--user")) {
                    throw new IllegalArgumentException("Unknown option: ".concat(nextOption2));
                }
                currentUser2 = UserHandle.parseUserArg(getNextArgRequired());
            }
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println(
                "Grammatical inflection manager (grammatical_inflection) shell commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println(
                "  set-system-grammatical-gender [--user <USER_ID>] [--grammaticalGender"
                    + " <GRAMMATICAL_GENDER>]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Set the system grammatical gender for system.",
                "      --user <USER_ID>: apply for the given user, the current user is used when"
                    + " unspecified.",
                "      --grammaticalGender <GRAMMATICAL_GENDER>: The terms of address the user"
                    + " preferred in system, not specified (0) is used when unspecified.",
                "                 eg. 0 = not_specified, 1 = neuter, 2 = feminine, 3 = masculine.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-system-grammatical-gender [--user <USER_ID>]",
                "      Get the system grammatical gender for system.",
                "      --user <USER_ID>: apply for the given user, the current user is used when"
                    + " unspecified.");
    }
}
