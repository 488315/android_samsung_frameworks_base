package com.android.server.smartclip;

import android.text.TextUtils;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.ArrayList;

public final class SpenGarageSpecManager {
    public static SpenGarageSpecManager sInstance;
    public final boolean mIsBundledSpenSupported;
    public final ArrayList mSupportedExternalSpenFeatures = new ArrayList();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class SupportedExternalSpenFeature {
        public static final /* synthetic */ SupportedExternalSpenFeature[] $VALUES;
        public static final SupportedExternalSpenFeature REMOTE;

        static {
            SupportedExternalSpenFeature supportedExternalSpenFeature =
                    new SupportedExternalSpenFeature("REMOTE", 0);
            REMOTE = supportedExternalSpenFeature;
            $VALUES = new SupportedExternalSpenFeature[] {supportedExternalSpenFeature};
        }

        public static SupportedExternalSpenFeature valueOf(String str) {
            return (SupportedExternalSpenFeature)
                    Enum.valueOf(SupportedExternalSpenFeature.class, str);
        }

        public static SupportedExternalSpenFeature[] values() {
            return (SupportedExternalSpenFeature[]) $VALUES.clone();
        }
    }

    public SpenGarageSpecManager() {
        this.mIsBundledSpenSupported = false;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_GARAGE_SPEC");
        if (TextUtils.isEmpty(string)) {
            Log.i("SpenGarageSpecManager", "Spen Garage Spec is empty");
            return;
        }
        this.mIsBundledSpenSupported = true;
        String replaceAll = string.toLowerCase().replaceAll(" ", "");
        for (String str : replaceAll.split(",")) {
            String[] split = str.split("=");
            if (split.length != 2) {
                Log.e("SpenGarageSpecManager", "Incorrect Spec, strSpec : ".concat(replaceAll));
            } else {
                String str2 = split[0];
                String str3 = split[1];
                str2.getClass();
                if (str2.equals("unbundled_spec")) {
                    String[] split2 = str3.split("\\|");
                    Log.i("SpenGarageSpecManager", "unbundled_spec = ".concat(str3));
                    for (String str4 : split2) {
                        SupportedExternalSpenFeature valueOf =
                                SupportedExternalSpenFeature.valueOf(str4.toUpperCase());
                        if (valueOf != null) {
                            this.mSupportedExternalSpenFeatures.add(valueOf);
                        }
                    }
                } else if (str2.equals("bundled")) {
                    this.mIsBundledSpenSupported = Boolean.valueOf(str3).booleanValue();
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "bundle = ", str3, "SpenGarageSpecManager");
                }
            }
        }
    }

    public static synchronized SpenGarageSpecManager getInstance() {
        SpenGarageSpecManager spenGarageSpecManager;
        synchronized (SpenGarageSpecManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SpenGarageSpecManager();
                }
                spenGarageSpecManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return spenGarageSpecManager;
    }
}
