package android.privacy.internal.rappor;

import android.hardware.scontext.SContextConstants;
import android.privacy.DifferentialPrivacyConfig;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public class RapporConfig implements DifferentialPrivacyConfig {
    private static final String ALGORITHM_NAME = "Rappor";
    final String mEncoderId;
    final int mNumBits;
    final int mNumBloomHashes;
    final int mNumCohorts;
    final double mProbabilityF;
    final double mProbabilityP;
    final double mProbabilityQ;

    public RapporConfig(String str, int i, double d, double d2, double d3, int i2, int i3) {
        Preconditions.checkArgument(!TextUtils.isEmpty(str), "encoderId cannot be empty");
        this.mEncoderId = str;
        Preconditions.checkArgument(i > 0, "numBits needs to be > 0");
        this.mNumBits = i;
        Preconditions.checkArgument(d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d <= 1.0d, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        Preconditions.checkArgument(d2 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d2 <= 1.0d, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        Preconditions.checkArgument(d3 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d3 <= 1.0d, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        Preconditions.checkArgument(i2 > 0, "numCohorts needs to be > 0");
        this.mNumCohorts = i2;
        Preconditions.checkArgument(i3 > 0, "numBloomHashes needs to be > 0");
        this.mNumBloomHashes = i3;
    }

    @Override // android.privacy.DifferentialPrivacyConfig
    public String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    public String toString() {
        return String.format("EncoderId: %s, NumBits: %d, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f, NumCohorts: %d, NumBloomHashes: %d", this.mEncoderId, Integer.valueOf(this.mNumBits), Double.valueOf(this.mProbabilityF), Double.valueOf(this.mProbabilityP), Double.valueOf(this.mProbabilityQ), Integer.valueOf(this.mNumCohorts), Integer.valueOf(this.mNumBloomHashes));
    }
}
