package android.hardware.radio.network;

import android.hardware.radio.network.IRadioNetworkIndication;
import android.hardware.radio.network.IRadioNetworkResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioNetwork extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$network$IRadioNetwork".replace('$', '.');
    public static final String HASH = "5867b4f5be491ec815fafea8a3f268b0295427df";
    public static final int VERSION = 4;

    void cancelEmergencyNetworkScan(int i, boolean z) throws RemoteException;

    void exitEmergencyMode(int i) throws RemoteException;

    void getAllowedNetworkTypesBitmap(int i) throws RemoteException;

    @Deprecated
    void getAvailableBandModes(int i) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getBarringInfo(int i) throws RemoteException;

    @Deprecated
    void getCdmaRoamingPreference(int i) throws RemoteException;

    void getCellInfoList(int i) throws RemoteException;

    void getDataRegistrationState(int i) throws RemoteException;

    @Deprecated
    void getImsRegistrationState(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNetworkSelectionMode(int i) throws RemoteException;

    void getOperator(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getSystemSelectionChannels(int i) throws RemoteException;

    void getUsageSetting(int i) throws RemoteException;

    void getVoiceRadioTechnology(int i) throws RemoteException;

    void getVoiceRegistrationState(int i) throws RemoteException;

    void isCellularIdentifierTransparencyEnabled(int i) throws RemoteException;

    void isN1ModeEnabled(int i) throws RemoteException;

    void isNrDualConnectivityEnabled(int i) throws RemoteException;

    void isNullCipherAndIntegrityEnabled(int i) throws RemoteException;

    void isSatelliteEnabledForCarrier(int i) throws RemoteException;

    void isSecurityAlgorithmsUpdatedEnabled(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void setAllowedNetworkTypesBitmap(int i, int i2) throws RemoteException;

    @Deprecated
    void setBandMode(int i, int i2) throws RemoteException;

    void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException;

    @Deprecated
    void setCdmaRoamingPreference(int i, int i2) throws RemoteException;

    void setCellInfoListRate(int i, int i2) throws RemoteException;

    void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws RemoteException;

    void setEmergencyMode(int i, int i2) throws RemoteException;

    void setIndicationFilter(int i, int i2) throws RemoteException;

    void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws RemoteException;

    @Deprecated
    void setLocationUpdates(int i, boolean z) throws RemoteException;

    void setN1ModeEnabled(int i, boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    void setNetworkSelectionModeManual(int i, String str, int i2) throws RemoteException;

    void setNrDualConnectivityState(int i, byte b) throws RemoteException;

    void setNullCipherAndIntegrityEnabled(int i, boolean z) throws RemoteException;

    void setResponseFunctions(IRadioNetworkResponse iRadioNetworkResponse, IRadioNetworkIndication iRadioNetworkIndication) throws RemoteException;

    void setSatelliteEnabledForCarrier(int i, boolean z) throws RemoteException;

    void setSatellitePlmn(int i, String[] strArr, String[] strArr2) throws RemoteException;

    void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws RemoteException;

    void setSignalStrengthReportingCriteria(int i, SignalThresholdInfo[] signalThresholdInfoArr) throws RemoteException;

    @Deprecated
    void setSuppServiceNotifications(int i, boolean z) throws RemoteException;

    void setSystemSelectionChannels(int i, boolean z, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException;

    void setUsageSetting(int i, int i2) throws RemoteException;

    void startNetworkScan(int i, NetworkScanRequest networkScanRequest) throws RemoteException;

    void stopNetworkScan(int i) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str) throws RemoteException;

    void triggerEmergencyNetworkScan(int i, EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws RemoteException;

    public static class Default implements IRadioNetwork {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void cancelEmergencyNetworkScan(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void exitEmergencyMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAllowedNetworkTypesBitmap(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAvailableBandModes(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAvailableNetworks(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getBarringInfo(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getCdmaRoamingPreference(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getCellInfoList(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getDataRegistrationState(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getImsRegistrationState(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getNetworkSelectionMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getOperator(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getSignalStrength(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getSystemSelectionChannels(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getUsageSetting(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getVoiceRadioTechnology(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getVoiceRegistrationState(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isCellularIdentifierTransparencyEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isN1ModeEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isNrDualConnectivityEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isNullCipherAndIntegrityEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isSatelliteEnabledForCarrier(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isSecurityAlgorithmsUpdatedEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setAllowedNetworkTypesBitmap(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setBandMode(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCdmaRoamingPreference(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCellInfoListRate(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setEmergencyMode(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setIndicationFilter(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setLocationUpdates(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setN1ModeEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNetworkSelectionModeAutomatic(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNetworkSelectionModeManual(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNrDualConnectivityState(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNullCipherAndIntegrityEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setResponseFunctions(IRadioNetworkResponse iRadioNetworkResponse, IRadioNetworkIndication iRadioNetworkIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSatelliteEnabledForCarrier(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSatellitePlmn(int i, String[] strArr, String[] strArr2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSignalStrengthReportingCriteria(int i, SignalThresholdInfo[] signalThresholdInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSuppServiceNotifications(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSystemSelectionChannels(int i, boolean z, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setUsageSetting(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void startNetworkScan(int i, NetworkScanRequest networkScanRequest) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void stopNetworkScan(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void supplyNetworkDepersonalization(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void triggerEmergencyNetworkScan(int i, EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioNetwork {
        static final int TRANSACTION_cancelEmergencyNetworkScan = 39;
        static final int TRANSACTION_exitEmergencyMode = 40;
        static final int TRANSACTION_getAllowedNetworkTypesBitmap = 1;
        static final int TRANSACTION_getAvailableBandModes = 2;
        static final int TRANSACTION_getAvailableNetworks = 3;
        static final int TRANSACTION_getBarringInfo = 4;
        static final int TRANSACTION_getCdmaRoamingPreference = 5;
        static final int TRANSACTION_getCellInfoList = 6;
        static final int TRANSACTION_getDataRegistrationState = 7;
        static final int TRANSACTION_getImsRegistrationState = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNetworkSelectionMode = 9;
        static final int TRANSACTION_getOperator = 10;
        static final int TRANSACTION_getSignalStrength = 11;
        static final int TRANSACTION_getSystemSelectionChannels = 12;
        static final int TRANSACTION_getUsageSetting = 36;
        static final int TRANSACTION_getVoiceRadioTechnology = 13;
        static final int TRANSACTION_getVoiceRegistrationState = 14;
        static final int TRANSACTION_isCellularIdentifierTransparencyEnabled = 45;
        static final int TRANSACTION_isN1ModeEnabled = 43;
        static final int TRANSACTION_isNrDualConnectivityEnabled = 15;
        static final int TRANSACTION_isNullCipherAndIntegrityEnabled = 42;
        static final int TRANSACTION_isSatelliteEnabledForCarrier = 51;
        static final int TRANSACTION_isSecurityAlgorithmsUpdatedEnabled = 48;
        static final int TRANSACTION_responseAcknowledgement = 16;
        static final int TRANSACTION_setAllowedNetworkTypesBitmap = 17;
        static final int TRANSACTION_setBandMode = 18;
        static final int TRANSACTION_setBarringPassword = 19;
        static final int TRANSACTION_setCdmaRoamingPreference = 20;
        static final int TRANSACTION_setCellInfoListRate = 21;
        static final int TRANSACTION_setCellularIdentifierTransparencyEnabled = 46;
        static final int TRANSACTION_setEmergencyMode = 37;
        static final int TRANSACTION_setIndicationFilter = 22;
        static final int TRANSACTION_setLinkCapacityReportingCriteria = 23;
        static final int TRANSACTION_setLocationUpdates = 24;
        static final int TRANSACTION_setN1ModeEnabled = 44;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 25;
        static final int TRANSACTION_setNetworkSelectionModeManual = 26;
        static final int TRANSACTION_setNrDualConnectivityState = 27;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabled = 41;
        static final int TRANSACTION_setResponseFunctions = 28;
        static final int TRANSACTION_setSatelliteEnabledForCarrier = 50;
        static final int TRANSACTION_setSatellitePlmn = 49;
        static final int TRANSACTION_setSecurityAlgorithmsUpdatedEnabled = 47;
        static final int TRANSACTION_setSignalStrengthReportingCriteria = 29;
        static final int TRANSACTION_setSuppServiceNotifications = 30;
        static final int TRANSACTION_setSystemSelectionChannels = 31;
        static final int TRANSACTION_setUsageSetting = 35;
        static final int TRANSACTION_startNetworkScan = 32;
        static final int TRANSACTION_stopNetworkScan = 33;
        static final int TRANSACTION_supplyNetworkDepersonalization = 34;
        static final int TRANSACTION_triggerEmergencyNetworkScan = 38;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioNetwork)) {
                return (IRadioNetwork) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedNetworkTypesBitmap(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableBandModes(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableNetworks(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getBarringInfo(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaRoamingPreference(i7);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCellInfoList(i8);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDataRegistrationState(i9);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationState(i10);
                    return true;
                case 9:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNetworkSelectionMode(i11);
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getOperator(i12);
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSignalStrength(i13);
                    return true;
                case 12:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSystemSelectionChannels(i14);
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRadioTechnology(i15);
                    return true;
                case 14:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRegistrationState(i16);
                    return true;
                case 15:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isNrDualConnectivityEnabled(i17);
                    return true;
                case 16:
                    responseAcknowledgement();
                    return true;
                case 17:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedNetworkTypesBitmap(i18, i19);
                    return true;
                case 18:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBandMode(i20, i21);
                    return true;
                case 19:
                    int i22 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBarringPassword(i22, string, string2, string3);
                    return true;
                case 20:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCdmaRoamingPreference(i23, i24);
                    return true;
                case 21:
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCellInfoListRate(i25, i26);
                    return true;
                case 22:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIndicationFilter(i27, i28);
                    return true;
                case 23:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLinkCapacityReportingCriteria(i29, i30, i31, i32, iArrCreateIntArray, iArrCreateIntArray2, i33);
                    return true;
                case 24:
                    int i34 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationUpdates(i34, z);
                    return true;
                case 25:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomatic(i35);
                    return true;
                case 26:
                    int i36 = parcel.readInt();
                    String string4 = parcel.readString();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeManual(i36, string4, i37);
                    return true;
                case 27:
                    int i38 = parcel.readInt();
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setNrDualConnectivityState(i38, b);
                    return true;
                case 28:
                    IRadioNetworkResponse iRadioNetworkResponseAsInterface = IRadioNetworkResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioNetworkIndication iRadioNetworkIndicationAsInterface = IRadioNetworkIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioNetworkResponseAsInterface, iRadioNetworkIndicationAsInterface);
                    return true;
                case 29:
                    int i39 = parcel.readInt();
                    SignalThresholdInfo[] signalThresholdInfoArr = (SignalThresholdInfo[]) parcel.createTypedArray(SignalThresholdInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReportingCriteria(i39, signalThresholdInfoArr);
                    return true;
                case 30:
                    int i40 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSuppServiceNotifications(i40, z2);
                    return true;
                case 31:
                    int i41 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    RadioAccessSpecifier[] radioAccessSpecifierArr = (RadioAccessSpecifier[]) parcel.createTypedArray(RadioAccessSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemSelectionChannels(i41, z3, radioAccessSpecifierArr);
                    return true;
                case 32:
                    int i42 = parcel.readInt();
                    NetworkScanRequest networkScanRequest = (NetworkScanRequest) parcel.readTypedObject(NetworkScanRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    startNetworkScan(i42, networkScanRequest);
                    return true;
                case 33:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkScan(i43);
                    return true;
                case 34:
                    int i44 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalization(i44, string5);
                    return true;
                case 35:
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUsageSetting(i45, i46);
                    return true;
                case 36:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsageSetting(i47);
                    return true;
                case 37:
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmergencyMode(i48, i49);
                    return true;
                case 38:
                    int i50 = parcel.readInt();
                    EmergencyNetworkScanTrigger emergencyNetworkScanTrigger = (EmergencyNetworkScanTrigger) parcel.readTypedObject(EmergencyNetworkScanTrigger.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerEmergencyNetworkScan(i50, emergencyNetworkScanTrigger);
                    return true;
                case 39:
                    int i51 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelEmergencyNetworkScan(i51, z4);
                    return true;
                case 40:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyMode(i52);
                    return true;
                case 41:
                    int i53 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabled(i53, z5);
                    return true;
                case 42:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isNullCipherAndIntegrityEnabled(i54);
                    return true;
                case 43:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isN1ModeEnabled(i55);
                    return true;
                case 44:
                    int i56 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setN1ModeEnabled(i56, z6);
                    return true;
                case 45:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isCellularIdentifierTransparencyEnabled(i57);
                    return true;
                case 46:
                    int i58 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCellularIdentifierTransparencyEnabled(i58, z7);
                    return true;
                case 47:
                    int i59 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSecurityAlgorithmsUpdatedEnabled(i59, z8);
                    return true;
                case 48:
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isSecurityAlgorithmsUpdatedEnabled(i60);
                    return true;
                case 49:
                    int i61 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setSatellitePlmn(i61, strArrCreateStringArray, strArrCreateStringArray2);
                    return true;
                case 50:
                    int i62 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSatelliteEnabledForCarrier(i62, z9);
                    return true;
                case 51:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isSatelliteEnabledForCarrier(i63);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioNetwork {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAllowedNetworkTypesBitmap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAllowedNetworkTypesBitmap is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAvailableBandModes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableBandModes is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAvailableNetworks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableNetworks is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getBarringInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBarringInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getCdmaRoamingPreference(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaRoamingPreference is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getCellInfoList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCellInfoList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getDataRegistrationState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDataRegistrationState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getImsRegistrationState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsRegistrationState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getNetworkSelectionMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNetworkSelectionMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getOperator(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getOperator is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getSignalStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrength is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getSystemSelectionChannels(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getVoiceRadioTechnology(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVoiceRadioTechnology is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getVoiceRegistrationState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVoiceRegistrationState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isNrDualConnectivityEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isNrDualConnectivityEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setAllowedNetworkTypesBitmap(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAllowedNetworkTypesBitmap is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setBandMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBandMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBarringPassword is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCdmaRoamingPreference(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaRoamingPreference is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCellInfoListRate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCellInfoListRate is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setIndicationFilter(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setIndicationFilter is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    parcelObtain.writeInt(i5);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setLinkCapacityReportingCriteria is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setLocationUpdates(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setLocationUpdates is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNetworkSelectionModeAutomatic(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkSelectionModeAutomatic is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNetworkSelectionModeManual(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkSelectionModeManual is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNrDualConnectivityState(int i, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNrDualConnectivityState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setResponseFunctions(IRadioNetworkResponse iRadioNetworkResponse, IRadioNetworkIndication iRadioNetworkIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioNetworkResponse);
                    parcelObtain.writeStrongInterface(iRadioNetworkIndication);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSignalStrengthReportingCriteria(int i, SignalThresholdInfo[] signalThresholdInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(signalThresholdInfoArr, 0);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReportingCriteria is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSuppServiceNotifications(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSuppServiceNotifications is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSystemSelectionChannels(int i, boolean z, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedArray(radioAccessSpecifierArr, 0);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void startNetworkScan(int i, NetworkScanRequest networkScanRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(networkScanRequest, 0);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkScan is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void stopNetworkScan(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopNetworkScan is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void supplyNetworkDepersonalization(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyNetworkDepersonalization is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setUsageSetting(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setUsageSetting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getUsageSetting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(36, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsageSetting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setEmergencyMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(37, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setEmergencyMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void triggerEmergencyNetworkScan(int i, EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(emergencyNetworkScanTrigger, 0);
                    if (this.mRemote.transact(38, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method triggerEmergencyNetworkScan is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void cancelEmergencyNetworkScan(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(39, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelEmergencyNetworkScan is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void exitEmergencyMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(40, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method exitEmergencyMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNullCipherAndIntegrityEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(41, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNullCipherAndIntegrityEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isNullCipherAndIntegrityEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(42, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isNullCipherAndIntegrityEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isN1ModeEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(43, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isN1ModeEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setN1ModeEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(44, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setN1ModeEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isCellularIdentifierTransparencyEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(45, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isCellularIdentifierTransparencyEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(46, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCellularIdentifierTransparencyEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(47, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSecurityAlgorithmsUpdatedEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isSecurityAlgorithmsUpdatedEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(48, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isSecurityAlgorithmsUpdatedEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSatellitePlmn(int i, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    if (this.mRemote.transact(49, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSatellitePlmn is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSatelliteEnabledForCarrier(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(50, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSatelliteEnabledForCarrier is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isSatelliteEnabledForCarrier(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(51, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isSatelliteEnabledForCarrier is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
