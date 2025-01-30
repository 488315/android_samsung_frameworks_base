package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaOutputController$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Optional optional = ((MediaItem) obj).mMediaDeviceOptional;
        return optional.isPresent() && ((MediaDevice) optional.get()).isMutingExpectedDevice();
    }
}
