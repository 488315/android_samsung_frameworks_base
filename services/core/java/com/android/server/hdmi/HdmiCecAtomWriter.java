package com.android.server.hdmi;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class HdmiCecAtomWriter {
    protected static final int FEATURE_ABORT_OPCODE_UNKNOWN = 256;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageReportedSpecialArgs {
        public int mUserControlPressedCommand = 0;
        public int mFeatureAbortOpcode = 256;
        public int mFeatureAbortReason = 0;
    }

    public static void earcStatusChanged(int i, int i2, int i3, boolean z, boolean z2) {
        FrameworkStatsLog.write(701, z, z2, i != 0 ? i != 1 ? i != 2 ? i != 3 ? 0 : 4 : 3 : 2 : 1, i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : 4 : 3 : 2 : 1, i3);
    }

    public final void messageReported(HdmiCecMessage hdmiCecMessage, int i, int i2, int i3) {
        MessageReportedSpecialArgs messageReportedSpecialArgs;
        MessageReportedSpecialArgs messageReportedSpecialArgs2;
        int i4 = i3 == -1 ? 0 : i3 + 10;
        int i5 = hdmiCecMessage.mSource;
        int i6 = hdmiCecMessage.mOpcode;
        byte[] bArr = hdmiCecMessage.mParams;
        if (i6 == 0) {
            messageReportedSpecialArgs = new MessageReportedSpecialArgs();
            if (bArr.length > 0) {
                messageReportedSpecialArgs.mFeatureAbortOpcode = bArr[0] & 255;
                if (bArr.length > 1) {
                    messageReportedSpecialArgs.mFeatureAbortReason = bArr[1] + 10;
                }
            }
        } else {
            if (i6 != 68) {
                messageReportedSpecialArgs2 = new MessageReportedSpecialArgs();
                writeHdmiCecMessageReportedAtom(i2, i, i5, hdmiCecMessage.mDestination, i6, i4, messageReportedSpecialArgs2.mUserControlPressedCommand, messageReportedSpecialArgs2.mFeatureAbortOpcode, messageReportedSpecialArgs2.mFeatureAbortReason);
            }
            messageReportedSpecialArgs = new MessageReportedSpecialArgs();
            if (bArr.length > 0) {
                byte b = bArr[0];
                if (b < 30 || b > 41) {
                    messageReportedSpecialArgs.mUserControlPressedCommand = b + 256;
                } else {
                    messageReportedSpecialArgs.mUserControlPressedCommand = 2;
                }
            }
        }
        messageReportedSpecialArgs2 = messageReportedSpecialArgs;
        writeHdmiCecMessageReportedAtom(i2, i, i5, hdmiCecMessage.mDestination, i6, i4, messageReportedSpecialArgs2.mUserControlPressedCommand, messageReportedSpecialArgs2.mFeatureAbortOpcode, messageReportedSpecialArgs2.mFeatureAbortReason);
    }

    public void writeHdmiCecMessageReportedAtom(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        FrameworkStatsLog.write(310, i, i2, i3, i4, i5, i6, i7, i8, i9);
    }
}
