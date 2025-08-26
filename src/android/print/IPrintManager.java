package android.print;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.print.IPrintDocumentAdapter;
import android.print.IPrintJobStateChangeListener;
import android.print.IPrintServicesChangeListener;
import android.print.IPrinterDiscoveryObserver;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.printservice.recommendation.RecommendationInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPrintManager extends IInterface {

    public static class Default implements IPrintManager {
        @Override // android.print.IPrintManager
        public void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.print.IPrintManager
        public void cancelPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public boolean getBindInstantServiceAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // android.print.IPrintManager
        public Icon getCustomPrinterIcon(PrinterId printerId, int i) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public List<PrintJobInfo> getPrintJobInfos(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public List<RecommendationInfo> getPrintServiceRecommendations(int i) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public List<PrintServiceInfo> getPrintServices(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public boolean isPrintServiceEnabled(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.print.IPrintManager
        public Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.print.IPrintManager
        public void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void restartPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void setBindInstantServiceAllowed(int i, boolean z) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void startPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void stopPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
        }

        @Override // android.print.IPrintManager
        public void validatePrinters(List<PrinterId> list, int i) throws RemoteException {
        }
    }

    void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws RemoteException;

    void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException;

    void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException;

    void cancelPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException;

    void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    boolean getBindInstantServiceAllowed(int i) throws RemoteException;

    Icon getCustomPrinterIcon(PrinterId printerId, int i) throws RemoteException;

    PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) throws RemoteException;

    List<PrintJobInfo> getPrintJobInfos(int i, int i2) throws RemoteException;

    List<RecommendationInfo> getPrintServiceRecommendations(int i) throws RemoteException;

    List<PrintServiceInfo> getPrintServices(int i, int i2) throws RemoteException;

    boolean isPrintServiceEnabled(ComponentName componentName, int i) throws RemoteException;

    Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) throws RemoteException;

    void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws RemoteException;

    void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException;

    void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException;

    void restartPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException;

    void setBindInstantServiceAllowed(int i, boolean z) throws RemoteException;

    void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) throws RemoteException;

    void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int i) throws RemoteException;

    void startPrinterStateTracking(PrinterId printerId, int i) throws RemoteException;

    void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    void stopPrinterStateTracking(PrinterId printerId, int i) throws RemoteException;

    void validatePrinters(List<PrinterId> list, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IPrintManager {
        public static final String DESCRIPTOR = "android.print.IPrintManager";
        static final int TRANSACTION_addPrintJobStateChangeListener = 6;
        static final int TRANSACTION_addPrintServiceRecommendationsChangeListener = 13;
        static final int TRANSACTION_addPrintServicesChangeListener = 8;
        static final int TRANSACTION_cancelPrintJob = 4;
        static final int TRANSACTION_createPrinterDiscoverySession = 16;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 23;
        static final int TRANSACTION_getBindInstantServiceAllowed = 24;
        static final int TRANSACTION_getCustomPrinterIcon = 21;
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_getPrintServiceRecommendations = 15;
        static final int TRANSACTION_getPrintServices = 10;
        static final int TRANSACTION_isPrintServiceEnabled = 12;
        static final int TRANSACTION_print = 3;
        static final int TRANSACTION_removePrintJobStateChangeListener = 7;
        static final int TRANSACTION_removePrintServiceRecommendationsChangeListener = 14;
        static final int TRANSACTION_removePrintServicesChangeListener = 9;
        static final int TRANSACTION_restartPrintJob = 5;
        static final int TRANSACTION_setBindInstantServiceAllowed = 25;
        static final int TRANSACTION_setPrintServiceEnabled = 11;
        static final int TRANSACTION_startPrinterDiscovery = 17;
        static final int TRANSACTION_startPrinterStateTracking = 20;
        static final int TRANSACTION_stopPrinterDiscovery = 18;
        static final int TRANSACTION_stopPrinterStateTracking = 22;
        static final int TRANSACTION_validatePrinters = 19;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPrintManager)) {
                return (IPrintManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPrintJobInfos";
                case 2:
                    return "getPrintJobInfo";
                case 3:
                    return Context.PRINT_SERVICE;
                case 4:
                    return "cancelPrintJob";
                case 5:
                    return "restartPrintJob";
                case 6:
                    return "addPrintJobStateChangeListener";
                case 7:
                    return "removePrintJobStateChangeListener";
                case 8:
                    return "addPrintServicesChangeListener";
                case 9:
                    return "removePrintServicesChangeListener";
                case 10:
                    return "getPrintServices";
                case 11:
                    return "setPrintServiceEnabled";
                case 12:
                    return "isPrintServiceEnabled";
                case 13:
                    return "addPrintServiceRecommendationsChangeListener";
                case 14:
                    return "removePrintServiceRecommendationsChangeListener";
                case 15:
                    return "getPrintServiceRecommendations";
                case 16:
                    return "createPrinterDiscoverySession";
                case 17:
                    return "startPrinterDiscovery";
                case 18:
                    return "stopPrinterDiscovery";
                case 19:
                    return "validatePrinters";
                case 20:
                    return "startPrinterStateTracking";
                case 21:
                    return "getCustomPrinterIcon";
                case 22:
                    return "stopPrinterStateTracking";
                case 23:
                    return "destroyPrinterDiscoverySession";
                case 24:
                    return "getBindInstantServiceAllowed";
                case 25:
                    return "setBindInstantServiceAllowed";
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
                    parcel.enforceNoDataAvail();
                    List<PrintJobInfo> printJobInfos = getPrintJobInfos(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printJobInfos, 1);
                    return true;
                case 2:
                    PrintJobId printJobId = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PrintJobInfo printJobInfo = getPrintJobInfo(printJobId, i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(printJobInfo, 1);
                    return true;
                case 3:
                    String string = parcel.readString();
                    IPrintDocumentAdapter iPrintDocumentAdapterAsInterface = IPrintDocumentAdapter.Stub.asInterface(parcel.readStrongBinder());
                    PrintAttributes printAttributes = (PrintAttributes) parcel.readTypedObject(PrintAttributes.CREATOR);
                    String string2 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundlePrint = print(string, iPrintDocumentAdapterAsInterface, printAttributes, string2, i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundlePrint, 1);
                    return true;
                case 4:
                    PrintJobId printJobId2 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelPrintJob(printJobId2, i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    PrintJobId printJobId3 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restartPrintJob(printJobId3, i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IPrintJobStateChangeListener iPrintJobStateChangeListenerAsInterface = IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintJobStateChangeListener(iPrintJobStateChangeListenerAsInterface, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IPrintJobStateChangeListener iPrintJobStateChangeListenerAsInterface2 = IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintJobStateChangeListener(iPrintJobStateChangeListenerAsInterface2, i15);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IPrintServicesChangeListener iPrintServicesChangeListenerAsInterface = IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServicesChangeListener(iPrintServicesChangeListenerAsInterface, i16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IPrintServicesChangeListener iPrintServicesChangeListenerAsInterface2 = IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServicesChangeListener(iPrintServicesChangeListenerAsInterface2, i17);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PrintServiceInfo> printServices = getPrintServices(i18, i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServices, 1);
                    return true;
                case 11:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintServiceEnabled(componentName, z, i20);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPrintServiceEnabled = isPrintServiceEnabled(componentName2, i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPrintServiceEnabled);
                    return true;
                case 13:
                    IRecommendationsChangeListener iRecommendationsChangeListenerAsInterface = IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServiceRecommendationsChangeListener(iRecommendationsChangeListenerAsInterface, i22);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IRecommendationsChangeListener iRecommendationsChangeListenerAsInterface2 = IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServiceRecommendationsChangeListener(iRecommendationsChangeListenerAsInterface2, i23);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<RecommendationInfo> printServiceRecommendations = getPrintServiceRecommendations(i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServiceRecommendations, 1);
                    return true;
                case 16:
                    IPrinterDiscoveryObserver iPrinterDiscoveryObserverAsInterface = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createPrinterDiscoverySession(iPrinterDiscoveryObserverAsInterface, i25);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IPrinterDiscoveryObserver iPrinterDiscoveryObserverAsInterface2 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PrinterId.CREATOR);
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterDiscovery(iPrinterDiscoveryObserverAsInterface2, arrayListCreateTypedArrayList, i26);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IPrinterDiscoveryObserver iPrinterDiscoveryObserverAsInterface3 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterDiscovery(iPrinterDiscoveryObserverAsInterface3, i27);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(PrinterId.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    validatePrinters(arrayListCreateTypedArrayList2, i28);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PrinterId printerId = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterStateTracking(printerId, i29);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    PrinterId printerId2 = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Icon customPrinterIcon = getCustomPrinterIcon(printerId2, i30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(customPrinterIcon, 1);
                    return true;
                case 22:
                    PrinterId printerId3 = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterStateTracking(printerId3, i31);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IPrinterDiscoveryObserver iPrinterDiscoveryObserverAsInterface4 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyPrinterDiscoverySession(iPrinterDiscoveryObserverAsInterface4, i32);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindInstantServiceAllowed = getBindInstantServiceAllowed(i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindInstantServiceAllowed);
                    return true;
                case 25:
                    int i34 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindInstantServiceAllowed(i34, z2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPrintManager {
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

            @Override // android.print.IPrintManager
            public List<PrintJobInfo> getPrintJobInfos(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PrintJobInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printJobId, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PrintJobInfo) parcelObtain2.readTypedObject(PrintJobInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPrintDocumentAdapter);
                    parcelObtain.writeTypedObject(printAttributes, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void cancelPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printJobId, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void restartPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printJobId, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrintJobStateChangeListener);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrintJobStateChangeListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrintServicesChangeListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrintServicesChangeListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public List<PrintServiceInfo> getPrintServices(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PrintServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean isPrintServiceEnabled(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecommendationsChangeListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecommendationsChangeListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public List<RecommendationInfo> getPrintServiceRecommendations(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(RecommendationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void validatePrinters(List<PrinterId> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printerId, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public Icon getCustomPrinterIcon(PrinterId printerId, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printerId, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Icon) parcelObtain2.readTypedObject(Icon.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(printerId, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean getBindInstantServiceAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setBindInstantServiceAllowed(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
