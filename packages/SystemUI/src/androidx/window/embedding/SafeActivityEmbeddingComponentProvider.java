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
import androidx.window.extensions.core.util.function.Predicate;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.ActivityRule;
import androidx.window.extensions.embedding.ActivityStack;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean z = false;
        if (this.safeWindowExtensionsProvider.isWindowExtensionsValid$window_release() && ReflectionUtils.validateReflection$window_release("WindowExtensions#getActivityEmbeddingComponent is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isActivityEmbeddingComponentValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                boolean z2 = false;
                Class[] clsArr = new Class[0];
                Method method = SafeActivityEmbeddingComponentProvider.this.safeWindowExtensionsProvider.loader.loadClass("androidx.window.extensions.WindowExtensions").getMethod("getActivityEmbeddingComponent", null);
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(loadClass)) {
                    z2 = true;
                }
                return Boolean.valueOf(z2);
            }
        })) {
            WindowSdkExtensions.Companion.getClass();
            int i = WindowSdkExtensions.Companion.getInstance().extensionVersion;
            if (i == 1) {
                z = hasValidVendorApiLevel1$window_release();
            } else if (i == 2) {
                z = hasValidVendorApiLevel2$window_release();
            } else if (3 <= i && i < 5) {
                z = hasValidVendorApiLevel3$window_release();
            } else if (i == 5) {
                z = hasValidVendorApiLevel5$window_release();
            } else if (6 <= i && i <= Integer.MAX_VALUE && hasValidVendorApiLevel5$window_release() && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#getEmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetEmbeddedActivityWindowInfoValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Class loadClass;
                    boolean isPublic;
                    loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                    Method method = loadClass.getMethod("getEmbeddedActivityWindowInfo", Activity.class);
                    ReflectionUtils.INSTANCE.getClass();
                    isPublic = Modifier.isPublic(method.getModifiers());
                    return Boolean.valueOf(isPublic && method.getReturnType().equals(EmbeddedActivityWindowInfo.class));
                }
            }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetEmbeddedActivityWindowInfoCallbackValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Class loadClass;
                    boolean isPublic;
                    loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                    Method method = loadClass.getMethod("setEmbeddedActivityWindowInfoCallback", Executor.class, Consumer.class);
                    ReflectionUtils.INSTANCE.getClass();
                    isPublic = Modifier.isPublic(method.getModifiers());
                    return Boolean.valueOf(isPublic);
                }
            }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodClearEmbeddedActivityWindowInfoCallbackValid$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Class loadClass;
                    boolean isPublic;
                    loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                    Class[] clsArr = new Class[0];
                    Method method = loadClass.getMethod("clearEmbeddedActivityWindowInfoCallback", null);
                    ReflectionUtils.INSTANCE.getClass();
                    isPublic = Modifier.isPublic(method.getModifiers());
                    return Boolean.valueOf(isPublic);
                }
            }) && ReflectionUtils.validateReflection$window_release("Class EmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassEmbeddedActivityWindowInfoValid$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean isPublic;
                    boolean isPublic2;
                    boolean isPublic3;
                    boolean isPublic4;
                    boolean z2 = false;
                    Class[] clsArr = new Class[0];
                    Method method = EmbeddedActivityWindowInfo.class.getMethod("getActivity", null);
                    Class[] clsArr2 = new Class[0];
                    Method method2 = EmbeddedActivityWindowInfo.class.getMethod("isEmbedded", null);
                    Class[] clsArr3 = new Class[0];
                    Method method3 = EmbeddedActivityWindowInfo.class.getMethod("getTaskBounds", null);
                    Class[] clsArr4 = new Class[0];
                    Method method4 = EmbeddedActivityWindowInfo.class.getMethod("getActivityStackBounds", null);
                    ReflectionUtils.INSTANCE.getClass();
                    isPublic = Modifier.isPublic(method.getModifiers());
                    if (isPublic && method.getReturnType().equals(Activity.class)) {
                        isPublic2 = Modifier.isPublic(method2.getModifiers());
                        if (isPublic2) {
                            if (method2.getReturnType().equals(Boolean.TYPE)) {
                                isPublic3 = Modifier.isPublic(method3.getModifiers());
                                if (isPublic3 && method3.getReturnType().equals(Rect.class)) {
                                    isPublic4 = Modifier.isPublic(method4.getModifiers());
                                    if (isPublic4 && method4.getReturnType().equals(Rect.class)) {
                                        z2 = true;
                                    }
                                }
                            }
                        }
                    }
                    return Boolean.valueOf(z2);
                }
            })) {
                z = true;
            }
        }
        if (!z) {
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
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("setEmbeddingRules", Set.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#isActivityEmbedded is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodIsActivityEmbeddedValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("isActivityEmbedded", Activity.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic && method.getReturnType().equals(Boolean.TYPE));
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetSplitInfoCallbackJavaConsumerValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class<?> cls;
                Class loadClass;
                boolean isPublic;
                ConsumerAdapter consumerAdapter = SafeActivityEmbeddingComponentProvider.this.consumerAdapter;
                consumerAdapter.getClass();
                try {
                    cls = consumerAdapter.loader.loadClass("java.util.function.Consumer");
                } catch (ClassNotFoundException unused) {
                    cls = null;
                }
                if (cls == null) {
                    return Boolean.FALSE;
                }
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("setSplitInfoCallback", cls);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getSplitRatio is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitRatioValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getSplitRatio", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    if (method.getReturnType().equals(Float.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getLayoutDirection is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetLayoutDirectionValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getLayoutDirection", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    if (method.getReturnType().equals(Integer.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityRule.class.getMethod("shouldAlwaysExpand", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    if (method.getReturnType().equals(Boolean.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleBuilderLevel1Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                Method method = ActivityRule.Builder.class.getMethod("setShouldAlwaysExpand", Boolean.TYPE);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic && method.getReturnType().equals(ActivityRule.Builder.class));
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitInfoValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getPrimaryActivityStack", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSecondaryActivityStack", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitRatio", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(androidx.window.extensions.embedding.ActivityStack.class)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2 && method2.getReturnType().equals(androidx.window.extensions.embedding.ActivityStack.class)) {
                        isPublic3 = Modifier.isPublic(method3.getModifiers());
                        if (isPublic3) {
                            if (method3.getReturnType().equals(Float.TYPE)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPairRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPairRule.class.getMethod("getFinishSecondaryWithPrimary", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPairRule.class.getMethod("shouldClearTop", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    Class cls = Integer.TYPE;
                    if (method.getReturnType().equals(cls)) {
                        isPublic2 = Modifier.isPublic(method2.getModifiers());
                        if (isPublic2 && method2.getReturnType().equals(cls)) {
                            isPublic3 = Modifier.isPublic(method3.getModifiers());
                            if (isPublic3) {
                                if (method3.getReturnType().equals(Boolean.TYPE)) {
                                    z = true;
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleBuilderLevel1Valid$1
            /* JADX WARN: Code restructure failed: missing block: B:8:0x0043, code lost:
            
                if (r5.getReturnType().equals(androidx.window.extensions.embedding.SplitPairRule.Builder.class) != false) goto L12;
             */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    r5 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r5 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.String r2 = "setSplitRatio"
                    java.lang.reflect.Method r1 = r5.getMethod(r2, r1)
                    java.lang.Class[] r2 = new java.lang.Class[r0]
                    java.lang.Class r4 = java.lang.Integer.TYPE
                    r2[r3] = r4
                    java.lang.String r4 = "setLayoutDirection"
                    java.lang.reflect.Method r5 = r5.getMethod(r4, r2)
                    androidx.window.reflection.ReflectionUtils r2 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r2.getClass()
                    boolean r2 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r1)
                    if (r2 == 0) goto L46
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r2 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    java.lang.Class r1 = r1.getReturnType()
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L46
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r5)
                    if (r1 == 0) goto L46
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r1 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    java.lang.Class r5 = r5.getReturnType()
                    boolean r5 = r5.equals(r1)
                    if (r5 == 0) goto L46
                    goto L47
                L46:
                    r0 = r3
                L47:
                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleBuilderLevel1Valid$1.invoke():java.lang.Object");
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getPlaceholderIntent", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPlaceholderRule.class.getMethod("isSticky", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(Intent.class)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2) {
                        if (method2.getReturnType().equals(Boolean.TYPE)) {
                            isPublic3 = Modifier.isPublic(method3.getModifiers());
                            if (isPublic3) {
                                if (method3.getReturnType().equals(Integer.TYPE)) {
                                    z = true;
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleBuilderLevel1Valid$1
            /* JADX WARN: Code restructure failed: missing block: B:16:0x007d, code lost:
            
                if (r7.getReturnType().equals(androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class) != false) goto L20;
             */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    r7 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r7 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.String r2 = "setSplitRatio"
                    java.lang.reflect.Method r1 = r7.getMethod(r2, r1)
                    java.lang.Class[] r2 = new java.lang.Class[r0]
                    java.lang.Class r4 = java.lang.Integer.TYPE
                    r2[r3] = r4
                    java.lang.String r5 = "setLayoutDirection"
                    java.lang.reflect.Method r2 = r7.getMethod(r5, r2)
                    java.lang.Class[] r5 = new java.lang.Class[r0]
                    java.lang.Class r6 = java.lang.Boolean.TYPE
                    r5[r3] = r6
                    java.lang.String r6 = "setSticky"
                    java.lang.reflect.Method r5 = r7.getMethod(r6, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r0]
                    r6[r3] = r4
                    java.lang.String r4 = "setFinishPrimaryWithSecondary"
                    java.lang.reflect.Method r7 = r7.getMethod(r4, r6)
                    androidx.window.reflection.ReflectionUtils r4 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r4.getClass()
                    boolean r4 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r1)
                    if (r4 == 0) goto L80
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r4 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    java.lang.Class r1 = r1.getReturnType()
                    boolean r1 = r1.equals(r4)
                    if (r1 == 0) goto L80
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r2)
                    if (r1 == 0) goto L80
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    java.lang.Class r2 = r2.getReturnType()
                    boolean r1 = r2.equals(r1)
                    if (r1 == 0) goto L80
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r5)
                    if (r1 == 0) goto L80
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    java.lang.Class r2 = r5.getReturnType()
                    boolean r1 = r2.equals(r1)
                    if (r1 == 0) goto L80
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r7)
                    if (r1 == 0) goto L80
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    java.lang.Class r7 = r7.getReturnType()
                    boolean r7 = r7.equals(r1)
                    if (r7 == 0) goto L80
                    goto L81
                L80:
                    r0 = r3
                L81:
                    java.lang.Boolean r7 = java.lang.Boolean.valueOf(r0)
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleBuilderLevel1Valid$1.invoke():java.lang.Object");
            }
        });
    }

    public final boolean hasValidVendorApiLevel2$window_release() {
        return hasValidVendorApiLevel1$window_release() && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSetSplitInfoCallbackWindowConsumerValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("setSplitInfoCallback", Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodClearSplitInfoCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Class[] clsArr = new Class[0];
                Method method = loadClass.getMethod("clearSplitInfoCallback", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitAttributesCalculator is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSplitAttributesCalculatorValid$1
            {
                super(0);
            }

            /* JADX WARN: Code restructure failed: missing block: B:4:0x0032, code lost:
            
                if (r5 != false) goto L8;
             */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    r5 = this;
                    androidx.window.embedding.SafeActivityEmbeddingComponentProvider r0 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.this
                    java.lang.Class r0 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.access$getActivityEmbeddingComponentClass(r0)
                    r1 = 1
                    java.lang.Class[] r2 = new java.lang.Class[r1]
                    java.lang.Class<androidx.window.extensions.core.util.function.Function> r3 = androidx.window.extensions.core.util.function.Function.class
                    r4 = 0
                    r2[r4] = r3
                    java.lang.String r3 = "setSplitAttributesCalculator"
                    java.lang.reflect.Method r0 = r0.getMethod(r3, r2)
                    androidx.window.embedding.SafeActivityEmbeddingComponentProvider r5 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.this
                    java.lang.Class r5 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.access$getActivityEmbeddingComponentClass(r5)
                    java.lang.Class[] r2 = new java.lang.Class[r4]
                    r2 = 0
                    java.lang.String r3 = "clearSplitAttributesCalculator"
                    java.lang.reflect.Method r5 = r5.getMethod(r3, r2)
                    androidx.window.reflection.ReflectionUtils r2 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r2.getClass()
                    boolean r0 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r0)
                    if (r0 == 0) goto L35
                    boolean r5 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r5)
                    if (r5 == 0) goto L35
                    goto L36
                L35:
                    r1 = r4
                L36:
                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSplitAttributesCalculatorValid$1.invoke():java.lang.Object");
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitPlaceholderRule#getFinishPrimaryWithPlaceholder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetFinishPrimaryWithPlaceholderValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithPlaceholder", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    if (method.getReturnType().equals(Integer.TYPE)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitRule#getDefaultSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetDefaultSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getDefaultSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean z = false;
                Constructor declaredConstructor = ActivityRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class);
                Method method = ActivityRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(declaredConstructor.getModifiers());
                if (isPublic) {
                    isPublic2 = Modifier.isPublic(method.getModifiers());
                    if (isPublic2 && method.getReturnType().equals(ActivityRule.Builder.class)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class EmbeddingRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassEmbeddingRuleValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = EmbeddingRule.class.getMethod("getTag", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(String.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean isPublic4;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getLayoutDirection", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getSplitType", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setSplitType", SplitAttributes.SplitType.class);
                Class cls = Integer.TYPE;
                Method method4 = SplitAttributes.Builder.class.getMethod("setLayoutDirection", cls);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(cls)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2 && method2.getReturnType().equals(SplitAttributes.SplitType.class)) {
                        isPublic3 = Modifier.isPublic(method3.getModifiers());
                        if (isPublic3) {
                            isPublic4 = Modifier.isPublic(method4.getModifiers());
                            if (isPublic4) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributesCalculatorParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitAttributesCalculatorParamsValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean isPublic4;
                boolean isPublic5;
                boolean isPublic6;
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
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(WindowMetrics.class)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2 && method2.getReturnType().equals(Configuration.class)) {
                        isPublic3 = Modifier.isPublic(method3.getModifiers());
                        if (isPublic3 && method3.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.class)) {
                            isPublic4 = Modifier.isPublic(method4.getModifiers());
                            if (isPublic4) {
                                if (method4.getReturnType().equals(Boolean.TYPE)) {
                                    isPublic5 = Modifier.isPublic(method5.getModifiers());
                                    if (isPublic5 && method5.getReturnType().equals(WindowLayoutInfo.class)) {
                                        isPublic6 = Modifier.isPublic(method6.getModifiers());
                                        if (isPublic6 && method6.getReturnType().equals(String.class)) {
                                            z = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitAttributes.SplitType is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitTypeValid$1
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0085, code lost:
            
                if (r10 != false) goto L22;
             */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    r10 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$RatioSplitType> r10 = androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.reflect.Constructor r1 = r10.getDeclaredConstructor(r1)
                    java.lang.Class[] r4 = new java.lang.Class[r3]
                    java.lang.String r4 = "getRatio"
                    r5 = 0
                    java.lang.reflect.Method r4 = r10.getMethod(r4, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r3]
                    java.lang.String r6 = "splitEqually"
                    java.lang.reflect.Method r10 = r10.getMethod(r6, r5)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$HingeSplitType> r6 = androidx.window.extensions.embedding.SplitAttributes.SplitType.HingeSplitType.class
                    java.lang.Class[] r7 = new java.lang.Class[r0]
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType> r8 = androidx.window.extensions.embedding.SplitAttributes.SplitType.class
                    r7[r3] = r8
                    java.lang.reflect.Constructor r7 = r6.getDeclaredConstructor(r7)
                    java.lang.Class[] r8 = new java.lang.Class[r3]
                    java.lang.String r8 = "getFallbackSplitType"
                    java.lang.reflect.Method r6 = r6.getMethod(r8, r5)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$ExpandContainersSplitType> r8 = androidx.window.extensions.embedding.SplitAttributes.SplitType.ExpandContainersSplitType.class
                    java.lang.Class[] r9 = new java.lang.Class[r3]
                    java.lang.reflect.Constructor r5 = r8.getDeclaredConstructor(r5)
                    androidx.window.reflection.ReflectionUtils r8 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r8.getClass()
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r1)
                    if (r1 == 0) goto L88
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r4)
                    if (r1 == 0) goto L88
                    java.lang.Class r1 = r4.getReturnType()
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L88
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r7)
                    if (r1 == 0) goto L88
                    boolean r1 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r10)
                    if (r1 == 0) goto L88
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$RatioSplitType> r1 = androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType.class
                    java.lang.Class r10 = r10.getReturnType()
                    boolean r10 = r10.equals(r1)
                    if (r10 == 0) goto L88
                    boolean r10 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r6)
                    if (r10 == 0) goto L88
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType> r10 = androidx.window.extensions.embedding.SplitAttributes.SplitType.class
                    java.lang.Class r1 = r6.getReturnType()
                    boolean r10 = r1.equals(r10)
                    if (r10 == 0) goto L88
                    boolean r10 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r5)
                    if (r10 == 0) goto L88
                    goto L89
                L88:
                    r0 = r3
                L89:
                    java.lang.Boolean r10 = java.lang.Boolean.valueOf(r0)
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitTypeValid$1.invoke():java.lang.Object");
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPairRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Constructor declaredConstructor = SplitPairRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPairRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPairRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(declaredConstructor.getModifiers());
                if (isPublic) {
                    isPublic2 = Modifier.isPublic(method.getModifiers());
                    if (isPublic2 && method.getReturnType().equals(SplitPairRule.Builder.class)) {
                        isPublic3 = Modifier.isPublic(method2.getModifiers());
                        if (isPublic3 && method2.getReturnType().equals(SplitPairRule.Builder.class)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitPlaceholderRuleBuilderLevel2Valid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean isPublic4;
                boolean z = false;
                Constructor declaredConstructor = SplitPlaceholderRule.Builder.class.getDeclaredConstructor(Intent.class, Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPlaceholderRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPlaceholderRule.Builder.class.getMethod("setFinishPrimaryWithPlaceholder", Integer.TYPE);
                Method method3 = SplitPlaceholderRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(declaredConstructor.getModifiers());
                if (isPublic) {
                    isPublic2 = Modifier.isPublic(method.getModifiers());
                    if (isPublic2 && method.getReturnType().equals(SplitPlaceholderRule.Builder.class)) {
                        isPublic3 = Modifier.isPublic(method2.getModifiers());
                        if (isPublic3 && method2.getReturnType().equals(SplitPlaceholderRule.Builder.class)) {
                            isPublic4 = Modifier.isPublic(method3.getModifiers());
                            if (isPublic4 && method3.getReturnType().equals(SplitPlaceholderRule.Builder.class)) {
                                z = true;
                            }
                        }
                    }
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
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Class[] clsArr = new Class[0];
                Method method = loadClass.getMethod("invalidateTopVisibleSplitAttributes", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("#updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUpdateSplitAttributesValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("updateSplitAttributes", IBinder.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodSplitInfoGetTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getToken", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(IBinder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final boolean hasValidVendorApiLevel5$window_release() {
        return hasValidVendorApiLevel3$window_release() && ReflectionUtils.validateReflection$window_release("ActivityStack#getActivityToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isActivityStackGetActivityStackTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.ActivityStack.class.getMethod("getActivityStackToken", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(ActivityStack.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("registerActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodRegisterActivityStackCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("registerActivityStackCallback", Executor.class, Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("unregisterActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUnregisterActivityStackCallbackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("unregisterActivityStackCallback", Consumer.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("#pin(unPin)TopActivityStack is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodPinUnpinTopActivityStackValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                Class loadClass2;
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPinRule.class.getMethod("isSticky", null);
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Class<?> cls = Integer.TYPE;
                Method method2 = loadClass.getMethod("pinTopActivityStack", cls, SplitPinRule.class);
                loadClass2 = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method3 = loadClass2.getMethod("unpinTopActivityStack", cls);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic) {
                    Class cls2 = Boolean.TYPE;
                    if (method.getReturnType().equals(cls2)) {
                        isPublic2 = Modifier.isPublic(method2.getModifiers());
                        if (isPublic2 && method2.getReturnType().equals(cls2)) {
                            isPublic3 = Modifier.isPublic(method3.getModifiers());
                            if (isPublic3) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodUpdateSplitAttributesWithTokenValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Class loadClass;
                boolean isPublic;
                loadClass = SafeActivityEmbeddingComponentProvider.this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
                Method method = loadClass.getMethod("updateSplitAttributes", SplitInfo.Token.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitInfoToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isMethodGetSplitInfoTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitInfoToken", null);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(SplitInfo.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class AnimationBackground is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassAnimationBackgroundValid$1
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0088, code lost:
            
                if (r8.getReturnType().equals(androidx.window.extensions.embedding.SplitAttributes.Builder.class) != false) goto L22;
             */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    r11 = this;
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground> r11 = androidx.window.extensions.embedding.AnimationBackground.class
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground$ColorBackground> r0 = androidx.window.extensions.embedding.AnimationBackground.ColorBackground.class
                    r1 = 1
                    java.lang.Class[] r2 = new java.lang.Class[r1]
                    java.lang.Class r3 = java.lang.Integer.TYPE
                    r4 = 0
                    r2[r4] = r3
                    java.lang.String r5 = "createColorBackground"
                    java.lang.reflect.Method r2 = r11.getMethod(r5, r2)
                    java.lang.String r5 = "ANIMATION_BACKGROUND_DEFAULT"
                    java.lang.reflect.Field r5 = r11.getDeclaredField(r5)
                    java.lang.Class[] r6 = new java.lang.Class[r4]
                    java.lang.String r6 = "getColor"
                    r7 = 0
                    java.lang.reflect.Method r6 = r0.getMethod(r6, r7)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes> r8 = androidx.window.extensions.embedding.SplitAttributes.class
                    java.lang.Class[] r9 = new java.lang.Class[r4]
                    java.lang.String r9 = "getAnimationBackground"
                    java.lang.reflect.Method r7 = r8.getMethod(r9, r7)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$Builder> r8 = androidx.window.extensions.embedding.SplitAttributes.Builder.class
                    java.lang.Class[] r9 = new java.lang.Class[r1]
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground> r10 = androidx.window.extensions.embedding.AnimationBackground.class
                    r9[r4] = r10
                    java.lang.String r10 = "setAnimationBackground"
                    java.lang.reflect.Method r8 = r8.getMethod(r10, r9)
                    androidx.window.reflection.ReflectionUtils r9 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r9.getClass()
                    boolean r9 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r2)
                    if (r9 == 0) goto L8b
                    java.lang.Class r2 = r2.getReturnType()
                    boolean r0 = r2.equals(r0)
                    if (r0 == 0) goto L8b
                    int r0 = r5.getModifiers()
                    boolean r0 = java.lang.reflect.Modifier.isPublic(r0)
                    if (r0 == 0) goto L8b
                    boolean r0 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r6)
                    if (r0 == 0) goto L8b
                    java.lang.Class r0 = r6.getReturnType()
                    boolean r0 = r0.equals(r3)
                    if (r0 == 0) goto L8b
                    boolean r0 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r7)
                    if (r0 == 0) goto L8b
                    java.lang.Class r0 = r7.getReturnType()
                    boolean r11 = r0.equals(r11)
                    if (r11 == 0) goto L8b
                    boolean r11 = androidx.window.reflection.ReflectionUtils.isPublic$window_release(r8)
                    if (r11 == 0) goto L8b
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$Builder> r11 = androidx.window.extensions.embedding.SplitAttributes.Builder.class
                    java.lang.Class r0 = r8.getReturnType()
                    boolean r11 = r0.equals(r11)
                    if (r11 == 0) goto L8b
                    goto L8c
                L8b:
                    r1 = r4
                L8c:
                    java.lang.Boolean r11 = java.lang.Boolean.valueOf(r1)
                    return r11
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassAnimationBackgroundValid$1.invoke():java.lang.Object");
            }
        }) && ReflectionUtils.validateReflection$window_release("Class ActivityStack.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassActivityStackTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityStack.Token.class.getMethod("toBundle", null);
                Method method2 = ActivityStack.Token.class.getMethod("readFromBundle", Bundle.class);
                Method method3 = ActivityStack.Token.class.getMethod("createFromBinder", IBinder.class);
                Field declaredField = ActivityStack.Token.class.getDeclaredField("INVALID_ACTIVITY_STACK_TOKEN");
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(Bundle.class)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2 && method2.getReturnType().equals(ActivityStack.Token.class)) {
                        isPublic3 = Modifier.isPublic(method3.getModifiers());
                        if (isPublic3 && method3.getReturnType().equals(ActivityStack.Token.class) && Modifier.isPublic(declaredField.getModifiers())) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("Class WindowAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassWindowAttributesValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                boolean isPublic2;
                boolean isPublic3;
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = WindowAttributes.class.getMethod("getDimAreaBehavior", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getWindowAttributes", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setWindowAttributes", WindowAttributes.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                if (isPublic && method.getReturnType().equals(Integer.TYPE)) {
                    isPublic2 = Modifier.isPublic(method2.getModifiers());
                    if (isPublic2 && method2.getReturnType().equals(WindowAttributes.class)) {
                        isPublic3 = Modifier.isPublic(method3.getModifiers());
                        if (isPublic3 && method3.getReturnType().equals(SplitAttributes.Builder.class)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        }) && ReflectionUtils.validateReflection$window_release("SplitInfo.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider$isClassSplitInfoTokenValid$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isPublic;
                Method method = SplitInfo.Token.class.getMethod("createFromBinder", IBinder.class);
                ReflectionUtils.INSTANCE.getClass();
                isPublic = Modifier.isPublic(method.getModifiers());
                return Boolean.valueOf(isPublic && method.getReturnType().equals(SplitInfo.Token.class));
            }
        });
    }
}
