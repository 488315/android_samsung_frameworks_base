package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.DecoderFilter;
import com.samsung.android.sume.core.filter.EncoderFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.types.MediaType;

/* loaded from: classes6.dex */
public class CodecFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        CodecDescriptor codecDescriptor = (CodecDescriptor) mFDescriptor;
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$MediaType[codecDescriptor.getMediaType().ordinal()];
        if (i == 1 || i == 2) {
            return new DecoderFilter(codecDescriptor);
        }
        if (i == 3 || i == 4) {
            return new EncoderFilter(codecDescriptor);
        }
        throw new IllegalArgumentException("");
    }

    /* renamed from: com.samsung.android.sume.core.filter.factory.CodecFilterCreator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

        static {
            int[] iArr = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
            try {
                iArr[MediaType.COMPRESSED_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.RAW_AUDIO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.RAW_VIDEO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
