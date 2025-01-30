package com.android.systemui.media.taptotransfer.receiver;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaTttReceiverLogger extends TemporaryViewLogger {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaTttReceiverLogger(LogBuffer logBuffer) {
        super(logBuffer, "MediaTttReceiver");
    }
}
