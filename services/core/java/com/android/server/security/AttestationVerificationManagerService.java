package com.android.server.security;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelDuration;
import android.os.RemoteException;
import android.security.Flags;
import android.security.attestationverification.AttestationProfile;
import android.security.attestationverification.IAttestationVerificationManagerService;
import android.security.attestationverification.VerificationToken;
import android.text.TextUtils;
import android.util.ExceptionUtils;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.TimeUtils;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;

public final class AttestationVerificationManagerService extends SystemService {
    public final DumpLogger mDumpLogger;
    public final AttestationVerificationPeerDeviceVerifier mPeerDeviceVerifier;
    public final AnonymousClass1 mService;

    public final class DumpLogger {
        public final ArrayDeque mData = new ArrayDeque(10);
        public int mEventsLogged = 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00de, code lost:

       if (com.android.server.security.AttestationVerificationPeerDeviceVerifier.checkLocalBindingRequirements(r11, r2, r9, r10, r8) == false) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fe, code lost:

       if (r3 == false) goto L45;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0101, code lost:

       r1 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f0, code lost:

       r3 = true;
    */
    /* renamed from: -$$Nest$mverifyAttestationForAllVerifiers, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m855$$Nest$mverifyAttestationForAllVerifiers(
            com.android.server.security.AttestationVerificationManagerService r7,
            android.security.attestationverification.AttestationProfile r8,
            int r9,
            android.os.Bundle r10,
            byte[] r11,
            com.android.internal.infra.AndroidFuture r12) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.security.AttestationVerificationManagerService.m855$$Nest$mverifyAttestationForAllVerifiers(com.android.server.security.AttestationVerificationManagerService,"
                    + " android.security.attestationverification.AttestationProfile, int,"
                    + " android.os.Bundle, byte[], com.android.internal.infra.AndroidFuture):void");
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.security.AttestationVerificationManagerService$1] */
    public AttestationVerificationManagerService(Context context) throws Exception {
        super(context);
        DumpLogger dumpLogger = new DumpLogger();
        this.mDumpLogger = dumpLogger;
        this.mService =
                new IAttestationVerificationManagerService
                        .Stub() { // from class:
                                  // com.android.server.security.AttestationVerificationManagerService.1
                    public final void dump(
                            FileDescriptor fileDescriptor,
                            PrintWriter printWriter,
                            String[] strArr) {
                        if (!Flags.dumpAttestationVerifications()) {
                            super.dump(fileDescriptor, printWriter, strArr);
                            return;
                        }
                        if (DumpUtils.checkDumpAndUsageStatsPermission(
                                AttestationVerificationManagerService.this.getContext(),
                                "AVF",
                                printWriter)) {
                            IndentingPrintWriter indentingPrintWriter =
                                    new IndentingPrintWriter(printWriter, "    ");
                            indentingPrintWriter.print("AttestationVerificationManagerService");
                            indentingPrintWriter.println();
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("Event Log:");
                            indentingPrintWriter.increaseIndent();
                            DumpLogger dumpLogger2 =
                                    AttestationVerificationManagerService.this.mDumpLogger;
                            synchronized (dumpLogger2.mData) {
                                try {
                                    for (AttestationVerificationPeerDeviceVerifier.MyDumpData
                                            myDumpData : dumpLogger2.mData.reversed()) {
                                        indentingPrintWriter.println(
                                                TextUtils.formatSimple(
                                                        "Verification #%d [%s]",
                                                        new Object[] {
                                                            Integer.valueOf(
                                                                    myDumpData.mEventNumber),
                                                            TimeUtils.formatForLogging(
                                                                    myDumpData.mEventTimeMs)
                                                        }));
                                        indentingPrintWriter.increaseIndent();
                                        myDumpData.dumpTo(indentingPrintWriter);
                                        indentingPrintWriter.decreaseIndent();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                    }

                    public final void verifyAttestation(
                            AttestationProfile attestationProfile,
                            int i,
                            Bundle bundle,
                            byte[] bArr,
                            AndroidFuture androidFuture) {
                        AttestationVerificationManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.USE_ATTESTATION_VERIFICATION_SERVICE",
                                        null);
                        try {
                            Slog.d("AVF", "verifyAttestation");
                            AttestationVerificationManagerService
                                    .m855$$Nest$mverifyAttestationForAllVerifiers(
                                            AttestationVerificationManagerService.this,
                                            attestationProfile,
                                            i,
                                            bundle,
                                            bArr,
                                            androidFuture);
                        } catch (Throwable th) {
                            Slog.e("AVF", "failed to verify attestation", th);
                            throw ExceptionUtils.propagate(th, RemoteException.class);
                        }
                    }

                    public final void verifyToken(
                            VerificationToken verificationToken,
                            ParcelDuration parcelDuration,
                            AndroidFuture androidFuture) {
                        AttestationVerificationManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.USE_ATTESTATION_VERIFICATION_SERVICE",
                                        null);
                        androidFuture.complete(0);
                    }
                };
        this.mPeerDeviceVerifier =
                new AttestationVerificationPeerDeviceVerifier(context, dumpLogger);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.d("AVF", "Started");
        publishBinderService("attestation_verification", this.mService);
    }
}
