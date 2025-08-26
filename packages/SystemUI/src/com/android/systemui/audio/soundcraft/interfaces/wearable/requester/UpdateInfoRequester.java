package com.android.systemui.audio.soundcraft.interfaces.wearable.requester;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.google.gson.Gson;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class UpdateInfoRequester extends BudsPluginServiceRequester {
    public final BudsModel budsModel;

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

    public UpdateInfoRequester(Context context, String str, BudsModel budsModel) {
        super(context, str);
        this.budsModel = budsModel;
    }

    @Override // com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester
    public final void execute() throws RemoteException {
        Log.d("SoundCraft.wearable.UpdateInfoRequester", "execute : budsPluginPackageName=" + this.budsPluginPackageName);
        StringBuilder sb = new StringBuilder("requestUpdateBudsInfo : jsonString=");
        BudsModel budsModel = this.budsModel;
        sb.append(budsModel);
        Log.d("SoundCraft.wearable.UpdateInfoRequester", sb.toString());
        Messenger messenger = this.messenger;
        if (messenger != null) {
            Message messageObtain = Message.obtain((Handler) null, 1003);
            Bundle bundle = new Bundle();
            bundle.putString("result", new Gson().toJson(budsModel));
            messageObtain.setData(bundle);
            messenger.send(messageObtain);
        }
    }
}
