package com.android.server.audio;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManagerInternal;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.server.LocalServices;

import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.SamsungRingerModeDelegate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioService$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                Toast.makeText((Context) obj, 17042777, 0).show();
                break;
            case 1:
                AudioService audioService = (AudioService) obj;
                int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService.getClass();
                AudioManagerInternal audioManagerInternal =
                        (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
                if (audioManagerInternal != null) {
                    Context context = audioService.mContext;
                    SamsungRingerModeDelegate samsungRingerModeDelegate =
                            new SamsungRingerModeDelegate(new Handler());
                    NotificationManager notificationManager =
                            (NotificationManager) context.getSystemService("notification");
                    samsungRingerModeDelegate.mNm = notificationManager;
                    samsungRingerModeDelegate.mZenMode = notificationManager.getZenMode();
                    samsungRingerModeDelegate.mAudioManager =
                            (AudioManagerInternal)
                                    LocalServices.getService(AudioManagerInternal.class);
                    ContentResolver contentResolver = context.getContentResolver();
                    samsungRingerModeDelegate.mContentResolver = contentResolver;
                    contentResolver.registerContentObserver(
                            Settings.Global.getUriFor("zen_mode"),
                            false,
                            samsungRingerModeDelegate);
                    audioManagerInternal.setRingerModeDelegate(samsungRingerModeDelegate);
                    break;
                }
                break;
            default:
                String str = (String) obj;
                SemAudioSystem.setPolicyParameters(str);
                Log.i("AS.AudioService", "fine volume : " + str);
                break;
        }
    }
}
