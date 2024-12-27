package com.samsung.android.sume.core.filter.factory;

import android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda8;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptorHolder;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWProfile;
import com.samsung.android.sume.core.filter.ImgpFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.PluginDecorateFilter;
import com.samsung.android.sume.core.filter.SyncFilter;
import com.samsung.android.sume.core.filter.collection.ParallelFilter;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.types.ImgpType;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class NNFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
        MediaFilter filter;
        final NNDescriptor desc = (NNDescriptor) descriptor;
        List<MFDescriptor> nnfwDescriptors = (List) desc.getNNFWProfiles().stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.NNFilterCreator$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((NNFWProfile) obj).flatten();
            }
        }).flatMap(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.NNFilterCreator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NNFilterCreator.lambda$newFilter$0(NNDescriptor.this, (NNFWProfile) obj);
            }
        }).collect(Collectors.toList());
        Def.require(!nnfwDescriptors.isEmpty());
        if (nnfwDescriptors.size() == 1) {
            filter = new SyncFilter(factory.newFilter(nnfwDescriptors.get(0)));
        } else {
            filter = factory.newFilter(new ParallelDescriptor(ParallelFilter.Type.DNC, nnfwDescriptors));
        }
        ImgpDescriptor preImgpDescriptor = new ImgpDescriptor(ImgpType.ANY);
        ImgpDescriptor postImgpDescriptor = new ImgpDescriptor(ImgpType.ANY);
        preImgpDescriptor.setLatestPluginsOrder(true);
        postImgpDescriptor.setLatestPluginsOrder(true);
        MediaFilter filter2 = factory.newFilter(PluginDecorateFilter.class, desc, ImgpFilter.of(filter, factory.newFilter(preImgpDescriptor), factory.newFilter(postImgpDescriptor)));
        ((MutableMediaFormat) Objects.requireNonNull(desc.getInputFormat())).set(desc.getOption().asInputOption());
        ((MutableMediaFormat) Objects.requireNonNull(desc.getOutputFormat())).set(desc.getOption().asOutputOption());
        preImgpDescriptor.setOption(desc.getOption());
        postImgpDescriptor.setOption(desc.getOption());
        preImgpDescriptor.setFormat(desc.getInputFormat());
        if (desc.getTargetFormat() != null) {
            postImgpDescriptor.setFormat(desc.getTargetFormat().deepCopy2());
            postImgpDescriptor.setKeepFilterDatatype(true);
        } else {
            postImgpDescriptor.setFormat(desc.getOutputFormat());
        }
        return filter2;
    }

    static /* synthetic */ MFDescriptorHolder lambda$newFilter$0(NNDescriptor desc, NNFWProfile e) {
        return new MFDescriptorHolder(new NNFWDescriptor(e.getFw(), e.getHw()), desc);
    }
}
