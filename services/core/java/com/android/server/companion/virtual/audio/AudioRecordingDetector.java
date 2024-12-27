package com.android.server.companion.virtual.audio;

import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioRecordingDetector extends AudioManager.AudioRecordingCallback {
    public final AudioManager mAudioManager;
    public VirtualAudioController mAudioRecordingCallback;

    public AudioRecordingDetector(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    @Override // android.media.AudioManager.AudioRecordingCallback
    public final void onRecordingConfigChanged(List list) {
        ArrayList arrayList;
        super.onRecordingConfigChanged(list);
        VirtualAudioController virtualAudioController = this.mAudioRecordingCallback;
        if (virtualAudioController != null) {
            synchronized (virtualAudioController.mLock) {
                ArraySet arraySet = virtualAudioController.mRunningAppUids;
                arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) it.next();
                    if (arraySet.contains(Integer.valueOf(audioRecordingConfiguration.getClientUid()))) {
                        arrayList.add(audioRecordingConfiguration);
                    }
                }
            }
            synchronized (virtualAudioController.mCallbackLock) {
                IAudioConfigChangedCallback iAudioConfigChangedCallback = virtualAudioController.mConfigChangedCallback;
                if (iAudioConfigChangedCallback != null) {
                    try {
                        iAudioConfigChangedCallback.onRecordingConfigChanged(arrayList);
                    } catch (RemoteException e) {
                        Slog.e("VirtualAudioController", "RemoteException when calling onRecordingConfigChanged", e);
                    }
                }
            }
        }
    }
}
