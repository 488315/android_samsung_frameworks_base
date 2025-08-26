package com.samsung.android.sume.core.plugin;

import android.content.Context;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda13;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.plugin.PluginStore;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class PluginStore {
    private static final String TAG = Def.tagOf((Class<?>) PluginStore.class);
    protected Context context;
    protected Map<String, Entry> registry;

    protected abstract PluginStore add(PluginFixture<?> pluginFixture);

    public abstract Entry get(MFDescriptor mFDescriptor);

    public abstract PluginFixture<?> remove(PluginFixture<?> pluginFixture);

    public static class Entry {
        MFDescriptor descriptor;
        PluginFixture<?> pluginFixture;

        public Entry(MFDescriptor mFDescriptor, PluginFixture<?> pluginFixture) {
            this.descriptor = mFDescriptor;
            this.pluginFixture = pluginFixture;
        }

        public MFDescriptor getDescriptor() {
            return this.descriptor;
        }

        public void setDescriptor(MFDescriptor mFDescriptor) {
            this.descriptor = mFDescriptor;
        }

        public PluginFixture<?> getPluginFixture() {
            return this.pluginFixture;
        }

        public void setPluginFixture(PluginFixture<?> pluginFixture) {
            this.pluginFixture = pluginFixture;
        }
    }

    public PluginStore(Context context) {
        this.registry = new LinkedHashMap();
        this.context = context;
    }

    public PluginStore(Context context, Map<String, Entry> map) {
        this.context = context;
        this.registry = map;
    }

    private String getPluginName(Plugin<?> plugin) {
        if (plugin instanceof PluginAdapter) {
            return ((PluginAdapter) plugin).getPluginType().getName();
        }
        return (String) Arrays.stream(plugin.getClass().getGenericInterfaces()).filter(new Predicate() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PluginStore.lambda$getPluginName$0((Type) obj);
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ParameterizedType) ((Type) obj)).getActualTypeArguments()[0].getTypeName();
            }
        }).filter(new Predicate() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).startsWith("com.samsung.android");
            }
        }).findFirst().orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
    }

    static /* synthetic */ boolean lambda$getPluginName$0(Type type) {
        return type instanceof ParameterizedType;
    }

    public PluginStore add(Plugin<?>... pluginArr) {
        Arrays.stream(pluginArr).flatMap(new Function() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Plugin) obj).stream();
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9616lambda$add$3$comsamsungandroidsumecorepluginPluginStore((Plugin) obj);
            }
        });
        return this;
    }

    /* renamed from: lambda$add$3$com-samsung-android-sume-core-plugin-PluginStore, reason: not valid java name */
    /* synthetic */ void m9616lambda$add$3$comsamsungandroidsumecorepluginPluginStore(Plugin plugin) {
        PluginFixture<?> nNPlugin;
        try {
            String pluginName = getPluginName(plugin);
            Log.d(TAG, "found plugin type:" + pluginName);
            if (ImgpPlugin.class.getName().equals(pluginName)) {
                nNPlugin = new ImgpPlugin(plugin);
            } else {
                if (!NNPlugin.class.getName().equals(pluginName)) {
                    throw new IllegalArgumentException("unknown plugin type: " + pluginName);
                }
                nNPlugin = new NNPlugin(plugin);
            }
            nNPlugin.init(this.context);
            add(nNPlugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void clear() {
        this.registry.values().forEach(new Consumer() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PluginStore.Entry) obj).getPluginFixture().release();
            }
        });
        this.registry.clear();
    }

    public Context getContext() {
        return this.context;
    }

    public Stream<String> keyStream() {
        return this.registry.keySet().stream();
    }

    public static PluginStore of() {
        return of((Context) null);
    }

    public static PluginStore of(Context context) {
        return new StaplePluginStore(context);
    }

    public static PluginStore of(PluginStore... pluginStoreArr) {
        return of((List<PluginStore>) Arrays.asList(pluginStoreArr));
    }

    public static PluginStore of(List<PluginStore> list) {
        Def.require(!list.isEmpty());
        PluginStore pluginStoreOf = of((Context) list.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PluginStore.lambda$of$5((PluginStore) obj);
            }
        }).findFirst().flatMap(new Function() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Optional.ofNullable(((PluginStore) obj).getContext());
            }
        }).orElse(null));
        if (list.size() == 1) {
            pluginStoreOf.registry = list.get(0).registry;
            return pluginStoreOf;
        }
        pluginStoreOf.registry = (Map) list.stream().map(new Function() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PluginStore) obj).registry;
            }
        }).reduce(new BinaryOperator() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return PluginStore.lambda$of$8((Map) obj, (Map) obj2);
            }
        }).orElse(new HashMap());
        return pluginStoreOf;
    }

    static /* synthetic */ boolean lambda$of$5(PluginStore pluginStore) {
        return pluginStore.context != null;
    }

    static /* synthetic */ Map lambda$of$8(Map map, Map map2) {
        return (Map) Stream.concat(map.entrySet().stream(), map2.entrySet().stream()).collect(Collectors.toMap(new MediaBufferBase$$ExternalSyntheticLambda3(), new Function() { // from class: com.samsung.android.sume.core.plugin.PluginStore$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (PluginStore.Entry) ((Map.Entry) obj).getValue();
            }
        }));
    }
}
