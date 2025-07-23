package com.samsung.android.sume.core.descriptor;

import android.media.MediaFormat;
import android.util.Pair;
import android.view.Surface;
import com.samsung.android.sume.core.filter.DecoderFilter;
import com.samsung.android.sume.core.filter.EncoderFilter;
import com.samsung.android.sume.core.types.MediaType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class CodecDescriptor extends MFDescriptorBase {
    private int bitrate;
    private final Map<String, Object> data;
    private Pair<Integer, Integer> dimension;
    private MediaFormat mediaFormat;
    private final MediaType mediaType;
    private String mimeType;
    private boolean runInstant;
    private float scale;
    private Surface surface;

    public CodecDescriptor(MediaType mediaType) {
        this.scale = 0.0f;
        this.bitrate = 0;
        this.runInstant = false;
        this.mediaType = mediaType;
        this.data = new HashMap();
    }

    public CodecDescriptor(MediaType mediaType, String str) {
        this(mediaType);
        this.mimeType = str;
    }

    public <T> T get(String str) {
        return (T) this.data.get(str);
    }

    public <T, V> void set(String str, T t) {
        this.data.put(str, t);
    }

    /* renamed from: com.samsung.android.sume.core.descriptor.CodecDescriptor$1, reason: invalid class name */
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

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public String getFilterId() {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$MediaType[this.mediaType.ordinal()];
        if (i == 1 || i == 2) {
            return DecoderFilter.class.getName();
        }
        if (i == 3 || i == 4) {
            return EncoderFilter.class.getName();
        }
        throw new IllegalArgumentException("");
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public MediaFormat getMediaFormat() {
        return this.mediaFormat;
    }

    public void setMediaFormat(MediaFormat mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public Surface getSurface() {
        return this.surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void setDimension(int i, int i2) {
        this.dimension = new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(int i) {
        this.bitrate = i;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public boolean isRunInstant() {
        return this.runInstant;
    }

    public void setRunInstant(boolean z) {
        this.runInstant = z;
    }

    public Pair<Integer, Integer> getRectSize() {
        return this.dimension;
    }
}
