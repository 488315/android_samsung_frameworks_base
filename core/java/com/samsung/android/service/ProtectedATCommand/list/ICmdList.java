package com.samsung.android.service.ProtectedATCommand.list;

import android.util.NtpTrustedTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class ICmdList {
    public static final boolean ALLOWED_DATA_0_TO_9 = false;
    public static final boolean NOT_ALLOWED_DATA_0_TO_9 = true;
    protected int cmdType;
    private LinkedHashSet<ATCommands> mCmdList = new LinkedHashSet<>();

    protected abstract void addATCommands();

    protected final void putAtCommands(String cmd, int cmd_type, boolean check_data_0_to_9) {
        ATCommands atCmd;
        String cmd2 = cmd.trim();
        String cmdName = cmd2.toUpperCase();
        String[] tokens = {"+", "$", "^", "#"};
        int length = tokens.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String token = tokens[i];
            if (!cmdName.contains(token)) {
                i++;
            } else {
                cmdName = cmdName.split("\\" + token)[1];
                break;
            }
        }
        if (cmdName.contains("=")) {
            cmdName = cmdName.split("=")[0];
        }
        try {
            if (cmd2.contains(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)) {
                atCmd = new ATCommands(cmdName, cmd2.toUpperCase().getBytes("UTF-8"), check_data_0_to_9, cmd_type, true);
            } else {
                atCmd = new ATCommands(cmdName, cmd2.toUpperCase().getBytes("UTF-8"), check_data_0_to_9, cmd_type);
            }
            this.mCmdList.add(atCmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final void putAtCommands(String cmd, int cmd_type) {
        putAtCommands(cmd, cmd_type, false);
    }

    public final LinkedHashSet<ATCommands> getCmdSet() {
        return this.mCmdList;
    }

    public final List<ATCommands> getList() {
        return new ArrayList(this.mCmdList);
    }
}
