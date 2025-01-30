package com.android.systemui.media.controls.util;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum MediaUiEvent implements UiEventLogger.UiEventEnum {
    LOCAL_MEDIA_ADDED(1029),
    CAST_MEDIA_ADDED(1030),
    REMOTE_MEDIA_ADDED(1031),
    TRANSFER_TO_LOCAL(1032),
    TRANSFER_TO_CAST(1033),
    TRANSFER_TO_REMOTE(1034),
    RESUME_MEDIA_ADDED(EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED),
    ACTIVE_TO_RESUME(EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL),
    MEDIA_TIMEOUT(EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED),
    MEDIA_REMOVED(EnterpriseContainerCallback.CONTAINER_CANCELLED),
    /* JADX INFO: Fake field, exist only in values array */
    CAROUSEL_PAGE(1017),
    /* JADX INFO: Fake field, exist only in values array */
    DISMISS_SWIPE(1018),
    /* JADX INFO: Fake field, exist only in values array */
    OPEN_LONG_PRESS(1019),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1020),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1021),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1022),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1023),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1024),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1025),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1026),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1027),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1028),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1036),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1037),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1038),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1039),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1040),
    MEDIA_RECOMMENDATION_ADDED(1041),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1042),
    MEDIA_RECOMMENDATION_ACTIVATED(1043),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1044),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1045),
    /* JADX INFO: Fake field, exist only in values array */
    MEDIA_OPEN_BROADCAST_DIALOG(1079),
    MEDIA_CAROUSEL_SINGLE_PLAYER(1244),
    MEDIA_CAROUSEL_MULTIPLE_PLAYERS(1245);

    private final int metricId;

    MediaUiEvent(int i) {
        this.metricId = i;
    }

    public final int getId() {
        return this.metricId;
    }
}
