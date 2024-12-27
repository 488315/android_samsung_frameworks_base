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

    public int checkATCommand(Device device, LinkedHashMap<String, LinkedHashSet<ATCommands>> mAtMap, String strCmd, Packet packet) {
        ATCommands atCommand = PACMClassifier.getCommand(mAtMap, strCmd);
        int cmdType = atCommand.getType();
        switch (cmdType) {
            case 161:
                return checkUserOpenCommand(device, atCommand, packet, strCmd);
            case 162:
                return checkProtectedCommand(device, atCommand, strCmd);
            default:
                return checkUnregisteredCommand(device, atCommand);
        }
    }

    int checkAllCondition(Device device, ATCommands atCommand, String strCmd) {
        int result = checkAbsoluteCondition(device, atCommand);
        if (result != 1) {
            return result;
        }
        int result2 = checkCommonCondition(device, atCommand, strCmd);
        if (result2 != 1) {
            return result2;
        }
        return checkAdditionalCondition(strCmd);
    }

    int checkAbsoluteCondition(Device device, ATCommands atCommand) {
        if (device.isAutoBlockerOn() && !atCommand.isAutoBlockerOpenCommand()) {
            Slog.i(TAG, "AT command is not allowed by Auto Blocker");
            return 208;
        }
        if (device.isDevDevice() && !device.isTestMode()) {
            Slog.e(TAG, "The command is allowed because this device is a development device.");
            return 161;
        }
        return 1;
    }

    private int checkCommonCondition(Device device, ATCommands atCommand, String strCmd) {
        if (device.hasToken()) {
            return atCommand.getType() == 162 ? 176 : 161;
        }
        if (checkSpecialCommand(strCmd) == 1) {
            Slog.i(TAG, "This command is allowed because the command is a special command");
            return 161;
        }
        if (device.isMDFEnable()) {
            Slog.i(TAG, "This command is not allowed by CC mode");
            return 193;
        }
        if (!device.isSecureLockOn() || !device.isShipBin() || device.isMaintenanceModeOn() || atCommand.isSecureLockOpenCommand()) {
            return 1;
        }
        Slog.i(TAG, "This command is not allowed by secure lock");
        return 192;
    }

    static int checkSpecialCommand(String cmd) {
        if (cmd == null) {
            Slog.e(TAG, "cmd is null");
            return -268435456;
        }
        String[] spcCmds = {"ATD", "AT+CDV", "AT+TESTSPECIAL"};
        try {
            for (String spcCmd : spcCmds) {
                if (cmd.indexOf(spcCmd) == 0) {
                    return 1;
                }
            }
            return 255;
        } catch (Exception e) {
            e.printStackTrace();
            return -268435456;
        }
    }

    private int checkUserOpenCommand(Device device, ATCommands atCommand, Packet packet, String strCmd) {
        int result = checkAllCondition(device, atCommand, strCmd);
        if (result != 1) {
            return result;
        }
        Slog.i(TAG, "This Command is USER_OPEN_AT_COMMAND");
        int emRet = checkAttribute(device, atCommand, checkAtdDdexe(packet));
        if (emRet == 195 && device.hasCSTool()) {
            Slog.i(TAG, "This Command is now open becauese there is Galaxy Diag Tool");
            return 161;
        }
        if (emRet != 161) {
            Slog.i(TAG, "This Command is not USER_OPEN_AT_COMMAND because of attribute");
            return emRet;
        }
        return emRet;
    }

    private int checkAtdDdexe(Packet packet) {
        byte[] atd_ddexe = packet.getItem(4);
        if (atd_ddexe == null) {
            Slog.e(TAG, "atd_ddexe is null !!");
            return 0;
        }
        String str_atdddexe = new String(atd_ddexe, StandardCharsets.UTF_8);
        if (str_atdddexe.equals("ATD")) {
            Slog.i(TAG, "This cmd is from ATD");
            return 1;
        }
        if (!str_atdddexe.equals("DDEXE")) {
            return 0;
        }
        Slog.i(TAG, "This cmd is from DDEXE");
        return 2;
    }

    private static int checkAttribute(Device device, ATCommands atCommand, int atdddexe) {
        String cmd = new String(atCommand.getCmdBytes(), StandardCharsets.UTF_8);
        if (!atCommand.getHasAttribute()) {
            return atCommand.getType();
        }
        if (atCommand.isCarrierOpenCommand() && !atCommand.getCarrierOpenList().contains(device.salesCode())) {
            Slog.i(TAG, cmd + " is opened in only (" + atCommand.getCarrierOpenList() + ") device, so this cmd is block in " + device.salesCode() + " device");
            return 196;
        }
        if (atCommand.isCarrierBlockCommand() && atCommand.getCarrierBlockList().contains(device.salesCode())) {
            Slog.i(TAG, cmd + " is blocked in " + device.salesCode() + " device");
            return 197;
        }
        if (atCommand.isShipBlockCommand() && device.isShipBin() && atdddexe == 2) {
            Slog.i(TAG, cmd + " must be used in only no ship binary. So this is blocked because this binary is ship binary.");
            return 194;
        }
        if (!device.isFacBin()) {
            if (atCommand.isFacBinOpenATDDDEXECommand()) {
                Slog.i(TAG, cmd + " must be used in only factory binary. So this is blocked because this binary is not factory binary.");
                return 198;
            }
            if (atCommand.isFacBinOpenATDCommand() && atdddexe == 1) {
                Slog.i(TAG, cmd + " from ATD must be used in only factory binary. So this is blocked because this binary is not factory binary.");
                return 198;
            }
            if (atCommand.isFacBinOpenDDEXECommand() && atdddexe == 2) {
                Slog.i(TAG, cmd + " from DDEXE must be used in only factory binary. So this is blocked because this binary is not factory binary.");
                return 198;
            }
        }
        if (atCommand.isCSOpenCommand()) {
            Slog.i(TAG, cmd + " is only opend in Galaxy Diag Tool.");
            return 195;
        }
        return atCommand.getType();
    }
}
