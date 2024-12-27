package com.android.server.pm;

import android.content.pm.ShortcutServiceInternal;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ ShortcutService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda10(ShortcutService shortcutService, String str, int i) {
        this.f$0 = shortcutService;
        this.f$1 = i;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ShortcutService shortcutService = this.f$0;
        int i = this.f$1;
        String str = this.f$2;
        shortcutService.getClass();
        try {
            synchronized (shortcutService.mServiceLock) {
                try {
                    if (shortcutService.isUserUnlockedL(i)) {
                        ArrayList arrayList = new ArrayList(shortcutService.mListeners);
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            ((ShortcutServiceInternal.ShortcutChangeListener) arrayList.get(size)).onShortcutChanged(str, i);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception unused) {
        }
    }
}
