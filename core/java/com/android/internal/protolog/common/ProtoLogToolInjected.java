package com.android.internal.protolog.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
/* loaded from: classes5.dex */
public @interface ProtoLogToolInjected {

    public enum Value {
        VIEWER_CONFIG_PATH,
        LEGACY_OUTPUT_FILE_PATH,
        LEGACY_VIEWER_CONFIG_PATH,
        LOG_GROUPS,
        CACHE_UPDATER
    }

    Value value();
}
