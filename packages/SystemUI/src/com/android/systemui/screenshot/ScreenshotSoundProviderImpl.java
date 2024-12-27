package com.android.systemui.screenshot;

import android.R;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.File;

public final class ScreenshotSoundProviderImpl implements ScreenshotSoundProvider {
    public final Context context;

    public ScreenshotSoundProviderImpl(Context context) {
        this.context = context;
    }

    public final MediaPlayer getScreenshotSound() {
        return MediaPlayer.create(this.context, Uri.fromFile(new File(this.context.getResources().getString(R.string.country_detector))), null, new AudioAttributes.Builder().setUsage(13).setContentType(4).build(), AudioSystem.newAudioSessionId());
    }
}
