package com.android.server.broadcastradio.hal2;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;

import java.util.Map;

public final /* synthetic */ class RadioModule$$ExternalSyntheticLambda2
        implements RadioModule.AidlCallbackRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RadioModule$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
    public void run(ITunerCallback iTunerCallback) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 1:
                iTunerCallback.onParametersUpdated((Map) obj);
                break;
            default:
                iTunerCallback.onCurrentProgramInfoChanged((RadioManager.ProgramInfo) obj);
                break;
        }
    }
}
