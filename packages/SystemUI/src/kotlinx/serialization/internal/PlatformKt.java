package kotlinx.serialization.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Polymorphic;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.Serializable;

/* loaded from: classes4.dex */
public abstract class PlatformKt {
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a3, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0156, code lost:
    
        r6 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final KSerializer constructSerializerForGivenTypeArgs(KClass kClass, KSerializer... kSerializerArr) {
        Object obj;
        KSerializer kSerializer;
        Class<?> cls;
        Object obj2;
        Object obj3;
        KSerializer kSerializerInvokeSerializerOnCompanion;
        Field field;
        Serializable serializable;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        KSerializer[] kSerializerArr2 = (KSerializer[]) Arrays.copyOf(kSerializerArr, kSerializerArr.length);
        if (jClass.isEnum() && jClass.getAnnotation(Serializable.class) == null && jClass.getAnnotation(Polymorphic.class) == null) {
            return new EnumSerializer(jClass.getCanonicalName(), (Enum[]) jClass.getEnumConstants());
        }
        KSerializer[] kSerializerArr3 = (KSerializer[]) Arrays.copyOf(kSerializerArr2, kSerializerArr2.length);
        PolymorphicSerializer polymorphicSerializer = null;
        try {
            Field declaredField = jClass.getDeclaredField("Companion");
            declaredField.setAccessible(true);
            obj = declaredField.get(null);
        } catch (Throwable unused) {
            obj = null;
        }
        KSerializer kSerializerInvokeSerializerOnCompanion2 = obj == null ? null : invokeSerializerOnCompanion(obj, (KSerializer[]) Arrays.copyOf(kSerializerArr3, kSerializerArr3.length));
        if (kSerializerInvokeSerializerOnCompanion2 != null) {
            return kSerializerInvokeSerializerOnCompanion2;
        }
        String canonicalName = jClass.getCanonicalName();
        int i = 0;
        if (canonicalName == null || canonicalName.startsWith("java.") || canonicalName.startsWith("kotlin.")) {
            kSerializer = null;
        } else {
            Field[] declaredFields = jClass.getDeclaredFields();
            int length = declaredFields.length;
            Field field2 = null;
            int i2 = 0;
            boolean z = false;
            while (true) {
                if (i2 >= length) {
                    if (!z) {
                        break;
                    }
                } else {
                    Field field3 = declaredFields[i2];
                    if (Intrinsics.areEqual(field3.getName(), "INSTANCE") && Intrinsics.areEqual(field3.getType(), jClass) && Modifier.isStatic(field3.getModifiers())) {
                        if (z) {
                            break;
                        }
                        z = true;
                        field2 = field3;
                    }
                    i2++;
                }
            }
            if (field2 != null) {
                Object obj4 = field2.get(null);
                Method[] methods = jClass.getMethods();
                int length2 = methods.length;
                Method method = null;
                int i3 = 0;
                boolean z2 = false;
                while (true) {
                    if (i3 >= length2) {
                        if (!z2) {
                            break;
                        }
                    } else {
                        Method method2 = methods[i3];
                        if (Intrinsics.areEqual(method2.getName(), "serializer") && method2.getParameterTypes().length == 0 && Intrinsics.areEqual(method2.getReturnType(), KSerializer.class)) {
                            if (z2) {
                                break;
                            }
                            z2 = true;
                            method = method2;
                        }
                        i3++;
                    }
                }
                method = null;
                if (method != null) {
                    Object objInvoke = method.invoke(obj4, null);
                    if (objInvoke instanceof KSerializer) {
                        kSerializer = (KSerializer) objInvoke;
                    }
                }
            }
        }
        if (kSerializer != null) {
            return kSerializer;
        }
        KSerializer[] kSerializerArr4 = (KSerializer[]) Arrays.copyOf(kSerializerArr2, kSerializerArr2.length);
        Class<?>[] declaredClasses = jClass.getDeclaredClasses();
        int length3 = declaredClasses.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length3) {
                cls = null;
                break;
            }
            cls = declaredClasses[i4];
            if (cls.getAnnotation(NamedCompanion.class) != null) {
                break;
            }
            i4++;
        }
        if (cls == null) {
            obj2 = null;
        } else {
            try {
                Field declaredField2 = jClass.getDeclaredField(cls.getSimpleName());
                declaredField2.setAccessible(true);
                obj2 = declaredField2.get(null);
            } catch (Throwable unused2) {
            }
        }
        if (obj2 == null || (kSerializerInvokeSerializerOnCompanion = invokeSerializerOnCompanion(obj2, (KSerializer[]) Arrays.copyOf(kSerializerArr4, kSerializerArr4.length))) == null) {
            try {
                Class<?>[] declaredClasses2 = jClass.getDeclaredClasses();
                int length4 = declaredClasses2.length;
                Class<?> cls2 = null;
                boolean z3 = false;
                while (true) {
                    if (i < length4) {
                        Class<?> cls3 = declaredClasses2[i];
                        if (cls3.getSimpleName().equals("$serializer")) {
                            if (z3) {
                                break;
                            }
                            z3 = true;
                            cls2 = cls3;
                        }
                        i++;
                    } else if (!z3) {
                    }
                }
                obj3 = (cls2 == null || (field = cls2.getField("INSTANCE")) == null) ? null : field.get(null);
            } catch (NoSuchFieldException unused3) {
            }
            kSerializerInvokeSerializerOnCompanion = obj3 instanceof KSerializer ? (KSerializer) obj3 : null;
        }
        if (kSerializerInvokeSerializerOnCompanion != null) {
            return kSerializerInvokeSerializerOnCompanion;
        }
        if (jClass.getAnnotation(Polymorphic.class) != null || ((serializable = (Serializable) jClass.getAnnotation(Serializable.class)) != null && Reflection.getOrCreateKotlinClass(serializable.with()).equals(Reflection.getOrCreateKotlinClass(PolymorphicSerializer.class)))) {
            polymorphicSerializer = new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(jClass));
        }
        return polymorphicSerializer;
    }

    public static final KSerializer invokeSerializerOnCompanion(Object obj, KSerializer... kSerializerArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class[] clsArr;
        try {
            if (kSerializerArr.length == 0) {
                clsArr = new Class[0];
            } else {
                int length = kSerializerArr.length;
                Class[] clsArr2 = new Class[length];
                for (int i = 0; i < length; i++) {
                    clsArr2[i] = KSerializer.class;
                }
                clsArr = clsArr2;
            }
            Object objInvoke = obj.getClass().getDeclaredMethod("serializer", (Class[]) Arrays.copyOf(clsArr, clsArr.length)).invoke(obj, Arrays.copyOf(kSerializerArr, kSerializerArr.length));
            if (objInvoke instanceof KSerializer) {
                return (KSerializer) objInvoke;
            }
            return null;
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                throw e;
            }
            String message = cause.getMessage();
            if (message == null) {
                message = e.getMessage();
            }
            throw new InvocationTargetException(cause, message);
        }
    }
}
