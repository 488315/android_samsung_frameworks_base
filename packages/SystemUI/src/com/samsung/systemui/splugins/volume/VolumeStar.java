package com.samsung.systemui.splugins.volume;

import android.content.Context;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = "com.samsung.systemui.volume.PLUGIN", version = 3000)
/* loaded from: classes4.dex */
public interface VolumeStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.volume.PLUGIN";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAJOR_VERSION = 3;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 3000;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
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
