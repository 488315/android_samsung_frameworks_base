package android.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class EncoderProfiles {
    private List<AudioProfile> audioProfiles;
    private int durationSecs;
    private int fileFormat;
    private List<VideoProfile> videoProfiles;

    public int getDefaultDurationSeconds() {
        return this.durationSecs;
    }

    public int getRecommendedFileFormat() {
        return this.fileFormat;
    }

    public static final class VideoProfile {
        public static final int HDR_DOLBY_VISION = 4;
        public static final int HDR_HDR10 = 2;
        public static final int HDR_HDR10PLUS = 3;
        public static final int HDR_HLG = 1;
        public static final int HDR_NONE = 0;
        public static final int YUV_420 = 0;
        public static final int YUV_422 = 1;
        public static final int YUV_444 = 2;
        private int bitDepth;
        private int bitrate;
        private int chromaSubsampling;
        private int codec;
        private int frameRate;
        private int hdrFormat;
        private int height;
        private int profile;
        private int width;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ChromaSubsampling {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface HdrFormat {
        }

        public int getCodec() {
            return this.codec;
        }

        public String getMediaType() {
            int i = this.codec;
            if (i == 1) {
                return "video/3gpp";
            }
            if (i == 2) {
                return "video/avc";
            }
            if (i == 3) {
                return "video/mp4v-es";
            }
            if (i == 4) {
                return MediaFormat.MIMETYPE_VIDEO_VP8;
            }
            if (i == 5) {
                return "video/hevc";
            }
            if (i == 6) {
                return MediaFormat.MIMETYPE_VIDEO_VP9;
            }
            if (i == 7) {
                return MediaFormat.MIMETYPE_VIDEO_DOLBY_VISION;
            }
            if (i == 8) {
                return MediaFormat.MIMETYPE_VIDEO_AV1;
            }
            throw new RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getFrameRate() {
            return this.frameRate;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getProfile() {
            return this.profile;
        }

        public int getBitDepth() {
            return this.bitDepth;
        }

        public int getChromaSubsampling() {
            return this.chromaSubsampling;
        }

        public int getHdrFormat() {
            return this.hdrFormat;
        }

        VideoProfile(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.codec = i;
            this.width = i2;
            this.height = i3;
            this.frameRate = i4;
            this.bitrate = i5;
            this.profile = i6;
            this.chromaSubsampling = i7;
            this.bitDepth = i8;
            this.hdrFormat = i9;
        }

        VideoProfile(int i, int i2, int i3, int i4, int i5, int i6) {
            this(i, i2, i3, i4, i5, i6, 0, 8, 0);
        }
    }

    public List<AudioProfile> getAudioProfiles() {
        return this.audioProfiles;
    }

    public List<VideoProfile> getVideoProfiles() {
        return this.videoProfiles;
    }

    public static final class AudioProfile {
        private int bitrate;
        private int channels;
        private int codec;
        private int profile;
        private int sampleRate;

        public int getCodec() {
            return this.codec;
        }

        public String getMediaType() {
            int i = this.codec;
            if (i == 1) {
                return "audio/3gpp";
            }
            if (i == 2) {
                return "audio/amr-wb";
            }
            if (i == 3 || i == 4 || i == 5) {
                return "audio/mp4a-latm";
            }
            if (i == 6) {
                return MediaFormat.MIMETYPE_AUDIO_VORBIS;
            }
            if (i == 7) {
                return MediaFormat.MIMETYPE_AUDIO_OPUS;
            }
            throw new RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getSampleRate() {
            return this.sampleRate;
        }

        public int getChannels() {
            return this.channels;
        }

        public int getProfile() {
            int i = this.codec;
            if (i == 3) {
                return 1;
            }
            if (i == 4) {
                return 5;
            }
            if (i == 5) {
                return 39;
            }
            return this.profile;
        }

        AudioProfile(int i, int i2, int i3, int i4, int i5) {
            this.codec = i;
            this.channels = i2;
            this.sampleRate = i3;
            this.bitrate = i4;
            this.profile = i5;
        }
    }

    EncoderProfiles(int i, int i2, VideoProfile[] videoProfileArr, AudioProfile[] audioProfileArr) {
        this.durationSecs = i;
        this.fileFormat = i2;
        this.videoProfiles = Collections.unmodifiableList(Arrays.asList(videoProfileArr));
        this.audioProfiles = Collections.unmodifiableList(Arrays.asList(audioProfileArr));
    }
}
