package com.android.server.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceVolumeManager;
import android.media.AudioManager;
import android.media.VolumeInfo;
import android.os.Binder;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioManagerShellCommand extends ShellCommand {
    public final AudioService mService;

    public AudioManagerShellCommand(AudioService audioService) {
        this.mService = audioService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onCommand(String str) {
        char c;
        char c2;
        int i;
        long clearCallingIdentity;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        getOutPrintWriter();
        switch (str.hashCode()) {
            case -1934803544:
                if (str.equals("set-ringer-mode")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1873164504:
                if (str.equals("set-encoded-surround-mode")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1741904872:
                if (str.equals("adj-unmute")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1712382208:
                if (str.equals("adj-volume")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1460727410:
                if (str.equals("adj-group-volume")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1340000401:
                if (str.equals("set-surround-format-enabled")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1313475149:
                if (str.equals("set-group-volume")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1270938566:
                if (str.equals("clearScpm")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1110422657:
                if (str.equals("adj-mute")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -460075795:
                if (str.equals("reset-sound-dose-timeout")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 478371910:
                if (str.equals("set-device-volume")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 865963338:
                if (str.equals("set-sound-dose-value")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 937504014:
                if (str.equals("get-is-surround-format-enabled")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1247414261:
                if (str.equals("sendScpm")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1476886742:
                if (str.equals("get-sound-dose-value")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1578511132:
                if (str.equals("get-encoded-surround-mode")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1781088997:
                if (str.equals("set-volume")) {
                    c = 16;
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
                    getErrPrintWriter().println("Error: no ringer mode specified");
                } else {
                    switch (nextArg.hashCode()) {
                        case -1986416409:
                            if (nextArg.equals("NORMAL")) {
                                c2 = 0;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -1848997803:
                            if (nextArg.equals("SILENT")) {
                                c2 = 1;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1169293647:
                            if (nextArg.equals("VIBRATE")) {
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
                            i = 2;
                            break;
                        case 1:
                            i = 0;
                            break;
                        case 2:
                            i = 1;
                            break;
                        default:
                            i = -1;
                            break;
                    }
                    if (AudioManager.isValidRingerMode(i)) {
                        ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class)).setRingerModeInternal(i);
                        return 0;
                    }
                    getErrPrintWriter().println("Error: invalid value of ringerMode, should be one of NORMAL|SILENT|VIBRATE");
                }
                return 1;
            case 1:
                String nextArg2 = getNextArg();
                if (nextArg2 == null) {
                    getErrPrintWriter().println("Error: no encodedSurroundMode specified");
                } else {
                    try {
                        int parseInt = Integer.parseInt(nextArg2);
                        if (parseInt >= 0) {
                            ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class)).setEncodedSurroundMode(parseInt);
                            return 0;
                        }
                        getErrPrintWriter().println("Error: invalid value of encodedSurroundMode");
                    } catch (NumberFormatException unused) {
                        getErrPrintWriter().println("Error: wrong format specified for encoded surround mode");
                    }
                }
                return 1;
            case 2:
                AudioManager audioManager = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg = readIntArg();
                ActiveServices$$ExternalSyntheticOutline0.m(readIntArg, getOutPrintWriter(), "calling AudioManager.adjustStreamVolume(", ", AudioManager.ADJUST_UNMUTE, 0)");
                audioManager.adjustStreamVolume(readIntArg, 100, 0);
                return 0;
            case 3:
                AudioManager audioManager2 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg2 = readIntArg();
                int readDirectionArg = readDirectionArg();
                getOutPrintWriter().println(DualAppManagerService$$ExternalSyntheticOutline0.m(readIntArg2, readDirectionArg, "calling AudioManager.adjustStreamVolume(", ", ", ", 0)"));
                audioManager2.adjustStreamVolume(readIntArg2, readDirectionArg, 0);
                return 0;
            case 4:
                AudioManager audioManager3 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg3 = readIntArg();
                int readDirectionArg2 = readDirectionArg();
                getOutPrintWriter().println(DualAppManagerService$$ExternalSyntheticOutline0.m(readIntArg3, readDirectionArg2, "calling AudioManager.adjustVolumeGroupVolume(", ", ", ", 0)"));
                audioManager3.adjustVolumeGroupVolume(readIntArg3, readDirectionArg2, 0);
                return 0;
            case 5:
                String nextArg3 = getNextArg();
                String nextArg4 = getNextArg();
                if (nextArg3 == null) {
                    getErrPrintWriter().println("Error: no surroundFormat specified");
                } else if (nextArg4 == null) {
                    getErrPrintWriter().println("Error: no enabled value for surroundFormat specified");
                } else {
                    try {
                        int parseInt2 = Integer.parseInt(nextArg3);
                        boolean parseBoolean = Boolean.parseBoolean(nextArg4);
                        if (parseInt2 >= 0) {
                            ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class)).setSurroundFormatEnabled(parseInt2, parseBoolean);
                            return 0;
                        }
                        getErrPrintWriter().println("Error: invalid value of surroundFormat");
                    } catch (NumberFormatException unused2) {
                        getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
                    }
                }
                return 1;
            case 6:
                AudioManager audioManager4 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg4 = readIntArg();
                int readIntArg5 = readIntArg();
                getOutPrintWriter().println(DualAppManagerService$$ExternalSyntheticOutline0.m(readIntArg4, readIntArg5, "calling AudioManager.setVolumeGroupVolumeIndex(", ", ", ", 0)"));
                audioManager4.setVolumeGroupVolumeIndex(readIntArg4, readIntArg5, 0);
                return 0;
            case 7:
                if (!SystemProperties.getBoolean("ro.product_ship", true)) {
                    Context context = this.mService.mContext;
                    Intent intent = new Intent(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
                    intent.addFlags(67108864);
                    intent.addFlags(268435456);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        context.sendBroadcastAsUser(intent, UserHandle.ALL, null, null);
                    } finally {
                    }
                }
                return 0;
            case '\b':
                AudioManager audioManager5 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg6 = readIntArg();
                ActiveServices$$ExternalSyntheticOutline0.m(readIntArg6, getOutPrintWriter(), "calling AudioManager.adjustStreamVolume(", ", AudioManager.ADJUST_MUTE, 0)");
                audioManager5.adjustStreamVolume(readIntArg6, -100, 0);
                return 0;
            case '\t':
                ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class)).setCsd(-1.0f);
                getOutPrintWriter().println("Reset sound dose momentary exposure timeout");
                return 0;
            case '\n':
                AudioDeviceVolumeManager audioDeviceVolumeManager = (AudioDeviceVolumeManager) this.mService.mContext.getSystemService("audio_device_volume");
                int readIntArg7 = readIntArg();
                int readIntArg8 = readIntArg();
                int readIntArg9 = readIntArg();
                VolumeInfo build = new VolumeInfo.Builder(readIntArg7).setVolumeIndex(readIntArg8).build();
                AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(readIntArg9, "foo");
                getOutPrintWriter().println("calling AudioDeviceVolumeManager.setDeviceVolume(" + build + ", " + audioDeviceAttributes + ")");
                audioDeviceVolumeManager.setDeviceVolume(build, audioDeviceAttributes);
                return 0;
            case 11:
                String nextArg5 = getNextArg();
                if (nextArg5 == null) {
                    getErrPrintWriter().println("Error: no sound dose value specified");
                } else {
                    try {
                        float parseFloat = Float.parseFloat(nextArg5);
                        if (parseFloat >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                            ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class)).setCsd(parseFloat);
                            return 0;
                        }
                        getErrPrintWriter().println("Error: invalid value of sound dose");
                    } catch (NumberFormatException unused3) {
                        getErrPrintWriter().println("Error: wrong format specified for sound dose");
                    }
                }
                return 1;
            case '\f':
                String nextArg6 = getNextArg();
                if (nextArg6 == null) {
                    getErrPrintWriter().println("Error: no surroundFormat specified");
                } else {
                    try {
                        int parseInt3 = Integer.parseInt(nextArg6);
                        if (parseInt3 >= 0) {
                            AudioManager audioManager6 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                            PrintWriter outPrintWriter = getOutPrintWriter();
                            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(parseInt3, "Value of enabled for ", " is: ");
                            m.append(audioManager6.isSurroundFormatEnabled(parseInt3));
                            outPrintWriter.println(m.toString());
                            return 0;
                        }
                        getErrPrintWriter().println("Error: invalid value of surroundFormat");
                    } catch (NumberFormatException unused4) {
                        getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
                    }
                }
                return 1;
            case '\r':
                if (!SystemProperties.getBoolean("ro.product_ship", true)) {
                    Context context2 = this.mService.mContext;
                    Intent intent2 = new Intent("com.samsung.android.scpm.policy.UPDATE.Audio");
                    intent2.addFlags(67108864);
                    intent2.addFlags(268435456);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        context2.sendBroadcastAsUser(intent2, UserHandle.ALL, null, null);
                    } finally {
                    }
                }
                return 0;
            case 14:
                AudioManager audioManager7 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                getOutPrintWriter().println("Sound dose value: " + audioManager7.getCsd());
                return 0;
            case 15:
                AudioManager audioManager8 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                getOutPrintWriter().println("Encoded surround mode: " + audioManager8.getEncodedSurroundMode());
                return 0;
            case 16:
                AudioManager audioManager9 = (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
                int readIntArg10 = readIntArg();
                int readIntArg11 = readIntArg();
                getOutPrintWriter().println(DualAppManagerService$$ExternalSyntheticOutline0.m(readIntArg10, readIntArg11, "calling AudioManager.setStreamVolume(", ", ", ", 0)"));
                audioManager9.setStreamVolume(readIntArg10, readIntArg11, 0);
                return 0;
            default:
                return 0;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Audio manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-surround-format-enabled SURROUND_FORMAT IS_ENABLED");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Enables/disabled the SURROUND_FORMAT based on IS_ENABLED", "  get-is-surround-format-enabled SURROUND_FORMAT", "    Returns if the SURROUND_FORMAT is enabled", "  set-encoded-surround-mode SURROUND_SOUND_MODE");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets the encoded surround sound mode to SURROUND_SOUND_MODE", "  get-encoded-surround-mode", "    Returns the encoded surround sound mode", "  set-sound-dose-value");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets the current sound dose value", "  get-sound-dose-value", "    Returns the current sound dose value", "  reset-sound-dose-timeout");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Resets the sound dose timeout used for momentary exposure", "  set-ringer-mode NORMAL|SILENT|VIBRATE", "    Sets the Ringer mode to one of NORMAL|SILENT|VIBRATE", "  set-volume STREAM_TYPE VOLUME_INDEX");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets the volume for STREAM_TYPE to VOLUME_INDEX", "  set-device-volume STREAM_TYPE VOLUME_INDEX NATIVE_DEVICE_TYPE", "    Sets for NATIVE_DEVICE_TYPE the STREAM_TYPE volume to VOLUME_INDEX", "  adj-mute STREAM_TYPE");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    mutes the STREAM_TYPE", "  adj-unmute STREAM_TYPE", "    unmutes the STREAM_TYPE", "  adj-volume STREAM_TYPE <RAISE|LOWER|MUTE|UNMUTE>");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Adjusts the STREAM_TYPE volume given the specified direction", "  set-group-volume GROUP_ID VOLUME_INDEX", "    Sets the volume for GROUP_ID to VOLUME_INDEX", "  adj-group-volume GROUP_ID <RAISE|LOWER|MUTE|UNMUTE>");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Adjusts the group volume for GROUP_ID given the specified direction", "  sendScpm", "  clearScpm");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int readDirectionArg() {
        char c;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no argument provided");
            throw new IllegalArgumentException("No argument provided");
        }
        switch (nextArg.hashCode()) {
            case -1787076558:
                if (nextArg.equals("UNMUTE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2378265:
                if (nextArg.equals("MUTE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 72626913:
                if (nextArg.equals("LOWER")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 77737932:
                if (nextArg.equals("RAISE")) {
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
                return 100;
            case 1:
                return -100;
            case 2:
                return -1;
            case 3:
                return 1;
            default:
                throw new IllegalArgumentException("Wrong direction argument: ".concat(nextArg));
        }
    }

    public final int readIntArg() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no argument provided");
            throw new IllegalArgumentException("No argument provided");
        }
        try {
            return Integer.parseInt(nextArg);
        } catch (NumberFormatException unused) {
            this.getErrPrintWriter().println("Error: wrong format for argument ".concat(nextArg));
            throw new IllegalArgumentException("Wrong format for argument ".concat(nextArg));
        }
    }
}
