package com.android.server.media.metrics;

import android.content.Context;
import android.hardware.DataSpace;
import android.media.MediaMetrics;
import android.media.metrics.IMediaMetricsManager;
import android.media.metrics.MediaItemInfo;
import android.media.metrics.NetworkEvent;
import android.media.metrics.PlaybackErrorEvent;
import android.media.metrics.PlaybackMetrics;
import android.media.metrics.PlaybackStateEvent;
import android.media.metrics.TrackChangeEvent;
import android.os.Binder;
import android.os.PersistableBundle;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Size;
import android.util.Slog;
import android.util.StatsEvent;
import android.util.StatsLog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class MediaMetricsManagerService extends SystemService {
    public static final MediaItemInfo EMPTY_MEDIA_ITEM_INFO = new MediaItemInfo.Builder().build();
    public static final Pattern PATTERN_KNOWN_EDITING_LIBRARY_NAMES =
            Pattern.compile(
                    "androidx\\.media3:media3-(transformer|muxer):[\\d.]+(-(alpha|beta|rc)\\d\\d)?");
    public List mAllowlist;
    public List mBlockList;
    public final Context mContext;
    public final Object mLock;
    public Integer mMode;
    public List mNoUidAllowlist;
    public List mNoUidBlocklist;
    public final SecureRandom mSecureRandom;

    public final class BinderService extends IMediaMetricsManager.Stub {
        public BinderService() {}

        /* JADX WARN: Code restructure failed: missing block: B:22:0x003b, code lost:

           if (r9.equals("player_metrics_app_allowlist") == false) goto L11;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.lang.Integer loggingLevelInternal(
                java.lang.String[] r7, java.util.List r8, java.lang.String r9) {
            /*
                r0 = 1
                r1 = 0
                int r2 = r7.length
                r3 = r1
            L4:
                if (r3 >= r2) goto L59
                r4 = r7[r3]
                java.util.Iterator r5 = r8.iterator()
            Lc:
                boolean r6 = r5.hasNext()
                if (r6 == 0) goto L57
                java.lang.Object r6 = r5.next()
                java.lang.String r6 = (java.lang.String) r6
                boolean r6 = r4.equals(r6)
                if (r6 == 0) goto Lc
                r7 = -1
                int r8 = r9.hashCode()
                switch(r8) {
                    case -1894232751: goto L3e;
                    case -1289480849: goto L34;
                    case 1900310029: goto L28;
                    default: goto L26;
                }
            L26:
                r0 = r7
                goto L49
            L28:
                java.lang.String r8 = "player_metrics_per_app_attribution_allowlist"
                boolean r8 = r9.equals(r8)
                if (r8 != 0) goto L32
                goto L26
            L32:
                r0 = 2
                goto L49
            L34:
                java.lang.String r8 = "player_metrics_app_allowlist"
                boolean r8 = r9.equals(r8)
                if (r8 != 0) goto L49
                goto L26
            L3e:
                java.lang.String r8 = "player_metrics_per_app_attribution_blocklist"
                boolean r8 = r9.equals(r8)
                if (r8 != 0) goto L48
                goto L26
            L48:
                r0 = r1
            L49:
                switch(r0) {
                    case 0: goto L50;
                    case 1: goto L52;
                    case 2: goto L50;
                    default: goto L4c;
                }
            L4c:
                r1 = 99999(0x1869f, float:1.40128E-40)
                goto L52
            L50:
                r1 = 1000(0x3e8, float:1.401E-42)
            L52:
                java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
                return r7
            L57:
                int r3 = r3 + r0
                goto L4
            L59:
                r7 = 0
                return r7
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.metrics.MediaMetricsManagerService.BinderService.loggingLevelInternal(java.lang.String[],"
                        + " java.util.List, java.lang.String):java.lang.Integer");
        }

        public final String getBundleSessionId(int i) {
            return getSessionIdInternal();
        }

        public final String getEditingSessionId(int i) {
            return getSessionIdInternal();
        }

        public final String getPlaybackSessionId(int i) {
            return getSessionIdInternal();
        }

        public final String getRecordingSessionId(int i) {
            return getSessionIdInternal();
        }

        public final String getSessionIdInternal() {
            byte[] bArr = new byte[12];
            MediaMetricsManagerService.this.mSecureRandom.nextBytes(bArr);
            String encodeToString = Base64.encodeToString(bArr, 11);
            new MediaMetrics.Item("metrics.manager")
                    .set(MediaMetrics.Property.EVENT, "create")
                    .set(MediaMetrics.Property.LOG_SESSION_ID, encodeToString)
                    .record();
            return encodeToString;
        }

        public final String getTranscodingSessionId(int i) {
            return getSessionIdInternal();
        }

        public final int loggingLevel() {
            synchronized (MediaMetricsManagerService.this.mLock) {
                try {
                    int callingUid = Binder.getCallingUid();
                    if (MediaMetricsManagerService.this.mMode == null) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            MediaMetricsManagerService.this.mMode =
                                    Integer.valueOf(
                                            DeviceConfig.getInt("media", "media_metrics_mode", 2));
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    if (MediaMetricsManagerService.this.mMode.intValue() == 1) {
                        return 0;
                    }
                    if (MediaMetricsManagerService.this.mMode.intValue() == 0) {
                        Slog.v(
                                "MediaMetricsManagerService",
                                "Logging level blocked: MEDIA_METRICS_MODE_OFF");
                        return 99999;
                    }
                    String[] packagesForUid =
                            MediaMetricsManagerService.this
                                    .getContext()
                                    .getPackageManager()
                                    .getPackagesForUid(callingUid);
                    if (packagesForUid != null && packagesForUid.length != 0) {
                        if (MediaMetricsManagerService.this.mMode.intValue() == 2) {
                            MediaMetricsManagerService mediaMetricsManagerService =
                                    MediaMetricsManagerService.this;
                            if (mediaMetricsManagerService.mBlockList == null) {
                                mediaMetricsManagerService.mBlockList =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_app_blocklist");
                                if (MediaMetricsManagerService.this.mBlockList == null) {
                                    Slog.v(
                                            "MediaMetricsManagerService",
                                            "Logging level blocked: Failed to get"
                                                + " PLAYER_METRICS_APP_BLOCKLIST.");
                                    return 99999;
                                }
                            }
                            Integer loggingLevelInternal =
                                    loggingLevelInternal(
                                            packagesForUid,
                                            MediaMetricsManagerService.this.mBlockList,
                                            "player_metrics_app_blocklist");
                            if (loggingLevelInternal != null) {
                                return loggingLevelInternal.intValue();
                            }
                            MediaMetricsManagerService mediaMetricsManagerService2 =
                                    MediaMetricsManagerService.this;
                            if (mediaMetricsManagerService2.mNoUidBlocklist == null) {
                                mediaMetricsManagerService2.mNoUidBlocklist =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_per_app_attribution_blocklist");
                                if (MediaMetricsManagerService.this.mNoUidBlocklist == null) {
                                    Slog.v(
                                            "MediaMetricsManagerService",
                                            "Logging level blocked: Failed to get"
                                                + " PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST.");
                                    return 99999;
                                }
                            }
                            Integer loggingLevelInternal2 =
                                    loggingLevelInternal(
                                            packagesForUid,
                                            MediaMetricsManagerService.this.mNoUidBlocklist,
                                            "player_metrics_per_app_attribution_blocklist");
                            if (loggingLevelInternal2 != null) {
                                return loggingLevelInternal2.intValue();
                            }
                            return 0;
                        }
                        if (MediaMetricsManagerService.this.mMode.intValue() != 3) {
                            Slog.v(
                                    "MediaMetricsManagerService",
                                    "Logging level blocked: Blocked by default.");
                            return 99999;
                        }
                        MediaMetricsManagerService mediaMetricsManagerService3 =
                                MediaMetricsManagerService.this;
                        if (mediaMetricsManagerService3.mNoUidAllowlist == null) {
                            mediaMetricsManagerService3.mNoUidAllowlist =
                                    MediaMetricsManagerService.getListLocked(
                                            "player_metrics_per_app_attribution_allowlist");
                            if (MediaMetricsManagerService.this.mNoUidAllowlist == null) {
                                Slog.v(
                                        "MediaMetricsManagerService",
                                        "Logging level blocked: Failed to get"
                                            + " PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST.");
                                return 99999;
                            }
                        }
                        Integer loggingLevelInternal3 =
                                loggingLevelInternal(
                                        packagesForUid,
                                        MediaMetricsManagerService.this.mNoUidAllowlist,
                                        "player_metrics_per_app_attribution_allowlist");
                        if (loggingLevelInternal3 != null) {
                            return loggingLevelInternal3.intValue();
                        }
                        MediaMetricsManagerService mediaMetricsManagerService4 =
                                MediaMetricsManagerService.this;
                        if (mediaMetricsManagerService4.mAllowlist == null) {
                            mediaMetricsManagerService4.mAllowlist =
                                    MediaMetricsManagerService.getListLocked(
                                            "player_metrics_app_allowlist");
                            if (MediaMetricsManagerService.this.mAllowlist == null) {
                                Slog.v(
                                        "MediaMetricsManagerService",
                                        "Logging level blocked: Failed to get"
                                            + " PLAYER_METRICS_APP_ALLOWLIST.");
                                return 99999;
                            }
                        }
                        Integer loggingLevelInternal4 =
                                loggingLevelInternal(
                                        packagesForUid,
                                        MediaMetricsManagerService.this.mAllowlist,
                                        "player_metrics_app_allowlist");
                        if (loggingLevelInternal4 != null) {
                            return loggingLevelInternal4.intValue();
                        }
                        Slog.v(
                                "MediaMetricsManagerService",
                                "Logging level blocked: Not detected in any allowlist.");
                        return 99999;
                    }
                    Slog.d("MediaMetricsManagerService", "empty package from uid " + callingUid);
                    return MediaMetricsManagerService.this.mMode.intValue() == 2 ? 1000 : 99999;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void releaseSessionId(String str, int i) {
            Slog.v(
                    "MediaMetricsManagerService",
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            i, "Releasing sessionId ", str, " for userId ", " [NOP]"));
        }

        public final void reportBundleMetrics(
                String str, PersistableBundle persistableBundle, int i) {
            if (loggingLevel() != 99999
                    && persistableBundle.getInt("bundlesession-statsd-atom") == 322) {
                String string = persistableBundle.getString("playbackstateevent-sessionid");
                int i2 = persistableBundle.getInt("playbackstateevent-state", -1);
                long j = persistableBundle.getLong("playbackstateevent-lifetime", -1L);
                if (string == null || i2 < 0 || j < 0) {
                    BatteryService$$ExternalSyntheticOutline0.m(
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "dropping incomplete data for atom 322: _sessionId: ",
                                    string,
                                    " _state: ",
                                    " _lifetime: "),
                            j,
                            "MediaMetricsManagerService");
                } else {
                    StatsLog.write(
                            StatsEvent.newBuilder()
                                    .setAtomId(322)
                                    .writeString(string)
                                    .writeInt(i2)
                                    .writeLong(j)
                                    .usePooledBuffer()
                                    .build());
                }
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void reportEditingEndedEvent(
                java.lang.String r51, android.media.metrics.EditingEndedEvent r52, int r53) {
            /*
                Method dump skipped, instructions count: 1598
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.metrics.MediaMetricsManagerService.BinderService.reportEditingEndedEvent(java.lang.String,"
                        + " android.media.metrics.EditingEndedEvent, int):void");
        }

        public final void reportNetworkEvent(String str, NetworkEvent networkEvent, int i) {
            if (loggingLevel() == 99999) {
                return;
            }
            StatsLog.write(
                    StatsEvent.newBuilder()
                            .setAtomId(321)
                            .writeString(str)
                            .writeInt(networkEvent.getNetworkType())
                            .writeLong(networkEvent.getTimeSinceCreatedMillis())
                            .usePooledBuffer()
                            .build());
        }

        public final void reportPlaybackErrorEvent(
                String str, PlaybackErrorEvent playbackErrorEvent, int i) {
            if (loggingLevel() == 99999) {
                return;
            }
            StatsLog.write(
                    StatsEvent.newBuilder()
                            .setAtomId(323)
                            .writeString(str)
                            .writeString(playbackErrorEvent.getExceptionStack())
                            .writeInt(playbackErrorEvent.getErrorCode())
                            .writeInt(playbackErrorEvent.getSubErrorCode())
                            .writeLong(playbackErrorEvent.getTimeSinceCreatedMillis())
                            .usePooledBuffer()
                            .build());
        }

        public final void reportPlaybackMetrics(
                String str, PlaybackMetrics playbackMetrics, int i) {
            int loggingLevel = loggingLevel();
            if (loggingLevel == 99999) {
                return;
            }
            StatsLog.write(
                    StatsEvent.newBuilder()
                            .setAtomId(320)
                            .writeInt(loggingLevel == 0 ? Binder.getCallingUid() : 0)
                            .writeString(str)
                            .writeLong(playbackMetrics.getMediaDurationMillis())
                            .writeInt(playbackMetrics.getStreamSource())
                            .writeInt(playbackMetrics.getStreamType())
                            .writeInt(playbackMetrics.getPlaybackType())
                            .writeInt(playbackMetrics.getDrmType())
                            .writeInt(playbackMetrics.getContentType())
                            .writeString(playbackMetrics.getPlayerName())
                            .writeString(playbackMetrics.getPlayerVersion())
                            .writeByteArray(new byte[0])
                            .writeInt(playbackMetrics.getVideoFramesPlayed())
                            .writeInt(playbackMetrics.getVideoFramesDropped())
                            .writeInt(playbackMetrics.getAudioUnderrunCount())
                            .writeLong(playbackMetrics.getNetworkBytesRead())
                            .writeLong(playbackMetrics.getLocalBytesRead())
                            .writeLong(playbackMetrics.getNetworkTransferDurationMillis())
                            .writeString(
                                    Base64.encodeToString(playbackMetrics.getDrmSessionId(), 0))
                            .usePooledBuffer()
                            .build());
        }

        public final void reportPlaybackStateEvent(
                String str, PlaybackStateEvent playbackStateEvent, int i) {
            if (loggingLevel() == 99999) {
                return;
            }
            StatsLog.write(
                    StatsEvent.newBuilder()
                            .setAtomId(322)
                            .writeString(str)
                            .writeInt(playbackStateEvent.getState())
                            .writeLong(playbackStateEvent.getTimeSinceCreatedMillis())
                            .usePooledBuffer()
                            .build());
        }

        public final void reportTrackChangeEvent(
                String str, TrackChangeEvent trackChangeEvent, int i) {
            if (loggingLevel() == 99999) {
                return;
            }
            StatsLog.write(
                    StatsEvent.newBuilder()
                            .setAtomId(
                                    FrameworkStatsLog
                                            .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN)
                            .writeString(str)
                            .writeInt(trackChangeEvent.getTrackState())
                            .writeInt(trackChangeEvent.getTrackChangeReason())
                            .writeString(trackChangeEvent.getContainerMimeType())
                            .writeString(trackChangeEvent.getSampleMimeType())
                            .writeString(trackChangeEvent.getCodecName())
                            .writeInt(trackChangeEvent.getBitrate())
                            .writeLong(trackChangeEvent.getTimeSinceCreatedMillis())
                            .writeInt(trackChangeEvent.getTrackType())
                            .writeString(trackChangeEvent.getLanguage())
                            .writeString(trackChangeEvent.getLanguageRegion())
                            .writeInt(trackChangeEvent.getChannelCount())
                            .writeInt(trackChangeEvent.getAudioSampleRate())
                            .writeInt(trackChangeEvent.getWidth())
                            .writeInt(trackChangeEvent.getHeight())
                            .writeFloat(trackChangeEvent.getVideoFrameRate())
                            .usePooledBuffer()
                            .build());
        }
    }

    /* renamed from: -$$Nest$smgetBucketedDurationMillis, reason: not valid java name */
    public static long m672$$Nest$smgetBucketedDurationMillis(long j) {
        if (j == -1 || j <= 0) {
            return -1L;
        }
        return (long)
                Math.ceil(
                        Math.pow(
                                        2.0d,
                                        Math.min(
                                                        13,
                                                        Math.max(
                                                                0,
                                                                (int)
                                                                        Math.floor(
                                                                                (Math.log(
                                                                                                        (j
                                                                                                                        + 1)
                                                                                                                / 60000.0d)
                                                                                                / Math
                                                                                                        .log(
                                                                                                                2.0d))
                                                                                        + 8.0d)))
                                                - 8)
                                * 60000.0d);
    }

    /* renamed from: -$$Nest$smgetFilteredFirstMimeType, reason: not valid java name */
    public static String m673$$Nest$smgetFilteredFirstMimeType(String str, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String str2 = (String) list.get(i);
            if (str2.startsWith(str)) {
                return getFilteredMimeType(str2);
            }
        }
        return "";
    }

    /* renamed from: -$$Nest$smgetVideoFrameRateEnum, reason: not valid java name */
    public static int m674$$Nest$smgetVideoFrameRateEnum(float f) {
        int round = Math.round(f);
        if (round == 24) {
            return 2400;
        }
        if (round == 25) {
            return 2500;
        }
        if (round == 30) {
            return 3000;
        }
        if (round == 50) {
            return 5000;
        }
        if (round == 60) {
            return 6000;
        }
        if (round == 120) {
            return 12000;
        }
        if (round == 240) {
            return 24000;
        }
        if (round != 480) {
            return round != 960 ? 0 : 96000;
        }
        return 48000;
    }

    /* renamed from: -$$Nest$smgetVideoHdrFormatEnum, reason: not valid java name */
    public static int m675$$Nest$smgetVideoHdrFormatEnum(int i, String str) {
        if (i == 0) {
            return 0;
        }
        if (str.equals("video/dolby-vision")) {
            return 5;
        }
        int standard = DataSpace.getStandard(i);
        int transfer = DataSpace.getTransfer(i);
        if (standard == 393216 && transfer == 33554432) {
            return 2;
        }
        return (standard == 393216 && transfer == 29360128) ? 3 : 1;
    }

    /* renamed from: -$$Nest$smgetVideoResolutionEnum, reason: not valid java name */
    public static int m676$$Nest$smgetVideoResolutionEnum(Size size) {
        int width = size.getWidth();
        int height = size.getHeight();
        if (width == 352 && height == 640) {
            return 228;
        }
        if (width == 360 && height == 640) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_360X640;
        }
        if (width == 480 && height == 640) {
            return 311;
        }
        if (width == 480 && height == 854) {
            return 414;
        }
        if (width == 540 && height == 960) {
            return 524;
        }
        if (width == 576 && height == 1024) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_576X1024;
        }
        if (width == 1280 && height == 720) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_720P_HD;
        }
        if (width == 1920 && height == 1080) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080P_FHD;
        }
        if (width == 1440 && height == 2560) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1440X2560;
        }
        if (width == 3840 && height == 2160) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_4K_UHD;
        }
        if (width == 7680 && height == 4320) {
            return FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_8K_UHD;
        }
        return 0;
    }

    public MediaMetricsManagerService(Context context) {
        super(context);
        this.mMode = null;
        this.mAllowlist = null;
        this.mNoUidAllowlist = null;
        this.mBlockList = null;
        this.mNoUidBlocklist = null;
        this.mLock = new Object();
        this.mContext = context;
        this.mSecureRandom = new SecureRandom();
    }

    public static String getFilteredMimeType(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case -2123537834:
                if (str.equals("audio/eac3-joc")) {
                    c = 0;
                    break;
                }
                break;
            case -1851077871:
                if (str.equals("video/dolby-vision")) {
                    c = 1;
                    break;
                }
                break;
            case -1664118616:
                if (str.equals("video/3gpp")) {
                    c = 2;
                    break;
                }
                break;
            case -1662735862:
                if (str.equals("video/av01")) {
                    c = 3;
                    break;
                }
                break;
            case -1662541442:
                if (str.equals("video/hevc")) {
                    c = 4;
                    break;
                }
                break;
            case -1662384007:
                if (str.equals("video/mp2t")) {
                    c = 5;
                    break;
                }
                break;
            case -1662382439:
                if (str.equals("video/mpeg")) {
                    c = 6;
                    break;
                }
                break;
            case -1662095187:
                if (str.equals("video/webm")) {
                    c = 7;
                    break;
                }
                break;
            case -1248337486:
                if (str.equals("application/mp4")) {
                    c = '\b';
                    break;
                }
                break;
            case -1095064472:
                if (str.equals("audio/vnd.dts")) {
                    c = '\t';
                    break;
                }
                break;
            case -1003765268:
                if (str.equals("audio/vorbis")) {
                    c = '\n';
                    break;
                }
                break;
            case -979127466:
                if (str.equals("application/x-mpegURL")) {
                    c = 11;
                    break;
                }
                break;
            case -432837260:
                if (str.equals("audio/mpeg-L1")) {
                    c = '\f';
                    break;
                }
                break;
            case -432837259:
                if (str.equals("audio/mpeg-L2")) {
                    c = '\r';
                    break;
                }
                break;
            case -387023398:
                if (str.equals("audio/x-matroska")) {
                    c = 14;
                    break;
                }
                break;
            case -275430368:
                if (str.equals("application/x-matroska")) {
                    c = 15;
                    break;
                }
                break;
            case -156749520:
                if (str.equals("application/vnd.ms-sstr+xml")) {
                    c = 16;
                    break;
                }
                break;
            case -53558318:
                if (str.equals("audio/mp4a-latm")) {
                    c = 17;
                    break;
                }
                break;
            case -43467528:
                if (str.equals("application/webm")) {
                    c = 18;
                    break;
                }
                break;
            case 13915911:
                if (str.equals("video/x-flv")) {
                    c = 19;
                    break;
                }
                break;
            case 64194685:
                if (str.equals("application/dash+xml")) {
                    c = 20;
                    break;
                }
                break;
            case 187078296:
                if (str.equals("audio/ac3")) {
                    c = 21;
                    break;
                }
                break;
            case 187078886:
                if (str.equals("audio/av4")) {
                    c = 22;
                    break;
                }
                break;
            case 187090232:
                if (str.equals("audio/mp4")) {
                    c = 23;
                    break;
                }
                break;
            case 187091926:
                if (str.equals("audio/ogg")) {
                    c = 24;
                    break;
                }
                break;
            case 187094639:
                if (str.equals("audio/raw")) {
                    c = 25;
                    break;
                }
                break;
            case 187099443:
                if (str.equals("audio/wav")) {
                    c = 26;
                    break;
                }
                break;
            case 1187890754:
                if (str.equals("video/mp4v-es")) {
                    c = 27;
                    break;
                }
                break;
            case 1331836730:
                if (str.equals("video/avc")) {
                    c = 28;
                    break;
                }
                break;
            case 1331848029:
                if (str.equals("video/mp4")) {
                    c = 29;
                    break;
                }
                break;
            case 1331852436:
                if (str.equals("video/raw")) {
                    c = 30;
                    break;
                }
                break;
            case 1504578661:
                if (str.equals("audio/eac3")) {
                    c = 31;
                    break;
                }
                break;
            case 1504619009:
                if (str.equals("audio/flac")) {
                    c = ' ';
                    break;
                }
                break;
            case 1504824762:
                if (str.equals("audio/midi")) {
                    c = '!';
                    break;
                }
                break;
            case 1504831518:
                if (str.equals("audio/mpeg")) {
                    c = '\"';
                    break;
                }
                break;
            case 1504891608:
                if (str.equals("audio/opus")) {
                    c = '#';
                    break;
                }
                break;
            case 1505118770:
                if (str.equals("audio/webm")) {
                    c = '$';
                    break;
                }
                break;
            case 1505942594:
                if (str.equals("audio/vnd.dts.hd")) {
                    c = '%';
                    break;
                }
                break;
            case 1556697186:
                if (str.equals("audio/true-hd")) {
                    c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                    break;
                }
                break;
            case 1599127256:
                if (str.equals("video/x-vnd.on2.vp8")) {
                    c = '\'';
                    break;
                }
                break;
            case 1599127257:
                if (str.equals("video/x-vnd.on2.vp9")) {
                    c = '(';
                    break;
                }
                break;
            case 2039520277:
                if (str.equals("video/x-matroska")) {
                    c = ')';
                    break;
                }
                break;
        }
        switch (c) {
        }
        return "";
    }

    public static List getListLocked(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String string = DeviceConfig.getString("media", str, "failed_to_get");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!string.equals("failed_to_get")) {
                return Arrays.asList(string.split(","));
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "failed to get ", str, " from DeviceConfig", "MediaMetricsManagerService");
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("media_metrics", new BinderService());
        DeviceConfig.addOnPropertiesChangedListener(
                "media",
                this.mContext.getMainExecutor(),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.media.metrics.MediaMetricsManagerService$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        List listLocked;
                        List listLocked2;
                        List listLocked3;
                        MediaMetricsManagerService mediaMetricsManagerService =
                                MediaMetricsManagerService.this;
                        synchronized (mediaMetricsManagerService.mLock) {
                            try {
                                mediaMetricsManagerService.mMode =
                                        Integer.valueOf(properties.getInt("media_metrics_mode", 2));
                                List listLocked4 =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_app_allowlist");
                                if (listLocked4 == null) {
                                    if (mediaMetricsManagerService.mMode.intValue() != 3) {}
                                    listLocked =
                                            MediaMetricsManagerService.getListLocked(
                                                    "player_metrics_per_app_attribution_allowlist");
                                    if (listLocked == null
                                            || mediaMetricsManagerService.mMode.intValue() != 3) {
                                        mediaMetricsManagerService.mNoUidAllowlist = listLocked;
                                    }
                                    listLocked2 =
                                            MediaMetricsManagerService.getListLocked(
                                                    "player_metrics_app_blocklist");
                                    if (listLocked2 == null
                                            || mediaMetricsManagerService.mMode.intValue() != 2) {
                                        mediaMetricsManagerService.mBlockList = listLocked2;
                                    }
                                    listLocked3 =
                                            MediaMetricsManagerService.getListLocked(
                                                    "player_metrics_per_app_attribution_blocklist");
                                    if (listLocked3 == null
                                            || mediaMetricsManagerService.mMode.intValue() != 2) {
                                        mediaMetricsManagerService.mNoUidBlocklist = listLocked3;
                                    }
                                }
                                mediaMetricsManagerService.mAllowlist = listLocked4;
                                listLocked =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_per_app_attribution_allowlist");
                                if (listLocked == null) {}
                                mediaMetricsManagerService.mNoUidAllowlist = listLocked;
                                listLocked2 =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_app_blocklist");
                                if (listLocked2 == null) {}
                                mediaMetricsManagerService.mBlockList = listLocked2;
                                listLocked3 =
                                        MediaMetricsManagerService.getListLocked(
                                                "player_metrics_per_app_attribution_blocklist");
                                if (listLocked3 == null) {}
                                mediaMetricsManagerService.mNoUidBlocklist = listLocked3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
    }
}
