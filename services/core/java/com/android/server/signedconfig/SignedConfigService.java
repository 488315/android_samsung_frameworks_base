package com.android.server.signedconfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManagerInternal;
import android.net.Uri;
import android.os.Bundle;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class SignedConfigService {

    public final class UpdateReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i;
            int i2;
            PackageManagerInternal packageManagerInternal =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            Uri data = intent.getData();
            String schemeSpecificPart = data == null ? null : data.getSchemeSpecificPart();
            if (schemeSpecificPart == null) {
                return;
            }
            int identifier = context.getUser().getIdentifier();
            PackageInfo packageInfo =
                    packageManagerInternal.getPackageInfo(
                            1000, identifier, 128L, schemeSpecificPart);
            if (packageInfo == null) {
                Slog.w(
                        "SignedConfig",
                        "Got null PackageInfo for " + schemeSpecificPart + "; user " + identifier);
                return;
            }
            Bundle bundle = packageInfo.applicationInfo.metaData;
            if (bundle != null
                    && bundle.containsKey("android.settings.global")
                    && bundle.containsKey("android.settings.global.signature")) {
                SignedConfigEvent signedConfigEvent = new SignedConfigEvent();
                signedConfigEvent.status = 0;
                signedConfigEvent.version = 0;
                signedConfigEvent.verifiedWith = 0;
                try {
                    signedConfigEvent.type = 1;
                    signedConfigEvent.fromPackage = schemeSpecificPart;
                    try {
                        new GlobalSettingsConfigApplicator(
                                        context, schemeSpecificPart, signedConfigEvent)
                                .applyConfig(
                                        new String(
                                                Base64.getDecoder()
                                                        .decode(
                                                                bundle.getString(
                                                                        "android.settings.global")),
                                                StandardCharsets.UTF_8),
                                        bundle.getString("android.settings.global.signature"));
                        i2 = signedConfigEvent.type;
                        i = signedConfigEvent.status;
                    } catch (IllegalArgumentException unused) {
                        Slog.e(
                                "SignedConfig",
                                "Failed to base64 decode global settings config from "
                                        .concat(schemeSpecificPart));
                        i = 2;
                        signedConfigEvent.status = 2;
                        i2 = signedConfigEvent.type;
                    }
                    FrameworkStatsLog.write(
                            123,
                            i2,
                            i,
                            signedConfigEvent.version,
                            signedConfigEvent.fromPackage,
                            signedConfigEvent.verifiedWith);
                } catch (Throwable th) {
                    FrameworkStatsLog.write(
                            123,
                            signedConfigEvent.type,
                            signedConfigEvent.status,
                            signedConfigEvent.version,
                            signedConfigEvent.fromPackage,
                            signedConfigEvent.verifiedWith);
                    throw th;
                }
            }
        }
    }

    public static void registerUpdateReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(new UpdateReceiver(), intentFilter);
    }
}
