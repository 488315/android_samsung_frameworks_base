package android.hardware.lights;

import android.content.Context;
import android.hardware.lights.ILightsManager;
import android.hardware.lights.LightsManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.CloseGuard;
import com.android.internal.util.Preconditions;
import java.lang.ref.Reference;
import java.util.List;

/* loaded from: classes2.dex */
public final class SystemLightsManager extends LightsManager {
    private static final String TAG = "LightsManager";
    private final ILightsManager mService;

    public SystemLightsManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this(context, ILightsManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.LIGHTS_SERVICE)));
    }

    public SystemLightsManager(Context context, ILightsManager iLightsManager) {
        this.mService = (ILightsManager) Preconditions.checkNotNull(iLightsManager);
    }

    @Override // android.hardware.lights.LightsManager
    public List<Light> getLights() {
        try {
            return this.mService.getLights();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public LightState getLightState(Light light) {
        Preconditions.checkNotNull(light);
        try {
            return this.mService.getLightState(light.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public LightsManager.LightsSession openSession() {
        try {
            SystemLightsSession systemLightsSession = new SystemLightsSession();
            this.mService.openSession(systemLightsSession.getToken(), 0);
            return systemLightsSession;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.lights.LightsManager
    public LightsManager.LightsSession openSession(int i) {
        try {
            SystemLightsSession systemLightsSession = new SystemLightsSession();
            this.mService.openSession(systemLightsSession.getToken(), i);
            return systemLightsSession;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final class SystemLightsSession extends LightsManager.LightsSession implements AutoCloseable {
        private final CloseGuard mCloseGuard;
        private boolean mClosed;

        private SystemLightsSession() {
            CloseGuard closeGuard = new CloseGuard();
            this.mCloseGuard = closeGuard;
            this.mClosed = false;
            closeGuard.open("SystemLightsSession.close");
        }

        @Override // android.hardware.lights.LightsManager.LightsSession
        public void requestLights(LightsRequest lightsRequest) {
            Preconditions.checkNotNull(lightsRequest);
            if (this.mClosed) {
                return;
            }
            try {
                List<Integer> lights = lightsRequest.getLights();
                List<LightState> lightStates = lightsRequest.getLightStates();
                int[] iArr = new int[lights.size()];
                for (int i = 0; i < lights.size(); i++) {
                    iArr[i] = lights.get(i).intValue();
                }
                LightState[] lightStateArr = new LightState[lightStates.size()];
                for (int i2 = 0; i2 < lightStates.size(); i2++) {
                    lightStateArr[i2] = lightStates.get(i2);
                }
                SystemLightsManager.this.mService.setLightStates(getToken(), iArr, lightStateArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.hardware.lights.LightsManager.LightsSession, java.lang.AutoCloseable
        public void close() {
            if (!this.mClosed) {
                try {
                    SystemLightsManager.this.mService.closeSession(getToken());
                    this.mClosed = true;
                    this.mCloseGuard.close();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
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
