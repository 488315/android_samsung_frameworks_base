package com.android.systemui.media.dagger;

import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory implements Provider {
    public final Provider mediaFlagsProvider;
    public final Provider muteAwaitConnectionCliLazyProvider;

    public MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory(Provider provider, Provider provider2) {
        this.mediaFlagsProvider = provider;
        this.muteAwaitConnectionCliLazyProvider = provider2;
    }

    public static Optional providesMediaMuteAwaitConnectionCli(MediaFlags mediaFlags, Lazy lazy) {
        mediaFlags.getClass();
        Flags.INSTANCE.getClass();
        Optional empty = !((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_MUTE_AWAIT) ? Optional.empty() : Optional.of((MediaMuteAwaitConnectionCli) lazy.get());
        Preconditions.checkNotNullFromProvides(empty);
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesMediaMuteAwaitConnectionCli((MediaFlags) this.mediaFlagsProvider.get(), DoubleCheck.lazy(this.muteAwaitConnectionCliLazyProvider));
    }
}
