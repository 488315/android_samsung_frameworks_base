package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class MediaDataFilterImpl implements MediaDataManager.Listener {
    public final Set _listeners = new LinkedHashSet();
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final MediaFilterRepository mediaFilterRepository;
    public final MediaLogger mediaLogger;
    public final SystemClock systemClock;
    public final MediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public MediaDataFilterImpl(UserTracker userTracker, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaFilterRepository mediaFilterRepository, MediaLogger mediaLogger) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.mediaFilterRepository = mediaFilterRepository;
        this.mediaLogger = mediaLogger;
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl$userTrackerCallback$1
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
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        for (Map.Entry entry : ((Map) mediaFilterRepository.allUserEntries.$$delegate_0.getValue()).entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (!((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isProfileAvailable(mediaData.userId)) {
                InstanceId instanceId = mediaData.instanceId;
                StateFlowImpl stateFlowImpl = mediaFilterRepository._selectedUserEntries;
                LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
                if (linkedHashMap.remove(instanceId, mediaData)) {
                    stateFlowImpl.updateState(null, linkedHashMap);
                }
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(mediaData.instanceId), true);
                this.mediaLogger.logMediaRemoved(mediaData.instanceId, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Removing ", str, " after profile change"));
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
                }
            }
        }
    }

    public final void handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        ReadonlyStateFlow readonlyStateFlow;
        MediaLogger mediaLogger;
        Set set = CollectionsKt___CollectionsKt.toSet(this._listeners);
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        ArrayList arrayList = new ArrayList(((Map) mediaFilterRepository.selectedUserEntries.$$delegate_0.getValue()).keySet());
        mediaFilterRepository._selectedUserEntries.updateState(null, new LinkedHashMap());
        int size = arrayList.size();
        int i = 0;
        while (true) {
            readonlyStateFlow = mediaFilterRepository.allUserEntries;
            mediaLogger = this.mediaLogger;
            if (i >= size) {
                break;
            }
            Object obj = arrayList.get(i);
            i++;
            InstanceId instanceId = (InstanceId) obj;
            mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId), true);
            mediaLogger.logMediaRemoved(instanceId, "Removing media after user change");
            Map map = (Map) readonlyStateFlow.$$delegate_0.getValue();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry entry : map.entrySet()) {
                if (Intrinsics.areEqual(((MediaData) entry.getValue()).instanceId, instanceId)) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            String str = !linkedHashMap.isEmpty() ? (String) CollectionsKt___CollectionsKt.first(linkedHashMap.keySet()) : null;
            if (str != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
                }
            }
        }
        for (Map.Entry entry2 : ((Map) readonlyStateFlow.$$delegate_0.getValue()).entrySet()) {
            String str2 = (String) entry2.getKey();
            MediaData mediaData = (MediaData) entry2.getValue();
            if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.instanceId), mediaFilterRepository.addSelectedUserMediaEntry(mediaData));
                mediaLogger.logMediaLoaded(mediaData.instanceId, mediaData.active, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Re-adding ", str2, " after user change"));
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it2.next(), str2, null, mediaData, false, 56);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        if (str2 != null && !str2.equals(str)) {
            mediaFilterRepository.getClass();
            StateFlowImpl stateFlowImpl = mediaFilterRepository._allUserEntries;
            LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
            stateFlowImpl.updateState(null, linkedHashMap);
        }
        mediaFilterRepository.getClass();
        StateFlowImpl stateFlowImpl2 = mediaFilterRepository._allUserEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap((Map) stateFlowImpl2.getValue());
        linkedHashMap2.put(str, mediaData);
        stateFlowImpl2.updateState(null, linkedHashMap2);
        int i = mediaData.userId;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        if (notificationLockscreenUserManagerImpl.isCurrentProfile(i) && notificationLockscreenUserManagerImpl.isProfileAvailable(mediaData.userId)) {
            boolean zAddSelectedUserMediaEntry = mediaFilterRepository.addSelectedUserMediaEntry(mediaData);
            this.mediaLogger.logMediaLoaded(mediaData.instanceId, mediaData.active, "loading media");
            mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.instanceId), zAddSelectedUserMediaEntry);
            Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 56);
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        mediaFilterRepository.getClass();
        StateFlowImpl stateFlowImpl = mediaFilterRepository._allUserEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
        MediaData mediaData = (MediaData) linkedHashMap.remove(str);
        stateFlowImpl.updateState(null, linkedHashMap);
        if (mediaData != null) {
            InstanceId instanceId = mediaData.instanceId;
            StateFlowImpl stateFlowImpl2 = mediaFilterRepository._selectedUserEntries;
            LinkedHashMap linkedHashMap2 = new LinkedHashMap((Map) stateFlowImpl2.getValue());
            MediaData mediaData2 = (MediaData) linkedHashMap2.remove(instanceId);
            stateFlowImpl2.updateState(null, linkedHashMap2);
            if (mediaData2 != null) {
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId), true);
                this.mediaLogger.logMediaRemoved(instanceId, "removing media card");
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
                }
            }
        }
    }
}
