package com.android.server;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ISecurityStateManager;
import android.os.SystemProperties;
import android.os.VintfRuntimeInfo;
import android.text.TextUtils;
import android.util.Slog;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewUpdateService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecurityStateManagerService extends ISecurityStateManager.Stub {
    public static final Pattern KERNEL_RELEASE_PATTERN = Pattern.compile("(\\d+\\.\\d+\\.\\d+)(.*)");
    public final Context mContext;
    public final PackageManager mPackageManager;

    public SecurityStateManagerService(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public final Bundle getGlobalSecurityState() {
        Bundle bundle = new Bundle();
        bundle.putString("system_spl", Build.VERSION.SECURITY_PATCH);
        bundle.putString("vendor_spl", SystemProperties.get("ro.vendor.build.security_patch", ""));
        String string = this.mContext.getString(R.string.default_audio_route_category_name);
        if (!string.isEmpty()) {
            bundle.putString(string, getSpl(string));
        }
        Matcher matcher = KERNEL_RELEASE_PATTERN.matcher(VintfRuntimeInfo.getKernelVersion());
        bundle.putString("kernel_version", matcher.matches() ? matcher.group(1) : "");
        for (WebViewProviderInfo webViewProviderInfo : WebViewUpdateService.getAllWebViewPackages()) {
            String str = webViewProviderInfo.packageName;
            bundle.putString(str, getSpl(str));
        }
        for (String str2 : this.mContext.getResources().getStringArray(17236313)) {
            bundle.putString(str2, getSpl(str2));
        }
        return bundle;
    }

    public final String getSpl(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return this.mPackageManager.getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("SecurityStateManagerService", TextUtils.formatSimple("Failed to get SPL for package %s.", new Object[]{str}), e);
            return "";
        }
    }
}
