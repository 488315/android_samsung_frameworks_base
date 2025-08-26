package com.google.common.util.concurrent;

import java.util.logging.Logger;

/* loaded from: classes4.dex */
public final class LazyLogger {
    public final Object lock = new Object();
    public volatile Logger logger;
    public final String loggerName;

    public LazyLogger(Class<?> cls) {
        this.loggerName = cls.getName();
    }

    public final Logger get() {
        Logger logger = this.logger;
        if (logger != null) {
            return logger;
        }
        synchronized (this.lock) {
            try {
                Logger logger2 = this.logger;
                if (logger2 != null) {
                    return logger2;
                }
                Logger logger3 = Logger.getLogger(this.loggerName);
                this.logger = logger3;
                return logger3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
