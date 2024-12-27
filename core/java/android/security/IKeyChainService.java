package android.security;

import android.content.pm.StringParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import java.util.List;

/* loaded from: classes3.dex */
public interface IKeyChainService extends IInterface {
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

    public static class Default implements IKeyChainService {
        @Override // android.security.IKeyChainService
        public String requestPrivateKey(String alias) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificate(String alias) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCaCertificates(String alias) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean isUserSelectable(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public void setUserSelectable(String alias, boolean isUserSelectable) throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public int generateKeyPair(String algorithm, ParcelableKeyGenParameterSpec spec) throws RemoteException {
            return 0;
        }

        @Override // android.security.IKeyChainService
        public boolean setKeyPairCertificate(String alias, byte[] userCert, byte[] certChain) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String installCaCertificate(byte[] caCertificate) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean installKeyPair(byte[] privateKey, byte[] userCert, byte[] certChain, String alias, int uid) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean removeKeyPair(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean containsKeyPair(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public int[] getGrants(String alias) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean deleteCaCertificate(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean reset() throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public StringParceledListSlice getUserCaAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public StringParceledListSlice getSystemCaAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean containsCaAlias(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public byte[] getEncodedCaCertificate(String alias, boolean includeDeletedSystem) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public List<String> getCaCertificateChainAliases(String rootAlias, boolean includeDeletedSystem) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public void setCredentialManagementApp(String packageName, AppUriAuthenticationPolicy policy) throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean hasCredentialManagementApp() throws RemoteException {
            return false;
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
        public String getPredefinedAliasForPackageAndUri(String packageName, Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public void removeCredentialManagementApp() throws RemoteException {
        }

        @Override // android.security.IKeyChainService
        public boolean isCredentialManagementApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean setGrant(int uid, String alias, boolean value) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean hasGrant(int uid, String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String getWifiKeyGrantAsUser(String alias) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificateFromTrustCredential(String alias, boolean includeDeletedSystem) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public String getCertificateAlias(byte[] caCertificate) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public List<String> allSystemAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public List<String> userAliases() throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean containsAlias(String alias) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public byte[] findIssuer(byte[] caCertificate) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean deleteEntry(String alias, int uid) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public String[] listAliases(String type, int uid) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public byte[] getCertificateSystem(String alias, String type, int uid) throws RemoteException {
            return null;
        }

        @Override // android.security.IKeyChainService
        public boolean contains(String key, int uid) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean updateKeyPair(String alias, byte[] userCertificate, byte[] userCertificateChain, int uid) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean isCertificateEntry(String alias, int uid) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean attestKey(String alias, byte[] challenge) throws RemoteException {
            return false;
        }

        @Override // android.security.IKeyChainService
        public boolean setCertificateChain(String alias, byte[] certificateChain) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyChainService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyChainService)) {
                return (IKeyChainService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "reset";
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    String _result = requestPrivateKey(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result2 = getCertificate(_arg02);
                    reply.writeNoException();
                    reply.writeByteArray(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result3 = getCaCertificates(_arg03);
                    reply.writeNoException();
                    reply.writeByteArray(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result4 = isUserSelectable(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUserSelectable(_arg05, _arg1);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    ParcelableKeyGenParameterSpec _arg12 = (ParcelableKeyGenParameterSpec) data.readTypedObject(ParcelableKeyGenParameterSpec.CREATOR);
                    data.enforceNoDataAvail();
                    int _result5 = generateKeyPair(_arg06, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    byte[] _arg13 = data.createByteArray();
                    byte[] _arg2 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result6 = setKeyPairCertificate(_arg07, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 8:
                    byte[] _arg08 = data.createByteArray();
                    data.enforceNoDataAvail();
                    String _result7 = installCaCertificate(_arg08);
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 9:
                    byte[] _arg09 = data.createByteArray();
                    byte[] _arg14 = data.createByteArray();
                    byte[] _arg22 = data.createByteArray();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = installKeyPair(_arg09, _arg14, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result9 = removeKeyPair(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result10 = containsKeyPair(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 12:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    int[] _result11 = getGrants(_arg012);
                    reply.writeNoException();
                    reply.writeIntArray(_result11);
                    return true;
                case 13:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result12 = deleteCaCertificate(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 14:
                    boolean _result13 = reset();
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 15:
                    StringParceledListSlice _result14 = getUserCaAliases();
                    reply.writeNoException();
                    reply.writeTypedObject(_result14, 1);
                    return true;
                case 16:
                    StringParceledListSlice _result15 = getSystemCaAliases();
                    reply.writeNoException();
                    reply.writeTypedObject(_result15, 1);
                    return true;
                case 17:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result16 = containsCaAlias(_arg014);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 18:
                    String _arg015 = data.readString();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    byte[] _result17 = getEncodedCaCertificate(_arg015, _arg15);
                    reply.writeNoException();
                    reply.writeByteArray(_result17);
                    return true;
                case 19:
                    String _arg016 = data.readString();
                    boolean _arg16 = data.readBoolean();
                    data.enforceNoDataAvail();
                    List<String> _result18 = getCaCertificateChainAliases(_arg016, _arg16);
                    reply.writeNoException();
                    reply.writeStringList(_result18);
                    return true;
                case 20:
                    String _arg017 = data.readString();
                    AppUriAuthenticationPolicy _arg17 = (AppUriAuthenticationPolicy) data.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setCredentialManagementApp(_arg017, _arg17);
                    reply.writeNoException();
                    return true;
                case 21:
                    boolean _result19 = hasCredentialManagementApp();
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 22:
                    String _result20 = getCredentialManagementAppPackageName();
                    reply.writeNoException();
                    reply.writeString(_result20);
                    return true;
                case 23:
                    AppUriAuthenticationPolicy _result21 = getCredentialManagementAppPolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result21, 1);
                    return true;
                case 24:
                    String _arg018 = data.readString();
                    Uri _arg18 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    String _result22 = getPredefinedAliasForPackageAndUri(_arg018, _arg18);
                    reply.writeNoException();
                    reply.writeString(_result22);
                    return true;
                case 25:
                    removeCredentialManagementApp();
                    reply.writeNoException();
                    return true;
                case 26:
                    String _arg019 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result23 = isCredentialManagementApp(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 27:
                    int _arg020 = data.readInt();
                    String _arg19 = data.readString();
                    boolean _arg23 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result24 = setGrant(_arg020, _arg19, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 28:
                    int _arg021 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result25 = hasGrant(_arg021, _arg110);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 29:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    String _result26 = getWifiKeyGrantAsUser(_arg022);
                    reply.writeNoException();
                    reply.writeString(_result26);
                    return true;
                case 30:
                    String _arg023 = data.readString();
                    boolean _arg111 = data.readBoolean();
                    data.enforceNoDataAvail();
                    byte[] _result27 = getCertificateFromTrustCredential(_arg023, _arg111);
                    reply.writeNoException();
                    reply.writeByteArray(_result27);
                    return true;
                case 31:
                    byte[] _arg024 = data.createByteArray();
                    data.enforceNoDataAvail();
                    String _result28 = getCertificateAlias(_arg024);
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 32:
                    List<String> _result29 = allSystemAliases();
                    reply.writeNoException();
                    reply.writeStringList(_result29);
                    return true;
                case 33:
                    List<String> _result30 = userAliases();
                    reply.writeNoException();
                    reply.writeStringList(_result30);
                    return true;
                case 34:
                    String _arg025 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result31 = containsAlias(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 35:
                    byte[] _arg026 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result32 = findIssuer(_arg026);
                    reply.writeNoException();
                    reply.writeByteArray(_result32);
                    return true;
                case 36:
                    String _arg027 = data.readString();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result33 = deleteEntry(_arg027, _arg112);
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 37:
                    String _arg028 = data.readString();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result34 = listAliases(_arg028, _arg113);
                    reply.writeNoException();
                    reply.writeStringArray(_result34);
                    return true;
                case 38:
                    String _arg029 = data.readString();
                    String _arg114 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result35 = getCertificateSystem(_arg029, _arg114, _arg24);
                    reply.writeNoException();
                    reply.writeByteArray(_result35);
                    return true;
                case 39:
                    String _arg030 = data.readString();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result36 = contains(_arg030, _arg115);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 40:
                    String _arg031 = data.readString();
                    byte[] _arg116 = data.createByteArray();
                    byte[] _arg25 = data.createByteArray();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result37 = updateKeyPair(_arg031, _arg116, _arg25, _arg32);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 41:
                    String _arg032 = data.readString();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result38 = isCertificateEntry(_arg032, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 42:
                    String _arg033 = data.readString();
                    byte[] _arg118 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result39 = attestKey(_arg033, _arg118);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 43:
                    String _arg034 = data.readString();
                    byte[] _arg119 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result40 = setCertificateChain(_arg034, _arg119);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyChainService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.security.IKeyChainService
            public String requestPrivateKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificate(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCaCertificates(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isUserSelectable(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setUserSelectable(String alias, boolean isUserSelectable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeBoolean(isUserSelectable);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int generateKeyPair(String algorithm, ParcelableKeyGenParameterSpec spec) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(algorithm);
                    _data.writeTypedObject(spec, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setKeyPairCertificate(String alias, byte[] userCert, byte[] certChain) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(userCert);
                    _data.writeByteArray(certChain);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String installCaCertificate(byte[] caCertificate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(caCertificate);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean installKeyPair(byte[] privateKey, byte[] userCert, byte[] certChain, String alias, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(privateKey);
                    _data.writeByteArray(userCert);
                    _data.writeByteArray(certChain);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean removeKeyPair(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsKeyPair(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public int[] getGrants(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteCaCertificate(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean reset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getUserCaAliases() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    StringParceledListSlice _result = (StringParceledListSlice) _reply.readTypedObject(StringParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public StringParceledListSlice getSystemCaAliases() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    StringParceledListSlice _result = (StringParceledListSlice) _reply.readTypedObject(StringParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsCaAlias(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getEncodedCaCertificate(String alias, boolean includeDeletedSystem) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeBoolean(includeDeletedSystem);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> getCaCertificateChainAliases(String rootAlias, boolean includeDeletedSystem) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rootAlias);
                    _data.writeBoolean(includeDeletedSystem);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void setCredentialManagementApp(String packageName, AppUriAuthenticationPolicy policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasCredentialManagementApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCredentialManagementAppPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public AppUriAuthenticationPolicy getCredentialManagementAppPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    AppUriAuthenticationPolicy _result = (AppUriAuthenticationPolicy) _reply.readTypedObject(AppUriAuthenticationPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getPredefinedAliasForPackageAndUri(String packageName, Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(uri, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public void removeCredentialManagementApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCredentialManagementApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setGrant(int uid, String alias, boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(alias);
                    _data.writeBoolean(value);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean hasGrant(int uid, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(alias);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getWifiKeyGrantAsUser(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateFromTrustCredential(String alias, boolean includeDeletedSystem) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeBoolean(includeDeletedSystem);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String getCertificateAlias(byte[] caCertificate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(caCertificate);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> allSystemAliases() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public List<String> userAliases() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean containsAlias(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] findIssuer(byte[] caCertificate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(caCertificate);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean deleteEntry(String alias, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public String[] listAliases(String type, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeInt(uid);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public byte[] getCertificateSystem(String alias, String type, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeString(type);
                    _data.writeInt(uid);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean contains(String key, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(uid);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean updateKeyPair(String alias, byte[] userCertificate, byte[] userCertificateChain, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(userCertificate);
                    _data.writeByteArray(userCertificateChain);
                    _data.writeInt(uid);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean isCertificateEntry(String alias, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(uid);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean attestKey(String alias, byte[] challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(challenge);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IKeyChainService
            public boolean setCertificateChain(String alias, byte[] certificateChain) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(certificateChain);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }
    }
}
