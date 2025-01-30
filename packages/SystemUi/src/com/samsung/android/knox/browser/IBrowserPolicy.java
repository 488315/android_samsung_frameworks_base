package com.samsung.android.knox.browser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IBrowserPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.browser.IBrowserPolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IBrowserPolicy {
        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean clearHttpProxy(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final String getHttpProxy(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean isUrlBlocked(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final boolean setHttpProxy(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list) {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list) {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.browser.IBrowserPolicy
        public final int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
            return 0;
        }
    }

    boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap);

    boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr);

    boolean clearHttpProxy(ContextInfo contextInfo);

    boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str);

    boolean getBrowserSettingStatus(ContextInfo contextInfo, int i);

    String getHttpProxy(ContextInfo contextInfo);

    boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2);

    boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2);

    List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2);

    List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2);

    boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2);

    boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2);

    List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo);

    List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo);

    boolean isUrlBlocked(ContextInfo contextInfo, String str);

    boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i);

    boolean setHttpProxy(ContextInfo contextInfo, String str);

    int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z);

    int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z);

    int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list);

    int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list);

    int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z);

    int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IBrowserPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IBrowserPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
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

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean clearHttpProxy(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final String getHttpProxy(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IBrowserPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final List<String> getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final List<String> getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final List<String> getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final List<String> getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean isUrlBlocked(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final boolean setHttpProxy(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.browser.IBrowserPolicy
            public final int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBrowserPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBrowserPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IBrowserPolicy)) ? new Proxy(iBinder) : (IBrowserPolicy) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 22;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus = setBrowserSettingStatus(contextInfo, readBoolean, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus2 = getBrowserSettingStatus(contextInfo2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean httpProxy = setHttpProxy(contextInfo3, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(httpProxy);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearHttpProxy = clearHttpProxy(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearHttpProxy);
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
                    String readString2 = parcel.readString();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addWebBookmarkBitmap = addWebBookmarkBitmap(contextInfo6, uri, readString2, bitmap);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addWebBookmarkBitmap);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString3 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean addWebBookmarkByteBuffer = addWebBookmarkByteBuffer(contextInfo7, uri2, readString3, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addWebBookmarkByteBuffer);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteWebBookmark = deleteWebBookmark(contextInfo8, uri3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteWebBookmark);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterEnabledEnforcingFirewallPermission = setURLFilterEnabledEnforcingFirewallPermission(contextInfo9, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterEnabledEnforcingFirewallPermission);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterEnabledEnforcingBrowserPermission = setURLFilterEnabledEnforcingBrowserPermission(contextInfo10, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterEnabledEnforcingBrowserPermission);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterEnabledEnforcingFirewallPermission2 = getURLFilterEnabledEnforcingFirewallPermission(contextInfo11, readBoolean4, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterEnabledEnforcingFirewallPermission2);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterEnabledEnforcingBrowserPermission2 = getURLFilterEnabledEnforcingBrowserPermission(contextInfo12, readBoolean6, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterEnabledEnforcingBrowserPermission2);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int uRLFilterListEnforcingFirewallPermission = setURLFilterListEnforcingFirewallPermission(contextInfo13, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterListEnforcingFirewallPermission);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int uRLFilterListEnforcingBrowserPermission = setURLFilterListEnforcingBrowserPermission(contextInfo14, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterListEnforcingBrowserPermission);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterListEnforcingFirewallPermission2 = getURLFilterListEnforcingFirewallPermission(contextInfo15, readBoolean8, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterListEnforcingFirewallPermission2);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> uRLFilterListEnforcingBrowserPermission2 = getURLFilterListEnforcingBrowserPermission(contextInfo16, readBoolean10, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(uRLFilterListEnforcingBrowserPermission2);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUrlBlocked = isUrlBlocked(contextInfo17, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUrlBlocked);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterReportEnabledEnforcingFirewallPermission = setURLFilterReportEnabledEnforcingFirewallPermission(contextInfo18, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterReportEnabledEnforcingFirewallPermission);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int uRLFilterReportEnabledEnforcingBrowserPermission = setURLFilterReportEnabledEnforcingBrowserPermission(contextInfo19, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeInt(uRLFilterReportEnabledEnforcingBrowserPermission);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterReportEnabledEnforcingFirewallPermission2 = getURLFilterReportEnabledEnforcingFirewallPermission(contextInfo20, readBoolean14, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uRLFilterReportEnabledEnforcingFirewallPermission2);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uRLFilterReportEnabledEnforcingBrowserPermission2 = getURLFilterReportEnabledEnforcingBrowserPermission(contextInfo21, readBoolean16, readBoolean17);
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
        public final IBinder asBinder() {
            return this;
        }
    }
}
