package androidx.window.embedding;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowMetrics;
import androidx.window.SafeWindowExtensionsProvider;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.WindowExtensions;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.core.util.function.Function;
import androidx.window.extensions.core.util.function.Predicate;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.ActivityRule;
import androidx.window.extensions.embedding.ActivityStack;
import androidx.window.extensions.embedding.AnimationBackground;
import androidx.window.extensions.embedding.EmbeddedActivityWindowInfo;
import androidx.window.extensions.embedding.SplitAttributes;
import androidx.window.extensions.embedding.SplitAttributesCalculatorParams;
import androidx.window.extensions.embedding.SplitInfo;
import androidx.window.extensions.embedding.SplitPairRule;
import androidx.window.extensions.embedding.SplitPinRule;
import androidx.window.extensions.embedding.SplitPlaceholderRule;
import androidx.window.extensions.embedding.SplitRule;
import androidx.window.extensions.embedding.WindowAttributes;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class SafeActivityEmbeddingComponentProvider {
    public final ConsumerAdapter consumerAdapter;
    public final ClassLoader loader;
    public final SafeWindowExtensionsProvider safeWindowExtensionsProvider;
    public final WindowExtensions windowExtensions;

    public SafeActivityEmbeddingComponentProvider(ClassLoader classLoader, ConsumerAdapter consumerAdapter, WindowExtensions windowExtensions) {
        this.loader = classLoader;
        this.consumerAdapter = consumerAdapter;
        this.windowExtensions = windowExtensions;
        this.safeWindowExtensionsProvider = new SafeWindowExtensionsProvider(classLoader);
    }

    public final ActivityEmbeddingComponent getActivityEmbeddingComponent() {
        boolean zHasValidVendorApiLevel5$window_release = false;
        if (this.safeWindowExtensionsProvider.isWindowExtensionsValid$window_release() && ReflectionUtils.validateReflection$window_release("WindowExtensions#getActivityEmbeddingComponent is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isActivityEmbeddingComponentValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = this.this$0.safeWindowExtensionsProvider.loader.loadClass("androidx.window.extensions.WindowExtensions").getMethod("getActivityEmbeddingComponent", null);
                Class clsLoadClass = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(clsLoadClass)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        })) {
            WindowSdkExtensions.Companion.getClass();
            int i = WindowSdkExtensions.Companion.getInstance().extensionVersion;
            if (i == 1) {
                zHasValidVendorApiLevel5$window_release = hasValidVendorApiLevel1$window_release();
            } else if (i == 2) {
                zHasValidVendorApiLevel5$window_release = hasValidVendorApiLevel2$window_release();
            } else if (3 <= i && i < 5) {
                zHasValidVendorApiLevel5$window_release = hasValidVendorApiLevel3$window_release();
            } else if (i == 5) {
                zHasValidVendorApiLevel5$window_release = hasValidVendorApiLevel5$window_release();
            } else if (6 <= i && i <= Integer.MAX_VALUE && hasValidVendorApiLevel5$window_release() && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#getEmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetEmbeddedActivityWindowInfoValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws NoSuchMethodException, SecurityException {
                    Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("getEmbeddedActivityWindowInfo", Activity.class);
                    ReflectionUtils.INSTANCE.getClass();
                    return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(EmbeddedActivityWindowInfo.class));
                }
            }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetEmbeddedActivityWindowInfoCallbackValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws NoSuchMethodException, SecurityException {
                    Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("setEmbeddedActivityWindowInfoCallback", Executor.class, Consumer.class);
                    ReflectionUtils.INSTANCE.getClass();
                    return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
                }
            }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodClearEmbeddedActivityWindowInfoCallbackValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws NoSuchMethodException, SecurityException {
                    Class[] clsArr = new Class[0];
                    Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("clearEmbeddedActivityWindowInfoCallback", null);
                    ReflectionUtils.INSTANCE.getClass();
                    return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
                }
            }) && ReflectionUtils.validateReflection$window_release("Class EmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassEmbeddedActivityWindowInfoValid$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws NoSuchMethodException, SecurityException {
                    boolean z = false;
                    Class[] clsArr = new Class[0];
                    Method method = EmbeddedActivityWindowInfo.class.getMethod("getActivity", null);
                    Class[] clsArr2 = new Class[0];
                    Method method2 = EmbeddedActivityWindowInfo.class.getMethod("isEmbedded", null);
                    Class[] clsArr3 = new Class[0];
                    Method method3 = EmbeddedActivityWindowInfo.class.getMethod("getTaskBounds", null);
                    Class[] clsArr4 = new Class[0];
                    Method method4 = EmbeddedActivityWindowInfo.class.getMethod("getActivityStackBounds", null);
                    ReflectionUtils.INSTANCE.getClass();
                    if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Activity.class) && Modifier.isPublic(method2.getModifiers())) {
                        if (method2.getReturnType().equals(Boolean.TYPE) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(Rect.class) && Modifier.isPublic(method4.getModifiers()) && method4.getReturnType().equals(Rect.class)) {
                            z = true;
                        }
                    }
                    return Boolean.valueOf(z);
                }
            })) {
                zHasValidVendorApiLevel5$window_release = true;
            }
        }
        if (!zHasValidVendorApiLevel5$window_release) {
            return null;
        }
        try {
            return this.windowExtensions.getActivityEmbeddingComponent();
        } catch (UnsupportedOperationException unused) {
            return null;
        }
    }

    public final boolean hasValidVendorApiLevel1$window_release() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setEmbeddingRules is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetEmbeddingRulesValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("setEmbeddingRules", Set.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#isActivityEmbedded is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodIsActivityEmbeddedValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("isActivityEmbedded", Activity.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Boolean.TYPE));
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetSplitInfoCallbackJavaConsumerValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
                Class<?> clsLoadClass;
                ConsumerAdapter consumerAdapter = this.this$0.consumerAdapter;
                consumerAdapter.getClass();
                try {
                    clsLoadClass = consumerAdapter.loader.loadClass("java.util.function.Consumer");
                } catch (ClassNotFoundException unused) {
                    clsLoadClass = null;
                }
                if (clsLoadClass == null) {
                    return Boolean.FALSE;
                }
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("setSplitInfoCallback", clsLoadClass);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getSplitRatio is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitRatioValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getSplitRatio", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.getReturnType().equals(Float.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getLayoutDirection is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetLayoutDirectionValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getLayoutDirection", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.getReturnType().equals(Integer.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityRule.class.getMethod("shouldAlwaysExpand", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.getReturnType().equals(Boolean.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleBuilderLevel1Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = ActivityRule.Builder.class.getMethod("setShouldAlwaysExpand", Boolean.TYPE);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(ActivityRule.Builder.class));
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitInfoValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getPrimaryActivityStack", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSecondaryActivityStack", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitRatio", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(androidx.window.extensions.embedding.ActivityStack.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(androidx.window.extensions.embedding.ActivityStack.class) && Modifier.isPublic(method3.getModifiers())) {
                    if (method3.getReturnType().equals(Float.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPairRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPairRule.class.getMethod("getFinishSecondaryWithPrimary", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPairRule.class.getMethod("shouldClearTop", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    Class cls = Integer.TYPE;
                    if (method.getReturnType().equals(cls) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(cls) && Modifier.isPublic(method3.getModifiers())) {
                        if (method3.getReturnType().equals(Boolean.TYPE)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleBuilderLevel1Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = SplitPairRule.Builder.class.getMethod("setSplitRatio", Float.TYPE);
                Method method2 = SplitPairRule.Builder.class.getMethod("setLayoutDirection", Integer.TYPE);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitPairRule.Builder.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitPairRule.Builder.class));
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getPlaceholderIntent", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPlaceholderRule.class.getMethod("isSticky", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Intent.class) && Modifier.isPublic(method2.getModifiers())) {
                    if (method2.getReturnType().equals(Boolean.TYPE) && Modifier.isPublic(method3.getModifiers())) {
                        if (method3.getReturnType().equals(Integer.TYPE)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleBuilderLevel1Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = SplitPlaceholderRule.Builder.class.getMethod("setSplitRatio", Float.TYPE);
                Class cls = Integer.TYPE;
                Method method2 = SplitPlaceholderRule.Builder.class.getMethod("setLayoutDirection", cls);
                Method method3 = SplitPlaceholderRule.Builder.class.getMethod("setSticky", Boolean.TYPE);
                Method method4 = SplitPlaceholderRule.Builder.class.getMethod("setFinishPrimaryWithSecondary", cls);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitPlaceholderRule.Builder.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitPlaceholderRule.Builder.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(SplitPlaceholderRule.Builder.class) && Modifier.isPublic(method4.getModifiers()) && method4.getReturnType().equals(SplitPlaceholderRule.Builder.class));
            }
        });
    }

    public final boolean hasValidVendorApiLevel2$window_release() {
        return hasValidVendorApiLevel1$window_release() && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetSplitInfoCallbackWindowConsumerValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("setSplitInfoCallback", Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodClearSplitInfoCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Class[] clsArr = new Class[0];
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("clearSplitInfoCallback", null);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitAttributesCalculator is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSplitAttributesCalculatorValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("setSplitAttributesCalculator", Function.class);
                Class[] clsArr = new Class[0];
                Method method2 = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("clearSplitAttributesCalculator", null);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && Modifier.isPublic(method2.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitPlaceholderRule#getFinishPrimaryWithPlaceholder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetFinishPrimaryWithPlaceholderValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithPlaceholder", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.getReturnType().equals(Integer.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getDefaultSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetDefaultSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getDefaultSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Constructor declaredConstructor = ActivityRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class);
                Method method = ActivityRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(declaredConstructor.getModifiers()) && Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(ActivityRule.Builder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class EmbeddingRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassEmbeddingRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = EmbeddingRule.class.getMethod("getTag", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(String.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getLayoutDirection", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getSplitType", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setSplitType", SplitAttributes.SplitType.class);
                Class cls = Integer.TYPE;
                Method method4 = SplitAttributes.Builder.class.getMethod("setLayoutDirection", cls);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(cls) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitAttributes.SplitType.class) && Modifier.isPublic(method3.getModifiers()) && Modifier.isPublic(method4.getModifiers())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributesCalculatorParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitAttributesCalculatorParamsValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitAttributesCalculatorParams.class.getMethod("getParentWindowMetrics", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitAttributesCalculatorParams.class.getMethod("getParentConfiguration", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitAttributesCalculatorParams.class.getMethod("getDefaultSplitAttributes", null);
                Class[] clsArr4 = new Class[0];
                Method method4 = SplitAttributesCalculatorParams.class.getMethod("areDefaultConstraintsSatisfied", null);
                Class[] clsArr5 = new Class[0];
                Method method5 = SplitAttributesCalculatorParams.class.getMethod("getParentWindowLayoutInfo", null);
                Class[] clsArr6 = new Class[0];
                Method method6 = SplitAttributesCalculatorParams.class.getMethod("getSplitRuleTag", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(WindowMetrics.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(Configuration.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class) && Modifier.isPublic(method4.getModifiers())) {
                    if (method4.getReturnType().equals(Boolean.TYPE) && Modifier.isPublic(method5.getModifiers()) && method5.getReturnType().equals(WindowLayoutInfo.class) && Modifier.isPublic(method6.getModifiers()) && method6.getReturnType().equals(String.class)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributes.SplitType is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitTypeValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Class cls = Float.TYPE;
                Constructor declaredConstructor = SplitAttributes.SplitType.RatioSplitType.class.getDeclaredConstructor(cls);
                Class[] clsArr = new Class[0];
                Method method = SplitAttributes.SplitType.RatioSplitType.class.getMethod("getRatio", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitAttributes.SplitType.RatioSplitType.class.getMethod("splitEqually", null);
                Constructor declaredConstructor2 = SplitAttributes.SplitType.HingeSplitType.class.getDeclaredConstructor(SplitAttributes.SplitType.class);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitAttributes.SplitType.HingeSplitType.class.getMethod("getFallbackSplitType", null);
                Class[] clsArr4 = new Class[0];
                Constructor declaredConstructor3 = SplitAttributes.SplitType.ExpandContainersSplitType.class.getDeclaredConstructor(null);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(declaredConstructor.getModifiers()) && Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(cls) && Modifier.isPublic(declaredConstructor2.getModifiers()) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitAttributes.SplitType.RatioSplitType.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(SplitAttributes.SplitType.class) && Modifier.isPublic(declaredConstructor3.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Constructor declaredConstructor = SplitPairRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPairRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPairRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(declaredConstructor.getModifiers()) && Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitPairRule.Builder.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitPairRule.Builder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Constructor declaredConstructor = SplitPlaceholderRule.Builder.class.getDeclaredConstructor(Intent.class, Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPlaceholderRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPlaceholderRule.Builder.class.getMethod("setFinishPrimaryWithPlaceholder", Integer.TYPE);
                Method method3 = SplitPlaceholderRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(declaredConstructor.getModifiers()) && Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitPlaceholderRule.Builder.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(SplitPlaceholderRule.Builder.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(SplitPlaceholderRule.Builder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final boolean hasValidVendorApiLevel3$window_release() {
        return hasValidVendorApiLevel2$window_release() && ReflectionUtils.validateReflection$window_release("#invalidateTopVisibleSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodInvalidateTopVisibleSplitAttributesValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Class[] clsArr = new Class[0];
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("invalidateTopVisibleSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("#updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUpdateSplitAttributesValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("updateSplitAttributes", IBinder.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSplitInfoGetTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getToken", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(IBinder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final boolean hasValidVendorApiLevel5$window_release() {
        return hasValidVendorApiLevel3$window_release() && ReflectionUtils.validateReflection$window_release("ActivityStack#getActivityToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isActivityStackGetActivityStackTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.ActivityStack.class.getMethod("getActivityStackToken", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(ActivityStack.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("registerActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodRegisterActivityStackCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("registerActivityStackCallback", Executor.class, Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("unregisterActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUnregisterActivityStackCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("unregisterActivityStackCallback", Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("#pin(unPin)TopActivityStack is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodPinUnpinTopActivityStackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPinRule.class.getMethod("isSticky", null);
                Class clsLoadClass = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Class<?> cls = Integer.TYPE;
                Method method2 = clsLoadClass.getMethod("pinTopActivityStack", cls, SplitPinRule.class);
                Method method3 = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("unpinTopActivityStack", cls);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers())) {
                    Class cls2 = Boolean.TYPE;
                    if (method.getReturnType().equals(cls2) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(cls2) && Modifier.isPublic(method3.getModifiers())) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUpdateSplitAttributesWithTokenValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = this.this$0.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent").getMethod("updateSplitAttributes", SplitInfo.Token.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()));
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitInfoToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitInfoTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitInfoToken", null);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitInfo.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class AnimationBackground is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassAnimationBackgroundValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchFieldException, NoSuchMethodException, SecurityException {
                Class cls = Integer.TYPE;
                Method method = AnimationBackground.class.getMethod("createColorBackground", cls);
                Field declaredField = AnimationBackground.class.getDeclaredField("ANIMATION_BACKGROUND_DEFAULT");
                Class[] clsArr = new Class[0];
                Method method2 = AnimationBackground.ColorBackground.class.getMethod("getColor", null);
                Class[] clsArr2 = new Class[0];
                Method method3 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getAnimationBackground", null);
                Method method4 = SplitAttributes.Builder.class.getMethod("setAnimationBackground", AnimationBackground.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(AnimationBackground.ColorBackground.class) && Modifier.isPublic(declaredField.getModifiers()) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(cls) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(AnimationBackground.class) && Modifier.isPublic(method4.getModifiers()) && method4.getReturnType().equals(SplitAttributes.Builder.class));
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityStack.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityStackTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchFieldException, NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityStack.Token.class.getMethod("toBundle", null);
                Method method2 = ActivityStack.Token.class.getMethod("readFromBundle", Bundle.class);
                Method method3 = ActivityStack.Token.class.getMethod("createFromBinder", IBinder.class);
                Field declaredField = ActivityStack.Token.class.getDeclaredField("INVALID_ACTIVITY_STACK_TOKEN");
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Bundle.class) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(ActivityStack.Token.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(ActivityStack.Token.class) && Modifier.isPublic(declaredField.getModifiers())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class WindowAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassWindowAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = WindowAttributes.class.getMethod("getDimAreaBehavior", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getWindowAttributes", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setWindowAttributes", WindowAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                if (Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Integer.TYPE) && Modifier.isPublic(method2.getModifiers()) && method2.getReturnType().equals(WindowAttributes.class) && Modifier.isPublic(method3.getModifiers()) && method3.getReturnType().equals(SplitAttributes.Builder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitInfoTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws NoSuchMethodException, SecurityException {
                Method method = SplitInfo.Token.class.getMethod("createFromBinder", IBinder.class);
                ReflectionUtils.INSTANCE.getClass();
                return Boolean.valueOf(Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(SplitInfo.Token.class));
            }
        });
    }
}
