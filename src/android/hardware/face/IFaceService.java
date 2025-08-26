package android.hardware.face;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.Surface;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFaceService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.face.IFaceService";

    public static class Default implements IFaceService {
        @Override // android.hardware.face.IFaceService
        public void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public long authenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelFaceDetect(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public long detectFace(IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public long enroll(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public long enrollRemotely(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public void generateChallenge(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public long getAuthenticatorId(int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public List<Face> getEnrolledFaces(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public void getFeature(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public int getLockoutModeForUser(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.face.IFaceService
        public FaceSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public List<FaceSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public boolean hasEnrolledFaces(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public boolean isHardwareDetected(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void prepareForAuthentication(boolean z, IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerAuthenticators(FaceSensorConfigurations faceSensorConfigurations) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void removeAll(IBinder iBinder, int i, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void scheduleWatchdog() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public long semAuthenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Bundle bundle, byte[] bArr) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public long semAuthenticateExt(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Surface surface, byte[] bArr) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public String semGetInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public int semGetRemainingLockoutTime(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.face.IFaceService
        public int semGetSecurityLevel(boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.face.IFaceService
        public boolean semIsEnrollSession() throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public boolean semIsFrameworkHandleLockout() throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public boolean semIsSessionClose() throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void semPauseAuth() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void semPauseEnroll() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public boolean semResetAuthenticationTimeout() throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void semResumeAuth() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void semResumeEnroll() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void semSessionClose(int i) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void semSessionOpen() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public boolean semShouldRemoveTemplate() throws RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void setFeature(IBinder iBinder, int i, int i2, boolean z, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void startPreparedClient(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }
    }

    void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws RemoteException;

    void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException;

    long authenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException;

    void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException;

    void cancelEnrollment(IBinder iBinder, long j) throws RemoteException;

    void cancelFaceDetect(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException;

    long detectFace(IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException;

    byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException;

    long enroll(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) throws RemoteException;

    long enrollRemotely(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) throws RemoteException;

    void generateChallenge(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException;

    long getAuthenticatorId(int i, int i2) throws RemoteException;

    List<Face> getEnrolledFaces(int i, int i2, String str) throws RemoteException;

    void getFeature(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException;

    int getLockoutModeForUser(int i, int i2) throws RemoteException;

    FaceSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException;

    List<FaceSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException;

    boolean hasEnrolledFaces(int i, int i2, String str) throws RemoteException;

    void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException;

    boolean isHardwareDetected(int i, String str) throws RemoteException;

    void prepareForAuthentication(boolean z, IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws RemoteException;

    void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

    void registerAuthenticators(FaceSensorConfigurations faceSensorConfigurations) throws RemoteException;

    void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException;

    void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException;

    void removeAll(IBinder iBinder, int i, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException;

    void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException;

    void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException;

    void scheduleWatchdog() throws RemoteException;

    long semAuthenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Bundle bundle, byte[] bArr) throws RemoteException;

    long semAuthenticateExt(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Surface surface, byte[] bArr) throws RemoteException;

    String semGetInfo(int i) throws RemoteException;

    int semGetRemainingLockoutTime(int i) throws RemoteException;

    int semGetSecurityLevel(boolean z) throws RemoteException;

    boolean semIsEnrollSession() throws RemoteException;

    boolean semIsFrameworkHandleLockout() throws RemoteException;

    boolean semIsSessionClose() throws RemoteException;

    void semPauseAuth() throws RemoteException;

    void semPauseEnroll() throws RemoteException;

    boolean semResetAuthenticationTimeout() throws RemoteException;

    void semResumeAuth() throws RemoteException;

    void semResumeEnroll() throws RemoteException;

    void semSessionClose(int i) throws RemoteException;

    void semSessionOpen() throws RemoteException;

    boolean semShouldRemoveTemplate() throws RemoteException;

    void setFeature(IBinder iBinder, int i, int i2, boolean z, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException;

    void startPreparedClient(int i, int i2) throws RemoteException;

    void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IFaceService {
        static final int TRANSACTION_addAuthenticatorsRegisteredCallback = 30;
        static final int TRANSACTION_addLockoutResetCallback = 26;
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 9;
        static final int TRANSACTION_cancelAuthenticationFromService = 11;
        static final int TRANSACTION_cancelEnrollment = 14;
        static final int TRANSACTION_cancelFaceDetect = 10;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_detectFace = 6;
        static final int TRANSACTION_dumpSensorServiceStateProto = 2;
        static final int TRANSACTION_enroll = 12;
        static final int TRANSACTION_enrollRemotely = 13;
        static final int TRANSACTION_generateChallenge = 19;
        static final int TRANSACTION_getAuthenticatorId = 24;
        static final int TRANSACTION_getEnrolledFaces = 17;
        static final int TRANSACTION_getFeature = 28;
        static final int TRANSACTION_getLockoutModeForUser = 22;
        static final int TRANSACTION_getSensorProperties = 4;
        static final int TRANSACTION_getSensorPropertiesInternal = 3;
        static final int TRANSACTION_hasEnrolledFaces = 21;
        static final int TRANSACTION_invalidateAuthenticatorId = 23;
        static final int TRANSACTION_isHardwareDetected = 18;
        static final int TRANSACTION_prepareForAuthentication = 7;
        static final int TRANSACTION_registerAuthenticationStateListener = 31;
        static final int TRANSACTION_registerAuthenticators = 29;
        static final int TRANSACTION_registerBiometricStateListener = 33;
        static final int TRANSACTION_remove = 15;
        static final int TRANSACTION_removeAll = 16;
        static final int TRANSACTION_resetLockout = 25;
        static final int TRANSACTION_revokeChallenge = 20;
        static final int TRANSACTION_scheduleWatchdog = 34;
        static final int TRANSACTION_semAuthenticate = 35;
        static final int TRANSACTION_semAuthenticateExt = 36;
        static final int TRANSACTION_semGetInfo = 42;
        static final int TRANSACTION_semGetRemainingLockoutTime = 49;
        static final int TRANSACTION_semGetSecurityLevel = 47;
        static final int TRANSACTION_semIsEnrollSession = 37;
        static final int TRANSACTION_semIsFrameworkHandleLockout = 48;
        static final int TRANSACTION_semIsSessionClose = 46;
        static final int TRANSACTION_semPauseAuth = 40;
        static final int TRANSACTION_semPauseEnroll = 38;
        static final int TRANSACTION_semResetAuthenticationTimeout = 43;
        static final int TRANSACTION_semResumeAuth = 41;
        static final int TRANSACTION_semResumeEnroll = 39;
        static final int TRANSACTION_semSessionClose = 45;
        static final int TRANSACTION_semSessionOpen = 44;
        static final int TRANSACTION_semShouldRemoveTemplate = 50;
        static final int TRANSACTION_setFeature = 27;
        static final int TRANSACTION_startPreparedClient = 8;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 32;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 49;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IFaceService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFaceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFaceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFaceService)) {
                return (IFaceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createTestSession";
                case 2:
                    return "dumpSensorServiceStateProto";
                case 3:
                    return "getSensorPropertiesInternal";
                case 4:
                    return "getSensorProperties";
                case 5:
                    return "authenticate";
                case 6:
                    return "detectFace";
                case 7:
                    return "prepareForAuthentication";
                case 8:
                    return "startPreparedClient";
                case 9:
                    return "cancelAuthentication";
                case 10:
                    return "cancelFaceDetect";
                case 11:
                    return "cancelAuthenticationFromService";
                case 12:
                    return "enroll";
                case 13:
                    return "enrollRemotely";
                case 14:
                    return "cancelEnrollment";
                case 15:
                    return "remove";
                case 16:
                    return "removeAll";
                case 17:
                    return "getEnrolledFaces";
                case 18:
                    return "isHardwareDetected";
                case 19:
                    return "generateChallenge";
                case 20:
                    return "revokeChallenge";
                case 21:
                    return "hasEnrolledFaces";
                case 22:
                    return "getLockoutModeForUser";
                case 23:
                    return "invalidateAuthenticatorId";
                case 24:
                    return "getAuthenticatorId";
                case 25:
                    return "resetLockout";
                case 26:
                    return "addLockoutResetCallback";
                case 27:
                    return "setFeature";
                case 28:
                    return "getFeature";
                case 29:
                    return "registerAuthenticators";
                case 30:
                    return "addAuthenticatorsRegisteredCallback";
                case 31:
                    return "registerAuthenticationStateListener";
                case 32:
                    return "unregisterAuthenticationStateListener";
                case 33:
                    return "registerBiometricStateListener";
                case 34:
                    return "scheduleWatchdog";
                case 35:
                    return "semAuthenticate";
                case 36:
                    return "semAuthenticateExt";
                case 37:
                    return "semIsEnrollSession";
                case 38:
                    return "semPauseEnroll";
                case 39:
                    return "semResumeEnroll";
                case 40:
                    return "semPauseAuth";
                case 41:
                    return "semResumeAuth";
                case 42:
                    return "semGetInfo";
                case 43:
                    return "semResetAuthenticationTimeout";
                case 44:
                    return "semSessionOpen";
                case 45:
                    return "semSessionClose";
                case 46:
                    return "semIsSessionClose";
                case 47:
                    return "semGetSecurityLevel";
                case 48:
                    return "semIsFrameworkHandleLockout";
                case 49:
                    return "semGetRemainingLockoutTime";
                case 50:
                    return "semShouldRemoveTemplate";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFaceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFaceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    ITestSessionCallback iTestSessionCallbackAsInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession iTestSessionCreateTestSession = createTestSession(i3, iTestSessionCallbackAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iTestSessionCreateTestSession);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] bArrDumpSensorServiceStateProto = dumpSensorServiceStateProto(i4, z);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrDumpSensorServiceStateProto);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FaceSensorPropertiesInternal sensorProperties = getSensorProperties(i5, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    long j = parcel.readLong();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAuthenticate = authenticate(strongBinder, j, iFaceServiceReceiverAsInterface, faceAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAuthenticate);
                    return true;
                case 6:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface2 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions2 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jDetectFace = detectFace(strongBinder2, iFaceServiceReceiverAsInterface2, faceAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(jDetectFace);
                    return true;
                case 7:
                    boolean z2 = parcel.readBoolean();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    long j2 = parcel.readLong();
                    IBiometricSensorReceiver iBiometricSensorReceiverAsInterface = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions3 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    long j3 = parcel.readLong();
                    int i6 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(z2, strongBinder3, j2, iBiometricSensorReceiverAsInterface, faceAuthenticateOptions3, j3, i6, z3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(strongBinder4, string4, j4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    String string5 = parcel.readString();
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFaceDetect(strongBinder5, string5, j5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i9 = parcel.readInt();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string6 = parcel.readString();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(i9, strongBinder6, string6, j6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i10 = parcel.readInt();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface3 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string7 = parcel.readString();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    FaceEnrollOptions faceEnrollOptions = (FaceEnrollOptions) parcel.readTypedObject(FaceEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jEnroll = enroll(i10, strongBinder7, bArrCreateByteArray, iFaceServiceReceiverAsInterface3, string7, iArrCreateIntArray, surface, z4, faceEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(jEnroll);
                    return true;
                case 13:
                    int i11 = parcel.readInt();
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface4 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string8 = parcel.readString();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long jEnrollRemotely = enrollRemotely(i11, strongBinder8, bArrCreateByteArray2, iFaceServiceReceiverAsInterface4, string8, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLong(jEnrollRemotely);
                    return true;
                case 14:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(strongBinder9, j7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface5 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(strongBinder10, i12, i13, iFaceServiceReceiverAsInterface5, string9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int i14 = parcel.readInt();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface6 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(strongBinder11, i14, iFaceServiceReceiverAsInterface6, string10);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Face> enrolledFaces = getEnrolledFaces(i15, i16, string11);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFaces, 1);
                    return true;
                case 18:
                    int i17 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHardwareDetected = isHardwareDetected(i17, string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareDetected);
                    return true;
                case 19:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface7 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(strongBinder12, i18, i19, iFaceServiceReceiverAsInterface7, string13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    String string14 = parcel.readString();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(strongBinder13, i20, i21, string14, j8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledFaces = hasEnrolledFaces(i22, i23, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledFaces);
                    return true;
                case 22:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 23:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    IInvalidationCallback iInvalidationCallbackAsInterface = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(i26, i27, iInvalidationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(i28, i29);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 25:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(strongBinder14, i30, i31, bArrCreateByteArray3, string16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallbackAsInterface = IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(iBiometricServiceLockoutResetCallbackAsInterface, string17);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface8 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFeature(strongBinder15, i32, i33, z5, bArrCreateByteArray4, iFaceServiceReceiverAsInterface8, string18);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface9 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getFeature(strongBinder16, i34, i35, iFaceServiceReceiverAsInterface9, string19);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    FaceSensorConfigurations faceSensorConfigurations = (FaceSensorConfigurations) parcel.readTypedObject(FaceSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(faceSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallbackAsInterface = IFaceAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(iFaceAuthenticatorsRegisteredCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    AuthenticationStateListener authenticationStateListenerAsInterface = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(authenticationStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    AuthenticationStateListener authenticationStateListenerAsInterface2 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(authenticationStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBiometricStateListener iBiometricStateListenerAsInterface = IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(iBiometricStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    scheduleWatchdog();
                    return true;
                case 35:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    long j9 = parcel.readLong();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface10 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions4 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    long jSemAuthenticate = semAuthenticate(strongBinder17, j9, iFaceServiceReceiverAsInterface10, faceAuthenticateOptions4, bundle, bArrCreateByteArray5);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSemAuthenticate);
                    return true;
                case 36:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    long j10 = parcel.readLong();
                    IFaceServiceReceiver iFaceServiceReceiverAsInterface11 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions5 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    long jSemAuthenticateExt = semAuthenticateExt(strongBinder18, j10, iFaceServiceReceiverAsInterface11, faceAuthenticateOptions5, surface2, bArrCreateByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSemAuthenticateExt);
                    return true;
                case 37:
                    boolean zSemIsEnrollSession = semIsEnrollSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsEnrollSession);
                    return true;
                case 38:
                    semPauseEnroll();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    semResumeEnroll();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    semPauseAuth();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    semResumeAuth();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strSemGetInfo = semGetInfo(i36);
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetInfo);
                    return true;
                case 43:
                    boolean zSemResetAuthenticationTimeout = semResetAuthenticationTimeout();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemResetAuthenticationTimeout);
                    return true;
                case 44:
                    semSessionOpen();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSessionClose(i37);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    boolean zSemIsSessionClose = semIsSessionClose();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsSessionClose);
                    return true;
                case 47:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iSemGetSecurityLevel = semGetSecurityLevel(z6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetSecurityLevel);
                    return true;
                case 48:
                    boolean zSemIsFrameworkHandleLockout = semIsFrameworkHandleLockout();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsFrameworkHandleLockout);
                    return true;
                case 49:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSemGetRemainingLockoutTime = semGetRemainingLockoutTime(i38);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetRemainingLockoutTime);
                    return true;
                case 50:
                    boolean zSemShouldRemoveTemplate = semShouldRemoveTemplate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemShouldRemoveTemplate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFaceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFaceService.DESCRIPTOR;
            }

            @Override // android.hardware.face.IFaceService
            public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iTestSessionCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ITestSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public List<FaceSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public FaceSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FaceSensorPropertiesInternal) parcelObtain2.readTypedObject(FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long authenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long detectFace(IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iBiometricSensorReceiver);
                    parcelObtain.writeTypedObject(faceAuthenticateOptions, 0);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void startPreparedClient(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelFaceDetect(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long enroll(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(faceEnrollOptions, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long enrollRemotely(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void removeAll(IBinder iBinder, int i, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public List<Face> getEnrolledFaces(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Face.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean isHardwareDetected(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void generateChallenge(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean hasEnrolledFaces(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int getLockoutModeForUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long getAuthenticatorId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void setFeature(IBinder iBinder, int i, int i2, boolean z, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void getFeature(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticators(FaceSensorConfigurations faceSensorConfigurations) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(faceSensorConfigurations, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFaceAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void scheduleWatchdog() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long semAuthenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Bundle bundle, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeTypedObject(faceAuthenticateOptions, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long semAuthenticateExt(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Surface surface, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iFaceServiceReceiver);
                    parcelObtain.writeTypedObject(faceAuthenticateOptions, 0);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsEnrollSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semPauseEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semResumeEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semPauseAuth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semResumeAuth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public String semGetInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semResetAuthenticationTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semSessionOpen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semSessionClose(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsSessionClose() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int semGetSecurityLevel(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsFrameworkHandleLockout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int semGetRemainingLockoutTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semShouldRemoveTemplate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void dumpSensorServiceStateProto_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorPropertiesInternal_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void authenticate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void detectFace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareForAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void startPreparedClient_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelFaceDetect_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthenticationFromService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void enroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void enrollRemotely_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void cancelEnrollment_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void removeAll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getEnrolledFaces_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void isHardwareDetected_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void generateChallenge_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void revokeChallenge_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledFaces_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLockoutModeForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void addLockoutResetCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setFeature_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getFeature_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticators_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticationStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unregisterAuthenticationStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void scheduleWatchdog_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void semIsEnrollSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semPauseEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semResumeEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semPauseAuth_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semResumeAuth_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semGetInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semResetAuthenticationTimeout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semSessionOpen_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semSessionClose_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semIsSessionClose_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semGetSecurityLevel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semIsFrameworkHandleLockout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semGetRemainingLockoutTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void semShouldRemoveTemplate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }
    }
}
