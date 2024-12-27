package com.android.systemui.audio.soundcraft.interfaces.wearable.requester;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.feature.SoundCraftDebugFeatures;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.model.common.Equalizer;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.Gson;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class GetInfoRequester extends BudsPluginServiceRequester {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Function1 onBudsModelReceived;
    public final Messenger receiver;

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
        this.onBudsModelReceived = function1;
        this.receiver = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.requester.GetInfoRequester$receiver$1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                Unit unit;
                Bundle data;
                String string;
                Object failure;
                Message obtain = Message.obtain(message);
                GetInfoRequester getInfoRequester = GetInfoRequester.this;
                Intrinsics.checkNotNull(obtain);
                int i = GetInfoRequester.$r8$clinit;
                getInfoRequester.getClass();
                Log.d("SoundCraft.wearable.GetInfoRequester", "handleMessage : " + obtain);
                Message message2 = obtain.what == 1002 ? obtain : null;
                Function1 function12 = getInfoRequester.onBudsModelReceived;
                if (message2 == null || (data = message2.getData()) == null || (string = data.getString("result")) == null) {
                    unit = null;
                } else {
                    Log.d("SoundCraft.wearable.GetInfoRequester", "handleMessage : " + obtain + ", received : json=" + string);
                    try {
                        int i2 = Result.$r8$clinit;
                        failure = (BudsModel) new Gson().fromJson(BudsModel.class, string);
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    if (Result.m2527exceptionOrNullimpl(failure) != null) {
                        failure = new BudsModel(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 131071, null);
                    }
                    BudsModel budsModel = (BudsModel) failure;
                    Log.d("SoundCraft.wearable.GetInfoRequester", "parseBudsInfo : " + budsModel);
                    function12.invoke(budsModel);
                    unit = Unit.INSTANCE;
                }
                if (unit == null) {
                    Log.d("SoundCraft.wearable.GetInfoRequester", "handleMessage : " + obtain + ", received : json=null");
                    function12.invoke(null);
                }
                obtain.recycle();
                return true;
            }
        }));
    }

    @Override // com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester
    public final boolean bindService() {
        SoundCraftDebugFeatures soundCraftDebugFeatures = SoundCraftDebugFeatures.INSTANCE;
        Context context = this.context;
        soundCraftDebugFeatures.getClass();
        if (Settings.System.getInt(context.getContentResolver(), "audio_soundcraft_debug_dummy_buds_info", 0) != 1) {
            return super.bindService();
        }
        new GetDummyInfoRequester();
        Context context2 = this.context;
        List listOf = CollectionsKt__CollectionsKt.listOf(new Equalizer("Balanced", true), new Equalizer("Bass boost", false), new Equalizer("Smooth", false), new Equalizer("Dynamic", false), new Equalizer("Clear", false), new Equalizer("Treble boost", false), new Equalizer(SystemUIAnalytics.DT_WALLPAPER_SET_FROM_CUSTOM, false));
        Set mutableSetOf = SetsKt__SetsKt.mutableSetOf(new NoiseControl(context2.getString(R.string.sound_craft_wearable_noise_control_off), false), new NoiseControl(context2.getString(R.string.sound_craft_ambient_sound), false), new NoiseControl(context2.getString(R.string.sound_craft_noise_cancelling), false), new NoiseControl(context2.getString(R.string.sound_craft_adaptive), true));
        Boolean bool = Boolean.TRUE;
        Boolean bool2 = Boolean.FALSE;
        this.onBudsModelReceived.invoke(new BudsModel(bool, listOf, mutableSetOf, 4, 3, 4, 1, null, null, null, null, null, bool, bool2, bool, bool2, null, 69504, null));
        return true;
    }

    @Override // com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester
    public final void execute() {
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("execute : budsPluginPackageName="), this.budsPluginPackageName, "SoundCraft.wearable.GetInfoRequester");
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
