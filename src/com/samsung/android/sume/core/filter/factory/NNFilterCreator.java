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
    public MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        MediaFilter mediaFilterNewFilter;
        final NNDescriptor nNDescriptor = (NNDescriptor) mFDescriptor;
        List list = (List) nNDescriptor.getNNFWProfiles().stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.NNFilterCreator$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((NNFWProfile) obj).flatten();
            }
        }).flatMap(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.NNFilterCreator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NNFilterCreator.lambda$newFilter$0(nNDescriptor, (NNFWProfile) obj);
            }
        }).collect(Collectors.toList());
        Def.require(!list.isEmpty());
        if (list.size() == 1) {
            mediaFilterNewFilter = new SyncFilter(mediaFilterFactory.newFilter((MFDescriptor) list.get(0)));
        } else {
            mediaFilterNewFilter = mediaFilterFactory.newFilter(new ParallelDescriptor(ParallelFilter.Type.DNC, list));
        }
        ImgpDescriptor imgpDescriptor = new ImgpDescriptor(ImgpType.ANY);
        ImgpDescriptor imgpDescriptor2 = new ImgpDescriptor(ImgpType.ANY);
        imgpDescriptor.setLatestPluginsOrder(true);
        imgpDescriptor2.setLatestPluginsOrder(true);
        MediaFilter mediaFilterNewFilter2 = mediaFilterFactory.newFilter(PluginDecorateFilter.class, nNDescriptor, ImgpFilter.of(mediaFilterNewFilter, mediaFilterFactory.newFilter(imgpDescriptor), mediaFilterFactory.newFilter(imgpDescriptor2)));
        ((MutableMediaFormat) Objects.requireNonNull(nNDescriptor.getInputFormat())).set(nNDescriptor.getOption().asInputOption());
        ((MutableMediaFormat) Objects.requireNonNull(nNDescriptor.getOutputFormat())).set(nNDescriptor.getOption().asOutputOption());
        imgpDescriptor.setOption(nNDescriptor.getOption());
        imgpDescriptor2.setOption(nNDescriptor.getOption());
        imgpDescriptor.setFormat(nNDescriptor.getInputFormat());
        if (nNDescriptor.getTargetFormat() != null) {
            imgpDescriptor2.setFormat(nNDescriptor.getTargetFormat().deepCopy2());
            imgpDescriptor2.setKeepFilterDatatype(true);
            return mediaFilterNewFilter2;
        }
        imgpDescriptor2.setFormat(nNDescriptor.getOutputFormat());
        return mediaFilterNewFilter2;
    }

    static /* synthetic */ MFDescriptorHolder lambda$newFilter$0(NNDescriptor nNDescriptor, NNFWProfile nNFWProfile) {
        return new MFDescriptorHolder(new NNFWDescriptor(nNFWProfile.getFw(), nNFWProfile.getHw()), nNDescriptor);
    }
}
