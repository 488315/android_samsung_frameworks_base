package com.android.systemui.log.echo;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogcatEchoTrackerDebug implements LogcatEchoTracker {
    public final CoroutineScope applicationScope;
    public volatile Map bufferOverrides;
    public final CommandRegistry commandRegistry;
    public final GlobalSettings globalSettings;
    public final CoroutineDispatcher sequentialBgDispatcher;
    public final LogcatEchoSettingFormat settingFormat;
    public volatile Map tagOverrides;

    public LogcatEchoTrackerDebug(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, GlobalSettings globalSettings, CommandRegistry commandRegistry) {
        this.applicationScope = coroutineScope;
        this.globalSettings = globalSettings;
        this.commandRegistry = commandRegistry;
        CoroutineDispatcher.Key key = CoroutineDispatcher.Key;
        this.sequentialBgDispatcher = coroutineDispatcher.limitedParallelism(1);
        this.bufferOverrides = MapsKt__MapsKt.emptyMap();
        this.tagOverrides = MapsKt__MapsKt.emptyMap();
        this.settingFormat = new LogcatEchoSettingFormat();
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(LogLevel logLevel, String str) {
        LogLevel logLevel2 = (LogLevel) this.bufferOverrides.get(str);
        if (logLevel2 == null) {
            logLevel2 = LogcatEchoTrackerDebugKt.DEFAULT_LOG_LEVEL;
        }
        return logLevel.compareTo(logLevel2) >= 0;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(LogLevel logLevel, String str) {
        LogLevel logLevel2 = (LogLevel) this.tagOverrides.get(str);
        if (logLevel2 == null) {
            logLevel2 = LogcatEchoTrackerDebugKt.DEFAULT_LOG_LEVEL;
        }
        return logLevel.compareTo(logLevel2) >= 0;
    }

    public final List listEchoOverrides() {
        ArrayList arrayList = new ArrayList();
        Map map = this.bufferOverrides;
        Map map2 = this.tagOverrides;
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(new LogcatEchoOverride(EchoOverrideType.BUFFER, (String) entry.getKey(), (LogLevel) entry.getValue()));
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            arrayList.add(new LogcatEchoOverride(EchoOverrideType.TAG, (String) entry2.getKey(), (LogLevel) entry2.getValue()));
        }
        return arrayList;
    }

    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.sequentialBgDispatcher, null, new LogcatEchoTrackerDebug$loadEchoOverrides$1(this, null), 5);
        this.commandRegistry.registerCommand("echo", new Function0() { // from class: com.android.systemui.log.echo.LogcatEchoTrackerDebug$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new LogcatEchoTrackerCommand(LogcatEchoTrackerDebug.this);
            }
        });
    }
}
