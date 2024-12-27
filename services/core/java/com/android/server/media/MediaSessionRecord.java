package com.android.server.media;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ForegroundServiceDelegationOptions;
import android.app.IApplicationThread;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaMetadata;
import android.media.MediaRouter2Manager;
import android.media.Rating;
import android.media.RoutingSessionInfo;
import android.media.session.ISession;
import android.media.session.ISessionCallback;
import android.media.session.ISessionController;
import android.media.session.ISessionControllerCallback;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.ParcelableListBinder;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.media.MediaSessionRecord;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.samsung.android.audio.Rune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class MediaSessionRecord extends MediaSessionRecordImpl implements IBinder.DeathRecipient {
    public AudioAttributes mAudioAttrs;
    public final AudioManager mAudioManager;
    public final MediaSessionRecord$$ExternalSyntheticLambda0 mClearOptimisticVolumeRunnable;
    public final Context mContext;
    public final ControllerStub mController;
    public Bundle mExtras;
    public long mFlags;
    public final ForegroundServiceDelegationOptions mForegroundServiceDelegationOptions;
    public final MessageHandler mHandler;
    public PendingIntent mLaunchIntent;
    public MediaButtonReceiverHolder mMediaButtonReceiverHolder;
    public MediaMetadata mMetadata;
    public String mMetadataDescription;
    public final int mOwnerPid;
    public final int mOwnerUid;
    public final String mPackageName;
    public PlaybackState mPlaybackState;
    public int mPolicies;
    public List mQueue;
    public CharSequence mQueueTitle;
    public int mRatingType;
    public final MediaSessionService mService;
    public final SessionStub mSession;
    public final SessionCb mSessionCb;
    public final Bundle mSessionInfo;
    public final MediaSession.Token mSessionToken;
    public final String mTag;
    public final UriGrantsManagerInternal mUgmInternal;
    public final MediaSessionRecord$$ExternalSyntheticLambda0 mUserEngagementTimeoutExpirationRunnable;
    public final int mUserId;
    public final boolean mVolumeAdjustmentForRemoteGroupSessions;
    public String mVolumeControlId;
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final List ALWAYS_PRIORITY_STATES = Arrays.asList(4, 5, 9, 10);
    public static final List TRANSITION_PRIORITY_STATES = Arrays.asList(6, 8, 3);
    public static final AudioAttributes DEFAULT_ATTRIBUTES = new AudioAttributes.Builder().setUsage(1).build();
    public final Object mLock = new Object();
    public final CopyOnWriteArrayList mControllerCallbackHolders = new CopyOnWriteArrayList();
    public int mVolumeType = 1;
    public int mVolumeControlType = 2;
    public int mMaxVolume = 0;
    public int mCurrentVolume = 0;
    public int mOptimisticVolume = -1;
    public boolean mIsActive = false;
    public boolean mDestroyed = false;
    public long mDuration = -1;
    public int mUserEngagementState = 2;

    public interface ControllerCallbackCall {
        void performOn(ISessionControllerCallbackHolder iSessionControllerCallbackHolder);
    }

    public final class ControllerStub extends ISessionController.Stub {
        public ControllerStub() {
        }

        public final void adjustVolume(String str, String str2, int i, int i2) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.adjustVolume(str, str2, callingPid, callingUid, false, i, i2, false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void fastForward(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:fastForward", callingUid);
                sessionCb.mCb.onFastForward(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in fastForward.", e);
            }
        }

        public final Bundle getExtras() {
            Bundle bundle;
            synchronized (MediaSessionRecord.this.mLock) {
                bundle = MediaSessionRecord.this.mExtras;
            }
            return bundle;
        }

        public final long getFlags() {
            return MediaSessionRecord.this.mFlags;
        }

        public final PendingIntent getLaunchPendingIntent() {
            return MediaSessionRecord.this.mLaunchIntent;
        }

        public final MediaMetadata getMetadata() {
            MediaMetadata mediaMetadata;
            synchronized (MediaSessionRecord.this.mLock) {
                mediaMetadata = MediaSessionRecord.this.mMetadata;
            }
            return mediaMetadata;
        }

        public final String getPackageName() {
            return MediaSessionRecord.this.mPackageName;
        }

        public final PlaybackState getPlaybackState() {
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            synchronized (mediaSessionRecord.mLock) {
                try {
                    PlaybackState playbackState = null;
                    if (mediaSessionRecord.mDestroyed) {
                        return null;
                    }
                    PlaybackState playbackState2 = mediaSessionRecord.mPlaybackState;
                    long j = mediaSessionRecord.mDuration;
                    if (playbackState2 != null && (playbackState2.getState() == 3 || playbackState2.getState() == 4 || playbackState2.getState() == 5)) {
                        long lastPositionUpdateTime = playbackState2.getLastPositionUpdateTime();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (lastPositionUpdateTime > 0) {
                            long position = playbackState2.getPosition() + ((long) (playbackState2.getPlaybackSpeed() * (elapsedRealtime - lastPositionUpdateTime)));
                            if (j >= 0 && position > j) {
                                position = j;
                            } else if (position < 0) {
                                position = 0;
                            }
                            PlaybackState.Builder builder = new PlaybackState.Builder(playbackState2);
                            builder.setState(playbackState2.getState(), position, playbackState2.getPlaybackSpeed(), elapsedRealtime);
                            playbackState = builder.build();
                        }
                    }
                    return playbackState == null ? playbackState2 : playbackState;
                } finally {
                }
            }
        }

        public final ParceledListSlice getQueue() {
            ParceledListSlice parceledListSlice;
            synchronized (MediaSessionRecord.this.mLock) {
                parceledListSlice = MediaSessionRecord.this.mQueue == null ? null : new ParceledListSlice(MediaSessionRecord.this.mQueue);
            }
            return parceledListSlice;
        }

        public final CharSequence getQueueTitle() {
            return MediaSessionRecord.this.mQueueTitle;
        }

        public final int getRatingType() {
            return MediaSessionRecord.this.mRatingType;
        }

        public final Bundle getSessionInfo() {
            return MediaSessionRecord.this.mSessionInfo;
        }

        public final String getTag() {
            return MediaSessionRecord.this.mTag;
        }

        public final MediaController.PlaybackInfo getVolumeAttributes() {
            return MediaSessionRecord.this.getVolumeAttributes();
        }

        public final void next(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:next", callingUid);
                sessionCb.mCb.onNext(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in next.", e);
            }
        }

        public final void pause(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:pause", callingUid);
                sessionCb.mCb.onPause(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in pause.", e);
            }
        }

        public final void play(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:play", callingUid);
                sessionCb.mCb.onPlay(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in play.", e);
            }
        }

        public final void playFromMediaId(String str, String str2, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:playFromMediaId", callingUid);
                sessionCb.mCb.onPlayFromMediaId(str, callingPid, callingUid, str2, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in playFromMediaId.", e);
            }
        }

        public final void playFromSearch(String str, String str2, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:playFromSearch", callingUid);
                sessionCb.mCb.onPlayFromSearch(str, callingPid, callingUid, str2, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in playFromSearch.", e);
            }
        }

        public final void playFromUri(String str, Uri uri, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:playFromUri", callingUid);
                sessionCb.mCb.onPlayFromUri(str, callingPid, callingUid, uri, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in playFromUri.", e);
            }
        }

        public final void prepare(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:prepare", callingUid);
                sessionCb.mCb.onPrepare(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in prepare.", e);
            }
        }

        public final void prepareFromMediaId(String str, String str2, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:prepareFromMediaId", callingUid);
                sessionCb.mCb.onPrepareFromMediaId(str, callingPid, callingUid, str2, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in prepareFromMediaId.", e);
            }
        }

        public final void prepareFromSearch(String str, String str2, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:prepareFromSearch", callingUid);
                sessionCb.mCb.onPrepareFromSearch(str, callingPid, callingUid, str2, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in prepareFromSearch.", e);
            }
        }

        public final void prepareFromUri(String str, Uri uri, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:prepareFromUri", callingUid);
                sessionCb.mCb.onPrepareFromUri(str, callingPid, callingUid, uri, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in prepareFromUri.", e);
            }
        }

        public final void previous(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:previous", callingUid);
                sessionCb.mCb.onPrevious(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in previous.", e);
            }
        }

        public final void rate(String str, Rating rating) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:rate", callingUid);
                sessionCb.mCb.onRate(str, callingPid, callingUid, rating);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in rate.", e);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v1, types: [android.os.IBinder$DeathRecipient, com.android.server.media.MediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0] */
        public final void registerCallback(String str, final ISessionControllerCallback iSessionControllerCallback) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                if (mediaSessionRecord.mDestroyed) {
                    try {
                        iSessionControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                    return;
                }
                if (MediaSessionRecord.m656$$Nest$mgetControllerHolderIndexForCb(mediaSessionRecord, iSessionControllerCallback) < 0) {
                    Binder.getCallingUid();
                    ?? r3 = new IBinder.DeathRecipient() { // from class: com.android.server.media.MediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            MediaSessionRecord.ControllerStub.this.unregisterCallback(iSessionControllerCallback);
                        }
                    };
                    MediaSessionRecord.this.mControllerCallbackHolders.add(new ISessionControllerCallbackHolder(iSessionControllerCallback, str, r3));
                    Slog.d("MediaSessionRecord", "registering controller callback " + iSessionControllerCallback + " from controller" + str);
                    try {
                        iSessionControllerCallback.asBinder().linkToDeath(r3, 0);
                    } catch (RemoteException e) {
                        unregisterCallback(iSessionControllerCallback);
                        Slog.w("MediaSessionRecord", "registerCallback failed to linkToDeath", e);
                    }
                }
            }
        }

        public final void rewind(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:rewind", callingUid);
                sessionCb.mCb.onRewind(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in rewind.", e);
            }
        }

        public final void seekTo(String str, long j) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:seekTo", callingUid);
                sessionCb.mCb.onSeekTo(str, callingPid, callingUid, j);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in seekTo.", e);
            }
        }

        public final void sendCommand(String str, String str2, Bundle bundle, ResultReceiver resultReceiver) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:" + str2, callingUid);
                sessionCb.mCb.onCommand(str, callingPid, callingUid, str2, bundle, resultReceiver);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in sendCommand.", e);
            }
        }

        public final void sendCustomAction(String str, String str2, Bundle bundle) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:custom-" + str2, callingUid);
                sessionCb.mCb.onCustomAction(str, callingPid, callingUid, str2, bundle);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in sendCustomAction.", e);
            }
        }

        public final boolean sendMediaButton(String str, KeyEvent keyEvent) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                boolean isMediaSessionKey = KeyEvent.isMediaSessionKey(keyEvent.getKeyCode());
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                if (isMediaSessionKey) {
                    mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "action=" + KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + KeyEvent.keyCodeToString(keyEvent.getKeyCode()), callingUid);
                }
                ISessionCallback iSessionCallback = sessionCb.mCb;
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                iSessionCallback.onMediaButtonFromController(str, callingPid, callingUid, intent);
                return true;
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in sendMediaRequest.", e);
                return false;
            }
        }

        public final void setPlaybackSpeed(String str, float f) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:setPlaybackSpeed", callingUid);
                sessionCb.mCb.onSetPlaybackSpeed(str, callingPid, callingUid, f);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in setPlaybackSpeed.", e);
            }
        }

        public final void setVolumeTo(String str, String str2, int i, int i2) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.m661$$Nest$msetVolumeTo(MediaSessionRecord.this, str, str2, callingPid, callingUid, i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void skipToQueueItem(String str, long j) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:skipToTrack", callingUid);
                sessionCb.mCb.onSkipToTrack(str, callingPid, callingUid, j);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in skipToTrack", e);
            }
        }

        public final void stop(String str) {
            SessionCb sessionCb = MediaSessionRecord.this.mSessionCb;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, callingPid, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:stop", callingUid);
                sessionCb.mCb.onStop(str, callingPid, callingUid);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in stop.", e);
            }
        }

        public final void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) {
            synchronized (MediaSessionRecord.this.mLock) {
                int m656$$Nest$mgetControllerHolderIndexForCb = MediaSessionRecord.m656$$Nest$mgetControllerHolderIndexForCb(MediaSessionRecord.this, iSessionControllerCallback);
                String str = "";
                if (m656$$Nest$mgetControllerHolderIndexForCb != -1) {
                    try {
                        iSessionControllerCallback.asBinder().unlinkToDeath(((ISessionControllerCallbackHolder) MediaSessionRecord.this.mControllerCallbackHolders.get(m656$$Nest$mgetControllerHolderIndexForCb)).mDeathMonitor, 0);
                    } catch (NoSuchElementException e) {
                        Slog.w("MediaSessionRecord", "error unlinking to binder death", e);
                    }
                    str = ((ISessionControllerCallbackHolder) MediaSessionRecord.this.mControllerCallbackHolders.get(m656$$Nest$mgetControllerHolderIndexForCb)).mPackageName;
                    MediaSessionRecord.this.mControllerCallbackHolders.remove(m656$$Nest$mgetControllerHolderIndexForCb);
                }
                String[] strArr = MediaSessionRecord.ART_URIS;
                Slog.d("MediaSessionRecord", "unregistering callback " + iSessionControllerCallback.asBinder() + " " + str);
            }
        }
    }

    public final class ISessionControllerCallbackHolder {
        public final ISessionControllerCallback mCallback;
        public final IBinder.DeathRecipient mDeathMonitor;
        public final String mPackageName;

        public ISessionControllerCallbackHolder(ISessionControllerCallback iSessionControllerCallback, String str, MediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0 mediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0) {
            this.mCallback = iSessionControllerCallback;
            this.mPackageName = str;
            this.mDeathMonitor = mediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0;
        }
    }

    public final class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    MediaSessionRecord.m657$$Nest$mpushMetadataUpdate(MediaSessionRecord.this);
                    return;
                case 2:
                    MediaSessionRecord.m658$$Nest$mpushPlaybackStateUpdate(MediaSessionRecord.this);
                    return;
                case 3:
                    MediaSessionRecord.m660$$Nest$mpushQueueUpdate(MediaSessionRecord.this);
                    return;
                case 4:
                    MediaSessionRecord.m659$$Nest$mpushQueueTitleUpdate(MediaSessionRecord.this);
                    return;
                case 5:
                    MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                    synchronized (mediaSessionRecord.mLock) {
                        try {
                            if (!mediaSessionRecord.mDestroyed) {
                                mediaSessionRecord.performOnCallbackHolders("pushExtrasUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(mediaSessionRecord.mExtras));
                            }
                        } finally {
                        }
                    }
                    return;
                case 6:
                    MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                    final String str = (String) message.obj;
                    final Bundle data = message.getData();
                    synchronized (mediaSessionRecord2.mLock) {
                        try {
                            if (!mediaSessionRecord2.mDestroyed) {
                                mediaSessionRecord2.performOnCallbackHolders("pushEvent", new ControllerCallbackCall() { // from class: com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda6
                                    @Override // com.android.server.media.MediaSessionRecord.ControllerCallbackCall
                                    public final void performOn(MediaSessionRecord.ISessionControllerCallbackHolder iSessionControllerCallbackHolder) {
                                        iSessionControllerCallbackHolder.mCallback.onEvent(str, data);
                                    }
                                });
                            }
                        } finally {
                        }
                    }
                    return;
                case 7:
                default:
                    return;
                case 8:
                    MediaSessionRecord.this.pushVolumeUpdate();
                    return;
                case 9:
                    MediaSessionRecord mediaSessionRecord3 = MediaSessionRecord.this;
                    synchronized (mediaSessionRecord3.mLock) {
                        try {
                            if (mediaSessionRecord3.mDestroyed) {
                                mediaSessionRecord3.performOnCallbackHolders("pushSessionDestroyed", new MediaSessionRecord$$ExternalSyntheticLambda7());
                                synchronized (mediaSessionRecord3.mLock) {
                                    mediaSessionRecord3.mControllerCallbackHolders.clear();
                                }
                            }
                        } finally {
                        }
                    }
                    return;
            }
        }

        public final void post(int i) {
            obtainMessage(i, null).sendToTarget();
        }
    }

    public final class SessionCb {
        public final ISessionCallback mCb;

        public SessionCb(ISessionCallback iSessionCallback) {
            this.mCb = iSessionCallback;
        }
    }

    public final class SessionStub extends ISession.Stub {
        public SessionStub() {
        }

        public final void destroySession() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                MediaSessionService mediaSessionService = mediaSessionRecord.mService;
                synchronized (mediaSessionService.mLock) {
                    mediaSessionService.destroySessionLocked(mediaSessionRecord);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final IBinder getBinderForSetQueue() {
            return new ParcelableListBinder(MediaSession.QueueItem.class, new Consumer() { // from class: com.android.server.media.MediaSessionRecord$SessionStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MediaSessionRecord mediaSessionRecord;
                    MediaSessionRecord.SessionStub sessionStub = MediaSessionRecord.SessionStub.this;
                    List list = (List) obj;
                    synchronized (MediaSessionRecord.this.mLock) {
                        mediaSessionRecord = MediaSessionRecord.this;
                        mediaSessionRecord.mQueue = list;
                    }
                    mediaSessionRecord.mHandler.post(3);
                }
            });
        }

        public final ISessionController getController() {
            return MediaSessionRecord.this.mController;
        }

        public final void resetQueue() {
            MediaSessionRecord mediaSessionRecord;
            synchronized (MediaSessionRecord.this.mLock) {
                mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mQueue = null;
            }
            mediaSessionRecord.mHandler.post(3);
        }

        public final MediaMetadata sanitizeMediaMetadata(MediaMetadata mediaMetadata) {
            if (mediaMetadata == null) {
                return null;
            }
            MediaMetadata.Builder builder = new MediaMetadata.Builder(mediaMetadata);
            String[] strArr = MediaSessionRecord.ART_URIS;
            for (int i = 0; i < 3; i++) {
                String str = strArr[i];
                String string = mediaMetadata.getString(str);
                if (!TextUtils.isEmpty(string)) {
                    Uri parse = Uri.parse(string);
                    if ("content".equals(parse.getScheme())) {
                        try {
                            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                            ((UriGrantsManagerService.LocalService) mediaSessionRecord.mUgmInternal).checkGrantUriPermission(mediaSessionRecord.mOwnerUid, mediaSessionRecord.mPackageName, ContentProvider.getUriWithoutUserId(parse), 1, ContentProvider.getUserIdFromUri(parse, MediaSessionRecord.this.mUserId));
                        } catch (SecurityException unused) {
                            builder.putString(str, null);
                        }
                    }
                }
            }
            MediaMetadata build = builder.build();
            build.size();
            return build;
        }

        public final void sendEvent(String str, Bundle bundle) {
            MessageHandler messageHandler = MediaSessionRecord.this.mHandler;
            Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
            Message obtainMessage = messageHandler.obtainMessage(6, str);
            obtainMessage.setData(bundle2);
            obtainMessage.sendToTarget();
        }

        public final void setActive(boolean z) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (z) {
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiBegin(4, callingUid, callingPid);
            } else {
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiEnd(4, callingUid, callingPid);
            }
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mIsActive = z;
                mediaSessionRecord.updateUserEngagedStateIfNeededLocked(false);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                mediaSessionRecord2.mService.onSessionActiveStateChanged(mediaSessionRecord2, mediaSessionRecord2.mPlaybackState);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                MediaSessionRecord.this.mHandler.post(7);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void setCurrentVolume(int i) {
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            mediaSessionRecord.mCurrentVolume = i;
            mediaSessionRecord.mHandler.post(8);
        }

        public final void setExtras(Bundle bundle) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mExtras = bundle == null ? null : new Bundle(bundle);
            }
            MediaSessionRecord.this.mHandler.post(5);
        }

        public final void setFlags(int i) {
            int i2 = 65536 & i;
            if (i2 != 0) {
                if (MediaSessionRecord.this.mService.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", Binder.getCallingPid(), Binder.getCallingUid()) != 0) {
                    throw new SecurityException("Must hold the MODIFY_PHONE_STATE permission.");
                }
            }
            MediaSessionRecord.this.mFlags = i;
            if (i2 != 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                    mediaSessionRecord.mService.setGlobalPrioritySession(mediaSessionRecord);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            MediaSessionRecord.this.mHandler.post(7);
        }

        public final void setLaunchPendingIntent(PendingIntent pendingIntent) {
            MediaSessionRecord.this.mLaunchIntent = pendingIntent;
        }

        public final void setMediaButtonBroadcastReceiver(ComponentName componentName) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (componentName != null) {
                try {
                    if (!TextUtils.equals(MediaSessionRecord.this.mPackageName, componentName.getPackageName())) {
                        EventLog.writeEvent(1397638484, "238177121", -1, "");
                        throw new IllegalArgumentException("receiver does not belong to package name provided to MediaSessionRecord. Pkg = " + MediaSessionRecord.this.mPackageName + ", Receiver Pkg = " + componentName.getPackageName());
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            if ((mediaSessionRecord.mPolicies & 1) != 0) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            Context context = mediaSessionRecord.mContext;
            int i = mediaSessionRecord.mUserId;
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.addFlags(268435456);
            intent.setComponent(componentName);
            if (!context.getPackageManager().queryBroadcastReceiversAsUser(intent, PackageManager.ResolveInfoFlags.of(0L), UserHandle.of(i)).isEmpty()) {
                MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                mediaSessionRecord2.mMediaButtonReceiverHolder = new MediaButtonReceiverHolder(mediaSessionRecord2.mUserId, null, componentName, 1);
                MediaSessionRecord mediaSessionRecord3 = MediaSessionRecord.this;
                mediaSessionRecord3.mService.onMediaButtonReceiverChanged(mediaSessionRecord3);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (CompatChanges.isChangeEnabled(270049379L, callingUid)) {
                throw new IllegalArgumentException("Invalid component name: " + componentName);
            }
            Slog.w("MediaSessionRecord", "setMediaButtonBroadcastReceiver(): Ignoring invalid component name=" + componentName);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }

        public final void setMediaButtonReceiver(PendingIntent pendingIntent) {
            int i;
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if ((MediaSessionRecord.this.mPolicies & 1) != 0) {
                    return;
                }
                if (pendingIntent != null && pendingIntent.isActivity()) {
                    if (CompatChanges.isChangeEnabled(272737196L, callingUid)) {
                        throw new IllegalArgumentException("The media button receiver cannot be set to an activity.");
                    }
                    Slog.w("MediaSessionRecord", "Ignoring invalid media button receiver targeting an activity.");
                    return;
                }
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                int i2 = mediaSessionRecord.mUserId;
                String str = mediaSessionRecord.mPackageName;
                ComponentName componentName = null;
                MediaButtonReceiverHolder mediaButtonReceiverHolder = null;
                if (pendingIntent != null) {
                    if (pendingIntent.isBroadcast()) {
                        i = 1;
                    } else if (pendingIntent.isActivity()) {
                        i = 2;
                    } else {
                        if (!pendingIntent.isForegroundService() && !pendingIntent.isService()) {
                            i = 0;
                        }
                        i = 3;
                    }
                    List emptyList = Collections.emptyList();
                    if (i == 1) {
                        emptyList = pendingIntent.queryIntentComponents(786434);
                    } else if (i == 2) {
                        emptyList = pendingIntent.queryIntentComponents(851969);
                    } else if (i == 3) {
                        emptyList = pendingIntent.queryIntentComponents(786436);
                    }
                    Iterator it = emptyList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ResolveInfo resolveInfo = (ResolveInfo) it.next();
                        ComponentInfo componentInfo = resolveInfo.activityInfo;
                        if (componentInfo == null && (componentInfo = resolveInfo.serviceInfo) == null) {
                            componentInfo = null;
                        }
                        if (componentInfo != null && TextUtils.equals(componentInfo.packageName, pendingIntent.getCreatorPackage()) && componentInfo.packageName != null && componentInfo.name != null) {
                            componentName = new ComponentName(componentInfo.packageName, componentInfo.name);
                            break;
                        }
                    }
                    if (componentName != null) {
                        mediaButtonReceiverHolder = new MediaButtonReceiverHolder(i2, pendingIntent, componentName, i);
                    } else {
                        Log.w("PendingIntentHolder", "Unresolvable implicit intent is set, pi=" + pendingIntent);
                        mediaButtonReceiverHolder = new MediaButtonReceiverHolder(str, pendingIntent, i2);
                    }
                }
                mediaSessionRecord.mMediaButtonReceiverHolder = mediaButtonReceiverHolder;
                MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                mediaSessionRecord2.mService.onMediaButtonReceiverChanged(mediaSessionRecord2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setMetadata(MediaMetadata mediaMetadata, long j, String str) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mDuration = j;
                mediaSessionRecord.mMetadataDescription = str;
                mediaSessionRecord.mMetadata = sanitizeMediaMetadata(mediaMetadata);
            }
            if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                    mediaSessionRecord2.mService.onSessionMetadataChanged(mediaSessionRecord2, mediaSessionRecord2.mMetadata);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            MediaSessionRecord.this.mHandler.post(1);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setPlaybackState(android.media.session.PlaybackState r6) {
            /*
                r5 = this;
                com.android.server.media.MediaSessionRecord r0 = com.android.server.media.MediaSessionRecord.this
                android.media.session.PlaybackState r0 = r0.mPlaybackState
                r1 = 0
                if (r0 != 0) goto L9
                r0 = r1
                goto Ld
            L9:
                int r0 = r0.getState()
            Ld:
                if (r6 != 0) goto L11
                r2 = r1
                goto L15
            L11:
                int r2 = r6.getState()
            L15:
                java.util.List r3 = com.android.server.media.MediaSessionRecord.ALWAYS_PRIORITY_STATES
                java.lang.Integer r4 = java.lang.Integer.valueOf(r2)
                boolean r3 = r3.contains(r4)
                if (r3 != 0) goto L3a
                java.util.List r3 = com.android.server.media.MediaSessionRecord.TRANSITION_PRIORITY_STATES
                java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                boolean r0 = r3.contains(r0)
                if (r0 != 0) goto L38
                java.lang.Integer r0 = java.lang.Integer.valueOf(r2)
                boolean r0 = r3.contains(r0)
                if (r0 == 0) goto L38
                goto L3a
            L38:
                r0 = r1
                goto L3b
            L3a:
                r0 = 1
            L3b:
                com.android.server.media.MediaSessionRecord r2 = com.android.server.media.MediaSessionRecord.this
                java.lang.Object r2 = r2.mLock
                monitor-enter(r2)
                com.android.server.media.MediaSessionRecord r3 = com.android.server.media.MediaSessionRecord.this     // Catch: java.lang.Throwable -> L66
                r3.mPlaybackState = r6     // Catch: java.lang.Throwable -> L66
                r3.updateUserEngagedStateIfNeededLocked(r1)     // Catch: java.lang.Throwable -> L66
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L66
                long r1 = android.os.Binder.clearCallingIdentity()
                com.android.server.media.MediaSessionRecord r6 = com.android.server.media.MediaSessionRecord.this     // Catch: java.lang.Throwable -> L61
                com.android.server.media.MediaSessionService r3 = r6.mService     // Catch: java.lang.Throwable -> L61
                android.media.session.PlaybackState r4 = r6.mPlaybackState     // Catch: java.lang.Throwable -> L61
                r3.onSessionPlaybackStateChanged(r6, r0, r4)     // Catch: java.lang.Throwable -> L61
                android.os.Binder.restoreCallingIdentity(r1)
                com.android.server.media.MediaSessionRecord r5 = com.android.server.media.MediaSessionRecord.this
                com.android.server.media.MediaSessionRecord$MessageHandler r5 = r5.mHandler
                r6 = 2
                r5.post(r6)
                return
            L61:
                r5 = move-exception
                android.os.Binder.restoreCallingIdentity(r1)
                throw r5
            L66:
                r5 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L66
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.MediaSessionRecord.SessionStub.setPlaybackState(android.media.session.PlaybackState):void");
        }

        public final void setPlaybackToLocal(AudioAttributes audioAttributes) {
            boolean z;
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                z = mediaSessionRecord.mVolumeType == 2;
                mediaSessionRecord.mVolumeType = 1;
                mediaSessionRecord.mVolumeControlId = null;
                if (audioAttributes != null) {
                    mediaSessionRecord.mAudioAttrs = audioAttributes;
                } else {
                    Slog.e("MediaSessionRecord", "Received null audio attributes, using existing attributes");
                }
            }
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                    mediaSessionRecord2.mService.onSessionPlaybackTypeChanged(mediaSessionRecord2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    MediaSessionRecord.this.mHandler.post(8);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final void setPlaybackToRemote(int i, int i2, String str) {
            boolean z;
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                z = true;
                if (mediaSessionRecord.mVolumeType != 1) {
                    z = false;
                }
                mediaSessionRecord.mVolumeType = 2;
                mediaSessionRecord.mVolumeControlType = i;
                mediaSessionRecord.mMaxVolume = i2;
                mediaSessionRecord.mVolumeControlId = str;
            }
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                    mediaSessionRecord2.mService.onSessionPlaybackTypeChanged(mediaSessionRecord2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    MediaSessionRecord.this.mHandler.post(8);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final void setQueueTitle(CharSequence charSequence) {
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            mediaSessionRecord.mQueueTitle = charSequence;
            mediaSessionRecord.mHandler.post(4);
        }

        public final void setRatingType(int i) {
            MediaSessionRecord.this.mRatingType = i;
        }
    }

    /* renamed from: -$$Nest$mgetControllerHolderIndexForCb, reason: not valid java name */
    public static int m656$$Nest$mgetControllerHolderIndexForCb(MediaSessionRecord mediaSessionRecord, ISessionControllerCallback iSessionControllerCallback) {
        mediaSessionRecord.getClass();
        IBinder asBinder = iSessionControllerCallback.asBinder();
        for (int size = mediaSessionRecord.mControllerCallbackHolders.size() - 1; size >= 0; size--) {
            if (asBinder.equals(((ISessionControllerCallbackHolder) mediaSessionRecord.mControllerCallbackHolders.get(size)).mCallback.asBinder())) {
                return size;
            }
        }
        return -1;
    }

    /* renamed from: -$$Nest$mpushMetadataUpdate, reason: not valid java name */
    public static void m657$$Nest$mpushMetadataUpdate(MediaSessionRecord mediaSessionRecord) {
        synchronized (mediaSessionRecord.mLock) {
            try {
                if (mediaSessionRecord.mDestroyed) {
                    return;
                }
                mediaSessionRecord.performOnCallbackHolders("pushMetadataUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(1, mediaSessionRecord.mMetadata));
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mpushPlaybackStateUpdate, reason: not valid java name */
    public static void m658$$Nest$mpushPlaybackStateUpdate(MediaSessionRecord mediaSessionRecord) {
        synchronized (mediaSessionRecord.mLock) {
            try {
                if (mediaSessionRecord.mDestroyed) {
                    return;
                }
                mediaSessionRecord.performOnCallbackHolders("pushPlaybackStateUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(4, mediaSessionRecord.mPlaybackState));
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mpushQueueTitleUpdate, reason: not valid java name */
    public static void m659$$Nest$mpushQueueTitleUpdate(MediaSessionRecord mediaSessionRecord) {
        synchronized (mediaSessionRecord.mLock) {
            try {
                if (mediaSessionRecord.mDestroyed) {
                    return;
                }
                mediaSessionRecord.performOnCallbackHolders("pushQueueTitleUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(5, mediaSessionRecord.mQueueTitle));
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mpushQueueUpdate, reason: not valid java name */
    public static void m660$$Nest$mpushQueueUpdate(MediaSessionRecord mediaSessionRecord) {
        synchronized (mediaSessionRecord.mLock) {
            try {
                if (mediaSessionRecord.mDestroyed) {
                    return;
                }
                mediaSessionRecord.performOnCallbackHolders("pushQueueUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(3, mediaSessionRecord.mQueue == null ? null : new ArrayList(mediaSessionRecord.mQueue)));
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetVolumeTo, reason: not valid java name */
    public static void m661$$Nest$msetVolumeTo(final MediaSessionRecord mediaSessionRecord, String str, final String str2, final int i, final int i2, final int i3, final int i4) {
        if (mediaSessionRecord.mVolumeType == 1) {
            final int volumeStream = getVolumeStream(mediaSessionRecord.mAudioAttrs);
            mediaSessionRecord.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                    String str3 = str2;
                    int i5 = i;
                    int i6 = i2;
                    int i7 = i4;
                    int i8 = volumeStream;
                    int i9 = i3;
                    mediaSessionRecord2.getClass();
                    try {
                        mediaSessionRecord2.mAudioManager.setStreamVolumeForUid(i8, i9, i7, str3, i6, i5, mediaSessionRecord2.mContext.getApplicationInfo().targetSdkVersion);
                    } catch (IllegalArgumentException | SecurityException e) {
                        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i8, i9, "Cannot set volume: stream=", ", value=", ", flags=");
                        m.append(i7);
                        Slog.e("MediaSessionRecord", m.toString(), e);
                    }
                }
            });
            return;
        }
        if (mediaSessionRecord.mVolumeControlType != 2) {
            Slog.d("MediaSessionRecord", "Session does not support setting volume");
        } else {
            int max = Math.max(0, Math.min(i3, mediaSessionRecord.mMaxVolume));
            SessionCb sessionCb = mediaSessionRecord.mSessionCb;
            sessionCb.getClass();
            try {
                MediaSessionRecord mediaSessionRecord2 = MediaSessionRecord.this;
                mediaSessionRecord2.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord2.mOwnerUid, i, mediaSessionRecord2.mPackageName, str, "MediaSessionRecord:setVolumeTo", i2);
                sessionCb.mCb.onSetVolumeTo(str, i, i2, max);
            } catch (RemoteException e) {
                Slog.e("MediaSessionRecord", "Remote failure in setVolumeTo.", e);
            }
            int i5 = mediaSessionRecord.mOptimisticVolume;
            if (i5 < 0) {
                i5 = mediaSessionRecord.mCurrentVolume;
            }
            mediaSessionRecord.mOptimisticVolume = Math.max(0, Math.min(max, mediaSessionRecord.mMaxVolume));
            mediaSessionRecord.mHandler.removeCallbacks(mediaSessionRecord.mClearOptimisticVolumeRunnable);
            mediaSessionRecord.mHandler.postDelayed(mediaSessionRecord.mClearOptimisticVolumeRunnable, 1000L);
            if (i5 != mediaSessionRecord.mOptimisticVolume) {
                mediaSessionRecord.pushVolumeUpdate();
            }
            StringBuilder sb = new StringBuilder("Set optimistic volume to ");
            sb.append(mediaSessionRecord.mOptimisticVolume);
            sb.append(" max is ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, mediaSessionRecord.mMaxVolume, "MediaSessionRecord");
        }
        mediaSessionRecord.mService.notifyRemoteVolumeChanged(i4, mediaSessionRecord);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0] */
    public MediaSessionRecord(int i, int i2, int i3, String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, MediaSessionService mediaSessionService, Looper looper, int i4) {
        final int i5 = 0;
        this.mUserEngagementTimeoutExpirationRunnable = new Runnable() { // from class: com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                int i6 = i5;
                MediaSessionRecord mediaSessionRecord = this;
                switch (i6) {
                    case 0:
                        synchronized (mediaSessionRecord.mLock) {
                            mediaSessionRecord.updateUserEngagedStateIfNeededLocked(true);
                        }
                        return;
                    default:
                        boolean z = mediaSessionRecord.mOptimisticVolume != mediaSessionRecord.mCurrentVolume;
                        mediaSessionRecord.mOptimisticVolume = -1;
                        if (z) {
                            mediaSessionRecord.pushVolumeUpdate();
                            return;
                        }
                        return;
                }
            }
        };
        final int i6 = 1;
        this.mClearOptimisticVolumeRunnable = new Runnable() { // from class: com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                int i62 = i6;
                MediaSessionRecord mediaSessionRecord = this;
                switch (i62) {
                    case 0:
                        synchronized (mediaSessionRecord.mLock) {
                            mediaSessionRecord.updateUserEngagedStateIfNeededLocked(true);
                        }
                        return;
                    default:
                        boolean z = mediaSessionRecord.mOptimisticVolume != mediaSessionRecord.mCurrentVolume;
                        mediaSessionRecord.mOptimisticVolume = -1;
                        if (z) {
                            mediaSessionRecord.pushVolumeUpdate();
                            return;
                        }
                        return;
                }
            }
        };
        this.mOwnerPid = i;
        this.mOwnerUid = i2;
        this.mUserId = i3;
        this.mPackageName = str;
        this.mTag = str2;
        this.mSessionInfo = bundle;
        ControllerStub controllerStub = new ControllerStub();
        this.mController = controllerStub;
        this.mSessionToken = new MediaSession.Token(i2, controllerStub);
        this.mSession = new SessionStub();
        this.mSessionCb = new SessionCb(iSessionCallback);
        this.mService = mediaSessionService;
        Context context = mediaSessionService.getContext();
        this.mContext = context;
        this.mHandler = new MessageHandler(looper);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mAudioAttrs = DEFAULT_ATTRIBUTES;
        this.mPolicies = i4;
        this.mUgmInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mVolumeAdjustmentForRemoteGroupSessions = context.getResources().getBoolean(R.bool.config_windowShowCircularMask);
        ForegroundServiceDelegationOptions.Builder sticky = new ForegroundServiceDelegationOptions.Builder().setClientPid(i).setClientUid(i2).setClientPackageName(str).setClientAppThread((IApplicationThread) null).setSticky(false);
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "MediaSessionFgsDelegate_", "_", "_");
        m.append(str);
        this.mForegroundServiceDelegationOptions = sticky.setClientInstanceName(m.toString()).setForegroundServiceTypes(0).setDelegationService(2).build();
        iSessionCallback.asBinder().linkToDeath(this, 0);
    }

    public static int getVolumeStream(AudioAttributes audioAttributes) {
        int volumeControlStream;
        return (audioAttributes == null || (volumeControlStream = audioAttributes.getVolumeControlStream()) == Integer.MIN_VALUE) ? DEFAULT_ATTRIBUTES.getVolumeControlStream() : volumeControlStream;
    }

    public final void adjustVolume(String str, String str2, int i, int i2, boolean z, final int i3, int i4, final boolean z2) {
        final String str3;
        int i5;
        final int i6;
        final int i7 = i4 & 4;
        int i8 = (checkPlaybackActiveState(true) || isSystemPriority()) ? i4 & (-5) : i4;
        if (this.mVolumeType != 1) {
            MediaSessionService mediaSessionService = this.mService;
            if (!mediaSessionService.mIsAppCastingOn || this.mOwnerUid != mediaSessionService.mAppCastingUid) {
                if (this.mVolumeControlType == 0) {
                    Slog.d("MediaSessionRecord", "Session does not support volume adjustment");
                } else if (i3 == 101 || i3 == -100 || i3 == 100) {
                    Slog.w("MediaSessionRecord", "Muting remote playback is not supported");
                } else {
                    StringBuilder sb = new StringBuilder("adjusting volume, pkg=");
                    sb.append(str);
                    sb.append(", asSystemService=");
                    sb.append(z);
                    sb.append(", dir=");
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, i3, "MediaSessionRecord");
                    SessionCb sessionCb = this.mSessionCb;
                    MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                    try {
                        mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, i, mediaSessionRecord.mPackageName, str, "MediaSessionRecord:adjustVolume", i2);
                        if (z) {
                            sessionCb.mCb.onAdjustVolume(mediaSessionRecord.mContext.getPackageName(), Process.myPid(), 1000, i3);
                        } else {
                            sessionCb.mCb.onAdjustVolume(str, i, i2, i3);
                        }
                    } catch (RemoteException e) {
                        Slog.e("MediaSessionRecord", "Remote failure in adjustVolume.", e);
                    }
                    int i9 = this.mOptimisticVolume;
                    if (i9 < 0) {
                        i9 = this.mCurrentVolume;
                    }
                    int i10 = i9 + i3;
                    this.mOptimisticVolume = i10;
                    this.mOptimisticVolume = Math.max(0, Math.min(i10, this.mMaxVolume));
                    this.mHandler.removeCallbacks(this.mClearOptimisticVolumeRunnable);
                    this.mHandler.postDelayed(this.mClearOptimisticVolumeRunnable, 1000L);
                    if (i9 != this.mOptimisticVolume) {
                        pushVolumeUpdate();
                    }
                    Slog.d("MediaSessionRecord", "Adjusted optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
                    StringBuilder sb2 = new StringBuilder("adjustVolume volumeBefore=");
                    sb2.append(i9);
                    sb2.append(", current=");
                    GestureWakeup$$ExternalSyntheticOutline0.m(sb2, this.mCurrentVolume, "MediaSessionRecord");
                    if (i3 == 0 && i9 == this.mCurrentVolume) {
                        i8 &= -2;
                    }
                }
                this.mService.notifyRemoteVolumeChanged(i8, this);
                return;
            }
        }
        final int volumeStream = getVolumeStream(this.mAudioAttrs);
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(volumeStream, i3, "adjusting local volume, stream=", ", dir=", ", asSystemService=");
        m.append(z);
        m.append(", useSuggested=");
        m.append(z2);
        Slog.w("MediaSessionRecord", m.toString());
        if (z) {
            str3 = this.mContext.getOpPackageName();
            i6 = 1000;
            i5 = Process.myPid();
        } else {
            str3 = str2;
            i5 = i;
            i6 = i2;
        }
        final int i11 = i8;
        final int i12 = i5;
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.media.MediaSessionRecord, java.lang.Object] */
            /* JADX WARN: Type inference failed for: r1v1 */
            /* JADX WARN: Type inference failed for: r1v10 */
            /* JADX WARN: Type inference failed for: r1v2, types: [int] */
            /* JADX WARN: Type inference failed for: r1v6 */
            /* JADX WARN: Type inference failed for: r1v7 */
            /* JADX WARN: Type inference failed for: r1v8 */
            /* JADX WARN: Type inference failed for: r1v9 */
            /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.StringBuilder] */
            @Override // java.lang.Runnable
            public final void run() {
                ?? r1 = MediaSessionRecord.this;
                int i13 = volumeStream;
                int i14 = i3;
                int i15 = i11;
                boolean z3 = z2;
                int i16 = i7;
                String str4 = str3;
                int i17 = i6;
                int i18 = i12;
                r1.getClass();
                try {
                    try {
                        if (!z3) {
                            AudioManager audioManager = r1.mAudioManager;
                            int i19 = r1.mContext.getApplicationInfo().targetSdkVersion;
                            r1 = i17;
                            i17 = i19;
                            audioManager.adjustStreamVolumeForUid(i13, i14, i15, str4, i17, i18, i17);
                        } else if (AudioSystem.isStreamActive(i13, 0)) {
                            AudioManager audioManager2 = r1.mAudioManager;
                            int i20 = r1.mContext.getApplicationInfo().targetSdkVersion;
                            r1 = i17;
                            i17 = i20;
                            audioManager2.adjustSuggestedStreamVolumeForUid(i13, i14, i15, str4, i17, i18, i17);
                        } else {
                            AudioManager audioManager3 = r1.mAudioManager;
                            int i21 = i15 | i16;
                            int i22 = r1.mContext.getApplicationInfo().targetSdkVersion;
                            r1 = i17;
                            i17 = i22;
                            audioManager3.adjustSuggestedStreamVolumeForUid(Integer.MIN_VALUE, i14, i21, str4, i17, i18, i17);
                        }
                    } catch (IllegalArgumentException | SecurityException e2) {
                        e = e2;
                        ?? m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i14, i13, "Cannot adjust volume: direction=", ", stream=", ", flags=");
                        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i15, ", opPackageName=", str4, ", uid=", m2);
                        m2.append(r1);
                        m2.append(", useSuggested=");
                        m2.append(z3);
                        m2.append(", previousFlagPlaySound=");
                        m2.append(i16);
                        Slog.e("MediaSessionRecord", m2.toString(), e);
                    }
                } catch (IllegalArgumentException | SecurityException e3) {
                    e = e3;
                    r1 = i17;
                    ?? m22 = ArrayUtils$$ExternalSyntheticOutline0.m(i14, i13, "Cannot adjust volume: direction=", ", stream=", ", flags=");
                    AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(i15, ", opPackageName=", str4, ", uid=", m22);
                    m22.append(r1);
                    m22.append(", useSuggested=");
                    m22.append(z3);
                    m22.append(", previousFlagPlaySound=");
                    m22.append(i16);
                    Slog.e("MediaSessionRecord", m22.toString(), e);
                }
            }
        });
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        MediaSessionService mediaSessionService = this.mService;
        synchronized (mediaSessionService.mLock) {
            mediaSessionService.destroySessionLocked(this);
        }
    }

    public final boolean canHandleVolumeKey() {
        if (this.mVolumeType == 1) {
            Slog.d("MediaSessionRecord", "Local MediaSessionRecord can handle volume key");
            return true;
        }
        if (this.mVolumeControlType == 0) {
            Slog.d("MediaSessionRecord", "Local MediaSessionRecord with FIXED volume control can't handle volume key");
            return false;
        }
        if (this.mVolumeAdjustmentForRemoteGroupSessions) {
            Slog.d("MediaSessionRecord", "Volume adjustment for remote group sessions allowed so MediaSessionRecord can handle volume key");
            return true;
        }
        List<RoutingSessionInfo> routingSessions = MediaRouter2Manager.getInstance(this.mContext).getRoutingSessions(this.mPackageName);
        Slog.d("MediaSessionRecord", "Found " + routingSessions.size() + " routing sessions for package name " + this.mPackageName);
        boolean z = false;
        boolean z2 = true;
        for (RoutingSessionInfo routingSessionInfo : routingSessions) {
            Slog.d("MediaSessionRecord", "Found routingSessionInfo: " + routingSessionInfo);
            if (!routingSessionInfo.isSystemSession()) {
                if (routingSessionInfo.getVolumeHandling() == 0) {
                    z2 = false;
                }
                z = true;
            }
        }
        if (!z) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Package "), this.mPackageName, " has a remote media session but no associated routing session", "MediaSessionRecord");
        }
        return z && z2;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean checkPlaybackActiveState(boolean z) {
        PlaybackState playbackState = this.mPlaybackState;
        return playbackState != null && playbackState.isActive() == z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void close() {
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiEnd(4, Binder.getCallingUid(), Binder.getCallingPid());
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                this.mMetadata = null;
                this.mQueue = null;
                this.mSessionCb.mCb.asBinder().unlinkToDeath(this, 0);
                this.mDestroyed = true;
                this.mPlaybackState = null;
                updateUserEngagedStateIfNeededLocked(true);
                this.mHandler.post(9);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(this.mTag);
        m.append(" ");
        m.append(this);
        printWriter.println(m.toString());
        String m2 = AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, "  ");
        StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(m2, "ownerPid=");
        m3.append(this.mOwnerPid);
        m3.append(", ownerUid=");
        m3.append(this.mOwnerUid);
        m3.append(", userId=");
        StringBuilder m4 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(m3, this.mUserId, printWriter, m2, "package=");
        m4.append(this.mPackageName);
        printWriter.println(m4.toString());
        printWriter.println(m2 + "launchIntent=" + this.mLaunchIntent);
        printWriter.println(m2 + "mediaButtonReceiver=" + this.mMediaButtonReceiverHolder);
        StringBuilder sb = new StringBuilder();
        sb.append(m2);
        sb.append("active=");
        StringBuilder m5 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb, this.mIsActive, printWriter, m2, "flags=");
        m5.append(this.mFlags);
        printWriter.println(m5.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m2);
        sb2.append("rating type=");
        StringBuilder m6 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb2, this.mRatingType, printWriter, m2, "controllers: ");
        m6.append(this.mControllerCallbackHolders.size());
        printWriter.println(m6.toString());
        Iterator it = this.mControllerCallbackHolders.iterator();
        while (it.hasNext()) {
            ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
            StringBuilder m7 = Preconditions$$ExternalSyntheticOutline0.m(m2, " ");
            m7.append(iSessionControllerCallbackHolder.mPackageName);
            m7.append(iSessionControllerCallbackHolder.mDeathMonitor);
            printWriter.println(m7.toString());
        }
        StringBuilder m8 = Preconditions$$ExternalSyntheticOutline0.m(m2, "state=");
        PlaybackState playbackState = this.mPlaybackState;
        m8.append(playbackState == null ? null : playbackState.toString());
        printWriter.println(m8.toString());
        printWriter.println(m2 + "audioAttrs=" + this.mAudioAttrs);
        PrintWriter append = printWriter.append((CharSequence) m2).append("volumeType=");
        int i = this.mVolumeType;
        PrintWriter append2 = append.append(i != 1 ? i != 2 ? TextUtils.formatSimple("unknown(%d)", new Object[]{Integer.valueOf(i)}) : "REMOTE" : "LOCAL").append(", controlType=");
        int i2 = this.mVolumeControlType;
        append2.append(i2 != 0 ? i2 != 1 ? i2 != 2 ? TextUtils.formatSimple("unknown(%d)", new Object[]{Integer.valueOf(i2)}) : "ABSOLUTE" : "RELATIVE" : "FIXED").append(", max=").append(Integer.toString(this.mMaxVolume)).append(", current=").append(Integer.toString(this.mCurrentVolume)).append(", volumeControlId=").append(this.mVolumeControlId).println();
        printWriter.println(m2 + "metadata: " + this.mMetadataDescription);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(m2);
        sb3.append("queueTitle=");
        sb3.append((Object) this.mQueueTitle);
        sb3.append(", size=");
        List list = this.mQueue;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb3, list == null ? 0 : list.size(), printWriter);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void expireTempEngaged() {
        this.mHandler.post(this.mUserEngagementTimeoutExpirationRunnable);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions() {
        return this.mForegroundServiceDelegationOptions;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final String getPackageName() {
        return this.mPackageName;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getSessionPolicies() {
        int i;
        synchronized (this.mLock) {
            i = this.mPolicies;
        }
        return i;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getUid() {
        return this.mOwnerUid;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getUserId() {
        return this.mUserId;
    }

    public final MediaController.PlaybackInfo getVolumeAttributes() {
        synchronized (this.mLock) {
            try {
                int i = this.mVolumeType;
                if (i != 2) {
                    AudioAttributes audioAttributes = this.mAudioAttrs;
                    int volumeStream = getVolumeStream(audioAttributes);
                    return new MediaController.PlaybackInfo(i, 2, this.mAudioManager.getStreamMaxVolume(volumeStream), this.mAudioManager.getStreamVolume(volumeStream), audioAttributes, null);
                }
                int i2 = this.mOptimisticVolume;
                if (i2 == -1) {
                    i2 = this.mCurrentVolume;
                }
                return new MediaController.PlaybackInfo(this.mVolumeType, this.mVolumeControlType, this.mMaxVolume, i2, this.mAudioAttrs, this.mVolumeControlId);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isActive() {
        return this.mIsActive && !this.mDestroyed;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isLinkedToNotification(Notification notification) {
        return notification.isMediaNotification() && Objects.equals(notification.extras.getParcelable("android.mediaSession", MediaSession.Token.class), this.mSessionToken);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isSystemPriority() {
        return (this.mFlags & 65536) != 0;
    }

    public final void performOnCallbackHolders(String str, ControllerCallbackCall controllerCallbackCall) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mControllerCallbackHolders.iterator();
        while (it.hasNext()) {
            ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
            try {
                controllerCallbackCall.performOn(iSessionControllerCallbackHolder);
            } catch (RemoteException | NoSuchElementException e) {
                arrayList.add(iSessionControllerCallbackHolder);
                Slog.v("MediaSessionRecord", "Exception while executing: ".concat(str) + ", this=" + this + ", callback package=" + iSessionControllerCallbackHolder.mPackageName + ", exception=" + e);
            }
        }
        synchronized (this.mLock) {
            this.mControllerCallbackHolders.removeAll(arrayList);
        }
    }

    public final void pushVolumeUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                performOnCallbackHolders("pushVolumeUpdate", new MediaSessionRecord$$ExternalSyntheticLambda2(0, getVolumeAttributes()));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendMediaButton(String str, int i, int i2, boolean z, KeyEvent keyEvent, int i3, ResultReceiver resultReceiver) {
        SessionCb sessionCb = this.mSessionCb;
        sessionCb.getClass();
        try {
            boolean isMediaSessionKey = KeyEvent.isMediaSessionKey(keyEvent.getKeyCode());
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            if (isMediaSessionKey) {
                mediaSessionRecord.mService.tempAllowlistTargetPkgIfPossible(mediaSessionRecord.mOwnerUid, i, mediaSessionRecord.mPackageName, str, "action=" + KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + KeyEvent.keyCodeToString(keyEvent.getKeyCode()), i2);
            }
            if (z) {
                ISessionCallback iSessionCallback = sessionCb.mCb;
                String packageName = mediaSessionRecord.mContext.getPackageName();
                int myPid = Process.myPid();
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                iSessionCallback.onMediaButton(packageName, myPid, 1000, intent, i3, resultReceiver);
            } else {
                ISessionCallback iSessionCallback2 = sessionCb.mCb;
                Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent2.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                iSessionCallback2.onMediaButton(str, i, i2, intent2, i3, resultReceiver);
            }
            return true;
        } catch (RemoteException e) {
            Slog.e("MediaSessionRecord", "Remote failure in sendMediaRequest.", e);
            return false;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPackageName);
        sb.append("/");
        sb.append(this.mTag);
        sb.append("/");
        sb.append(this.mUniqueId);
        sb.append(" (userId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, sb, ")");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateUserEngagedStateIfNeededLocked(boolean r9) {
        /*
            r8 = this;
            boolean r0 = com.android.media.flags.Flags.enableNotifyingActivityManagerWithMediaSessionStatusChange()
            if (r0 != 0) goto L7
            return
        L7:
            int r0 = r8.mUserEngagementState
            boolean r1 = r8.isActive()
            r2 = 0
            r3 = 1
            r4 = 2
            if (r1 == 0) goto L39
            android.media.session.PlaybackState r1 = r8.mPlaybackState
            if (r1 == 0) goto L39
            boolean r1 = r8.mDestroyed
            if (r1 == 0) goto L1b
            goto L39
        L1b:
            boolean r1 = r8.isActive()
            if (r1 == 0) goto L2b
            android.media.session.PlaybackState r1 = r8.mPlaybackState
            boolean r1 = r1.isActive()
            if (r1 == 0) goto L2b
            r9 = r2
            goto L3a
        L2b:
            android.media.session.PlaybackState r1 = r8.mPlaybackState
            int r1 = r1.getState()
            if (r1 != r4) goto L39
            if (r0 == 0) goto L37
            if (r9 != 0) goto L39
        L37:
            r9 = r3
            goto L3a
        L39:
            r9 = r4
        L3a:
            if (r0 != r9) goto L3d
            return
        L3d:
            r8.mUserEngagementState = r9
            if (r9 != r3) goto L4c
            com.android.server.media.MediaSessionRecord$MessageHandler r1 = r8.mHandler
            com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0 r5 = r8.mUserEngagementTimeoutExpirationRunnable
            r6 = 600000(0x927c0, double:2.964394E-318)
            r1.postDelayed(r5, r6)
            goto L53
        L4c:
            com.android.server.media.MediaSessionRecord$MessageHandler r1 = r8.mHandler
            com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda0 r5 = r8.mUserEngagementTimeoutExpirationRunnable
            r1.removeCallbacks(r5)
        L53:
            if (r0 == r4) goto L57
            r0 = r3
            goto L58
        L57:
            r0 = r2
        L58:
            if (r9 == r4) goto L5b
            r2 = r3
        L5b:
            if (r0 == r2) goto L67
            com.android.server.media.MediaSessionRecord$MessageHandler r9 = r8.mHandler
            com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda4 r0 = new com.android.server.media.MediaSessionRecord$$ExternalSyntheticLambda4
            r0.<init>()
            r9.post(r0)
        L67:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.MediaSessionRecord.updateUserEngagedStateIfNeededLocked(boolean):void");
    }
}
