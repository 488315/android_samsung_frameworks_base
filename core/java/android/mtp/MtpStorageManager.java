package android.mtp;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaFile;
import android.media.MediaMetrics;
import android.os.FileObserver;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Base64;
import android.util.Log;

import com.android.internal.util.Preconditions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class MtpStorageManager {
    private static final int IN_IGNORED = 32768;
    private static final int IN_ISDIR = 1073741824;
    private static final int IN_ONLYDIR = 16777216;
    private static final int IN_Q_OVERFLOW = 16384;
    private static final String TAG = MtpStorageManager.class.getSimpleName();
    private MtpNotifier mMtpNotifier;
    private Set<String> mSubdirectories;
    private HashMap<Integer, MtpObject> mObjects = new HashMap<>();
    private HashMap<Integer, MtpObject> mRoots = new HashMap<>();
    private int mNextObjectId = 1;
    private int mNextStorageId = 2;
    private volatile boolean mCheckConsistency = false;
    private Thread mConsistencyThread =
            new Thread(
                    new Runnable() { // from class:
                                     // android.mtp.MtpStorageManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MtpStorageManager.this.lambda$new$0();
                        }
                    });

    public abstract static class MtpNotifier {
        public abstract void sendObjectAdded(int i);

        public abstract void sendObjectInfoChanged(int i);

        public abstract void sendObjectRemoved(int i);
    }

    private enum MtpObjectState {
        NORMAL,
        FROZEN,
        FROZEN_ADDED,
        FROZEN_REMOVED,
        FROZEN_ONESHOT_ADD,
        FROZEN_ONESHOT_DEL
    }

    private enum MtpOperation {
        NONE,
        ADD,
        RENAME,
        COPY,
        DELETE
    }

    private class MtpObjectObserver extends FileObserver {
        MtpObject mObject;

        MtpObjectObserver(MtpObject object) {
            super(object.getPath().toString(), 16778184);
            this.mObject = object;
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x0082, code lost:

           r5.this$0.sDebugLog("Object was null in event", r7);
        */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x008a, code lost:

           return;
        */
        @Override // android.os.FileObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onEvent(int r6, java.lang.String r7) {
            /*
                r5 = this;
                android.mtp.MtpStorageManager r0 = android.mtp.MtpStorageManager.this
                monitor-enter(r0)
                r1 = r6 & 16384(0x4000, float:2.2959E-41)
                if (r1 == 0) goto L10
                java.lang.String r1 = android.mtp.MtpStorageManager.m3081$$Nest$sfgetTAG()     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r2 = "Received Inotify overflow event!"
                android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> Lb0
            L10:
                android.mtp.MtpStorageManager$MtpObject r1 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager$MtpObject r1 = android.mtp.MtpStorageManager.MtpObject.m3089$$Nest$mgetChild(r1, r7)     // Catch: java.lang.Throwable -> Lb0
                r2 = r6 & 128(0x80, float:1.794E-43)
                if (r2 != 0) goto L98
                r2 = r6 & 256(0x100, float:3.59E-43)
                if (r2 == 0) goto L20
                goto L98
            L20:
                r2 = r6 & 64
                if (r2 != 0) goto L80
                r2 = r6 & 512(0x200, float:7.175E-43)
                if (r2 == 0) goto L29
                goto L80
            L29:
                r2 = 32768(0x8000, float:4.5918E-41)
                r2 = r2 & r6
                if (r2 == 0) goto L54
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = "Got inotify deleted"
                android.mtp.MtpStorageManager$MtpObject r4 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                java.nio.file.Path r4 = r4.getPath()     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager.m3080$$Nest$msDebugLog(r2, r3, r4)     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                android.os.FileObserver r2 = android.mtp.MtpStorageManager.MtpObject.m3083$$Nest$fgetmObserver(r2)     // Catch: java.lang.Throwable -> Lb0
                if (r2 == 0) goto L4d
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                android.os.FileObserver r2 = android.mtp.MtpStorageManager.MtpObject.m3083$$Nest$fgetmObserver(r2)     // Catch: java.lang.Throwable -> Lb0
                r2.stopWatching()     // Catch: java.lang.Throwable -> Lb0
            L4d:
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                r3 = 0
                android.mtp.MtpStorageManager.MtpObject.m3085$$Nest$fputmObserver(r2, r3)     // Catch: java.lang.Throwable -> Lb0
                goto Lae
            L54:
                r2 = r6 & 8
                if (r2 == 0) goto L67
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = "Got inotify CLOSE_WRITE event for"
                android.mtp.MtpStorageManager.m3079$$Nest$msDebugLog(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager$MtpObject r3 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager.m3077$$Nest$mhandleChangedObject(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                goto Lae
            L67:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb0
                r3.<init>()     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r4 = "Got unrecognized event "
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Lb0
                java.lang.StringBuilder r3 = r3.append(r6)     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager.m3079$$Nest$msDebugLog(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                goto Lae
            L80:
                if (r1 != 0) goto L8b
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = "Object was null in event"
                android.mtp.MtpStorageManager.m3079$$Nest$msDebugLog(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
                return
            L8b:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = "Got inotify removed event for"
                android.mtp.MtpStorageManager.m3079$$Nest$msDebugLog(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager.m3078$$Nest$mhandleRemovedObject(r2, r1)     // Catch: java.lang.Throwable -> Lb0
                goto Lae
            L98:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                java.lang.String r3 = "Got inotify added event for"
                android.mtp.MtpStorageManager.m3079$$Nest$msDebugLog(r2, r3, r7)     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lb0
                android.mtp.MtpStorageManager$MtpObject r3 = r5.mObject     // Catch: java.lang.Throwable -> Lb0
                r4 = 1073741824(0x40000000, float:2.0)
                r4 = r4 & r6
                if (r4 == 0) goto Laa
                r4 = 1
                goto Lab
            Laa:
                r4 = 0
            Lab:
                android.mtp.MtpStorageManager.m3076$$Nest$mhandleAddedObject(r2, r3, r7, r4)     // Catch: java.lang.Throwable -> Lb0
            Lae:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
                return
            Lb0:
                r1 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
                throw r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.mtp.MtpStorageManager.MtpObjectObserver.onEvent(int,"
                        + " java.lang.String):void");
        }

        @Override // android.os.FileObserver
        public void finalize() {}
    }

    public static class MtpObject {
        private HashMap<String, MtpObject> mChildren;
        private int mId;
        private boolean mIsDir;
        private String mName;
        private MtpObject mParent;
        private MtpStorage mStorage;
        private FileObserver mObserver = null;
        private boolean mVisited = false;
        private MtpObjectState mState = MtpObjectState.NORMAL;
        private MtpOperation mOp = MtpOperation.NONE;

        MtpObject(String name, int id, MtpStorage storage, MtpObject parent, boolean isDir) {
            this.mId = id;
            this.mName = name;
            this.mStorage = (MtpStorage) Preconditions.checkNotNull(storage);
            this.mParent = parent;
            this.mIsDir = isDir;
            this.mChildren = this.mIsDir ? new HashMap<>() : null;
        }

        public String getName() {
            return this.mName;
        }

        public int getId() {
            return this.mId;
        }

        public boolean isDir() {
            return this.mIsDir;
        }

        public int getFormat() {
            if (this.mIsDir) {
                return 12289;
            }
            return MediaFile.getFormatCode(this.mName, null);
        }

        public int getStorageId() {
            return getRoot().getId();
        }

        public long getModifiedTime() {
            return getPath().toFile().lastModified() / 1000;
        }

        public MtpObject getParent() {
            return this.mParent;
        }

        public MtpObject getRoot() {
            return isRoot() ? this : this.mParent.getRoot();
        }

        public long getSize() {
            if (this.mIsDir) {
                return 0L;
            }
            return maybeApplyTranscodeLengthWorkaround(getPath().toFile().length());
        }

        private long maybeApplyTranscodeLengthWorkaround(long length) {
            if (this.mStorage.isHostWindows()
                    && isTranscodeMtpEnabled()
                    && isFileTranscodeSupported()) {
                return 2 * length;
            }
            return length;
        }

        private boolean isTranscodeMtpEnabled() {
            return SystemProperties.getBoolean("sys.fuse.transcode_mtp", false);
        }

        private boolean isFileTranscodeSupported() {
            Path path = getPath();
            try {
                StructStat stat = Os.stat(path.toString());
                return stat.st_nlink > 1;
            } catch (ErrnoException e) {
                Log.w(
                        MtpStorageManager.TAG,
                        "Failed to stat path: " + getPath() + ". Ignoring transcoding.");
                return false;
            }
        }

        public Path getPath() {
            return isRoot()
                    ? Paths.get(this.mName, new String[0])
                    : this.mParent.getPath().resolve(this.mName);
        }

        public boolean isRoot() {
            return this.mParent == null;
        }

        public String getVolumeName() {
            return this.mStorage.getVolumeName();
        }

        public boolean isSkipObserving() {
            if (getPath().toString().startsWith("/storage/emulated/0/SmartSwitch/tmp")) {
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String name) {
            this.mName = name;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(int id) {
            this.mId = id;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVisited() {
            return this.mVisited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParent(MtpObject parent) {
            if (getStorageId() != parent.getStorageId()) {
                this.mStorage = (MtpStorage) Preconditions.checkNotNull(parent.getStorage());
            }
            this.mParent = parent;
        }

        private MtpStorage getStorage() {
            return this.mStorage;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDir(boolean dir) {
            if (dir != this.mIsDir) {
                this.mIsDir = dir;
                this.mChildren = this.mIsDir ? new HashMap<>() : null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVisited(boolean visited) {
            this.mVisited = visited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObjectState getState() {
            return this.mState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(MtpObjectState state) {
            this.mState = state;
            if (this.mState == MtpObjectState.NORMAL) {
                this.mOp = MtpOperation.NONE;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpOperation getOperation() {
            return this.mOp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOperation(MtpOperation op) {
            this.mOp = op;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FileObserver getObserver() {
            return this.mObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setObserver(FileObserver observer) {
            this.mObserver = observer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChild(MtpObject child) {
            this.mChildren.put(child.getName(), child);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObject getChild(String name) {
            return this.mChildren.get(name);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Collection<MtpObject> getChildren() {
            return this.mChildren.values();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean exists() {
            return getPath().toFile().exists();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObject copy(boolean recursive) {
            MtpObject copy =
                    new MtpObject(this.mName, this.mId, this.mStorage, this.mParent, this.mIsDir);
            copy.mIsDir = this.mIsDir;
            copy.mVisited = this.mVisited;
            copy.mState = this.mState;
            copy.mChildren = this.mIsDir ? new HashMap<>() : null;
            if (recursive && this.mIsDir) {
                for (MtpObject child : this.mChildren.values()) {
                    MtpObject childCopy = child.copy(true);
                    childCopy.setParent(copy);
                    copy.addChild(childCopy);
                }
            }
            return copy;
        }
    }

    public MtpStorageManager(MtpNotifier notifier, Set<String> subdirectories) {
        this.mMtpNotifier = notifier;
        this.mSubdirectories = subdirectories;
        if (this.mCheckConsistency) {
            this.mConsistencyThread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        while (this.mCheckConsistency) {
            try {
                Thread.sleep(15000L);
                if (checkConsistency()) {
                    Log.v(TAG, "Cache is consistent");
                } else {
                    Log.w(TAG, "Cache is not consistent");
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public synchronized void close() {
        for (MtpObject obj : this.mObjects.values()) {
            if (obj.getObserver() != null) {
                obj.getObserver().stopWatching();
                obj.setObserver(null);
            }
        }
        for (MtpObject obj2 : this.mRoots.values()) {
            if (obj2.getObserver() != null) {
                obj2.getObserver().stopWatching();
                obj2.setObserver(null);
            }
        }
        if (this.mCheckConsistency) {
            this.mCheckConsistency = false;
            this.mConsistencyThread.interrupt();
            try {
                this.mConsistencyThread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void setSubdirectories(Set<String> subDirs) {
        this.mSubdirectories = subDirs;
    }

    public synchronized MtpStorage addMtpStorage(
            StorageVolume volume, Supplier<Boolean> isHostWindows) {
        int storageId;
        int mId;
        String volumeId = volume.getId();
        if (volumeId != null
                && volumeId.startsWith("emulated;")
                && (mId =
                                Integer.parseInt(
                                        volumeId.substring(
                                                volumeId.lastIndexOf(
                                                                NavigationBarInflaterView
                                                                        .GRAVITY_SEPARATOR)
                                                        + 1)))
                        >= 95
                && mId < 100) {
            MtpStorage storage = new MtpStorage(volume, 65538, isHostWindows);
            MtpObject root = new MtpObject(storage.getPath(), 65538, storage, null, true);
            this.mRoots.put(65538, root);
            return storage;
        }
        int storageId2 = volume.getStorageId();
        if (storageId2 == 65537) {
            storageId = storageId2;
        } else {
            storageId = ((getNextStorageId() & 65535) << 16) + 1;
        }
        MtpStorage storage2 = new MtpStorage(volume, storageId, isHostWindows);
        MtpObject root2 = new MtpObject(storage2.getPath(), storageId, storage2, null, true);
        this.mRoots.put(Integer.valueOf(storageId), root2);
        return storage2;
    }

    public synchronized void removeMtpStorage(MtpStorage storage) {
        removeObjectFromCache(getStorageRoot(storage.getStorageId()), true, true);
    }

    private synchronized boolean isSpecialSubDir(MtpObject obj) {
        boolean z;
        if (obj.getParent().isRoot() && this.mSubdirectories != null) {
            z = this.mSubdirectories.contains(obj.getName()) ? false : true;
        }
        return z;
    }

    public synchronized MtpObject getByPath(String path) {
        MtpObject obj = null;
        for (MtpObject root : this.mRoots.values()) {
            if (path.startsWith(root.getName())) {
                obj = root;
                path = path.substring(root.getName().length());
            }
        }
        for (String name : path.split("/")) {
            if (obj != null && obj.isDir()) {
                if (!"".equals(name)) {
                    if (!obj.isVisited()) {
                        getChildren(obj, true);
                    }
                    obj = obj.getChild(name);
                }
            }
            return null;
        }
        return obj;
    }

    public synchronized MtpObject getObject(int id) {
        if (id == 0 || id == -1) {
            Log.w(TAG, "Can't get root storages with getObject()");
            return null;
        }
        if (!this.mObjects.containsKey(Integer.valueOf(id))) {
            Log.w(TAG, "Id " + id + " doesn't exist");
            return null;
        }
        return this.mObjects.get(Integer.valueOf(id));
    }

    public MtpObject getStorageRoot(int id) {
        if (!this.mRoots.containsKey(Integer.valueOf(id))) {
            Log.w(TAG, "StorageId " + id + " doesn't exist");
            return null;
        }
        return this.mRoots.get(Integer.valueOf(id));
    }

    private int getNextObjectId() {
        int ret = this.mNextObjectId;
        this.mNextObjectId = (int) (this.mNextObjectId + 1);
        return ret;
    }

    private int getNextStorageId() {
        int i = this.mNextStorageId;
        this.mNextStorageId = i + 1;
        return i;
    }

    /* JADX WARN: Incorrect condition in loop: B:15:0x002a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.util.List<android.mtp.MtpStorageManager.MtpObject> getObjects(
            int r16, int r17, int r18, boolean r19) {
        /*
            r15 = this;
            r12 = r15
            r0 = r16
            r13 = r18
            monitor-enter(r15)
            if (r0 != 0) goto La
            r1 = 1
            goto Lb
        La:
            r1 = 0
        Lb:
            r5 = r1
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L64
            r2.<init>()     // Catch: java.lang.Throwable -> L64
            r1 = 1
            r3 = -1
            if (r0 != r3) goto L16
            r0 = 0
        L16:
            r14 = 0
            if (r13 != r3) goto L43
            if (r0 != 0) goto L43
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r12.mRoots     // Catch: java.lang.Throwable -> L64
            java.util.Collection r3 = r3.values()     // Catch: java.lang.Throwable -> L64
            java.util.Iterator r7 = r3.iterator()     // Catch: java.lang.Throwable -> L64
            r8 = r1
        L26:
            boolean r1 = r7.hasNext()     // Catch: java.lang.Throwable -> L64
            if (r1 == 0) goto L3e
            java.lang.Object r1 = r7.next()     // Catch: java.lang.Throwable -> L64
            r3 = r1
            android.mtp.MtpStorageManager$MtpObject r3 = (android.mtp.MtpStorageManager.MtpObject) r3     // Catch: java.lang.Throwable -> L64
            r1 = r15
            r4 = r17
            r6 = r19
            boolean r1 = r1.getObjects(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L64
            r8 = r8 & r1
            goto L26
        L3e:
            if (r8 == 0) goto L41
            r14 = r2
        L41:
            monitor-exit(r15)
            return r14
        L43:
            if (r0 != 0) goto L4a
            android.mtp.MtpStorageManager$MtpObject r3 = r15.getStorageRoot(r13)     // Catch: java.lang.Throwable -> L64
            goto L4e
        L4a:
            android.mtp.MtpStorageManager$MtpObject r3 = r15.getObject(r0)     // Catch: java.lang.Throwable -> L64
        L4e:
            if (r3 != 0) goto L52
            monitor-exit(r15)
            return r14
        L52:
            r6 = r15
            r7 = r2
            r8 = r3
            r9 = r17
            r10 = r5
            r11 = r19
            boolean r4 = r6.getObjects(r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L64
            r1 = r4
            if (r1 == 0) goto L62
            r14 = r2
        L62:
            monitor-exit(r15)
            return r14
        L64:
            r0 = move-exception
            monitor-exit(r15)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.mtp.MtpStorageManager.getObjects(int, int, int,"
                    + " boolean):java.util.List");
    }

    private synchronized boolean getObjects(
            List<MtpObject> toAdd, MtpObject parent, int format, boolean rec, boolean isSearch) {
        Collection<MtpObject> children = getChildren(parent, isSearch);
        if (children == null) {
            return false;
        }
        for (MtpObject o : children) {
            if (format == 0 || o.getFormat() == format) {
                toAdd.add(o);
            }
        }
        boolean ret = true;
        if (rec) {
            for (MtpObject o2 : children) {
                if (o2.isDir()) {
                    ret &= getObjects(toAdd, o2, format, true, isSearch);
                }
            }
        }
        return ret;
    }

    private synchronized Collection<MtpObject> getChildren(MtpObject object, boolean isSearch) {
        if (object != null) {
            if (object.isDir()) {
                if (!object.isVisited() && isSearch) {
                    Path dir = object.getPath();
                    if (object.getObserver() != null) {
                        Log.e(TAG, "Observer is not null!");
                    }
                    object.setObserver(new MtpObjectObserver(object));
                    object.getObserver().startWatching();
                    try {
                        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
                        try {
                            for (Path file : stream) {
                                addObjectToCache(
                                        object,
                                        file.getFileName().toString(),
                                        file.toFile().isDirectory());
                            }
                            if (stream != null) {
                                stream.close();
                            }
                            object.setVisited(true);
                            if (object.isDir()
                                    && (object.isSkipObserving()
                                            || object.getName()
                                                    .startsWith(MediaMetrics.SEPARATOR))) {
                                object.getObserver().stopWatching();
                                object.setObserver(null);
                            }
                        } catch (Throwable th) {
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException | DirectoryIteratorException e) {
                        Log.e(TAG, e.toString());
                        object.getObserver().stopWatching();
                        object.setObserver(null);
                        return null;
                    }
                }
                return object.getChildren();
            }
        }
        Log.w(
                TAG,
                "Can't find children of "
                        + (object == null ? "null" : Integer.valueOf(object.getId())));
        return null;
    }

    private synchronized MtpObject addObjectToCache(
            MtpObject parent, String newName, boolean isDir) {
        if (!parent.isRoot() && getObject(parent.getId()) != parent) {
            return null;
        }
        if (parent.getChild(newName) != null) {
            return null;
        }
        if (this.mSubdirectories != null
                && parent.isRoot()
                && !this.mSubdirectories.contains(newName)) {
            return null;
        }
        MtpObject obj = new MtpObject(newName, getNextObjectId(), parent.mStorage, parent, isDir);
        this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        parent.addChild(obj);
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021 A[Catch: all -> 0x00ab, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:19:0x0061, B:20:0x006a, B:22:0x0070, B:23:0x007b, B:26:0x0083, B:27:0x0090, B:29:0x0096, B:44:0x0049), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030 A[Catch: all -> 0x00ab, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:19:0x0061, B:20:0x006a, B:22:0x0070, B:23:0x007b, B:26:0x0083, B:27:0x0090, B:29:0x0096, B:44:0x0049), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0061 A[Catch: all -> 0x00ab, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:19:0x0061, B:20:0x006a, B:22:0x0070, B:23:0x007b, B:26:0x0083, B:27:0x0090, B:29:0x0096, B:44:0x0049), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0070 A[Catch: all -> 0x00ab, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:19:0x0061, B:20:0x006a, B:22:0x0070, B:23:0x007b, B:26:0x0083, B:27:0x0090, B:29:0x0096, B:44:0x0049), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0096 A[Catch: all -> 0x00ab, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:19:0x0061, B:20:0x006a, B:22:0x0070, B:23:0x007b, B:26:0x0083, B:27:0x0090, B:29:0x0096, B:44:0x0049), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean removeObjectFromCache(
            android.mtp.MtpStorageManager.MtpObject r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r8.isRoot()     // Catch: java.lang.Throwable -> Lab
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L1e
            android.mtp.MtpStorageManager$MtpObject r0 = r8.getParent()     // Catch: java.lang.Throwable -> Lab
            java.util.HashMap r0 = android.mtp.MtpStorageManager.MtpObject.m3082$$Nest$fgetmChildren(r0)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r3 = r8.getName()     // Catch: java.lang.Throwable -> Lab
            boolean r0 = r0.remove(r3, r8)     // Catch: java.lang.Throwable -> Lab
            if (r0 == 0) goto L1c
            goto L1e
        L1c:
            r0 = r1
            goto L1f
        L1e:
            r0 = r2
        L1f:
            if (r0 != 0) goto L2a
            java.lang.String r3 = "Failed to remove from parent "
            java.nio.file.Path r4 = r8.getPath()     // Catch: java.lang.Throwable -> Lab
            r7.sDebugLog(r3, r4)     // Catch: java.lang.Throwable -> Lab
        L2a:
            boolean r3 = r8.isRoot()     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L47
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r7.mRoots     // Catch: java.lang.Throwable -> Lab
            int r4 = r8.getId()     // Catch: java.lang.Throwable -> Lab
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> Lab
            boolean r3 = r3.remove(r4, r8)     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L44
            if (r0 == 0) goto L44
            r3 = r2
            goto L45
        L44:
            r3 = r1
        L45:
            r0 = r3
            goto L5f
        L47:
            if (r9 == 0) goto L5f
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r7.mObjects     // Catch: java.lang.Throwable -> Lab
            int r4 = r8.getId()     // Catch: java.lang.Throwable -> Lab
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> Lab
            boolean r3 = r3.remove(r4, r8)     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L5d
            if (r0 == 0) goto L5d
            r3 = r2
            goto L5e
        L5d:
            r3 = r1
        L5e:
            r0 = r3
        L5f:
            if (r0 != 0) goto L6a
            java.lang.String r3 = "Failed to remove from global cache "
            java.nio.file.Path r4 = r8.getPath()     // Catch: java.lang.Throwable -> Lab
            r7.sDebugLog(r3, r4)     // Catch: java.lang.Throwable -> Lab
        L6a:
            android.os.FileObserver r3 = android.mtp.MtpStorageManager.MtpObject.m3091$$Nest$mgetObserver(r8)     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L7b
            android.os.FileObserver r3 = android.mtp.MtpStorageManager.MtpObject.m3091$$Nest$mgetObserver(r8)     // Catch: java.lang.Throwable -> Lab
            r3.stopWatching()     // Catch: java.lang.Throwable -> Lab
            r3 = 0
            android.mtp.MtpStorageManager.MtpObject.m3098$$Nest$msetObserver(r8, r3)     // Catch: java.lang.Throwable -> Lab
        L7b:
            boolean r3 = r8.isDir()     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto La9
            if (r10 == 0) goto La9
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lab
            java.util.Collection r4 = android.mtp.MtpStorageManager.MtpObject.m3090$$Nest$mgetChildren(r8)     // Catch: java.lang.Throwable -> Lab
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Lab
            java.util.Iterator r4 = r3.iterator()     // Catch: java.lang.Throwable -> Lab
        L90:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> Lab
            if (r5 == 0) goto La9
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> Lab
            android.mtp.MtpStorageManager$MtpObject r5 = (android.mtp.MtpStorageManager.MtpObject) r5     // Catch: java.lang.Throwable -> Lab
            boolean r6 = r7.removeObjectFromCache(r5, r9, r2)     // Catch: java.lang.Throwable -> Lab
            if (r6 == 0) goto La6
            if (r0 == 0) goto La6
            r6 = r2
            goto La7
        La6:
            r6 = r1
        La7:
            r0 = r6
            goto L90
        La9:
            monitor-exit(r7)
            return r0
        Lab:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.mtp.MtpStorageManager.removeObjectFromCache(android.mtp.MtpStorageManager$MtpObject,"
                    + " boolean, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleAddedObject(MtpObject parent, String path, boolean isDir) {
        DirectoryStream<Path> stream;
        MtpOperation op = MtpOperation.NONE;
        MtpObject obj = parent.getChild(path);
        if (obj != null) {
            MtpObjectState state = obj.getState();
            op = obj.getOperation();
            if (obj.isDir() != isDir && state != MtpObjectState.FROZEN_REMOVED) {
                sDebugLog("Inconsistent directory info! ", obj.getPath());
            }
            obj.setDir(isDir);
            switch (state) {
                case NORMAL:
                case FROZEN_ADDED:
                    return;
                case FROZEN:
                case FROZEN_REMOVED:
                    obj.setState(MtpObjectState.FROZEN_ADDED);
                    break;
                case FROZEN_ONESHOT_ADD:
                    obj.setState(MtpObjectState.NORMAL);
                    break;
                default:
                    sDebugLog("Unexpected state in add " + state, path);
                    break;
            }
            Log.i(TAG, state + " transitioned to " + obj.getState() + " in op " + op);
        } else {
            obj = addObjectToCache(parent, path, isDir);
            if (obj != null) {
                this.mMtpNotifier.sendObjectAdded(obj.getId());
            } else {
                sDebugLog("object alraeady exists", path);
                return;
            }
        }
        if (isDir) {
            if (op == MtpOperation.RENAME) {
                return;
            }
            if (op == MtpOperation.COPY && !obj.isVisited()) {
                return;
            }
            if (obj.getObserver() != null) {
                Log.e(TAG, "Observer is not null!");
                return;
            }
            obj.setObserver(new MtpObjectObserver(obj));
            obj.getObserver().startWatching();
            obj.setVisited(true);
            try {
                stream = Files.newDirectoryStream(obj.getPath());
            } catch (IOException | DirectoryIteratorException e) {
                Log.e(TAG, e.toString());
                obj.getObserver().stopWatching();
                obj.setObserver(null);
            }
            try {
                for (Path file : stream) {
                    sDebugLog("Manually handling event for ", file.getFileName().toString());
                    handleAddedObject(
                            obj, file.getFileName().toString(), file.toFile().isDirectory());
                }
                if (stream != null) {
                    stream.close();
                }
                if (obj.isSkipObserving() || obj.getName().startsWith(MediaMetrics.SEPARATOR)) {
                    obj.getObserver().stopWatching();
                    obj.setObserver(null);
                }
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemovedObject(MtpObject obj) {
        MtpObjectState state = obj.getState();
        MtpOperation op = obj.getOperation();
        boolean z = true;
        switch (state) {
            case NORMAL:
                if (removeObjectFromCache(obj, true, true)) {
                    this.mMtpNotifier.sendObjectRemoved(obj.getId());
                    break;
                }
                break;
            case FROZEN:
                obj.setState(MtpObjectState.FROZEN_REMOVED);
                break;
            case FROZEN_ADDED:
                obj.setState(MtpObjectState.FROZEN_REMOVED);
                break;
            case FROZEN_REMOVED:
            case FROZEN_ONESHOT_ADD:
            default:
                sDebugLog("Got unexpected object remove for", obj.getName());
                break;
            case FROZEN_ONESHOT_DEL:
                if (op == MtpOperation.RENAME) {
                    z = false;
                }
                removeObjectFromCache(obj, z, false);
                break;
        }
        Log.i(TAG, state + " transitioned to " + obj.getState() + " in op " + op);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleChangedObject(MtpObject parent, String path) {
        MtpOperation mtpOperation = MtpOperation.NONE;
        MtpObject obj = parent.getChild(path);
        if (obj != null) {
            if (!obj.isDir() && obj.getSize() > 0) {
                obj.getState();
                obj.getOperation();
                this.mMtpNotifier.sendObjectInfoChanged(obj.getId());
                Log.d(TAG, "sendObjectInfoChanged: id=" + obj.getId() + ",size=" + obj.getSize());
            }
        } else {
            sDebugLog("object is null", path);
        }
    }

    public void flushEvents() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void dump() {
        Iterator<Integer> it = this.mObjects.keySet().iterator();
        while (it.hasNext()) {
            int key = it.next().intValue();
            MtpObject obj = this.mObjects.get(Integer.valueOf(key));
            Log.i(
                    TAG,
                    key
                            + " | "
                            + (obj.getParent() == null
                                    ? Integer.valueOf(obj.getParent().getId())
                                    : "null")
                            + " | "
                            + obj.getName()
                            + " | "
                            + (obj.isDir() ? "dir" : "obj")
                            + " | "
                            + (obj.isVisited() ? "v" : "nv")
                            + " | "
                            + obj.getState());
        }
    }

    public synchronized boolean checkConsistency() {
        boolean ret;
        List<MtpObject> objs = new ArrayList<>();
        objs.addAll(this.mRoots.values());
        objs.addAll(this.mObjects.values());
        ret = true;
        for (MtpObject obj : objs) {
            if (!obj.exists()) {
                Log.w(TAG, "Object doesn't exist " + obj.getPath() + " " + obj.getId());
                ret = false;
            }
            if (obj.getState() != MtpObjectState.NORMAL) {
                Log.w(TAG, "Object " + obj.getPath() + " in state " + obj.getState());
                ret = false;
            }
            if (obj.getOperation() != MtpOperation.NONE) {
                Log.w(TAG, "Object " + obj.getPath() + " in operation " + obj.getOperation());
                ret = false;
            }
            if (!obj.isRoot() && this.mObjects.get(Integer.valueOf(obj.getId())) != obj) {
                Log.w(TAG, "Object " + obj.getPath() + " is not in map correctly");
                ret = false;
            }
            if (obj.getParent() != null) {
                if (obj.getParent().isRoot()
                        && obj.getParent()
                                != this.mRoots.get(Integer.valueOf(obj.getParent().getId()))) {
                    Log.w(TAG, "Root parent is not in root mapping " + obj.getPath());
                    ret = false;
                }
                if (!obj.getParent().isRoot()
                        && obj.getParent()
                                != this.mObjects.get(Integer.valueOf(obj.getParent().getId()))) {
                    Log.w(TAG, "Parent is not in object mapping " + obj.getPath());
                    ret = false;
                }
                if (obj.getParent().getChild(obj.getName()) != obj) {
                    Log.w(TAG, "Child does not exist in parent " + obj.getPath());
                    ret = false;
                }
            }
            if (obj.isDir()) {
                if (obj.isVisited() == (obj.getObserver() == null)) {
                    Log.w(
                            TAG,
                            obj.getPath()
                                    + " is "
                                    + (obj.isVisited() ? "" : "not ")
                                    + " visited but observer is "
                                    + obj.getObserver());
                    ret = false;
                }
                if (!obj.isVisited() && obj.getChildren().size() > 0) {
                    Log.w(TAG, obj.getPath() + " is not visited but has children");
                    ret = false;
                }
                try {
                    DirectoryStream<Path> stream = Files.newDirectoryStream(obj.getPath());
                    try {
                        Set<String> files = new HashSet<>();
                        for (Path file : stream) {
                            if (obj.isVisited()
                                    && obj.getChild(file.getFileName().toString()) == null
                                    && (this.mSubdirectories == null
                                            || !obj.isRoot()
                                            || this.mSubdirectories.contains(
                                                    file.getFileName().toString()))) {
                                Log.w(TAG, "File exists in fs but not in children " + file);
                                ret = false;
                            }
                            files.add(file.toString());
                        }
                        for (MtpObject child : obj.getChildren()) {
                            if (!files.contains(child.getPath().toString())) {
                                Log.w(
                                        TAG,
                                        "File in children doesn't exist in fs " + child.getPath());
                                ret = false;
                            }
                            if (child != this.mObjects.get(Integer.valueOf(child.getId()))) {
                                Log.w(TAG, "Child is not in object map " + child.getPath());
                                ret = false;
                            }
                        }
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (Throwable th) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException | DirectoryIteratorException e) {
                    Log.w(TAG, e.toString());
                    ret = false;
                }
            }
        }
        return ret;
    }

    public synchronized int beginSendObject(MtpObject parent, String name, int format) {
        sDebugLog("beginSendObject", name);
        if (!parent.isDir()) {
            return -1;
        }
        if (parent.isRoot()
                && this.mSubdirectories != null
                && !this.mSubdirectories.contains(name)) {
            return -1;
        }
        boolean z = true;
        getChildren(parent, true);
        if (format != 12289) {
            z = false;
        }
        MtpObject obj = addObjectToCache(parent, name, z);
        if (obj == null) {
            return -1;
        }
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(MtpOperation.ADD);
        return obj.getId();
    }

    public synchronized boolean endSendObject(MtpObject obj, boolean succeeded) {
        Log.v(TAG, "endSendObject " + succeeded);
        return generalEndAddObject(obj, succeeded, true);
    }

    public synchronized boolean beginRenameObject(MtpObject obj, String newName) {
        sDebugLog("beginRenameObject", obj.getName() + " " + newName);
        if (obj.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(obj)) {
            return false;
        }
        if (obj.getParent().getChild(newName) != null) {
            return false;
        }
        MtpObject oldObj = obj.copy(false);
        obj.setName(newName);
        obj.getParent().addChild(obj);
        oldObj.getParent().addChild(oldObj);
        return generalBeginRenameObject(oldObj, obj);
    }

    public synchronized boolean endRenameObject(MtpObject obj, String oldName, boolean success) {
        MtpObject oldObj;
        Log.v(TAG, "endRenameObject " + success);
        MtpObject parent = obj.getParent();
        oldObj = parent.getChild(oldName);
        if (!success) {
            MtpObjectState oldState = oldObj.getState();
            oldObj.setName(obj.getName());
            oldObj.setState(obj.getState());
            oldObj = obj;
            oldObj.setName(oldName);
            oldObj.setState(oldState);
            obj = oldObj;
            parent.addChild(obj);
            parent.addChild(oldObj);
        }
        return generalEndRenameObject(oldObj, obj, success);
    }

    public synchronized boolean beginRemoveObject(MtpObject obj) {
        boolean z;
        sDebugLog("beginRemoveObject", obj.getName());
        if (!obj.isRoot() && !isSpecialSubDir(obj)) {
            z = generalBeginRemoveObject(obj, MtpOperation.DELETE);
        }
        return z;
    }

    public synchronized boolean endRemoveObject(MtpObject obj, boolean success) {
        boolean z;
        Log.v(TAG, "endRemoveObject " + success);
        boolean ret = true;
        z = false;
        if (obj.isDir()) {
            Iterator it = new ArrayList(obj.getChildren()).iterator();
            while (it.hasNext()) {
                MtpObject child = (MtpObject) it.next();
                if (child.getOperation() == MtpOperation.DELETE) {
                    ret = endRemoveObject(child, success) && ret;
                }
            }
        }
        if (generalEndRemoveObject(obj, success, true) && ret) {
            z = true;
        }
        return z;
    }

    public synchronized boolean beginMoveObject(MtpObject obj, MtpObject newParent) {
        sDebugLog("beginMoveObject", newParent.getPath());
        boolean z = false;
        if (obj.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(obj)) {
            return false;
        }
        getChildren(newParent, true);
        if (newParent.getChild(obj.getName()) != null) {
            return false;
        }
        if (obj.getStorageId() != newParent.getStorageId()) {
            MtpObject newObj = obj.copy(true);
            newObj.setParent(newParent);
            newParent.addChild(newObj);
            if (generalBeginRemoveObject(obj, MtpOperation.RENAME)
                    && generalBeginCopyObject(newObj, false)) {
                z = true;
            }
            return z;
        }
        MtpObject oldObj = obj.copy(false);
        obj.setParent(newParent);
        oldObj.getParent().addChild(oldObj);
        obj.getParent().addChild(obj);
        return generalBeginRenameObject(oldObj, obj);
    }

    public synchronized boolean endMoveObject(
            MtpObject oldParent, MtpObject newParent, String name, boolean success) {
        Log.v(TAG, "endMoveObject " + success);
        MtpObject oldObj = oldParent.getChild(name);
        MtpObject newObj = newParent.getChild(name);
        boolean z = false;
        if (oldObj != null && newObj != null) {
            if (oldParent.getStorageId() != newObj.getStorageId()) {
                boolean ret = endRemoveObject(oldObj, success);
                if (generalEndCopyObject(newObj, success, true) && ret) {
                    z = true;
                }
                return z;
            }
            if (!success) {
                MtpObjectState oldState = oldObj.getState();
                oldObj.setParent(newObj.getParent());
                oldObj.setState(newObj.getState());
                oldObj = newObj;
                oldObj.setParent(oldParent);
                oldObj.setState(oldState);
                newObj = oldObj;
                newObj.getParent().addChild(newObj);
                oldParent.addChild(oldObj);
            }
            return generalEndRenameObject(oldObj, newObj, success);
        }
        return false;
    }

    public synchronized int beginCopyObject(MtpObject object, MtpObject newParent) {
        sDebugLog("beginCopyObject", object.getName() + " " + newParent.getPath());
        String name = object.getName();
        if (!newParent.isDir()) {
            return -1;
        }
        if (newParent.isRoot()
                && this.mSubdirectories != null
                && !this.mSubdirectories.contains(name)) {
            return -1;
        }
        getChildren(newParent, true);
        if (newParent.getChild(name) != null) {
            return -1;
        }
        MtpObject newObj = object.copy(object.isDir());
        newParent.addChild(newObj);
        newObj.setParent(newParent);
        if (!generalBeginCopyObject(newObj, true)) {
            return -1;
        }
        return newObj.getId();
    }

    public synchronized boolean endCopyObject(MtpObject object, boolean success) {
        sDebugLog("endCopyObject " + success, object.getName());
        return generalEndCopyObject(object, success, false);
    }

    private synchronized boolean generalEndAddObject(
            MtpObject obj, boolean succeeded, boolean removeGlobal) {
        switch (obj.getState().ordinal()) {
            case 1:
                if (succeeded) {
                    obj.setState(MtpObjectState.FROZEN_ONESHOT_ADD);
                    break;
                } else if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                break;
            case 2:
                obj.setState(MtpObjectState.NORMAL);
                if (!succeeded) {
                    MtpObject parent = obj.getParent();
                    if (!removeObjectFromCache(obj, removeGlobal, false)) {
                        return false;
                    }
                    handleAddedObject(parent, obj.getName(), obj.isDir());
                    break;
                }
                break;
            case 3:
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                if (succeeded) {
                    this.mMtpNotifier.sendObjectRemoved(obj.getId());
                    break;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private synchronized boolean generalEndRemoveObject(
            MtpObject obj, boolean success, boolean removeGlobal) {
        switch (obj.getState().ordinal()) {
            case 1:
                if (success) {
                    obj.setState(MtpObjectState.FROZEN_ONESHOT_DEL);
                    break;
                } else {
                    obj.setState(MtpObjectState.NORMAL);
                    break;
                }
            case 2:
                obj.setState(MtpObjectState.NORMAL);
                if (success) {
                    MtpObject parent = obj.getParent();
                    if (!removeObjectFromCache(obj, removeGlobal, false)) {
                        return false;
                    }
                    handleAddedObject(parent, obj.getName(), obj.isDir());
                    break;
                }
                break;
            case 3:
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                if (!success) {
                    this.mMtpNotifier.sendObjectRemoved(obj.getId());
                    break;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private synchronized boolean generalBeginRenameObject(MtpObject fromObj, MtpObject toObj) {
        fromObj.setState(MtpObjectState.FROZEN);
        toObj.setState(MtpObjectState.FROZEN);
        fromObj.setOperation(MtpOperation.RENAME);
        toObj.setOperation(MtpOperation.RENAME);
        return true;
    }

    private synchronized boolean generalEndRenameObject(
            MtpObject fromObj, MtpObject toObj, boolean success) {
        boolean ret;
        ret = generalEndRemoveObject(fromObj, success, !success);
        return generalEndAddObject(toObj, success, success) && ret;
    }

    private synchronized boolean generalBeginRemoveObject(MtpObject obj, MtpOperation op) {
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(op);
        if (obj.isDir()) {
            for (MtpObject child : obj.getChildren()) {
                generalBeginRemoveObject(child, op);
            }
        }
        return true;
    }

    private synchronized boolean generalBeginCopyObject(MtpObject obj, boolean newId) {
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(MtpOperation.COPY);
        if (newId) {
            obj.setId(getNextObjectId());
            this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        }
        if (obj.isDir()) {
            for (MtpObject child : obj.getChildren()) {
                if (!generalBeginCopyObject(child, newId)) {
                    return false;
                }
            }
        }
        return true;
    }

    private synchronized boolean generalEndCopyObject(
            MtpObject obj, boolean success, boolean addGlobal) {
        boolean ret;
        boolean z;
        if (success && addGlobal) {
            this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        }
        boolean ret2 = true;
        ret = false;
        if (obj.isDir()) {
            Iterator it = new ArrayList(obj.getChildren()).iterator();
            while (it.hasNext()) {
                MtpObject child = (MtpObject) it.next();
                if (child.getOperation() == MtpOperation.COPY) {
                    ret2 = generalEndCopyObject(child, success, addGlobal) && ret2;
                }
            }
        }
        if (!success && addGlobal) {
            z = false;
            if (generalEndAddObject(obj, success, z) && ret2) {
                ret = true;
            }
        }
        z = true;
        if (generalEndAddObject(obj, success, z)) {
            ret = true;
        }
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sDebugLog(String str, String path) {
        try {
            Log.i(TAG, str + " : " + Base64.encodeToString(path.getBytes("UTF-8"), 2));
        } catch (UnsupportedEncodingException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sDebugLog(String str, Path path) {
        try {
            Log.i(TAG, str + " : " + Base64.encodeToString(path.toString().getBytes("UTF-8"), 2));
        } catch (UnsupportedEncodingException e) {
        }
    }
}
