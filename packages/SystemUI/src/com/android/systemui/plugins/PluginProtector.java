package com.android.systemui.plugins;

import android.util.Log;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAnimationsProtector;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockControllerProtector;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockEventsProtector;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceControllerProtector;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceEventsProtector;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.plugins.clocks.ClockFaceLayoutProtector;
import com.android.systemui.plugins.clocks.ClockProvider;
import com.android.systemui.plugins.clocks.ClockProviderPlugin;
import com.android.systemui.plugins.clocks.ClockProviderPluginProtector;
import com.android.systemui.plugins.clocks.ClockProviderProtector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginProtector {
    private static final String TAG = "PluginProtector";
    private static final Map<Class, Factory> sFactories;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface Factory {
        Object create(Object obj, ProtectedPluginListener protectedPluginListener);
    }

    static {
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        final int i4 = 3;
        final int i5 = 4;
        final int i6 = 5;
        final int i7 = 6;
        final int i8 = 7;
        final int i9 = 8;
        sFactories = Map.ofEntries(Map.entry(TestPlugin.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockAnimations.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i2) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockController.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i3) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockEvents.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i4) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockFaceController.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i5) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockFaceEvents.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i6) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockFaceLayout.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i7) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockProvider.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i8) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }), Map.entry(ClockProviderPlugin.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.PluginProtector.Factory
            public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
                Object lambda$static$0;
                Object lambda$static$1;
                Object lambda$static$2;
                Object lambda$static$3;
                Object lambda$static$4;
                Object lambda$static$5;
                Object lambda$static$6;
                Object lambda$static$7;
                Object lambda$static$8;
                switch (i9) {
                    case 0:
                        lambda$static$0 = PluginProtector.lambda$static$0(obj, protectedPluginListener);
                        return lambda$static$0;
                    case 1:
                        lambda$static$1 = PluginProtector.lambda$static$1(obj, protectedPluginListener);
                        return lambda$static$1;
                    case 2:
                        lambda$static$2 = PluginProtector.lambda$static$2(obj, protectedPluginListener);
                        return lambda$static$2;
                    case 3:
                        lambda$static$3 = PluginProtector.lambda$static$3(obj, protectedPluginListener);
                        return lambda$static$3;
                    case 4:
                        lambda$static$4 = PluginProtector.lambda$static$4(obj, protectedPluginListener);
                        return lambda$static$4;
                    case 5:
                        lambda$static$5 = PluginProtector.lambda$static$5(obj, protectedPluginListener);
                        return lambda$static$5;
                    case 6:
                        lambda$static$6 = PluginProtector.lambda$static$6(obj, protectedPluginListener);
                        return lambda$static$6;
                    case 7:
                        lambda$static$7 = PluginProtector.lambda$static$7(obj, protectedPluginListener);
                        return lambda$static$7;
                    default:
                        lambda$static$8 = PluginProtector.lambda$static$8(obj, protectedPluginListener);
                        return lambda$static$8;
                }
            }
        }));
    }

    private PluginProtector() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$0(Object obj, ProtectedPluginListener protectedPluginListener) {
        return TestPluginProtector.protect((TestPlugin) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$1(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockAnimationsProtector.protect((ClockAnimations) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$2(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockControllerProtector.protect((ClockController) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$3(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockEventsProtector.protect((ClockEvents) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$4(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockFaceControllerProtector.protect((ClockFaceController) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$5(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockFaceEventsProtector.protect((ClockFaceEvents) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$6(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockFaceLayoutProtector.protect((ClockFaceLayout) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$7(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockProviderProtector.protect((ClockProvider) obj, protectedPluginListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$8(Object obj, ProtectedPluginListener protectedPluginListener) {
        return ClockProviderPluginProtector.protect((ClockProviderPlugin) obj, protectedPluginListener);
    }

    public static <T> T protectIfAble(T t, ProtectedPluginListener protectedPluginListener) {
        T t2 = (T) tryProtect(t, protectedPluginListener);
        return t2 != null ? t2 : t;
    }

    public static <T> T tryProtect(T t, ProtectedPluginListener protectedPluginListener) {
        int i;
        HashSet hashSet = new HashSet();
        Class<?> cls = t.getClass();
        while (true) {
            i = 0;
            if (cls == null) {
                break;
            }
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            while (i < length) {
                hashSet.add(interfaces[i]);
                i++;
            }
            cls = cls.getSuperclass();
        }
        Iterator it = hashSet.iterator();
        Factory factory = null;
        while (it.hasNext()) {
            Factory factory2 = sFactories.get((Class) it.next());
            if (factory2 != null) {
                i++;
                factory = factory2;
            }
        }
        if (factory != null) {
            if (i < 2) {
                return (T) factory.create(t, protectedPluginListener);
            }
            throw new UnsupportedOperationException("Plugin implements more than one protected interface");
        }
        Log.i(TAG, "Wasn't able to wrap " + t);
        return null;
    }
}
