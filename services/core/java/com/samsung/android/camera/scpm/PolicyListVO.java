package com.samsung.android.camera.scpm;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

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
