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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionManager)) {
                return (ISessionManager) queryLocalInterface;
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
                    String readString = parcel.readString();
                    ISessionCallback asInterface = ISessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ISession createSession = createSession(readString, asInterface, readString2, bundle, readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSession);
                    return true;
                case 2:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<MediaSession.Token> sessions = getSessions(componentName, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sessions, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MediaSession.Token mediaKeyEventSession = getMediaKeyEventSession(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mediaKeyEventSession, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mediaKeyEventSessionPackageName = getMediaKeyEventSessionPackageName(readString4);
                    parcel2.writeNoException();
                    parcel2.writeString(mediaKeyEventSessionPackageName);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchMediaKeyEvent(readString5, readBoolean, keyEvent, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    MediaSession.Token token = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dispatchMediaKeyEventToSessionAsSystemService = dispatchMediaKeyEventToSessionAsSystemService(readString6, keyEvent2, token);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dispatchMediaKeyEventToSessionAsSystemService);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    KeyEvent keyEvent3 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEvent(readString7, readString8, readBoolean3, keyEvent3, readInt3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    KeyEvent keyEvent4 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    MediaSession.Token token2 = (MediaSession.Token) parcel.readTypedObject(MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchVolumeKeyEventToSessionAsSystemService(readString9, readString10, keyEvent4, token2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchAdjustVolume(readString11, readString12, readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IActiveSessionsListener asInterface2 = IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSessionsListener(asInterface2, componentName2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IActiveSessionsListener asInterface3 = IActiveSessionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSessionsListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ISession2TokensListener asInterface4 = ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSession2TokensListener(asInterface4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ISession2TokensListener asInterface5 = ISession2TokensListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSession2TokensListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IRemoteSessionCallback asInterface6 = IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteSessionCallback(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IRemoteSessionCallback asInterface7 = IRemoteSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteSessionCallback(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean isGlobalPriorityActive = isGlobalPriorityActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGlobalPriorityActive);
                    return true;
                case 17:
                    IOnMediaKeyEventDispatchedListener asInterface8 = IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventDispatchedListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IOnMediaKeyEventDispatchedListener asInterface9 = IOnMediaKeyEventDispatchedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventDispatchedListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IOnMediaKeyEventSessionChangedListener asInterface10 = IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addOnMediaKeyEventSessionChangedListener(asInterface10, readString13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IOnMediaKeyEventSessionChangedListener asInterface11 = IOnMediaKeyEventSessionChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMediaKeyEventSessionChangedListener(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IOnVolumeKeyLongPressListener asInterface12 = IOnVolumeKeyLongPressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnVolumeKeyLongPressListener(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IOnMediaKeyListener asInterface13 = IOnMediaKeyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setOnMediaKeyListener(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString14 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTrusted = isTrusted(readString14, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTrusted);
                    return true;
                case 24:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaKeyDispatcher(readString15);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCustomMediaSessionPolicyProvider(readString16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCustomMediaKeyDispatcher = hasCustomMediaKeyDispatcher(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCustomMediaKeyDispatcher);
                    return true;
                case 27:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCustomMediaSessionPolicyProvider = hasCustomMediaSessionPolicyProvider(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCustomMediaSessionPolicyProvider);
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
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionPolicies(token4, readInt11);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSessionCallback);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public List<MediaSession.Token> getSessions(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MediaSession.Token.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public MediaSession.Token getMediaKeyEventSession(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaSession.Token) obtain2.readTypedObject(MediaSession.Token.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public String getMediaKeyEventSessionPackageName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean dispatchMediaKeyEventToSessionAsSystemService(String str, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEvent(String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEventToSessionAsSystemService(String str, String str2, KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActiveSessionsListener);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActiveSessionsListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSession2TokensListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSession2TokensListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void registerRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void unregisterRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteSessionCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isGlobalPriorityActive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventDispatchedListener(IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventDispatchedListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeOnMediaKeyEventSessionChangedListener(IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyEventSessionChangedListener);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnVolumeKeyLongPressListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnMediaKeyListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isTrusted(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaKeyDispatcher(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCustomMediaSessionPolicyProvider(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaKeyDispatcher(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean hasCustomMediaSessionPolicyProvider(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public int getSessionPolicies(MediaSession.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setSessionPolicies(MediaSession.Token token, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void expireTempEngagedSessions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
