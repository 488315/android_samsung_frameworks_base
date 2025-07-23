package com.android.systemui.media;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.service.notification.StatusBarNotification;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.media.NotificationMediaManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationMediaManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationMediaManager$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PlaybackState playbackState;
        StatusBarNotification statusBarNotification;
        MediaController mediaController;
        MediaSession.Token token;
        PlaybackState playbackState2;
        boolean z = true;
        final int i = 0;
        switch (this.$r8$classId) {
            case 0:
                final NotificationMediaManager notificationMediaManager = (NotificationMediaManager) this.f$0;
                final KeyguardSliceProvider keyguardSliceProvider = (KeyguardSliceProvider) this.f$1;
                MediaController mediaController2 = notificationMediaManager.mMediaController;
                final int state = (mediaController2 == null || (playbackState = mediaController2.getPlaybackState()) == null) ? 0 : playbackState.getState();
                notificationMediaManager.mHandler.post(new Runnable() { // from class: com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                NotificationMediaManager notificationMediaManager2 = notificationMediaManager;
                                ((KeyguardSliceProvider) keyguardSliceProvider).onPrimaryMetadataOrStateChanged(notificationMediaManager2.mMediaMetadata, state);
                                break;
                            default:
                                NotificationMediaManager notificationMediaManager3 = notificationMediaManager;
                                List list = (List) keyguardSliceProvider;
                                int i2 = state;
                                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                                notificationMediaManager3.getClass();
                                for (int i3 = 0; i3 < list.size(); i3++) {
                                    ((KeyguardSliceProvider) ((NotificationMediaManager.MediaListener) list.get(i3))).onPrimaryMetadataOrStateChanged(notificationMediaManager3.mMediaMetadata, i2);
                                }
                                break;
                        }
                    }
                });
                break;
            case 1:
                NotificationMediaManager notificationMediaManager2 = (NotificationMediaManager) this.f$0;
                List list = (List) this.f$1;
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                notificationMediaManager2.getClass();
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        statusBarNotification = (StatusBarNotification) obj;
                        if (statusBarNotification.getNotification().isMediaNotification() && (token = (MediaSession.Token) statusBarNotification.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class)) != null) {
                            mediaController = new MediaController(notificationMediaManager2.mContext, token);
                            PlaybackState playbackState3 = mediaController.getPlaybackState();
                            if (3 == (playbackState3 != null ? playbackState3.getState() : 0)) {
                            }
                        }
                    } else {
                        statusBarNotification = null;
                        mediaController = null;
                    }
                }
                if (mediaController != null) {
                    MediaController mediaController3 = notificationMediaManager2.mMediaController;
                    if (!(mediaController3 != mediaController ? mediaController3 == null ? false : mediaController3.controlsSameSession(mediaController) : true)) {
                        notificationMediaManager2.mMediaMetadata = null;
                        MediaController mediaController4 = notificationMediaManager2.mMediaController;
                        if (mediaController4 != null) {
                            mediaController4.unregisterCallback(notificationMediaManager2.mMediaListener);
                        }
                        notificationMediaManager2.mMediaController = mediaController;
                        mediaController.registerCallback(notificationMediaManager2.mMediaListener, notificationMediaManager2.mHandler);
                        notificationMediaManager2.mMediaMetadata = notificationMediaManager2.mMediaController.getMetadata();
                    }
                }
                if (statusBarNotification != null && !statusBarNotification.getKey().equals(notificationMediaManager2.mMediaNotificationKey)) {
                    notificationMediaManager2.mMediaNotificationKey = statusBarNotification.getKey();
                    break;
                }
                break;
            case 2:
                final NotificationMediaManager notificationMediaManager3 = (NotificationMediaManager) this.f$0;
                final ArrayList arrayList2 = (ArrayList) this.f$1;
                MediaController mediaController5 = notificationMediaManager3.mMediaController;
                if (mediaController5 != null && (playbackState2 = mediaController5.getPlaybackState()) != null) {
                    i = playbackState2.getState();
                }
                final boolean z2 = z ? 1 : 0;
                notificationMediaManager3.mHandler.post(new Runnable() { // from class: com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (z2) {
                            case 0:
                                NotificationMediaManager notificationMediaManager22 = notificationMediaManager3;
                                ((KeyguardSliceProvider) arrayList2).onPrimaryMetadataOrStateChanged(notificationMediaManager22.mMediaMetadata, i);
                                break;
                            default:
                                NotificationMediaManager notificationMediaManager32 = notificationMediaManager3;
                                List list2 = (List) arrayList2;
                                int i22 = i;
                                HashSet hashSet2 = NotificationMediaManager.PAUSED_MEDIA_STATES;
                                notificationMediaManager32.getClass();
                                for (int i3 = 0; i3 < list2.size(); i3++) {
                                    ((KeyguardSliceProvider) ((NotificationMediaManager.MediaListener) list2.get(i3))).onPrimaryMetadataOrStateChanged(notificationMediaManager32.mMediaMetadata, i22);
                                }
                                break;
                        }
                    }
                });
                break;
            default:
                ((NotificationMediaManager.AnonymousClass1) this.f$0).this$0.mMediaMetadata = (MediaMetadata) this.f$1;
                break;
        }
    }
}
