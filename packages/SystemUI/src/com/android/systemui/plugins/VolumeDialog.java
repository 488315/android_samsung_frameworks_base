package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = VolumeDialog.ACTION, version = 1)
@DependsOn(target = Callback.class)
/* loaded from: classes2.dex */
public interface VolumeDialog extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_VOLUME";
    public static final String ACTION_VOLUME_UNDO = "com.android.systemui.volume.ACTION_VOLUME_UNDO";
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @ProvidesInterface(version = 1)
    public interface Callback {
        public static final int VERSION = 1;

        void onZenPrioritySettingsClicked();

        void onZenSettingsClicked();
    }

    void destroy();

    void init(int i, Callback callback);
}
