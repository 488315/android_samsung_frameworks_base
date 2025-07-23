package com.samsung.android.provider;

import android.content.Context;
import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SemDynamicFeature {
    public static final String ACTIVE_KEY_NAME = "persist.sys.df.enabled";
    private static ArrayList<String> ALLOWED_COUNTRY_LIST = new ArrayList<>(Arrays.asList("KR", "KOREA"));
    private static final String FORCE_ENABLE = "persist.sys.df.system.FORCE_ENABLE";
    public static final String SERVICE_NAME = "dynamic_feature";
    private static final String TAG = "SemDynamicFeature";
    private static DynamicFeatureManager dfManager = null;
    private static boolean sInit = false;
    private static boolean sSuitable = false;

    public static Feature getProperty(Context context, String str, String str2) {
        Properties properties = getProperties(context, str, str2);
        if (properties != null) {
            return properties.getFeature(str2);
        }
        return null;
    }

    public static void markDisabled() {
        SystemProperties.set(ACTIVE_KEY_NAME, "false");
    }

    public static void markEnabled() {
        SystemProperties.set(ACTIVE_KEY_NAME, "true");
    }

    public static boolean isSuitable() {
        if (sInit) {
            return sSuitable;
        }
        return false;
    }

    private static boolean isBetaBinaryType() {
        String trim = Build.DISPLAY.replace("test-keys", "").trim();
        return GnssSignalType.CODE_TYPE_Z.equals(GnssSignalType.CODE_TYPE_Z.equals(trim.substring(trim.length() + (-4), trim.length() + (-3))) ? GnssSignalType.CODE_TYPE_Z : GnssSignalType.CODE_TYPE_C);
    }

    public static boolean isTestBinary() {
        String str = "";
        try {
            str = SystemProperties.get("ro.build.type", "UNKNOWN");
            r2 = "eng".equals(str) || "userdebug".equals(str);
            StringBuilder sb = new StringBuilder();
            sb.append(r2 ? "This is a test binary " : "This is not a test binary ");
            sb.append(str);
            Slog.d(TAG, sb.toString());
            return r2;
        } catch (Exception unused) {
            Slog.e(TAG, "Fail to read binary type " + str);
            return r2;
        }
    }

    public static boolean isForceEnabled() {
        try {
            return SystemProperties.getBoolean(FORCE_ENABLE, false);
        } catch (Exception e) {
            Slog.e(TAG, "isForceEnabled : " + e.getMessage());
            return false;
        }
    }

    private static boolean isAllowedCSCCountry() {
        boolean z = false;
        try {
            String str = SystemProperties.get("ro.csc.country_code");
            if (str != null && !str.isEmpty()) {
                z = ALLOWED_COUNTRY_LIST.contains(str);
            }
            if (!z) {
                Slog.e(TAG, "locale is not allowed " + str);
            }
            return z;
        } catch (Exception e) {
            Slog.e(TAG, "isAllowedCountry : " + e.getMessage());
            return z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DynamicFeatureManager getService(Context context) {
        DynamicFeatureManager dynamicFeatureManager = dfManager;
        if (dynamicFeatureManager != null) {
            return dynamicFeatureManager;
        }
        try {
            dfManager = (DynamicFeatureManager) context.getSystemService(SERVICE_NAME);
            Slog.d(TAG, "dfManager: " + dfManager);
            if (dfManager != null) {
                Slog.i(TAG, "dfManager : " + dfManager);
            }
        } catch (Exception e) {
            Slog.d(TAG, "   " + e.getMessage());
        }
        return dfManager;
    }

    public static String getVid(Context context) {
        DynamicFeatureManager service = getService(context);
        dfManager = service;
        if (service == null) {
            Slog.d(TAG, "Failed to get dfManager");
            return null;
        }
        return service.getVid();
    }

    public static int setEndpoint(Context context, int i) {
        DynamicFeatureManager service = getService(context);
        dfManager = service;
        if (service == null) {
            Slog.d(TAG, "Failed to get dfManager");
            return -1;
        }
        return service.setEndpoint(i);
    }

    public static Properties getProperties(Context context, String str, String... strArr) {
        DynamicFeatureManager service = getService(context);
        dfManager = service;
        if (service == null) {
            Slog.d(TAG, "Failed to get dfManager");
            return null;
        }
        try {
            return service.getProperties(str, strArr);
        } catch (Exception unused) {
            Slog.e(TAG, "Remote Exception");
            return null;
        }
    }

    public static final class Properties implements Parcelable {
        public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() { // from class: com.samsung.android.provider.SemDynamicFeature.Properties.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Properties createFromParcel(Parcel parcel) {
                return new Properties(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Properties[] newArray(int i) {
                return new Properties[i];
            }
        };
        public static final String PROPERTY_CARGO = "PROPERTY_CARGO";
        private ArrayList<Feature> features;
        private String mNamespace;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Properties(String str, ArrayList<Feature> arrayList) {
            Objects.requireNonNull(str);
            this.mNamespace = str;
            ArrayList<Feature> arrayList2 = new ArrayList<>();
            this.features = arrayList2;
            if (arrayList != null) {
                arrayList2.addAll(arrayList);
            }
        }

        protected Properties(Parcel parcel) {
            readFromParcel(parcel);
        }

        public Feature getFeature(String str) {
            Iterator<Feature> it = this.features.iterator();
            while (it.hasNext()) {
                Feature next = it.next();
                if (str.equals(next.getName())) {
                    return next;
                }
            }
            return null;
        }

        public String getNamespace() {
            return this.mNamespace;
        }

        public ArrayList<Feature> getFeatures() {
            return this.features;
        }

        public String getString(String str, String str2) {
            Objects.requireNonNull(str);
            Iterator<Feature> it = this.features.iterator();
            while (it.hasNext()) {
                Feature next = it.next();
                if (str.equals(next.getName())) {
                    return next.getString();
                }
            }
            return str2;
        }

        private ArrayList<Feature> getList() {
            return this.features;
        }

        public boolean getBoolean(String str, boolean z) {
            Objects.requireNonNull(str);
            Feature feature = getFeature(str);
            if (feature != null) {
                try {
                    return feature.getBoolean();
                } catch (Exception unused) {
                }
            }
            return z;
        }

        public int getInt(String str, int i) {
            Objects.requireNonNull(str);
            Feature feature = getFeature(str);
            if (feature == null) {
                return i;
            }
            try {
                return feature.getInt();
            } catch (NumberFormatException unused) {
                Slog.e(SemDynamicFeature.TAG, "Parsing int failed for " + str);
                return i;
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mNamespace);
            parcel.writeList(this.features);
        }

        public void readFromParcel(Parcel parcel) {
            this.mNamespace = parcel.readString();
            this.features = parcel.readArrayList(Feature.class.getClassLoader(), Feature.class);
        }

        public boolean isAbTest(String str) {
            Objects.requireNonNull(str);
            Feature feature = getFeature(str);
            if (feature == null) {
                return false;
            }
            return feature.isAbTest();
        }

        public boolean contains(String str) {
            Objects.requireNonNull(str);
            return getFeature(str) != null;
        }

        public boolean sendAbTestResult(Context context, String str, String str2) {
            if (!isAbTest(str)) {
                return false;
            }
            SemDynamicFeature.dfManager = SemDynamicFeature.getService(context);
            if (SemDynamicFeature.dfManager == null) {
                Slog.e(SemDynamicFeature.TAG, "DynamicFeatureService is not started");
                return false;
            }
            return SemDynamicFeature.dfManager.sendAbTestResult(this.mNamespace, str, str2);
        }
    }
}
