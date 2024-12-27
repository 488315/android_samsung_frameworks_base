package com.android.server.broadcastradio.hal2;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import com.android.server.broadcastradio.hal2.RadioModule;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RadioModule$$ExternalSyntheticLambda2 implements RadioModule.AidlCallbackRunnable {
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
