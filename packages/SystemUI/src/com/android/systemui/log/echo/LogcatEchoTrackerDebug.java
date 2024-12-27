package com.android.systemui.log.echo;

import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class LogcatEchoTrackerDebug implements LogcatEchoTracker {
    public final CoroutineScope applicationScope;
    public final CommandRegistry commandRegistry;
    public final GlobalSettings globalSettings;
    public final CoroutineDispatcher sequentialBgDispatcher;
    public volatile Map bufferOverrides = MapsKt__MapsKt.emptyMap();
    public volatile Map tagOverrides = MapsKt__MapsKt.emptyMap();
    public final LogcatEchoSettingFormat settingFormat = new LogcatEchoSettingFormat();

    public LogcatEchoTrackerDebug(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, GlobalSettings globalSettings, CommandRegistry commandRegistry) {
        this.applicationScope = coroutineScope;
        this.globalSettings = globalSettings;
        this.commandRegistry = commandRegistry;
        this.sequentialBgDispatcher = coroutineDispatcher.limitedParallelism(1);
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
        BuildersKt.launch$default(this.applicationScope, this.sequentialBgDispatcher, null, new LogcatEchoTrackerDebug$loadEchoOverrides$1(this, null), 2);
        this.commandRegistry.registerCommand("echo", new Function0() { // from class: com.android.systemui.log.echo.LogcatEchoTrackerDebug$start$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new LogcatEchoTrackerCommand(LogcatEchoTrackerDebug.this);
            }
        });
    }
}
