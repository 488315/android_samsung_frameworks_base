package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.util.ArraySet;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda24 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ArraySet f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda24(int i, ArraySet arraySet) {
        this.$r8$classId = i;
        this.f$0 = arraySet;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ArraySet arraySet = this.f$0;
        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
        switch (i) {
            case 0:
                if (shortcutInfo.isManifestShortcut()) {
                    arraySet.add(shortcutInfo.getId());
                    break;
                }
                break;
            default:
                if (shortcutInfo.getBitmapPath() != null) {
                    String bitmapPath = shortcutInfo.getBitmapPath();
                    int lastIndexOf = bitmapPath.lastIndexOf(File.separatorChar);
                    if (lastIndexOf != -1) {
                        bitmapPath = bitmapPath.substring(lastIndexOf + 1);
                    }
                    arraySet.add(bitmapPath);
                    break;
                }
                break;
        }
    }
}
