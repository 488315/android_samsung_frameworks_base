package com.samsung.android.sume.core.buffer;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.MediaMetrics;
import android.telecom.Logging.Session;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.MetaDataUtil;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.solution.filter.UniImgp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class MediaBufferFileWriter {
    private static final String TAG = Def.tagOf((Class<?>) MediaBufferFileWriter.class);
    private BiFunction<MediaBuffer, String, Boolean> compressImageWriter;
    private ByteBuffer exif;
    private Supplier<ExifInterface> exifSupplier;
    private String ext;
    private Bitmap gainmap;
    private ByteBuffer icc;
    private boolean isHDR = false;
    private final String path;
    private final String prefix;
    private Shape shape;
    private UniExifInterface uniExifInterface;

    public MediaBufferFileWriter(String path, String name) {
        this.path = path;
        int extPos = name.lastIndexOf(MediaMetrics.SEPARATOR);
        if (extPos > 0) {
            this.prefix = name.substring(0, extPos);
            this.ext = name.substring(extPos + 1);
        } else {
            this.prefix = name;
            this.ext = null;
        }
    }

    public MediaBufferFileWriter(String path) {
        int namePos = path.lastIndexOf("/");
        this.path = path.substring(0, namePos);
        String name = path.substring(namePos + 1);
        int extPos = name.lastIndexOf(MediaMetrics.SEPARATOR);
        if (extPos > 0) {
            this.prefix = name.substring(0, extPos);
            this.ext = name.substring(extPos + 1);
        } else {
            this.prefix = name;
            this.ext = null;
        }
    }

    public boolean write(MediaBuffer buffer) {
        if (buffer instanceof MediaBufferGroup) {
            return writeGroup(buffer);
        }
        return writeSingle(buffer, "");
    }

    private boolean writeGroup(MediaBuffer buffer) {
        try {
            extractMetaBuffers(buffer);
            MediaBuffer primaryBuffer = ((MediaBufferGroup) buffer).getPrimaryBuffer();
            return writeSingle(primaryBuffer, "");
        } catch (UnsupportedOperationException e) {
            List<MediaBuffer> buffers = buffer.asList();
            for (int i = 0; i < buffers.size(); i++) {
                if (!writeSingle(buffers.get(i), Session.SESSION_SEPARATION_CHAR_CHILD + i)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean writeSingle(MediaBuffer buffer, String index) {
        String fullPath;
        boolean success;
        Supplier<ExifInterface> supplier;
        ExifInterface src;
        Log.m94d(TAG, "writeSingle: " + buffer);
        if (buffer instanceof MutableMediaBuffer) {
            buffer = ((MutableMediaBuffer) buffer).get();
        }
        MutableMediaFormat format = buffer.getFormat().toMutableFormat();
        Shape shape = this.shape;
        if (shape != null) {
            format.set("encode-shape", shape);
        }
        if (this.isHDR) {
            format.set("encode-hdr", true);
        }
        MediaBuffer buffer2 = MediaBuffer.m328of(format, buffer.getData());
        List<MediaBuffer> metadataBuffer = new ArrayList<>();
        UniExifInterface uniExifInterface = this.uniExifInterface;
        if (uniExifInterface != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(1, uniExifInterface));
        } else {
            ByteBuffer byteBuffer = this.exif;
            if (byteBuffer != null) {
                metadataBuffer.add(MediaBuffer.metadataBufferOf(1, byteBuffer));
            }
        }
        ByteBuffer byteBuffer2 = this.icc;
        if (byteBuffer2 != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(2, byteBuffer2));
        }
        Bitmap bitmap = this.gainmap;
        if (bitmap != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(3, bitmap));
        }
        if (!metadataBuffer.isEmpty()) {
            buffer2 = MediaBuffer.groupOf(buffer2, metadataBuffer);
        }
        String str = this.ext;
        if (str != null) {
            fullPath = Def.fmtstr("%s/%s%s.%s", this.path, this.prefix, index, str);
            success = ((Boolean) ((BiFunction) Optional.ofNullable(this.compressImageWriter).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return MediaBufferFileWriter.lambda$writeSingle$1();
                }
            })).apply(buffer2, fullPath)).booleanValue();
        } else {
            this.ext = (String) Optional.ofNullable(buffer2.getFormat().getColorFormat()).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaBufferFileWriter.lambda$writeSingle$2((ColorFormat) obj);
                }
            }).orElse("raw");
            fullPath = Def.fmtstr("%s/%s_%dx%d%s.%s", this.path, this.prefix, Integer.valueOf(buffer2.getStride() / buffer2.getChannels()), Integer.valueOf(buffer2.getScanline()), index, this.ext);
            success = writeRawImageSingle(buffer2, fullPath);
        }
        if (success && (supplier = this.exifSupplier) != null && (src = supplier.get()) != null) {
            try {
                RandomAccessFile os = new RandomAccessFile(fullPath, "rw");
                try {
                    os.getChannel().position(0L);
                    ExifInterface dst = new ExifInterface(os.getFD());
                    for (String tag : MetaDataUtil.getExifTags()) {
                        if (src.hasAttribute(tag)) {
                            dst.setAttribute(tag, src.getAttribute(tag));
                        }
                    }
                    dst.saveAttributes();
                    os.close();
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    static /* synthetic */ BiFunction lambda$writeSingle$1() {
        return new BiFunction() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MediaBufferFileWriter.lambda$writeSingle$0((MediaBuffer) obj, (String) obj2);
            }
        };
    }

    static /* synthetic */ Boolean lambda$writeSingle$0(MediaBuffer buf, String name) {
        Log.m102w(TAG, "not implement internal compress image writer yet, plz should set explicitly");
        return false;
    }

    /* renamed from: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$1 */
    static /* synthetic */ class C52541 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$ColorFormat;

        static {
            int[] iArr = new int[ColorFormat.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$ColorFormat = iArr;
            try {
                iArr[ColorFormat.YUV420.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$ColorFormat[ColorFormat.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static /* synthetic */ String lambda$writeSingle$2(ColorFormat e) {
        switch (C52541.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[e.ordinal()]) {
            case 1:
                return "i420";
            case 2:
                return "gray";
            default:
                return e.name().toLowerCase(Locale.ROOT);
        }
    }

    private boolean writeRawImageSingle(MediaBuffer buffer, String name) {
        MediaBuffer mediaBuffer = buffer;
        final DataType dataType = buffer.getFormat().getDataType();
        if (Arrays.stream(new DataType[]{DataType.f3067U8, DataType.f3066S8}).noneMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaBufferFileWriter.lambda$writeRawImageSingle$3(DataType.this, (DataType) obj);
            }
        })) {
            MutableMediaFormat fmt = MediaFormat.mutableImageOf(new Object[0]);
            fmt.setDataType(DataType.m467of(DataType.f3067U8, buffer.getChannels()));
            mediaBuffer = MediaBuffer.mutableOf(fmt.toMediaFormat());
            UniImgp.ofCvtData().run(buffer, (MutableMediaBuffer) mediaBuffer);
        }
        try {
            FileOutputStream os = new FileOutputStream(name);
            try {
                ByteBuffer byteBuffer = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                os.getChannel().write(byteBuffer);
                Log.m98i(TAG, "success to save " + name);
                os.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.m96e(TAG, "fail to save " + name);
            return false;
        }
    }

    static /* synthetic */ boolean lambda$writeRawImageSingle$3(DataType dataType, DataType e) {
        return e == dataType.depth();
    }

    private void extractMetaBuffers(MediaBuffer buffer) {
        buffer.asList().forEach(new Consumer() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaBufferFileWriter.this.m340xaf869f87((MediaBuffer) obj);
            }
        });
    }

    /* renamed from: lambda$extractMetaBuffers$5$com-samsung-android-sume-core-buffer-MediaBufferFileWriter */
    /* synthetic */ void m340xaf869f87(final MediaBuffer it) {
        if (it.getFormat().getMediaType() == MediaType.META) {
            if (it.getFormat().contains("exif")) {
                if (it.getData() instanceof UniExifInterface) {
                    this.uniExifInterface = (UniExifInterface) it.getTypedData(UniExifInterface.class);
                    return;
                } else if (it.getData() instanceof ExifInterface) {
                    this.exifSupplier = new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return MediaBufferFileWriter.lambda$extractMetaBuffers$4(MediaBuffer.this);
                        }
                    };
                    return;
                } else {
                    if (it.getData() instanceof ByteBuffer) {
                        this.exif = (ByteBuffer) it.getTypedData(ByteBuffer.class);
                        return;
                    }
                    return;
                }
            }
            if (it.getFormat().contains("icc")) {
                this.icc = (ByteBuffer) it.getTypedData(ByteBuffer.class);
            } else if (it.getFormat().contains("gain-map")) {
                this.gainmap = (Bitmap) it.getTypedData(Bitmap.class);
            } else {
                Log.m102w(TAG, "not supported metadata given " + it);
            }
        }
    }

    static /* synthetic */ ExifInterface lambda$extractMetaBuffers$4(MediaBuffer it) {
        return (ExifInterface) it.getTypedData(ExifInterface.class);
    }

    public MediaBufferFileWriter setCompressImageWriter(BiFunction<MediaBuffer, String, Boolean> compressImageWriter) {
        this.compressImageWriter = compressImageWriter;
        return this;
    }

    public MediaBufferFileWriter setExifSupplier(Supplier<ExifInterface> exifSupplier) {
        this.exifSupplier = exifSupplier;
        return this;
    }

    public MediaBufferFileWriter setUniExifInterface(UniExifInterface uniExifInterface) {
        this.uniExifInterface = uniExifInterface;
        return this;
    }

    public MediaBufferFileWriter setExif(ByteBuffer exif) {
        this.exif = exif;
        return this;
    }

    public MediaBufferFileWriter setIcc(ByteBuffer icc) {
        this.icc = icc;
        return this;
    }

    public MediaBufferFileWriter setGainmap(Bitmap gainmap) {
        this.gainmap = gainmap;
        return this;
    }

    public MediaBufferFileWriter setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public MediaBufferFileWriter setHDR(boolean isHDR) {
        this.isHDR = isHDR;
        return this;
    }
}
