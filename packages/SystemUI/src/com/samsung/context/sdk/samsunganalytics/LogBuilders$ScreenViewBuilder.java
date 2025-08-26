package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class LogBuilders$ScreenViewBuilder extends LogBuilders$LogBuilder {
    public final Map build() {
        if (TextUtils.isEmpty((CharSequence) ((HashMap) this.logs).get("pn"))) {
            Utils.throwException("Failure to build Log : Screen name cannot be null");
        } else {
            set("t", "pv");
        }
        set("ts", String.valueOf(System.currentTimeMillis()));
        return this.logs;
    }

    public final void setScreenView$1(String str) {
        if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to set Screen View : Screen name cannot be null.");
        } else {
            set("pn", str);
        }
    }
}
