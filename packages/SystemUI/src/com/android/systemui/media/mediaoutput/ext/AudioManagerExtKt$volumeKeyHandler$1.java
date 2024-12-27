package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

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
        if (keyEvent == null) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
        if (num == null) {
            return false;
        }
        Integer num2 = AudioManagerExtKt.isBroadcasting(this.$this_volumeKeyHandler) ? null : num;
        if (num2 == null) {
            return false;
        }
        this.$this_volumeKeyHandler.adjustVolume(num2.intValue(), 0);
        return true;
    }
}
