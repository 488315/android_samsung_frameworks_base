package com.android.server.media;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
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
import android.os.DeadObjectException;
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
import android.view.KeyEvent;
import com.android.server.LocalServices;
import com.android.server.media.MediaSessionRecord;
import com.android.server.uri.UriGrantsManagerInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class MediaSessionRecord implements IBinder.DeathRecipient, MediaSessionRecordImpl {
    public AudioAttributes mAudioAttrs;
    public AudioManager mAudioManager;
    public final Context mContext;
    public final ControllerStub mController;
    public Bundle mExtras;
    public long mFlags;
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
    public final int mUserId;
    public final boolean mVolumeAdjustmentForRemoteGroupSessions;
    public String mVolumeControlId;
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final boolean DEBUG = Log.isLoggable("MediaSessionRecord", 3);
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
    public final Runnable mClearOptimisticVolumeRunnable = new Runnable() { // from class: com.android.server.media.MediaSessionRecord.3
        @Override // java.lang.Runnable
        public void run() {
            boolean z = MediaSessionRecord.this.mOptimisticVolume != MediaSessionRecord.this.mCurrentVolume;
            MediaSessionRecord.this.mOptimisticVolume = -1;
            if (z) {
                MediaSessionRecord.this.pushVolumeUpdate();
            }
        }
    };

    public static int getVolumeStream(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return DEFAULT_ATTRIBUTES.getVolumeControlStream();
        }
        int volumeControlStream = audioAttributes.getVolumeControlStream();
        return volumeControlStream == Integer.MIN_VALUE ? DEFAULT_ATTRIBUTES.getVolumeControlStream() : volumeControlStream;
    }

    public MediaSessionRecord(int i, int i2, int i3, String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, MediaSessionService mediaSessionService, Looper looper, int i4) {
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
        SessionCb sessionCb = new SessionCb(iSessionCallback);
        this.mSessionCb = sessionCb;
        this.mService = mediaSessionService;
        Context context = mediaSessionService.getContext();
        this.mContext = context;
        this.mHandler = new MessageHandler(looper);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mAudioAttrs = DEFAULT_ATTRIBUTES;
        this.mPolicies = i4;
        this.mUgmInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mVolumeAdjustmentForRemoteGroupSessions = context.getResources().getBoolean(R.bool.config_sustainedPerformanceModeSupported);
        sessionCb.mCb.asBinder().linkToDeath(this, 0);
    }

    public ISession getSessionBinder() {
        return this.mSession;
    }

    public MediaSession.Token getSessionToken() {
        return this.mSessionToken;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public String getPackageName() {
        return this.mPackageName;
    }

    public MediaButtonReceiverHolder getMediaButtonReceiver() {
        return this.mMediaButtonReceiverHolder;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUid() {
        return this.mOwnerUid;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUserId() {
        return this.mUserId;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isSystemPriority() {
        return (this.mFlags & 65536) != 0;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void adjustVolume(String str, String str2, int i, int i2, boolean z, int i3, int i4, boolean z2) {
        int i5 = i4 & 4;
        int i6 = (checkPlaybackActiveState(true) || isSystemPriority()) ? i4 & (-5) : i4;
        if (this.mVolumeType == 1 || shouldNotSendKeyToAppCastingSession()) {
            postAdjustLocalVolume(getVolumeStream(this.mAudioAttrs), i3, i6, str2, i, i2, z, z2, i5);
            return;
        }
        if (this.mVolumeControlType == 0) {
            Log.d("MediaSessionRecord", "Session does not support volume adjustment");
        } else if (i3 == 101 || i3 == -100 || i3 == 100) {
            Log.w("MediaSessionRecord", "Muting remote playback is not supported");
        } else {
            boolean z3 = DEBUG;
            if (z3) {
                Log.w("MediaSessionRecord", "adjusting volume, pkg=" + str + ", asSystemService=" + z + ", dir=" + i3);
            }
            this.mSessionCb.adjustVolume(str, i, i2, z, i3);
            int i7 = this.mOptimisticVolume;
            if (i7 < 0) {
                i7 = this.mCurrentVolume;
            }
            int i8 = i7 + i3;
            this.mOptimisticVolume = i8;
            this.mOptimisticVolume = Math.max(0, Math.min(i8, this.mMaxVolume));
            this.mHandler.removeCallbacks(this.mClearOptimisticVolumeRunnable);
            this.mHandler.postDelayed(this.mClearOptimisticVolumeRunnable, 1000L);
            if (i7 != this.mOptimisticVolume) {
                pushVolumeUpdate();
            }
            if (z3) {
                Log.d("MediaSessionRecord", "Adjusted optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
            }
            Log.d("MediaSessionRecord", "adjusting pkg=" + str + ", asSystemService=" + z + ", dir=" + i3 + ", volumeBefore=" + i7 + ", current=" + this.mCurrentVolume + ", optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
        }
        this.mService.notifyRemoteVolumeChanged(i6, this);
    }

    public final void setVolumeTo(String str, final String str2, final int i, final int i2, final int i3, final int i4) {
        if (this.mVolumeType == 1) {
            final int volumeStream = getVolumeStream(this.mAudioAttrs);
            this.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaSessionRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        MediaSessionRecord.this.mAudioManager.setStreamVolumeForUid(volumeStream, i3, i4, str2, i2, i, MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                    } catch (IllegalArgumentException | SecurityException e) {
                        Log.e("MediaSessionRecord", "Cannot set volume: stream=" + volumeStream + ", value=" + i3 + ", flags=" + i4, e);
                    }
                }
            });
            return;
        }
        if (this.mVolumeControlType != 2) {
            Log.d("MediaSessionRecord", "Session does not support setting volume");
        } else {
            int max = Math.max(0, Math.min(i3, this.mMaxVolume));
            this.mSessionCb.setVolumeTo(str, i, i2, max);
            int i5 = this.mOptimisticVolume;
            if (i5 < 0) {
                i5 = this.mCurrentVolume;
            }
            this.mOptimisticVolume = Math.max(0, Math.min(max, this.mMaxVolume));
            this.mHandler.removeCallbacks(this.mClearOptimisticVolumeRunnable);
            this.mHandler.postDelayed(this.mClearOptimisticVolumeRunnable, 1000L);
            if (i5 != this.mOptimisticVolume) {
                pushVolumeUpdate();
            }
            Log.d("MediaSessionRecord", "Set optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
        }
        this.mService.notifyRemoteVolumeChanged(i4, this);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isActive() {
        return this.mIsActive && !this.mDestroyed;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean checkPlaybackActiveState(boolean z) {
        PlaybackState playbackState = this.mPlaybackState;
        return playbackState != null && playbackState.isActive() == z;
    }

    public boolean isPlaybackTypeLocal() {
        return this.mVolumeType == 1;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        this.mService.onSessionDied(this);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl, java.lang.AutoCloseable
    public void close() {
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiEnd(4, Binder.getCallingUid(), Binder.getCallingPid());
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            this.mSessionCb.mCb.asBinder().unlinkToDeath(this, 0);
            this.mDestroyed = true;
            this.mPlaybackState = null;
            this.mHandler.post(9);
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public boolean sendMediaButton(String str, int i, int i2, boolean z, KeyEvent keyEvent, int i3, ResultReceiver resultReceiver) {
        return this.mSessionCb.sendMediaButton(str, i, i2, z, keyEvent, i3, resultReceiver);
    }

    public boolean canHandleVolumeKey() {
        if (isPlaybackTypeLocal()) {
            return true;
        }
        if (this.mVolumeControlType == 0) {
            return false;
        }
        if (this.mVolumeAdjustmentForRemoteGroupSessions) {
            return true;
        }
        boolean z = true;
        boolean z2 = false;
        for (RoutingSessionInfo routingSessionInfo : MediaRouter2Manager.getInstance(this.mContext).getRoutingSessions(this.mPackageName)) {
            if (!routingSessionInfo.isSystemSession()) {
                if (routingSessionInfo.getVolumeHandling() == 0) {
                    z2 = true;
                    z = false;
                } else {
                    z2 = true;
                }
            }
        }
        if (!z2) {
            Log.d("MediaSessionRecord", "Package " + this.mPackageName + " has a remote media session but no associated routing session");
        }
        return z2 && z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getSessionPolicies() {
        int i;
        synchronized (this.mLock) {
            i = this.mPolicies;
        }
        return i;
    }

    public void setSessionPolicies(int i) {
        synchronized (this.mLock) {
            this.mPolicies = i;
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + this.mTag + " " + this);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        String sb2 = sb.toString();
        printWriter.println(sb2 + "ownerPid=" + this.mOwnerPid + ", ownerUid=" + this.mOwnerUid + ", userId=" + this.mUserId);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("package=");
        sb3.append(this.mPackageName);
        printWriter.println(sb3.toString());
        printWriter.println(sb2 + "launchIntent=" + this.mLaunchIntent);
        printWriter.println(sb2 + "mediaButtonReceiver=" + this.mMediaButtonReceiverHolder);
        printWriter.println(sb2 + "active=" + this.mIsActive);
        printWriter.println(sb2 + "flags=" + this.mFlags);
        printWriter.println(sb2 + "rating type=" + this.mRatingType);
        printWriter.println(sb2 + "controllers: " + this.mControllerCallbackHolders.size());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append("state=");
        PlaybackState playbackState = this.mPlaybackState;
        sb4.append(playbackState == null ? null : playbackState.toString());
        printWriter.println(sb4.toString());
        printWriter.println(sb2 + "audioAttrs=" + this.mAudioAttrs);
        printWriter.append((CharSequence) sb2).append("volumeType=").append(toVolumeTypeString(this.mVolumeType)).append(", controlType=").append(toVolumeControlTypeString(this.mVolumeControlType)).append(", max=").append(Integer.toString(this.mMaxVolume)).append(", current=").append(Integer.toString(this.mCurrentVolume)).append(", volumeControlId=").append(this.mVolumeControlId).println();
        printWriter.println(sb2 + "metadata: " + this.mMetadataDescription);
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("queueTitle=");
        sb5.append((Object) this.mQueueTitle);
        sb5.append(", size=");
        List list = this.mQueue;
        sb5.append(list == null ? 0 : list.size());
        printWriter.println(sb5.toString());
        printWriter.println(sb2 + "mControllerCallbackHolders:");
        synchronized (this.mLock) {
            Iterator it = this.mControllerCallbackHolders.iterator();
            while (it.hasNext()) {
                printWriter.print(sb2 + sb2 + ((ISessionControllerCallbackHolder) it.next()).mPackageName + " ");
                printWriter.println();
            }
        }
    }

    public static String toVolumeControlTypeString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? TextUtils.formatSimple("unknown(%d)", new Object[]{Integer.valueOf(i)}) : "ABSOLUTE" : "RELATIVE" : "FIXED";
    }

    public static String toVolumeTypeString(int i) {
        return i != 1 ? i != 2 ? TextUtils.formatSimple("unknown(%d)", new Object[]{Integer.valueOf(i)}) : "REMOTE" : "LOCAL";
    }

    public String toString() {
        return this.mPackageName + "/" + this.mTag + " (userId=" + this.mUserId + ")";
    }

    public final void postAdjustLocalVolume(final int i, final int i2, final int i3, String str, int i4, int i5, boolean z, final boolean z2, final int i6) {
        final String str2;
        final int i7;
        final int i8;
        if (DEBUG) {
            Log.w("MediaSessionRecord", "adjusting local volume, stream=" + i + ", dir=" + i2 + ", asSystemService=" + z + ", useSuggested=" + z2);
        }
        if (z) {
            String opPackageName = this.mContext.getOpPackageName();
            i7 = Process.myPid();
            i8 = 1000;
            str2 = opPackageName;
        } else {
            str2 = str;
            i7 = i4;
            i8 = i5;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaSessionRecord.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (z2) {
                        if (AudioSystem.isStreamActive(i, 0)) {
                            MediaSessionRecord.this.mAudioManager.adjustSuggestedStreamVolumeForUid(i, i2, i3, str2, i8, i7, MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                        } else {
                            MediaSessionRecord.this.mAudioManager.adjustSuggestedStreamVolumeForUid(Integer.MIN_VALUE, i2, i6 | i3, str2, i8, i7, MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                        }
                    } else {
                        MediaSessionRecord.this.mAudioManager.adjustStreamVolumeForUid(i, i2, i3, str2, i8, i7, MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                    }
                } catch (IllegalArgumentException | SecurityException e) {
                    Log.e("MediaSessionRecord", "Cannot adjust volume: direction=" + i2 + ", stream=" + i + ", flags=" + i3 + ", opPackageName=" + str2 + ", uid=" + i8 + ", useSuggested=" + z2 + ", previousFlagPlaySound=" + i6, e);
                }
            }
        });
    }

    public final void logCallbackException(String str, ISessionControllerCallbackHolder iSessionControllerCallbackHolder, Exception exc) {
        Log.v("MediaSessionRecord", str + ", this=" + this + ", callback package=" + iSessionControllerCallbackHolder.mPackageName + ", exception=" + exc);
    }

    public final void pushPlaybackStateUpdate() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            PlaybackState playbackState = this.mPlaybackState;
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onPlaybackStateChanged(playbackState);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushPlaybackStateUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushPlaybackStateUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushMetadataUpdate() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            MediaMetadata mediaMetadata = this.mMetadata;
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onMetadataChanged(mediaMetadata);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushMetadataUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushMetadataUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushQueueUpdate() {
        ParceledListSlice parceledListSlice;
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            ArrayList arrayList = this.mQueue == null ? null : new ArrayList(this.mQueue);
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList2 = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                if (arrayList != null) {
                    parceledListSlice = new ParceledListSlice(arrayList);
                    parceledListSlice.setInlineCountLimit(1);
                } else {
                    parceledListSlice = null;
                }
                try {
                    iSessionControllerCallbackHolder.mCallback.onQueueChanged(parceledListSlice);
                } catch (DeadObjectException e) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushQueueUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushQueueUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList2 != null) {
                this.mControllerCallbackHolders.removeAll(arrayList2);
            }
        }
    }

    public final void pushQueueTitleUpdate() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            CharSequence charSequence = this.mQueueTitle;
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onQueueTitleChanged(charSequence);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushQueueTitleUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushQueueTitleUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushExtrasUpdate() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            Bundle bundle = this.mExtras;
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onExtrasChanged(bundle);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushExtrasUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushExtrasUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushVolumeUpdate() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            MediaController.PlaybackInfo volumeAttributes = getVolumeAttributes();
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onVolumeInfoChanged(volumeAttributes);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushVolumeUpdate", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushVolumeUpdate", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushEvent(String str, Bundle bundle) {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            Iterator it = this.mControllerCallbackHolders.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                try {
                    iSessionControllerCallbackHolder.mCallback.onEvent(str, bundle);
                } catch (DeadObjectException e) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(iSessionControllerCallbackHolder);
                    logCallbackException("Removing dead callback in pushEvent", iSessionControllerCallbackHolder, e);
                } catch (RemoteException e2) {
                    logCallbackException("unexpected exception in pushEvent", iSessionControllerCallbackHolder, e2);
                }
            }
            if (arrayList != null) {
                this.mControllerCallbackHolders.removeAll(arrayList);
            }
        }
    }

    public final void pushSessionDestroyed() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                Iterator it = this.mControllerCallbackHolders.iterator();
                while (it.hasNext()) {
                    ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                    try {
                        iSessionControllerCallbackHolder.mCallback.asBinder().unlinkToDeath(iSessionControllerCallbackHolder.mDeathMonitor, 0);
                        iSessionControllerCallbackHolder.mCallback.onSessionDestroyed();
                    } catch (DeadObjectException e) {
                        logCallbackException("Removing dead callback in pushSessionDestroyed", iSessionControllerCallbackHolder, e);
                    } catch (RemoteException e2) {
                        logCallbackException("unexpected exception in pushSessionDestroyed", iSessionControllerCallbackHolder, e2);
                    } catch (NoSuchElementException e3) {
                        logCallbackException("error unlinking to binder death", iSessionControllerCallbackHolder, e3);
                    }
                }
                this.mControllerCallbackHolders.clear();
            }
        }
    }

    public final PlaybackState getStateWithUpdatedPosition() {
        synchronized (this.mLock) {
            PlaybackState playbackState = null;
            if (this.mDestroyed) {
                return null;
            }
            PlaybackState playbackState2 = this.mPlaybackState;
            long j = this.mDuration;
            if (playbackState2 != null && (playbackState2.getState() == 3 || playbackState2.getState() == 4 || playbackState2.getState() == 5)) {
                long lastPositionUpdateTime = playbackState2.getLastPositionUpdateTime();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (lastPositionUpdateTime > 0) {
                    long playbackSpeed = ((long) (playbackState2.getPlaybackSpeed() * (elapsedRealtime - lastPositionUpdateTime))) + playbackState2.getPosition();
                    long j2 = (j < 0 || playbackSpeed <= j) ? playbackSpeed < 0 ? 0L : playbackSpeed : j;
                    PlaybackState.Builder builder = new PlaybackState.Builder(playbackState2);
                    builder.setState(playbackState2.getState(), j2, playbackState2.getPlaybackSpeed(), elapsedRealtime);
                    playbackState = builder.build();
                }
            }
            return playbackState == null ? playbackState2 : playbackState;
        }
    }

    public final int getControllerHolderIndexForCb(ISessionControllerCallback iSessionControllerCallback) {
        IBinder asBinder = iSessionControllerCallback.asBinder();
        for (int size = this.mControllerCallbackHolders.size() - 1; size >= 0; size--) {
            if (asBinder.equals(((ISessionControllerCallbackHolder) this.mControllerCallbackHolders.get(size)).mCallback.asBinder())) {
                return size;
            }
        }
        return -1;
    }

    public final MediaController.PlaybackInfo getVolumeAttributes() {
        synchronized (this.mLock) {
            int i = this.mVolumeType;
            if (i == 2) {
                int i2 = this.mOptimisticVolume;
                if (i2 == -1) {
                    i2 = this.mCurrentVolume;
                }
                return new MediaController.PlaybackInfo(this.mVolumeType, this.mVolumeControlType, this.mMaxVolume, i2, this.mAudioAttrs, this.mVolumeControlId);
            }
            AudioAttributes audioAttributes = this.mAudioAttrs;
            int volumeStream = getVolumeStream(audioAttributes);
            return new MediaController.PlaybackInfo(i, 2, this.mAudioManager.getStreamMaxVolume(volumeStream), this.mAudioManager.getStreamVolume(volumeStream), audioAttributes, null);
        }
    }

    public static boolean componentNameExists(ComponentName componentName, Context context, int i) {
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.addFlags(268435456);
        intent.setComponent(componentName);
        return !context.getPackageManager().queryBroadcastReceiversAsUser(intent, PackageManager.ResolveInfoFlags.of(0L), UserHandle.of(i)).isEmpty();
    }

    public final class SessionStub extends ISession.Stub {
        public SessionStub() {
        }

        public void destroySession() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.mService.onSessionDied(MediaSessionRecord.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendEvent(String str, Bundle bundle) {
            MediaSessionRecord.this.mHandler.post(6, str, bundle == null ? null : new Bundle(bundle));
        }

        public ISessionController getController() {
            return MediaSessionRecord.this.mController;
        }

        public void setActive(boolean z) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (z) {
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiBegin(4, callingUid, callingPid);
            } else {
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).logFgsApiEnd(4, callingUid, callingPid);
            }
            MediaSessionRecord.this.mIsActive = z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.mService.onSessionActiveStateChanged(MediaSessionRecord.this);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                MediaSessionRecord.this.mHandler.post(7);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void setFlags(int i) {
            int i2 = 65536 & i;
            if (i2 != 0) {
                MediaSessionRecord.this.mService.enforcePhoneStatePermission(Binder.getCallingPid(), Binder.getCallingUid());
            }
            MediaSessionRecord.this.mFlags = i;
            if (i2 != 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord.this.mService.setGlobalPrioritySession(MediaSessionRecord.this);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            MediaSessionRecord.this.mHandler.post(7);
        }

        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if ((MediaSessionRecord.this.mPolicies & 1) != 0) {
                    return;
                }
                if (pendingIntent != null && pendingIntent.isActivity()) {
                    Log.w("MediaSessionRecord", "Ignoring invalid media button receiver targeting an activity: " + pendingIntent);
                    return;
                }
                MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
                mediaSessionRecord.mMediaButtonReceiverHolder = MediaButtonReceiverHolder.create(mediaSessionRecord.mUserId, pendingIntent, MediaSessionRecord.this.mPackageName);
                MediaSessionRecord.this.mService.onMediaButtonReceiverChanged(MediaSessionRecord.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setMediaButtonBroadcastReceiver(ComponentName componentName) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (componentName != null) {
                try {
                    if (!TextUtils.equals(MediaSessionRecord.this.mPackageName, componentName.getPackageName())) {
                        EventLog.writeEvent(1397638484, "238177121", -1, "");
                        throw new IllegalArgumentException("receiver does not belong to package name provided to MediaSessionRecord. Pkg = " + MediaSessionRecord.this.mPackageName + ", Receiver Pkg = " + componentName.getPackageName());
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if ((1 & MediaSessionRecord.this.mPolicies) != 0) {
                return;
            }
            if (!MediaSessionRecord.componentNameExists(componentName, MediaSessionRecord.this.mContext, MediaSessionRecord.this.mUserId)) {
                if (CompatChanges.isChangeEnabled(270049379L, callingUid)) {
                    throw new IllegalArgumentException("Invalid component name: " + componentName);
                }
                Log.w("MediaSessionRecord", "setMediaButtonBroadcastReceiver(): Ignoring invalid component name=" + componentName);
                return;
            }
            MediaSessionRecord mediaSessionRecord = MediaSessionRecord.this;
            mediaSessionRecord.mMediaButtonReceiverHolder = MediaButtonReceiverHolder.create(mediaSessionRecord.mUserId, componentName);
            MediaSessionRecord.this.mService.onMediaButtonReceiverChanged(MediaSessionRecord.this);
        }

        public void setLaunchPendingIntent(PendingIntent pendingIntent) {
            MediaSessionRecord.this.mLaunchIntent = pendingIntent;
        }

        public void setMetadata(MediaMetadata mediaMetadata, long j, String str) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mDuration = j;
                MediaSessionRecord.this.mMetadataDescription = str;
                MediaSessionRecord.this.mMetadata = sanitizeMediaMetadata(mediaMetadata);
            }
            MediaSessionRecord.this.mHandler.post(1);
        }

        public final MediaMetadata sanitizeMediaMetadata(MediaMetadata mediaMetadata) {
            if (mediaMetadata == null) {
                return null;
            }
            MediaMetadata.Builder builder = new MediaMetadata.Builder(mediaMetadata);
            for (String str : MediaSessionRecord.ART_URIS) {
                String string = mediaMetadata.getString(str);
                if (!TextUtils.isEmpty(string)) {
                    Uri parse = Uri.parse(string);
                    if ("content".equals(parse.getScheme())) {
                        try {
                            MediaSessionRecord.this.mUgmInternal.checkGrantUriPermission(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), ContentProvider.getUriWithoutUserId(parse), 1, ContentProvider.getUserIdFromUri(parse, MediaSessionRecord.this.getUserId()));
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

        public void setPlaybackState(PlaybackState playbackState) {
            boolean z = false;
            int state = MediaSessionRecord.this.mPlaybackState == null ? 0 : MediaSessionRecord.this.mPlaybackState.getState();
            int state2 = playbackState == null ? 0 : playbackState.getState();
            if (MediaSessionRecord.ALWAYS_PRIORITY_STATES.contains(Integer.valueOf(state2)) || (!MediaSessionRecord.TRANSITION_PRIORITY_STATES.contains(Integer.valueOf(state)) && MediaSessionRecord.TRANSITION_PRIORITY_STATES.contains(Integer.valueOf(state2)))) {
                z = true;
            }
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mPlaybackState = playbackState;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.mService.onSessionPlaybackStateChanged(MediaSessionRecord.this, z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                MediaSessionRecord.this.mHandler.post(2);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void resetQueue() {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mQueue = null;
            }
            MediaSessionRecord.this.mHandler.post(3);
        }

        public IBinder getBinderForSetQueue() {
            return new ParcelableListBinder(MediaSession.QueueItem.class, new Consumer() { // from class: com.android.server.media.MediaSessionRecord$SessionStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MediaSessionRecord.SessionStub.this.lambda$getBinderForSetQueue$0((List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getBinderForSetQueue$0(List list) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mQueue = list;
            }
            MediaSessionRecord.this.mHandler.post(3);
        }

        public void setQueueTitle(CharSequence charSequence) {
            MediaSessionRecord.this.mQueueTitle = charSequence;
            MediaSessionRecord.this.mHandler.post(4);
        }

        public void setExtras(Bundle bundle) {
            synchronized (MediaSessionRecord.this.mLock) {
                MediaSessionRecord.this.mExtras = bundle == null ? null : new Bundle(bundle);
            }
            MediaSessionRecord.this.mHandler.post(5);
        }

        public void setRatingType(int i) {
            MediaSessionRecord.this.mRatingType = i;
        }

        public void setCurrentVolume(int i) {
            MediaSessionRecord.this.mCurrentVolume = i;
            MediaSessionRecord.this.mHandler.post(8);
        }

        public void setPlaybackToLocal(AudioAttributes audioAttributes) {
            boolean z;
            synchronized (MediaSessionRecord.this.mLock) {
                z = MediaSessionRecord.this.mVolumeType == 2;
                MediaSessionRecord.this.mVolumeType = 1;
                MediaSessionRecord.this.mVolumeControlId = null;
                if (audioAttributes != null) {
                    MediaSessionRecord.this.mAudioAttrs = audioAttributes;
                } else {
                    Log.e("MediaSessionRecord", "Received null audio attributes, using existing attributes");
                }
            }
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord.this.mService.onSessionPlaybackTypeChanged(MediaSessionRecord.this);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    MediaSessionRecord.this.mHandler.post(8);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public void setPlaybackToRemote(int i, int i2, String str) {
            boolean z;
            synchronized (MediaSessionRecord.this.mLock) {
                z = true;
                if (MediaSessionRecord.this.mVolumeType != 1) {
                    z = false;
                }
                MediaSessionRecord.this.mVolumeType = 2;
                MediaSessionRecord.this.mVolumeControlType = i;
                MediaSessionRecord.this.mMaxVolume = i2;
                MediaSessionRecord.this.mVolumeControlId = str;
            }
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaSessionRecord.this.mService.onSessionPlaybackTypeChanged(MediaSessionRecord.this);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    MediaSessionRecord.this.mHandler.post(8);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
    }

    public class SessionCb {
        public final ISessionCallback mCb;

        public SessionCb(ISessionCallback iSessionCallback) {
            this.mCb = iSessionCallback;
        }

        public boolean sendMediaButton(String str, int i, int i2, boolean z, KeyEvent keyEvent, int i3, ResultReceiver resultReceiver) {
            try {
                if (KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                    MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "action=" + KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                }
                if (z) {
                    this.mCb.onMediaButton(MediaSessionRecord.this.mContext.getPackageName(), Process.myPid(), 1000, createMediaButtonIntent(keyEvent), i3, resultReceiver);
                    return true;
                }
                this.mCb.onMediaButton(str, i, i2, createMediaButtonIntent(keyEvent), i3, resultReceiver);
                return true;
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in sendMediaRequest.", e);
                return false;
            }
        }

        public boolean sendMediaButton(String str, int i, int i2, boolean z, KeyEvent keyEvent) {
            try {
                if (KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                    MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "action=" + KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                }
                if (z) {
                    this.mCb.onMediaButton(MediaSessionRecord.this.mContext.getPackageName(), Process.myPid(), 1000, createMediaButtonIntent(keyEvent), 0, (ResultReceiver) null);
                    return true;
                }
                this.mCb.onMediaButtonFromController(str, i, i2, createMediaButtonIntent(keyEvent));
                return true;
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in sendMediaRequest.", e);
                return false;
            }
        }

        public void sendCommand(String str, int i, int i2, String str2, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:" + str2);
                this.mCb.onCommand(str, i, i2, str2, bundle, resultReceiver);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in sendCommand.", e);
            }
        }

        public void sendCustomAction(String str, int i, int i2, String str2, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:custom-" + str2);
                this.mCb.onCustomAction(str, i, i2, str2, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in sendCustomAction.", e);
            }
        }

        public void prepare(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepare");
                this.mCb.onPrepare(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in prepare.", e);
            }
        }

        public void prepareFromMediaId(String str, int i, int i2, String str2, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromMediaId");
                this.mCb.onPrepareFromMediaId(str, i, i2, str2, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in prepareFromMediaId.", e);
            }
        }

        public void prepareFromSearch(String str, int i, int i2, String str2, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromSearch");
                this.mCb.onPrepareFromSearch(str, i, i2, str2, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in prepareFromSearch.", e);
            }
        }

        public void prepareFromUri(String str, int i, int i2, Uri uri, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromUri");
                this.mCb.onPrepareFromUri(str, i, i2, uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in prepareFromUri.", e);
            }
        }

        public void play(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:play");
                this.mCb.onPlay(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in play.", e);
            }
        }

        public void playFromMediaId(String str, int i, int i2, String str2, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromMediaId");
                this.mCb.onPlayFromMediaId(str, i, i2, str2, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in playFromMediaId.", e);
            }
        }

        public void playFromSearch(String str, int i, int i2, String str2, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromSearch");
                this.mCb.onPlayFromSearch(str, i, i2, str2, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in playFromSearch.", e);
            }
        }

        public void playFromUri(String str, int i, int i2, Uri uri, Bundle bundle) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromUri");
                this.mCb.onPlayFromUri(str, i, i2, uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in playFromUri.", e);
            }
        }

        public void skipToTrack(String str, int i, int i2, long j) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:skipToTrack");
                this.mCb.onSkipToTrack(str, i, i2, j);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in skipToTrack", e);
            }
        }

        public void pause(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:pause");
                this.mCb.onPause(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in pause.", e);
            }
        }

        public void stop(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:stop");
                this.mCb.onStop(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in stop.", e);
            }
        }

        public void next(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:next");
                this.mCb.onNext(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in next.", e);
            }
        }

        public void previous(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:previous");
                this.mCb.onPrevious(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in previous.", e);
            }
        }

        public void fastForward(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:fastForward");
                this.mCb.onFastForward(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in fastForward.", e);
            }
        }

        public void rewind(String str, int i, int i2) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:rewind");
                this.mCb.onRewind(str, i, i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in rewind.", e);
            }
        }

        public void seekTo(String str, int i, int i2, long j) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:seekTo");
                this.mCb.onSeekTo(str, i, i2, j);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in seekTo.", e);
            }
        }

        public void rate(String str, int i, int i2, Rating rating) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:rate");
                this.mCb.onRate(str, i, i2, rating);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in rate.", e);
            }
        }

        public void setPlaybackSpeed(String str, int i, int i2, float f) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:setPlaybackSpeed");
                this.mCb.onSetPlaybackSpeed(str, i, i2, f);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in setPlaybackSpeed.", e);
            }
        }

        public void adjustVolume(String str, int i, int i2, boolean z, int i3) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:adjustVolume");
                if (z) {
                    this.mCb.onAdjustVolume(MediaSessionRecord.this.mContext.getPackageName(), Process.myPid(), 1000, i3);
                } else {
                    this.mCb.onAdjustVolume(str, i, i2, i3);
                }
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in adjustVolume.", e);
            }
        }

        public void setVolumeTo(String str, int i, int i2, int i3) {
            try {
                MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(MediaSessionRecord.this.getUid(), MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:setVolumeTo");
                this.mCb.onSetVolumeTo(str, i, i2, i3);
            } catch (RemoteException e) {
                Log.e("MediaSessionRecord", "Remote failure in setVolumeTo.", e);
            }
        }

        public final Intent createMediaButtonIntent(KeyEvent keyEvent) {
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
            return intent;
        }
    }

    public class ControllerStub extends ISessionController.Stub {
        public ControllerStub() {
        }

        public void sendCommand(String str, String str2, Bundle bundle, ResultReceiver resultReceiver) {
            MediaSessionRecord.this.mSessionCb.sendCommand(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle, resultReceiver);
        }

        public boolean sendMediaButton(String str, KeyEvent keyEvent) {
            return MediaSessionRecord.this.mSessionCb.sendMediaButton(str, Binder.getCallingPid(), Binder.getCallingUid(), false, keyEvent);
        }

        public void registerCallback(String str, final ISessionControllerCallback iSessionControllerCallback) {
            synchronized (MediaSessionRecord.this.mLock) {
                if (MediaSessionRecord.this.mDestroyed) {
                    try {
                        iSessionControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                    return;
                }
                if (MediaSessionRecord.this.getControllerHolderIndexForCb(iSessionControllerCallback) < 0) {
                    ISessionControllerCallbackHolder iSessionControllerCallbackHolder = MediaSessionRecord.this.new ISessionControllerCallbackHolder(iSessionControllerCallback, str, Binder.getCallingUid(), new IBinder.DeathRecipient() { // from class: com.android.server.media.MediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            MediaSessionRecord.ControllerStub.this.lambda$registerCallback$0(iSessionControllerCallback);
                        }
                    });
                    MediaSessionRecord.this.mControllerCallbackHolders.add(iSessionControllerCallbackHolder);
                    if (MediaSessionRecord.DEBUG) {
                        Log.d("MediaSessionRecord", "registering controller callback " + iSessionControllerCallback + " from controller " + str);
                    }
                    try {
                        iSessionControllerCallback.asBinder().linkToDeath(iSessionControllerCallbackHolder.mDeathMonitor, 0);
                    } catch (RemoteException e) {
                        lambda$registerCallback$0(iSessionControllerCallback);
                        Log.w("MediaSessionRecord", "registerCallback failed to linkToDeath", e);
                    }
                }
            }
        }

        /* renamed from: unregisterCallback, reason: merged with bridge method [inline-methods] */
        public void lambda$registerCallback$0(ISessionControllerCallback iSessionControllerCallback) {
            synchronized (MediaSessionRecord.this.mLock) {
                IBinder asBinder = iSessionControllerCallback.asBinder();
                Iterator it = MediaSessionRecord.this.mControllerCallbackHolders.iterator();
                while (it.hasNext()) {
                    ISessionControllerCallbackHolder iSessionControllerCallbackHolder = (ISessionControllerCallbackHolder) it.next();
                    if (asBinder.equals(iSessionControllerCallbackHolder.mCallback.asBinder())) {
                        try {
                            asBinder.unlinkToDeath(iSessionControllerCallbackHolder.mDeathMonitor, 0);
                        } catch (NoSuchElementException e) {
                            Log.w("MediaSessionRecord", "error unlinking to binder death", e);
                        }
                        MediaSessionRecord.this.mControllerCallbackHolders.remove(iSessionControllerCallbackHolder);
                        if (MediaSessionRecord.DEBUG) {
                            Log.d("MediaSessionRecord", "unregistering callback " + iSessionControllerCallback + " / " + asBinder);
                        }
                        return;
                    }
                }
            }
        }

        public String getPackageName() {
            return MediaSessionRecord.this.mPackageName;
        }

        public String getTag() {
            return MediaSessionRecord.this.mTag;
        }

        public Bundle getSessionInfo() {
            return MediaSessionRecord.this.mSessionInfo;
        }

        public PendingIntent getLaunchPendingIntent() {
            return MediaSessionRecord.this.mLaunchIntent;
        }

        public long getFlags() {
            return MediaSessionRecord.this.mFlags;
        }

        public MediaController.PlaybackInfo getVolumeAttributes() {
            return MediaSessionRecord.this.getVolumeAttributes();
        }

        public void adjustVolume(String str, String str2, int i, int i2) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.adjustVolume(str, str2, callingPid, callingUid, false, i, i2, false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVolumeTo(String str, String str2, int i, int i2) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaSessionRecord.this.setVolumeTo(str, str2, callingPid, callingUid, i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void prepare(String str) {
            MediaSessionRecord.this.mSessionCb.prepare(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void prepareFromMediaId(String str, String str2, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.prepareFromMediaId(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle);
        }

        public void prepareFromSearch(String str, String str2, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.prepareFromSearch(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle);
        }

        public void prepareFromUri(String str, Uri uri, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.prepareFromUri(str, Binder.getCallingPid(), Binder.getCallingUid(), uri, bundle);
        }

        public void play(String str) {
            MediaSessionRecord.this.mSessionCb.play(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void playFromMediaId(String str, String str2, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.playFromMediaId(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle);
        }

        public void playFromSearch(String str, String str2, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.playFromSearch(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle);
        }

        public void playFromUri(String str, Uri uri, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.playFromUri(str, Binder.getCallingPid(), Binder.getCallingUid(), uri, bundle);
        }

        public void skipToQueueItem(String str, long j) {
            MediaSessionRecord.this.mSessionCb.skipToTrack(str, Binder.getCallingPid(), Binder.getCallingUid(), j);
        }

        public void pause(String str) {
            MediaSessionRecord.this.mSessionCb.pause(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void stop(String str) {
            MediaSessionRecord.this.mSessionCb.stop(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void next(String str) {
            MediaSessionRecord.this.mSessionCb.next(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void previous(String str) {
            MediaSessionRecord.this.mSessionCb.previous(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void fastForward(String str) {
            MediaSessionRecord.this.mSessionCb.fastForward(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void rewind(String str) {
            MediaSessionRecord.this.mSessionCb.rewind(str, Binder.getCallingPid(), Binder.getCallingUid());
        }

        public void seekTo(String str, long j) {
            MediaSessionRecord.this.mSessionCb.seekTo(str, Binder.getCallingPid(), Binder.getCallingUid(), j);
        }

        public void rate(String str, Rating rating) {
            MediaSessionRecord.this.mSessionCb.rate(str, Binder.getCallingPid(), Binder.getCallingUid(), rating);
        }

        public void setPlaybackSpeed(String str, float f) {
            MediaSessionRecord.this.mSessionCb.setPlaybackSpeed(str, Binder.getCallingPid(), Binder.getCallingUid(), f);
        }

        public void sendCustomAction(String str, String str2, Bundle bundle) {
            MediaSessionRecord.this.mSessionCb.sendCustomAction(str, Binder.getCallingPid(), Binder.getCallingUid(), str2, bundle);
        }

        public MediaMetadata getMetadata() {
            MediaMetadata mediaMetadata;
            synchronized (MediaSessionRecord.this.mLock) {
                mediaMetadata = MediaSessionRecord.this.mMetadata;
            }
            return mediaMetadata;
        }

        public PlaybackState getPlaybackState() {
            return MediaSessionRecord.this.getStateWithUpdatedPosition();
        }

        public ParceledListSlice getQueue() {
            ParceledListSlice parceledListSlice;
            synchronized (MediaSessionRecord.this.mLock) {
                parceledListSlice = MediaSessionRecord.this.mQueue == null ? null : new ParceledListSlice(MediaSessionRecord.this.mQueue);
            }
            return parceledListSlice;
        }

        public CharSequence getQueueTitle() {
            return MediaSessionRecord.this.mQueueTitle;
        }

        public Bundle getExtras() {
            Bundle bundle;
            synchronized (MediaSessionRecord.this.mLock) {
                bundle = MediaSessionRecord.this.mExtras;
            }
            return bundle;
        }

        public int getRatingType() {
            return MediaSessionRecord.this.mRatingType;
        }
    }

    public class ISessionControllerCallbackHolder {
        public final ISessionControllerCallback mCallback;
        public final IBinder.DeathRecipient mDeathMonitor;
        public final String mPackageName;
        public final int mUid;

        public ISessionControllerCallbackHolder(ISessionControllerCallback iSessionControllerCallback, String str, int i, IBinder.DeathRecipient deathRecipient) {
            this.mCallback = iSessionControllerCallback;
            this.mPackageName = str;
            this.mUid = i;
            this.mDeathMonitor = deathRecipient;
        }
    }

    public class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    MediaSessionRecord.this.pushMetadataUpdate();
                    break;
                case 2:
                    MediaSessionRecord.this.pushPlaybackStateUpdate();
                    break;
                case 3:
                    MediaSessionRecord.this.pushQueueUpdate();
                    break;
                case 4:
                    MediaSessionRecord.this.pushQueueTitleUpdate();
                    break;
                case 5:
                    MediaSessionRecord.this.pushExtrasUpdate();
                    break;
                case 6:
                    MediaSessionRecord.this.pushEvent((String) message.obj, message.getData());
                    break;
                case 8:
                    MediaSessionRecord.this.pushVolumeUpdate();
                    break;
                case 9:
                    MediaSessionRecord.this.pushSessionDestroyed();
                    break;
            }
        }

        public void post(int i) {
            post(i, null);
        }

        public void post(int i, Object obj) {
            obtainMessage(i, obj).sendToTarget();
        }

        public void post(int i, Object obj, Bundle bundle) {
            Message obtainMessage = obtainMessage(i, obj);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }

    public long getFlags() {
        return this.mFlags;
    }

    public boolean hasFlag(int i) {
        return (((long) i) & this.mFlags) != 0;
    }

    public final boolean shouldNotSendKeyToAppCastingSession() {
        return this.mService.mIsAppCastingOn && getUid() == this.mService.mAppCastingUid;
    }
}
