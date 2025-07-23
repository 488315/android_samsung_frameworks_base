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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IMiscPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean changeLockScreenString(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.IMiscPolicy
            public void clearAllGlobalProxy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void clearNotificationDialog() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public List<String> getAppUidBrowserList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getAppUidFromSocketPortNumber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int getCredentialsFails(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getCurrentLockScreenString(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public List<String> getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProxyProperties) obtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IMiscPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SimChangeInfo) obtain2.readTypedObject(SimChangeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties getProxyForSsid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProxyProperties) obtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String getSystemActiveFont(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public float getSystemActiveFontSize(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public float[] getSystemFontSizes(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String[] getSystemFonts(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isGlobalProxyAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isNFCStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean isNFCStateChangeAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void refreshCredentialsDialogFails() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public ProxyProperties retrieveExternalProxy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ProxyProperties) obtain2.readTypedObject(ProxyProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public String retrieveProxyCredentials(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setCredentialsFails(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(proxyProperties, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iProxyCredentialsCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public void showCredentialsDialogNotification(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IMiscPolicy
            public boolean startNFC(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMiscPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMiscPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMiscPolicy)) ? new Proxy(iBinder) : (IMiscPolicy) queryLocalInterface;
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
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerBytes(contextInfo, uri, readString, readLong, readString2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean changeLockScreenString = changeLockScreenString(contextInfo2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeLockScreenString);
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
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean systemActiveFont = setSystemActiveFont(contextInfo5, readString4, readString5);
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
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean systemActiveFontSize = setSystemActiveFontSize(contextInfo8, readFloat);
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowNFCStateChange = allowNFCStateChange(contextInfo11, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowNFCStateChange);
                    return true;
                case 12:
                    boolean isNFCStateChangeAllowed = isNFCStateChangeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNFCStateChangeAllowed);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean startNFC = startNFC(contextInfo12, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startNFC);
                    return true;
                case 14:
                    boolean isNFCStarted = isNFCStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNFCStarted);
                    return true;
                case 15:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int globalProxyEnforcingFirewallPermission = setGlobalProxyEnforcingFirewallPermission(contextInfo13, readString6, readInt, createStringArrayList);
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
                    int clearGlobalProxyEnableEnforcingFirewallPermission = clearGlobalProxyEnableEnforcingFirewallPermission(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearGlobalProxyEnableEnforcingFirewallPermission);
                    return true;
                case 20:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearGlobalProxyEnableEnforcingSecurityPermission = clearGlobalProxyEnableEnforcingSecurityPermission(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearGlobalProxyEnableEnforcingSecurityPermission);
                    return true;
                case 21:
                    boolean isGlobalProxyAllowed = isGlobalProxyAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGlobalProxyAllowed);
                    return true;
                case 22:
                    String readString7 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCredentialsFails(readString7, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int credentialsFails = getCredentialsFails(readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialsFails);
                    return true;
                case 24:
                    String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String retrieveProxyCredentials = retrieveProxyCredentials(readString9, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(retrieveProxyCredentials);
                    return true;
                case 25:
                    clearAllGlobalProxy();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ProxyProperties retrieveExternalProxy = retrieveExternalProxy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(retrieveExternalProxy, 1);
                    return true;
                case 27:
                    List<String> appUidBrowserList = getAppUidBrowserList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(appUidBrowserList);
                    return true;
                case 28:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showCredentialsDialogNotification(readString10);
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
                    IProxyCredentialsCallback asInterface = IProxyCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setProxyCredentials(bundle, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ProxyProperties proxyForSsid = getProxyForSsid(readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(proxyForSsid, 1);
                    return true;
                case 33:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String appUidFromSocketPortNumber = getAppUidFromSocketPortNumber(readInt4);
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
