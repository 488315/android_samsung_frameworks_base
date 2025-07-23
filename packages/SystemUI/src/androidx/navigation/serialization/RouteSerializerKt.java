package androidx.navigation.serialization;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.navigation.CollectionNavType;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.serialization.NavTypeConverterKt;
import androidx.navigation.serialization.RouteBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.descriptors.ContextAwareKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.internal.KTypeWrapper;
import kotlinx.serialization.modules.SerializersModuleKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RouteSerializerKt {
    public static final NavType computeNavType(SerialDescriptor serialDescriptor, String str, Map map) {
        Object obj;
        NavType navType;
        Iterator it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            KTypeWrapper kTypeWrapper = (KTypeWrapper) ((KType) obj);
            if (serialDescriptor.isNullable() == kTypeWrapper.isMarkedNullable() && serialDescriptor.hashCode() == SerializersKt.serializer(SerializersModuleKt.EmptySerializersModule, kTypeWrapper).getDescriptor().hashCode()) {
                break;
            }
        }
        KType kType = (KType) obj;
        NavType navType2 = kType != null ? (NavType) map.get(kType) : null;
        NavType navType3 = navType2 != null ? navType2 : null;
        if (navType3 == null) {
            InternalType internalType = NavTypeConverterKt.toInternalType(serialDescriptor);
            int[] iArr = NavTypeConverterKt.WhenMappings.$EnumSwitchMapping$0;
            switch (iArr[internalType.ordinal()]) {
                case 1:
                    navType = NavType.IntType;
                    break;
                case 2:
                    navType = NavType.BoolType;
                    break;
                case 3:
                    navType = NavType.FloatType;
                    break;
                case 4:
                    navType = NavType.LongType;
                    break;
                case 5:
                    navType = NavType.StringType;
                    break;
                case 6:
                    navType = NavType.IntArrayType;
                    break;
                case 7:
                    navType = NavType.BoolArrayType;
                    break;
                case 8:
                    navType = NavType.FloatArrayType;
                    break;
                case 9:
                    navType = NavType.LongArrayType;
                    break;
                case 10:
                    if (NavTypeConverterKt.toInternalType(serialDescriptor.getElementDescriptor(0)) != InternalType.STRING) {
                        navType = UNKNOWN.INSTANCE;
                        break;
                    } else {
                        navType = NavType.StringArrayType;
                        break;
                    }
                case 11:
                    int i = iArr[NavTypeConverterKt.toInternalType(serialDescriptor.getElementDescriptor(0)).ordinal()];
                    if (i == 1) {
                        navType = NavType.IntListType;
                        break;
                    } else if (i == 2) {
                        navType = NavType.BoolListType;
                        break;
                    } else if (i == 3) {
                        navType = NavType.FloatListType;
                        break;
                    } else if (i == 4) {
                        navType = NavType.LongListType;
                        break;
                    } else if (i == 5) {
                        navType = NavType.StringListType;
                        break;
                    } else {
                        navType = UNKNOWN.INSTANCE;
                        break;
                    }
                default:
                    navType = UNKNOWN.INSTANCE;
                    break;
            }
            navType3 = navType;
        }
        if (!Intrinsics.areEqual(navType3, UNKNOWN.INSTANCE)) {
            return navType3;
        }
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Cannot cast ", str, " of type ");
        m.append(serialDescriptor.getSerialName());
        m.append(" to a NavType. Make sure to provide custom NavType for this argument.");
        throw new IllegalArgumentException(m.toString());
    }

    public static final List generateNavArguments(final KSerializer kSerializer, final Map map) {
        Function0 function0 = new Function0() { // from class: androidx.navigation.serialization.RouteSerializerKt$generateNavArguments$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                throw new IllegalArgumentException("Cannot generate NavArguments for polymorphic serializer " + KSerializer.this + ". Arguments can only be generated from concrete classes or objects.");
            }
        };
        if (kSerializer instanceof PolymorphicSerializer) {
            function0.invoke();
            throw null;
        }
        int elementsCount = kSerializer.getDescriptor().getElementsCount();
        ArrayList arrayList = new ArrayList(elementsCount);
        for (final int i = 0; i < elementsCount; i++) {
            final String elementName = kSerializer.getDescriptor().getElementName(i);
            Function1 function1 = new Function1() { // from class: androidx.navigation.serialization.RouteSerializerKt$generateNavArguments$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    NavArgumentBuilder navArgumentBuilder = (NavArgumentBuilder) obj;
                    SerialDescriptor elementDescriptor = KSerializer.this.getDescriptor().getElementDescriptor(i);
                    boolean isNullable = elementDescriptor.isNullable();
                    NavType computeNavType = RouteSerializerKt.computeNavType(elementDescriptor, elementName, map);
                    NavArgument.Builder builder = navArgumentBuilder.builder;
                    builder.type = computeNavType;
                    builder.isNullable = isNullable;
                    if (KSerializer.this.getDescriptor().isElementOptional(i)) {
                        navArgumentBuilder.builder.unknownDefaultValuePresent = true;
                    }
                    return Unit.INSTANCE;
                }
            };
            NavArgumentBuilder navArgumentBuilder = new NavArgumentBuilder();
            function1.mo779invoke(navArgumentBuilder);
            NavArgument.Builder builder = navArgumentBuilder.builder;
            NavType navType = builder.type;
            if (navType == null) {
                NavType.Companion.getClass();
                navType = NavType.StringType;
            }
            arrayList.add(new NamedNavArgument(elementName, new NavArgument(navType, builder.isNullable, null, false, builder.unknownDefaultValuePresent)));
        }
        return arrayList;
    }

    public static String generateRoutePattern$default(final KSerializer kSerializer, Map map) {
        Function0 function0 = new Function0() { // from class: androidx.navigation.serialization.RouteSerializerKt$generateRoutePattern$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StringBuilder sb = new StringBuilder("Cannot generate route pattern from polymorphic class ");
                KClass capturedKClass = ContextAwareKt.getCapturedKClass(KSerializer.this.getDescriptor());
                throw new IllegalArgumentException(TransitionKt$$ExternalSyntheticOutline0.m(sb, capturedKClass != null ? ((ClassReference) capturedKClass).getSimpleName() : null, ". Routes can only be generated from concrete classes or objects."));
            }
        };
        if (kSerializer instanceof PolymorphicSerializer) {
            function0.invoke();
            throw null;
        }
        final RouteBuilder routeBuilder = new RouteBuilder(kSerializer);
        Function3 function3 = new Function3() { // from class: androidx.navigation.serialization.RouteSerializerKt$generateRoutePattern$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int intValue = ((Number) obj).intValue();
                String str = (String) obj2;
                RouteBuilder routeBuilder2 = RouteBuilder.this;
                routeBuilder2.getClass();
                int i = RouteBuilder.WhenMappings.$EnumSwitchMapping$0[(((((NavType) obj3) instanceof CollectionNavType) || routeBuilder2.serializer.getDescriptor().isElementOptional(intValue)) ? RouteBuilder.ParamType.QUERY : RouteBuilder.ParamType.PATH).ordinal()];
                if (i == 1) {
                    routeBuilder2.pathArgs += '/' + ("{" + str + '}');
                } else if (i == 2) {
                    routeBuilder2.addQuery(str, "{" + str + '}');
                }
                return Unit.INSTANCE;
            }
        };
        int elementsCount = kSerializer.getDescriptor().getElementsCount();
        for (int i = 0; i < elementsCount; i++) {
            String elementName = kSerializer.getDescriptor().getElementName(i);
            function3.invoke(Integer.valueOf(i), elementName, computeNavType(kSerializer.getDescriptor().getElementDescriptor(i), elementName, map));
        }
        return routeBuilder.path + routeBuilder.pathArgs + routeBuilder.queryArgs;
    }

    public static final String generateRouteWithArgs(Object obj, Map map) {
        KSerializer serializer = SerializersKt.serializer(Reflection.getOrCreateKotlinClass(obj.getClass()));
        RouteEncoder routeEncoder = new RouteEncoder(serializer, map);
        routeEncoder.serializer.serialize(routeEncoder, obj);
        final Map map2 = MapsKt__MapsKt.toMap(routeEncoder.map);
        final RouteBuilder routeBuilder = new RouteBuilder(serializer);
        Function3 function3 = new Function3() { // from class: androidx.navigation.serialization.RouteSerializerKt$generateRouteWithArgs$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                int intValue = ((Number) obj2).intValue();
                String str = (String) obj3;
                List<String> list = map2.get(str);
                list.getClass();
                List<String> list2 = list;
                RouteBuilder routeBuilder2 = routeBuilder;
                routeBuilder2.getClass();
                int i = RouteBuilder.WhenMappings.$EnumSwitchMapping$0[(((((NavType) obj4) instanceof CollectionNavType) || routeBuilder2.serializer.getDescriptor().isElementOptional(intValue)) ? RouteBuilder.ParamType.QUERY : RouteBuilder.ParamType.PATH).ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        Iterator<T> it = list2.iterator();
                        while (it.hasNext()) {
                            routeBuilder2.addQuery(str, (String) it.next());
                        }
                    }
                } else {
                    if (list2.size() != 1) {
                        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Expected one value for argument ", str, ", found ");
                        m.append(list2.size());
                        m.append("values instead.");
                        throw new IllegalArgumentException(m.toString().toString());
                    }
                    routeBuilder2.pathArgs += '/' + ((String) CollectionsKt___CollectionsKt.first((List) list2));
                }
                return Unit.INSTANCE;
            }
        };
        int elementsCount = serializer.getDescriptor().getElementsCount();
        for (int i = 0; i < elementsCount; i++) {
            String elementName = serializer.getDescriptor().getElementName(i);
            NavType navType = (NavType) ((LinkedHashMap) map).get(elementName);
            if (navType == null) {
                throw new IllegalStateException("MISSING NAV TYPE");
            }
            function3.invoke(Integer.valueOf(i), elementName, navType);
        }
        return routeBuilder.path + routeBuilder.pathArgs + routeBuilder.queryArgs;
    }
}
