package android.content.om;

import android.content.om.IOverlayManager;
import android.content.om.ISamsungOverlayCallback;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IOverlayManager extends IInterface {
    void addOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    void applyThemeParkWallpaperColor(Uri uri) throws RemoteException;

    void applyWallpaperColor(List list, List list2, boolean z) throws RemoteException;

    void applyWallpaperColors(List list, int i, int i2) throws RemoteException;

    boolean changeOverlayState(String str, int i, boolean z) throws RemoteException;

    void commit(OverlayManagerTransaction overlayManagerTransaction) throws RemoteException;

    Map<String, List<OverlayInfo>> getAllOverlays(int i) throws RemoteException;

    OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) throws RemoteException;

    String[] getDefaultOverlayPackages() throws RemoteException;

    boolean getLastPalette(List list, List list2) throws RemoteException;

    OverlayInfoExt getOverlayForPath(String str, int i) throws RemoteException;

    OverlayInfo getOverlayInfo(String str, int i) throws RemoteException;

    OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) throws RemoteException;

    List<OverlayInfo> getOverlayInfosForTarget(String str, int i) throws RemoteException;

    OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) throws RemoteException;

    String getPartitionOrder() throws RemoteException;

    List<String> getThemeParkOverlayNames(String str) throws RemoteException;

    List getWallpaperColors() throws RemoteException;

    void invalidateCachesForOverlay(String str, int i) throws RemoteException;

    boolean isDefaultPartitionOrder() throws RemoteException;

    List readLastPalette() throws RemoteException;

    void removeOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    void replaceOverlays(List<OverlayInfoExt> list, List<OverlayInfoExt> list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    boolean setEnabled(String str, boolean z, int i) throws RemoteException;

    boolean setEnabledExclusive(String str, boolean z, int i) throws RemoteException;

    boolean setEnabledExclusiveInCategory(String str, int i) throws RemoteException;

    boolean setHighestPriority(String str, int i) throws RemoteException;

    boolean setLowestPriority(String str, int i) throws RemoteException;

    boolean setPriority(String str, String str2, int i) throws RemoteException;

    public static class Default implements IOverlayManager {
        @Override // android.content.om.IOverlayManager
        public Map<String, List<OverlayInfo>> getAllOverlays(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public List<OverlayInfo> getOverlayInfosForTarget(String targetPackageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfo getOverlayInfo(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabled(String packageName, boolean enable, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusive(String packageName, boolean enable, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusiveInCategory(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setPriority(String packageName, String newParentPackageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setHighestPriority(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setLowestPriority(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public String[] getDefaultOverlayPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public void invalidateCachesForOverlay(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void commit(OverlayManagerTransaction transaction) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public String getPartitionOrder() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean isDefaultPartitionOrder() throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public void replaceOverlays(List<OverlayInfoExt> overlaysToRemove, List<OverlayInfoExt> overlaysToAdd, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void addOverlays(List<OverlayInfoExt> overlays, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void removeOverlays(List<OverlayInfoExt> overlays, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public boolean changeOverlayState(String packageName, int userId, boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt[] getAllOverlaysInCategory(int category, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt getOverlayForPath(String path, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt[] getOverlaysForTarget(String packageName, int category, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public void applyWallpaperColors(List wallpaperColors, int numColorType, int numLuminence) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void applyWallpaperColor(List wallpaperColorSS, List wallpaperColorGG, boolean isGray) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public List getWallpaperColors() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public List readLastPalette() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean getLastPalette(List wallpaperColorSS, List wallpaperColorGG) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public void applyThemeParkWallpaperColor(Uri path) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public List<String> getThemeParkOverlayNames(String prefix) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOverlayManager {
        public static final String DESCRIPTOR = "android.content.om.IOverlayManager";
        static final int TRANSACTION_addOverlays = 17;
        static final int TRANSACTION_applyThemeParkWallpaperColor = 28;
        static final int TRANSACTION_applyWallpaperColor = 24;
        static final int TRANSACTION_applyWallpaperColors = 23;
        static final int TRANSACTION_changeOverlayState = 19;
        static final int TRANSACTION_commit = 13;
        static final int TRANSACTION_getAllOverlays = 1;
        static final int TRANSACTION_getAllOverlaysInCategory = 20;
        static final int TRANSACTION_getDefaultOverlayPackages = 11;
        static final int TRANSACTION_getLastPalette = 27;
        static final int TRANSACTION_getOverlayForPath = 21;
        static final int TRANSACTION_getOverlayInfo = 3;
        static final int TRANSACTION_getOverlayInfoByIdentifier = 4;
        static final int TRANSACTION_getOverlayInfosForTarget = 2;
        static final int TRANSACTION_getOverlaysForTarget = 22;
        static final int TRANSACTION_getPartitionOrder = 14;
        static final int TRANSACTION_getThemeParkOverlayNames = 29;
        static final int TRANSACTION_getWallpaperColors = 25;
        static final int TRANSACTION_invalidateCachesForOverlay = 12;
        static final int TRANSACTION_isDefaultPartitionOrder = 15;
        static final int TRANSACTION_readLastPalette = 26;
        static final int TRANSACTION_removeOverlays = 18;
        static final int TRANSACTION_replaceOverlays = 16;
        static final int TRANSACTION_setEnabled = 5;
        static final int TRANSACTION_setEnabledExclusive = 6;
        static final int TRANSACTION_setEnabledExclusiveInCategory = 7;
        static final int TRANSACTION_setHighestPriority = 9;
        static final int TRANSACTION_setLowestPriority = 10;
        static final int TRANSACTION_setPriority = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOverlayManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IOverlayManager)) {
                return (IOverlayManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getAllOverlays";
                case 2:
                    return "getOverlayInfosForTarget";
                case 3:
                    return "getOverlayInfo";
                case 4:
                    return "getOverlayInfoByIdentifier";
                case 5:
                    return "setEnabled";
                case 6:
                    return "setEnabledExclusive";
                case 7:
                    return "setEnabledExclusiveInCategory";
                case 8:
                    return "setPriority";
                case 9:
                    return "setHighestPriority";
                case 10:
                    return "setLowestPriority";
                case 11:
                    return "getDefaultOverlayPackages";
                case 12:
                    return "invalidateCachesForOverlay";
                case 13:
                    return "commit";
                case 14:
                    return "getPartitionOrder";
                case 15:
                    return "isDefaultPartitionOrder";
                case 16:
                    return "replaceOverlays";
                case 17:
                    return "addOverlays";
                case 18:
                    return "removeOverlays";
                case 19:
                    return "changeOverlayState";
                case 20:
                    return "getAllOverlaysInCategory";
                case 21:
                    return "getOverlayForPath";
                case 22:
                    return "getOverlaysForTarget";
                case 23:
                    return "applyWallpaperColors";
                case 24:
                    return "applyWallpaperColor";
                case 25:
                    return "getWallpaperColors";
                case 26:
                    return "readLastPalette";
                case 27:
                    return "getLastPalette";
                case 28:
                    return "applyThemeParkWallpaperColor";
                case 29:
                    return "getThemeParkOverlayNames";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, final Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    Map<String, List<OverlayInfo>> _result = getAllOverlays(_arg0);
                    reply.writeNoException();
                    if (_result == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result.size());
                        _result.forEach(new BiConsumer() { // from class: android.content.om.IOverlayManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IOverlayManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    List<OverlayInfo> _result2 = getOverlayInfosForTarget(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    OverlayInfo _result3 = getOverlayInfo(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    OverlayIdentifier _arg04 = (OverlayIdentifier) data.readTypedObject(OverlayIdentifier.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    OverlayInfo _result4 = getOverlayInfoByIdentifier(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    boolean _arg14 = data.readBoolean();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = setEnabled(_arg05, _arg14, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    boolean _arg15 = data.readBoolean();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = setEnabledExclusive(_arg06, _arg15, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = setEnabledExclusiveInCategory(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg17 = data.readString();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = setPriority(_arg08, _arg17, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = setHighestPriority(_arg09, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = setLowestPriority(_arg010, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    String[] _result11 = getDefaultOverlayPackages();
                    reply.writeNoException();
                    reply.writeStringArray(_result11);
                    return true;
                case 12:
                    String _arg011 = data.readString();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    invalidateCachesForOverlay(_arg011, _arg110);
                    reply.writeNoException();
                    return true;
                case 13:
                    OverlayManagerTransaction _arg012 = (OverlayManagerTransaction) data.readTypedObject(OverlayManagerTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    commit(_arg012);
                    reply.writeNoException();
                    return true;
                case 14:
                    String _result12 = getPartitionOrder();
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 15:
                    boolean _result13 = isDefaultPartitionOrder();
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 16:
                    List<OverlayInfoExt> _arg013 = data.createTypedArrayList(OverlayInfoExt.CREATOR);
                    List<OverlayInfoExt> _arg111 = data.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback _arg24 = ISamsungOverlayCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    replaceOverlays(_arg013, _arg111, _arg24, _arg3);
                    reply.writeNoException();
                    return true;
                case 17:
                    List<OverlayInfoExt> _arg014 = data.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback _arg112 = ISamsungOverlayCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    addOverlays(_arg014, _arg112, _arg25);
                    reply.writeNoException();
                    return true;
                case 18:
                    List<OverlayInfoExt> _arg015 = data.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback _arg113 = ISamsungOverlayCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    removeOverlays(_arg015, _arg113, _arg26);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg016 = data.readString();
                    int _arg114 = data.readInt();
                    boolean _arg27 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result14 = changeOverlayState(_arg016, _arg114, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 20:
                    int _arg017 = data.readInt();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    OverlayInfoExt[] _result15 = getAllOverlaysInCategory(_arg017, _arg115);
                    reply.writeNoException();
                    reply.writeTypedArray(_result15, 1);
                    return true;
                case 21:
                    String _arg018 = data.readString();
                    int _arg116 = data.readInt();
                    data.enforceNoDataAvail();
                    OverlayInfoExt _result16 = getOverlayForPath(_arg018, _arg116);
                    reply.writeNoException();
                    reply.writeTypedObject(_result16, 1);
                    return true;
                case 22:
                    String _arg019 = data.readString();
                    int _arg117 = data.readInt();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    OverlayInfoExt[] _result17 = getOverlaysForTarget(_arg019, _arg117, _arg28);
                    reply.writeNoException();
                    reply.writeTypedArray(_result17, 1);
                    return true;
                case 23:
                    List _arg020 = data.readArrayList(getClass().getClassLoader());
                    int _arg118 = data.readInt();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    applyWallpaperColors(_arg020, _arg118, _arg29);
                    reply.writeNoException();
                    return true;
                case 24:
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg021 = data.readArrayList(cl);
                    List _arg119 = data.readArrayList(cl);
                    boolean _arg210 = data.readBoolean();
                    data.enforceNoDataAvail();
                    applyWallpaperColor(_arg021, _arg119, _arg210);
                    reply.writeNoException();
                    return true;
                case 25:
                    List _result18 = getWallpaperColors();
                    reply.writeNoException();
                    reply.writeList(_result18);
                    return true;
                case 26:
                    List _result19 = readLastPalette();
                    reply.writeNoException();
                    reply.writeList(_result19);
                    return true;
                case 27:
                    List _arg022 = new ArrayList();
                    List _arg120 = new ArrayList();
                    data.enforceNoDataAvail();
                    boolean _result20 = getLastPalette(_arg022, _arg120);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    reply.writeList(_arg022);
                    reply.writeList(_arg120);
                    return true;
                case 28:
                    Uri _arg023 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    applyThemeParkWallpaperColor(_arg023);
                    reply.writeNoException();
                    return true;
                case 29:
                    String _arg024 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result21 = getThemeParkOverlayNames(_arg024);
                    reply.writeNoException();
                    reply.writeStringList(_result21);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel reply, String k, List v) {
            reply.writeString(k);
            reply.writeTypedList(v, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IOverlayManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.om.IOverlayManager
            public Map<String, List<OverlayInfo>> getAllOverlays(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, List<OverlayInfo>> _result = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.content.om.IOverlayManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IOverlayManager.Stub.Proxy.lambda$getAllOverlays$0(Parcel.this, _result, i);
                        }
                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getAllOverlays$0(Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                _result.put(k, _reply.createTypedArrayList(OverlayInfo.CREATOR));
            }

            @Override // android.content.om.IOverlayManager
            public List<OverlayInfo> getOverlayInfosForTarget(String targetPackageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<OverlayInfo> _result = _reply.createTypedArrayList(OverlayInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfo(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    OverlayInfo _result = (OverlayInfo) _reply.readTypedObject(OverlayInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(packageName, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    OverlayInfo _result = (OverlayInfo) _reply.readTypedObject(OverlayInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabled(String packageName, boolean enable, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(enable);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusive(String packageName, boolean enable, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(enable);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusiveInCategory(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setPriority(String packageName, String newParentPackageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(newParentPackageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setHighestPriority(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setLowestPriority(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String[] getDefaultOverlayPackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void invalidateCachesForOverlay(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void commit(OverlayManagerTransaction transaction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(transaction, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String getPartitionOrder() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean isDefaultPartitionOrder() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void replaceOverlays(List<OverlayInfoExt> overlaysToRemove, List<OverlayInfoExt> overlaysToAdd, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(overlaysToRemove, 0);
                    _data.writeTypedList(overlaysToAdd, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(ownerId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void addOverlays(List<OverlayInfoExt> overlays, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(overlays, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(ownerId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void removeOverlays(List<OverlayInfoExt> overlays, ISamsungOverlayCallback callback, int ownerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(overlays, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(ownerId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean changeOverlayState(String packageName, int userId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getAllOverlaysInCategory(int category, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(category);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    OverlayInfoExt[] _result = (OverlayInfoExt[]) _reply.createTypedArray(OverlayInfoExt.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt getOverlayForPath(String path, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    OverlayInfoExt _result = (OverlayInfoExt) _reply.readTypedObject(OverlayInfoExt.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getOverlaysForTarget(String packageName, int category, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(category);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    OverlayInfoExt[] _result = (OverlayInfoExt[]) _reply.createTypedArray(OverlayInfoExt.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColors(List wallpaperColors, int numColorType, int numLuminence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeList(wallpaperColors);
                    _data.writeInt(numColorType);
                    _data.writeInt(numLuminence);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColor(List wallpaperColorSS, List wallpaperColorGG, boolean isGray) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeList(wallpaperColorSS);
                    _data.writeList(wallpaperColorGG);
                    _data.writeBoolean(isGray);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List getWallpaperColors() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List readLastPalette() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean getLastPalette(List wallpaperColorSS, List wallpaperColorGG) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    ClassLoader cl = getClass().getClassLoader();
                    _reply.readList(wallpaperColorSS, cl);
                    _reply.readList(wallpaperColorGG, cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyThemeParkWallpaperColor(Uri path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(path, 0);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List<String> getThemeParkOverlayNames(String prefix) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(prefix);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
