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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFaceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFaceService)) {
                return (IFaceService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    ITestSessionCallback asInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession createTestSession = createTestSession(readInt, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] dumpSensorServiceStateProto = dumpSensorServiceStateProto(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(dumpSensorServiceStateProto);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FaceSensorPropertiesInternal sensorProperties = getSensorProperties(readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    IFaceServiceReceiver asInterface2 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, asInterface2, faceAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 6:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IFaceServiceReceiver asInterface3 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions2 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long detectFace = detectFace(readStrongBinder2, asInterface3, faceAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(detectFace);
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    long readLong2 = parcel.readLong();
                    IBiometricSensorReceiver asInterface4 = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions3 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readBoolean2, readStrongBinder3, readLong2, asInterface4, faceAuthenticateOptions3, readLong3, readInt4, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder4, readString4, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    String readString5 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFaceDetect(readStrongBinder5, readString5, readLong5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString6 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readInt7, readStrongBinder6, readString6, readLong6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    byte[] createByteArray = parcel.createByteArray();
                    IFaceServiceReceiver asInterface5 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString7 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    FaceEnrollOptions faceEnrollOptions = (FaceEnrollOptions) parcel.readTypedObject(FaceEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long enroll = enroll(readInt8, readStrongBinder7, createByteArray, asInterface5, readString7, createIntArray, surface, readBoolean4, faceEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(enroll);
                    return true;
                case 13:
                    int readInt9 = parcel.readInt();
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    byte[] createByteArray2 = parcel.createByteArray();
                    IFaceServiceReceiver asInterface6 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString8 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long enrollRemotely = enrollRemotely(readInt9, readStrongBinder8, createByteArray2, asInterface6, readString8, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLong(enrollRemotely);
                    return true;
                case 14:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(readStrongBinder9, readLong7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    IFaceServiceReceiver asInterface7 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder10, readInt10, readInt11, asInterface7, readString9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt12 = parcel.readInt();
                    IFaceServiceReceiver asInterface8 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readStrongBinder11, readInt12, asInterface8, readString10);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Face> enrolledFaces = getEnrolledFaces(readInt13, readInt14, readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFaces, 1);
                    return true;
                case 18:
                    int readInt15 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readInt15, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 19:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    IFaceServiceReceiver asInterface9 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(readStrongBinder12, readInt16, readInt17, asInterface9, readString13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    String readString14 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readStrongBinder13, readInt18, readInt19, readString14, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFaces = hasEnrolledFaces(readInt20, readInt21, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFaces);
                    return true;
                case 22:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 23:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    IInvalidationCallback asInterface10 = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt24, readInt25, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 25:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(readStrongBinder14, readInt28, readInt29, createByteArray3, readString16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBiometricServiceLockoutResetCallback asInterface11 = IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(asInterface11, readString17);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    byte[] createByteArray4 = parcel.createByteArray();
                    IFaceServiceReceiver asInterface12 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFeature(readStrongBinder15, readInt30, readInt31, readBoolean5, createByteArray4, asInterface12, readString18);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    IFaceServiceReceiver asInterface13 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getFeature(readStrongBinder16, readInt32, readInt33, asInterface13, readString19);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    FaceSensorConfigurations faceSensorConfigurations = (FaceSensorConfigurations) parcel.readTypedObject(FaceSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(faceSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IFaceAuthenticatorsRegisteredCallback asInterface14 = IFaceAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    AuthenticationStateListener asInterface15 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    AuthenticationStateListener asInterface16 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBiometricStateListener asInterface17 = IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    scheduleWatchdog();
                    return true;
                case 35:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    long readLong9 = parcel.readLong();
                    IFaceServiceReceiver asInterface18 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions4 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    long semAuthenticate = semAuthenticate(readStrongBinder17, readLong9, asInterface18, faceAuthenticateOptions4, bundle, createByteArray5);
                    parcel2.writeNoException();
                    parcel2.writeLong(semAuthenticate);
                    return true;
                case 36:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    long readLong10 = parcel.readLong();
                    IFaceServiceReceiver asInterface19 = IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FaceAuthenticateOptions faceAuthenticateOptions5 = (FaceAuthenticateOptions) parcel.readTypedObject(FaceAuthenticateOptions.CREATOR);
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    long semAuthenticateExt = semAuthenticateExt(readStrongBinder18, readLong10, asInterface19, faceAuthenticateOptions5, surface2, createByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeLong(semAuthenticateExt);
                    return true;
                case 37:
                    boolean semIsEnrollSession = semIsEnrollSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsEnrollSession);
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
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String semGetInfo = semGetInfo(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeString(semGetInfo);
                    return true;
                case 43:
                    boolean semResetAuthenticationTimeout = semResetAuthenticationTimeout();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semResetAuthenticationTimeout);
                    return true;
                case 44:
                    semSessionOpen();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSessionClose(readInt35);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    boolean semIsSessionClose = semIsSessionClose();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsSessionClose);
                    return true;
                case 47:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int semGetSecurityLevel = semGetSecurityLevel(readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetSecurityLevel);
                    return true;
                case 48:
                    boolean semIsFrameworkHandleLockout = semIsFrameworkHandleLockout();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsFrameworkHandleLockout);
                    return true;
                case 49:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int semGetRemainingLockoutTime = semGetRemainingLockoutTime(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetRemainingLockoutTime);
                    return true;
                case 50:
                    boolean semShouldRemoveTemplate = semShouldRemoveTemplate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semShouldRemoveTemplate);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTestSessionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITestSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public List<FaceSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public FaceSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FaceSensorPropertiesInternal) obtain2.readTypedObject(FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long authenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long detectFace(IBinder iBinder, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void startPreparedClient(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelFaceDetect(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long enroll(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(faceEnrollOptions, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long enrollRemotely(int i, IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void removeAll(IBinder iBinder, int i, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public List<Face> getEnrolledFaces(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Face.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean isHardwareDetected(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void generateChallenge(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean hasEnrolledFaces(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int getLockoutModeForUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long getAuthenticatorId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void setFeature(IBinder iBinder, int i, int i2, boolean z, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void getFeature(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticators(FaceSensorConfigurations faceSensorConfigurations) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeTypedObject(faceSensorConfigurations, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iFaceAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void scheduleWatchdog() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long semAuthenticate(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Bundle bundle, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long semAuthenticateExt(IBinder iBinder, long j, IFaceServiceReceiver iFaceServiceReceiver, FaceAuthenticateOptions faceAuthenticateOptions, Surface surface, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsEnrollSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semPauseEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semResumeEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semPauseAuth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semResumeAuth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public String semGetInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semResetAuthenticationTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semSessionOpen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void semSessionClose(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsSessionClose() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int semGetSecurityLevel(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semIsFrameworkHandleLockout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int semGetRemainingLockoutTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean semShouldRemoveTemplate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFaceService.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
