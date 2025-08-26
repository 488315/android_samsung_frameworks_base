package com.android.server;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Build;
import android.os.UEventObserver;
import android.system.ErrnoException;
import android.util.Slog;
import com.samsung.android.service.sats.ISatsService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public final class SatsService extends ISatsService.Stub {
    private static final String ACTION_EM_AT_ACTIVATION_REQUEST = "com.sec.atd.em_at_activation_request";
    private static final String ACTION_EM_AT_REQUEST_RECONNECT = "com.sec.atd.em_at_request_reconnect";
    private static final String ACTION_FACM_REQUEST_FTCLIENT_START = "com.sec.factory.entry.REQUEST_FTCLIENT_START";
    private static final String ACTION_HMT_REQUEST_RECONNECT = "com.sec.hmt.request_reconnect";
    private static final int CONNECT_AT_DISTRIBUTOR = 0;
    private static final int CONNECT_DATA_DISTRIBUTOR = 1;
    private static final String JIG_STATE = "SWITCH_NAME";
    public static final int SATS_EXCEPTION_ERROR = -7;
    public static final int SATS_FLAG_NOT_EXISTS = -2;
    public static final int SATS_NO_ERROR = 0;
    public static final int SATS_RETURN_INVALID_ARGUMENTS = -5;
    public static final int SATS_RETURN_NATIVE_ERROR = -1;
    public static final int SATS_RETURN_PERMISSION_DENIED = -4;
    public static final int SATS_SERVICE_NOT_AVAILABLE = -6;
    public static final int SATS_SERVICE_NOT_SUPPORTED = 0;
    public static final int SATS_SERVICE_SUPPORTED = 1;
    public static final int SATS_STRING_NOT_EXISTS = -3;
    private static final String TAG = "SatsService";
    private static Context mContext;
    private static final Object mLockUEvent = new Object();
    private EngModesCmdHelper mEmCmdHelper;
    private int mErrorCode;
    private final BroadcastReceiver mReceiver;
    private Thread mThreadUart;
    private Thread mThreadUsb;
    private final UEventObserver mUEventObserver;
    private boolean mThreadUartGoWait = true;
    private ArrayList<IWorkOnAt> serviceInterfaces = new ArrayList<>();
    private ArrayList<String> cmdList = new ArrayList<>();
    private IWorkOnAt mDrkAtCommander = null;
    private IWorkOnAt mHermesAtCommander = null;

    public native byte[] commandForESS(Context context, String str);

    public SatsService(Context context) {
        this.mEmCmdHelper = null;
        UEventObserver uEventObserver = new UEventObserver() { // from class: com.android.server.SatsService.1
            @Override // android.os.UEventObserver
            public void onUEvent(UEventObserver.UEvent uEvent) {
                synchronized (SatsService.mLockUEvent) {
                    if (uEvent.toString().indexOf(SatsService.JIG_STATE) != -1) {
                        try {
                            if ("uart3".equalsIgnoreCase(uEvent.get(SatsService.JIG_STATE))) {
                                int i = Integer.parseInt(uEvent.get("SWITCH_STATE"));
                                if (i == 0) {
                                    Slog.i(SatsService.TAG, "SATServiceAt will wait.");
                                    SatsService.this.mThreadUartGoWait = true;
                                } else if (i == 1) {
                                    Slog.i(SatsService.TAG, "SATServiceAt will wake up.");
                                    SatsService.this.mThreadUartGoWait = false;
                                    synchronized (SatsService.this.mThreadUart) {
                                        SatsService.this.mThreadUart.notifyAll();
                                    }
                                } else {
                                    Slog.e(SatsService.TAG, "Unknown state[" + i + NavigationBarInflaterView.SIZE_MOD_END);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        this.mUEventObserver = uEventObserver;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.SatsService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) throws InterruptedException {
                String action = intent.getAction();
                Slog.i(SatsService.TAG, "Broadcast received:" + action);
                try {
                    if (SatsService.ACTION_EM_AT_REQUEST_RECONNECT.equals(action) || SatsService.ACTION_EM_AT_ACTIVATION_REQUEST.equals(action) || SatsService.ACTION_HMT_REQUEST_RECONNECT.equals(action) || SatsService.ACTION_FACM_REQUEST_FTCLIENT_START.equals(action)) {
                        Slog.i(SatsService.TAG, "onReceive:" + action);
                        Slog.i(SatsService.TAG, "SATServiceAt will wake up through received intent...");
                        Thread.sleep(500L);
                        SatsService.this.mThreadUartGoWait = false;
                        synchronized (SatsService.this.mThreadUart) {
                            SatsService.this.mThreadUart.notifyAll();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mErrorCode = 0;
        setContext(context);
        try {
            this.serviceInterfaces.add(new AuthUnlockATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+FRPUNLCK");
            this.serviceInterfaces.add(new HdcptestATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+HDCPTEST");
            this.serviceInterfaces.add(new SamsungAttestationATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+DEVROOTK");
            this.serviceInterfaces.add(new HermesATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+ISOSECHW");
            this.serviceInterfaces.add(new QRNGATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+QRNGTEST");
            this.serviceInterfaces.add(new AutoBlockATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+ABSTACHK");
            this.serviceInterfaces.add(new UserDeviceATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+URDEVICE");
            this.mEmCmdHelper = new EngModesCmdHelper();
            this.cmdList.add("AT+ENGMODES");
            this.serviceInterfaces.add(new CassATCmd(context.getApplicationContext()));
            this.cmdList.add("AT+MGRTCASS");
            this.mThreadUart = new Thread(new AtCmdHandler(0), "SATServiceAt");
            this.mThreadUsb = new Thread(new AtCmdHandler(1), "SATServiceData");
            this.mThreadUart.start();
            this.mThreadUsb.start();
            uEventObserver.startObserving(JIG_STATE);
            registerForBroadcasts();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        System.loadLibrary(".engmodejni.samsung");
    }

    private static void setContext(Context context) {
        mContext = context;
    }

    private void registerForBroadcasts() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_EM_AT_REQUEST_RECONNECT);
        intentFilter.addAction(ACTION_EM_AT_ACTIVATION_REQUEST);
        intentFilter.addAction(ACTION_HMT_REQUEST_RECONNECT);
        intentFilter.addAction(ACTION_FACM_REQUEST_FTCLIENT_START);
        mContext.registerReceiver(this.mReceiver, intentFilter, 2);
    }

    public final class AtCmdHandler implements Runnable {
        private static final String AT_COMMAND_HEADER = "AT";
        private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
        private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID PARAM)";
        private static final String AT_RESPONSE_NA = "NA";
        private static final String AT_RESPONSE_START = "\r\n";
        private static final String THREAD_SUFFIX_UART = "At";
        private static final String THREAD_SUFFIX_USB = "Data";
        private static final String UART_SOCKET_NAME = "SatsService";
        private static final String USB_SOCKET_NAME = "/data/misc/.socket_stream";
        private String THREAD_TAG;
        private LocalSocketAddress mLocalSocketAddress = null;
        private boolean mGettedBuffer = false;
        private LocalSocket mLocalSocket = new LocalSocket(2);

        public AtCmdHandler(int i) {
            Slog.i(UART_SOCKET_NAME, "AtCmdHandler called.");
            selectTarget(i);
        }

        public void selectTarget(int i) {
            if (i == 0) {
                Slog.i(UART_SOCKET_NAME, "connect at distributor");
                this.mLocalSocketAddress = new LocalSocketAddress(UART_SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT);
                this.THREAD_TAG = "SatsServiceAt";
            } else if (i == 1) {
                Slog.i(UART_SOCKET_NAME, "connect data distributor");
                this.mLocalSocketAddress = new LocalSocketAddress(USB_SOCKET_NAME, LocalSocketAddress.Namespace.FILESYSTEM);
                this.THREAD_TAG = "SatsServiceData";
            } else {
                Slog.e(UART_SOCKET_NAME, "Invalid target : [" + i + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:64:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws InterruptedException, IOException, ErrnoException {
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            String line;
            String strDoWork;
            while (true) {
                if (this.mLocalSocket == null) {
                    this.mLocalSocket = new LocalSocket(2);
                }
                try {
                    this.mLocalSocket.connect(this.mLocalSocketAddress);
                } catch (Exception e) {
                    Slog.e(this.THREAD_TAG, "Failed to connect daemon - " + e);
                }
                if (this.mLocalSocket.isConnected()) {
                    Slog.i(this.THREAD_TAG, "Connected to daemon.");
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(this.mLocalSocket.getInputStream(), "UTF-8"));
                        try {
                            bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.mLocalSocket.getOutputStream(), "UTF-8"));
                        } catch (Exception e2) {
                            e = e2;
                            bufferedWriter = null;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedReader = null;
                        bufferedWriter = null;
                    }
                    try {
                        this.mGettedBuffer = true;
                    } catch (Exception e4) {
                        e = e4;
                        Slog.e(this.THREAD_TAG, "Failed to get input/output stream - " + e);
                        this.mGettedBuffer = false;
                        while (true) {
                            if (!this.mGettedBuffer) {
                                break;
                            }
                        }
                    }
                    while (true) {
                        if (!this.mGettedBuffer) {
                            try {
                                line = bufferedReader.readLine();
                            } catch (Exception e5) {
                                Slog.e(this.THREAD_TAG, "Socket seems be closed - " + e5);
                                this.mGettedBuffer = false;
                                SatsService.this.mThreadUartGoWait = true;
                                closeInputStream(this.mLocalSocket);
                                closeOutputStream(this.mLocalSocket);
                                closeLocalSocket(this.mLocalSocket);
                                this.mLocalSocket = null;
                            }
                            if (line != null) {
                                if (isValidCommand(line)) {
                                    Slog.i(this.THREAD_TAG, "command_0: " + line);
                                    if (line.contains("AT+ENGMODES")) {
                                        strDoWork = executeEmAtCommand(line);
                                    } else {
                                        strDoWork = doWork(line);
                                    }
                                    bufferedWriter.write(strDoWork);
                                    bufferedWriter.flush();
                                    Slog.i(this.THREAD_TAG, "command_1:" + line + " Response:" + strDoWork);
                                } else if (!line.equals("") && this.THREAD_TAG.equals("SatsServiceData")) {
                                    bufferedWriter.write("NA");
                                    bufferedWriter.flush();
                                    Slog.i(this.THREAD_TAG, "Command:" + line + " Response:NA");
                                }
                            } else {
                                Slog.e(this.THREAD_TAG, "Socket seems be closed.");
                                this.mGettedBuffer = false;
                                SatsService.this.mThreadUartGoWait = true;
                                closeInputStream(this.mLocalSocket);
                                closeOutputStream(this.mLocalSocket);
                                closeLocalSocket(this.mLocalSocket);
                                this.mLocalSocket = null;
                                break;
                            }
                        }
                    }
                } else {
                    try {
                        Thread.sleep(5000L);
                        if (this.THREAD_TAG.equals("SatsServiceAt")) {
                            Slog.i(this.THREAD_TAG, "Wait until JIG is inserted or ATD Activation intent");
                            synchronized (SatsService.this.mThreadUart) {
                                while (SatsService.this.mThreadUartGoWait) {
                                    SatsService.this.mThreadUart.wait();
                                }
                            }
                        } else {
                            continue;
                        }
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
            }
        }

        private void closeLocalSocket(LocalSocket localSocket) {
            if (localSocket != null) {
                try {
                    localSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeInputStream(LocalSocket localSocket) throws ErrnoException {
            try {
                localSocket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void closeOutputStream(LocalSocket localSocket) throws ErrnoException {
            try {
                localSocket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean isValidCommand(String str) {
            if (str == null) {
                Slog.e(this.THREAD_TAG, "error: cmd = null");
                return false;
            }
            Slog.i(this.THREAD_TAG, "isValidCommand: cmd is [" + str + NavigationBarInflaterView.SIZE_MOD_END);
            try {
                if (str.contains("=") && str.indexOf("=") > 2) {
                    Iterator it = SatsService.this.cmdList.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        if (str.substring(0, str.indexOf("=")).equals(str2)) {
                            Slog.i(this.THREAD_TAG, "isValidCommand: cmd is true. " + str2);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        private String executeEmAtCommand(String str) {
            String[] strArr = {"8,0,0", "8,0,1", "8,0,2", "8,0,3", "7,0,1,0,0", "7,1,0,0,0", "7,1,1,0,0", "7,2,0,0,0", "7,2,1,0,0", "7,1,1,1,0", "7,0,0,1,0", "7,0,0,2,0", "7,0,0,3,0", "7,0,0,4,0", "7,0,0,5,0", "7,0,0,0,1"};
            String str2 = (this.THREAD_TAG.equals("SatsServiceData") ? "" + str.trim() : "") + AT_RESPONSE_START;
            String strSubstring = str.substring(str.indexOf("=") + 1, str.length());
            for (int i = 0; i < 16; i++) {
                String str3 = strArr[i];
                if (strSubstring.equals(str3)) {
                    Slog.i(this.THREAD_TAG, "executeEmAtCommand: test command(" + str3 + NavigationBarInflaterView.KEY_CODE_END);
                    return (((str2 + "+ENGMODES:") + str.substring(str.indexOf("=") + 1, str.indexOf("=") + 2)) + ",OK") + AT_RESPONSE_END;
                }
            }
            byte[] bArrProcessCmd = SatsService.this.mEmCmdHelper.processCmd(SatsService.mContext, strSubstring);
            if (bArrProcessCmd == null) {
                return (((str2 + "+ENGMODES:") + str.substring(str.indexOf("=") + 1, str.indexOf("=") + 2)) + ",NG,FFFFFFFF") + AT_RESPONSE_END;
            }
            return str2 + new String(bArrProcessCmd, StandardCharsets.UTF_8);
        }

        private String doWork(String str) {
            StringBuilder sb = new StringBuilder();
            Iterator it = SatsService.this.serviceInterfaces.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Slog.i(UART_SOCKET_NAME, "doWork: iterator: ");
                IWorkOnAt iWorkOnAt = (IWorkOnAt) it.next();
                Slog.i(UART_SOCKET_NAME, "doWork: cmd " + iWorkOnAt.getCmd());
                if (str.substring(str.indexOf("+") + 1, str.indexOf("=")).equals(iWorkOnAt.getCmd())) {
                    if (this.THREAD_TAG.equals("SatsServiceData")) {
                        sb.append(str.trim());
                    }
                    sb.append(AT_RESPONSE_START);
                    try {
                        sb.append(str.substring(str.indexOf("+"), str.indexOf("=")));
                    } catch (Exception e) {
                        e.printStackTrace();
                        sb.append("NG (INVALID PARAM)\r\n\r\nOK\r\n");
                    }
                    Slog.i(UART_SOCKET_NAME, "doWork: cmdResponse is equal to cmd.");
                    sb.append(":");
                    sb.append(iWorkOnAt.processCmd(str.substring(str.indexOf("=") + 1, str.length())));
                    sb.append(AT_RESPONSE_END);
                } else {
                    Slog.i(UART_SOCKET_NAME, "doWork: cmd not in list");
                }
            }
            if (sb.toString().isEmpty()) {
                Slog.i(UART_SOCKET_NAME, "doWork: no such service");
                sb.append("NG (INVALID PARAM)\r\n\r\nOK\r\n");
            }
            return sb.toString();
        }
    }

    @Override // com.samsung.android.service.sats.ISatsService
    public String executePseudoDrkAtCommnd(String str) {
        String str2;
        if (!"eng".equals(Build.TYPE)) {
            Slog.e(TAG, "It is only supported on eng binary.");
            return null;
        }
        try {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            String str3 = "";
            ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
            if (activityManager.getRunningAppProcesses() != null) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = activityManager.getRunningAppProcesses().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningAppProcessInfo next = it.next();
                    if (next.pid == callingPid) {
                        str3 = next.processName;
                        break;
                    }
                }
            }
            if ("system".equals(str3) && callingUid == 1000) {
                if (this.mDrkAtCommander == null) {
                    this.mDrkAtCommander = new SamsungAttestationATCmd(mContext.getApplicationContext());
                }
                if (this.mHermesAtCommander == null) {
                    this.mHermesAtCommander = new HermesATCmd(mContext.getApplicationContext());
                }
                if (str.substring(0, str.indexOf("=")).equals("AT+" + this.mDrkAtCommander.getCmd())) {
                    str2 = "\r\n" + str.substring(str.indexOf("+"), str.indexOf("=")) + ":" + this.mDrkAtCommander.processCmd(str.substring(str.indexOf("=") + 1, str.length()));
                } else {
                    if (str.substring(0, str.indexOf("=")).equals("AT+" + this.mHermesAtCommander.getCmd())) {
                        str2 = "\r\n" + str.substring(str.indexOf("+"), str.indexOf("=")) + ":" + this.mHermesAtCommander.processCmd(str.substring(str.indexOf("=") + 1, str.length()));
                    } else {
                        Slog.e(TAG, "Invalid command : " + str);
                        str2 = "\r\nNG (INVALID PARAM)";
                    }
                }
                return str2 + "\r\n\r\nOK\r\n";
            }
            Slog.e(TAG, "Permission denied : Name = [" + str3 + "], UID = [" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
            return "NG (Permission Denied)";
        } catch (Exception e) {
            Slog.e(TAG, "Failed to excute Pseudo DRK AT command : " + str);
            e.printStackTrace();
            return ("\r\nNG (Exception OCCURS)") + "\r\n\r\nOK\r\n";
        }
    }

    private final class EngModesCmdHelper {
        private static final int AT_CMD_EM_SEQ_NO = 3;
        private static final String AT_CMD_EM_WRITING_END = "FFF";
        private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
        private static final String AT_RESPONSE_EXCEPION = "NG,EXCEPTION";
        private static final String AT_RESPONSE_MISSED_DATA = "NG,DATA MISSED";
        private static final String AT_RESPONSE_OK = "OK";
        private final String[] lCmdParams = {"0,5,"};
        private int mCmdCounter;
        private String mCmdData;

        public EngModesCmdHelper() {
            Slog.i(SatsService.TAG, "Initialized");
            init();
        }

        public byte[] processCmd(Context context, String str) {
            try {
                for (String str2 : this.lCmdParams) {
                    int length = str2.length();
                    if (str.length() >= length && str2.equals(str.substring(0, length))) {
                        return proceedlCmd(context, str, length);
                    }
                }
                return SatsService.this.commandForESS(context, str);
            } catch (RuntimeException e) {
                Slog.e(SatsService.TAG, "Failed to excute ENGMODES AT command : " + str);
                e.printStackTrace();
                init();
                return ("+ENGMODES:" + str.substring(0, 1) + ",NG,EXCEPTION\r\n\r\nOK\r\n").getBytes(StandardCharsets.UTF_8);
            }
        }

        private byte[] proceedlCmd(Context context, String str, int i) throws NumberFormatException {
            int dataIndex;
            int i2 = i + 3;
            String strSubstring = str.substring(i, i2);
            if (strSubstring.equals("FFF")) {
                dataIndex = getDataIndex() + 1;
            } else {
                dataIndex = Integer.parseInt(strSubstring);
            }
            if (!appendData(dataIndex, str.substring(i2, str.length()).trim())) {
                Slog.e(SatsService.TAG, "Failed to append command SN-" + dataIndex + " EN-" + (getDataIndex() + 1));
                String str2 = "+ENGMODES:" + str.substring(0, 1) + ",NG,DATA MISSED SN-" + dataIndex + " EN-" + (getDataIndex() + 1) + AT_RESPONSE_END;
                init();
                return str2.getBytes(StandardCharsets.UTF_8);
            }
            if (strSubstring.equals("FFF")) {
                String strSubstring2 = str.substring(0, i);
                if (this.lCmdParams[0].equals(strSubstring2)) {
                    strSubstring2 = "0,2,";
                }
                byte[] bArrCommandForESS = SatsService.this.commandForESS(context, strSubstring2 + getTotalData());
                init();
                return bArrCommandForESS;
            }
            return ("+ENGMODES:" + str.substring(0, 1) + ",OK\r\n\r\nOK\r\n").getBytes(StandardCharsets.UTF_8);
        }

        public boolean appendData(int i, String str) {
            if (i == 1) {
                this.mCmdCounter = i;
                this.mCmdData = str;
                return true;
            }
            if (i != this.mCmdCounter + 1) {
                return false;
            }
            this.mCmdCounter = i;
            this.mCmdData += str;
            return true;
        }

        private String getTotalData() {
            return this.mCmdData;
        }

        private int getDataIndex() {
            return this.mCmdCounter;
        }

        private void init() {
            this.mCmdCounter = 0;
            this.mCmdData = "";
        }
    }
}
