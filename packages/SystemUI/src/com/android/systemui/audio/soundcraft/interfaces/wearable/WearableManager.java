package com.android.systemui.audio.soundcraft.interfaces.wearable;

import android.content.Context;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.requester.UpdateInfoRequester;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class WearableManager {
    public final Context context;
    public final SoundCraftSettings settings;

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

    public WearableManager(Context context, SoundCraftSettings soundCraftSettings) {
        this.context = context;
        this.settings = soundCraftSettings;
    }

    public final void updateBudsModel(BudsModel budsModel) {
        new UpdateInfoRequester(this.context, this.settings.budsPluginPackageName, budsModel).bindService();
    }
}
