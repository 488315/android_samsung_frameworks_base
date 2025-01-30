package com.samsung.android.server.audio;

import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.util.ArraySet;
import com.android.server.audio.AudioService;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SemAudioServiceInternal {
    public WeakReference mAudioService;

    public SemAudioServiceInternal(AudioService audioService) {
        this.mAudioService = new WeakReference(audioService);
    }

    public List getUidListUsingAudio() {
        int clientUid;
        AudioService audioService = (AudioService) this.mAudioService.get();
        if (audioService == null) {
            return Collections.emptyList();
        }
        ArraySet arraySet = new ArraySet();
        for (AudioPlaybackConfiguration audioPlaybackConfiguration : audioService.getActivePlaybackConfigurationsInternal()) {
            if (audioPlaybackConfiguration != null && (clientUid = audioPlaybackConfiguration.getClientUid()) > 10000 && (audioPlaybackConfiguration.getPlayerType() == 3 || audioPlaybackConfiguration.getPlayerState() == 2)) {
                arraySet.add(Integer.valueOf(clientUid));
            }
        }
        Iterator it = audioService.getActiveRecordingConfigurationsInternal().iterator();
        while (it.hasNext()) {
            int clientUid2 = ((AudioRecordingConfiguration) it.next()).getClientUid();
            if (clientUid2 > 10000) {
                arraySet.add(Integer.valueOf(clientUid2));
            }
        }
        Iterator it2 = arraySet.iterator();
        while (it2.hasNext()) {
            if (!audioService.isUsingAudio(((Integer) it2.next()).intValue() % 100000)) {
                it2.remove();
            }
        }
        return new ArrayList(arraySet);
    }

    public int getAppDevice(int i) {
        AudioService audioService = (AudioService) this.mAudioService.get();
        if (audioService == null) {
            return 0;
        }
        return audioService.getAppDevice(i);
    }
}
