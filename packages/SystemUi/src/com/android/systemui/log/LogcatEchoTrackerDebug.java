package com.android.systemui.log;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.provider.Settings;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogcatEchoTrackerDebug implements LogcatEchoTracker {
    public static final Factory Factory = new Factory(null);
    private final Map<String, LogLevel> cachedBufferLevels;
    private final Map<String, LogLevel> cachedTagLevels;
    private final ContentResolver contentResolver;
    private final boolean logInBackgroundThread;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ LogcatEchoTrackerDebug(ContentResolver contentResolver, DefaultConstructorMarker defaultConstructorMarker) {
        this(contentResolver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void attach(Looper looper) {
        Trace.beginSection("LogcatEchoTrackerDebug#attach");
        ContentResolver contentResolver = this.contentResolver;
        Uri uriFor = Settings.Global.getUriFor("systemui/buffer");
        final Handler handler = new Handler(looper);
        contentResolver.registerContentObserver(uriFor, true, new ContentObserver(handler) { // from class: com.android.systemui.log.LogcatEchoTrackerDebug$attach$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                LogcatEchoTrackerDebug.this.clearCache();
            }
        });
        ContentResolver contentResolver2 = this.contentResolver;
        Uri uriFor2 = Settings.Global.getUriFor("systemui/tag");
        final Handler handler2 = new Handler(looper);
        contentResolver2.registerContentObserver(uriFor2, true, new ContentObserver(handler2) { // from class: com.android.systemui.log.LogcatEchoTrackerDebug$attach$2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                LogcatEchoTrackerDebug.this.clearCache();
            }
        });
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearCache() {
        Trace.beginSection("LogcatEchoTrackerDebug#clearCache");
        this.cachedBufferLevels.clear();
        Trace.endSection();
    }

    public static final LogcatEchoTrackerDebug create(ContentResolver contentResolver, Looper looper) {
        Factory.getClass();
        LogcatEchoTrackerDebug logcatEchoTrackerDebug = new LogcatEchoTrackerDebug(contentResolver, null);
        logcatEchoTrackerDebug.attach(looper);
        return logcatEchoTrackerDebug;
    }

    private final LogLevel getLogLevel(String str, String str2, Map<String, LogLevel> map) {
        LogLevel logLevel = map.get(str);
        if (logLevel != null) {
            return logLevel;
        }
        LogLevel readSetting = readSetting(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str2, "/", str));
        map.put(str, readSetting);
        return readSetting;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final LogLevel parseProp(String str) {
        String lowerCase = str != null ? str.toLowerCase(Locale.ROOT) : null;
        if (lowerCase != null) {
            switch (lowerCase.hashCode()) {
                case -1408208058:
                    if (lowerCase.equals("assert")) {
                        return LogLevel.WTF;
                    }
                    break;
                case 100:
                    if (lowerCase.equals("d")) {
                        return LogLevel.DEBUG;
                    }
                    break;
                case 101:
                    if (lowerCase.equals("e")) {
                        return LogLevel.ERROR;
                    }
                    break;
                case 105:
                    if (lowerCase.equals("i")) {
                        return LogLevel.INFO;
                    }
                    break;
                case 118:
                    if (lowerCase.equals("v")) {
                        return LogLevel.VERBOSE;
                    }
                    break;
                case 119:
                    if (lowerCase.equals("w")) {
                        return LogLevel.WARNING;
                    }
                    break;
                case 118057:
                    if (lowerCase.equals("wtf")) {
                        return LogLevel.WTF;
                    }
                    break;
                case 3237038:
                    if (lowerCase.equals("info")) {
                        return LogLevel.INFO;
                    }
                    break;
                case 3641990:
                    if (lowerCase.equals("warn")) {
                        return LogLevel.WARNING;
                    }
                    break;
                case 95458899:
                    if (lowerCase.equals("debug")) {
                        return LogLevel.DEBUG;
                    }
                    break;
                case 96784904:
                    if (lowerCase.equals("error")) {
                        return LogLevel.ERROR;
                    }
                    break;
                case 351107458:
                    if (lowerCase.equals("verbose")) {
                        return LogLevel.VERBOSE;
                    }
                    break;
                case 1124446108:
                    if (lowerCase.equals("warning")) {
                        return LogLevel.WARNING;
                    }
                    break;
            }
        }
        return LogcatEchoTrackerDebugKt.DEFAULT_LEVEL;
    }

    private final LogLevel readSetting(String str) {
        LogLevel logLevel;
        try {
            try {
                Trace.beginSection("LogcatEchoTrackerDebug#readSetting");
                logLevel = parseProp(Settings.Global.getString(this.contentResolver, str));
            } catch (Settings.SettingNotFoundException unused) {
                logLevel = LogcatEchoTrackerDebugKt.DEFAULT_LEVEL;
            }
            return logLevel;
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public boolean getLogInBackgroundThread() {
        return this.logInBackgroundThread;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public synchronized boolean isBufferLoggable(String str, LogLevel logLevel) {
        return logLevel.ordinal() >= getLogLevel(str, "systemui/buffer", this.cachedBufferLevels).ordinal();
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public synchronized boolean isTagLoggable(String str, LogLevel logLevel) {
        return logLevel.compareTo(getLogLevel(str, "systemui/tag", this.cachedTagLevels)) >= 0;
    }

    private LogcatEchoTrackerDebug(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.cachedBufferLevels = new LinkedHashMap();
        this.cachedTagLevels = new LinkedHashMap();
        this.logInBackgroundThread = true;
    }
}
