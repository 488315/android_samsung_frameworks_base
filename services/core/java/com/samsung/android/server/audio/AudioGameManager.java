package com.samsung.android.server.audio;

import android.content.Context;
import android.media.AudioSystem;
import android.util.Log;

import com.android.server.audio.AudioSystemAdapter;

import com.samsung.android.game.SemGameManager;
import com.samsung.android.server.audio.utils.AudioUtils;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AudioGameManager {
    public final AudioSystemAdapter mAudioSystem;
    public final Context mContext;
    public final SemGameManager mSemGameManager;
    public final ArrayList mUidList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioGameThread extends Thread {
        public AudioGameThread() {
            super("AudioGameThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                Iterator it = AudioGameManager.this.mSemGameManager.getGameList().iterator();
                while (it.hasNext()) {
                    int uidForPackage =
                            AudioUtils.getUidForPackage(
                                    AudioGameManager.this.mContext, (String) it.next());
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

    public AudioGameManager(Context context) {
        this.mContext = context;
        this.mSemGameManager = new SemGameManager();
        this.mUidList = new ArrayList();
        new AudioGameThread().start();
    }

    public AudioGameManager(Context context, AudioSystemAdapter audioSystemAdapter) {
        this.mContext = context;
        this.mAudioSystem = audioSystemAdapter;
        this.mUidList = new ArrayList();
    }

    public static boolean isGamePackager(String str) {
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

    public final void addGameUid(int i, boolean z) {
        synchronized (this.mUidList) {
            try {
                if (!this.mUidList.contains(Integer.valueOf(i))) {
                    this.mUidList.add(Integer.valueOf(i));
                    if (z) {
                        setParamGameUidList();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setParamGameUidList() {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("g_game_uid");
        sb.append("=");
        if (this.mUidList.size() == 0) {
            sb.append("0");
        } else {
            sb.append(this.mUidList.size());
            synchronized (this.mUidList) {
                try {
                    Iterator it = this.mUidList.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        sb.append("|");
                        sb.append(intValue);
                    }
                } catch (Throwable th) {
                    throw th;
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
}
