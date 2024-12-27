package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.DecoderFilter;
import com.samsung.android.sume.core.filter.EncoderFilter;
import com.samsung.android.sume.core.filter.MediaFilter;

/* loaded from: classes6.dex */
public class CodecFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
        switch (codecDescriptor.getMediaType()) {
            case COMPRESSED_AUDIO:
            case COMPRESSED_VIDEO:
                return new DecoderFilter((CodecDescriptor) descriptor);
            case RAW_AUDIO:
            case RAW_VIDEO:
                return new EncoderFilter((CodecDescriptor) descriptor);
            default:
                throw new IllegalArgumentException("");
        }
    }
}
