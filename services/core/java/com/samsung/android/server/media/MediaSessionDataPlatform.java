package com.samsung.android.server.media;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.media.MediaSessionRecord;
import com.android.server.utils.EventLogger;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.server.audio.AudioExecutor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MediaSessionDataPlatform {
    public static ContentResolver mCr;
    public static MediaSessionDataPlatform sInstance;
    public SemMediaResourceHelper mSemMediaResourceHelper;
    public final Uri mUri;
    public final ConcurrentHashMap mPlayList = new ConcurrentHashMap();
    public final Handler mHandler = new Handler();
    public final EventLogger mEventLogger = new EventLogger(50, "MediaSessionDataPlatform logger");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ListeningToMusic {
        public String album;
        public String albumArtUri;
        public String albumArtist;
        public String artUri;
        public String artist;
        public String author;
        public long btFolderType;
        public String castType;
        public String compilation;
        public String composer;
        public String date;
        public long discNumber;
        public String displayDescription;
        public String displayIconUri;
        public String displaySubtitle;
        public String displayTitle;
        public long duration;
        public long endTime;
        public String genre;
        public String mediaId;
        public String mediaType;
        public String mediaUri;
        public long numTracks;
        public String packageName;
        public int playbackState;
        public long playingTime;
        public long startTime;
        public String title;
        public long trackNumber;
        public String writer;
        public long year;

        public final ContentValues makeContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("startTime", Long.valueOf(this.startTime));
            contentValues.put("endTime", Long.valueOf(this.endTime));
            contentValues.put("packageName", this.packageName);
            contentValues.put("mediaType", this.mediaType);
            contentValues.put("castType", this.castType);
            contentValues.put("album", this.album);
            contentValues.put("albumArtist", this.albumArtist);
            contentValues.put("albumArtUri", this.albumArtUri);
            contentValues.put("artist", this.artist);
            contentValues.put("artUri", this.artUri);
            contentValues.put("author", this.author);
            contentValues.put("btFolderType", Long.valueOf(this.btFolderType));
            contentValues.put("compilation", this.compilation);
            contentValues.put("composer", this.composer);
            contentValues.put("date", this.date);
            contentValues.put("discNumber", Long.valueOf(this.discNumber));
            contentValues.put("displayDescription", this.displayDescription);
            contentValues.put("displayIconUri", this.displayIconUri);
            contentValues.put("displaySubtitle", this.displaySubtitle);
            contentValues.put("displayTitle", this.displayTitle);
            contentValues.put("duration", Long.valueOf(this.duration));
            contentValues.put("genre", this.genre);
            contentValues.put("mediaId", this.mediaId);
            contentValues.put("mediaUri", this.mediaUri);
            contentValues.put("numTracks", Long.valueOf(this.numTracks));
            contentValues.put(KnoxCustomManagerService.SHORTCUT_TITLE, this.title);
            contentValues.put("trackNumber", Long.valueOf(this.trackNumber));
            contentValues.put("writer", this.writer);
            contentValues.put("year", Long.valueOf(this.year));
            return contentValues;
        }

        public final String toString() {
            return "packageName:"
                    + this.packageName
                    + ", title:"
                    + this.title
                    + ", artist:"
                    + this.artist
                    + ", album:"
                    + this.album
                    + ", genre:"
                    + this.genre
                    + ", mediaType:"
                    + this.mediaType
                    + ", castType:"
                    + this.castType
                    + ", startTime:"
                    + this.startTime
                    + ", endTime:"
                    + this.endTime
                    + ", playingTime:"
                    + this.playingTime
                    + ", duration: "
                    + this.duration;
        }
    }

    public MediaSessionDataPlatform(Context context) {
        this.mSemMediaResourceHelper = null;
        mCr = context.getContentResolver();
        this.mUri =
                Uri.parse(
                        "content://com.samsung.android.moneta.datacollector.reception.external.data/media_history");
        Log.i("MediaSessionDataPlatform", "createMediaResourceHelper");
        try {
            this.mSemMediaResourceHelper = SemMediaResourceHelper.createInstance(2, false);
        } catch (IllegalStateException unused) {
            Log.i("MediaSessionDataPlatform", "IllegalStateException SemMediaResourceHelper");
        }
    }

    public static boolean compareString(String str, String str2) {
        return str != null ? (str.equals(str2) || str2 == null) ? false : true : str2 != null;
    }

    public static int isMetadataChanged(
            ListeningToMusic listeningToMusic, MediaMetadata mediaMetadata) {
        String str;
        if (listeningToMusic == null || (str = listeningToMusic.title) == null) {
            return mediaMetadata != null ? 1 : 0;
        }
        if (mediaMetadata != null) {
            if (compareString(str, mediaMetadata.getString("android.media.metadata.TITLE"))) {
                return 1;
            }
            String str2 = listeningToMusic.albumArtist;
            if (str2 == null || str2.isEmpty()) {
                str2 = listeningToMusic.artist;
            }
            String string = mediaMetadata.getString("android.media.metadata.ALBUM_ARTIST");
            if (string == null || string.isEmpty()) {
                string = mediaMetadata.getString("android.media.metadata.ARTIST");
            }
            if (compareString(str2, string)) {
                return 2;
            }
            if (compareString(
                    listeningToMusic.album,
                    mediaMetadata.getString("android.media.metadata.ALBUM"))) {
                return 3;
            }
            if (mediaMetadata.getLong("android.media.metadata.DURATION") > 0
                    && listeningToMusic.duration
                            != mediaMetadata.getLong("android.media.metadata.DURATION")) {
                return 4;
            }
        }
        return 0;
    }

    public final String getMediaType(int i) {
        EventLogger eventLogger = this.mEventLogger;
        try {
            ArrayList mediaResourceInfo = this.mSemMediaResourceHelper.getMediaResourceInfo(2);
            if (mediaResourceInfo != null) {
                Iterator it = mediaResourceInfo.iterator();
                while (it.hasNext()) {
                    SemMediaResourceHelper.MediaResourceInfo mediaResourceInfo2 =
                            (SemMediaResourceHelper.MediaResourceInfo) it.next();
                    boolean isEncoder = mediaResourceInfo2.isEncoder();
                    int uidForPid = Process.getUidForPid(mediaResourceInfo2.getPid());
                    if (!isEncoder && uidForPid == i) {
                        EventLogger.StringEvent stringEvent =
                                new EventLogger.StringEvent(
                                        "getMediaType for uid: " + i + " Video");
                        stringEvent.printLog(0, "MediaSessionDataPlatform");
                        eventLogger.enqueue(stringEvent);
                        return "Video";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SemMediaResourceHelper semMediaResourceHelper = this.mSemMediaResourceHelper;
            if (semMediaResourceHelper != null) {
                semMediaResourceHelper.release();
            }
            Log.i("MediaSessionDataPlatform", "createMediaResourceHelper");
            try {
                this.mSemMediaResourceHelper = SemMediaResourceHelper.createInstance(2, false);
            } catch (IllegalStateException unused) {
                Log.i("MediaSessionDataPlatform", "IllegalStateException SemMediaResourceHelper");
            }
        }
        EventLogger.StringEvent stringEvent2 =
                new EventLogger.StringEvent(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "getMediaType for uid: ", " Audio"));
        stringEvent2.printLog(0, "MediaSessionDataPlatform");
        eventLogger.enqueue(stringEvent2);
        return "Audio";
    }

    public final void sendData(MediaSessionRecord mediaSessionRecord) {
        final ListeningToMusic listeningToMusic =
                (ListeningToMusic)
                        this.mPlayList.get(Integer.valueOf(mediaSessionRecord.mOwnerUid));
        EventLogger eventLogger = this.mEventLogger;
        if (listeningToMusic == null) {
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent("Failed: insert data is null");
            stringEvent.printLog(0, "MediaSessionDataPlatform");
            eventLogger.enqueue(stringEvent);
            return;
        }
        long epochMilli = Instant.now().toEpochMilli();
        listeningToMusic.endTime = epochMilli;
        long j = epochMilli - listeningToMusic.startTime;
        listeningToMusic.playingTime = j;
        if (j >= 59500) {
            AudioExecutor.execute(
                    new Runnable() { // from class:
                                     // com.samsung.android.server.media.MediaSessionDataPlatform$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaSessionDataPlatform mediaSessionDataPlatform =
                                    MediaSessionDataPlatform.this;
                            MediaSessionDataPlatform.ListeningToMusic listeningToMusic2 =
                                    listeningToMusic;
                            mediaSessionDataPlatform.getClass();
                            try {
                                Uri insert =
                                        MediaSessionDataPlatform.mCr.insert(
                                                mediaSessionDataPlatform.mUri,
                                                listeningToMusic2.makeContentValues());
                                EventLogger eventLogger2 = mediaSessionDataPlatform.mEventLogger;
                                if (insert != null) {
                                    EventLogger.StringEvent stringEvent2 =
                                            new EventLogger.StringEvent(
                                                    "Succeeded: insert " + listeningToMusic2);
                                    stringEvent2.printLog(0, "MediaSessionDataPlatform");
                                    eventLogger2.enqueue(stringEvent2);
                                } else {
                                    EventLogger.StringEvent stringEvent3 =
                                            new EventLogger.StringEvent(
                                                    "Failed: insert " + listeningToMusic2);
                                    stringEvent3.printLog(0, "MediaSessionDataPlatform");
                                    eventLogger2.enqueue(stringEvent3);
                                }
                            } catch (Exception e) {
                                Log.w("MediaSessionDataPlatform", e.getMessage());
                            }
                        }
                    });
            return;
        }
        EventLogger.StringEvent stringEvent2 =
                new EventLogger.StringEvent(
                        AudioConfig$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Do not send data because playing time too short "),
                                listeningToMusic.playingTime,
                                "ms"));
        stringEvent2.printLog(0, "MediaSessionDataPlatform");
        eventLogger.enqueue(stringEvent2);
    }
}
