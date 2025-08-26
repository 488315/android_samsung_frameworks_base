package com.android.systemui.wallpaper.video;

import android.app.WallpaperManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.video.VideoFileSaveService;
import java.io.File;

/* loaded from: classes3.dex */
public abstract class IVideoFileSaveService$Stub extends Binder implements IInterface {
    public IVideoFileSaveService$Stub() {
        attachInterface(this, "com.android.systemui.wallpaper.video.IVideoFileSaveService");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.wallpaper.video.IVideoFileSaveService");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.wallpaper.video.IVideoFileSaveService");
            return true;
        }
        switch (i) {
            case 1:
                String string = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
                videoFileSaveService.mVideoFileExt = string;
                videoFileSaveService.mUserId = i3;
                videoFileSaveService.mCurentWhich = i4;
                parcel2.writeNoException();
                return true;
            case 2:
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor videoFileDescriptorAsUserWithFilename = ((VideoFileSaveService.AnonymousClass2) this).getVideoFileDescriptorAsUserWithFilename(null, z);
                parcel2.writeNoException();
                parcel2.writeTypedObject(videoFileDescriptorAsUserWithFilename, 1);
                return true;
            case 3:
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean zIsVideoFileExistsWithFilename = ((VideoFileSaveService.AnonymousClass2) this).isVideoFileExistsWithFilename(null, z2);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsVideoFileExistsWithFilename);
                return true;
            case 4:
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean zDeleteVideoFileWithFilename = ((VideoFileSaveService.AnonymousClass2) this).deleteVideoFileWithFilename(null, z3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zDeleteVideoFileWithFilename);
                return true;
            case 5:
                boolean zRenameVideoFileWithFilename = ((VideoFileSaveService.AnonymousClass2) this).renameVideoFileWithFilename(null);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRenameVideoFileWithFilename);
                return true;
            case 6:
                ((VideoFileSaveService.AnonymousClass2) this).setVideoLockscreenWallpaperAsOwnerWithFilename(null);
                parcel2.writeNoException();
                return true;
            case 7:
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                ((VideoFileSaveService.AnonymousClass2) this).setVideoWallpaperAsOwnerWithFilename(bundle, null);
                parcel2.writeNoException();
                return true;
            case 8:
                String string2 = parcel.readString();
                boolean z4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor videoFileDescriptorAsUserWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).getVideoFileDescriptorAsUserWithFilename(string2, z4);
                parcel2.writeNoException();
                parcel2.writeTypedObject(videoFileDescriptorAsUserWithFilename2, 1);
                return true;
            case 9:
                String string3 = parcel.readString();
                boolean z5 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean zIsVideoFileExistsWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).isVideoFileExistsWithFilename(string3, z5);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsVideoFileExistsWithFilename2);
                return true;
            case 10:
                String string4 = parcel.readString();
                boolean z6 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean zDeleteVideoFileWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).deleteVideoFileWithFilename(string4, z6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zDeleteVideoFileWithFilename2);
                return true;
            case 11:
                String string5 = parcel.readString();
                parcel.enforceNoDataAvail();
                VideoFileSaveService.AnonymousClass2 anonymousClass2 = (VideoFileSaveService.AnonymousClass2) this;
                if (UserHandle.semGetMyUserId() != 0) {
                    throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
                }
                boolean z7 = LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE;
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(VideoFileSaveService.this.getApplicationContext());
                String videoFilePath = wallpaperManager.getVideoFilePath(2);
                String videoFilePath2 = wallpaperManager.getVideoFilePath(18);
                for (File file : new File("/data/user_de/0/com.android.systemui/files").listFiles(VideoFileSaveService.this.mVideoWallpaperFileFilter)) {
                    String name = file.getName();
                    if ((TextUtils.isEmpty(videoFilePath) || !videoFilePath.contains(string5)) && (!z7 || TextUtils.isEmpty(videoFilePath2) || !videoFilePath2.contains(string5))) {
                        if (name.contains(string5) && file.delete()) {
                            Log.i("VideoFileCopyService", "deleteVideoFiles: ".concat(name));
                        } else {
                            Log.w("VideoFileCopyService", "deleteVideoFiles, fail: ".concat(name));
                        }
                    }
                }
                parcel2.writeNoException();
                return true;
            case 12:
                String string6 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zRenameVideoFileWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).renameVideoFileWithFilename(string6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRenameVideoFileWithFilename2);
                return true;
            case 13:
                String string7 = parcel.readString();
                parcel.enforceNoDataAvail();
                ((VideoFileSaveService.AnonymousClass2) this).setVideoLockscreenWallpaperAsOwnerWithFilename(string7);
                parcel2.writeNoException();
                return true;
            case 14:
                String string8 = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                ((VideoFileSaveService.AnonymousClass2) this).setVideoWallpaperAsOwnerWithFilename(bundle2, string8);
                parcel2.writeNoException();
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
