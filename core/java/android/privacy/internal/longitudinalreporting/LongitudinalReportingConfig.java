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

  public LongitudinalReportingConfig(
      String encoderId, double probabilityF, double probabilityP, double probabilityQ) {
    boolean z = false;
    Preconditions.checkArgument(
        probabilityF >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && probabilityF <= 1.0d,
        "probabilityF must be in range [0.0, 1.0]");
    this.mProbabilityF = probabilityF;
    Preconditions.checkArgument(
        probabilityP >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && probabilityP <= 1.0d,
        "probabilityP must be in range [0.0, 1.0]");
    this.mProbabilityP = probabilityP;
    if (probabilityQ >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && probabilityQ <= 1.0d) {
      z = true;
    }
    Preconditions.checkArgument(z, "probabilityQ must be in range [0.0, 1.0]");
    this.mProbabilityQ = probabilityQ;
    Preconditions.checkArgument(!TextUtils.isEmpty(encoderId), "encoderId cannot be empty");
    this.mEncoderId = encoderId;
    this.mIRRConfig =
        new RapporConfig(
            encoderId,
            1,
            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
            probabilityF,
            1.0d - probabilityF,
            1,
            1);
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
    return String.format(
        "EncoderId: %s, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f",
        this.mEncoderId,
        Double.valueOf(this.mProbabilityF),
        Double.valueOf(this.mProbabilityP),
        Double.valueOf(this.mProbabilityQ));
  }
}
