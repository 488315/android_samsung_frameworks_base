package com.facebook.rebound;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SpringConfigRegistry {
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
        HashMap hashMap = (HashMap) this.mSpringConfigMap;
        if (hashMap.containsKey(springConfig)) {
            return;
        }
        hashMap.put(springConfig, str);
    }
}
