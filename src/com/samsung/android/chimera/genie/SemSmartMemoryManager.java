package com.samsung.android.chimera.genie;

import android.content.Context;

/* loaded from: classes6.dex */
public class SemSmartMemoryManager {
    private static final String TAG = "SemSmartMemoryManager";
    private static SemSmartMemoryManager sSmartMemMgr;

    private SemSmartMemoryManager(Context context) {
    }

    public static synchronized SemSmartMemoryManager getInstance(Context context) {
        SemSmartMemoryManager semSmartMemoryManager;
        synchronized (SemSmartMemoryManager.class) {
            if (sSmartMemMgr == null) {
                sSmartMemMgr = new SemSmartMemoryManager(context);
            }
            semSmartMemoryManager = sSmartMemMgr;
        }
        return semSmartMemoryManager;
    }

    public void prepare() {
        GenieHintManager.getGenieHintManager().setGenieSessionStart();
    }

    public void request(SemMemRequest semMemRequest) {
        GenieHintManager.getGenieHintManager().prepareMemoryRequest(new MemRequest(semMemRequest.getType(), semMemRequest.getSize()));
    }

    public void release() {
        GenieHintManager.getGenieHintManager().setGenieSessionEnd();
    }
}
