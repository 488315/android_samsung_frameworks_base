package com.android.systemui.statusbar;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationMediaManager implements Dumpable {
    public static final HashSet CONNECTING_MEDIA_STATES;
    public static final HashSet PAUSED_MEDIA_STATES;
    public final Executor mBackgroundExecutor;
    public final Context mContext;
    public final Handler mHandler;
    MediaController mMediaController;
    public final MediaDataManager mMediaDataManager;
    final MediaController.Callback mMediaListener = new MediaController.Callback() { // from class: com.android.systemui.statusbar.NotificationMediaManager.1
        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            Flags.FEATURE_FLAGS.getClass();
            NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
            notificationMediaManager.mMediaMetadata = mediaMetadata;
            HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
            ArrayList arrayList = new ArrayList(notificationMediaManager.mMediaListeners);
            Flags.FEATURE_FLAGS.getClass();
            notificationMediaManager.updateMediaMetaData(arrayList);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            super.onPlaybackStateChanged(playbackState);
            if (playbackState != null) {
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                int state = playbackState.getState();
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                notificationMediaManager.getClass();
                if (state == 1 || state == 7 || state == 0) {
                    NotificationMediaManager notificationMediaManager2 = NotificationMediaManager.this;
                    notificationMediaManager2.getClass();
                    Flags.FEATURE_FLAGS.getClass();
                    notificationMediaManager2.mMediaNotificationKey = null;
                    notificationMediaManager2.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager2.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager2.mMediaListener);
                    }
                    notificationMediaManager2.mMediaController = null;
                }
                NotificationMediaManager.this.findAndUpdateMediaNotifications();
            }
        }
    };
    public final ArrayList mMediaListeners = new ArrayList();
    public MediaMetadata mMediaMetadata;
    public String mMediaNotificationKey;
    public final NotifCollection mNotifCollection;
    public final NotifPipeline mNotifPipeline;
    public final NotificationVisibilityProvider mVisibilityProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MediaListener {
    }

    static {
        HashSet hashSet = new HashSet();
        PAUSED_MEDIA_STATES = hashSet;
        HashSet hashSet2 = new HashSet();
        CONNECTING_MEDIA_STATES = hashSet2;
        hashSet.add(0);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(7);
        hashSet2.add(8);
        hashSet2.add(6);
    }

    public NotificationMediaManager(Context context, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, NotifCollection notifCollection, MediaDataManager mediaDataManager, DumpManager dumpManager, Executor executor, Handler handler) {
        this.mContext = context;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mMediaDataManager = mediaDataManager;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        this.mBackgroundExecutor = executor;
        this.mHandler = handler;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.NotificationMediaManager.2
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                NotificationMediaManager.this.mMediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
                NotificationMediaManager.this.findAndUpdateMediaNotifications();
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                notificationMediaManager.getClass();
                if (notificationEntry.mKey.equals(notificationMediaManager.mMediaNotificationKey)) {
                    Flags.FEATURE_FLAGS.getClass();
                    notificationMediaManager.mMediaNotificationKey = null;
                    notificationMediaManager.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
                    }
                    notificationMediaManager.mMediaController = null;
                    ArrayList arrayList = new ArrayList(notificationMediaManager.mMediaListeners);
                    Flags.FEATURE_FLAGS.getClass();
                    notificationMediaManager.updateMediaMetaData(arrayList);
                }
                notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                notificationMediaManager.getClass();
                if (notificationEntry.mKey.equals(notificationMediaManager.mMediaNotificationKey)) {
                    Flags.FEATURE_FLAGS.getClass();
                    notificationMediaManager.mMediaNotificationKey = null;
                    notificationMediaManager.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
                    }
                    notificationMediaManager.mMediaController = null;
                    ArrayList arrayList = new ArrayList(notificationMediaManager.mMediaListeners);
                    Flags.FEATURE_FLAGS.getClass();
                    notificationMediaManager.updateMediaMetaData(arrayList);
                }
                notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                NotificationMediaManager.this.mMediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
            }
        });
        mediaDataManager.addListener(new AnonymousClass3());
        dumpManager.registerDumpable(this);
    }

    public static boolean isPlayingState(int i) {
        return (PAUSED_MEDIA_STATES.contains(Integer.valueOf(i)) || CONNECTING_MEDIA_STATES.contains(Integer.valueOf(i))) ? false : true;
    }

    public final void clearCurrentMediaNotification() {
        Flags.FEATURE_FLAGS.getClass();
        this.mMediaNotificationKey = null;
        this.mMediaMetadata = null;
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(this.mMediaListener);
        }
        this.mMediaController = null;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("    mMediaNotificationKey=");
        printWriter.println(this.mMediaNotificationKey);
        printWriter.print("    mMediaController=");
        printWriter.print(this.mMediaController);
        if (this.mMediaController != null) {
            printWriter.print(" state=" + this.mMediaController.getPlaybackState());
        }
        printWriter.println();
        printWriter.print("    mMediaMetadata=");
        printWriter.print(this.mMediaMetadata);
        if (this.mMediaMetadata != null) {
            printWriter.print(" title=" + ((Object) this.mMediaMetadata.getText("android.media.metadata.TITLE")));
        }
        printWriter.println();
    }

    public final void findAndUpdateMediaNotifications() {
        boolean z;
        NotificationEntry notificationEntry;
        MediaController mediaController;
        MediaSession.Token token;
        Collection allNotifs = this.mNotifPipeline.getAllNotifs();
        Flags.FEATURE_FLAGS.getClass();
        Iterator it = allNotifs.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                notificationEntry = null;
                mediaController = null;
                break;
            }
            notificationEntry = (NotificationEntry) it.next();
            if (notificationEntry.mSbn.getNotification().isMediaNotification() && (token = (MediaSession.Token) notificationEntry.mSbn.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class)) != null) {
                mediaController = new MediaController(this.mContext, token);
                PlaybackState playbackState = mediaController.getPlaybackState();
                if (3 == (playbackState != null ? playbackState.getState() : 0)) {
                    break;
                }
            }
        }
        StatusBarNotification statusBarNotification = notificationEntry != null ? notificationEntry.mSbn : null;
        if (mediaController != null) {
            MediaController mediaController2 = this.mMediaController;
            if (mediaController2 == mediaController) {
                z = true;
            } else if (mediaController2 != null) {
                z = mediaController2.controlsSameSession(mediaController);
            }
            if (!z) {
                this.mMediaMetadata = null;
                MediaController mediaController3 = this.mMediaController;
                if (mediaController3 != null) {
                    mediaController3.unregisterCallback(this.mMediaListener);
                }
                this.mMediaController = mediaController;
                mediaController.registerCallback(this.mMediaListener, this.mHandler);
                this.mMediaMetadata = this.mMediaController.getMetadata();
            }
        }
        if (statusBarNotification != null && !statusBarNotification.getKey().equals(this.mMediaNotificationKey)) {
            this.mMediaNotificationKey = statusBarNotification.getKey();
        }
        ArrayList arrayList = new ArrayList(this.mMediaListeners);
        Flags.FEATURE_FLAGS.getClass();
        updateMediaMetaData(arrayList);
    }

    public final void updateMediaMetaData(MediaListener mediaListener) {
        PlaybackState playbackState;
        MediaMetadata mediaMetadata = this.mMediaMetadata;
        MediaController mediaController = this.mMediaController;
        ((KeyguardSliceProvider) mediaListener).onPrimaryMetadataOrStateChanged(mediaMetadata, (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) ? 0 : playbackState.getState());
    }

    public final void updateMediaMetaData(List list) {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        int i = 0;
        int state = (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) ? 0 : playbackState.getState();
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((KeyguardSliceProvider) ((MediaListener) arrayList.get(i))).onPrimaryMetadataOrStateChanged(this.mMediaMetadata, state);
            i++;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.NotificationMediaManager$3, reason: invalid class name */
    public final class AnonymousClass3 implements MediaDataManager.Listener {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(final String str, boolean z) {
            Flags.FEATURE_FLAGS.getClass();
            if (z) {
                NotificationMediaManager.this.mNotifPipeline.getAllNotifs().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.NotificationMediaManager$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.equals(((NotificationEntry) obj).mKey, str);
                    }
                }).findAny().ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.NotificationMediaManager$3$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NotificationEntry notificationEntry = (NotificationEntry) obj;
                        NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                        notificationMediaManager.mNotifCollection.dismissNotification(notificationEntry, new DismissedByUserStats(3, 1, ((NotificationVisibilityProviderImpl) notificationMediaManager.mVisibilityProvider).obtain(notificationEntry)));
                    }
                });
            } else {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Not dismissing ", str, " because it was removed by the system", "NotificationMediaManager");
            }
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        }
    }
}
