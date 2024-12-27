package android.gesture;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class GestureLibraries {
    private GestureLibraries() {
    }

    public static GestureLibrary fromFile(String path) {
        return fromFile(new File(path));
    }

    public static GestureLibrary fromFile(File path) {
        return new FileGestureLibrary(path);
    }

    public static GestureLibrary fromFileDescriptor(ParcelFileDescriptor pfd) {
        return new FileGestureLibrary(pfd.getFileDescriptor());
    }

    public static GestureLibrary fromPrivateFile(Context context, String name) {
        return fromFile(context.getFileStreamPath(name));
    }

    public static GestureLibrary fromRawResource(Context context, int resourceId) {
        return new ResourceGestureLibrary(context, resourceId);
    }

    private static class FileGestureLibrary extends GestureLibrary {
        private final FileDescriptor mFd;
        private final File mPath;

        public FileGestureLibrary(File path) {
            this.mPath = path;
            this.mFd = null;
        }

        public FileGestureLibrary(FileDescriptor fd) {
            this.mPath = null;
            this.mFd = fd;
        }

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            if (this.mPath != null) {
                return !this.mPath.canWrite();
            }
            return false;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            if (!this.mStore.hasChanged()) {
                return true;
            }
            if (this.mPath != null) {
                File file = this.mPath;
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    return false;
                }
                try {
                    file.createNewFile();
                    this.mStore.save(new FileOutputStream(file), true);
                    return true;
                } catch (IOException e) {
                    Log.d(GestureConstants.LOG_TAG, "Could not save the gesture library in " + this.mPath, e);
                    return false;
                }
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
            if (this.mPath != null) {
                File file = this.mPath;
                if (!file.exists() || !file.canRead()) {
                    return false;
                }
                try {
                    this.mStore.load(new FileInputStream(file), true);
                    return true;
                } catch (IOException e) {
                    Log.d(GestureConstants.LOG_TAG, "Could not load the gesture library from " + this.mPath, e);
                    return false;
                }
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

        public ResourceGestureLibrary(Context context, int resourceId) {
            this.mContext = new WeakReference<>(context);
            this.mResourceId = resourceId;
        }

        @Override // android.gesture.GestureLibrary
        public boolean isReadOnly() {
            return true;
        }

        @Override // android.gesture.GestureLibrary
        public boolean save() {
            return false;
        }

        @Override // android.gesture.GestureLibrary
        public boolean load() {
            Context context = this.mContext.get();
            if (context == null) {
                return false;
            }
            InputStream in = context.getResources().openRawResource(this.mResourceId);
            try {
                this.mStore.load(in, true);
                return true;
            } catch (IOException e) {
                Log.d(GestureConstants.LOG_TAG, "Could not load the gesture library from raw resource " + context.getResources().getResourceName(this.mResourceId), e);
                return false;
            }
        }
    }
}
