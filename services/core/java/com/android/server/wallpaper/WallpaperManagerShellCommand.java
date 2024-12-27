package com.android.server.wallpaper;

import android.os.ShellCommand;

import java.io.PrintWriter;

public final class WallpaperManagerShellCommand extends ShellCommand {
    public final WallpaperManagerService mService;

    public WallpaperManagerShellCommand(WallpaperManagerService wallpaperManagerService) {
        this.mService = wallpaperManagerService;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0039, code lost:

       if (r5.equals("dim-with-uid") == false) goto L8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 1
            if (r5 != 0) goto L8
            r4.onHelp()
            return r1
        L8:
            r2 = -1
            int r3 = r5.hashCode()
            switch(r3) {
                case -1462105208: goto L3c;
                case -296046994: goto L33;
                case 1499: goto L28;
                case 3198785: goto L1d;
                case 309630996: goto L12;
                default: goto L10;
            }
        L10:
            r1 = r2
            goto L47
        L12:
            java.lang.String r1 = "get-dim-amount"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L1b
            goto L10
        L1b:
            r1 = 4
            goto L47
        L1d:
            java.lang.String r1 = "help"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L26
            goto L10
        L26:
            r1 = 3
            goto L47
        L28:
            java.lang.String r1 = "-h"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L31
            goto L10
        L31:
            r1 = 2
            goto L47
        L33:
            java.lang.String r3 = "dim-with-uid"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L47
            goto L10
        L3c:
            java.lang.String r1 = "set-dim-amount"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L46
            goto L10
        L46:
            r1 = r0
        L47:
            switch(r1) {
                case 0: goto La2;
                case 1: goto L6f;
                case 2: goto L6b;
                case 3: goto L6b;
                case 4: goto L4f;
                default: goto L4a;
            }
        L4a:
            int r4 = r4.handleDefaultCommands(r5)
            return r4
        L4f:
            com.android.server.wallpaper.WallpaperManagerService r5 = r4.mService
            float r5 = r5.getWallpaperDimAmount()
            java.io.PrintWriter r4 = r4.getOutPrintWriter()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "The current wallpaper dim amount is: "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r4.println(r5)
            return r0
        L6b:
            r4.onHelp()
            return r0
        L6f:
            java.lang.String r5 = r4.getNextArgRequired()
            int r5 = java.lang.Integer.parseInt(r5)
            java.lang.String r1 = r4.getNextArgRequired()
            float r1 = java.lang.Float.parseFloat(r1)
            com.android.server.wallpaper.WallpaperManagerService r2 = r4.mService
            r2.setWallpaperDimAmountForUid(r1, r5)
            java.io.PrintWriter r4 = r4.getOutPrintWriter()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Dimming the wallpaper for UID: "
            r2.<init>(r3)
            r2.append(r5)
            java.lang.String r5 = " to: "
            r2.append(r5)
            r2.append(r1)
            java.lang.String r5 = r2.toString()
            r4.println(r5)
            return r0
        La2:
            java.lang.String r5 = r4.getNextArgRequired()
            float r5 = java.lang.Float.parseFloat(r5)
            com.android.server.wallpaper.WallpaperManagerService r1 = r4.mService     // Catch: android.os.RemoteException -> Lb0
            r1.setWallpaperDimAmount(r5)     // Catch: android.os.RemoteException -> Lb0
            goto Lb7
        Lb0:
            java.lang.String r1 = "WallpaperManagerShellCommand"
            java.lang.String r2 = "Can't set wallpaper dim amount"
            android.util.Log.e(r1, r2)
        Lb7:
            java.io.PrintWriter r4 = r4.getOutPrintWriter()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Dimming the wallpaper to: "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r4.println(r5)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperManagerShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Wallpaper manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-dim-amount DIMMING");
        outPrintWriter.println(
                "    Sets the current dimming value to DIMMING (a number between 0 and 1).");
        outPrintWriter.println();
        outPrintWriter.println("  dim-with-uid UID DIMMING");
        outPrintWriter.println(
                "    Sets the wallpaper dim amount to DIMMING as if an app with uid, UID, called"
                    + " it.");
        outPrintWriter.println();
        outPrintWriter.println("  get-dim-amount");
        outPrintWriter.println("    Get the current wallpaper dim amount.");
    }
}
