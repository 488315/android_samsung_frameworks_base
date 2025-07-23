package android.security.keystore.recovery;

import android.annotation.SystemApi;
import java.security.GeneralSecurityException;

@SystemApi
/* loaded from: classes3.dex */
public class InternalRecoveryServiceException extends GeneralSecurityException {
    public InternalRecoveryServiceException(String str) {
        super(str);
    }

    public InternalRecoveryServiceException(String str, Throwable th) {
        super(str, th);
    }
}
