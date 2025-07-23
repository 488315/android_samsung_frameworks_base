package android.os;

import android.util.Log;
import android.util.SparseArray;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class FileObserver {
    public static final int ACCESS = 1;
    public static final int ALL_EVENTS = 4095;
    public static final int ATTRIB = 4;
    public static final int CLOSE_NOWRITE = 16;
    public static final int CLOSE_WRITE = 8;
    public static final int CREATE = 256;
    public static final int DELETE = 512;
    public static final int DELETE_SELF = 1024;
    private static final String LOG_TAG = "FileObserver";
    public static final int MODIFY = 2;
    public static final int MOVED_FROM = 64;
    public static final int MOVED_TO = 128;
    public static final int MOVE_SELF = 2048;
    public static final int OPEN = 32;
    private static ObserverThread s_observerThread;
    private int[] mDescriptors;
    private final List<File> mFiles;
    private final int mMask;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotifyEventType {
    }

    public abstract void onEvent(int i, String str);

    private static class ObserverThread extends Thread {
        private SparseArray<WeakReference> mRealObservers;
        private int m_fd;
        private HashMap<Integer, WeakReference> m_observers;

        private native int init();

        private native void observe(int i);

        private native void startWatching(int i, String[] strArr, int i2, int[] iArr);

        private native void stopWatching(int i, int[] iArr);

        public ObserverThread() {
            super(FileObserver.LOG_TAG);
            this.m_observers = new HashMap<>();
            this.mRealObservers = new SparseArray<>();
            this.m_fd = init();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            observe(this.m_fd);
        }

        public int[] startWatching(List<File> list, int i, FileObserver fileObserver) {
            int size = list.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = list.get(i2).getAbsolutePath();
            }
            int[] iArr = new int[size];
            Arrays.fill(iArr, -1);
            startWatching(this.m_fd, strArr, i, iArr);
            WeakReference weakReference = new WeakReference(fileObserver);
            synchronized (this.mRealObservers) {
                for (int i3 = 0; i3 < size; i3++) {
                    int i4 = iArr[i3];
                    if (i4 >= 0) {
                        this.mRealObservers.put(i4, weakReference);
                    }
                }
            }
            return iArr;
        }

        public void stopWatching(int[] iArr) {
            stopWatching(this.m_fd, iArr);
        }

        public void onEvent(int i, int i2, String str) {
            FileObserver fileObserver;
            synchronized (this.mRealObservers) {
                WeakReference weakReference = this.mRealObservers.get(i);
                if (weakReference != null) {
                    fileObserver = (FileObserver) weakReference.get();
                    if (fileObserver == null) {
                        this.mRealObservers.remove(i);
                    }
                } else {
                    fileObserver = null;
                }
            }
            if (fileObserver != null) {
                try {
                    fileObserver.onEvent(i2, str);
                } catch (Throwable th) {
                    Log.wtf(FileObserver.LOG_TAG, "Unhandled exception in FileObserver " + fileObserver, th);
                }
            }
        }
    }

    static {
        ObserverThread observerThread = new ObserverThread();
        s_observerThread = observerThread;
        observerThread.start();
    }

    @Deprecated
    public FileObserver(String str) {
        this(new File(str));
    }

    public FileObserver(File file) {
        this((List<File>) Arrays.asList(file));
    }

    public FileObserver(List<File> list) {
        this(list, 4095);
    }

    @Deprecated
    public FileObserver(String str, int i) {
        this(new File(str), i);
    }

    public FileObserver(File file, int i) {
        this((List<File>) Arrays.asList(file), i);
    }

    public FileObserver(List<File> list, int i) {
        this.mFiles = list;
        this.mMask = i;
    }

    protected void finalize() {
        stopWatching();
    }

    public void startWatching() {
        if (this.mDescriptors == null) {
            this.mDescriptors = s_observerThread.startWatching(this.mFiles, this.mMask, this);
        }
    }

    public void stopWatching() {
        int[] iArr = this.mDescriptors;
        if (iArr != null) {
            s_observerThread.stopWatching(iArr);
            this.mDescriptors = null;
        }
    }
}
