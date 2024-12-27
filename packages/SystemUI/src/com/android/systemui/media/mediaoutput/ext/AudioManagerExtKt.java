package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;
import java.lang.reflect.Method;
import kotlin.Result;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class AudioManagerExtKt {
    public static final boolean isBroadcasting(AudioManager audioManager) {
        for (AudioDeviceInfo audioDeviceInfo : audioManager.getDevices(2)) {
            if (audioDeviceInfo.getType() == 30) {
                return true;
            }
        }
        return false;
    }

    public static final void setDeviceForced(AudioManager audioManager, AudioDeviceInfo audioDeviceInfo) {
        Object failure;
        Object failure2;
        audioManager.semSetDeviceForced(audioDeviceInfo.semGetInternalType(), audioDeviceInfo.semGetAddress());
        AudioProductStrategy audioProductStrategyWithId = AudioProductStrategy.getAudioProductStrategyWithId(19);
        if (audioProductStrategyWithId == null) {
            return;
        }
        try {
            int i = Result.$r8$clinit;
            failure = AudioManager.class.getDeclaredMethod("removePreferredDeviceForStrategy", AudioProductStrategy.class);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
        if (m2527exceptionOrNullimpl != null) {
            m2527exceptionOrNullimpl.printStackTrace();
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Method method = (Method) failure;
        if (method != null) {
            method.setAccessible(true);
            try {
                failure2 = method.invoke(audioManager, audioProductStrategyWithId);
            } catch (Throwable th2) {
                int i3 = Result.$r8$clinit;
                failure2 = new Result.Failure(th2);
            }
            Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure2);
            if (m2527exceptionOrNullimpl2 != null) {
                m2527exceptionOrNullimpl2.printStackTrace();
            }
            Result.m2526boximpl(failure2);
        }
    }
}
