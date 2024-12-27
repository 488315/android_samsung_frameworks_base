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

public final class SemDynamicFeature {
    private static final String FORCE_ENABLE = "persist.sys.df.system.FORCE_ENABLE";
    public static final String SERVICE_NAME = "dynamic_feature";
    private static final String TAG = "SemDynamicFeature";
    private static DynamicFeatureManager dfManager = null;
    private static boolean sSuitable = false;
    private static boolean sInit = false;
    private static ArrayList<String> ALLOWED_COUNTRY_LIST = new ArrayList<>(Arrays.asList("KR"));

    public static Feature getProperty(Context context, String namespace, String name) {
        Properties properties = getProperties(context, namespace, name);
        if (properties != null) {
            return properties.getFeature(name);
        }
        return null;
    }

    public static boolean isSuitable() {
        if (sInit) {
            return sSuitable;
        }
        sSuitable = (isBetaBinaryType() && isAllowedCSCCountry()) || isForceEnabled();
        sInit = true;
        return sSuitable;
    }

    private static boolean isBetaBinaryType() {
        String id = Build.DISPLAY;
        String binaryType =
                GnssSignalType.CODE_TYPE_Z.equals(
                                id.substring(id.length() + (-4), id.length() + (-3)).toUpperCase())
                        ? GnssSignalType.CODE_TYPE_Z
                        : GnssSignalType.CODE_TYPE_C;
        if (GnssSignalType.CODE_TYPE_Z.equals(binaryType)) {
            return true;
        }
        return false;
    }

    private static boolean isForceEnabled() {
        try {
            boolean isForceEnabled = SystemProperties.getBoolean(FORCE_ENABLE, false);
            return isForceEnabled;
        } catch (Exception ee) {
            Slog.e(TAG, "isForceEnabled : " + ee.getMessage());
            return false;
        }
    }

    private static boolean isAllowedCSCCountry() {
        boolean isAllowed = false;
        try {
            String locale = SystemProperties.get("ro.csc.country_code");
            if (locale != null && locale.length() > 0) {
                isAllowed = ALLOWED_COUNTRY_LIST.contains(locale);
            }
            if (!isAllowed) {
                Slog.e(TAG, "locale is not allowed " + locale);
            }
        } catch (Exception ee) {
            Slog.e(TAG, "isAllowedCountry : " + ee.getMessage());
        }
        return isAllowed;
    }

    public static DynamicFeatureManager getService(Context context) {
        if (dfManager != null) {
            return dfManager;
        }
        try {
            dfManager = (DynamicFeatureManager) context.getSystemService(SERVICE_NAME);
            Slog.d(TAG, "dfManager: " + dfManager);
            if (dfManager != null) {
                Slog.i(TAG, "dfManager : " + dfManager);
            }
        } catch (Exception ee) {
            Slog.d(TAG, "   " + ee.getMessage());
        }
        return dfManager;
    }

    public static Properties getProperties(Context context, String namespace, String... names) {
        dfManager = getService(context);
        if (dfManager == null) {
            Slog.d(TAG, "Failed to get dfManager");
            return null;
        }
        try {
            Properties properties = dfManager.getProperties(namespace, names);
            return properties;
        } catch (Exception e) {
            Slog.e(TAG, "Remote Exception");
            return null;
        }
    }

    public static final class Properties implements Parcelable {
        public static final Parcelable.Creator<Properties> CREATOR =
                new Parcelable.Creator<
                        Properties>() { // from class:
                                        // com.samsung.android.provider.SemDynamicFeature.Properties.1
                    @Override // android.os.Parcelable.Creator
                    public Properties createFromParcel(Parcel source) {
                        return new Properties(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public Properties[] newArray(int size) {
                        return new Properties[size];
                    }
                };
        public static final String PROPERTY_CARGO = "PROPERTY_CARGO";
        private ArrayList<Feature> features;
        private String mNamespace;

        public Properties(String namespace, ArrayList<Feature> list) {
            Objects.requireNonNull(namespace);
            this.mNamespace = namespace;
            this.features = new ArrayList<>();
            if (list != null) {
                this.features.addAll(list);
            }
        }

        protected Properties(Parcel in) {
            readFromParcel(in);
        }

        public Feature getFeature(String name) {
            Iterator<Feature> it = this.features.iterator();
            while (it.hasNext()) {
                Feature feature = it.next();
                if (name.equals(feature.getName())) {
                    return feature;
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

        public String getString(String name, String defaultValue) {
            Objects.requireNonNull(name);
            Iterator<Feature> it = this.features.iterator();
            while (it.hasNext()) {
                Feature feature = it.next();
                if (name.equals(feature.getName())) {
                    return feature.getString();
                }
            }
            return defaultValue;
        }

        private ArrayList<Feature> getList() {
            return this.features;
        }

        public boolean getBoolean(String name, boolean defaultValue) {
            Objects.requireNonNull(name);
            Feature feature = getFeature(name);
            if (feature == null) {
                return defaultValue;
            }
            try {
                return feature.getBoolean();
            } catch (Exception e) {
                return defaultValue;
            }
        }

        public int getInt(String name, int defaultValue) {
            Objects.requireNonNull(name);
            Feature feature = getFeature(name);
            if (feature == null) {
                return defaultValue;
            }
            try {
                return feature.getInt();
            } catch (NumberFormatException e) {
                Slog.e(SemDynamicFeature.TAG, "Parsing int failed for " + name);
                return defaultValue;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mNamespace);
            dest.writeList(this.features);
        }

        public void readFromParcel(Parcel source) {
            this.mNamespace = source.readString();
            this.features = source.readArrayList(Feature.class.getClassLoader(), Feature.class);
        }

        public boolean isAbTest(String name) {
            Objects.requireNonNull(name);
            Feature feature = getFeature(name);
            if (feature == null) {
                return false;
            }
            return feature.isAbTest();
        }

        public boolean contains(String name) {
            Objects.requireNonNull(name);
            Feature feature = getFeature(name);
            return feature != null;
        }

        public boolean sendAbTestResult(Context context, String name, String message) {
            if (!isAbTest(name)) {
                return false;
            }
            SemDynamicFeature.dfManager = SemDynamicFeature.getService(context);
            if (SemDynamicFeature.dfManager == null) {
                Slog.e(SemDynamicFeature.TAG, "DynamicFeatureService is not started");
                return false;
            }
            return SemDynamicFeature.dfManager.sendAbTestResult(this.mNamespace, name, message);
        }
    }
}
