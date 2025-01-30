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

    InputDeviceLightsManager(int deviceId) {
        this.mDeviceId = deviceId;
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
        LightsManager.LightsSession session = new InputDeviceLightsSession();
        this.mGlobal.openLightSession(this.mDeviceId, this.mPackageName, session.getToken());
        return session;
    }

    @Override // android.hardware.lights.LightsManager
    public LightsManager.LightsSession openSession(int priority) {
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
        public void requestLights(LightsRequest request) {
            Preconditions.checkNotNull(request);
            Preconditions.checkArgument(!this.mClosed);
            InputDeviceLightsManager.this.mGlobal.requestLights(InputDeviceLightsManager.this.mDeviceId, request, getToken());
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
