package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;
import com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec;
import java.security.Permission;
import java.security.spec.DSAParameterSpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;

/* loaded from: classes5.dex */
class BouncyCastleProviderConfiguration implements ProviderConfiguration {
    private volatile Object dhDefaultParams;
    private volatile ECParameterSpec ecImplicitCaParams;
    private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA);
    private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.EC_IMPLICITLY_CA);
    private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS);
    private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.DH_DEFAULT_PARAMS);
    private static Permission BC_EC_CURVE_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.ACCEPTABLE_EC_CURVES);
    private static Permission BC_ADDITIONAL_EC_CURVE_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.ADDITIONAL_EC_PARAMETERS);
    private ThreadLocal ecThreadSpec = new ThreadLocal();
    private ThreadLocal dhThreadSpec = new ThreadLocal();
    private volatile Set acceptableNamedCurves = new HashSet();
    private volatile Map additionalECParameters = new HashMap();

    BouncyCastleProviderConfiguration() {
    }

    void setParameter(String str, Object obj) {
        ECParameterSpec eCParameterSpec;
        SecurityManager securityManager = System.getSecurityManager();
        if (str.equals(ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            if ((obj instanceof ECParameterSpec) || obj == null) {
                eCParameterSpec = (ECParameterSpec) obj;
            } else {
                eCParameterSpec = EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj);
            }
            if (eCParameterSpec == null) {
                this.ecThreadSpec.remove();
                return;
            } else {
                this.ecThreadSpec.set(eCParameterSpec);
                return;
            }
        }
        if (str.equals(ConfigurableProvider.EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            if ((obj instanceof ECParameterSpec) || obj == null) {
                this.ecImplicitCaParams = (ECParameterSpec) obj;
                return;
            } else {
                this.ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj);
                return;
            }
        }
        if (str.equals(ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
            }
            if (!(obj instanceof DHParameterSpec) && !(obj instanceof DHParameterSpec[]) && obj != null) {
                throw new IllegalArgumentException("not a valid DHParameterSpec");
            }
            if (obj == null) {
                this.dhThreadSpec.remove();
                return;
            } else {
                this.dhThreadSpec.set(obj);
                return;
            }
        }
        if (str.equals(ConfigurableProvider.DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_PERMISSION);
            }
            if ((obj instanceof DHParameterSpec) || (obj instanceof DHParameterSpec[]) || obj == null) {
                this.dhDefaultParams = obj;
                return;
            }
            throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
        }
        if (str.equals(ConfigurableProvider.ACCEPTABLE_EC_CURVES)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_CURVE_PERMISSION);
            }
            this.acceptableNamedCurves = (Set) obj;
        } else if (str.equals(ConfigurableProvider.ADDITIONAL_EC_PARAMETERS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
            }
            this.additionalECParameters = (Map) obj;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public ECParameterSpec getEcImplicitlyCa() {
        ECParameterSpec eCParameterSpec = (ECParameterSpec) this.ecThreadSpec.get();
        return eCParameterSpec != null ? eCParameterSpec : this.ecImplicitCaParams;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public DHParameterSpec getDHDefaultParameters(int i) {
        Object obj = this.dhThreadSpec.get();
        if (obj == null) {
            obj = this.dhDefaultParams;
        }
        if (obj instanceof DHParameterSpec) {
            DHParameterSpec dHParameterSpec = (DHParameterSpec) obj;
            if (dHParameterSpec.getP().bitLength() == i) {
                return dHParameterSpec;
            }
        } else if (obj instanceof DHParameterSpec[]) {
            DHParameterSpec[] dHParameterSpecArr = (DHParameterSpec[]) obj;
            for (int i2 = 0; i2 != dHParameterSpecArr.length; i2++) {
                if (dHParameterSpecArr[i2].getP().bitLength() == i) {
                    return dHParameterSpecArr[i2];
                }
            }
        }
        DHParameters dHParameters = (DHParameters) CryptoServicesRegistrar.getSizedProperty(CryptoServicesRegistrar.Property.DH_DEFAULT_PARAMS, i);
        if (dHParameters != null) {
            return new DHDomainParameterSpec(dHParameters);
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public DSAParameterSpec getDSADefaultParameters(int i) {
        DSAParameters dSAParameters = (DSAParameters) CryptoServicesRegistrar.getSizedProperty(CryptoServicesRegistrar.Property.DSA_DEFAULT_PARAMS, i);
        if (dSAParameters != null) {
            return new DSAParameterSpec(dSAParameters.getP(), dSAParameters.getQ(), dSAParameters.getG());
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public Set getAcceptableNamedCurves() {
        return Collections.unmodifiableSet(this.acceptableNamedCurves);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public Map getAdditionalECParameters() {
        return Collections.unmodifiableMap(this.additionalECParameters);
    }
}
