package com.android.systemui.basic.util;

import android.util.Log;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogWrapper {
    public final ILog mLogger;
    public final ModuleType mModule;
    public final SamsungServiceLogger mServiceLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ILog {
    }

    public LogWrapper(ModuleType moduleType, SamsungServiceLogger samsungServiceLogger) {
        this(moduleType, samsungServiceLogger, new ILog() { // from class: com.android.systemui.basic.util.LogWrapper.1
        });
    }

    /* renamed from: d */
    public final void m98d(String str, String str2) {
        String moduleTag = toModuleTag(str);
        this.mLogger.getClass();
        Log.d(moduleTag, str2);
    }

    /* renamed from: dp */
    public final void m99dp(String str, String str2) {
        m98d(str, str2);
        m102p(str2);
    }

    /* renamed from: e */
    public final void m100e(String str, String str2) {
        String moduleTag = toModuleTag(str);
        this.mLogger.getClass();
        Log.e(moduleTag, str2);
    }

    /* renamed from: i */
    public final void m101i(String str, String str2) {
        String moduleTag = toModuleTag(str);
        this.mLogger.getClass();
        Log.i(moduleTag, str2);
    }

    /* renamed from: p */
    public final void m102p(String str) {
        SamsungServiceLogger samsungServiceLogger = this.mServiceLogger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).log(this.mModule.toString(), LogLevel.DEBUG, str);
        }
    }

    public final String toModuleTag(String str) {
        return this.mModule.toString() + str;
    }

    /* renamed from: v */
    public final void m103v(String str) {
        toModuleTag(str);
        this.mLogger.getClass();
    }

    public LogWrapper(ModuleType moduleType, SamsungServiceLogger samsungServiceLogger, ILog iLog) {
        this.mModule = moduleType;
        this.mServiceLogger = samsungServiceLogger;
        this.mLogger = iLog;
    }
}
