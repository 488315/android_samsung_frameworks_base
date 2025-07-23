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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPrintManager)) {
                return (IPrintManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PrintJobInfo> printJobInfos = getPrintJobInfos(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printJobInfos, 1);
                    return true;
                case 2:
                    PrintJobId printJobId = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PrintJobInfo printJobInfo = getPrintJobInfo(printJobId, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(printJobInfo, 1);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    IPrintDocumentAdapter asInterface = IPrintDocumentAdapter.Stub.asInterface(parcel.readStrongBinder());
                    PrintAttributes printAttributes = (PrintAttributes) parcel.readTypedObject(PrintAttributes.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle print = print(readString, asInterface, printAttributes, readString2, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(print, 1);
                    return true;
                case 4:
                    PrintJobId printJobId2 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelPrintJob(printJobId2, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    PrintJobId printJobId3 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restartPrintJob(printJobId3, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IPrintJobStateChangeListener asInterface2 = IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintJobStateChangeListener(asInterface2, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IPrintJobStateChangeListener asInterface3 = IPrintJobStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintJobStateChangeListener(asInterface3, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IPrintServicesChangeListener asInterface4 = IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServicesChangeListener(asInterface4, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IPrintServicesChangeListener asInterface5 = IPrintServicesChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServicesChangeListener(asInterface5, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PrintServiceInfo> printServices = getPrintServices(readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServices, 1);
                    return true;
                case 11:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintServiceEnabled(componentName, readBoolean, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPrintServiceEnabled = isPrintServiceEnabled(componentName2, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPrintServiceEnabled);
                    return true;
                case 13:
                    IRecommendationsChangeListener asInterface6 = IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrintServiceRecommendationsChangeListener(asInterface6, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IRecommendationsChangeListener asInterface7 = IRecommendationsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrintServiceRecommendationsChangeListener(asInterface7, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<RecommendationInfo> printServiceRecommendations = getPrintServiceRecommendations(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printServiceRecommendations, 1);
                    return true;
                case 16:
                    IPrinterDiscoveryObserver asInterface8 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createPrinterDiscoverySession(asInterface8, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IPrinterDiscoveryObserver asInterface9 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(PrinterId.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterDiscovery(asInterface9, createTypedArrayList, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IPrinterDiscoveryObserver asInterface10 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterDiscovery(asInterface10, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(PrinterId.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    validatePrinters(createTypedArrayList2, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PrinterId printerId = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPrinterStateTracking(printerId, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    PrinterId printerId2 = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Icon customPrinterIcon = getCustomPrinterIcon(printerId2, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(customPrinterIcon, 1);
                    return true;
                case 22:
                    PrinterId printerId3 = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPrinterStateTracking(printerId3, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IPrinterDiscoveryObserver asInterface11 = IPrinterDiscoveryObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyPrinterDiscoverySession(asInterface11, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindInstantServiceAllowed = getBindInstantServiceAllowed(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindInstantServiceAllowed);
                    return true;
                case 25:
                    int readInt32 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindInstantServiceAllowed(readInt32, readBoolean2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PrintJobInfo) obtain2.readTypedObject(PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintDocumentAdapter);
                    obtain.writeTypedObject(printAttributes, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void cancelPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void restartPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintJobStateChangeListener);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintJobStateChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintServicesChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintServicesChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public List<PrintServiceInfo> getPrintServices(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PrintServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean isPrintServiceEnabled(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecommendationsChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecommendationsChangeListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public List<RecommendationInfo> getPrintServiceRecommendations(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(RecommendationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void validatePrinters(List<PrinterId> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void startPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public Icon getCustomPrinterIcon(PrinterId printerId, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Icon) obtain2.readTypedObject(Icon.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void stopPrinterStateTracking(PrinterId printerId, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrinterDiscoveryObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public boolean getBindInstantServiceAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintManager
            public void setBindInstantServiceAllowed(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
