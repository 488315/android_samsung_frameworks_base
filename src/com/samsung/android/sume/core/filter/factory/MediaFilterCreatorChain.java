package com.samsung.android.sume.core.filter.factory;

import android.app.PendingIntent$$ExternalSyntheticLambda2;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain;
import com.samsung.android.sume.core.plugin.PluginStore;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
class MediaFilterCreatorChain implements MediaFilterCreator {
    private List<MediaFilterCreator> creators;
    private DescriptorFinder descriptorFinder;
    private final List<Enum<?>> types;

    /* JADX INFO: Access modifiers changed from: private */
    interface DescriptorFinder {
        MFDescriptor find(MFDescriptor mFDescriptor);
    }

    static /* synthetic */ MFDescriptor lambda$prepare$4(MFDescriptor mFDescriptor) {
        return null;
    }

    public MediaFilterCreatorChain(Enum<?>... enumArr) {
        this.types = Arrays.asList(enumArr);
    }

    void prepare(final Map<Class<?>, List<MediaFilterCreator>> map) {
        List<MediaFilterCreator> list = (List) this.types.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$0(map, (Enum) obj);
            }
        }).collect(Collectors.toList());
        this.creators = list;
        this.descriptorFinder = (DescriptorFinder) list.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$1((MediaFilterCreator) obj);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$3((MediaFilterCreator) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaFilterCreatorChain.lambda$prepare$5();
            }
        });
    }

    static /* synthetic */ MediaFilterCreator lambda$prepare$0(Map map, Enum r1) {
        return (MediaFilterCreator) ((List) Optional.ofNullable((List) map.get(r1)).orElseGet(new PendingIntent$$ExternalSyntheticLambda2())).stream().findFirst().orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    static /* synthetic */ boolean lambda$prepare$1(MediaFilterCreator mediaFilterCreator) {
        return mediaFilterCreator instanceof PluginFilterCreator;
    }

    static /* synthetic */ DescriptorFinder lambda$prepare$3(final MediaFilterCreator mediaFilterCreator) {
        return new DescriptorFinder() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda0
            @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain.DescriptorFinder
            public final MFDescriptor find(MFDescriptor mFDescriptor) {
                return MediaFilterCreatorChain.lambda$prepare$2(MediaFilterCreator.this, mFDescriptor);
            }
        };
    }

    static /* synthetic */ MFDescriptor lambda$prepare$2(MediaFilterCreator mediaFilterCreator, MFDescriptor mFDescriptor) {
        return (MFDescriptor) Optional.ofNullable(((PluginFilterCreator) mediaFilterCreator).getPluginStore().get(mFDescriptor)).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PluginStore.Entry) obj).getDescriptor();
            }
        }).orElse(null);
    }

    static /* synthetic */ DescriptorFinder lambda$prepare$5() {
        return new DescriptorFinder() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain.DescriptorFinder
            public final MFDescriptor find(MFDescriptor mFDescriptor) {
                return MediaFilterCreatorChain.lambda$prepare$4(mFDescriptor);
            }
        };
    }

    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(final MediaFilterFactory mediaFilterFactory, final MFDescriptor mFDescriptor, final MediaFilter mediaFilter) {
        return (MediaFilter) Optional.ofNullable(this.descriptorFinder).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MFDescriptor find;
                find = ((MediaFilterCreatorChain.DescriptorFinder) obj).find(MFDescriptor.this);
                return find;
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.this.m9560xeb0b27c7(mediaFilterFactory, mediaFilter, (MFDescriptor) obj);
            }
        }).orElse(null);
    }

    /* renamed from: lambda$newFilter$7$com-samsung-android-sume-core-filter-factory-MediaFilterCreatorChain, reason: not valid java name */
    /* synthetic */ MediaFilter m9560xeb0b27c7(MediaFilterFactory mediaFilterFactory, MediaFilter mediaFilter, MFDescriptor mFDescriptor) {
        Iterator<MediaFilterCreator> it = this.creators.iterator();
        MediaFilter mediaFilter2 = null;
        while (it.hasNext()) {
            mediaFilter2 = it.next().newFilter(mediaFilterFactory, mFDescriptor, mediaFilter);
        }
        return mediaFilter2;
    }
}
