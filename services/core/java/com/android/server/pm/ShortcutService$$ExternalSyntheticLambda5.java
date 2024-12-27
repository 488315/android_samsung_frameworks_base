package com.android.server.pm;

import android.os.UserHandle;

import com.android.server.uri.UriGrantsManagerService;

public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda5(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = (ShortcutPackage) this.f$0;
                ShortcutLauncher shortcutLauncher = (ShortcutLauncher) this.f$1;
                if (shortcutPackage != null) {
                    shortcutPackage.removeShortcutPackageItem();
                }
                if (shortcutLauncher != null) {
                    shortcutLauncher.removeShortcutPackageItem();
                    return;
                }
                return;
            default:
                ShortcutService.AnonymousClass3 anonymousClass3 =
                        (ShortcutService.AnonymousClass3) this.f$0;
                UserHandle userHandle = (UserHandle) this.f$1;
                ShortcutService shortcutService = ShortcutService.this;
                int identifier = userHandle.getIdentifier();
                ((UriGrantsManagerService.LocalService) shortcutService.mUriGrantsManagerInternal)
                        .revokeUriPermissionFromOwner(
                                shortcutService.mUriPermissionOwner, null, -1, 0);
                synchronized (shortcutService.mServiceLock) {
                    try {
                        if (shortcutService.mUsers.get(identifier) != null) {
                            shortcutService.getUserShortcutsLocked(identifier).mCachedLauncher =
                                    null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
