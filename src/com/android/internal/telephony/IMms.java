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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMms)) {
                return (IMms) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    long readLong = parcel.readLong();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendMessage(readInt, readInt2, readString, uri, readString2, bundle, pendingIntent, readLong, readString3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    long readLong2 = parcel.readLong();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    downloadMessage(readInt3, readInt4, readString4, readString5, uri2, bundle2, pendingIntent2, readLong2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    String readString9 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Uri importTextMessage = importTextMessage(readString7, readString8, readInt5, readString9, readLong3, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importTextMessage, 1);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    String readString10 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString11 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Uri importMultimediaMessage = importMultimediaMessage(readInt6, readString10, uri3, readString11, readLong4, readBoolean3, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importMultimediaMessage, 1);
                    return true;
                case 5:
                    String readString12 = parcel.readString();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteStoredMessage = deleteStoredMessage(readString12, uri4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteStoredMessage);
                    return true;
                case 6:
                    String readString13 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean deleteStoredConversation = deleteStoredConversation(readString13, readLong5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteStoredConversation);
                    return true;
                case 7:
                    String readString14 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ContentValues contentValues = (ContentValues) parcel.readTypedObject(ContentValues.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateStoredMessageStatus = updateStoredMessageStatus(readString14, uri5, contentValues);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateStoredMessageStatus);
                    return true;
                case 8:
                    String readString15 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean archiveStoredConversation = archiveStoredConversation(readString15, readLong6, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(archiveStoredConversation);
                    return true;
                case 9:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri addTextMessageDraft = addTextMessageDraft(readString16, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addTextMessageDraft, 1);
                    return true;
                case 10:
                    int readInt7 = parcel.readInt();
                    String readString19 = parcel.readString();
                    Uri uri6 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    Uri addMultimediaMessageDraft = addMultimediaMessageDraft(readInt7, readString19, uri6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addMultimediaMessageDraft, 1);
                    return true;
                case 11:
                    int readInt8 = parcel.readInt();
                    String readString20 = parcel.readString();
                    Uri uri7 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMessage(readInt8, readString20, uri7, bundle3, pendingIntent3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString21 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoPersisting(readString21, readBoolean6);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void downloadMessage(int i, int i2, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri importMultimediaMessage(int i, String str, Uri uri, String str2, long j, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredMessage(String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean deleteStoredConversation(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(contentValues, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean archiveStoredConversation(String str, long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri addTextMessageDraft(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public Uri addMultimediaMessageDraft(int i, String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public void setAutoPersisting(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IMms
            public boolean getAutoPersisting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
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
