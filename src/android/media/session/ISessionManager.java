package android.media.session;

import android.content.ComponentName;
import android.media.IRemoteSessionCallback;
import android.media.session.IActiveSessionsListener;
import android.media.session.IOnMediaKeyEventDispatchedListener;
import android.media.session.IOnMediaKeyEventSessionChangedListener;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionCallback;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISessionManager extends IInterface {

    public static class Default implements ISessionManager {
        @Override // android.media.session.ISessionManager
        public void addOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public ISession createSession(String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean dispatchMediaKeyEventToSessionAsSystemService(String str, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEvent(String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEventToSessionAsSystemService(String str, String str2, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void expireTempEngagedSessions() throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public MediaSession.Token getMediaKeyEventSession(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public String getMediaKeyEventSessionPackageName(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public int getSessionPolicies(MediaSession.Token token) throws RemoteException {
            return 0;
        }

        @Override // android.media.session.ISessionManager
        public List<MediaSession.Token> getSessions(ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public boolean hasCustomMediaKeyDispatcher(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public boolean hasCustomMediaSessionPolicyProvider(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public boolean isGlobalPriorityActive() throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public boolean isTrusted(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void registerRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setCustomMediaKeyDispatcher(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setCustomMediaSessionPolicyProvider(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setSessionPolicies(MediaSession.Token token, int i) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void unregisterRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
        }
    }

    void addOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException;

    void addOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, String str) throws RemoteException;

    void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) throws RemoteException;

    void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) throws RemoteException;

    ISession createSession(String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) throws RemoteException;

    void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) throws RemoteException;

    void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) throws RemoteException;

    boolean dispatchMediaKeyEventToSessionAsSystemService(String str, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException;

    void dispatchVolumeKeyEvent(String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) throws RemoteException;

    void dispatchVolumeKeyEventToSessionAsSystemService(String str, String str2, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException;

    void expireTempEngagedSessions() throws RemoteException;

    MediaSession.Token getMediaKeyEventSession(String str) throws RemoteException;

    String getMediaKeyEventSessionPackageName(String str) throws RemoteException;

    int getSessionPolicies(MediaSession.Token token) throws RemoteException;

    List<MediaSession.Token> getSessions(ComponentName componentName, int i) throws RemoteException;

    boolean hasCustomMediaKeyDispatcher(String str) throws RemoteException;

    boolean hasCustomMediaSessionPolicyProvider(String str) throws RemoteException;

    boolean isGlobalPriorityActive() throws RemoteException;

    boolean isTrusted(String str, int i, int i2) throws RemoteException;

    void registerRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException;

    void removeOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException;

    void removeOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws RemoteException;

    void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException;

    void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException;

    void setCustomMediaKeyDispatcher(String str) throws RemoteException;

    void setCustomMediaSessionPolicyProvider(String str) throws RemoteException;

    void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException;

    void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException;

    void setSessionPolicies(MediaSession.Token token, int i) throws RemoteException;

    void unregisterRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionManager {
        public static final String DESCRIPTOR = "android.media.session.ISessionManager";
        static final int TRANSACTION_addOnMediaKeyEventDispatchedListener = 17;
        static final int TRANSACTION_addOnMediaKeyEventSessionChangedListener = 19;
        static final int TRANSACTION_addSession2TokensListener = 12;
        static final int TRANSACTION_addSessionsListener = 10;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_dispatchAdjustVolume = 9;
        static final int TRANSACTION_dispatchMediaKeyEvent = 5;
        static final int TRANSACTION_dispatchMediaKeyEventToSessionAsSystemService = 6;
        static final int TRANSACTION_dispatchVolumeKeyEvent = 7;
        static final int TRANSACTION_dispatchVolumeKeyEventToSessionAsSystemService = 8;
        static final int TRANSACTION_expireTempEngagedSessions = 30;
        static final int TRANSACTION_getMediaKeyEventSession = 3;
        static final int TRANSACTION_getMediaKeyEventSessionPackageName = 4;
        static final int TRANSACTION_getSessionPolicies = 28;
        static final int TRANSACTION_getSessions = 2;
        static final int TRANSACTION_hasCustomMediaKeyDispatcher = 26;
        static final int TRANSACTION_hasCustomMediaSessionPolicyProvider = 27;
        static final int TRANSACTION_isGlobalPriorityActive = 16;
        static final int TRANSACTION_isTrusted = 23;
        static final int TRANSACTION_registerRemoteSessionCallback = 14;
        static final int TRANSACTION_removeOnMediaKeyEventDispatchedListener = 18;
        static final int TRANSACTION_removeOnMediaKeyEventSessionChangedListener = 20;
        static final int TRANSACTION_removeSession2TokensListener = 13;
        static final int TRANSACTION_removeSessionsListener = 11;
        static final int TRANSACTION_setCustomMediaKeyDispatcher = 24;
        static final int TRANSACTION_setCustomMediaSessionPolicyProvider = 25;
        static final int TRANSACTION_setOnMediaKeyListener = 22;
        static final int TRANSACTION_setOnVolumeKeyLongPressListener = 21;
        static final int TRANSACTION_setSessionPolicies = 29;
        static final int TRANSACTION_unregisterRemoteSessionCallback = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISessionManager)) {
                return (ISessionManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "getSessions";
                case 3:
                    return "getMediaKeyEventSession";
                case 4:
                    return "getMediaKeyEventSessionPackageName";
                case 5:
                    return "dispatchMediaKeyEvent";
                case 6:
                    return "dispatchMediaKeyEventToSessionAsSystemService";
                case 7:
                    return "dispatchVolumeKeyEvent";
                case 8:
                    return "dispatchVolumeKeyEventToSessionAsSystemService";
                case 9:
                    return "dispatchAdjustVolume";
                case 10:
                    return "addSessionsListener";
                case 11:
                    return "removeSessionsListener";
                case 12:
                    return "addSession2TokensListener";
                case 13:
                    return "removeSession2TokensListener";
                case 14:
                    return "registerRemoteSessionCallback";
                case 15:
                    return "unregisterRemoteSessionCallback";
                case 16:
                    return "isGlobalPriorityActive";
                case 17:
                    return "addOnMediaKeyEventDispatchedListener";
                case 18:
                    return "removeOnMediaKeyEventDispatchedListener";
                case 19:
                    return "addOnMediaKeyEventSessionChangedListener";
                case 20:
                    return "removeOnMediaKeyEventSessionChangedListener";
                case 21:
                    return "setOnVolumeKeyLongPressListener";
                case 22:
                    return "setOnMediaKeyListener";
                case 23:
                    return "isTrusted";
                case 24:
                    return "setCustomMediaKeyDispatcher";
                case 25:
                    return "setCustomMediaSessionPolicyProvider";
                case 26:
                    return "hasCustomMediaKeyDispatcher";
                case 27:
                    return "hasCustomMediaSessionPolicyProvider";
                case 28:
                    return "getSessionPolicies";
                case 29:
                    return "setSessionPolicies";
                case 30:
                    return "expireTempEngagedSessions";
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
                    String string = parcel.readString();
                    ISessionCallback iSessionCallbackAsInterface = ISessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ISession iSessionCreateSession = createSession(string, iSessionCallbackAsInterface, string2, bundle, i3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iSessionCreateSession);
                    return true;
                case 2:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<MediaSession.Token> sessions = getSessions(componentName, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sessions, 1);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MediaSession.Token mediaKeyEventSession = getMediaKeyEventSession(string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mediaKeyEventSession, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mediaKeyEventSessionPackageName = getMediaKeyEventSessionPackageName(string4);
                    parcel2.writeNoException();
                    parcel2.writeString(mediaKeyEventSessionPackageName);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchMediaKeyEvent(string5, z, keyEvent, z2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDispatchMediaKeyEventToSessionAsSystemService = dispatchMediaKeyEventToSessionAsSystemService(string6, keyEvent2, token);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDispatchMediaKeyEventToSessionAsSystemService);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    KeyEvent keyEvent3 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    int i5 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEvent(string7, string8, z3, keyEvent3, i5, z4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    KeyEvent keyEvent4 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    MediaSession.Token token2 = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEventToSessionAsSystemService(string9, string10, keyEvent4, token2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchAdjustVolume(string11, string12, i6, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IActiveSessionsListener iActiveSessionsListenerAsInterface = IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSessionsListener(iActiveSessionsListenerAsInterface, componentName2, i9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IActiveSessionsListener iActiveSessionsListenerAsInterface2 = IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSessionsListener(iActiveSessionsListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ISession2TokensListener iSession2TokensListenerAsInterface = ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSession2TokensListener(iSession2TokensListenerAsInterface, i10);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ISession2TokensListener iSession2TokensListenerAsInterface2 = ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSession2TokensListener(iSession2TokensListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IRemoteSessionCallback iRemoteSessionCallbackAsInterface = IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteSessionCallback(iRemoteSessionCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IRemoteSessionCallback iRemoteSessionCallbackAsInterface2 = IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteSessionCallback(iRemoteSessionCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean zIsGlobalPriorityActive = isGlobalPriorityActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGlobalPriorityActive);
                    return true;
                case 17:
                    IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListenerAsInterface = IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventDispatchedListener(iOnMediaKeyEventDispatchedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListenerAsInterface2 = IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventDispatchedListener(iOnMediaKeyEventDispatchedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListenerAsInterface = IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventSessionChangedListener(iOnMediaKeyEventSessionChangedListenerAsInterface, string13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListenerAsInterface2 = IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventSessionChangedListener(iOnMediaKeyEventSessionChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListenerAsInterface = IOnVolumeKeyLongPressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnVolumeKeyLongPressListener(iOnVolumeKeyLongPressListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IOnMediaKeyListener iOnMediaKeyListenerAsInterface = IOnMediaKeyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnMediaKeyListener(iOnMediaKeyListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string14 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTrusted = isTrusted(string14, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTrusted);
                    return true;
                case 24:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaKeyDispatcher(string15);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaSessionPolicyProvider(string16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasCustomMediaKeyDispatcher = hasCustomMediaKeyDispatcher(string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCustomMediaKeyDispatcher);
                    return true;
                case 27:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasCustomMediaSessionPolicyProvider = hasCustomMediaSessionPolicyProvider(string18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCustomMediaSessionPolicyProvider);
                    return true;
                case 28:
                    MediaSession.Token token3 = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sessionPolicies = getSessionPolicies(token3);
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionPolicies);
                    return true;
                case 29:
                    MediaSession.Token token4 = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionPolicies(token4, i13);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    expireTempEngagedSessions();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISessionManager {
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

            @Override // android.media.session.ISessionManager
            public ISession createSession(String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSessionCallback);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public List<MediaSession.Token> getSessions(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(MediaSession.Token.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public MediaSession.Token getMediaKeyEventSession(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MediaSession.Token) parcelObtain2.readTypedObject(MediaSession.Token.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public String getMediaKeyEventSessionPackageName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean dispatchMediaKeyEventToSessionAsSystemService(String str, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEvent(String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEventToSessionAsSystemService(String str, String str2, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActiveSessionsListener);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActiveSessionsListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSession2TokensListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSession2TokensListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void registerRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void unregisterRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isGlobalPriorityActive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnVolumeKeyLongPressListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnMediaKeyListener);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isTrusted(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaKeyDispatcher(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaSessionPolicyProvider(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaKeyDispatcher(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaSessionPolicyProvider(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public int getSessionPolicies(MediaSession.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setSessionPolicies(MediaSession.Token token, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void expireTempEngagedSessions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
