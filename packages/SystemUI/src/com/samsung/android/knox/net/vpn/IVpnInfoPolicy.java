package com.samsung.android.knox.net.vpn;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IVpnInfoPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.IVpnInfoPolicy";

    public class Default implements IVpnInfoPolicy {
        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean deleteProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getAlwaysOnProfile(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getCaCertificate(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public List<String> getDnsDomains(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public List<String> getDnsServers(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public List<String> getForwardRoutes(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getId(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getIpSecIdentifier(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getL2TPSecret(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getName(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getOcspServerUrl(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getPresharedKey(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getServerName(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getState(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public List<String> getSupportedConnectionTypes(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getType(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getUserCertificate(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getUserName(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getUserNameById(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getUserPwd(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String getUserPwdById(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public String[] getVPNList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isAdminProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setId(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setServerName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setUserName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setUserPassword(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public boolean setVpnProfile(String str) throws RemoteException {
            return false;
        }
    }

    boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) throws RemoteException;

    boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) throws RemoteException;

    boolean deleteProfile(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo) throws RemoteException;

    String getAlwaysOnProfile(ContextInfo contextInfo) throws RemoteException;

    String getCaCertificate(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getDnsDomains(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getDnsServers(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getForwardRoutes(ContextInfo contextInfo, String str) throws RemoteException;

    String getId(ContextInfo contextInfo, String str) throws RemoteException;

    String getIpSecIdentifier(ContextInfo contextInfo, String str) throws RemoteException;

    String getL2TPSecret(ContextInfo contextInfo, String str) throws RemoteException;

    String getName(ContextInfo contextInfo, String str) throws RemoteException;

    String getOcspServerUrl(ContextInfo contextInfo, String str) throws RemoteException;

    String getPresharedKey(ContextInfo contextInfo, String str) throws RemoteException;

    String getServerName(ContextInfo contextInfo, String str) throws RemoteException;

    String getState(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getSupportedConnectionTypes(ContextInfo contextInfo) throws RemoteException;

    String getType(ContextInfo contextInfo, String str) throws RemoteException;

    String getUserCertificate(ContextInfo contextInfo, String str) throws RemoteException;

    String getUserName(ContextInfo contextInfo, String str) throws RemoteException;

    String getUserNameById(ContextInfo contextInfo, String str) throws RemoteException;

    String getUserPwd(ContextInfo contextInfo, String str) throws RemoteException;

    String getUserPwdById(ContextInfo contextInfo, String str) throws RemoteException;

    String[] getVPNList(ContextInfo contextInfo) throws RemoteException;

    boolean isAdminProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean setId(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) throws RemoteException;

    boolean setName(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setServerName(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setUserName(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setUserPassword(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setVpnProfile(String str) throws RemoteException;

    public abstract class Stub extends Binder implements IVpnInfoPolicy {
        public static final int TRANSACTION_allowOnlySecureConnections = 36;
        public static final int TRANSACTION_allowUserAddProfiles = 45;
        public static final int TRANSACTION_allowUserChangeProfiles = 43;
        public static final int TRANSACTION_allowUserSetAlwaysOn = 41;
        public static final int TRANSACTION_checkRacoonSecurity = 38;
        public static final int TRANSACTION_createProfile = 1;
        public static final int TRANSACTION_deleteProfile = 2;
        public static final int TRANSACTION_getAllVpnSettingsProfiles = 50;
        public static final int TRANSACTION_getAlwaysOnProfile = 40;
        public static final int TRANSACTION_getCaCertificate = 11;
        public static final int TRANSACTION_getDnsDomains = 31;
        public static final int TRANSACTION_getDnsServers = 29;
        public static final int TRANSACTION_getForwardRoutes = 33;
        public static final int TRANSACTION_getId = 22;
        public static final int TRANSACTION_getIpSecIdentifier = 35;
        public static final int TRANSACTION_getL2TPSecret = 26;
        public static final int TRANSACTION_getName = 17;
        public static final int TRANSACTION_getOcspServerUrl = 48;
        public static final int TRANSACTION_getPresharedKey = 9;
        public static final int TRANSACTION_getServerName = 21;
        public static final int TRANSACTION_getState = 23;
        public static final int TRANSACTION_getSupportedConnectionTypes = 49;
        public static final int TRANSACTION_getType = 16;
        public static final int TRANSACTION_getUserCertificate = 13;
        public static final int TRANSACTION_getUserName = 18;
        public static final int TRANSACTION_getUserNameById = 51;
        public static final int TRANSACTION_getUserPwd = 19;
        public static final int TRANSACTION_getUserPwdById = 52;
        public static final int TRANSACTION_getVPNList = 20;
        public static final int TRANSACTION_isAdminProfile = 24;
        public static final int TRANSACTION_isL2TPSecretEnabled = 27;
        public static final int TRANSACTION_isOnlySecureConnectionsAllowed = 37;
        public static final int TRANSACTION_isPPTPEncryptionEnabled = 15;
        public static final int TRANSACTION_isUserAddProfilesAllowed = 46;
        public static final int TRANSACTION_isUserChangeProfilesAllowed = 44;
        public static final int TRANSACTION_isUserSetAlwaysOnAllowed = 42;
        public static final int TRANSACTION_setAlwaysOnProfile = 39;
        public static final int TRANSACTION_setCaCertificate = 10;
        public static final int TRANSACTION_setDnsDomains = 30;
        public static final int TRANSACTION_setDnsServers = 28;
        public static final int TRANSACTION_setEncryptionEnabledForPPTP = 14;
        public static final int TRANSACTION_setForwardRoutes = 32;
        public static final int TRANSACTION_setId = 5;
        public static final int TRANSACTION_setIpSecIdentifier = 34;
        public static final int TRANSACTION_setL2TPSecret = 25;
        public static final int TRANSACTION_setName = 3;
        public static final int TRANSACTION_setOcspServerUrl = 47;
        public static final int TRANSACTION_setPresharedKey = 8;
        public static final int TRANSACTION_setServerName = 4;
        public static final int TRANSACTION_setUserCertificate = 12;
        public static final int TRANSACTION_setUserName = 6;
        public static final int TRANSACTION_setUserPassword = 7;
        public static final int TRANSACTION_setVpnProfile = 53;

        class Proxy implements IVpnInfoPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(vpnAdminProfile, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean deleteProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getAlwaysOnProfile(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getCaCertificate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public List<String> getDnsDomains(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public List<String> getDnsServers(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public List<String> getForwardRoutes(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getId(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IVpnInfoPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getIpSecIdentifier(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getL2TPSecret(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getName(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getOcspServerUrl(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getPresharedKey(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getServerName(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getState(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public List<String> getSupportedConnectionTypes(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getType(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getUserCertificate(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getUserName(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getUserNameById(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getUserPwd(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String getUserPwdById(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public String[] getVPNList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isAdminProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setId(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setServerName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setUserName(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setUserPassword(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public boolean setVpnProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IVpnInfoPolicy.DESCRIPTOR);
        }

        public static IVpnInfoPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVpnInfoPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVpnInfoPolicy)) ? new Proxy(iBinder) : (IVpnInfoPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVpnInfoPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVpnInfoPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    VpnAdminProfile vpnAdminProfile = (VpnAdminProfile) parcel.readTypedObject(VpnAdminProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCreateProfile = createProfile(contextInfo, vpnAdminProfile);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCreateProfile);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteProfile = deleteProfile(contextInfo2, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteProfile);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean name = setName(contextInfo3, string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(name);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean serverName = setServerName(contextInfo4, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverName);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean id = setId(contextInfo5, string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(id);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userName = setUserName(contextInfo6, string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userName);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userPassword = setUserPassword(contextInfo7, string10, string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userPassword);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean presharedKey = setPresharedKey(contextInfo8, string12, string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(presharedKey);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String presharedKey2 = getPresharedKey(contextInfo9, string14);
                    parcel2.writeNoException();
                    parcel2.writeString(presharedKey2);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean caCertificate = setCaCertificate(contextInfo10, string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(caCertificate);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String caCertificate2 = getCaCertificate(contextInfo11, string17);
                    parcel2.writeNoException();
                    parcel2.writeString(caCertificate2);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userCertificate = setUserCertificate(contextInfo12, string18, string19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userCertificate);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userCertificate2 = getUserCertificate(contextInfo13, string20);
                    parcel2.writeNoException();
                    parcel2.writeString(userCertificate2);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean encryptionEnabledForPPTP = setEncryptionEnabledForPPTP(contextInfo14, string21, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(encryptionEnabledForPPTP);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPPTPEncryptionEnabled = isPPTPEncryptionEnabled(contextInfo15, string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPPTPEncryptionEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String type = getType(contextInfo16, string23);
                    parcel2.writeNoException();
                    parcel2.writeString(type);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String name2 = getName(contextInfo17, string24);
                    parcel2.writeNoException();
                    parcel2.writeString(name2);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userName2 = getUserName(contextInfo18, string25);
                    parcel2.writeNoException();
                    parcel2.writeString(userName2);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userPwd = getUserPwd(contextInfo19, string26);
                    parcel2.writeNoException();
                    parcel2.writeString(userPwd);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] vPNList = getVPNList(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(vPNList);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String serverName2 = getServerName(contextInfo21, string27);
                    parcel2.writeNoException();
                    parcel2.writeString(serverName2);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String id2 = getId(contextInfo22, string28);
                    parcel2.writeNoException();
                    parcel2.writeString(id2);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String state = getState(contextInfo23, string29);
                    parcel2.writeNoException();
                    parcel2.writeString(state);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminProfile = isAdminProfile(contextInfo24, string30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminProfile);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string31 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean l2TPSecret = setL2TPSecret(contextInfo25, string31, z2, string32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(l2TPSecret);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String l2TPSecret2 = getL2TPSecret(contextInfo26, string33);
                    parcel2.writeNoException();
                    parcel2.writeString(l2TPSecret2);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsL2TPSecretEnabled = isL2TPSecretEnabled(contextInfo27, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsL2TPSecretEnabled);
                    return true;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string35 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean dnsServers = setDnsServers(contextInfo28, string35, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dnsServers);
                    return true;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dnsServers2 = getDnsServers(contextInfo29, string36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(dnsServers2);
                    return true;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string37 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean dnsDomains = setDnsDomains(contextInfo30, string37, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dnsDomains);
                    return true;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dnsDomains2 = getDnsDomains(contextInfo31, string38);
                    parcel2.writeNoException();
                    parcel2.writeStringList(dnsDomains2);
                    return true;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string39 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean forwardRoutes = setForwardRoutes(contextInfo32, string39, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forwardRoutes);
                    return true;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> forwardRoutes2 = getForwardRoutes(contextInfo33, string40);
                    parcel2.writeNoException();
                    parcel2.writeStringList(forwardRoutes2);
                    return true;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean ipSecIdentifier = setIpSecIdentifier(contextInfo34, string41, string42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ipSecIdentifier);
                    return true;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ipSecIdentifier2 = getIpSecIdentifier(contextInfo35, string43);
                    parcel2.writeNoException();
                    parcel2.writeString(ipSecIdentifier2);
                    return true;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOnlySecureConnections = allowOnlySecureConnections(contextInfo36, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOnlySecureConnections);
                    return true;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOnlySecureConnectionsAllowed = isOnlySecureConnectionsAllowed(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOnlySecureConnectionsAllowed);
                    return true;
                case 38:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean zCheckRacoonSecurity = checkRacoonSecurity(contextInfo38, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckRacoonSecurity);
                    return true;
                case 39:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnProfile = setAlwaysOnProfile(contextInfo39, string44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnProfile);
                    return true;
                case 40:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String alwaysOnProfile2 = getAlwaysOnProfile(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnProfile2);
                    return true;
                case 41:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserSetAlwaysOn = allowUserSetAlwaysOn(contextInfo41, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserSetAlwaysOn);
                    return true;
                case 42:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserSetAlwaysOnAllowed = isUserSetAlwaysOnAllowed(contextInfo42, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserSetAlwaysOnAllowed);
                    return true;
                case 43:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserChangeProfiles = allowUserChangeProfiles(contextInfo43, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserChangeProfiles);
                    return true;
                case 44:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserChangeProfilesAllowed = isUserChangeProfilesAllowed(contextInfo44, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserChangeProfilesAllowed);
                    return true;
                case 45:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserAddProfiles = allowUserAddProfiles(contextInfo45, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserAddProfiles);
                    return true;
                case 46:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserAddProfilesAllowed = isUserAddProfilesAllowed(contextInfo46, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserAddProfilesAllowed);
                    return true;
                case 47:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string45 = parcel.readString();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean ocspServerUrl = setOcspServerUrl(contextInfo47, string45, string46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ocspServerUrl);
                    return true;
                case 48:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ocspServerUrl2 = getOcspServerUrl(contextInfo48, string47);
                    parcel2.writeNoException();
                    parcel2.writeString(ocspServerUrl2);
                    return true;
                case 49:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> supportedConnectionTypes = getSupportedConnectionTypes(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedConnectionTypes);
                    return true;
                case 50:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> allVpnSettingsProfiles = getAllVpnSettingsProfiles(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allVpnSettingsProfiles);
                    return true;
                case 51:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userNameById = getUserNameById(contextInfo51, string48);
                    parcel2.writeNoException();
                    parcel2.writeString(userNameById);
                    return true;
                case 52:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userPwdById = getUserPwdById(contextInfo52, string49);
                    parcel2.writeNoException();
                    parcel2.writeString(userPwdById);
                    return true;
                case 53:
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean vpnProfile = setVpnProfile(string50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(vpnProfile);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
