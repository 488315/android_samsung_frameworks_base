package com.android.server.display;

import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Slog;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.display.ExternalDisplayStatsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ExternalDisplayStatsService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExternalDisplayStatsService f$0;

    public /* synthetic */ ExternalDisplayStatsService$$ExternalSyntheticLambda0(ExternalDisplayStatsService externalDisplayStatsService, int i) {
        this.$r8$classId = i;
        this.f$0 = externalDisplayStatsService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ExternalDisplayStatsService externalDisplayStatsService = this.f$0;
        switch (i) {
            case 0:
                if (!externalDisplayStatsService.mIsInitialized) {
                    Slog.e("ExternalDisplayStatsService", "scheduleDeinit is called but never initialized");
                    break;
                } else {
                    externalDisplayStatsService.mIsInitialized = false;
                    ExternalDisplayStatsService.Injector injector = externalDisplayStatsService.mInjector;
                    injector.mContext.unregisterReceiver(externalDisplayStatsService.mInteractivityReceiver);
                    if (injector.mAudioManager == null) {
                        injector.mAudioManager = (AudioManager) injector.mContext.getSystemService(AudioManager.class);
                    }
                    AudioManager audioManager = injector.mAudioManager;
                    if (audioManager != null) {
                        audioManager.unregisterAudioPlaybackCallback(externalDisplayStatsService.mAudioPlaybackCallback);
                        break;
                    }
                }
                break;
            default:
                if (!externalDisplayStatsService.mIsInitialized) {
                    externalDisplayStatsService.mIsInitialized = true;
                    IntentFilter m = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF", "android.intent.action.SCREEN_ON");
                    externalDisplayStatsService.mInteractiveExternalDisplays = -1;
                    externalDisplayStatsService.mIsExternalDisplayUsedForAudio = false;
                    ExternalDisplayStatsService.Injector injector2 = externalDisplayStatsService.mInjector;
                    injector2.mContext.registerReceiver(externalDisplayStatsService.mInteractivityReceiver, m, null, injector2.mHandler, 4);
                    if (injector2.mAudioManager == null) {
                        injector2.mAudioManager = (AudioManager) injector2.mContext.getSystemService(AudioManager.class);
                    }
                    AudioManager audioManager2 = injector2.mAudioManager;
                    if (audioManager2 != null) {
                        audioManager2.registerAudioPlaybackCallback(externalDisplayStatsService.mAudioPlaybackCallback, injector2.mHandler);
                        break;
                    }
                } else {
                    Slog.e("ExternalDisplayStatsService", "scheduleInit is called but already initialized");
                    break;
                }
                break;
        }
    }
}
