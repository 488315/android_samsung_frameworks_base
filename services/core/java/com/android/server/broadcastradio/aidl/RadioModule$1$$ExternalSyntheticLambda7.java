package com.android.server.broadcastradio.aidl;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import com.android.server.broadcastradio.aidl.RadioModule;
import com.android.server.utils.Slogf;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RadioModule$1$$ExternalSyntheticLambda7 implements RadioModule.AidlCallbackRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RadioModule$1$$ExternalSyntheticLambda7(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
    public final void run(ITunerCallback iTunerCallback, int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                RadioManager.ProgramInfo programInfo = (RadioManager.ProgramInfo) obj;
                if (!ConversionUtils.programInfoMeetsSdkVersionRequirement(programInfo, i)) {
                    Slogf.e("BcRadioAidlSrv.module", "onCurrentProgramInfoChanged: cannot send program info requiring higher target SDK version");
                    break;
                } else {
                    iTunerCallback.onCurrentProgramInfoChanged(programInfo);
                    break;
                }
            default:
                iTunerCallback.onParametersUpdated((Map) obj);
                break;
        }
    }
}
