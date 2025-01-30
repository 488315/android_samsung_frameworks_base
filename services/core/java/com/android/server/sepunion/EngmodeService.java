package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes3.dex */
public final class EngmodeService extends IEngmodeService.Stub implements AbsSemSystemService {
  public Context mContext;
  public EngineeringModeManager mManager;
  public ConnectivityManager.NetworkCallback mNetworkCallback;
  public TimerTask mRunTimeTask;
  public String mServerDate;
  public Thread mTimeThread;
  public Timer mTimerObserve;
  public Timer mUpdateTimer;
  public BroadcastReceiver mBootCompleteReceiver =
      new BroadcastReceiver() { // from class: com.android.server.sepunion.EngmodeService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          if (intent != null) {
            try {
              if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                Slog.i("engmode_service", "BootCompleted");
                if (EngmodeService.this.registerNetworkCallback()) {
                  return;
                }
                EngmodeService.this.registerNetworkCallback();
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      };
  public boolean mNeedUpdate = true;

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  public EngmodeService(Context context) {
    this.mContext = context;
    this.mManager = new EngineeringModeManager(this.mContext);
    Slog.i("engmode_service", "EM Service is started");
  }

  public final TimerTask makeRunTimeTask() {
    return new TimerTask() { // from class: com.android.server.sepunion.EngmodeService.1
      @Override // java.util.TimerTask, java.lang.Runnable
      public void run() {
        try {
          if (EngmodeService.this.mTimeThread != null
              && EngmodeService.this.mTimeThread.isAlive()) {
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
  }

  public final boolean registerNetworkCallback() {
    try {
      ConnectivityManager connectivityManager =
          (ConnectivityManager) this.mContext.getSystemService("connectivity");
      NetworkRequest.Builder addCapability =
          new NetworkRequest.Builder().addCapability(12).addCapability(16);
      this.mNetworkCallback =
          new ConnectivityManager
              .NetworkCallback() { // from class: com.android.server.sepunion.EngmodeService.3
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
              Slog.i("engmode_service", "Network is available");
              try {
                if (EngmodeService.this.mTimerObserve == null) {
                  EngmodeService.this.mTimerObserve = new Timer();
                  EngmodeService engmodeService = EngmodeService.this;
                  engmodeService.mRunTimeTask = engmodeService.makeRunTimeTask();
                  EngmodeService.this.mTimerObserve.schedule(
                      EngmodeService.this.mRunTimeTask, 0L, 21600000L);
                  return;
                }
                if (!EngmodeService.this.mNeedUpdate) {
                  Slog.i("engmode_service", "There is no need for update");
                  return;
                }
                if (EngmodeService.this.mTimeThread.isAlive()) {
                  Slog.i("engmode_service", "Previous thread is alive, skip this time");
                  return;
                }
                EngmodeService.this.mRunTimeTask.cancel();
                EngmodeService engmodeService2 = EngmodeService.this;
                engmodeService2.mRunTimeTask = engmodeService2.makeRunTimeTask();
                EngmodeService.this.mTimerObserve.cancel();
                EngmodeService.this.mTimerObserve = new Timer();
                EngmodeService.this.mTimerObserve.schedule(
                    EngmodeService.this.mRunTimeTask, 0L, 21600000L);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
              Slog.i("engmode_service", "Network isn't available");
            }
          };
      connectivityManager.registerNetworkCallback(addCapability.build(), this.mNetworkCallback);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
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

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(
        "\n##### EngmodeService #####\n##### (dumpsys sepunion EngmodeService) #####\n");
  }

  public final boolean getEmManager() {
    this.mManager = new EngineeringModeManager(this.mContext);
    return true;
  }

  public final boolean isTokenInstalled() {
    try {
      if (getEmManager()) {
        return this.mManager.isTokenInstalled() == 1;
      }
      Slog.e("engmode_service", "Failed to get EM Manager");
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final byte[] makeTimeReq() {
    Slog.i("engmode_service", "makeTimeReq() called");
    if (!getEmManager()) {
      Slog.i("engmode_service", "Failed to get manager");
      return null;
    }
    return this.mManager.makeTimeReq();
  }

  public final byte[] updateTime(byte[] bArr) {
    Slog.i("engmode_service", "updateTimeReq() called");
    if (!getEmManager()) {
      Slog.i("engmode_service", "Failed to get manager");
      return null;
    }
    return this.mManager.updateTime(bArr);
  }

  public class EngmodeTimeThread implements Runnable {
    public EngmodeTimeThread() {}

    @Override // java.lang.Runnable
    public void run() {
      if (!EngmodeService.this.isTokenInstalled()) {
        Slog.i("engmode_service_time", "no token, exit");
        return;
      }
      int i = 0;
      while (i < 2 && EngmodeService.this.mServerDate == null) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        i++;
        sb.append(i);
        sb.append("/");
        sb.append(2);
        sb.append(")");
        Slog.i("engmode_service_time", sb.toString());
        try {
          byte[] bytes = "ENGTRS0004".getBytes();
          byte[] bArr = new byte[10];
          byte[] makeTimeReq = EngmodeService.this.makeTimeReq();
          if (makeTimeReq == null) {
            Slog.w("engmode_service_time", "makeTimeReq() failed");
          } else {
            byte[] post = EngmodeService.this.new EMSConnectionTask().post(makeTimeReq);
            if (post == null) {
              Slog.e("engmode_service_time", "No valid response from EMS server!)");
            } else {
              byte[] updateTime = EngmodeService.this.updateTime(post);
              if (updateTime == null) {
                Slog.e("engmode_service_time", "updateTime() failed");
              } else {
                System.arraycopy(post, 0, bArr, 0, 10);
                Slog.i("engmode_service_time", "TRS Header : " + new String(bArr));
                if (Arrays.equals(bytes, bArr)) {
                  byte[] bArr2 = new byte[8];
                  byte[] bArr3 = new byte[90];
                  byte b = updateTime[0];
                  if (b == 0) {
                    System.arraycopy(updateTime, 1, bArr2, 0, 8);
                    EngmodeService.this.mServerDate = new String(bArr2);
                  } else if (b == 1) {
                    System.arraycopy(updateTime, 9, bArr3, 0, 90);
                    EngmodeService.this.new EMSConnectionTask().post(bArr3);
                  } else if (b != 2) {
                    Slog.e("engmode_service_time", "RTD response is invalid");
                  }
                } else {
                  EngmodeService.this.mServerDate = new String(updateTime);
                }
                if (EngmodeService.this.mServerDate != null) {
                  EngmodeService.this.mNeedUpdate = false;
                  EngmodeService.this.mUpdateTimer = new Timer();
                  EngmodeService.this.mUpdateTimer.schedule(
                      new TimerTask() { // from class:
                                        // com.android.server.sepunion.EngmodeService.EngmodeTimeThread.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                          EngmodeService.this.mNeedUpdate = true;
                          EngmodeService.this.mServerDate = null;
                          Slog.i("engmode_service_time", "clear engmode service");
                        }
                      },
                      18000000L);
                  Slog.i(
                      "engmode_service_time",
                      "EM Server time (" + EngmodeService.this.mServerDate + ")");
                  if (EngmodeService.this.getEmManager()) {
                    EngmodeService.this.mManager.getStatus(65295);
                  } else {
                    Slog.e("engmode_service_time", "Failed to get manager, getStatus skip");
                  }
                }
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      Slog.i("engmode_service_time", "end");
    }
  }

  public class EMSConnectionTask {
    public EMSConnectionTask() {}

    public final byte[] post(byte[] bArr) {
      try {
        MultipartUtility multipartUtility =
            EngmodeService.this
            .new MultipartUtility("https://kwb.secmobilesvc.com:7788/requestEmrToken.kwb", null);
        multipartUtility.addByteArrayPart("tokenreq", bArr);
        ArrayList finish = multipartUtility.finish();
        byte[] bArr2 = new byte[finish.size()];
        for (int i = 0; i < finish.size(); i++) {
          bArr2[i] = ((Byte) finish.get(i)).byteValue();
        }
        return bArr2;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public class MultipartUtility {
    public final String boundary;
    public String charset;
    public HttpURLConnection httpConn;
    public OutputStream outputStream;
    public PrintWriter writer;

    public MultipartUtility(String str, String str2) {
      this.charset = str2;
      String str3 = "===" + System.currentTimeMillis() + "===";
      this.boundary = str3;
      HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
      this.httpConn = httpURLConnection;
      httpURLConnection.setUseCaches(false);
      this.httpConn.setDoOutput(true);
      this.httpConn.setDoInput(true);
      this.httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + str3);
      this.httpConn.setConnectTimeout(10000);
      this.httpConn.setReadTimeout(10000);
      this.outputStream = this.httpConn.getOutputStream();
      this.writer =
          new PrintWriter(
              (Writer)
                  new OutputStreamWriter(this.outputStream, str2 == null ? "ISO-8859-1" : str2),
              true);
    }

    public void addByteArrayPart(String str, byte[] bArr) {
      this.writer.append((CharSequence) ("--" + this.boundary)).append((CharSequence) "\r\n");
      this.writer
          .append(
              (CharSequence)
                  ("Content-Disposition: form-data; name=\""
                      + str
                      + "\"; filename=\""
                      + str
                      + "\""))
          .append((CharSequence) "\r\n");
      this.writer
          .append((CharSequence) "Content-Type: multipart/form-data")
          .append((CharSequence) "\r\n");
      this.writer
          .append((CharSequence) "Content-Transfer-Encoding: binary")
          .append((CharSequence) "\r\n");
      this.writer.append((CharSequence) "\r\n");
      this.writer.flush();
      this.outputStream.write(bArr);
      this.outputStream.flush();
      this.writer.append((CharSequence) "\r\n");
      this.writer.flush();
    }

    public ArrayList finish() {
      this.writer.append((CharSequence) "\r\n").flush();
      this.writer
          .append((CharSequence) ("--" + this.boundary + "--"))
          .append((CharSequence) "\r\n");
      this.writer.close();
      this.outputStream.close();
      int responseCode = this.httpConn.getResponseCode();
      if (responseCode == 200) {
        ArrayList arrayList = new ArrayList();
        InputStream inputStream = this.httpConn.getInputStream();
        while (true) {
          int read = inputStream.read();
          if (read != -1) {
            arrayList.add(Byte.valueOf((byte) read));
          } else {
            inputStream.close();
            this.httpConn.disconnect();
            return arrayList;
          }
        }
      } else {
        throw new IOException("Server returned non-OK status: " + responseCode);
      }
    }
  }
}
