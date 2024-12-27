package com.android.systemui.log.echo;

import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
