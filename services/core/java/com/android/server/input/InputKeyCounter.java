package com.android.server.input;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.Map;

/* loaded from: classes2.dex */
public class InputKeyCounter {
  public static final boolean DEBUG;
  public static final boolean SHIP_BUILD;
  public final HwKeyCount mCurrentKeyCount = new HwKeyCount();

  static {
    boolean equals = "true".equals(SystemProperties.get("ro.product_ship", "false"));
    SHIP_BUILD = equals;
    DEBUG = !equals;
  }

  public void increaseCount(int i) {
    this.mCurrentKeyCount.add(i);
  }

  public long getAllKeyCount() {
    return this.mCurrentKeyCount.getAllKeyCount();
  }

  public void saveCount() {
    ArrayMap keyCountMap = this.mCurrentKeyCount.getKeyCountMap();
    String str = new String();
    for (Map.Entry entry : keyCountMap.entrySet()) {
      str =
          str.concat(Integer.toString(((Integer) entry.getKey()).intValue()))
              .concat(",")
              .concat(Integer.toString(((Integer) entry.getValue()).intValue()))
              .concat("/");
    }
    if (DEBUG) {
      Log.i("InputKeyCounter", "saveCount - concat data :" + str);
    }
    SystemProperties.set("persist.service.bgkeycount", str);
  }

  public void sendBroadcastKeyCount(Context context) {
    ArrayMap keyCountMap = this.mCurrentKeyCount.getKeyCountMap();
    this.mCurrentKeyCount.clearKeyCount();
    sendBroadcastKeyCountInternal(context, keyCountMap);
  }

  public final void sendBroadcastKeyCountInternal(Context context, ArrayMap arrayMap) {
    if (arrayMap == null || arrayMap.size() == 0) {
      Log.i("InputKeyCounter", "No map object");
      return;
    }
    int size = arrayMap.size();
    ContentValues[] contentValuesArr = new ContentValues[size];
    for (int i = 0; i < size; i++) {
      ContentValues contentValues = new ContentValues();
      contentValuesArr[i] = contentValues;
      contentValues.put("app_id", "com.android.server.input");
      contentValuesArr[i].put(LauncherConfigurationInternal.KEY_FEATURE_INT, "BKCS");
      contentValuesArr[i].put("extra", (Integer) arrayMap.keyAt(i));
      contentValuesArr[i].put("value", (Integer) arrayMap.valueAt(i));
    }
    Intent intent = new Intent();
    intent.setAction(
        "com.samsung.android.providers.context.log.action.USE_MULTI_APP_FEATURE_SURVEY");
    intent.putExtra("data", contentValuesArr);
    intent.setPackage("com.samsung.android.providers.context");
    context.sendBroadcast(intent);
    if (DEBUG) {
      Log.d("InputKeyCounter", "Sendbroadcast keycount - lenght: " + arrayMap.size());
      for (int i2 = 0; i2 < size; i2++) {
        Log.d(
            "InputKeyCounter", "Sendbroadcast keycount - cvs: " + contentValuesArr[i2].toString());
      }
    }
  }

  public void kickOldies(Context context) {
    if (DEBUG) {
      Log.i("InputKeyCounter", "read old data!");
    }
    String[] split = SystemProperties.get("persist.service.bgkeycount", "null").split("/");
    HwKeyCount hwKeyCount = new HwKeyCount();
    try {
      for (String str : split) {
        if (DEBUG) {
          Log.d("InputKeyCounter", "read old saved keycount strings = " + str);
        }
        String[] split2 = str.split(",");
        if (split2.length != 2) {
          Log.w("InputKeyCounter", "throw up the data!");
          return;
        }
        hwKeyCount.add(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
      }
    } catch (NumberFormatException unused) {
      Log.e("InputKeyCounter", "NumberFormatException, throw up the data!");
      SystemProperties.set("persist.service.bgkeycount", "");
    }
    sendBroadcastKeyCountInternal(context, hwKeyCount.getKeyCountMap());
    SystemProperties.set("persist.service.bgkeycount", "");
  }

  public class HwKeyCount {
    public long mAllKeyCount;
    public ArrayMap mKeyCountMap;

    public HwKeyCount() {
      this.mKeyCountMap = new ArrayMap();
      this.mAllKeyCount = 0L;
    }

    public void add(int i) {
      synchronized (this.mKeyCountMap) {
        if (this.mKeyCountMap.containsKey(Integer.valueOf(i))) {
          int intValue = ((Integer) this.mKeyCountMap.get(Integer.valueOf(i))).intValue();
          this.mKeyCountMap.remove(Integer.valueOf(i));
          int i2 = intValue + 1;
          this.mKeyCountMap.put(Integer.valueOf(i), Integer.valueOf(i2));
          if (InputKeyCounter.DEBUG) {
            Log.d("InputKeyCounter", "Add keyCode: " + i + ", currentCount= " + i2);
          }
        } else {
          this.mKeyCountMap.put(Integer.valueOf(i), 1);
          if (InputKeyCounter.DEBUG) {
            Log.d("InputKeyCounter", "Add keyCode: " + i + ", currentCount: 1");
          }
        }
        this.mAllKeyCount++;
      }
    }

    public void add(int i, int i2) {
      synchronized (this.mKeyCountMap) {
        this.mKeyCountMap.put(Integer.valueOf(i), Integer.valueOf(i2));
        this.mAllKeyCount = i2;
        if (InputKeyCounter.DEBUG) {
          Log.d("InputKeyCounter", "Add keyCode: " + i + ", currentCount:" + i2);
        }
      }
    }

    public ArrayMap getKeyCountMap() {
      ArrayMap arrayMap = new ArrayMap();
      synchronized (this.mKeyCountMap) {
        arrayMap.putAll(this.mKeyCountMap);
      }
      return arrayMap;
    }

    public long getAllKeyCount() {
      long j;
      synchronized (this.mKeyCountMap) {
        j = this.mAllKeyCount;
      }
      if (InputKeyCounter.DEBUG) {
        Log.d("InputKeyCounter", "getAllKeyCount: " + j);
      }
      return j;
    }

    public void clearKeyCount() {
      synchronized (this.mKeyCountMap) {
        this.mKeyCountMap.clear();
        this.mAllKeyCount = 0L;
      }
    }
  }
}
