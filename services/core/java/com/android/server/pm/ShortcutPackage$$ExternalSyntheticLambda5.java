package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.util.ArrayMap;

import java.util.function.Consumer;

public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutPackage f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda5(
            ShortcutPackage shortcutPackage, Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = shortcutPackage;
        this.f$1 = obj;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = this.f$0;
                String str = (String) this.f$1;
                int i = this.f$2;
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                shortcutPackage.getClass();
                if (shortcutInfo != null) {
                    if (str == null) {
                        if (i != 0) {
                            shortcutInfo.setDisabledMessageResId(i);
                            shortcutPackage.mShortcutUser.mService
                                    .fixUpShortcutResourceNamesAndValues(shortcutInfo);
                            break;
                        }
                    } else {
                        shortcutInfo.setDisabledMessage(str);
                        break;
                    }
                }
                break;
            default:
                ShortcutPackage shortcutPackage2 = this.f$0;
                ArrayMap arrayMap = (ArrayMap) this.f$1;
                int i2 = this.f$2;
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj;
                shortcutPackage2.getClass();
                if (!shortcutInfo2.isManifestShortcut()) {
                    if (shortcutInfo2.isDynamic() && i2 != 0) {
                        ShortcutPackage.incrementCountForActivity(
                                arrayMap, shortcutInfo2.getActivity(), 1);
                        break;
                    }
                } else {
                    ShortcutPackage.incrementCountForActivity(
                            arrayMap, shortcutInfo2.getActivity(), 1);
                    break;
                }
                break;
        }
    }
}
