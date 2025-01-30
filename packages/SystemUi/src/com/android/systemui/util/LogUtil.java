package com.android.systemui.util;

import android.util.Log;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.LongConsumer;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class LogUtil {
    public static final Map beginTimes = Collections.synchronizedMap(new LinkedHashMap<Integer, Long>() { // from class: com.android.systemui.util.LogUtil$beginTimes$1
        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof Integer) {
                return super.containsKey((Integer) obj);
            }
            return false;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsValue(Object obj) {
            if (obj instanceof Long) {
                return super.containsValue((Long) obj);
            }
            return false;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Object get(Object obj) {
            if (obj instanceof Integer) {
                return (Long) super.get((Integer) obj);
            }
            return null;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
        public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
            return !(obj instanceof Integer) ? obj2 : (Long) super.getOrDefault((Integer) obj, (Long) obj2);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Object remove(Object obj) {
            if (obj instanceof Integer) {
                return (Long) super.remove((Integer) obj);
            }
            return null;
        }

        @Override // java.util.LinkedHashMap
        public final boolean removeEldestEntry(Map.Entry<Integer, Long> entry) {
            return super.size() > 10;
        }

        @Override // java.util.HashMap, java.util.Map
        public final /* bridge */ boolean remove(Object obj, Object obj2) {
            if ((obj instanceof Integer) && (obj2 instanceof Long)) {
                return super.remove((Integer) obj, (Long) obj2);
            }
            return false;
        }
    });
    public static final boolean isDebugLevelHigh;
    public static final boolean isDebugLevelMid;

    static {
        isDebugLevelMid = DeviceType.getDebugLevel() == 1;
        isDebugLevelHigh = DeviceType.getDebugLevel() == 2;
    }

    /* renamed from: d */
    public static final void m223d(String str, String str2, Object... objArr) {
        Log.d(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }

    /* renamed from: dm */
    public static final void m224dm(String str, Object... objArr) {
        if (isDebugLevelMid || isDebugLevelHigh) {
            Log.d("NotificationPanelView", getMsg(str, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public static final void endTime(int i, String str, String str2, Object... objArr) {
        internalEndTime(i, 0, null, str, str2, Arrays.copyOf(objArr, objArr.length));
    }

    public static final String getCaller() {
        String str;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("   ");
            int i2 = i + 4;
            if (i2 >= stackTrace.length) {
                str = "<bottom of call stack>";
            } else {
                StackTraceElement stackTraceElement = stackTrace[i2];
                str = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
            }
            sb.append(str);
            sb.append('\n');
        }
        return sb.toString();
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

    /* renamed from: i */
    public static final void m225i(String str, String str2, Object... objArr) {
        Log.i(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }

    public static final void internalEndTime(int i, int i2, LongConsumer longConsumer, String str, String str2, Object... objArr) {
        if (((Long) beginTimes.remove(Integer.valueOf(i))) != null) {
            long nanoTime = (long) ((System.nanoTime() - r4.longValue()) / 1000000.0d);
            if (nanoTime >= i2 || i2 == 0 || longConsumer != null) {
                if (str2 != null) {
                    if (!(objArr.length == 0)) {
                        int i3 = StringCompanionObject.$r8$clinit;
                        Locale locale = Locale.US;
                        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                        str2 = String.format(locale, str2, Arrays.copyOf(copyOf, copyOf.length));
                    }
                }
                if (str != null && str2 != null) {
                    m226w(str, str2 + " / elapsed time: " + nanoTime + "ms", new Object[0]);
                }
                if (longConsumer != null) {
                    longConsumer.accept(nanoTime);
                }
            }
        }
    }

    public static final void internalLapTime(int i, LongConsumer longConsumer, String str, String str2, Object... objArr) {
        Long l = (Long) beginTimes.get(Integer.valueOf(i));
        if (l != null) {
            long longValue = l.longValue();
            if ((!(objArr.length == 0)) && str2 != null) {
                int i2 = StringCompanionObject.$r8$clinit;
                Locale locale = Locale.US;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                str2 = String.format(locale, str2, Arrays.copyOf(copyOf, copyOf.length));
            }
            long nanoTime = (long) ((System.nanoTime() - longValue) / 1000000.0d);
            if (str != null) {
                m226w(str, ((Object) str2) + " / lap time: " + nanoTime + "ms", new Object[0]);
            }
            if (longConsumer != null) {
                longConsumer.accept(nanoTime);
            }
        }
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

    public static final int startTime(int i) {
        Map map = beginTimes;
        if (i < 0) {
            i = 0;
            while (map.containsKey(Integer.valueOf(i))) {
                i++;
            }
        }
        map.put(Integer.valueOf(i), Long.valueOf(System.nanoTime()));
        return i;
    }

    /* renamed from: w */
    public static final void m226w(String str, String str2, Object... objArr) {
        Log.w(str, getMsg(str2, Arrays.copyOf(objArr, objArr.length)));
    }
}
