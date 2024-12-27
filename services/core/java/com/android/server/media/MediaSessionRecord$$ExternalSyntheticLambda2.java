package com.android.server.media;

import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import com.android.server.media.MediaSessionRecord;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaSessionRecord$$ExternalSyntheticLambda2 implements MediaSessionRecord.ControllerCallbackCall {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaSessionRecord$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public /* synthetic */ MediaSessionRecord$$ExternalSyntheticLambda2(Bundle bundle) {
        this.$r8$classId = 2;
        this.f$0 = bundle;
    }

    @Override // com.android.server.media.MediaSessionRecord.ControllerCallbackCall
    public final void performOn(MediaSessionRecord.ISessionControllerCallbackHolder iSessionControllerCallbackHolder) {
        ParceledListSlice parceledListSlice;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                iSessionControllerCallbackHolder.mCallback.onVolumeInfoChanged((MediaController.PlaybackInfo) obj);
                break;
            case 1:
                iSessionControllerCallbackHolder.mCallback.onMetadataChanged((MediaMetadata) obj);
                break;
            case 2:
                iSessionControllerCallbackHolder.mCallback.onExtrasChanged((Bundle) obj);
                break;
            case 3:
                ArrayList arrayList = (ArrayList) obj;
                if (arrayList != null) {
                    parceledListSlice = new ParceledListSlice(arrayList);
                    parceledListSlice.setInlineCountLimit(1);
                } else {
                    parceledListSlice = null;
                }
                iSessionControllerCallbackHolder.mCallback.onQueueChanged(parceledListSlice);
                break;
            case 4:
                iSessionControllerCallbackHolder.mCallback.onPlaybackStateChanged((PlaybackState) obj);
                break;
            default:
                iSessionControllerCallbackHolder.mCallback.onQueueTitleChanged((CharSequence) obj);
                break;
        }
    }
}
