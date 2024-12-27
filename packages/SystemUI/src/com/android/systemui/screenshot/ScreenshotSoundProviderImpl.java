package com.android.systemui.screenshot;

import android.R;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.File;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotSoundProviderImpl implements ScreenshotSoundProvider {
    public final Context context;

    public ScreenshotSoundProviderImpl(Context context) {
        this.context = context;
    }

    public final MediaPlayer getScreenshotSound() {
        return MediaPlayer.create(this.context, Uri.fromFile(new File(this.context.getResources().getString(R.string.country_detector))), null, new AudioAttributes.Builder().setUsage(13).setContentType(4).build(), AudioSystem.newAudioSessionId());
    }
}
