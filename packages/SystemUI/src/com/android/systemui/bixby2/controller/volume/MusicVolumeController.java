package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class MusicVolumeController extends VolumeType {
    public static final int $stable = 8;
    private final Lazy mediaSessionManager$delegate;
    private MediaController.PlaybackInfo playbackInfo;
    private MediaController remoteController;
    private final String streamTypeToString = "Media";

    public MusicVolumeController(final Context context) {
        this.mediaSessionManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bixby2.controller.volume.MusicVolumeController$mediaSessionManager$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MediaSessionManager invoke() {
                return (MediaSessionManager) context.getSystemService("media_session");
            }
        });
    }

    private final List<MediaController> getActiveSessions() {
        return getMediaSessionManager().getActiveSessions(null);
    }

    private final MediaSessionManager getMediaSessionManager() {
        return (MediaSessionManager) this.mediaSessionManager$delegate.getValue();
    }

    private final boolean isRemotePlayerActive() {
        Object obj;
        Iterator<T> it = getActiveSessions().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            MediaController.PlaybackInfo playbackInfo = ((MediaController) obj).getPlaybackInfo();
            if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                break;
            }
        }
        MediaController mediaController = (MediaController) obj;
        if (mediaController == null) {
            return false;
        }
        this.remoteController = mediaController;
        this.playbackInfo = mediaController.getPlaybackInfo();
        mediaController.getPackageName();
        return true;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getMaxVolume() {
        if (!isRemotePlayerActive()) {
            return super.getMaxVolume();
        }
        MediaController.PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo != null) {
            return playbackInfo.getMaxVolume();
        }
        return -1;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getMinVolume() {
        if (isRemotePlayerActive()) {
            return 0;
        }
        return super.getMinVolume();
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 3;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getVolume() {
        if (!isRemotePlayerActive()) {
            return super.getVolume();
        }
        MediaController.PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo != null) {
            return playbackInfo.getCurrentVolume();
        }
        return -1;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public void setStreamVolume(int i, int i2) {
        MediaController mediaController;
        if (!isRemotePlayerActive() || (mediaController = this.remoteController) == null) {
            super.setStreamVolume(i, i2);
        } else {
            mediaController.getPackageName();
            mediaController.setVolumeTo(i, i2);
        }
    }
}
