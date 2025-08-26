package com.samsung.android.sume.core.buffer;

import android.media.ExifInterface;
import android.media.MediaMetrics;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class MediaBufferFileReader {
    private static final String TAG = Def.tagOf((Class<?>) MediaBufferFileReader.class);
    private static final List<String> imageExt;
    private BiFunction<MediaFormat, String, MediaBuffer> compressedMediaReader;
    private Consumer<ExifInterface> exifConsumer;
    private MediaFormat format;
    private final List<String> paths;

    static {
        List<String> list = (List) Arrays.stream(ColorFormat.values()).skip(0L).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ColorFormat) obj).name().toLowerCase(Locale.ROOT);
            }
        }).collect(Collectors.toList());
        imageExt = list;
        list.addAll(Arrays.asList("i420", "jpg", "heic", "png", "jpeg"));
    }

    public MediaBufferFileReader(String... strArr) throws FileNotFoundException {
        for (String str : strArr) {
            if (!new File(str).exists()) {
                throw new FileNotFoundException("no file for " + str);
            }
        }
        this.paths = Arrays.asList(strArr);
    }

    public MediaBufferFileReader setFormat(MediaFormat mediaFormat) {
        this.format = mediaFormat;
        return this;
    }

    public MediaBufferFileReader setShape(Shape shape) {
        if (this.format == null) {
            this.format = m9525x86d04131(this.paths.get(0));
        }
        this.format = this.format.toMutableFormat().setShape(shape).toMediaFormat();
        return this;
    }

    public MediaBufferFileReader setCompressedMediaReader(BiFunction<MediaFormat, String, MediaBuffer> biFunction) {
        this.compressedMediaReader = biFunction;
        return this;
    }

    public MediaBufferFileReader setExifConsumer(Consumer<ExifInterface> consumer) {
        this.exifConsumer = consumer;
        return this;
    }

    private MediaFormat getImageFormatFromName(String str, String str2) {
        ColorFormat colorFormatValueOf;
        MutableMediaFormat mutableMediaFormatMutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
        ColorFormat colorFormat = ColorFormat.NONE;
        if (Arrays.asList("jpg", "heic", "png", "jpeg").contains(str2.toLowerCase())) {
            mutableMediaFormatMutableImageOf.setMediaType(MediaType.COMPRESSED_IMAGE);
        } else {
            mutableMediaFormatMutableImageOf.setMediaType(MediaType.RAW_IMAGE);
            if ("i420".equals(str2)) {
                colorFormatValueOf = ColorFormat.YUV420;
            } else {
                colorFormatValueOf = ColorFormat.valueOf(str2.toUpperCase(Locale.ROOT));
            }
            colorFormat = colorFormatValueOf;
            Matcher matcher = Pattern.compile("\\d+x\\d+").matcher(str);
            if (matcher.find()) {
                Integer[] numArr = (Integer[]) Arrays.stream(matcher.group().split("x")).map(new MediaBufferFileReader$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return MediaBufferFileReader.lambda$getImageFormatFromName$1(i);
                    }
                });
                mutableMediaFormatMutableImageOf.setCols(numArr[0].intValue());
                mutableMediaFormatMutableImageOf.setRows(numArr[1].intValue());
            } else {
                throw new UnsupportedOperationException("not supported yet");
            }
        }
        mutableMediaFormatMutableImageOf.setColorFormat(colorFormat);
        mutableMediaFormatMutableImageOf.setDataType(DataType.of(DataType.U8, colorFormat.getChannels()));
        return mutableMediaFormatMutableImageOf.toMediaFormat();
    }

    static /* synthetic */ Integer[] lambda$getImageFormatFromName$1(int i) {
        return new Integer[i];
    }

    private MediaFormat getVideoFormatFromName(String str, String str2) {
        MutableMediaFormat mutableMediaFormatMutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
        mutableMediaFormatMutableImageOf.setMediaType(MediaType.COMPRESSED_VIDEO);
        mutableMediaFormatMutableImageOf.setDataType(DataType.U8C3);
        return mutableMediaFormatMutableImageOf.toMediaFormat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getFormatFromName, reason: merged with bridge method [inline-methods] */
    public MediaFormat m9525x86d04131(String str) {
        String extension = getExtension(str);
        if (isImage(extension)) {
            return getImageFormatFromName(str, extension);
        }
        if (isVideo(extension)) {
            return getVideoFormatFromName(str, extension);
        }
        throw new UnsupportedOperationException("not supported yet for " + extension);
    }

    static String getExtension(String str) {
        return str.substring(str.lastIndexOf(MediaMetrics.SEPARATOR) + 1).toLowerCase(Locale.ROOT);
    }

    private static ExifInterface readExif(String str) throws IOException {
        Exception e;
        ExifInterface exifInterface;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                exifInterface = new ExifInterface(fileInputStream);
                try {
                    fileInputStream.close();
                    return exifInterface;
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return exifInterface;
                }
            } finally {
            }
        } catch (Exception e3) {
            e = e3;
            exifInterface = null;
        }
    }

    private boolean isImage(String str) {
        return imageExt.contains(str);
    }

    private boolean isVideo(String str) {
        return BnRConstants.VIDEO_FILE_EXTENSION.equals(str);
    }

    public MediaBuffer read() throws UnsupportedOperationException {
        List list = (List) this.paths.stream().map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9526xa4e33a0c((String) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((MediaBuffer) obj);
            }
        }).collect(Collectors.toList());
        if (list.size() == 1) {
            return (MediaBuffer) list.get(0);
        }
        return MediaBuffer.groupOf(0, (List<MediaBuffer>) list);
    }

    /* renamed from: lambda$read$7$com-samsung-android-sume-core-buffer-MediaBufferFileReader, reason: not valid java name */
    /* synthetic */ MediaBuffer m9526xa4e33a0c(final String str) throws IOException {
        final MediaFormat mediaFormat = (MediaFormat) Optional.ofNullable(this.format).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m9525x86d04131(str);
            }
        });
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$MediaType[mediaFormat.getMediaType().ordinal()];
        if (i == 1) {
            return readRawImage(mediaFormat, str);
        }
        if (i != 2) {
            if (i == 3) {
                return (MediaBuffer) Optional.ofNullable(this.compressedMediaReader).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaBufferFileReader.lambda$read$5(mediaFormat, str, (BiFunction) obj);
                    }
                }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda7
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return MediaBufferFileReader.lambda$read$6();
                    }
                });
            }
            throw new UnsupportedOperationException("not support yet");
        }
        MediaBuffer mediaBuffer = (MediaBuffer) Optional.ofNullable(this.compressedMediaReader).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaBufferFileReader.lambda$read$3(mediaFormat, str, (BiFunction) obj);
            }
        }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaBufferFileReader.lambda$read$4();
            }
        });
        ExifInterface exif = readExif(str);
        if (exif != null) {
            int i2 = 0;
            switch (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)) {
                case 3:
                case 4:
                    i2 = 180;
                    break;
                case 5:
                case 6:
                    i2 = 90;
                    break;
                case 7:
                case 8:
                    i2 = 270;
                    break;
            }
            if (i2 != 0) {
                mediaBuffer.setExtra("rotation-degrees", Integer.valueOf(i2));
            }
            Consumer<ExifInterface> consumer = this.exifConsumer;
            if (consumer != null) {
                consumer.accept(exif);
            }
        }
        return mediaBuffer;
    }

    /* renamed from: com.samsung.android.sume.core.buffer.MediaBufferFileReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

        static {
            int[] iArr = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
            try {
                iArr[MediaType.RAW_IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static /* synthetic */ MediaBuffer lambda$read$3(MediaFormat mediaFormat, String str, BiFunction biFunction) {
        return (MediaBuffer) biFunction.apply(mediaFormat, str);
    }

    static /* synthetic */ IllegalArgumentException lambda$read$4() {
        return new IllegalArgumentException("not implement internal compress image reader yet, plz should set explicitly");
    }

    static /* synthetic */ MediaBuffer lambda$read$5(MediaFormat mediaFormat, String str, BiFunction biFunction) {
        return (MediaBuffer) biFunction.apply(mediaFormat, str);
    }

    static /* synthetic */ IllegalArgumentException lambda$read$6() {
        return new IllegalArgumentException("not implement internal compress image reader yet, plz should set explicitly");
    }

    private MediaBuffer readRawImage(MediaFormat mediaFormat, String str) throws IOException {
        File file = new File(str);
        Def.check(file.exists(), "not exist input file " + str, new Object[0]);
        Def.require(file.length() >= mediaFormat.size());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ByteBuffer byteBufferOrder = ByteBuffer.allocateDirect((int) file.length()).order(ByteOrder.nativeOrder());
                fileInputStream.getChannel().read(byteBufferOrder);
                MediaBuffer mediaBufferOf = MediaBuffer.of(mediaFormat, byteBufferOrder);
                fileInputStream.close();
                return mediaBufferOf;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
