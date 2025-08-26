package com.android.systemui.log;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaLoggerImpl implements MediaLogger {
    public final MediaLogWriter writer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MediaLoggerImpl(MediaLogWriter mediaLogWriter) {
        this.writer = mediaLogWriter;
    }
}
