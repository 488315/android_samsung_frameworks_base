package androidx.versionedparcelable;

import android.os.Parcelable;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public abstract class VersionedParcel {
    final SimpleArrayMap<String, Class<?>> mParcelizerCache;
    final SimpleArrayMap<String, Method> mReadCache;
    final SimpleArrayMap<String, Method> mWriteCache;

    VersionedParcel(SimpleArrayMap<String, Method> simpleArrayMap, SimpleArrayMap<String, Method> simpleArrayMap2, SimpleArrayMap<String, Class<?>> simpleArrayMap3) {
        this.mReadCache = simpleArrayMap;
        this.mWriteCache = simpleArrayMap2;
        this.mParcelizerCache = simpleArrayMap3;
    }

    private Class<?> findParcelClass(Class<?> cls) throws ClassNotFoundException {
        String name = cls.getName();
        SimpleArrayMap<String, Class<?>> simpleArrayMap = this.mParcelizerCache;
        Class<?> cls2 = simpleArrayMap.get(name);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
        simpleArrayMap.put(cls.getName(), cls3);
        return cls3;
    }

    private Method getReadMethod(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        SimpleArrayMap<String, Method> simpleArrayMap = this.mReadCache;
        Method method = simpleArrayMap.get(str);
        if (method != null) {
            return method;
        }
        Method declaredMethod = Class.forName(str, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class);
        simpleArrayMap.put(str, declaredMethod);
        return declaredMethod;
    }

    private Method getWriteMethod(Class<?> cls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        String name = cls.getName();
        SimpleArrayMap<String, Method> simpleArrayMap = this.mWriteCache;
        Method method = simpleArrayMap.get(name);
        if (method != null) {
            return method;
        }
        Method declaredMethod = findParcelClass(cls).getDeclaredMethod("write", cls, VersionedParcel.class);
        simpleArrayMap.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    protected abstract void closeField();

    protected abstract VersionedParcel createSubParcel();

    protected abstract boolean readBoolean();

    public final boolean readBoolean(int i, boolean z) {
        return !readField(i) ? z : readBoolean();
    }

    protected abstract byte[] readByteArray();

    public final byte[] readByteArray(byte[] bArr) {
        return !readField(2) ? bArr : readByteArray();
    }

    protected abstract CharSequence readCharSequence();

    public final CharSequence readCharSequence(int i, CharSequence charSequence) {
        return !readField(i) ? charSequence : readCharSequence();
    }

    protected abstract boolean readField(int i);

    protected abstract int readInt();

    public final int readInt(int i, int i2) {
        return !readField(i2) ? i : readInt();
    }

    protected abstract <T extends Parcelable> T readParcelable();

    public final <T extends Parcelable> T readParcelable(T t, int i) {
        return !readField(i) ? t : (T) readParcelable();
    }

    protected abstract String readString();

    public final String readString(int i, String str) {
        return !readField(i) ? str : readString();
    }

    public final VersionedParcelable readVersionedParcelable(IconCompat iconCompat) {
        return !readField(1) ? iconCompat : readVersionedParcelable();
    }

    protected abstract void setOutputField(int i);

    public final void writeBoolean(int i, boolean z) {
        setOutputField(i);
        writeBoolean(z);
    }

    protected abstract void writeBoolean(boolean z);

    protected abstract void writeByteArray(byte[] bArr);

    public final void writeByteArray$1(byte[] bArr) {
        setOutputField(2);
        writeByteArray(bArr);
    }

    public final void writeCharSequence(int i, CharSequence charSequence) {
        setOutputField(i);
        writeCharSequence(charSequence);
    }

    protected abstract void writeCharSequence(CharSequence charSequence);

    protected abstract void writeInt(int i);

    public final void writeInt(int i, int i2) {
        setOutputField(i2);
        writeInt(i);
    }

    protected abstract void writeParcelable(Parcelable parcelable);

    public final void writeParcelable(Parcelable parcelable, int i) {
        setOutputField(i);
        writeParcelable(parcelable);
    }

    public final void writeString(int i, String str) {
        setOutputField(i);
        writeString(str);
    }

    protected abstract void writeString(String str);

    public final void writeVersionedParcelable(IconCompat iconCompat) {
        setOutputField(1);
        writeVersionedParcelable((VersionedParcelable) iconCompat);
    }

    protected final <T extends VersionedParcelable> T readVersionedParcelable() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        try {
            return (T) getReadMethod(readString).invoke(null, createSubParcel());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException(e3);
        } catch (InvocationTargetException e4) {
            Throwable cause = e4.getCause();
            if (!(cause instanceof RuntimeException)) {
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new RuntimeException(e4);
            }
            throw ((RuntimeException) cause);
        }
    }

    protected final void writeVersionedParcelable(VersionedParcelable versionedParcelable) {
        if (versionedParcelable == null) {
            writeString(null);
            return;
        }
        try {
            writeString(findParcelClass(versionedParcelable.getClass()).getName());
            VersionedParcel createSubParcel = createSubParcel();
            try {
                getWriteMethod(versionedParcelable.getClass()).invoke(null, versionedParcelable, createSubParcel);
                createSubParcel.closeField();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException(e3);
            } catch (InvocationTargetException e4) {
                Throwable cause = e4.getCause();
                if (!(cause instanceof RuntimeException)) {
                    if (cause instanceof Error) {
                        throw ((Error) cause);
                    }
                    throw new RuntimeException(e4);
                }
                throw ((RuntimeException) cause);
            }
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException(versionedParcelable.getClass().getSimpleName().concat(" does not have a Parcelizer"), e5);
        }
    }
}
