package com.android.server.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioPlayerStateMonitor {
    public static final boolean DEBUG;
    public static final String TAG;
    public static AudioPlayerStateMonitor sInstance;
    public final Object mLock = new Object();
    public final Map mListenerMap = new ArrayMap();
    public final Set mActiveAudioUids = new ArraySet();
    public ArrayMap mPrevActiveAudioPlaybackConfigs = new ArrayMap();
    public final List mSortedAudioPlaybackClientUids = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioManagerPlaybackListener extends AudioManager.AudioPlaybackCallback {
        public AudioManagerPlaybackListener() {
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public final void onPlaybackConfigChanged(List list) {
            int i;
            synchronized (AudioPlayerStateMonitor.this.mLock) {
                try {
                    ((ArraySet) AudioPlayerStateMonitor.this.mActiveAudioUids).clear();
                    ArrayMap arrayMap = new ArrayMap();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
                        if (audioPlaybackConfiguration.isActive()) {
                            ((ArraySet) AudioPlayerStateMonitor.this.mActiveAudioUids).add(Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
                            arrayMap.put(Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()), audioPlaybackConfiguration);
                        }
                    }
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) arrayMap.valueAt(i2);
                        int clientUid = audioPlaybackConfiguration2.getClientUid();
                        if (!AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.containsKey(Integer.valueOf(audioPlaybackConfiguration2.getPlayerInterfaceId()))) {
                            if (AudioPlayerStateMonitor.DEBUG) {
                                Log.d(AudioPlayerStateMonitor.TAG, "Found a new active media playback. " + audioPlaybackConfiguration2);
                            }
                            int indexOf = ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).indexOf(Integer.valueOf(clientUid));
                            if (indexOf != 0) {
                                if (indexOf > 0) {
                                    ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).remove(indexOf);
                                }
                                ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).add(0, Integer.valueOf(clientUid));
                            }
                        }
                    }
                    if (((ArraySet) AudioPlayerStateMonitor.this.mActiveAudioUids).size() > 0) {
                        AudioPlayerStateMonitor audioPlayerStateMonitor = AudioPlayerStateMonitor.this;
                        if (!((ArraySet) audioPlayerStateMonitor.mActiveAudioUids).contains(((ArrayList) audioPlayerStateMonitor.mSortedAudioPlaybackClientUids).get(0))) {
                            int i3 = 1;
                            while (true) {
                                if (i3 >= ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).size()) {
                                    i3 = -1;
                                    i = -1;
                                    break;
                                } else {
                                    Integer num = (Integer) ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).get(i3);
                                    i = num.intValue();
                                    if (((ArraySet) AudioPlayerStateMonitor.this.mActiveAudioUids).contains(num)) {
                                        break;
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                            while (i3 > 0) {
                                List list2 = AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids;
                                ((ArrayList) list2).set(i3, (Integer) ((ArrayList) list2).get(i3 - 1));
                                i3--;
                            }
                            ((ArrayList) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids).set(0, Integer.valueOf(i));
                        }
                    }
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        AudioPlaybackConfiguration audioPlaybackConfiguration3 = (AudioPlaybackConfiguration) it2.next();
                        if ((AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.remove(Integer.valueOf(audioPlaybackConfiguration3.getPlayerInterfaceId())) != null) != audioPlaybackConfiguration3.isActive()) {
                            AudioPlayerStateMonitor.m645$$Nest$msendAudioPlayerActiveStateChangedMessageLocked(AudioPlayerStateMonitor.this, audioPlaybackConfiguration3, false);
                        }
                    }
                    Iterator it3 = AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.values().iterator();
                    while (it3.hasNext()) {
                        AudioPlayerStateMonitor.m645$$Nest$msendAudioPlayerActiveStateChangedMessageLocked(AudioPlayerStateMonitor.this, (AudioPlaybackConfiguration) it3.next(), true);
                    }
                    AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs = arrayMap;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageHandler extends Handler {
        public final OnAudioPlayerActiveStateChangedListener mListener;

        public MessageHandler(Looper looper, OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener) {
            super(looper);
            this.mListener = onAudioPlayerActiveStateChangedListener;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            this.mListener.onAudioPlayerActiveStateChanged((AudioPlaybackConfiguration) message.obj, message.arg1 != 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnAudioPlayerActiveStateChangedListener {
        void onAudioPlayerActiveStateChanged(AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z);
    }

    /* renamed from: -$$Nest$msendAudioPlayerActiveStateChangedMessageLocked, reason: not valid java name */
    public static void m645$$Nest$msendAudioPlayerActiveStateChangedMessageLocked(AudioPlayerStateMonitor audioPlayerStateMonitor, AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
        Iterator it = ((ArrayMap) audioPlayerStateMonitor.mListenerMap).values().iterator();
        while (it.hasNext()) {
            ((MessageHandler) it.next()).obtainMessage(1, z ? 1 : 0, 0, audioPlaybackConfiguration).sendToTarget();
        }
    }

    static {
        int i = MediaSessionService.LONG_PRESS_TIMEOUT;
        DEBUG = true;
        TAG = "AudioPlayerStateMonitor";
    }

    public AudioPlayerStateMonitor(Context context) {
        ((AudioManager) context.getSystemService("audio")).registerAudioPlaybackCallback(new AudioManagerPlaybackListener(), null);
    }

    public static AudioPlayerStateMonitor getInstance(Context context) {
        AudioPlayerStateMonitor audioPlayerStateMonitor;
        synchronized (AudioPlayerStateMonitor.class) {
            try {
                if (sInstance == null) {
                    sInstance = new AudioPlayerStateMonitor(context);
                }
                audioPlayerStateMonitor = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return audioPlayerStateMonitor;
    }

    public final void dump(Context context, PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("Audio playback (lastly played comes first)");
                for (int i = 0; i < ((ArrayList) this.mSortedAudioPlaybackClientUids).size(); i++) {
                    int intValue = ((Integer) ((ArrayList) this.mSortedAudioPlaybackClientUids).get(i)).intValue();
                    printWriter.print("  uid=" + intValue + " packages=");
                    String[] packagesForUid = context.getPackageManager().getPackagesForUid(intValue);
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        for (String str : packagesForUid) {
                            printWriter.print(str + " ");
                        }
                    }
                    printWriter.println();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isPlaybackActive(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = ((ArraySet) this.mActiveAudioUids).contains(Integer.valueOf(i));
        }
        return contains;
    }

    public final void registerListener(OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener, Handler handler) {
        synchronized (this.mLock) {
            try {
                ((ArrayMap) this.mListenerMap).put(onAudioPlayerActiveStateChangedListener, new MessageHandler(handler == null ? Looper.myLooper() : handler.getLooper(), onAudioPlayerActiveStateChangedListener));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
