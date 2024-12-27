package com.android.server.chimera.genie;

import android.util.Log;
import android.util.Slog;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.chimera.ppn.QuickSwap;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MemoryReclaimer {
    public static void executeQuickSwap(long j, ArrayList arrayList) {
        QuickSwap quickSwap;
        QuickSwap quickSwap2;
        Log.d("MemoryReclaimer", "Executing QuickSwap");
        PerProcessNandswap perProcessNandswap = PerProcessNandswap.getInstance();
        if (perProcessNandswap.isQuickSwapEnable() && (quickSwap2 = perProcessNandswap.mQuickSwap) != null) {
            quickSwap2.mGenieDump = arrayList;
        }
        PerProcessNandswap perProcessNandswap2 = PerProcessNandswap.getInstance();
        if (!perProcessNandswap2.isQuickSwapEnable() || (quickSwap = perProcessNandswap2.mQuickSwap) == null || quickSwap.mQuickSwapHandler == null) {
            return;
        }
        synchronized (quickSwap.isDoingQuickSwapLock) {
            try {
                if (quickSwap.isDoingQuickSwap) {
                    Slog.i("QuickSwap", "QuickSwap is skipped because QuickSwap is already running.");
                } else {
                    quickSwap.isDoingQuickSwap = true;
                    QuickSwap.QuickSwapHandler quickSwapHandler = quickSwap.mQuickSwapHandler;
                    quickSwapHandler.sendMessage(quickSwapHandler.obtainMessage(1, Long.valueOf(j)));
                }
            } finally {
            }
        }
    }
}
