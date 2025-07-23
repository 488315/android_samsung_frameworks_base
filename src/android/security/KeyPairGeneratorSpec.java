package android.security;

import android.content.Context;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

@Deprecated
/* loaded from: classes3.dex */
public final class KeyPairGeneratorSpec implements AlgorithmParameterSpec {
    private final Context mContext;
    private final Date mEndDate;
    private final int mKeySize;
    private final String mKeyType;
    private final String mKeystoreAlias;
    private final BigInteger mSerialNumber;
    private final AlgorithmParameterSpec mSpec;
    private final Date mStartDate;
    private final X500Principal mSubjectDN;

    public int getFlags() {
        return 0;
    }

    @Deprecated
    public boolean isEncryptionRequired() {
        return false;
    }

    public KeyPairGeneratorSpec(Context context, String str, String str2, int i, AlgorithmParameterSpec algorithmParameterSpec, X500Principal x500Principal, BigInteger bigInteger, Date date, Date date2, int i2) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("keyStoreAlias must not be empty");
        }
        if (x500Principal == null) {
            throw new IllegalArgumentException("subjectDN == null");
        }
        if (bigInteger == null) {
            throw new IllegalArgumentException("serialNumber == null");
        }
        if (date == null) {
            throw new IllegalArgumentException("startDate == null");
        }
        if (date2 == null) {
            throw new IllegalArgumentException("endDate == null");
        }
        if (date2.before(date)) {
            throw new IllegalArgumentException("endDate < startDate");
        }
        if (date2.before(date)) {
            throw new IllegalArgumentException("endDate < startDate");
        }
        this.mContext = context;
        this.mKeystoreAlias = str;
        this.mKeyType = str2;
        this.mKeySize = i;
        this.mSpec = algorithmParameterSpec;
        this.mSubjectDN = x500Principal;
        this.mSerialNumber = bigInteger;
        this.mStartDate = date;
        this.mEndDate = date2;
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public String getKeyType() {
        return this.mKeyType;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public X500Principal getSubjectDN() {
        return this.mSubjectDN;
    }

    public BigInteger getSerialNumber() {
        return this.mSerialNumber;
    }

    public Date getStartDate() {
        return this.mStartDate;
    }

    public Date getEndDate() {
        return this.mEndDate;
    }

    @Deprecated
    public static final class Builder {
        private final Context mContext;
        private Date mEndDate;
        private int mKeySize = -1;
        private String mKeyType;
        private String mKeystoreAlias;
        private BigInteger mSerialNumber;
        private AlgorithmParameterSpec mSpec;
        private Date mStartDate;
        private X500Principal mSubjectDN;

        @Deprecated
        public Builder setEncryptionRequired() {
            return this;
        }

        public Builder(Context context) {
            if (context == null) {
                throw new NullPointerException("context == null");
            }
            this.mContext = context;
        }

        public Builder setAlias(String str) {
            if (str == null) {
                throw new NullPointerException("alias == null");
            }
            this.mKeystoreAlias = str;
            return this;
        }

        public Builder setKeyType(String str) throws NoSuchAlgorithmException {
            if (str == null) {
                throw new NullPointerException("keyType == null");
            }
            try {
                KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(str);
                this.mKeyType = str;
                return this;
            } catch (IllegalArgumentException unused) {
                throw new NoSuchAlgorithmException("Unsupported key type: " + str);
            }
        }

        public Builder setKeySize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("keySize < 0");
            }
            this.mKeySize = i;
            return this;
        }

        public Builder setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null) {
                throw new NullPointerException("spec == null");
            }
            this.mSpec = algorithmParameterSpec;
            return this;
        }

        public Builder setSubject(X500Principal x500Principal) {
            if (x500Principal == null) {
                throw new NullPointerException("subject == null");
            }
            this.mSubjectDN = x500Principal;
            return this;
        }

        public Builder setSerialNumber(BigInteger bigInteger) {
            if (bigInteger == null) {
                throw new NullPointerException("serialNumber == null");
            }
            this.mSerialNumber = bigInteger;
            return this;
        }

        public Builder setStartDate(Date date) {
            if (date == null) {
                throw new NullPointerException("startDate == null");
            }
            this.mStartDate = date;
            return this;
        }

        public Builder setEndDate(Date date) {
            if (date == null) {
                throw new NullPointerException("endDate == null");
            }
            this.mEndDate = date;
            return this;
        }

        public KeyPairGeneratorSpec build() {
            return new KeyPairGeneratorSpec(this.mContext, this.mKeystoreAlias, this.mKeyType, this.mKeySize, this.mSpec, this.mSubjectDN, this.mSerialNumber, this.mStartDate, this.mEndDate, 0);
        }
    }
}
