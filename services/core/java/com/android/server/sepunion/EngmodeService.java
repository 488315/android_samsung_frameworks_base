package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Slog;
import com.samsung.android.sepunion.IEngmodeService;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EngmodeService extends IEngmodeService.Stub implements AbsSemSystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public EngineeringModeManager mManager;
    public AnonymousClass3 mNetworkCallback;
    public TimerTask mRunTimeTask;
    public Thread mTimeThread;
    public Timer mTimerObserve;
    public Timer mUpdateTimer;
    public boolean mNeedChangeFlag = false;
    public final AnonymousClass2 mBootCompleteReceiver = new BroadcastReceiver() { // from class: com.android.server.sepunion.EngmodeService.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        Slog.i("engmode_service", "BootCompleted");
                        if (EngmodeService.m863$$Nest$mregisterNetworkCallback(EngmodeService.this)) {
                            return;
                        }
                        EngmodeService.m863$$Nest$mregisterNetworkCallback(EngmodeService.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public boolean mNeedUpdate = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EMSConnectionTask {
        public static String SERVER_URI = "https://kwb.secmobilesvc.com:7788/requestEmrToken.kwb";

        /* renamed from: -$$Nest$mpost, reason: not valid java name */
        public static byte[] m864$$Nest$mpost(EMSConnectionTask eMSConnectionTask, byte[] bArr, int i) {
            MultipartUtility multipartUtility;
            if (i == 1) {
                SERVER_URI = "https://kwb.secmobilesvc.com:7788/requestapi/trq/5/token.kwb";
            } else if (i == 2) {
                SERVER_URI = "https://kwb.secmobilesvc.com:7788/requestapi/tak/5/token.kwb";
            }
            try {
                multipartUtility = new MultipartUtility(SERVER_URI, null);
            } catch (IOException e) {
                e = e;
                multipartUtility = null;
            }
            try {
                multipartUtility.addByteArrayPart(bArr);
                ArrayList finish = multipartUtility.finish();
                byte[] bArr2 = new byte[finish.size()];
                for (int i2 = 0; i2 < finish.size(); i2++) {
                    bArr2[i2] = ((Byte) finish.get(i2)).byteValue();
                }
                return bArr2;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                if (multipartUtility == null) {
                    return null;
                }
                try {
                    multipartUtility.writer.close();
                    multipartUtility.outputStream.close();
                    return null;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return null;
                }
            }
        }

        public EMSConnectionTask(EngmodeService engmodeService) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EngmodeTimeThread implements Runnable {
        public static final int MAINLINE_API_LEVEL = Integer.parseInt("34");

        public EngmodeTimeThread() {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x00b3 A[Catch: Exception -> 0x007a, TryCatch #0 {Exception -> 0x007a, blocks: (B:12:0x0040, B:14:0x0072, B:17:0x007d, B:19:0x0083, B:21:0x008c, B:22:0x00a8, B:24:0x00b3, B:26:0x00ba, B:28:0x00d4, B:30:0x00dc, B:33:0x0100, B:38:0x0109, B:39:0x017f, B:41:0x0188, B:44:0x010e, B:46:0x0116, B:47:0x011f, B:48:0x011a, B:49:0x0128, B:50:0x012d, B:52:0x0133, B:61:0x0142, B:62:0x0146, B:64:0x014a, B:69:0x0165, B:70:0x0176, B:71:0x017b), top: B:11:0x0040 }] */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00ba A[Catch: Exception -> 0x007a, TryCatch #0 {Exception -> 0x007a, blocks: (B:12:0x0040, B:14:0x0072, B:17:0x007d, B:19:0x0083, B:21:0x008c, B:22:0x00a8, B:24:0x00b3, B:26:0x00ba, B:28:0x00d4, B:30:0x00dc, B:33:0x0100, B:38:0x0109, B:39:0x017f, B:41:0x0188, B:44:0x010e, B:46:0x0116, B:47:0x011f, B:48:0x011a, B:49:0x0128, B:50:0x012d, B:52:0x0133, B:61:0x0142, B:62:0x0146, B:64:0x014a, B:69:0x0165, B:70:0x0176, B:71:0x017b), top: B:11:0x0040 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 464
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.EngmodeService.EngmodeTimeThread.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultipartUtility {
        public final String boundary;
        public final HttpURLConnection httpConn;
        public final OutputStream outputStream;
        public final PrintWriter writer;

        public MultipartUtility(String str, String str2) throws IOException {
            String str3 = "===" + System.currentTimeMillis() + "===";
            this.boundary = str3;
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            this.httpConn = httpURLConnection;
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + str3);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            this.outputStream = outputStream;
            this.writer = new PrintWriter((Writer) new OutputStreamWriter(outputStream, str2 == null ? "ISO-8859-1" : str2), true);
        }

        public final void addByteArrayPart(byte[] bArr) {
            this.writer.append((CharSequence) ("--" + this.boundary)).append((CharSequence) "\r\n");
            this.writer.append((CharSequence) "Content-Disposition: form-data; name=\"tokenreq\"; filename=\"tokenreq\"").append((CharSequence) "\r\n");
            this.writer.append((CharSequence) "Content-Type: multipart/form-data").append((CharSequence) "\r\n");
            this.writer.append((CharSequence) "Content-Transfer-Encoding: binary").append((CharSequence) "\r\n");
            this.writer.append((CharSequence) "\r\n");
            this.writer.flush();
            this.outputStream.write(bArr);
            this.outputStream.flush();
            this.writer.append((CharSequence) "\r\n");
            this.writer.flush();
        }

        public final ArrayList finish() {
            this.writer.append((CharSequence) "\r\n").flush();
            this.writer.append((CharSequence) ("--" + this.boundary + "--")).append((CharSequence) "\r\n");
            this.writer.close();
            this.outputStream.close();
            int responseCode = this.httpConn.getResponseCode();
            if (responseCode != 200) {
                throw new IOException(VibrationParam$1$$ExternalSyntheticOutline0.m(responseCode, "Server returned non-OK status: "));
            }
            ArrayList arrayList = new ArrayList();
            InputStream inputStream = null;
            try {
                inputStream = this.httpConn.getInputStream();
                while (true) {
                    int read = inputStream.read();
                    if (read == -1) {
                        break;
                    }
                    arrayList.add(Byte.valueOf((byte) read));
                }
                inputStream.close();
                this.httpConn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return arrayList;
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.sepunion.EngmodeService$3] */
    /* renamed from: -$$Nest$mregisterNetworkCallback, reason: not valid java name */
    public static boolean m863$$Nest$mregisterNetworkCallback(EngmodeService engmodeService) {
        engmodeService.getClass();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) engmodeService.mContext.getSystemService("connectivity");
            NetworkRequest.Builder addCapability = new NetworkRequest.Builder().addCapability(12).addCapability(16);
            engmodeService.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.sepunion.EngmodeService.3
                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onAvailable(Network network) {
                    Slog.i("engmode_service", "Network is available");
                    try {
                        EngmodeService engmodeService2 = EngmodeService.this;
                        if (engmodeService2.mTimerObserve == null) {
                            engmodeService2.mTimerObserve = new Timer();
                            final EngmodeService engmodeService3 = EngmodeService.this;
                            engmodeService3.getClass();
                            engmodeService3.mRunTimeTask = new TimerTask() { // from class: com.android.server.sepunion.EngmodeService.1
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public final void run() {
                                    try {
                                        Thread thread = EngmodeService.this.mTimeThread;
                                        if (thread != null && thread.isAlive()) {
                                            Slog.i("engmode_service", "Time thread already running, pass");
                                            return;
                                        }
                                        EngmodeService.this.mTimeThread = new Thread(EngmodeService.this.new EngmodeTimeThread());
                                        EngmodeService.this.mTimeThread.start();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            EngmodeService engmodeService4 = EngmodeService.this;
                            engmodeService4.mTimerObserve.schedule(engmodeService4.mRunTimeTask, 0L, 21600000L);
                            return;
                        }
                        if (!engmodeService2.mNeedUpdate) {
                            Slog.i("engmode_service", "There is no need for update");
                            return;
                        }
                        if (engmodeService2.mTimeThread.isAlive()) {
                            Slog.i("engmode_service", "Previous thread is alive, skip this time");
                            return;
                        }
                        EngmodeService.this.mRunTimeTask.cancel();
                        final EngmodeService engmodeService5 = EngmodeService.this;
                        engmodeService5.getClass();
                        engmodeService5.mRunTimeTask = new TimerTask() { // from class: com.android.server.sepunion.EngmodeService.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                try {
                                    Thread thread = EngmodeService.this.mTimeThread;
                                    if (thread != null && thread.isAlive()) {
                                        Slog.i("engmode_service", "Time thread already running, pass");
                                        return;
                                    }
                                    EngmodeService.this.mTimeThread = new Thread(EngmodeService.this.new EngmodeTimeThread());
                                    EngmodeService.this.mTimeThread.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        EngmodeService.this.mTimerObserve.cancel();
                        EngmodeService.this.mTimerObserve = new Timer();
                        EngmodeService engmodeService6 = EngmodeService.this;
                        engmodeService6.mTimerObserve.schedule(engmodeService6.mRunTimeTask, 0L, 21600000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    Slog.i("engmode_service", "Network isn't available");
                }
            };
            connectivityManager.registerNetworkCallback(addCapability.build(), engmodeService.mNetworkCallback);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.EngmodeService$2] */
    public EngmodeService(Context context) {
        this.mContext = context;
        this.mManager = new EngineeringModeManager(context);
        Slog.i("engmode_service", "EM Service is started");
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### EngmodeService #####\n##### (dumpsys sepunion EngmodeService) #####\n");
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            try {
                Slog.d("engmode_service", "onBootPhase() boot completed");
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                this.mContext.registerReceiver(this.mBootCompleteReceiver, intentFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void onCleanupUser(int i) {
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {
    }

    public final void onDestroy() {
    }

    public final void onStart() {
    }

    public final void onStartUser(int i) {
    }

    public final void onStopUser(int i) {
    }

    public final void onSwitchUser(int i) {
    }

    public final void onUnlockUser(int i) {
    }
}
