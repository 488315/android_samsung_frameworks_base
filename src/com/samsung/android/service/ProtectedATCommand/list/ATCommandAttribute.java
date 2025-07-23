package com.samsung.android.service.ProtectedATCommand.list;

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

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0138, code lost:
    
        android.util.Slog.e("ATCommands", "#### Error Command Convention, Must check AT Command List File");
        android.util.Slog.e("ATCommands", "#### And This command can't operate with attribute");
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    byte[] setAttribute(byte[] r18) {
        /*
            Method dump skipped, instructions count: 490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.service.ProtectedATCommand.list.ATCommandAttribute.setAttribute(byte[]):byte[]");
    }
}
