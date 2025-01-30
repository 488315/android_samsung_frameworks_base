package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.MediaCodecFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.NNFilter;
import com.samsung.android.sume.core.filter.PluginDecorateFilter;
import com.samsung.android.sume.core.filter.PluginFilter;
import com.samsung.android.sume.core.filter.collection.ParallelFilter;
import com.samsung.android.sume.core.filter.collection.SequentialFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory;
import com.samsung.android.sume.core.plugin.PluginStore;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class MediaFilterFactory {
    private static final String TAG = Def.tagOf((Class<?>) MediaFilterFactory.class);
    private Supplier<BufferChannel> bufferChannelSupplier;
    private final Map<Class<?>, List<MediaFilterCreator>> creators;
    private final PluginStore pluginStore;

    public static final class Builder {
        private Supplier<BufferChannel> bufferChannelSupplier;
        private final Map<Class<?>, Comparator<MediaFilterCreator>> comparators;
        private final Map<Class<?>, List<MediaFilterCreator>> creators;
        private final List<PluginStore> pluginStores;

        public Builder() {
            this.pluginStores = new ArrayList();
            this.creators = new HashMap();
            this.comparators = new HashMap();
        }

        public Builder(MediaFilterFactory baseFactory) {
            this.pluginStores = new ArrayList();
            HashMap hashMap = new HashMap();
            this.creators = hashMap;
            this.comparators = new HashMap();
            hashMap.putAll(baseFactory.getCreatorRegistry());
        }

        public Builder addDefaultCreators() {
            List<MediaFilterCreator> pluginFilterCreators = new ArrayList<>();
            pluginFilterCreators.add(new PluginFilterCreator());
            this.creators.put(PluginFilter.class, pluginFilterCreators);
            this.creators.put(PluginDecorateFilter.class, pluginFilterCreators);
            addCreator(ParallelFilter.class, new ParallelFilterCreator());
            addCreator(SequentialFilter.class, new SequentialFilterCreator());
            addCreator(NNFilter.class, new NNFilterCreator());
            addCreator(MediaCodecFilter.class, new CodecFilterCreator());
            addCreator(MediaFilter.class, new MediaFilterCreator() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$Builder$$ExternalSyntheticLambda1
                @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
                public final MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
                    return MediaFilterFactory.Builder.lambda$addDefaultCreators$0(mediaFilterFactory, mFDescriptor, mediaFilter);
                }
            });
            return this;
        }

        static /* synthetic */ MediaFilter lambda$addDefaultCreators$0(MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
            try {
                Class<?> clazz = Class.forName(descriptor.getFilterId());
                return (MediaFilter) clazz.getConstructor(descriptor.getClass()).newInstance(descriptor);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("id: " + descriptor.getFilterId() + "\ndesc: " + descriptor);
            }
        }

        public Builder addPluginStore(PluginStore store) {
            this.pluginStores.add(store);
            return this;
        }

        public Builder addPluginStore(PluginStore... stores) {
            this.pluginStores.addAll(Arrays.asList(stores));
            return this;
        }

        public Builder addCreator(Class<?> type, MediaFilterCreator creator) {
            if (!this.creators.containsKey(type)) {
                this.creators.put(type, new ArrayList());
            }
            this.creators.get(type).add(0, creator);
            return this;
        }

        public Builder addCreatorComparator(Class<?> type, Comparator<MediaFilterCreator> comparator) {
            this.comparators.put(type, comparator);
            return this;
        }

        public Builder addBufferChannelSupplier(Supplier<BufferChannel> bufferChannelSupplier) {
            this.bufferChannelSupplier = bufferChannelSupplier;
            return this;
        }

        public MediaFilterFactory build() {
            this.comparators.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MediaFilterFactory.Builder.this.m392x8891efff((Class) obj, (Comparator) obj2);
                }
            });
            MediaFilterFactory factory = new MediaFilterFactory(this.creators, PluginStore.m449of(this.pluginStores));
            Supplier<BufferChannel> supplier = this.bufferChannelSupplier;
            if (supplier != null) {
                factory.setBufferChannelSupplier(supplier);
            }
            return factory;
        }

        /* renamed from: lambda$build$1$com-samsung-android-sume-core-filter-factory-MediaFilterFactory$Builder */
        /* synthetic */ void m392x8891efff(Class key, Comparator value) {
            if (this.creators.containsKey(key)) {
                this.creators.get(key).sort(value);
            }
        }
    }

    MediaFilterFactory(Map<Class<?>, List<MediaFilterCreator>> creatorRegistry, PluginStore pluginStore) {
        this.creators = creatorRegistry;
        this.pluginStore = pluginStore;
        init();
    }

    private void init() {
        Stream.concat(((List) Optional.ofNullable(this.creators.get(PluginFilter.class)).orElse(new ArrayList())).stream(), ((List) Optional.ofNullable(this.creators.get(PluginDecorateFilter.class)).orElse(new ArrayList())).stream()).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaFilterFactory.lambda$init$0((MediaFilterCreator) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaFilterFactory.this.m389x999bf6a4((MediaFilterCreator) obj);
            }
        });
        this.creators.values().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaFilterFactory.lambda$init$2((List) obj);
            }
        }).flatMap(new MediaFilterFactory$$ExternalSyntheticLambda3()).forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaFilterFactory.this.m390x351ae6a6((MediaFilterCreator) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$init$0(MediaFilterCreator e) {
        return e instanceof PluginFilterCreator;
    }

    /* renamed from: lambda$init$1$com-samsung-android-sume-core-filter-factory-MediaFilterFactory */
    /* synthetic */ void m389x999bf6a4(MediaFilterCreator e) {
        ((PluginFilterCreator) e).setPluginStore(this.pluginStore);
    }

    static /* synthetic */ boolean lambda$init$2(List e) {
        return !e.isEmpty() && (e.get(0) instanceof MediaFilterCreatorChain);
    }

    /* renamed from: lambda$init$3$com-samsung-android-sume-core-filter-factory-MediaFilterFactory */
    /* synthetic */ void m390x351ae6a6(MediaFilterCreator e) {
        ((MediaFilterCreatorChain) e).prepare(this.creators);
    }

    Map<Class<?>, List<MediaFilterCreator>> getCreatorRegistry() {
        return this.creators;
    }

    public Supplier<BufferChannel> getBufferChannelSupplier() {
        return this.bufferChannelSupplier;
    }

    void setBufferChannelSupplier(Supplier<BufferChannel> bufferChannelSupplier) {
        this.bufferChannelSupplier = bufferChannelSupplier;
    }

    public void clear() {
        this.creators.clear();
        this.pluginStore.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaFilter addAdditionalFilters(MediaFilter filter) {
        filter.getDescriptor();
        return filter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MediaFilter newFilter(final Class<?> type, final MFDescriptor descriptor, final MediaFilter successor) {
        return (MediaFilter) Optional.ofNullable(this.creators.get(type)).flatMap(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Optional findFirst;
                findFirst = ((List) obj).stream().findFirst();
                return findFirst;
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterFactory.this.m391xbaecdff6(descriptor, successor, (MediaFilterCreator) obj);
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MediaFilter addAdditionalFilters;
                addAdditionalFilters = MediaFilterFactory.this.addAdditionalFilters((MediaFilter) obj);
                return addAdditionalFilters;
            }
        }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaFilterFactory.lambda$newFilter$6(type, descriptor);
            }
        });
    }

    /* renamed from: lambda$newFilter$5$com-samsung-android-sume-core-filter-factory-MediaFilterFactory */
    /* synthetic */ MediaFilter m391xbaecdff6(MFDescriptor descriptor, MediaFilter successor, MediaFilterCreator it) {
        return it.newFilter(this, descriptor, successor);
    }

    static /* synthetic */ RuntimeException lambda$newFilter$6(Class type, MFDescriptor descriptor) {
        throw new IllegalStateException("fail to create filter: type=" + type + ", descriptor=" + descriptor);
    }

    public MediaFilter newFilter(MFDescriptor descriptor, MediaFilter successor) {
        return newFilter(descriptor.getFilterType(), descriptor, successor);
    }

    public MediaFilter newFilter(MFDescriptor descriptor) {
        return newFilter(descriptor, null);
    }
}
