package android.privacy.internal.longitudinalreporting;

import android.hardware.scontext.SContextConstants;
import android.privacy.DifferentialPrivacyEncoder;
import android.privacy.internal.rappor.RapporConfig;
import android.privacy.internal.rappor.RapporEncoder;

/* loaded from: classes3.dex */
public class LongitudinalReportingEncoder implements DifferentialPrivacyEncoder {
    private static final boolean DEBUG = false;
    private static final String PRR1_ENCODER_ID = "prr1_encoder_id";
    private static final String PRR2_ENCODER_ID = "prr2_encoder_id";
    private static final String TAG = "LongitudinalEncoder";
    private final LongitudinalReportingConfig mConfig;
    private final Boolean mFakeValue;
    private final RapporEncoder mIRREncoder;
    private final boolean mIsSecure;

    public static LongitudinalReportingEncoder createEncoder(LongitudinalReportingConfig longitudinalReportingConfig, byte[] bArr) {
        return new LongitudinalReportingEncoder(longitudinalReportingConfig, true, bArr);
    }

    public static LongitudinalReportingEncoder createInsecureEncoderForTest(LongitudinalReportingConfig longitudinalReportingConfig) {
        return new LongitudinalReportingEncoder(longitudinalReportingConfig, false, null);
    }

    private LongitudinalReportingEncoder(LongitudinalReportingConfig longitudinalReportingConfig, boolean z, byte[] bArr) {
        RapporEncoder rapporEncoderCreateInsecureEncoderForTest;
        this.mConfig = longitudinalReportingConfig;
        this.mIsSecure = z;
        if (getLongTermRandomizedResult(longitudinalReportingConfig.getProbabilityP(), z, bArr, longitudinalReportingConfig.getEncoderId() + PRR1_ENCODER_ID)) {
            this.mFakeValue = Boolean.valueOf(getLongTermRandomizedResult(longitudinalReportingConfig.getProbabilityQ(), z, bArr, longitudinalReportingConfig.getEncoderId() + PRR2_ENCODER_ID));
        } else {
            this.mFakeValue = null;
        }
        RapporConfig iRRConfig = longitudinalReportingConfig.getIRRConfig();
        if (z) {
            rapporEncoderCreateInsecureEncoderForTest = RapporEncoder.createEncoder(iRRConfig, bArr);
        } else {
            rapporEncoderCreateInsecureEncoderForTest = RapporEncoder.createInsecureEncoderForTest(iRRConfig);
        }
        this.mIRREncoder = rapporEncoderCreateInsecureEncoderForTest;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeString(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBoolean(boolean z) {
        Boolean bool = this.mFakeValue;
        if (bool != null) {
            z = bool.booleanValue();
        }
        return this.mIRREncoder.encodeBoolean(z);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBits(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public LongitudinalReportingConfig getConfig() {
        return this.mConfig;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public boolean isInsecureEncoderForTest() {
        return !this.mIsSecure;
    }

    public static boolean getLongTermRandomizedResult(double d, boolean z, byte[] bArr, String str) {
        RapporEncoder rapporEncoderCreateInsecureEncoderForTest;
        double d2 = d < 0.5d ? d * 2.0d : (1.0d - d) * 2.0d;
        boolean z2 = d >= 0.5d;
        RapporConfig rapporConfig = new RapporConfig(str, 1, d2, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 1.0d, 1, 1);
        if (z) {
            rapporEncoderCreateInsecureEncoderForTest = RapporEncoder.createEncoder(rapporConfig, bArr);
        } else {
            rapporEncoderCreateInsecureEncoderForTest = RapporEncoder.createInsecureEncoderForTest(rapporConfig);
        }
        return rapporEncoderCreateInsecureEncoderForTest.encodeBoolean(z2)[0] > 0;
    }
}
