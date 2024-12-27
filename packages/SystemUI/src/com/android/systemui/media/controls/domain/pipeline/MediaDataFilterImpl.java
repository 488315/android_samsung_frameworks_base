package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaLoadingModel;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class MediaDataFilterImpl implements MediaDataManager.Listener {
    public static final Companion Companion = new Companion(null);
    public final Set _listeners = new LinkedHashSet();
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final MediaUiEventLogger logger;
    public final MediaFilterRepository mediaFilterRepository;
    public final MediaFlags mediaFlags;
    public final MediaLoadingLogger mediaLoadingLogger;
    public final SystemClock systemClock;
    public final MediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getSMARTSPACE_MAX_AGE$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    public MediaDataFilterImpl(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags, MediaFilterRepository mediaFilterRepository, MediaLoadingLogger mediaLoadingLogger) {
        this.context = context;
        this.broadcastSender = broadcastSender;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        this.mediaFilterRepository = mediaFilterRepository;
        this.mediaLoadingLogger = mediaLoadingLogger;
        ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onProfilesChanged(List list) {
                MediaDataFilterImpl.this.handleProfileChanged$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MediaDataFilterImpl.this.handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
        };
        this.userTrackerCallback = r1;
        ((UserTrackerImpl) userTracker).addCallback(r1, executor);
    }

    public final String getKey(InstanceId instanceId) {
        Map map = (Map) this.mediaFilterRepository.allUserEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).instanceId, instanceId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (!linkedHashMap.isEmpty()) {
            return (String) CollectionsKt___CollectionsKt.first(linkedHashMap.keySet());
        }
        return null;
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
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(mediaData.instanceId));
                this.mediaLoadingLogger.logMediaRemoved(mediaData.instanceId, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Removing ", str, " after profile change"));
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
                }
            }
        }
    }

    public final void handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        MediaLoadingLogger mediaLoadingLogger;
        Set set = CollectionsKt___CollectionsKt.toSet(this._listeners);
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        ArrayList arrayList = new ArrayList(((Map) mediaFilterRepository.selectedUserEntries.$$delegate_0.getValue()).keySet());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        StateFlowImpl stateFlowImpl = mediaFilterRepository._selectedUserEntries;
        stateFlowImpl.updateState(null, linkedHashMap);
        Iterator it = arrayList.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            mediaLoadingLogger = this.mediaLoadingLogger;
            if (!hasNext) {
                break;
            }
            InstanceId instanceId = (InstanceId) it.next();
            mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId));
            mediaLoadingLogger.logMediaRemoved(instanceId, "Removing media after user change");
            String key = getKey(instanceId);
            if (key != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    ((MediaDataManager.Listener) it2.next()).onMediaDataRemoved(key, false);
                }
            }
        }
        for (Map.Entry entry : ((Map) mediaFilterRepository.allUserEntries.$$delegate_0.getValue()).entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isCurrentProfile(mediaData.userId)) {
                LinkedHashMap linkedHashMap2 = new LinkedHashMap((Map) stateFlowImpl.getValue());
                linkedHashMap2.put(mediaData.instanceId, mediaData);
                stateFlowImpl.updateState(null, linkedHashMap2);
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.instanceId, false, 2, null));
                mediaLoadingLogger.logMediaLoaded(mediaData.instanceId, mediaData.active, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Re-adding ", str, " after user change"));
                Iterator it3 = set.iterator();
                while (it3.hasNext()) {
                    MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it3.next(), str, null, mediaData, false, 0, false, 56);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
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
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        int i2 = mediaData.userId;
        if (notificationLockscreenUserManagerImpl.isCurrentProfile(i2) && notificationLockscreenUserManagerImpl.isProfileAvailable(i2)) {
            StateFlowImpl stateFlowImpl3 = mediaFilterRepository._selectedUserEntries;
            LinkedHashMap linkedHashMap3 = new LinkedHashMap((Map) stateFlowImpl3.getValue());
            linkedHashMap3.put(mediaData.instanceId, mediaData);
            stateFlowImpl3.updateState(null, linkedHashMap3);
            this.mediaLoadingLogger.logMediaLoaded(mediaData.instanceId, mediaData.active, "loading media");
            mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.instanceId, false, 2, null));
            Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
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
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId));
                this.mediaLoadingLogger.logMediaRemoved(instanceId, "removing media card");
                Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0181, code lost:
    
        if (r3 != false) goto L56;
     */
    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSmartspaceMediaDataLoaded(java.lang.String r47, com.android.systemui.media.controls.shared.model.SmartspaceMediaData r48) {
        /*
            Method dump skipped, instructions count: 490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl.onSmartspaceMediaDataLoaded(java.lang.String, com.android.systemui.media.controls.shared.model.SmartspaceMediaData):void");
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaFilterRepository mediaFilterRepository = this.mediaFilterRepository;
        InstanceId instanceId = (InstanceId) mediaFilterRepository.reactivatedId.$$delegate_0.getValue();
        MediaLoadingLogger mediaLoadingLogger = this.mediaLoadingLogger;
        if (instanceId != null) {
            mediaFilterRepository._reactivatedId.setValue(null);
            MediaData mediaData = (MediaData) ((Map) mediaFilterRepository.selectedUserEntries.$$delegate_0.getValue()).get(instanceId);
            if (mediaData != null) {
                mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(instanceId, z));
                mediaLoadingLogger.logMediaLoaded(instanceId, mediaData.active, "expiring reactivated id");
                for (MediaDataManager.Listener listener : CollectionsKt___CollectionsKt.toSet(this._listeners)) {
                    String key = getKey(instanceId);
                    if (key != null) {
                        MediaDataManager.Listener.onMediaDataLoaded$default(listener, key, key, mediaData, z, 0, false, 48);
                    }
                }
            }
        }
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) mediaFilterRepository.smartspaceMediaData.$$delegate_0.getValue();
        if (smartspaceMediaData.isActive) {
            mediaFilterRepository._smartspaceMediaData.updateState(null, SmartspaceMediaData.copy$default(LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB));
        }
        mediaFilterRepository.setRecommendationsLoadingState(new SmartspaceMediaLoadingModel.Removed(str, z));
        mediaLoadingLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLoadingLogger$logRecommendationRemoved$2 mediaLoadingLogger$logRecommendationRemoved$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaLoadingLogger$logRecommendationRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str2 = logMessage.getStr2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("removing recommendation ", str1, ", immediate=", ", reason: ", bool1);
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = mediaLoadingLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLoadingLog", logLevel, mediaLoadingLogger$logRecommendationRemoved$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = "removing recommendations card";
        logBuffer.commit(obtain);
        Iterator it = CollectionsKt___CollectionsKt.toSet(this._listeners).iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }
}
