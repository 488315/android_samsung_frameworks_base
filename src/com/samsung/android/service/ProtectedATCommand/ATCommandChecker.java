package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/* loaded from: classes6.dex */
public abstract class ATCommandChecker {
    public static final int ATD = 1;
    public static final int ATDDDEXEERR = 0;
    public static final int DDEXE = 2;
    protected static final String TAG = "PACMClassifier";

    abstract int checkAdditionalCondition(String str);

    abstract int checkProtectedCommand(Device device, ATCommands aTCommands, String str);

    protected abstract int checkUnregisteredCommand(Device device, ATCommands aTCommands);

    public int checkATCommand(Device device, LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, String str, Packet packet) {
        ATCommands command = PACMClassifier.getCommand(linkedHashMap, str);
        int type = command.getType();
        if (type == 161) {
            return checkUserOpenCommand(device, command, packet, str);
        }
        if (type == 162) {
            return checkProtectedCommand(device, command, str);
        }
        return checkUnregisteredCommand(device, command);
    }

    int checkAllCondition(Device device, ATCommands aTCommands, String str) {
        int checkAbsoluteCondition = checkAbsoluteCondition(device, aTCommands);
        if (checkAbsoluteCondition != 1) {
            return checkAbsoluteCondition;
        }
        int checkCommonCondition = checkCommonCondition(device, aTCommands, str);
        return checkCommonCondition != 1 ? checkCommonCondition : checkAdditionalCondition(str);
    }

    int checkAbsoluteCondition(Device device, ATCommands aTCommands) {
        if (device.isAutoBlockerOn() && !aTCommands.isAutoBlockerOpenCommand()) {
            Slog.i(TAG, "AT command is not allowed by Auto Blocker");
            return 208;
        }
        if (!device.isDevDevice() || device.isTestMode()) {
            return 1;
        }
        Slog.e(TAG, "The command is allowed because this device is a development device.");
        return 161;
    }

    private int checkCommonCondition(Device device, ATCommands aTCommands, String str) {
        if (device.hasToken()) {
            return aTCommands.getType() == 162 ? 176 : 161;
        }
        if (checkSpecialCommand(str) == 1) {
            Slog.i(TAG, "This command is allowed because the command is a special command");
            return 161;
        }
        if (device.isMDFEnable()) {
            Slog.i(TAG, "This command is not allowed by CC mode");
            return 193;
        }
        if (!device.isSecureLockOn() || !device.isShipBin() || device.isMaintenanceModeOn() || aTCommands.isSecureLockOpenCommand()) {
            return 1;
        }
        Slog.i(TAG, "This command is not allowed by secure lock");
        return 192;
    }

    static int checkSpecialCommand(String str) {
        if (str == null) {
            Slog.e(TAG, "cmd is null");
            return -268435456;
        }
        String[] strArr = {"ATD", "AT+CDV", "AT+TESTSPECIAL"};
        for (int i = 0; i < 3; i++) {
            try {
                if (str.indexOf(strArr[i]) == 0) {
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -268435456;
            }
        }
        return 255;
    }

    private int checkUserOpenCommand(Device device, ATCommands aTCommands, Packet packet, String str) {
        int checkAllCondition = checkAllCondition(device, aTCommands, str);
        if (checkAllCondition != 1) {
            return checkAllCondition;
        }
        Slog.i(TAG, "This Command is USER_OPEN_AT_COMMAND");
        int checkAttribute = checkAttribute(device, aTCommands, checkAtdDdexe(packet));
        if (checkAttribute == 195 && device.hasCSTool()) {
            Slog.i(TAG, "This Command is now open becauese there is Galaxy Diag Tool");
            return 161;
        }
        if (checkAttribute != 161) {
            Slog.i(TAG, "This Command is not USER_OPEN_AT_COMMAND because of attribute");
        }
        return checkAttribute;
    }

    private int checkAtdDdexe(Packet packet) {
        byte[] item = packet.getItem(4);
        if (item == null) {
            Slog.e(TAG, "atd_ddexe is null !!");
            return 0;
        }
        String str = new String(item, StandardCharsets.UTF_8);
        if (str.equals("ATD")) {
            Slog.i(TAG, "This cmd is from ATD");
            return 1;
        }
        if (!str.equals("DDEXE")) {
            return 0;
        }
        Slog.i(TAG, "This cmd is from DDEXE");
        return 2;
    }

    private static int checkAttribute(Device device, ATCommands aTCommands, int i) {
        String str = new String(aTCommands.getCmdBytes(), StandardCharsets.UTF_8);
        if (!aTCommands.getHasAttribute()) {
            return aTCommands.getType();
        }
        if (aTCommands.isCarrierOpenCommand() && !aTCommands.getCarrierOpenList().contains(device.salesCode())) {
            Slog.i(TAG, str + " is opened in only (" + aTCommands.getCarrierOpenList() + ") device, so this cmd is block in " + device.salesCode() + " device");
            return 196;
        }
        if (aTCommands.isCarrierBlockCommand() && aTCommands.getCarrierBlockList().contains(device.salesCode())) {
            Slog.i(TAG, str + " is blocked in " + device.salesCode() + " device");
            return 197;
        }
        if (aTCommands.isShipBlockCommand() && device.isShipBin() && i == 2) {
            Slog.i(TAG, str.concat(" must be used in only no ship binary. So this is blocked because this binary is ship binary."));
            return 194;
        }
        if (!device.isFacBin()) {
            if (aTCommands.isFacBinOpenATDDDEXECommand()) {
                Slog.i(TAG, str.concat(" must be used in only factory binary. So this is blocked because this binary is not factory binary."));
                return 198;
            }
            if (aTCommands.isFacBinOpenATDCommand() && i == 1) {
                Slog.i(TAG, str.concat(" from ATD must be used in only factory binary. So this is blocked because this binary is not factory binary."));
                return 198;
            }
            if (aTCommands.isFacBinOpenDDEXECommand() && i == 2) {
                Slog.i(TAG, str.concat(" from DDEXE must be used in only factory binary. So this is blocked because this binary is not factory binary."));
                return 198;
            }
        }
        if (aTCommands.isCSOpenCommand()) {
            Slog.i(TAG, str.concat(" is only opend in Galaxy Diag Tool."));
            return 195;
        }
        return aTCommands.getType();
    }
}
