package com.samsung.android.knox.browser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IBrowserPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.browser.IBrowserPolicy";

    public class Default implements IBrowserPolicy {
        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean clearHttpProxy(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public String getHttpProxy(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean isUrlBlocked(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public boolean setHttpProxy(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
            return 0;
        }
    }

    boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) throws RemoteException;

    boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) throws RemoteException;

    boolean clearHttpProxy(ContextInfo contextInfo) throws RemoteException;

    boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) throws RemoteException;

    boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) throws RemoteException;

    String getHttpProxy(ContextInfo contextInfo) throws RemoteException;

    boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) throws RemoteException;

    List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException;

    boolean isUrlBlocked(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) throws RemoteException;

    boolean setHttpProxy(ContextInfo contextInfo, String str) throws RemoteException;

    int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException;

    int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException;

    int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list) throws RemoteException;

    int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list) throws RemoteException;

    int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException;

    int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IBrowserPolicy {
        public static final int TRANSACTION_addWebBookmarkBitmap = 6;
        public static final int TRANSACTION_addWebBookmarkByteBuffer = 7;
        public static final int TRANSACTION_clearHttpProxy = 4;
        public static final int TRANSACTION_deleteWebBookmark = 8;
        public static final int TRANSACTION_getBrowserSettingStatus = 2;
        public static final int TRANSACTION_getHttpProxy = 5;
        public static final int TRANSACTION_getURLFilterEnabledEnforcingBrowserPermission = 12;
        public static final int TRANSACTION_getURLFilterEnabledEnforcingFirewallPermission = 11;
        public static final int TRANSACTION_getURLFilterListEnforcingBrowserPermission = 16;
        public static final int TRANSACTION_getURLFilterListEnforcingFirewallPermission = 15;
        public static final int TRANSACTION_getURLFilterReportEnabledEnforcingBrowserPermission = 21;
        public static final int TRANSACTION_getURLFilterReportEnabledEnforcingFirewallPermission = 20;
        public static final int TRANSACTION_getURLFilterReportEnforcingBrowserPermission = 23;
        public static final int TRANSACTION_getURLFilterReportEnforcingFirewallPermission = 22;
        public static final int TRANSACTION_isUrlBlocked = 17;
        public static final int TRANSACTION_setBrowserSettingStatus = 1;
        public static final int TRANSACTION_setHttpProxy = 3;
        public static final int TRANSACTION_setURLFilterEnabledEnforcingBrowserPermission = 10;
        public static final int TRANSACTION_setURLFilterEnabledEnforcingFirewallPermission = 9;
        public static final int TRANSACTION_setURLFilterListEnforcingBrowserPermission = 14;
        public static final int TRANSACTION_setURLFilterListEnforcingFirewallPermission = 13;
        public static final int TRANSACTION_setURLFilterReportEnabledEnforcingBrowserPermission = 19;
        public static final int TRANSACTION_setURLFilterReportEnabledEnforcingFirewallPermission = 18;

        class Proxy implements IBrowserPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean clearHttpProxy(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public String getHttpProxy(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IBrowserPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean isUrlBlocked(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public boolean setHttpProxy(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IBrowserPolicy.DESCRIPTOR);
        }

        public static IBrowserPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBrowserPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IBrowserPolicy)) ? new Proxy(iBinder) : (IBrowserPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setBrowserSettingStatus";
                case 2:
                    return "getBrowserSettingStatus";
                case 3:
                    return "setHttpProxy";
                case 4:
                    return "clearHttpProxy";
                case 5:
                    return "getHttpProxy";
                case 6:
                    return "addWebBookmarkBitmap";
                case 7:
                    return "addWebBookmarkByteBuffer";
                case 8:
                    return "deleteWebBookmark";
                case 9:
                    return "setURLFilterEnabledEnforcingFirewallPermission";
                case 10:
                    return "setURLFilterEnabledEnforcingBrowserPermission";
                case 11:
                    return "getURLFilterEnabledEnforcingFirewallPermission";
                case 12:
                    return "getURLFilterEnabledEnforcingBrowserPermission";
                case 13:
                    return "setURLFilterListEnforcingFirewallPermission";
                case 14:
                    return "setURLFilterListEnforcingBrowserPermission";
                case 15:
                    return "getURLFilterListEnforcingFirewallPermission";
                case 16:
                    return "getURLFilterListEnforcingBrowserPermission";
                case 17:
                    return "isUrlBlocked";
                case 18:
                    return "setURLFilterReportEnabledEnforcingFirewallPermission";
                case 19:
                    return "setURLFilterReportEnabledEnforcingBrowserPermission";
                case 20:
                    return "getURLFilterReportEnabledEnforcingFirewallPermission";
                case 21:
                    return "getURLFilterReportEnabledEnforcingBrowserPermission";
                case 22:
                    return "getURLFilterReportEnforcingFirewallPermission";
                case 23:
                    return "getURLFilterReportEnforcingBrowserPermission";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 22;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBrowserPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBrowserPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus = setBrowserSettingStatus(contextInfo, z, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus2 = getBrowserSettingStatus(contextInfo2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean httpProxy = setHttpProxy(contextInfo3, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(httpProxy);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearHttpProxy = clearHttpProxy(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearHttpProxy);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String httpProxy2 = getHttpProxy(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeString(httpProxy2);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string2 = parcel.readString();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddWebBookmarkBitmap = addWebBookmarkBitmap(contextInfo6, uri, string2, bitmap);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddWebBookmarkBitmap);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zAddWebBookmarkByteBuffer = addWebBookmarkByteBuffer(contextInfo7, uri2, string3, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddWebBookmarkByteBuffer);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteWebBookmark = deleteWebBookmark(contextInfo8, uri3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteWebBookmark);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterEnabledEnforcingFirewallPermission = setURLFilterEnabledEnforcingFirewallPermission(contextInfo9, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterEnabledEnforcingFirewallPermission);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterEnabledEnforcingBrowserPermission = setURLFilterEnabledEnforcingBrowserPermission(contextInfo10, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterEnabledEnforcingBrowserPermission);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterEnabledEnforcingFirewallPermission2 = getURLFilterEnabledEnforcingFirewallPermission(contextInfo11, z4, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterEnabledEnforcingFirewallPermission2);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterEnabledEnforcingBrowserPermission2 = getURLFilterEnabledEnforcingBrowserPermission(contextInfo12, z6, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterEnabledEnforcingBrowserPermission2);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int uRLFilterListEnforcingFirewallPermission = setURLFilterListEnforcingFirewallPermission(contextInfo13, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterListEnforcingFirewallPermission);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int uRLFilterListEnforcingBrowserPermission = setURLFilterListEnforcingBrowserPermission(contextInfo14, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterListEnforcingBrowserPermission);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterListEnforcingFirewallPermission2 = getURLFilterListEnforcingFirewallPermission(contextInfo15, z8, z9);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterListEnforcingFirewallPermission2);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterListEnforcingBrowserPermission2 = getURLFilterListEnforcingBrowserPermission(contextInfo16, z10, z11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterListEnforcingBrowserPermission2);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUrlBlocked = isUrlBlocked(contextInfo17, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUrlBlocked);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterReportEnabledEnforcingFirewallPermission = setURLFilterReportEnabledEnforcingFirewallPermission(contextInfo18, z12);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterReportEnabledEnforcingFirewallPermission);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterReportEnabledEnforcingBrowserPermission = setURLFilterReportEnabledEnforcingBrowserPermission(contextInfo19, z13);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterReportEnabledEnforcingBrowserPermission);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterReportEnabledEnforcingFirewallPermission2 = getURLFilterReportEnabledEnforcingFirewallPermission(contextInfo20, z14, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterReportEnabledEnforcingFirewallPermission2);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z16 = parcel.readBoolean();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterReportEnabledEnforcingBrowserPermission2 = getURLFilterReportEnabledEnforcingBrowserPermission(contextInfo21, z16, z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterReportEnabledEnforcingBrowserPermission2);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterReportEnforcingFirewallPermission = getURLFilterReportEnforcingFirewallPermission(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterReportEnforcingFirewallPermission);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterReportEnforcingBrowserPermission = getURLFilterReportEnforcingBrowserPermission(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterReportEnforcingBrowserPermission);
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
