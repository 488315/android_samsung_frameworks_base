package android.sec.enterprise.certificate;

import android.sec.enterprise.auditlog.AuditEvents;
import android.util.Log;
import com.samsung.android.security.mdf.MdfUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.util.Set;

/* loaded from: classes3.dex */
public final class DelegatingCertPathValidator extends CertPathValidatorSpi {
    private static boolean DEBUG = false;
    private static final String TAG = "DelegatingCertPathValidator";
    private final CertPathValidator mDelegate;

    public DelegatingCertPathValidator() {
        try {
            this.mDelegate = CertPathValidator.getInstance("PKIX", "CertPathProvider");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // java.security.cert.CertPathValidatorSpi
    public CertPathChecker engineGetRevocationChecker() {
        return this.mDelegate.getRevocationChecker();
    }

    @Override // java.security.cert.CertPathValidatorSpi
    public CertPathValidatorResult engineValidate(CertPath cp, CertPathParameters params) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        if (DEBUG) {
            Log.m94d(TAG, "engineValidate");
        }
        if (!(params instanceof PKIXParameters)) {
            throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXParameters");
        }
        if (!DelegatingCertPathValidatorHelper.isChainTrustedByMdm(cp.getCertificates())) {
            throw new CertPathValidatorException("A certificate from chain is not trusted by MDM policy");
        }
        PKIXRevocationChecker revChecker = (PKIXRevocationChecker) engineGetRevocationChecker();
        Set<PKIXRevocationChecker.Option> opt = revChecker.getOptions();
        DelegatingCertPathValidatorHelper.setRevocationChecker(revChecker, (PKIXParameters) params);
        try {
            try {
                CertPathValidatorResult ret = this.mDelegate.validate(cp, params);
                return ret;
            } catch (CertPathValidatorException e) {
                MdfUtils.logMdf(String.format(AuditEvents.AUDIT_CERT_PATH_VALIDATOR_FAILED, e.getMessage()), "", false, 3, "CertPathValidator");
                throw e;
            }
        } finally {
            revChecker.setOptions(opt);
        }
    }
}
