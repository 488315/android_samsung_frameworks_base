package com.android.server.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class AudioPlayerStateMonitor {
    public static String TAG = "AudioPlayerStateMonitor";
    public static AudioPlayerStateMonitor sInstance;
    public final Object mLock = new Object();
    public final Map mListenerMap = new ArrayMap();
    public final Set mActiveAudioUids = new ArraySet();
    public ArrayMap mPrevActiveAudioPlaybackConfigs = new ArrayMap();
    public final List mSortedAudioPlaybackClientUids = new ArrayList();

    public interface OnAudioPlayerActiveStateChangedListener {
        void onAudioPlayerActiveStateChanged(AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z);
    }

    public final class MessageHandler extends Handler {
        public final OnAudioPlayerActiveStateChangedListener mListener;

        public MessageHandler(Looper looper, OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener) {
            super(looper);
            this.mListener = onAudioPlayerActiveStateChangedListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            this.mListener.onAudioPlayerActiveStateChanged((AudioPlaybackConfiguration) message.obj, message.arg1 != 0);
        }

        public void sendAudioPlayerActiveStateChangedMessage(AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            obtainMessage(1, z ? 1 : 0, 0, audioPlaybackConfiguration).sendToTarget();
        }
    }

    public static AudioPlayerStateMonitor getInstance(Context context) {
        AudioPlayerStateMonitor audioPlayerStateMonitor;
        synchronized (AudioPlayerStateMonitor.class) {
            if (sInstance == null) {
                sInstance = new AudioPlayerStateMonitor(context);
            }
            audioPlayerStateMonitor = sInstance;
        }
        return audioPlayerStateMonitor;
    }

    public AudioPlayerStateMonitor(Context context) {
        ((AudioManager) context.getSystemService("audio")).registerAudioPlaybackCallback(new AudioManagerPlaybackListener(), null);
    }

    public void registerListener(OnAudioPlayerActiveStateChangedListener onAudioPlayerActiveStateChangedListener, Handler handler) {
        synchronized (this.mLock) {
            this.mListenerMap.put(onAudioPlayerActiveStateChangedListener, new MessageHandler(handler == null ? Looper.myLooper() : handler.getLooper(), onAudioPlayerActiveStateChangedListener));
        }
    }

    public List getSortedAudioPlaybackClientUids() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            arrayList.addAll(this.mSortedAudioPlaybackClientUids);
        }
        return arrayList;
    }

    public boolean isPlaybackActive(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mActiveAudioUids.contains(Integer.valueOf(i));
        }
        return contains;
    }

    public void cleanUpAudioPlaybackUids(int i) {
        synchronized (this.mLock) {
            int identifier = UserHandle.getUserHandleForUid(i).getIdentifier();
            for (int size = this.mSortedAudioPlaybackClientUids.size() - 1; size >= 0 && ((Integer) this.mSortedAudioPlaybackClientUids.get(size)).intValue() != i; size--) {
                int intValue = ((Integer) this.mSortedAudioPlaybackClientUids.get(size)).intValue();
                if (identifier == UserHandle.getUserHandleForUid(intValue).getIdentifier() && !isPlaybackActive(intValue)) {
                    this.mSortedAudioPlaybackClientUids.remove(size);
                }
            }
        }
    }

    public void dump(Context context, PrintWriter printWriter, String str) {
        synchronized (this.mLock) {
            printWriter.println(str + "Audio playback (lastly played comes first)");
            String str2 = str + "  ";
            for (int i = 0; i < this.mSortedAudioPlaybackClientUids.size(); i++) {
                int intValue = ((Integer) this.mSortedAudioPlaybackClientUids.get(i)).intValue();
                printWriter.print(str2 + "uid=" + intValue + " packages=");
                String[] packagesForUid = context.getPackageManager().getPackagesForUid(intValue);
                if (packagesForUid != null && packagesForUid.length > 0) {
                    for (String str3 : packagesForUid) {
                        printWriter.print(str3 + " ");
                    }
                }
                printWriter.println();
            }
        }
    }

    public final void sendAudioPlayerActiveStateChangedMessageLocked(AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
        Iterator it = this.mListenerMap.values().iterator();
        while (it.hasNext()) {
            ((MessageHandler) it.next()).sendAudioPlayerActiveStateChangedMessage(audioPlaybackConfiguration, z);
        }
    }

    public class AudioManagerPlaybackListener extends AudioManager.AudioPlaybackCallback {
        public AudioManagerPlaybackListener() {
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(List list) {
            int i;
            synchronized (AudioPlayerStateMonitor.this.mLock) {
                AudioPlayerStateMonitor.this.mActiveAudioUids.clear();
                ArrayMap arrayMap = new ArrayMap();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
                    if (audioPlaybackConfiguration.isActive()) {
                        AudioPlayerStateMonitor.this.mActiveAudioUids.add(Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
                        arrayMap.put(Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()), audioPlaybackConfiguration);
                    }
                }
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) arrayMap.valueAt(i2);
                    int clientUid = audioPlaybackConfiguration2.getClientUid();
                    if (!AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.containsKey(Integer.valueOf(audioPlaybackConfiguration2.getPlayerInterfaceId()))) {
                        Log.d(AudioPlayerStateMonitor.TAG, "Found a new active media playback. " + audioPlaybackConfiguration2);
                        int indexOf = AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.indexOf(Integer.valueOf(clientUid));
                        if (indexOf != 0) {
                            if (indexOf > 0) {
                                AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.remove(indexOf);
                            }
                            AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.add(0, Integer.valueOf(clientUid));
                        }
                    }
                }
                if (AudioPlayerStateMonitor.this.mActiveAudioUids.size() > 0) {
                    AudioPlayerStateMonitor audioPlayerStateMonitor = AudioPlayerStateMonitor.this;
                    if (!audioPlayerStateMonitor.mActiveAudioUids.contains(audioPlayerStateMonitor.mSortedAudioPlaybackClientUids.get(0))) {
                        int i3 = 1;
                        while (true) {
                            if (i3 >= AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.size()) {
                                i3 = -1;
                                i = -1;
                                break;
                            } else {
                                i = ((Integer) AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.get(i3)).intValue();
                                if (AudioPlayerStateMonitor.this.mActiveAudioUids.contains(Integer.valueOf(i))) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        }
                        while (i3 > 0) {
                            List list2 = AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids;
                            list2.set(i3, (Integer) list2.get(i3 - 1));
                            i3--;
                        }
                        AudioPlayerStateMonitor.this.mSortedAudioPlaybackClientUids.set(0, Integer.valueOf(i));
                    }
                }
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration3 = (AudioPlaybackConfiguration) it2.next();
                    if ((AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.remove(Integer.valueOf(audioPlaybackConfiguration3.getPlayerInterfaceId())) != null) != audioPlaybackConfiguration3.isActive()) {
                        AudioPlayerStateMonitor.this.sendAudioPlayerActiveStateChangedMessageLocked(audioPlaybackConfiguration3, false);
                    }
                }
                Iterator it3 = AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs.values().iterator();
                while (it3.hasNext()) {
                    AudioPlayerStateMonitor.this.sendAudioPlayerActiveStateChangedMessageLocked((AudioPlaybackConfiguration) it3.next(), true);
                }
                AudioPlayerStateMonitor.this.mPrevActiveAudioPlaybackConfigs = arrayMap;
            }
        }
    }
}
