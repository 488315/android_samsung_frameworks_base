package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.location.gnss.hal.GnssNative;
import com.att.iqi.lib.metrics.hw.HwConstants;

/* loaded from: classes2.dex */
public abstract class HdmiCecMessageValidator {
  public static final SparseArray sValidationInfo = new SparseArray();

  public interface ParameterValidator {
    int isValid(byte[] bArr);
  }

  public static boolean isValidAnalogueFrequency(int i) {
    int i2 = i & GnssNative.GNSS_AIDING_TYPE_ALL;
    return (i2 == 0 || i2 == 65535) ? false : true;
  }

  public static boolean isValidDisplayControl(int i) {
    int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    return i2 == 0 || i2 == 64 || i2 == 128 || i2 == 192;
  }

  public static boolean isValidType(int i) {
    return i >= 0 && i <= 7 && i != 2;
  }

  public static boolean isValidUiBroadcastType(int i) {
    return i == 0 || i == 1 || i == 16 || i == 32 || i == 48 || i == 64 || i == 80 || i == 96
        || i == 112 || i == 128 || i == 144 || i == 145 || i == 160;
  }

  public static boolean isWithinRange(int i, int i2, int i3) {
    int i4 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    return i4 >= i2 && i4 <= i3;
  }

  public static int toErrorCode(boolean z) {
    return z ? 0 : 3;
  }

  public static int validateAddress(int i, int i2, int i3, int i4) {
    if (((1 << i) & i3) == 0) {
      return 1;
    }
    return ((1 << i2) & i4) == 0 ? 2 : 0;
  }

  public class ValidationInfo {
    public final ParameterValidator parameterValidator;
    public final int validDestinations;
    public final int validSources;

    public ValidationInfo(ParameterValidator parameterValidator, int i, int i2) {
      this.parameterValidator = parameterValidator;
      this.validSources = i;
      this.validDestinations = i2;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  static {
    PhysicalAddressValidator physicalAddressValidator = new PhysicalAddressValidator();
    addValidationInfo(130, physicalAddressValidator, 64985, 32768);
    addValidationInfo(FrameworkStatsLog.f681x729e24be, physicalAddressValidator, 32767, 32767);
    addValidationInfo(
        132, new ReportPhysicalAddressValidator(), GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
    addValidationInfo(128, new RoutingChangeValidator(), GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
    addValidationInfo(129, physicalAddressValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32768);
    addValidationInfo(134, physicalAddressValidator, 32767, 32768);
    addValidationInfo(112, new SystemAudioModeRequestValidator(), 32767, 32767);
    FixedLengthValidator fixedLengthValidator = new FixedLengthValidator(0);
    addValidationInfo(
        IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT,
        fixedLengthValidator,
        32767,
        32767);
    addValidationInfo(FrameworkStatsLog.f680x89db317, fixedLengthValidator, 32767, 32767);
    addValidationInfo(145, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
    addValidationInfo(113, fixedLengthValidator, 32767, 32767);
    addValidationInfo(143, fixedLengthValidator, 32767, 32767);
    addValidationInfo(140, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
    addValidationInfo(70, fixedLengthValidator, 32767, 32767);
    addValidationInfo(131, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
    addValidationInfo(125, fixedLengthValidator, 32767, 32767);
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
        54, fixedLengthValidator, GnssNative.GNSS_AIDING_TYPE_ALL, GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(197, fixedLengthValidator, 32767, 32767);
    addValidationInfo(13, fixedLengthValidator, 32767, 32767);
    addValidationInfo(6, fixedLengthValidator, 32767, 32767);
    addValidationInfo(5, fixedLengthValidator, 32767, 32767);
    addValidationInfo(69, fixedLengthValidator, 32767, 32767);
    addValidationInfo(139, fixedLengthValidator, 32767, GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(9, new VariableLengthValidator(1, 8), 32767, 32767);
    addValidationInfo(10, new RecordStatusInfoValidator(), 32767, 32767);
    addValidationInfo(51, new AnalogueTimerValidator(), 32767, 32767);
    addValidationInfo(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED,
        new DigitalTimerValidator(),
        32767,
        32767);
    addValidationInfo(161, new ExternalTimerValidator(), 32767, 32767);
    addValidationInfo(52, new AnalogueTimerValidator(), 32767, 32767);
    addValidationInfo(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE,
        new DigitalTimerValidator(),
        32767,
        32767);
    addValidationInfo(162, new ExternalTimerValidator(), 32767, 32767);
    addValidationInfo(103, new AsciiValidator(1, 14), 32767, 32767);
    addValidationInfo(67, new TimerClearedStatusValidator(), 32767, 32767);
    addValidationInfo(53, new TimerStatusValidator(), 32767, 32767);
    FixedLengthValidator fixedLengthValidator2 = new FixedLengthValidator(1);
    addValidationInfo(FrameworkStatsLog.f679x663f9746, fixedLengthValidator2, 32767, 32767);
    addValidationInfo(50, new AsciiValidator(3), 32767, 32768);
    MinimumOneByteRangeValidator minimumOneByteRangeValidator =
        new MinimumOneByteRangeValidator(1, 3);
    addValidationInfo(66, new MinimumOneByteRangeValidator(1, 4), 32767, 32767);
    addValidationInfo(27, new MinimumOneByteRangeValidator(17, 31), 32767, 32767);
    addValidationInfo(26, minimumOneByteRangeValidator, 32767, 32767);
    addValidationInfo(65, new PlayModeValidator(), 32767, 32767);
    addValidationInfo(8, minimumOneByteRangeValidator, 32767, 32767);
    addValidationInfo(146, new SelectAnalogueServiceValidator(), 32767, 32767);
    addValidationInfo(147, new SelectDigitalServiceValidator(), 32767, 32767);
    addValidationInfo(7, new TunerDeviceStatusValidator(), 32767, 32767);
    VariableLengthValidator variableLengthValidator = new VariableLengthValidator(0, 14);
    addValidationInfo(135, new FixedLengthValidator(3), 32767, 32768);
    addValidationInfo(
        137, new VariableLengthValidator(1, 14), GnssNative.GNSS_AIDING_TYPE_ALL, 32767);
    addValidationInfo(
        160,
        new VariableLengthValidator(4, 14),
        GnssNative.GNSS_AIDING_TYPE_ALL,
        GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(
        138,
        variableLengthValidator,
        GnssNative.GNSS_AIDING_TYPE_ALL,
        GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(100, new OsdStringValidator(), 32767, 32767);
    addValidationInfo(71, new AsciiValidator(1, 14), 32767, 32767);
    addValidationInfo(141, new MinimumOneByteRangeValidator(0, 2), 32767, 32767);
    addValidationInfo(142, new MinimumOneByteRangeValidator(0, 1), 32767, 32767);
    addValidationInfo(68, new UserControlPressedValidator(), 32767, 32767);
    addValidationInfo(
        144, new MinimumOneByteRangeValidator(0, 3), 32767, GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(0, new FixedLengthValidator(2), 32767, 32767);
    addValidationInfo(122, fixedLengthValidator2, 32767, 32767);
    addValidationInfo(FrameworkStatsLog.f640x2165d62a, new FixedLengthValidator(3), 32767, 32767);
    addValidationInfo(FrameworkStatsLog.f636xd07885aa, fixedLengthValidator2, 32767, 32767);
    addValidationInfo(
        114, new MinimumOneByteRangeValidator(0, 1), 32767, GnssNative.GNSS_AIDING_TYPE_ALL);
    addValidationInfo(126, new SingleByteRangeValidator(0, 1), 32767, 32767);
    addValidationInfo(
        FrameworkStatsLog.f664xb094ac9f, new MinimumOneByteRangeValidator(0, 6), 32767, 32767);
    addValidationInfo(
        FrameworkStatsLog.f643xde3a78eb,
        fixedLengthValidator,
        GnssNative.GNSS_AIDING_TYPE_ALL,
        32767);
    addValidationInfo(FrameworkStatsLog.f641xaad26533, physicalAddressValidator, 32767, 32768);
    addValidationInfo(168, new VariableLengthValidator(4, 14), 32767, 32768);
    addValidationInfo(
        FrameworkStatsLog.INTEGRITY_RULES_PUSHED,
        variableLengthValidator,
        GnssNative.GNSS_AIDING_TYPE_ALL,
        32768);
  }

  public static void addValidationInfo(
      int i, ParameterValidator parameterValidator, int i2, int i3) {
    sValidationInfo.append(i, new ValidationInfo(parameterValidator, i2, i3));
  }

  public static int validate(int i, int i2, int i3, byte[] bArr) {
    ValidationInfo validationInfo = (ValidationInfo) sValidationInfo.get(i3);
    if (validationInfo == null) {
      HdmiLogger.warning("No validation information for the opcode: " + i3, new Object[0]);
      return 0;
    }
    int validateAddress =
        validateAddress(i, i2, validationInfo.validSources, validationInfo.validDestinations);
    if (validateAddress != 0) {
      return validateAddress;
    }
    int isValid = validationInfo.parameterValidator.isValid(bArr);
    if (isValid != 0) {
      return isValid;
    }
    return 0;
  }

  public class FixedLengthValidator implements ParameterValidator {
    public final int mLength;

    public FixedLengthValidator(int i) {
      this.mLength = i;
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      return bArr.length < this.mLength ? 4 : 0;
    }
  }

  public class VariableLengthValidator implements ParameterValidator {
    public final int mMaxLength;
    public final int mMinLength;

    public VariableLengthValidator(int i, int i2) {
      this.mMinLength = i;
      this.mMaxLength = i2;
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      return bArr.length < this.mMinLength ? 4 : 0;
    }
  }

  public static boolean isValidPhysicalAddress(byte[] bArr, int i) {
    int twoBytesToInt = HdmiUtils.twoBytesToInt(bArr, i);
    while (twoBytesToInt != 0) {
      int i2 = 61440 & twoBytesToInt;
      twoBytesToInt = (twoBytesToInt << 4) & GnssNative.GNSS_AIDING_TYPE_ALL;
      if (i2 == 0 && twoBytesToInt != 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidAsciiString(byte[] bArr, int i, int i2) {
    while (i < bArr.length && i < i2) {
      if (!isWithinRange(bArr[i], 32, 126)) {
        return false;
      }
      i++;
    }
    return true;
  }

  public static boolean isValidDayOfMonth(int i) {
    return isWithinRange(i, 1, 31);
  }

  public static boolean isValidMonthOfYear(int i) {
    return isWithinRange(i, 1, 12);
  }

  public static boolean isValidHour(int i) {
    return isWithinRange(i, 0, 23);
  }

  public static boolean isValidMinute(int i) {
    return isWithinRange(i, 0, 59);
  }

  public static boolean isValidDurationHours(int i) {
    return isWithinRange(i, 0, 99);
  }

  public static boolean isValidRecordingSequence(int i) {
    int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    return (i2 & 128) == 0 && Integer.bitCount(i2) <= 1;
  }

  public static boolean isValidAnalogueBroadcastType(int i) {
    return isWithinRange(i, 0, 2);
  }

  public static boolean isValidBroadcastSystem(int i) {
    return isWithinRange(i, 0, 31);
  }

  public static boolean isAribDbs(int i) {
    return i == 0 || isWithinRange(i, 8, 10);
  }

  public static boolean isAtscDbs(int i) {
    return i == 1 || isWithinRange(i, 16, 18);
  }

  public static boolean isDvbDbs(int i) {
    return i == 2 || isWithinRange(i, 24, 27);
  }

  public static boolean isValidDigitalBroadcastSystem(int i) {
    return isAribDbs(i) || isAtscDbs(i) || isDvbDbs(i);
  }

  public static boolean isValidChannelIdentifier(byte[] bArr, int i) {
    int i2 = bArr[i] & 252;
    return i2 == 4 ? bArr.length - i >= 3 : i2 == 8 && bArr.length - i >= 4;
  }

  public static boolean isValidDigitalServiceIdentification(byte[] bArr, int i) {
    byte b = bArr[i];
    int i2 = b & 128;
    int i3 = b & Byte.MAX_VALUE;
    int i4 = i + 1;
    if (i2 == 0) {
      return isAribDbs(i3)
          ? bArr.length - i4 >= 6
          : isAtscDbs(i3) ? bArr.length - i4 >= 4 : isDvbDbs(i3) && bArr.length - i4 >= 6;
    }
    if (i2 == 128 && isValidDigitalBroadcastSystem(i3)) {
      return isValidChannelIdentifier(bArr, i4);
    }
    return false;
  }

  public static boolean isValidExternalPlug(int i) {
    return isWithinRange(i, 1, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
  }

  public static boolean isValidExternalSource(byte[] bArr, int i) {
    byte b = bArr[i];
    int i2 = i + 1;
    if (b == 4) {
      return isValidExternalPlug(bArr[i2]);
    }
    if (b != 5 || bArr.length - i2 < 2) {
      return false;
    }
    return isValidPhysicalAddress(bArr, i2);
  }

  public static boolean isValidProgrammedInfo(int i) {
    return isWithinRange(i, 0, 11);
  }

  public static boolean isValidNotProgrammedErrorInfo(int i) {
    return isWithinRange(i, 0, 14);
  }

  public static boolean isValidTimerStatusData(byte[] bArr, int i) {
    boolean z;
    byte b = bArr[i];
    if ((b & HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED) == 16) {
      int i2 = b & 15;
      if (isValidProgrammedInfo(i2)) {
        if (i2 != 9 && i2 != 11) {
          return true;
        }
        z = true;
      }
      z = false;
    } else {
      int i3 = b & 15;
      if (isValidNotProgrammedErrorInfo(i3)) {
        if (i3 != 14) {
          return true;
        }
        z = true;
      }
      z = false;
    }
    int i4 = i + 1;
    if (!z || bArr.length - i4 < 2) {
      return false;
    }
    return isValidDurationHours(bArr[i4]) && isValidMinute(bArr[i4 + 1]);
  }

  public static boolean isValidPlayMode(int i) {
    return isWithinRange(i, 5, 7)
        || isWithinRange(i, 9, 11)
        || isWithinRange(i, 21, 23)
        || isWithinRange(i, 25, 27)
        || isWithinRange(i, 36, 37)
        || i == 32;
  }

  public static boolean isValidUiSoundPresenationControl(int i) {
    int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    return i2 == 32
        || i2 == 48
        || i2 == 128
        || i2 == 144
        || i2 == 160
        || isWithinRange(i2, 177, FrameworkStatsLog.f633xa546c0ca)
        || isWithinRange(i2, 193, 195);
  }

  public static boolean isValidTunerDeviceInfo(byte[] bArr) {
    int i = bArr[0] & Byte.MAX_VALUE;
    if (i == 0) {
      if (bArr.length >= 5) {
        return isValidDigitalServiceIdentification(bArr, 1);
      }
      return false;
    }
    if (i == 1) {
      return true;
    }
    return i == 2
        && bArr.length >= 5
        && isValidAnalogueBroadcastType(bArr[1])
        && isValidAnalogueFrequency(HdmiUtils.twoBytesToInt(bArr, 2))
        && isValidBroadcastSystem(bArr[4]);
  }

  public class PhysicalAddressValidator implements ParameterValidator {
    public PhysicalAddressValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 2) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0));
    }
  }

  public class SystemAudioModeRequestValidator extends PhysicalAddressValidator {
    public SystemAudioModeRequestValidator() {
      super();
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.PhysicalAddressValidator,
              // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length == 0) {
        return 0;
      }
      return super.isValid(bArr);
    }
  }

  public class ReportPhysicalAddressValidator implements ParameterValidator {
    public ReportPhysicalAddressValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 3) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0)
          && HdmiCecMessageValidator.isValidType(bArr[2])) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class RoutingChangeValidator implements ParameterValidator {
    public RoutingChangeValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 4) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0)
          && HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 2)) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class RecordStatusInfoValidator implements ParameterValidator {
    public RecordStatusInfoValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      boolean z = true;
      if (bArr.length < 1) {
        return 4;
      }
      if (!HdmiCecMessageValidator.isWithinRange(bArr[0], 1, 7)
          && !HdmiCecMessageValidator.isWithinRange(bArr[0], 9, 14)
          && !HdmiCecMessageValidator.isWithinRange(bArr[0], 16, 23)
          && !HdmiCecMessageValidator.isWithinRange(bArr[0], 26, 27)
          && bArr[0] != 31) {
        z = false;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class AsciiValidator implements ParameterValidator {
    public final int mMaxLength;
    public final int mMinLength;

    public AsciiValidator(int i) {
      this.mMinLength = i;
      this.mMaxLength = i;
    }

    public AsciiValidator(int i, int i2) {
      this.mMinLength = i;
      this.mMaxLength = i2;
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < this.mMinLength) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isValidAsciiString(bArr, 0, this.mMaxLength));
    }
  }

  public class OsdStringValidator implements ParameterValidator {
    public OsdStringValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 2) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidDisplayControl(bArr[0])
          && HdmiCecMessageValidator.isValidAsciiString(bArr, 1, 14)) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class MinimumOneByteRangeValidator implements ParameterValidator {
    public final int mMaxValue;
    public final int mMinValue;

    public MinimumOneByteRangeValidator(int i, int i2) {
      this.mMinValue = i;
      this.mMaxValue = i2;
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isWithinRange(bArr[0], this.mMinValue, this.mMaxValue));
    }
  }

  public class SingleByteRangeValidator implements ParameterValidator {
    public final int mMaxValue;
    public final int mMinValue;

    public SingleByteRangeValidator(int i, int i2) {
      this.mMinValue = i;
      this.mMaxValue = i2;
    }

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      if (bArr.length > 1) {
        return 5;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isWithinRange(bArr[0], this.mMinValue, this.mMaxValue));
    }
  }

  public class AnalogueTimerValidator implements ParameterValidator {
    public AnalogueTimerValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 11) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidDayOfMonth(bArr[0])
          && HdmiCecMessageValidator.isValidMonthOfYear(bArr[1])
          && HdmiCecMessageValidator.isValidHour(bArr[2])
          && HdmiCecMessageValidator.isValidMinute(bArr[3])
          && HdmiCecMessageValidator.isValidDurationHours(bArr[4])
          && HdmiCecMessageValidator.isValidMinute(bArr[5])
          && HdmiCecMessageValidator.isValidRecordingSequence(bArr[6])
          && HdmiCecMessageValidator.isValidAnalogueBroadcastType(bArr[7])
          && HdmiCecMessageValidator.isValidAnalogueFrequency(HdmiUtils.twoBytesToInt(bArr, 8))
          && HdmiCecMessageValidator.isValidBroadcastSystem(bArr[10])) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class DigitalTimerValidator implements ParameterValidator {
    public DigitalTimerValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 11) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidDayOfMonth(bArr[0])
          && HdmiCecMessageValidator.isValidMonthOfYear(bArr[1])
          && HdmiCecMessageValidator.isValidHour(bArr[2])
          && HdmiCecMessageValidator.isValidMinute(bArr[3])
          && HdmiCecMessageValidator.isValidDurationHours(bArr[4])
          && HdmiCecMessageValidator.isValidMinute(bArr[5])
          && HdmiCecMessageValidator.isValidRecordingSequence(bArr[6])
          && HdmiCecMessageValidator.isValidDigitalServiceIdentification(bArr, 7)) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class ExternalTimerValidator implements ParameterValidator {
    public ExternalTimerValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 9) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidDayOfMonth(bArr[0])
          && HdmiCecMessageValidator.isValidMonthOfYear(bArr[1])
          && HdmiCecMessageValidator.isValidHour(bArr[2])
          && HdmiCecMessageValidator.isValidMinute(bArr[3])
          && HdmiCecMessageValidator.isValidDurationHours(bArr[4])
          && HdmiCecMessageValidator.isValidMinute(bArr[5])
          && HdmiCecMessageValidator.isValidRecordingSequence(bArr[6])
          && HdmiCecMessageValidator.isValidExternalSource(bArr, 7)) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class TimerClearedStatusValidator implements ParameterValidator {
    public TimerClearedStatusValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      boolean z = true;
      if (bArr.length < 1) {
        return 4;
      }
      if (!HdmiCecMessageValidator.isWithinRange(bArr[0], 0, 2) && (bArr[0] & 255) != 128) {
        z = false;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class TimerStatusValidator implements ParameterValidator {
    public TimerStatusValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isValidTimerStatusData(bArr, 0));
    }
  }

  public class PlayModeValidator implements ParameterValidator {
    public PlayModeValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(HdmiCecMessageValidator.isValidPlayMode(bArr[0]));
    }
  }

  public class SelectAnalogueServiceValidator implements ParameterValidator {
    public SelectAnalogueServiceValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 4) {
        return 4;
      }
      boolean z = false;
      if (HdmiCecMessageValidator.isValidAnalogueBroadcastType(bArr[0])
          && HdmiCecMessageValidator.isValidAnalogueFrequency(HdmiUtils.twoBytesToInt(bArr, 1))
          && HdmiCecMessageValidator.isValidBroadcastSystem(bArr[3])) {
        z = true;
      }
      return HdmiCecMessageValidator.toErrorCode(z);
    }
  }

  public class SelectDigitalServiceValidator implements ParameterValidator {
    public SelectDigitalServiceValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 4) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isValidDigitalServiceIdentification(bArr, 0));
    }
  }

  public class TunerDeviceStatusValidator implements ParameterValidator {
    public TunerDeviceStatusValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      return HdmiCecMessageValidator.toErrorCode(
          HdmiCecMessageValidator.isValidTunerDeviceInfo(bArr));
    }
  }

  public class UserControlPressedValidator implements ParameterValidator {
    public UserControlPressedValidator() {}

    @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
    public int isValid(byte[] bArr) {
      if (bArr.length < 1) {
        return 4;
      }
      if (bArr.length == 1) {
        return 0;
      }
      byte b = bArr[0];
      if (b == 86) {
        return HdmiCecMessageValidator.toErrorCode(
            HdmiCecMessageValidator.isValidUiBroadcastType(bArr[1]));
      }
      if (b == 87) {
        return HdmiCecMessageValidator.toErrorCode(
            HdmiCecMessageValidator.isValidUiSoundPresenationControl(bArr[1]));
      }
      if (b == 96) {
        return HdmiCecMessageValidator.toErrorCode(
            HdmiCecMessageValidator.isValidPlayMode(bArr[1]));
      }
      if (b != 103) {
        return 0;
      }
      if (bArr.length >= 4) {
        return HdmiCecMessageValidator.toErrorCode(
            HdmiCecMessageValidator.isValidChannelIdentifier(bArr, 1));
      }
      return 4;
    }
  }
}
