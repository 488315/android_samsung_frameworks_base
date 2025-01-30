package com.samsung.android.ims;

import android.content.ContentValues;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.sec.enterprise.content.SecContentProviderURI;
import com.samsung.android.ims.ISemEpdgListener;
import com.samsung.android.ims.SemAutoConfigListener;
import com.samsung.android.ims.SemImsDmConfigListener;
import com.samsung.android.ims.SemImsRegiListener;
import com.samsung.android.ims.SemSimMobStatusListener;
import com.samsung.android.ims.cmc.ISemCmcRecordingListener;
import com.samsung.android.ims.cmc.SemCmcRecordingInfo;
import com.samsung.android.ims.p038ft.SemImsFtListener;
import com.samsung.android.ims.settings.SemImsProfile;

/* loaded from: classes5.dex */
public interface SemImsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsService";

    void enableRcsByPhoneId(boolean z, int i) throws RemoteException;

    boolean getBooleanConfig(String str, int i) throws RemoteException;

    ContentValues getConfigValues(String[] strArr, int i) throws RemoteException;

    SemImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException;

    String getRcsProfileType(int i) throws RemoteException;

    SemImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException;

    SemImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException;

    boolean hasCrossSimCallingSupport(int i) throws RemoteException;

    boolean isCmcEmergencyCallSupported() throws RemoteException;

    boolean isCmcEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCrossSimCallingRegistered(int i) throws RemoteException;

    boolean isForbiddenByPhoneId(int i) throws RemoteException;

    boolean isRcsEnabled(boolean z, int i) throws RemoteException;

    boolean isServiceAvailable(String str, int i, int i2) throws RemoteException;

    boolean isSimMobilityActivated(int i) throws RemoteException;

    boolean isVoLteAvailable(int i) throws RemoteException;

    String registerAutoConfigurationListener(SemAutoConfigListener semAutoConfigListener, int i) throws RemoteException;

    void registerDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException;

    String registerEpdgListener(ISemEpdgListener iSemEpdgListener) throws RemoteException;

    String registerImsOngoingFtEventListener(SemImsFtListener semImsFtListener) throws RemoteException;

    String registerImsRegistrationListenerForSlot(SemImsRegiListener semImsRegiListener, int i) throws RemoteException;

    void registerSemCmcRecordingListener(ISemCmcRecordingListener iSemCmcRecordingListener, int i) throws RemoteException;

    String registerSimMobilityStatusListener(SemSimMobStatusListener semSimMobStatusListener, int i) throws RemoteException;

    void sendIidToken(String str, int i) throws RemoteException;

    void sendMsisdnNumber(String str, int i) throws RemoteException;

    void sendSemCmcRecordingEvent(SemCmcRecordingInfo semCmcRecordingInfo, int i, int i2) throws RemoteException;

    void sendTryRegisterByPhoneId(int i) throws RemoteException;

    void sendVerificationCode(String str, int i) throws RemoteException;

    void setRttMode(int i, int i2) throws RemoteException;

    void unRegisterEpdgListener(String str) throws RemoteException;

    void unregisterAutoConfigurationListener(String str, int i) throws RemoteException;

    void unregisterDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException;

    void unregisterImsOngoingFtEventListener(String str) throws RemoteException;

    void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterSimMobilityStatusListener(String str, int i) throws RemoteException;

    public static class Default implements SemImsService {
        @Override // com.samsung.android.ims.SemImsService
        public String registerImsRegistrationListenerForSlot(SemImsRegiListener listener, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterImsRegistrationListenerForSlot(String token, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerSimMobilityStatusListener(SemSimMobStatusListener listener, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterSimMobilityStatusListener(String token, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void registerDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerAutoConfigurationListener(SemAutoConfigListener listener, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterAutoConfigurationListener(String token, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerImsOngoingFtEventListener(SemImsFtListener listener) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterImsOngoingFtEventListener(String token) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration getRegistrationInfoByServiceType(String serviceType, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration[] getRegistrationInfoByPhoneId(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isServiceAvailable(String service, int rat, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String getRcsProfileType(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isVoLteAvailable(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isSimMobilityActivated(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void setRttMode(int phoneId, int mode) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendTryRegisterByPhoneId(int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void enableRcsByPhoneId(boolean enable, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isRcsEnabled(boolean needAutoConfigCheck, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsProfile[] getCurrentProfileForSlot(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isForbiddenByPhoneId(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendVerificationCode(String value, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendMsisdnNumber(String value, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendIidToken(String value, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public ContentValues getConfigValues(String[] fields, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean getBooleanConfig(String key, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendSemCmcRecordingEvent(SemCmcRecordingInfo info, int event, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void registerSemCmcRecordingListener(ISemCmcRecordingListener listener, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerEpdgListener(ISemEpdgListener listener) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unRegisterEpdgListener(String token) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCmcEmergencyCallSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCmcEmergencyNumber(String number, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCmcPotentialEmergencyNumber(String number, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCrossSimCallingRegistered(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean hasCrossSimCallingSupport(int phoneId) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements SemImsService {
        static final int TRANSACTION_enableRcsByPhoneId = 19;
        static final int TRANSACTION_getBooleanConfig = 27;
        static final int TRANSACTION_getConfigValues = 26;
        static final int TRANSACTION_getCurrentProfileForSlot = 21;
        static final int TRANSACTION_getRcsProfileType = 14;
        static final int TRANSACTION_getRegistrationInfoByPhoneId = 12;
        static final int TRANSACTION_getRegistrationInfoByServiceType = 11;
        static final int TRANSACTION_hasCrossSimCallingSupport = 36;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 32;
        static final int TRANSACTION_isCmcEmergencyNumber = 33;
        static final int TRANSACTION_isCmcPotentialEmergencyNumber = 34;
        static final int TRANSACTION_isCrossSimCallingRegistered = 35;
        static final int TRANSACTION_isForbiddenByPhoneId = 22;
        static final int TRANSACTION_isRcsEnabled = 20;
        static final int TRANSACTION_isServiceAvailable = 13;
        static final int TRANSACTION_isSimMobilityActivated = 16;
        static final int TRANSACTION_isVoLteAvailable = 15;
        static final int TRANSACTION_registerAutoConfigurationListener = 7;
        static final int TRANSACTION_registerDmValueListener = 5;
        static final int TRANSACTION_registerEpdgListener = 30;
        static final int TRANSACTION_registerImsOngoingFtEventListener = 9;
        static final int TRANSACTION_registerImsRegistrationListenerForSlot = 1;
        static final int TRANSACTION_registerSemCmcRecordingListener = 29;
        static final int TRANSACTION_registerSimMobilityStatusListener = 3;
        static final int TRANSACTION_sendIidToken = 25;
        static final int TRANSACTION_sendMsisdnNumber = 24;
        static final int TRANSACTION_sendSemCmcRecordingEvent = 28;
        static final int TRANSACTION_sendTryRegisterByPhoneId = 18;
        static final int TRANSACTION_sendVerificationCode = 23;
        static final int TRANSACTION_setRttMode = 17;
        static final int TRANSACTION_unRegisterEpdgListener = 31;
        static final int TRANSACTION_unregisterAutoConfigurationListener = 8;
        static final int TRANSACTION_unregisterDmValueListener = 6;
        static final int TRANSACTION_unregisterImsOngoingFtEventListener = 10;
        static final int TRANSACTION_unregisterImsRegistrationListenerForSlot = 2;
        static final int TRANSACTION_unregisterSimMobilityStatusListener = 4;

        public Stub() {
            attachInterface(this, SemImsService.DESCRIPTOR);
        }

        public static SemImsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemImsService.DESCRIPTOR);
            if (iin != null && (iin instanceof SemImsService)) {
                return (SemImsService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerImsRegistrationListenerForSlot";
                case 2:
                    return "unregisterImsRegistrationListenerForSlot";
                case 3:
                    return "registerSimMobilityStatusListener";
                case 4:
                    return "unregisterSimMobilityStatusListener";
                case 5:
                    return "registerDmValueListener";
                case 6:
                    return "unregisterDmValueListener";
                case 7:
                    return "registerAutoConfigurationListener";
                case 8:
                    return "unregisterAutoConfigurationListener";
                case 9:
                    return "registerImsOngoingFtEventListener";
                case 10:
                    return "unregisterImsOngoingFtEventListener";
                case 11:
                    return "getRegistrationInfoByServiceType";
                case 12:
                    return "getRegistrationInfoByPhoneId";
                case 13:
                    return SecContentProviderURI.ENTERPRISELICENSEPOLICY_ISSERVICEAVAILABLE_METHOD;
                case 14:
                    return "getRcsProfileType";
                case 15:
                    return "isVoLteAvailable";
                case 16:
                    return "isSimMobilityActivated";
                case 17:
                    return "setRttMode";
                case 18:
                    return "sendTryRegisterByPhoneId";
                case 19:
                    return "enableRcsByPhoneId";
                case 20:
                    return "isRcsEnabled";
                case 21:
                    return "getCurrentProfileForSlot";
                case 22:
                    return "isForbiddenByPhoneId";
                case 23:
                    return "sendVerificationCode";
                case 24:
                    return "sendMsisdnNumber";
                case 25:
                    return "sendIidToken";
                case 26:
                    return "getConfigValues";
                case 27:
                    return "getBooleanConfig";
                case 28:
                    return "sendSemCmcRecordingEvent";
                case 29:
                    return "registerSemCmcRecordingListener";
                case 30:
                    return "registerEpdgListener";
                case 31:
                    return "unRegisterEpdgListener";
                case 32:
                    return "isCmcEmergencyCallSupported";
                case 33:
                    return "isCmcEmergencyNumber";
                case 34:
                    return "isCmcPotentialEmergencyNumber";
                case 35:
                    return "isCrossSimCallingRegistered";
                case 36:
                    return "hasCrossSimCallingSupport";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(SemImsService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(SemImsService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            SemImsRegiListener _arg0 = SemImsRegiListener.Stub.asInterface(data.readStrongBinder());
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result = registerImsRegistrationListenerForSlot(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterImsRegistrationListenerForSlot(_arg02, _arg12);
                            reply.writeNoException();
                            return true;
                        case 3:
                            SemSimMobStatusListener _arg03 = SemSimMobStatusListener.Stub.asInterface(data.readStrongBinder());
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result2 = registerSimMobilityStatusListener(_arg03, _arg13);
                            reply.writeNoException();
                            reply.writeString(_result2);
                            return true;
                        case 4:
                            String _arg04 = data.readString();
                            int _arg14 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterSimMobilityStatusListener(_arg04, _arg14);
                            reply.writeNoException();
                            return true;
                        case 5:
                            SemImsDmConfigListener _arg05 = SemImsDmConfigListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerDmValueListener(_arg05);
                            reply.writeNoException();
                            return true;
                        case 6:
                            SemImsDmConfigListener _arg06 = SemImsDmConfigListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            unregisterDmValueListener(_arg06);
                            reply.writeNoException();
                            return true;
                        case 7:
                            SemAutoConfigListener _arg07 = SemAutoConfigListener.Stub.asInterface(data.readStrongBinder());
                            int _arg15 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result3 = registerAutoConfigurationListener(_arg07, _arg15);
                            reply.writeNoException();
                            reply.writeString(_result3);
                            return true;
                        case 8:
                            String _arg08 = data.readString();
                            int _arg16 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterAutoConfigurationListener(_arg08, _arg16);
                            reply.writeNoException();
                            return true;
                        case 9:
                            SemImsFtListener _arg09 = SemImsFtListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            String _result4 = registerImsOngoingFtEventListener(_arg09);
                            reply.writeNoException();
                            reply.writeString(_result4);
                            return true;
                        case 10:
                            String _arg010 = data.readString();
                            data.enforceNoDataAvail();
                            unregisterImsOngoingFtEventListener(_arg010);
                            reply.writeNoException();
                            return true;
                        case 11:
                            String _arg011 = data.readString();
                            int _arg17 = data.readInt();
                            data.enforceNoDataAvail();
                            SemImsRegistration _result5 = getRegistrationInfoByServiceType(_arg011, _arg17);
                            reply.writeNoException();
                            reply.writeTypedObject(_result5, 1);
                            return true;
                        case 12:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            SemImsRegistration[] _result6 = getRegistrationInfoByPhoneId(_arg012);
                            reply.writeNoException();
                            reply.writeTypedArray(_result6, 1);
                            return true;
                        case 13:
                            String _arg013 = data.readString();
                            int _arg18 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result7 = isServiceAvailable(_arg013, _arg18, _arg2);
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 14:
                            int _arg014 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result8 = getRcsProfileType(_arg014);
                            reply.writeNoException();
                            reply.writeString(_result8);
                            return true;
                        case 15:
                            int _arg015 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result9 = isVoLteAvailable(_arg015);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 16:
                            int _arg016 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result10 = isSimMobilityActivated(_arg016);
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 17:
                            int _arg017 = data.readInt();
                            int _arg19 = data.readInt();
                            data.enforceNoDataAvail();
                            setRttMode(_arg017, _arg19);
                            reply.writeNoException();
                            return true;
                        case 18:
                            int _arg018 = data.readInt();
                            data.enforceNoDataAvail();
                            sendTryRegisterByPhoneId(_arg018);
                            reply.writeNoException();
                            return true;
                        case 19:
                            boolean _arg019 = data.readBoolean();
                            int _arg110 = data.readInt();
                            data.enforceNoDataAvail();
                            enableRcsByPhoneId(_arg019, _arg110);
                            reply.writeNoException();
                            return true;
                        case 20:
                            boolean _arg020 = data.readBoolean();
                            int _arg111 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result11 = isRcsEnabled(_arg020, _arg111);
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 21:
                            int _arg021 = data.readInt();
                            data.enforceNoDataAvail();
                            SemImsProfile[] _result12 = getCurrentProfileForSlot(_arg021);
                            reply.writeNoException();
                            reply.writeTypedArray(_result12, 1);
                            return true;
                        case 22:
                            int _arg022 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result13 = isForbiddenByPhoneId(_arg022);
                            reply.writeNoException();
                            reply.writeBoolean(_result13);
                            return true;
                        case 23:
                            String _arg023 = data.readString();
                            int _arg112 = data.readInt();
                            data.enforceNoDataAvail();
                            sendVerificationCode(_arg023, _arg112);
                            reply.writeNoException();
                            return true;
                        case 24:
                            String _arg024 = data.readString();
                            int _arg113 = data.readInt();
                            data.enforceNoDataAvail();
                            sendMsisdnNumber(_arg024, _arg113);
                            reply.writeNoException();
                            return true;
                        case 25:
                            String _arg025 = data.readString();
                            int _arg114 = data.readInt();
                            data.enforceNoDataAvail();
                            sendIidToken(_arg025, _arg114);
                            reply.writeNoException();
                            return true;
                        case 26:
                            String[] _arg026 = data.createStringArray();
                            int _arg115 = data.readInt();
                            data.enforceNoDataAvail();
                            ContentValues _result14 = getConfigValues(_arg026, _arg115);
                            reply.writeNoException();
                            reply.writeTypedObject(_result14, 1);
                            return true;
                        case 27:
                            String _arg027 = data.readString();
                            int _arg116 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result15 = getBooleanConfig(_arg027, _arg116);
                            reply.writeNoException();
                            reply.writeBoolean(_result15);
                            return true;
                        case 28:
                            SemCmcRecordingInfo _arg028 = (SemCmcRecordingInfo) data.readTypedObject(SemCmcRecordingInfo.CREATOR);
                            int _arg117 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            sendSemCmcRecordingEvent(_arg028, _arg117, _arg22);
                            reply.writeNoException();
                            return true;
                        case 29:
                            ISemCmcRecordingListener _arg029 = ISemCmcRecordingListener.Stub.asInterface(data.readStrongBinder());
                            int _arg118 = data.readInt();
                            data.enforceNoDataAvail();
                            registerSemCmcRecordingListener(_arg029, _arg118);
                            reply.writeNoException();
                            return true;
                        case 30:
                            ISemEpdgListener _arg030 = ISemEpdgListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            String _result16 = registerEpdgListener(_arg030);
                            reply.writeNoException();
                            reply.writeString(_result16);
                            return true;
                        case 31:
                            String _arg031 = data.readString();
                            data.enforceNoDataAvail();
                            unRegisterEpdgListener(_arg031);
                            reply.writeNoException();
                            return true;
                        case 32:
                            boolean _result17 = isCmcEmergencyCallSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result17);
                            return true;
                        case 33:
                            String _arg032 = data.readString();
                            int _arg119 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result18 = isCmcEmergencyNumber(_arg032, _arg119);
                            reply.writeNoException();
                            reply.writeBoolean(_result18);
                            return true;
                        case 34:
                            String _arg033 = data.readString();
                            int _arg120 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result19 = isCmcPotentialEmergencyNumber(_arg033, _arg120);
                            reply.writeNoException();
                            reply.writeBoolean(_result19);
                            return true;
                        case 35:
                            int _arg034 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result20 = isCrossSimCallingRegistered(_arg034);
                            reply.writeNoException();
                            reply.writeBoolean(_result20);
                            return true;
                        case 36:
                            int _arg035 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result21 = hasCrossSimCallingSupport(_arg035);
                            reply.writeNoException();
                            reply.writeBoolean(_result21);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements SemImsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerImsRegistrationListenerForSlot(SemImsRegiListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterImsRegistrationListenerForSlot(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerSimMobilityStatusListener(SemSimMobStatusListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterSimMobilityStatusListener(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void registerDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerAutoConfigurationListener(SemAutoConfigListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterAutoConfigurationListener(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerImsOngoingFtEventListener(SemImsFtListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterImsOngoingFtEventListener(String token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration getRegistrationInfoByServiceType(String serviceType, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(serviceType);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    SemImsRegistration _result = (SemImsRegistration) _reply.readTypedObject(SemImsRegistration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration[] getRegistrationInfoByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    SemImsRegistration[] _result = (SemImsRegistration[]) _reply.createTypedArray(SemImsRegistration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isServiceAvailable(String service, int rat, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(service);
                    _data.writeInt(rat);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String getRcsProfileType(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isVoLteAvailable(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isSimMobilityActivated(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void setRttMode(int phoneId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(mode);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendTryRegisterByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void enableRcsByPhoneId(boolean enable, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isRcsEnabled(boolean needAutoConfigCheck, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeBoolean(needAutoConfigCheck);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsProfile[] getCurrentProfileForSlot(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    SemImsProfile[] _result = (SemImsProfile[]) _reply.createTypedArray(SemImsProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isForbiddenByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendVerificationCode(String value, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(value);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendMsisdnNumber(String value, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(value);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendIidToken(String value, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(value);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public ContentValues getConfigValues(String[] fields, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStringArray(fields);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    ContentValues _result = (ContentValues) _reply.readTypedObject(ContentValues.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean getBooleanConfig(String key, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendSemCmcRecordingEvent(SemCmcRecordingInfo info, int event, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(event);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void registerSemCmcRecordingListener(ISemCmcRecordingListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerEpdgListener(ISemEpdgListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unRegisterEpdgListener(String token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCmcEmergencyCallSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCmcEmergencyNumber(String number, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(number);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCmcPotentialEmergencyNumber(String number, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(number);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCrossSimCallingRegistered(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean hasCrossSimCallingSupport(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
