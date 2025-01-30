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

/* loaded from: classes4.dex */
public class MediaBufferFileReader {
    private static final String TAG = Def.tagOf((Class<?>) MediaBufferFileReader.class);
    private static final List<String> imageExt;
    private BiFunction<MediaFormat, String, MediaBuffer> compressedMediaReader;
    private Consumer<ExifInterface> exifConsumer;
    private MediaFormat format;
    private final List<String> paths;

    static {
        List<String> list = (List) Arrays.stream(ColorFormat.values()).skip(0L).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String lowerCase;
                lowerCase = ((ColorFormat) obj).name().toLowerCase(Locale.ROOT);
                return lowerCase;
            }
        }).collect(Collectors.toList());
        imageExt = list;
        list.addAll(Arrays.asList("i420", "jpg", "heic", "png", "jpeg"));
    }

    public MediaBufferFileReader(String... paths) throws FileNotFoundException {
        for (String path : paths) {
            File file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException("no file for " + path);
            }
        }
        this.paths = Arrays.asList(paths);
    }

    public MediaBufferFileReader setFormat(MediaFormat format) {
        this.format = format;
        return this;
    }

    public MediaBufferFileReader setShape(Shape shape) {
        if (this.format == null) {
            this.format = m338x86d04131(this.paths.get(0));
        }
        this.format = this.format.toMutableFormat().setShape(shape).toMediaFormat();
        return this;
    }

    public MediaBufferFileReader setCompressedMediaReader(BiFunction<MediaFormat, String, MediaBuffer> compressedImageReader) {
        this.compressedMediaReader = compressedImageReader;
        return this;
    }

    public MediaBufferFileReader setExifConsumer(Consumer<ExifInterface> exifConsumer) {
        this.exifConsumer = exifConsumer;
        return this;
    }

    private MediaFormat getImageFormatFromName(String name, String ext) {
        MutableMediaFormat format = MediaFormat.mutableImageOf(new Object[0]);
        ColorFormat colorFormat = ColorFormat.NONE;
        if (Arrays.asList("jpg", "heic", "png", "jpeg").contains(ext.toLowerCase())) {
            format.setMediaType(MediaType.COMPRESSED_IMAGE);
        } else {
            format.setMediaType(MediaType.RAW_IMAGE);
            if ("i420".equals(ext)) {
                colorFormat = ColorFormat.YUV420;
            } else {
                colorFormat = ColorFormat.valueOf(ext.toUpperCase(Locale.ROOT));
            }
            Matcher resolutionMatcher = Pattern.compile("\\d+x\\d+").matcher(name);
            if (resolutionMatcher.find()) {
                Integer[] resolution = (Integer[]) Arrays.stream(resolutionMatcher.group().split("x")).map(new MediaBufferFileReader$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return MediaBufferFileReader.lambda$getImageFormatFromName$1(i);
                    }
                });
                format.setCols(resolution[0].intValue());
                format.setRows(resolution[1].intValue());
            } else {
                throw new UnsupportedOperationException("not supported yet");
            }
        }
        format.setColorFormat(colorFormat);
        format.setDataType(DataType.m467of(DataType.f3067U8, colorFormat.getChannels()));
        return format.toMediaFormat();
    }

    static /* synthetic */ Integer[] lambda$getImageFormatFromName$1(int x$0) {
        return new Integer[x$0];
    }

    private MediaFormat getVideoFormatFromName(String name, String ext) {
        MutableMediaFormat format = MediaFormat.mutableImageOf(new Object[0]);
        format.setMediaType(MediaType.COMPRESSED_VIDEO);
        format.setDataType(DataType.U8C3);
        return format.toMediaFormat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getFormatFromName, reason: merged with bridge method [inline-methods] */
    public MediaFormat m338x86d04131(String name) {
        String ext = getExtension(name);
        if (isImage(ext)) {
            return getImageFormatFromName(name, ext);
        }
        if (isVideo(ext)) {
            return getVideoFormatFromName(name, ext);
        }
        throw new UnsupportedOperationException("not supported yet for " + ext);
    }

    static String getExtension(String name) {
        return name.substring(name.lastIndexOf(MediaMetrics.SEPARATOR) + 1).toLowerCase(Locale.ROOT);
    }

    private static ExifInterface readExif(String path) {
        ExifInterface exif = null;
        try {
            FileInputStream is = new FileInputStream(path);
            try {
                exif = new ExifInterface(is);
                is.close();
            } finally {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exif;
    }

    private boolean isImage(String ext) {
        return imageExt.contains(ext);
    }

    private boolean isVideo(String ext) {
        return BnRConstants.VIDEO_FILE_EXTENSION.equals(ext);
    }

    public MediaBuffer read() throws UnsupportedOperationException {
        List<MediaBuffer> buffers = (List) this.paths.stream().map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaBufferFileReader.this.m339xa4e33a0c((String) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((MediaBuffer) obj);
            }
        }).collect(Collectors.toList());
        if (buffers.size() == 1) {
            return buffers.get(0);
        }
        return MediaBuffer.groupOf(0, buffers);
    }

    /* renamed from: lambda$read$7$com-samsung-android-sume-core-buffer-MediaBufferFileReader */
    /* synthetic */ MediaBuffer m339xa4e33a0c(final String path) {
        final MediaFormat format = (MediaFormat) Optional.ofNullable(this.format).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaBufferFileReader.this.m338x86d04131(path);
            }
        });
        switch (C52531.$SwitchMap$com$samsung$android$sume$core$types$MediaType[format.getMediaType().ordinal()]) {
            case 1:
                return readRawImage(format, path);
            case 2:
                MediaBuffer buffer = (MediaBuffer) Optional.ofNullable(this.compressedMediaReader).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaBufferFileReader.lambda$read$3(MediaFormat.this, path, (BiFunction) obj);
                    }
                }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda4
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return MediaBufferFileReader.lambda$read$4();
                    }
                });
                ExifInterface exif = readExif(path);
                if (exif != null) {
                    int orientation = 0;
                    switch (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)) {
                        case 3:
                        case 4:
                            orientation = 180;
                            break;
                        case 5:
                        case 6:
                            orientation = 90;
                            break;
                        case 7:
                        case 8:
                            orientation = 270;
                            break;
                    }
                    if (orientation != 0) {
                        buffer.setExtra("rotation-degrees", Integer.valueOf(orientation));
                    }
                    Consumer<ExifInterface> consumer = this.exifConsumer;
                    if (consumer != null) {
                        consumer.accept(exif);
                        return buffer;
                    }
                    return buffer;
                }
                return buffer;
            case 3:
                return (MediaBuffer) Optional.ofNullable(this.compressedMediaReader).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaBufferFileReader.lambda$read$5(MediaFormat.this, path, (BiFunction) obj);
                    }
                }).orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.buffer.MediaBufferFileReader$$ExternalSyntheticLambda6
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return MediaBufferFileReader.lambda$read$6();
                    }
                });
            default:
                throw new UnsupportedOperationException("not support yet");
        }
    }

    /* renamed from: com.samsung.android.sume.core.buffer.MediaBufferFileReader$1 */
    static /* synthetic */ class C52531 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$MediaType;

        static {
            int[] iArr = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$MediaType = iArr;
            try {
                iArr[MediaType.RAW_IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$MediaType[MediaType.COMPRESSED_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static /* synthetic */ MediaBuffer lambda$read$3(MediaFormat format, String path, BiFunction reader) {
        return (MediaBuffer) reader.apply(format, path);
    }

    static /* synthetic */ IllegalArgumentException lambda$read$4() {
        return new IllegalArgumentException("not implement internal compress image reader yet, plz should set explicitly");
    }

    static /* synthetic */ MediaBuffer lambda$read$5(MediaFormat format, String path, BiFunction reader) {
        return (MediaBuffer) reader.apply(format, path);
    }

    static /* synthetic */ IllegalArgumentException lambda$read$6() {
        return new IllegalArgumentException("not implement internal compress image reader yet, plz should set explicitly");
    }

    private MediaBuffer readRawImage(MediaFormat mediaFormat, String path) {
        File file = new File(path);
        Def.check(file.exists(), "not exist input file " + path, new Object[0]);
        Def.require(file.length() >= mediaFormat.size());
        try {
            FileInputStream is = new FileInputStream(file);
            try {
                ByteBuffer buffer = ByteBuffer.allocateDirect((int) file.length()).order(ByteOrder.nativeOrder());
                is.getChannel().read(buffer);
                MediaBuffer m325of = MediaBuffer.m325of(mediaFormat, buffer);
                is.close();
                return m325of;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
