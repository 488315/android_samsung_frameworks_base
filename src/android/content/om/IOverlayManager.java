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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOverlayManager)) {
                return (IOverlayManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, List<OverlayInfo>> allOverlays = getAllOverlays(i3);
                    parcel2.writeNoException();
                    if (allOverlays == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allOverlays.size());
                        allOverlays.forEach(new BiConsumer() { // from class: android.content.om.IOverlayManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IOverlayManager.Stub.lambda$onTransact$0(parcel2, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                case 2:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<OverlayInfo> overlayInfosForTarget = getOverlayInfosForTarget(string, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overlayInfosForTarget, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfo overlayInfo = getOverlayInfo(string2, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfo, 1);
                    return true;
                case 4:
                    OverlayIdentifier overlayIdentifier = (OverlayIdentifier) parcel.readTypedObject(OverlayIdentifier.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfo overlayInfoByIdentifier = getOverlayInfoByIdentifier(overlayIdentifier, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfoByIdentifier, 1);
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabled = setEnabled(string3, z, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabled);
                    return true;
                case 6:
                    String string4 = parcel.readString();
                    int i8 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableWithConstraints = enableWithConstraints(string4, i8, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableWithConstraints);
                    return true;
                case 7:
                    String string5 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusive = setEnabledExclusive(string5, z2, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusive);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusiveInCategory = setEnabledExclusiveInCategory(string6, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusiveInCategory);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean priority = setPriority(string7, string8, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(priority);
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean highestPriority = setHighestPriority(string9, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highestPriority);
                    return true;
                case 11:
                    String string10 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lowestPriority = setLowestPriority(string10, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lowestPriority);
                    return true;
                case 12:
                    String[] defaultOverlayPackages = getDefaultOverlayPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(defaultOverlayPackages);
                    return true;
                case 13:
                    String string11 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateCachesForOverlay(string11, i14);
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
                    boolean zIsDefaultPartitionOrder = isDefaultPartitionOrder();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDefaultPartitionOrder);
                    return true;
                case 17:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback iSamsungOverlayCallbackAsInterface = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    replaceOverlays(arrayListCreateTypedArrayList2, arrayListCreateTypedArrayList3, iSamsungOverlayCallbackAsInterface, i15);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback iSamsungOverlayCallbackAsInterface2 = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOverlays(arrayListCreateTypedArrayList4, iSamsungOverlayCallbackAsInterface2, i16);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ArrayList arrayListCreateTypedArrayList5 = parcel.createTypedArrayList(OverlayInfoExt.CREATOR);
                    ISamsungOverlayCallback iSamsungOverlayCallbackAsInterface3 = ISamsungOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOverlays(arrayListCreateTypedArrayList5, iSamsungOverlayCallbackAsInterface3, i17);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string12 = parcel.readString();
                    int i18 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zChangeOverlayState = changeOverlayState(string12, i18, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeOverlayState);
                    return true;
                case 21:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt[] allOverlaysInCategory = getAllOverlaysInCategory(i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allOverlaysInCategory, 1);
                    return true;
                case 22:
                    String string13 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt overlayForPath = getOverlayForPath(string13, i21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayForPath, 1);
                    return true;
                case 23:
                    String string14 = parcel.readString();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverlayInfoExt[] overlaysForTarget = getOverlaysForTarget(string14, i22, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(overlaysForTarget, 1);
                    return true;
                case 24:
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyWallpaperColors(arrayList, i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ClassLoader classLoader = getClass().getClassLoader();
                    ArrayList arrayList2 = parcel.readArrayList(classLoader);
                    ArrayList arrayList3 = parcel.readArrayList(classLoader);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    applyWallpaperColor(arrayList2, arrayList3, z4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    List wallpaperColors = getWallpaperColors();
                    parcel2.writeNoException();
                    parcel2.writeList(wallpaperColors);
                    return true;
                case 27:
                    List lastPalette = readLastPalette();
                    parcel2.writeNoException();
                    parcel2.writeList(lastPalette);
                    return true;
                case 28:
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean lastPalette2 = getLastPalette(arrayList4, arrayList5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lastPalette2);
                    parcel2.writeList(arrayList4);
                    parcel2.writeList(arrayList5);
                    return true;
                case 29:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyThemeParkWallpaperColor(uri);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> themeParkOverlayNames = getThemeParkOverlayNames(string15);
                    parcel2.writeNoException();
                    parcel2.writeStringList(themeParkOverlayNames);
                    return true;
                case 31:
                    int i26 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyValidatorListener(i26, z5);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    final HashMap map = i2 < 0 ? null : new HashMap();
                    IntStream.range(0, i2).forEach(new IntConsumer() { // from class: android.content.om.IOverlayManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.createTypedArrayList(OverlayInfo.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List<OverlayInfo> getOverlayInfosForTarget(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(OverlayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayInfo) parcelObtain2.readTypedObject(OverlayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(overlayIdentifier, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayInfo) parcelObtain2.readTypedObject(OverlayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabled(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean enableWithConstraints(String str, int i, List<OverlayConstraint> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusive(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusiveInCategory(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setPriority(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setHighestPriority(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setLowestPriority(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String[] getDefaultOverlayPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void invalidateCachesForOverlay(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void commit(OverlayManagerTransaction overlayManagerTransaction) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(overlayManagerTransaction, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public String getPartitionOrder() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean isDefaultPartitionOrder() throws RemoteException {
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

            @Override // android.content.om.IOverlayManager
            public void replaceOverlays(List<OverlayInfoExt> list, List<OverlayInfoExt> list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeStrongInterface(iSamsungOverlayCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void addOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iSamsungOverlayCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void removeOverlays(List<OverlayInfoExt> list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iSamsungOverlayCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean changeOverlayState(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayInfoExt[]) parcelObtain2.createTypedArray(OverlayInfoExt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt getOverlayForPath(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayInfoExt) parcelObtain2.readTypedObject(OverlayInfoExt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayInfoExt[]) parcelObtain2.createTypedArray(OverlayInfoExt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColors(List list, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyWallpaperColor(List list, List list2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    parcelObtain.writeList(list2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List getWallpaperColors() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List readLastPalette() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean getLastPalette(List list, List list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    ClassLoader classLoader = getClass().getClassLoader();
                    parcelObtain2.readList(list, classLoader);
                    parcelObtain2.readList(list2, classLoader);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void applyThemeParkWallpaperColor(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public List<String> getThemeParkOverlayNames(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void notifyValidatorListener(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
