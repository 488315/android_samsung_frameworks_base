package com.android.server.location.gnss.sec;

import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class CarrierConfig {
    public static CarrierConfig mInstance;
    public Carrier mCarrier;
    public String mDeviceMode;
    public String mSalesCode;
    public String mSimOperator;
    public HashMap mSalesCodeToCarrierMap = new HashMap();
    public HashMap mExtraConfigHashMap = new HashMap();

    public enum Carrier {
        USA_TMO,
        USA_ATT,
        USA_AIO,
        USA_TMK,
        USA_TFN,
        USA_TFO,
        USA_TFA,
        USA_TFC,
        USA_XAU,
        USA_XAA,
        USA_XAR,
        USA_XAG,
        USA_DSH,
        USA_DSA,
        USA_VZW,
        USA_SPR,
        USA_XAS,
        USA_BST,
        USA_VMU,
        USA_USC,
        USA_ACG,
        USA_DSG,
        JPN_DCM,
        JPN_KDI,
        JPN_RKT,
        CAD_TLS,
        CAD_BMC,
        CAD_RWC,
        CAD_XAC,
        KOR_SKT,
        KOR_KTT,
        KOR_LGT,
        CHN_CMC,
        CHN_CTC,
        CHN_CHU,
        CHN_CHN,
        CHN_CHC,
        ARG_UFN,
        MEX_MNX,
        MEX_IUS,
        MEX_UNE,
        PER_PEO,
        PER_PNT,
        PER_PET,
        SUI_SWC,
        TUR_TUR,
        SWA_IND,
        NO_OPERATOR
    }

    public CarrierConfig() {
        setSalescodeToHashMap();
    }

    public static synchronized CarrierConfig getInstance() {
        CarrierConfig carrierConfig;
        synchronized (CarrierConfig.class) {
            if (mInstance == null) {
                mInstance = new CarrierConfig();
            }
            carrierConfig = mInstance;
        }
        return carrierConfig;
    }

    public void setSalesCode(String str) {
        this.mSalesCode = str;
        this.mCarrier = getCarrier();
    }

    public void setSimOperator(String str) {
        this.mSimOperator = str;
        this.mCarrier = getCarrier();
    }

    public boolean isNoOperator() {
        return this.mCarrier == Carrier.NO_OPERATOR;
    }

    public boolean isKoreaMarket() {
        boolean z = "SKT".equals(this.mSalesCode) || "SKC".equals(this.mSalesCode) || "SKO".equals(this.mSalesCode) || "LGT".equals(this.mSalesCode) || "LUC".equals(this.mSalesCode) || "LUO".equals(this.mSalesCode) || "KTT".equals(this.mSalesCode) || "KTC".equals(this.mSalesCode) || "KTO".equals(this.mSalesCode) || "KOO".equals(this.mSalesCode);
        Log.d("CarrierConfigForGnss", "isKORMarket : " + z);
        return z;
    }

    public boolean isKoreaLgu() {
        return "LGT".equals(this.mSalesCode) || "LUC".equals(this.mSalesCode) || "LUO".equals(this.mSalesCode);
    }

    public boolean isKoreaSktSim() {
        return "45005".equals(this.mSimOperator) || "45011".equals(this.mSimOperator);
    }

    public boolean isKoreaLguSim() {
        return "45006".equals(this.mSimOperator);
    }

    public boolean isKoreaKttSim() {
        return "45008".equals(this.mSimOperator);
    }

    public boolean isChinaCarrier() {
        Carrier carrier = this.mCarrier;
        return carrier == Carrier.CHN_CHN || carrier == Carrier.CHN_CHU || carrier == Carrier.CHN_CHC || carrier == Carrier.CHN_CMC || carrier == Carrier.CHN_CTC;
    }

    public boolean isChinaOpen() {
        return this.mCarrier == Carrier.CHN_CHN;
    }

    public boolean isChinaMobile() {
        return this.mCarrier == Carrier.CHN_CMC;
    }

    public boolean isChinaUnicom() {
        return this.mCarrier == Carrier.CHN_CHU;
    }

    public boolean isChinaTdOpen() {
        return this.mCarrier == Carrier.CHN_CHC;
    }

    public boolean isChinaTelecom() {
        return this.mCarrier == Carrier.CHN_CTC;
    }

    public boolean isUSAMarket() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY);
        String str2 = SystemProperties.get("ro.csc.countryiso_code");
        Log.d("CarrierConfigForGnss", "isUSAMarket : code/country_Code/countryISO_Code = " + this.mSalesCode + "/" + str + "/" + str2);
        boolean z = "TMB".equals(this.mSalesCode) || "ATT".equals(this.mSalesCode) || "TFA".equals(this.mSalesCode) || "TFO".equals(this.mSalesCode) || "TFN".equals(this.mSalesCode) || "TFC".equals(this.mSalesCode) || "AIO".equals(this.mSalesCode) || "TMK".equals(this.mSalesCode) || "DSH".equals(this.mSalesCode) || "DSA".equals(this.mSalesCode) || "DSG".equals(this.mSalesCode) || "XAU".equals(this.mSalesCode) || "XAR".equals(this.mSalesCode) || "XAG".equals(this.mSalesCode) || "XAA".equals(this.mSalesCode) || "VZW".equals(this.mSalesCode) || "SPR".equals(this.mSalesCode) || "USC".equals(this.mSalesCode) || "ACG".equals(this.mSalesCode) || "BST".equals(this.mSalesCode) || "VMU".equals(this.mSalesCode) || "XAS".equals(this.mSalesCode) || "USA".equals(str) || "US".equals(str2);
        Log.d("CarrierConfigForGnss", "isUSAMarket : " + z);
        return z;
    }

    public boolean isUsaVerizon() {
        return "VZW".equals(this.mSalesCode);
    }

    public boolean isUsaTmbServerCarrier() {
        Carrier carrier = this.mCarrier;
        return carrier == Carrier.USA_TMO || carrier == Carrier.USA_XAA || carrier == Carrier.USA_XAG || carrier == Carrier.USA_TFN || carrier == Carrier.USA_TFO || carrier == Carrier.USA_TMK || carrier == Carrier.USA_DSH;
    }

    public boolean isUsaCdmaMarket() {
        boolean z = "VZW".equals(this.mSalesCode) || "SPR".equals(this.mSalesCode) || "USC".equals(this.mSalesCode) || "BST".equals(this.mSalesCode) || "VMU".equals(this.mSalesCode) || "XAS".equals(this.mSalesCode) || "LRA".equals(this.mSalesCode) || "TFNVZW".equals(this.mSalesCode) || "ACG".equals(this.mSalesCode);
        Log.d("CarrierConfigForGnss", "isUsaCdmaMarket : " + z);
        return z;
    }

    public boolean isJapanDocomo() {
        return this.mCarrier == Carrier.JPN_DCM;
    }

    public boolean isJapanKdi() {
        return this.mCarrier == Carrier.JPN_KDI;
    }

    public boolean isJapanJcom() {
        return "JCO".equals(this.mSalesCode);
    }

    public boolean isJapanUQMobile() {
        return "UQM".equals(this.mSalesCode);
    }

    public boolean isEPDNListenRequired() {
        return isJapanKdi() || isUsaVerizon();
    }

    public boolean isSupportSuplNiWithSettingOff() {
        Carrier carrier = this.mCarrier;
        return carrier == Carrier.KOR_SKT || carrier == Carrier.KOR_KTT || carrier == Carrier.KOR_LGT || carrier == Carrier.USA_ATT || carrier == Carrier.USA_TMO || carrier == Carrier.USA_XAU || carrier == Carrier.USA_XAR || carrier == Carrier.USA_XAG || carrier == Carrier.USA_XAA || carrier == Carrier.USA_AIO || carrier == Carrier.USA_TFA || carrier == Carrier.USA_TFC || carrier == Carrier.USA_TFO || carrier == Carrier.USA_TMK || carrier == Carrier.USA_DSH || carrier == Carrier.USA_DSA || carrier == Carrier.USA_SPR || carrier == Carrier.ARG_UFN || carrier == Carrier.MEX_IUS || carrier == Carrier.MEX_MNX || carrier == Carrier.MEX_UNE || carrier == Carrier.PER_PEO || carrier == Carrier.PER_PNT || carrier == Carrier.PER_PET || carrier == Carrier.JPN_RKT;
    }

    public boolean isLppeSupportMarket() {
        boolean z = "TMB".equals(this.mSalesCode) || "ATT".equals(this.mSalesCode) || "AIO".equals(this.mSalesCode) || "XAU".equals(this.mSalesCode) || "XAA".equals(this.mSalesCode) || "XAR".equals(this.mSalesCode) || "XAG".equals(this.mSalesCode) || "TMK".equals(this.mSalesCode) || "DSH".equals(this.mSalesCode) || "DSA".equals(this.mSalesCode) || "DSG".equals(this.mSalesCode) || "TFO".equals(this.mSalesCode) || "TFA".equals(this.mSalesCode) || "VZW".equals(this.mSalesCode) || "SPR".equals(this.mSalesCode) || "VMU".equals(this.mSalesCode) || "BST".equals(this.mSalesCode) || "XAS".equals(this.mSalesCode) || "GCF".equals(this.mSalesCode);
        Log.d("CarrierConfigForGnss", "isLppeSupportMarket : " + z);
        return z;
    }

    public boolean isOTDOASupportMarket() {
        boolean z = "VZW".equals(this.mSalesCode) || "USC".equals(this.mSalesCode) || "ACG".equals(this.mSalesCode) || "GCF".equals(this.mSalesCode);
        Log.d("CarrierConfigForGnss", "isOTDOASupportMarket : " + z);
        return z;
    }

    public boolean isCanadaMarket() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY);
        String str2 = SystemProperties.get("ro.csc.countryiso_code");
        Log.d("CarrierConfigForGnss", "isCaMarket : code/country_Code/countryISO_Code = " + this.mSalesCode + "/" + str + "/" + str2);
        boolean z = "BMC".equals(this.mSalesCode) || "RWC".equals(this.mSalesCode) || "TLS".equals(this.mSalesCode) || "SJR".equals(this.mSalesCode) || "XAC".equals(this.mSalesCode) || "Canada".equals(str) || "CA".equals(str2);
        Log.d("CarrierConfigForGnss", "isCaMarket : " + z);
        return z;
    }

    public boolean shouldSupportSuplUseApnLatinMarket() {
        Carrier carrier = this.mCarrier;
        return carrier == Carrier.ARG_UFN || carrier == Carrier.MEX_IUS || carrier == Carrier.MEX_MNX || carrier == Carrier.MEX_UNE;
    }

    public boolean isVendorIgnoreNfwLocPolicy() {
        return isKoreaMarket();
    }

    public boolean isKorSuplNiSupported() {
        return isKoreaSktSim() || isKoreaKttSim() || isKoreaLguSim();
    }

    public boolean isChinaSuplNiSupported() {
        return isChinaMobile() || isChinaTdOpen();
    }

    public final void setSalescodeToHashMap() {
        this.mSalesCodeToCarrierMap.put("TMB", Carrier.USA_TMO);
        this.mSalesCodeToCarrierMap.put("TMK", Carrier.USA_TMK);
        this.mSalesCodeToCarrierMap.put("TFN", Carrier.USA_TFN);
        this.mSalesCodeToCarrierMap.put("TFO", Carrier.USA_TFO);
        this.mSalesCodeToCarrierMap.put("DSH", Carrier.USA_DSH);
        this.mSalesCodeToCarrierMap.put("DSA", Carrier.USA_DSA);
        this.mSalesCodeToCarrierMap.put("DSG", Carrier.USA_DSG);
        this.mSalesCodeToCarrierMap.put("ATT", Carrier.USA_ATT);
        this.mSalesCodeToCarrierMap.put("AIO", Carrier.USA_AIO);
        this.mSalesCodeToCarrierMap.put("TFA", Carrier.USA_TFA);
        this.mSalesCodeToCarrierMap.put("TFC", Carrier.USA_TFC);
        this.mSalesCodeToCarrierMap.put("XAU", Carrier.USA_XAU);
        this.mSalesCodeToCarrierMap.put("XAA", Carrier.USA_XAA);
        this.mSalesCodeToCarrierMap.put("XAR", Carrier.USA_XAR);
        this.mSalesCodeToCarrierMap.put("XAG", Carrier.USA_XAG);
        this.mSalesCodeToCarrierMap.put("VZW", Carrier.USA_VZW);
        this.mSalesCodeToCarrierMap.put("SPR", Carrier.USA_SPR);
        this.mSalesCodeToCarrierMap.put("XAS", Carrier.USA_XAS);
        this.mSalesCodeToCarrierMap.put("BST", Carrier.USA_BST);
        this.mSalesCodeToCarrierMap.put("VMU", Carrier.USA_VMU);
        this.mSalesCodeToCarrierMap.put("USC", Carrier.USA_USC);
        this.mSalesCodeToCarrierMap.put("ACG", Carrier.USA_ACG);
        this.mSalesCodeToCarrierMap.put("DCM", Carrier.JPN_DCM);
        HashMap hashMap = this.mSalesCodeToCarrierMap;
        Carrier carrier = Carrier.JPN_KDI;
        hashMap.put("KDI", carrier);
        this.mSalesCodeToCarrierMap.put("JCO", carrier);
        this.mSalesCodeToCarrierMap.put("UQM", carrier);
        this.mSalesCodeToCarrierMap.put("RKT", Carrier.JPN_RKT);
        HashMap hashMap2 = this.mSalesCodeToCarrierMap;
        Carrier carrier2 = Carrier.CAD_TLS;
        hashMap2.put("TLS", carrier2);
        this.mSalesCodeToCarrierMap.put("KDO", carrier2);
        this.mSalesCodeToCarrierMap.put("PMB", carrier2);
        HashMap hashMap3 = this.mSalesCodeToCarrierMap;
        Carrier carrier3 = Carrier.CAD_BMC;
        hashMap3.put("BMC", carrier3);
        this.mSalesCodeToCarrierMap.put("VMC", carrier3);
        this.mSalesCodeToCarrierMap.put("PCM", carrier3);
        this.mSalesCodeToCarrierMap.put("SOL", carrier3);
        this.mSalesCodeToCarrierMap.put("BWA", carrier3);
        HashMap hashMap4 = this.mSalesCodeToCarrierMap;
        Carrier carrier4 = Carrier.CAD_RWC;
        hashMap4.put("RWC", carrier4);
        this.mSalesCodeToCarrierMap.put("FMC", carrier4);
        this.mSalesCodeToCarrierMap.put("CHR", carrier4);
        this.mSalesCodeToCarrierMap.put("TBT", carrier4);
        this.mSalesCodeToCarrierMap.put("VTR", carrier4);
        this.mSalesCodeToCarrierMap.put("FIZ", carrier4);
        this.mSalesCodeToCarrierMap.put("ESK", carrier4);
        this.mSalesCodeToCarrierMap.put("SJR", carrier4);
        this.mSalesCodeToCarrierMap.put("GLW", carrier4);
        HashMap hashMap5 = this.mSalesCodeToCarrierMap;
        Carrier carrier5 = Carrier.CAD_XAC;
        hashMap5.put("XAC", carrier5);
        this.mSalesCodeToCarrierMap.put("CAO", carrier5);
        HashMap hashMap6 = this.mSalesCodeToCarrierMap;
        Carrier carrier6 = Carrier.KOR_SKT;
        hashMap6.put("SKT", carrier6);
        this.mSalesCodeToCarrierMap.put("SKC", carrier6);
        this.mSalesCodeToCarrierMap.put("SKO", carrier6);
        HashMap hashMap7 = this.mSalesCodeToCarrierMap;
        Carrier carrier7 = Carrier.KOR_LGT;
        hashMap7.put("LGT", carrier7);
        this.mSalesCodeToCarrierMap.put("LUC", carrier7);
        this.mSalesCodeToCarrierMap.put("LUO", carrier7);
        HashMap hashMap8 = this.mSalesCodeToCarrierMap;
        Carrier carrier8 = Carrier.KOR_KTT;
        hashMap8.put("KTT", carrier8);
        this.mSalesCodeToCarrierMap.put("KTC", carrier8);
        this.mSalesCodeToCarrierMap.put("KTO", carrier8);
        this.mSalesCodeToCarrierMap.put("CHM", Carrier.CHN_CMC);
        this.mSalesCodeToCarrierMap.put("CTC", Carrier.CHN_CTC);
        this.mSalesCodeToCarrierMap.put("CHU", Carrier.CHN_CHU);
        HashMap hashMap9 = this.mSalesCodeToCarrierMap;
        Carrier carrier9 = Carrier.CHN_CHC;
        hashMap9.put("CHN", carrier9);
        this.mSalesCodeToCarrierMap.put("CHC", carrier9);
        this.mSalesCodeToCarrierMap.put("UFN", Carrier.ARG_UFN);
        this.mSalesCodeToCarrierMap.put("MNX", Carrier.MEX_MNX);
        this.mSalesCodeToCarrierMap.put("IUS", Carrier.MEX_IUS);
        this.mSalesCodeToCarrierMap.put("UNE", Carrier.MEX_UNE);
        this.mSalesCodeToCarrierMap.put("PEO", Carrier.PER_PEO);
        this.mSalesCodeToCarrierMap.put("PNT", Carrier.PER_PNT);
        this.mSalesCodeToCarrierMap.put("PET", Carrier.PER_PET);
        this.mSalesCodeToCarrierMap.put("SWC", Carrier.SUI_SWC);
        this.mSalesCodeToCarrierMap.put("TUR", Carrier.TUR_TUR);
        HashMap hashMap10 = this.mSalesCodeToCarrierMap;
        Carrier carrier10 = Carrier.SWA_IND;
        hashMap10.put("INS", carrier10);
        this.mSalesCodeToCarrierMap.put("INU", carrier10);
    }

    public Carrier getCarrier() {
        if (this.mSimOperator != null && isKoreaMarket()) {
            String str = this.mSimOperator;
            str.hashCode();
            switch (str) {
                case "45005":
                    return Carrier.KOR_SKT;
                case "45006":
                    return Carrier.KOR_LGT;
                case "45008":
                    return Carrier.KOR_KTT;
                case "45011":
                    return Carrier.KOR_SKT;
            }
        }
        return (Carrier) this.mSalesCodeToCarrierMap.getOrDefault(this.mSalesCode, Carrier.NO_OPERATOR);
    }

    public HashMap getConfigMap() {
        this.mExtraConfigHashMap.clear();
        setCarrierConfigMap();
        return this.mExtraConfigHashMap;
    }

    public final void setCarrierConfigMap() {
        Carrier carrier = getCarrier();
        if (isAttConfigRequired(carrier)) {
            setAttConfigMap();
            return;
        }
        if (isTmbConfigRequired(carrier)) {
            setTmbConfigMap();
            return;
        }
        if (isCanadaConfigRequired(carrier)) {
            setCanadaConfigMap();
            return;
        }
        if (isVzwConfigRequired(carrier)) {
            setVzwConfigMap();
            return;
        }
        if (isSprConfigRequired(carrier)) {
            setSprConfigMap();
            return;
        }
        if (isUscConfigRequired(carrier)) {
            setUscConfigMap();
            return;
        }
        if (isUfnConfigRequired(carrier)) {
            setUfnConfigMap();
            return;
        }
        if (isMexicoConfigRequired(carrier)) {
            setMexicoConfigMap();
            return;
        }
        if (isPeruConfigRequired(carrier)) {
            setPeruConfigMap();
            return;
        }
        if (isIndiaConfigRequired(carrier)) {
            setIndiaConfigMap();
            return;
        }
        if (isLgtConfigRequired(carrier)) {
            setLgtConfigMap();
            return;
        }
        if (isKttConfigRequired(carrier)) {
            setKttConfigMap();
            return;
        }
        if (isSktConfigRequired(carrier)) {
            setSktConfigMap();
            return;
        }
        if (isCmcConfigRequired(carrier)) {
            setCmcConfigMap();
            return;
        }
        if (isDcmConfigRequired(carrier)) {
            setDcmConfigMap();
        } else if (isKdiConfigRequired(carrier)) {
            setKdiConfigMap();
        } else if (isRktConfigRequired(carrier)) {
            setRktConfigMap();
        }
    }

    public final boolean isAttConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_ATT || carrier == Carrier.USA_XAR || carrier == Carrier.USA_DSA || carrier == Carrier.USA_DSG;
    }

    public final boolean isTmbConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_TMO || carrier == Carrier.USA_TFO || carrier == Carrier.USA_DSH || carrier == Carrier.USA_TMK || carrier == Carrier.USA_XAU || carrier == Carrier.USA_XAG;
    }

    public final boolean isCanadaConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_TFA || carrier == Carrier.USA_TFC || carrier == Carrier.USA_AIO || carrier == Carrier.CAD_BMC || carrier == Carrier.CAD_RWC || carrier == Carrier.CAD_TLS || carrier == Carrier.CAD_XAC;
    }

    public final boolean isVzwConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_VZW;
    }

    public final boolean isSprConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_SPR || carrier == Carrier.USA_XAS || carrier == Carrier.USA_BST || carrier == Carrier.USA_VMU;
    }

    public final boolean isUscConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_USC || carrier == Carrier.USA_ACG;
    }

    public final boolean isUfnConfigRequired(Carrier carrier) {
        return carrier == Carrier.ARG_UFN;
    }

    public final boolean isMexicoConfigRequired(Carrier carrier) {
        return carrier == Carrier.MEX_MNX || carrier == Carrier.MEX_IUS || carrier == Carrier.MEX_UNE;
    }

    public final boolean isPeruConfigRequired(Carrier carrier) {
        return carrier == Carrier.PER_PEO || carrier == Carrier.PER_PET || carrier == Carrier.PER_PNT;
    }

    public final boolean isIndiaConfigRequired(Carrier carrier) {
        return carrier == Carrier.SWA_IND;
    }

    public final boolean isLgtConfigRequired(Carrier carrier) {
        return carrier == Carrier.KOR_LGT;
    }

    public final boolean isKttConfigRequired(Carrier carrier) {
        return carrier == Carrier.KOR_KTT;
    }

    public final boolean isSktConfigRequired(Carrier carrier) {
        return carrier == Carrier.KOR_SKT;
    }

    public final boolean isCmcConfigRequired(Carrier carrier) {
        return carrier == Carrier.CHN_CMC;
    }

    public final boolean isDcmConfigRequired(Carrier carrier) {
        return carrier == Carrier.JPN_DCM;
    }

    public final boolean isKdiConfigRequired(Carrier carrier) {
        return carrier == Carrier.JPN_KDI;
    }

    public final boolean isRktConfigRequired(Carrier carrier) {
        return carrier == Carrier.JPN_RKT;
    }

    public boolean isTmbSuplServerRequired() {
        return isTmbConfigRequired(getCarrier());
    }

    public boolean isGoogleServerAgpsOnlyRequired() {
        Carrier carrier = getCarrier();
        return isAttConfigRequired(carrier) || isSprConfigRequired(carrier) || isVzwConfigRequired(carrier) || isUscConfigRequired(carrier) || isCanadaConfigRequired(carrier);
    }

    public boolean isChcSuplRequired() {
        Carrier carrier = getCarrier();
        return carrier == Carrier.CHN_CMC || carrier == Carrier.CHN_CHC;
    }

    public boolean isUneSuplRequired() {
        return isMexicoConfigRequired(getCarrier());
    }

    public void setDeviceMode(String str) {
        this.mDeviceMode = str;
    }

    public boolean isTabletDevice() {
        String str = this.mDeviceMode;
        if (str != null) {
            return str.toLowerCase().contains("tablet");
        }
        return false;
    }

    public final void setAttConfigMap() {
        this.mExtraConfigHashMap.put("CP_LPP_GUARD_TIME_SEC", "2");
        this.mExtraConfigHashMap.put("REAIDING_INTERVAL_SEC", "7200");
        this.mExtraConfigHashMap.put("AUTO_SUPL_VERSION_FOR_NI", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1_2");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_NI_ALLOW_GPS_OFF", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("VENDOR_LBS_SERVER_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("RTI_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("EE_SYNC_THRESHOLD_DAYS", "3");
        this.mExtraConfigHashMap.put("WARM_STANDBY2", "23");
        this.mExtraConfigHashMap.put("ENABLE_GALILEO", "TRUE");
    }

    public final void setTmbConfigMap() {
        this.mExtraConfigHashMap.put("CP_LPP_GUARD_TIME_SEC", "2");
        this.mExtraConfigHashMap.put("REAIDING_INTERVAL_SEC", "7200");
        this.mExtraConfigHashMap.put("AUTO_SUPL_VERSION_FOR_NI", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1_2");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("SUPL_NI_ALLOW_GPS_OFF", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("VENDOR_LBS_SERVER_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("RTI_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("EE_SYNC_THRESHOLD_DAYS", "3");
        this.mExtraConfigHashMap.put("WARM_STANDBY2", "23");
        this.mExtraConfigHashMap.put("ENABLE_GALILEO", "TRUE");
        if (isTabletDevice()) {
            return;
        }
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSB", "1");
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSA", "1");
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_AUTO", "1");
        this.mExtraConfigHashMap.put("ENABLE_4G_CP_CAPS_MSB", "1");
        this.mExtraConfigHashMap.put("ENABLE_4G_CP_CAPS_MSA", "0");
        this.mExtraConfigHashMap.put("ENABLE_4G_CP_CAPS_AUTO", "1");
        this.mExtraConfigHashMap.put("ENABLE_3G_CP_CAPS_MSB", "1");
        this.mExtraConfigHashMap.put("ENABLE_3G_CP_CAPS_MSA", "0");
        this.mExtraConfigHashMap.put("ENABLE_3G_CP_CAPS_AUTO", "1");
        this.mExtraConfigHashMap.put("ENABLE_2G_CP_CAPS_MSB", "1");
        this.mExtraConfigHashMap.put("ENABLE_2G_CP_CAPS_MSA", "1");
        this.mExtraConfigHashMap.put("ENABLE_2G_CP_CAPS_AUTO", "1");
        this.mExtraConfigHashMap.put("ENABLE_LPPE_CIVIC_ADDRESS", "0");
        this.mExtraConfigHashMap.put("ENABLE_LPP_HA_GAD_SHAPE", "TRUE");
    }

    public final void setCanadaConfigMap() {
        this.mExtraConfigHashMap.put("emergencyExtensionSeconds", "300");
        this.mExtraConfigHashMap.put("CP_LPP_GUARD_TIME_SEC", "2");
        this.mExtraConfigHashMap.put("REAIDING_INTERVAL_SEC", "7200");
        this.mExtraConfigHashMap.put("AUTO_SUPL_VERSION_FOR_NI", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("SUPL_NI_ALLOW_GPS_OFF", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("VENDOR_LBS_SERVER_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("RTI_ENABLE", "FALSE");
        this.mExtraConfigHashMap.put("EE_SYNC_THRESHOLD_DAYS", "3");
        this.mExtraConfigHashMap.put("WARM_STANDBY2", "23");
        this.mExtraConfigHashMap.put("ENABLE_GALILEO", "TRUE");
    }

    public final void setVzwConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("SUPL_UT1_SEC", "10");
        this.mExtraConfigHashMap.put("SUPL_UT2_SEC", "10");
        this.mExtraConfigHashMap.put("SUPL_UT3_SEC", "30");
        this.mExtraConfigHashMap.put("USE_RRLP_GOOGLE_SUPL", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1_2");
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_MULTI_LOCID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPPE_CAPABLE", "TRUE");
    }

    public final void setSprConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HOST_NI", "supl2019.lbs.sprint.com");
        this.mExtraConfigHashMap.put("USE_RRLP_GOOGLE_SUPL", "FALSE");
    }

    public final void setUscConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
        this.mExtraConfigHashMap.put("USE_RRLP_GOOGLE_SUPL", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1_1");
    }

    public final void setUfnConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
    }

    public final void setMexicoConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
    }

    public final void setPeruConfigMap() {
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
    }

    public final void setIndiaConfigMap() {
        this.mExtraConfigHashMap.put("ENABLE_NAVIC", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSB", "1");
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSA", "1");
        this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_AUTO", "1");
    }

    public final void setLgtConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "FALSE");
        this.mExtraConfigHashMap.put("ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
        this.mExtraConfigHashMap.put("ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
    }

    public final void setKttConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPPE_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "FALSE");
        this.mExtraConfigHashMap.put("ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
        this.mExtraConfigHashMap.put("ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
    }

    public final void setSktConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "FALSE");
        this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "TRUE");
        this.mExtraConfigHashMap.put("ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
        this.mExtraConfigHashMap.put("ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
    }

    public final void setCmcConfigMap() {
        this.mExtraConfigHashMap.put("REAIDING_INTERVAL_SEC", "600");
        this.mExtraConfigHashMap.put("SUPL_LOG_ENABLE", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LOG_PATH", "/data/AGPSLog.txt");
        this.mExtraConfigHashMap.put("SUPL_NI_GPS_ICON", "TRUE");
        this.mExtraConfigHashMap.put("NO_DATA_STANDALONE", "TRUE");
        this.mExtraConfigHashMap.put("VENDOR_LBS_XTRA_SET_WITH_EE_IGNORE", "TRUE");
    }

    public final void setDcmConfigMap() {
        this.mExtraConfigHashMap.put("SUPL_UT1_SEC", "10");
        this.mExtraConfigHashMap.put("SUPL_UT2_SEC", "10");
        this.mExtraConfigHashMap.put("SUPL_UT3_SEC", "10");
        this.mExtraConfigHashMap.put("TCP_CONNETION_TIMEOUT", "30");
        this.mExtraConfigHashMap.put("SUPL_NTT_DOCOMO", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
    }

    public final void setKdiConfigMap() {
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
        this.mExtraConfigHashMap.put("emergencyExtensionSeconds", "240");
    }

    public final void setRktConfigMap() {
        this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
    }
}
