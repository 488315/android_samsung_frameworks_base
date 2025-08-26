package android.security;

import android.content.pm.StringParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.List;

/* loaded from: classes3.dex */
public interface IKeyChainService extends IInterface {

    public static class Default implements IKeyChainService {
        @Override // android.security.IKeyChainService
        public List<String> allSystemAliases() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean attestKey(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean contains(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean containsAlias(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean containsCaAlias(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean containsKeyPair(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean deleteCaCertificate(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean deleteEntry(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public byte[] findIssuer(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public int generateKeyPair(String str, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException {
            return 0;
        }

        @Override // android.security.IKeyChainService
        public List<String> getCaCertificateChainAliases(String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCaCertificates(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificate(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public String getCertificateAlias(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificateFromTrustCredential(String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificateSystem(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public String getCredentialManagementAppPackageName() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getEncodedCaCertificate(String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public int[] getGrants(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public String getPredefinedAliasForPackageAndUri(String str, Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public StringParceledListSlice getSystemCaAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public StringParceledListSlice getUserCaAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public String getWifiKeyGrantAsUser(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean hasCredentialManagementApp() throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean hasGrant(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String installCaCertificate(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean isCertificateEntry(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean isCredentialManagementApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean isUserSelectable(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String[] listAliases(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public void removeCredentialManagementApp() throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean removeKeyPair(String str) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String requestPrivateKey(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean reset() throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean setCertificateChain(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public void setCredentialManagementApp(String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean setGrant(int i, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean setKeyPairCertificate(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public void setUserSelectable(String str, boolean z) throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean updateKeyPair(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public List<String> userAliases() throws RemoteException {
            return null;
        }
    }

    List<String> allSystemAliases() throws RemoteException;

    boolean attestKey(String str, byte[] bArr) throws RemoteException;

    boolean contains(String str, int i) throws RemoteException;

    boolean containsAlias(String str) throws RemoteException;

    boolean containsCaAlias(String str) throws RemoteException;

    boolean containsKeyPair(String str) throws RemoteException;

    boolean deleteCaCertificate(String str) throws RemoteException;

    boolean deleteEntry(String str, int i) throws RemoteException;

    byte[] findIssuer(byte[] bArr) throws RemoteException;

    int generateKeyPair(String str, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException;

    List<String> getCaCertificateChainAliases(String str, boolean z) throws RemoteException;

    byte[] getCaCertificates(String str) throws RemoteException;

    byte[] getCertificate(String str) throws RemoteException;

    String getCertificateAlias(byte[] bArr) throws RemoteException;

    byte[] getCertificateFromTrustCredential(String str, boolean z) throws RemoteException;

    byte[] getCertificateSystem(String str, String str2, int i) throws RemoteException;

    String getCredentialManagementAppPackageName() throws RemoteException;

    AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws RemoteException;

    byte[] getEncodedCaCertificate(String str, boolean z) throws RemoteException;

    int[] getGrants(String str) throws RemoteException;

    String getPredefinedAliasForPackageAndUri(String str, Uri uri) throws RemoteException;

    StringParceledListSlice getSystemCaAliases() throws RemoteException;

    StringParceledListSlice getUserCaAliases() throws RemoteException;

    String getWifiKeyGrantAsUser(String str) throws RemoteException;

    boolean hasCredentialManagementApp() throws RemoteException;

    boolean hasGrant(int i, String str) throws RemoteException;

    String installCaCertificate(byte[] bArr) throws RemoteException;

    boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, String str, int i) throws RemoteException;

    boolean isCertificateEntry(String str, int i) throws RemoteException;

    boolean isCredentialManagementApp(String str) throws RemoteException;

    boolean isUserSelectable(String str) throws RemoteException;

    String[] listAliases(String str, int i) throws RemoteException;

    void removeCredentialManagementApp() throws RemoteException;

    boolean removeKeyPair(String str) throws RemoteException;

    String requestPrivateKey(String str) throws RemoteException;

    boolean reset() throws RemoteException;

    boolean setCertificateChain(String str, byte[] bArr) throws RemoteException;

    void setCredentialManagementApp(String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws RemoteException;

    boolean setGrant(int i, String str, boolean z) throws RemoteException;

    boolean setKeyPairCertificate(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    void setUserSelectable(String str, boolean z) throws RemoteException;

    boolean updateKeyPair(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    List<String> userAliases() throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyChainService {
        public static final String DESCRIPTOR = "android.security.IKeyChainService";
        static final int TRANSACTION_allSystemAliases = 32;
        static final int TRANSACTION_attestKey = 42;
        static final int TRANSACTION_contains = 39;
        static final int TRANSACTION_containsAlias = 34;
        static final int TRANSACTION_containsCaAlias = 17;
        static final int TRANSACTION_containsKeyPair = 11;
        static final int TRANSACTION_deleteCaCertificate = 13;
        static final int TRANSACTION_deleteEntry = 36;
        static final int TRANSACTION_findIssuer = 35;
        static final int TRANSACTION_generateKeyPair = 6;
        static final int TRANSACTION_getCaCertificateChainAliases = 19;
        static final int TRANSACTION_getCaCertificates = 3;
        static final int TRANSACTION_getCertificate = 2;
        static final int TRANSACTION_getCertificateAlias = 31;
        static final int TRANSACTION_getCertificateFromTrustCredential = 30;
        static final int TRANSACTION_getCertificateSystem = 38;
        static final int TRANSACTION_getCredentialManagementAppPackageName = 22;
        static final int TRANSACTION_getCredentialManagementAppPolicy = 23;
        static final int TRANSACTION_getEncodedCaCertificate = 18;
        static final int TRANSACTION_getGrants = 12;
        static final int TRANSACTION_getPredefinedAliasForPackageAndUri = 24;
        static final int TRANSACTION_getSystemCaAliases = 16;
        static final int TRANSACTION_getUserCaAliases = 15;
        static final int TRANSACTION_getWifiKeyGrantAsUser = 29;
        static final int TRANSACTION_hasCredentialManagementApp = 21;
        static final int TRANSACTION_hasGrant = 28;
        static final int TRANSACTION_installCaCertificate = 8;
        static final int TRANSACTION_installKeyPair = 9;
        static final int TRANSACTION_isCertificateEntry = 41;
        static final int TRANSACTION_isCredentialManagementApp = 26;
        static final int TRANSACTION_isUserSelectable = 4;
        static final int TRANSACTION_listAliases = 37;
        static final int TRANSACTION_removeCredentialManagementApp = 25;
        static final int TRANSACTION_removeKeyPair = 10;
        static final int TRANSACTION_requestPrivateKey = 1;
        static final int TRANSACTION_reset = 14;
        static final int TRANSACTION_setCertificateChain = 43;
        static final int TRANSACTION_setCredentialManagementApp = 20;
        static final int TRANSACTION_setGrant = 27;
        static final int TRANSACTION_setKeyPairCertificate = 7;
        static final int TRANSACTION_setUserSelectable = 5;
        static final int TRANSACTION_updateKeyPair = 40;
        static final int TRANSACTION_userAliases = 33;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyChainService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyChainService)) {
                return (IKeyChainService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "requestPrivateKey";
                case 2:
                    return "getCertificate";
                case 3:
                    return "getCaCertificates";
                case 4:
                    return "isUserSelectable";
                case 5:
                    return "setUserSelectable";
                case 6:
                    return "generateKeyPair";
                case 7:
                    return "setKeyPairCertificate";
                case 8:
                    return "installCaCertificate";
                case 9:
                    return "installKeyPair";
                case 10:
                    return "removeKeyPair";
                case 11:
                    return "containsKeyPair";
                case 12:
                    return "getGrants";
                case 13:
                    return "deleteCaCertificate";
                case 14:
                    return Contract.Reset.PATH;
                case 15:
                    return "getUserCaAliases";
                case 16:
                    return "getSystemCaAliases";
                case 17:
                    return "containsCaAlias";
                case 18:
                    return "getEncodedCaCertificate";
                case 19:
                    return "getCaCertificateChainAliases";
                case 20:
                    return "setCredentialManagementApp";
                case 21:
                    return "hasCredentialManagementApp";
                case 22:
                    return "getCredentialManagementAppPackageName";
                case 23:
                    return "getCredentialManagementAppPolicy";
                case 24:
                    return "getPredefinedAliasForPackageAndUri";
                case 25:
                    return "removeCredentialManagementApp";
                case 26:
                    return "isCredentialManagementApp";
                case 27:
                    return "setGrant";
                case 28:
                    return "hasGrant";
                case 29:
                    return "getWifiKeyGrantAsUser";
                case 30:
                    return "getCertificateFromTrustCredential";
                case 31:
                    return "getCertificateAlias";
                case 32:
                    return "allSystemAliases";
                case 33:
                    return "userAliases";
                case 34:
                    return "containsAlias";
                case 35:
                    return "findIssuer";
                case 36:
                    return "deleteEntry";
                case 37:
                    return "listAliases";
                case 38:
                    return "getCertificateSystem";
                case 39:
                    return "contains";
                case 40:
                    return "updateKeyPair";
                case 41:
                    return "isCertificateEntry";
                case 42:
                    return "attestKey";
                case 43:
                    return "setCertificateChain";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strRequestPrivateKey = requestPrivateKey(string);
                    parcel2.writeNoException();
                    parcel2.writeString(strRequestPrivateKey);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] certificate = getCertificate(string2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificate);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] caCertificates = getCaCertificates(string3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(caCertificates);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserSelectable = isUserSelectable(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserSelectable);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(string5, z);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (ParcelableKeyGenParameterSpec) parcel.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iGenerateKeyPair = generateKeyPair(string6, parcelableKeyGenParameterSpec);
                    parcel2.writeNoException();
                    parcel2.writeInt(iGenerateKeyPair);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean keyPairCertificate = setKeyPairCertificate(string7, bArrCreateByteArray, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyPairCertificate);
                    return true;
                case 8:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String strInstallCaCertificate = installCaCertificate(bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(strInstallCaCertificate);
                    return true;
                case 9:
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    String string8 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zInstallKeyPair = installKeyPair(bArrCreateByteArray4, bArrCreateByteArray5, bArrCreateByteArray6, string8, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInstallKeyPair);
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveKeyPair = removeKeyPair(string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveKeyPair);
                    return true;
                case 11:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zContainsKeyPair = containsKeyPair(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zContainsKeyPair);
                    return true;
                case 12:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] grants = getGrants(string11);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(grants);
                    return true;
                case 13:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteCaCertificate = deleteCaCertificate(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteCaCertificate);
                    return true;
                case 14:
                    boolean zReset = reset();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReset);
                    return true;
                case 15:
                    StringParceledListSlice userCaAliases = getUserCaAliases();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCaAliases, 1);
                    return true;
                case 16:
                    StringParceledListSlice systemCaAliases = getSystemCaAliases();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemCaAliases, 1);
                    return true;
                case 17:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zContainsCaAlias = containsCaAlias(string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zContainsCaAlias);
                    return true;
                case 18:
                    String string14 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] encodedCaCertificate = getEncodedCaCertificate(string14, z2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(encodedCaCertificate);
                    return true;
                case 19:
                    String string15 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> caCertificateChainAliases = getCaCertificateChainAliases(string15, z3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(caCertificateChainAliases);
                    return true;
                case 20:
                    String string16 = parcel.readString();
                    AppUriAuthenticationPolicy appUriAuthenticationPolicy = (AppUriAuthenticationPolicy) parcel.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagementApp(string16, appUriAuthenticationPolicy);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean zHasCredentialManagementApp = hasCredentialManagementApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCredentialManagementApp);
                    return true;
                case 22:
                    String credentialManagementAppPackageName = getCredentialManagementAppPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(credentialManagementAppPackageName);
                    return true;
                case 23:
                    AppUriAuthenticationPolicy credentialManagementAppPolicy = getCredentialManagementAppPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialManagementAppPolicy, 1);
                    return true;
                case 24:
                    String string17 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    String predefinedAliasForPackageAndUri = getPredefinedAliasForPackageAndUri(string17, uri);
                    parcel2.writeNoException();
                    parcel2.writeString(predefinedAliasForPackageAndUri);
                    return true;
                case 25:
                    removeCredentialManagementApp();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsCredentialManagementApp = isCredentialManagementApp(string18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCredentialManagementApp);
                    return true;
                case 27:
                    int i4 = parcel.readInt();
                    String string19 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean grant = setGrant(i4, string19, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(grant);
                    return true;
                case 28:
                    int i5 = parcel.readInt();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasGrant = hasGrant(i5, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasGrant);
                    return true;
                case 29:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiKeyGrantAsUser = getWifiKeyGrantAsUser(string21);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiKeyGrantAsUser);
                    return true;
                case 30:
                    String string22 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] certificateFromTrustCredential = getCertificateFromTrustCredential(string22, z5);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificateFromTrustCredential);
                    return true;
                case 31:
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String certificateAlias = getCertificateAlias(bArrCreateByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeString(certificateAlias);
                    return true;
                case 32:
                    List<String> listAllSystemAliases = allSystemAliases();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listAllSystemAliases);
                    return true;
                case 33:
                    List<String> listUserAliases = userAliases();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listUserAliases);
                    return true;
                case 34:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zContainsAlias = containsAlias(string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zContainsAlias);
                    return true;
                case 35:
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrFindIssuer = findIssuer(bArrCreateByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrFindIssuer);
                    return true;
                case 36:
                    String string24 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteEntry = deleteEntry(string24, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteEntry);
                    return true;
                case 37:
                    String string25 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrListAliases = listAliases(string25, i7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListAliases);
                    return true;
                case 38:
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] certificateSystem = getCertificateSystem(string26, string27, i8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificateSystem);
                    return true;
                case 39:
                    String string28 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zContains = contains(string28, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zContains);
                    return true;
                case 40:
                    String string29 = parcel.readString();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateKeyPair = updateKeyPair(string29, bArrCreateByteArray9, bArrCreateByteArray10, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateKeyPair);
                    return true;
                case 41:
                    String string30 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateEntry = isCertificateEntry(string30, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateEntry);
                    return true;
                case 42:
                    String string31 = parcel.readString();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zAttestKey = attestKey(string31, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAttestKey);
                    return true;
                case 43:
                    String string32 = parcel.readString();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean certificateChain = setCertificateChain(string32, bArrCreateByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(certificateChain);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeyChainService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.security.IKeyChainService
            public String requestPrivateKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCaCertificates(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isUserSelectable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setUserSelectable(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int generateKeyPair(String str, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setKeyPairCertificate(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String installCaCertificate(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean removeKeyPair(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsKeyPair(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int[] getGrants(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteCaCertificate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean reset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getUserCaAliases() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StringParceledListSlice) parcelObtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getSystemCaAliases() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StringParceledListSlice) parcelObtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsCaAlias(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getEncodedCaCertificate(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> getCaCertificateChainAliases(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setCredentialManagementApp(String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(appUriAuthenticationPolicy, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasCredentialManagementApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCredentialManagementAppPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AppUriAuthenticationPolicy) parcelObtain2.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getPredefinedAliasForPackageAndUri(String str, Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void removeCredentialManagementApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCredentialManagementApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setGrant(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasGrant(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getWifiKeyGrantAsUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateFromTrustCredential(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCertificateAlias(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> allSystemAliases() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> userAliases() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsAlias(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] findIssuer(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteEntry(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String[] listAliases(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateSystem(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean contains(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean updateKeyPair(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCertificateEntry(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean attestKey(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setCertificateChain(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
