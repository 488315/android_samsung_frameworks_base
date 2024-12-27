package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = VolumeDialog.ACTION, version = 1)
@DependsOn(target = Callback.class)
public interface VolumeDialog extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_VOLUME";
    public static final int VERSION = 1;

    @ProvidesInterface(version = 1)
    public interface Callback {
        public static final int VERSION = 1;

        void onZenPrioritySettingsClicked();

        void onZenSettingsClicked();
    }

    void destroy();

    void init(int i, Callback callback);
}
