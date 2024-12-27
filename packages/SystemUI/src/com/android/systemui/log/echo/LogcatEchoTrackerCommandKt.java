package com.android.systemui.log.echo;

import kotlin.text.Regex;

public abstract class LogcatEchoTrackerCommandKt {
    public static final Regex OVERRIDE_PATTERN = new Regex("([^:]+):(.*)");
}
