package com.samsung.android.sume.core;

import com.samsung.android.sume.core.filter.MediaFilter;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class Def {
    public static final int INVALID = -1;
    private static final String TAG = tagOf((Class<?>) Def.class);

    public static String getCoreVersion() {
        return BuildConfig.SUME_CORE_VERSION;
    }

    public static String fmtstr(String fmt, Object... args) {
        return String.format(fmt, args);
    }

    public static String fmtstrln(String fmt, Object... args) {
        return String.format(fmt + "\n", args);
    }

    public static String tagOf(Class<?> clazz) {
        return "Sume@" + clazz.getSimpleName();
    }

    public static String tagOf(Object object) {
        String tag;
        if (object instanceof String) {
            tag = (String) object;
        } else if (object instanceof Class) {
            tag = ((Class) object).getSimpleName();
        } else {
            tag = object.getClass().getSimpleName();
        }
        return "<<" + tag + "@" + object.hashCode() + ">>";
    }

    static /* synthetic */ String lambda$taglnOf$0(Object it) {
        return tagOf(it) + "\n";
    }

    public static String taglnOf(Object object) {
        return (String) Optional.ofNullable(object).map(new Function() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Def.lambda$taglnOf$0(obj);
            }
        }).orElse("");
    }

    public static void check(boolean condition) {
        check(condition, "", new Object[0]);
    }

    public static void check(boolean condition, String fmt, Object... args) {
        if (!condition) {
            throw new IllegalStateException(fmtstr(fmt, args));
        }
    }

    public static void require(boolean condition) {
        require(condition, "", new Object[0]);
    }

    public static void require(boolean condition, String fmt, Object... args) {
        if (!condition) {
            throw new IllegalArgumentException(fmtstr(fmt, args));
        }
    }

    public static String contentToString(String... values) {
        return (String) Arrays.stream(values).filter(new Predicate() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Def.lambda$contentToString$1((String) obj);
            }
        }).collect(Collectors.joining(", "));
    }

    static /* synthetic */ boolean lambda$contentToString$1(String it) {
        return !it.isEmpty();
    }

    public static String contentToStringln(final String indentMark, String... values) {
        return (String) Arrays.stream(values).filter(new Predicate() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Def.lambda$contentToStringln$2((String) obj);
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String replaceAll;
                replaceAll = (r0 + ((String) obj)).replaceAll("\n", "\n" + indentMark + r0);
                return replaceAll;
            }
        }).collect(Collectors.joining("\n"));
    }

    static /* synthetic */ boolean lambda$contentToStringln$2(String it) {
        return !it.isEmpty();
    }

    public static boolean isRangeIn(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static String makeExceptionTag(Exception exception, MediaFilter mediaFilter) {
        return "@[" + mediaFilter.getDescriptor().getFilterId() + "]@" + exception.getMessage();
    }

    public static long getFileSize(FileDescriptor fd) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fd);
            long size = fis.getChannel().size();
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return size;
        } catch (IOException e2) {
            if (fis == null) {
                return -1L;
            }
            try {
                fis.close();
                return -1L;
            } catch (IOException e3) {
                e3.printStackTrace();
                return -1L;
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }
}
