package com.android.server.enterprise.constrained;

import android.R;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.ServiceManager;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.ISystemUIAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.EnrollData;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ConstrainedModeService extends ConstrainedModeInternal {
  public Context mContext;
  public EdmStorageProvider mEdmStorageProvider;
  public PackageManagerAdapter mPackageManagerAdapter;
  public static final byte[] CONSTRAINED_DELIMITER = {8, 14, 25, -1};
  public static ConstrainedModeService sConstrainedService = null;
  public static final Object lock = new Object();
  public EnterpriseDeviceManager mEDM = null;
  public HashMap mCachedConstrainedData = new HashMap();
  public BroadcastReceiver mReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.enterprise.constrained.ConstrainedModeService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
            ConstrainedModeService.this.updateNotification();
          }
        }
      };

  public ConstrainedModeService(Context context) {
    this.mEdmStorageProvider = null;
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
    this.mContext.registerReceiver(this.mReceiver, intentFilter);
    this.mPackageManagerAdapter = PackageManagerAdapter.getInstance(context);
    updateConstrainedStateData(false);
    new Thread(
            new Runnable() { // from class:
                             // com.android.server.enterprise.constrained.ConstrainedModeService.2
              @Override // java.lang.Runnable
              public void run() {
                boolean z = false;
                while (!z) {
                  if (INotificationManager.Stub.asInterface(
                          ServiceManager.getService("notification"))
                      != null) {
                    ConstrainedModeService.this.updateConstrainedStateData(true);
                    z = true;
                  } else {
                    try {
                      Thread.sleep(1000L);
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }
                }
              }
            })
        .start();
  }

  public static void addService(Context context) {
    if (sConstrainedService == null) {
      synchronized (lock) {
        if (sConstrainedService == null) {
          ConstrainedModeService constrainedModeService = new ConstrainedModeService(context);
          sConstrainedService = constrainedModeService;
          LocalServices.addService(ConstrainedModeInternal.class, constrainedModeService);
        }
      }
    }
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public boolean checkConstrainedState() {
    List constrainedStateAll = getConstrainedStateAll();
    if (constrainedStateAll != null) {
      Iterator it = constrainedStateAll.iterator();
      while (it.hasNext()) {
        if (((EnrollData) it.next()).getConstrainedState() == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public final void updateNotification() {
    List<EnrollData> constrainedStateAll = getConstrainedStateAll();
    if (constrainedStateAll != null) {
      for (EnrollData enrollData : constrainedStateAll) {
        showConstrainedStateNotification(enrollData.getPackageName(), null, null, null, false);
        if (enrollData.getConstrainedState() == 0) {
          showConstrainedStateNotification(
              enrollData.getPackageName(),
              enrollData.getComment(),
              enrollData.getDownloadUrl(),
              enrollData.getTargetPkgName(),
              true);
        }
      }
    }
  }

  public void cleanUpConstrainedState(int i) {
    if (i != Binder.getCallingUid()) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.BIND_DEVICE_ADMIN", "Only system or itself can remove an EDM admin");
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("status", (Integer) 1);
    contentValues.put("adminUid", Integer.valueOf(i));
    if (this.mEdmStorageProvider.getValue("ConstrainedStateTable", "adminUid", contentValues)
        != null) {
      String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
      if (packageNameForUid != null) {
        disableConstrainedStateInternal(i, packageNameForUid);
        return;
      }
      return;
    }
    Log.d("ConstrainedMode", "constrained state will go on");
  }

  public final void showConstrainedStateNotification(
      String str, String str2, String str3, String str4, boolean z) {
    String str5 = str + "_ConstrainedNoti";
    NotificationManager notificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    if (notificationManager == null) {
      Log.d("ConstrainedMode", "Failed to get NotificationManager");
      return;
    }
    notificationManager.createNotificationChannel(
        new NotificationChannel("CONSTRAINED_MODE", str, 4));
    if (z) {
      Notification.Builder builder = new Notification.Builder(this.mContext, "CONSTRAINED_MODE");
      builder.setWhen(0L);
      builder.setSmallIcon(R.drawable.perm_group_aural);
      builder.setContentTitle(this.mContext.getString(R.string.face_acquired_too_right));
      if (str2.equals("DEFAULT")) {
        builder.setContentText(this.mContext.getString(R.string.font_family_menu_material));
        builder.setStyle(
            new Notification.BigTextStyle()
                .bigText(this.mContext.getString(R.string.font_family_menu_material)));
      } else {
        builder.setContentText(str2);
        builder.setStyle(new Notification.BigTextStyle().bigText(str2));
      }
      builder.setPriority(2);
      builder.setOngoing(true);
      if (str3 != null && str3.length() > 0) {
        Intent intent =
            new Intent("com.samsung.android.knox.intent.action.CHECK_REENROLLMENT_INTERNAL");
        intent.putExtra("pkg", str);
        intent.putExtra("url", str3);
        if (str4 != null && str4.length() > 0) {
          intent.putExtra("targetPkgName", str4);
        }
        builder.setContentIntent(
            PendingIntent.getBroadcast(this.mContext, str5.hashCode(), intent, 67108864));
      }
      notificationManager.notify(4558, builder.build());
      return;
    }
    notificationManager.cancel(4558);
  }

  public final boolean isMatch(byte[] bArr, byte[] bArr2, int i) {
    for (int i2 = 0; i2 < bArr.length; i2++) {
      int i3 = i + i2;
      if (i3 >= bArr2.length || bArr[i2] != bArr2[i3]) {
        return false;
      }
    }
    return true;
  }

  public final List split(byte[] bArr, byte[] bArr2) {
    LinkedList linkedList = new LinkedList();
    int i = 0;
    int i2 = 0;
    while (i < bArr2.length) {
      if (isMatch(bArr, bArr2, i)) {
        linkedList.add(Arrays.copyOfRange(bArr2, i2, i));
        i += bArr.length;
        i2 = i;
      }
      i++;
    }
    linkedList.add(Arrays.copyOfRange(bArr2, i2, bArr2.length));
    return linkedList;
  }

  /* JADX WARN: Removed duplicated region for block: B:58:0x018d A[Catch: Exception -> 0x02d5, RuntimeException -> 0x02d7, all -> 0x02f2, TryCatch #0 {all -> 0x02f2, blocks: (B:14:0x0048, B:16:0x004e, B:19:0x0294, B:20:0x0061, B:22:0x006d, B:24:0x0085, B:26:0x0282, B:28:0x009b, B:31:0x00aa, B:33:0x00b0, B:36:0x00b7, B:37:0x00be, B:39:0x00c4, B:41:0x00cd, B:43:0x00f0, B:45:0x00f7, B:47:0x0117, B:49:0x013f, B:50:0x0151, B:52:0x0157, B:56:0x0187, B:58:0x018d, B:59:0x01a5, B:61:0x01b6, B:63:0x01bc, B:64:0x01c0, B:66:0x01c6, B:69:0x01f7, B:72:0x01fd, B:74:0x021d, B:77:0x0233, B:78:0x0254, B:79:0x0243, B:82:0x0205, B:85:0x020b, B:92:0x0162, B:94:0x0169, B:95:0x017e, B:98:0x025e, B:99:0x0270, B:107:0x02de, B:109:0x02e5, B:103:0x02ee, B:104:0x02f1, B:135:0x02b4, B:137:0x02ba, B:140:0x02c5), top: B:2:0x0008 }] */
  /* JADX WARN: Removed duplicated region for block: B:66:0x01c6 A[Catch: Exception -> 0x02d5, RuntimeException -> 0x02d7, all -> 0x02f2, TryCatch #0 {all -> 0x02f2, blocks: (B:14:0x0048, B:16:0x004e, B:19:0x0294, B:20:0x0061, B:22:0x006d, B:24:0x0085, B:26:0x0282, B:28:0x009b, B:31:0x00aa, B:33:0x00b0, B:36:0x00b7, B:37:0x00be, B:39:0x00c4, B:41:0x00cd, B:43:0x00f0, B:45:0x00f7, B:47:0x0117, B:49:0x013f, B:50:0x0151, B:52:0x0157, B:56:0x0187, B:58:0x018d, B:59:0x01a5, B:61:0x01b6, B:63:0x01bc, B:64:0x01c0, B:66:0x01c6, B:69:0x01f7, B:72:0x01fd, B:74:0x021d, B:77:0x0233, B:78:0x0254, B:79:0x0243, B:82:0x0205, B:85:0x020b, B:92:0x0162, B:94:0x0169, B:95:0x017e, B:98:0x025e, B:99:0x0270, B:107:0x02de, B:109:0x02e5, B:103:0x02ee, B:104:0x02f1, B:135:0x02b4, B:137:0x02ba, B:140:0x02c5), top: B:2:0x0008 }] */
  /* JADX WARN: Removed duplicated region for block: B:76:0x0231  */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0251  */
  /* JADX WARN: Removed duplicated region for block: B:91:0x01a3  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateConstrainedStateData(boolean z) {
    FileInputStream fileInputStream;
    String str;
    HashMap hashMap;
    String str2;
    int i;
    int i2;
    String str3;
    HashMap hashMap2;
    File[] fileArr;
    int i3;
    File file;
    String str4;
    int i4;
    String str5;
    byte[] bArr;
    int i5;
    Iterator it;
    String str6 = "status";
    FileInputStream fileInputStream2 = null;
    try {
      try {
        HashMap hashMap3 = new HashMap();
        int i6 = 0;
        int i7 = 1;
        List valuesList =
            this.mEdmStorageProvider.getValuesList(
                "ConstrainedStateTable", new String[] {"adminUid", "status"}, null);
        File[] listFiles = new File("/efs/constrained").listFiles();
        String str7 = "ConstrainedMode";
        if (listFiles != null) {
          int length = listFiles.length;
          FileInputStream fileInputStream3 = null;
          int i8 = 0;
          while (i8 < length) {
            try {
              File file2 = listFiles[i8];
              if (file2.isFile()) {
                HashMap hashMap4 = hashMap3;
                byte[] bArr2 = new byte[(int) file2.length()];
                fileInputStream = new FileInputStream(file2);
                try {
                  if (fileInputStream.read(bArr2) == 0) {
                    fileInputStream.close();
                    str2 = str6;
                    i = i8;
                    i2 = length;
                    str3 = str7;
                    fileInputStream3 = fileInputStream;
                    fileArr = listFiles;
                    hashMap2 = hashMap4;
                  } else {
                    List split = split(CONSTRAINED_DELIMITER, bArr2);
                    String str8 =
                        split.get(i6) != null
                            ? new String(
                                (byte[]) split.get(i6), i6, ((byte[]) split.get(i6)).length)
                            : null;
                    if (str8 == null) {
                      Log.d(str7, "something's wrong // type is null");
                    } else {
                      if (!str8.equals("normal")
                          && !str8.equals("normalv2")
                          && !str8.equals("normalv3")) {
                        Log.d(str7, "type is not normal");
                      }
                      if (split.get(i7) == null || ((byte[]) split.get(i7)).length <= 0) {
                        file = file2;
                        i = i8;
                        str4 = null;
                      } else {
                        file = file2;
                        i = i8;
                        str4 =
                            new String((byte[]) split.get(i7), 0, ((byte[]) split.get(i7)).length);
                      }
                      if (str4 != null) {
                        if (split.get(2) != null) {
                          int i9 = length;
                          String str9 =
                              new String((byte[]) split.get(2), 0, ((byte[]) split.get(2)).length);
                          byte[] bArr3 = (byte[]) split.get(3);
                          if (bArr3 != null) {
                            fileArr = listFiles;
                            i4 =
                                ((bArr3[0] & 255) << 24)
                                    | ((bArr3[1] & 255) << 16)
                                    | ((bArr3[2] & 255) << 8)
                                    | (bArr3[3] & 255);
                          } else {
                            fileArr = listFiles;
                            i4 = 0;
                          }
                          if (i4 <= 0) {
                            Log.d(str7, "something's wrong");
                            str2 = str6;
                            str3 = str7;
                            hashMap2 = hashMap4;
                            i2 = i9;
                            i3 = 1;
                          } else {
                            if (!str8.equals("normalv2") && !str8.equals("normalv3")) {
                              str5 = null;
                              bArr = null;
                              String str10 =
                                  !str8.equals("normalv3")
                                      ? new String(
                                          (byte[]) split.get(6), 0, ((byte[]) split.get(6)).length)
                                      : null;
                              EnrollData enrollData = new EnrollData();
                              DevicePolicyManager devicePolicyManager =
                                  (DevicePolicyManager)
                                      this.mContext.getSystemService("device_policy");
                              if (valuesList != null && valuesList.size() > 0) {
                                it = valuesList.iterator();
                                while (it.hasNext()) {
                                  ContentValues contentValues = (ContentValues) it.next();
                                  int intValue = contentValues.getAsInteger("adminUid").intValue();
                                  int intValue2 = contentValues.getAsInteger(str6).intValue();
                                  String packageNameForUid =
                                      this.mEdmStorageProvider.getPackageNameForUid(intValue);
                                  str2 = str6;
                                  Iterator it2 = it;
                                  ComponentName componentName =
                                      new ComponentName(
                                          "com.sds.mobile.mdm.client.MDMService",
                                          "com.sds.mobile.mdm.client.MDMService.ui.receiver.DeviceAdminPolicyReceiver");
                                  if (intValue == 1000
                                      && devicePolicyManager != null
                                      && devicePolicyManager.isAdminActive(componentName)) {
                                    Log.d(str7, "Constrained mode is off - sds mdm was activated");
                                  } else if (packageNameForUid == null
                                      || !packageNameForUid.equals(str4)) {
                                    it = it2;
                                    str6 = str2;
                                  } else {
                                    Log.d(str7, "Constrained mode is off");
                                  }
                                  i5 = intValue2;
                                }
                              }
                              str2 = str6;
                              i5 = 0;
                              enrollData.setData(str4, str9, i4, str5, i5, bArr, str10);
                              i3 = 1;
                              if (z) {
                                str3 = str7;
                                i2 = i9;
                              } else if (i5 == 0) {
                                i2 = i9;
                                showConstrainedStateNotification(str4, str9, str5, str10, true);
                                str3 = str7;
                              } else {
                                str3 = str7;
                                i2 = i9;
                                showConstrainedStateNotification(str4, str9, str5, str10, false);
                              }
                              hashMap2 = hashMap4;
                              hashMap2.put(file.getName(), enrollData);
                            }
                            str5 =
                                split.get(4) != null
                                    ? new String(
                                        (byte[]) split.get(4), 0, ((byte[]) split.get(4)).length)
                                    : null;
                            bArr = (byte[]) split.get(5);
                            if (!str8.equals("normalv3")) {}
                            EnrollData enrollData2 = new EnrollData();
                            DevicePolicyManager devicePolicyManager2 =
                                (DevicePolicyManager)
                                    this.mContext.getSystemService("device_policy");
                            if (valuesList != null) {
                              it = valuesList.iterator();
                              while (it.hasNext()) {}
                            }
                            str2 = str6;
                            i5 = 0;
                            enrollData2.setData(str4, str9, i4, str5, i5, bArr, str10);
                            i3 = 1;
                            if (z) {}
                            hashMap2 = hashMap4;
                            hashMap2.put(file.getName(), enrollData2);
                          }
                        } else {
                          str2 = str6;
                          i2 = length;
                          str3 = str7;
                          fileArr = listFiles;
                          hashMap2 = hashMap4;
                          i3 = 1;
                          Log.d(str3, "comment is null");
                        }
                      } else {
                        str2 = str6;
                        i2 = length;
                        str3 = str7;
                        fileArr = listFiles;
                        hashMap2 = hashMap4;
                        i3 = 1;
                        Log.d(str3, "package name is null");
                      }
                      fileInputStream.close();
                      fileInputStream3 = null;
                      i8 = i + 1;
                      str7 = str3;
                      hashMap3 = hashMap2;
                      i7 = i3;
                      length = i2;
                      listFiles = fileArr;
                      str6 = str2;
                      i6 = 0;
                    }
                    str2 = str6;
                    i = i8;
                    i2 = length;
                    str3 = str7;
                    fileArr = listFiles;
                    hashMap2 = hashMap4;
                    i3 = i7;
                    fileInputStream.close();
                    fileInputStream3 = null;
                    i8 = i + 1;
                    str7 = str3;
                    hashMap3 = hashMap2;
                    i7 = i3;
                    length = i2;
                    listFiles = fileArr;
                    str6 = str2;
                    i6 = 0;
                  }
                } catch (RuntimeException e) {
                  e = e;
                  e.printStackTrace();
                  throw e;
                } catch (Exception e2) {
                  e = e2;
                  e.printStackTrace();
                  HashMap hashMap5 = this.mCachedConstrainedData;
                  if (hashMap5 != null) {
                    hashMap5.clear();
                  }
                  if (fileInputStream != null) {
                    fileInputStream.close();
                  }
                  return;
                }
              } else {
                str2 = str6;
                i = i8;
                i2 = length;
                str3 = str7;
                hashMap2 = hashMap3;
                fileArr = listFiles;
              }
              i3 = i7;
              i8 = i + 1;
              str7 = str3;
              hashMap3 = hashMap2;
              i7 = i3;
              length = i2;
              listFiles = fileArr;
              str6 = str2;
              i6 = 0;
            } catch (RuntimeException e3) {
              e = e3;
            } catch (Exception e4) {
              e = e4;
              fileInputStream = fileInputStream3;
            } catch (Throwable th) {
              th = th;
              fileInputStream2 = fileInputStream3;
              if (fileInputStream2 != null) {
                try {
                  fileInputStream2.close();
                } catch (Exception unused) {
                }
              }
              throw th;
            }
          }
          str = str7;
          hashMap = hashMap3;
          fileInputStream = fileInputStream3;
        } else {
          str = "ConstrainedMode";
          hashMap = hashMap3;
          fileInputStream = null;
        }
        if (hashMap.isEmpty()) {
          Log.i(str, "Data is null");
          this.mCachedConstrainedData.clear();
        } else {
          this.mCachedConstrainedData.clear();
          this.mCachedConstrainedData.putAll(hashMap);
        }
        if (fileInputStream == null) {
          return;
        }
      } catch (Throwable th2) {
        th = th2;
      }
    } catch (RuntimeException e5) {
      e = e5;
    } catch (Exception e6) {
      e = e6;
      fileInputStream = null;
    } catch (Throwable th3) {
      th = th3;
      fileInputStream2 = null;
    }
    try {
      fileInputStream.close();
    } catch (Exception unused2) {
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:51:0x0164 A[Catch: all -> 0x00c8, Exception -> 0x00cd, RuntimeException -> 0x00d1, TryCatch #9 {RuntimeException -> 0x00d1, Exception -> 0x00cd, all -> 0x00c8, blocks: (B:103:0x00b7, B:105:0x00bd, B:34:0x00f2, B:36:0x0103, B:38:0x0109, B:39:0x011a, B:41:0x0122, B:43:0x0128, B:45:0x0134, B:48:0x013c, B:49:0x015a, B:51:0x0164, B:53:0x016a, B:57:0x016e, B:59:0x0179, B:55:0x0173, B:62:0x018e, B:100:0x0111, B:31:0x00d7, B:33:0x00dd, B:101:0x00e8), top: B:102:0x00b7 }] */
  /* JADX WARN: Removed duplicated region for block: B:59:0x0179 A[Catch: all -> 0x00c8, Exception -> 0x00cd, RuntimeException -> 0x00d1, TryCatch #9 {RuntimeException -> 0x00d1, Exception -> 0x00cd, all -> 0x00c8, blocks: (B:103:0x00b7, B:105:0x00bd, B:34:0x00f2, B:36:0x0103, B:38:0x0109, B:39:0x011a, B:41:0x0122, B:43:0x0128, B:45:0x0134, B:48:0x013c, B:49:0x015a, B:51:0x0164, B:53:0x016a, B:57:0x016e, B:59:0x0179, B:55:0x0173, B:62:0x018e, B:100:0x0111, B:31:0x00d7, B:33:0x00dd, B:101:0x00e8), top: B:102:0x00b7 }] */
  /* JADX WARN: Removed duplicated region for block: B:65:0x01a1 A[Catch: all -> 0x01af, Exception -> 0x01b2, RuntimeException -> 0x01c2, TRY_LEAVE, TryCatch #10 {all -> 0x01af, blocks: (B:23:0x0062, B:24:0x0087, B:26:0x008e, B:28:0x0099, B:63:0x0194, B:65:0x01a1, B:68:0x01a8, B:78:0x01b3, B:74:0x01c3, B:75:0x01c6), top: B:22:0x0062 }] */
  /* JADX WARN: Removed duplicated region for block: B:68:0x01a8 A[Catch: all -> 0x01af, Exception -> 0x01b2, RuntimeException -> 0x01c2, TRY_ENTER, TRY_LEAVE, TryCatch #10 {all -> 0x01af, blocks: (B:23:0x0062, B:24:0x0087, B:26:0x008e, B:28:0x0099, B:63:0x0194, B:65:0x01a1, B:68:0x01a8, B:78:0x01b3, B:74:0x01c3, B:75:0x01c6), top: B:22:0x0062 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean enableConstrainedState(
      int i, String str, String str2, String str3, String str4, int i2) {
    Throwable th;
    byte[] bArr;
    PackageInfo packageInfo;
    String str5;
    if (i2 <= 0) {
      return false;
    }
    String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        try {
          File file = new File("/efs/constrained");
          if (!file.exists()) {
            file.mkdirs();
          }
          HashMap hashMap = this.mCachedConstrainedData;
          if (hashMap != null) {
            Iterator it = hashMap.entrySet().iterator();
            while (true) {
              if (!it.hasNext()) {
                break;
              }
              Map.Entry entry = (Map.Entry) it.next();
              if (((EnrollData) entry.getValue()).getPackageName().equals(packageNameForUid)) {
                Log.i("ConstrainedMode", "already existing values // update");
                new File("/efs/constrained", (String) entry.getKey()).delete();
                break;
              }
            }
          }
          FileOutputStream fileOutputStream = null;
          try {
            try {
              int hashCode = ("_" + packageNameForUid + str).hashCode() & Integer.MAX_VALUE;
              File file2 = new File("/efs/constrained", Integer.toString(hashCode));
              while (file2.exists()) {
                hashCode++;
                file2 = new File("/efs/constrained", Integer.toString(hashCode));
              }
              byte[] bArr2 = {(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
              FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
              if (str3 != null) {
                try {
                  if (str3.length() > 0) {
                    fileOutputStream2.write("normalv3".getBytes());
                    bArr = CONSTRAINED_DELIMITER;
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.write(packageNameForUid.getBytes());
                    fileOutputStream2.write(bArr);
                    if (str != null || str.length() <= 0) {
                      fileOutputStream2.write("DEFAULT".getBytes());
                    } else {
                      fileOutputStream2.write(str.getBytes());
                    }
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.write(bArr2);
                    if (str2 != null && str2.length() > 0) {
                      fileOutputStream2.write(bArr);
                      fileOutputStream2.write(str2.getBytes());
                      if (str3 != null || str3.length() <= 0 || str4 == null) {
                        packageInfo =
                            this.mPackageManagerAdapter.getPackageInfo(packageNameForUid, 64, 0);
                        if (packageInfo != null) {
                          for (Signature signature : packageInfo.signatures) {
                            if (signature != null) {
                              str5 = signature.toCharsString();
                              break;
                            }
                          }
                        }
                        str5 = null;
                        if (str5 != null) {
                          byte[] byteArray = new Signature(str5.getBytes()).toByteArray();
                          fileOutputStream2.write(CONSTRAINED_DELIMITER);
                          fileOutputStream2.write(byteArray);
                        }
                      } else {
                        byte[] byteArray2 = new Signature(str4.getBytes()).toByteArray();
                        fileOutputStream2.write(bArr);
                        fileOutputStream2.write(byteArray2);
                        fileOutputStream2.write(bArr);
                        fileOutputStream2.write(str3.getBytes());
                      }
                    }
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    if (this.mEdmStorageProvider.putInt(i, "ConstrainedStateTable", "status", 1)) {
                      file2.delete();
                      return false;
                    }
                    updateConstrainedStateData(true);
                    return true;
                  }
                } catch (RuntimeException e) {
                  e = e;
                  e.printStackTrace();
                  throw e;
                } catch (Exception e2) {
                  e = e2;
                  fileOutputStream = fileOutputStream2;
                  e.printStackTrace();
                  if (fileOutputStream != null) {
                    try {
                      fileOutputStream.close();
                    } catch (Exception e3) {
                      e3.printStackTrace();
                    }
                  }
                  return false;
                } catch (Throwable th2) {
                  th = th2;
                  fileOutputStream = fileOutputStream2;
                  if (fileOutputStream == null) {
                    throw th;
                  }
                  try {
                    fileOutputStream.close();
                    throw th;
                  } catch (Exception e4) {
                    e4.printStackTrace();
                    throw th;
                  }
                }
              }
              if (str2 == null || str2.length() <= 0) {
                fileOutputStream2.write("normal".getBytes());
              } else {
                fileOutputStream2.write("normalv2".getBytes());
              }
              bArr = CONSTRAINED_DELIMITER;
              fileOutputStream2.write(bArr);
              fileOutputStream2.write(packageNameForUid.getBytes());
              fileOutputStream2.write(bArr);
              if (str != null) {}
              fileOutputStream2.write("DEFAULT".getBytes());
              fileOutputStream2.write(bArr);
              fileOutputStream2.write(bArr2);
              if (str2 != null) {
                fileOutputStream2.write(bArr);
                fileOutputStream2.write(str2.getBytes());
                if (str3 != null) {}
                packageInfo = this.mPackageManagerAdapter.getPackageInfo(packageNameForUid, 64, 0);
                if (packageInfo != null) {}
                str5 = null;
                if (str5 != null) {}
              }
              fileOutputStream2.flush();
              fileOutputStream2.close();
              if (this.mEdmStorageProvider.putInt(i, "ConstrainedStateTable", "status", 1)) {}
            } catch (Throwable th3) {
              th = th3;
            }
          } catch (RuntimeException e5) {
            e = e5;
          } catch (Exception e6) {
            e = e6;
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (RuntimeException e7) {
        e7.printStackTrace();
        throw e7;
      }
    } catch (Exception e8) {
      e8.printStackTrace();
    }
  }

  public final boolean disableConstrainedStateInternal(int i, String str) {
    boolean z;
    boolean z2;
    boolean isRestrictedByConstrainedState = getEDM().isRestrictedByConstrainedState(64);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z3 = true;
    try {
      try {
        HashMap hashMap = this.mCachedConstrainedData;
        if (hashMap != null) {
          for (Map.Entry entry : hashMap.entrySet()) {
            if (((EnrollData) entry.getValue()).getPackageName().equals(str)) {
              Log.i("ConstrainedMode", "remove! " + ((String) entry.getKey()));
              new File("/efs/constrained", (String) entry.getKey()).delete();
              this.mEdmStorageProvider.putInt(i, "ConstrainedStateTable", "status", 1);
              showConstrainedStateNotification(str, null, null, null, false);
              z2 = true;
              break;
            }
          }
        }
        z2 = false;
        if (z2) {
          updateConstrainedStateData(true);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = true;
      } catch (Exception e) {
        e.printStackTrace();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = false;
      }
      if (isRestrictedByConstrainedState
          && getEDM().getRestrictionPolicy() != null
          && getEDM().getRestrictionPolicy().isScreenCaptureEnabledInternal(false)) {
        getEDM().getRestrictionPolicy().setScreenCapture(true);
        Log.d("ConstrainedMode", "setScreenCapture enabled due to disableConstrainedState");
      }
      boolean z4 = !isRestrictedByConstrainedState(8);
      boolean isBluetoothAllowedOnDB = isBluetoothAllowedOnDB();
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        ISystemUIAdapter iSystemUIAdapter =
            (ISystemUIAdapter) AdapterRegistry.getAdapter(ISystemUIAdapter.class);
        if (!isBluetoothAllowedOnDB || !z4) {
          z3 = false;
        }
        iSystemUIAdapter.setBluetoothAllowedAsUser(0, z3);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Log.i("ConstrainedMode", "No data");
        return z;
      } finally {
      }
    } finally {
    }
  }

  public final boolean isBluetoothAllowedOnDB() {
    ArrayList booleanList =
        this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "bluetoothEnabled");
    if (booleanList == null) {
      return true;
    }
    Iterator it = booleanList.iterator();
    while (it.hasNext()) {
      if (!((Boolean) it.next()).booleanValue()) {
        return false;
      }
    }
    return true;
  }

  public boolean disableConstrainedState(int i) {
    return disableConstrainedStateInternal(i, this.mEdmStorageProvider.getPackageNameForUid(i));
  }

  public final List getConstrainedStateAll() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (this.mCachedConstrainedData != null) {
          ArrayList arrayList = new ArrayList();
          Iterator it = this.mCachedConstrainedData.entrySet().iterator();
          while (it.hasNext()) {
            arrayList.add((EnrollData) ((Map.Entry) it.next()).getValue());
          }
          if (!arrayList.isEmpty()) {
            return arrayList;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isRestrictedByConstrainedState(int i) {
    List<EnrollData> constrainedStateAll = getConstrainedStateAll();
    if (constrainedStateAll == null) {
      return false;
    }
    for (EnrollData enrollData : constrainedStateAll) {
      if (enrollData.getConstrainedState() == 0 && (enrollData.getPolicyBitMask() & i) > 0) {
        return true;
      }
    }
    return false;
  }

  public int getConstrainedState() {
    List constrainedStateAll = getConstrainedStateAll();
    if (constrainedStateAll == null) {
      return 0;
    }
    Iterator it = constrainedStateAll.iterator();
    while (it.hasNext()) {
      if (((EnrollData) it.next()).getConstrainedState() == 0) {
        return 2;
      }
    }
    return 1;
  }
}
