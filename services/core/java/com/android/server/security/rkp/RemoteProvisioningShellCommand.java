package com.android.server.security.rkp;

import android.hardware.security.keymint.DeviceInfo;
import android.hardware.security.keymint.IRemotelyProvisionedComponent;
import android.hardware.security.keymint.MacedPublicKey;
import android.hardware.security.keymint.ProtectedData;
import android.hardware.security.keymint.RpcHardwareInfo;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.util.IndentingPrintWriter;
import co.nstant.p006in.cbor.CborDecoder;
import co.nstant.p006in.cbor.CborEncoder;
import co.nstant.p006in.cbor.model.Array;
import co.nstant.p006in.cbor.model.ByteString;
import co.nstant.p006in.cbor.model.DataItem;
import co.nstant.p006in.cbor.model.Map;
import co.nstant.p006in.cbor.model.SimpleValue;
import co.nstant.p006in.cbor.model.UnsignedInteger;
import com.android.internal.util.jobs.XmlUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Base64;

/* loaded from: classes3.dex */
public class RemoteProvisioningShellCommand extends ShellCommand {
  static final String EEK_ED25519_BASE64 =
      "goRDoQEnoFgqpAEBAycgBiFYIJm57t1e5FL2hcZMYtw+YatXSH11NymtdoAy0rPLY1jZWEAeIghLpLekyNdOAw7+uK8UTKc7b6XN3Np5xitk/pk5r3bngPpmAIUNB5gqrJFcpyUUSQY0dcqKJ3rZ41pJ6wIDhEOhASegWE6lAQECWCDQrsEVyirPc65rzMvRlh1l6LHd10oaN7lDOpfVmd+YCAM4GCAEIVggvoXnRsSjQlpA2TY6phXQLFh+PdwzAjLS/F4ehyVfcmBYQJvPkOIuS6vRGLEOjl0gJ0uEWP78MpB+cgWDvNeCvvpkeC1UEEvAMb9r6B414vAtzmwvT/L1T6XUg62WovGHWAQ=";
  static final String EEK_P256_BASE64 =
      "goRDoQEmoFhNpQECAyYgASFYIPcUituX9MxT79JkEcTjdR9mH6RxDGzP+glGgHSHVPKtIlggXn9b9uzk9hnM/xM3/Q+hyJPbGAZ2xF3m12p3hsMtr49YQC+XjkL7vgctlUeFR5NAsB/Um0ekxESp8qEHhxDHn8sR9L+f6Dvg5zRMFfx7w34zBfTRNDztAgRgehXgedOK/ySEQ6EBJqBYcaYBAgJYIDVztz+gioCJsSZn6ct8daGvAmH8bmUDkTvTS30UlD5GAzgYIAEhWCDgQc8vDzQPHDMsQbDP1wwwVTXSHmpHE0su0UiWfiScaCJYIB/ORcX7YbqBIfnlBZubOQ52hoZHuB4vRfHOr9o/gGjbWECMs7p+ID4ysGjfYNEdffCsOI5RvP9s4Wc7Snm8Vnizmdh8igfY2rW1f3H02GvfMyc0e2XRKuuGmZirOrSAqr1Q";
  public final Injector mInjector;

  public RemoteProvisioningShellCommand() {
    this(new Injector());
  }

  public RemoteProvisioningShellCommand(Injector injector) {
    this.mInjector = injector;
  }

  public void onHelp() {
    getOutPrintWriter()
        .print(
            "usage: cmd remote_provisioning SUBCOMMAND [ARGS]\n"
                + "help\n"
                + "  Show this message.\n"
                + "dump\n"
                + "  Dump service diagnostics.\n"
                + "list [--min-version MIN_VERSION]\n"
                + "  List the names of the IRemotelyProvisionedComponent instances.\n"
                + "csr [--challenge CHALLENGE] NAME\n"
                + "  Generate and print a base64-encoded CSR from the named\n"
                + "  IRemotelyProvisionedComponent. A base64-encoded challenge can be provided,\n"
                + "  or else it defaults to an empty challenge.\n");
  }

  /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x003b A[Catch: Exception -> 0x0040, TRY_LEAVE, TryCatch #0 {Exception -> 0x0040, blocks: (B:7:0x0008, B:15:0x0031, B:17:0x0036, B:19:0x003b, B:21:0x0018, B:24:0x0022), top: B:6:0x0008 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int onCommand(String str) {
    boolean z;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    try {
      int hashCode = str.hashCode();
      if (hashCode != 98818) {
        if (hashCode == 3322014 && str.equals("list")) {
          z = false;
          if (z) {
            return list();
          }
          if (z) {
            return csr();
          }
          return handleDefaultCommands(str);
        }
        z = -1;
        if (z) {}
      } else {
        if (str.equals("csr")) {
          z = true;
          if (z) {}
        }
        z = -1;
        if (z) {}
      }
    } catch (Exception e) {
      e.printStackTrace(getErrPrintWriter());
      return -1;
    }
  }

  public void dump(PrintWriter printWriter) {
    try {
      IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
      for (String str : this.mInjector.getIrpcNames()) {
        indentingPrintWriter.println(str + XmlUtils.STRING_ARRAY_SEPARATOR);
        indentingPrintWriter.increaseIndent();
        dumpRpcInstance(indentingPrintWriter, str);
        indentingPrintWriter.decreaseIndent();
      }
    } catch (Exception e) {
      e.printStackTrace(printWriter);
    }
  }

  public final void dumpRpcInstance(PrintWriter printWriter, String str) {
    RpcHardwareInfo hardwareInfo = this.mInjector.getIrpcBinder(str).getHardwareInfo();
    printWriter.println("hwVersion=" + hardwareInfo.versionNumber);
    printWriter.println("rpcAuthorName=" + hardwareInfo.rpcAuthorName);
    if (hardwareInfo.versionNumber < 3) {
      printWriter.println("supportedEekCurve=" + hardwareInfo.supportedEekCurve);
    }
    printWriter.println("uniqueId=" + hardwareInfo.uniqueId);
    printWriter.println("supportedNumKeysInCsr=" + hardwareInfo.supportedNumKeysInCsr);
  }

  public final int list() {
    for (String str : this.mInjector.getIrpcNames()) {
      getOutPrintWriter().println(str);
    }
    return 0;
  }

  public final int csr() {
    byte[] composeCertificateRequestV1;
    byte[] bArr = new byte[0];
    while (true) {
      String nextOption = getNextOption();
      if (nextOption != null) {
        if (nextOption.equals("--challenge")) {
          bArr = Base64.getDecoder().decode(getNextArgRequired());
        } else {
          getErrPrintWriter().println("error: unknown option");
          return -1;
        }
      } else {
        IRemotelyProvisionedComponent irpcBinder =
            this.mInjector.getIrpcBinder(getNextArgRequired());
        RpcHardwareInfo hardwareInfo = irpcBinder.getHardwareInfo();
        MacedPublicKey[] macedPublicKeyArr = new MacedPublicKey[0];
        int i = hardwareInfo.versionNumber;
        if (i == 1 || i == 2) {
          DeviceInfo deviceInfo = new DeviceInfo();
          ProtectedData protectedData = new ProtectedData();
          composeCertificateRequestV1 =
              composeCertificateRequestV1(
                  deviceInfo,
                  bArr,
                  protectedData,
                  irpcBinder.generateCertificateRequest(
                      false,
                      macedPublicKeyArr,
                      getEekChain(hardwareInfo.supportedEekCurve),
                      bArr,
                      deviceInfo,
                      protectedData));
        } else if (i == 3) {
          composeCertificateRequestV1 =
              irpcBinder.generateCertificateRequestV2(macedPublicKeyArr, bArr);
        } else {
          getErrPrintWriter()
              .println("error: unsupported hwVersion: " + hardwareInfo.versionNumber);
          return -1;
        }
        getOutPrintWriter()
            .println(Base64.getEncoder().encodeToString(composeCertificateRequestV1));
        return 0;
      }
    }
  }

  public final byte[] getEekChain(int i) {
    if (i == 1) {
      return Base64.getDecoder().decode(EEK_P256_BASE64);
    }
    if (i == 2) {
      return Base64.getDecoder().decode(EEK_ED25519_BASE64);
    }
    throw new IllegalArgumentException("unsupported EEK curve: " + i);
  }

  public final byte[] composeCertificateRequestV1(
      DeviceInfo deviceInfo, byte[] bArr, ProtectedData protectedData, byte[] bArr2) {
    Array add = new Array().add(decode(deviceInfo.deviceInfo)).add(new Map());
    return encode(
        new Array()
            .add(add)
            .add(new ByteString(bArr))
            .add(decode(protectedData.protectedData))
            .add(
                new Array()
                    .add(
                        new ByteString(
                            encode(
                                new Map().put(new UnsignedInteger(1L), new UnsignedInteger(5L)))))
                    .add(new Map())
                    .add(SimpleValue.NULL)
                    .add(new ByteString(bArr2))));
  }

  public final byte[] encode(DataItem dataItem) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    new CborEncoder(byteArrayOutputStream).encode(dataItem);
    return byteArrayOutputStream.toByteArray();
  }

  public final DataItem decode(byte[] bArr) {
    return new CborDecoder(new ByteArrayInputStream(bArr)).decodeNext();
  }

  class Injector {
    public String[] getIrpcNames() {
      return ServiceManager.getDeclaredInstances(IRemotelyProvisionedComponent.DESCRIPTOR);
    }

    public IRemotelyProvisionedComponent getIrpcBinder(String str) {
      String str2 = IRemotelyProvisionedComponent.DESCRIPTOR + "/" + str;
      IRemotelyProvisionedComponent asInterface =
          IRemotelyProvisionedComponent.Stub.asInterface(
              ServiceManager.waitForDeclaredService(str2));
      if (asInterface != null) {
        return asInterface;
      }
      throw new IllegalArgumentException("failed to find " + str2);
    }
  }
}
