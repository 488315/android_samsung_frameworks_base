package android.gesture;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class GestureLibraries {
    private GestureLibraries() {
    }

    public static GestureLibrary fromFile(String str) {
        return fromFile(new File(str));
    }

    public static GestureLibrary fromFile(File file) {
        return new FileGestureLibrary(file);
    }

    public static GestureLibrary fromFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        return new FileGestureLibrary(parcelFileDescriptor.getFileDescriptor());
    }

    public static GestureLibrary fromPrivateFile(Context context, String str) {
        return fromFile(context.getFileStreamPath(str));
    }

    public static GestureLibrary fromRawResource(Context context, int i) {
        return new ResourceGestureLibrary(context, i);
    }

    private static class FileGestureLibrary extends GestureLibrary {
        private final FileDescriptor mFd;
        private final File mPath;

        public FileGestureLibrary(File file) {
            this.mPath = file;
            this.mFd = null;
        }

        public FileGestureLibrary(FileDescriptor fileDescriptor) {
            this.mPath = null;
            this.mFd = fileDescriptor;
        }

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            if (this.mPath != null) {
                return !r0.canWrite();
            }
            return false;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            boolean z = true;
            if (!this.mStore.hasChanged()) {
                return true;
            }
            File file = this.mPath;
            if (file != null) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    return false;
                }
                try {
                    file.createNewFile();
                    this.mStore.save(new FileOutputStream(file), true);
                } catch (IOException e) {
                    Log.d(GestureConstants.LOG_TAG, "Could not save the gesture library in " + this.mPath, e);
                    z = false;
                }
                return z;
            }
            try {
                this.mStore.save(new FileOutputStream(this.mFd), true);
                return true;
            } catch (IOException e2) {
                Log.d(GestureConstants.LOG_TAG, "Could not save the gesture library", e2);
                return false;
            }
        }

        @Override // android.gesture.GestureLibrary
        public boolean load() {
            File file = this.mPath;
            boolean z = true;
            if (file != null) {
                if (file.exists() && file.canRead()) {
                    try {
                        this.mStore.load(new FileInputStream(file), true);
                    } catch (IOException e) {
                        Log.d(GestureConstants.LOG_TAG, "Could not load the gesture library from " + this.mPath, e);
                    }
                    return z;
                }
                z = false;
                return z;
            }
            try {
                this.mStore.load(new FileInputStream(this.mFd), true);
                return true;
            } catch (IOException e2) {
                Log.d(GestureConstants.LOG_TAG, "Could not load the gesture library", e2);
                return false;
            }
        }
    }

    private static class ResourceGestureLibrary extends GestureLibrary {
        private final WeakReference<Context> mContext;
        private final int mResourceId;

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            return true;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            return false;
        }

        public ResourceGestureLibrary(Context context, int i) {
            this.mContext = new WeakReference<>(context);
            this.mResourceId = i;
        }

        @Override // android.gesture.GestureLibrary
        public boolean load() {
            Context context = this.mContext.get();
            if (context == null) {
                return false;
            }
            try {
                this.mStore.load(context.getResources().openRawResource(this.mResourceId), true);
                return true;
            } catch (IOException e) {
                Log.d(GestureConstants.LOG_TAG, "Could not load the gesture library from raw resource " + context.getResources().getResourceName(this.mResourceId), e);
                return false;
            }
        }
    }
}
