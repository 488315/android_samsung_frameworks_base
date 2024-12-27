package com.android.server.companion.virtual.audio;

import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioPlaybackDetector extends AudioManager.AudioPlaybackCallback {
    public final AudioManager mAudioManager;
    public VirtualAudioController mAudioPlaybackCallback;

    public AudioPlaybackDetector(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    @Override // android.media.AudioManager.AudioPlaybackCallback
    public final void onPlaybackConfigChanged(List list) {
        ArrayList arrayList;
        super.onPlaybackConfigChanged(list);
        VirtualAudioController virtualAudioController = this.mAudioPlaybackCallback;
        if (virtualAudioController != null) {
            synchronized (virtualAudioController.mLock) {
                try {
                    ArraySet findPlayingAppUids =
                            VirtualAudioController.findPlayingAppUids(
                                    virtualAudioController.mRunningAppUids, list);
                    if (!virtualAudioController.mPlayingAppUids.equals(findPlayingAppUids)) {
                        virtualAudioController.mPlayingAppUids = findPlayingAppUids;
                        virtualAudioController.notifyAppsNeedingAudioRoutingChanged();
                    }
                } finally {
                }
            }
            synchronized (virtualAudioController.mLock) {
                ArraySet arraySet = virtualAudioController.mRunningAppUids;
                arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) it.next();
                    if (arraySet.contains(
                            Integer.valueOf(audioPlaybackConfiguration.getClientUid()))) {
                        arrayList.add(audioPlaybackConfiguration);
                    }
                }
            }
            synchronized (virtualAudioController.mCallbackLock) {
                IAudioConfigChangedCallback iAudioConfigChangedCallback =
                        virtualAudioController.mConfigChangedCallback;
                if (iAudioConfigChangedCallback != null) {
                    try {
                        iAudioConfigChangedCallback.onPlaybackConfigChanged(arrayList);
                    } catch (RemoteException e) {
                        Slog.e(
                                "VirtualAudioController",
                                "RemoteException when calling onPlaybackConfigChanged",
                                e);
                    }
                }
            }
        }
    }
}
