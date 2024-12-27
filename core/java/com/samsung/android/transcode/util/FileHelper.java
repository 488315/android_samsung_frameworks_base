package com.samsung.android.transcode.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.sec.enterprise.content.SecContentProviderURI;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes6.dex */
public class FileHelper {
    private FileHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static String getVEEditFilePath(Context context, Uri mediaUri) {
        String editPath = null;
        if (mediaUri == null) {
            return null;
        }
        String uriStr = mediaUri.toString();
        if (uriStr.length() <= 0) {
            return null;
        }
        LogS.d("TranscodeLib", "uriStr :" + uriStr);
        if (uriStr.startsWith(SecContentProviderURI.CONTENT)) {
            if (uriStr.startsWith(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString())
                    || uriStr.startsWith(MediaStore.Video.Media.INTERNAL_CONTENT_URI.toString())) {
                Cursor cursor = getVideoFileInfoByUri(mediaUri, context);
                if (cursor != null) {
                    try {
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            editPath = cursor.getString(cursor.getColumnIndex("_data"));
                        }
                    } catch (Throwable th) {
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                    return editPath;
                }
                return editPath;
            }
            return mediaUri.getPath();
        }
        if (uriStr.startsWith("file://")) {
            return mediaUri.getPath();
        }
        return uriStr;
    }

    private static Cursor getVideoFileInfoByUri(Uri uri, Context context) {
        String[] cols = {"_data", "duration"};
        try {
            Cursor c = context.getContentResolver().query(uri, cols, null, null, null);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getExternalSdCardStoragePath(Context context) {
        if (context != null && !isManagedProfile(context)) {
            StorageManager storageManager =
                    (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            List<StorageVolume> storageVolumes =
                    (List)
                            Optional.ofNullable(storageManager)
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.transcode.util.FileHelper$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return ((StorageManager) obj)
                                                            .getStorageVolumes();
                                                }
                                            })
                                    .orElse(null);
            if (storageVolumes != null) {
                int length = storageVolumes.size();
                for (int i = 0; i < length; i++) {
                    StorageVolume storageVolume = storageVolumes.get(i);
                    String subsystem = storageVolume.semGetSubSystem();
                    if (subsystem != null) {
                        String storagePath = storageVolume.semGetPath();
                        if ("sd".equals(subsystem)) {
                            return storagePath;
                        }
                    }
                }
                return "/NoSdCard/";
            }
            return "/NoSdCard/";
        }
        return "/NoSdCard/";
    }

    public static boolean isSdcardPath(Context context, String filePath) {
        String removalSdPath = getExternalSdCardStoragePath(context);
        return filePath.startsWith(removalSdPath);
    }

    private static boolean isManagedProfile(Context context) {
        try {
            return ((Boolean)
                            Optional.ofNullable((UserManager) context.getSystemService("user"))
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.transcode.util.FileHelper$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return Boolean.valueOf(
                                                            ((UserManager) obj)
                                                                    .semIsManagedProfile());
                                                }
                                            })
                                    .orElse(false))
                    .booleanValue();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
