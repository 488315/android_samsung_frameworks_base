package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.util.Log;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.util.time.SystemClock;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class LegacyMediaDataFilterImpl implements MediaDataManager.Listener {
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final MediaUiEventLogger logger;
    public MediaDataManager mediaDataManager;
    public final MediaFlags mediaFlags;
    public String reactivatedKey;
    public final SystemClock systemClock;
    public final LegacyMediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;
    public final Set _listeners = new LinkedHashSet();
    public final LinkedHashMap allEntries = new LinkedHashMap();
    public final LinkedHashMap userEntries = new LinkedHashMap();
    public SmartspaceMediaData smartspaceMediaData = LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA;

    public LegacyMediaDataFilterImpl(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        this.context = context;
        this.broadcastSender = broadcastSender;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onProfilesChanged(List list) {
                LegacyMediaDataFilterImpl.this.handleProfileChanged$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                LegacyMediaDataFilterImpl.this.handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
        };
        this.userTrackerCallback = r1;
        ((UserTrackerImpl) userTracker).addCallback(r1, executor);
    }

    public final void handleProfileChanged$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (!((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isProfileAvailable(mediaData.userId)) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Removing ", str, " after profile change", "MediaDataFilter");
                this.userEntries.remove(str, mediaData);
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
                }
            }
        }
    }

    public final void handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        Set<MediaDataManager.Listener> set = CollectionsKt___CollectionsKt.toSet(this._listeners);
        ArrayList arrayList = new ArrayList(this.userEntries.keySet());
        this.userEntries.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Removing ", str, " after user change", "MediaDataFilter");
            for (MediaDataManager.Listener listener : set) {
                Intrinsics.checkNotNull(str);
                listener.onMediaDataRemoved(str, false);
            }
        }
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str2 = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Re-adding ", str2, " after user change", "MediaDataFilter");
                this.userEntries.put(str2, mediaData);
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it2.next(), str2, null, mediaData, false, 0, false, 56);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        if (str2 != null && !str2.equals(str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        int i2 = mediaData.userId;
        if (notificationLockscreenUserManagerImpl.isCurrentProfile(i2) && notificationLockscreenUserManagerImpl.isProfileAvailable(i2)) {
            if (str2 != null && !str2.equals(str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        this.allEntries.remove(str);
        if (((MediaData) this.userEntries.remove(str)) != null) {
            Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
            while (it.hasNext()) {
                ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
    
        if (r2 != false) goto L41;
     */
    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSmartspaceMediaDataLoaded(java.lang.String r34, com.android.systemui.media.controls.shared.model.SmartspaceMediaData r35) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl.onSmartspaceMediaDataLoaded(java.lang.String, com.android.systemui.media.controls.shared.model.SmartspaceMediaData):void");
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        String str2 = this.reactivatedKey;
        if (str2 != null) {
            this.reactivatedKey = null;
            Log.d("MediaDataFilter", "expiring reactivated key ".concat(str2));
            MediaData mediaData = (MediaData) this.userEntries.get(str2);
            if (mediaData != null) {
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str2, str2, mediaData, z, 0, false, 48);
                }
            }
        }
        SmartspaceMediaData smartspaceMediaData = this.smartspaceMediaData;
        if (smartspaceMediaData.isActive) {
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
        }
        Iterator it2 = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
        while (it2.hasNext()) {
            ((MediaDataManager.Listener) it2.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }
}
