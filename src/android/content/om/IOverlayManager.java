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

    public static class Default implements IOverlayManager {
        @Override // android.content.om.IOverlayManager
        public void addOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void applyThemeParkWallpaperColor(Uri uri) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void applyWallpaperColor(List list, List list2, boolean z) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void applyWallpaperColors(List list, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean changeOverlayState(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public void commit(OverlayManagerTransaction overlayManagerTransaction) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public boolean enableWithConstraints(String str, int i, List<OverlayConstraint> list) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public Map<String, List<OverlayInfo>> getAllOverlays(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public String[] getDefaultOverlayPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean getLastPalette(List list, List list2) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt getOverlayForPath(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfo getOverlayInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public List<OverlayInfo> getOverlayInfosForTarget(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public String getPartitionOrder() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public List<String> getThemeParkOverlayNames(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public List getWallpaperColors() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public void invalidateCachesForOverlay(String str, int i) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public boolean isDefaultPartitionOrder() throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public void notifyValidatorListener(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public List readLastPalette() throws RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public void removeOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void replaceOverlays(List<OverlayInfoExt> list, List<OverlayInfoExt> list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabled(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusive(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusiveInCategory(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setHighestPriority(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setLowestPriority(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setPriority(String str, String str2, int i) throws RemoteException {
            return false;
        }
    }

    void addOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    void applyThemeParkWallpaperColor(Uri uri) throws RemoteException;

    void applyWallpaperColor(List list, List list2, boolean z) throws RemoteException;

    void applyWallpaperColors(List list, int i, int i2) throws RemoteException;

    boolean changeOverlayState(String str, int i, boolean z) throws RemoteException;

    void commit(OverlayManagerTransaction overlayManagerTransaction) throws RemoteException;

    boolean enableWithConstraints(String str, int i, List<OverlayConstraint> list) throws RemoteException;

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

    void notifyValidatorListener(int i, boolean z) throws RemoteException;

    List readLastPalette() throws RemoteException;

    void removeOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    void replaceOverlays(List<OverlayInfoExt> list, List<OverlayInfoExt> list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException;

    boolean setEnabled(String str, boolean z, int i) throws RemoteException;

    boolean setEnabledExclusive(String str, boolean z, int i) throws RemoteException;

    boolean setEnabledExclusiveInCategory(String str, int i) throws RemoteException;

    boolean setHighestPriority(String str, int i) throws RemoteException;

    boolean setLowestPriority(String str, int i) throws RemoteException;

    boolean setPriority(String str, String str2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IOverlayManager {
        public static final String DESCRIPTOR = "android.content.om.IOverlayManager";
        static final int TRANSACTION_addOverlays = 18;
        static final int TRANSACTION_applyThemeParkWallpaperColor = 29;
        static final int TRANSACTION_applyWallpaperColor = 25;
        static final int TRANSACTION_applyWallpaperColors = 24;
        static final int TRANSACTION_changeOverlayState = 20;
        static final int TRANSACTION_commit = 14;
        static final int TRANSACTION_enableWithConstraints = 6;
        static final int TRANSACTION_getAllOverlays = 1;
        static final int TRANSACTION_getAllOverlaysInCategory = 21;
        static final int TRANSACTION_getDefaultOverlayPackages = 12;
        static final int TRANSACTION_getLastPalette = 28;
        static final int TRANSACTION_getOverlayForPath = 22;
        static final int TRANSACTION_getOverlayInfo = 3;
        static final int TRANSACTION_getOverlayInfoByIdentifier = 4;
        static final int TRANSACTION_getOverlayInfosForTarget = 2;
        static final int TRANSACTION_getOverlaysForTarget = 23;
        static final int TRANSACTION_getPartitionOrder = 15;
        static final int TRANSACTION_getThemeParkOverlayNames = 30;
        static final int TRANSACTION_getWallpaperColors = 26;
        static final int TRANSACTION_invalidateCachesForOverlay = 13;
        static final int TRANSACTION_isDefaultPartitionOrder = 16;
        static final int TRANSACTION_notifyValidatorListener = 31;
        static final int TRANSACTION_readLastPalette = 27;
        static final int TRANSACTION_removeOverlays = 19;
        static final int TRANSACTION_replaceOverlays = 17;
        static final int TRANSACTION_setEnabled = 5;
        static final int TRANSACTION_setEnabledExclusive = 7;
        static final int TRANSACTION_setEnabledExclusiveInCategory = 8;
        static final int TRANSACTION_setHighestPriority = 10;
        static final int TRANSACTION_setLowestPriority = 11;
        static final int TRANSACTION_setPriority = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOverlayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOverlayManager)) {
                return (IOverlayManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "enableWithConstraints";
                case 7:
                    return "setEnabledExclusive";
                case 8:
                    return "setEnabledExclusiveInCategory";
                case 9:
                    return "setPriority";
                case 10:
                    return "setHighestPriority";
                case 11:
                    return "setLowestPriority";
                case 12:
                    return "getDefaultOverlayPackages";
                case 13:
                    return "invalidateCachesForOverlay";
                case 14:
                    return "commit";
                case 15:
                    return "getPartitionOrder";
                case 16:
                    return "isDefaultPartitionOrder";
                case 17:
                    return "replaceOverlays";
                case 18:
                    return "addOverlays";
                case 19:
                    return "removeOverlays";
                case 20:
                    return "changeOverlayState";
                case 21:
                    return "getAllOverlaysInCategory";
                case 22:
                    return "getOverlayForPath";
                case 23:
                    return "getOverlaysForTarget";
                case 24:
                    return "applyWallpaperColors";
                case 25:
                    return "applyWallpaperColor";
                case 26:
                    return "getWallpaperColors";
                case 27:
                    return "readLastPalette";
                case 28:
                    return "getLastPalette";
                case 29:
                    return "applyThemeParkWallpaperColor";
                case 30:
                    return "getThemeParkOverlayNames";
                case 31:
                    return "notifyValidatorListener";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
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
                    parcel.enforceNoDataAvail();
                    Map<String, List<OverlayInfo>> allOverlays = getAllOverlays(readInt);
                    parcel2.writeNoException();
                    if (allOverlays == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allOverlays.size());
                        allOverlays.forEach(new BiConsumer() { // from class: android.content.om.IOverlayManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IOverlayManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<OverlayInfo> overlayInfosForTarget = getOverlayInfosForTarget(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overlayInfosForTarget, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfo overlayInfo = getOverlayInfo(readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfo, 1);
                    return true;
                case 4:
                    OverlayIdentifier overlayIdentifier = (OverlayIdentifier) parcel.readTypedObject(OverlayIdentifier.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfo overlayInfoByIdentifier = getOverlayInfoByIdentifier(overlayIdentifier, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfoByIdentifier, 1);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabled = setEnabled(readString3, readBoolean, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabled);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableWithConstraints = enableWithConstraints(readString4, readInt6, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableWithConstraints);
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusive = setEnabledExclusive(readString5, readBoolean2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusive);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusiveInCategory = setEnabledExclusiveInCategory(readString6, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusiveInCategory);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean priority = setPriority(readString7, readString8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(priority);
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean highestPriority = setHighestPriority(readString9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highestPriority);
                    return true;
                case 11:
                    String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lowestPriority = setLowestPriority(readString10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lowestPriority);
                    return true;
                case 12:
                    String[] defaultOverlayPackages = getDefaultOverlayPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(defaultOverlayPackages);
                    return true;
                case 13:
                    String readString11 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateCachesForOverlay(readString11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    OverlayManagerTransaction overlayManagerTransaction = (OverlayManagerTransaction) parcel.readTypedObject(OverlayManagerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    commit(overlayManagerTransaction);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String partitionOrder = getPartitionOrder();
                    parcel2.writeNoException();
                    parcel2.writeString(partitionOrder);
                    return true;
                case 16:
                    boolean isDefaultPartitionOrder = isDefaultPartitionOrder();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDefaultPartitionOrder);
                    return true;
                case 17:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback asInterface = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    replaceOverlays(createTypedArrayList2, createTypedArrayList3, asInterface, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback asInterface2 = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOverlays(createTypedArrayList4, asInterface2, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ArrayList createTypedArrayList5 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback asInterface3 = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOverlays(createTypedArrayList5, asInterface3, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString12 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean changeOverlayState = changeOverlayState(readString12, readInt16, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeOverlayState);
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt[] allOverlaysInCategory = getAllOverlaysInCategory(readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allOverlaysInCategory, 1);
                    return true;
                case 22:
                    String readString13 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt overlayForPath = getOverlayForPath(readString13, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayForPath, 1);
                    return true;
                case 23:
                    String readString14 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt[] overlaysForTarget = getOverlaysForTarget(readString14, readInt20, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(overlaysForTarget, 1);
                    return true;
                case 24:
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyWallpaperColors(readArrayList, readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ClassLoader classLoader = getClass().getClassLoader();
                    ArrayList readArrayList2 = parcel.readArrayList(classLoader);
                    ArrayList readArrayList3 = parcel.readArrayList(classLoader);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    applyWallpaperColor(readArrayList2, readArrayList3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    List wallpaperColors = getWallpaperColors();
                    parcel2.writeNoException();
                    parcel2.writeList(wallpaperColors);
                    return true;
                case 27:
                    List readLastPalette = readLastPalette();
                    parcel2.writeNoException();
                    parcel2.writeList(readLastPalette);
                    return true;
                case 28:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean lastPalette = getLastPalette(arrayList, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lastPalette);
                    parcel2.writeList(arrayList);
                    parcel2.writeList(arrayList2);
                    return true;
                case 29:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyThemeParkWallpaperColor(uri);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> themeParkOverlayNames = getThemeParkOverlayNames(readString15);
                    parcel2.writeNoException();
                    parcel2.writeStringList(themeParkOverlayNames);
                    return true;
                case 31:
                    int readInt24 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyValidatorListener(readInt24, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, List list) {
            parcel.writeString(str);
            parcel.writeTypedList(list, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IOverlayManager {
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

            @Override // android.content.om.IOverlayManager
            public Map<String, List<OverlayInfo>> getAllOverlays(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.content.om.IOverlayManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), Parcel.this.createTypedArrayList(OverlayInfo.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List<OverlayInfo> getOverlayInfosForTarget(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayInfo) obtain2.readTypedObject(OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(overlayIdentifier, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayInfo) obtain2.readTypedObject(OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabled(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean enableWithConstraints(String str, int i, List<OverlayConstraint> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusive(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusiveInCategory(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setPriority(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setHighestPriority(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setLowestPriority(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String[] getDefaultOverlayPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void invalidateCachesForOverlay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void commit(OverlayManagerTransaction overlayManagerTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(overlayManagerTransaction, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String getPartitionOrder() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean isDefaultPartitionOrder() throws RemoteException {
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

            @Override // android.content.om.IOverlayManager
            public void replaceOverlays(List<OverlayInfoExt> list, List<OverlayInfoExt> list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeStrongInterface(iSamsungOverlayCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void addOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iSamsungOverlayCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void removeOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iSamsungOverlayCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean changeOverlayState(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayInfoExt[]) obtain2.createTypedArray(OverlayInfoExt.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt getOverlayForPath(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayInfoExt) obtain2.readTypedObject(OverlayInfoExt.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayInfoExt[]) obtain2.createTypedArray(OverlayInfoExt.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColors(List list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColor(List list, List list2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List getWallpaperColors() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List readLastPalette() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean getLastPalette(List list, List list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    ClassLoader classLoader = getClass().getClassLoader();
                    obtain2.readList(list, classLoader);
                    obtain2.readList(list2, classLoader);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyThemeParkWallpaperColor(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List<String> getThemeParkOverlayNames(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void notifyValidatorListener(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
