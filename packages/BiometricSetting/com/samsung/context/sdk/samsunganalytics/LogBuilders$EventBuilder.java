package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class LogBuilders$EventBuilder extends LogBuilders$LogBuilder {
    public final /* synthetic */ int $r8$classId;

    @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders$LogBuilder
    public final Map build() {
        switch (this.$r8$classId) {
            case 0:
                if (!((HashMap) this.logs).containsKey("en")) {
                    Utils.throwException("Failure to build Log : Event name cannot be null");
                }
                set("t", "ev");
                break;
            default:
                if (TextUtils.isEmpty((CharSequence) ((HashMap) this.logs).get("pn"))) {
                    Utils.throwException("Failure to build Log : Screen name cannot be null");
                } else {
                    set("t", "pv");
                }
                break;
        }
        return super.build();
    }
}
