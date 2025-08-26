package com.android.internal.protolog;

import android.app.slice.Slice;
import android.hardware.display.SemWifiDisplayParameter;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes4.dex */
public class ProtoLogCommandHandler extends ShellCommand {
    private final PrintWriter mPrintWriter;
    private final ProtoLogConfigurationService mProtoLogConfigurationService;

    public ProtoLogCommandHandler(ProtoLogConfigurationService protoLogConfigurationService) {
        this(protoLogConfigurationService, null);
    }

    public ProtoLogCommandHandler(ProtoLogConfigurationService protoLogConfigurationService, PrintWriter printWriter) {
        this.mProtoLogConfigurationService = protoLogConfigurationService;
        this.mPrintWriter = printWriter;
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public int onCommand(String str) {
        if (str == null) {
            onHelp();
            return 0;
        }
        str.hashCode();
        if (str.equals("groups")) {
            return handleGroupsCommands(getNextArg());
        }
        if (str.equals("logcat")) {
            return handleLogcatCommands(getNextArg());
        }
        return handleDefaultCommands(str);
    }

    @Override // android.os.ShellCommand, com.android.modules.utils.BasicShellCommandHandler
    public int handleDefaultCommands(String str) {
        if (str == null || "help".equals(str) || "-h".equals(str)) {
            onHelp();
            return 0;
        }
        getOutPrintWriter().println("Unknown command: " + str + " (use 'help' command for guidance)");
        return -1;
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("ProtoLog commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  groups (list | status)");
        outPrintWriter.println("    list - lists all ProtoLog groups registered with ProtoLog service");
        outPrintWriter.println("    status <group> - print the status of a ProtoLog group");
        outPrintWriter.println();
        outPrintWriter.println("  logcat (enable | disable) <group>");
        outPrintWriter.println("    enable or disable ProtoLog to logcat");
        outPrintWriter.println();
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public PrintWriter getOutPrintWriter() {
        PrintWriter printWriter = this.mPrintWriter;
        return printWriter != null ? printWriter : super.getOutPrintWriter();
    }

    private int handleGroupsCommands(String str) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (str == null) {
            outPrintWriter.println("Incomplete command. Use 'cmd protolog help' for guidance.");
            return 0;
        }
        str.hashCode();
        if (str.equals("status")) {
            String nextArg = getNextArg();
            if (nextArg == null) {
                outPrintWriter.println("Incomplete command. Use 'cmd protolog help' for guidance.");
                return 0;
            }
            outPrintWriter.println("ProtoLog group " + nextArg + "'s status:");
            if (!Set.of((Object[]) this.mProtoLogConfigurationService.getGroups()).contains(nextArg)) {
                outPrintWriter.println("UNREGISTERED");
                return 0;
            }
            outPrintWriter.println("LOG_TO_LOGCAT = " + this.mProtoLogConfigurationService.isLoggingToLogcat(nextArg));
            return 0;
        }
        if (str.equals(Slice.HINT_LIST)) {
            String[] groups = this.mProtoLogConfigurationService.getGroups();
            if (groups.length == 0) {
                outPrintWriter.println("No ProtoLog groups registered with ProtoLog service.");
                return 0;
            }
            outPrintWriter.println("ProtoLog groups registered with service:");
            for (String str2 : groups) {
                outPrintWriter.println("- " + str2);
            }
            return 0;
        }
        outPrintWriter.println("Unknown command: " + str);
        return -1;
    }

    private int handleLogcatCommands(String str) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (str == null || peekNextArg() == null) {
            outPrintWriter.println("Incomplete command. Use 'cmd protolog help' for guidance.");
            return 0;
        }
        str.hashCode();
        if (str.equals("enable")) {
            this.mProtoLogConfigurationService.enableProtoLogToLogcat(outPrintWriter, processGroups());
            return 0;
        }
        if (str.equals(SemWifiDisplayParameter.VALUE_DISABLE)) {
            this.mProtoLogConfigurationService.disableProtoLogToLogcat(outPrintWriter, processGroups());
            return 0;
        }
        outPrintWriter.println("Unknown command: " + str);
        return -1;
    }

    private String[] processGroups() {
        if (getRemainingArgsCount() == 0) {
            return this.mProtoLogConfigurationService.getGroups();
        }
        ArrayList arrayList = new ArrayList();
        while (getRemainingArgsCount() > 0) {
            arrayList.add(getNextArg());
        }
        return (String[]) arrayList.toArray(new String[0]);
    }
}
