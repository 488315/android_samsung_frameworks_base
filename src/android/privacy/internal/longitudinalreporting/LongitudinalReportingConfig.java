package android.privacy.internal.longitudinalreporting;

import android.hardware.scontext.SContextConstants;
import android.privacy.DifferentialPrivacyConfig;
import android.privacy.internal.rappor.RapporConfig;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public class LongitudinalReportingConfig implements DifferentialPrivacyConfig {
    private static final String ALGORITHM_NAME = "LongitudinalReporting";
    private final String mEncoderId;
    private final RapporConfig mIRRConfig;
    private final double mProbabilityF;
    private final double mProbabilityP;
    private final double mProbabilityQ;

    public LongitudinalReportingConfig(String str, double d, double d2, double d3) {
        boolean z = false;
        Preconditions.checkArgument(d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d <= 1.0d, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        Preconditions.checkArgument(d2 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d2 <= 1.0d, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        if (d3 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d3 <= 1.0d) {
            z = true;
        }
        Preconditions.checkArgument(z, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        Preconditions.checkArgument(!TextUtils.isEmpty(str), "encoderId cannot be empty");
        this.mEncoderId = str;
        this.mIRRConfig = new RapporConfig(str, 1, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, d, 1.0d - d, 1, 1);
    }

    @Override // android.privacy.DifferentialPrivacyConfig
    public String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    RapporConfig getIRRConfig() {
        return this.mIRRConfig;
    }

    double getProbabilityP() {
        return this.mProbabilityP;
    }

    double getProbabilityQ() {
        return this.mProbabilityQ;
    }

    String getEncoderId() {
        return this.mEncoderId;
    }

    public String toString() {
        return String.format("EncoderId: %s, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f", this.mEncoderId, Double.valueOf(this.mProbabilityF), Double.valueOf(this.mProbabilityP), Double.valueOf(this.mProbabilityQ));
    }
}
