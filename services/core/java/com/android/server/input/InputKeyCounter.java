package com.android.server.input;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InputKeyCounter {
    public static final boolean DEBUG =
            !"true".equals(SystemProperties.get("ro.product_ship", "false"));
    public final HwKeyCount mCurrentKeyCount = new HwKeyCount();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HwKeyCount {
        public final ArrayMap mKeyCountMap = new ArrayMap();
        public long mAllKeyCount = 0;

        public final void add(int i, int i2) {
            synchronized (this.mKeyCountMap) {
                try {
                    this.mKeyCountMap.put(Integer.valueOf(i), Integer.valueOf(i2));
                    this.mAllKeyCount = i2;
                    if (InputKeyCounter.DEBUG) {
                        Log.d("InputKeyCounter", "Add keyCode: " + i + ", currentCount:" + i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final ArrayMap getKeyCountMap() {
            ArrayMap arrayMap = new ArrayMap();
            synchronized (this.mKeyCountMap) {
                arrayMap.putAll(this.mKeyCountMap);
            }
            return arrayMap;
        }
    }

    public static void sendBroadcastKeyCountInternal(Context context, ArrayMap arrayMap) {
        if (arrayMap.size() == 0) {
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
                        "InputKeyCounter",
                        "Sendbroadcast keycount - cvs: " + contentValuesArr[i2].toString());
            }
        }
    }
}
