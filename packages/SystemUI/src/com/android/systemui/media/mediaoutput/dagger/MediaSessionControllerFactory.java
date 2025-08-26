package com.android.systemui.media.mediaoutput.dagger;

import android.media.session.MediaController;
import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;

/* loaded from: classes2.dex */
public interface MediaSessionControllerFactory {
    MediaSessionController create(MediaController mediaController);
}
