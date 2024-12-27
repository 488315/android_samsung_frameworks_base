package com.android.server.location.settings;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SettingsStore$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsStore f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SettingsStore$$ExternalSyntheticLambda1(
            SettingsStore settingsStore, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsStore;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SettingsStore settingsStore = this.f$0;
                CountDownLatch countDownLatch = (CountDownLatch) this.f$1;
                settingsStore.mFile.delete();
                countDownLatch.countDown();
                return;
            default:
                SettingsStore settingsStore2 = this.f$0;
                LocationUserSettings locationUserSettings = (LocationUserSettings) this.f$1;
                settingsStore2.getClass();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = settingsStore2.mFile.startWrite();
                    DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
                    locationUserSettings.getClass();
                    dataOutputStream.writeInt(1);
                    dataOutputStream.writeBoolean(locationUserSettings.mAdasGnssLocationEnabled);
                    settingsStore2.mFile.finishWrite(fileOutputStream);
                    return;
                } catch (IOException e) {
                    settingsStore2.mFile.failWrite(fileOutputStream);
                    Log.e("LocationManagerService", "failure serializing location settings", e);
                    return;
                } catch (Throwable th) {
                    settingsStore2.mFile.failWrite(fileOutputStream);
                    throw th;
                }
        }
    }
}
