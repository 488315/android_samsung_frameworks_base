package com.samsung.context.sdk.samsunganalytics.internal.terms;

import android.app.Application;
import com.samsung.context.sdk.samsunganalytics.Configuration;

/* loaded from: classes4.dex */
public class RegisterLogSender {
    public final Application application;
    public final Configuration config;
    public final String deviceId;
    public final RegisterType registerType;

    public RegisterLogSender(Application application, Configuration configuration, RegisterType registerType) {
        this.application = application;
        this.config = configuration;
        configuration.getClass();
        this.deviceId = null;
        this.registerType = registerType;
    }
}
