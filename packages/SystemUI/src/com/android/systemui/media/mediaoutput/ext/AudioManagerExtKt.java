package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class AudioManagerExtKt {
    public static final Lazy mediaStrategy$delegate = LazyKt__LazyJVMKt.lazy(new AudioManagerExtKt$$ExternalSyntheticLambda0());

    public static final Flow getDeviceChanged(AudioManager audioManager) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new AudioManagerExtKt$deviceChanged$1(audioManager, null)), -1, 2);
    }

    public static final boolean isBroadcasting(AudioManager audioManager) {
        for (AudioDeviceInfo audioDeviceInfo : audioManager.getDevices(2)) {
            if (audioDeviceInfo.getType() == 30) {
                return true;
            }
        }
        return false;
    }

    public static final void removePreferredDeviceForStrategy(AudioManager audioManager) {
        Object failure;
        Object failure2;
        AudioProductStrategy audioProductStrategy = (AudioProductStrategy) mediaStrategy$delegate.getValue();
        if (audioProductStrategy == null) {
            return;
        }
        try {
            int i = Result.$r8$clinit;
            failure = AudioManager.class.getDeclaredMethod("removePreferredDeviceForStrategy", AudioProductStrategy.class);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            m3422exceptionOrNullimpl.printStackTrace();
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Method method = (Method) failure;
        if (method != null) {
            method.setAccessible(true);
            try {
                failure2 = method.invoke(audioManager, audioProductStrategy);
            } catch (Throwable th2) {
                int i3 = Result.$r8$clinit;
                failure2 = new Result.Failure(th2);
            }
            Throwable m3422exceptionOrNullimpl2 = Result.m3422exceptionOrNullimpl(failure2);
            if (m3422exceptionOrNullimpl2 != null) {
                m3422exceptionOrNullimpl2.printStackTrace();
            }
            Result.m3421boximpl(failure2);
        }
    }

    public static final void setDeviceForced(AudioManager audioManager, AudioDeviceInfo audioDeviceInfo) {
        audioManager.semSetDeviceForced(audioDeviceInfo.semGetInternalType(), audioDeviceInfo.semGetAddress());
        removePreferredDeviceForStrategy(audioManager);
    }
}
