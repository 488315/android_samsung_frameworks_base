package com.android.systemui.media.controls.pipeline;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaDataFilter implements MediaDataManager.Listener {
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final MediaUiEventLogger logger;
    public final MediaFlags mediaFlags;
    public String reactivatedKey;
    public final SystemClock systemClock;
    public final MediaDataFilter$userTrackerCallback$1 userTrackerCallback;
    public final Set _listeners = new LinkedHashSet();
    public final LinkedHashMap allEntries = new LinkedHashMap();
    public final LinkedHashMap userEntries = new LinkedHashMap();
    public SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.controls.pipeline.MediaDataFilter$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public MediaDataFilter(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.pipeline.MediaDataFilter$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MediaDataFilter.this.m158xa9aae04c(i);
            }
        };
        this.userTrackerCallback = r1;
        ((UserTrackerImpl) userTracker).addCallback(r1, executor);
    }

    /* renamed from: getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Set m157xef59304f() {
        return CollectionsKt___CollectionsKt.toSet(this._listeners);
    }

    /* renamed from: handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m158xa9aae04c(int i) {
        Set m157xef59304f = m157xef59304f();
        LinkedHashMap linkedHashMap = this.userEntries;
        ArrayList arrayList = new ArrayList(linkedHashMap.keySet());
        linkedHashMap.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Log.d("MediaDataFilter", "Removing " + str + " after user change");
            Iterator it2 = m157xef59304f.iterator();
            while (it2.hasNext()) {
                ((MediaDataManager.Listener) it2.next()).onMediaDataRemoved(str);
            }
        }
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str2 = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
                AbstractC0689x6838b71d.m68m("Re-adding ", str2, " after user change", "MediaDataFilter");
                linkedHashMap.put(str2, mediaData);
                Iterator it3 = m157xef59304f.iterator();
                while (it3.hasNext()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default((MediaDataManager.Listener) it3.next(), str2, null, mediaData, false, 0, false, 56);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        LinkedHashMap linkedHashMap = this.allEntries;
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            linkedHashMap.remove(str2);
        }
        linkedHashMap.put(str, mediaData);
        if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
            LinkedHashMap linkedHashMap2 = this.userEntries;
            if (str2 != null && !Intrinsics.areEqual(str2, str)) {
                linkedHashMap2.remove(str2);
            }
            linkedHashMap2.put(str, mediaData);
            Iterator it = m157xef59304f().iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str) {
        this.allEntries.remove(str);
        if (((MediaData) this.userEntries.remove(str)) != null) {
            Iterator it = m157xef59304f().iterator();
            while (it.hasNext()) {
                ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c3, code lost:
    
        if (r2 != false) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0126 A[LOOP:1: B:45:0x0120->B:47:0x0126, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x008f  */
    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        long j;
        long j2;
        SmartspaceAction smartspaceAction;
        MediaUiEventLogger mediaUiEventLogger;
        boolean z;
        Bundle extras;
        Bundle extras2;
        long j3;
        boolean z2 = smartspaceMediaData.isActive;
        if (!z2) {
            this.mediaFlags.isPersistentSsCardEnabled();
            Log.d("MediaDataFilter", "Inactive recommendation data. Skip triggering.");
            return;
        }
        this.smartspaceMediaData = smartspaceMediaData;
        LinkedHashMap linkedHashMap = this.userEntries;
        TreeMap treeMap = new TreeMap(new Comparator() { // from class: com.android.systemui.media.controls.pipeline.MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaData mediaData = (MediaData) MediaDataFilter.this.userEntries.get((String) obj);
                Comparable valueOf = mediaData != null ? Long.valueOf(mediaData.lastActive) : -1;
                MediaData mediaData2 = (MediaData) MediaDataFilter.this.userEntries.get((String) obj2);
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, mediaData2 != null ? Long.valueOf(mediaData2.lastActive) : -1);
            }
        });
        treeMap.putAll(linkedHashMap);
        boolean isEmpty = treeMap.isEmpty();
        SystemClock systemClock = this.systemClock;
        if (!isEmpty) {
            ((SystemClockImpl) systemClock).getClass();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            MediaData mediaData = (MediaData) treeMap.get((String) treeMap.lastKey());
            if (mediaData != null) {
                j = elapsedRealtime - mediaData.lastActive;
                j2 = MediaDataFilterKt.SMARTSPACE_MAX_AGE;
                smartspaceAction = smartspaceMediaData.cardAction;
                if (smartspaceAction != null && (extras2 = smartspaceAction.getExtras()) != null) {
                    j3 = extras2.getLong("resumable_media_max_age_seconds", 0L);
                    if (j3 > 0) {
                        j2 = TimeUnit.SECONDS.toMillis(j3);
                    }
                }
                boolean z3 = true;
                if (!(smartspaceAction == null && (extras = smartspaceAction.getExtras()) != null && extras.containsKey("SHOULD_TRIGGER_RESUME")) ? smartspaceAction.getExtras().getBoolean("SHOULD_TRIGGER_RESUME", true) : true) {
                    if (!linkedHashMap.isEmpty()) {
                        Iterator it = linkedHashMap.entrySet().iterator();
                        while (it.hasNext()) {
                            if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                        if (!linkedHashMap.isEmpty()) {
                        }
                    }
                }
                z3 = false;
                mediaUiEventLogger = this.logger;
                if (j < j2 && z3) {
                    String str2 = (String) treeMap.lastKey();
                    AbstractC0689x6838b71d.m68m("reactivating ", str2, " instead of smartspace", "MediaDataFilter");
                    this.reactivatedKey = str2;
                    Object obj = treeMap.get(str2);
                    Intrinsics.checkNotNull(obj);
                    MediaData copy$default = MediaData.copy$default((MediaData) obj, null, null, null, null, null, null, true, null, false, false, null, false, null, 0, 268419071);
                    mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_ACTIVATED, copy$default.appUid, copy$default.packageName, copy$default.instanceId);
                    for (MediaDataManager.Listener listener : m157xef59304f()) {
                        ((SystemClockImpl) systemClock).getClass();
                        MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, str2, str2, copy$default, false, (int) (System.currentTimeMillis() - smartspaceMediaData.headphoneConnectionTimeMillis), true, 8);
                    }
                }
                if (smartspaceMediaData.isValid()) {
                    Log.d("MediaDataFilter", "Invalid recommendation data. Skip showing the rec card");
                    return;
                }
                SmartspaceMediaData smartspaceMediaData2 = this.smartspaceMediaData;
                String str3 = smartspaceMediaData2.packageName;
                mediaUiEventLogger.getClass();
                mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_ADDED, 0, str3, smartspaceMediaData2.instanceId);
                Iterator it2 = m157xef59304f().iterator();
                while (it2.hasNext()) {
                    ((MediaDataManager.Listener) it2.next()).onSmartspaceMediaDataLoaded(str, smartspaceMediaData);
                }
                return;
            }
        }
        j = Long.MAX_VALUE;
        j2 = MediaDataFilterKt.SMARTSPACE_MAX_AGE;
        smartspaceAction = smartspaceMediaData.cardAction;
        if (smartspaceAction != null) {
            j3 = extras2.getLong("resumable_media_max_age_seconds", 0L);
            if (j3 > 0) {
            }
        }
        boolean z32 = true;
        if (!(smartspaceAction == null && (extras = smartspaceAction.getExtras()) != null && extras.containsKey("SHOULD_TRIGGER_RESUME")) ? smartspaceAction.getExtras().getBoolean("SHOULD_TRIGGER_RESUME", true) : true) {
        }
        z32 = false;
        mediaUiEventLogger = this.logger;
        if (j < j2) {
            String str22 = (String) treeMap.lastKey();
            AbstractC0689x6838b71d.m68m("reactivating ", str22, " instead of smartspace", "MediaDataFilter");
            this.reactivatedKey = str22;
            Object obj2 = treeMap.get(str22);
            Intrinsics.checkNotNull(obj2);
            MediaData copy$default2 = MediaData.copy$default((MediaData) obj2, null, null, null, null, null, null, true, null, false, false, null, false, null, 0, 268419071);
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_ACTIVATED, copy$default2.appUid, copy$default2.packageName, copy$default2.instanceId);
            while (r6.hasNext()) {
            }
        }
        if (smartspaceMediaData.isValid()) {
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        String str2 = this.reactivatedKey;
        if (str2 != null) {
            this.reactivatedKey = null;
            Log.d("MediaDataFilter", "expiring reactivated key ".concat(str2));
            MediaData mediaData = (MediaData) this.userEntries.get(str2);
            if (mediaData != null) {
                Iterator it = m157xef59304f().iterator();
                while (it.hasNext()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str2, str2, mediaData, z, 0, false, 48);
                }
            }
        }
        SmartspaceMediaData smartspaceMediaData = this.smartspaceMediaData;
        if (smartspaceMediaData.isActive) {
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
        }
        Iterator it2 = m157xef59304f().iterator();
        while (it2.hasNext()) {
            ((MediaDataManager.Listener) it2.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }
}
