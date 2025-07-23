package com.android.systemui.statusbar.notification.row;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationEntryProcessorFactoryLooperImpl implements NotificationEntryProcessorFactory {
    public final Looper mMainLooper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HandlerProcessor extends Handler {
        public final Consumer consumer;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public HandlerProcessor(Looper looper, Consumer<NotificationEntry> consumer) {
            super(looper);
            this.consumer = consumer;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(message.what, "Unknown message type: "));
            }
            this.consumer.accept((NotificationEntry) message.obj);
        }
    }

    public NotificationEntryProcessorFactoryLooperImpl(Looper looper) {
        this.mMainLooper = looper;
    }
}
