package com.samsung.android.battauthmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;

public final class WpcAuthenticator {
    public final PowerManager.WakeLock mAuthWakeLock;
    public final WpcAuthHandler mWpcAuthHandler;
    public final ArrayList certChainList = new ArrayList();
    public byte[] certChainHash = null;
    public byte[] productPublicKey = null;
    public byte[] requestChallenge = null;
    public int wholeCertChainLength = 0;
    public AuthStatus status = AuthStatus.STATUS_NONE;
    public final BattAuthHelper mBattAuthHelper = new BattAuthHelper();

    public final class AuthStatus {
        public static final /* synthetic */ AuthStatus[] $VALUES;
        public static final AuthStatus STATUS_NONE;
        public static final AuthStatus WAIT_CERT_CHAIN;
        public static final AuthStatus WAIT_CHALLENGE_AUTH;
        public static final AuthStatus WAIT_DIGEST;

        static {
            AuthStatus authStatus = new AuthStatus("STATUS_NONE", 0);
            STATUS_NONE = authStatus;
            AuthStatus authStatus2 = new AuthStatus("WAIT_DIGEST", 1);
            WAIT_DIGEST = authStatus2;
            AuthStatus authStatus3 = new AuthStatus("WAIT_CERT_CHAIN", 2);
            WAIT_CERT_CHAIN = authStatus3;
            AuthStatus authStatus4 = new AuthStatus("WAIT_CHALLENGE_AUTH", 3);
            WAIT_CHALLENGE_AUTH = authStatus4;
            $VALUES = new AuthStatus[] {authStatus, authStatus2, authStatus3, authStatus4};
        }

        public static AuthStatus valueOf(String str) {
            return (AuthStatus) Enum.valueOf(AuthStatus.class, str);
        }

        public static AuthStatus[] values() {
            return (AuthStatus[]) $VALUES.clone();
        }
    }

    public final class BatteryEventReceiver extends BroadcastReceiver {
        public boolean isAttachedAuthPad = false;

        public BatteryEventReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BufferedReader bufferedReader;
            int i = 0;
            int intExtra = intent.getIntExtra("misc_event", 0);
            int intExtra2 = intent.getIntExtra("sec_plug_type", 0);
            boolean z = (intExtra & 64) == 64;
            StringBuilder sb = new StringBuilder("onReceive: ");
            sb.append(intent.getAction());
            sb.append(", misc_event: ");
            sb.append(intExtra);
            sb.append(", plug event: ");
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    sb, intExtra2, "BattAuthManager_WpcAuthenticator");
            if ((intExtra & 512) == 512) {
                try {
                    bufferedReader =
                            new BufferedReader(
                                    new FileReader(
                                            "/sys/class/power_supply/battery/wpc_auth_mode",
                                            StandardCharsets.UTF_8));
                } catch (IOException | NumberFormatException e) {
                    Slog.e("BattAuthManager_WpcAuthenticator", "exception in readAuthMode", e);
                }
                try {
                    int parseInt = Integer.parseInt(bufferedReader.readLine());
                    bufferedReader.close();
                    i = parseInt;
                    if (i == 3 || i == 4) {
                        Slog.i("BattAuthManager_WpcAuthenticator", "Starting authentication");
                        this.isAttachedAuthPad = true;
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(1);
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            if (!z && this.isAttachedAuthPad) {
                Slog.i("BattAuthManager_WpcAuthenticator", "Auth pad has been detached");
                this.isAttachedAuthPad = false;
                WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
            } else if (this.isAttachedAuthPad && (intExtra & 1024) == 1024) {
                int ordinal = WpcAuthenticator.this.status.ordinal();
                if (ordinal == 1) {
                    WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(3);
                    return;
                }
                if (ordinal == 2) {
                    WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(5);
                } else if (ordinal != 3) {
                    WpcAuthenticator.this.status = AuthStatus.STATUS_NONE;
                } else {
                    WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(8);
                }
            }
        }
    }

    public final class WpcAuthHandler extends Handler {
        public WpcAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        public static String getNameOfMsgWhat(int i) {
            return i == 0
                    ? "MSG_AUTH_STOP"
                    : 1 == i
                            ? "MSG_AUTH_START"
                            : 2 == i
                                    ? "MSG_AUTH_REQ_DIGEST"
                                    : 3 == i
                                            ? "MSG_AUTH_GET_AND_CHECK_DIGEST"
                                            : 4 == i
                                                    ? "MSG_AUTH_REQ_CERT_CHAIN"
                                                    : 5 == i
                                                            ? "MSG_AUTH_GET_AND_VERIFY_CERT_CHAIN"
                                                            : 7 == i
                                                                    ? "MSG_AUTH_REQ_CHALLENGE"
                                                                    : 8 == i
                                                                            ? "MSG_AUTH_GET_AND_VERIFY_CHALLENGE"
                                                                            : 1000 == i
                                                                                    ? "MSG_AUTH_TIMEOUT"
                                                                                    : NandswapManager$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    i,
                                                                                                    "");
        }

        /* JADX WARN: Code restructure failed: missing block: B:109:0x0314, code lost:

           android.util.Slog.d("BattAuthManager_WpcAuthenticator", "searchDigest, match found! " + r7);
        */
        /* JADX WARN: Code restructure failed: missing block: B:110:0x0329, code lost:

           r8 = r5;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 1037
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.battauthmanager.WpcAuthenticator.WpcAuthHandler.handleMessage(android.os.Message):void");
        }
    }

    /* renamed from: -$$Nest$msetAuthPass, reason: not valid java name */
    public static void m1134$$Nest$msetAuthPass(WpcAuthenticator wpcAuthenticator, boolean z) {
        byte[] bArr = {1};
        byte[] bArr2 = {2};
        BattAuthHelper battAuthHelper = wpcAuthenticator.mBattAuthHelper;
        if (z) {
            battAuthHelper.writeDataToBattMisc(bArr);
            Base64.Encoder encoder = Base64.getEncoder();
            String m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            encoder.encodeToString(wpcAuthenticator.certChainHash),
                            encoder.encodeToString(wpcAuthenticator.productPublicKey));
            Path path = Paths.get("/efs/Battery/qi_digests/cached_digests", new String[0]);
            Path path2 = Paths.get("/efs/Battery/qi_digests/cached_digests.tmp", new String[0]);
            if (!Files.exists(path, new LinkOption[0])) {
                try {
                    Files.createFile(path, new FileAttribute[0]);
                    Slog.d("BattAuthManager_WpcAuthenticator", "saveDigest, created empty file");
                } catch (IOException e) {
                    Slog.e("BattAuthManager_WpcAuthenticator", "saveDigest", e);
                }
            }
            try {
                Charset charset = StandardCharsets.UTF_8;
                BufferedReader newBufferedReader = Files.newBufferedReader(path, charset);
                try {
                    BufferedWriter newBufferedWriter =
                            Files.newBufferedWriter(path2, charset, StandardOpenOption.CREATE_NEW);
                    int i = 0;
                    while (true) {
                        try {
                            String readLine = newBufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (readLine.equals(m$1)) {
                                i++;
                            } else {
                                newBufferedWriter.write(readLine);
                                newBufferedWriter.newLine();
                            }
                        } finally {
                        }
                    }
                    Slog.d("BattAuthManager_WpcAuthenticator", "saveDigest, skipLines : " + i);
                    newBufferedWriter.write(m$1);
                    newBufferedWriter.newLine();
                    Files.delete(path);
                    Files.move(path2, path, StandardCopyOption.REPLACE_EXISTING);
                    newBufferedWriter.close();
                    newBufferedReader.close();
                    try {
                        Os.chmod(
                                "/efs/Battery/qi_digests/cached_digests",
                                FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED);
                        Os.chown("/efs/Battery/qi_digests/cached_digests", 1000, 1000);
                    } catch (ErrnoException e2) {
                        Slog.e(
                                "BattAuthManager_WpcAuthenticator",
                                "setPermission: ErrnoException",
                                e2);
                    }
                } finally {
                }
            } catch (IOException e3) {
                Slog.e("BattAuthManager_WpcAuthenticator", "saveDigest", e3);
            }
        } else {
            battAuthHelper.writeDataToBattMisc(bArr2);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setAuthPass, result : ", "BattAuthManager_WpcAuthenticator", z);
    }

    public WpcAuthenticator(Context context, Looper looper) {
        this.mAuthWakeLock = null;
        this.mWpcAuthHandler = new WpcAuthHandler(looper);
        context.registerReceiver(
                new BatteryEventReceiver(),
                new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"));
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager != null) {
            this.mAuthWakeLock = powerManager.newWakeLock(1, "BattAuthManager_WpcAuthenticator");
        }
        this.mAuthWakeLock.setReferenceCounted(false);
    }

    public static String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.US;
        sb.append("len(" + bArr.length + "), ");
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%02X ", new Object[] {Byte.valueOf(bArr[i])}, sb, i, 1);
        }
        return sb.toString();
    }

    public static void removeDigests() {
        final Path path = Paths.get("/efs/Battery/qi_digests/", new String[0]);
        try {
            Files.walkFileTree(
                    path,
                    new SimpleFileVisitor() { // from class:
                                              // com.samsung.android.battauthmanager.WpcAuthenticator.1
                        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                        public final FileVisitResult postVisitDirectory(
                                Object obj, IOException iOException) {
                            Path path2 = (Path) obj;
                            if (!path2.equals(path)) {
                                Files.delete(path2);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                        public final FileVisitResult visitFile(
                                Object obj, BasicFileAttributes basicFileAttributes) {
                            Files.delete((Path) obj);
                            return FileVisitResult.CONTINUE;
                        }
                    });
            Slog.d("BattAuthManager_WpcAuthenticator", "removeDigests");
        } catch (IOException e) {
            Slog.e(
                    "BattAuthManager_WpcAuthenticator",
                    "Failed to delete removeDigests: " + e.getMessage());
        }
    }
}
