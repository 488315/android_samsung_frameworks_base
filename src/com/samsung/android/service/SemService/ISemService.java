package com.samsung.android.service.SemService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.service.SemService.ISemService";

    public static class Default implements ISemService {
        @Override // com.samsung.android.service.SemService.ISemService
        public int ICD() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void agent_SLOG(String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void check_Network(int i) throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int check_SeState(byte[] bArr, byte[] bArr2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int closeSpiDriver() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int close_Spi(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int continue_attestation(String str, int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_AidFactoryReset(byte[] bArr, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_FactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_FullFactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int eSE_LowFactoryReset() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int esek_certificate_check() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int getAtr_Spi() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String getCPLC14mode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String get_ESEA() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int get_HQMMemory(byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_Check_Status() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String grdm_check_restricted_mode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_get_attes_cert(int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_get_session() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_release_session() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int grdm_request_key(int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String[] handle_CCM(byte[] bArr, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int handle_CCMScp11c(byte[] bArr, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int isLccmSwp() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int openSpiDriver() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int open_Spi(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int resetForCOSU() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int scp11_certificate_check() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void secureLog(String str) throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void sem_factory() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int send_Data(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void start_SLOG() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int start_attestation(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void stop_SLOG() throws RemoteException {
        }

        @Override // com.samsung.android.service.SemService.ISemService
        public void stop_request_credentials() throws RemoteException {
        }
    }

    int ICD() throws RemoteException;

    void agent_SLOG(String str) throws RemoteException;

    void check_Network(int i) throws RemoteException;

    int check_SeState(byte[] bArr, byte[] bArr2) throws RemoteException;

    int closeSpiDriver() throws RemoteException;

    int close_Spi(int i) throws RemoteException;

    int continue_attestation(String str, int i, byte[] bArr) throws RemoteException;

    int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws RemoteException;

    int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) throws RemoteException;

    int eSE_AidFactoryReset(byte[] bArr, int i) throws RemoteException;

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

    int handle_CCMScp11c(byte[] bArr, int i) throws RemoteException;

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

    public static abstract class Stub extends Binder implements ISemService {
        static final int TRANSACTION_ICD = 11;
        static final int TRANSACTION_agent_SLOG = 40;
        static final int TRANSACTION_check_Network = 37;
        static final int TRANSACTION_check_SeState = 22;
        static final int TRANSACTION_closeSpiDriver = 32;
        static final int TRANSACTION_close_Spi = 20;
        static final int TRANSACTION_continue_attestation = 13;
        static final int TRANSACTION_deactivate_Cards = 8;
        static final int TRANSACTION_deactivate_CardsAID = 9;
        static final int TRANSACTION_eSE_AidFactoryReset = 39;
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
        static final int TRANSACTION_handle_CCMScp11c = 38;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
        }

        public Stub() {
            attachInterface(this, ISemService.DESCRIPTOR);
        }

        public static ISemService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemService)) {
                return (ISemService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "check_Network";
                case 38:
                    return "handle_CCMScp11c";
                case 39:
                    return "eSE_AidFactoryReset";
                case 40:
                    return "agent_SLOG";
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
                parcel.enforceInterface(ISemService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String _esea = get_ESEA();
                    parcel2.writeNoException();
                    parcel2.writeString(_esea);
                    return true;
                case 2:
                    String cPLC14mode = getCPLC14mode();
                    parcel2.writeNoException();
                    parcel2.writeString(cPLC14mode);
                    return true;
                case 3:
                    sem_factory();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] handle_CCM = handle_CCM(createByteArray, readInt);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(handle_CCM);
                    parcel2.writeByteArray(createByteArray);
                    return true;
                case 5:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] handle_CCMCB = handle_CCMCB(createByteArray2, readInt2, createByteArray3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(handle_CCMCB);
                    parcel2.writeByteArray(createByteArray2);
                    parcel2.writeByteArray(createByteArray3);
                    return true;
                case 6:
                    int isLccmSwp = isLccmSwp();
                    parcel2.writeNoException();
                    parcel2.writeInt(isLccmSwp);
                    return true;
                case 7:
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int i3 = get_HQMMemory(createByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    parcel2.writeByteArray(createByteArray4);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deactivate_Cards = deactivate_Cards(readInt4, createStringArray, createIntArray, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(deactivate_Cards);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deactivate_CardsAID = deactivate_CardsAID(readInt6, readInt7, createStringArray2, createIntArray2, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(deactivate_CardsAID);
                    return true;
                case 10:
                    int eSE_FactoryReset = eSE_FactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(eSE_FactoryReset);
                    return true;
                case 11:
                    int ICD = ICD();
                    parcel2.writeNoException();
                    parcel2.writeInt(ICD);
                    return true;
                case 12:
                    byte[] createByteArray5 = parcel.createByteArray();
                    int readInt9 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int start_attestation = start_attestation(createByteArray5, readInt9, createByteArray6, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(start_attestation);
                    parcel2.writeByteArray(createByteArray5);
                    parcel2.writeByteArray(createByteArray6);
                    return true;
                case 13:
                    String readString = parcel.readString();
                    int readInt11 = parcel.readInt();
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int continue_attestation = continue_attestation(readString, readInt11, createByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeInt(continue_attestation);
                    parcel2.writeByteArray(createByteArray7);
                    return true;
                case 14:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    secureLog(readString2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    start_SLOG();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    stop_SLOG();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int atr_Spi = getAtr_Spi();
                    parcel2.writeNoException();
                    parcel2.writeInt(atr_Spi);
                    return true;
                case 18:
                    int resetForCOSU = resetForCOSU();
                    parcel2.writeNoException();
                    parcel2.writeInt(resetForCOSU);
                    return true;
                case 19:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int open_Spi = open_Spi(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(open_Spi);
                    return true;
                case 20:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int close_Spi = close_Spi(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(close_Spi);
                    return true;
                case 21:
                    byte[] createByteArray8 = parcel.createByteArray();
                    int readInt14 = parcel.readInt();
                    byte[] createByteArray9 = parcel.createByteArray();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int send_Data = send_Data(createByteArray8, readInt14, createByteArray9, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(send_Data);
                    parcel2.writeByteArray(createByteArray8);
                    parcel2.writeByteArray(createByteArray9);
                    return true;
                case 22:
                    byte[] createByteArray10 = parcel.createByteArray();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int check_SeState = check_SeState(createByteArray10, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeInt(check_SeState);
                    return true;
                case 23:
                    byte[] createByteArray12 = parcel.createByteArray();
                    byte[] createByteArray13 = parcel.createByteArray();
                    String readString3 = parcel.readString();
                    byte[] createByteArray14 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int start_request_credentials = start_request_credentials(createByteArray12, createByteArray13, readString3, createByteArray14);
                    parcel2.writeNoException();
                    parcel2.writeInt(start_request_credentials);
                    parcel2.writeByteArray(createByteArray14);
                    return true;
                case 24:
                    stop_request_credentials();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int grdm_get_session = grdm_get_session();
                    parcel2.writeNoException();
                    parcel2.writeInt(grdm_get_session);
                    return true;
                case 26:
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray15 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int grdm_request_key = grdm_request_key(readInt16, createByteArray15);
                    parcel2.writeNoException();
                    parcel2.writeInt(grdm_request_key);
                    parcel2.writeByteArray(createByteArray15);
                    return true;
                case 27:
                    int grdm_release_session = grdm_release_session();
                    parcel2.writeNoException();
                    parcel2.writeInt(grdm_release_session);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    byte[] createByteArray16 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int grdm_get_attes_cert = grdm_get_attes_cert(readInt17, createByteArray16);
                    parcel2.writeNoException();
                    parcel2.writeInt(grdm_get_attes_cert);
                    parcel2.writeByteArray(createByteArray16);
                    return true;
                case 29:
                    String grdm_check_restricted_mode = grdm_check_restricted_mode();
                    parcel2.writeNoException();
                    parcel2.writeString(grdm_check_restricted_mode);
                    return true;
                case 30:
                    int grdm_Check_Status = grdm_Check_Status();
                    parcel2.writeNoException();
                    parcel2.writeInt(grdm_Check_Status);
                    return true;
                case 31:
                    int openSpiDriver = openSpiDriver();
                    parcel2.writeNoException();
                    parcel2.writeInt(openSpiDriver);
                    return true;
                case 32:
                    int closeSpiDriver = closeSpiDriver();
                    parcel2.writeNoException();
                    parcel2.writeInt(closeSpiDriver);
                    return true;
                case 33:
                    int eSE_LowFactoryReset = eSE_LowFactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(eSE_LowFactoryReset);
                    return true;
                case 34:
                    int eSE_FullFactoryReset = eSE_FullFactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(eSE_FullFactoryReset);
                    return true;
                case 35:
                    int esek_certificate_check = esek_certificate_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(esek_certificate_check);
                    return true;
                case 36:
                    int scp11_certificate_check = scp11_certificate_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(scp11_certificate_check);
                    return true;
                case 37:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    check_Network(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    byte[] createByteArray17 = parcel.createByteArray();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int handle_CCMScp11c = handle_CCMScp11c(createByteArray17, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(handle_CCMScp11c);
                    parcel2.writeByteArray(createByteArray17);
                    return true;
                case 39:
                    byte[] createByteArray18 = parcel.createByteArray();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int eSE_AidFactoryReset = eSE_AidFactoryReset(createByteArray18, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeInt(eSE_AidFactoryReset);
                    return true;
                case 40:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    agent_SLOG(readString4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String get_ESEA() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String getCPLC14mode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void sem_factory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCM(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    String[] createStringArray = obtain2.createStringArray();
                    obtain2.readByteArray(bArr);
                    return createStringArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String[] createStringArray = obtain2.createStringArray();
                    obtain2.readByteArray(bArr);
                    obtain2.readByteArray(bArr2);
                    return createStringArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int isLccmSwp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int get_HQMMemory(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FactoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int ICD() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_attestation(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int continue_attestation(String str, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void secureLog(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void start_SLOG() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_SLOG() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int getAtr_Spi() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int resetForCOSU() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int open_Spi(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int close_Spi(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int send_Data(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int check_SeState(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr3);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr3);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_request_credentials() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_session() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_request_key(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_release_session() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_attes_cert(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String grdm_check_restricted_mode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_Check_Status() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int openSpiDriver() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int closeSpiDriver() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_LowFactoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FullFactoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int esek_certificate_check() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int scp11_certificate_check() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void check_Network(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int handle_CCMScp11c(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_AidFactoryReset(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void agent_SLOG(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
