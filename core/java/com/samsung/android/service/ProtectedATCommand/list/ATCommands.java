package com.samsung.android.service.ProtectedATCommand.list;

import android.os.SystemProperties;
import android.util.Slog;

import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class ATCommands {
    static final String TAG = "ATCommands";
    private static final boolean mIsTestBinary =
            "eng".equals(SystemProperties.get("ro.build.type"));
    private ATCommandAttribute mAttribute;
    private byte[] mCmds;
    private boolean mFlags;
    private boolean mHasAttribute;
    private String mName;
    private int mType;

    public ATCommands() {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
    }

    public ATCommands(String cmdName, byte[] cmds) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = cmdName;
        this.mCmds = cmds;
    }

    public ATCommands(String cmdName, byte[] cmds, boolean flags, int type) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = cmdName;
        this.mCmds = cmds;
        this.mFlags = flags;
        this.mType = type;
    }

    public ATCommands(String cmdName, byte[] cmds, boolean flags, int type, boolean hasAttribute) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = cmdName;
        this.mCmds = cmds;
        this.mFlags = flags;
        this.mType = type;
        this.mHasAttribute = hasAttribute;
        debugLog("CMD Name = " + this.mName);
        debugLog("CMD Type = " + this.mType);
        debugLog("CMD Attribute = " + this.mHasAttribute);
        if (this.mHasAttribute) {
            this.mName = cmdName.split("\\|")[0];
            this.mCmds = this.mAttribute.setAttribute(cmds);
        }
    }

    public String getName() {
        return this.mName;
    }

    public byte[] getCmdBytes() {
        return this.mCmds;
    }

    public boolean getFlags() {
        return this.mFlags;
    }

    public int getType() {
        return this.mType;
    }

    public boolean getHasAttribute() {
        return this.mHasAttribute;
    }

    public boolean isSecureLockOpenCommand() {
        return this.mAttribute.getSecureLockOpen();
    }

    public boolean isShipBlockCommand() {
        return this.mAttribute.getShipBlock();
    }

    public boolean isCSOpenCommand() {
        return this.mAttribute.getCSOpen();
    }

    public boolean isCarrierOpenCommand() {
        return this.mAttribute.getCarrierOpen();
    }

    public String getCarrierOpenList() {
        return this.mAttribute.getCarrierOpenList();
    }

    public boolean isCarrierBlockCommand() {
        return this.mAttribute.getCarrierBlock();
    }

    public String getCarrierBlockList() {
        return this.mAttribute.getCarrierBlockList();
    }

    public boolean isFacBinOpenATDCommand() {
        return this.mAttribute.getFacBinOpenATD();
    }

    public boolean isFacBinOpenDDEXECommand() {
        return this.mAttribute.getFacBinOpenDDEXE();
    }

    public boolean isFacBinOpenATDDDEXECommand() {
        return this.mAttribute.getFacBinOpenATDDDEXE();
    }

    public boolean isAutoBlockerOpenCommand() {
        return this.mAttribute.getAutoBlockerOpen();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ATCommands)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ATCommands cmd1 = (ATCommands) obj;
        String c1 = new String(cmd1.getCmdBytes(), StandardCharsets.UTF_8);
        String c2 = new String(this.mCmds, StandardCharsets.UTF_8);
        String[] splited_cmd1 = c1.split("=");
        String[] splited_cmd2 = c2.split("=");
        if (splited_cmd1.length < 2 || splited_cmd2.length < 2) {
            if (c1.contains("=") && !c2.contains("=")) {
                c2 = c2 + "=*";
            } else if (c2.contains("=") && !c1.contains("=")) {
                c1 = c1 + "=*";
            }
            return c1.equals(c2);
        }
        String[] raw_c1 = splited_cmd1[1].split(",");
        String[] raw_c2 = splited_cmd2[1].split(",");
        int size = Math.min(raw_c2.length, raw_c1.length);
        for (int i = 0; i < size; i++) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (raw_c1[i].equals("*")) {
                if ((this.mFlags || cmd1.getFlags())
                        && Integer.valueOf(raw_c2[i]).intValue() >= 0) {
                    if (Integer.valueOf(raw_c2[i]).intValue() <= 9) {
                        return false;
                    }
                }
                return true;
            }
            if (!raw_c1[i].equals(raw_c2[i])) {
                return false;
            }
        }
        int i2 = raw_c1.length;
        return i2 == raw_c2.length;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public static void debugLog(String str) {
        if (mIsTestBinary) {
            Slog.d(TAG, str);
        }
    }
}
