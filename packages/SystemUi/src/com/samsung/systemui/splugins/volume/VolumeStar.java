package com.samsung.systemui.splugins.volume;

import android.content.Context;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = "com.samsung.systemui.volume.PLUGIN", version = 3000)
/* loaded from: classes3.dex */
public interface VolumeStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.volume.PLUGIN";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAJOR_VERSION = 3;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 3000;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.samsung.systemui.volume.PLUGIN";
        public static final int MAJOR_VERSION = 3;
        public static final int MINOR_VERSION = 0;
        public static final int VERSION = 3000;

        private Companion() {
        }
    }

    void init(Context context, Context context2, VolumeStarDependency volumeStarDependency);

    void start();

    void stop();
}
