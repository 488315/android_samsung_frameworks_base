package com.android.server.display.mode;

import android.os.Debug;
import com.android.server.display.mode.RefreshRateToken;
import com.samsung.android.core.SystemHistory;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class RefreshRateTokenController {
    public final Object mLock;
    public SystemHistory mRefreshRateTokenHistory;
    public final ArrayList mRefreshRateTokens = new ArrayList();

    public RefreshRateTokenController(Object obj) {
        this.mLock = obj;
        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            this.mRefreshRateTokenHistory = new SystemHistory(15, "RefreshRateTokenController");
        }
    }

    public IRefreshRateToken createRefreshRateToken(RefreshRateToken refreshRateToken, RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo) {
        synchronized (this.mLock) {
            this.mRefreshRateTokens.add(refreshRateToken);
            refreshRateToken.init(refreshRateTokenInfo, new Consumer() { // from class: com.android.server.display.mode.RefreshRateTokenController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RefreshRateTokenController.this.removeRefreshRateToken((RefreshRateToken) obj);
                }
            });
            refreshRateToken.accept();
            if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                this.mRefreshRateTokenHistory.add("Adding refreshRateToken=" + refreshRateToken + ", caller=" + Debug.getCallers(5));
            }
        }
        return refreshRateToken;
    }

    public void removeRefreshRateToken(RefreshRateToken refreshRateToken) {
        synchronized (this.mLock) {
            this.mRefreshRateTokens.remove(refreshRateToken);
            refreshRateToken.accept();
            if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                this.mRefreshRateTokenHistory.add("Removing refreshRateToken=" + refreshRateToken + ", caller=" + Debug.getCallers(5));
            }
        }
    }

    public ArrayList getRefreshRateTokens() {
        return this.mRefreshRateTokens;
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("  RefreshRateTokens:" + this.mRefreshRateTokens);
            if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                this.mRefreshRateTokenHistory.dump(printWriter);
            }
        }
    }
}
