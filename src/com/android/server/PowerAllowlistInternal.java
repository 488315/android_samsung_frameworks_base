package com.android.server;

/* loaded from: classes6.dex */
public interface PowerAllowlistInternal {

    public interface TempAllowlistChangeListener {
        void onAppAdded(int i);

        void onAppRemoved(int i);
    }

    void registerTempAllowlistChangeListener(TempAllowlistChangeListener tempAllowlistChangeListener);

    void unregisterTempAllowlistChangeListener(TempAllowlistChangeListener tempAllowlistChangeListener);
}
