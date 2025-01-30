package com.android.server;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.p009os.Binder;
import android.p009os.Build;
import android.p009os.UEventObserver;
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

/* loaded from: classes5.dex */
public final class SatsService extends ISatsService.Stub {
  private static final String ACTION_EM_AT_ACTIVATION_REQUEST =
      "com.sec.atd.em_at_activation_request";
  private static final String ACTION_EM_AT_REQUEST_RECONNECT =
      "com.sec.atd.em_at_request_reconnect";
  private static final String ACTION_FACM_REQUEST_FTCLIENT_START =
      "com.sec.factory.entry.REQUEST_FTCLIENT_START";
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
    UEventObserver uEventObserver =
        new UEventObserver() { // from class: com.android.server.SatsService.1
          @Override // android.p009os.UEventObserver
          public void onUEvent(UEventObserver.UEvent event) {
            synchronized (SatsService.mLockUEvent) {
              if (event.toString().indexOf(SatsService.JIG_STATE) != -1) {
                try {
                  String switchName = event.get(SatsService.JIG_STATE);
                  if ("uart3".equalsIgnoreCase(switchName)) {
                    String switchState = event.get("SWITCH_STATE");
                    int state = Integer.parseInt(switchState);
                    switch (state) {
                      case 0:
                        Slog.m117i(SatsService.TAG, "SATServiceAt will wait.");
                        SatsService.this.mThreadUartGoWait = true;
                        break;
                      case 1:
                        Slog.m117i(SatsService.TAG, "SATServiceAt will wake up.");
                        SatsService.this.mThreadUartGoWait = false;
                        synchronized (SatsService.this.mThreadUart) {
                          SatsService.this.mThreadUart.notifyAll();
                        }
                        break;
                      default:
                        Slog.m115e(
                            SatsService.TAG,
                            "Unknown state[" + state + NavigationBarInflaterView.SIZE_MOD_END);
                        break;
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
    this.mReceiver =
        new BroadcastReceiver() { // from class: com.android.server.SatsService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            Slog.m117i(SatsService.TAG, "Broadcast received:" + action);
            try {
              if (SatsService.ACTION_EM_AT_REQUEST_RECONNECT.equals(action)
                  || SatsService.ACTION_EM_AT_ACTIVATION_REQUEST.equals(action)
                  || SatsService.ACTION_HMT_REQUEST_RECONNECT.equals(action)
                  || SatsService.ACTION_FACM_REQUEST_FTCLIENT_START.equals(action)) {
                Slog.m117i(SatsService.TAG, "onReceive:" + action);
                Slog.m117i(SatsService.TAG, "SATServiceAt will wake up through received intent...");
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
      this.serviceInterfaces.add(new ReactiveATCmd(context.getApplicationContext()));
      this.cmdList.add("AT+REACTIVE");
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
      this.serviceInterfaces.add(new UserDeviceATCmd(context.getApplicationContext()));
      this.cmdList.add("AT+URDEVICE");
      this.serviceInterfaces.add(new AutoBlockATCmd(context.getApplicationContext()));
      this.cmdList.add("AT+ABSTACHK");
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
    } catch (Throwable e) {
      e.printStackTrace();
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
    mContext.registerReceiver(this.mReceiver, intentFilter);
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

    public AtCmdHandler(int connectTarget) {
      Slog.m117i(UART_SOCKET_NAME, "AtCmdHandler called.");
      selectTarget(connectTarget);
    }

    public void selectTarget(int connectTarget) {
      switch (connectTarget) {
        case 0:
          Slog.m117i(UART_SOCKET_NAME, "connect at distributor");
          this.mLocalSocketAddress =
              new LocalSocketAddress(UART_SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT);
          this.THREAD_TAG = "SatsServiceAt";
          break;
        case 1:
          Slog.m117i(UART_SOCKET_NAME, "connect data distributor");
          this.mLocalSocketAddress =
              new LocalSocketAddress(USB_SOCKET_NAME, LocalSocketAddress.Namespace.FILESYSTEM);
          this.THREAD_TAG = "SatsServiceData";
          break;
        default:
          Slog.m115e(
              UART_SOCKET_NAME,
              "Invalid target : [" + connectTarget + NavigationBarInflaterView.SIZE_MOD_END);
          break;
      }
    }

    @Override // java.lang.Runnable
    public void run() {
      String strIncome;
      String response;
      while (true) {
        if (this.mLocalSocket == null) {
          this.mLocalSocket = new LocalSocket(2);
        }
        try {
          this.mLocalSocket.connect(this.mLocalSocketAddress);
        } catch (Exception e) {
          Slog.m115e(this.THREAD_TAG, "Failed to connect daemon - " + e);
        }
        if (this.mLocalSocket.isConnected()) {
          Slog.m117i(this.THREAD_TAG, "Connected to daemon.");
          BufferedReader br = null;
          BufferedWriter bw = null;
          try {
            br =
                new BufferedReader(
                    new InputStreamReader(this.mLocalSocket.getInputStream(), "UTF-8"));
            bw =
                new BufferedWriter(
                    new OutputStreamWriter(this.mLocalSocket.getOutputStream(), "UTF-8"));
            this.mGettedBuffer = true;
          } catch (Exception e2) {
            Slog.m115e(this.THREAD_TAG, "Failed to get input/output stream - " + e2);
            this.mGettedBuffer = false;
          }
          while (true) {
            if (this.mGettedBuffer) {
              try {
                strIncome = br.readLine();
              } catch (Exception e3) {
                Slog.m115e(this.THREAD_TAG, "Socket seems be closed - " + e3);
                this.mGettedBuffer = false;
                SatsService.this.mThreadUartGoWait = true;
                closeInputStream(this.mLocalSocket);
                closeOutputStream(this.mLocalSocket);
                closeLocalSocket(this.mLocalSocket);
                this.mLocalSocket = null;
              }
              if (strIncome != null) {
                if (isValidCommand(strIncome)) {
                  Slog.m117i(this.THREAD_TAG, "command_0: " + strIncome);
                  if (strIncome.contains("AT+ENGMODES")) {
                    response = executeEmAtCommand(strIncome);
                  } else {
                    response = doWork(strIncome);
                  }
                  bw.write(response);
                  bw.flush();
                  Slog.m117i(this.THREAD_TAG, "command_1:" + strIncome + " Response:" + response);
                } else if (!strIncome.equals("") && this.THREAD_TAG.equals("SatsServiceData")) {
                  bw.write("NA");
                  bw.flush();
                  Slog.m117i(this.THREAD_TAG, "Command:" + strIncome + " Response:NA");
                }
              } else {
                Slog.m115e(this.THREAD_TAG, "Socket seems be closed.");
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
              Slog.m117i(this.THREAD_TAG, "Wait until JIG is inserted or ATD Activation intent");
              synchronized (SatsService.this.mThreadUart) {
                while (SatsService.this.mThreadUartGoWait) {
                  SatsService.this.mThreadUart.wait();
                }
              }
            } else {
              continue;
            }
          } catch (Exception e4) {
            e4.printStackTrace();
          }
        }
      }
    }

    private void closeLocalSocket(LocalSocket socket) {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    private void closeInputStream(LocalSocket socket) {
      try {
        socket.shutdownInput();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void closeOutputStream(LocalSocket socket) {
      try {
        socket.shutdownOutput();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private boolean isValidCommand(String cmd) {
      if (cmd == null) {
        Slog.m115e(this.THREAD_TAG, "error: cmd = null");
        return false;
      }
      Slog.m117i(
          this.THREAD_TAG,
          "isValidCommand: cmd is [" + cmd + NavigationBarInflaterView.SIZE_MOD_END);
      try {
        if (cmd.contains("=") && cmd.indexOf("=") > AT_COMMAND_HEADER.length()) {
          Iterator<String> cmdListIter = SatsService.this.cmdList.iterator();
          while (cmdListIter.hasNext()) {
            String command = cmdListIter.next();
            if (cmd.substring(0, cmd.indexOf("=")).equals(command)) {
              Slog.m117i(this.THREAD_TAG, "isValidCommand: cmd is true. " + command);
              return true;
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return false;
    }

    private String executeEmAtCommand(String cmd) {
      String[] testParams = {
        "8,0,0",
        "8,0,1",
        "8,0,2",
        "8,0,3",
        "7,0,1,0,0",
        "7,1,0,0,0",
        "7,1,1,0,0",
        "7,2,0,0,0",
        "7,2,1,0,0",
        "7,1,1,1,0",
        "7,0,0,1,0",
        "7,0,0,2,0",
        "7,0,0,3,0",
        "7,0,0,4,0",
        "7,0,0,5,0",
        "7,0,0,0,1"
      };
      String response =
          (this.THREAD_TAG.equals("SatsServiceData") ? "" + cmd.trim() : "") + AT_RESPONSE_START;
      String parameter = cmd.substring(cmd.indexOf("=") + 1, cmd.length());
      for (String testParam : testParams) {
        if (parameter.equals(testParam)) {
          Slog.m117i(
              this.THREAD_TAG,
              "executeEmAtCommand: test command("
                  + testParam
                  + NavigationBarInflaterView.KEY_CODE_END);
          return (((response + "+ENGMODES:")
                      + cmd.substring(cmd.indexOf("=") + 1, cmd.indexOf("=") + 2))
                  + ",OK")
              + AT_RESPONSE_END;
        }
      }
      byte[] result = SatsService.this.mEmCmdHelper.processCmd(SatsService.mContext, parameter);
      if (result == null) {
        return (((response + "+ENGMODES:")
                    + cmd.substring(cmd.indexOf("=") + 1, cmd.indexOf("=") + 2))
                + ",NG,FFFFFFFF")
            + AT_RESPONSE_END;
      }
      return response + new String(result, StandardCharsets.UTF_8);
    }

    private String doWork(String cmd) {
      StringBuilder responseDo;
      StringBuilder responseDo2 = new StringBuilder();
      Iterator<IWorkOnAt> iterator = SatsService.this.serviceInterfaces.iterator();
      while (true) {
        if (!iterator.hasNext()) {
          break;
        }
        Slog.m117i(UART_SOCKET_NAME, "doWork: iterator: ");
        IWorkOnAt atCmd = iterator.next();
        Slog.m117i(UART_SOCKET_NAME, "doWork: cmd " + atCmd.getCmd());
        if (cmd.substring(cmd.indexOf("+") + 1, cmd.indexOf("=")).equals(atCmd.getCmd())) {
          if (this.THREAD_TAG.equals("SatsServiceData")) {
            responseDo2 = responseDo2.append(cmd.trim());
          }
          StringBuilder responseDo3 = responseDo2.append(AT_RESPONSE_START);
          try {
            responseDo = responseDo3.append(cmd.substring(cmd.indexOf("+"), cmd.indexOf("=")));
          } catch (Exception e) {
            e.printStackTrace();
            responseDo = responseDo3.append(AT_RESPONSE_INVALID_PARAM).append(AT_RESPONSE_END);
          }
          Slog.m117i(UART_SOCKET_NAME, "doWork: cmdResponse is equal to cmd.");
          responseDo2 =
              responseDo
                  .append(":")
                  .append(atCmd.processCmd(cmd.substring(cmd.indexOf("=") + 1, cmd.length())))
                  .append(AT_RESPONSE_END);
        } else {
          Slog.m117i(UART_SOCKET_NAME, "doWork: cmd not in list");
        }
      }
      if (responseDo2.toString().isEmpty()) {
        Slog.m117i(UART_SOCKET_NAME, "doWork: no such service");
        responseDo2 = responseDo2.append("NG (INVALID PARAM)\r\n\r\nOK\r\n");
      }
      return responseDo2.toString();
    }
  }

  @Override // com.samsung.android.service.sats.ISatsService
  public String executePseudoDrkAtCommnd(String cmd) {
    String response;
    StringBuilder sb;
    int callerUid;
    String callerName;
    String RESPONSE_NG = "NG (Exception OCCURS)";
    String RESPONSE_PERM = "NG (Permission Denied)";
    try {
      if (!"eng".equals(Build.TYPE)) {
        Slog.m115e(TAG, "It is only supported on eng binary.");
        return null;
      }
      try {
        int callerPid = Binder.getCallingPid();
        callerUid = Binder.getCallingUid();
        callerName = "";
        ActivityManager am = (ActivityManager) mContext.getSystemService("activity");
        if (am.getRunningAppProcesses() != null) {
          try {
            Iterator<ActivityManager.RunningAppProcessInfo> it =
                am.getRunningAppProcesses().iterator();
            while (true) {
              if (!it.hasNext()) {
                break;
              }
              ActivityManager.RunningAppProcessInfo processInfo = it.next();
              String RESPONSE_NG2 = RESPONSE_NG;
              String RESPONSE_PERM2 = RESPONSE_PERM;
              try {
                if (processInfo.pid == callerPid) {
                  callerName = processInfo.processName;
                  break;
                }
                RESPONSE_NG = RESPONSE_NG2;
                RESPONSE_PERM = RESPONSE_PERM2;
              } catch (Exception e) {
                e = e;
                Slog.m115e(TAG, "Failed to excute Pseudo DRK AT command : " + cmd);
                e.printStackTrace();
                response = "\r\nNG (Exception OCCURS)";
                sb = new StringBuilder();
                return sb.append(response).append("\r\n\r\nOK\r\n").toString();
              } catch (Throwable th) {
                th = th;
                String str = "\r\n\r\n\r\nOK\r\n";
                throw th;
              }
            }
          } catch (Exception e2) {
            e = e2;
            Slog.m115e(TAG, "Failed to excute Pseudo DRK AT command : " + cmd);
            e.printStackTrace();
            response = "\r\nNG (Exception OCCURS)";
            sb = new StringBuilder();
            return sb.append(response).append("\r\n\r\nOK\r\n").toString();
          } catch (Throwable th2) {
            th = th2;
            String str2 = "\r\n\r\n\r\nOK\r\n";
            throw th;
          }
        }
        try {
          try {
          } catch (Exception e3) {
            e = e3;
            Slog.m115e(TAG, "Failed to excute Pseudo DRK AT command : " + cmd);
            e.printStackTrace();
            response = "\r\nNG (Exception OCCURS)";
            sb = new StringBuilder();
            return sb.append(response).append("\r\n\r\nOK\r\n").toString();
          }
        } catch (Exception e4) {
          e = e4;
        } catch (Throwable th3) {
          th = th3;
          String str22 = "\r\n\r\n\r\nOK\r\n";
          throw th;
        }
      } catch (Exception e5) {
        e = e5;
      } catch (Throwable th4) {
        th = th4;
      }
      if ("system".equals(callerName) && callerUid == 1000) {
        if (this.mDrkAtCommander == null) {
          this.mDrkAtCommander = new SamsungAttestationATCmd(mContext.getApplicationContext());
        }
        if (this.mHermesAtCommander == null) {
          this.mHermesAtCommander = new HermesATCmd(mContext.getApplicationContext());
        }
        if (cmd.substring(0, cmd.indexOf("=")).equals("AT+" + this.mDrkAtCommander.getCmd())) {
          response =
              "\r\n"
                  + cmd.substring(cmd.indexOf("+"), cmd.indexOf("="))
                  + ":"
                  + this.mDrkAtCommander.processCmd(
                      cmd.substring(cmd.indexOf("=") + 1, cmd.length()));
        } else if (cmd.substring(0, cmd.indexOf("="))
            .equals("AT+" + this.mHermesAtCommander.getCmd())) {
          response =
              "\r\n"
                  + cmd.substring(cmd.indexOf("+"), cmd.indexOf("="))
                  + ":"
                  + this.mHermesAtCommander.processCmd(
                      cmd.substring(cmd.indexOf("=") + 1, cmd.length()));
        } else {
          Slog.m115e(TAG, "Invalid command : " + cmd);
          response = "\r\nNG (INVALID PARAM)";
        }
        sb = new StringBuilder();
        return sb.append(response).append("\r\n\r\nOK\r\n").toString();
      }
      Slog.m115e(
          TAG,
          "Permission denied : Name = ["
              + callerName
              + "], UID = ["
              + callerUid
              + NavigationBarInflaterView.SIZE_MOD_END);
      String str3 = "\r\n\r\n\r\nOK\r\n";
      return "NG (Permission Denied)";
    } catch (Throwable th5) {
      th = th5;
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
      Slog.m117i(SatsService.TAG, "Initialized");
      init();
    }

    public byte[] processCmd(Context context, String param) {
      try {
        for (String lCmdParam : this.lCmdParams) {
          int lCmdParamLen = lCmdParam.length();
          if (param.length() >= lCmdParamLen
              && lCmdParam.equals(param.substring(0, lCmdParamLen))) {
            return proceedlCmd(context, param, lCmdParamLen);
          }
        }
        return SatsService.this.commandForESS(context, param);
      } catch (RuntimeException e) {
        Slog.m115e(SatsService.TAG, "Failed to excute ENGMODES AT command : " + param);
        e.printStackTrace();
        init();
        return ("+ENGMODES:" + param.substring(0, 1) + "," + AT_RESPONSE_EXCEPION + AT_RESPONSE_END)
            .getBytes(StandardCharsets.UTF_8);
      }
    }

    private byte[] proceedlCmd(Context context, String param, int fixedParamLen) {
      int sequenceNo;
      String strSequenceNo = param.substring(fixedParamLen, fixedParamLen + 3);
      if (strSequenceNo.equals("FFF")) {
        sequenceNo = getDataIndex() + 1;
      } else {
        sequenceNo = Integer.parseInt(strSequenceNo);
      }
      if (!appendData(sequenceNo, param.substring(fixedParamLen + 3, param.length()).trim())) {
        Slog.m115e(
            SatsService.TAG,
            "Failed to append command SN-" + sequenceNo + " EN-" + (getDataIndex() + 1));
        String errStr =
            "+ENGMODES:"
                + param.substring(0, 1)
                + ","
                + AT_RESPONSE_MISSED_DATA
                + " SN-"
                + sequenceNo
                + " EN-"
                + (getDataIndex() + 1)
                + AT_RESPONSE_END;
        init();
        return errStr.getBytes(StandardCharsets.UTF_8);
      }
      if (strSequenceNo.equals("FFF")) {
        String preParam = param.substring(0, fixedParamLen);
        if (this.lCmdParams[0].equals(preParam)) {
          preParam = "0,2,";
        }
        byte[] result = SatsService.this.commandForESS(context, preParam + getTotalData());
        init();
        return result;
      }
      return ("+ENGMODES:" + param.substring(0, 1) + ",OK" + AT_RESPONSE_END)
          .getBytes(StandardCharsets.UTF_8);
    }

    public boolean appendData(int sequenceNo, String data) {
      if (sequenceNo != 1) {
        if (sequenceNo == this.mCmdCounter + 1) {
          this.mCmdCounter = sequenceNo;
          this.mCmdData += data;
          return true;
        }
        return false;
      }
      this.mCmdCounter = sequenceNo;
      this.mCmdData = data;
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
