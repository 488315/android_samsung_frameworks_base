package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Predicate;

public final /* synthetic */ class MediaOutputController$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        MediaItem mediaItem = (MediaItem) obj;
        return mediaItem.mMediaDeviceOptional.isPresent() && ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).isMutingExpectedDevice();
    }
}
