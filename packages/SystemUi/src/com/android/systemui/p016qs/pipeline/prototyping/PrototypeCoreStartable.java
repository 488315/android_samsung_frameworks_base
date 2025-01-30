package com.android.systemui.p016qs.pipeline.prototyping;

import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p016qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.p016qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.user.data.repository.UserRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrototypeCoreStartable implements CoreStartable {
    public final AutoAddRepository autoAddRepository;
    public final FeatureFlags featureFlags;
    public final CoroutineScope scope;
    public final TileSpecRepository tileSpecRepository;
    public final UserRepository userRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public PrototypeCoreStartable(TileSpecRepository tileSpecRepository, AutoAddRepository autoAddRepository, UserRepository userRepository, FeatureFlags featureFlags, CoroutineScope coroutineScope, CommandRegistry commandRegistry) {
        this.tileSpecRepository = tileSpecRepository;
        this.autoAddRepository = autoAddRepository;
        this.userRepository = userRepository;
        this.featureFlags = featureFlags;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags flags = Flags.INSTANCE;
        this.featureFlags.getClass();
    }
}
