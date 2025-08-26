package android.hardware.radio;

import android.graphics.Bitmap;
import android.hardware.radio.ITuner;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.media.tv.interactive.TvInteractiveAppService;
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

/* loaded from: classes2.dex */
public interface ITuner extends IInterface {

    public static class Default implements ITuner {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.ITuner
        public void cancel() throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void cancelAnnouncement() throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void close() throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public RadioManager.BandConfig getConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.hardware.radio.ITuner
        public Bitmap getImage(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.radio.ITuner
        public Map<String, String> getParameters(List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.hardware.radio.ITuner
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override // android.hardware.radio.ITuner
        public boolean isConfigFlagSet(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.radio.ITuner
        public boolean isConfigFlagSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.radio.ITuner
        public boolean isMuted() throws RemoteException {
            return false;
        }

        @Override // android.hardware.radio.ITuner
        public void seek(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void setConfigFlag(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void setMuted(boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public Map<String, String> setParameters(Map<String, String> map) throws RemoteException {
            return null;
        }

        @Override // android.hardware.radio.ITuner
        public boolean startBackgroundScan() throws RemoteException {
            return false;
        }

        @Override // android.hardware.radio.ITuner
        public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void step(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void stopProgramListUpdates() throws RemoteException {
        }

        @Override // android.hardware.radio.ITuner
        public void tune(ProgramSelector programSelector) throws RemoteException {
        }
    }

    void cancel() throws RemoteException;

    void cancelAnnouncement() throws RemoteException;

    void close() throws RemoteException;

    RadioManager.BandConfig getConfiguration() throws RemoteException;

    Bitmap getImage(int i) throws RemoteException;

    Map<String, String> getParameters(List<String> list) throws RemoteException;

    boolean isClosed() throws RemoteException;

    boolean isConfigFlagSet(int i) throws RemoteException;

    boolean isConfigFlagSupported(int i) throws RemoteException;

    boolean isMuted() throws RemoteException;

    void seek(boolean z, boolean z2) throws RemoteException;

    void setConfigFlag(int i, boolean z) throws RemoteException;

    void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException;

    void setMuted(boolean z) throws RemoteException;

    Map<String, String> setParameters(Map<String, String> map) throws RemoteException;

    boolean startBackgroundScan() throws RemoteException;

    void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException;

    void step(boolean z, boolean z2) throws RemoteException;

    void stopProgramListUpdates() throws RemoteException;

    void tune(ProgramSelector programSelector) throws RemoteException;

    public static abstract class Stub extends Binder implements ITuner {
        public static final String DESCRIPTOR = "android.hardware.radio.ITuner";
        static final int TRANSACTION_cancel = 10;
        static final int TRANSACTION_cancelAnnouncement = 11;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_getConfiguration = 4;
        static final int TRANSACTION_getImage = 12;
        static final int TRANSACTION_getParameters = 20;
        static final int TRANSACTION_isClosed = 2;
        static final int TRANSACTION_isConfigFlagSet = 17;
        static final int TRANSACTION_isConfigFlagSupported = 16;
        static final int TRANSACTION_isMuted = 6;
        static final int TRANSACTION_seek = 8;
        static final int TRANSACTION_setConfigFlag = 18;
        static final int TRANSACTION_setConfiguration = 3;
        static final int TRANSACTION_setMuted = 5;
        static final int TRANSACTION_setParameters = 19;
        static final int TRANSACTION_startBackgroundScan = 13;
        static final int TRANSACTION_startProgramListUpdates = 14;
        static final int TRANSACTION_step = 7;
        static final int TRANSACTION_stopProgramListUpdates = 15;
        static final int TRANSACTION_tune = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITuner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITuner)) {
                return (ITuner) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "close";
                case 2:
                    return "isClosed";
                case 3:
                    return "setConfiguration";
                case 4:
                    return "getConfiguration";
                case 5:
                    return "setMuted";
                case 6:
                    return "isMuted";
                case 7:
                    return "step";
                case 8:
                    return "seek";
                case 9:
                    return TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 10:
                    return "cancel";
                case 11:
                    return "cancelAnnouncement";
                case 12:
                    return "getImage";
                case 13:
                    return "startBackgroundScan";
                case 14:
                    return "startProgramListUpdates";
                case 15:
                    return "stopProgramListUpdates";
                case 16:
                    return "isConfigFlagSupported";
                case 17:
                    return "isConfigFlagSet";
                case 18:
                    return "setConfigFlag";
                case 19:
                    return "setParameters";
                case 20:
                    return "getParameters";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean zIsClosed = isClosed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClosed);
                    return true;
                case 3:
                    RadioManager.BandConfig bandConfig = (RadioManager.BandConfig) parcel.readTypedObject(RadioManager.BandConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConfiguration(bandConfig);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    RadioManager.BandConfig configuration = getConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configuration, 1);
                    return true;
                case 5:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMuted(z);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean zIsMuted = isMuted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMuted);
                    return true;
                case 7:
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    step(z2, z3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    seek(z4, z5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ProgramSelector programSelector = (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(programSelector);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    cancelAnnouncement();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap image = getImage(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(image, 1);
                    return true;
                case 13:
                    boolean zStartBackgroundScan = startBackgroundScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartBackgroundScan);
                    return true;
                case 14:
                    ProgramList.Filter filter = (ProgramList.Filter) parcel.readTypedObject(ProgramList.Filter.CREATOR);
                    parcel.enforceNoDataAvail();
                    startProgramListUpdates(filter);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    stopProgramListUpdates();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConfigFlagSupported = isConfigFlagSupported(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConfigFlagSupported);
                    return true;
                case 17:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConfigFlagSet = isConfigFlagSet(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConfigFlagSet);
                    return true;
                case 18:
                    int i6 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setConfigFlag(i6, z6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i7 = parcel.readInt();
                    final HashMap map = i7 < 0 ? null : new HashMap();
                    IntStream.range(0, i7).forEach(new IntConsumer() { // from class: android.hardware.radio.ITuner$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i8) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), parcel3.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    Map<String, String> parameters = setParameters(map);
                    parcel2.writeNoException();
                    if (parameters == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(parameters.size());
                        parameters.forEach(new BiConsumer() { // from class: android.hardware.radio.ITuner$Stub$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ITuner.Stub.lambda$onTransact$1(parcel2, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 20:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    Map<String, String> parameters2 = getParameters(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    if (parameters2 == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(parameters2.size());
                        parameters2.forEach(new BiConsumer() { // from class: android.hardware.radio.ITuner$Stub$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ITuner.Stub.lambda$onTransact$2(parcel2, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel parcel, String str, String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        static /* synthetic */ void lambda$onTransact$2(Parcel parcel, String str, String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ITuner {
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

            @Override // android.hardware.radio.ITuner
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public boolean isClosed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bandConfig, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public RadioManager.BandConfig getConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RadioManager.BandConfig) parcelObtain2.readTypedObject(RadioManager.BandConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void setMuted(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public boolean isMuted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void step(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void seek(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void tune(ProgramSelector programSelector) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(programSelector, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void cancelAnnouncement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public Bitmap getImage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public boolean startBackgroundScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(filter, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void stopProgramListUpdates() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public boolean isConfigFlagSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public boolean isConfigFlagSet(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public void setConfigFlag(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITuner
            public Map<String, String> setParameters(Map<String, String> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.radio.ITuner$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ITuner.Stub.Proxy.lambda$setParameters$0(parcelObtain, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map2 = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.hardware.radio.ITuner$Stub$Proxy$$ExternalSyntheticLambda2
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map2.put(parcel.readString(), parcel.readString());
                        }
                    });
                    return map2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$setParameters$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.hardware.radio.ITuner
            public Map<String, String> getParameters(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.hardware.radio.ITuner$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.readString());
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
