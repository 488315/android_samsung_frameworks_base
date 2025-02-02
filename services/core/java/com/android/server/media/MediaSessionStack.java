package com.android.server.media;

import android.media.session.MediaSession;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class MediaSessionStack {
  public static final boolean DEBUG = MediaSessionService.DEBUG;
  public final AudioPlayerStateMonitor mAudioPlayerStateMonitor;
  public MediaSessionRecordImpl mMediaButtonSession;
  public final OnMediaButtonSessionChangedListener mOnMediaButtonSessionChangedListener;
  public final List mSessions = new ArrayList();
  public final SparseArray mCachedActiveLists = new SparseArray();
  public boolean mIsMultiSoundOn = false;
  public final List mPrevPlayedUids = new ArrayList();

  public interface OnMediaButtonSessionChangedListener {
    void onMediaButtonSessionChanged(
        MediaSessionRecordImpl mediaSessionRecordImpl,
        MediaSessionRecordImpl mediaSessionRecordImpl2);
  }

  public MediaSessionStack(
      AudioPlayerStateMonitor audioPlayerStateMonitor,
      OnMediaButtonSessionChangedListener onMediaButtonSessionChangedListener) {
    this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
    this.mOnMediaButtonSessionChangedListener = onMediaButtonSessionChangedListener;
  }

  public void addSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
    Slog.i(
        "MediaSessionStack",
        TextUtils.formatSimple(
            "addSession to bottom of stack | record: %s", new Object[] {mediaSessionRecordImpl}));
    this.mSessions.add(mediaSessionRecordImpl);
    clearCache(mediaSessionRecordImpl.getUserId());
    updateMediaButtonSessionIfNeeded();
  }

  public void removeSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
    Slog.i(
        "MediaSessionStack",
        TextUtils.formatSimple(
            "removeSession | record: %s", new Object[] {mediaSessionRecordImpl}));
    this.mSessions.remove(mediaSessionRecordImpl);
    if (this.mMediaButtonSession == mediaSessionRecordImpl) {
      updateMediaButtonSession(null);
    }
    clearCache(mediaSessionRecordImpl.getUserId());
  }

  public boolean contains(MediaSessionRecordImpl mediaSessionRecordImpl) {
    return this.mSessions.contains(mediaSessionRecordImpl);
  }

  public MediaSessionRecord getMediaSessionRecord(MediaSession.Token token) {
    for (MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
      if (mediaSessionRecordImpl instanceof MediaSessionRecord) {
        MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaSessionRecordImpl;
        if (Objects.equals(mediaSessionRecord.getSessionToken(), token)) {
          return mediaSessionRecord;
        }
      }
    }
    return null;
  }

  public void onPlaybackStateChanged(MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
    MediaSessionRecordImpl findMediaButtonSession;
    if (z) {
      Slog.i(
          "MediaSessionStack",
          TextUtils.formatSimple(
              "onPlaybackStateChanged - Pushing session to top | record: %s",
              new Object[] {mediaSessionRecordImpl}));
      this.mSessions.remove(mediaSessionRecordImpl);
      if (this.mIsMultiSoundOn) {
        if (!mediaSessionRecordImpl.checkPlaybackActiveState(true)
            && this.mSessions.size() > 0
            && ((MediaSessionRecordImpl) this.mSessions.get(0)).checkPlaybackActiveState(true)) {
          this.mSessions.add(1, mediaSessionRecordImpl);
        } else {
          this.mSessions.add(0, mediaSessionRecordImpl);
        }
      } else {
        this.mSessions.add(0, mediaSessionRecordImpl);
      }
      clearCache(mediaSessionRecordImpl.getUserId());
    }
    MediaSessionRecordImpl mediaSessionRecordImpl2 = this.mMediaButtonSession;
    if (mediaSessionRecordImpl2 == null
        || mediaSessionRecordImpl2.getUid() != mediaSessionRecordImpl.getUid()
        || (findMediaButtonSession = findMediaButtonSession(this.mMediaButtonSession.getUid()))
            == this.mMediaButtonSession
        || (findMediaButtonSession.getSessionPolicies() & 2) != 0) {
      return;
    }
    updateMediaButtonSession(findMediaButtonSession);
  }

  public void onSessionActiveStateChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
    clearCache(mediaSessionRecordImpl.getUserId());
  }

  public void updateMediaButtonSessionIfNeeded() {
    if (DEBUG) {
      Log.d("MediaSessionStack", "updateMediaButtonSessionIfNeeded, callers=" + getCallers(2));
    }
    List sortedAudioPlaybackClientUids =
        this.mAudioPlayerStateMonitor.getSortedAudioPlaybackClientUids();
    for (int i = 0; i < sortedAudioPlaybackClientUids.size(); i++) {
      int intValue = ((Integer) sortedAudioPlaybackClientUids.get(i)).intValue();
      MediaSessionRecordImpl findMediaButtonSession = findMediaButtonSession(intValue);
      if (findMediaButtonSession == null) {
        if (DEBUG) {
          Log.d("MediaSessionStack", "updateMediaButtonSessionIfNeeded, skipping uid=" + intValue);
        }
      } else {
        boolean z = (findMediaButtonSession.getSessionPolicies() & 2) != 0;
        if (DEBUG) {
          Log.d(
              "MediaSessionStack",
              "updateMediaButtonSessionIfNeeded, checking uid="
                  + intValue
                  + ", mediaButtonSession="
                  + findMediaButtonSession
                  + ", ignoreButtonSession="
                  + z);
        }
        if (!z) {
          this.mAudioPlayerStateMonitor.cleanUpAudioPlaybackUids(findMediaButtonSession.getUid());
          if (findMediaButtonSession != this.mMediaButtonSession) {
            updateMediaButtonSession(findMediaButtonSession);
            return;
          }
          return;
        }
      }
    }
  }

  public void updateMediaButtonSessionBySessionPolicyChange(MediaSessionRecord mediaSessionRecord) {
    if ((mediaSessionRecord.getSessionPolicies() & 2) != 0) {
      if (mediaSessionRecord == this.mMediaButtonSession) {
        updateMediaButtonSession(null);
        return;
      }
      return;
    }
    updateMediaButtonSessionIfNeeded();
  }

  public final MediaSessionRecordImpl findMediaButtonSession(int i) {
    MediaSessionRecordImpl mediaSessionRecordImpl = null;
    for (MediaSessionRecordImpl mediaSessionRecordImpl2 : this.mSessions) {
      if (!(mediaSessionRecordImpl2 instanceof MediaSession2Record)
          && i == mediaSessionRecordImpl2.getUid()) {
        if (mediaSessionRecordImpl2.checkPlaybackActiveState(
            this.mAudioPlayerStateMonitor.isPlaybackActive(mediaSessionRecordImpl2.getUid()))) {
          return mediaSessionRecordImpl2;
        }
        if (mediaSessionRecordImpl == null) {
          mediaSessionRecordImpl = mediaSessionRecordImpl2;
        }
      }
    }
    return mediaSessionRecordImpl;
  }

  public List getActiveSessions(int i) {
    List list = (List) this.mCachedActiveLists.get(i);
    if (list != null) {
      return list;
    }
    List priorityList = getPriorityList(true, i);
    this.mCachedActiveLists.put(i, priorityList);
    return priorityList;
  }

  public List getSession2Tokens(int i) {
    ArrayList arrayList = new ArrayList();
    for (MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
      if (i == -1 || mediaSessionRecordImpl.getUserId() == i) {
        if (mediaSessionRecordImpl.isActive()
            && (mediaSessionRecordImpl instanceof MediaSession2Record)) {
          arrayList.add(((MediaSession2Record) mediaSessionRecordImpl).getSession2Token());
        }
      }
    }
    return arrayList;
  }

  public MediaSessionRecordImpl getMediaButtonSession() {
    return this.mMediaButtonSession;
  }

  public void updateMediaButtonSession(MediaSessionRecordImpl mediaSessionRecordImpl) {
    MediaSessionRecordImpl mediaSessionRecordImpl2 = this.mMediaButtonSession;
    this.mMediaButtonSession = mediaSessionRecordImpl;
    this.mOnMediaButtonSessionChangedListener.onMediaButtonSessionChanged(
        mediaSessionRecordImpl2, mediaSessionRecordImpl);
  }

  public MediaSessionRecordImpl getDefaultVolumeSession() {
    List priorityList = getPriorityList(true, -1);
    int size = priorityList.size();
    for (int i = 0; i < size; i++) {
      MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) priorityList.get(i);
      if (mediaSessionRecord.checkPlaybackActiveState(true)
          && mediaSessionRecord.canHandleVolumeKey()) {
        return mediaSessionRecord;
      }
    }
    return null;
  }

  public MediaSessionRecordImpl getDefaultRemoteSession(int i) {
    List priorityList = getPriorityList(true, i);
    int size = priorityList.size();
    for (int i2 = 0; i2 < size; i2++) {
      MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) priorityList.get(i2);
      if (!mediaSessionRecord.isPlaybackTypeLocal()) {
        return mediaSessionRecord;
      }
    }
    return null;
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println(str + "Media button session is " + this.mMediaButtonSession);
    printWriter.println(str + "Sessions Stack - have " + this.mSessions.size() + " sessions:");
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append("  ");
    String sb2 = sb.toString();
    Iterator it = this.mSessions.iterator();
    while (it.hasNext()) {
      ((MediaSessionRecordImpl) it.next()).dump(printWriter, sb2);
    }
  }

  public List getPriorityList(boolean z, int i) {
    ArrayList arrayList = new ArrayList();
    int i2 = 0;
    int i3 = 0;
    for (MediaSessionRecordImpl mediaSessionRecordImpl : this.mSessions) {
      if (mediaSessionRecordImpl instanceof MediaSessionRecord) {
        MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaSessionRecordImpl;
        if (i == -1 || i == mediaSessionRecord.getUserId()) {
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

  public final void clearCache(int i) {
    this.mCachedActiveLists.remove(i);
    this.mCachedActiveLists.remove(-1);
  }

  public static String getCallers(int i) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StringBuilder sb = new StringBuilder();
    for (int i2 = 0; i2 < i; i2++) {
      sb.append(getCaller(stackTrace, i2));
      sb.append(" ");
    }
    return sb.toString();
  }

  public static String getCaller(StackTraceElement[] stackTraceElementArr, int i) {
    int i2 = i + 4;
    if (i2 >= stackTraceElementArr.length) {
      return "<bottom of call stack>";
    }
    StackTraceElement stackTraceElement = stackTraceElementArr[i2];
    return stackTraceElement.getClassName()
        + "."
        + stackTraceElement.getMethodName()
        + XmlUtils.STRING_ARRAY_SEPARATOR
        + stackTraceElement.getLineNumber();
  }

  public void updateMultiSoundInfo(boolean z) {
    this.mIsMultiSoundOn = z;
  }

  public List getActiveSessionsSortedPlayback(int i) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList(getPriorityList(false, i));
    final ArrayList arrayList3 =
        new ArrayList(this.mAudioPlayerStateMonitor.getSortedAudioPlaybackClientUids());
    this.mPrevPlayedUids.stream()
        .filter(
            new Predicate() { // from class:
              // com.android.server.media.MediaSessionStack$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$getActiveSessionsSortedPlayback$0;
                lambda$getActiveSessionsSortedPlayback$0 =
                    MediaSessionStack.lambda$getActiveSessionsSortedPlayback$0(
                        arrayList3, (Integer) obj);
                return lambda$getActiveSessionsSortedPlayback$0;
              }
            })
        .forEach(new Convert$$ExternalSyntheticLambda0(arrayList3));
    this.mPrevPlayedUids.clear();
    for (int i2 = 0; i2 < Math.min(arrayList3.size(), 5); i2++) {
      this.mPrevPlayedUids.add((Integer) arrayList3.get(i2));
    }
    for (int size = arrayList3.size() - 1; size >= 0; size--) {
      int intValue = ((Integer) arrayList3.get(size)).intValue();
      Iterator it = arrayList2.iterator();
      while (it.hasNext()) {
        MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) it.next();
        if (mediaSessionRecord.getUid() == intValue) {
          arrayList.add(0, mediaSessionRecord);
          it.remove();
        }
      }
    }
    arrayList.addAll(arrayList2);
    return arrayList;
  }

  public static /* synthetic */ boolean lambda$getActiveSessionsSortedPlayback$0(
      ArrayList arrayList, Integer num) {
    return !arrayList.contains(num);
  }
}
