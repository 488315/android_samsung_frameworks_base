package com.android.systemui.media.mediaoutput.entity;

import android.media.MediaRoute2Info;

public interface RouteDevice extends AudioDevice {
    MediaRoute2Info getMediaRoute2Info();
}
