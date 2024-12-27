package com.android.server.pm;

import android.util.ArraySet;
import android.util.Slog;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda28 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda28(
            ShortcutPackage shortcutPackage, Set set, Consumer consumer) {
        this.f$0 = shortcutPackage;
        this.f$1 = set;
        this.f$2 = consumer;
    }

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda28(
            File[] fileArr, ArraySet arraySet, File file) {
        this.f$0 = fileArr;
        this.f$1 = arraySet;
        this.f$2 = file;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                File[] fileArr = (File[]) this.f$0;
                ArraySet arraySet = (ArraySet) this.f$1;
                File file = (File) this.f$2;
                try {
                    for (File file2 : fileArr) {
                        if (file2.isFile() && !arraySet.contains(file2.getName())) {
                            file2.delete();
                        }
                    }
                    break;
                } catch (Exception unused) {
                    Slog.d(
                            "ShortcutService",
                            AccountManagerService$$ExternalSyntheticOutline0.m(
                                    file, "Failed to remove dangling bitmap files: "),
                            new Exception());
                    return;
                }
                break;
            default:
                ShortcutPackage shortcutPackage = (ShortcutPackage) this.f$0;
                shortcutPackage
                        .fromAppSearch()
                        .thenAccept(
                                new ShortcutPackage$$ExternalSyntheticLambda2(
                                        shortcutPackage, (Set) this.f$1, (Consumer) this.f$2, 4));
                break;
        }
    }
}
