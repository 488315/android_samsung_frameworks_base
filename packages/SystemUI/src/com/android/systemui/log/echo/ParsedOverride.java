package com.android.systemui.log.echo;

import com.android.systemui.log.core.LogLevel;

/* loaded from: classes2.dex */
public final class ParsedOverride {
    public final LogLevel level;
    public final String name;
    public final EchoOverrideType type;

    public ParsedOverride(EchoOverrideType echoOverrideType, String str, LogLevel logLevel) {
        this.type = echoOverrideType;
        this.name = str;
        this.level = logLevel;
    }
}
