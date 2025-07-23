package kotlin.jvm.internal;

import androidx.compose.runtime.internal.ComposableLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ClassReference implements KClass, ClassBasedDeclarationContainer {
    public static final Companion Companion = new Companion(null);
    public static final Map FUNCTION_CLASSES;
    public static final HashMap classFqNames;
    public static final Map simpleNames;
    public final Class jClass;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        int i = 0;
        List asList = Arrays.asList(Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, ComposableLambda.class, Function12.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, ComposableLambda.class, Function22.class);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(asList, 10));
        for (Object obj : asList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            arrayList.add(new Pair((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        FUNCTION_CLASSES = MapsKt__MapsKt.toMap(arrayList);
        HashMap hashMap = new HashMap();
        hashMap.put("boolean", "kotlin.Boolean");
        hashMap.put("char", "kotlin.Char");
        hashMap.put("byte", "kotlin.Byte");
        hashMap.put("short", "kotlin.Short");
        hashMap.put("int", "kotlin.Int");
        hashMap.put("float", "kotlin.Float");
        hashMap.put("long", "kotlin.Long");
        hashMap.put("double", "kotlin.Double");
        HashMap hashMap2 = new HashMap();
        hashMap2.put("java.lang.Boolean", "kotlin.Boolean");
        hashMap2.put("java.lang.Character", "kotlin.Char");
        hashMap2.put("java.lang.Byte", "kotlin.Byte");
        hashMap2.put("java.lang.Short", "kotlin.Short");
        hashMap2.put("java.lang.Integer", "kotlin.Int");
        hashMap2.put("java.lang.Float", "kotlin.Float");
        hashMap2.put("java.lang.Long", "kotlin.Long");
        hashMap2.put("java.lang.Double", "kotlin.Double");
        HashMap hashMap3 = new HashMap();
        hashMap3.put("java.lang.Object", "kotlin.Any");
        hashMap3.put("java.lang.String", "kotlin.String");
        hashMap3.put("java.lang.CharSequence", "kotlin.CharSequence");
        hashMap3.put("java.lang.Throwable", "kotlin.Throwable");
        hashMap3.put("java.lang.Cloneable", "kotlin.Cloneable");
        hashMap3.put("java.lang.Number", "kotlin.Number");
        hashMap3.put("java.lang.Comparable", "kotlin.Comparable");
        hashMap3.put("java.lang.Enum", "kotlin.Enum");
        hashMap3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        hashMap3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        hashMap3.put("java.util.Iterator", "kotlin.collections.Iterator");
        hashMap3.put("java.util.Collection", "kotlin.collections.Collection");
        hashMap3.put("java.util.List", "kotlin.collections.List");
        hashMap3.put("java.util.Set", "kotlin.collections.Set");
        hashMap3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        hashMap3.put("java.util.Map", "kotlin.collections.Map");
        hashMap3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        hashMap3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        hashMap3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        hashMap3.putAll(hashMap);
        hashMap3.putAll(hashMap2);
        for (String str : hashMap.values()) {
            StringBuilder sb = new StringBuilder("kotlin.jvm.internal.");
            str.getClass();
            sb.append(StringsKt__StringsKt.substringAfterLast(str, str));
            sb.append("CompanionObject");
            Pair pair = new Pair(sb.toString(), str.concat(".Companion"));
            hashMap3.put(pair.getFirst(), pair.getSecond());
        }
        for (Map.Entry entry : FUNCTION_CLASSES.entrySet()) {
            hashMap3.put(((Class) entry.getKey()).getName(), "kotlin.Function" + ((Number) entry.getValue()).intValue());
        }
        classFqNames = hashMap3;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(hashMap3.size()));
        for (Map.Entry entry2 : hashMap3.entrySet()) {
            Object key = entry2.getKey();
            String str2 = (String) entry2.getValue();
            str2.getClass();
            linkedHashMap.put(key, StringsKt__StringsKt.substringAfterLast(str2, str2));
        }
        simpleNames = linkedHashMap;
    }

    public ClassReference(Class<?> cls) {
        this.jClass = cls;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof ClassReference) && JvmClassMappingKt.getJavaObjectType(this).equals(JvmClassMappingKt.getJavaObjectType((KClass) obj));
    }

    @Override // kotlin.jvm.internal.ClassBasedDeclarationContainer
    public final Class getJClass() {
        return this.jClass;
    }

    public final String getQualifiedName() {
        String str;
        Class cls = this.jClass;
        Companion.getClass();
        String str2 = null;
        if (cls.isAnonymousClass() || cls.isLocalClass()) {
            return null;
        }
        if (!cls.isArray()) {
            String str3 = (String) classFqNames.get(cls.getName());
            return str3 == null ? cls.getCanonicalName() : str3;
        }
        Class<?> componentType = cls.getComponentType();
        if (componentType.isPrimitive() && (str = (String) classFqNames.get(componentType.getName())) != null) {
            str2 = str.concat("Array");
        }
        return str2 == null ? "kotlin.Array" : str2;
    }

    public final String getSimpleName() {
        Class cls = this.jClass;
        Companion.getClass();
        String str = null;
        if (cls.isAnonymousClass()) {
            return null;
        }
        if (!cls.isLocalClass()) {
            if (!cls.isArray()) {
                String str2 = (String) ((LinkedHashMap) simpleNames).get(cls.getName());
                return str2 == null ? cls.getSimpleName() : str2;
            }
            Class<?> componentType = cls.getComponentType();
            if (componentType.isPrimitive()) {
                String str3 = (String) ((LinkedHashMap) simpleNames).get(componentType.getName());
                if (str3 != null) {
                    str = str3.concat("Array");
                }
            }
            return str == null ? "Array" : str;
        }
        String simpleName = cls.getSimpleName();
        Method enclosingMethod = cls.getEnclosingMethod();
        if (enclosingMethod != null) {
            String substringAfter$default = StringsKt__StringsKt.substringAfter$default(simpleName, enclosingMethod.getName() + '$');
            if (substringAfter$default != null) {
                return substringAfter$default;
            }
        }
        Constructor<?> enclosingConstructor = cls.getEnclosingConstructor();
        if (enclosingConstructor == null) {
            return StringsKt__StringsKt.substringAfter$default(simpleName, '$');
        }
        return StringsKt__StringsKt.substringAfter$default(simpleName, enclosingConstructor.getName() + '$');
    }

    @Override // kotlin.reflect.KClass
    public final int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    public final boolean isInstance(Object obj) {
        Class cls = this.jClass;
        Companion.getClass();
        Integer num = (Integer) FUNCTION_CLASSES.get(cls);
        if (num != null) {
            return TypeIntrinsics.isFunctionOfArity(num.intValue(), obj);
        }
        if (cls.isPrimitive()) {
            cls = JvmClassMappingKt.getJavaObjectType(Reflection.getOrCreateKotlinClass(cls));
        }
        return cls.isInstance(obj);
    }

    public final String toString() {
        return this.jClass + " (Kotlin reflection is not available)";
    }
}
