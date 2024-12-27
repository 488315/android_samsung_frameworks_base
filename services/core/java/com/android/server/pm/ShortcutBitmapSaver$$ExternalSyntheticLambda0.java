package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import android.util.Slog;

import libcore.io.IoUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

public final /* synthetic */ class ShortcutBitmapSaver$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutBitmapSaver$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ShortcutBitmapSaver.PendingItem pendingItem;
        ShortcutInfo shortcutInfo;
        ShortcutService.FileOutputStreamWithPath openIconFileForWrite;
        File file;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ShortcutBitmapSaver shortcutBitmapSaver = (ShortcutBitmapSaver) obj;
                shortcutBitmapSaver.getClass();
                while (true) {
                    ShortcutInfo shortcutInfo2 = null;
                    byte b = 0;
                    byte b2 = 0;
                    try {
                        synchronized (shortcutBitmapSaver.mPendingItems) {
                            try {
                                if (((LinkedBlockingDeque) shortcutBitmapSaver.mPendingItems).size()
                                        == 0) {
                                    return;
                                } else {
                                    pendingItem =
                                            (ShortcutBitmapSaver.PendingItem)
                                                    ((LinkedBlockingDeque)
                                                                    shortcutBitmapSaver
                                                                            .mPendingItems)
                                                            .pop();
                                }
                            } finally {
                            }
                        }
                        shortcutInfo = pendingItem.shortcut;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        if (shortcutInfo.isIconPendingSave()) {
                            try {
                                openIconFileForWrite =
                                        shortcutBitmapSaver.mService.openIconFileForWrite(
                                                shortcutInfo.getUserId(), shortcutInfo);
                                file = openIconFileForWrite.mFile;
                            } catch (IOException | RuntimeException e) {
                                Slog.e("ShortcutService", "Unable to write bitmap to file", e);
                                if (0 != 0 && (b2 == true ? 1 : 0).exists()) {
                                    (b == true ? 1 : 0).delete();
                                }
                                if (shortcutInfo.getBitmapPath() != null) {}
                            }
                            try {
                                openIconFileForWrite.write(pendingItem.bytes);
                                IoUtils.closeQuietly(openIconFileForWrite);
                                shortcutInfo.setBitmapPath(file.getAbsolutePath());
                                if (shortcutInfo.getBitmapPath() != null) {
                                    shortcutInfo.clearFlags(2048);
                                }
                                ShortcutBitmapSaver.removeIcon(shortcutInfo);
                                shortcutInfo.clearFlags(2048);
                            } catch (Throwable th2) {
                                IoUtils.closeQuietly(openIconFileForWrite);
                                throw th2;
                                break;
                            }
                        } else {
                            if (shortcutInfo.getBitmapPath() != null) {
                                shortcutInfo.clearFlags(2048);
                            }
                            ShortcutBitmapSaver.removeIcon(shortcutInfo);
                            shortcutInfo.clearFlags(2048);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        shortcutInfo2 = shortcutInfo;
                        if (shortcutInfo2 != null) {
                            if (shortcutInfo2.getBitmapPath() == null) {
                                ShortcutBitmapSaver.removeIcon(shortcutInfo2);
                            }
                            shortcutInfo2.clearFlags(2048);
                        }
                        throw th;
                    }
                }
                break;
            default:
                ((CountDownLatch) obj).countDown();
                return;
        }
    }
}
