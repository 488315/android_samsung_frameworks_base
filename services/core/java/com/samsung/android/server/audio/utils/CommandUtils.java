package com.samsung.android.server.audio.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import java.io.PrintWriter;
import java.util.function.Function;

/* loaded from: classes2.dex */
public abstract class CommandUtils {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0075, code lost:
    
        if (r6 == 1) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0079, code lost:
    
        android.media.AudioSystem.setParameters(r13[2]);
        r12.println("  Success to set [" + r13[2] + "]");
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x016c A[Catch: IndexOutOfBoundsException | NumberFormatException -> 0x018c, IndexOutOfBoundsException | NumberFormatException -> 0x018c, TRY_LEAVE, TryCatch #0 {IndexOutOfBoundsException | NumberFormatException -> 0x018c, blocks: (B:3:0x0001, B:6:0x0015, B:6:0x0015, B:14:0x004f, B:14:0x004f, B:19:0x0054, B:19:0x0054, B:20:0x0059, B:20:0x0059, B:28:0x0079, B:28:0x0079, B:29:0x009a, B:29:0x009a, B:31:0x00a8, B:31:0x00a8, B:32:0x00c4, B:32:0x00c4, B:33:0x0064, B:33:0x0064, B:36:0x006c, B:36:0x006c, B:39:0x00e5, B:39:0x00e5, B:48:0x0107, B:48:0x0107, B:49:0x010d, B:49:0x010d, B:55:0x0140, B:55:0x0140, B:56:0x0146, B:56:0x0146, B:57:0x0111, B:57:0x0111, B:60:0x011b, B:60:0x011b, B:63:0x0125, B:63:0x0125, B:66:0x012f, B:66:0x012f, B:69:0x016c, B:69:0x016c, B:70:0x00f0, B:70:0x00f0, B:73:0x00f8, B:73:0x00f8, B:76:0x001d, B:76:0x001d, B:79:0x0025, B:79:0x0025, B:82:0x002f, B:82:0x002f), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean handleCustomCommand(PrintWriter printWriter, String[] strArr, Context context, Function function) {
        char c;
        boolean z;
        try {
            String str = strArr[0];
            char c2 = 65535;
            switch (str.hashCode()) {
                case -2139970140:
                    if (str.equals("safe-media")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1165733461:
                    if (str.equals("acparam")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -859602134:
                    if (str.equals("volumepanel")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1954998432:
                    if (str.equals("mediapanel")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                String str2 = strArr[1];
                int hashCode = str2.hashCode();
                if (hashCode != 43001270) {
                    if (hashCode == 43012802 && str2.equals("--set")) {
                        z = true;
                        if (z) {
                            printWriter.println("  mSafeMediaVolumeState = " + ((String) function.apply(-1)));
                        } else {
                            if (!z) {
                                return false;
                            }
                            String str3 = strArr[2];
                            switch (str3.hashCode()) {
                                case 48:
                                    if (str3.equals("0")) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case 49:
                                    if (str3.equals("1")) {
                                        c2 = 1;
                                        break;
                                    }
                                    break;
                                case 50:
                                    if (str3.equals("2")) {
                                        c2 = 2;
                                        break;
                                    }
                                    break;
                                case 51:
                                    if (str3.equals("3")) {
                                        c2 = 3;
                                        break;
                                    }
                                    break;
                            }
                            if (c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3) {
                                printWriter.println("  Success to set " + ((String) function.apply(Integer.valueOf(Integer.parseInt(strArr[2])))));
                            } else {
                                printWriter.println("  Warning : Only numbers between 0 and 3 are allowed.");
                            }
                        }
                    }
                    z = -1;
                    if (z) {
                    }
                } else {
                    if (str2.equals("--get")) {
                        z = false;
                        if (z) {
                        }
                    }
                    z = -1;
                    if (z) {
                    }
                }
            } else if (c == 1) {
                String str4 = strArr[1];
                int hashCode2 = str4.hashCode();
                if (hashCode2 != 43001270) {
                    if (hashCode2 == 43012802 && str4.equals("--set")) {
                        c2 = 1;
                    }
                } else if (str4.equals("--get")) {
                    c2 = 0;
                }
                String parameters = AudioSystem.getParameters(strArr[2]);
                if ("".equals(parameters)) {
                    printWriter.println("  Warning: " + strArr[2] + " is Unknown arguments");
                } else {
                    printWriter.println("KEY : " + strArr[2] + "\nVALUE : " + parameters);
                }
            } else if (c == 2) {
                executePanel(context, "mediapanel");
            } else {
                if (c != 3) {
                    return false;
                }
                executePanel(context, "volumepanel");
            }
            return true;
        } catch (IndexOutOfBoundsException | NumberFormatException unused) {
            printWriter.println("  Warning : command requires the next arguments");
            return false;
        }
    }

    public static void executePanel(Context context, String str) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.panel.SettingsPanelActivity"));
        if ("mediapanel".equals(str)) {
            intent.setAction("com.android.settings.panel.action.MEDIA_OUTPUT");
        } else if ("volumepanel".equals(str)) {
            intent.setAction("android.settings.panel.action.VOLUME");
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
