package com.android.systemui.media.controls.domain.pipeline;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ LegacyMediaDataManagerImpl f$0;

    public /* synthetic */ LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl) {
        this.f$0 = legacyMediaDataManagerImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String str = (String) obj;
        int i = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.f$0;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("session destroyed for ", str, "MediaDataManager");
        MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.remove(str);
        if (mediaData != null) {
            MediaData copy$default = MediaData.copy$default(mediaData, null, null, null, null, null, null, false, null, false, null, 0L, 0L, null, 0, 268433407);
            boolean z = copy$default.token != null;
            MediaButton mediaButton = copy$default.semanticActions;
            if (z && mediaButton != null) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Notification removed but using session actions ", str, "MediaDataManager");
                legacyMediaDataManagerImpl.mediaEntries.put(str, copy$default);
                legacyMediaDataManagerImpl.notifyMediaDataLoaded$1(str, str, copy$default);
            } else if (mediaButton == null) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Session destroyed but using notification actions ", str, "MediaDataManager");
                legacyMediaDataManagerImpl.mediaEntries.put(str, copy$default);
                legacyMediaDataManagerImpl.notifyMediaDataLoaded$1(str, str, copy$default);
            } else {
                boolean z2 = copy$default.active;
                MediaUiEventLogger mediaUiEventLogger = legacyMediaDataManagerImpl.logger;
                String str2 = copy$default.packageName;
                int i2 = copy$default.appUid;
                if (!z2 || legacyMediaDataManagerImpl.isAbleToResume$1(copy$default)) {
                    MediaFlags mediaFlags = legacyMediaDataManagerImpl.mediaFlags;
                    mediaFlags.getClass();
                    Flags.INSTANCE.getClass();
                    mediaFlags.featureFlags.getClass();
                    if (legacyMediaDataManagerImpl.isAbleToResume$1(copy$default)) {
                        Log.d("MediaDataManager", "Notification (false) and/or session (" + z + ") gone for inactive player " + str);
                        legacyMediaDataManagerImpl.convertToResumePlayer$1(copy$default, str);
                    } else {
                        Log.d("MediaDataManager", "Removing player " + str);
                        LegacyMediaDataManagerImpl.notifyMediaDataRemoved$default(legacyMediaDataManagerImpl, str);
                        mediaUiEventLogger.logMediaRemoved(i2, str2, copy$default.instanceId);
                    }
                } else {
                    Log.d("MediaDataManager", "Removing still-active player " + str);
                    LegacyMediaDataManagerImpl.notifyMediaDataRemoved$default(legacyMediaDataManagerImpl, str);
                    mediaUiEventLogger.logMediaRemoved(i2, str2, copy$default.instanceId);
                }
            }
        }
        return Unit.INSTANCE;
    }
}
