package com.android.systemui.media.controls.domain.pipeline;

import android.service.notification.StatusBarNotification;
import com.android.systemui.media.controls.shared.model.MediaData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MediaDataManager {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    void addListener(Listener listener);

    boolean dismissMediaData(String str, long j, boolean z);

    boolean hasActiveMediaOrRecommendation();

    boolean hasAnyMediaOrRecommendation();

    void onNotificationAdded(String str, StatusBarNotification statusBarNotification);

    void onNotificationRemoved(String str);

    void onSwipeToDismiss();

    void removeListener(Listener listener);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Listener {
        static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, int i) {
            if ((i & 8) != 0) {
                z = true;
            }
            listener.onMediaDataLoaded(str, str2, mediaData, z);
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z);

        default void onMediaDataRemoved(String str, boolean z) {
        }
    }
}
