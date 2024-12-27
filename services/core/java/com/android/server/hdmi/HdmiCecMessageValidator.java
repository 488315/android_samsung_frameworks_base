package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.location.gnss.hal.GnssNative;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HdmiCecMessageValidator {
    public static final SparseArray sValidationInfo = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AsciiValidator implements ParameterValidator {
        public final /* synthetic */ int $r8$classId;
        public final int mMaxLength;
        public final int mMinLength;

        public AsciiValidator() {
            this.$r8$classId = 0;
            this.mMinLength = 3;
            this.mMaxLength = 3;
        }

        public AsciiValidator(int i) {
            this.$r8$classId = 0;
            this.mMinLength = 1;
            this.mMaxLength = 14;
        }

        public AsciiValidator(int i, int i2) {
            this.$r8$classId = 1;
            this.mMinLength = i;
            this.mMaxLength = i2;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public final int isValid(byte[] bArr) {
            switch (this.$r8$classId) {
                case 0:
                    if (bArr.length < this.mMinLength) {
                        return 4;
                    }
                    boolean z = false;
                    for (int i = 0; i < bArr.length && i < this.mMaxLength; i++) {
                        if (!HdmiCecMessageValidator.isWithinRange(bArr[i], 32, 126)) {
                            return HdmiCecMessageValidator.m573$$Nest$smtoErrorCode(z);
                        }
                    }
                    z = true;
                    return HdmiCecMessageValidator.m573$$Nest$smtoErrorCode(z);
                default:
                    if (bArr.length < 1) {
                        return 4;
                    }
                    return HdmiCecMessageValidator.m573$$Nest$smtoErrorCode(
                            HdmiCecMessageValidator.isWithinRange(
                                    bArr[0], this.mMinLength, this.mMaxLength));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FixedLengthValidator implements ParameterValidator {
        public final /* synthetic */ int $r8$classId;
        public final int mLength;

        public /* synthetic */ FixedLengthValidator(int i, int i2) {
            this.$r8$classId = i2;
            this.mLength = i;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public final int isValid(byte[] bArr) {
            switch (this.$r8$classId) {
                case 0:
                    if (bArr.length < this.mLength) {}
                    break;
                default:
                    if (bArr.length < this.mLength) {}
                    break;
            }
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ParameterValidator {
        int isValid(byte[] bArr);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class PlayModeValidator implements ParameterValidator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ PlayModeValidator(int i) {
            this.$r8$classId = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:109:0x0113, code lost:

           if ((r11.length - 1) >= 2) goto L100;
        */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x00d7, code lost:

           if (com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(r11[4], 0, 31) != false) goto L80;
        */
        /* JADX WARN: Code restructure failed: missing block: B:91:0x00fe, code lost:

           if (r1 != 11) goto L110;
        */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x0102, code lost:

           if ((r11.length - 1) < 2) goto L110;
        */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x0115, code lost:

           r1 = 1;
        */
        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int isValid(byte[] r11) {
            /*
                Method dump skipped, instructions count: 982
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.hdmi.HdmiCecMessageValidator.PlayModeValidator.isValid(byte[]):int");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemAudioModeRequestValidator extends PlayModeValidator {
        @Override // com.android.server.hdmi.HdmiCecMessageValidator.PlayModeValidator,
                  // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public final int isValid(byte[] bArr) {
            if (bArr.length == 0) {
                return 0;
            }
            return super.isValid(bArr);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ValidationInfo {
        public final ParameterValidator parameterValidator;
        public final int validDestinations;
        public final int validSources;

        public ValidationInfo(ParameterValidator parameterValidator, int i, int i2) {
            this.parameterValidator = parameterValidator;
            this.validSources = i;
            this.validDestinations = i2;
        }
    }

    /* renamed from: -$$Nest$smisValidPlayMode, reason: not valid java name */
    public static boolean m571$$Nest$smisValidPlayMode(int i) {
        return isWithinRange(i, 5, 7)
                || isWithinRange(i, 9, 11)
                || isWithinRange(i, 21, 23)
                || isWithinRange(i, 25, 27)
                || isWithinRange(i, 36, 37)
                || i == 32;
    }

    /* renamed from: -$$Nest$smisValidRecordingSequence, reason: not valid java name */
    public static boolean m572$$Nest$smisValidRecordingSequence(int i) {
        return (i & 128) == 0
                && Integer.bitCount(
                                i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                        <= 1;
    }

    /* renamed from: -$$Nest$smtoErrorCode, reason: not valid java name */
    public static int m573$$Nest$smtoErrorCode(boolean z) {
        return z ? 0 : 3;
    }

    static {
        PlayModeValidator playModeValidator = new PlayModeValidator(5);
        addValidationInfo(130, playModeValidator, 65503, 32768);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED,
                playModeValidator,
                32767,
                32767);
        addValidationInfo(132, new PlayModeValidator(7), GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
        addValidationInfo(128, new PlayModeValidator(8), GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
        addValidationInfo(129, playModeValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
        addValidationInfo(134, playModeValidator, 32767, 32768);
        addValidationInfo(112, new SystemAudioModeRequestValidator(5), 32767, 32767);
        int i = 0;
        FixedLengthValidator fixedLengthValidator = new FixedLengthValidator(i, 0);
        addValidationInfo(
                IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT,
                fixedLengthValidator,
                32767,
                32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK,
                fixedLengthValidator,
                32767,
                32767);
        addValidationInfo(145, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
        addValidationInfo(113, fixedLengthValidator, 32767, 32767);
        addValidationInfo(143, fixedLengthValidator, 32767, 32767);
        addValidationInfo(140, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
        addValidationInfo(70, fixedLengthValidator, 32767, 32767);
        addValidationInfo(131, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
        addValidationInfo(125, fixedLengthValidator, 32767, 32767);
        int i2 = 4;
        addValidationInfo(4, fixedLengthValidator, 32767, 32767);
        addValidationInfo(192, fixedLengthValidator, 32767, 32767);
        addValidationInfo(11, fixedLengthValidator, 32767, 32767);
        addValidationInfo(15, fixedLengthValidator, 32767, 32767);
        addValidationInfo(193, fixedLengthValidator, 32767, 32767);
        addValidationInfo(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR,
                fixedLengthValidator,
                32767,
                32767);
        addValidationInfo(195, fixedLengthValidator, 32767, 32767);
        addValidationInfo(196, fixedLengthValidator, 32767, 32767);
        addValidationInfo(133, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
        addValidationInfo(
                54,
                fixedLengthValidator,
                GnssNative.GNSS_AIDING_TYPE_ALL,
                GnssNative.GNSS_AIDING_TYPE_ALL);
        addValidationInfo(197, fixedLengthValidator, 32767, 32767);
        addValidationInfo(13, fixedLengthValidator, 32767, 32767);
        addValidationInfo(6, fixedLengthValidator, 32767, 32767);
        addValidationInfo(5, fixedLengthValidator, 32767, 32767);
        addValidationInfo(69, fixedLengthValidator, 32767, 32767);
        addValidationInfo(139, fixedLengthValidator, 32767, GnssNative.GNSS_AIDING_TYPE_ALL);
        int i3 = 1;
        addValidationInfo(9, new FixedLengthValidator(i3, 1), 32767, 32767);
        addValidationInfo(10, new PlayModeValidator(6), 32767, 32767);
        addValidationInfo(51, new PlayModeValidator(1), 32767, 32767);
        addValidationInfo(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED,
                new PlayModeValidator(2),
                32767,
                32767);
        addValidationInfo(161, new PlayModeValidator(3), 32767, 32767);
        addValidationInfo(52, new PlayModeValidator(1), 32767, 32767);
        addValidationInfo(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE,
                new PlayModeValidator(2),
                32767,
                32767);
        addValidationInfo(162, new PlayModeValidator(3), 32767, 32767);
        addValidationInfo(103, new AsciiValidator(0), 32767, 32767);
        addValidationInfo(67, new PlayModeValidator(12), 32767, 32767);
        addValidationInfo(53, new PlayModeValidator(13), 32767, 32767);
        FixedLengthValidator fixedLengthValidator2 = new FixedLengthValidator(i3, 0);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL,
                fixedLengthValidator2,
                32767,
                32767);
        addValidationInfo(50, new AsciiValidator(), 1, 32768);
        int i4 = 3;
        AsciiValidator asciiValidator = new AsciiValidator(1, 3);
        addValidationInfo(66, new AsciiValidator(1, 4), 32767, 32767);
        addValidationInfo(27, new AsciiValidator(17, 31), 32767, 32767);
        addValidationInfo(26, asciiValidator, 32767, 32767);
        addValidationInfo(65, new PlayModeValidator(0), 32767, 32767);
        addValidationInfo(8, asciiValidator, 32767, 32767);
        addValidationInfo(146, new PlayModeValidator(9), 32767, 32767);
        addValidationInfo(147, new PlayModeValidator(10), 32767, 32767);
        addValidationInfo(7, new PlayModeValidator(14), 32767, 32767);
        FixedLengthValidator fixedLengthValidator3 = new FixedLengthValidator(i, 1);
        addValidationInfo(135, new FixedLengthValidator(i4, 0), 32767, 32768);
        addValidationInfo(
                137, new FixedLengthValidator(i3, 1), GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
        addValidationInfo(
                160,
                new FixedLengthValidator(i2, 1),
                GnssNative.GNSS_AIDING_TYPE_ALL,
                GnssNative.GNSS_AIDING_TYPE_ALL);
        addValidationInfo(
                138,
                fixedLengthValidator3,
                GnssNative.GNSS_AIDING_TYPE_ALL,
                GnssNative.GNSS_AIDING_TYPE_ALL);
        addValidationInfo(100, new PlayModeValidator(4), 32767, 32767);
        addValidationInfo(71, new AsciiValidator(0), 32767, 32767);
        addValidationInfo(141, new AsciiValidator(0, 2), 32767, 32767);
        addValidationInfo(142, new AsciiValidator(0, 1), 32767, 32767);
        addValidationInfo(68, new PlayModeValidator(15), 32767, 32767);
        addValidationInfo(144, new AsciiValidator(0, 3), 32767, GnssNative.GNSS_AIDING_TYPE_ALL);
        int i5 = 0;
        addValidationInfo(0, new FixedLengthValidator(2, i5), 32767, 32767);
        addValidationInfo(122, fixedLengthValidator2, 32767, 32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_SETTINGS,
                new FixedLengthValidator(i4, i5),
                32767,
                32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED,
                fixedLengthValidator2,
                32767,
                32767);
        addValidationInfo(114, new PlayModeValidator(11), 32, GnssNative.GNSS_AIDING_TYPE_ALL);
        addValidationInfo(126, new PlayModeValidator(11), 32767, 32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE,
                new AsciiValidator(0, 6),
                32767,
                32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP,
                fixedLengthValidator,
                GnssNative.GNSS_AIDING_TYPE_ALL,
                32767);
        addValidationInfo(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT,
                playModeValidator,
                32767,
                32768);
        addValidationInfo(168, new FixedLengthValidator(i2, 1), 32767, 32768);
        addValidationInfo(
                FrameworkStatsLog.INTEGRITY_RULES_PUSHED,
                fixedLengthValidator3,
                GnssNative.GNSS_AIDING_TYPE_ALL,
                32768);
    }

    public static void addValidationInfo(
            int i, ParameterValidator parameterValidator, int i2, int i3) {
        sValidationInfo.append(i, new ValidationInfo(parameterValidator, i2, i3));
    }

    public static boolean isValidAnalogueFrequency(int i) {
        int i2 = i & GnssNative.GNSS_AIDING_TYPE_ALL;
        return (i2 == 0 || i2 == 65535) ? false : true;
    }

    public static boolean isValidChannelIdentifier(int i, byte[] bArr) {
        int i2 = bArr[i] & 252;
        return i2 == 4 ? bArr.length - i >= 3 : i2 == 8 && bArr.length - i >= 4;
    }

    public static boolean isValidDigitalServiceIdentification(int i, byte[] bArr) {
        byte b = bArr[i];
        int i2 = b & 128;
        int i3 = b & Byte.MAX_VALUE;
        int i4 = i + 1;
        if (i2 == 0) {
            return i3 == 0 || isWithinRange(i3, 8, 10)
                    ? bArr.length - i4 >= 6
                    : (i3 == 1 || isWithinRange(i3, 16, 18))
                            ? bArr.length - i4 >= 4
                            : (i3 == 2 || isWithinRange(i3, 24, 27)) && bArr.length - i4 >= 6;
        }
        if (i2 == 128
                && (i3 == 0
                        || isWithinRange(i3, 8, 10)
                        || i3 == 1
                        || isWithinRange(i3, 16, 18)
                        || i3 == 2
                        || isWithinRange(i3, 24, 27))) {
            return isValidChannelIdentifier(i4, bArr);
        }
        return false;
    }

    public static boolean isValidMinute(int i) {
        return isWithinRange(i, 0, 59);
    }

    public static boolean isValidPhysicalAddress(int i, byte[] bArr) {
        int twoBytesToInt = HdmiUtils.twoBytesToInt(i, bArr);
        while (twoBytesToInt != 0) {
            int i2 = 61440 & twoBytesToInt;
            twoBytesToInt = (twoBytesToInt << 4) & GnssNative.GNSS_AIDING_TYPE_ALL;
            if (i2 == 0 && twoBytesToInt != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWithinRange(int i, int i2, int i3) {
        int i4 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        return i4 >= i2 && i4 <= i3;
    }

    public static int validateAddress(int i, int i2, int i3, int i4) {
        if (((1 << i) & i3) == 0) {
            return 1;
        }
        return ((1 << i2) & i4) == 0 ? 2 : 0;
    }
}
