package com.android.server.media;

import android.media.AudioSystem;
import android.media.IAudioService;
import android.os.ServiceManager;
import android.util.AndroidException;

/* loaded from: classes2.dex */
public abstract class VolumeCtrl {
    public static final String USAGE = new String("the options are as follows: \n\t\t--stream STREAM selects the stream to control, see AudioManager.STREAM_*\n\t\t                controls AudioManager.STREAM_MUSIC if no stream is specified\n\t\t--set INDEX     sets the volume index value\n\t\t--adj DIRECTION adjusts the volume, use raise|same|lower for the direction\n\t\t--get           outputs the current volume\n\t\t--show          shows the UI during the volume change\n\texamples:\n\t\tadb shell media_session volume --show --stream 3 --set 11\n\t\tadb shell media_session volume --stream 0 --adj lower\n\t\tadb shell media_session volume --stream 3 --get\n\n\t\tSamsung custom options are as follows: \n\t\t--setfine INDEX sets the fine volume index value\n\t\t--getfine       outputs the current fine volume\n\texamples:\n\t\tadb shell media_session volume --show --setfine 127\n\t\tadb shell media_session volume --getfine\n");

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void run(MediaShellCommand mediaShellCommand) {
        int i;
        IAudioService asInterface;
        String str;
        char c;
        char c2;
        String str2 = null;
        int i2 = 3;
        int i3 = 5;
        char c3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            String nextOption = mediaShellCommand.getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -923461764:
                        if (nextOption.equals("--setfine")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 42995463:
                        if (nextOption.equals("--adj")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 43001270:
                        if (nextOption.equals("--get")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 43012802:
                        if (nextOption.equals("--set")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1064856566:
                        if (nextOption.equals("--device")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1311395952:
                        if (nextOption.equals("--getfine")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1333399709:
                        if (nextOption.equals("--show")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1508023584:
                        if (nextOption.equals("--stream")) {
                            c2 = 7;
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
                        i3 = Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log("[V]", "will set fine volume");
                        z3 = true;
                        break;
                    case 1:
                        str2 = mediaShellCommand.getNextArgRequired();
                        mediaShellCommand.log("[V]", "will adjust volume");
                        c3 = 2;
                        break;
                    case 2:
                        mediaShellCommand.log("[V]", "will get volume");
                        z = true;
                        break;
                    case 3:
                        i3 = Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log("[V]", "will set volume to index=" + i3);
                        c3 = (char) 1;
                        break;
                    case 4:
                        i4 = Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log("[V]", "will set device");
                        break;
                    case 5:
                        mediaShellCommand.log("[V]", "will get fine volume");
                        z2 = true;
                        break;
                    case 6:
                        i5 = 1;
                        break;
                    case 7:
                        i2 = Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log("[V]", "will control stream=" + i2 + " (" + streamName(i2) + ")");
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown argument " + nextOption);
                }
            } else {
                if (c3 == 2) {
                    if (str2 == null) {
                        mediaShellCommand.showError("Error: no valid volume adjustment (null)");
                        return;
                    }
                    switch (str2.hashCode()) {
                        case 3522662:
                            if (str2.equals("same")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 103164673:
                            if (str2.equals("lower")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 108275692:
                            if (str2.equals("raise")) {
                                c = 2;
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
                            i = 0;
                            break;
                        case 1:
                            i = -1;
                            break;
                        case 2:
                            break;
                        default:
                            mediaShellCommand.showError("Error: no valid volume adjustment, was " + str2 + ", expected lower|same|raise");
                            return;
                    }
                    mediaShellCommand.log("[V]", "Connecting to AudioService");
                    asInterface = IAudioService.Stub.asInterface(ServiceManager.checkService("audio"));
                    if (asInterface != null) {
                        mediaShellCommand.log("[E]", "Error type 2");
                        throw new AndroidException("Can't connect to audio service; is the system running?");
                    }
                    if (c3 == 1 && (i3 > asInterface.getStreamMaxVolume(i2) || i3 < asInterface.getStreamMinVolume(i2))) {
                        mediaShellCommand.showError(String.format("Error: invalid volume index %d for stream %d (should be in [%d..%d])", Integer.valueOf(i3), Integer.valueOf(i2), Integer.valueOf(asInterface.getStreamMinVolume(i2)), Integer.valueOf(asInterface.getStreamMaxVolume(i2))));
                        return;
                    }
                    String name = mediaShellCommand.getClass().getPackage().getName();
                    if (c3 != 1) {
                        str = "[V]";
                        if (c3 == 2) {
                            asInterface.adjustStreamVolume(i2, i, i5, name);
                        }
                    } else if (i4 != 0) {
                        str = "[V]";
                        asInterface.setStreamVolumeForDeviceWithAttribution(i2, i3, i5, name, (String) null, i4);
                    } else {
                        str = "[V]";
                        asInterface.setStreamVolume(i2, i3, i5, name);
                    }
                    if (z) {
                        if (i4 != 0) {
                            mediaShellCommand.log(str, "device : " + AudioSystem.getOutputDeviceName(i4) + " stream(" + streamName(i2) + ") volume is " + AudioSystem.getStreamVolumeIndex(i2, i4));
                        } else {
                            mediaShellCommand.log(str, "volume is " + asInterface.getStreamVolume(i2) + " in range [" + asInterface.getStreamMinVolume(i2) + ".." + asInterface.getStreamMaxVolume(i2) + "]");
                        }
                    }
                    if (z3) {
                        int i6 = i5 | 1048576;
                        if (i3 > asInterface.getStreamMaxVolume(3) * 10 || i3 < 0) {
                            mediaShellCommand.showError(String.format("Error: invalid volume index %d(should be in [0..%d])", Integer.valueOf(i3), Integer.valueOf(asInterface.getStreamMaxVolume(3) * 10)));
                            return;
                        } else {
                            asInterface.setFineVolume(3, i3, i6, 0, name);
                            return;
                        }
                    }
                    if (z2) {
                        mediaShellCommand.log(str, "fine volume is " + asInterface.getFineVolume(3, 0));
                        return;
                    }
                    return;
                }
                i = 1;
                mediaShellCommand.log("[V]", "Connecting to AudioService");
                asInterface = IAudioService.Stub.asInterface(ServiceManager.checkService("audio"));
                if (asInterface != null) {
                }
            }
        }
    }

    public static String streamName(int i) {
        try {
            return AudioSystem.STREAM_NAMES[i];
        } catch (ArrayIndexOutOfBoundsException unused) {
            return "invalid stream";
        }
    }
}
