package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.service.notification.StatusBarNotification;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface MediaDataManager {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    void addListener(Listener listener);

    void addResumptionControls(int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2);

    boolean dismissMediaData(String str, long j, boolean z);

    void dismissSmartspaceRecommendation(String str, long j);

    boolean hasActiveMediaOrRecommendation();

    boolean hasAnyMediaOrRecommendation();

    boolean isRecommendationActive();

    void onNotificationAdded(String str, StatusBarNotification statusBarNotification);

    void onNotificationRemoved(String str);

    void onSwipeToDismiss();

    void removeListener(Listener listener);

    void setInactive(String str, boolean z, boolean z2);

    void setMediaResumptionEnabled(boolean z);

    void setResumeAction(Runnable runnable, String str);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Listener {
        static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, int i, boolean z2, int i2) {
            if ((i2 & 8) != 0) {
                z = true;
            }
            listener.onMediaDataLoaded(str, str2, mediaData, z, (i2 & 16) != 0 ? 0 : i, (i2 & 32) != 0 ? false : z2);
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2);

        default void onMediaDataRemoved(String str, boolean z) {
        }

        default void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        default void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }
    }
}
