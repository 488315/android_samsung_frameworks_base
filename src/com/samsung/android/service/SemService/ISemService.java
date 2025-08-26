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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemService)) {
                return (ISemService) iInterfaceQueryLocalInterface;
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
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrHandle_CCM = handle_CCM(bArrCreateByteArray, i3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrHandle_CCM);
                    parcel2.writeByteArray(bArrCreateByteArray);
                    return true;
                case 5:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrHandle_CCMCB = handle_CCMCB(bArrCreateByteArray2, i4, bArrCreateByteArray3, i5);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrHandle_CCMCB);
                    parcel2.writeByteArray(bArrCreateByteArray2);
                    parcel2.writeByteArray(bArrCreateByteArray3);
                    return true;
                case 6:
                    int iIsLccmSwp = isLccmSwp();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsLccmSwp);
                    return true;
                case 7:
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int i6 = get_HQMMemory(bArrCreateByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeInt(i6);
                    parcel2.writeByteArray(bArrCreateByteArray4);
                    return true;
                case 8:
                    int i7 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iDeactivate_Cards = deactivate_Cards(i7, strArrCreateStringArray, iArrCreateIntArray, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeactivate_Cards);
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iDeactivate_CardsAID = deactivate_CardsAID(i9, i10, strArrCreateStringArray2, iArrCreateIntArray2, i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeactivate_CardsAID);
                    return true;
                case 10:
                    int iESE_FactoryReset = eSE_FactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(iESE_FactoryReset);
                    return true;
                case 11:
                    int iICD = ICD();
                    parcel2.writeNoException();
                    parcel2.writeInt(iICD);
                    return true;
                case 12:
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    int i12 = parcel.readInt();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStart_attestation = start_attestation(bArrCreateByteArray5, i12, bArrCreateByteArray6, i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart_attestation);
                    parcel2.writeByteArray(bArrCreateByteArray5);
                    parcel2.writeByteArray(bArrCreateByteArray6);
                    return true;
                case 13:
                    String string = parcel.readString();
                    int i14 = parcel.readInt();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iContinue_attestation = continue_attestation(string, i14, bArrCreateByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iContinue_attestation);
                    parcel2.writeByteArray(bArrCreateByteArray7);
                    return true;
                case 14:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    secureLog(string2);
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
                    int iResetForCOSU = resetForCOSU();
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetForCOSU);
                    return true;
                case 19:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iOpen_Spi = open_Spi(i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpen_Spi);
                    return true;
                case 20:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iClose_Spi = close_Spi(i16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClose_Spi);
                    return true;
                case 21:
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    int i17 = parcel.readInt();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSend_Data = send_Data(bArrCreateByteArray8, i17, bArrCreateByteArray9, i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSend_Data);
                    parcel2.writeByteArray(bArrCreateByteArray8);
                    parcel2.writeByteArray(bArrCreateByteArray9);
                    return true;
                case 22:
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iCheck_SeState = check_SeState(bArrCreateByteArray10, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheck_SeState);
                    return true;
                case 23:
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray14 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iStart_request_credentials = start_request_credentials(bArrCreateByteArray12, bArrCreateByteArray13, string3, bArrCreateByteArray14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart_request_credentials);
                    parcel2.writeByteArray(bArrCreateByteArray14);
                    return true;
                case 24:
                    stop_request_credentials();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int iGrdm_get_session = grdm_get_session();
                    parcel2.writeNoException();
                    parcel2.writeInt(iGrdm_get_session);
                    return true;
                case 26:
                    int i19 = parcel.readInt();
                    byte[] bArrCreateByteArray15 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iGrdm_request_key = grdm_request_key(i19, bArrCreateByteArray15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iGrdm_request_key);
                    parcel2.writeByteArray(bArrCreateByteArray15);
                    return true;
                case 27:
                    int iGrdm_release_session = grdm_release_session();
                    parcel2.writeNoException();
                    parcel2.writeInt(iGrdm_release_session);
                    return true;
                case 28:
                    int i20 = parcel.readInt();
                    byte[] bArrCreateByteArray16 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iGrdm_get_attes_cert = grdm_get_attes_cert(i20, bArrCreateByteArray16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iGrdm_get_attes_cert);
                    parcel2.writeByteArray(bArrCreateByteArray16);
                    return true;
                case 29:
                    String strGrdm_check_restricted_mode = grdm_check_restricted_mode();
                    parcel2.writeNoException();
                    parcel2.writeString(strGrdm_check_restricted_mode);
                    return true;
                case 30:
                    int iGrdm_Check_Status = grdm_Check_Status();
                    parcel2.writeNoException();
                    parcel2.writeInt(iGrdm_Check_Status);
                    return true;
                case 31:
                    int iOpenSpiDriver = openSpiDriver();
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpenSpiDriver);
                    return true;
                case 32:
                    int iCloseSpiDriver = closeSpiDriver();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCloseSpiDriver);
                    return true;
                case 33:
                    int iESE_LowFactoryReset = eSE_LowFactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(iESE_LowFactoryReset);
                    return true;
                case 34:
                    int iESE_FullFactoryReset = eSE_FullFactoryReset();
                    parcel2.writeNoException();
                    parcel2.writeInt(iESE_FullFactoryReset);
                    return true;
                case 35:
                    int iEsek_certificate_check = esek_certificate_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(iEsek_certificate_check);
                    return true;
                case 36:
                    int iScp11_certificate_check = scp11_certificate_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(iScp11_certificate_check);
                    return true;
                case 37:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    check_Network(i21);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    byte[] bArrCreateByteArray17 = parcel.createByteArray();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iHandle_CCMScp11c = handle_CCMScp11c(bArrCreateByteArray17, i22);
                    parcel2.writeNoException();
                    parcel2.writeInt(iHandle_CCMScp11c);
                    parcel2.writeByteArray(bArrCreateByteArray17);
                    return true;
                case 39:
                    byte[] bArrCreateByteArray18 = parcel.createByteArray();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iESE_AidFactoryReset = eSE_AidFactoryReset(bArrCreateByteArray18, i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iESE_AidFactoryReset);
                    return true;
                case 40:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    agent_SLOG(string4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String getCPLC14mode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void sem_factory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCM(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    String[] strArrCreateStringArray = parcelObtain2.createStringArray();
                    parcelObtain2.readByteArray(bArr);
                    return strArrCreateStringArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String[] handle_CCMCB(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    String[] strArrCreateStringArray = parcelObtain2.createStringArray();
                    parcelObtain2.readByteArray(bArr);
                    parcelObtain2.readByteArray(bArr2);
                    return strArrCreateStringArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int isLccmSwp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int get_HQMMemory(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_Cards(int i, String[] strArr, int[] iArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int deactivate_CardsAID(int i, int i2, String[] strArr, int[] iArr, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FactoryReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int ICD() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_attestation(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    parcelObtain2.readByteArray(bArr2);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int continue_attestation(String str, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void secureLog(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void start_SLOG() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_SLOG() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int getAtr_Spi() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int resetForCOSU() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int open_Spi(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int close_Spi(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int send_Data(byte[] bArr, int i, byte[] bArr2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    parcelObtain2.readByteArray(bArr2);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int check_SeState(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int start_request_credentials(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr3);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr3);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void stop_request_credentials() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_session() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_request_key(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_release_session() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_get_attes_cert(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public String grdm_check_restricted_mode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int grdm_Check_Status() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int openSpiDriver() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int closeSpiDriver() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_LowFactoryReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_FullFactoryReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int esek_certificate_check() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int scp11_certificate_check() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void check_Network(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int handle_CCMScp11c(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public int eSE_AidFactoryReset(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.SemService.ISemService
            public void agent_SLOG(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
