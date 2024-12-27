package com.android.systemui.util;

import android.os.Looper;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.LongConsumer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public final class LogUtil {
    private static final double DIVIDE_TIME = 1000000.0d;
    private static final int MAX_ENTRIES = 10;
    private static final String TAG = "LogUtil";
    private static final Map<Integer, Long> beginTimes = Collections.synchronizedMap(new LinkedHashMap<Integer, Long>() { // from class: com.android.systemui.util.LogUtil$beginTimes$1
        public /* bridge */ boolean containsKey(Integer num) {
            return super.containsKey((Object) num);
        }

        public /* bridge */ boolean containsValue(Long l) {
            return super.containsValue((Object) l);
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Set<Map.Entry<Integer, Long>> entrySet() {
            return getEntries();
        }

        public /* bridge */ Long get(Integer num) {
            return (Long) super.get((Object) num);
        }

        public /* bridge */ Set<Map.Entry<Integer, Long>> getEntries() {
            return super.entrySet();
        }

        public /* bridge */ Set<Integer> getKeys() {
            return super.keySet();
        }

        public /* bridge */ Long getOrDefault(Integer num, Long l) {
            return (Long) super.getOrDefault((Object) num, (Integer) l);
        }

        public /* bridge */ int getSize() {
            return super.size();
        }

        public /* bridge */ Collection<Long> getValues() {
            return super.values();
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Set<Integer> keySet() {
            return getKeys();
        }

        public /* bridge */ Long remove(Integer num) {
            return (Long) super.remove((Object) num);
        }

        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry<Integer, Long> entry) {
            return size() > 10;
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Collection<Long> values() {
            return getValues();
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof Integer) {
                return containsKey((Integer) obj);
            }
            return false;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsValue(Object obj) {
            if (obj instanceof Long) {
                return containsValue((Long) obj);
            }
            return false;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Long get(Object obj) {
            if (obj instanceof Integer) {
                return get((Integer) obj);
            }
            return null;
        }

        public final /* bridge */ Long getOrDefault(Object obj, Long l) {
            return !(obj instanceof Integer) ? l : getOrDefault((Integer) obj, l);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Long remove(Object obj) {
            if (obj instanceof Integer) {
                return remove((Integer) obj);
            }
            return null;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            if (obj instanceof Integer) {
                return get((Integer) obj);
            }
            return null;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
        public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
            return !(obj instanceof Integer) ? obj2 : getOrDefault((Integer) obj, (Long) obj2);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ /* synthetic */ Object remove(Object obj) {
            if (obj instanceof Integer) {
                return remove((Integer) obj);
            }
            return null;
        }

        public /* bridge */ boolean remove(Integer num, Long l) {
            return super.remove((Object) num, (Object) l);
        }

        @Override // java.util.HashMap, java.util.Map
        public final /* bridge */ boolean remove(Object obj, Object obj2) {
            if ((obj instanceof Integer) && (obj2 instanceof Long)) {
                return remove((Integer) obj, (Long) obj2);
            }
            return false;
        }
    });
    private static final boolean isDebugLevelHigh;
    private static final boolean isDebugLevelMid;

    static {
        isDebugLevelMid = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID;
        isDebugLevelHigh = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_HIGH;
    }

    public static final void checkIfMainThread(String str, String str2) {
        if (Intrinsics.areEqual(Looper.getMainLooper(), Looper.myLooper())) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            str = TAG;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "not main thread";
        }
        Log.w(str, str2);
        new Exception().printStackTrace(System.out);
    }

    public static final void d(String str, String str2, Object... objArr) {
        Log.d(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }

    public static final void dm(String str, String str2, Object... objArr) {
        if (isDebugLevelMid || isDebugLevelHigh) {
            Log.d(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    private static final long elapsedTime(long j) {
        return (long) ((System.nanoTime() - j) / DIVIDE_TIME);
    }

    public static final void endTime(int i, String str, String str2, Object... objArr) {
        internalEndTime(i, 0, null, str, str2, Arrays.copyOf(objArr, objArr.length));
    }

    public static final void execTime(Runnable runnable, int i, String str, String str2, Object... objArr) {
        int startTime = startTime(-1);
        runnable.run();
        endTime(startTime, i, str, str2, objArr);
    }

    public static final String getCaller(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("   ");
            Intrinsics.checkNotNull(stackTrace);
            sb.append(getCaller(stackTrace, i2));
            sb.append('\n');
        }
        return sb.toString();
    }

    public static final int getInt(Object obj) {
        return obj != null ? 1 : 0;
    }

    public static final String getMsg(String str, Object... objArr) {
        if (str == null) {
            return "";
        }
        if (!(!(objArr.length == 0))) {
            return str;
        }
        int i = StringCompanionObject.$r8$clinit;
        Locale locale = Locale.US;
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
        return String.format(locale, str, Arrays.copyOf(copyOf, copyOf.length));
    }

    private static final int getNextKey() {
        int i = 0;
        while (beginTimes.containsKey(Integer.valueOf(i))) {
            i++;
        }
        return i;
    }

    public static final void i(String str, String str2, Object... objArr) {
        Log.i(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }

    private static final void internalEndTime(int i, int i2, LongConsumer longConsumer, String str, String str2, Object... objArr) {
        Long remove = beginTimes.remove(Integer.valueOf(i));
        if (remove != null) {
            long elapsedTime = elapsedTime(remove.longValue());
            if (elapsedTime >= i2 || i2 == 0 || longConsumer != null) {
                if (str2 != null) {
                    if (!(objArr.length == 0)) {
                        int i3 = StringCompanionObject.$r8$clinit;
                        Locale locale = Locale.US;
                        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                        str2 = String.format(locale, str2, Arrays.copyOf(copyOf, copyOf.length));
                    }
                }
                if (str != null && str2 != null) {
                    w(str, str2 + " / elapsed time: " + elapsedTime + "ms", new Object[0]);
                }
                if (longConsumer != null) {
                    longConsumer.accept(elapsedTime);
                }
            }
        }
    }

    private static final void internalLapTime(int i, LongConsumer longConsumer, String str, String str2, Object... objArr) {
        Long l = beginTimes.get(Integer.valueOf(i));
        if (l != null) {
            long longValue = l.longValue();
            if ((!(objArr.length == 0)) && str2 != null) {
                int i2 = StringCompanionObject.$r8$clinit;
                Locale locale = Locale.US;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                str2 = String.format(locale, str2, Arrays.copyOf(copyOf, copyOf.length));
            }
            long elapsedTime = elapsedTime(longValue);
            if (str != null) {
                w(str, ((Object) str2) + " / lap time: " + elapsedTime + "ms", new Object[0]);
            }
            if (longConsumer != null) {
                longConsumer.accept(elapsedTime);
            }
        }
    }

    private static final int internalStartTime(int i, String str, String str2, Object... objArr) {
        if (i < 0) {
            i = getNextKey();
        }
        beginTimes.put(Integer.valueOf(i), Long.valueOf(System.nanoTime()));
        if (str != null && str2 != null) {
            if (!(objArr.length == 0)) {
                int i2 = StringCompanionObject.$r8$clinit;
                Locale locale = Locale.US;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                str2 = String.format(locale, str2, Arrays.copyOf(copyOf, copyOf.length));
            }
            d(str, str2.concat(" / started"), new Object[0]);
        }
        return i;
    }

    public static final boolean isDebugLevelHigh() {
        return isDebugLevelHigh;
    }

    public static final boolean isDebugLevelMid() {
        return isDebugLevelMid;
    }

    public static final boolean isTraceEnabled() {
        return Trace.isTagEnabled(4096L);
    }

    public static final void lapTime(int i, String str, String str2, Object... objArr) {
        internalLapTime(i, null, str, str2, Arrays.copyOf(objArr, objArr.length));
    }

    public static final String makeDateTimeStr(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return getMsg("%02d-%02d %02d:%02d:%02d.%03d", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)));
    }

    public static final String makeTimeStr(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return getMsg("%02d:%02d:%02d.%03d", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)));
    }

    public static final void runInDebugLevelMidOnly(Runnable runnable) {
        if ((isDebugLevelMid || isDebugLevelHigh) && runnable != null) {
            runnable.run();
        }
    }

    public static final int startTime(int i) {
        return internalStartTime(i, null, null, new Object[0]);
    }

    public static final void traceBegin(String str, int i) {
        Trace.asyncTraceBegin(4096L, str, i);
    }

    public static final void traceEnd(String str, int i) {
        Trace.asyncTraceEnd(4096L, str, i);
    }

    public static final void w(String str, String str2, Object... objArr) {
        Log.w(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }

    public static final void endTime(int i, LongConsumer longConsumer) {
        internalEndTime(i, 0, longConsumer, null, null, new Object[0]);
    }

    public static final int getInt(boolean z) {
        return z ? 1 : 0;
    }

    public static final void lapTime(int i, LongConsumer longConsumer) {
        internalLapTime(i, longConsumer, null, null, new Object[0]);
    }

    public static final int startTime(int i, String str, String str2, Object... objArr) {
        return internalStartTime(i, str, str2, Arrays.copyOf(objArr, objArr.length));
    }

    public static final void endTime(int i, int i2, String str, String str2, Object... objArr) {
        internalEndTime(i, i2, null, str, str2, Arrays.copyOf(objArr, objArr.length));
    }

    public static final String getMsg(long j, String str, Object... objArr) {
        if (str == null) {
            return "";
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(makeTimeStr(j), " ", getMsg(str, Arrays.copyOf(objArr, objArr.length)));
    }

    private static final String getCaller(StackTraceElement[] stackTraceElementArr, int i) {
        int i2 = i + 4;
        if (i2 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = stackTraceElementArr[i2];
        return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }
}
