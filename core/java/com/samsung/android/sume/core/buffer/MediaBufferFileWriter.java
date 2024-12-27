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

/* loaded from: classes6.dex */
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
        ExifInterface src;
        Log.d(TAG, "writeSingle: " + buffer);
        if (buffer instanceof MutableMediaBuffer) {
            buffer = ((MutableMediaBuffer) buffer).get();
        }
        MutableMediaFormat format = buffer.getFormat().toMutableFormat();
        if (this.shape != null) {
            format.set("encode-shape", this.shape);
        }
        if (this.isHDR) {
            format.set("encode-hdr", true);
        }
        MediaBuffer buffer2 = MediaBuffer.of(format, buffer.getData());
        List<MediaBuffer> metadataBuffer = new ArrayList<>();
        if (this.uniExifInterface != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(1, this.uniExifInterface));
        } else if (this.exif != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(1, this.exif));
        }
        if (this.icc != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(2, this.icc));
        }
        if (this.gainmap != null) {
            metadataBuffer.add(MediaBuffer.metadataBufferOf(3, this.gainmap));
        }
        if (!metadataBuffer.isEmpty()) {
            buffer2 = MediaBuffer.groupOf(buffer2, metadataBuffer);
        }
        if (this.ext != null) {
            fullPath = Def.fmtstr("%s/%s%s.%s", this.path, this.prefix, index, this.ext);
            success =
                    ((Boolean)
                                    ((BiFunction)
                                                    Optional.ofNullable(this.compressImageWriter)
                                                            .orElseGet(
                                                                    new Supplier() { // from class:
                                                                                     // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda3
                                                                        @Override // java.util.function.Supplier
                                                                        public final Object get() {
                                                                            return MediaBufferFileWriter
                                                                                    .lambda$writeSingle$1();
                                                                        }
                                                                    }))
                                            .apply(buffer2, fullPath))
                            .booleanValue();
        } else {
            this.ext =
                    (String)
                            Optional.ofNullable(buffer2.getFormat().getColorFormat())
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return MediaBufferFileWriter
                                                            .lambda$writeSingle$2(
                                                                    (ColorFormat) obj);
                                                }
                                            })
                                    .orElse("raw");
            fullPath =
                    Def.fmtstr(
                            "%s/%s_%dx%d%s.%s",
                            this.path,
                            this.prefix,
                            Integer.valueOf(buffer2.getStride() / buffer2.getChannels()),
                            Integer.valueOf(buffer2.getScanline()),
                            index,
                            this.ext);
            success = writeRawImageSingle(buffer2, fullPath);
        }
        if (success && this.exifSupplier != null && (src = this.exifSupplier.get()) != null) {
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
        return new BiFunction() { // from class:
                                  // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MediaBufferFileWriter.lambda$writeSingle$0((MediaBuffer) obj, (String) obj2);
            }
        };
    }

    static /* synthetic */ Boolean lambda$writeSingle$0(MediaBuffer buf, String name) {
        Log.w(TAG, "not implement internal compress image writer yet, plz should set explicitly");
        return false;
    }

    static /* synthetic */ String lambda$writeSingle$2(ColorFormat e) {
        switch (e) {
            case YUV420:
                return "i420";
            case NONE:
                return "gray";
            default:
                return e.name().toLowerCase(Locale.ROOT);
        }
    }

    private boolean writeRawImageSingle(MediaBuffer buffer, String name) {
        MediaBuffer mediaBuffer = buffer;
        final DataType dataType = buffer.getFormat().getDataType();
        if (Arrays.stream(new DataType[] {DataType.U8, DataType.S8})
                .noneMatch(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return MediaBufferFileWriter.lambda$writeRawImageSingle$3(
                                        DataType.this, (DataType) obj);
                            }
                        })) {
            MutableMediaFormat fmt = MediaFormat.mutableImageOf(new Object[0]);
            fmt.setDataType(DataType.of(DataType.U8, buffer.getChannels()));
            mediaBuffer = MediaBuffer.mutableOf(fmt.toMediaFormat());
            UniImgp.ofCvtData().run(buffer, (MutableMediaBuffer) mediaBuffer);
        }
        try {
            FileOutputStream os = new FileOutputStream(name);
            try {
                ByteBuffer byteBuffer = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                os.getChannel().write(byteBuffer);
                Log.i(TAG, "success to save " + name);
                os.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "fail to save " + name);
            return false;
        }
    }

    static /* synthetic */ boolean lambda$writeRawImageSingle$3(DataType dataType, DataType e) {
        return e == dataType.depth();
    }

    private void extractMetaBuffers(MediaBuffer buffer) {
        buffer.asList()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                MediaBufferFileWriter.this.m9113xaf869f87((MediaBuffer) obj);
                            }
                        });
    }

    /* renamed from: lambda$extractMetaBuffers$5$com-samsung-android-sume-core-buffer-MediaBufferFileWriter, reason: not valid java name */
    /* synthetic */ void m9113xaf869f87(final MediaBuffer it) {
        if (it.getFormat().getMediaType() == MediaType.META) {
            if (it.getFormat().contains("exif")) {
                if (it.getData() instanceof UniExifInterface) {
                    this.uniExifInterface =
                            (UniExifInterface) it.getTypedData(UniExifInterface.class);
                    return;
                } else if (it.getData() instanceof ExifInterface) {
                    this.exifSupplier =
                            new Supplier() { // from class:
                                             // com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda2
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    return MediaBufferFileWriter.lambda$extractMetaBuffers$4(
                                            MediaBuffer.this);
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
                Log.w(TAG, "not supported metadata given " + it);
            }
        }
    }

    static /* synthetic */ ExifInterface lambda$extractMetaBuffers$4(MediaBuffer it) {
        return (ExifInterface) it.getTypedData(ExifInterface.class);
    }

    public MediaBufferFileWriter setCompressImageWriter(
            BiFunction<MediaBuffer, String, Boolean> compressImageWriter) {
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
