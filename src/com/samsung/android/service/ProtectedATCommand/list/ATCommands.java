package com.samsung.android.service.ProtectedATCommand.list;

import android.os.SystemProperties;
import android.util.Slog;
import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class ATCommands {
    static final String TAG = "ATCommands";
    private static final boolean mIsTestBinary = "eng".equals(SystemProperties.get("ro.build.type"));
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

    public ATCommands(String str, byte[] bArr) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = str;
        this.mCmds = bArr;
    }

    public ATCommands(String str, byte[] bArr, boolean z, int i) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = str;
        this.mCmds = bArr;
        this.mFlags = z;
        this.mType = i;
    }

    public ATCommands(String str, byte[] bArr, boolean z, int i, boolean z2) {
        this.mName = "";
        this.mCmds = null;
        this.mFlags = false;
        this.mType = 175;
        this.mHasAttribute = false;
        this.mAttribute = new ATCommandAttribute();
        this.mName = str;
        this.mCmds = bArr;
        this.mFlags = z;
        this.mType = i;
        this.mHasAttribute = z2;
        debugLog("CMD Name = " + this.mName);
        debugLog("CMD Type = " + this.mType);
        debugLog("CMD Attribute = " + this.mHasAttribute);
        if (this.mHasAttribute) {
            this.mName = str.split("\\|")[0];
            this.mCmds = this.mAttribute.setAttribute(bArr);
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

    /* JADX WARN: Type inference failed for: r10v3, types: [com.samsung.android.service.ProtectedATCommand.list.ATCommands] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r9v10, types: [int] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v6, types: [com.samsung.android.service.ProtectedATCommand.list.ATCommands] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public boolean equals(Object obj) {
        if (!(obj instanceof ATCommands)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ATCommands aTCommands = (ATCommands) obj;
        String str = new String(aTCommands.getCmdBytes(), StandardCharsets.UTF_8);
        String str2 = new String(this.mCmds, StandardCharsets.UTF_8);
        String[] split = str.split("=");
        String[] split2 = str2.split("=");
        if (split.length < 2 || split2.length < 2) {
            if (str.contains("=") && !str2.contains("=")) {
                str2 = str2.concat("=*");
            } else if (str2.contains("=") && !str.contains("=")) {
                str = str.concat("=*");
            }
            return str.equals(str2);
        }
        String[] split3 = split[1].split(",");
        String[] split4 = split2[1].split(",");
        int min = Math.min(split4.length, split3.length);
        int i = 0;
        ?? r9 = this;
        ?? r10 = aTCommands;
        while (i < min) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (split3[i].equals("*")) {
                if ((r9.mFlags || r10.getFlags()) && Integer.valueOf(split4[i]).intValue() >= 0) {
                    r9 = Integer.valueOf(split4[i]).intValue();
                    r10 = 9;
                    if (r9 <= 9) {
                        return false;
                    }
                }
                return true;
            }
            if (!split3[i].equals(split4[i])) {
                return false;
            }
            i++;
            r9 = r9;
            r10 = r10;
        }
        return split3.length == split4.length;
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
