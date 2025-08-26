package com.android.systemui.media.controls.domain.pipeline;

import android.app.StatusBarManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacyMediaDataManagerImpl f$0;

    public /* synthetic */ LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = legacyMediaDataManagerImpl;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        final LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.f$0;
        final String str = (String) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                int i = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
                legacyMediaDataManagerImpl.setInactive(str, zBooleanValue, false);
                break;
            default:
                final PlaybackState playbackState = (PlaybackState) obj2;
                final MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.get(str);
                if (mediaData != null) {
                    legacyMediaDataManagerImpl.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$updateState$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaData mediaData2 = mediaData;
                            MediaSession.Token token = mediaData2.token;
                            if (token == null) {
                                Log.d("MediaDataManager", "State updated, but token was null");
                                return;
                            }
                            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = legacyMediaDataManagerImpl;
                            MediaController mediaControllerCreate = legacyMediaDataManagerImpl2.mediaControllerFactory.create(token);
                            UserHandle userHandle = new UserHandle(mediaData.userId);
                            legacyMediaDataManagerImpl2.mediaFlags.getClass();
                            String str2 = mediaData2.packageName;
                            MediaButton mediaButtonCreateActionsFromState = !StatusBarManager.useMediaSessionActionsForApp(str2, userHandle) ? null : MediaActionsKt.createActionsFromState(legacyMediaDataManagerImpl2.context, str2, mediaControllerCreate);
                            final MediaData mediaDataCopy$default = mediaButtonCreateActionsFromState != null ? MediaData.copy$default(mediaData, null, null, mediaButtonCreateActionsFromState, null, null, null, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), 0L, 0L, null, 0, 267386367) : MediaData.copy$default(mediaData, null, null, null, null, null, null, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), 0L, 0L, null, 0, 267386879);
                            Log.d("MediaDataManager", "State updated outside of notification");
                            final LegacyMediaDataManagerImpl legacyMediaDataManagerImpl3 = legacyMediaDataManagerImpl;
                            DelayableExecutor delayableExecutor = legacyMediaDataManagerImpl3.foregroundExecutor;
                            final String str3 = str;
                            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$updateState$1$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl4 = legacyMediaDataManagerImpl3;
                                    String str4 = str3;
                                    legacyMediaDataManagerImpl4.onMediaDataLoaded(str4, str4, mediaDataCopy$default);
                                }
                            });
                        }
                    });
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
