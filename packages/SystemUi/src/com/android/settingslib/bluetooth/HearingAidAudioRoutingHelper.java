package com.android.settingslib.bluetooth;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HearingAidAudioRoutingHelper {
    public final AudioManager mAudioManager;

    public HearingAidAudioRoutingHelper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    public List<AudioProductStrategy> getAudioProductStrategies() {
        return AudioManager.getAudioProductStrategies();
    }

    public final boolean removePreferredDeviceForStrategies(List list) {
        Iterator it = list.iterator();
        boolean z = true;
        while (it.hasNext()) {
            z &= this.mAudioManager.removePreferredDeviceForStrategy((AudioProductStrategy) it.next());
        }
        return z;
    }
}
