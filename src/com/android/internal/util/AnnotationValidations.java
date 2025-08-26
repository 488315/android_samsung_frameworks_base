package com.android.internal.util;

import android.annotation.AppIdInt;
import android.annotation.ColorInt;
import android.annotation.FloatRange;
import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.Size;
import android.annotation.UserIdInt;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public class AnnotationValidations {
    @Deprecated
    public static void validate(Class<? extends Annotation> cls, Annotation annotation, int i, Object... objArr) {
    }

    @Deprecated
    public static void validate(Class<? extends Annotation> cls, Annotation annotation, Object obj) {
    }

    @Deprecated
    public static void validate(Class<? extends Annotation> cls, Annotation annotation, Object obj, Object... objArr) {
    }

    private AnnotationValidations() {
    }

    public static void validate(Class<UserIdInt> cls, UserIdInt userIdInt, int i) {
        if ((i == -10000 || i >= -3) && i <= 21474) {
            return;
        }
        invalid(cls, Integer.valueOf(i));
    }

    public static void validate(Class<AppIdInt> cls, AppIdInt appIdInt, int i) {
        if (i / 100000 != 0 || i < 0) {
            invalid(cls, Integer.valueOf(i));
        }
    }

    public static void validate(Class<IntRange> cls, IntRange intRange, int i, String str, long j, String str2, long j2) {
        validate(cls, intRange, i, str, j);
        validate(cls, intRange, i, str2, j2);
    }

    public static void validate(Class<IntRange> cls, IntRange intRange, int i, String str, long j) {
        str.hashCode();
        if (str.equals("to")) {
            if (i > j) {
                invalid(cls, Integer.valueOf(i), str, Long.valueOf(j));
            }
        } else if (str.equals("from") && i < j) {
            invalid(cls, Integer.valueOf(i), str, Long.valueOf(j));
        }
    }

    public static void validate(Class<IntRange> cls, IntRange intRange, long j, String str, long j2, String str2, long j3) {
        validate(cls, intRange, j, str, j2);
        validate(cls, intRange, j, str2, j3);
    }

    public static void validate(Class<IntRange> cls, IntRange intRange, long j, String str, long j2) {
        str.hashCode();
        if (str.equals("to")) {
            if (j > j2) {
                invalid(cls, Long.valueOf(j), str, Long.valueOf(j2));
            }
        } else if (str.equals("from") && j < j2) {
            invalid(cls, Long.valueOf(j), str, Long.valueOf(j2));
        }
    }

    public static void validate(Class<FloatRange> cls, FloatRange floatRange, float f, String str, float f2, String str2, float f3) {
        validate(cls, floatRange, f, str, f2);
        validate(cls, floatRange, f, str2, f3);
    }

    public static void validate(Class<FloatRange> cls, FloatRange floatRange, float f, String str, float f2) {
        str.hashCode();
        if (str.equals("to")) {
            if (f > f2) {
                invalid(cls, Float.valueOf(f), str, Float.valueOf(f2));
            }
        } else if (str.equals("from") && f < f2) {
            invalid(cls, Float.valueOf(f), str, Float.valueOf(f2));
        }
    }

    public static void validate(Class<NonNull> cls, NonNull nonNull, Object obj) {
        obj.getClass();
    }

    public static void validate(Class<Size> cls, Size size, int i, String str, int i2, String str2, int i3) {
        validate(cls, size, i, str, i2);
        validate(cls, size, i, str2, i3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void validate(Class<Size> cls, Size size, int i, String str, int i2) {
        str.hashCode();
        switch (str) {
            case "max":
                if (i > i2) {
                    invalid(cls, Integer.valueOf(i), str, Integer.valueOf(i2));
                    break;
                }
                break;
            case "min":
                if (i < i2) {
                    invalid(cls, Integer.valueOf(i), str, Integer.valueOf(i2));
                    break;
                }
                break;
            case "value":
                if (i2 != -1 && i != i2) {
                    invalid(cls, Integer.valueOf(i), str, Integer.valueOf(i2));
                    break;
                }
                break;
            case "multiple":
                if (i % i2 != 0) {
                    invalid(cls, Integer.valueOf(i), str, Integer.valueOf(i2));
                    break;
                }
                break;
        }
    }

    public static void validate(Class<PackageManager.PermissionResult> cls, PackageManager.PermissionResult permissionResult, int i) {
        validateIntEnum(cls, i, 0);
    }

    public static void validate(Class<PackageManager.PackageInfoFlagsBits> cls, PackageManager.PackageInfoFlagsBits packageInfoFlagsBits, long j) {
        validateLongFlags(cls, j, BitUtils.flagsUpTo(536870912));
    }

    public static void validate(Class<Intent.Flags> cls, Intent.Flags flags, int i) {
        validateIntFlags(cls, i, BitUtils.flagsUpTo(Integer.MIN_VALUE));
    }

    public static void validate(Class<? extends Annotation> cls, Annotation annotation, int i) {
        if (!(("android.annotation".equals(cls.getPackageName()) && cls.getSimpleName().endsWith("Res")) || ColorInt.class.equals(cls)) || i >= 0) {
            return;
        }
        invalid(cls, Integer.valueOf(i));
    }

    public static void validate(Class<? extends Annotation> cls, Annotation annotation, long j) {
        if ("android.annotation".equals(cls.getPackageName()) && cls.getSimpleName().endsWith("Long") && j < 0) {
            invalid(cls, Long.valueOf(j));
        }
    }

    private static void validateIntEnum(Class<? extends Annotation> cls, int i, int i2) {
        if (i > i2) {
            invalid(cls, Integer.valueOf(i));
        }
    }

    private static void validateIntFlags(Class<? extends Annotation> cls, int i, int i2) {
        if ((i2 & i) != i2) {
            invalid(cls, "0x" + Integer.toHexString(i));
        }
    }

    private static void validateLongFlags(Class<? extends Annotation> cls, long j, int i) {
        long j2 = i;
        if ((j2 & j) != j2) {
            invalid(cls, "0x" + Long.toHexString(j));
        }
    }

    private static void invalid(Class<? extends Annotation> cls, Object obj) {
        invalid("@" + cls.getSimpleName(), obj);
    }

    private static void invalid(Class<? extends Annotation> cls, Object obj, String str, Object obj2) {
        String str2;
        if ("value".equals(str)) {
            str2 = "";
        } else {
            str2 = str + " = ";
        }
        invalid("@" + cls.getSimpleName() + NavigationBarInflaterView.KEY_CODE_START + str2 + obj2 + NavigationBarInflaterView.KEY_CODE_END, obj);
    }

    private static void invalid(String str, Object obj) {
        throw new IllegalStateException("Invalid " + str + ": " + obj);
    }
}
