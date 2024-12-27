package com.android.server.signedconfig;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GlobalSettingsConfigApplicator {
    public static final Set ALLOWED_KEYS =
            Collections.unmodifiableSet(
                    new ArraySet(
                            Arrays.asList("hidden_api_policy", "hidden_api_blacklist_exemptions")));
    public static final Map KEY_VALUE_MAPPERS =
            makeMap(
                    "hidden_api_policy",
                    makeMap(
                            "DEFAULT",
                            String.valueOf(-1),
                            "DISABLED",
                            String.valueOf(0),
                            "JUST_WARN",
                            String.valueOf(1),
                            "ENABLED",
                            String.valueOf(2)));
    public final Context mContext;
    public final SignedConfigEvent mEvent;
    public final String mSourcePackage;
    public final SignatureVerifier mVerifier;

    public GlobalSettingsConfigApplicator(
            Context context, String str, SignedConfigEvent signedConfigEvent) {
        this.mContext = context;
        this.mSourcePackage = str;
        this.mEvent = signedConfigEvent;
        this.mVerifier = new SignatureVerifier(signedConfigEvent);
    }

    public static Map makeMap(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        int length = objArr.length / 2;
        ArrayMap arrayMap = new ArrayMap(length);
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            arrayMap.put(objArr[i2], objArr[i2 + 1]);
        }
        return Collections.unmodifiableMap(arrayMap);
    }

    public final void applyConfig(String str, String str2) {
        boolean z;
        SignedConfig.PerSdkConfig perSdkConfig;
        SignedConfigEvent signedConfigEvent = this.mEvent;
        try {
            z = this.mVerifier.verifySignature(str, str2);
        } catch (GeneralSecurityException e) {
            Slog.e("SignedConfig", "Failed to verify signature", e);
            signedConfigEvent.status = 4;
            z = false;
        }
        String str3 = this.mSourcePackage;
        if (!z) {
            Slog.e(
                    "SignedConfig",
                    "Signature check on global settings in package " + str3 + " failed; ignoring");
            return;
        }
        try {
            SignedConfig parse = SignedConfig.parse(str);
            int i = parse.version;
            signedConfigEvent.version = i;
            int i2 =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), "signed_config_version", 0);
            if (i2 >= i) {
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i,
                                "Global settings from package ",
                                str3,
                                " is older than existing: ",
                                "<="),
                        i2,
                        "SignedConfig");
                signedConfigEvent.status = 6;
                return;
            }
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            "Got new global settings from package ",
                            str3,
                            ": version ",
                            " replacing existing version "),
                    i2,
                    "SignedConfig");
            int i3 = Build.VERSION.SDK_INT;
            Iterator it = parse.perSdkConfig.iterator();
            while (true) {
                if (!it.hasNext()) {
                    perSdkConfig = null;
                    break;
                }
                perSdkConfig = (SignedConfig.PerSdkConfig) it.next();
                if (perSdkConfig.minSdk <= i3 && i3 <= perSdkConfig.maxSdk) {
                    break;
                }
            }
            if (perSdkConfig == null) {
                Slog.i(
                        "SignedConfig",
                        "Settings is not applicable to current SDK version; ignoring");
                signedConfigEvent.status = 8;
                return;
            }
            HermesService$3$$ExternalSyntheticOutline0.m(
                    i, "Updating global settings to version ", "SignedConfig");
            for (Map.Entry entry : perSdkConfig.values.entrySet()) {
                Settings.Global.putString(
                        this.mContext.getContentResolver(),
                        (String) entry.getKey(),
                        (String) entry.getValue());
            }
            Settings.Global.putInt(this.mContext.getContentResolver(), "signed_config_version", i);
            signedConfigEvent.status = 1;
        } catch (InvalidConfigException e2) {
            Slog.e("SignedConfig", "Failed to parse global settings from package " + str3, e2);
            signedConfigEvent.status = 5;
        }
    }
}
