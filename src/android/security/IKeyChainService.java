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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyChainService)) {
                return (IKeyChainService) queryLocalInterface;
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String requestPrivateKey = requestPrivateKey(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(requestPrivateKey);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] certificate = getCertificate(readString2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificate);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] caCertificates = getCaCertificates(readString3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(caCertificates);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserSelectable = isUserSelectable(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSelectable);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(readString5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (ParcelableKeyGenParameterSpec) parcel.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    int generateKeyPair = generateKeyPair(readString6, parcelableKeyGenParameterSpec);
                    parcel2.writeNoException();
                    parcel2.writeInt(generateKeyPair);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean keyPairCertificate = setKeyPairCertificate(readString7, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyPairCertificate);
                    return true;
                case 8:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String installCaCertificate = installCaCertificate(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(installCaCertificate);
                    return true;
                case 9:
                    byte[] createByteArray4 = parcel.createByteArray();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    String readString8 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installKeyPair = installKeyPair(createByteArray4, createByteArray5, createByteArray6, readString8, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installKeyPair);
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeKeyPair = removeKeyPair(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeKeyPair);
                    return true;
                case 11:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsKeyPair = containsKeyPair(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsKeyPair);
                    return true;
                case 12:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] grants = getGrants(readString11);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(grants);
                    return true;
                case 13:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteCaCertificate = deleteCaCertificate(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteCaCertificate);
                    return true;
                case 14:
                    boolean reset = reset();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reset);
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
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsCaAlias = containsCaAlias(readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsCaAlias);
                    return true;
                case 18:
                    String readString14 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] encodedCaCertificate = getEncodedCaCertificate(readString14, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(encodedCaCertificate);
                    return true;
                case 19:
                    String readString15 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> caCertificateChainAliases = getCaCertificateChainAliases(readString15, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(caCertificateChainAliases);
                    return true;
                case 20:
                    String readString16 = parcel.readString();
                    AppUriAuthenticationPolicy appUriAuthenticationPolicy = (AppUriAuthenticationPolicy) parcel.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagementApp(readString16, appUriAuthenticationPolicy);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean hasCredentialManagementApp = hasCredentialManagementApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCredentialManagementApp);
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
                    String readString17 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    String predefinedAliasForPackageAndUri = getPredefinedAliasForPackageAndUri(readString17, uri);
                    parcel2.writeNoException();
                    parcel2.writeString(predefinedAliasForPackageAndUri);
                    return true;
                case 25:
                    removeCredentialManagementApp();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCredentialManagementApp = isCredentialManagementApp(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCredentialManagementApp);
                    return true;
                case 27:
                    int readInt2 = parcel.readInt();
                    String readString19 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean grant = setGrant(readInt2, readString19, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(grant);
                    return true;
                case 28:
                    int readInt3 = parcel.readInt();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasGrant = hasGrant(readInt3, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasGrant);
                    return true;
                case 29:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiKeyGrantAsUser = getWifiKeyGrantAsUser(readString21);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiKeyGrantAsUser);
                    return true;
                case 30:
                    String readString22 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] certificateFromTrustCredential = getCertificateFromTrustCredential(readString22, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificateFromTrustCredential);
                    return true;
                case 31:
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String certificateAlias = getCertificateAlias(createByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeString(certificateAlias);
                    return true;
                case 32:
                    List<String> allSystemAliases = allSystemAliases();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allSystemAliases);
                    return true;
                case 33:
                    List<String> userAliases = userAliases();
                    parcel2.writeNoException();
                    parcel2.writeStringList(userAliases);
                    return true;
                case 34:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsAlias = containsAlias(readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsAlias);
                    return true;
                case 35:
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] findIssuer = findIssuer(createByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(findIssuer);
                    return true;
                case 36:
                    String readString24 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteEntry = deleteEntry(readString24, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteEntry);
                    return true;
                case 37:
                    String readString25 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] listAliases = listAliases(readString25, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listAliases);
                    return true;
                case 38:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] certificateSystem = getCertificateSystem(readString26, readString27, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificateSystem);
                    return true;
                case 39:
                    String readString28 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean contains = contains(readString28, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contains);
                    return true;
                case 40:
                    String readString29 = parcel.readString();
                    byte[] createByteArray9 = parcel.createByteArray();
                    byte[] createByteArray10 = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateKeyPair = updateKeyPair(readString29, createByteArray9, createByteArray10, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateKeyPair);
                    return true;
                case 41:
                    String readString30 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCertificateEntry = isCertificateEntry(readString30, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateEntry);
                    return true;
                case 42:
                    String readString31 = parcel.readString();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean attestKey = attestKey(readString31, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(attestKey);
                    return true;
                case 43:
                    String readString32 = parcel.readString();
                    byte[] createByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean certificateChain = setCertificateChain(readString32, createByteArray12);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCaCertificates(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isUserSelectable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setUserSelectable(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int generateKeyPair(String str, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setKeyPairCertificate(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String installCaCertificate(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean removeKeyPair(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsKeyPair(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int[] getGrants(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteCaCertificate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean reset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getUserCaAliases() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StringParceledListSlice) obtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getSystemCaAliases() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StringParceledListSlice) obtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsCaAlias(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getEncodedCaCertificate(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> getCaCertificateChainAliases(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setCredentialManagementApp(String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(appUriAuthenticationPolicy, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasCredentialManagementApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCredentialManagementAppPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppUriAuthenticationPolicy) obtain2.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getPredefinedAliasForPackageAndUri(String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void removeCredentialManagementApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCredentialManagementApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setGrant(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasGrant(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getWifiKeyGrantAsUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateFromTrustCredential(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCertificateAlias(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> allSystemAliases() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> userAliases() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsAlias(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] findIssuer(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteEntry(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String[] listAliases(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateSystem(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean contains(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean updateKeyPair(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCertificateEntry(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean attestKey(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setCertificateChain(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
