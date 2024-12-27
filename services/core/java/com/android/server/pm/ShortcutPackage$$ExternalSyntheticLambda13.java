package com.android.server.pm;

import android.content.pm.ShortcutInfo;

import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda13(int i, List list) {
        this.$r8$classId = i;
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        List list = this.f$0;
        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
        switch (i) {
            case 0:
                list.add(shortcutInfo);
                break;
            default:
                if (!shortcutInfo.isAlive()) {
                    list.add(shortcutInfo);
                    break;
                }
                break;
        }
    }
}
