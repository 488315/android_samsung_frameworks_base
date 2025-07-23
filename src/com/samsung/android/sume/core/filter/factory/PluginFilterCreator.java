package com.samsung.android.sume.core.filter.factory;

import android.content.Context;
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
                    boolean anyMatch;
                    anyMatch = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda8
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
                    return PluginFilterCreator.this.m9565x46ec1c58((String) obj);
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
                        PluginFixture join;
                        join = ImgpPlugin.join((ImgpPlugin) ((PluginFixture) obj), (ImgpPlugin) ((PluginFixture) obj2));
                        return join;
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
                return PluginFilterCreator.lambda$createImgpFilter$5(MediaFilter.this, (ContentFilterRegister) obj);
            }
        }).orElse(imgpFilter);
    }

    static /* synthetic */ boolean lambda$createImgpFilter$0(ImgpPlugin.Type type) {
        return type != ImgpPlugin.Type.CUSTOM;
    }

    /* renamed from: lambda$createImgpFilter$3$com-samsung-android-sume-core-filter-factory-PluginFilterCreator, reason: not valid java name */
    /* synthetic */ Optional m9565x46ec1c58(String str) {
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
        final Pair<String, Pattern> load = nNPlugin.getModelPathLoader().load(nNDescriptor.getModelId());
        try {
            arrayList = (List) Arrays.stream(context.getAssets().list(load.first)).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean find;
                    find = ((Pattern) Pair.this.second).matcher((String) obj).find();
                    return find;
                }
            }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PluginFilterCreator.lambda$createNNFilter$7(Context.this, load, (String) obj);
                }
            }).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((NNFileDescriptor) obj);
                }
            }).collect(Collectors.toList());
            String arrays = Arrays.toString(arrayList.stream().map(new NNFWDescriptor$$ExternalSyntheticLambda1()).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.filter.factory.PluginFilterCreator$$ExternalSyntheticLambda12
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return PluginFilterCreator.lambda$createNNFilter$8(i);
                }
            }));
            Log.d(TAG, "success to load model file: " + load.first + "/" + arrays);
        } catch (IOException e) {
            e.printStackTrace();
            arrayList = new ArrayList<>();
        }
        if (arrayList.isEmpty()) {
            if (nNDescriptor.isIgnorableFilter()) {
                nNFilter = new ByPassFilter(nNDescriptor);
            } else {
                throw new IllegalArgumentException(Def.fmtstr("can't find model file: " + load.first + ", regex=" + load.second, new Object[0]));
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
                return PluginFilterCreator.lambda$createNNFilter$9(MediaFilter.this, (ContentFilterRegister) obj);
            }
        }).orElse(nNFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ com.samsung.android.sume.core.types.nn.NNFileDescriptor lambda$createNNFilter$7(android.content.Context r3, android.util.Pair r4, java.lang.String r5) {
        /*
            r0 = 0
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            r1.<init>()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            F r4 = r4.first     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            r1.append(r4)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            java.lang.String r4 = "/"
            r1.append(r4)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            r1.append(r5)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            java.lang.String r4 = r1.toString()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            android.content.res.AssetFileDescriptor r3 = r3.openFd(r4)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L4e
            com.samsung.android.sume.core.types.nn.NNFileDescriptor r4 = new com.samsung.android.sume.core.types.nn.NNFileDescriptor     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            android.os.ParcelFileDescriptor r1 = r3.getParcelFileDescriptor()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            android.os.ParcelFileDescriptor r1 = r1.dup()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            r4.<init>(r1)     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            r4.setName(r5)     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            long r1 = r3.getStartOffset()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            r4.setOffset(r1)     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            long r1 = r3.getDeclaredLength()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            r4.setLength(r1)     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L5e
            if (r3 == 0) goto L49
            r3.close()     // Catch: java.io.IOException -> L45
            return r4
        L45:
            r3 = move-exception
            r3.printStackTrace()
        L49:
            return r4
        L4a:
            r4 = move-exception
            goto L50
        L4c:
            r4 = move-exception
            goto L60
        L4e:
            r4 = move-exception
            r3 = r0
        L50:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L5e
            if (r3 == 0) goto L5d
            r3.close()     // Catch: java.io.IOException -> L59
            goto L5d
        L59:
            r3 = move-exception
            r3.printStackTrace()
        L5d:
            return r0
        L5e:
            r4 = move-exception
            r0 = r3
        L60:
            if (r0 == 0) goto L6a
            r0.close()     // Catch: java.io.IOException -> L66
            goto L6a
        L66:
            r3 = move-exception
            r3.printStackTrace()
        L6a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.filter.factory.PluginFilterCreator.lambda$createNNFilter$7(android.content.Context, android.util.Pair, java.lang.String):com.samsung.android.sume.core.types.nn.NNFileDescriptor");
    }

    static /* synthetic */ String[] lambda$createNNFilter$8(int i) {
        return new String[i];
    }

    static /* synthetic */ MediaFilter lambda$createNNFilter$9(MediaFilter mediaFilter, ContentFilterRegister contentFilterRegister) {
        return new ContentFilter(contentFilterRegister, mediaFilter);
    }
}
