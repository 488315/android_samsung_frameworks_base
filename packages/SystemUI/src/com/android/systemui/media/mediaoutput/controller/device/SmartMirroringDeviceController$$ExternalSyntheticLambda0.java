package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class SmartMirroringDeviceController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SmartMirroringDeviceController$$ExternalSyntheticLambda0(int i, Context context, Object obj) {
        this.$r8$classId = i;
        this.f$0 = context;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$1;
        int i = this.$r8$classId;
        Context context = this.f$0;
        switch (i) {
            case 0:
                SmartMirroringDeviceController.Companion companion = SmartMirroringDeviceController.Companion;
                return new SmartMirroringClient(context, (AudioManager) obj);
            default:
                Log.d("SmartMirroringDeviceController", "unregisterReceiver");
                context.unregisterReceiver((SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1) obj);
                return Unit.INSTANCE;
        }
    }
}
