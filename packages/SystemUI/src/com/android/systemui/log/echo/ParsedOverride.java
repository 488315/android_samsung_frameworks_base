package com.android.systemui.log.echo;

import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
