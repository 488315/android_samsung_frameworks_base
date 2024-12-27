package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.filter.MediaMuxerFilter;
import com.samsung.android.sume.core.types.MediaType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MediaMuxerDescriptor extends MFDescriptorBase {
    private final Set<Integer> mediaTypesToNotifyEvent = new HashSet();
    private final int outputFormat;

    public MediaMuxerDescriptor(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setMediaTypeToNotifyEvent(MediaType... mediaTypes) {
        this.mediaTypesToNotifyEvent.addAll(
                (Collection)
                        Arrays.stream(mediaTypes)
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.descriptor.MediaMuxerDescriptor$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                Integer valueOf;
                                                valueOf =
                                                        Integer.valueOf(
                                                                ((MediaType) obj)
                                                                        .rank()
                                                                        .getValue());
                                                return valueOf;
                                            }
                                        })
                                .collect(Collectors.toList()));
    }

    public boolean isMediaTypeToNotifyEvent(MediaType mediaType) {
        return this.mediaTypesToNotifyEvent.contains(Integer.valueOf(mediaType.rank().getValue()));
    }

    public int getOutputFormat() {
        return this.outputFormat;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
              // com.samsung.android.sume.core.descriptor.MFDescriptor
    public String getFilterId() {
        return MediaMuxerFilter.class.getName();
    }
}
