package android.app;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.IActivityManager;
import android.app.IUiAutomationConnection;
import android.companion.virtual.VirtualDeviceManager;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.IPermissionManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.SurfaceControl;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.IAccessibilityManager;
import android.window.ScreenCapture;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntegerExpressionEvaluator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class UiAutomationConnection extends IUiAutomationConnection.Stub {
    private static final int INITIAL_FROZEN_ROTATION_UNSPECIFIED = -1;
    private static final String TAG = "UiAutomationConnection";
    private IAccessibilityServiceClient mClient;
    private boolean mIsShutdown;
    private int mOwningUid;
    private final IWindowManager mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
    private final IAccessibilityManager mAccessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
    private final IPermissionManager mPermissionManager = IPermissionManager.Stub.asInterface(ServiceManager.getService("permissionmgr"));
    private final IActivityManager mActivityManager = IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
    private final Object mLock = new Object();
    private final Binder mToken = new Binder();
    private int mInitialFrozenRotation = -1;

    public UiAutomationConnection() {
        Log.d(TAG, "Created on user " + Process.myUserHandle());
    }

    @Override // android.app.IUiAutomationConnection
    public void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int i) {
        if (iAccessibilityServiceClient == null) {
            throw new IllegalArgumentException("Client cannot be null!");
        }
        synchronized (this.mLock) {
            throwIfShutdownLocked();
            if (isConnectedLocked()) {
                throw new IllegalStateException("Already connected.");
            }
            this.mOwningUid = Binder.getCallingUid();
            registerUiTestAutomationServiceLocked(iAccessibilityServiceClient, Binder.getCallingUserHandle().getIdentifier(), i);
            storeRotationStateLocked();
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void disconnect() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            if (!isConnectedLocked()) {
                throw new IllegalStateException("Already disconnected.");
            }
            this.mOwningUid = -1;
            unregisterUiTestAutomationServiceLocked();
            restoreRotationStateLocked();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
    
        if (r0.getAction() == 1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0043, code lost:
    
        r4 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
    
        if (r3 == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004e, code lost:
    
        r0 = android.hardware.input.InputManagerGlobal.getInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0052, code lost:
    
        if (r8 == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0054, code lost:
    
        r8 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0057, code lost:
    
        r7 = r0.injectInputEvent(r7, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
    
        if (r2 == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:
    
        r6.mWindowManager.syncInputTransactions(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
    
        r6.rethrowFromSystemServer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006f, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0065, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0056, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0049, code lost:
    
        r6.mWindowManager.syncInputTransactions(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0066, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0070, code lost:
    
        android.os.Binder.restoreCallingIdentity(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0073, code lost:
    
        throw r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0041, code lost:
    
        if (r0.getAction() == 1) goto L25;
     */
    @Override // android.app.IUiAutomationConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean injectInputEvent(android.view.InputEvent r7, boolean r8, boolean r9) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            r6.throwIfCalledByNotTrustedUidLocked()     // Catch: java.lang.Throwable -> L74
            r6.throwIfShutdownLocked()     // Catch: java.lang.Throwable -> L74
            r6.throwIfNotConnectedLocked()     // Catch: java.lang.Throwable -> L74
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L74
            boolean r0 = r7 instanceof android.view.KeyEvent
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L28
            r0 = r7
            android.view.KeyEvent r0 = (android.view.KeyEvent) r0
            int r3 = r0.getAction()
            if (r3 != 0) goto L1e
            r3 = r2
            goto L1f
        L1e:
            r3 = r1
        L1f:
            int r0 = r0.getAction()
            if (r0 != r2) goto L26
            goto L43
        L26:
            r2 = r1
            goto L43
        L28:
            r0 = r7
            android.view.MotionEvent r0 = (android.view.MotionEvent) r0
            int r3 = r0.getAction()
            if (r3 == 0) goto L3c
            r3 = 8194(0x2002, float:1.1482E-41)
            boolean r3 = r0.isFromSource(r3)
            if (r3 == 0) goto L3a
            goto L3c
        L3a:
            r3 = r1
            goto L3d
        L3c:
            r3 = r2
        L3d:
            int r0 = r0.getAction()
            if (r0 != r2) goto L26
        L43:
            long r4 = android.os.Binder.clearCallingIdentity()
            if (r3 == 0) goto L4e
            android.view.IWindowManager r0 = r6.mWindowManager     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
            r0.syncInputTransactions(r9)     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
        L4e:
            android.hardware.input.InputManagerGlobal r0 = android.hardware.input.InputManagerGlobal.getInstance()     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
            if (r8 == 0) goto L56
            r8 = 2
            goto L57
        L56:
            r8 = r1
        L57:
            boolean r7 = r0.injectInputEvent(r7, r8)     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
            if (r2 == 0) goto L62
            android.view.IWindowManager r6 = r6.mWindowManager     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
            r6.syncInputTransactions(r9)     // Catch: java.lang.Throwable -> L66 android.os.RemoteException -> L68
        L62:
            android.os.Binder.restoreCallingIdentity(r4)
            return r7
        L66:
            r6 = move-exception
            goto L70
        L68:
            r6 = move-exception
            r6.rethrowFromSystemServer()     // Catch: java.lang.Throwable -> L66
            android.os.Binder.restoreCallingIdentity(r4)
            return r1
        L70:
            android.os.Binder.restoreCallingIdentity(r4)
            throw r6
        L74:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L74
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.UiAutomationConnection.injectInputEvent(android.view.InputEvent, boolean, boolean):boolean");
    }

    @Override // android.app.IUiAutomationConnection
    public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        this.mAccessibilityManager.injectInputEventToInputFilter(inputEvent);
    }

    @Override // android.app.IUiAutomationConnection
    public void syncInputTransactions(boolean z) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            this.mWindowManager.syncInputTransactions(z);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean setRotation(int i) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (i == -2) {
                this.mWindowManager.thawRotation("UiAutomationConnection#setRotation");
            } else {
                this.mWindowManager.freezeRotation(i, "UiAutomationConnection#setRotation");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeScreenshot(Rect rect, ScreenCapture.ScreenCaptureListener screenCaptureListener, int i) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mWindowManager.captureDisplay(i, new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build(), screenCaptureListener);
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeSurfaceControlScreenshot(SurfaceControl surfaceControl, ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setChildrenOnly(false).build(), screenCaptureListener) != 0) {
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean clearWindowContentFrameStats(int i) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IBinder windowToken = this.mAccessibilityManager.getWindowToken(i, callingUserId);
            if (windowToken != null) {
                return this.mWindowManager.clearWindowContentFrameStats(windowToken);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public WindowContentFrameStats getWindowContentFrameStats(int i) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IBinder windowToken = this.mAccessibilityManager.getWindowToken(i, callingUserId);
            if (windowToken != null) {
                return this.mWindowManager.getWindowContentFrameStats(windowToken);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SurfaceControl.clearAnimationFrameStats();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowAnimationFrameStats windowAnimationFrameStats = new WindowAnimationFrameStats();
            SurfaceControl.getAnimationFrameStats(windowAnimationFrameStats);
            return windowAnimationFrameStats;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.grantRuntimePermission(str, str2, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void revokeRuntimePermission(String str, String str2, int i) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.revokeRuntimePermission(str, str2, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, i, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void adoptShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.startDelegateShellPermissionIdentity(i, strArr);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void dropShellPermissionIdentity() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.stopDelegateShellPermissionIdentity();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public List<String> getAdoptedShellPermissions() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getDelegatedShellPermissions();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void addOverridePermissionState(int i, String str, int i2) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.addOverridePermissionState(callingUid, i, str, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void removeOverridePermissionState(int i, String str) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.removeOverridePermissionState(callingUid, i, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearOverridePermissionStates(int i) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.clearOverridePermissionStates(callingUid, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearAllOverridePermissionStates() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.clearAllOverridePermissionStates(callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class Repeater implements Runnable {
        private final InputStream readFrom;
        private final OutputStream writeTo;

        public Repeater(UiAutomationConnection uiAutomationConnection, InputStream inputStream, OutputStream outputStream) {
            this.readFrom = inputStream;
            this.writeTo = outputStream;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = this.readFrom.read(bArr);
                    if (read < 0) {
                        return;
                    }
                    this.writeTo.write(bArr, 0, read);
                    this.writeTo.flush();
                }
            } catch (IOException unused) {
            } finally {
                IoUtils.closeQuietly(this.readFrom);
                IoUtils.closeQuietly(this.writeTo);
            }
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommand(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
        executeShellCommandWithStderr(str, parcelFileDescriptor, parcelFileDescriptor2, null);
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommandWithStderr(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            handleExecuteShellCommandProcess(Runtime.getRuntime().exec(str), parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3);
        } catch (IOException e) {
            IoUtils.closeQuietly(parcelFileDescriptor);
            IoUtils.closeQuietly(parcelFileDescriptor2);
            IoUtils.closeQuietly(parcelFileDescriptor3);
            throw new RuntimeException("Error running shell command '" + str + "'", e);
        } catch (IllegalArgumentException | NullPointerException | SecurityException e2) {
            IoUtils.closeQuietly(parcelFileDescriptor);
            IoUtils.closeQuietly(parcelFileDescriptor2);
            IoUtils.closeQuietly(parcelFileDescriptor3);
            throw e2;
        }
    }

    private void handleExecuteShellCommandProcess(final Process process, final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2, final ParcelFileDescriptor parcelFileDescriptor3) {
        final Thread thread;
        final Thread thread2;
        final Thread thread3;
        if (parcelFileDescriptor != null) {
            Thread thread4 = new Thread(new Repeater(this, process.getInputStream(), new FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
            thread4.start();
            thread = thread4;
        } else {
            thread = null;
        }
        if (parcelFileDescriptor2 != null) {
            Thread thread5 = new Thread(new Repeater(this, new FileInputStream(parcelFileDescriptor2.getFileDescriptor()), process.getOutputStream()));
            thread5.start();
            thread2 = thread5;
        } else {
            thread2 = null;
        }
        if (parcelFileDescriptor3 != null) {
            Thread thread6 = new Thread(new Repeater(this, process.getErrorStream(), new FileOutputStream(parcelFileDescriptor3.getFileDescriptor())));
            thread6.start();
            thread3 = thread6;
        } else {
            thread3 = null;
        }
        new Thread(new Runnable(this) { // from class: android.app.UiAutomationConnection.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread thread7 = thread2;
                    if (thread7 != null) {
                        thread7.join();
                    }
                    Thread thread8 = thread;
                    if (thread8 != null) {
                        thread8.join();
                    }
                    Thread thread9 = thread3;
                    if (thread9 != null) {
                        thread9.join();
                    }
                } catch (InterruptedException unused) {
                    Log.e(UiAutomationConnection.TAG, "At least one of the threads was interrupted");
                }
                IoUtils.closeQuietly(parcelFileDescriptor);
                IoUtils.closeQuietly(parcelFileDescriptor2);
                IoUtils.closeQuietly(parcelFileDescriptor3);
                process.destroy();
            }
        }).start();
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommandArrayWithStderr(String[] strArr, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            handleExecuteShellCommandProcess(Runtime.getRuntime().exec(strArr), parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3);
        } catch (IOException e) {
            throw new RuntimeException("Error running shell command '" + String.join(" ", strArr) + "'", e);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void shutdown() {
        synchronized (this.mLock) {
            if (isConnectedLocked()) {
                throwIfCalledByNotTrustedUidLocked();
            }
            throwIfShutdownLocked();
            this.mIsShutdown = true;
            if (isConnectedLocked()) {
                disconnect();
            }
        }
    }

    private void registerUiTestAutomationServiceLocked(IAccessibilityServiceClient iAccessibilityServiceClient, int i, int i2) {
        int i3;
        IAccessibilityManager asInterface = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = -1;
        accessibilityServiceInfo.feedbackType = 16;
        accessibilityServiceInfo.flags |= IntegerExpressionEvaluator.I_DECR;
        accessibilityServiceInfo.setCapabilities(11);
        if ((i2 & 4) == 0) {
            accessibilityServiceInfo.setAccessibilityTool(true);
        }
        try {
            i3 = i;
            try {
                asInterface.registerUiTestAutomationService(this.mToken, iAccessibilityServiceClient, accessibilityServiceInfo, i3, i2);
                this.mClient = iAccessibilityServiceClient;
            } catch (RemoteException e) {
                e = e;
                throw new IllegalStateException("Error while registering UiTestAutomationService for user " + i3 + MediaMetrics.SEPARATOR, e);
            }
        } catch (RemoteException e2) {
            e = e2;
            i3 = i;
        }
    }

    private void unregisterUiTestAutomationServiceLocked() {
        try {
            IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE)).unregisterUiTestAutomationService(this.mClient);
            this.mClient = null;
        } catch (RemoteException e) {
            throw new IllegalStateException("Error while unregistering UiTestAutomationService", e);
        }
    }

    private void storeRotationStateLocked() {
        try {
            if (this.mWindowManager.isRotationFrozen()) {
                this.mInitialFrozenRotation = this.mWindowManager.getDefaultDisplayRotation();
            }
        } catch (RemoteException unused) {
        }
    }

    private void restoreRotationStateLocked() {
        try {
            int i = this.mInitialFrozenRotation;
            if (i != -1) {
                this.mWindowManager.freezeRotation(i, "UiAutomationConnection#restoreRotationStateLocked");
            } else {
                this.mWindowManager.thawRotation("UiAutomationConnection#restoreRotationStateLocked");
            }
        } catch (RemoteException unused) {
        }
    }

    private boolean isConnectedLocked() {
        return this.mClient != null;
    }

    private void throwIfShutdownLocked() {
        if (this.mIsShutdown) {
            throw new IllegalStateException("Connection shutdown!");
        }
    }

    private void throwIfNotConnectedLocked() {
        if (!isConnectedLocked()) {
            throw new IllegalStateException("Not connected!");
        }
    }

    private void throwIfCalledByNotTrustedUidLocked() {
        int callingUid = Binder.getCallingUid();
        int i = this.mOwningUid;
        if (callingUid != i && i != 1000 && callingUid != 0) {
            throw new SecurityException("Calling from not trusted UID!");
        }
    }
}
