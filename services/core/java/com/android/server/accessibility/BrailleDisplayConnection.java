package com.android.server.accessibility;

import android.accessibilityservice.IBrailleDisplayConnection;
import android.accessibilityservice.IBrailleDisplayController;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
class BrailleDisplayConnection extends IBrailleDisplayConnection.Stub {
    public static final Set sConnectedNodes = new ArraySet();
    public IBrailleDisplayController mController;
    public File mHidrawNode;
    public Thread mInputThread;
    public final Object mLock;
    public OutputStream mOutputStream;
    public HandlerThread mOutputThread;
    public BrailleDisplayScanner mScanner;
    public final AccessibilityServiceConnection mServiceConnection;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.BrailleDisplayConnection$1, reason: invalid class name */
    public final class AnonymousClass1 implements BrailleDisplayScanner {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object val$nativeInterface;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.val$nativeInterface = obj;
        }

        public static Object readFromFileDescriptor(Path path, Function function) {
            try {
                FileInputStream fileInputStream = new FileInputStream(path.toFile());
                try {
                    Object apply =
                            function.apply(Integer.valueOf(fileInputStream.getFD().getInt$()));
                    fileInputStream.close();
                    return apply;
                } finally {
                }
            } catch (IOException unused) {
                return null;
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public final int getDeviceBusType(Path path) {
            switch (this.$r8$classId) {
                case 0:
                    Objects.requireNonNull(path);
                    NativeInterface nativeInterface = (NativeInterface) this.val$nativeInterface;
                    Objects.requireNonNull(nativeInterface);
                    Integer num =
                            (Integer)
                                    readFromFileDescriptor(
                                            path,
                                            new BrailleDisplayConnection$1$$ExternalSyntheticLambda0(
                                                    nativeInterface, 2));
                    if (num != null) {
                        return num.intValue();
                    }
                    return -1;
                default:
                    return ((Bundle) ((Map) this.val$nativeInterface).get(path))
                                    .getBoolean("BUS_BLUETOOTH")
                            ? 5
                            : 3;
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public final byte[] getDeviceReportDescriptor(Path path) {
            switch (this.$r8$classId) {
                case 0:
                    Objects.requireNonNull(path);
                    return (byte[])
                            readFromFileDescriptor(
                                    path,
                                    new BrailleDisplayConnection$1$$ExternalSyntheticLambda0(
                                            (NativeInterface) this.val$nativeInterface, 0));
                default:
                    return ((Bundle) ((Map) this.val$nativeInterface).get(path))
                            .getByteArray("DESCRIPTOR");
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public final Collection getHidrawNodePaths(Path path) {
            switch (this.$r8$classId) {
                case 0:
                    ArrayList arrayList = new ArrayList();
                    try {
                        DirectoryStream<Path> newDirectoryStream =
                                Files.newDirectoryStream(path, "hidraw*");
                        try {
                            Iterator<Path> it = newDirectoryStream.iterator();
                            while (it.hasNext()) {
                                arrayList.add(it.next());
                            }
                            newDirectoryStream.close();
                            return arrayList;
                        } finally {
                        }
                    } catch (IOException unused) {
                        return null;
                    }
                default:
                    if (((Map) this.val$nativeInterface).isEmpty()) {
                        return null;
                    }
                    return ((Map) this.val$nativeInterface).keySet();
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public final String getName(Path path) {
            switch (this.$r8$classId) {
                case 0:
                    Objects.requireNonNull(path);
                    NativeInterface nativeInterface = (NativeInterface) this.val$nativeInterface;
                    Objects.requireNonNull(nativeInterface);
                    return (String)
                            readFromFileDescriptor(
                                    path,
                                    new BrailleDisplayConnection$1$$ExternalSyntheticLambda0(
                                            nativeInterface, 1));
                default:
                    return ((Bundle) ((Map) this.val$nativeInterface).get(path)).getString("NAME");
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public final String getUniqueId(Path path) {
            switch (this.$r8$classId) {
                case 0:
                    Objects.requireNonNull(path);
                    NativeInterface nativeInterface = (NativeInterface) this.val$nativeInterface;
                    Objects.requireNonNull(nativeInterface);
                    return (String)
                            readFromFileDescriptor(
                                    path,
                                    new BrailleDisplayConnection$1$$ExternalSyntheticLambda0(
                                            nativeInterface, 3));
                default:
                    return ((Bundle) ((Map) this.val$nativeInterface).get(path))
                            .getString("UNIQUE_ID");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BrailleDisplayScanner {
        int getDeviceBusType(Path path);

        byte[] getDeviceReportDescriptor(Path path);

        Collection getHidrawNodePaths(Path path);

        String getName(Path path);

        String getUniqueId(Path path);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultNativeInterface implements NativeInterface {
        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public final int getHidrawBusType(int i) {
            return BrailleDisplayConnection.nativeGetHidrawBusType(i);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public final byte[] getHidrawDesc(int i, int i2) {
            return BrailleDisplayConnection.nativeGetHidrawDesc(i, i2);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public final int getHidrawDescSize(int i) {
            return BrailleDisplayConnection.nativeGetHidrawDescSize(i);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public final String getHidrawName(int i) {
            return BrailleDisplayConnection.nativeGetHidrawName(i);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public final String getHidrawUniq(int i) {
            return BrailleDisplayConnection.nativeGetHidrawUniq(i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface NativeInterface {
        int getHidrawBusType(int i);

        byte[] getHidrawDesc(int i, int i2);

        int getHidrawDescSize(int i);

        String getHidrawName(int i);

        String getHidrawUniq(int i);
    }

    public BrailleDisplayConnection(
            Object obj, AccessibilityServiceConnection accessibilityServiceConnection) {
        Objects.requireNonNull(obj);
        this.mLock = obj;
        this.mScanner = getDefaultNativeScanner(new DefaultNativeInterface());
        Objects.requireNonNull(accessibilityServiceConnection);
        this.mServiceConnection = accessibilityServiceConnection;
    }

    public static BrailleDisplayScanner getDefaultNativeScanner(NativeInterface nativeInterface) {
        Objects.requireNonNull(nativeInterface);
        return new AnonymousClass1(0, nativeInterface);
    }

    public static boolean isBrailleDisplay(byte[] bArr) {
        int i;
        int i2 = 0;
        boolean z = false;
        while (i2 < bArr.length) {
            byte b = bArr[i2];
            if ((b & 240) == 240) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        b, "Item ", " declares unsupported long type", "BrailleDisplayConnection");
                return false;
            }
            int i3 = b & 3;
            if (i3 == 0) {
                i = 0;
            } else if (i3 != 1) {
                i = 2;
                if (i3 != 2) {
                    i = 4;
                }
            } else {
                i = 1;
            }
            int i4 = i2 + i;
            if (i4 >= bArr.length) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        b,
                        "Item ",
                        " specifies size past the remaining bytes",
                        "BrailleDisplayConnection");
                return false;
            }
            if (i == 1) {
                byte b2 = bArr[i2 + 1];
                if (((byte) (b & 252)) == 4 && b2 == 65) {
                    z = true;
                }
            }
            i2 = i4 + 1;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeGetHidrawBusType(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native byte[] nativeGetHidrawDesc(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeGetHidrawDescSize(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String nativeGetHidrawName(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String nativeGetHidrawUniq(int i);

    public final void connectLocked(
            String str, String str2, int i, IBrailleDisplayController iBrailleDisplayController) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(iBrailleDisplayController);
        this.mController = iBrailleDisplayController;
        Path of = Path.of("/dev", new String[0]);
        ArrayList arrayList = new ArrayList();
        Collection<Path> hidrawNodePaths = this.mScanner.getHidrawNodePaths(of);
        if (hidrawNodePaths == null) {
            Slog.w("BrailleDisplayConnection", "Unable to access the HIDRAW node directory");
            sendConnectionErrorLocked(1);
            return;
        }
        boolean z = false;
        for (Path path : hidrawNodePaths) {
            byte[] deviceReportDescriptor = this.mScanner.getDeviceReportDescriptor(path);
            if (deviceReportDescriptor == null) {
                z = true;
            } else {
                String uniqueId = this.mScanner.getUniqueId(path);
                boolean equalsIgnoreCase =
                        uniqueId != null
                                ? str.equalsIgnoreCase(uniqueId)
                                : !TextUtils.isEmpty(str2)
                                        && str2.equals(this.mScanner.getName(path));
                if (isBrailleDisplay(deviceReportDescriptor)
                        && this.mScanner.getDeviceBusType(path) == i
                        && equalsIgnoreCase) {
                    arrayList.add(Pair.create(path.toFile(), deviceReportDescriptor));
                }
            }
        }
        int i2 = 2;
        if (arrayList.size() != 1) {
            if (z) {
                Slog.w(
                        "BrailleDisplayConnection",
                        "Unable to access some HIDRAW node's descriptor");
                i2 = 3;
            } else {
                Slog.w(
                        "BrailleDisplayConnection",
                        "Unable to find a unique Braille display matching the provided device");
            }
            sendConnectionErrorLocked(i2);
            return;
        }
        this.mHidrawNode = (File) ((Pair) arrayList.get(0)).first;
        byte[] bArr = (byte[]) ((Pair) arrayList.get(0)).second;
        ArraySet arraySet = (ArraySet) sConnectedNodes;
        if (arraySet.contains(this.mHidrawNode)) {
            Slog.w(
                    "BrailleDisplayConnection",
                    "Unable to find an unused Braille display matching the provided device");
            sendConnectionErrorLocked(2);
            return;
        }
        arraySet.add(this.mHidrawNode);
        Thread thread =
                new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                BrailleDisplayConnection brailleDisplayConnection =
                                        BrailleDisplayConnection.this;
                                Set set = BrailleDisplayConnection.sConnectedNodes;
                                brailleDisplayConnection.getClass();
                                Process.setThreadPriority(10);
                                try {
                                    FileInputStream fileInputStream =
                                            new FileInputStream(
                                                    brailleDisplayConnection.mHidrawNode);
                                    try {
                                        byte[] bArr2 =
                                                new byte[IBinder.getSuggestedMaxIpcSizeBytes()];
                                        while (true) {
                                            if (Thread.interrupted()) {
                                                break;
                                            }
                                            if (!brailleDisplayConnection.mHidrawNode.exists()) {
                                                brailleDisplayConnection.disconnect();
                                                break;
                                            }
                                            int read = fileInputStream.read(bArr2);
                                            if (read > 0) {
                                                try {
                                                    brailleDisplayConnection.mController.onInput(
                                                            Arrays.copyOfRange(bArr2, 0, read));
                                                } catch (RemoteException e) {
                                                    Slog.e(
                                                            "BrailleDisplayConnection",
                                                            "Error calling onInput",
                                                            e);
                                                    brailleDisplayConnection.disconnect();
                                                }
                                            }
                                        }
                                        fileInputStream.close();
                                    } finally {
                                    }
                                } catch (IOException e2) {
                                    Slog.d(
                                            "BrailleDisplayConnection",
                                            "Error reading from connected Braille display",
                                            e2);
                                    brailleDisplayConnection.disconnect();
                                }
                            }
                        },
                        "BrailleDisplayConnection input thread");
        this.mInputThread = thread;
        thread.setDaemon(true);
        this.mInputThread.start();
        try {
            this.mServiceConnection.mBrailleDisplayConnection = this;
            this.mController.onConnected(this, bArr);
        } catch (RemoteException e) {
            Slog.e("BrailleDisplayConnection", "Error calling onConnected", e);
            disconnect();
        }
    }

    public final void disconnect() {
        synchronized (this.mLock) {
            Thread thread = this.mInputThread;
            if (thread != null) {
                thread.interrupt();
            }
            this.mInputThread = null;
            HandlerThread handlerThread = this.mOutputThread;
            if (handlerThread != null) {
                handlerThread.quit();
            }
            this.mOutputThread = null;
            OutputStream outputStream = this.mOutputStream;
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Slog.e("BrailleDisplayConnection", "Unable to close output stream", e);
                }
            }
            this.mOutputStream = null;
            this.mServiceConnection.mBrailleDisplayConnection = null;
            try {
                this.mController.onDisconnected();
            } catch (RemoteException unused) {
                Slog.e("BrailleDisplayConnection", "Error calling onDisconnected");
            }
            ((ArraySet) sConnectedNodes).remove(this.mHidrawNode);
        }
    }

    public final void sendConnectionErrorLocked(int i) {
        try {
            this.mController.onConnectionFailed(i);
        } catch (RemoteException e) {
            Slog.e("BrailleDisplayConnection", "Error calling onConnectionFailed", e);
        }
    }

    public final void setTestData(List list) {
        Objects.requireNonNull(list);
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bundle bundle = (Bundle) it.next();
            arrayMap.put(Path.of(bundle.getString("HIDRAW_PATH"), new String[0]), bundle);
        }
        synchronized (this.mLock) {
            this.mScanner = new AnonymousClass1(1, arrayMap);
        }
    }

    public final void write(final byte[] bArr) {
        Objects.requireNonNull(bArr);
        if (bArr.length > IBinder.getSuggestedMaxIpcSizeBytes()) {
            Slog.e(
                    "BrailleDisplayConnection",
                    "Requested write of size "
                            + bArr.length
                            + " which is larger than maximum "
                            + IBinder.getSuggestedMaxIpcSizeBytes());
            disconnect();
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mServiceConnection.mService == null) {
                    throw new IllegalStateException("Accessibility service is not connected");
                }
                if (this.mOutputThread == null) {
                    try {
                        this.mOutputStream = new FileOutputStream(this.mHidrawNode);
                        HandlerThread handlerThread =
                                new HandlerThread("BrailleDisplayConnection output thread", 10);
                        this.mOutputThread = handlerThread;
                        handlerThread.setDaemon(true);
                        this.mOutputThread.start();
                    } catch (Exception e) {
                        Slog.e("BrailleDisplayConnection", "Unable to create write stream", e);
                        disconnect();
                        return;
                    }
                }
                this.mOutputThread
                        .getThreadHandler()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BrailleDisplayConnection brailleDisplayConnection =
                                                BrailleDisplayConnection.this;
                                        byte[] bArr2 = bArr;
                                        Set set = BrailleDisplayConnection.sConnectedNodes;
                                        brailleDisplayConnection.getClass();
                                        try {
                                            brailleDisplayConnection.mOutputStream.write(bArr2);
                                        } catch (IOException e2) {
                                            Slog.d(
                                                    "BrailleDisplayConnection",
                                                    "Error writing to connected Braille display",
                                                    e2);
                                            brailleDisplayConnection.disconnect();
                                        }
                                    }
                                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
