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
        char c = 1;
        final int state = 0;
        switch (this.$r8$classId) {
            case 0:
                final NotificationMediaManager notificationMediaManager = (NotificationMediaManager) this.f$0;
                final KeyguardSliceProvider keyguardSliceProvider = (KeyguardSliceProvider) this.f$1;
                MediaController mediaController2 = notificationMediaManager.mMediaController;
                final int state2 = (mediaController2 == null || (playbackState = mediaController2.getPlaybackState()) == null) ? 0 : playbackState.getState();
                notificationMediaManager.mHandler.post(new Runnable() { // from class: com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (state) {
                            case 0:
                                NotificationMediaManager notificationMediaManager2 = notificationMediaManager;
                                ((KeyguardSliceProvider) keyguardSliceProvider).onPrimaryMetadataOrStateChanged(notificationMediaManager2.mMediaMetadata, state2);
                                break;
                            default:
                                NotificationMediaManager notificationMediaManager3 = notificationMediaManager;
                                List list = (List) keyguardSliceProvider;
                                int i = state2;
                                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                                notificationMediaManager3.getClass();
                                for (int i2 = 0; i2 < list.size(); i2++) {
                                    ((KeyguardSliceProvider) ((NotificationMediaManager.MediaListener) list.get(i2))).onPrimaryMetadataOrStateChanged(notificationMediaManager3.mMediaMetadata, i);
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
                int i = 0;
                while (true) {
                    if (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
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
                    state = playbackState2.getState();
                }
                final char c2 = c == true ? 1 : 0;
                notificationMediaManager3.mHandler.post(new Runnable() { // from class: com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (c2) {
                            case 0:
                                NotificationMediaManager notificationMediaManager22 = notificationMediaManager3;
                                ((KeyguardSliceProvider) arrayList2).onPrimaryMetadataOrStateChanged(notificationMediaManager22.mMediaMetadata, state);
                                break;
                            default:
                                NotificationMediaManager notificationMediaManager32 = notificationMediaManager3;
                                List list2 = (List) arrayList2;
                                int i2 = state;
                                HashSet hashSet2 = NotificationMediaManager.PAUSED_MEDIA_STATES;
                                notificationMediaManager32.getClass();
                                for (int i22 = 0; i22 < list2.size(); i22++) {
                                    ((KeyguardSliceProvider) ((NotificationMediaManager.MediaListener) list2.get(i22))).onPrimaryMetadataOrStateChanged(notificationMediaManager32.mMediaMetadata, i2);
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
