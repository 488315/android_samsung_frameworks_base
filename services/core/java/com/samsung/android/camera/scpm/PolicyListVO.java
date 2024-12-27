package com.samsung.android.camera.scpm;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PolicyListVO {
    public static final Pattern BASE64_PATTERN =
            Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    public final String extra;
    public final String packageName;
    public final String value;

    public PolicyListVO(String str, String str2, String str3) {
        if (BASE64_PATTERN.matcher(str).find()) {
            this.packageName = new String(Base64.decode(str, 0), StandardCharsets.UTF_8);
        } else {
            this.packageName = str;
        }
        this.extra = str3;
        this.value = str2;
    }

    public final String toString() {
        return "packageName = "
                + this.packageName
                + " value = "
                + this.value
                + " extra = "
                + this.extra;
    }
}
