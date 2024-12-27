package com.android.server.backup;

import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda13
        implements Consumer {
    public final /* synthetic */ UserBackupManagerService f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda13(
            UserBackupManagerService userBackupManagerService, Set set) {
        this.f$0 = userBackupManagerService;
        this.f$1 = set;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        UserBackupManagerService userBackupManagerService = this.f$0;
        Set set = this.f$1;
        String str = (String) obj;
        userBackupManagerService.getClass();
        if (set.add(str)) {
            userBackupManagerService.dataChangedImpl(str);
        }
    }
}
