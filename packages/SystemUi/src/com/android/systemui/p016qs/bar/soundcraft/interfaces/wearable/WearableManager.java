package com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable;

import android.content.Context;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester.UpdateInfoRequester;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WearableManager {
    public final Context context;
    public final SoundCraftSettings soundCraftSettings;

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

    public WearableManager(Context context, SoundCraftSettings soundCraftSettings) {
        this.context = context;
        this.soundCraftSettings = soundCraftSettings;
    }

    public final void updateBudsInfo(BudsInfo budsInfo) {
        new UpdateInfoRequester(this.context, this.soundCraftSettings.budsPluginPackageName, budsInfo).bindService();
    }
}
