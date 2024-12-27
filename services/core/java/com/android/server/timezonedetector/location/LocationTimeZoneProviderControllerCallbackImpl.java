package com.android.server.timezonedetector.location;

import java.util.Objects;

public final class LocationTimeZoneProviderControllerCallbackImpl {
    public final HandlerThreadingDomain mThreadingDomain;

    public LocationTimeZoneProviderControllerCallbackImpl(
            HandlerThreadingDomain handlerThreadingDomain) {
        Objects.requireNonNull(handlerThreadingDomain);
        this.mThreadingDomain = handlerThreadingDomain;
    }
}
