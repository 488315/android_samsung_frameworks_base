package com.samsung.android.sume.core.descriptor;

import android.media.MediaFormat;
import android.util.Pair;
import android.view.Surface;
import com.samsung.android.sume.core.filter.DecoderFilter;
import com.samsung.android.sume.core.filter.EncoderFilter;
import com.samsung.android.sume.core.types.MediaType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
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

    public CodecDescriptor(MediaType mediaType, String mimeType) {
        this(mediaType);
        this.mimeType = mimeType;
    }

    public <T> T get(String str) {
        return (T) this.data.get(str);
    }

    public <T, V> void set(String key, T value) {
        this.data.put(key, value);
    }

    /* renamed from: com.samsung.android.sume.core.descriptor.CodecDescriptor$1 */
    static /* synthetic */ class C52641 {
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

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public String getFilterId() {
        switch (C52641.$SwitchMap$com$samsung$android$sume$core$types$MediaType[this.mediaType.ordinal()]) {
            case 1:
            case 2:
                return DecoderFilter.class.getName();
            case 3:
            case 4:
                return EncoderFilter.class.getName();
            default:
                throw new IllegalArgumentException("");
        }
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

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Surface getSurface() {
        return this.surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void setDimension(int width, int height) {
        this.dimension = new Pair<>(Integer.valueOf(width), Integer.valueOf(height));
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isRunInstant() {
        return this.runInstant;
    }

    public void setRunInstant(boolean runInstant) {
        this.runInstant = runInstant;
    }

    public Pair<Integer, Integer> getRectSize() {
        return this.dimension;
    }
}
