package com.samsung.android.knox.net.vpn;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IVpnInfoPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.IVpnInfoPolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IVpnInfoPolicy {
        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean deleteProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getAlwaysOnProfile(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getCaCertificate(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final List<String> getDnsDomains(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final List<String> getDnsServers(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final List<String> getForwardRoutes(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getId(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getIpSecIdentifier(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getL2TPSecret(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getName(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getOcspServerUrl(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getPresharedKey(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getServerName(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getState(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final List<String> getSupportedConnectionTypes(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getType(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getUserCertificate(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getUserName(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getUserNameById(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getUserPwd(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String getUserPwdById(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final String[] getVPNList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isAdminProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setId(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setName(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setServerName(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setUserName(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setUserPassword(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
        public final boolean setVpnProfile(String str) {
            return false;
        }
    }

    boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z);

    boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z);

    boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z);

    boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z);

    boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr);

    boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile);

    boolean deleteProfile(ContextInfo contextInfo, String str);

    List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo);

    String getAlwaysOnProfile(ContextInfo contextInfo);

    String getCaCertificate(ContextInfo contextInfo, String str);

    List<String> getDnsDomains(ContextInfo contextInfo, String str);

    List<String> getDnsServers(ContextInfo contextInfo, String str);

    List<String> getForwardRoutes(ContextInfo contextInfo, String str);

    String getId(ContextInfo contextInfo, String str);

    String getIpSecIdentifier(ContextInfo contextInfo, String str);

    String getL2TPSecret(ContextInfo contextInfo, String str);

    String getName(ContextInfo contextInfo, String str);

    String getOcspServerUrl(ContextInfo contextInfo, String str);

    String getPresharedKey(ContextInfo contextInfo, String str);

    String getServerName(ContextInfo contextInfo, String str);

    String getState(ContextInfo contextInfo, String str);

    List<String> getSupportedConnectionTypes(ContextInfo contextInfo);

    String getType(ContextInfo contextInfo, String str);

    String getUserCertificate(ContextInfo contextInfo, String str);

    String getUserName(ContextInfo contextInfo, String str);

    String getUserNameById(ContextInfo contextInfo, String str);

    String getUserPwd(ContextInfo contextInfo, String str);

    String getUserPwdById(ContextInfo contextInfo, String str);

    String[] getVPNList(ContextInfo contextInfo);

    boolean isAdminProfile(ContextInfo contextInfo, String str);

    boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str);

    boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo);

    boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str);

    boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z);

    boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z);

    boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z);

    boolean setAlwaysOnProfile(ContextInfo contextInfo, String str);

    boolean setCaCertificate(ContextInfo contextInfo, String str, String str2);

    boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list);

    boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list);

    boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z);

    boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list);

    boolean setId(ContextInfo contextInfo, String str, String str2);

    boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2);

    boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2);

    boolean setName(ContextInfo contextInfo, String str, String str2);

    boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2);

    boolean setPresharedKey(ContextInfo contextInfo, String str, String str2);

    boolean setServerName(ContextInfo contextInfo, String str, String str2);

    boolean setUserCertificate(ContextInfo contextInfo, String str, String str2);

    boolean setUserName(ContextInfo contextInfo, String str, String str2);

    boolean setUserPassword(ContextInfo contextInfo, String str, String str2);

    boolean setVpnProfile(String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IVpnInfoPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IVpnInfoPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(vpnAdminProfile, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean deleteProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final List<String> getAllVpnSettingsProfiles(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getAlwaysOnProfile(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getCaCertificate(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final List<String> getDnsDomains(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final List<String> getDnsServers(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final List<String> getForwardRoutes(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getId(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IVpnInfoPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getIpSecIdentifier(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getL2TPSecret(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getName(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getOcspServerUrl(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getPresharedKey(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getServerName(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getState(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final List<String> getSupportedConnectionTypes(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getType(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getUserCertificate(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getUserName(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getUserNameById(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getUserPwd(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String getUserPwdById(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final String[] getVPNList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isAdminProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setDnsDomains(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setDnsServers(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setForwardRoutes(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setId(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setName(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setServerName(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setUserName(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setUserPassword(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IVpnInfoPolicy
            public final boolean setVpnProfile(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnInfoPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVpnInfoPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IVpnInfoPolicy)) ? new Proxy(iBinder) : (IVpnInfoPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean createProfile = createProfile(contextInfo, vpnAdminProfile);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createProfile);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteProfile = deleteProfile(contextInfo2, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteProfile);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean name = setName(contextInfo3, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(name);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean serverName = setServerName(contextInfo4, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverName);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean id = setId(contextInfo5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(id);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userName = setUserName(contextInfo6, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userName);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userPassword = setUserPassword(contextInfo7, readString10, readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userPassword);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean presharedKey = setPresharedKey(contextInfo8, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(presharedKey);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String presharedKey2 = getPresharedKey(contextInfo9, readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(presharedKey2);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean caCertificate = setCaCertificate(contextInfo10, readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(caCertificate);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String caCertificate2 = getCaCertificate(contextInfo11, readString17);
                    parcel2.writeNoException();
                    parcel2.writeString(caCertificate2);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userCertificate = setUserCertificate(contextInfo12, readString18, readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userCertificate);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userCertificate2 = getUserCertificate(contextInfo13, readString20);
                    parcel2.writeNoException();
                    parcel2.writeString(userCertificate2);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString21 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean encryptionEnabledForPPTP = setEncryptionEnabledForPPTP(contextInfo14, readString21, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(encryptionEnabledForPPTP);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPPTPEncryptionEnabled = isPPTPEncryptionEnabled(contextInfo15, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPPTPEncryptionEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String type = getType(contextInfo16, readString23);
                    parcel2.writeNoException();
                    parcel2.writeString(type);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String name2 = getName(contextInfo17, readString24);
                    parcel2.writeNoException();
                    parcel2.writeString(name2);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userName2 = getUserName(contextInfo18, readString25);
                    parcel2.writeNoException();
                    parcel2.writeString(userName2);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userPwd = getUserPwd(contextInfo19, readString26);
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
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String serverName2 = getServerName(contextInfo21, readString27);
                    parcel2.writeNoException();
                    parcel2.writeString(serverName2);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String id2 = getId(contextInfo22, readString28);
                    parcel2.writeNoException();
                    parcel2.writeString(id2);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String state = getState(contextInfo23, readString29);
                    parcel2.writeNoException();
                    parcel2.writeString(state);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAdminProfile = isAdminProfile(contextInfo24, readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminProfile);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString31 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean l2TPSecret = setL2TPSecret(contextInfo25, readString31, readBoolean2, readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(l2TPSecret);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String l2TPSecret2 = getL2TPSecret(contextInfo26, readString33);
                    parcel2.writeNoException();
                    parcel2.writeString(l2TPSecret2);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isL2TPSecretEnabled = isL2TPSecretEnabled(contextInfo27, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isL2TPSecretEnabled);
                    return true;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString35 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean dnsServers = setDnsServers(contextInfo28, readString35, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dnsServers);
                    return true;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dnsServers2 = getDnsServers(contextInfo29, readString36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(dnsServers2);
                    return true;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString37 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean dnsDomains = setDnsDomains(contextInfo30, readString37, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dnsDomains);
                    return true;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dnsDomains2 = getDnsDomains(contextInfo31, readString38);
                    parcel2.writeNoException();
                    parcel2.writeStringList(dnsDomains2);
                    return true;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString39 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean forwardRoutes = setForwardRoutes(contextInfo32, readString39, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forwardRoutes);
                    return true;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> forwardRoutes2 = getForwardRoutes(contextInfo33, readString40);
                    parcel2.writeNoException();
                    parcel2.writeStringList(forwardRoutes2);
                    return true;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean ipSecIdentifier = setIpSecIdentifier(contextInfo34, readString41, readString42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ipSecIdentifier);
                    return true;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ipSecIdentifier2 = getIpSecIdentifier(contextInfo35, readString43);
                    parcel2.writeNoException();
                    parcel2.writeString(ipSecIdentifier2);
                    return true;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOnlySecureConnections = allowOnlySecureConnections(contextInfo36, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOnlySecureConnections);
                    return true;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOnlySecureConnectionsAllowed = isOnlySecureConnectionsAllowed(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOnlySecureConnectionsAllowed);
                    return true;
                case 38:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean checkRacoonSecurity = checkRacoonSecurity(contextInfo38, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkRacoonSecurity);
                    return true;
                case 39:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnProfile = setAlwaysOnProfile(contextInfo39, readString44);
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
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserSetAlwaysOn = allowUserSetAlwaysOn(contextInfo41, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserSetAlwaysOn);
                    return true;
                case 42:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserSetAlwaysOnAllowed = isUserSetAlwaysOnAllowed(contextInfo42, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSetAlwaysOnAllowed);
                    return true;
                case 43:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserChangeProfiles = allowUserChangeProfiles(contextInfo43, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserChangeProfiles);
                    return true;
                case 44:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserChangeProfilesAllowed = isUserChangeProfilesAllowed(contextInfo44, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserChangeProfilesAllowed);
                    return true;
                case 45:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserAddProfiles = allowUserAddProfiles(contextInfo45, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserAddProfiles);
                    return true;
                case 46:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserAddProfilesAllowed = isUserAddProfilesAllowed(contextInfo46, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserAddProfilesAllowed);
                    return true;
                case 47:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString45 = parcel.readString();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean ocspServerUrl = setOcspServerUrl(contextInfo47, readString45, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ocspServerUrl);
                    return true;
                case 48:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ocspServerUrl2 = getOcspServerUrl(contextInfo48, readString47);
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
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userNameById = getUserNameById(contextInfo51, readString48);
                    parcel2.writeNoException();
                    parcel2.writeString(userNameById);
                    return true;
                case 52:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String userPwdById = getUserPwdById(contextInfo52, readString49);
                    parcel2.writeNoException();
                    parcel2.writeString(userPwdById);
                    return true;
                case 53:
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean vpnProfile = setVpnProfile(readString50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(vpnProfile);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
