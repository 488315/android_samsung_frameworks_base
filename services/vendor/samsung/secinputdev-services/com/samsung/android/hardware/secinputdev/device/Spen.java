package com.samsung.android.hardware.secinputdev.device;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputCommandService;
import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;

import java.util.HashMap;
import java.util.Map;

public class Spen extends SemInputDevice {
    private static final String SET_CLEAR_COVER_MODE = "clear_cover_mode";
    private static final String SET_FOLD_STATE = "set_fold_state";
    private static final String SET_REFRESH_RATE_MODE = "refresh_rate_mode";
    private static final String SET_SPEN_AOD_ENABLE = "set_epen_aod_enable";
    private static final String SET_SPEN_BLE_CHARGING_MODE = "epen_ble_charging_mode";
    private static final String SET_SPEN_COVER_TYPE = "set_cover_type";
    private static final String SET_SPEN_LOWCURRENT_MODE = "set_lowcurrent_mode";
    private static final String SET_SPEN_PDCT_LOWSENSITIVITY_ENABLE =
            "set_pdct_lowsensitivity_enable";
    private static final String SET_SPEN_POWER_MODE = "sec_wacom_device_enable";
    private static final String SET_SPEN_SAVING_MODE = "set_saving_mode";
    private static final String SET_SPEN_SCREEN_OFF_MEMO = "set_screen_off_memo";
    private static final String SET_SPEN_WIRELESS_CHARGING_MODE = "set_wcharging_mode";
    private static final String SET_SPEN_WIRELESS_CHARGING_TX_ID = "set_wcharging_tx_id";
    private final StringBuilder stringBuilderForSupportCommands;
    private final Map<Integer, Command> supportCommands;

    public Spen(String name, int devid, int feature, String cmdlist) {
        super(name, devid, feature, cmdlist);
        this.supportCommands = new HashMap();
        this.stringBuilderForSupportCommands = new StringBuilder();
        setCommands();
        Log.i(this.TAG, "supportCommands: " + this.supportCommands);
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public String getSupportCommands() {
        return "supportCommands: " + this.stringBuilderForSupportCommands.toString();
    }

    private enum Command {
        REFRESH_RATE(
                SemInputConstants.Command.REFRESH_RATE.toInt(), Spen.SET_REFRESH_RATE_MODE, null),
        CLEAR_COVER(SemInputConstants.Command.CLEAR_COVER.toInt(), Spen.SET_CLEAR_COVER_MODE, null),
        AOD(
                SemInputConstants.Command.AOD.toInt(),
                Spen.SET_SPEN_AOD_ENABLE,
                SemInputConstants.Property.AOD_ENABLE),
        FOLD_STATE(SemInputConstants.Command.FOLD_STATE.toInt(), Spen.SET_FOLD_STATE, null),
        WIRELESS_CHARGER(
                SemInputConstants.Command.WIRELESS_CHARGER.toInt(),
                Spen.SET_SPEN_WIRELESS_CHARGING_MODE,
                SemInputConstants.Property.EPEN_WCHARGING),
        SPEN_COVER_TYPE(
                SemInputConstants.Command.SPEN_COVER_TYPE.toInt(), Spen.SET_SPEN_COVER_TYPE, null),
        SPEN_SAVING_MODE(
                SemInputConstants.Command.SPEN_SAVING_MODE.toInt(),
                Spen.SET_SPEN_SAVING_MODE,
                SemInputConstants.Property.EPEN_SAVING),
        SPEN_POWER(SemInputConstants.Command.SPEN_POWER.toInt(), Spen.SET_SPEN_POWER_MODE, null),
        SPEN_BLE_CHARGING(
                SemInputConstants.Command.SPEN_BLE_CHARGING.toInt(),
                Spen.SET_SPEN_BLE_CHARGING_MODE,
                SemInputConstants.Property.BLE_CHARGING),
        SPEN_SCREEN_OFF_MEMO(
                SemInputConstants.Command.SPEN_SCREEN_OFF_MEMO.toInt(),
                Spen.SET_SPEN_SCREEN_OFF_MEMO,
                SemInputConstants.Property.EPEN_MEMO),
        SPEN_PDCT_LOWSENSITIVITY(
                SemInputConstants.Command.SPEN_PDCT_LOWSENSITIVITY.toInt(),
                Spen.SET_SPEN_PDCT_LOWSENSITIVITY_ENABLE,
                null),
        SPEN_LOWCURRENT(
                SemInputConstants.Command.SPEN_LOWCURRENT.toInt(),
                Spen.SET_SPEN_LOWCURRENT_MODE,
                null),
        SPEN_SET_WIRELESS_CHARGER_TX_ID(
                SemInputConstants.Command.SPEN_SET_WIRELESS_CHARGER_TX_ID.toInt(),
                Spen.SET_SPEN_WIRELESS_CHARGING_TX_ID,
                null);

        private String name;
        SemInputConstants.Property property;
        private int value;

        Command(int value, String name, SemInputConstants.Property property) {
            this.value = value;
            this.name = name;
            this.property = property;
        }

        public int getInt() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public SemInputConstants.Property getProperty() {
            return this.property;
        }

        @Override // java.lang.Enum
        public String toString() {
            if (this.property != null) {
                return this.name + "(" + this.property.getNodeName() + ")";
            }
            return this.name;
        }
    }

    private void setCommands() {
        this.supportCommands.clear();
        for (Command command : Command.values()) {
            if (this.cmdlistSet.contains(command.getName()) || command.getProperty() != null) {
                this.supportCommands.put(Integer.valueOf(command.getInt()), command);
            }
        }
        this.stringBuilderForSupportCommands.delete(
                0, this.stringBuilderForSupportCommands.length());
        int length = 0;
        for (Command command2 : this.supportCommands.values()) {
            this.stringBuilderForSupportCommands.append(command2.toString());
            this.stringBuilderForSupportCommands.append(", ");
            length += command2.toString().length();
            if (length > 100) {
                this.stringBuilderForSupportCommands.append("\n     ");
                length = 0;
            }
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public int setProperty(
            SemInputConstants.Command command, String mode, SemInputCommandService.Result result) {
        Command spenCommand = this.supportCommands.get(Integer.valueOf(command.toInt()));
        if (spenCommand == null) {
            Log.d(this.TAG, "setProperty: not support cmd \"" + command + "\" at supportCommands");
            return -5;
        }
        if (this.cmdlistSet.contains(spenCommand.getName())) {
            runOnThread(new SetPropertyTask(spenCommand.getName() + "," + mode, result), result);
            return 1;
        }
        if (spenCommand.getProperty() == null) {
            Log.d(this.TAG, "setProperty: not support cmd \"" + spenCommand.getName() + "\"");
            return -5;
        }
        runOnThread(new SetPropertyTask(spenCommand.getProperty(), mode, result), result);
        return 1;
    }

    private final class SetPropertyTask implements Runnable {
        final String command;
        final SemInputConstants.Property property;
        final SemInputCommandService.Result result;
        final String time;

        public SetPropertyTask(String command, SemInputCommandService.Result result) {
            this.time = SemInputDumpsysData.getCurrentTimeString();
            this.property = SemInputConstants.Property.CMD;
            this.command = command;
            this.result = result;
        }

        public SetPropertyTask(
                SemInputConstants.Property property,
                String mode,
                SemInputCommandService.Result result) {
            this.time = SemInputDumpsysData.getCurrentTimeString();
            this.property = property;
            this.command = mode;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            Spen.this.waitUntilRecovery();
            Log.d(
                    Spen.this.TAG,
                    "SetPropertyTask: "
                            + this.property
                            + ","
                            + this.command
                            + " ["
                            + this.time
                            + "]");
            this.result.set(
                    Spen.this.sysinputHAL.setProperty(
                            Spen.this.devid, this.property, this.command));
            Spen.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] SetPropertyTask: " + this.property + "," + this.command;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    protected boolean supportSetProperty() {
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    protected boolean supportGetProperty() {
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    protected boolean supportRunCommand() {
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    protected boolean supportActivate() {
        return true;
    }
}
