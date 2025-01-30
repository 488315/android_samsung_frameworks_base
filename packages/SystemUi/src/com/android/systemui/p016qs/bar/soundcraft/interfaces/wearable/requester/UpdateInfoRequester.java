package com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import com.google.gson.Gson;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UpdateInfoRequester extends BudsPluginServiceRequester {
    public final BudsInfo budsInfo;

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

    public UpdateInfoRequester(Context context, String str, BudsInfo budsInfo) {
        super(context, str);
        this.budsInfo = budsInfo;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester
    public final void execute() {
        Log.d("SoundCraft.wearable.UpdateInfoRequester", "execute : budsPluginPackageName=" + this.budsPluginPackageName);
        StringBuilder sb = new StringBuilder("requestUpdateBudsInfo : jsonString=");
        BudsInfo budsInfo = this.budsInfo;
        sb.append(budsInfo);
        Log.d("SoundCraft.wearable.UpdateInfoRequester", sb.toString());
        Messenger messenger = this.messenger;
        if (messenger != null) {
            Message obtain = Message.obtain((Handler) null, 1003);
            Bundle bundle = new Bundle();
            bundle.putString("result", new Gson().toJson(budsInfo));
            obtain.setData(bundle);
            messenger.send(obtain);
        }
    }
}
