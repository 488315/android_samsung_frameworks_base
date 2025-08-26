package android.app.people;

import android.app.people.IConversationListener;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPeopleManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.people.IPeopleManager";

    public static class Default implements IPeopleManager {
        @Override // android.app.people.IPeopleManager
        public void addOrUpdateStatus(String str, int i, String str2, ConversationStatus conversationStatus) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public void clearStatus(String str, int i, String str2, String str3) throws RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void clearStatuses(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public ConversationChannel getConversation(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public long getLastInteraction(String str, int i, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.people.IPeopleManager
        public ParceledListSlice getRecentConversations() throws RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public ParceledListSlice getStatuses(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.people.IPeopleManager
        public boolean isConversation(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.people.IPeopleManager
        public void registerConversationListener(String str, int i, String str2, IConversationListener iConversationListener) throws RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void removeAllRecentConversations() throws RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void removeRecentConversation(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.app.people.IPeopleManager
        public void unregisterConversationListener(IConversationListener iConversationListener) throws RemoteException {
        }
    }

    void addOrUpdateStatus(String str, int i, String str2, ConversationStatus conversationStatus) throws RemoteException;

    void clearStatus(String str, int i, String str2, String str3) throws RemoteException;

    void clearStatuses(String str, int i, String str2) throws RemoteException;

    ConversationChannel getConversation(String str, int i, String str2) throws RemoteException;

    long getLastInteraction(String str, int i, String str2) throws RemoteException;

    ParceledListSlice getRecentConversations() throws RemoteException;

    ParceledListSlice getStatuses(String str, int i, String str2) throws RemoteException;

    boolean isConversation(String str, int i, String str2) throws RemoteException;

    void registerConversationListener(String str, int i, String str2, IConversationListener iConversationListener) throws RemoteException;

    void removeAllRecentConversations() throws RemoteException;

    void removeRecentConversation(String str, int i, String str2) throws RemoteException;

    void unregisterConversationListener(IConversationListener iConversationListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IPeopleManager {
        static final int TRANSACTION_addOrUpdateStatus = 7;
        static final int TRANSACTION_clearStatus = 8;
        static final int TRANSACTION_clearStatuses = 9;
        static final int TRANSACTION_getConversation = 1;
        static final int TRANSACTION_getLastInteraction = 6;
        static final int TRANSACTION_getRecentConversations = 2;
        static final int TRANSACTION_getStatuses = 10;
        static final int TRANSACTION_isConversation = 5;
        static final int TRANSACTION_registerConversationListener = 11;
        static final int TRANSACTION_removeAllRecentConversations = 4;
        static final int TRANSACTION_removeRecentConversation = 3;
        static final int TRANSACTION_unregisterConversationListener = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IPeopleManager.DESCRIPTOR);
        }

        public static IPeopleManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPeopleManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPeopleManager)) {
                return (IPeopleManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getConversation";
                case 2:
                    return "getRecentConversations";
                case 3:
                    return "removeRecentConversation";
                case 4:
                    return "removeAllRecentConversations";
                case 5:
                    return "isConversation";
                case 6:
                    return "getLastInteraction";
                case 7:
                    return "addOrUpdateStatus";
                case 8:
                    return "clearStatus";
                case 9:
                    return "clearStatuses";
                case 10:
                    return "getStatuses";
                case 11:
                    return "registerConversationListener";
                case 12:
                    return "unregisterConversationListener";
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
                parcel.enforceInterface(IPeopleManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPeopleManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ConversationChannel conversation = getConversation(string, i3, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversation, 1);
                    return true;
                case 2:
                    ParceledListSlice recentConversations = getRecentConversations();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentConversations, 1);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeRecentConversation(string3, i4, string4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    removeAllRecentConversations();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i5 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsConversation = isConversation(string5, i5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConversation);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    int i6 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastInteraction = getLastInteraction(string7, i6, string8);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastInteraction);
                    return true;
                case 7:
                    String string9 = parcel.readString();
                    int i7 = parcel.readInt();
                    String string10 = parcel.readString();
                    ConversationStatus conversationStatus = (ConversationStatus) parcel.readTypedObject(ConversationStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    addOrUpdateStatus(string9, i7, string10, conversationStatus);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string11 = parcel.readString();
                    int i8 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatus(string11, i8, string12, string13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string14 = parcel.readString();
                    int i9 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatuses(string14, i9, string15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string16 = parcel.readString();
                    int i10 = parcel.readInt();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice statuses = getStatuses(string16, i10, string17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statuses, 1);
                    return true;
                case 11:
                    String string18 = parcel.readString();
                    int i11 = parcel.readInt();
                    String string19 = parcel.readString();
                    IConversationListener iConversationListenerAsInterface = IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerConversationListener(string18, i11, string19, iConversationListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IConversationListener iConversationListenerAsInterface2 = IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterConversationListener(iConversationListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPeopleManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPeopleManager.DESCRIPTOR;
            }

            @Override // android.app.people.IPeopleManager
            public ConversationChannel getConversation(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ConversationChannel) parcelObtain2.readTypedObject(ConversationChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public ParceledListSlice getRecentConversations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeRecentConversation(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeAllRecentConversations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public boolean isConversation(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public long getLastInteraction(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void addOrUpdateStatus(String str, int i, String str2, ConversationStatus conversationStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(conversationStatus, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatus(String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatuses(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public ParceledListSlice getStatuses(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void registerConversationListener(String str, int i, String str2, IConversationListener iConversationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void unregisterConversationListener(IConversationListener iConversationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
