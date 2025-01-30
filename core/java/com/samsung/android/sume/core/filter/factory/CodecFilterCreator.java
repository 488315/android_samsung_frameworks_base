package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.DecoderFilter;
import com.samsung.android.sume.core.filter.EncoderFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.types.MediaType;

/* loaded from: classes4.dex */
public class CodecFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
        CodecDescriptor codecDescriptor = (CodecDescriptor) descriptor;
        switch (C52781.$SwitchMap$com$samsung$android$sume$core$types$MediaType[codecDescriptor.getMediaType().ordinal()]) {
            case 1:
            case 2:
                return new DecoderFilter((CodecDescriptor) descriptor);
            case 3:
            case 4:
                return new EncoderFilter((CodecDescriptor) descriptor);
            default:
                throw new IllegalArgumentException("");
        }
    }

    /* renamed from: com.samsung.android.sume.core.filter.factory.CodecFilterCreator$1 */
    static /* synthetic */ class C52781 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

        static {
            int[] iArr = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
            try {
                iArr[MediaType.COMPRESSED_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.RAW_AUDIO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.RAW_VIDEO.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
