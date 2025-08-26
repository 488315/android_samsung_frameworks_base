package com.samsung.android.knox;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;
import com.samsung.android.knox.deviceinfo.SimChangeInfo;
import com.samsung.android.knox.net.ProxyProperties;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMiscPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IMiscPolicy";

    boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean changeLockScreenString(ContextInfo contextInfo, String str) throws RemoteException;

    void clearAllGlobalProxy() throws RemoteException;

    int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException;

    int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException;

    void clearNotificationDialog() throws RemoteException;

    List<String> getAppUidBrowserList() throws RemoteException;

    String getAppUidFromSocketPortNumber(int i) throws RemoteException;

    int getCredentialsFails(String str) throws RemoteException;

    String getCurrentLockScreenString(ContextInfo contextInfo) throws RemoteException;

    List<String> getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException;

    ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException;

    SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) throws RemoteException;

    ProxyProperties getProxyForSsid(String str) throws RemoteException;

    String getSystemActiveFont(ContextInfo contextInfo) throws RemoteException;

    float getSystemActiveFontSize(ContextInfo contextInfo) throws RemoteException;

    float[] getSystemFontSizes(ContextInfo contextInfo) throws RemoteException;

    String[] getSystemFonts(ContextInfo contextInfo) throws RemoteException;

    boolean isGlobalProxyAllowed() throws RemoteException;

    boolean isNFCStarted() throws RemoteException;

    boolean isNFCStateChangeAllowed() throws RemoteException;

    void refreshCredentialsDialogFails() throws RemoteException;

    ProxyProperties retrieveExternalProxy() throws RemoteException;

    String retrieveProxyCredentials(String str, int i) throws RemoteException;

    void setCredentialsFails(String str, int i) throws RemoteException;

    int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List<String> list) throws RemoteException;

    int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) throws RemoteException;

    void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException;

    void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) throws RemoteException;

    boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) throws RemoteException;

    void showCredentialsDialogNotification(String str) throws RemoteException;

    boolean startNFC(ContextInfo contextInfo, boolean z) throws RemoteException;

    public class Default implements IMiscPolicy {
        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean changeLockScreenString(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public List<String> getAppUidBrowserList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public String getAppUidFromSocketPortNumber(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public int getCredentialsFails(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public String getCurrentLockScreenString(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public List<String> getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public ProxyProperties getProxyForSsid(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public String getSystemActiveFont(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public float getSystemActiveFontSize(ContextInfo contextInfo) throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public float[] getSystemFontSizes(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public String[] getSystemFonts(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean isGlobalProxyAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean isNFCStarted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean isNFCStateChangeAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public ProxyProperties retrieveExternalProxy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public String retrieveProxyCredentials(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public boolean startNFC(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void clearAllGlobalProxy() throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void clearNotificationDialog() throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void refreshCredentialsDialogFails() throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void showCredentialsDialogNotification(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void setCredentialsFails(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IMiscPolicy
        public void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IMiscPolicy {
        public static final int TRANSACTION_allowNFCStateChange = 11;
        public static final int TRANSACTION_changeLockScreenString = 2;
        public static final int TRANSACTION_clearAllGlobalProxy = 25;
        public static final int TRANSACTION_clearGlobalProxyEnableEnforcingFirewallPermission = 19;
        public static final int TRANSACTION_clearGlobalProxyEnableEnforcingSecurityPermission = 20;
        public static final int TRANSACTION_clearNotificationDialog = 30;
        public static final int TRANSACTION_getAppUidBrowserList = 27;
        public static final int TRANSACTION_getAppUidFromSocketPortNumber = 33;
        public static final int TRANSACTION_getCredentialsFails = 23;
        public static final int TRANSACTION_getCurrentLockScreenString = 3;
        public static final int TRANSACTION_getGlobalProxyEnforcingFirewallPermission = 17;
        public static final int TRANSACTION_getGlobalProxyEnforcingSecurityPermission = 18;
        public static final int TRANSACTION_getLastSimChangeInfo = 4;
        public static final int TRANSACTION_getProxyForSsid = 32;
        public static final int TRANSACTION_getSystemActiveFont = 6;
        public static final int TRANSACTION_getSystemActiveFontSize = 9;
        public static final int TRANSACTION_getSystemFontSizes = 10;
        public static final int TRANSACTION_getSystemFonts = 7;
        public static final int TRANSACTION_isGlobalProxyAllowed = 21;
        public static final int TRANSACTION_isNFCStarted = 14;
        public static final int TRANSACTION_isNFCStateChangeAllowed = 12;
        public static final int TRANSACTION_refreshCredentialsDialogFails = 29;
        public static final int TRANSACTION_retrieveExternalProxy = 26;
        public static final int TRANSACTION_retrieveProxyCredentials = 24;
        public static final int TRANSACTION_setCredentialsFails = 22;
        public static final int TRANSACTION_setGlobalProxyEnforcingFirewallPermission = 15;
        public static final int TRANSACTION_setGlobalProxyEnforcingSecurityPermission = 16;
        public static final int TRANSACTION_setProxyCredentials = 31;
        public static final int TRANSACTION_setRingerBytes = 1;
        public static final int TRANSACTION_setSystemActiveFont = 5;
        public static final int TRANSACTION_setSystemActiveFontSize = 8;
        public static final int TRANSACTION_showCredentialsDialogNotification = 28;
        public static final int TRANSACTION_startNFC = 13;

        class Proxy implements IMiscPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean changeLockScreenString(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.IMiscPolicy
            public void clearAllGlobalProxy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void clearNotificationDialog() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public List<String> getAppUidBrowserList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getAppUidFromSocketPortNumber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int getCredentialsFails(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getCurrentLockScreenString(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public List<String> getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProxyProperties) parcelObtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IMiscPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SimChangeInfo) parcelObtain2.readTypedObject(SimChangeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties getProxyForSsid(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProxyProperties) parcelObtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getSystemActiveFont(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public float getSystemActiveFontSize(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public float[] getSystemFontSizes(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String[] getSystemFonts(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isGlobalProxyAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isNFCStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isNFCStateChangeAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void refreshCredentialsDialogFails() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties retrieveExternalProxy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ProxyProperties) parcelObtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String retrieveProxyCredentials(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setCredentialsFails(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(proxyProperties, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iProxyCredentialsCallback);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void showCredentialsDialogNotification(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean startNFC(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMiscPolicy.DESCRIPTOR);
        }

        public static IMiscPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMiscPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiscPolicy)) ? new Proxy(iBinder) : (IMiscPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMiscPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMiscPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerBytes(contextInfo, uri, string, j, string2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zChangeLockScreenString = changeLockScreenString(contextInfo2, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeLockScreenString);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String currentLockScreenString = getCurrentLockScreenString(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeString(currentLockScreenString);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    SimChangeInfo lastSimChangeInfo = getLastSimChangeInfo(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastSimChangeInfo, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean systemActiveFont = setSystemActiveFont(contextInfo5, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemActiveFont);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String systemActiveFont2 = getSystemActiveFont(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeString(systemActiveFont2);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] systemFonts = getSystemFonts(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(systemFonts);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean systemActiveFontSize = setSystemActiveFontSize(contextInfo8, f);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemActiveFontSize);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    float systemActiveFontSize2 = getSystemActiveFontSize(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeFloat(systemActiveFontSize2);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    float[] systemFontSizes = getSystemFontSizes(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(systemFontSizes);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowNFCStateChange = allowNFCStateChange(contextInfo11, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowNFCStateChange);
                    return true;
                case 12:
                    boolean zIsNFCStateChangeAllowed = isNFCStateChangeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNFCStateChangeAllowed);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zStartNFC = startNFC(contextInfo12, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartNFC);
                    return true;
                case 14:
                    boolean zIsNFCStarted = isNFCStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNFCStarted);
                    return true;
                case 15:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    int i3 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int globalProxyEnforcingFirewallPermission = setGlobalProxyEnforcingFirewallPermission(contextInfo13, string6, i3, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalProxyEnforcingFirewallPermission);
                    return true;
                case 16:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ProxyProperties proxyProperties = (ProxyProperties) parcel.readTypedObject(ProxyProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    int globalProxyEnforcingSecurityPermission = setGlobalProxyEnforcingSecurityPermission(contextInfo14, proxyProperties);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalProxyEnforcingSecurityPermission);
                    return true;
                case 17:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> globalProxyEnforcingFirewallPermission2 = getGlobalProxyEnforcingFirewallPermission(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeStringList(globalProxyEnforcingFirewallPermission2);
                    return true;
                case 18:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ProxyProperties globalProxyEnforcingSecurityPermission2 = getGlobalProxyEnforcingSecurityPermission(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalProxyEnforcingSecurityPermission2, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearGlobalProxyEnableEnforcingFirewallPermission = clearGlobalProxyEnableEnforcingFirewallPermission(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearGlobalProxyEnableEnforcingFirewallPermission);
                    return true;
                case 20:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearGlobalProxyEnableEnforcingSecurityPermission = clearGlobalProxyEnableEnforcingSecurityPermission(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearGlobalProxyEnableEnforcingSecurityPermission);
                    return true;
                case 21:
                    boolean zIsGlobalProxyAllowed = isGlobalProxyAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGlobalProxyAllowed);
                    return true;
                case 22:
                    String string7 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCredentialsFails(string7, i4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int credentialsFails = getCredentialsFails(string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialsFails);
                    return true;
                case 24:
                    String string9 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRetrieveProxyCredentials = retrieveProxyCredentials(string9, i5);
                    parcel2.writeNoException();
                    parcel2.writeString(strRetrieveProxyCredentials);
                    return true;
                case 25:
                    clearAllGlobalProxy();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ProxyProperties proxyPropertiesRetrieveExternalProxy = retrieveExternalProxy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(proxyPropertiesRetrieveExternalProxy, 1);
                    return true;
                case 27:
                    List<String> appUidBrowserList = getAppUidBrowserList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(appUidBrowserList);
                    return true;
                case 28:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showCredentialsDialogNotification(string10);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    refreshCredentialsDialogFails();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    clearNotificationDialog();
                    parcel2.writeNoException();
                    return true;
                case 31:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IProxyCredentialsCallback iProxyCredentialsCallbackAsInterface = IProxyCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setProxyCredentials(bundle, iProxyCredentialsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ProxyProperties proxyForSsid = getProxyForSsid(string11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(proxyForSsid, 1);
                    return true;
                case 33:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String appUidFromSocketPortNumber = getAppUidFromSocketPortNumber(i6);
                    parcel2.writeNoException();
                    parcel2.writeString(appUidFromSocketPortNumber);
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
