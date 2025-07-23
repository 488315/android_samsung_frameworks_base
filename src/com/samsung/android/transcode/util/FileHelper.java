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

    public static String getVEEditFilePath(Context context, Uri uri) {
        String str = null;
        if (uri != null) {
            String uri2 = uri.toString();
            if (uri2.length() > 0) {
                LogS.d("TranscodeLib", "uriStr :" + uri2);
                if (!uri2.startsWith(SecContentProviderURI.CONTENT)) {
                    return uri2.startsWith("file://") ? uri.getPath() : uri2;
                }
                if (uri2.startsWith(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString()) || uri2.startsWith(MediaStore.Video.Media.INTERNAL_CONTENT_URI.toString())) {
                    Cursor videoFileInfoByUri = getVideoFileInfoByUri(uri, context);
                    if (videoFileInfoByUri != null) {
                        try {
                            if (videoFileInfoByUri.getCount() > 0) {
                                videoFileInfoByUri.moveToFirst();
                                str = videoFileInfoByUri.getString(videoFileInfoByUri.getColumnIndex("_data"));
                            }
                        } catch (Throwable th) {
                            if (videoFileInfoByUri != null) {
                                try {
                                    videoFileInfoByUri.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    }
                    if (videoFileInfoByUri != null) {
                        videoFileInfoByUri.close();
                    }
                    return str;
                }
                return uri.getPath();
            }
        }
        return null;
    }

    private static Cursor getVideoFileInfoByUri(Uri uri, Context context) {
        try {
            return context.getContentResolver().query(uri, new String[]{"_data", "duration"}, null, null, null);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getExternalSdCardStoragePath(Context context) {
        List list;
        if (context != null && !isManagedProfile(context) && (list = (List) Optional.ofNullable((StorageManager) context.getSystemService(Context.STORAGE_SERVICE)).map(new Function() { // from class: com.samsung.android.transcode.util.FileHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StorageManager) obj).getStorageVolumes();
            }
        }).orElse(null)) != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                StorageVolume storageVolume = (StorageVolume) list.get(i);
                String semGetSubSystem = storageVolume.semGetSubSystem();
                if (semGetSubSystem != null) {
                    String semGetPath = storageVolume.semGetPath();
                    if ("sd".equals(semGetSubSystem)) {
                        return semGetPath;
                    }
                }
            }
            return "/NoSdCard/";
        }
        return "/NoSdCard/";
    }

    public static boolean isSdcardPath(Context context, String str) {
        return str.startsWith(getExternalSdCardStoragePath(context));
    }

    private static boolean isManagedProfile(Context context) {
        try {
            return ((Boolean) Optional.ofNullable((UserManager) context.getSystemService("user")).map(new Function() { // from class: com.samsung.android.transcode.util.FileHelper$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(((UserManager) obj).semIsManagedProfile());
                }
            }).orElse(false)).booleanValue();
        } catch (RuntimeException unused) {
            return false;
        }
    }
}
