package com.samsung.android.service.ProtectedATCommand.list;

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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x013a, code lost:

       android.util.Slog.e("ATCommands", "#### Error Command Convention, Must check AT Command List File");
       android.util.Slog.e("ATCommands", "#### And This command can't operate with attribute");
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01ac, code lost:

       android.util.Slog.e("ATCommands", "#### Error Command Convention, Must check AT Command List File");
       android.util.Slog.e("ATCommands", "#### And This command can't operate with attribute");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    byte[] setAttribute(byte[] r17) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.service.ProtectedATCommand.list.ATCommandAttribute.setAttribute(byte[]):byte[]");
    }
}
