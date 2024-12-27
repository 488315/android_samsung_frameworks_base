package com.android.systemui.audio.soundcraft.interfaces.wearable;

import android.content.Context;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.requester.UpdateInfoRequester;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WearableManager {
    public final Context context;
    public final SoundCraftSettings settings;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
