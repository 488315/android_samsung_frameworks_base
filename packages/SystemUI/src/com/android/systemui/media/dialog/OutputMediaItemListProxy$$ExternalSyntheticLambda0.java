package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class OutputMediaItemListProxy$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        MediaItem mediaItem = (MediaItem) obj;
        return mediaItem.mMediaDeviceOptional.isPresent() && ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).isMutingExpectedDevice();
    }
}
