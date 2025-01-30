package com.samsung.android.server.audio;

import android.content.Context;
import android.media.AudioSystem;
import android.util.Log;
import com.android.server.audio.AudioSystemAdapter;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class AudioGameManager {
    public AudioGameThread mAudioGameThread;
    public AudioSystemAdapter mAudioSystem;
    public final Context mContext;
    public SemGameManager mSemGameManager;
    public ArrayList mUidList;

    public AudioGameManager(Context context) {
        this.mContext = context;
        this.mSemGameManager = new SemGameManager();
        this.mUidList = new ArrayList();
        AudioGameThread audioGameThread = new AudioGameThread();
        this.mAudioGameThread = audioGameThread;
        audioGameThread.start();
    }

    public AudioGameManager(Context context, AudioSystemAdapter audioSystemAdapter) {
        this.mContext = context;
        this.mAudioSystem = audioSystemAdapter;
        this.mUidList = new ArrayList();
    }

    public void addGameUid(int i, boolean z) {
        synchronized (this.mUidList) {
            if (!this.mUidList.contains(Integer.valueOf(i))) {
                this.mUidList.add(Integer.valueOf(i));
                if (z) {
                    setParamGameUidList();
                }
            }
        }
    }

    public void deleteGameUid(int i) {
        synchronized (this.mUidList) {
            if (this.mUidList.contains(Integer.valueOf(i))) {
                this.mUidList.remove(Integer.valueOf(i));
                setParamGameUidList();
            }
        }
    }

    public void setParamGameUidList() {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("g_game_uid");
        sb.append("=");
        if (this.mUidList.size() == 0) {
            sb.append("0");
        } else {
            sb.append(this.mUidList.size());
            synchronized (this.mUidList) {
                Iterator it = this.mUidList.iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    sb.append("|");
                    sb.append(intValue);
                }
            }
        }
        Log.i("AudioGameManager", sb.toString());
        AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
        if (audioSystemAdapter != null) {
            audioSystemAdapter.setParameters(sb.toString());
        } else {
            AudioSystem.setParameters(sb.toString());
        }
    }

    public boolean isGamePackager(String str) {
        try {
            if (!SemGameManager.isAvailable() || !SemGameManager.isGamePackage(str)) {
                if (!str.contains("dolbygametest")) {
                    return false;
                }
            }
            return true;
        } catch (IllegalStateException unused) {
            return false;
        }
    }

    public class AudioGameThread extends Thread {
        public AudioGameThread() {
            super("AudioGameThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Iterator it = AudioGameManager.this.mSemGameManager.getGameList().iterator();
                while (it.hasNext()) {
                    int uidForPackage = AudioUtils.getUidForPackage(AudioGameManager.this.mContext, (String) it.next());
                    if (uidForPackage != 0) {
                        AudioGameManager.this.addGameUid(uidForPackage, false);
                        if (AudioGameManager.this.mUidList.size() >= 256) {
                            break;
                        }
                    }
                }
                AudioGameManager.this.setParamGameUidList();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
}
