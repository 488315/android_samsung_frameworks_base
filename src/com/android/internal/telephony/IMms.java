package com.android.internal.telephony;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IMms extends IInterface {

    public static class Default implements IMms {
        @Override // com.android.internal.telephony.IMms
        public Uri addMultimediaMessageDraft(int i, String str, Uri uri) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public Uri addTextMessageDraft(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean archiveStoredConversation(String str, long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean deleteStoredConversation(String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public boolean deleteStoredMessage(String str, Uri uri) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public void downloadMessage(int i, int i2, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public boolean getAutoPersisting() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IMms
        public Uri importMultimediaMessage(int i, String str, Uri uri, String str2, long j, boolean z, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IMms
        public void sendMessage(int i, int i2, String str, Uri uri, String str2, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public void setAutoPersisting(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IMms
        public boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) throws RemoteException {
            return false;
        }
    }

    Uri addMultimediaMessageDraft(int i, String str, Uri uri) throws RemoteException;

    Uri addTextMessageDraft(String str, String str2, String str3) throws RemoteException;

    boolean archiveStoredConversation(String str, long j, boolean z) throws RemoteException;

    boolean deleteStoredConversation(String str, long j) throws RemoteException;

    boolean deleteStoredMessage(String str, Uri uri) throws RemoteException;

    void downloadMessage(int i, int i2, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException;

    boolean getAutoPersisting() throws RemoteException;

    Uri importMultimediaMessage(int i, String str, Uri uri, String str2, long j, boolean z, boolean z2) throws RemoteException;

    Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) throws RemoteException;

    void sendMessage(int i, int i2, String str, Uri uri, String str2, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException;

    void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    void setAutoPersisting(String str, boolean z) throws RemoteException;

    boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) throws RemoteException;

    public static abstract class Stub extends Binder implements IMms {
        public static final String DESCRIPTOR = "com.android.internal.telephony.IMms";
        static final int TRANSACTION_addMultimediaMessageDraft = 10;
        static final int TRANSACTION_addTextMessageDraft = 9;
        static final int TRANSACTION_archiveStoredConversation = 8;
        static final int TRANSACTION_deleteStoredConversation = 6;
        static final int TRANSACTION_deleteStoredMessage = 5;
        static final int TRANSACTION_downloadMessage = 2;
        static final int TRANSACTION_getAutoPersisting = 13;
        static final int TRANSACTION_importMultimediaMessage = 4;
        static final int TRANSACTION_importTextMessage = 3;
        static final int TRANSACTION_sendMessage = 1;
        static final int TRANSACTION_sendStoredMessage = 11;
        static final int TRANSACTION_setAutoPersisting = 12;
        static final int TRANSACTION_updateStoredMessageStatus = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMms asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMms)) {
                return (IMms) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendMessage";
                case 2:
                    return "downloadMessage";
                case 3:
                    return "importTextMessage";
                case 4:
                    return "importMultimediaMessage";
                case 5:
                    return "deleteStoredMessage";
                case 6:
                    return "deleteStoredConversation";
                case 7:
                    return "updateStoredMessageStatus";
                case 8:
                    return "archiveStoredConversation";
                case 9:
                    return "addTextMessageDraft";
                case 10:
                    return "addMultimediaMessageDraft";
                case 11:
                    return "sendStoredMessage";
                case 12:
                    return "setAutoPersisting";
                case 13:
                    return "getAutoPersisting";
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    long j = parcel.readLong();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendMessage(i3, i4, string, uri, string2, bundle, pendingIntent, j, string3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    long j2 = parcel.readLong();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    downloadMessage(i5, i6, string4, string5, uri2, bundle2, pendingIntent2, j2, string6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i7 = parcel.readInt();
                    String string9 = parcel.readString();
                    long j3 = parcel.readLong();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Uri uriImportTextMessage = importTextMessage(string7, string8, i7, string9, j3, z, z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriImportTextMessage, 1);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    String string10 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string11 = parcel.readString();
                    long j4 = parcel.readLong();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Uri uriImportMultimediaMessage = importMultimediaMessage(i8, string10, uri3, string11, j4, z3, z4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriImportMultimediaMessage, 1);
                    return true;
                case 5:
                    String string12 = parcel.readString();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDeleteStoredMessage = deleteStoredMessage(string12, uri4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteStoredMessage);
                    return true;
                case 6:
                    String string13 = parcel.readString();
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteStoredConversation = deleteStoredConversation(string13, j5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteStoredConversation);
                    return true;
                case 7:
                    String string14 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ContentValues contentValues = (ContentValues) parcel.readTypedObject(ContentValues.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateStoredMessageStatus = updateStoredMessageStatus(string14, uri5, contentValues);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateStoredMessageStatus);
                    return true;
                case 8:
                    String string15 = parcel.readString();
                    long j6 = parcel.readLong();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zArchiveStoredConversation = archiveStoredConversation(string15, j6, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zArchiveStoredConversation);
                    return true;
                case 9:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri uriAddTextMessageDraft = addTextMessageDraft(string16, string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriAddTextMessageDraft, 1);
                    return true;
                case 10:
                    int i9 = parcel.readInt();
                    String string19 = parcel.readString();
                    Uri uri6 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    Uri uriAddMultimediaMessageDraft = addMultimediaMessageDraft(i9, string19, uri6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriAddMultimediaMessageDraft, 1);
                    return true;
                case 11:
                    int i10 = parcel.readInt();
                    String string20 = parcel.readString();
                    Uri uri7 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMessage(i10, string20, uri7, bundle3, pendingIntent3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string21 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoPersisting(string21, z6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean autoPersisting = getAutoPersisting();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoPersisting);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMms {
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

            @Override // com.android.internal.telephony.IMms
            public void sendMessage(int i, int i2, String str, Uri uri, String str2, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void downloadMessage(int i, int i2, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri importMultimediaMessage(int i, String str, Uri uri, String str2, long j, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredMessage(String str, Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredConversation(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(contentValues, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean archiveStoredConversation(String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri addTextMessageDraft(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri addMultimediaMessageDraft(int i, String str, Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void setAutoPersisting(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean getAutoPersisting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
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
