package com.facebook.rebound;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SpringConfigRegistry {
    public static final SpringConfigRegistry INSTANCE = new SpringConfigRegistry(true);
    public final Map mSpringConfigMap = new HashMap();

    public SpringConfigRegistry(boolean z) {
        if (z) {
            addSpringConfig(SpringConfig.defaultConfig, "default config");
        }
    }

    public final void addSpringConfig(SpringConfig springConfig, String str) {
        if (springConfig == null) {
            throw new IllegalArgumentException("springConfig is required");
        }
        if (str == null) {
            throw new IllegalArgumentException("configName is required");
        }
        if (((HashMap) this.mSpringConfigMap).containsKey(springConfig)) {
            return;
        }
        ((HashMap) this.mSpringConfigMap).put(springConfig, str);
    }
}
