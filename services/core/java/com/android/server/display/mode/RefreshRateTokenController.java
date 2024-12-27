package com.android.server.display.mode;

import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;

import com.samsung.android.core.SystemHistory;
import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.function.Consumer;

public final class RefreshRateTokenController {
    public final Object mLock;
    public final SystemHistory mRefreshRateTokenHistory;
    public final ArrayList mRefreshRateTokens = new ArrayList();

    public RefreshRateTokenController(Object obj) {
        this.mLock = obj;
        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            this.mRefreshRateTokenHistory = new SystemHistory(15, "RefreshRateTokenController");
        }
    }

    public final void createRefreshRateToken(
            RefreshRateToken refreshRateToken,
            RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo) {
        synchronized (this.mLock) {
            try {
                this.mRefreshRateTokens.add(refreshRateToken);
                Consumer consumer =
                        new Consumer() { // from class:
                                         // com.android.server.display.mode.RefreshRateTokenController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                RefreshRateTokenController refreshRateTokenController =
                                        RefreshRateTokenController.this;
                                RefreshRateToken refreshRateToken2 = (RefreshRateToken) obj;
                                synchronized (refreshRateTokenController.mLock) {
                                    try {
                                        refreshRateTokenController.mRefreshRateTokens.remove(
                                                refreshRateToken2);
                                        refreshRateToken2.accept();
                                        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                                            refreshRateTokenController.mRefreshRateTokenHistory.add(
                                                    "Removing refreshRateToken="
                                                            + refreshRateToken2
                                                            + ", caller="
                                                            + Debug.getCallers(5));
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        };
                refreshRateToken.mInfo = refreshRateTokenInfo;
                refreshRateToken.mRemoveConsumer = consumer;
                IBinder iBinder = refreshRateTokenInfo.mToken;
                if (iBinder != null) {
                    try {
                        iBinder.linkToDeath(refreshRateToken, 0);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                refreshRateToken.accept();
                if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                    this.mRefreshRateTokenHistory.add(
                            "Adding refreshRateToken="
                                    + refreshRateToken
                                    + ", caller="
                                    + Debug.getCallers(5));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
