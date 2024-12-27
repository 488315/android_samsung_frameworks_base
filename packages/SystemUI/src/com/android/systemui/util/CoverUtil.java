package com.android.systemui.util;

import com.android.systemui.util.CoverUtil;
import com.samsung.android.cover.CoverState;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CoverUtil {
    private ArrayList<CoverStateChangedListener> mCoverStateChangedListeners = new ArrayList<>();
    private CoverState mCoverState = null;

    public interface CoverStateChangedListener {
        void onUpdateCoverState(CoverState coverState);
    }

    private void setCoverState(CoverState coverState) {
        this.mCoverState = coverState;
    }

    public void addListener(CoverStateChangedListener coverStateChangedListener) {
        this.mCoverStateChangedListeners.add(coverStateChangedListener);
    }

    public CoverState getCoverState() {
        return this.mCoverState;
    }

    public void removeListener(CoverStateChangedListener coverStateChangedListener) {
        this.mCoverStateChangedListeners.remove(coverStateChangedListener);
    }

    public void updateCoverState(final CoverState coverState) {
        setCoverState(coverState);
        this.mCoverStateChangedListeners.forEach(new Consumer() { // from class: com.android.systemui.util.CoverUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CoverUtil.CoverStateChangedListener) obj).onUpdateCoverState(coverState);
            }
        });
    }
}
