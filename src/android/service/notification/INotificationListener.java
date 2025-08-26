package android.service.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.IStatusBarNotificationHolder;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface INotificationListener extends IInterface {

    public static class Default implements INotificationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.notification.INotificationListener
        public void onActionClicked(String str, Notification.Action action, int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onAllowedAdjustmentsChanged() throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onEdgeNotificationPosted(String str, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onEdgeNotificationRemoved(String str, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onInterruptionFilterChanged(int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerHintsChanged(int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelGroupModification(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelModification(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationClicked(String str) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationDirectReply(String str) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannelFull(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationExpansionChanged(String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationFeedbackReceived(String str, NotificationRankingUpdate notificationRankingUpdate, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPostedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemovedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContextFull(StatusBarNotification statusBarNotification, String str) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationVisibilityChanged(String str, boolean z) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationsSeen(List<String> list) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onPanelHidden() throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onPanelRevealed(int i) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onStatusBarIconsBehaviorChanged(boolean z) throws RemoteException {
        }

        @Override // android.service.notification.INotificationListener
        public void onSuggestedReplySent(String str, CharSequence charSequence, int i) throws RemoteException {
        }
    }

    void onActionClicked(String str, Notification.Action action, int i) throws RemoteException;

    void onAllowedAdjustmentsChanged() throws RemoteException;

    void onEdgeNotificationPosted(String str, int i, Bundle bundle) throws RemoteException;

    void onEdgeNotificationRemoved(String str, int i, Bundle bundle) throws RemoteException;

    void onInterruptionFilterChanged(int i) throws RemoteException;

    void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onListenerHintsChanged(int i) throws RemoteException;

    void onNotificationChannelGroupModification(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) throws RemoteException;

    void onNotificationChannelModification(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) throws RemoteException;

    void onNotificationClicked(String str) throws RemoteException;

    void onNotificationDirectReply(String str) throws RemoteException;

    void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationEnqueuedWithChannelFull(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationExpansionChanged(String str, boolean z, boolean z2) throws RemoteException;

    void onNotificationFeedbackReceived(String str, NotificationRankingUpdate notificationRankingUpdate, Bundle bundle) throws RemoteException;

    void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationPostedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException;

    void onNotificationRemovedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException;

    void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) throws RemoteException;

    void onNotificationSnoozedUntilContextFull(StatusBarNotification statusBarNotification, String str) throws RemoteException;

    void onNotificationVisibilityChanged(String str, boolean z) throws RemoteException;

    void onNotificationsSeen(List<String> list) throws RemoteException;

    void onPanelHidden() throws RemoteException;

    void onPanelRevealed(int i) throws RemoteException;

    void onStatusBarIconsBehaviorChanged(boolean z) throws RemoteException;

    void onSuggestedReplySent(String str, CharSequence charSequence, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements INotificationListener {
        public static final String DESCRIPTOR = "android.service.notification.INotificationListener";
        static final int TRANSACTION_onActionClicked = 23;
        static final int TRANSACTION_onAllowedAdjustmentsChanged = 25;
        static final int TRANSACTION_onEdgeNotificationPosted = 27;
        static final int TRANSACTION_onEdgeNotificationRemoved = 28;
        static final int TRANSACTION_onInterruptionFilterChanged = 9;
        static final int TRANSACTION_onListenerConnected = 1;
        static final int TRANSACTION_onListenerHintsChanged = 8;
        static final int TRANSACTION_onNotificationChannelGroupModification = 11;
        static final int TRANSACTION_onNotificationChannelModification = 10;
        static final int TRANSACTION_onNotificationClicked = 24;
        static final int TRANSACTION_onNotificationDirectReply = 21;
        static final int TRANSACTION_onNotificationEnqueuedWithChannel = 12;
        static final int TRANSACTION_onNotificationEnqueuedWithChannelFull = 13;
        static final int TRANSACTION_onNotificationExpansionChanged = 20;
        static final int TRANSACTION_onNotificationFeedbackReceived = 26;
        static final int TRANSACTION_onNotificationPosted = 2;
        static final int TRANSACTION_onNotificationPostedFull = 3;
        static final int TRANSACTION_onNotificationRankingUpdate = 7;
        static final int TRANSACTION_onNotificationRemoved = 5;
        static final int TRANSACTION_onNotificationRemovedFull = 6;
        static final int TRANSACTION_onNotificationSnoozedUntilContext = 14;
        static final int TRANSACTION_onNotificationSnoozedUntilContextFull = 15;
        static final int TRANSACTION_onNotificationVisibilityChanged = 19;
        static final int TRANSACTION_onNotificationsSeen = 16;
        static final int TRANSACTION_onPanelHidden = 18;
        static final int TRANSACTION_onPanelRevealed = 17;
        static final int TRANSACTION_onStatusBarIconsBehaviorChanged = 4;
        static final int TRANSACTION_onSuggestedReplySent = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 27;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INotificationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INotificationListener)) {
                return (INotificationListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onListenerConnected";
                case 2:
                    return "onNotificationPosted";
                case 3:
                    return "onNotificationPostedFull";
                case 4:
                    return "onStatusBarIconsBehaviorChanged";
                case 5:
                    return "onNotificationRemoved";
                case 6:
                    return "onNotificationRemovedFull";
                case 7:
                    return "onNotificationRankingUpdate";
                case 8:
                    return "onListenerHintsChanged";
                case 9:
                    return "onInterruptionFilterChanged";
                case 10:
                    return "onNotificationChannelModification";
                case 11:
                    return "onNotificationChannelGroupModification";
                case 12:
                    return "onNotificationEnqueuedWithChannel";
                case 13:
                    return "onNotificationEnqueuedWithChannelFull";
                case 14:
                    return "onNotificationSnoozedUntilContext";
                case 15:
                    return "onNotificationSnoozedUntilContextFull";
                case 16:
                    return "onNotificationsSeen";
                case 17:
                    return "onPanelRevealed";
                case 18:
                    return "onPanelHidden";
                case 19:
                    return "onNotificationVisibilityChanged";
                case 20:
                    return "onNotificationExpansionChanged";
                case 21:
                    return "onNotificationDirectReply";
                case 22:
                    return "onSuggestedReplySent";
                case 23:
                    return "onActionClicked";
                case 24:
                    return "onNotificationClicked";
                case 25:
                    return "onAllowedAdjustmentsChanged";
                case 26:
                    return "onNotificationFeedbackReceived";
                case 27:
                    return "onEdgeNotificationPosted";
                case 28:
                    return "onEdgeNotificationRemoved";
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
                    NotificationRankingUpdate notificationRankingUpdate = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onListenerConnected(notificationRankingUpdate);
                    return true;
                case 2:
                    IStatusBarNotificationHolder iStatusBarNotificationHolderAsInterface = IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    NotificationRankingUpdate notificationRankingUpdate2 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationPosted(iStatusBarNotificationHolderAsInterface, notificationRankingUpdate2);
                    return true;
                case 3:
                    StatusBarNotification statusBarNotification = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    NotificationRankingUpdate notificationRankingUpdate3 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationPostedFull(statusBarNotification, notificationRankingUpdate3);
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStatusBarIconsBehaviorChanged(z);
                    return true;
                case 5:
                    IStatusBarNotificationHolder iStatusBarNotificationHolderAsInterface2 = IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    NotificationRankingUpdate notificationRankingUpdate4 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    NotificationStats notificationStats = (NotificationStats) parcel.readTypedObject(NotificationStats.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationRemoved(iStatusBarNotificationHolderAsInterface2, notificationRankingUpdate4, notificationStats, i3);
                    return true;
                case 6:
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    NotificationRankingUpdate notificationRankingUpdate5 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    NotificationStats notificationStats2 = (NotificationStats) parcel.readTypedObject(NotificationStats.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationRemovedFull(statusBarNotification2, notificationRankingUpdate5, notificationStats2, i4);
                    return true;
                case 7:
                    NotificationRankingUpdate notificationRankingUpdate6 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationRankingUpdate(notificationRankingUpdate6);
                    return true;
                case 8:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onListenerHintsChanged(i5);
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInterruptionFilterChanged(i6);
                    return true;
                case 10:
                    String string = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannel notificationChannel = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationChannelModification(string, userHandle, notificationChannel, i7);
                    return true;
                case 11:
                    String string2 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) parcel.readTypedObject(NotificationChannelGroup.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationChannelGroupModification(string2, userHandle2, notificationChannelGroup, i8);
                    return true;
                case 12:
                    IStatusBarNotificationHolder iStatusBarNotificationHolderAsInterface3 = IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    NotificationChannel notificationChannel2 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    NotificationRankingUpdate notificationRankingUpdate7 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationEnqueuedWithChannel(iStatusBarNotificationHolderAsInterface3, notificationChannel2, notificationRankingUpdate7);
                    return true;
                case 13:
                    StatusBarNotification statusBarNotification3 = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    NotificationChannel notificationChannel3 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    NotificationRankingUpdate notificationRankingUpdate8 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationEnqueuedWithChannelFull(statusBarNotification3, notificationChannel3, notificationRankingUpdate8);
                    return true;
                case 14:
                    IStatusBarNotificationHolder iStatusBarNotificationHolderAsInterface4 = IStatusBarNotificationHolder.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSnoozedUntilContext(iStatusBarNotificationHolderAsInterface4, string3);
                    return true;
                case 15:
                    StatusBarNotification statusBarNotification4 = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSnoozedUntilContextFull(statusBarNotification4, string4);
                    return true;
                case 16:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onNotificationsSeen(arrayListCreateStringArrayList);
                    return true;
                case 17:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPanelRevealed(i9);
                    return true;
                case 18:
                    onPanelHidden();
                    return true;
                case 19:
                    String string5 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationVisibilityChanged(string5, z2);
                    return true;
                case 20:
                    String string6 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationExpansionChanged(string6, z3, z4);
                    return true;
                case 21:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationDirectReply(string7);
                    return true;
                case 22:
                    String string8 = parcel.readString();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSuggestedReplySent(string8, charSequence, i10);
                    return true;
                case 23:
                    String string9 = parcel.readString();
                    Notification.Action action = (Notification.Action) parcel.readTypedObject(Notification.Action.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActionClicked(string9, action, i11);
                    return true;
                case 24:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationClicked(string10);
                    return true;
                case 25:
                    onAllowedAdjustmentsChanged();
                    return true;
                case 26:
                    String string11 = parcel.readString();
                    NotificationRankingUpdate notificationRankingUpdate9 = (NotificationRankingUpdate) parcel.readTypedObject(NotificationRankingUpdate.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationFeedbackReceived(string11, notificationRankingUpdate9, bundle);
                    return true;
                case 27:
                    String string12 = parcel.readString();
                    int i12 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEdgeNotificationPosted(string12, i12, bundle2);
                    return true;
                case 28:
                    String string13 = parcel.readString();
                    int i13 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEdgeNotificationRemoved(string13, i13, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INotificationListener {
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

            @Override // android.service.notification.INotificationListener
            public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBarNotificationHolder);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationPostedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onStatusBarIconsBehaviorChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBarNotificationHolder);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    parcelObtain.writeTypedObject(notificationStats, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationRemovedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    parcelObtain.writeTypedObject(notificationStats, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onListenerHintsChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onInterruptionFilterChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationChannelModification(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationChannelGroupModification(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeTypedObject(notificationChannelGroup, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBarNotificationHolder);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationEnqueuedWithChannelFull(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBarNotificationHolder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationSnoozedUntilContextFull(StatusBarNotification statusBarNotification, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationsSeen(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onPanelRevealed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onPanelHidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationVisibilityChanged(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationExpansionChanged(String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationDirectReply(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onSuggestedReplySent(String str, CharSequence charSequence, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onActionClicked(String str, Notification.Action action, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(action, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationClicked(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onAllowedAdjustmentsChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onNotificationFeedbackReceived(String str, NotificationRankingUpdate notificationRankingUpdate, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(notificationRankingUpdate, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onEdgeNotificationPosted(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.notification.INotificationListener
            public void onEdgeNotificationRemoved(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
