package android.hardware.biometrics;

import android.content.Context;
import android.hardware.biometrics.ITestSessionCallback;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class BiometricTestSession implements AutoCloseable {
    private static final String BASE_TAG = "BiometricTestSession";
    private CountDownLatch mCloseLatch;
    private final int mSensorId;
    private ITestSession mTestSession;
    private final List<ITestSession> mTestSessionsForAllSensors = new ArrayList();
    private final ArraySet<Integer> mTestedUsers;
    private final ArraySet<Integer> mUsersCleaningUp;

    public interface TestSessionProvider {
        ITestSession createTestSession(Context context, int i, ITestSessionCallback iTestSessionCallback) throws RemoteException;
    }

    private class TestSessionCallbackIml extends ITestSessionCallback.Stub {
        private final int mSensorId;

        private TestSessionCallbackIml(int i) {
            this.mSensorId = i;
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupStarted(int i) {
            Log.d(BiometricTestSession.this.getTag(), "onCleanupStarted, sensor: " + this.mSensorId + ", userId: " + i);
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupFinished(int i) {
            Log.d(BiometricTestSession.this.getTag(), "onCleanupFinished, sensor: " + this.mSensorId + ", userId: " + i + ", remaining users: " + BiometricTestSession.this.mUsersCleaningUp.size());
            BiometricTestSession.this.mUsersCleaningUp.remove(Integer.valueOf(i));
            if (!BiometricTestSession.this.mUsersCleaningUp.isEmpty() || BiometricTestSession.this.mCloseLatch == null) {
                return;
            }
            Log.d(BiometricTestSession.this.getTag(), "counting down");
            BiometricTestSession.this.mCloseLatch.countDown();
        }
    }

    public BiometricTestSession(Context context, List<SensorProperties> list, int i, TestSessionProvider testSessionProvider) throws RemoteException {
        this.mSensorId = i;
        Iterator<SensorProperties> it = list.iterator();
        while (it.hasNext()) {
            int sensorId = it.next().getSensorId();
            ITestSession createTestSession = testSessionProvider.createTestSession(context, sensorId, new TestSessionCallbackIml(sensorId));
            this.mTestSessionsForAllSensors.add(createTestSession);
            if (sensorId == i) {
                this.mTestSession = createTestSession;
            }
        }
        this.mTestedUsers = new ArraySet<>();
        this.mUsersCleaningUp = new ArraySet<>();
        setTestHalEnabled(true);
        Log.d(getTag(), "Opening BiometricTestSession");
    }

    private void setTestHalEnabled(boolean z) {
        try {
            for (ITestSession iTestSession : this.mTestSessionsForAllSensors) {
                Log.w(getTag(), "setTestHalEnabled, sensor: " + iTestSession.getSensorId() + " enabled: " + z);
                iTestSession.setTestHalEnabled(z);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startEnroll(int i) {
        try {
            this.mTestedUsers.add(Integer.valueOf(i));
            this.mTestSession.startEnroll(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishEnroll(int i) {
        try {
            this.mTestedUsers.add(Integer.valueOf(i));
            this.mTestSession.finishEnroll(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void acceptAuthentication(int i) {
        try {
            this.mTestSession.acceptAuthentication(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rejectAuthentication(int i) {
        try {
            this.mTestSession.rejectAuthentication(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAcquired(int i, int i2) {
        try {
            this.mTestSession.notifyAcquired(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyError(int i, int i2) {
        try {
            this.mTestSession.notifyError(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cleanupInternalState(int i) {
        try {
            if (this.mUsersCleaningUp.contains(Integer.valueOf(i))) {
                Log.w(getTag(), "Cleanup already in progress for user: " + i);
            }
            for (ITestSession iTestSession : this.mTestSessionsForAllSensors) {
                this.mUsersCleaningUp.add(Integer.valueOf(i));
                Log.d(getTag(), "cleanupInternalState for sensor: " + iTestSession.getSensorId());
                this.mCloseLatch = new CountDownLatch(1);
                iTestSession.cleanupInternalState(i);
                try {
                    Log.d(getTag(), "Awaiting latch...");
                    this.mCloseLatch.await(3L, TimeUnit.SECONDS);
                    Log.d(getTag(), "Finished awaiting");
                } catch (InterruptedException e) {
                    Log.e(getTag(), "Latch interrupted", e);
                }
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        Log.d(getTag(), "Close, mTestedUsers size; " + this.mTestedUsers.size());
        if (!this.mTestedUsers.isEmpty()) {
            Iterator<Integer> it = this.mTestedUsers.iterator();
            while (it.hasNext()) {
                cleanupInternalState(it.next().intValue());
            }
        }
        if (!this.mUsersCleaningUp.isEmpty()) {
            Log.e(getTag(), "Cleanup not finished before shutdown - pending: " + this.mUsersCleaningUp.size());
        }
        setTestHalEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTag() {
        return "BiometricTestSession_" + this.mSensorId;
    }

    public void notifyVendorAcquired(int i, int i2) {
        try {
            this.mTestSession.notifyVendorAcquired(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVendorError(int i, int i2) {
        try {
            this.mTestSession.notifyVendorError(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
