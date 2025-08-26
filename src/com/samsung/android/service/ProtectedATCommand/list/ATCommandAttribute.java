package com.samsung.android.service.ProtectedATCommand.list;

import android.util.NtpTrustedTime;
import android.util.Slog;
import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class ATCommandAttribute {
    private static final String AUTOBLOCKER_OPEN = "ABO";
    private static final String CARRIER_BLOCK = "CRB";
    private static final String CARRIER_OPEN = "CRO";
    private static final String CSTOOL_OPEN = "CSO";
    private static final String FACBIN_OPEN_ATD = "FBOA";
    private static final String FACBIN_OPEN_ATD_DDEXE = "FBOAD";
    private static final String FACBIN_OPEN_DDEXE = "FBOD";
    private static final String SECURELOCK_OPEN = "SLO";
    private static final String SHIPBIN_BLOCK = "SBB";
    private boolean mSecureLockOpen = false;
    private boolean mShipBlock = false;
    private boolean mCSOpen = false;
    private boolean mFacBinOpenATDDDEXE = false;
    private boolean mFacBinOpenATD = false;
    private boolean mFacBinOpenDDEXE = false;
    private boolean mAutoBlockerOpen = false;
    private boolean mCarrierOpen = false;
    private String mCarrierOpenList = null;
    private boolean mCarrierBlock = false;
    private String mCarrierBlockList = null;

    public boolean getSecureLockOpen() {
        return this.mSecureLockOpen;
    }

    public boolean getShipBlock() {
        return this.mShipBlock;
    }

    public boolean getCSOpen() {
        return this.mCSOpen;
    }

    public boolean getCarrierOpen() {
        return this.mCarrierOpen;
    }

    public String getCarrierOpenList() {
        return this.mCarrierOpenList;
    }

    public boolean getCarrierBlock() {
        return this.mCarrierBlock;
    }

    public String getCarrierBlockList() {
        return this.mCarrierBlockList;
    }

    public boolean getFacBinOpenATD() {
        return this.mFacBinOpenATD;
    }

    public boolean getFacBinOpenDDEXE() {
        return this.mFacBinOpenDDEXE;
    }

    public boolean getFacBinOpenATDDDEXE() {
        return this.mFacBinOpenATDDDEXE;
    }

    public boolean getAutoBlockerOpen() {
        return this.mAutoBlockerOpen;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0138, code lost:
    
        android.util.Slog.e("ATCommands", "#### Error Command Convention, Must check AT Command List File");
        android.util.Slog.e("ATCommands", "#### And This command can't operate with attribute");
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01a4  */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r11v17 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v19 */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v22 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v24 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    byte[] setAttribute(byte[] bArr) {
        char c;
        boolean z;
        String str = new String(bArr, StandardCharsets.UTF_8);
        boolean z2 = true;
        String[] strArrSplit = str.substring(str.indexOf(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) + 1).split("\\|");
        int length = strArrSplit.length;
        int i = 0;
        while (true) {
            if (i < length) {
                String str2 = strArrSplit[i];
                ATCommands.debugLog("Attribute = " + str2);
                str2.hashCode();
                ?? r11 = -1;
                r11 = -1;
                r11 = -1;
                r11 = -1;
                r11 = -1;
                r11 = -1;
                r11 = -1;
                r11 = -1;
                switch (str2.hashCode()) {
                    case 64590:
                        if (str2.equals(AUTOBLOCKER_OPEN)) {
                            r11 = 0;
                            break;
                        }
                        break;
                    case 67039:
                        if (str2.equals(CSTOOL_OPEN)) {
                            r11 = z2;
                            break;
                        }
                        break;
                    case 81875:
                        if (str2.equals(SHIPBIN_BLOCK)) {
                            r11 = 2;
                            break;
                        }
                        break;
                    case 82198:
                        if (str2.equals(SECURELOCK_OPEN)) {
                            r11 = 3;
                            break;
                        }
                        break;
                    case 2151310:
                        if (str2.equals(FACBIN_OPEN_ATD)) {
                            r11 = 4;
                            break;
                        }
                        break;
                    case 2151313:
                        if (str2.equals(FACBIN_OPEN_DDEXE)) {
                            r11 = 5;
                            break;
                        }
                        break;
                    case 66690678:
                        if (str2.equals(FACBIN_OPEN_ATD_DDEXE)) {
                            r11 = 6;
                            break;
                        }
                        break;
                }
                switch (r11) {
                    case 0:
                        ATCommands.debugLog("AUTOBLOCKER_OPEN set");
                        this.mAutoBlockerOpen = z2;
                        break;
                    case 1:
                        ATCommands.debugLog("CSTOOL_OPEN set");
                        this.mCSOpen = z2;
                        break;
                    case 2:
                        ATCommands.debugLog("SHIPBIN_BLOCK set");
                        this.mShipBlock = z2;
                        break;
                    case 3:
                        ATCommands.debugLog("SECURELOCK_OPEN set");
                        this.mSecureLockOpen = z2;
                        break;
                    case 4:
                        ATCommands.debugLog("FACBIN_OPEN_ATD set");
                        this.mFacBinOpenATD = z2;
                        break;
                    case 5:
                        ATCommands.debugLog("FACBIN_OPEN_DDEXE set");
                        this.mFacBinOpenDDEXE = z2;
                        break;
                    case 6:
                        ATCommands.debugLog("FACBIN_OPEN_ATDDDEXE set");
                        this.mFacBinOpenATDDDEXE = z2;
                        break;
                }
                c = 0;
                boolean z3 = z2;
                if (str2.contains(CARRIER_OPEN)) {
                    ATCommands.debugLog("CARRIER_OPEN set");
                    if (str2.length() <= 3) {
                        Slog.e("ATCommands", "The length of the attribute(" + str2 + ") is invalid : " + str2.length());
                    } else if (str2.charAt(3) == '(' && str2.charAt(str2.length() - 1) == ')') {
                        this.mCarrierOpen = z3;
                        this.mCarrierOpenList = str2.split(CARRIER_OPEN)[z3 ? 1 : 0].substring(z3 ? 1 : 0, str2.split(CARRIER_OPEN)[z3 ? 1 : 0].length() - (z3 ? 1 : 0));
                        if (str2.contains(CARRIER_BLOCK)) {
                            z = true;
                        } else {
                            ATCommands.debugLog("CARRIER_BLOCK set");
                            if (str2.length() <= 3) {
                                Slog.e("ATCommands", "The length of the attribute(" + str2 + ") is invalid : " + str2.length());
                            } else if (str2.charAt(3) == '(') {
                                z = true;
                                if (str2.charAt(str2.length() - 1) == ')') {
                                    this.mCarrierBlock = true;
                                    this.mCarrierBlockList = str2.split(CARRIER_BLOCK)[1].substring(1, str2.split(CARRIER_BLOCK)[1].length() - 1);
                                }
                            }
                        }
                        i++;
                        z2 = z;
                    }
                } else {
                    if (str2.contains(CARRIER_BLOCK)) {
                    }
                    i++;
                    z2 = z;
                }
            } else {
                c = 0;
            }
        }
        Slog.e("ATCommands", "#### Error Command Convention, Must check AT Command List File");
        Slog.e("ATCommands", "#### And This command can't operate with attribute");
        return str.split("\\|")[c].getBytes(StandardCharsets.UTF_8);
    }
}
