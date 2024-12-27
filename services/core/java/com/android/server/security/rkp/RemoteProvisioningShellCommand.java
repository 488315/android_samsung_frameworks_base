package com.android.server.security.rkp;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.security.keymint.DeviceInfo;
import android.hardware.security.keymint.IRemotelyProvisionedComponent;
import android.hardware.security.keymint.MacedPublicKey;
import android.hardware.security.keymint.ProtectedData;
import android.hardware.security.keymint.RpcHardwareInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.IInterface;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.security.rkp.service.RegistrationProxy;
import android.security.rkp.service.RemotelyProvisionedKey;
import android.util.IndentingPrintWriter;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.UnsignedInteger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.time.Duration;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RemoteProvisioningShellCommand extends ShellCommand {
    public static final Duration BIND_TIMEOUT = Duration.ofSeconds(10);
    public final int mCallerUid;
    public final Context mContext;
    public final Injector mInjector;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public static IRemotelyProvisionedComponent getIrpcBinder(String str) {
            IRemotelyProvisionedComponent iRemotelyProvisionedComponent;
            StringBuilder sb = new StringBuilder();
            String str2 = IRemotelyProvisionedComponent.DESCRIPTOR;
            String m = BootReceiver$$ExternalSyntheticOutline0.m(sb, str2, "/", str);
            IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(m);
            int i = IRemotelyProvisionedComponent.Stub.$r8$clinit;
            if (waitForDeclaredService == null) {
                iRemotelyProvisionedComponent = null;
            } else {
                IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str2);
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof IRemotelyProvisionedComponent)) {
                    IRemotelyProvisionedComponent.Stub.Proxy proxy =
                            new IRemotelyProvisionedComponent.Stub.Proxy();
                    proxy.mRemote = waitForDeclaredService;
                    iRemotelyProvisionedComponent = proxy;
                } else {
                    iRemotelyProvisionedComponent =
                            (IRemotelyProvisionedComponent) queryLocalInterface;
                }
            }
            if (iRemotelyProvisionedComponent != null) {
                return iRemotelyProvisionedComponent;
            }
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("failed to find ", m));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OutcomeFuture implements OutcomeReceiver {
        public final CompletableFuture mFuture = new CompletableFuture();

        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            this.mFuture.completeExceptionally((Exception) th);
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            this.mFuture.complete(obj);
        }
    }

    public RemoteProvisioningShellCommand(Context context, int i) {
        Injector injector = new Injector();
        this.mContext = context;
        this.mCallerUid = i;
        this.mInjector = injector;
    }

    public final void certify() {
        String nextArgRequired = getNextArgRequired();
        Executor mainExecutor = this.mContext.getMainExecutor();
        CancellationSignal cancellationSignal = new CancellationSignal();
        OutcomeFuture outcomeFuture = new OutcomeFuture();
        Injector injector = this.mInjector;
        Context context = this.mContext;
        int i = this.mCallerUid;
        injector.getClass();
        String m =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(),
                        IRemotelyProvisionedComponent.DESCRIPTOR,
                        "/",
                        nextArgRequired);
        OutcomeFuture outcomeFuture2 = new OutcomeFuture();
        RegistrationProxy.createAsync(context, i, m, BIND_TIMEOUT, mainExecutor, outcomeFuture2);
        ((RegistrationProxy) outcomeFuture2.mFuture.join())
                .getKeyAsync(452436, cancellationSignal, mainExecutor, outcomeFuture);
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(
                        ((RemotelyProvisionedKey) outcomeFuture.mFuture.join())
                                .getEncodedCertChain());
        PrintWriter outPrintWriter = getOutPrintWriter();
        Iterator<? extends Certificate> it =
                CertificateFactory.getInstance("X.509")
                        .generateCertificates(byteArrayInputStream)
                        .iterator();
        while (it.hasNext()) {
            String encodeToString = Base64.getEncoder().encodeToString(it.next().getEncoded());
            outPrintWriter.println("-----BEGIN CERTIFICATE-----");
            outPrintWriter.println(encodeToString.replaceAll("(.{64})", "$1\n").stripTrailing());
            outPrintWriter.println("-----END CERTIFICATE-----");
        }
    }

    public final int csr() {
        byte[] decode;
        Parcel obtain;
        Parcel obtain2;
        byte[] byteArray;
        byte[] bArr = new byte[0];
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                this.mInjector.getClass();
                IRemotelyProvisionedComponent.Stub.Proxy proxy =
                        (IRemotelyProvisionedComponent.Stub.Proxy)
                                Injector.getIrpcBinder(nextArgRequired);
                RpcHardwareInfo hardwareInfo = proxy.getHardwareInfo();
                MacedPublicKey[] macedPublicKeyArr = new MacedPublicKey[0];
                int i = hardwareInfo.versionNumber;
                if (i == 1 || i == 2) {
                    DeviceInfo deviceInfo = new DeviceInfo();
                    ProtectedData protectedData = new ProtectedData();
                    int i2 = hardwareInfo.supportedEekCurve;
                    if (i2 == 1) {
                        decode =
                                Base64.getDecoder()
                                        .decode(
                                                "goRDoQEmoFhNpQECAyYgASFYIPcUituX9MxT79JkEcTjdR9mH6RxDGzP+glGgHSHVPKtIlggXn9b9uzk9hnM/xM3/Q+hyJPbGAZ2xF3m12p3hsMtr49YQC+XjkL7vgctlUeFR5NAsB/Um0ekxESp8qEHhxDHn8sR9L+f6Dvg5zRMFfx7w34zBfTRNDztAgRgehXgedOK/ySEQ6EBJqBYcaYBAgJYIDVztz+gioCJsSZn6ct8daGvAmH8bmUDkTvTS30UlD5GAzgYIAEhWCDgQc8vDzQPHDMsQbDP1wwwVTXSHmpHE0su0UiWfiScaCJYIB/ORcX7YbqBIfnlBZubOQ52hoZHuB4vRfHOr9o/gGjbWECMs7p+ID4ysGjfYNEdffCsOI5RvP9s4Wc7Snm8Vnizmdh8igfY2rW1f3H02GvfMyc0e2XRKuuGmZirOrSAqr1Q");
                    } else {
                        if (i2 != 2) {
                            throw new IllegalArgumentException(
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            i2, "unsupported EEK curve: "));
                        }
                        decode =
                                Base64.getDecoder()
                                        .decode(
                                                "goRDoQEnoFgqpAEBAycgBiFYIJm57t1e5FL2hcZMYtw+YatXSH11NymtdoAy0rPLY1jZWEAeIghLpLekyNdOAw7+uK8UTKc7b6XN3Np5xitk/pk5r3bngPpmAIUNB5gqrJFcpyUUSQY0dcqKJ3rZ41pJ6wIDhEOhASegWE6lAQECWCDQrsEVyirPc65rzMvRlh1l6LHd10oaN7lDOpfVmd+YCAM4GCAEIVggvoXnRsSjQlpA2TY6phXQLFh+PdwzAjLS/F4ehyVfcmBYQJvPkOIuS6vRGLEOjl0gJ0uEWP78MpB+cgWDvNeCvvpkeC1UEEvAMb9r6B414vAtzmwvT/L1T6XUg62WovGHWAQ=");
                    }
                    obtain = Parcel.obtain(proxy.mRemote);
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
                        obtain.writeBoolean(false);
                        obtain.writeTypedArray(macedPublicKeyArr, 0);
                        obtain.writeByteArray(decode);
                        obtain.writeByteArray(bArr);
                        if (!proxy.mRemote.transact(3, obtain, obtain2, 0)) {
                            throw new RemoteException(
                                    "Method generateCertificateRequest is unimplemented.");
                        }
                        obtain2.readException();
                        byte[] createByteArray = obtain2.createByteArray();
                        if (obtain2.readInt() != 0) {
                            deviceInfo.readFromParcel(obtain2);
                        }
                        if (obtain2.readInt() != 0) {
                            protectedData.readFromParcel(obtain2);
                        }
                        obtain2.recycle();
                        obtain.recycle();
                        Array array = new Array();
                        array.objects.add(
                                new CborDecoder(new ByteArrayInputStream(deviceInfo.deviceInfo))
                                        .decodeNext());
                        array.objects.add(new Map());
                        Array array2 = new Array();
                        Map map = new Map();
                        UnsignedInteger unsignedInteger = new UnsignedInteger(1L);
                        if (map.map.put(unsignedInteger, new UnsignedInteger(5L)) == null) {
                            ((LinkedList) map.keys).add(unsignedInteger);
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        new CborEncoder(byteArrayOutputStream).encode(map);
                        array2.objects.add(new ByteString(byteArrayOutputStream.toByteArray()));
                        array2.objects.add(new Map());
                        array2.objects.add(SimpleValue.NULL);
                        array2.objects.add(new ByteString(createByteArray));
                        Array array3 = new Array();
                        array3.objects.add(array);
                        array3.objects.add(new ByteString(bArr));
                        array3.objects.add(
                                new CborDecoder(
                                                new ByteArrayInputStream(
                                                        protectedData.protectedData))
                                        .decodeNext());
                        array3.objects.add(array2);
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        new CborEncoder(byteArrayOutputStream2).encode(array3);
                        byteArray = byteArrayOutputStream2.toByteArray();
                    } catch (Throwable th) {
                        throw th;
                    }
                } else {
                    if (i != 3) {
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("error: unsupported hwVersion: "),
                                hardwareInfo.versionNumber,
                                getErrPrintWriter());
                        return -1;
                    }
                    obtain = Parcel.obtain(proxy.mRemote);
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
                        obtain.writeTypedArray(macedPublicKeyArr, 0);
                        obtain.writeByteArray(bArr);
                        if (!proxy.mRemote.transact(4, obtain, obtain2, 0)) {
                            throw new RemoteException(
                                    "Method generateCertificateRequestV2 is unimplemented.");
                        }
                        obtain2.readException();
                        byteArray = obtain2.createByteArray();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                getOutPrintWriter().println(Base64.getEncoder().encodeToString(byteArray));
                return 0;
            }
            if (!nextOption.equals("--challenge")) {
                getErrPrintWriter().println("error: unknown option ".concat(nextOption));
                return -1;
            }
            bArr = Base64.getDecoder().decode(getNextArgRequired());
        }
    }

    public final void dump(PrintWriter printWriter) {
        try {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            this.mInjector.getClass();
            for (String str :
                    ServiceManager.getDeclaredInstances(IRemotelyProvisionedComponent.DESCRIPTOR)) {
                indentingPrintWriter.println(str + ":");
                indentingPrintWriter.increaseIndent();
                dumpRpcInstance(indentingPrintWriter, str);
                indentingPrintWriter.decreaseIndent();
            }
        } catch (Exception e) {
            e.printStackTrace(printWriter);
        }
    }

    public final void dumpRpcInstance(PrintWriter printWriter, String str) {
        this.mInjector.getClass();
        RpcHardwareInfo hardwareInfo =
                ((IRemotelyProvisionedComponent.Stub.Proxy) Injector.getIrpcBinder(str))
                        .getHardwareInfo();
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("hwVersion="),
                        hardwareInfo.versionNumber,
                        printWriter,
                        "rpcAuthorName="),
                hardwareInfo.rpcAuthorName,
                printWriter);
        if (hardwareInfo.versionNumber < 3) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("supportedEekCurve="),
                    hardwareInfo.supportedEekCurve,
                    printWriter);
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("uniqueId="), hardwareInfo.uniqueId, printWriter);
        if (hardwareInfo.versionNumber >= 3) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("supportedNumKeysInCsr="),
                    hardwareInfo.supportedNumKeysInCsr,
                    printWriter);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0054 A[Catch: Exception -> 0x0029, TryCatch #0 {Exception -> 0x0029, blocks: (B:7:0x0008, B:18:0x0046, B:20:0x004b, B:22:0x004f, B:24:0x0054, B:26:0x0063, B:30:0x001f, B:33:0x002b, B:36:0x0035), top: B:6:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r7) {
        /*
            r6 = this;
            if (r7 != 0) goto L7
            int r6 = r6.handleDefaultCommands(r7)
            return r6
        L7:
            r0 = -1
            int r1 = r7.hashCode()     // Catch: java.lang.Exception -> L29
            r2 = 1
            r3 = 98818(0x18202, float:1.38474E-40)
            r4 = 2
            r5 = 0
            if (r1 == r3) goto L35
            r3 = 3322014(0x32b09e, float:4.655133E-39)
            if (r1 == r3) goto L2b
            r3 = 668936792(0x27df2a58, float:6.1940843E-15)
            if (r1 == r3) goto L1f
            goto L3f
        L1f:
            java.lang.String r1 = "certify"
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Exception -> L29
            if (r1 == 0) goto L3f
            r1 = r4
            goto L40
        L29:
            r7 = move-exception
            goto L70
        L2b:
            java.lang.String r1 = "list"
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Exception -> L29
            if (r1 == 0) goto L3f
            r1 = r5
            goto L40
        L35:
            java.lang.String r1 = "csr"
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Exception -> L29
            if (r1 == 0) goto L3f
            r1 = r2
            goto L40
        L3f:
            r1 = r0
        L40:
            if (r1 == 0) goto L54
            if (r1 == r2) goto L4f
            if (r1 == r4) goto L4b
            int r6 = r6.handleDefaultCommands(r7)     // Catch: java.lang.Exception -> L29
            return r6
        L4b:
            r6.certify()     // Catch: java.lang.Exception -> L29
            return r5
        L4f:
            int r6 = r6.csr()     // Catch: java.lang.Exception -> L29
            return r6
        L54:
            com.android.server.security.rkp.RemoteProvisioningShellCommand$Injector r7 = r6.mInjector     // Catch: java.lang.Exception -> L29
            r7.getClass()     // Catch: java.lang.Exception -> L29
            java.lang.String r7 = android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR     // Catch: java.lang.Exception -> L29
            java.lang.String[] r7 = android.os.ServiceManager.getDeclaredInstances(r7)     // Catch: java.lang.Exception -> L29
            int r1 = r7.length     // Catch: java.lang.Exception -> L29
            r2 = r5
        L61:
            if (r2 >= r1) goto L6f
            r3 = r7[r2]     // Catch: java.lang.Exception -> L29
            java.io.PrintWriter r4 = r6.getOutPrintWriter()     // Catch: java.lang.Exception -> L29
            r4.println(r3)     // Catch: java.lang.Exception -> L29
            int r2 = r2 + 1
            goto L61
        L6f:
            return r5
        L70:
            java.io.PrintWriter r6 = r6.getErrPrintWriter()
            r7.printStackTrace(r6)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.security.rkp.RemoteProvisioningShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        getOutPrintWriter()
                .print(
                        "usage: cmd remote_provisioning SUBCOMMAND [ARGS]\n"
                            + "help\n"
                            + "  Show this message.\n"
                            + "dump\n"
                            + "  Dump service diagnostics.\n"
                            + "list\n"
                            + "  List the names of the IRemotelyProvisionedComponent instances.\n"
                            + "csr [--challenge CHALLENGE] NAME\n"
                            + "  Generate and print a base64-encoded CSR from the named\n"
                            + "  IRemotelyProvisionedComponent. A base64-encoded challenge can be"
                            + " provided,\n"
                            + "  or else it defaults to an empty challenge.\n"
                            + "certify NAME\n"
                            + "  Output the PEM-encoded certificate chain provisioned for the"
                            + " named\n"
                            + "  IRemotelyProvisionedComponent.\n");
    }
}
