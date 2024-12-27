package com.samsung.android.sume.core.types;

import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda13;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public interface NumericEnum {
    public static final String SEP = ":";

    int getValue();

    String stringfy();

    static <T> T fromValue(Class<T> clazz, final int value) {
        if (!NumericEnum.class.isAssignableFrom(clazz)) {
            throw new UnsupportedOperationException("type is not NumericEnum");
        }
        try {
            final Method m = clazz.getMethod("getValue", null);
            return Arrays.stream((Object[]) Objects.requireNonNull(clazz.getEnumConstants()))
                    .filter(
                            new Predicate() { // from class:
                                              // com.samsung.android.sume.core.types.NumericEnum$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return NumericEnum.lambda$fromValue$0(m, value, obj);
                                }
                            })
                    .findFirst()
                    .orElseThrow(
                            new Supplier() { // from class:
                                             // com.samsung.android.sume.core.types.NumericEnum$$ExternalSyntheticLambda1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    return NumericEnum.lambda$fromValue$1();
                                }
                            });
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("type is not NumericEnum");
        }
    }

    static /* synthetic */ boolean lambda$fromValue$0(Method m, int value, Object e) {
        try {
            return ((Integer) m.invoke(e, null)).intValue() == value;
        } catch (IllegalAccessException | InvocationTargetException e2) {
            return false;
        }
    }

    static /* synthetic */ IllegalArgumentException lambda$fromValue$1() {
        return new IllegalArgumentException("no matched value");
    }

    static <T> T fromJson(final Class<T> cls, String str) {
        return (T)
                Stream.of((Object[]) str.split(":"))
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.types.NumericEnum$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean matches;
                                        matches =
                                                Pattern.compile("-?\\d+(\\.\\d+)?")
                                                        .matcher((String) obj)
                                                        .matches();
                                        return matches;
                                    }
                                })
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.types.NumericEnum$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Object fromValue;
                                        fromValue =
                                                NumericEnum.fromValue(
                                                        cls, Integer.parseInt((String) obj));
                                        return fromValue;
                                    }
                                })
                        .findFirst()
                        .orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
    }
}
