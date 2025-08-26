package com.android.systemui.media;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
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
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class NotificationMediaManager implements Dumpable {
    public static final HashSet CONNECTING_MEDIA_STATES;
    public static final HashSet PAUSED_MEDIA_STATES;
    public final Executor mBackgroundExecutor;
    public final Context mContext;
    public final Handler mHandler;
    MediaController mMediaController;
    public final MediaDataManager mMediaDataManager;
    final MediaController.Callback mMediaListener = new AnonymousClass1();
    public final ArrayList mMediaListeners = new ArrayList();
    public MediaMetadata mMediaMetadata;
    public String mMediaNotificationKey;
    public final NotifCollection mNotifCollection;
    public final NotifPipeline mNotifPipeline;
    public final NotificationVisibilityProvider mVisibilityProvider;

    /* renamed from: com.android.systemui.media.NotificationMediaManager$1, reason: invalid class name */
    public class AnonymousClass1 extends MediaController.Callback {
        public AnonymousClass1() {
        }

        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            NotificationMediaManager.this.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda0(3, this, mediaMetadata));
            NotificationMediaManager.this.dispatchUpdateMediaMetaData();
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
                    notificationMediaManager2.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda5(notificationMediaManager2));
                }
                NotificationMediaManager.this.findAndUpdateMediaNotifications();
            }
        }
    }

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
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.media.NotificationMediaManager.2
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
                    notificationMediaManager.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda5(notificationMediaManager));
                    notificationMediaManager.dispatchUpdateMediaMetaData();
                }
                notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                notificationMediaManager.getClass();
                if (notificationEntry.mKey.equals(notificationMediaManager.mMediaNotificationKey)) {
                    notificationMediaManager.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda5(notificationMediaManager));
                    notificationMediaManager.dispatchUpdateMediaMetaData();
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

    public final void dispatchUpdateMediaMetaData() {
        this.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda0(2, this, new ArrayList(this.mMediaListeners)));
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
        Collection allNotifs = this.mNotifPipeline.getAllNotifs();
        ArrayList arrayList = new ArrayList();
        Iterator it = allNotifs.iterator();
        while (it.hasNext()) {
            arrayList.add(((NotificationEntry) it.next()).mSbn);
        }
        this.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda0(1, this, arrayList));
        dispatchUpdateMediaMetaData();
    }

    /* renamed from: com.android.systemui.media.NotificationMediaManager$3, reason: invalid class name */
    public class AnonymousClass3 implements MediaDataManager.Listener {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(final String str, boolean z) {
            if (z) {
                NotificationMediaManager.this.mNotifPipeline.getAllNotifs().stream().filter(new Predicate() { // from class: com.android.systemui.media.NotificationMediaManager$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.equals(((NotificationEntry) obj).mKey, str);
                    }
                }).findAny().ifPresent(new Consumer() { // from class: com.android.systemui.media.NotificationMediaManager$3$$ExternalSyntheticLambda1
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
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        }
    }
}
