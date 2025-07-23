package android.sec.enterprise.certificate;

import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
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
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        if (DEBUG) {
            Log.d(TAG, "engineValidate");
        }
        if (!(certPathParameters instanceof PKIXParameters)) {
            throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXParameters");
        }
        if (!DelegatingCertPathValidatorHelper.isChainTrustedByMdm(certPath.getCertificates())) {
            throw new CertPathValidatorException("A certificate from chain is not trusted by MDM policy");
        }
        PKIXRevocationChecker pKIXRevocationChecker = (PKIXRevocationChecker) engineGetRevocationChecker();
        Set<PKIXRevocationChecker.Option> options = pKIXRevocationChecker.getOptions();
        DelegatingCertPathValidatorHelper.setRevocationChecker(pKIXRevocationChecker, (PKIXParameters) certPathParameters);
        try {
            try {
                return this.mDelegate.validate(certPath, certPathParameters);
            } catch (CertPathValidatorException e) {
                AuditLog.logEventAsUser(UserHandle.myUserId(), 354, e.getMessage());
                throw e;
            }
        } finally {
            pKIXRevocationChecker.setOptions(options);
        }
    }
}
