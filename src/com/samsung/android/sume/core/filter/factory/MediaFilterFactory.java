package com.samsung.android.sume.core.filter.factory;

import android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda8;
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

/* loaded from: classes6.dex */
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

        public Builder(MediaFilterFactory mediaFilterFactory) {
            this.pluginStores = new ArrayList();
            HashMap hashMap = new HashMap();
            this.creators = hashMap;
            this.comparators = new HashMap();
            hashMap.putAll(mediaFilterFactory.getCreatorRegistry());
        }

        public Builder addDefaultCreators() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new PluginFilterCreator());
            this.creators.put(PluginFilter.class, arrayList);
            this.creators.put(PluginDecorateFilter.class, arrayList);
            addCreator(ParallelFilter.class, new ParallelFilterCreator());
            addCreator(SequentialFilter.class, new SequentialFilterCreator());
            addCreator(NNFilter.class, new NNFilterCreator());
            addCreator(MediaCodecFilter.class, new CodecFilterCreator());
            addCreator(MediaFilter.class, new MediaFilterCreator() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$Builder$$ExternalSyntheticLambda0
                @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
                public final MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
                    return MediaFilterFactory.Builder.lambda$addDefaultCreators$0(mediaFilterFactory, mFDescriptor, mediaFilter);
                }
            });
            return this;
        }

        static /* synthetic */ MediaFilter lambda$addDefaultCreators$0(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
            try {
                return (MediaFilter) Class.forName(mFDescriptor.getFilterId()).getConstructor(mFDescriptor.getClass()).newInstance(mFDescriptor);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("id: " + mFDescriptor.getFilterId() + "\ndesc: " + mFDescriptor);
            }
        }

        public Builder addPluginStore(PluginStore pluginStore) {
            this.pluginStores.add(pluginStore);
            return this;
        }

        public Builder addPluginStore(PluginStore... pluginStoreArr) {
            this.pluginStores.addAll(Arrays.asList(pluginStoreArr));
            return this;
        }

        public Builder addCreator(Class<?> cls, MediaFilterCreator mediaFilterCreator) {
            if (!this.creators.containsKey(cls)) {
                this.creators.put(cls, new ArrayList());
            }
            this.creators.get(cls).add(0, mediaFilterCreator);
            return this;
        }

        public Builder addCreatorComparator(Class<?> cls, Comparator<MediaFilterCreator> comparator) {
            this.comparators.put(cls, comparator);
            return this;
        }

        public Builder addBufferChannelSupplier(Supplier<BufferChannel> supplier) {
            this.bufferChannelSupplier = supplier;
            return this;
        }

        public MediaFilterFactory build() {
            this.comparators.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MediaFilterFactory.Builder.this.m9564x8891efff((Class) obj, (Comparator) obj2);
                }
            });
            MediaFilterFactory mediaFilterFactory = new MediaFilterFactory(this.creators, PluginStore.of(this.pluginStores));
            Supplier<BufferChannel> supplier = this.bufferChannelSupplier;
            if (supplier != null) {
                mediaFilterFactory.setBufferChannelSupplier(supplier);
            }
            return mediaFilterFactory;
        }

        /* renamed from: lambda$build$1$com-samsung-android-sume-core-filter-factory-MediaFilterFactory$Builder, reason: not valid java name */
        /* synthetic */ void m9564x8891efff(Class cls, Comparator comparator) {
            if (this.creators.containsKey(cls)) {
                this.creators.get(cls).sort(comparator);
            }
        }
    }

    MediaFilterFactory(Map<Class<?>, List<MediaFilterCreator>> map, PluginStore pluginStore) {
        this.creators = map;
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
                MediaFilterFactory.this.m9561x999bf6a4((MediaFilterCreator) obj);
            }
        });
        this.creators.values().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaFilterFactory.lambda$init$2((List) obj);
            }
        }).flatMap(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaFilterFactory.this.m9562x351ae6a6((MediaFilterCreator) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$init$0(MediaFilterCreator mediaFilterCreator) {
        return mediaFilterCreator instanceof PluginFilterCreator;
    }

    /* renamed from: lambda$init$1$com-samsung-android-sume-core-filter-factory-MediaFilterFactory, reason: not valid java name */
    /* synthetic */ void m9561x999bf6a4(MediaFilterCreator mediaFilterCreator) {
        ((PluginFilterCreator) mediaFilterCreator).setPluginStore(this.pluginStore);
    }

    static /* synthetic */ boolean lambda$init$2(List list) {
        return !list.isEmpty() && (list.get(0) instanceof MediaFilterCreatorChain);
    }

    /* renamed from: lambda$init$3$com-samsung-android-sume-core-filter-factory-MediaFilterFactory, reason: not valid java name */
    /* synthetic */ void m9562x351ae6a6(MediaFilterCreator mediaFilterCreator) {
        ((MediaFilterCreatorChain) mediaFilterCreator).prepare(this.creators);
    }

    Map<Class<?>, List<MediaFilterCreator>> getCreatorRegistry() {
        return this.creators;
    }

    public Supplier<BufferChannel> getBufferChannelSupplier() {
        return this.bufferChannelSupplier;
    }

    void setBufferChannelSupplier(Supplier<BufferChannel> supplier) {
        this.bufferChannelSupplier = supplier;
    }

    public void clear() {
        this.creators.clear();
        this.pluginStore.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaFilter addAdditionalFilters(MediaFilter mediaFilter) {
        mediaFilter.getDescriptor();
        return mediaFilter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MediaFilter newFilter(final Class<?> cls, final MFDescriptor mFDescriptor, final MediaFilter mediaFilter) {
        return (MediaFilter) Optional.ofNullable(this.creators.get(cls)).flatMap(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Optional findFirst;
                findFirst = ((List) obj).stream().findFirst();
                return findFirst;
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterFactory.this.m9563xbaecdff6(mFDescriptor, mediaFilter, (MediaFilterCreator) obj);
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MediaFilter addAdditionalFilters;
                addAdditionalFilters = MediaFilterFactory.this.addAdditionalFilters((MediaFilter) obj);
                return addAdditionalFilters;
            }
        }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaFilterFactory.lambda$newFilter$6(cls, mFDescriptor);
            }
        });
    }

    /* renamed from: lambda$newFilter$5$com-samsung-android-sume-core-filter-factory-MediaFilterFactory, reason: not valid java name */
    /* synthetic */ MediaFilter m9563xbaecdff6(MFDescriptor mFDescriptor, MediaFilter mediaFilter, MediaFilterCreator mediaFilterCreator) {
        return mediaFilterCreator.newFilter(this, mFDescriptor, mediaFilter);
    }

    static /* synthetic */ RuntimeException lambda$newFilter$6(Class cls, MFDescriptor mFDescriptor) {
        throw new IllegalStateException("fail to create filter: type=" + cls + ", descriptor=" + mFDescriptor);
    }

    public MediaFilter newFilter(MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        return newFilter(mFDescriptor.getFilterType(), mFDescriptor, mediaFilter);
    }

    public MediaFilter newFilter(MFDescriptor mFDescriptor) {
        return newFilter(mFDescriptor, null);
    }
}
