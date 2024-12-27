package com.android.server.media;

import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MediaSessionStack {
    public static final boolean DEBUG;
    public final AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    public MediaSessionRecordImpl mMediaButtonSession;
    public final MediaSessionService.FullUserRecord mOnMediaButtonSessionChangedListener;
    public final List mSessions = new ArrayList();
    public final SparseArray mCachedActiveLists = new SparseArray();
    public boolean mIsMultiSoundOn = false;
    public final List mPrevPlayedUids = new ArrayList();

    static {
        int i = MediaSessionService.LONG_PRESS_TIMEOUT;
        DEBUG = true;
    }

    public MediaSessionStack(
            AudioPlayerStateMonitor audioPlayerStateMonitor,
            MediaSessionService.FullUserRecord fullUserRecord) {
        this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
        this.mOnMediaButtonSessionChangedListener = fullUserRecord;
    }

    public final void addSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
        Slog.i(
                "MediaSessionStack",
                TextUtils.formatSimple(
                        "addSession to bottom of stack | record: %s",
                        new Object[] {mediaSessionRecordImpl}));
        ((ArrayList) this.mSessions).add(mediaSessionRecordImpl);
        this.mCachedActiveLists.remove(mediaSessionRecordImpl.getUserId());
        this.mCachedActiveLists.remove(-1);
        updateMediaButtonSessionIfNeeded();
    }

    public final MediaSessionRecordImpl findMediaButtonSession(int i) {
        Iterator it = ((ArrayList) this.mSessions).iterator();
        MediaSessionRecordImpl mediaSessionRecordImpl = null;
        while (it.hasNext()) {
            MediaSessionRecordImpl mediaSessionRecordImpl2 = (MediaSessionRecordImpl) it.next();
            if (!(mediaSessionRecordImpl2 instanceof MediaSession2Record)
                    && i == mediaSessionRecordImpl2.getUid()) {
                if (mediaSessionRecordImpl2.checkPlaybackActiveState(
                        this.mAudioPlayerStateMonitor.isPlaybackActive(
                                mediaSessionRecordImpl2.getUid()))) {
                    return mediaSessionRecordImpl2;
                }
                if (mediaSessionRecordImpl == null) {
                    mediaSessionRecordImpl = mediaSessionRecordImpl2;
                }
            }
        }
        return mediaSessionRecordImpl;
    }

    public final MediaSessionRecord getDefaultVolumeSession() {
        ArrayList arrayList = (ArrayList) getPriorityList(-1, true);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) arrayList.get(i);
            if (mediaSessionRecord.checkPlaybackActiveState(true)
                    && mediaSessionRecord.canHandleVolumeKey()) {
                return mediaSessionRecord;
            }
        }
        return null;
    }

    public final List getPriorityList(int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mSessions).iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            MediaSessionRecordImpl mediaSessionRecordImpl = (MediaSessionRecordImpl) it.next();
            if (mediaSessionRecordImpl instanceof MediaSessionRecord) {
                MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaSessionRecordImpl;
                if (i == -1 || i == mediaSessionRecord.mUserId) {
                    if (mediaSessionRecord.isActive()) {
                        if (mediaSessionRecord.checkPlaybackActiveState(true)) {
                            arrayList.add(i3, mediaSessionRecord);
                            i2++;
                            i3++;
                        } else {
                            arrayList.add(i2, mediaSessionRecord);
                            i2++;
                        }
                    } else if (!z) {
                        arrayList.add(mediaSessionRecord);
                    }
                }
            }
        }
        return arrayList;
    }

    public final List getSession2Tokens(int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mSessions).iterator();
        while (it.hasNext()) {
            MediaSessionRecordImpl mediaSessionRecordImpl = (MediaSessionRecordImpl) it.next();
            if (i == -1 || mediaSessionRecordImpl.getUserId() == i) {
                if (mediaSessionRecordImpl.isActive()
                        && (mediaSessionRecordImpl instanceof MediaSession2Record)) {
                    arrayList.add(((MediaSession2Record) mediaSessionRecordImpl).mSessionToken);
                }
            }
        }
        return arrayList;
    }

    public final void onPlaybackStateChanged(
            MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
        MediaSessionRecordImpl findMediaButtonSession;
        if (z) {
            Slog.i(
                    "MediaSessionStack",
                    TextUtils.formatSimple(
                            "onPlaybackStateChanged - Pushing session to top | record: %s",
                            new Object[] {mediaSessionRecordImpl}));
            ((ArrayList) this.mSessions).remove(mediaSessionRecordImpl);
            if (!this.mIsMultiSoundOn) {
                ((ArrayList) this.mSessions).add(0, mediaSessionRecordImpl);
            } else if (mediaSessionRecordImpl.checkPlaybackActiveState(true)
                    || ((ArrayList) this.mSessions).size() <= 0
                    || !((MediaSessionRecordImpl) ((ArrayList) this.mSessions).get(0))
                            .checkPlaybackActiveState(true)) {
                ((ArrayList) this.mSessions).add(0, mediaSessionRecordImpl);
            } else {
                ((ArrayList) this.mSessions).add(1, mediaSessionRecordImpl);
            }
            this.mCachedActiveLists.remove(mediaSessionRecordImpl.getUserId());
            this.mCachedActiveLists.remove(-1);
        }
        MediaSessionRecordImpl mediaSessionRecordImpl2 = this.mMediaButtonSession;
        if (mediaSessionRecordImpl2 == null
                || mediaSessionRecordImpl2.getUid() != mediaSessionRecordImpl.getUid()
                || (findMediaButtonSession =
                                findMediaButtonSession(this.mMediaButtonSession.getUid()))
                        == this.mMediaButtonSession
                || (findMediaButtonSession.getSessionPolicies() & 2) != 0) {
            return;
        }
        updateMediaButtonSession(findMediaButtonSession);
    }

    public final void removeSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
        Slog.i(
                "MediaSessionStack",
                TextUtils.formatSimple(
                        "removeSession | record: %s", new Object[] {mediaSessionRecordImpl}));
        ((ArrayList) this.mSessions).remove(mediaSessionRecordImpl);
        if (this.mMediaButtonSession == mediaSessionRecordImpl) {
            updateMediaButtonSession(null);
        }
        this.mCachedActiveLists.remove(mediaSessionRecordImpl.getUserId());
        this.mCachedActiveLists.remove(-1);
    }

    public final void updateMediaButtonSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
        MediaSessionRecordImpl mediaSessionRecordImpl2 = this.mMediaButtonSession;
        this.mMediaButtonSession = mediaSessionRecordImpl;
        MediaSessionService.FullUserRecord fullUserRecord =
                this.mOnMediaButtonSessionChangedListener;
        fullUserRecord.getClass();
        Log.d(
                "MediaSessionService",
                "Media button session is changed to " + mediaSessionRecordImpl);
        synchronized (MediaSessionService.this.mLock) {
            if (mediaSessionRecordImpl2 != null) {
                try {
                    MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (mediaSessionRecordImpl != null) {
                fullUserRecord.rememberMediaButtonReceiverLocked(mediaSessionRecordImpl);
                MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
            }
            fullUserRecord.pushAddressedPlayerChangedLocked();
        }
    }

    public final void updateMediaButtonSessionIfNeeded() {
        String str;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("updateMediaButtonSessionIfNeeded, callers=");
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                int i2 = i + 4;
                if (i2 >= stackTrace.length) {
                    str = "<bottom of call stack>";
                } else {
                    StackTraceElement stackTraceElement = stackTrace[i2];
                    str =
                            stackTraceElement.getClassName()
                                    + "."
                                    + stackTraceElement.getMethodName()
                                    + ":"
                                    + stackTraceElement.getLineNumber();
                }
                sb2.append(str);
                sb2.append(" ");
            }
            sb.append(sb2.toString());
            Log.d("MediaSessionStack", sb.toString());
        }
        AudioPlayerStateMonitor audioPlayerStateMonitor = this.mAudioPlayerStateMonitor;
        audioPlayerStateMonitor.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (audioPlayerStateMonitor.mLock) {
            arrayList.addAll(audioPlayerStateMonitor.mSortedAudioPlaybackClientUids);
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            int intValue = ((Integer) arrayList.get(i3)).intValue();
            MediaSessionRecordImpl findMediaButtonSession = findMediaButtonSession(intValue);
            if (findMediaButtonSession != null) {
                boolean z = (findMediaButtonSession.getSessionPolicies() & 2) != 0;
                if (DEBUG) {
                    StringBuilder sb3 =
                            new StringBuilder("updateMediaButtonSessionIfNeeded, checking uid=");
                    sb3.append(intValue);
                    sb3.append(", mediaButtonSession=");
                    sb3.append(findMediaButtonSession);
                    sb3.append(", ignoreButtonSession=");
                    RCPManagerService$$ExternalSyntheticOutline0.m("MediaSessionStack", sb3, z);
                }
                if (!z) {
                    AudioPlayerStateMonitor audioPlayerStateMonitor2 =
                            this.mAudioPlayerStateMonitor;
                    int uid = findMediaButtonSession.getUid();
                    synchronized (audioPlayerStateMonitor2.mLock) {
                        try {
                            int identifier = UserHandle.getUserHandleForUid(uid).getIdentifier();
                            for (int size =
                                            ((ArrayList)
                                                                    audioPlayerStateMonitor2
                                                                            .mSortedAudioPlaybackClientUids)
                                                            .size()
                                                    - 1;
                                    size >= 0
                                            && ((Integer)
                                                                    ((ArrayList)
                                                                                    audioPlayerStateMonitor2
                                                                                            .mSortedAudioPlaybackClientUids)
                                                                            .get(size))
                                                            .intValue()
                                                    != uid;
                                    size--) {
                                int intValue2 =
                                        ((Integer)
                                                        ((ArrayList)
                                                                        audioPlayerStateMonitor2
                                                                                .mSortedAudioPlaybackClientUids)
                                                                .get(size))
                                                .intValue();
                                if (identifier
                                                == UserHandle.getUserHandleForUid(intValue2)
                                                        .getIdentifier()
                                        && !audioPlayerStateMonitor2.isPlaybackActive(intValue2)) {
                                    ((ArrayList)
                                                    audioPlayerStateMonitor2
                                                            .mSortedAudioPlaybackClientUids)
                                            .remove(size);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (findMediaButtonSession != this.mMediaButtonSession) {
                        updateMediaButtonSession(findMediaButtonSession);
                        return;
                    }
                    return;
                }
            } else if (DEBUG) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        intValue,
                        "updateMediaButtonSessionIfNeeded, skipping uid=",
                        "MediaSessionStack");
            }
        }
    }
}
