package android.hardware.input;

import android.app.ActivityThread;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.hardware.lights.LightsManager;
import android.hardware.lights.LightsRequest;
import android.util.CloseGuard;
import com.android.internal.util.Preconditions;
import java.lang.ref.Reference;
import java.util.List;

/* loaded from: classes2.dex */
class InputDeviceLightsManager extends LightsManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "InputDeviceLightsManager";
    private final int mDeviceId;
    private final InputManagerGlobal mGlobal = InputManagerGlobal.getInstance();
    private final String mPackageName = ActivityThread.currentPackageName();

    InputDeviceLightsManager(int i) {
        this.mDeviceId = i;
    }

    @Override // android.hardware.lights.LightsManager
    public List<Light> getLights() {
        return this.mGlobal.getLights(this.mDeviceId);
    }

    @Override // android.hardware.lights.LightsManager
    public LightState getLightState(Light light) {
        Preconditions.checkNotNull(light);
        return this.mGlobal.getLightState(this.mDeviceId, light);
    }

    @Override // android.hardware.lights.LightsManager
    public LightsManager.LightsSession openSession() {
        InputDeviceLightsSession inputDeviceLightsSession = new InputDeviceLightsSession();
        this.mGlobal.openLightSession(this.mDeviceId, this.mPackageName, inputDeviceLightsSession.getToken());
        return inputDeviceLightsSession;
    }

    @Override // android.hardware.lights.LightsManager
    public LightsManager.LightsSession openSession(int i) {
        throw new UnsupportedOperationException();
    }

    public final class InputDeviceLightsSession extends LightsManager.LightsSession implements AutoCloseable {
        private final CloseGuard mCloseGuard;
        private boolean mClosed;

        private InputDeviceLightsSession() {
            CloseGuard closeGuard = new CloseGuard();
            this.mCloseGuard = closeGuard;
            this.mClosed = false;
            closeGuard.open("InputDeviceLightsSession.close");
        }

        @Override // android.hardware.lights.LightsManager.LightsSession
        public void requestLights(LightsRequest lightsRequest) {
            Preconditions.checkNotNull(lightsRequest);
            Preconditions.checkArgument(!this.mClosed);
            InputDeviceLightsManager.this.mGlobal.requestLights(InputDeviceLightsManager.this.mDeviceId, lightsRequest, getToken());
        }

        @Override // android.hardware.lights.LightsManager.LightsSession, java.lang.AutoCloseable
        public void close() {
            if (!this.mClosed) {
                InputDeviceLightsManager.this.mGlobal.closeLightSession(InputDeviceLightsManager.this.mDeviceId, getToken());
                this.mClosed = true;
                this.mCloseGuard.close();
            }
            Reference.reachabilityFence(this);
        }

        protected void finalize() throws Throwable {
            try {
                this.mCloseGuard.warnIfOpen();
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
