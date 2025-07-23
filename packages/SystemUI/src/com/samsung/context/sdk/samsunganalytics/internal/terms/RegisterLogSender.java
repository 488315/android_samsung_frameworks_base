package com.samsung.context.sdk.samsunganalytics.internal.terms;

import android.app.Application;
import com.samsung.context.sdk.samsunganalytics.Configuration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
