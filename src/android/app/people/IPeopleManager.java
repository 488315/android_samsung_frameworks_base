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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPeopleManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPeopleManager)) {
                return (IPeopleManager) queryLocalInterface;
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
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ConversationChannel conversation = getConversation(readString, readInt, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversation, 1);
                    return true;
                case 2:
                    ParceledListSlice recentConversations = getRecentConversations();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentConversations, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeRecentConversation(readString3, readInt2, readString4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    removeAllRecentConversations();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isConversation = isConversation(readString5, readInt3, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConversation);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastInteraction = getLastInteraction(readString7, readInt4, readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastInteraction);
                    return true;
                case 7:
                    String readString9 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    String readString10 = parcel.readString();
                    ConversationStatus conversationStatus = (ConversationStatus) parcel.readTypedObject(ConversationStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    addOrUpdateStatus(readString9, readInt5, readString10, conversationStatus);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString11 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatus(readString11, readInt6, readString12, readString13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString14 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearStatuses(readString14, readInt7, readString15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString16 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice statuses = getStatuses(readString16, readInt8, readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statuses, 1);
                    return true;
                case 11:
                    String readString18 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString19 = parcel.readString();
                    IConversationListener asInterface = IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerConversationListener(readString18, readInt9, readString19, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IConversationListener asInterface2 = IConversationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterConversationListener(asInterface2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ConversationChannel) obtain2.readTypedObject(ConversationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public ParceledListSlice getRecentConversations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeRecentConversation(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void removeAllRecentConversations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public boolean isConversation(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public long getLastInteraction(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void addOrUpdateStatus(String str, int i, String str2, ConversationStatus conversationStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(conversationStatus, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatus(String str, int i, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void clearStatuses(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public ParceledListSlice getStatuses(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void registerConversationListener(String str, int i, String str2, IConversationListener iConversationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.people.IPeopleManager
            public void unregisterConversationListener(IConversationListener iConversationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPeopleManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iConversationListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
