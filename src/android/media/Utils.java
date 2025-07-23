package android.media;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.Utils;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.vibrator.persistence.VibrationXmlParser;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import com.android.internal.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class Utils {
    public static final String SYNCHRONIZED_VIBRATION = "synchronized";
    private static final String TAG = "Utils";
    public static final String VIBRATION_URI_PARAM = "vibration_uri";

    public static <T extends Comparable<? super T>> void sortDistinctRanges(Range<T>[] rangeArr) {
        Arrays.sort(rangeArr, new Comparator<Range<T>>() { // from class: android.media.Utils.1
            @Override // java.util.Comparator
            public int compare(Range<T> range, Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                if (range.getLower().compareTo(range2.getUpper()) > 0) {
                    return 1;
                }
                throw new IllegalArgumentException("sample rate ranges must be distinct (" + range + " and " + range2 + NavigationBarInflaterView.KEY_CODE_END);
            }
        });
    }

    public static <T extends Comparable<? super T>> Range<T>[] intersectSortedDistinctRanges(Range<T>[] rangeArr, Range<T>[] rangeArr2) {
        Vector vector = new Vector();
        int i = 0;
        for (Range<T> range : rangeArr2) {
            while (i < rangeArr.length && rangeArr[i].getUpper().compareTo(range.getLower()) < 0) {
                i++;
            }
            while (i < rangeArr.length && rangeArr[i].getUpper().compareTo(range.getUpper()) < 0) {
                vector.add(range.intersect(rangeArr[i]));
                i++;
            }
            if (i == rangeArr.length) {
                break;
            }
            if (rangeArr[i].getLower().compareTo(range.getUpper()) <= 0) {
                vector.add(range.intersect(rangeArr[i]));
            }
        }
        return (Range[]) vector.toArray(new Range[vector.size()]);
    }

    public static <T extends Comparable<? super T>> int binarySearchDistinctRanges(Range<T>[] rangeArr, T t) {
        return Arrays.binarySearch(rangeArr, Range.create(t, t), new Comparator<Range<T>>() { // from class: android.media.Utils.2
            @Override // java.util.Comparator
            public int compare(Range<T> range, Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                return range.getLower().compareTo(range2.getUpper()) > 0 ? 1 : 0;
            }
        });
    }

    static int gcd(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return 1;
        }
        if (i2 < 0) {
            i2 = -i2;
        }
        if (i < 0) {
            i = -i;
        }
        while (i != 0) {
            int i3 = i2 % i;
            i2 = i;
            i = i3;
        }
        return i2;
    }

    static Range<Integer> factorRange(Range<Integer> range, int i) {
        return i == 1 ? range : Range.create(Integer.valueOf(divUp(range.getLower().intValue(), i)), Integer.valueOf(range.getUpper().intValue() / i));
    }

    static Range<Long> factorRange(Range<Long> range, long j) {
        return j == 1 ? range : Range.create(Long.valueOf(divUp(range.getLower().longValue(), j)), Long.valueOf(range.getUpper().longValue() / j));
    }

    private static Rational scaleRatio(Rational rational, int i, int i2) {
        int gcd = gcd(i, i2);
        return new Rational((int) (rational.getNumerator() * (i / gcd)), (int) (rational.getDenominator() * (i2 / gcd)));
    }

    static Range<Rational> scaleRange(Range<Rational> range, int i, int i2) {
        return i == i2 ? range : Range.create(scaleRatio(range.getLower(), i, i2), scaleRatio(range.getUpper(), i, i2));
    }

    static Range<Integer> alignRange(Range<Integer> range, int i) {
        return range.intersect(Integer.valueOf(divUp(range.getLower().intValue(), i) * i), Integer.valueOf((range.getUpper().intValue() / i) * i));
    }

    static int divUp(int i, int i2) {
        return ((i + i2) - 1) / i2;
    }

    static long divUp(long j, long j2) {
        return ((j + j2) - 1) / j2;
    }

    private static long lcm(int i, int i2) {
        if (i == 0 || i2 == 0) {
            throw new IllegalArgumentException("lce is not defined for zero arguments");
        }
        return (i * i2) / gcd(i, i2);
    }

    static Range<Integer> intRangeFor(double d) {
        return Range.create(Integer.valueOf((int) d), Integer.valueOf((int) Math.ceil(d)));
    }

    static Range<Long> longRangeFor(double d) {
        return Range.create(Long.valueOf((long) d), Long.valueOf((long) Math.ceil(d)));
    }

    static Size parseSize(Object obj, Size size) {
        if (obj == null) {
            return size;
        }
        try {
            return Size.parseSize((String) obj);
        } catch (ClassCastException | NumberFormatException unused) {
            Log.w(TAG, "could not parse size '" + obj + "'");
            return size;
        }
    }

    static int parseIntSafely(Object obj, int i) {
        if (obj == null) {
            return i;
        }
        try {
            return Integer.parseInt((String) obj);
        } catch (ClassCastException | NumberFormatException unused) {
            Log.w(TAG, "could not parse integer '" + obj + "'");
            return i;
        }
    }

    static Range<Integer> parseIntRange(Object obj, Range<Integer> range) {
        if (obj == null) {
            return range;
        }
        try {
            String str = (String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return Range.create(Integer.valueOf(Integer.parseInt(str.substring(0, indexOf), 10)), Integer.valueOf(Integer.parseInt(str.substring(indexOf + 1), 10)));
            }
            int parseInt = Integer.parseInt(str);
            return Range.create(Integer.valueOf(parseInt), Integer.valueOf(parseInt));
        } catch (ClassCastException | NumberFormatException | IllegalArgumentException unused) {
            Log.w(TAG, "could not parse integer range '" + obj + "'");
            return range;
        }
    }

    static Range<Long> parseLongRange(Object obj, Range<Long> range) {
        if (obj == null) {
            return range;
        }
        try {
            String str = (String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return Range.create(Long.valueOf(Long.parseLong(str.substring(0, indexOf), 10)), Long.valueOf(Long.parseLong(str.substring(indexOf + 1), 10)));
            }
            long parseLong = Long.parseLong(str);
            return Range.create(Long.valueOf(parseLong), Long.valueOf(parseLong));
        } catch (ClassCastException | NumberFormatException | IllegalArgumentException unused) {
            Log.w(TAG, "could not parse long range '" + obj + "'");
            return range;
        }
    }

    static Range<Rational> parseRationalRange(Object obj, Range<Rational> range) {
        if (obj == null) {
            return range;
        }
        try {
            String str = (String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return Range.create(Rational.parseRational(str.substring(0, indexOf)), Rational.parseRational(str.substring(indexOf + 1)));
            }
            Rational parseRational = Rational.parseRational(str);
            return Range.create(parseRational, parseRational);
        } catch (ClassCastException | NumberFormatException | IllegalArgumentException unused) {
            Log.w(TAG, "could not parse rational range '" + obj + "'");
            return range;
        }
    }

    static Pair<Size, Size> parseSizeRange(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str = (String) obj;
            int indexOf = str.indexOf(45);
            if (indexOf >= 0) {
                return Pair.create(Size.parseSize(str.substring(0, indexOf)), Size.parseSize(str.substring(indexOf + 1)));
            }
            Size parseSize = Size.parseSize(str);
            return Pair.create(parseSize, parseSize);
        } catch (ClassCastException | NumberFormatException | IllegalArgumentException unused) {
            Log.w(TAG, "could not parse size range '" + obj + "'");
            return null;
        }
    }

    public static File getUniqueExternalFile(Context context, String str, String str2, String str3) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(str);
        externalStoragePublicDirectory.mkdirs();
        try {
            return FileUtils.buildUniqueFile(externalStoragePublicDirectory, str3, str2);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Unable to get a unique file name: " + e);
            return null;
        }
    }

    static String getFileDisplayNameFromUri(Context context, Uri uri) {
        Uri uri2;
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            return uri.getLastPathSegment();
        }
        if ("content".equals(scheme)) {
            uri2 = uri;
            Cursor query = context.getContentResolver().query(uri2, new String[]{"_display_name"}, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        query.moveToFirst();
                        String string = query.getString(query.getColumnIndex("_display_name"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } else {
            uri2 = uri;
        }
        return uri2.toString();
    }

    public static class ListenerList<V> {
        private final boolean mClearCallingIdentity;
        private final boolean mForceRemoveConsistency;
        private HashMap<Object, ListenerWithCancellation<V>> mListeners;
        private final boolean mRestrictSingleCallerOnEvent;

        public interface Listener<V> {
            void onEvent(int i, V v);
        }

        private interface ListenerWithCancellation<V> extends Listener<V> {
            void cancel();
        }

        public ListenerList() {
            this(true, true, false);
        }

        public ListenerList(boolean z, boolean z2, boolean z3) {
            this.mListeners = new HashMap<>();
            this.mRestrictSingleCallerOnEvent = z;
            this.mClearCallingIdentity = z2;
            this.mForceRemoveConsistency = z3;
        }

        public void add(Object obj, Executor executor, Listener<V> listener) {
            Objects.requireNonNull(obj);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(listener);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(executor, listener);
            synchronized (this.mListeners) {
                this.mListeners.put(obj, anonymousClass1);
            }
        }

        /* renamed from: android.media.Utils$ListenerList$1, reason: invalid class name */
        class AnonymousClass1 implements ListenerWithCancellation<V> {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Listener val$listener;
            private final Object mLock = new Object();
            private volatile boolean mCancelled = false;

            AnonymousClass1(Executor executor, Listener listener) {
                this.val$executor = executor;
                this.val$listener = listener;
            }

            @Override // android.media.Utils.ListenerList.Listener
            public void onEvent(final int i, final V v) {
                Executor executor = this.val$executor;
                final Listener listener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.media.Utils$ListenerList$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Utils.ListenerList.AnonymousClass1.this.lambda$onEvent$0(listener, i, v);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onEvent$0(Listener listener, int i, Object obj) {
                if (ListenerList.this.mRestrictSingleCallerOnEvent || ListenerList.this.mForceRemoveConsistency) {
                    synchronized (this.mLock) {
                        if (this.mCancelled) {
                            return;
                        }
                        listener.onEvent(i, obj);
                        return;
                    }
                }
                if (this.mCancelled) {
                    return;
                }
                listener.onEvent(i, obj);
            }

            @Override // android.media.Utils.ListenerList.ListenerWithCancellation
            public void cancel() {
                if (ListenerList.this.mForceRemoveConsistency) {
                    synchronized (this.mLock) {
                        this.mCancelled = true;
                    }
                    return;
                }
                this.mCancelled = true;
            }
        }

        public void remove(Object obj) {
            Objects.requireNonNull(obj);
            synchronized (this.mListeners) {
                ListenerWithCancellation<V> listenerWithCancellation = this.mListeners.get(obj);
                if (listenerWithCancellation == null) {
                    return;
                }
                this.mListeners.remove(obj);
                listenerWithCancellation.cancel();
            }
        }

        public void notify(int i, V v) {
            synchronized (this.mListeners) {
                if (this.mListeners.size() == 0) {
                    return;
                }
                Object[] array = this.mListeners.values().toArray();
                Long valueOf = this.mClearCallingIdentity ? Long.valueOf(Binder.clearCallingIdentity()) : null;
                try {
                    for (Object obj : array) {
                        ((ListenerWithCancellation) obj).onEvent(i, v);
                    }
                } finally {
                    if (valueOf != null) {
                        Binder.restoreCallingIdentity(valueOf.longValue());
                    }
                }
            }
        }
    }

    public static String anonymizeBluetoothAddress(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() != 17) {
            return str;
        }
        return "XX:XX:XX:XX" + str.substring(11);
    }

    public static String anonymizeBluetoothAddress(int i, String str) {
        return !AudioSystem.isBluetoothDevice(i) ? str : anonymizeBluetoothAddress(str);
    }

    public static boolean isRingtoneVibrationSettingsSupported(Context context) {
        Resources resources = context.getResources();
        return resources != null && resources.getBoolean(R.bool.config_ringtoneVibrationSettingsSupported);
    }

    public static boolean hasVibration(Uri uri) {
        return (uri == null || uri.getQueryParameter(VIBRATION_URI_PARAM) == null) ? false : true;
    }

    public static Uri getVibrationUri(Uri uri) {
        String queryParameter;
        if (uri == null || (queryParameter = uri.getQueryParameter(VIBRATION_URI_PARAM)) == null) {
            return null;
        }
        return Uri.parse(queryParameter);
    }

    public static VibrationEffect parseVibrationEffect(Vibrator vibrator, Uri uri) {
        if (uri == null) {
            Log.w(TAG, "The vibration Uri is null.");
            return null;
        }
        String path = uri.getPath();
        if (path == null || path.equals(SYNCHRONIZED_VIBRATION)) {
            Log.w(TAG, "Ignore the vibration parsing for file:" + path);
            return null;
        }
        File file = new File(path);
        if (file.exists() && file.canRead()) {
            try {
                return VibrationXmlParser.parseDocument(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).resolve(vibrator);
            } catch (IOException e) {
                Log.e(TAG, "FileNotFoundException" + e);
            }
        } else {
            Log.w(TAG, "File exists:" + file.exists() + ", canRead:" + file.canRead());
        }
        return null;
    }
}
