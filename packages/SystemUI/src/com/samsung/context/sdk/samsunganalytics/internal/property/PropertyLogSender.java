package com.samsung.context.sdk.samsunganalytics.internal.property;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;

/* loaded from: classes4.dex */
public class PropertyLogSender {
    public final Configuration config;
    public final Context context;

    public PropertyLogSender(Context context, Configuration configuration) {
        this.context = context;
        this.config = configuration;
    }
}
