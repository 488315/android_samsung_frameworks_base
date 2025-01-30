package com.samsung.android.camera.scpm;

import android.util.Base64;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class PolicyListVO {
    public static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    public String decodedName;
    public String disallowUnihalVersion;
    public String packageName;
    public String value;

    public PolicyListVO(String str, String str2, String str3) {
        if (isBase64Encoded(str)) {
            this.packageName = str;
            this.decodedName = new String(Base64.decode(str, 0));
        } else {
            this.packageName = Base64.encodeToString(str.getBytes(), 2);
            this.decodedName = str;
        }
        this.disallowUnihalVersion = str3;
        this.value = str2;
    }

    public String toString() {
        return "packageName = " + this.decodedName + " value = " + this.value + " disallowUnihalVersion = " + this.disallowUnihalVersion;
    }

    public static boolean isBase64Encoded(String str) {
        return BASE64_PATTERN.matcher(str).find();
    }
}
