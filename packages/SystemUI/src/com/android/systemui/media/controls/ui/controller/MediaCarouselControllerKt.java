package com.android.systemui.media.controls.ui.controller;

import android.content.Intent;
import android.util.Log;

public abstract class MediaCarouselControllerKt {
    public static final Intent settingsIntent = new Intent().setAction("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    public static final boolean DEBUG = Log.isLoggable("MediaCarouselController", 3);
}
