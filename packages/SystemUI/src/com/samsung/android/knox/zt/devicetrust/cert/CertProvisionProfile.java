package com.samsung.android.knox.zt.devicetrust.cert;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.KnoxZtException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CertProvisionProfile {
    public static final String CA_DEFAULT = "default";
    public static final String CA_LOOPBACK = "loopback";
    public static final int CLIENT_ID_TYPE_IMEI = 0;
    public static final int CLIENT_ID_TYPE_SAK_UID = 1;
    public static final int KEY_OWNER_APP = 1;
    public static final int KEY_OWNER_SYSTEM = 0;
    public static final int KEY_PURPOSE_ENCRYPT = 1;
    public static final int KEY_PURPOSE_SIGN = 4;
    public static final String KEY_TYPE_EC = "EC";
    public static final String KEY_TYPE_RSA = "RSA";
    public static final String PROTOCOL_ACME = "acme";
    public static final String PROTOCOL_SCEP = "scep";
    public static final String PROVIDER_ANDROID = "AndroidKeyStore";
    public static final String PROVIDER_UCM = "UcmKeystore";
    public static final String SAN_DNS_NAME = "dNSName";
    public static final String SAN_IP_ADDRESS = "iPAddress";
    public static final String SAN_RFC822_NAME = "rfc822Name";
    public static final String SAN_URI = "uniformResourceIdentifier";
    public static final String TYPE_PROVISION = "provision";
    public static final String TYPE_RENEW = "renew";
    public static final String TYPE_REVOKE = "revoke";
    public final String mChallengePassword;
    public final int mClientIdentifierType;
    public final List<String> mClientIdentifiers;
    public final String mKeyAlias;
    public final List<String> mKeyExtendedPurposes;
    public final int mKeyOwner;
    public final String mKeyProvider;
    public final String mProtocol;
    public final String mProvisionType;
    public final String mRootCA;
    public final String mServerHost;
    public final String mServerPath;
    public final String mServerPort;
    public final Bundle mSubject;
    public final Bundle mSubjectAltName;
    public final int mSystemKeyPurposes;
    public final int mSystemKeySize;
    public final String mSystemKeyType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public final String mKeyAlias;
        public final int mKeyOwner;
        public final String mKeyProvider;
        public final String mProtocol;
        public final String mProvisionType;
        public final String mRootCA;
        public final Bundle mSubject;
        public String mServerHost = "";
        public String mServerPort = "";
        public String mServerPath = "";
        public Bundle mSubjectAltName = new Bundle();
        public List<String> mKeyExtendedPurposes = new ArrayList();
        public String mChallengePassword = "";
        public int mClientIdentifierType = -1;
        public List<String> mClientIdentifiers = new ArrayList();
        public String mSystemKeyType = "";
        public int mSystemKeyPurposes = 0;
        public int mSystemKeySize = -1;

        public Builder(String str, String str2, String str3, String str4, int i, String str5, Bundle bundle) {
            this.mRootCA = str;
            this.mProtocol = str2;
            this.mProvisionType = str3;
            this.mKeyProvider = str4;
            this.mKeyOwner = i;
            this.mKeyAlias = str5;
            this.mSubject = bundle;
        }

        public CertProvisionProfile build() throws KnoxZtException {
            return new CertProvisionProfile(this.mRootCA, this.mProtocol, this.mProvisionType, this.mKeyProvider, this.mKeyOwner, this.mKeyAlias, this.mSubject, this.mServerHost, this.mServerPort, this.mServerPath, this.mSubjectAltName, this.mKeyExtendedPurposes, this.mChallengePassword, this.mClientIdentifierType, this.mClientIdentifiers, this.mSystemKeyType, this.mSystemKeyPurposes, this.mSystemKeySize, 0);
        }

        public Builder setChallengePassword(String str) {
            this.mChallengePassword = str;
            return this;
        }

        public Builder setClientIdentifierType(int i) {
            this.mClientIdentifierType = i;
            return this;
        }

        public Builder setClientIdentifiers(String... strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.mClientIdentifiers = Arrays.asList(strArr);
            return this;
        }

        public Builder setKeyExtendedPurposes(String... strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.mKeyExtendedPurposes = Arrays.asList(strArr);
            return this;
        }

        public Builder setServerHost(String str) {
            this.mServerHost = str;
            return this;
        }

        public Builder setServerPath(String str) {
            this.mServerPath = str;
            return this;
        }

        public Builder setServerPort(String str) {
            this.mServerPort = str;
            return this;
        }

        public Builder setSubjectAltName(Bundle bundle) {
            this.mSubjectAltName = bundle;
            return this;
        }

        public Builder setSystemKeyPurposes(int i) {
            this.mSystemKeyPurposes = i;
            return this;
        }

        public Builder setSystemKeySize(int i) {
            this.mSystemKeySize = i;
            return this;
        }

        public Builder setSystemKeyType(String str) {
            this.mSystemKeyType = str;
            return this;
        }
    }

    public /* synthetic */ CertProvisionProfile(String str, String str2, String str3, String str4, int i, String str5, Bundle bundle, String str6, String str7, String str8, Bundle bundle2, List list, String str9, int i2, List list2, String str10, int i3, int i4, int i5) {
        this(str, str2, str3, str4, i, str5, bundle, str6, str7, str8, bundle2, list, str9, i2, list2, str10, i3, i4);
    }

    public static List<Integer> getSupportedClientIdentifierTypes() {
        return Arrays.asList(0, 1);
    }

    public static List<Integer> getSupportedKeyOwners() {
        return Arrays.asList(0, 1);
    }

    public static List<String> getSupportedKeyProviders() {
        return Arrays.asList(PROVIDER_ANDROID, "UcmKeystore");
    }

    public static List<String> getSupportedProtocols() {
        return Arrays.asList(PROTOCOL_ACME, PROTOCOL_SCEP);
    }

    public static List<String> getSupportedProvisionTypes() {
        return Arrays.asList(TYPE_PROVISION, "renew", TYPE_REVOKE);
    }

    public static List<String> getSupportedRootCAs() {
        return Arrays.asList("default", CA_LOOPBACK);
    }

    public static List<String> getSupportedSubjectAltName() {
        return Arrays.asList(SAN_DNS_NAME, SAN_IP_ADDRESS, SAN_RFC822_NAME, SAN_URI);
    }

    public static List<Integer> getSupportedSystemKeyPurposes() {
        return Arrays.asList(1, 4, 5);
    }

    public static List<String> getSupportedSystemKeyTypes() {
        return Arrays.asList("RSA", KEY_TYPE_EC);
    }

    public final String convertKeyProvider(String str) {
        return PROVIDER_ANDROID.equals(str) ? PROVIDER_ANDROID : Pattern.matches("^([a-zA-Z_]\\w*)+([.][a-zA-Z_]\\w*)+:(.)*$", str) ? "UcmKeystore" : "";
    }

    public String getChallengePassword() {
        return this.mChallengePassword;
    }

    public int getClientIdentifierType() {
        return this.mClientIdentifierType;
    }

    public List<String> getClientIdentifiers() {
        return this.mClientIdentifiers;
    }

    public String getKeyAlias() {
        return this.mKeyAlias;
    }

    public List<String> getKeyExtendedPurposes() {
        return this.mKeyExtendedPurposes;
    }

    public int getKeyOwner() {
        return this.mKeyOwner;
    }

    public String getKeyProvider() {
        return this.mKeyProvider;
    }

    public String getProtocol() {
        return this.mProtocol;
    }

    public String getProvisionType() {
        return this.mProvisionType;
    }

    public String getRootCA() {
        return this.mRootCA;
    }

    public String getServerHost() {
        return this.mServerHost;
    }

    public String getServerPath() {
        return this.mServerPath;
    }

    public String getServerPort() {
        return this.mServerPort;
    }

    public Bundle getSubject() {
        return this.mSubject;
    }

    public Bundle getSubjectAltName() {
        return this.mSubjectAltName;
    }

    public int getSystemKeyPurposes() {
        return this.mSystemKeyPurposes;
    }

    public int getSystemKeySize() {
        return this.mSystemKeySize;
    }

    public String getSystemKeyType() {
        return this.mSystemKeyType;
    }

    public final boolean validateStringOrStringArrayList(Object obj) {
        try {
            if (obj instanceof String) {
                return true;
            }
            if (!(obj instanceof ArrayList)) {
                return false;
            }
            ArrayList arrayList = (ArrayList) obj;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
            }
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private CertProvisionProfile(String str, String str2, String str3, String str4, int i, String str5, Bundle bundle, String str6, String str7, String str8, Bundle bundle2, List<String> list, String str9, int i2, List<String> list2, String str10, int i3, int i4) {
        if (!getSupportedRootCAs().contains(str)) {
            throw new KnoxZtException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("invalid root ca : ", str));
        }
        this.mRootCA = str;
        if (!getSupportedProtocols().contains(str2)) {
            throw new KnoxZtException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("invalid protocol : ", str2));
        }
        this.mProtocol = str2;
        if (!getSupportedProvisionTypes().contains(str3)) {
            throw new KnoxZtException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("invalid provision type : ", str3));
        }
        this.mProvisionType = str3;
        if (!getSupportedKeyProviders().contains(convertKeyProvider(str4))) {
            throw new KnoxZtException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("invalid key provider : ", str4));
        }
        this.mKeyProvider = str4;
        if (!getSupportedKeyOwners().contains(Integer.valueOf(i))) {
            throw new KnoxZtException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "invalid key owner : "));
        }
        this.mKeyOwner = i;
        this.mKeyAlias = str5;
        for (String str11 : bundle.keySet()) {
            if (!validateStringOrStringArrayList(bundle.get(str11))) {
                throw new KnoxZtException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("subject ", str11, "'s value must be passed as type String or StringArrayList"));
            }
        }
        this.mSubject = bundle;
        this.mServerHost = str6;
        this.mServerPort = str7;
        this.mServerPath = str8;
        for (String str12 : bundle2.keySet()) {
            if (!getSupportedSubjectAltName().contains(str12)) {
                throw new KnoxZtException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("invalid subject alternative name property : ", str12));
            }
            if (!validateStringOrStringArrayList(bundle2.get(str12))) {
                throw new KnoxZtException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("subject alternative name ", str12, "'s value must be passed as type String or StringArrayList"));
            }
        }
        this.mSubjectAltName = bundle2;
        this.mKeyExtendedPurposes = list;
        this.mChallengePassword = str9;
        if (i2 != -1 && !getSupportedClientIdentifierTypes().contains(Integer.valueOf(i2))) {
            throw new KnoxZtException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "invalid client identifier type : "));
        }
        this.mClientIdentifierType = i2;
        this.mClientIdentifiers = list2;
        if (!str10.equals("") && !getSupportedSystemKeyTypes().contains(str10)) {
            throw new KnoxZtException("invalid system key type : ".concat(str10));
        }
        this.mSystemKeyType = str10;
        if (i3 != 0 && !getSupportedSystemKeyPurposes().contains(Integer.valueOf(i3))) {
            throw new KnoxZtException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "invalid system key purposes : "));
        }
        this.mSystemKeyPurposes = i3;
        this.mSystemKeySize = i4;
    }
}
