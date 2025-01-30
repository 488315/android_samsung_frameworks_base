package com.android.server.appprelauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ScpmController extends BroadcastReceiver {
  public Context mContext;
  public ExecutorService mExecutor;
  public ScheduledExecutorService mScheduledExecutor;
  public final PrelauncherStorage mStorage;
  public String mToken;

  public ScpmController(PrelauncherStorage prelauncherStorage) {
    this.mStorage = prelauncherStorage;
  }

  public boolean registerScpm(Context context) {
    this.mContext = context;
    try {
      Bundle bundle = new Bundle();
      bundle.putString("packageName", "android");
      bundle.putString("appId", "5bog2om0it");
      bundle.putString("version", "0.0.1");
      bundle.putString("receiverPackageName", "android");
      Bundle call =
          this.mContext
              .getContentResolver()
              .call(ScpmApiContract.URI, "register", "android", bundle);
      if (call != null) {
        boolean z = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1) == 1;
        String string = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
        int i = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
        String string2 = call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
        this.mToken = string;
        if (z) {
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.preltest-6d4c");
          intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
          this.mContext.registerReceiver(this, intentFilter);
          Slog.i("PRELScpm", "Registered in SCPM");
          return true;
        }
        Slog.e("PRELScpm", "Failed to register: rcode = " + i + ", rmsg = " + string2);
      }
      return false;
    } catch (Exception e) {
      Slog.e("PRELScpm", "Cannot register package : " + e.getMessage());
      return false;
    }
  }

  @Override // android.content.BroadcastReceiver
  public void onReceive(Context context, final Intent intent) {
    if (intent == null) {
      Slog.i("PRELScpm", "intent is null");
      return;
    }
    String action = intent.getAction();
    Slog.i("PRELScpm", "intent is received: " + action);
    if ("com.samsung.android.scpm.policy.UPDATE.preltest-6d4c".equals(action)) {
      ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
      this.mExecutor = newSingleThreadExecutor;
      newSingleThreadExecutor.submit(
          new Runnable() { // from class:
            // com.android.server.appprelauncher.ScpmController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              ScpmController.this.lambda$onReceive$0(intent);
            }
          });
      this.mExecutor.shutdown();
      return;
    }
    if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
      startThread(context);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onReceive$0(Intent intent) {
    try {
      String stringExtra = intent.getStringExtra("policy");
      if (stringExtra != null && Build.IS_DEBUGGABLE) {
        this.mStorage.storeScpmConfig(stringExtra);
      }
      updateDevicePolicy();
    } catch (Exception e) {
      Slog.e("PRELScpm", "Failed to run thread : " + e.getMessage());
    }
  }

  public final boolean isAvailable() {
    Context context = this.mContext;
    if (context == null) {
      Slog.w("PRELScpm", "Context is null");
      return false;
    }
    try {
      return context
              .getPackageManager()
              .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
          != null;
    } catch (Exception e) {
      Slog.e("PRELScpm", "unknown exception from isAvailable : " + e.getMessage());
      return false;
    }
  }

  public final void readDataFromFile(ParcelFileDescriptor parcelFileDescriptor) {
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new InputStreamReader(
                  new FileInputStream(parcelFileDescriptor.getFileDescriptor()),
                  StandardCharsets.UTF_8));
      try {
        StringBuilder sb = new StringBuilder();
        while (true) {
          String readLine = bufferedReader.readLine();
          if (readLine != null) {
            sb.append(readLine);
          } else {
            this.mStorage.storeScpmConfig(sb.toString());
            bufferedReader.close();
            return;
          }
        }
      } finally {
      }
    } catch (Exception e) {
      Slog.e("PRELScpm", "Exception occurred: " + e.getMessage());
    }
  }

  public final void updateDevicePolicy() {
    if (isAvailable()) {
      Uri parse =
          Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/preltest-6d4c");
      try {
        ParcelFileDescriptor openFileDescriptor =
            this.mContext.getContentResolver().openFileDescriptor(parse, "r");
        try {
          if (openFileDescriptor == null) {
            Bundle bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
            Bundle call =
                this.mContext
                    .getContentResolver()
                    .call(parse, "getLastError", this.mContext.getPackageName(), bundle);
            Slog.e(
                "PRELScpm",
                "Cannot get new policy : "
                    + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE)
                    + ", "
                    + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
          } else {
            Slog.i("PRELScpm", "Got new policy");
            readDataFromFile(openFileDescriptor);
          }
          if (openFileDescriptor != null) {
            openFileDescriptor.close();
          }
        } finally {
        }
      } catch (Throwable th) {
        Slog.e("PRELScpm", "Unknown exception : " + th.getMessage());
      }
    }
  }

  public void startThread(final Context context) {
    ScheduledExecutorService newSingleThreadScheduledExecutor =
        Executors.newSingleThreadScheduledExecutor();
    this.mScheduledExecutor = newSingleThreadScheduledExecutor;
    newSingleThreadScheduledExecutor.schedule(
        new Runnable() { // from class:
          // com.android.server.appprelauncher.ScpmController$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            ScpmController.this.lambda$startThread$1(context);
          }
        },
        60L,
        TimeUnit.SECONDS);
    this.mScheduledExecutor.shutdown();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startThread$1(Context context) {
    try {
      if (isAvailable()) {
        Slog.d("PRELScpm", "Scpm isAvailable");
        registerScpm(context);
      }
    } catch (Exception e) {
      Slog.e("PRELScpm", "Exception in SCPM register thread: " + e.getMessage());
    }
  }
}
