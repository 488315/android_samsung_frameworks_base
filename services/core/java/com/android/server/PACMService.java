package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;

import com.samsung.android.service.ProtectedATCommand.Device;
import com.samsung.android.service.ProtectedATCommand.PACMClassifier;
import com.samsung.android.service.ProtectedATCommand.Packet;
import com.samsung.android.service.ProtectedATCommand.list.ICmdList;
import com.samsung.android.service.ProtectedATCommand.list.ProtectedCommand;
import com.samsung.android.service.ProtectedATCommand.list.UserOpenCommand;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public final class PACMService extends SystemService {
    public static final List mReceiverList = new ArrayList();
    public final LinkedHashMap mAtMap;
    public final Context mContext;
    public final Device mDevice;
    public final AnonymousClass1 mHandler;
    public final Object mLock;
    public final AnonymousClass2 mReceiver;

    public final class PACServiceSocketThread implements Runnable {
        public PACServiceSocketThread() {}

        public final byte[] executeCommand(Packet packet) {
            int i;
            synchronized (PACMService.this.mLock) {
                try {
                    byte[] bArr = null;
                    if (packet.isEmpty()) {
                        Slog.e("PACMSOCKET", "Buffer is abnormal(" + packet.size() + ")");
                        return null;
                    }
                    Slog.i("PACMSOCKET", "START - executeCommand :" + packet.size());
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (packet.compareVersion(1) != 1) {
                        return null;
                    }
                    int command = packet.getCommand();
                    Slog.d("PACMSOCKET", "Command : " + command);
                    if (command != 1) {
                        Slog.e("PACMSOCKET", "Unknown command(" + command + ")");
                        i = -268435453;
                    } else {
                        byte[] item = packet.getItem(2);
                        Slog.i("PACMSOCKET", "Packet.PAC_PACKET_CMD_AT_CMD_CHECK:");
                        if (item != null) {
                            String upperCase =
                                    new String(item, StandardCharsets.UTF_8)
                                            .toUpperCase(Locale.ENGLISH);
                            Slog.i("PACMSOCKET", "cmd : " + upperCase);
                            PACMService pACMService = PACMService.this;
                            i =
                                    pACMService.mDevice.checkATCommand(
                                            pACMService.mAtMap, upperCase, packet);
                        } else {
                            i = 0;
                        }
                    }
                    Slog.i("PACMSOCKET", "END - executeCommand :" + command + "(" + i + ")");
                    bArr = new Packet().getResponsePacket(1, command, i);
                    return bArr;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            LocalSocket accept;
            InputStream inputStream;
            try {
                LocalSocket localSocket = new LocalSocket(2);
                try {
                    localSocket.bind(
                            new LocalSocketAddress(
                                    "/dev/socket/pacm/pacmservice",
                                    LocalSocketAddress.Namespace.FILESYSTEM));
                    LocalServerSocket localServerSocket =
                            new LocalServerSocket(localSocket.getFileDescriptor());
                    while (true) {
                        try {
                            try {
                                accept = localServerSocket.accept();
                                try {
                                    inputStream = accept.getInputStream();
                                } catch (Throwable th) {
                                    if (accept != null) {
                                        try {
                                            accept.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Exception e) {
                                Slog.i(
                                        "PACMSOCKET",
                                        "Socket connection may be closed. " + e.toString());
                                e.printStackTrace();
                            }
                            try {
                                OutputStream outputStream = accept.getOutputStream();
                                try {
                                    Slog.d("PACMSOCKET", "[v.1] Ready to connect.");
                                    Packet packet = new Packet();
                                    packet.readStream(inputStream);
                                    byte[] executeCommand = executeCommand(packet);
                                    if (executeCommand == null) {
                                        Slog.e("PACMSOCKET", "ret is null");
                                        outputStream.write(-1);
                                    } else {
                                        outputStream.write(executeCommand);
                                    }
                                    Slog.d("PACMSOCKET", "Disconnected.");
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    accept.close();
                                } catch (Throwable th3) {
                                    if (outputStream != null) {
                                        try {
                                            outputStream.close();
                                        } catch (Throwable th4) {
                                            th3.addSuppressed(th4);
                                        }
                                    }
                                    throw th3;
                                }
                            } catch (Throwable th5) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th6) {
                                        th5.addSuppressed(th6);
                                    }
                                }
                                throw th5;
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            } catch (Exception e2) {
                Slog.e("PACMSOCKET", "Failed to open local socket", e2);
                e2.printStackTrace();
                Slog.e("PACMSOCKET", "Socket thread has been stopped.");
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.PACMService$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.PACMService$2] */
    public PACMService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mAtMap = new LinkedHashMap();
        this.mHandler =
                new Handler() { // from class: com.android.server.PACMService.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1) {
                            return;
                        }
                        Slog.d("PACMService", "MESSAGE_CHECK_BOARDCAST_RECEIVER");
                        List list = PACMService.mReceiverList;
                        if (list == null) {
                            Slog.e("PACMService", "List is null");
                            return;
                        }
                        PACMService pACMService = PACMService.this;
                        ArrayList arrayList = (ArrayList) list;
                        boolean contains = arrayList.contains(pACMService.mReceiver);
                        AnonymousClass1 anonymousClass1 = pACMService.mHandler;
                        if (!contains) {
                            pACMService.registerForBroadcasts();
                            anonymousClass1.sendEmptyMessageDelayed(1, 2000L);
                            return;
                        }
                        Slog.i(
                                "PACMService",
                                "Already registered BroadcastReceiver! [" + arrayList.size() + "]");
                        if (anonymousClass1.hasMessages(1)) {
                            anonymousClass1.removeMessages(1);
                        }
                    }
                };
        this.mReceiver =
                new BroadcastReceiver() { // from class: com.android.server.PACMService.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Slog.i("PACMService", "Broadcast received:" + action);
                        if (action.equals(
                                "com.samsung.android.aircommandmanager.START_LOCAL_SOCKET")) {
                            Slog.i("PACMService", "GalaxyDiag app start");
                            if (PACMService.this.mDevice.isCsToolInstalled()) {
                                PACMService.this.mDevice.setCSTool(true);
                                return;
                            }
                            return;
                        }
                        if (action.equals(
                                "com.samsung.android.aircommandmanager.STOP_LOCAL_SOCKET")) {
                            Slog.i("PACMService", "GalaxyDiag app end");
                            PACMService.this.mDevice.setCSTool(false);
                        }
                    }
                };
        this.mContext = context;
        this.mDevice = new Device(context);
        try {
            ICmdList[] iCmdListArr = {new ProtectedCommand(), new UserOpenCommand()};
            for (int i = 0; i < 2; i++) {
                int putCommandList =
                        PACMClassifier.putCommandList(this.mAtMap, iCmdListArr[i].getList());
                if (putCommandList != 1) {
                    Slog.e(
                            "PACMService",
                            "Failed to add class command list(" + putCommandList + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new Thread(new PACServiceSocketThread()).start();
        } catch (Exception e2) {
            Slog.e("PACMService", "Failed to start PACM Service");
            e2.printStackTrace();
        }
        Slog.i("PACMService", "Success to start PACM Service(S-ver : 10.6.0/ P-ver : 1)");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 480) {
            Slog.d("PACMService", "PHASE_LOCK_SETTINGS_READY");
            registerForBroadcasts();
            sendEmptyMessageDelayed(1, 2000L);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.d("PACMService", "onStart() ");
    }

    public final void registerForBroadcasts() {
        AnonymousClass2 anonymousClass2 = this.mReceiver;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.aircommandmanager.START_LOCAL_SOCKET");
            intentFilter.addAction("com.samsung.android.aircommandmanager.STOP_LOCAL_SOCKET");
            this.mContext.registerReceiver(anonymousClass2, intentFilter, 2);
            ((ArrayList) mReceiverList).add(anonymousClass2);
        } catch (Exception e) {
            Slog.e("PACMService", "Failed to add Broadcast");
            e.printStackTrace();
        }
    }
}
