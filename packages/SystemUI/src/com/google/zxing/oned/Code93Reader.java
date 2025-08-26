package com.google.zxing.oned;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.sec.ims.volte2.data.VolteConstants;

/* loaded from: classes4.dex */
public final class Code93Reader extends OneDReader {
    public static final int ASTERISK_ENCODING;
    public static final int[] CHARACTER_ENCODINGS;

    static {
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
        int[] iArr = {IKnoxCustomManager.Stub.TRANSACTION_setForcedDisplaySizeDensity, 328, 324, 322, IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal, IKnoxCustomManager.Stub.TRANSACTION_startTcpDump, IKnoxCustomManager.Stub.TRANSACTION_getBsoh, 336, IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal, 266, 424, VolteConstants.ErrorCode.BAD_EXTENSION, 418, 404, 402, 394, 360, 356, 354, 308, IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub, 344, CustomDeviceManager.DESTINATION_ADDRESS, 326, 300, IKnoxCustomManager.Stub.TRANSACTION_startSmartView, 436, 434, 428, VolteConstants.ErrorCode.SESSION_INTERVAL_TOO_SMALL, VolteConstants.ErrorCode.NOT_ACCEPTABLE, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, IKnoxCustomManager.Stub.TRANSACTION_getTcpDump, 474, 470, VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING, 350};
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[47];
    }
}
