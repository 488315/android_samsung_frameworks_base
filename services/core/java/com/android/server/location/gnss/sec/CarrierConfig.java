package com.android.server.location.gnss.sec;

import android.os.SystemProperties;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CarrierConfig {
    public static CarrierConfig mInstance;
    public Carrier mCarrier;
    public String mDeviceMode;
    public final HashMap mExtraConfigHashMap;
    public String mSalesCode;
    public final HashMap mSalesCodeToCarrierMap;
    public String mSimOperator;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Carrier {
        public static final /* synthetic */ Carrier[] $VALUES;
        public static final Carrier ARG_UFN;
        public static final Carrier CAD_BMC;
        public static final Carrier CAD_RWC;
        public static final Carrier CAD_TLS;
        public static final Carrier CAD_XAC;
        public static final Carrier CHN_CHC;
        public static final Carrier CHN_CHN;
        public static final Carrier CHN_CHU;
        public static final Carrier CHN_CMC;
        public static final Carrier CHN_CTC;
        public static final Carrier JPN_DCM;
        public static final Carrier JPN_KDI;
        public static final Carrier JPN_RKT;
        public static final Carrier JPN_SBM;
        public static final Carrier KOR_KTT;
        public static final Carrier KOR_LGT;
        public static final Carrier KOR_SKT;
        public static final Carrier MEX_IUS;
        public static final Carrier MEX_MNX;
        public static final Carrier MEX_UNE;
        public static final Carrier NO_OPERATOR;
        public static final Carrier PER_PEO;
        public static final Carrier PER_PET;
        public static final Carrier PER_PNT;
        public static final Carrier SUI_SWC;
        public static final Carrier SWA_IND;
        public static final Carrier TUR_TUR;
        public static final Carrier USA_ACG;
        public static final Carrier USA_AIO;
        public static final Carrier USA_ATT;
        public static final Carrier USA_BST;
        public static final Carrier USA_DSA;
        public static final Carrier USA_DSG;
        public static final Carrier USA_DSH;
        public static final Carrier USA_SPR;
        public static final Carrier USA_TFA;
        public static final Carrier USA_TFC;
        public static final Carrier USA_TFN;
        public static final Carrier USA_TFO;
        public static final Carrier USA_TMK;
        public static final Carrier USA_TMO;
        public static final Carrier USA_USC;
        public static final Carrier USA_VMU;
        public static final Carrier USA_VZW;
        public static final Carrier USA_XAA;
        public static final Carrier USA_XAG;
        public static final Carrier USA_XAR;
        public static final Carrier USA_XAS;
        public static final Carrier USA_XAU;

        static {
            Carrier carrier = new Carrier("USA_TMO", 0);
            USA_TMO = carrier;
            Carrier carrier2 = new Carrier("USA_ATT", 1);
            USA_ATT = carrier2;
            Carrier carrier3 = new Carrier("USA_AIO", 2);
            USA_AIO = carrier3;
            Carrier carrier4 = new Carrier("USA_TMK", 3);
            USA_TMK = carrier4;
            Carrier carrier5 = new Carrier("USA_TFN", 4);
            USA_TFN = carrier5;
            Carrier carrier6 = new Carrier("USA_TFO", 5);
            USA_TFO = carrier6;
            Carrier carrier7 = new Carrier("USA_TFA", 6);
            USA_TFA = carrier7;
            Carrier carrier8 = new Carrier("USA_TFC", 7);
            USA_TFC = carrier8;
            Carrier carrier9 = new Carrier("USA_XAU", 8);
            USA_XAU = carrier9;
            Carrier carrier10 = new Carrier("USA_XAA", 9);
            USA_XAA = carrier10;
            Carrier carrier11 = new Carrier("USA_XAR", 10);
            USA_XAR = carrier11;
            Carrier carrier12 = new Carrier("USA_XAG", 11);
            USA_XAG = carrier12;
            Carrier carrier13 = new Carrier("USA_DSH", 12);
            USA_DSH = carrier13;
            Carrier carrier14 = new Carrier("USA_DSA", 13);
            USA_DSA = carrier14;
            Carrier carrier15 = new Carrier("USA_VZW", 14);
            USA_VZW = carrier15;
            Carrier carrier16 = new Carrier("USA_SPR", 15);
            USA_SPR = carrier16;
            Carrier carrier17 = new Carrier("USA_XAS", 16);
            USA_XAS = carrier17;
            Carrier carrier18 = new Carrier("USA_BST", 17);
            USA_BST = carrier18;
            Carrier carrier19 = new Carrier("USA_VMU", 18);
            USA_VMU = carrier19;
            Carrier carrier20 = new Carrier("USA_USC", 19);
            USA_USC = carrier20;
            Carrier carrier21 = new Carrier("USA_ACG", 20);
            USA_ACG = carrier21;
            Carrier carrier22 = new Carrier("USA_DSG", 21);
            USA_DSG = carrier22;
            Carrier carrier23 = new Carrier("JPN_DCM", 22);
            JPN_DCM = carrier23;
            Carrier carrier24 = new Carrier("JPN_KDI", 23);
            JPN_KDI = carrier24;
            Carrier carrier25 = new Carrier("JPN_RKT", 24);
            JPN_RKT = carrier25;
            Carrier carrier26 = new Carrier("JPN_SBM", 25);
            JPN_SBM = carrier26;
            Carrier carrier27 = new Carrier("CAD_TLS", 26);
            CAD_TLS = carrier27;
            Carrier carrier28 = new Carrier("CAD_BMC", 27);
            CAD_BMC = carrier28;
            Carrier carrier29 = new Carrier("CAD_RWC", 28);
            CAD_RWC = carrier29;
            Carrier carrier30 = new Carrier("CAD_XAC", 29);
            CAD_XAC = carrier30;
            Carrier carrier31 = new Carrier("KOR_SKT", 30);
            KOR_SKT = carrier31;
            Carrier carrier32 = new Carrier("KOR_KTT", 31);
            KOR_KTT = carrier32;
            Carrier carrier33 = new Carrier("KOR_LGT", 32);
            KOR_LGT = carrier33;
            Carrier carrier34 = new Carrier("CHN_CMC", 33);
            CHN_CMC = carrier34;
            Carrier carrier35 = new Carrier("CHN_CTC", 34);
            CHN_CTC = carrier35;
            Carrier carrier36 = new Carrier("CHN_CHU", 35);
            CHN_CHU = carrier36;
            Carrier carrier37 = new Carrier("CHN_CHN", 36);
            CHN_CHN = carrier37;
            Carrier carrier38 = new Carrier("CHN_CHC", 37);
            CHN_CHC = carrier38;
            Carrier carrier39 = new Carrier("ARG_UFN", 38);
            ARG_UFN = carrier39;
            Carrier carrier40 = new Carrier("MEX_MNX", 39);
            MEX_MNX = carrier40;
            Carrier carrier41 = new Carrier("MEX_IUS", 40);
            MEX_IUS = carrier41;
            Carrier carrier42 = new Carrier("MEX_UNE", 41);
            MEX_UNE = carrier42;
            Carrier carrier43 = new Carrier("PER_PEO", 42);
            PER_PEO = carrier43;
            Carrier carrier44 = new Carrier("PER_PNT", 43);
            PER_PNT = carrier44;
            Carrier carrier45 = new Carrier("PER_PET", 44);
            PER_PET = carrier45;
            Carrier carrier46 = new Carrier("SUI_SWC", 45);
            SUI_SWC = carrier46;
            Carrier carrier47 = new Carrier("TUR_TUR", 46);
            TUR_TUR = carrier47;
            Carrier carrier48 = new Carrier("SWA_IND", 47);
            SWA_IND = carrier48;
            Carrier carrier49 = new Carrier("NO_OPERATOR", 48);
            NO_OPERATOR = carrier49;
            $VALUES =
                    new Carrier[] {
                        carrier, carrier2, carrier3, carrier4, carrier5, carrier6, carrier7,
                        carrier8, carrier9, carrier10, carrier11, carrier12, carrier13, carrier14,
                        carrier15, carrier16, carrier17, carrier18, carrier19, carrier20, carrier21,
                        carrier22, carrier23, carrier24, carrier25, carrier26, carrier27, carrier28,
                        carrier29, carrier30, carrier31, carrier32, carrier33, carrier34, carrier35,
                        carrier36, carrier37, carrier38, carrier39, carrier40, carrier41, carrier42,
                        carrier43, carrier44, carrier45, carrier46, carrier47, carrier48, carrier49
                    };
        }

        public static Carrier valueOf(String str) {
            return (Carrier) Enum.valueOf(Carrier.class, str);
        }

        public static Carrier[] values() {
            return (Carrier[]) $VALUES.clone();
        }
    }

    public CarrierConfig() {
        HashMap hashMap = new HashMap();
        this.mSalesCodeToCarrierMap = hashMap;
        this.mExtraConfigHashMap = new HashMap();
        hashMap.put("TMB", Carrier.USA_TMO);
        hashMap.put("TMK", Carrier.USA_TMK);
        hashMap.put("TFN", Carrier.USA_TFN);
        hashMap.put("TFO", Carrier.USA_TFO);
        hashMap.put("DSH", Carrier.USA_DSH);
        hashMap.put("DSA", Carrier.USA_DSA);
        hashMap.put("DSG", Carrier.USA_DSG);
        hashMap.put("ATT", Carrier.USA_ATT);
        hashMap.put("AIO", Carrier.USA_AIO);
        hashMap.put("TFA", Carrier.USA_TFA);
        hashMap.put("TFC", Carrier.USA_TFC);
        hashMap.put("XAU", Carrier.USA_XAU);
        hashMap.put("XAA", Carrier.USA_XAA);
        hashMap.put("XAR", Carrier.USA_XAR);
        hashMap.put("XAG", Carrier.USA_XAG);
        hashMap.put("VZW", Carrier.USA_VZW);
        hashMap.put("SPR", Carrier.USA_SPR);
        hashMap.put("XAS", Carrier.USA_XAS);
        hashMap.put("BST", Carrier.USA_BST);
        hashMap.put("VMU", Carrier.USA_VMU);
        hashMap.put("USC", Carrier.USA_USC);
        hashMap.put("ACG", Carrier.USA_ACG);
        hashMap.put("DCM", Carrier.JPN_DCM);
        Carrier carrier = Carrier.JPN_KDI;
        hashMap.put("KDI", carrier);
        hashMap.put("JCO", carrier);
        hashMap.put("UQM", carrier);
        hashMap.put("RKT", Carrier.JPN_RKT);
        hashMap.put("SBM", Carrier.JPN_SBM);
        Carrier carrier2 = Carrier.CAD_TLS;
        hashMap.put("TLS", carrier2);
        hashMap.put("KDO", carrier2);
        hashMap.put("PMB", carrier2);
        Carrier carrier3 = Carrier.CAD_BMC;
        hashMap.put("BMC", carrier3);
        hashMap.put("VMC", carrier3);
        hashMap.put("PCM", carrier3);
        hashMap.put("SOL", carrier3);
        hashMap.put("BWA", carrier3);
        Carrier carrier4 = Carrier.CAD_RWC;
        hashMap.put("RWC", carrier4);
        hashMap.put("FMC", carrier4);
        hashMap.put("CHR", carrier4);
        hashMap.put("TBT", carrier4);
        hashMap.put("VTR", carrier4);
        hashMap.put("FIZ", carrier4);
        hashMap.put("ESK", carrier4);
        hashMap.put("SJR", carrier4);
        hashMap.put("GLW", carrier4);
        Carrier carrier5 = Carrier.CAD_XAC;
        hashMap.put("XAC", carrier5);
        hashMap.put("CAO", carrier5);
        Carrier carrier6 = Carrier.KOR_SKT;
        hashMap.put("SKT", carrier6);
        hashMap.put("SKC", carrier6);
        hashMap.put("SKO", carrier6);
        Carrier carrier7 = Carrier.KOR_LGT;
        hashMap.put("LGT", carrier7);
        hashMap.put("LUC", carrier7);
        hashMap.put("LUO", carrier7);
        Carrier carrier8 = Carrier.KOR_KTT;
        hashMap.put("KTT", carrier8);
        hashMap.put("KTC", carrier8);
        hashMap.put("KTO", carrier8);
        hashMap.put("CHM", Carrier.CHN_CMC);
        hashMap.put("CTC", Carrier.CHN_CTC);
        hashMap.put("CHU", Carrier.CHN_CHU);
        Carrier carrier9 = Carrier.CHN_CHC;
        hashMap.put("CHN", carrier9);
        hashMap.put("CHC", carrier9);
        hashMap.put("UFN", Carrier.ARG_UFN);
        hashMap.put("MNX", Carrier.MEX_MNX);
        hashMap.put("IUS", Carrier.MEX_IUS);
        hashMap.put("UNE", Carrier.MEX_UNE);
        hashMap.put("PEO", Carrier.PER_PEO);
        hashMap.put("PNT", Carrier.PER_PNT);
        hashMap.put("PET", Carrier.PER_PET);
        hashMap.put("SWC", Carrier.SUI_SWC);
        hashMap.put("TUR", Carrier.TUR_TUR);
        Carrier carrier10 = Carrier.SWA_IND;
        hashMap.put("INS", carrier10);
        hashMap.put("INU", carrier10);
    }

    public static synchronized CarrierConfig getInstance() {
        CarrierConfig carrierConfig;
        synchronized (CarrierConfig.class) {
            try {
                if (mInstance == null) {
                    mInstance = new CarrierConfig();
                }
                carrierConfig = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return carrierConfig;
    }

    public static boolean isCanadaConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_TFA
                || carrier == Carrier.USA_TFC
                || carrier == Carrier.USA_AIO
                || carrier == Carrier.CAD_BMC
                || carrier == Carrier.CAD_RWC
                || carrier == Carrier.CAD_TLS
                || carrier == Carrier.CAD_XAC;
    }

    public static boolean isTmbConfigRequired(Carrier carrier) {
        return carrier == Carrier.USA_TMO
                || carrier == Carrier.USA_TFO
                || carrier == Carrier.USA_DSH
                || carrier == Carrier.USA_TMK
                || carrier == Carrier.USA_XAU
                || carrier == Carrier.USA_XAG;
    }

    public final Carrier getCarrier() {
        Carrier carrier;
        if (this.mSimOperator != null && isKoreaMarket()) {
            String str = this.mSimOperator;
            str.getClass();
            carrier = Carrier.KOR_SKT;
            switch (str) {
                case "45005":
                    return carrier;
                case "45006":
                    return Carrier.KOR_LGT;
                case "45008":
                    return Carrier.KOR_KTT;
                case "45011":
                    return carrier;
            }
        }
        return (Carrier)
                this.mSalesCodeToCarrierMap.getOrDefault(this.mSalesCode, Carrier.NO_OPERATOR);
    }

    public final HashMap getConfigMap() {
        Object obj;
        boolean z;
        this.mExtraConfigHashMap.clear();
        Carrier carrier = getCarrier();
        if (carrier == Carrier.USA_ATT
                || carrier == Carrier.USA_XAR
                || carrier == Carrier.USA_DSA
                || carrier == Carrier.USA_DSG) {
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
        } else if (isTmbConfigRequired(carrier)) {
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
            String str = this.mDeviceMode;
            if (!(str != null ? str.toLowerCase().contains("tablet") : false)) {
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
        } else if (isCanadaConfigRequired(carrier)) {
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
        } else {
            if (carrier == Carrier.USA_VZW) {
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
            } else {
                if (carrier == Carrier.USA_SPR
                        || carrier == Carrier.USA_XAS
                        || carrier == Carrier.USA_BST
                        || carrier == Carrier.USA_VMU) {
                    obj = "SUPL_UT2_SEC";
                    z = true;
                } else {
                    obj = "SUPL_UT2_SEC";
                    z = false;
                }
                if (z) {
                    this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                    this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1");
                    this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
                    this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                    this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                    this.mExtraConfigHashMap.put("SUPL_HOST_NI", "supl2019.lbs.sprint.com");
                    this.mExtraConfigHashMap.put("USE_RRLP_GOOGLE_SUPL", "FALSE");
                } else {
                    if (carrier == Carrier.USA_USC || carrier == Carrier.USA_ACG) {
                        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                        this.mExtraConfigHashMap.put("SUPL_HMAC_HASH", "SHA256");
                        this.mExtraConfigHashMap.put("USE_RRLP_GOOGLE_SUPL", "FALSE");
                        this.mExtraConfigHashMap.put("SUPL_SSL_METHOD", "TLSv1_1");
                    } else if (carrier == Carrier.ARG_UFN) {
                        this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                        this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                    } else {
                        if (carrier == Carrier.MEX_MNX
                                || carrier == Carrier.MEX_IUS
                                || carrier == Carrier.MEX_UNE) {
                            this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                        } else if (carrier == Carrier.PER_PEO
                                || carrier == Carrier.PER_PET
                                || carrier == Carrier.PER_PNT) {
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                        } else if (carrier == Carrier.SWA_IND) {
                            this.mExtraConfigHashMap.put("ENABLE_NAVIC", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSB", "1");
                            this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_MSA", "1");
                            this.mExtraConfigHashMap.put("ENABLE_5G_CP_CAPS_AUTO", "1");
                        } else if (carrier == Carrier.KOR_LGT) {
                            this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
                            this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "FALSE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                        } else if (carrier == Carrier.KOR_KTT) {
                            this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
                            this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPPE_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "FALSE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                        } else if (carrier == Carrier.KOR_SKT) {
                            this.mExtraConfigHashMap.put("SUPL_OTDOA_CAPABLE", "FALSE");
                            this.mExtraConfigHashMap.put("SUPL_ECID_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "FALSE");
                            this.mExtraConfigHashMap.put("SUPL_VER_SKT_NI", "TRUE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_SUPL_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                            this.mExtraConfigHashMap.put(
                                    "ALLOW_CP_IGNORE_NFW_LOCATION_POLICY", "TRUE");
                        } else if (carrier == Carrier.CHN_CMC) {
                            this.mExtraConfigHashMap.put("REAIDING_INTERVAL_SEC", "600");
                            this.mExtraConfigHashMap.put("SUPL_LOG_ENABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LOG_PATH", "/data/AGPSLog.txt");
                            this.mExtraConfigHashMap.put("SUPL_NI_GPS_ICON", "TRUE");
                            this.mExtraConfigHashMap.put("NO_DATA_STANDALONE", "TRUE");
                            this.mExtraConfigHashMap.put(
                                    "VENDOR_LBS_XTRA_SET_WITH_EE_IGNORE", "TRUE");
                        } else if (carrier == Carrier.JPN_DCM) {
                            this.mExtraConfigHashMap.put("SUPL_UT1_SEC", "10");
                            this.mExtraConfigHashMap.put(obj, "10");
                            this.mExtraConfigHashMap.put("SUPL_UT3_SEC", "10");
                            this.mExtraConfigHashMap.put("TCP_CONNETION_TIMEOUT", "30");
                            this.mExtraConfigHashMap.put("SUPL_NTT_DOCOMO", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_OPERATOR_DCM", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_IS_AGGRESSIVE", "FALSE");
                        } else if (carrier == Carrier.JPN_KDI) {
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("emergencyExtensionSeconds", "240");
                            this.mExtraConfigHashMap.put("SUPL_OPERATOR_KDDI", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_SETCAPS_MSA_NI", "FALSE");
                            this.mExtraConfigHashMap.put("SUPL_SETCAPS_MSA_SI", "FALSE");
                        } else if (carrier == Carrier.JPN_RKT) {
                            this.mExtraConfigHashMap.put("USE_NI_SLP_ADDRESS", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_USE_APN", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_LPP_CAPABLE", "TRUE");
                            this.mExtraConfigHashMap.put("SUPL_OPERATOR_RAKUTEN", "TRUE");
                        } else if (carrier == Carrier.JPN_SBM) {
                            this.mExtraConfigHashMap.put("emergencyExtensionSeconds", "300");
                        }
                    }
                }
            }
        }
        return this.mExtraConfigHashMap;
    }

    public final boolean isCanadaMarket() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY);
        String str2 = SystemProperties.get("ro.csc.countryiso_code");
        StringBuilder sb = new StringBuilder("isCaMarket : code/country_Code/countryISO_Code = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, this.mSalesCode, "/", str, "/");
        VpnManagerService$$ExternalSyntheticOutline0.m(sb, str2, "CarrierConfigForGnss");
        boolean z =
                "BMC".equals(this.mSalesCode)
                        || "RWC".equals(this.mSalesCode)
                        || "TLS".equals(this.mSalesCode)
                        || "SJR".equals(this.mSalesCode)
                        || "XAC".equals(this.mSalesCode)
                        || "Canada".equals(str)
                        || "CA".equals(str2);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isCaMarket : ", "CarrierConfigForGnss", z);
        return z;
    }

    public final boolean isChinaCarrier() {
        Carrier carrier = this.mCarrier;
        return carrier == Carrier.CHN_CHN
                || carrier == Carrier.CHN_CHU
                || carrier == Carrier.CHN_CHC
                || carrier == Carrier.CHN_CMC
                || carrier == Carrier.CHN_CTC;
    }

    public final boolean isKoreaMarket() {
        boolean z =
                "SKT".equals(this.mSalesCode)
                        || "SKC".equals(this.mSalesCode)
                        || "SKO".equals(this.mSalesCode)
                        || "LGT".equals(this.mSalesCode)
                        || "LUC".equals(this.mSalesCode)
                        || "LUO".equals(this.mSalesCode)
                        || "KTT".equals(this.mSalesCode)
                        || "KTC".equals(this.mSalesCode)
                        || "KTO".equals(this.mSalesCode)
                        || "KOO".equals(this.mSalesCode);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isKORMarket : ", "CarrierConfigForGnss", z);
        return z;
    }

    public final boolean isKoreaSktSim() {
        return "45005".equals(this.mSimOperator) || "45011".equals(this.mSimOperator);
    }

    public final boolean isUSAMarket() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY);
        String str2 = SystemProperties.get("ro.csc.countryiso_code");
        StringBuilder sb = new StringBuilder("isUSAMarket : code/country_Code/countryISO_Code = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, this.mSalesCode, "/", str, "/");
        VpnManagerService$$ExternalSyntheticOutline0.m(sb, str2, "CarrierConfigForGnss");
        boolean z =
                "TMB".equals(this.mSalesCode)
                        || "ATT".equals(this.mSalesCode)
                        || "TFA".equals(this.mSalesCode)
                        || "TFO".equals(this.mSalesCode)
                        || "TFN".equals(this.mSalesCode)
                        || "TFC".equals(this.mSalesCode)
                        || "AIO".equals(this.mSalesCode)
                        || "TMK".equals(this.mSalesCode)
                        || "DSH".equals(this.mSalesCode)
                        || "DSA".equals(this.mSalesCode)
                        || "DSG".equals(this.mSalesCode)
                        || "XAU".equals(this.mSalesCode)
                        || "XAR".equals(this.mSalesCode)
                        || "XAG".equals(this.mSalesCode)
                        || "XAA".equals(this.mSalesCode)
                        || "VZW".equals(this.mSalesCode)
                        || "SPR".equals(this.mSalesCode)
                        || "USC".equals(this.mSalesCode)
                        || "ACG".equals(this.mSalesCode)
                        || "BST".equals(this.mSalesCode)
                        || "VMU".equals(this.mSalesCode)
                        || "XAS".equals(this.mSalesCode)
                        || "USA".equals(str)
                        || "US".equals(str2);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isUSAMarket : ", "CarrierConfigForGnss", z);
        return z;
    }

    public final boolean isUsaVerizon() {
        return "VZW".equals(this.mSalesCode);
    }
}
