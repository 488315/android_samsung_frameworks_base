package com.android.server;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes.dex */
public abstract class ProfileService extends SystemService {
  public String SERVICE_NAME;
  public String TAG;
  public Set packageBlockList;

  public abstract boolean checkAppId(int i);

  public abstract IBinder getBinderOfService();

  public abstract void initializeInterfaceOfService();

  public abstract boolean isServiceRunning();

  public abstract void setInterfaceOfService(IBinder iBinder);

  public boolean validateAppIDhook() {
    return true;
  }

  public ProfileService(Context context, String str, String str2) {
    super(context);
    this.TAG = str;
    this.SERVICE_NAME = str2;
  }

  public Set initPackageBlockList(String str) {
    HashSet hashSet = new HashSet();
    if (new File(str).exists()) {
      try {
        BufferedReader createBufferedReader = createBufferedReader(str);
        while (true) {
          try {
            String readLine = createBufferedReader.readLine();
            if (readLine == null) {
              break;
            }
            if (!readLine.startsWith("#")) {
              hashSet.add(readLine);
            }
          } finally {
          }
        }
        createBufferedReader.close();
      } catch (IOException unused) {
        Slog.w(this.TAG, "Exception caught while reading " + str);
      }
    } else {
      Slog.w(this.TAG, "Blocklist file " + str + " does not exist");
    }
    return hashSet;
  }

  public BufferedReader createBufferedReader(String str) {
    return new BufferedReader(
        new InputStreamReader(new FileInputStream(str), StandardCharsets.UTF_8));
  }

  public boolean isInBlockList(String str, Set set) {
    if (set == null) {
      return false;
    }
    if (set.contains(str)) {
      return true;
    }
    Iterator it = set.iterator();
    while (it.hasNext()) {
      String str2 = (String) it.next();
      if (str2.contains("*")) {
        try {
          if (str.matches(str2.replace("*", "\\S+").replace(".", "\\."))) {
            return true;
          }
        } catch (PatternSyntaxException unused) {
          Slog.e(this.TAG, "Invalid regular expression's syntax in pattern: " + str2);
        }
      }
    }
    return false;
  }

  public boolean isPackageBlockListed(String str) {
    return isInBlockList(str, this.packageBlockList);
  }

  public boolean checkUserAndService(int i, int i2) {
    if (isServiceRunning() && checkUserId(i)) {
      return !validateAppIDhook() || checkAppId(i2);
    }
    return false;
  }

  public boolean checkUserId(int i) {
    if (i >= 0) {
      return true;
    }
    Slog.w(this.TAG, "Invalid user id: " + i);
    return false;
  }

  /* renamed from: selectSuitableProfileService, reason: merged with bridge method [inline-methods] */
  public void lambda$selectSuitableProfileService$0() {
    IBinder binderOfService = getBinderOfService();
    if (binderOfService != null) {
      try {
        binderOfService.linkToDeath(
            new IBinder.DeathRecipient() { // from class: com.android.server.ProfileService.1
              @Override // android.os.IBinder.DeathRecipient
              public void binderDied() {
                ProfileService.this.initializeInterfaceOfService();
                Slog.w(
                    ProfileService.this.TAG,
                    ProfileService.this.SERVICE_NAME + " died; reconnecting");
                ProfileService.this.lambda$selectSuitableProfileService$0();
              }
            },
            0);
      } catch (RemoteException unused) {
        binderOfService = null;
      }
    }
    if (binderOfService != null) {
      setInterfaceOfService(binderOfService);
      Slog.i(this.TAG, this.SERVICE_NAME + " found successfully");
      return;
    }
    Slog.w(this.TAG, this.SERVICE_NAME + " not found; trying again");
    BackgroundThread.getHandler()
        .postDelayed(
            new Runnable() { // from class:
              // com.android.server.ProfileService$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                ProfileService.this.lambda$selectSuitableProfileService$0();
              }
            },
            1000L);
  }
}
