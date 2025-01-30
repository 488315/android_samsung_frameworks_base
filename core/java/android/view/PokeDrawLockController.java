package android.view;

import android.util.Log;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes4.dex */
public class PokeDrawLockController {
    private static final String TAG = "PokeDrawLockController";
    private int mAllowedPokeDrawLockCounts = 0;
    private boolean mRequestedToAllowPokeDrawLock;
    private ViewRootImpl mViewRootImpl;

    public PokeDrawLockController(ViewRootImpl viewRoot) {
        this.mViewRootImpl = viewRoot;
    }

    public void updateAllowedPokeDrawLockCounts(boolean isAllowedPokeDrawLock) {
        if (isAllowedPokeDrawLock) {
            this.mAllowedPokeDrawLockCounts++;
            return;
        }
        consumeRequestedToAllowPokeDrawLock(true);
        int i = this.mAllowedPokeDrawLockCounts - 1;
        this.mAllowedPokeDrawLockCounts = i;
        if (i <= 0) {
            this.mAllowedPokeDrawLockCounts = 0;
        }
    }

    void requestToAllowPokeDrawLock() {
        if (this.mAllowedPokeDrawLockCounts > 0) {
            this.mRequestedToAllowPokeDrawLock = true;
        }
    }

    public boolean consumeRequestedToAllowPokeDrawLock(boolean forceConsume) {
        if (this.mAllowedPokeDrawLockCounts > 0 || forceConsume) {
            if (!this.mRequestedToAllowPokeDrawLock) {
                return false;
            }
            this.mRequestedToAllowPokeDrawLock = false;
            return true;
        }
        return true;
    }

    public boolean shouldSkipPokeDrawLockIfNeeded(boolean reportNextDraw) {
        if (hasSurface()) {
            return (reportNextDraw || this.mAllowedPokeDrawLockCounts == 0 || (this.mViewRootImpl.mWindowAttributes.samsungFlags & 262144) != 0) ? false : true;
        }
        if (CoreRune.SAFE_DEBUG) {
            Log.m96e(TAG, "shouldSkipPokeDrawLockIfNeeded, Surface is not valid.");
        }
        return true;
    }

    public boolean hasSurface() {
        return this.mViewRootImpl.mSurface.isValid();
    }

    public boolean isRequestedToAllowPokeDrawLock() {
        return this.mRequestedToAllowPokeDrawLock;
    }
}
