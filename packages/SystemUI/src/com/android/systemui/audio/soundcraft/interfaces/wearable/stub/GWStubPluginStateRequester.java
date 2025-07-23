package com.android.systemui.audio.soundcraft.interfaces.wearable.stub;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.systemui.audio.soundcraft.model.buds.plugin.GWPluginModel;
import com.google.gson.Gson;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GWStubPluginStateRequester extends GWStubServiceRequester {
    public final Function1 onPluginStateReceived;
    public final Messenger receiver;

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

    public GWStubPluginStateRequester(Context context, Function1 function1) {
        super(context);
        this.onPluginStateReceived = function1;
        this.receiver = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubPluginStateRequester$receiver$1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                Object failure;
                Function1 function12 = GWStubPluginStateRequester.this.onPluginStateReceived;
                String string = message.getData().getString("result");
                if (string == null) {
                    string = "";
                }
                if (message.what == 2001) {
                    Log.d("SoundCraft.wearable.GWStubPluginStateRequester", "MSG_WHAT_GETTING_INSTALLED_PLUGIN_LIST_INFO_RESULT message from service");
                    try {
                        int i = Result.$r8$clinit;
                        ArrayList arrayList = new ArrayList();
                        JSONArray jSONArray = new JSONArray(string);
                        int length = jSONArray.length();
                        for (int i2 = 0; i2 < length; i2++) {
                            GWPluginModel gWPluginModel = (GWPluginModel) new Gson().fromJson(jSONArray.getString(i2), GWPluginModel.class);
                            String str = gWPluginModel.packageName;
                            String str2 = gWPluginModel.pluginType;
                            boolean z = gWPluginModel.isEnabled;
                            Log.d("SoundCraft.wearable.GWStubPluginStateRequester", "model : packageName=" + str + ", isEnabled=" + z + ", isConnected=" + gWPluginModel.isConnected + ", pluginType=" + str2);
                            if (!z || !Intrinsics.areEqual(str2, "earbud")) {
                                gWPluginModel = null;
                            }
                            if (gWPluginModel != null) {
                                arrayList.add(gWPluginModel);
                            }
                        }
                        function12.mo779invoke(arrayList);
                        failure = Unit.INSTANCE;
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                    if (m3422exceptionOrNullimpl != null) {
                        Log.e("SoundCraft.wearable.GWStubPluginStateRequester", "failed : e=" + m3422exceptionOrNullimpl);
                        function12.mo779invoke(EmptyList.INSTANCE);
                    }
                }
                return true;
            }
        }));
    }

    @Override // com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubServiceRequester
    public final void execute() {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            Messenger messenger = this.messenger;
            failure = null;
            if (messenger != null) {
                Message obtain = Message.obtain((Handler) null, 1001);
                obtain.replyTo = this.receiver;
                messenger.send(obtain);
                failure = Unit.INSTANCE;
            }
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (Result.m3422exceptionOrNullimpl(failure) != null) {
            Log.e("SoundCraft.wearable.GWStubPluginStateRequester", "execute : onFailure");
        }
    }
}
