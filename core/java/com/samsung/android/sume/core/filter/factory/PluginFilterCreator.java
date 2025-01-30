package com.samsung.android.sume.core.filter.factory;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda12;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.PluginDescriptor;
import com.samsung.android.sume.core.descriptor.p044nn.NNDescriptor;
import com.samsung.android.sume.core.descriptor.p044nn.NNFWDescriptor$$ExternalSyntheticLambda1;
import com.samsung.android.sume.core.filter.ByPassFilter;
import com.samsung.android.sume.core.filter.ContentFilter;
import com.samsung.android.sume.core.filter.ContentFilterRegister;
import com.samsung.android.sume.core.filter.ImgpFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.NNFilter;
import com.samsung.android.sume.core.functional.ModelSelector;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.plugin.NNPlugin;
import com.samsung.android.sume.core.plugin.PluginFixture;
import com.samsung.android.sume.core.plugin.PluginStore;
import com.samsung.android.sume.core.types.p045nn.NNFileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class PluginFilterCreator implements MediaFilterCreator {
    private static final String TAG = Def.tagOf((Class<?>) PluginFilterCreator.class);
    private PluginStore pluginStore;

    PluginFilterCreator() {
    }

    void setPluginStore(PluginStore pluginStore) {
        this.pluginStore = pluginStore;
    }

    public PluginStore getPluginStore() {
        return this.pluginStore;
    }

    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
        Def.require(descriptor instanceof PluginDescriptor);
        if (descriptor instanceof NNDescriptor) {
            return createNNFilter((NNDescriptor) descriptor, successor);
        }
        if (descriptor instanceof ImgpDescriptor) {
            return createImgpFilter((ImgpDescriptor) descriptor);
        }
        throw new UnsupportedOperationException("not yet supported except NNDescriptor");
    }

    private MediaFilter createImgpFilter(ImgpDescriptor descriptor) {
        ImgpPlugin plugin;
        if (descriptor.getPluginId() == ImgpPlugin.Type.ANY) {
            final List<String> typeNames = (List) Arrays.stream(ImgpPlugin.Type.values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PluginFilterCreator.lambda$createImgpFilter$0((ImgpPlugin.Type) obj);
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ImgpPlugin.Type) obj).name();
                }
            }).collect(Collectors.toList());
            List<PluginFixture<?>> plugins = (List) this.pluginStore.keyStream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean anyMatch;
                    anyMatch = typeNames.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda14
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            boolean equals;
                            equals = ((String) obj2).equals(r1);
                            return equals;
                        }
                    });
                    return anyMatch;
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PluginFilterCreator.this.m393x46ec1c58((String) obj);
                }
            }).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Optional) obj).isPresent();
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (PluginFixture) ((Optional) obj).get();
                }
            }).collect(Collectors.toList());
            Def.require(!plugins.isEmpty());
            if (descriptor.isLatestPluginsOrder()) {
                Collections.reverse(plugins);
            }
            if (plugins.size() == 1) {
                plugin = (ImgpPlugin) plugins.get(0);
            } else {
                plugin = (ImgpPlugin) plugins.stream().reduce(new BinaryOperator() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        PluginFixture join;
                        join = ImgpPlugin.join((ImgpPlugin) ((PluginFixture) obj), (ImgpPlugin) ((PluginFixture) obj2));
                        return join;
                    }
                }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
            }
        } else {
            PluginStore.Entry found = (PluginStore.Entry) Optional.ofNullable(this.pluginStore.get(descriptor)).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
            plugin = (ImgpPlugin) found.getPluginFixture();
            ((PluginDescriptor) found.getDescriptor()).copyTo(descriptor);
        }
        final ImgpFilter imgpFilter = new ImgpFilter(descriptor, plugin);
        return (MediaFilter) Optional.ofNullable(plugin.getContentFilterRegister()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PluginFilterCreator.lambda$createImgpFilter$5(MediaFilter.this, (ContentFilterRegister) obj);
            }
        }).orElse(imgpFilter);
    }

    static /* synthetic */ boolean lambda$createImgpFilter$0(ImgpPlugin.Type e) {
        return e != ImgpPlugin.Type.CUSTOM;
    }

    /* renamed from: lambda$createImgpFilter$3$com-samsung-android-sume-core-filter-factory-PluginFilterCreator */
    /* synthetic */ Optional m393x46ec1c58(String e) {
        return Optional.ofNullable(this.pluginStore.get(new ImgpDescriptor(ImgpPlugin.Type.valueOf(e)))).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PluginStore.Entry) obj).getPluginFixture();
            }
        });
    }

    static /* synthetic */ MediaFilter lambda$createImgpFilter$5(MediaFilter filter, ContentFilterRegister it) {
        return new ContentFilter(it, filter);
    }

    private MediaFilter createNNFilter(NNDescriptor descriptor, MediaFilter successor) {
        List<NNFileDescriptor> nnFileDescriptor;
        final MediaFilter filter;
        PluginStore.Entry found = (PluginStore.Entry) Optional.ofNullable(this.pluginStore.get(descriptor)).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
        NNPlugin plugin = (NNPlugin) found.getPluginFixture();
        ((PluginDescriptor) found.getDescriptor()).copyTo(descriptor);
        final Context context = this.pluginStore.getContext();
        if (context == null) {
            throw new IllegalStateException("NNPlugin filter require context from PluginStore, but nothing is given");
        }
        final Pair<String, Pattern> paths = plugin.getModelPathLoader().load(descriptor.getModelId());
        try {
            nnFileDescriptor = (List) Arrays.stream(context.getAssets().list(paths.first)).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean find;
                    find = ((Pattern) Pair.this.second).matcher((String) obj).find();
                    return find;
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PluginFilterCreator.lambda$createNNFilter$7(Context.this, paths, (String) obj);
                }
            }).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((NNFileDescriptor) obj);
                }
            }).collect(Collectors.toList());
            String models = Arrays.toString(nnFileDescriptor.stream().map(new NNFWDescriptor$$ExternalSyntheticLambda1()).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda11
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return PluginFilterCreator.lambda$createNNFilter$8(i);
                }
            }));
            Log.m94d(TAG, "success to load model file: " + paths.first + "/" + models);
        } catch (IOException ex) {
            ex.printStackTrace();
            nnFileDescriptor = new ArrayList();
        }
        if (nnFileDescriptor.isEmpty()) {
            if (descriptor.isIgnorableFilter()) {
                filter = new ByPassFilter(descriptor);
            } else {
                throw new IllegalArgumentException(Def.fmtstr("can't find model file: " + paths.first + ", regex=" + paths.second, new Object[0]));
            }
        } else {
            if (nnFileDescriptor.size() > 1) {
                ModelSelector modelSelector = plugin.getModelSelector();
                Def.require(modelSelector != null, "multiple model found, but model selector is not given", new Object[0]);
                descriptor.setModelSelector(modelSelector);
            }
            descriptor.setNNFileDescriptors(nnFileDescriptor);
            filter = new NNFilter(descriptor, plugin, (MediaFilter) Objects.requireNonNull(successor));
        }
        return (MediaFilter) Optional.ofNullable(plugin.getContentFilterRegister()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PluginFilterCreator.lambda$createNNFilter$9(MediaFilter.this, (ContentFilterRegister) obj);
            }
        }).orElse(filter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ NNFileDescriptor lambda$createNNFilter$7(Context context, Pair paths, String it) {
        AssetFileDescriptor afd = null;
        try {
            try {
                afd = context.getAssets().openFd(((String) paths.first) + "/" + it);
                NNFileDescriptor nnfd = new NNFileDescriptor(afd.getParcelFileDescriptor().dup());
                nnfd.setName(it);
                nnfd.setOffset(afd.getStartOffset());
                nnfd.setLength(afd.getDeclaredLength());
                return nnfd;
            } catch (IOException e) {
                e.printStackTrace();
                if (afd == null) {
                    return null;
                }
                try {
                    afd.close();
                    return null;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    static /* synthetic */ String[] lambda$createNNFilter$8(int x$0) {
        return new String[x$0];
    }

    static /* synthetic */ MediaFilter lambda$createNNFilter$9(MediaFilter filter, ContentFilterRegister it) {
        return new ContentFilter(it, filter);
    }
}
