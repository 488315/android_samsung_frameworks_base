package android.media;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.util.Log;

import com.samsung.android.audio.SoundTheme;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaMetadataRetriever implements AutoCloseable {
    private static final int EMBEDDED_PICTURE_TYPE_ANY = 65535;
    public static final int METADATA_KEY_ALBUM = 1;
    public static final int METADATA_KEY_ALBUMARTIST = 13;
    public static final int METADATA_KEY_ARTIST = 2;
    public static final int METADATA_KEY_AUTHOR = 3;
    public static final int METADATA_KEY_BITRATE = 20;
    public static final int METADATA_KEY_BITS_PER_SAMPLE = 39;
    public static final int METADATA_KEY_CAPTURE_FRAMERATE = 25;
    public static final int METADATA_KEY_CD_TRACK_NUMBER = 0;
    public static final int METADATA_KEY_COLOR_RANGE = 37;
    public static final int METADATA_KEY_COLOR_STANDARD = 35;
    public static final int METADATA_KEY_COLOR_TRANSFER = 36;
    public static final int METADATA_KEY_COMPILATION = 15;
    public static final int METADATA_KEY_COMPOSER = 4;
    public static final int METADATA_KEY_DATE = 5;
    public static final int METADATA_KEY_DISC_NUMBER = 14;
    public static final int METADATA_KEY_DURATION = 9;
    public static final int METADATA_KEY_EXIF_LENGTH = 34;
    public static final int METADATA_KEY_EXIF_OFFSET = 33;
    public static final int METADATA_KEY_GENRE = 6;
    public static final int METADATA_KEY_HAS_AUDIO = 16;
    public static final int METADATA_KEY_HAS_IMAGE = 26;
    public static final int METADATA_KEY_HAS_VIDEO = 17;
    public static final int METADATA_KEY_IMAGE_COUNT = 27;
    public static final int METADATA_KEY_IMAGE_HEIGHT = 30;
    public static final int METADATA_KEY_IMAGE_PRIMARY = 28;
    public static final int METADATA_KEY_IMAGE_ROTATION = 31;
    public static final int METADATA_KEY_IMAGE_WIDTH = 29;
    public static final int METADATA_KEY_IS_DRM = 22;
    public static final int METADATA_KEY_LOCATION = 23;
    public static final int METADATA_KEY_MIMETYPE = 12;
    public static final int METADATA_KEY_NUM_TRACKS = 10;
    public static final int METADATA_KEY_SAMPLERATE = 38;
    public static final int METADATA_KEY_TIMED_TEXT_LANGUAGES = 21;
    public static final int METADATA_KEY_TITLE = 7;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int METADATA_KEY_VIDEO_CODEC_MIME_TYPE = 40;

    public static final int METADATA_KEY_VIDEO_FRAME_COUNT = 32;
    public static final int METADATA_KEY_VIDEO_HEIGHT = 19;
    public static final int METADATA_KEY_VIDEO_ROTATION = 24;
    public static final int METADATA_KEY_VIDEO_WIDTH = 18;
    public static final int METADATA_KEY_WRITER = 11;
    public static final int METADATA_KEY_XMP_LENGTH = 42;
    public static final int METADATA_KEY_XMP_OFFSET = 41;
    public static final int METADATA_KEY_YEAR = 8;
    public static final int OPTION_CLOSEST = 3;
    public static final int OPTION_CLOSEST_SYNC = 2;
    public static final int OPTION_NEXT_SYNC = 1;
    public static final int OPTION_PREVIOUS_SYNC = 0;
    public static final int SEM_METADATA_KEY_360_VIDEO = 1021;
    public static final int SEM_METADATA_KEY_AUDIOCODECINFO = 1025;
    public static final int SEM_METADATA_KEY_AUTHORIZATION = 1015;
    public static final int SEM_METADATA_KEY_BITS_PER_SAMPLE = 1020;
    public static final int SEM_METADATA_KEY_CREATIONTIME = 1026;
    public static final int SEM_METADATA_KEY_HDR10_VIDEO = 1027;
    public static final int SEM_METADATA_KEY_LENS_FOCAL_LENGTH = 1034;
    public static final int SEM_METADATA_KEY_LENS_TYPE = 1033;
    public static final int SEM_METADATA_KEY_LENS_ZOOMED_FOCAL_LENGTH = 1035;
    public static final int SEM_METADATA_KEY_MULTI_AUDIO_CHANNELS = 1012;
    public static final int SEM_METADATA_KEY_MULTI_AUDIO_LANGUAGES = 1011;
    public static final int SEM_METADATA_KEY_NUM_AUDIO_TRACKS = 1010;
    public static final int SEM_METADATA_KEY_RECORDINGMODE = 1022;
    public static final int SEM_METADATA_KEY_SAMPLING_RATE = 1019;
    public static final int SEM_METADATA_KEY_SLOWVIDEOINFO = 1023;
    public static final int SEM_METADATA_KEY_USER_EDITED_DURATION = 1029;
    public static final int SEM_METADATA_KEY_UTC_OFFSET = 1032;
    public static final int SEM_METADATA_KEY_VIDEOCODECINFO = 1024;
    public static final int SEM_METADATA_KEY_VIDEO_BIT_DEPTH = 1028;
    public static final int SEM_METADATA_KEY_VIDEO_SYNC_FRAME_SIZE_INFO = 1031;
    public static final int SEM_METADATA_KEY_VIDEO_SYNC_FRAME_TIME_INFO = 1030;
    public static final int SEM_OPTION_HW_CODEC = 0;
    public static final int SEM_OPTION_SW_CODEC = 1;
    private static final String[] STANDARD_GENRES = {
        "Blues",
        "Classic Rock",
        "Country",
        "Dance",
        "Disco",
        "Funk",
        "Grunge",
        "Hip-Hop",
        "Jazz",
        "Metal",
        "New Age",
        "Oldies",
        "Other",
        "Pop",
        "R&B",
        "Rap",
        "Reggae",
        "Rock",
        "Techno",
        "Industrial",
        "Alternative",
        "Ska",
        "Death Metal",
        "Pranks",
        "Soundtrack",
        "Euro-Techno",
        "Ambient",
        "Trip-Hop",
        "Vocal",
        "Jazz+Funk",
        "Fusion",
        "Trance",
        "Classical",
        "Instrumental",
        "Acid",
        "House",
        "Game",
        "Sound Clip",
        "Gospel",
        "Noise",
        "AlternRock",
        "Bass",
        "Soul",
        "Punk",
        "Space",
        "Meditative",
        "Instrumental Pop",
        "Instrumental Rock",
        "Ethnic",
        "Gothic",
        "Darkwave",
        "Techno-Industrial",
        "Electronic",
        "Pop-Folk",
        "Eurodance",
        "Dream",
        "Southern Rock",
        "Comedy",
        "Cult",
        "Gangsta",
        "Top 40",
        "Christian Rap",
        "Pop/Funk",
        "Jungle",
        "Native American",
        "Cabaret",
        "New Wave",
        "Psychadelic",
        "Rave",
        "Showtunes",
        "Trailer",
        "Lo-Fi",
        "Tribal",
        "Acid Punk",
        "Acid Jazz",
        "Polka",
        SoundTheme.Retro,
        "Musical",
        "Rock & Roll",
        "Hard Rock",
        "Folk",
        "Folk-Rock",
        "National Folk",
        "Swing",
        "Fast Fusion",
        "Bebob",
        "Latin",
        "Revival",
        "Celtic",
        "Bluegrass",
        "Avantgarde",
        "Gothic Rock",
        "Progressive Rock",
        "Psychedelic Rock",
        "Symphonic Rock",
        "Slow Rock",
        "Big Band",
        "Chorus",
        "Easy Listening",
        "Acoustic",
        "Humour",
        "Speech",
        "Chanson",
        "Opera",
        "Chamber Music",
        "Sonata",
        "Symphony",
        "Booty Bass",
        "Primus",
        "Porn Groove",
        "Satire",
        "Slow Jam",
        "Club",
        "Tango",
        "Samba",
        "Folklore",
        "Ballad",
        "Power Ballad",
        "Rhythmic Soul",
        "Freestyle",
        "Duet",
        "Punk Rock",
        "Drum Solo",
        "A capella",
        "Euro-House",
        "Dance Hall",
        "Goa",
        "Drum & Bass",
        "Club-House",
        "Hardcore",
        "Terror",
        "Indie",
        "BritPop",
        "Afro-Punk",
        "Polsk Punk",
        "Beat",
        "Christian Gangsta Rap",
        "Heavy Metal",
        "Black Metal",
        "Crossover",
        "Contemporary Christian",
        "Christian Rock",
        "Merengue",
        "Salsa",
        "Thrash Metal",
        "Anime",
        "Jpop",
        "Synthpop"
    };
    private static final String TAG = "MediaMetadataRetriever";
    private long mNativeContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Option {}

    private native List<Bitmap> _getFrameAtIndex(int i, int i2, BitmapParams bitmapParams);

    private native Bitmap _getFrameAtTime(long j, int i, int i2, int i3, BitmapParams bitmapParams);

    private native Bitmap _getImageAtIndex(int i, BitmapParams bitmapParams);

    private native void _setDataSource(MediaDataSource mediaDataSource)
            throws IllegalArgumentException;

    private native void _setDataSource(
            IBinder iBinder, String str, String[] strArr, String[] strArr2)
            throws IllegalArgumentException;

    private native void _setDataSource(FileDescriptor fileDescriptor, long j, long j2)
            throws IllegalArgumentException;

    private native void detailedThumbnailMode(boolean z, int i);

    private native byte[] getEmbeddedPicture(int i);

    private native String nativeExtractMetadata(int i);

    private final native void native_finalize();

    private static native void native_init();

    private native void native_setup();

    public native Bitmap getThumbnailImageAtIndex(int i, BitmapParams bitmapParams, int i2, int i3);

    public native void release() throws IOException;

    public native void semSetVideoSize(int i, int i2, boolean z, boolean z2);

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public MediaMetadataRetriever() {
        native_setup();
    }

    public void setDataSource(String path) throws IllegalArgumentException {
        if (path == null) {
            Log.e(TAG, "setDataSource path is null");
            throw new IllegalArgumentException("null path");
        }
        Uri uri = Uri.parse(path);
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            path = uri.getPath();
        } else if (scheme != null) {
            setDataSource(path, new HashMap());
            return;
        }
        try {
            FileInputStream is = new FileInputStream(path);
            try {
                FileDescriptor fd = is.getFD();
                setDataSource(fd, 0L, 576460752303423487L);
                is.close();
            } finally {
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "setDataSource - FileNotFoundException");
            throw new IllegalArgumentException(path + " does not exist");
        } catch (IOException e2) {
            Log.e(TAG, "setDataSource - IOException");
            throw new IllegalArgumentException("couldn't open " + path);
        }
    }

    public void setDataSource(String uri, Map<String, String> headers)
            throws IllegalArgumentException {
        int i = 0;
        String[] keys = new String[headers.size()];
        String[] values = new String[headers.size()];
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            keys[i] = entry.getKey();
            values[i] = entry.getValue();
            i++;
        }
        _setDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(uri), uri, keys, values);
    }

    public void setDataSource(FileDescriptor fd, long offset, long length)
            throws IllegalArgumentException {
        try {
            ParcelFileDescriptor modernFd = FileUtils.convertToModernFd(fd);
            try {
                if (modernFd == null) {
                    _setDataSource(fd, offset, length);
                } else {
                    _setDataSource(modernFd.getFileDescriptor(), offset, length);
                }
                if (modernFd != null) {
                    modernFd.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.w(TAG, "Ignoring IO error while setting data source", e);
        }
    }

    public void setDataSource(FileDescriptor fd) throws IllegalArgumentException {
        setDataSource(fd, 0L, 576460752303423487L);
    }

    public void setDataSource(Context context, Uri uri)
            throws IllegalArgumentException, SecurityException {
        if (uri == null) {
            Log.e(TAG, "setDataSource - uri is null");
            throw new IllegalArgumentException("null uri");
        }
        String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            setDataSource(uri.getPath());
            return;
        }
        AssetFileDescriptor fd = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            try {
                boolean optimize =
                        SystemProperties.getBoolean("fuse.sys.transcode_retriever_optimize", false);
                Bundle opts = new Bundle();
                opts.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
                AssetFileDescriptor fd2 =
                        optimize
                                ? resolver.openTypedAssetFileDescriptor(uri, "*/*", opts)
                                : resolver.openAssetFileDescriptor(uri, "r");
                if (fd2 == null) {
                    Log.e(TAG, "setDataSource - fd is null");
                    throw new IllegalArgumentException("got null FileDescriptor for " + uri);
                }
                FileDescriptor descriptor = fd2.getFileDescriptor();
                if (!descriptor.valid()) {
                    Log.e(TAG, "setDataSource -descriptor is not valid");
                    throw new IllegalArgumentException("got invalid FileDescriptor for " + uri);
                }
                if (fd2.getDeclaredLength() < 0) {
                    setDataSource(descriptor);
                } else {
                    setDataSource(descriptor, fd2.getStartOffset(), fd2.getDeclaredLength());
                }
                if (fd2 == null) {
                    return;
                }
                try {
                    fd2.close();
                } catch (IOException e) {
                    Log.e(TAG, "setDataSource -descriptor is not valid");
                }
            } catch (FileNotFoundException e2) {
                Log.e(TAG, "setDataSource - FileNotFoundException");
                throw new IllegalArgumentException("could not access " + uri);
            }
        } catch (SecurityException e3) {
            if (0 != 0) {
                try {
                    fd.close();
                } catch (IOException e4) {
                    Log.e(TAG, "setDataSource -descriptor is not valid");
                }
            }
            setDataSource(uri.toString());
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fd.close();
                } catch (IOException e5) {
                    Log.e(TAG, "setDataSource -descriptor is not valid");
                }
            }
            throw th;
        }
    }

    public void setDataSource(MediaDataSource dataSource) throws IllegalArgumentException {
        _setDataSource(dataSource);
    }

    public String extractMetadata(int keyCode) {
        String meta = nativeExtractMetadata(keyCode);
        if (keyCode == 6) {
            return convertGenreTag(meta);
        }
        return meta;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00cf, code lost:

       return null;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String convertGenreTag(java.lang.String r8) {
        /*
            r7 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = 0
            char r0 = r8.charAt(r0)
            boolean r0 = java.lang.Character.isDigit(r0)
            if (r0 == 0) goto L26
            int r0 = java.lang.Integer.parseInt(r8)     // Catch: java.lang.NumberFormatException -> L24
            if (r0 < 0) goto L23
            java.lang.String[] r2 = android.media.MediaMetadataRetriever.STANDARD_GENRES     // Catch: java.lang.NumberFormatException -> L24
            int r2 = r2.length     // Catch: java.lang.NumberFormatException -> L24
            if (r0 >= r2) goto L23
            java.lang.String[] r2 = android.media.MediaMetadataRetriever.STANDARD_GENRES     // Catch: java.lang.NumberFormatException -> L24
            r1 = r2[r0]     // Catch: java.lang.NumberFormatException -> L24
            return r1
        L23:
            goto L25
        L24:
            r0 = move-exception
        L25:
            return r1
        L26:
            r0 = 0
            r2 = 0
        L28:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L45
            if (r0 != 0) goto L36
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r0 = r3
        L36:
            int r3 = r0.length()
            if (r3 == 0) goto L41
            java.lang.String r3 = ", "
            r0.append(r3)
        L41:
            r0.append(r2)
            r2 = 0
        L45:
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 == 0) goto L5a
        L4c:
            if (r0 == 0) goto L59
            int r3 = r0.length()
            if (r3 != 0) goto L55
            goto L59
        L55:
            java.lang.String r1 = r0.toString()
        L59:
            return r1
        L5a:
            java.lang.String r3 = "(RX)"
            boolean r3 = r8.startsWith(r3)
            r4 = 4
            if (r3 == 0) goto L6a
            java.lang.String r2 = "Remix"
            java.lang.String r8 = r8.substring(r4)
            goto L28
        L6a:
            java.lang.String r3 = "(CR)"
            boolean r3 = r8.startsWith(r3)
            if (r3 == 0) goto L79
            java.lang.String r2 = "Cover"
            java.lang.String r8 = r8.substring(r4)
            goto L28
        L79:
            java.lang.String r3 = "(("
            boolean r3 = r8.startsWith(r3)
            r4 = -1
            r5 = 41
            r6 = 1
            if (r3 == 0) goto L9f
            int r3 = r8.indexOf(r5)
            if (r3 != r4) goto L92
            java.lang.String r2 = r8.substring(r6)
            java.lang.String r8 = ""
            goto L9e
        L92:
            int r4 = r3 + 1
            java.lang.String r2 = r8.substring(r6, r4)
            int r4 = r3 + 1
            java.lang.String r8 = r8.substring(r4)
        L9e:
            goto L28
        L9f:
            java.lang.String r3 = "("
            boolean r3 = r8.startsWith(r3)
            if (r3 == 0) goto Ld2
            int r3 = r8.indexOf(r5)
            if (r3 != r4) goto Lae
            return r1
        Lae:
            java.lang.String r4 = r8.substring(r6, r3)
            java.lang.String r5 = r4.toString()     // Catch: java.lang.NumberFormatException -> Ld0
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> Ld0
            if (r5 < 0) goto Lcf
            java.lang.String[] r6 = android.media.MediaMetadataRetriever.STANDARD_GENRES     // Catch: java.lang.NumberFormatException -> Ld0
            int r6 = r6.length     // Catch: java.lang.NumberFormatException -> Ld0
            if (r5 >= r6) goto Lcf
            java.lang.String[] r6 = android.media.MediaMetadataRetriever.STANDARD_GENRES     // Catch: java.lang.NumberFormatException -> Ld0
            r6 = r6[r5]     // Catch: java.lang.NumberFormatException -> Ld0
            r2 = r6
            int r5 = r3 + 1
            java.lang.String r8 = r8.substring(r5)
            goto L28
        Lcf:
            return r1
        Ld0:
            r5 = move-exception
            return r1
        Ld2:
            r2 = r8
            java.lang.String r8 = ""
            goto L28
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.media.MediaMetadataRetriever.convertGenreTag(java.lang.String):java.lang.String");
    }

    public Bitmap getFrameAtTime(long timeUs, int option) {
        if (option < 0 || option > 3) {
            throw new IllegalArgumentException("Unsupported option: " + option);
        }
        return _getFrameAtTime(timeUs, option, -1, -1, null);
    }

    public Bitmap getFrameAtTime(long timeUs, int option, BitmapParams params) {
        if (option < 0 || option > 3) {
            throw new IllegalArgumentException("Unsupported option: " + option);
        }
        return _getFrameAtTime(timeUs, option, -1, -1, params);
    }

    public Bitmap getScaledFrameAtTime(long timeUs, int option, int dstWidth, int dstHeight) {
        validate(option, dstWidth, dstHeight);
        return _getFrameAtTime(timeUs, option, dstWidth, dstHeight, null);
    }

    public Bitmap getScaledFrameAtTime(
            long timeUs, int option, int dstWidth, int dstHeight, BitmapParams params) {
        validate(option, dstWidth, dstHeight);
        return _getFrameAtTime(timeUs, option, dstWidth, dstHeight, params);
    }

    private void validate(int option, int dstWidth, int dstHeight) {
        if (option < 0 || option > 3) {
            throw new IllegalArgumentException("Unsupported option: " + option);
        }
        if (dstWidth <= 0) {
            throw new IllegalArgumentException("Invalid width: " + dstWidth);
        }
        if (dstHeight <= 0) {
            throw new IllegalArgumentException("Invalid height: " + dstHeight);
        }
    }

    public Bitmap getFrameAtTime(long timeUs) {
        return getFrameAtTime(timeUs, 2);
    }

    public Bitmap getFrameAtTime() {
        return _getFrameAtTime(-1L, 2, -1, -1, null);
    }

    public static final class BitmapParams {
        private Bitmap.Config inPreferredConfig = Bitmap.Config.ARGB_8888;
        private Bitmap.Config outActualConfig = Bitmap.Config.ARGB_8888;

        public void setPreferredConfig(Bitmap.Config config) {
            if (config == null) {
                throw new IllegalArgumentException("preferred config can't be null");
            }
            this.inPreferredConfig = config;
        }

        public Bitmap.Config getPreferredConfig() {
            return this.inPreferredConfig;
        }

        public Bitmap.Config getActualConfig() {
            return this.outActualConfig;
        }
    }

    public Bitmap getFrameAtIndex(int frameIndex, BitmapParams params) {
        List<Bitmap> bitmaps = getFramesAtIndex(frameIndex, 1, params);
        return bitmaps.get(0);
    }

    public Bitmap getFrameAtIndex(int frameIndex) {
        List<Bitmap> bitmaps = getFramesAtIndex(frameIndex, 1);
        return bitmaps.get(0);
    }

    public List<Bitmap> getFramesAtIndex(int frameIndex, int numFrames, BitmapParams params) {
        return getFramesAtIndexInternal(frameIndex, numFrames, params);
    }

    public List<Bitmap> getFramesAtIndex(int frameIndex, int numFrames) {
        return getFramesAtIndexInternal(frameIndex, numFrames, null);
    }

    private List<Bitmap> getFramesAtIndexInternal(
            int frameIndex, int numFrames, BitmapParams params) {
        if (!"yes".equals(extractMetadata(17))) {
            throw new IllegalStateException("Does not contain video or image sequences");
        }
        int frameCount = Integer.parseInt(extractMetadata(32));
        if (frameIndex < 0
                || numFrames < 1
                || frameIndex >= frameCount
                || frameIndex > frameCount - numFrames) {
            throw new IllegalArgumentException(
                    "Invalid frameIndex or numFrames: " + frameIndex + ", " + numFrames);
        }
        return _getFrameAtIndex(frameIndex, numFrames, params);
    }

    public Bitmap getImageAtIndex(int imageIndex, BitmapParams params) {
        return getImageAtIndexInternal(imageIndex, params);
    }

    public Bitmap getImageAtIndex(int imageIndex) {
        return getImageAtIndexInternal(imageIndex, null);
    }

    public Bitmap getPrimaryImage(BitmapParams params) {
        return getImageAtIndexInternal(-1, params);
    }

    public Bitmap getPrimaryImage() {
        return getImageAtIndexInternal(-1, null);
    }

    private Bitmap getImageAtIndexInternal(int imageIndex, BitmapParams params) {
        if (!"yes".equals(extractMetadata(26))) {
            throw new IllegalStateException("Does not contain still images");
        }
        String imageCount = extractMetadata(27);
        if (imageIndex >= Integer.parseInt(imageCount)) {
            throw new IllegalArgumentException("Invalid image index: " + imageCount);
        }
        return _getImageAtIndex(imageIndex, params);
    }

    public void semSetDetailedThumbnailMode(int option) throws IllegalStateException {
        detailedThumbnailMode(true, option);
    }

    public void semResetDetailedThumbnailMode() throws IllegalStateException {
        detailedThumbnailMode(false, 0);
    }

    public byte[] getEmbeddedPicture() {
        return getEmbeddedPicture(65535);
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        release();
    }

    protected void finalize() throws Throwable {
        try {
            native_finalize();
        } finally {
            super.finalize();
        }
    }
}
