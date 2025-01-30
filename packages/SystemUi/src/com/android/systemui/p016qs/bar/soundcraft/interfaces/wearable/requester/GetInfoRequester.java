package com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import com.google.gson.Gson;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GetInfoRequester extends BudsPluginServiceRequester {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Function1 callback;
    public final Messenger receiver;

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

    public GetInfoRequester(Context context, String str, Function1 function1) {
        super(context, str);
        this.callback = function1;
        this.receiver = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.wearable.requester.GetInfoRequester$receiver$1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                Unit unit;
                Bundle data;
                String string;
                Object failure;
                Message obtain = Message.obtain(message);
                GetInfoRequester getInfoRequester = GetInfoRequester.this;
                int i = GetInfoRequester.$r8$clinit;
                getInfoRequester.getClass();
                Log.d("SoundCraft.wearable.GetInfoRequester", "handleMessage : " + obtain);
                Message message2 = obtain.what == 1002 ? obtain : null;
                Function1 function12 = getInfoRequester.callback;
                if (message2 == null || (data = message2.getData()) == null || (string = data.getString("result")) == null) {
                    unit = null;
                } else {
                    try {
                        int i2 = Result.$r8$clinit;
                        failure = (BudsInfo) new Gson().fromJson(BudsInfo.class, string);
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    if (Result.m2859exceptionOrNullimpl(failure) != null) {
                        failure = new BudsInfo(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
                    }
                    BudsInfo budsInfo = (BudsInfo) failure;
                    Log.d("SoundCraft.wearable.GetInfoRequester", "parseBudsInfo : " + budsInfo);
                    function12.invoke(budsInfo);
                    unit = Unit.INSTANCE;
                }
                if (unit == null) {
                    function12.invoke(null);
                }
                obtain.recycle();
                return true;
            }
        }));
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester
    public final void execute() {
        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("execute : budsPluginPackageName="), this.budsPluginPackageName, "SoundCraft.wearable.GetInfoRequester");
        try {
            int i = Result.$r8$clinit;
            Log.d("SoundCraft.wearable.GetInfoRequester", "requestGetBudsInfo");
            Messenger messenger = this.messenger;
            if (messenger != null) {
                messenger.send(Message.obtain(null, 1001, this.receiver));
                Unit unit = Unit.INSTANCE;
            }
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            new Result.Failure(th);
        }
    }
}
