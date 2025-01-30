package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class VersionedParcel {
    public final SimpleArrayMap mParcelizerCache;
    public final SimpleArrayMap mReadCache;
    public final SimpleArrayMap mWriteCache;

    public VersionedParcel(SimpleArrayMap simpleArrayMap, SimpleArrayMap simpleArrayMap2, SimpleArrayMap simpleArrayMap3) {
        this.mReadCache = simpleArrayMap;
        this.mWriteCache = simpleArrayMap2;
        this.mParcelizerCache = simpleArrayMap3;
    }

    public abstract VersionedParcelParcel createSubParcel();

    public final Class findParcelClass(Class cls) {
        String name = cls.getName();
        SimpleArrayMap simpleArrayMap = this.mParcelizerCache;
        Class cls2 = (Class) simpleArrayMap.get(name);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
        simpleArrayMap.put(cls.getName(), cls3);
        return cls3;
    }

    public final Method getReadMethod(String str) {
        SimpleArrayMap simpleArrayMap = this.mReadCache;
        Method method = (Method) simpleArrayMap.get(str);
        if (method != null) {
            return method;
        }
        Method declaredMethod = Class.forName(str, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class);
        simpleArrayMap.put(str, declaredMethod);
        return declaredMethod;
    }

    public final Method getWriteMethod(Class cls) {
        String name = cls.getName();
        SimpleArrayMap simpleArrayMap = this.mWriteCache;
        Method method = (Method) simpleArrayMap.get(name);
        if (method != null) {
            return method;
        }
        Method declaredMethod = findParcelClass(cls).getDeclaredMethod("write", cls, VersionedParcel.class);
        simpleArrayMap.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    public final Object[] readArray(int i, Object[] objArr) {
        Serializable serializable;
        if (!readField(i)) {
            return objArr;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(readInt);
        if (readInt != 0) {
            int readInt2 = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt2 == 1) {
                while (readInt > 0) {
                    arrayList.add(readVersionedParcelable());
                    readInt--;
                }
            } else if (readInt2 == 2) {
                while (readInt > 0) {
                    arrayList.add(readParcelable());
                    readInt--;
                }
            } else if (readInt2 == 3) {
                while (readInt > 0) {
                    String readString = readString();
                    if (readString == null) {
                        serializable = null;
                    } else {
                        try {
                            serializable = (Serializable) new ObjectInputStream(this, new ByteArrayInputStream(readByteArray())) { // from class: androidx.versionedparcelable.VersionedParcel.1
                                @Override // java.io.ObjectInputStream
                                public final Class resolveClass(ObjectStreamClass objectStreamClass) {
                                    Class<?> cls = Class.forName(objectStreamClass.getName(), false, C05611.class.getClassLoader());
                                    return cls != null ? cls : super.resolveClass(objectStreamClass);
                                }
                            }.readObject();
                        } catch (IOException e) {
                            throw new RuntimeException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to read Serializable object (name = ", readString, ")"), e);
                        } catch (ClassNotFoundException e2) {
                            throw new RuntimeException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to read Serializable object (name = ", readString, ")"), e2);
                        }
                    }
                    arrayList.add(serializable);
                    readInt--;
                }
            } else if (readInt2 == 4) {
                while (readInt > 0) {
                    arrayList.add(readString());
                    readInt--;
                }
            } else if (readInt2 == 5) {
                while (readInt > 0) {
                    arrayList.add(readStrongBinder());
                    readInt--;
                }
            }
        }
        return arrayList.toArray(objArr);
    }

    public abstract boolean readBoolean();

    public abstract Bundle readBundle();

    public abstract byte[] readByteArray();

    public abstract CharSequence readCharSequence();

    public abstract boolean readField(int i);

    public abstract int readInt();

    public final int readInt(int i, int i2) {
        return !readField(i2) ? i : readInt();
    }

    public abstract long readLong();

    public abstract Parcelable readParcelable();

    public final Parcelable readParcelable(Parcelable parcelable, int i) {
        return !readField(i) ? parcelable : readParcelable();
    }

    public abstract String readString();

    public final String readString(int i, String str) {
        return !readField(i) ? str : readString();
    }

    public abstract IBinder readStrongBinder();

    public final VersionedParcelable readVersionedParcelable(VersionedParcelable versionedParcelable, int i) {
        return !readField(i) ? versionedParcelable : readVersionedParcelable();
    }

    public abstract void setOutputField(int i);

    public final void writeArray(int i, Object[] objArr) {
        int i2;
        setOutputField(i);
        if (objArr == null) {
            writeInt(-1);
            return;
        }
        int length = objArr.length;
        writeInt(length);
        if (length > 0) {
            int i3 = 0;
            Object obj = objArr[0];
            if (obj instanceof String) {
                i2 = 4;
            } else if (obj instanceof Parcelable) {
                i2 = 2;
            } else if (obj instanceof VersionedParcelable) {
                i2 = 1;
            } else if (obj instanceof Serializable) {
                i2 = 3;
            } else if (obj instanceof IBinder) {
                i2 = 5;
            } else if (obj instanceof Integer) {
                i2 = 7;
            } else {
                if (!(obj instanceof Float)) {
                    throw new IllegalArgumentException(obj.getClass().getName().concat(" cannot be VersionedParcelled"));
                }
                i2 = 8;
            }
            writeInt(i2);
            if (i2 == 1) {
                while (i3 < length) {
                    writeVersionedParcelable((VersionedParcelable) objArr[i3]);
                    i3++;
                }
                return;
            }
            if (i2 == 2) {
                while (i3 < length) {
                    writeParcelable((Parcelable) objArr[i3]);
                    i3++;
                }
                return;
            }
            if (i2 != 3) {
                if (i2 == 4) {
                    while (i3 < length) {
                        writeString((String) objArr[i3]);
                        i3++;
                    }
                    return;
                } else {
                    if (i2 != 5) {
                        return;
                    }
                    while (i3 < length) {
                        writeStrongBinder((IBinder) objArr[i3]);
                        i3++;
                    }
                    return;
                }
            }
            while (i3 < length) {
                Serializable serializable = (Serializable) objArr[i3];
                if (serializable == null) {
                    writeString(null);
                } else {
                    String name = serializable.getClass().getName();
                    writeString(name);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(serializable);
                        objectOutputStream.close();
                        writeByteArray(byteArrayOutputStream.toByteArray());
                    } catch (IOException e) {
                        throw new RuntimeException(PathParser$$ExternalSyntheticOutline0.m29m("VersionedParcelable encountered IOException writing serializable object (name = ", name, ")"), e);
                    }
                }
                i3++;
            }
        }
    }

    public abstract void writeBoolean(boolean z);

    public abstract void writeBundle(Bundle bundle);

    public abstract void writeByteArray(byte[] bArr);

    public abstract void writeCharSequence(CharSequence charSequence);

    public abstract void writeInt(int i);

    public final void writeInt(int i, int i2) {
        setOutputField(i2);
        writeInt(i);
    }

    public abstract void writeLong(long j);

    public abstract void writeParcelable(Parcelable parcelable);

    public final void writeParcelable(Parcelable parcelable, int i) {
        setOutputField(i);
        writeParcelable(parcelable);
    }

    public final void writeString(int i, String str) {
        setOutputField(i);
        writeString(str);
    }

    public abstract void writeString(String str);

    public abstract void writeStrongBinder(IBinder iBinder);

    public final void writeVersionedParcelable(VersionedParcelable versionedParcelable) {
        if (versionedParcelable == null) {
            writeString(null);
            return;
        }
        try {
            writeString(findParcelClass(versionedParcelable.getClass()).getName());
            VersionedParcelParcel createSubParcel = createSubParcel();
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
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (!(cause instanceof Error)) {
                    throw new RuntimeException(e4);
                }
                throw ((Error) cause);
            }
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException(versionedParcelable.getClass().getSimpleName().concat(" does not have a Parcelizer"), e5);
        }
    }

    public final VersionedParcelable readVersionedParcelable() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        try {
            return (VersionedParcelable) getReadMethod(readString).invoke(null, createSubParcel());
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
}
