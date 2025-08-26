package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public final class LegacyMediaDataFilterImpl implements MediaDataManager.Listener {
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public LegacyMediaDataManagerImpl mediaDataManager;
    public final SystemClock systemClock;
    public final LegacyMediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;
    public final Set _listeners = new LinkedHashSet();
    public final LinkedHashMap allEntries = new LinkedHashMap();
    public final LinkedHashMap userEntries = new LinkedHashMap();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public LegacyMediaDataFilterImpl(UserTracker userTracker, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onProfilesChanged(List list) {
                this.this$0.handleProfileChanged$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                this.this$0.handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
        };
        this.userTrackerCallback = r2;
        ((UserTrackerImpl) userTracker).addCallback(r2, executor);
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
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str = (String) obj;
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Removing ", str, " after user change", "MediaDataFilter");
            for (MediaDataManager.Listener listener : set) {
                str.getClass();
                listener.onMediaDataRemoved(str, false);
            }
        }
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str2 = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Re-adding ", str2, " after user change", "MediaDataFilter");
                this.userEntries.put(str2, mediaData);
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str2, null, mediaData, false, 56);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        if (str2 != null && !str2.equals(str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        int i = mediaData.userId;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        if (notificationLockscreenUserManagerImpl.isCurrentProfile(i) && notificationLockscreenUserManagerImpl.isProfileAvailable(mediaData.userId)) {
            if (str2 != null && !str2.equals(str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 56);
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
}
