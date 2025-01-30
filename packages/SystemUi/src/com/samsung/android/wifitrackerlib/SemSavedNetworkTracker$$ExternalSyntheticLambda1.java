package com.samsung.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class SemSavedNetworkTracker$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj);
    }
}
