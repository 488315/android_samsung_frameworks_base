package com.samsung.android.server.pm.install;

import android.content.Context;
import android.util.ArrayMap;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PackageInstallTimeLogger {
    public static final boolean ENABLE_SURVEY_MODE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public final Context mContext;
    public final ArrayMap mInstallLogging = new ArrayMap();
    public final ArrayList mHistoricalInfo = new ArrayList();

    public PackageInstallTimeLogger(Context context) {
        this.mContext = context;
    }
}
