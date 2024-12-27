package com.samsung.android.server.audio;

import android.content.Context;
import android.util.Log;

import com.samsung.android.audio.Rune;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SoundAppPolicyManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ SoundAppPolicyManager f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ SoundAppPolicyManager$$ExternalSyntheticLambda0(
            SoundAppPolicyManager soundAppPolicyManager, Context context) {
        this.f$0 = soundAppPolicyManager;
        this.f$1 = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SoundAppPolicyManager soundAppPolicyManager = this.f$0;
        Context context = this.f$1;
        soundAppPolicyManager.getClass();
        if (context.getPackageManager()
                        .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
                != null) {
            Log.i("SoundAppPolicyManager", "init SCPMv2");
            if (soundAppPolicyManager.register()) {
                soundAppPolicyManager.checkAndUpdateAppList();
                if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                    soundAppPolicyManager.checkAndUpdateLiveTranslateList();
                }
            }
        }
    }
}
