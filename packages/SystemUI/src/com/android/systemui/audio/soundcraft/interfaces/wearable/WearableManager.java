package com.android.systemui.audio.soundcraft.interfaces.wearable;

import android.content.Context;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.requester.UpdateInfoRequester;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WearableManager {
    public final Context context;
    public final SoundCraftSettings settings;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
