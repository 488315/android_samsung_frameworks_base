package androidx.core.os;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import java.io.Serializable;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BundleKt {
    public static final Bundle bundleOf(Pair... pairArr) {
        Bundle bundle = new Bundle(pairArr.length);
        for (Pair pair : pairArr) {
            String str = (String) pair.component1();
            Object component2 = pair.component2();
            if (component2 == null) {
                bundle.putString(str, null);
            } else if (component2 instanceof Boolean) {
                bundle.putBoolean(str, ((Boolean) component2).booleanValue());
            } else if (component2 instanceof Byte) {
                bundle.putByte(str, ((Number) component2).byteValue());
            } else if (component2 instanceof Character) {
                bundle.putChar(str, ((Character) component2).charValue());
            } else if (component2 instanceof Double) {
                bundle.putDouble(str, ((Number) component2).doubleValue());
            } else if (component2 instanceof Float) {
                bundle.putFloat(str, ((Number) component2).floatValue());
            } else if (component2 instanceof Integer) {
                bundle.putInt(str, ((Number) component2).intValue());
            } else if (component2 instanceof Long) {
                bundle.putLong(str, ((Number) component2).longValue());
            } else if (component2 instanceof Short) {
                bundle.putShort(str, ((Number) component2).shortValue());
            } else if (component2 instanceof Bundle) {
                bundle.putBundle(str, (Bundle) component2);
            } else if (component2 instanceof CharSequence) {
                bundle.putCharSequence(str, (CharSequence) component2);
            } else if (component2 instanceof Parcelable) {
                bundle.putParcelable(str, (Parcelable) component2);
            } else if (component2 instanceof boolean[]) {
                bundle.putBooleanArray(str, (boolean[]) component2);
            } else if (component2 instanceof byte[]) {
                bundle.putByteArray(str, (byte[]) component2);
            } else if (component2 instanceof char[]) {
                bundle.putCharArray(str, (char[]) component2);
            } else if (component2 instanceof double[]) {
                bundle.putDoubleArray(str, (double[]) component2);
            } else if (component2 instanceof float[]) {
                bundle.putFloatArray(str, (float[]) component2);
            } else if (component2 instanceof int[]) {
                bundle.putIntArray(str, (int[]) component2);
            } else if (component2 instanceof long[]) {
                bundle.putLongArray(str, (long[]) component2);
            } else if (component2 instanceof short[]) {
                bundle.putShortArray(str, (short[]) component2);
            } else if (component2 instanceof Object[]) {
                Class<?> componentType = component2.getClass().getComponentType();
                componentType.getClass();
                if (Parcelable.class.isAssignableFrom(componentType)) {
                    bundle.putParcelableArray(str, (Parcelable[]) component2);
                } else if (String.class.isAssignableFrom(componentType)) {
                    bundle.putStringArray(str, (String[]) component2);
                } else if (CharSequence.class.isAssignableFrom(componentType)) {
                    bundle.putCharSequenceArray(str, (CharSequence[]) component2);
                } else {
                    if (!Serializable.class.isAssignableFrom(componentType)) {
                        throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + str + '\"');
                    }
                    bundle.putSerializable(str, (Serializable) component2);
                }
            } else if (component2 instanceof Serializable) {
                bundle.putSerializable(str, (Serializable) component2);
            } else if (component2 instanceof IBinder) {
                bundle.putBinder(str, (IBinder) component2);
            } else if (component2 instanceof Size) {
                int i = BundleApi21ImplKt.$r8$clinit;
                bundle.putSize(str, (Size) component2);
            } else {
                if (!(component2 instanceof SizeF)) {
                    throw new IllegalArgumentException("Illegal value type " + component2.getClass().getCanonicalName() + " for key \"" + str + '\"');
                }
                int i2 = BundleApi21ImplKt.$r8$clinit;
                bundle.putSizeF(str, (SizeF) component2);
            }
        }
        return bundle;
    }
}
