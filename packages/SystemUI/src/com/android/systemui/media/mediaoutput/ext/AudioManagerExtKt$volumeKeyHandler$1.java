package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AudioManagerExtKt$volumeKeyHandler$1 implements View.OnUnhandledKeyEventListener {
    public final /* synthetic */ AudioManager $this_volumeKeyHandler;

    public AudioManagerExtKt$volumeKeyHandler$1(AudioManager audioManager) {
        this.$this_volumeKeyHandler = audioManager;
    }

    @Override // android.view.View.OnUnhandledKeyEventListener
    public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
        Log.i("AudioManagerExt", "onUnhandledKeyEvent() - keyCode = " + keyEvent.getKeyCode());
        if (keyEvent.getAction() != 0) {
            keyEvent = null;
        }
        if (keyEvent != null) {
            int keyCode = keyEvent.getKeyCode();
            Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
            if (num != null) {
                Integer num2 = AudioManagerExtKt.isBroadcasting(this.$this_volumeKeyHandler) ? null : num;
                if (num2 != null) {
                    this.$this_volumeKeyHandler.adjustVolume(num2.intValue(), 0);
                    return true;
                }
            }
        }
        return false;
    }
}
