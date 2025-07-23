package com.samsung.android.sume.core;

import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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

    public static boolean isRangeIn(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    public static String getCoreVersion() {
        return BuildConfig.SUME_CORE_VERSION;
    }

    public static String fmtstr(String str, Object... objArr) {
        return String.format(str, objArr);
    }

    public static String fmtstrln(String str, Object... objArr) {
        return String.format(str + ShaderAssembler.NEWLINE, objArr);
    }

    public static String tagOf(Class<?> cls) {
        return "Sume@" + cls.getSimpleName();
    }

    public static String tagOf(Object obj) {
        String simpleName;
        if (obj instanceof String) {
            simpleName = (String) obj;
        } else if (obj instanceof Class) {
            simpleName = ((Class) obj).getSimpleName();
        } else {
            simpleName = obj.getClass().getSimpleName();
        }
        return "<<" + simpleName + "@" + obj.hashCode() + ">>";
    }

    static /* synthetic */ String lambda$taglnOf$0(Object obj) {
        return tagOf(obj) + ShaderAssembler.NEWLINE;
    }

    public static String taglnOf(Object obj) {
        return (String) Optional.ofNullable(obj).map(new Function() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return Def.lambda$taglnOf$0(obj2);
            }
        }).orElse("");
    }

    public static void check(boolean z) {
        check(z, "", new Object[0]);
    }

    public static void check(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalStateException(fmtstr(str, objArr));
        }
    }

    public static void require(boolean z) {
        require(z, "", new Object[0]);
    }

    public static void require(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(fmtstr(str, objArr));
        }
    }

    public static String contentToString(String... strArr) {
        return (String) Arrays.stream(strArr).filter(new Predicate() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Def.lambda$contentToString$1((String) obj);
            }
        }).collect(Collectors.joining(", "));
    }

    static /* synthetic */ boolean lambda$contentToString$1(String str) {
        return !str.isEmpty();
    }

    public static String contentToStringln(final String str, String... strArr) {
        return (String) Arrays.stream(strArr).filter(new Predicate() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Def.lambda$contentToStringln$2((String) obj);
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.Def$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String replaceAll;
                replaceAll = (r0 + ((String) obj)).replaceAll(ShaderAssembler.NEWLINE, ShaderAssembler.NEWLINE + str + r0);
                return replaceAll;
            }
        }).collect(Collectors.joining(ShaderAssembler.NEWLINE));
    }

    static /* synthetic */ boolean lambda$contentToStringln$2(String str) {
        return !str.isEmpty();
    }

    public static String makeExceptionTag(Exception exc, MediaFilter mediaFilter) {
        return "@[" + mediaFilter.getDescriptor().getFilterId() + "]@" + exc.getMessage();
    }

    public static long getFileSize(FileDescriptor fileDescriptor) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(fileDescriptor);
        } catch (IOException unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            long size = fileInputStream.getChannel().size();
            try {
                fileInputStream.close();
                return size;
            } catch (IOException e) {
                e.printStackTrace();
                return size;
            }
        } catch (IOException unused2) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 == null) {
                return -1L;
            }
            try {
                fileInputStream2.close();
                return -1L;
            } catch (IOException e2) {
                e2.printStackTrace();
                return -1L;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }
}
