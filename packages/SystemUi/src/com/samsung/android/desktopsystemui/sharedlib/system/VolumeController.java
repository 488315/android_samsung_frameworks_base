package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.media.AudioManager;
import android.media.IVolumeController;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class VolumeController {
    private static final int NO_VOLUME_CHANGED = 0;
    private static final String TAG = "[DSU]VolumeController ";
    public static final int VOLUME_CHANGED = 1;
    public static final int VOLUME_STAR_CHANGED = 2;
    public static final String VOLUME_STAR_ENABLED = "volume_star_enabled";
    private static final AudioManager mAudioManager = (AudioManager) AppGlobals.getInitialApplication().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    protected final C4581VC mVolumeController = new C4581VC();
    private ArrayList<IVolumeControllerCallback> mCallbacks = new ArrayList<>();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.desktopsystemui.sharedlib.system.VolumeController$VC */
    public class C4581VC extends IVolumeController.Stub {
        public final HandlerC4582W mWorker;

        public void dismiss() {
            Log.d(VolumeController.TAG, "dismiss volume panel");
        }

        public void setA11yMode(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setA11yMode", i, VolumeController.TAG);
            if (i == 100) {
                this.mWorker.removeMessages(2);
                this.mWorker.obtainMessage(2, Boolean.TRUE).sendToTarget();
            } else {
                if (i != 101) {
                    return;
                }
                this.mWorker.removeMessages(2);
                this.mWorker.obtainMessage(2, Boolean.FALSE).sendToTarget();
            }
        }

        public void volumeChanged(int i, int i2) {
            Log.d(VolumeController.TAG, "Volume changed in VC");
            this.mWorker.removeMessages(1);
            this.mWorker.obtainMessage(1, i, i2).sendToTarget();
        }

        private C4581VC() {
            this.mWorker = VolumeController.this.new HandlerC4582W(Looper.getMainLooper());
        }

        public void displaySafeVolumeWarning(int i) {
        }

        public void masterMuteChanged(int i) {
        }

        public void setLayoutDirection(int i) {
        }

        public void displayVolumeLimiterToast() {
        }

        public void displayCsdWarning(int i, int i2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.desktopsystemui.sharedlib.system.VolumeController$W */
    public final class HandlerC4582W extends Handler {
        public HandlerC4582W(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1 && message.arg2 != 0) {
                Bundle bundle = new Bundle();
                Iterator it = VolumeController.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IVolumeControllerCallback) it.next()).volumeControllerCallback(1, bundle);
                }
                return;
            }
            if (i == 2) {
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean(VolumeController.VOLUME_STAR_ENABLED, ((Boolean) message.obj).booleanValue());
                Iterator it2 = VolumeController.this.mCallbacks.iterator();
                while (it2.hasNext()) {
                    ((IVolumeControllerCallback) it2.next()).volumeControllerCallback(2, bundle2);
                }
            }
        }
    }

    public void addCallback(IVolumeControllerCallback iVolumeControllerCallback) {
        this.mCallbacks.add(iVolumeControllerCallback);
    }

    public void notifyVisible(boolean z) {
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("visible ", z, TAG);
        mAudioManager.notifyVolumeControllerVisible(this.mVolumeController, z);
    }

    public void removeCallback(IVolumeControllerCallback iVolumeControllerCallback) {
        this.mCallbacks.remove(iVolumeControllerCallback);
    }

    public void setVolumeController() {
        try {
            Log.d(TAG, "Volume controller set");
            mAudioManager.setVolumeController(this.mVolumeController);
        } catch (SecurityException e) {
            Log.w(TAG, "Unable to set the volume controller", e);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface IVolumeControllerCallback {
        default void volumeControllerCallback(int i, Bundle bundle) {
        }
    }
}
