package com.android.systemui.media.mediaoutput.dagger;

import android.media.session.MediaController;
import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MediaSessionControllerFactory {
    MediaSessionController create(MediaController mediaController);
}
