package com.samsung.android.sume.core.filter.factory;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.PluginDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor$$ExternalSyntheticLambda1;
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
import com.samsung.android.sume.core.types.nn.NNFileDescriptor;
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

/* loaded from: classes6.dex */
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
    public MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        Def.require(mFDescriptor instanceof PluginDescriptor);
        if (mFDescriptor instanceof NNDescriptor) {
            return createNNFilter((NNDescriptor) mFDescriptor, mediaFilter);
        }
        if (mFDescriptor instanceof ImgpDescriptor) {
            return createImgpFilter((ImgpDescriptor) mFDescriptor);
        }
        throw new UnsupportedOperationException("not yet supported except NNDescriptor");
    }

    private MediaFilter createImgpFilter(ImgpDescriptor imgpDescriptor) {
        ImgpPlugin imgpPlugin;
        if (imgpDescriptor.getPluginId() == ImgpPlugin.Type.ANY) {
            final List list = (List) Arrays.stream(ImgpPlugin.Type.values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda0
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
            List list2 = (List) this.pluginStore.keyStream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda8
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return ((String) obj2).equals(str);
                        }
                    });
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m9578x46ec1c58((String) obj);
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
            Def.require(!list2.isEmpty());
            if (imgpDescriptor.isLatestPluginsOrder()) {
                Collections.reverse(list2);
            }
            if (list2.size() == 1) {
                imgpPlugin = (ImgpPlugin) list2.get(0);
            } else {
                imgpPlugin = (ImgpPlugin) list2.stream().reduce(new BinaryOperator() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return ImgpPlugin.join((ImgpPlugin) ((PluginFixture) obj), (ImgpPlugin) ((PluginFixture) obj2));
                    }
                }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
            }
        } else {
            PluginStore.Entry entry = (PluginStore.Entry) Optional.ofNullable(this.pluginStore.get(imgpDescriptor)).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
            ImgpPlugin imgpPlugin2 = (ImgpPlugin) entry.getPluginFixture();
            ((PluginDescriptor) entry.getDescriptor()).copyTo(imgpDescriptor);
            imgpPlugin = imgpPlugin2;
        }
        final ImgpFilter imgpFilter = new ImgpFilter(imgpDescriptor, imgpPlugin);
        return (MediaFilter) Optional.ofNullable(imgpPlugin.getContentFilterRegister()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PluginFilterCreator.lambda$createImgpFilter$5(imgpFilter, (ContentFilterRegister) obj);
            }
        }).orElse(imgpFilter);
    }

    static /* synthetic */ boolean lambda$createImgpFilter$0(ImgpPlugin.Type type) {
        return type != ImgpPlugin.Type.CUSTOM;
    }

    /* renamed from: lambda$createImgpFilter$3$com-samsung-android-sume-core-filter-factory-PluginFilterCreator, reason: not valid java name */
    /* synthetic */ Optional m9578x46ec1c58(String str) {
        return Optional.ofNullable(this.pluginStore.get(new ImgpDescriptor(ImgpPlugin.Type.valueOf(str)))).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PluginStore.Entry) obj).getPluginFixture();
            }
        });
    }

    static /* synthetic */ MediaFilter lambda$createImgpFilter$5(MediaFilter mediaFilter, ContentFilterRegister contentFilterRegister) {
        return new ContentFilter(contentFilterRegister, mediaFilter);
    }

    private MediaFilter createNNFilter(NNDescriptor nNDescriptor, MediaFilter mediaFilter) {
        List<NNFileDescriptor> arrayList;
        final MediaFilter nNFilter;
        PluginStore.Entry entry = (PluginStore.Entry) Optional.ofNullable(this.pluginStore.get(nNDescriptor)).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        NNPlugin nNPlugin = (NNPlugin) entry.getPluginFixture();
        ((PluginDescriptor) entry.getDescriptor()).copyTo(nNDescriptor);
        final Context context = this.pluginStore.getContext();
        if (context == null) {
            throw new IllegalStateException("NNPlugin filter require context from PluginStore, but nothing is given");
        }
        final Pair<String, Pattern> pairLoad = nNPlugin.getModelPathLoader().load(nNDescriptor.getModelId());
        try {
            arrayList = (List) Arrays.stream(context.getAssets().list(pairLoad.first)).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Pattern) pairLoad.second).matcher((String) obj).find();
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PluginFilterCreator.lambda$createNNFilter$7(context, pairLoad, (String) obj);
                }
            }).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((NNFileDescriptor) obj);
                }
            }).collect(Collectors.toList());
            String string = Arrays.toString(arrayList.stream().map(new NNFWDescriptor$$ExternalSyntheticLambda1()).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda12
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return PluginFilterCreator.lambda$createNNFilter$8(i);
                }
            }));
            Log.d(TAG, "success to load model file: " + pairLoad.first + "/" + string);
        } catch (IOException e) {
            e.printStackTrace();
            arrayList = new ArrayList<>();
        }
        if (arrayList.isEmpty()) {
            if (nNDescriptor.isIgnorableFilter()) {
                nNFilter = new ByPassFilter(nNDescriptor);
            } else {
                throw new IllegalArgumentException(Def.fmtstr("can't find model file: " + pairLoad.first + ", regex=" + pairLoad.second, new Object[0]));
            }
        } else {
            if (arrayList.size() > 1) {
                ModelSelector modelSelector = nNPlugin.getModelSelector();
                Def.require(modelSelector != null, "multiple model found, but model selector is not given", new Object[0]);
                nNDescriptor.setModelSelector(modelSelector);
            }
            nNDescriptor.setNNFileDescriptors(arrayList);
            nNFilter = new NNFilter(nNDescriptor, nNPlugin, (MediaFilter) Objects.requireNonNull(mediaFilter));
        }
        return (MediaFilter) Optional.ofNullable(nNPlugin.getContentFilterRegister()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PluginFilterCreator.lambda$createNNFilter$9(nNFilter, (ContentFilterRegister) obj);
            }
        }).orElse(nNFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static /* synthetic */ NNFileDescriptor lambda$createNNFilter$7(Context context, Pair pair, String str) throws Throwable {
        AssetFileDescriptor assetFileDescriptorOpenFd;
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            assetFileDescriptorOpenFd = context.getAssets().openFd(((String) pair.first) + "/" + str);
            try {
                try {
                    NNFileDescriptor nNFileDescriptor = new NNFileDescriptor(assetFileDescriptorOpenFd.getParcelFileDescriptor().dup());
                    nNFileDescriptor.setName(str);
                    nNFileDescriptor.setOffset(assetFileDescriptorOpenFd.getStartOffset());
                    nNFileDescriptor.setLength(assetFileDescriptorOpenFd.getDeclaredLength());
                    if (assetFileDescriptorOpenFd != null) {
                        try {
                            assetFileDescriptorOpenFd.close();
                            return nNFileDescriptor;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return nNFileDescriptor;
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (assetFileDescriptorOpenFd != null) {
                        try {
                            assetFileDescriptorOpenFd.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                assetFileDescriptor = assetFileDescriptorOpenFd;
                if (assetFileDescriptor != null) {
                    try {
                        assetFileDescriptor.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            assetFileDescriptorOpenFd = null;
        } catch (Throwable th2) {
            th = th2;
            if (assetFileDescriptor != null) {
            }
            throw th;
        }
    }

    static /* synthetic */ String[] lambda$createNNFilter$8(int i) {
        return new String[i];
    }

    static /* synthetic */ MediaFilter lambda$createNNFilter$9(MediaFilter mediaFilter, ContentFilterRegister contentFilterRegister) {
        return new ContentFilter(contentFilterRegister, mediaFilter);
    }
}
