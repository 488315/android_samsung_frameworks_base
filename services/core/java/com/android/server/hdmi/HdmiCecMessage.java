package com.android.server.hdmi;

import android.hardware.hdmi.DeviceFeatures;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

import com.android.internal.util.FrameworkStatsLog;

import libcore.util.EmptyArray;

import java.util.Arrays;
import java.util.Objects;

public class HdmiCecMessage {
    public static final byte[] EMPTY_PARAM = EmptyArray.BYTE;
    public final int mDestination;
    public final int mOpcode;
    public final byte[] mParams;
    public final int mSource;
    public final int mValidationResult;

    public HdmiCecMessage(int i, int i2, int i3, int i4, byte[] bArr) {
        this.mSource = i;
        this.mDestination = i2;
        this.mOpcode = i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        this.mParams = Arrays.copyOf(bArr, bArr.length);
        this.mValidationResult = i4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HdmiCecMessage(int r10, int r11, int r12, byte[] r13) {
        /*
            r9 = this;
            r0 = r12 & 255(0xff, float:3.57E-43)
            android.util.SparseArray r1 = com.android.server.hdmi.HdmiCecMessageValidator.sValidationInfo
            java.lang.Object r1 = r1.get(r0)
            com.android.server.hdmi.HdmiCecMessageValidator$ValidationInfo r1 = (com.android.server.hdmi.HdmiCecMessageValidator.ValidationInfo) r1
            r2 = 0
            if (r1 != 0) goto L1a
            java.lang.String r1 = "No validation information for the opcode: "
            java.lang.String r0 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r0, r1)
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.android.server.hdmi.HdmiLogger.warning(r0, r1)
        L18:
            r7 = r2
            goto L2f
        L1a:
            int r0 = r1.validSources
            int r3 = r1.validDestinations
            int r0 = com.android.server.hdmi.HdmiCecMessageValidator.validateAddress(r10, r11, r0, r3)
            if (r0 == 0) goto L26
        L24:
            r7 = r0
            goto L2f
        L26:
            com.android.server.hdmi.HdmiCecMessageValidator$ParameterValidator r0 = r1.parameterValidator
            int r0 = r0.isValid(r13)
            if (r0 == 0) goto L18
            goto L24
        L2f:
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r8 = r13
            r3.<init>(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.hdmi.HdmiCecMessage.<init>(int, int,"
                    + " int, byte[]):void");
    }

    public static HdmiCecMessage build(int i, int i2, int i3) {
        return new HdmiCecMessage(i, i2, i3, EMPTY_PARAM);
    }

    public static HdmiCecMessage build(int i, int i2, int i3, byte[] bArr) {
        int i4 = i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        if (i4 == 115) {
            if (bArr.length == 0) {
                return new HdmiCecMessage(i, i2, 115, 4, bArr);
            }
            byte b = bArr[0];
            int validateAddress = HdmiCecMessageValidator.validateAddress(i, i2, 32767, 32767);
            return validateAddress == 0
                    ? new SetAudioVolumeLevelMessage(i, i2, b, bArr)
                    : new HdmiCecMessage(i, i2, 115, validateAddress, bArr);
        }
        if (i4 != 166) {
            return new HdmiCecMessage(i, i2, i4, bArr);
        }
        int validateAddress2 = HdmiCecMessageValidator.validateAddress(i, i2, 32767, 32768);
        if (validateAddress2 != 0) {
            return new HdmiCecMessage(
                    i,
                    i2,
                    FrameworkStatsLog
                            .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                    validateAddress2,
                    bArr);
        }
        if (bArr.length < 4) {
            return new HdmiCecMessage(
                    i,
                    i2,
                    FrameworkStatsLog
                            .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                    4,
                    bArr);
        }
        int unsignedInt = Byte.toUnsignedInt(bArr[0]);
        int endOfSequence = HdmiUtils.getEndOfSequence(2, bArr);
        if (endOfSequence != -1 && HdmiUtils.getEndOfSequence(endOfSequence + 1, bArr) != -1) {
            return new ReportFeaturesMessage(
                    i,
                    i2,
                    bArr,
                    unsignedInt,
                    DeviceFeatures.fromOperand(
                            Arrays.copyOfRange(
                                    bArr, HdmiUtils.getEndOfSequence(2, bArr) + 1, bArr.length)));
        }
        return new HdmiCecMessage(
                i,
                i2,
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                4,
                bArr);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof HdmiCecMessage)) {
            return false;
        }
        HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) obj;
        return this.mSource == hdmiCecMessage.mSource
                && this.mDestination == hdmiCecMessage.mDestination
                && this.mOpcode == hdmiCecMessage.mOpcode
                && Arrays.equals(this.mParams, hdmiCecMessage.mParams)
                && this.mValidationResult == hdmiCecMessage.mValidationResult;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mSource),
                Integer.valueOf(this.mDestination),
                Integer.valueOf(this.mOpcode),
                Integer.valueOf(Arrays.hashCode(this.mParams)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0250  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 1012
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.HdmiCecMessage.toString():java.lang.String");
    }
}
