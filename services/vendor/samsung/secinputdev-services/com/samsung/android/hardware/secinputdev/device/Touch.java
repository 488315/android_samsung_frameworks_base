package com.samsung.android.hardware.secinputdev.device;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputCommandService;
import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;
import com.samsung.android.hardware.secinputdev.SemInputFeatures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes.dex */
public class Touch extends SemInputDevice {
    private static final String SET_ALWAYS_LOW_POWER_MODE = "set_always_lpm";
    private static final String SET_AOD_ENABLE = "aod_enable";
    private static final String SET_AOD_NOTI_RECT = "set_aod_noti_rect";
    private static final String SET_AOD_RECT = "set_aod_rect";
    private static final String SET_AWD_MODE = "awd_mode";
    private static final String SET_BEZEL_ENABLE = "bezel_enable";
    private static final String SET_BODY_DETECTION = "set_body_detection";
    private static final String SET_BRUSH_ENABLE = "brush_enable";
    private static final String SET_CHARGER_MODE = "charger_mode";
    private static final String SET_CLEAR_COVER_MODE = "clear_cover_mode";
    private static final String SET_DOUBLE_TAP_TO_WAKE_UP = "aot_enable";
    private static final String SET_EAR_DETECT_ENABLE = "ear_detect_enable";
    private static final String SET_EXTERNAL_NOISE_MODE = "external_noise_mode";
    private static final String SET_FAST_RESPONSE = "set_fast_response";
    private static final String SET_FOD_ENABLE = "fod_enable";
    private static final String SET_FOD_ICON_VISIBLE = "fod_icon_visible";
    private static final String SET_FOD_LP_MODE = "fod_lp_mode";
    private static final String SET_FOD_RECT = "set_fod_rect";
    private static final String SET_FOLD_STATE = "set_fold_state";
    private static final String SET_FP_INT_CONTROL = "fp_int_control";
    private static final String SET_GAME_MODE = "set_game_mode";
    private static final String SET_GLOVE_MODE = "glove_mode";
    private static final String SET_GRIP_DATA = "set_grip_data";
    private static final String SET_LCD_ORIENTATION = "lcd_orientation";
    private static final String SET_LOW_SENSITIVITY_MODE_ENABLE = "low_sensitivity_mode_enable";
    private static final String SET_NFC_MODE = "nfc_rf_field_mode";
    private static final String SET_NOTE_MODE = "set_note_mode";
    private static final String SET_POCKET_MODE_ENABLE = "pocket_mode_enable";
    private static final String SET_PROX_LP_SCAN_MODE = "prox_lp_scan_mode";
    private static final String SET_REFRESH_RATE_MODE = "refresh_rate_mode";
    private static final String SET_SCAN_RATE = "set_scan_rate";
    private static final String SET_SINGLETAP_ENABLE = "singletap_enable";
    private static final String SET_SIP_MODE = "set_sip_mode";
    private static final String SET_SPAY_ENABLE = "spay_enable";
    private static final String SET_STYLUS_ENABLE = "stylus_enable";
    private static final String SET_SYNC_CHANGED = "sync_changed";
    private static final String SET_TEMPERATURE = "set_temperature";
    private static final String SET_TOUCHABLE_AREA = "set_touchable_area";
    private static final String SET_TWO_FINGER_DOUBLETAP_MODE = "two_finger_doubletap_enable";
    private static final String SET_WIRELESS_CHARGER_MODE = "set_wirelesscharger_mode";
    private final StringBuilder stringBuilderForSupportCommands;
    private final Map<Integer, String> supportCommands;

    public Touch(String name, int devid, int feature, String cmdlist) {
        super(name, devid, feature, cmdlist);
        this.supportCommands = new HashMap();
        this.stringBuilderForSupportCommands = new StringBuilder();
        if (!SemInputFeatures.USE_CMDTHREAD) {
            this.pendingQueue = new LinkedList();
        }
        setCommands();
        Log.i(this.TAG, "supportCommands: " + this.supportCommands);
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public String getSupportCommands() {
        return "supportCommands: " + this.stringBuilderForSupportCommands.toString();
    }

    private enum Command {
        GAME(SemInputConstants.Command.GAME.toInt(), Touch.SET_GAME_MODE),
        SCAN_RATE(SemInputConstants.Command.SCAN_RATE.toInt(), Touch.SET_SCAN_RATE),
        FAST_RESPONSE(SemInputConstants.Command.FAST_RESPONSE.toInt(), Touch.SET_FAST_RESPONSE),
        REFRESH_RATE(SemInputConstants.Command.REFRESH_RATE.toInt(), Touch.SET_REFRESH_RATE_MODE),
        GLOVE(SemInputConstants.Command.GLOVE.toInt(), Touch.SET_GLOVE_MODE),
        CLEAR_COVER(SemInputConstants.Command.CLEAR_COVER.toInt(), Touch.SET_CLEAR_COVER_MODE),
        ORIENTATION(SemInputConstants.Command.ORIENTATION.toInt(), Touch.SET_LCD_ORIENTATION),
        PROX_LP_SCAN(SemInputConstants.Command.PROX_LP_SCAN.toInt(), Touch.SET_PROX_LP_SCAN_MODE),
        GRIP_DATA(SemInputConstants.Command.GRIP_DATA.toInt(), Touch.SET_GRIP_DATA),
        SIP(SemInputConstants.Command.SIP.toInt(), Touch.SET_SIP_MODE),
        NOTE_APP(SemInputConstants.Command.NOTE_APP.toInt(), Touch.SET_NOTE_MODE),
        TEMPERATURE(SemInputConstants.Command.TEMPERATURE.toInt(), Touch.SET_TEMPERATURE),
        SPAY(SemInputConstants.Command.SPAY.toInt(), Touch.SET_SPAY_ENABLE),
        STYLUS(SemInputConstants.Command.STYLUS.toInt(), Touch.SET_STYLUS_ENABLE),
        BRUSH(SemInputConstants.Command.BRUSH.toInt(), Touch.SET_BRUSH_ENABLE),
        AOD_RECT(SemInputConstants.Command.AOD_RECT.toInt(), Touch.SET_AOD_RECT),
        AOD_NOTI_RECT(SemInputConstants.Command.AOD_NOTI_RECT.toInt(), Touch.SET_AOD_NOTI_RECT),
        AOD(SemInputConstants.Command.AOD.toInt(), Touch.SET_AOD_ENABLE),
        FOD(SemInputConstants.Command.FOD.toInt(), Touch.SET_FOD_ENABLE),
        FOD_ICON_VISIBLE(
                SemInputConstants.Command.FOD_ICON_VISIBLE.toInt(), Touch.SET_FOD_ICON_VISIBLE),
        FOD_RECT(SemInputConstants.Command.FOD_RECT.toInt(), Touch.SET_FOD_RECT),
        FOD_LP(SemInputConstants.Command.FOD_LP.toInt(), Touch.SET_FOD_LP_MODE),
        SINGLETAP(SemInputConstants.Command.SINGLETAP.toInt(), Touch.SET_SINGLETAP_ENABLE),
        EAR_DETECT(SemInputConstants.Command.EAR_DETECT.toInt(), Touch.SET_EAR_DETECT_ENABLE),
        EXTERNAL_NOISE(
                SemInputConstants.Command.EXTERNAL_NOISE.toInt(), Touch.SET_EXTERNAL_NOISE_MODE),
        TOUCHABLE_AREA(SemInputConstants.Command.TOUCHABLE_AREA.toInt(), Touch.SET_TOUCHABLE_AREA),
        FP_INT_CONTROL(SemInputConstants.Command.FP_INT_CONTROL.toInt(), Touch.SET_FP_INT_CONTROL),
        SYNC_CHANGED(SemInputConstants.Command.SYNC_CHANGED.toInt(), Touch.SET_SYNC_CHANGED),
        POCKET_MODE(SemInputConstants.Command.POCKET_MODE.toInt(), Touch.SET_POCKET_MODE_ENABLE),
        LOW_SENSITIVITY(
                SemInputConstants.Command.LOW_SENSITIVITY.toInt(),
                Touch.SET_LOW_SENSITIVITY_MODE_ENABLE),
        CHARGER(SemInputConstants.Command.CHARGER.toInt(), Touch.SET_CHARGER_MODE),
        AOT(SemInputConstants.Command.AOT.toInt(), Touch.SET_DOUBLE_TAP_TO_WAKE_UP),
        FOLD_STATE(SemInputConstants.Command.FOLD_STATE.toInt(), Touch.SET_FOLD_STATE),
        WIRELESS_CHARGER(
                SemInputConstants.Command.WIRELESS_CHARGER.toInt(),
                Touch.SET_WIRELESS_CHARGER_MODE),
        TWO_FINGER_DOUBLETAP(
                SemInputConstants.Command.TWO_FINGER_DOUBLETAP.toInt(),
                Touch.SET_TWO_FINGER_DOUBLETAP_MODE),
        ALWAYS_LOW_POWER_MODE(
                SemInputConstants.Command.ALWAYS_LOW_POWER_MODE.toInt(),
                Touch.SET_ALWAYS_LOW_POWER_MODE),
        BEZEL(SemInputConstants.Command.BEZEL.toInt(), Touch.SET_BEZEL_ENABLE),
        BODY_DETECTION(SemInputConstants.Command.BODY_DETECTION.toInt(), Touch.SET_BODY_DETECTION),
        AWD(SemInputConstants.Command.AWD.toInt(), Touch.SET_AWD_MODE),
        NFC_MODE(SemInputConstants.Command.NFC_FIELD.toInt(), Touch.SET_NFC_MODE);

        private String name;
        private int value;

        Command(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getInt() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }

    private void setCommands() {
        this.supportCommands.clear();
        for (Command command : Command.values()) {
            if (this.cmdlistSet.contains(command.getName())) {
                this.supportCommands.put(Integer.valueOf(command.getInt()), command.getName());
            }
        }
        this.stringBuilderForSupportCommands.delete(
                0, this.stringBuilderForSupportCommands.length());
        int length = 0;
        for (String name : this.supportCommands.values()) {
            this.stringBuilderForSupportCommands.append(name);
            this.stringBuilderForSupportCommands.append(", ");
            length += name.length();
            if (length > 100) {
                this.stringBuilderForSupportCommands.append("\n     ");
                length = 0;
            }
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public int setProperty(
            SemInputConstants.Command command, String mode, SemInputCommandService.Result result) {
        String commandName = this.supportCommands.get(Integer.valueOf(command.toInt()));
        if (commandName == null) {
            Log.d(this.TAG, "setProperty: not support cmd \"" + command + "\" at supportCommands");
            return -5;
        }
        runOnThread(new SetPropertyTask(commandName + "," + mode, result), result);
        return 1;
    }

    private final class SetPropertyTask implements Runnable {
        final String command;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public SetPropertyTask(String command, SemInputCommandService.Result result) {
            this.command = command;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            Touch.this.waitUntilRecovery();
            Log.d(
                    Touch.this.TAG,
                    "SetPropertyTask: "
                            + SemInputConstants.Property.CMD
                            + ","
                            + this.command
                            + " ["
                            + this.time
                            + "]");
            this.result.set(
                    Touch.this.sysinputHAL.setProperty(
                            Touch.this.devid, SemInputConstants.Property.CMD, this.command));
            Touch.this.removeTask(this);
        }

        public String toString() {
            return "["
                    + this.time
                    + "] SetPropertyTask: "
                    + SemInputConstants.Property.CMD
                    + ","
                    + this.command;
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

    public int streamRawdata(int mode) {
        SemInputCommandService.Result result = new SemInputCommandService.Result();
        runOnThread(new StreamRawdataTask(mode, result), result);
        return 1;
    }

    private final class StreamRawdataTask implements Runnable {
        final int mode;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public StreamRawdataTask(int mode, SemInputCommandService.Result result) {
            this.mode = mode;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            Touch.this.waitUntilRecovery();
            Log.d(Touch.this.TAG, "StreamRawdataTask: " + this.mode + " [" + this.time + "]");
            this.result.set(Touch.this.sysinputHAL.streamRawdata(Touch.this.devid, this.mode));
            Touch.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] StreamRawdataTask: " + this.mode;
        }
    }
}
