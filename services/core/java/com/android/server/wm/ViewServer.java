package com.android.server.wm;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.util.Slog;
import com.android.server.wm.WindowManagerService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ViewServer implements Runnable {
    public final int mPort;
    public ServerSocket mServer;
    public Thread mThread;
    public ExecutorService mThreadPool;
    public final WindowManagerService mWindowManager;

    public final class ViewServerWorker implements Runnable, WindowManagerService.WindowChangeListener {
        public final Socket mClient;
        public boolean mNeedWindowListUpdate = false;
        public boolean mNeedFocusedWindowUpdate = false;

        public ViewServerWorker(Socket socket) {
            this.mClient = socket;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v12 */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14 */
        /* JADX WARN: Type inference failed for: r2v15 */
        /* JADX WARN: Type inference failed for: r2v16 */
        /* JADX WARN: Type inference failed for: r2v2, types: [java.io.BufferedReader] */
        /* JADX WARN: Type inference failed for: r2v3 */
        /* JADX WARN: Type inference failed for: r2v4, types: [java.io.BufferedReader] */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r2v6 */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r4v11, types: [java.lang.StringBuilder] */
        /* JADX WARN: Type inference failed for: r5v17, types: [com.android.server.wm.WindowManagerService] */
        /* JADX WARN: Type inference failed for: r7v0, types: [com.android.server.wm.ViewServer$ViewServerWorker] */
        /* JADX WARN: Type inference failed for: r7v1 */
        /* JADX WARN: Type inference failed for: r7v10 */
        /* JADX WARN: Type inference failed for: r7v11 */
        /* JADX WARN: Type inference failed for: r7v2, types: [com.android.server.wm.ViewServer$ViewServerWorker] */
        /* JADX WARN: Type inference failed for: r7v5, types: [java.io.IOException] */
        /* JADX WARN: Type inference failed for: r7v6, types: [java.net.Socket] */
        /* JADX WARN: Type inference failed for: r7v7 */
        /* JADX WARN: Type inference failed for: r7v8 */
        /* JADX WARN: Type inference failed for: r7v9, types: [java.net.Socket] */
        @Override // java.lang.Runnable
        public final void run() {
            String substring;
            ?? r2 = 0;
            r2 = 0;
            r2 = 0;
            try {
                try {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mClient.getInputStream()), 1024);
                        try {
                            String readLine = bufferedReader.readLine();
                            int indexOf = readLine.indexOf(32);
                            boolean z = true;
                            if (indexOf == -1) {
                                substring = "";
                                r2 = readLine;
                            } else {
                                String substring2 = readLine.substring(0, indexOf);
                                substring = readLine.substring(indexOf + 1);
                                r2 = substring2;
                            }
                            if ("PROTOCOL".equalsIgnoreCase(r2)) {
                                z = ViewServer.m1072$$Nest$smwriteValue(this.mClient);
                            } else if ("SERVER".equalsIgnoreCase(r2)) {
                                z = ViewServer.m1072$$Nest$smwriteValue(this.mClient);
                            } else if ("LIST".equalsIgnoreCase(r2)) {
                                z = ViewServer.this.mWindowManager.viewServerListWindows(this.mClient);
                            } else if ("GET_FOCUS".equalsIgnoreCase(r2)) {
                                z = ViewServer.this.mWindowManager.viewServerGetFocusedWindow(this.mClient);
                            } else if ("AUTOLIST".equalsIgnoreCase(r2)) {
                                windowManagerAutolistLoop();
                            } else {
                                z = ViewServer.this.mWindowManager.viewServerWindowCommand(this.mClient, r2, substring);
                            }
                            if (!z) {
                                Slog.w("WindowManager", "An error occurred with the command: " + r2);
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            this = this.mClient;
                        } catch (IOException e2) {
                            e = e2;
                            r2 = bufferedReader;
                            Slog.w("WindowManager", "Connection error: ", e);
                            if (r2 != 0) {
                                try {
                                    r2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            this = this.mClient;
                            if (this != 0) {
                                this.close();
                                r2 = r2;
                                this = this;
                            }
                        } catch (Throwable th) {
                            th = th;
                            r2 = bufferedReader;
                            if (r2 != 0) {
                                try {
                                    r2.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            Socket socket = this.mClient;
                            if (socket == null) {
                                throw th;
                            }
                            try {
                                socket.close();
                                throw th;
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (IOException e6) {
                        e = e6;
                    }
                    if (this != 0) {
                        this.close();
                        r2 = r2;
                        this = this;
                    }
                } catch (IOException e7) {
                    this = e7;
                    this.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public final void windowManagerAutolistLoop() {
            BufferedWriter bufferedWriter;
            Throwable th;
            ViewServer viewServer;
            boolean z;
            boolean z2;
            boolean z3;
            ViewServer.this.mWindowManager.addWindowChangeListener(this);
            BufferedWriter bufferedWriter2 = null;
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.mClient.getOutputStream()));
                while (!Thread.interrupted()) {
                    try {
                        synchronized (this) {
                            while (true) {
                                try {
                                    z = this.mNeedWindowListUpdate;
                                    if (z || this.mNeedFocusedWindowUpdate) {
                                        break;
                                    } else {
                                        wait();
                                    }
                                } finally {
                                }
                            }
                            z2 = true;
                            if (z) {
                                this.mNeedWindowListUpdate = false;
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            if (this.mNeedFocusedWindowUpdate) {
                                this.mNeedFocusedWindowUpdate = false;
                            } else {
                                z2 = false;
                            }
                        }
                        if (z3) {
                            bufferedWriter.write("LIST UPDATE\n");
                            bufferedWriter.flush();
                        }
                        if (z2) {
                            bufferedWriter.write("ACTION_FOCUS UPDATE\n");
                            bufferedWriter.flush();
                        }
                    } catch (Exception unused) {
                        bufferedWriter2 = bufferedWriter;
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                            } catch (IOException unused2) {
                            }
                        }
                        viewServer = ViewServer.this;
                        viewServer.mWindowManager.removeWindowChangeListener(this);
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException unused3) {
                            }
                        }
                        ViewServer.this.mWindowManager.removeWindowChangeListener(this);
                        throw th;
                    }
                }
                try {
                    bufferedWriter.close();
                } catch (IOException unused4) {
                }
                viewServer = ViewServer.this;
            } catch (Exception unused5) {
            } catch (Throwable th3) {
                bufferedWriter = null;
                th = th3;
            }
            viewServer.mWindowManager.removeWindowChangeListener(this);
        }
    }

    /* renamed from: -$$Nest$smwriteValue, reason: not valid java name */
    public static boolean m1072$$Nest$smwriteValue(Socket socket) {
        boolean z = false;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
                try {
                    bufferedWriter2.write("4");
                    bufferedWriter2.write("\n");
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    z = true;
                } catch (Exception unused) {
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    return z;
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
        return z;
    }

    public ViewServer(WindowManagerService windowManagerService, int i) {
        this.mWindowManager = windowManagerService;
        this.mPort = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        while (Thread.currentThread() == this.mThread) {
            try {
                Socket accept = this.mServer.accept();
                ExecutorService executorService = this.mThreadPool;
                if (executorService != null) {
                    executorService.submit(new ViewServerWorker(accept));
                } else {
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                Slog.w("WindowManager", "Connection error: ", e2);
            }
        }
    }

    public final boolean start() {
        if (this.mThread != null) {
            return false;
        }
        this.mServer = new ServerSocket(this.mPort, 10, InetAddress.getLocalHost());
        this.mThread = new Thread(this, AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPort, new StringBuilder("Remote View Server [port="), "]"));
        this.mThreadPool = Executors.newFixedThreadPool(10);
        this.mThread.start();
        return true;
    }
}
