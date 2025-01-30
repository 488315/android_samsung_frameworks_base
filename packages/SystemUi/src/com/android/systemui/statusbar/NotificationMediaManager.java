package com.android.systemui.statusbar;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.widget.ImageView;
import com.android.systemui.Dumpable;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMediaManager implements Dumpable {
    public static final HashSet CONNECTING_MEDIA_STATES;
    public static final HashSet PAUSED_MEDIA_STATES;
    public BackDropView mBackdrop;
    public ImageView mBackdropBack;
    public ImageView mBackdropFront;
    public BiometricUnlockController mBiometricUnlockController;
    public final Context mContext;
    boolean mIsLockscreenLiveWallpaperEnabled;
    public LockscreenWallpaper mLockscreenWallpaper;
    public final MediaArtworkProcessor mMediaArtworkProcessor;
    public MediaController mMediaController;
    public final MediaDataManager mMediaDataManager;
    public final C25861 mMediaListener;
    public final ArrayList mMediaListeners;
    public MediaMetadata mMediaMetadata;
    public String mMediaNotificationKey;
    public final NotifCollection mNotifCollection;
    public final NotifPipeline mNotifPipeline;
    public NotificationPresenter mPresenter;
    public final StatusBarStateController mStatusBarStateController;
    public final Point mTmpDisplaySize;
    public final NotificationVisibilityProvider mVisibilityProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.statusbar.NotificationMediaManager$1] */
    public NotificationMediaManager(Context context, Lazy lazy, Lazy lazy2, NotificationVisibilityProvider notificationVisibilityProvider, MediaArtworkProcessor mediaArtworkProcessor, KeyguardBypassController keyguardBypassController, NotifPipeline notifPipeline, NotifCollection notifCollection, DelayableExecutor delayableExecutor, MediaDataManager mediaDataManager, StatusBarStateController statusBarStateController, SysuiColorExtractor sysuiColorExtractor, KeyguardStateController keyguardStateController, DumpManager dumpManager, WallpaperManager wallpaperManager, DisplayManager displayManager) {
        new ArraySet();
        this.mTmpDisplaySize = new Point();
        this.mMediaListener = new MediaController.Callback() { // from class: com.android.systemui.statusbar.NotificationMediaManager.1
            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                super.onMetadataChanged(mediaMetadata);
                NotificationMediaManager.this.mMediaArtworkProcessor.getClass();
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                notificationMediaManager.mMediaMetadata = mediaMetadata;
                notificationMediaManager.dispatchUpdateMediaMetaData(true);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                super.onPlaybackStateChanged(playbackState);
                if (playbackState != null) {
                    NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                    int state = playbackState.getState();
                    HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                    notificationMediaManager.getClass();
                    if (!((state == 1 || state == 7 || state == 0) ? false : true)) {
                        NotificationMediaManager notificationMediaManager2 = NotificationMediaManager.this;
                        notificationMediaManager2.mMediaNotificationKey = null;
                        notificationMediaManager2.mMediaArtworkProcessor.getClass();
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
        new Runnable() { // from class: com.android.systemui.statusbar.NotificationMediaManager.4
            @Override // java.lang.Runnable
            public final void run() {
                NotificationMediaManager.this.mBackdropFront.setVisibility(4);
                NotificationMediaManager.this.mBackdropFront.animate().cancel();
                NotificationMediaManager.this.mBackdropFront.setImageDrawable(null);
            }
        };
        this.mContext = context;
        this.mMediaArtworkProcessor = mediaArtworkProcessor;
        this.mMediaListeners = new ArrayList();
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mMediaDataManager = mediaDataManager;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        this.mStatusBarStateController = statusBarStateController;
        this.mIsLockscreenLiveWallpaperEnabled = wallpaperManager.isLockscreenLiveWallpaperEnabled();
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
                    notificationMediaManager.mMediaNotificationKey = null;
                    notificationMediaManager.mMediaArtworkProcessor.getClass();
                    notificationMediaManager.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
                    }
                    notificationMediaManager.mMediaController = null;
                    notificationMediaManager.dispatchUpdateMediaMetaData(true);
                }
                notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                HashSet hashSet = NotificationMediaManager.PAUSED_MEDIA_STATES;
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                notificationMediaManager.getClass();
                if (notificationEntry.mKey.equals(notificationMediaManager.mMediaNotificationKey)) {
                    notificationMediaManager.mMediaNotificationKey = null;
                    notificationMediaManager.mMediaArtworkProcessor.getClass();
                    notificationMediaManager.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
                    }
                    notificationMediaManager.mMediaController = null;
                    notificationMediaManager.dispatchUpdateMediaMetaData(true);
                }
                notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                NotificationMediaManager.this.mMediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
            }
        });
        mediaDataManager.mediaDataFilter._listeners.add(new C25883());
        dumpManager.registerDumpable(this);
    }

    public static boolean isPlayingState(int i) {
        return (PAUSED_MEDIA_STATES.contains(Integer.valueOf(i)) || CONNECTING_MEDIA_STATES.contains(Integer.valueOf(i))) ? false : true;
    }

    public final void dispatchUpdateMediaMetaData(boolean z) {
        PlaybackState playbackState;
        NotificationPresenter notificationPresenter = this.mPresenter;
        if (notificationPresenter != null) {
            ((StatusBarNotificationPresenter) notificationPresenter).mMediaManager.updateMediaMetaData(z, true);
        }
        MediaController mediaController = this.mMediaController;
        int state = (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) ? 0 : playbackState.getState();
        ArrayList arrayList = new ArrayList(this.mMediaListeners);
        for (int i = 0; i < arrayList.size(); i++) {
            ((KeyguardSliceProvider) ((MediaListener) arrayList.get(i))).onPrimaryMetadataOrStateChanged(this.mMediaMetadata, state);
        }
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
        Iterator it = this.mNotifPipeline.getAllNotifs().iterator();
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
        if (mediaController != null) {
            MediaController mediaController2 = this.mMediaController;
            if (!(mediaController2 == mediaController ? true : mediaController2 == null ? false : mediaController2.controlsSameSession(mediaController))) {
                this.mMediaArtworkProcessor.getClass();
                this.mMediaMetadata = null;
                MediaController mediaController3 = this.mMediaController;
                C25861 c25861 = this.mMediaListener;
                if (mediaController3 != null) {
                    mediaController3.unregisterCallback(c25861);
                }
                this.mMediaController = mediaController;
                mediaController.registerCallback(c25861);
                this.mMediaMetadata = this.mMediaController.getMetadata();
                z = true;
            }
        }
        if (notificationEntry != null && !notificationEntry.mSbn.getKey().equals(this.mMediaNotificationKey)) {
            this.mMediaNotificationKey = notificationEntry.mSbn.getKey();
        }
        dispatchUpdateMediaMetaData(z);
    }

    public BackDropView getBackDropView() {
        return this.mBackdrop;
    }

    public final boolean isLockscreenWallpaperOnNotificationShade() {
        LockscreenWallpaper lockscreenWallpaper;
        return (this.mBackdrop == null || (lockscreenWallpaper = this.mLockscreenWallpaper) == null || lockscreenWallpaper.mWallpaperManager.isLockscreenLiveWallpaperEnabled() || (!this.mBackdropFront.isVisibleToUser() && !this.mBackdropBack.isVisibleToUser())) ? false : true;
    }

    public final void updateMediaMetaData(boolean z, boolean z2) {
        if (this.mIsLockscreenLiveWallpaperEnabled) {
            return;
        }
        Trace.beginSection("CentralSurfaces#updateMediaMetaData");
        Trace.endSection();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.NotificationMediaManager$3 */
    public final class C25883 implements MediaDataManager.Listener {
        public C25883() {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(final String str) {
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
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        }
    }
}
