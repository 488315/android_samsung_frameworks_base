package com.android.server.display;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Slog;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BrightnessSetting {
    public float mBrightness;
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.android.server.display.BrightnessSetting.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what == 1) {
                        float intBitsToFloat = Float.intBitsToFloat(message.arg1);
                        Iterator it = BrightnessSetting.this.mListeners.iterator();
                        while (it.hasNext()) {
                            DisplayPowerController$$ExternalSyntheticLambda10
                                    displayPowerController$$ExternalSyntheticLambda10 =
                                            (DisplayPowerController$$ExternalSyntheticLambda10)
                                                    ((BrightnessSettingListener) it.next());
                            Float valueOf = Float.valueOf(intBitsToFloat);
                            DisplayPowerController displayPowerController =
                                    displayPowerController$$ExternalSyntheticLambda10.f$0;
                            DisplayPowerController.DisplayControllerHandler
                                    displayControllerHandler = displayPowerController.mHandler;
                            Message obtainMessage =
                                    displayControllerHandler.obtainMessage(8, valueOf);
                            displayPowerController.mClock.getClass();
                            displayControllerHandler.sendMessageAtTime(
                                    obtainMessage, SystemClock.uptimeMillis());
                        }
                    }
                }
            };
    public final CopyOnWriteArraySet mListeners = new CopyOnWriteArraySet();
    public final LogicalDisplay mLogicalDisplay;
    public final PersistentDataStore mPersistentDataStore;
    public final DisplayManagerService.SyncRoot mSyncRoot;
    public int mUserSerial;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BrightnessSettingListener {}

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.BrightnessSetting$1] */
    public BrightnessSetting(
            int i,
            PersistentDataStore persistentDataStore,
            LogicalDisplay logicalDisplay,
            DisplayManagerService.SyncRoot syncRoot) {
        PersistentDataStore.DisplayState displayState;
        this.mPersistentDataStore = persistentDataStore;
        this.mLogicalDisplay = logicalDisplay;
        this.mUserSerial = i;
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        persistentDataStore.getClass();
        float f = Float.NaN;
        if (displayDevice != null
                && displayDevice.hasStableUniqueId()
                && (displayState =
                                persistentDataStore.getDisplayState(displayDevice.mUniqueId, false))
                        != null) {
            f = displayState.getBrightness(i);
        }
        this.mBrightness = f;
        this.mSyncRoot = syncRoot;
    }

    public final void setBrightness(float f) {
        if (Float.isNaN(f)) {
            Slog.w("BrightnessSetting", "Attempting to set invalid brightness");
            return;
        }
        synchronized (this.mSyncRoot) {
            try {
                if (f != this.mBrightness) {
                    this.mPersistentDataStore.setBrightness(
                            this.mLogicalDisplay.mPrimaryDisplayDevice, f, this.mUserSerial);
                }
                this.mBrightness = f;
                sendMessage(obtainMessage(1, Float.floatToIntBits(f), 0));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
