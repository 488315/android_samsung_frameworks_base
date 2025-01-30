package com.android.wm.shell.compatui;

import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CompatUIShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final ShellCommandHandler mShellCommandHandler;

    public CompatUIShellCommandHandler(ShellCommandHandler shellCommandHandler, CompatUIConfiguration compatUIConfiguration) {
        this.mShellCommandHandler = shellCommandHandler;
        this.mCompatUIConfiguration = compatUIConfiguration;
    }

    public static boolean invokeOrError(String str, PrintWriter printWriter, CompatUIShellCommandHandler$$ExternalSyntheticLambda0 compatUIShellCommandHandler$$ExternalSyntheticLambda0) {
        Boolean bool;
        str.getClass();
        switch (str) {
            case "0":
            case "false":
                bool = Boolean.FALSE;
                break;
            case "1":
            case "true":
                bool = Boolean.TRUE;
                break;
            default:
                bool = null;
                break;
        }
        if (bool == null) {
            printWriter.println("Error: expected true, 1, false, 0.");
            return false;
        }
        compatUIShellCommandHandler$$ExternalSyntheticLambda0.accept(bool);
        return true;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        if (strArr.length != 2) {
            KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
            return false;
        }
        String str = strArr[0];
        str.getClass();
        boolean equals = str.equals("reachabilityEducationEnabled");
        CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
        if (equals) {
            String str2 = strArr[1];
            Objects.requireNonNull(compatUIConfiguration);
            return invokeOrError(str2, printWriter, new CompatUIShellCommandHandler$$ExternalSyntheticLambda0(compatUIConfiguration, 1));
        }
        if (!str.equals("restartDialogEnabled")) {
            KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
            return false;
        }
        String str3 = strArr[1];
        Objects.requireNonNull(compatUIConfiguration);
        return invokeOrError(str3, printWriter, new CompatUIShellCommandHandler$$ExternalSyntheticLambda0(compatUIConfiguration, 0));
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println("    restartDialogEnabled [0|false|1|true]");
        printWriter.println("      Enable/Disable the restart education dialog for Size Compat Mode");
        printWriter.println("    reachabilityEducationEnabled [0|false|1|true]");
        printWriter.println("      Enable/Disable the restart education dialog for letterbox reachability");
        printWriter.println("      Disable the restart education dialog for letterbox reachability");
    }
}
