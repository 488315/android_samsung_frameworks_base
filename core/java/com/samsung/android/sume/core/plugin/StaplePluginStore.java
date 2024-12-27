package com.samsung.android.sume.core.plugin;

import android.content.Context;

import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptorParser;
import com.samsung.android.sume.core.functional.DescriptorLoader;
import com.samsung.android.sume.core.functional.DescriptorStreamLoader;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class StaplePluginStore extends PluginStore {
    public StaplePluginStore(Context context) {
        super(context);
    }

    @Override // com.samsung.android.sume.core.plugin.PluginStore
    public PluginStore add(PluginFixture<?> fixture) {
        MFDescriptor descriptor =
                (MFDescriptor)
                        Stream.of(
                                        (Object[])
                                                new Optional[] {
                                                    Optional.ofNullable(
                                                                    fixture.getDescriptorLoader())
                                                            .map(
                                                                    new Function() { // from class:
                                                                                     // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda1
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            return ((DescriptorLoader)
                                                                                            obj)
                                                                                    .load();
                                                                        }
                                                                    }),
                                                    Optional.ofNullable(
                                                                    fixture
                                                                            .getDescriptorStreamLoader())
                                                            .flatMap(
                                                                    new Function() { // from class:
                                                                                     // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda2
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            return StaplePluginStore
                                                                                    .this
                                                                                    .m9206xccf3d0d6(
                                                                                            (DescriptorStreamLoader)
                                                                                                    obj);
                                                                        }
                                                                    })
                                                })
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return Objects.nonNull((Optional) obj);
                                            }
                                        })
                                .findFirst()
                                .flatMap(Function.identity())
                                .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        this.registry.put(descriptor.getFilterId(), new PluginStore.Entry(descriptor, fixture));
        return this;
    }

    /* renamed from: lambda$add$1$com-samsung-android-sume-core-plugin-StaplePluginStore, reason: not valid java name */
    /* synthetic */ Optional m9206xccf3d0d6(final DescriptorStreamLoader e) {
        return Optional.ofNullable(this.context)
                .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                MFDescriptor parse;
                                parse =
                                        MFDescriptorParser.of(MFDescriptorParser.Type.JSON)
                                                .parse(
                                                        DescriptorStreamLoader.this.load(
                                                                (Context) obj));
                                return parse;
                            }
                        });
    }

    @Override // com.samsung.android.sume.core.plugin.PluginStore
    public PluginFixture<?> remove(final PluginFixture<?> fixture) {
        return (PluginFixture)
                this.registry.entrySet().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return StaplePluginStore.lambda$remove$2(
                                                PluginFixture.this, (Map.Entry) obj);
                                    }
                                })
                        .findFirst()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.plugin.StaplePluginStore$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return StaplePluginStore.this.m9207x1d949b77(
                                                (Map.Entry) obj);
                                    }
                                })
                        .orElse(null);
    }

    static /* synthetic */ boolean lambda$remove$2(PluginFixture fixture, Map.Entry e) {
        return ((PluginStore.Entry) e.getValue()).getPluginFixture() == fixture;
    }

    /* renamed from: lambda$remove$3$com-samsung-android-sume-core-plugin-StaplePluginStore, reason: not valid java name */
    /* synthetic */ PluginFixture m9207x1d949b77(Map.Entry e) {
        return this.registry.remove(e.getKey()).getPluginFixture();
    }

    @Override // com.samsung.android.sume.core.plugin.PluginStore
    public PluginStore.Entry get(MFDescriptor descriptor) {
        return this.registry.get(descriptor.getFilterId());
    }
}
