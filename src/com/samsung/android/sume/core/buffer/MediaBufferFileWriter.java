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

    public MediaBufferFileWriter(String str, String str2) {
        this.path = str;
        int lastIndexOf = str2.lastIndexOf(MediaMetrics.SEPARATOR);
        if (lastIndexOf > 0) {
            this.prefix = str2.substring(0, lastIndexOf);
            this.ext = str2.substring(lastIndexOf + 1);
        } else {
            this.prefix = str2;
            this.ext = null;
        }
    }

    public MediaBufferFileWriter(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        this.path = str.substring(0, lastIndexOf);
        String substring = str.substring(lastIndexOf + 1);
        int lastIndexOf2 = substring.lastIndexOf(MediaMetrics.SEPARATOR);
        if (lastIndexOf2 > 0) {
            this.prefix = substring.substring(0, lastIndexOf2);
            this.ext = substring.substring(lastIndexOf2 + 1);
        } else {
            this.prefix = substring;
            this.ext = null;
        }
    }

    public boolean write(MediaBuffer mediaBuffer) {
        if (mediaBuffer instanceof MediaBufferGroup) {
            return writeGroup(mediaBuffer);
        }
        return writeSingle(mediaBuffer, "");
    }

    private boolean writeGroup(MediaBuffer mediaBuffer) {
        try {
            extractMetaBuffers(mediaBuffer);
            return writeSingle(((MediaBufferGroup) mediaBuffer).getPrimaryBuffer(), "");
        } catch (UnsupportedOperationException unused) {
            List<MediaBuffer> asList = mediaBuffer.asList();
            for (int i = 0; i < asList.size(); i++) {
                if (!this.writeSingle(asList.get(i), Session.SESSION_SEPARATION_CHAR_CHILD + i)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean writeSingle(MediaBuffer mediaBuffer, String str) {
        String fmtstr;
        boolean writeRawImageSingle;
        Supplier<ExifInterface> supplier;
        ExifInterface exifInterface;
        Log.d(TAG, "writeSingle: " + mediaBuffer);
        if (mediaBuffer instanceof MutableMediaBuffer) {
            mediaBuffer = ((MutableMediaBuffer) mediaBuffer).get();
        }
        MutableMediaFormat mutableFormat = mediaBuffer.getFormat().toMutableFormat();
        Shape shape = this.shape;
        if (shape != null) {
            mutableFormat.set("encode-shape", shape);
        }
        if (this.isHDR) {
            mutableFormat.set("encode-hdr", true);
        }
        MediaBuffer of = MediaBuffer.of(mutableFormat, mediaBuffer.getData());
        ArrayList arrayList = new ArrayList();
        UniExifInterface uniExifInterface = this.uniExifInterface;
        if (uniExifInterface != null) {
            arrayList.add(MediaBuffer.metadataBufferOf(1, uniExifInterface));
        } else {
            ByteBuffer byteBuffer = this.exif;
            if (byteBuffer != null) {
                arrayList.add(MediaBuffer.metadataBufferOf(1, byteBuffer));
            }
        }
        ByteBuffer byteBuffer2 = this.icc;
        if (byteBuffer2 != null) {
            arrayList.add(MediaBuffer.metadataBufferOf(2, byteBuffer2));
        }
        Bitmap bitmap = this.gainmap;
        if (bitmap != null) {
            arrayList.add(MediaBuffer.metadataBufferOf(3, bitmap));
        }
        if (!arrayList.isEmpty()) {
            of = MediaBuffer.groupOf(of, arrayList);
        }
        String str2 = this.ext;
        if (str2 != null) {
            fmtstr = Def.fmtstr("%s/%s%s.%s", this.path, this.prefix, str, str2);
            writeRawImageSingle = ((Boolean) ((BiFunction) Optional.ofNullable(this.compressImageWriter).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return MediaBufferFileWriter.lambda$writeSingle$1();
                }
            })).apply(of, fmtstr)).booleanValue();
        } else {
            this.ext = (String) Optional.ofNullable(of.getFormat().getColorFormat()).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaBufferFileWriter.lambda$writeSingle$2((ColorFormat) obj);
                }
            }).orElse("raw");
            fmtstr = Def.fmtstr("%s/%s_%dx%d%s.%s", this.path, this.prefix, Integer.valueOf(of.getStride() / of.getChannels()), Integer.valueOf(of.getScanline()), str, this.ext);
            writeRawImageSingle = writeRawImageSingle(of, fmtstr);
        }
        if (writeRawImageSingle && (supplier = this.exifSupplier) != null && (exifInterface = supplier.get()) != null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(fmtstr, "rw");
                try {
                    randomAccessFile.getChannel().position(0L);
                    ExifInterface exifInterface2 = new ExifInterface(randomAccessFile.getFD());
                    for (String str3 : MetaDataUtil.getExifTags()) {
                        if (exifInterface.hasAttribute(str3)) {
                            exifInterface2.setAttribute(str3, exifInterface.getAttribute(str3));
                        }
                    }
                    exifInterface2.saveAttributes();
                    randomAccessFile.close();
                    return writeRawImageSingle;
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writeRawImageSingle;
    }

    static /* synthetic */ BiFunction lambda$writeSingle$1() {
        return new BiFunction() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MediaBufferFileWriter.lambda$writeSingle$0((MediaBuffer) obj, (String) obj2);
            }
        };
    }

    static /* synthetic */ Boolean lambda$writeSingle$0(MediaBuffer mediaBuffer, String str) {
        Log.w(TAG, "not implement internal compress image writer yet, plz should set explicitly");
        return false;
    }

    /* renamed from: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$ColorFormat;

        static {
            int[] iArr = new int[ColorFormat.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$ColorFormat = iArr;
            try {
                iArr[ColorFormat.YUV420.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$ColorFormat[ColorFormat.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static /* synthetic */ String lambda$writeSingle$2(ColorFormat colorFormat) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[colorFormat.ordinal()];
        if (i == 1) {
            return "i420";
        }
        if (i == 2) {
            return "gray";
        }
        return colorFormat.name().toLowerCase(Locale.ROOT);
    }

    private boolean writeRawImageSingle(MediaBuffer mediaBuffer, String str) {
        final DataType dataType = mediaBuffer.getFormat().getDataType();
        if (Arrays.stream(new DataType[]{DataType.U8, DataType.S8}).noneMatch(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaBufferFileWriter.lambda$writeRawImageSingle$3(DataType.this, (DataType) obj);
            }
        })) {
            MutableMediaFormat mutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
            mutableImageOf.setDataType(DataType.of(DataType.U8, mediaBuffer.getChannels()));
            MutableMediaBuffer mutableOf = MediaBuffer.mutableOf(mutableImageOf.toMediaFormat());
            UniImgp.ofCvtData().run(mediaBuffer, mutableOf);
            mediaBuffer = mutableOf;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                fileOutputStream.getChannel().write((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
                Log.i(TAG, "success to save " + str);
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "fail to save " + str);
            return false;
        }
    }

    static /* synthetic */ boolean lambda$writeRawImageSingle$3(DataType dataType, DataType dataType2) {
        return dataType2 == dataType.depth();
    }

    private void extractMetaBuffers(MediaBuffer mediaBuffer) {
        mediaBuffer.asList().forEach(new Consumer() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaBufferFileWriter.this.m9514xaf869f87((MediaBuffer) obj);
            }
        });
    }

    /* renamed from: lambda$extractMetaBuffers$5$com-samsung-android-sume-core-buffer-MediaBufferFileWriter, reason: not valid java name */
    /* synthetic */ void m9514xaf869f87(final MediaBuffer mediaBuffer) {
        if (mediaBuffer.getFormat().getMediaType() == MediaType.META) {
            if (mediaBuffer.getFormat().contains("exif")) {
                if (mediaBuffer.getData() instanceof UniExifInterface) {
                    this.uniExifInterface = (UniExifInterface) mediaBuffer.getTypedData(UniExifInterface.class);
                    return;
                } else if (mediaBuffer.getData() instanceof ExifInterface) {
                    this.exifSupplier = new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileWriter$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return MediaBufferFileWriter.lambda$extractMetaBuffers$4(MediaBuffer.this);
                        }
                    };
                    return;
                } else {
                    if (mediaBuffer.getData() instanceof ByteBuffer) {
                        this.exif = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                        return;
                    }
                    return;
                }
            }
            if (mediaBuffer.getFormat().contains("icc")) {
                this.icc = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                return;
            }
            if (mediaBuffer.getFormat().contains("gain-map")) {
                this.gainmap = (Bitmap) mediaBuffer.getTypedData(Bitmap.class);
                return;
            }
            Log.w(TAG, "not supported metadata given " + mediaBuffer);
        }
    }

    static /* synthetic */ ExifInterface lambda$extractMetaBuffers$4(MediaBuffer mediaBuffer) {
        return (ExifInterface) mediaBuffer.getTypedData(ExifInterface.class);
    }

    public MediaBufferFileWriter setCompressImageWriter(BiFunction<MediaBuffer, String, Boolean> biFunction) {
        this.compressImageWriter = biFunction;
        return this;
    }

    public MediaBufferFileWriter setExifSupplier(Supplier<ExifInterface> supplier) {
        this.exifSupplier = supplier;
        return this;
    }

    public MediaBufferFileWriter setUniExifInterface(UniExifInterface uniExifInterface) {
        this.uniExifInterface = uniExifInterface;
        return this;
    }

    public MediaBufferFileWriter setExif(ByteBuffer byteBuffer) {
        this.exif = byteBuffer;
        return this;
    }

    public MediaBufferFileWriter setIcc(ByteBuffer byteBuffer) {
        this.icc = byteBuffer;
        return this;
    }

    public MediaBufferFileWriter setGainmap(Bitmap bitmap) {
        this.gainmap = bitmap;
        return this;
    }

    public MediaBufferFileWriter setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public MediaBufferFileWriter setHDR(boolean z) {
        this.isHDR = z;
        return this;
    }
}
