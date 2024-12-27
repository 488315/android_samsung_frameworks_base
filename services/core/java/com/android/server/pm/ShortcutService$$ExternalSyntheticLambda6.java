package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.infra.AndroidFuture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ArraySet arraySet;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ArrayList arrayList = (ArrayList) obj2;
                ShortcutPackage shortcutPackage = (ShortcutPackage) obj;
                synchronized (shortcutPackage.mPackageItemLock) {
                    arraySet = new ArraySet(1);
                    shortcutPackage.forEachShortcut(
                            new ShortcutPackage$$ExternalSyntheticLambda24(1, arraySet));
                }
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    arrayList.add(shortcutPackage.mPackageName + ":" + ((String) it.next()));
                }
                return;
            case 1:
                ShortcutPackage shortcutPackage2 = (ShortcutPackage) obj;
                ((ArrayMap) obj2).put(shortcutPackage2.mPackageName, shortcutPackage2);
                return;
            case 2:
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                ((AndroidFuture) obj2)
                        .complete(shortcutInfo == null ? null : shortcutInfo.getIntents());
                return;
            case 3:
                ((ArraySet) obj2).remove((String) obj);
                return;
            default:
                List list = (List) obj;
                ((Consumer) obj2)
                        .accept(
                                (list == null || list.isEmpty())
                                        ? null
                                        : (ShortcutInfo) list.get(0));
                return;
        }
    }
}
