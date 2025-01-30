package com.samsung.android.service.SemService;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.service.SemService.ISemService";

    int ICD() throws RemoteException;

    void check_Network(int i) throws RemoteException;

    int check_SeState(byte[] bArr, byte[] bArr2) throws RemoteException;

    int closeSpiDriver() throws RemoteException;

    int close_Spi(int i) throws RemoteException;

    int continue_attestation(String str, int i, byte[] bArr) throws RemoteException;

    int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws RemoteException;

    int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) throws RemoteException;

    int eSE_FactoryReset() throws RemoteException;

    int eSE_FullFactoryReset() throws RemoteException;

    int eSE_LowFactoryReset() throws RemoteException;

    int esek_certificate_check() throws RemoteException;

    int getAtr_Spi() throws RemoteException;

    String getCPLC14mode() throws RemoteException;

    String get_ESEA() throws RemoteException;

    int get_HQMMemory(byte[] bArr) throws RemoteException;

    int grdm_Check_Status() throws RemoteException;

    String grdm_check_restricted_mode() throws RemoteException;

    int grdm_get_attes_cert(int i, byte[] bArr) throws RemoteException;

    int grdm_get_session() throws RemoteException;

    int grdm_release_session() throws RemoteException;

    int grdm_request_key(int i, byte[] bArr) throws RemoteException;

    String[] handle_CCM(byte[] bArr, int i) throws RemoteException;

    String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException;

    int inject_esesm_keyset() throws RemoteException;

    int isLccmSwp() throws RemoteException;

    int openSpiDriver() throws RemoteException;

    int open_Spi(int i) throws RemoteException;

    int resetForCOSU() throws RemoteException;

    int scp11_certificate_check() throws RemoteException;

    void secureLog(String str) throws RemoteException;

    void sem_factory() throws RemoteException;

    int send_Data(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException;

    void start_SLOG() throws RemoteException;

    int start_attestation(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException;

    int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) throws RemoteException;

    void stop_SLOG() throws RemoteException;

    void stop_request_credentials() throws RemoteException;

    int verify_esesm_keyset() throws RemoteException;

    public static class Default implements ISemService {
        @Override // com.samsung.android.service.SemService.ISemService
        public String get_ESEA() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String getCPLC14mode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void sem_factory() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String[] handle_CCM(byte[] ccmData, int ccmDataLen) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String[] handle_CCMCB(byte[] ccmData, int ccmDataLen, byte[] respData, int respLen) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int isLccmSwp() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int get_HQMMemory(byte[] hw_memory_data) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int deactivate_Cards(int RequestType, String[] package_name, int[] package_len, int arrayLen) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int deactivate_CardsAID(int RequestType, int bean, String[] package_name, int[] package_len, int arrayLen) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_FactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int ICD() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int start_attestation(byte[] drRsp, int drLen, byte[] svRsp, int svLen) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int continue_attestation(String data, int dataLen, byte[] rspData) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void secureLog(String msg) throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void start_SLOG() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void stop_SLOG() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int getAtr_Spi() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int resetForCOSU() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int open_Spi(int flag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int close_Spi(int flag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int send_Data(byte[] baCmd, int cLen, byte[] baRsp, int flag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int check_SeState(byte[] appletAid, byte[] associatedAid) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int start_request_credentials(byte[] appletAid, byte[] associatedAid, String serviceName, byte[] key_blob) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void stop_request_credentials() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_get_session() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_request_key(int domainIndex, byte[] key_blob) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_release_session() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_get_attes_cert(int index, byte[] rspData) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String grdm_check_restricted_mode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_Check_Status() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int openSpiDriver() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int closeSpiDriver() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_LowFactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_FullFactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int esek_certificate_check() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int scp11_certificate_check() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int inject_esesm_keyset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int verify_esesm_keyset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void check_Network(int type) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemService {
        static final int TRANSACTION_ICD = 11;
        static final int TRANSACTION_check_Network = 39;
        static final int TRANSACTION_check_SeState = 22;
        static final int TRANSACTION_closeSpiDriver = 32;
        static final int TRANSACTION_close_Spi = 20;
        static final int TRANSACTION_continue_attestation = 13;
        static final int TRANSACTION_deactivate_Cards = 8;
        static final int TRANSACTION_deactivate_CardsAID = 9;
        static final int TRANSACTION_eSE_FactoryReset = 10;
        static final int TRANSACTION_eSE_FullFactoryReset = 34;
        static final int TRANSACTION_eSE_LowFactoryReset = 33;
        static final int TRANSACTION_esek_certificate_check = 35;
        static final int TRANSACTION_getAtr_Spi = 17;
        static final int TRANSACTION_getCPLC14mode = 2;
        static final int TRANSACTION_get_ESEA = 1;
        static final int TRANSACTION_get_HQMMemory = 7;
        static final int TRANSACTION_grdm_Check_Status = 30;
        static final int TRANSACTION_grdm_check_restricted_mode = 29;
        static final int TRANSACTION_grdm_get_attes_cert = 28;
        static final int TRANSACTION_grdm_get_session = 25;
        static final int TRANSACTION_grdm_release_session = 27;
        static final int TRANSACTION_grdm_request_key = 26;
        static final int TRANSACTION_handle_CCM = 4;
        static final int TRANSACTION_handle_CCMCB = 5;
        static final int TRANSACTION_inject_esesm_keyset = 37;
        static final int TRANSACTION_isLccmSwp = 6;
        static final int TRANSACTION_openSpiDriver = 31;
        static final int TRANSACTION_open_Spi = 19;
        static final int TRANSACTION_resetForCOSU = 18;
        static final int TRANSACTION_scp11_certificate_check = 36;
        static final int TRANSACTION_secureLog = 14;
        static final int TRANSACTION_sem_factory = 3;
        static final int TRANSACTION_send_Data = 21;
        static final int TRANSACTION_start_SLOG = 15;
        static final int TRANSACTION_start_attestation = 12;
        static final int TRANSACTION_start_request_credentials = 23;
        static final int TRANSACTION_stop_SLOG = 16;
        static final int TRANSACTION_stop_request_credentials = 24;
        static final int TRANSACTION_verify_esesm_keyset = 38;

        public Stub() {
            attachInterface(this, ISemService.DESCRIPTOR);
        }

        public static ISemService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemService)) {
                return (ISemService) iin;
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
                    return "get_ESEA";
                case 2:
                    return "getCPLC14mode";
                case 3:
                    return "sem_factory";
                case 4:
                    return "handle_CCM";
                case 5:
                    return "handle_CCMCB";
                case 6:
                    return "isLccmSwp";
                case 7:
                    return "get_HQMMemory";
                case 8:
                    return "deactivate_Cards";
                case 9:
                    return "deactivate_CardsAID";
                case 10:
                    return "eSE_FactoryReset";
                case 11:
                    return "ICD";
                case 12:
                    return "start_attestation";
                case 13:
                    return "continue_attestation";
                case 14:
                    return "secureLog";
                case 15:
                    return "start_SLOG";
                case 16:
                    return "stop_SLOG";
                case 17:
                    return "getAtr_Spi";
                case 18:
                    return "resetForCOSU";
                case 19:
                    return "open_Spi";
                case 20:
                    return "close_Spi";
                case 21:
                    return "send_Data";
                case 22:
                    return "check_SeState";
                case 23:
                    return "start_request_credentials";
                case 24:
                    return "stop_request_credentials";
                case 25:
                    return "grdm_get_session";
                case 26:
                    return "grdm_request_key";
                case 27:
                    return "grdm_release_session";
                case 28:
                    return "grdm_get_attes_cert";
                case 29:
                    return "grdm_check_restricted_mode";
                case 30:
                    return "grdm_Check_Status";
                case 31:
                    return "openSpiDriver";
                case 32:
                    return "closeSpiDriver";
                case 33:
                    return "eSE_LowFactoryReset";
                case 34:
                    return "eSE_FullFactoryReset";
                case 35:
                    return "esek_certificate_check";
                case 36:
                    return "scp11_certificate_check";
                case 37:
                    return "inject_esesm_keyset";
                case 38:
                    return "verify_esesm_keyset";
                case 39:
                    return "check_Network";
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
                data.enforceInterface(ISemService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _result = get_ESEA();
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 2:
                            String _result2 = getCPLC14mode();
                            reply.writeNoException();
                            reply.writeString(_result2);
                            return true;
                        case 3:
                            sem_factory();
                            reply.writeNoException();
                            return true;
                        case 4:
                            byte[] _arg0 = data.createByteArray();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            String[] _result3 = handle_CCM(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeStringArray(_result3);
                            reply.writeByteArray(_arg0);
                            return true;
                        case 5:
                            byte[] _arg02 = data.createByteArray();
                            int _arg12 = data.readInt();
                            byte[] _arg2 = data.createByteArray();
                            int _arg3 = data.readInt();
                            data.enforceNoDataAvail();
                            String[] _result4 = handle_CCMCB(_arg02, _arg12, _arg2, _arg3);
                            reply.writeNoException();
                            reply.writeStringArray(_result4);
                            reply.writeByteArray(_arg02);
                            reply.writeByteArray(_arg2);
                            return true;
                        case 6:
                            int _result5 = isLccmSwp();
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 7:
                            byte[] _arg03 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result6 = get_HQMMemory(_arg03);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            reply.writeByteArray(_arg03);
                            return true;
                        case 8:
                            int _arg04 = data.readInt();
                            String[] _arg13 = data.createStringArray();
                            int[] _arg22 = data.createIntArray();
                            int _arg32 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result7 = deactivate_Cards(_arg04, _arg13, _arg22, _arg32);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 9:
                            int _arg05 = data.readInt();
                            int _arg14 = data.readInt();
                            String[] _arg23 = data.createStringArray();
                            int[] _arg33 = data.createIntArray();
                            int _arg4 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result8 = deactivate_CardsAID(_arg05, _arg14, _arg23, _arg33, _arg4);
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 10:
                            int _result9 = eSE_FactoryReset();
                            reply.writeNoException();
                            reply.writeInt(_result9);
                            return true;
                        case 11:
                            int _result10 = ICD();
                            reply.writeNoException();
                            reply.writeInt(_result10);
                            return true;
                        case 12:
                            byte[] _arg06 = data.createByteArray();
                            int _arg15 = data.readInt();
                            byte[] _arg24 = data.createByteArray();
                            int _arg34 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result11 = start_attestation(_arg06, _arg15, _arg24, _arg34);
                            reply.writeNoException();
                            reply.writeInt(_result11);
                            reply.writeByteArray(_arg06);
                            reply.writeByteArray(_arg24);
                            return true;
                        case 13:
                            String _arg07 = data.readString();
                            int _arg16 = data.readInt();
                            byte[] _arg25 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result12 = continue_attestation(_arg07, _arg16, _arg25);
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            reply.writeByteArray(_arg25);
                            return true;
                        case 14:
                            String _arg08 = data.readString();
                            data.enforceNoDataAvail();
                            secureLog(_arg08);
                            reply.writeNoException();
                            return true;
                        case 15:
                            start_SLOG();
                            reply.writeNoException();
                            return true;
                        case 16:
                            stop_SLOG();
                            reply.writeNoException();
                            return true;
                        case 17:
                            int _result13 = getAtr_Spi();
                            reply.writeNoException();
                            reply.writeInt(_result13);
                            return true;
                        case 18:
                            int _result14 = resetForCOSU();
                            reply.writeNoException();
                            reply.writeInt(_result14);
                            return true;
                        case 19:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result15 = open_Spi(_arg09);
                            reply.writeNoException();
                            reply.writeInt(_result15);
                            return true;
                        case 20:
                            int _arg010 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result16 = close_Spi(_arg010);
                            reply.writeNoException();
                            reply.writeInt(_result16);
                            return true;
                        case 21:
                            byte[] _arg011 = data.createByteArray();
                            int _arg17 = data.readInt();
                            byte[] _arg26 = data.createByteArray();
                            int _arg35 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result17 = send_Data(_arg011, _arg17, _arg26, _arg35);
                            reply.writeNoException();
                            reply.writeInt(_result17);
                            reply.writeByteArray(_arg011);
                            reply.writeByteArray(_arg26);
                            return true;
                        case 22:
                            byte[] _arg012 = data.createByteArray();
                            byte[] _arg18 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result18 = check_SeState(_arg012, _arg18);
                            reply.writeNoException();
                            reply.writeInt(_result18);
                            return true;
                        case 23:
                            byte[] _arg013 = data.createByteArray();
                            byte[] _arg19 = data.createByteArray();
                            String _arg27 = data.readString();
                            byte[] _arg36 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result19 = start_request_credentials(_arg013, _arg19, _arg27, _arg36);
                            reply.writeNoException();
                            reply.writeInt(_result19);
                            reply.writeByteArray(_arg36);
                            return true;
                        case 24:
                            stop_request_credentials();
                            reply.writeNoException();
                            return true;
                        case 25:
                            int _result20 = grdm_get_session();
                            reply.writeNoException();
                            reply.writeInt(_result20);
                            return true;
                        case 26:
                            int _arg014 = data.readInt();
                            byte[] _arg110 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result21 = grdm_request_key(_arg014, _arg110);
                            reply.writeNoException();
                            reply.writeInt(_result21);
                            reply.writeByteArray(_arg110);
                            return true;
                        case 27:
                            int _result22 = grdm_release_session();
                            reply.writeNoException();
                            reply.writeInt(_result22);
                            return true;
                        case 28:
                            int _arg015 = data.readInt();
                            byte[] _arg111 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result23 = grdm_get_attes_cert(_arg015, _arg111);
                            reply.writeNoException();
                            reply.writeInt(_result23);
                            reply.writeByteArray(_arg111);
                            return true;
                        case 29:
                            String _result24 = grdm_check_restricted_mode();
                            reply.writeNoException();
                            reply.writeString(_result24);
                            return true;
                        case 30:
                            int _result25 = grdm_Check_Status();
                            reply.writeNoException();
                            reply.writeInt(_result25);
                            return true;
                        case 31:
                            int _result26 = openSpiDriver();
                            reply.writeNoException();
                            reply.writeInt(_result26);
                            return true;
                        case 32:
                            int _result27 = closeSpiDriver();
                            reply.writeNoException();
                            reply.writeInt(_result27);
                            return true;
                        case 33:
                            int _result28 = eSE_LowFactoryReset();
                            reply.writeNoException();
                            reply.writeInt(_result28);
                            return true;
                        case 34:
                            int _result29 = eSE_FullFactoryReset();
                            reply.writeNoException();
                            reply.writeInt(_result29);
                            return true;
                        case 35:
                            int _result30 = esek_certificate_check();
                            reply.writeNoException();
                            reply.writeInt(_result30);
                            return true;
                        case 36:
                            int _result31 = scp11_certificate_check();
                            reply.writeNoException();
                            reply.writeInt(_result31);
                            return true;
                        case 37:
                            int _result32 = inject_esesm_keyset();
                            reply.writeNoException();
                            reply.writeInt(_result32);
                            return true;
                        case 38:
                            int _result33 = verify_esesm_keyset();
                            reply.writeNoException();
                            reply.writeInt(_result33);
                            return true;
                        case 39:
                            int _arg016 = data.readInt();
                            data.enforceNoDataAvail();
                            check_Network(_arg016);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String get_ESEA() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String getCPLC14mode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void sem_factory() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCM(byte[] ccmData, int ccmDataLen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(ccmData);
                    _data.writeInt(ccmDataLen);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.readByteArray(ccmData);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCMCB(byte[] ccmData, int ccmDataLen, byte[] respData, int respLen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(ccmData);
                    _data.writeInt(ccmDataLen);
                    _data.writeByteArray(respData);
                    _data.writeInt(respLen);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.readByteArray(ccmData);
                    _reply.readByteArray(respData);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int isLccmSwp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int get_HQMMemory(byte[] hw_memory_data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(hw_memory_data);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(hw_memory_data);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_Cards(int RequestType, String[] package_name, int[] package_len, int arrayLen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(RequestType);
                    _data.writeStringArray(package_name);
                    _data.writeIntArray(package_len);
                    _data.writeInt(arrayLen);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_CardsAID(int RequestType, int bean, String[] package_name, int[] package_len, int arrayLen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(RequestType);
                    _data.writeInt(bean);
                    _data.writeStringArray(package_name);
                    _data.writeIntArray(package_len);
                    _data.writeInt(arrayLen);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FactoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int ICD() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_attestation(byte[] drRsp, int drLen, byte[] svRsp, int svLen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(drRsp);
                    _data.writeInt(drLen);
                    _data.writeByteArray(svRsp);
                    _data.writeInt(svLen);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(drRsp);
                    _reply.readByteArray(svRsp);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int continue_attestation(String data, int dataLen, byte[] rspData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeString(data);
                    _data.writeInt(dataLen);
                    _data.writeByteArray(rspData);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(rspData);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void secureLog(String msg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeString(msg);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void start_SLOG() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_SLOG() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int getAtr_Spi() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int resetForCOSU() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int open_Spi(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(flag);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int close_Spi(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(flag);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int send_Data(byte[] baCmd, int cLen, byte[] baRsp, int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(baCmd);
                    _data.writeInt(cLen);
                    _data.writeByteArray(baRsp);
                    _data.writeInt(flag);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(baCmd);
                    _reply.readByteArray(baRsp);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int check_SeState(byte[] appletAid, byte[] associatedAid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(appletAid);
                    _data.writeByteArray(associatedAid);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_request_credentials(byte[] appletAid, byte[] associatedAid, String serviceName, byte[] key_blob) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeByteArray(appletAid);
                    _data.writeByteArray(associatedAid);
                    _data.writeString(serviceName);
                    _data.writeByteArray(key_blob);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(key_blob);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_request_credentials() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_session() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_request_key(int domainIndex, byte[] key_blob) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(domainIndex);
                    _data.writeByteArray(key_blob);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(key_blob);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_release_session() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_attes_cert(int index, byte[] rspData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(index);
                    _data.writeByteArray(rspData);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(rspData);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String grdm_check_restricted_mode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_Check_Status() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int openSpiDriver() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int closeSpiDriver() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_LowFactoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FullFactoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int esek_certificate_check() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int scp11_certificate_check() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int inject_esesm_keyset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int verify_esesm_keyset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void check_Network(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemService.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
