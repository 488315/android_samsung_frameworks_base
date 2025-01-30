package com.android.server;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import com.samsung.android.service.ProtectedATCommand.PACMClassifier;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;
import com.samsung.android.service.ProtectedATCommand.list.CPCommand;
import com.samsung.android.service.ProtectedATCommand.list.ICmdList;
import com.samsung.android.service.ProtectedATCommand.list.ProtectedCommandOpt1;
import com.samsung.android.service.ProtectedATCommand.list.ProtectedCommandOpt2;
import com.samsung.android.service.ProtectedATCommand.list.UserOpenCommand;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class PACMService extends SystemService {
  public static boolean mGalaxyDiag = false;
  public static final boolean mIsJDMDevice = false;
  public static List mReceiverList = new ArrayList();
  public final LinkedHashMap mAtMap;
  public HashSet mCache;
  public final Context mContext;
  public String mEmTokenState;
  public final Handler mHandler;
  public final Object mLock;
  public final BroadcastReceiver mReceiver;
  public Thread mThreadSocket;
  public PowerManager.WakeLock mWakeLock;

  public PACMService(Context context) {
    super(context);
    this.mLock = new Object();
    this.mEmTokenState = "";
    this.mWakeLock = null;
    this.mCache = new HashSet();
    this.mAtMap = new LinkedHashMap();
    this.mHandler =
        new Handler() { // from class: com.android.server.PACMService.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            if (message.what != 1) {
              return;
            }
            Slog.d("PACMService", "MESSAGE_CHECK_BOARDCAST_RECEIVER");
            if (PACMService.mReceiverList == null) {
              Slog.e("PACMService", "List is null");
              return;
            }
            if (!PACMService.mReceiverList.contains(PACMService.this.mReceiver)) {
              PACMService.this.registerForBroadcasts();
              PACMService.this.mHandler.sendEmptyMessageDelayed(1, 2000L);
              return;
            }
            Slog.i(
                "PACMService",
                "Already registered BroadcastReceiver! [" + PACMService.mReceiverList.size() + "]");
            if (PACMService.this.mHandler.hasMessages(1)) {
              PACMService.this.mHandler.removeMessages(1);
            }
          }
        };
    this.mReceiver =
        new BroadcastReceiver() { // from class: com.android.server.PACMService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            Slog.i("PACMService", "Broadcast received:" + action);
            if (action.equals("com.android.server.em.EM_SYNC_TOKEN_STATE")) {
              PACMService.this.mEmTokenState = intent.getExtras().getString("ts");
              Slog.i("PACMService", "ts : " + PACMService.this.mEmTokenState);
              synchronized (PACMService.this.mLock) {
                PACMService.this.mCache.clear();
              }
              return;
            }
            if (action.equals("com.samsung.android.aircommandmanager.START_LOCAL_SOCKET")) {
              Slog.i("PACMService", "GalaxyDiag app start");
              if (PACMService.this.isPackageInstalled("com.samsung.android.app.mobiledoctor")
                  || PACMService.this.isPackageInstalled("kr.co.avad.diagnostictool")
                  || PACMService.this.isPackageInstalled("com.samsung.android.app.mobiledoctor.ve")
                  || PACMService.this.isPackageInstalled("com.samsung.android.app.repaircal")) {
                PACMService.mGalaxyDiag = true;
                return;
              }
              return;
            }
            if (action.equals("com.samsung.android.aircommandmanager.STOP_LOCAL_SOCKET")) {
              Slog.i("PACMService", "GalaxyDiag app end");
              PACMService.mGalaxyDiag = false;
            }
          }
        };
    this.mContext = context;
    this.mCache.clear();
    this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "PACM_WL");
    registerProtectedCommandList();
    try {
      Thread thread = new Thread(new PACServiceSocketThread());
      this.mThreadSocket = thread;
      thread.start();
    } catch (Exception e) {
      Slog.e("PACMService", "Failed to start PACM Service");
      e.printStackTrace();
    }
    Slog.i("PACMService", "Success to start PACM Service(S-ver : 10.6.0/ P-ver : 1)");
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    Slog.d("PACMService", "onStart() ");
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 480) {
      Slog.d("PACMService", "PHASE_LOCK_SETTINGS_READY");
      registerForBroadcasts();
      this.mHandler.sendEmptyMessageDelayed(1, 2000L);
    }
  }

  public final void registerProtectedCommandList() {
    try {
      ICmdList[] iCmdListArr = {
        new ProtectedCommandOpt1(this.mContext),
        new ProtectedCommandOpt2(this.mContext),
        new UserOpenCommand(this.mContext),
        new CPCommand(this.mContext)
      };
      for (int i = 0; i < 4; i++) {
        int putCommandList = PACMClassifier.putCommandList(this.mAtMap, iCmdListArr[i].getList());
        if (putCommandList != 1) {
          Slog.e("PACMService", "Failed to add class command list(" + putCommandList + ")");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final boolean isPackageInstalled(String str) {
    PackageManager packageManager = this.mContext.getPackageManager();
    if (str == null) {
      Slog.e("PACMService", "package name is null in isPackageInstalled");
      return false;
    }
    try {
      packageManager.getPackageInfo(str, 0);
      if (packageManager.checkSignatures("android", str) != 0) {
        Slog.e("PACMService", str + " is installed but signature is not matched");
        return false;
      }
      Slog.i("PACMService", str + " is installed and signature is matched.");
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.e("PACMService", "GalaxyDiag app is not installed!");
      return false;
    }
  }

  public final void registerForBroadcasts() {
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.android.server.em.EM_SYNC_TOKEN_STATE");
      intentFilter.addAction("com.samsung.android.aircommandmanager.START_LOCAL_SOCKET");
      intentFilter.addAction("com.samsung.android.aircommandmanager.STOP_LOCAL_SOCKET");
      this.mContext.registerReceiver(this.mReceiver, intentFilter);
      mReceiverList.add(this.mReceiver);
    } catch (Exception e) {
      Slog.e("PACMService", "Failed to add Broadcast");
      e.printStackTrace();
    }
  }

  public final class PACServiceSocketThread implements Runnable {
    public byte[] buffer;
    public LocalServerSocket mLocalServerSocket;
    public LocalSocket mLocalSocket;

    public PACServiceSocketThread() {}

    public int ByteToInt(byte[] bArr) {
      return new BigInteger(bArr).intValue();
    }

    public byte[] BigEndianToLittleEndian(byte[] bArr) {
      ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
      allocate.order(ByteOrder.LITTLE_ENDIAN);
      allocate.putInt(new BigInteger(bArr).intValue());
      return allocate.array();
    }

    public final boolean checkEmState() {
      return PACMService.this.mEmTokenState.equals("NM")
          || PACMService.this.mEmTokenState.equals("")
          || PACMService.this.mEmTokenState.equals("ES");
    }

    public final int checkClassStatus(int i) {
      int i2 = 1;
      if (PACMService.this.mCache.contains("MODE" + i)) {
        Slog.d("PACMSOCKET", "mode(" + i + ") is already cached");
      } else {
        if (PACMService.this.mWakeLock == null) {
          Slog.e("PACMSOCKET", "mWakeLock is null");
          return 0;
        }
        if (!PACMService.this.mWakeLock.isHeld()) {
          PACMService.this.mWakeLock.acquire();
        }
        EngineeringModeManager engineeringModeManager =
            new EngineeringModeManager(PACMService.this.mContext);
        if (checkEmState() && engineeringModeManager.isConnected()) {
          Slog.d("PACMSOCKET", "Call getStatus(" + i + ")");
          int status = engineeringModeManager.getStatus(i);
          Slog.i("PACMSOCKET", "getStatus ret : " + status);
          if (status == 1) {
            PACMService.this.mCache.add("MODE" + i);
          }
          i2 = status;
        } else {
          Slog.e(
              "PACMSOCKET",
              "tstate : "
                  + PACMService.this.mEmTokenState
                  + ", em connected : "
                  + engineeringModeManager.isConnected());
          i2 = 0;
        }
        if (PACMService.this.mWakeLock.isHeld()) {
          PACMService.this.mWakeLock.release();
        }
      }
      return i2;
    }

    public final boolean isForceTestUser() {
      boolean equals = "true".equals(SystemProperties.get("security.pacm.test", "false"));
      if (equals) {
        Slog.i("PACMSOCKET", "[AT command Test Mode] Simulate user device");
      }
      return equals;
    }

    public final boolean isDevAndNoship() {
      String str = SystemProperties.get("ro.boot.em.status", "0x1");
      return (str.equals("0x0") || str.equals("0x2")) ? false : true;
    }

    public final int checkAtdDdex() {
      byte[] item = PAC_Packet.getItem(this.buffer, 4);
      if (item == null) {
        Slog.e("PACMSOCKET", "atd_ddex is null !!");
        return 0;
      }
      String str = new String(item, Charset.forName("UTF-8"));
      if (str.equals("ATD")) {
        Slog.i("PACMSOCKET", "This cmd is from ATD");
        return 1;
      }
      if (!str.equals("DDEX")) {
        return 0;
      }
      Slog.i("PACMSOCKET", "This cmd is from DDEX");
      return 2;
    }

    public final boolean isSecureLock() {
      boolean z = false;
      try {
        z =
            ((KeyguardManager) PACMService.this.mContext.getSystemService(KeyguardManager.class))
                .isDeviceLocked();
        Slog.d("PACMSOCKET", "secureLock : " + z);
        return z;
      } catch (Exception e) {
        Slog.e("PACMSOCKET", "Failed to get secureLock", e);
        return z;
      }
    }

    public final boolean isAutoBlocker() {
      if (Settings.Secure.getInt(
              PACMService.this.mContext.getContentResolver(), "rampart_blocked_at_cmd", 0)
          != 1) {
        return false;
      }
      Slog.d("PACMSOCKET", "Auto Blocker is on");
      return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0290 A[Catch: Exception -> 0x029a, all -> 0x02bd, TRY_LEAVE, TryCatch #0 {Exception -> 0x029a, blocks: (B:10:0x0028, B:12:0x0031, B:16:0x004e, B:19:0x0071, B:20:0x01e7, B:22:0x01ed, B:25:0x01f5, B:27:0x01fb, B:28:0x0203, B:30:0x0220, B:32:0x0226, B:33:0x0243, B:35:0x0249, B:36:0x0267, B:38:0x0290, B:42:0x0091, B:44:0x00a1, B:46:0x00d8, B:48:0x00de, B:51:0x00ed, B:53:0x00f3, B:55:0x00f9, B:57:0x0106, B:58:0x010c, B:60:0x0173, B:62:0x017a, B:64:0x0180, B:66:0x0186, B:67:0x0112, B:69:0x011f, B:70:0x0129, B:71:0x0133, B:72:0x013d, B:74:0x014a, B:76:0x015a, B:78:0x015e, B:80:0x0168, B:83:0x0190, B:85:0x0198, B:87:0x01be, B:88:0x01da, B:89:0x01df), top: B:9:0x0028, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] executeCommand(int i) {
      int version;
      int checkClassStatus;
      synchronized (PACMService.this.mLock) {
        String str = "";
        byte[] bArr = null;
        if (this.buffer != null && i != 0) {
          Slog.i("PACMSOCKET", "START - executeCommand :" + i);
          try {
            version = PAC_Packet.getVersion(this.buffer);
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (version != 1) {
            Slog.e("PACMSOCKET", "Version is abnormal(" + version + ")");
            return null;
          }
          int command = PAC_Packet.getCommand(this.buffer);
          Slog.d("PACMSOCKET", "Command : " + command);
          boolean z = false;
          int i2 = 161;
          if (command == 0) {
            byte[] item = PAC_Packet.getItem(this.buffer, 0);
            if (item != null) {
              int ByteToInt = ByteToInt(BigEndianToLittleEndian(item));
              Slog.d("PACMSOCKET", "parsed_mode(" + ByteToInt + ")");
              if (ByteToInt < 0) {
                Slog.e("PACMSOCKET", "Failed to parse mode(" + ByteToInt + ")");
              } else {
                checkClassStatus = checkClassStatus(ByteToInt);
              }
            } else {
              Slog.e("PACMSOCKET", "Failed to get mode number");
            }
            checkClassStatus = 0;
          } else if (command == 1) {
            byte[] item2 = PAC_Packet.getItem(this.buffer, 2);
            Slog.i("PACMSOCKET", "PAC_Packet.PAC_PACKET_CMD_AT_CMD_CHECK:");
            if (item2 != null) {
              str = new String(item2, Charset.forName("UTF-8")).toUpperCase(Locale.ENGLISH);
              Slog.i("PACMSOCKET", "cmd : " + str);
              ATCommands command2 = PACMClassifier.getCommand(PACMService.this.mAtMap, str);
              if (!isAutoBlocker() || PACMClassifier.hasAutoBlockerAttribute(command2)) {
                int i3 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP;
                if (command2 == null) {
                  if (isDevAndNoship() && !isForceTestUser()) {
                    Slog.e(
                        "PACMSOCKET",
                        "This command is unregistered. But it is temporarily allowed.");
                    checkClassStatus = 161;
                  }
                } else {
                  switch (command2.getType()) {
                    case 161:
                      Slog.i("PACMSOCKET", "This Command is USER_OPEN_AT_COMMAND");
                      if (1 != checkClassStatus(28)) {
                        i3 =
                            PACMClassifier.checkAttribute(command2, isSecureLock(), checkAtdDdex());
                        if (i3 != 195 || !PACMService.mGalaxyDiag) {
                          if (i3 != 161) {
                            Slog.i(
                                "PACMSOCKET",
                                "This Command is not USER_OPEN_AT_COMMAND because of attribute");
                            z = true;
                            break;
                          }
                        } else {
                          Slog.i(
                              "PACMSOCKET",
                              "This Command is now open becauese there is Galaxy Diag Tool");
                        }
                      }
                      i3 = 161;
                      break;
                    case 162:
                      Slog.i("PACMSOCKET", "This Command is CP_AT_COMMAND");
                      i3 = 162;
                      break;
                    case FrameworkStatsLog.f640x2165d62a /* 163 */:
                    case FrameworkStatsLog.f636xd07885aa /* 164 */:
                      Slog.i("PACMSOCKET", "This Command is PROTECTED_AT_COMMAND");
                      if (1 == checkClassStatus(28)) {
                        Slog.i("PACMSOCKET", "This Command is ALLOWED_PROTECTED_AT_COMMAND");
                        i3 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT;
                        break;
                      } else {
                        Slog.i("PACMSOCKET", "This Command is NOT_ALLOWED_PROTECTED_AT_COMMAND");
                        i3 = 177;
                        break;
                      }
                    default:
                      Slog.i("PACMSOCKET", "This Command is not registered");
                      break;
                  }
                  if (i3 != 161 && isDevAndNoship() && !isForceTestUser()) {
                    Slog.e(
                        "PACMSOCKET",
                        "This command is be temporarily allowed. but it will be blocked");
                    i3 = 161;
                  }
                }
                checkClassStatus = i3;
              } else {
                Slog.i("PACMSOCKET", "AT command is blocked by Auto Blocker");
                checkClassStatus = 208;
              }
            }
            checkClassStatus = 0;
          } else {
            Slog.e("PACMSOCKET", "Unknown command(" + command + ")");
            checkClassStatus = -268435453;
          }
          if (!isAutoBlocker()) {
            if (PACMService.mIsJDMDevice) {
              if (!z) {
                if (PACMClassifier.isJDMProtectedCommand(str) == 1) {
                  Slog.i("PACMSOCKET", "This command is JDM protected command");
                } else {
                  Slog.i("PACMSOCKET", "open " + str + " in PACM because of JDM device");
                  checkClassStatus = 161;
                }
              } else if (PACMClassifier.isJDMOpenCommand(str) == 1) {
                Slog.i("PACMSOCKET", "open " + command + " in PACM because of JDM open command");
                checkClassStatus = 161;
              }
              Slog.i("PACMSOCKET", "END - executeCommand :" + command + "(" + i2 + ")");
              bArr = PAC_Packet.initPacket(1);
              if (bArr != null) {
                bArr = PAC_Packet.putItem(bArr, Integer.valueOf(i2), 3);
              }
              return bArr;
            }
            if (PACMClassifier.checkSpecialCommand(str) == 1) {
              Slog.i("PACMSOCKET", "open " + command + " in PACM because of Special command");
              Slog.i("PACMSOCKET", "END - executeCommand :" + command + "(" + i2 + ")");
              bArr = PAC_Packet.initPacket(1);
              if (bArr != null) {}
              return bArr;
            }
          }
          i2 = checkClassStatus;
          Slog.i("PACMSOCKET", "END - executeCommand :" + command + "(" + i2 + ")");
          bArr = PAC_Packet.initPacket(1);
          if (bArr != null) {}
          return bArr;
        }
        Slog.e("PACMSOCKET", "buffer is abnormal(" + i + ")");
        return null;
      }
    }

    @Override // java.lang.Runnable
    public void run() {
      LocalSocket accept;
      InputStream inputStream;
      try {
        LocalSocket localSocket = new LocalSocket(2);
        this.mLocalSocket = localSocket;
        localSocket.bind(
            new LocalSocketAddress(
                "/dev/socket/pacm/pacmservice", LocalSocketAddress.Namespace.FILESYSTEM));
        this.mLocalServerSocket = new LocalServerSocket(this.mLocalSocket.getFileDescriptor());
        this.buffer = new byte[512];
        while (true) {
          LocalServerSocket localServerSocket = this.mLocalServerSocket;
          if (localServerSocket != null) {
            try {
              accept = localServerSocket.accept();
              try {
                inputStream = accept.getInputStream();
              } finally {
                if (accept == null) {
                  break;
                } else {
                  try {
                    break;
                  } catch (Throwable th) {
                  }
                }
              }
            } catch (Exception e) {
              Slog.i("PACMSOCKET", "Socket connection may be closed. " + e.toString());
            }
            try {
              OutputStream outputStream = accept.getOutputStream();
              try {
                Slog.d("PACMSOCKET", "[v.1] Ready to connect.");
                if (this.mLocalSocket != null) {
                  accept.getPeerCredentials();
                  byte[] executeCommand = executeCommand(inputStream.read(this.buffer));
                  if (executeCommand == null) {
                    Slog.e("PACMSOCKET", "ret is null");
                    outputStream.write(-1);
                  } else {
                    outputStream.write(executeCommand);
                  }
                }
                Slog.d("PACMSOCKET", "Disconnected.");
                if (outputStream != null) {
                  outputStream.close();
                }
                if (inputStream != null) {
                  inputStream.close();
                }
                accept.close();
              } catch (Throwable th2) {
                if (outputStream != null) {
                  try {
                    outputStream.close();
                  } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                  }
                }
                throw th2;
              }
            } finally {
              if (inputStream == null) {
                break;
              } else {
                try {
                  break;
                } catch (Throwable th4) {
                }
              }
            }
          } else {
            if (localServerSocket != null) {
              try {
                localServerSocket.close();
              } catch (IOException e2) {
                Slog.e("PACMSOCKET", "Failed to close server socket.");
                e2.printStackTrace();
              }
            }
            try {
              LocalSocket localSocket2 = this.mLocalSocket;
              if (localSocket2 != null) {
                localSocket2.close();
              }
            } catch (IOException e3) {
              Slog.e("PACMSOCKET", "Failed to close mLocalSocket socket.");
              e3.printStackTrace();
            }
            Slog.e("PACMSOCKET", "Socket thread has been stopped.");
            return;
          }
        }
      } catch (Exception e4) {
        Slog.e("PACMSOCKET", "mLocalSocket.start Open", e4);
        e4.printStackTrace();
        try {
          LocalSocket localSocket3 = this.mLocalSocket;
          if (localSocket3 != null) {
            localSocket3.close();
          }
        } catch (Exception e5) {
          Slog.e("PACMSOCKET", "mLocalSocket.start close", e5);
          e5.printStackTrace();
        }
      }
    }
  }

  public abstract class PAC_Packet {
    public static int getVersion(byte[] bArr) {
      if (bArr == null) {
        return 0;
      }
      return bArr[0];
    }

    public static int getCommand(byte[] bArr) {
      if (bArr == null) {
        return -268435456;
      }
      if (bArr.length < 2) {
        Slog.e("PACMService", "buf len is abnormal" + bArr.length);
        return -268435456;
      }
      return new BigInteger(new byte[] {bArr[2], bArr[1]}).intValue();
    }

    public static byte[] getItem(byte[] bArr, int i) {
      ByteBuffer put = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(bArr);
      put.position(3);
      byte b = put.get(put.position());
      put.position(put.position() + 1);
      Slog.d("PACMService", "item_count : " + ((int) b));
      for (int i2 = 0; i2 < b; i2++) {
        short s = put.getShort();
        int i3 = put.getShort();
        Slog.d("PACMService", "type : " + ((int) s) + ", " + i3);
        if (s == i) {
          byte[] bArr2 = new byte[i3];
          put.get(bArr2, 0, i3);
          return bArr2;
        }
        put.position(put.position() + i3);
      }
      return null;
    }

    public static byte[] initPacket(int i) {
      byte[] bArr = new byte[3];
      try {
        ByteBuffer order = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN);
        order.put((byte) 1);
        order.putShort((short) i);
        order.position(0);
        order.get(bArr, 0, 3);
        return bArr;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public static byte[] putItem(byte[] bArr, Object obj, int i) {
      ByteBuffer put = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(bArr);
      put.position(3);
      byte b = put.get(put.position());
      put.position(put.position() + 1);
      for (int i2 = 0; i2 < b; i2++) {
        put.getShort();
        put.position(put.position() + put.getShort());
      }
      if (i == 1 || i == 3) {
        if (put.position() + 4 < 512) {
          put.putShort((short) i);
          put.putShort((short) 4);
          put.putInt(Integer.valueOf(obj.toString()).intValue());
        } else {
          Slog.e("PACMService", "Packet is full, Can't put item to packet");
          return null;
        }
      }
      int position = put.position();
      put.rewind();
      byte[] bArr2 = new byte[position];
      put.get(bArr2, 0, position);
      bArr2[3] = (byte) (b + 1);
      return bArr2;
    }
  }
}
