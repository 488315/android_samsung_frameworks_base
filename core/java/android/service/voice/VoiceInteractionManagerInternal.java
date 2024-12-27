package android.service.voice;

import android.content.ComponentName;
import android.media.AudioFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionManagerInternal {

    public interface WearableHotwordDetectionCallback {
        void onDetected();

        void onError(String str);

        void onRejected();
    }

    public abstract HotwordDetectionServiceIdentity getHotwordDetectionServiceIdentity();

    public abstract String getVoiceInteractorPackageName(IBinder iBinder);

    public abstract boolean hasActiveSession(String str);

    public abstract void onPreCreatedUserConversion(int i);

    public abstract void startListeningFromWearable(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, ComponentName componentName, int i, WearableHotwordDetectionCallback wearableHotwordDetectionCallback);

    public abstract void startLocalVoiceInteraction(IBinder iBinder, String str, Bundle bundle);

    public abstract void stopLocalVoiceInteraction(IBinder iBinder);

    public abstract boolean supportsLocalVoiceInteraction();

    public static class HotwordDetectionServiceIdentity {
        private final int mIsolatedUid;
        private final int mOwnerUid;

        public HotwordDetectionServiceIdentity(int isolatedUid, int ownerUid) {
            this.mIsolatedUid = isolatedUid;
            this.mOwnerUid = ownerUid;
        }

        public int getIsolatedUid() {
            return this.mIsolatedUid;
        }

        public int getOwnerUid() {
            return this.mOwnerUid;
        }
    }
}
