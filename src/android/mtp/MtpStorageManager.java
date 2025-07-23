package android.mtp;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaFile;
import android.media.MediaMetrics;
import android.os.FileObserver;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Base64;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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
    private static final String TAG = "MtpStorageManager";
    private MtpNotifier mMtpNotifier;
    private Set<String> mSubdirectories;
    private HashMap<Integer, MtpObject> mObjects = new HashMap<>();
    private HashMap<Integer, MtpObject> mRoots = new HashMap<>();
    private int mNextObjectId = 1;
    private int mNextStorageId = 2;
    private volatile boolean mCheckConsistency = false;
    private Thread mConsistencyThread = new Thread(new Runnable() { // from class: android.mtp.MtpStorageManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            MtpStorageManager.this.lambda$new$0();
        }
    });

    public static abstract class MtpNotifier {
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

        @Override // android.os.FileObserver
        public void finalize() {
        }

        MtpObjectObserver(MtpObject mtpObject) {
            super(mtpObject.getPath().toString(), 16778184);
            this.mObject = mtpObject;
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x007d, code lost:
        
            r4.this$0.sDebugLog("Object was null in event", r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0085, code lost:
        
            return;
         */
        @Override // android.os.FileObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onEvent(int r5, java.lang.String r6) {
            /*
                r4 = this;
                java.lang.String r0 = "Got unrecognized event "
                android.mtp.MtpStorageManager r1 = android.mtp.MtpStorageManager.this
                monitor-enter(r1)
                r2 = r5 & 16384(0x4000, float:2.2959E-41)
                if (r2 == 0) goto L12
                java.lang.String r2 = android.mtp.MtpStorageManager.m3296$$Nest$sfgetTAG()     // Catch: java.lang.Throwable -> Lab
                java.lang.String r3 = "Received Inotify overflow event!"
                android.util.Log.e(r2, r3)     // Catch: java.lang.Throwable -> Lab
            L12:
                android.mtp.MtpStorageManager$MtpObject r2 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager$MtpObject r2 = android.mtp.MtpStorageManager.MtpObject.m3304$$Nest$mgetChild(r2, r6)     // Catch: java.lang.Throwable -> Lab
                r3 = r5 & 128(0x80, float:1.8E-43)
                if (r3 != 0) goto L93
                r3 = r5 & 256(0x100, float:3.59E-43)
                if (r3 == 0) goto L22
                goto L93
            L22:
                r3 = r5 & 64
                if (r3 != 0) goto L7b
                r3 = r5 & 512(0x200, float:7.17E-43)
                if (r3 == 0) goto L2b
                goto L7b
            L2b:
                r2 = 32768(0x8000, float:4.5918E-41)
                r2 = r2 & r5
                if (r2 == 0) goto L56
                android.mtp.MtpStorageManager r5 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.String r6 = "Got inotify deleted"
                android.mtp.MtpStorageManager$MtpObject r0 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                java.nio.file.Path r0 = r0.getPath()     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager.m3295$$Nest$msDebugLog(r5, r6, r0)     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager$MtpObject r5 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                android.os.FileObserver r5 = android.mtp.MtpStorageManager.MtpObject.m3298$$Nest$fgetmObserver(r5)     // Catch: java.lang.Throwable -> Lab
                if (r5 == 0) goto L4f
                android.mtp.MtpStorageManager$MtpObject r5 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                android.os.FileObserver r5 = android.mtp.MtpStorageManager.MtpObject.m3298$$Nest$fgetmObserver(r5)     // Catch: java.lang.Throwable -> Lab
                r5.stopWatching()     // Catch: java.lang.Throwable -> Lab
            L4f:
                android.mtp.MtpStorageManager$MtpObject r4 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                r5 = 0
                android.mtp.MtpStorageManager.MtpObject.m3300$$Nest$fputmObserver(r4, r5)     // Catch: java.lang.Throwable -> Lab
                goto La9
            L56:
                r2 = r5 & 8
                if (r2 == 0) goto L69
                android.mtp.MtpStorageManager r5 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.String r0 = "Got inotify CLOSE_WRITE event for"
                android.mtp.MtpStorageManager.m3294$$Nest$msDebugLog(r5, r0, r6)     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager r5 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager$MtpObject r4 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager.m3292$$Nest$mhandleChangedObject(r5, r4, r6)     // Catch: java.lang.Throwable -> Lab
                goto La9
            L69:
                android.mtp.MtpStorageManager r4 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab
                r2.<init>(r0)     // Catch: java.lang.Throwable -> Lab
                r2.append(r5)     // Catch: java.lang.Throwable -> Lab
                java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager.m3294$$Nest$msDebugLog(r4, r5, r6)     // Catch: java.lang.Throwable -> Lab
                goto La9
            L7b:
                if (r2 != 0) goto L86
                android.mtp.MtpStorageManager r4 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.String r5 = "Object was null in event"
                android.mtp.MtpStorageManager.m3294$$Nest$msDebugLog(r4, r5, r6)     // Catch: java.lang.Throwable -> Lab
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lab
                return
            L86:
                android.mtp.MtpStorageManager r5 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.String r0 = "Got inotify removed event for"
                android.mtp.MtpStorageManager.m3294$$Nest$msDebugLog(r5, r0, r6)     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager r4 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager.m3293$$Nest$mhandleRemovedObject(r4, r2)     // Catch: java.lang.Throwable -> Lab
                goto La9
            L93:
                android.mtp.MtpStorageManager r0 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                java.lang.String r2 = "Got inotify added event for"
                android.mtp.MtpStorageManager.m3294$$Nest$msDebugLog(r0, r2, r6)     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager r0 = android.mtp.MtpStorageManager.this     // Catch: java.lang.Throwable -> Lab
                android.mtp.MtpStorageManager$MtpObject r4 = r4.mObject     // Catch: java.lang.Throwable -> Lab
                r2 = 1073741824(0x40000000, float:2.0)
                r5 = r5 & r2
                if (r5 == 0) goto La5
                r5 = 1
                goto La6
            La5:
                r5 = 0
            La6:
                android.mtp.MtpStorageManager.m3291$$Nest$mhandleAddedObject(r0, r4, r6, r5)     // Catch: java.lang.Throwable -> Lab
            La9:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lab
                return
            Lab:
                r4 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lab
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.MtpObjectObserver.onEvent(int, java.lang.String):void");
        }
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

        MtpObject(String str, int i, MtpStorage mtpStorage, MtpObject mtpObject, boolean z) {
            this.mId = i;
            this.mName = str;
            this.mStorage = (MtpStorage) Preconditions.checkNotNull(mtpStorage);
            this.mParent = mtpObject;
            this.mIsDir = z;
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

        private long maybeApplyTranscodeLengthWorkaround(long j) {
            return (this.mStorage.isHostWindows() && isTranscodeMtpEnabled() && isFileTranscodeSupported()) ? j * 2 : j;
        }

        private boolean isTranscodeMtpEnabled() {
            return SystemProperties.getBoolean("sys.fuse.transcode_mtp", false);
        }

        private boolean isFileTranscodeSupported() {
            try {
                return Os.stat(getPath().toString()).st_nlink > 1;
            } catch (ErrnoException unused) {
                Log.w(MtpStorageManager.TAG, "Failed to stat path: " + getPath() + ". Ignoring transcoding.");
                return false;
            }
        }

        public Path getPath() {
            return isRoot() ? Paths.get(this.mName, new String[0]) : this.mParent.getPath().resolve(this.mName);
        }

        public boolean isRoot() {
            return this.mParent == null;
        }

        public String getVolumeName() {
            return this.mStorage.getVolumeName();
        }

        public boolean isSkipObserving() {
            return getPath().toString().startsWith("/storage/emulated/0/SmartSwitch/tmp");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String str) {
            this.mName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(int i) {
            this.mId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVisited() {
            return this.mVisited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParent(MtpObject mtpObject) {
            if (getStorageId() != mtpObject.getStorageId()) {
                this.mStorage = (MtpStorage) Preconditions.checkNotNull(mtpObject.getStorage());
            }
            this.mParent = mtpObject;
        }

        private MtpStorage getStorage() {
            return this.mStorage;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDir(boolean z) {
            if (z != this.mIsDir) {
                this.mIsDir = z;
                this.mChildren = z ? new HashMap<>() : null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVisited(boolean z) {
            this.mVisited = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObjectState getState() {
            return this.mState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(MtpObjectState mtpObjectState) {
            this.mState = mtpObjectState;
            if (mtpObjectState == MtpObjectState.NORMAL) {
                this.mOp = MtpOperation.NONE;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpOperation getOperation() {
            return this.mOp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOperation(MtpOperation mtpOperation) {
            this.mOp = mtpOperation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FileObserver getObserver() {
            return this.mObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setObserver(FileObserver fileObserver) {
            this.mObserver = fileObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChild(MtpObject mtpObject) {
            this.mChildren.put(mtpObject.getName(), mtpObject);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObject getChild(String str) {
            return this.mChildren.get(str);
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
        public MtpObject copy(boolean z) {
            MtpObject mtpObject = new MtpObject(this.mName, this.mId, this.mStorage, this.mParent, this.mIsDir);
            mtpObject.mIsDir = this.mIsDir;
            mtpObject.mVisited = this.mVisited;
            mtpObject.mState = this.mState;
            mtpObject.mChildren = this.mIsDir ? new HashMap<>() : null;
            if (z && this.mIsDir) {
                Iterator<MtpObject> it = this.mChildren.values().iterator();
                while (it.hasNext()) {
                    MtpObject copy = it.next().copy(true);
                    copy.setParent(mtpObject);
                    mtpObject.addChild(copy);
                }
            }
            return mtpObject;
        }
    }

    public MtpStorageManager(MtpNotifier mtpNotifier, Set<String> set) {
        this.mMtpNotifier = mtpNotifier;
        this.mSubdirectories = set;
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
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    public synchronized void close() {
        for (MtpObject mtpObject : this.mObjects.values()) {
            if (mtpObject.getObserver() != null) {
                mtpObject.getObserver().stopWatching();
                mtpObject.setObserver(null);
            }
        }
        for (MtpObject mtpObject2 : this.mRoots.values()) {
            if (mtpObject2.getObserver() != null) {
                mtpObject2.getObserver().stopWatching();
                mtpObject2.setObserver(null);
            }
        }
        if (this.mCheckConsistency) {
            this.mCheckConsistency = false;
            this.mConsistencyThread.interrupt();
            try {
                this.mConsistencyThread.join();
            } catch (InterruptedException unused) {
            }
        }
    }

    public synchronized void setSubdirectories(Set<String> set) {
        this.mSubdirectories = set;
    }

    public synchronized MtpStorage addMtpStorage(StorageVolume storageVolume, Supplier<Boolean> supplier) {
        int parseInt;
        String id = storageVolume.getId();
        if (id != null && id.startsWith("emulated;") && (parseInt = Integer.parseInt(id.substring(id.lastIndexOf(NavigationBarInflaterView.GRAVITY_SEPARATOR) + 1))) >= 95 && parseInt < 100) {
            MtpStorage mtpStorage = new MtpStorage(storageVolume, 65538, supplier);
            this.mRoots.put(65538, new MtpObject(mtpStorage.getPath(), 65538, mtpStorage, null, true));
            return mtpStorage;
        }
        int storageId = storageVolume.getStorageId();
        if (storageId != 65537) {
            storageId = ((getNextStorageId() & 65535) << 16) + 1;
        }
        int i = storageId;
        MtpStorage mtpStorage2 = new MtpStorage(storageVolume, i, supplier);
        this.mRoots.put(Integer.valueOf(i), new MtpObject(mtpStorage2.getPath(), i, mtpStorage2, null, true));
        return mtpStorage2;
    }

    public synchronized void removeMtpStorage(MtpStorage mtpStorage) {
        removeObjectFromCache(getStorageRoot(mtpStorage.getStorageId()), true, true);
    }

    private synchronized boolean isSpecialSubDir(MtpObject mtpObject) {
        boolean z;
        Set<String> set;
        if (mtpObject.getParent().isRoot() && (set = this.mSubdirectories) != null) {
            z = set.contains(mtpObject.getName()) ? false : true;
        }
        return z;
    }

    public synchronized MtpObject getByPath(String str) {
        MtpObject mtpObject = null;
        for (MtpObject mtpObject2 : this.mRoots.values()) {
            if (str.startsWith(mtpObject2.getName())) {
                str = str.substring(mtpObject2.getName().length());
                mtpObject = mtpObject2;
            }
        }
        for (String str2 : str.split("/")) {
            if (mtpObject != null && mtpObject.isDir()) {
                if (!"".equals(str2)) {
                    if (!mtpObject.isVisited()) {
                        getChildren(mtpObject, true);
                    }
                    mtpObject = mtpObject.getChild(str2);
                }
            }
            return null;
        }
        return mtpObject;
    }

    public synchronized MtpObject getObject(int i) {
        if (i == 0 || i == -1) {
            Log.w(TAG, "Can't get root storages with getObject()");
            return null;
        }
        if (!this.mObjects.containsKey(Integer.valueOf(i))) {
            Log.w(TAG, "Id " + i + " doesn't exist");
            return null;
        }
        return this.mObjects.get(Integer.valueOf(i));
    }

    public MtpObject getStorageRoot(int i) {
        if (!this.mRoots.containsKey(Integer.valueOf(i))) {
            Log.w(TAG, "StorageId " + i + " doesn't exist");
            return null;
        }
        return this.mRoots.get(Integer.valueOf(i));
    }

    private int getNextObjectId() {
        int i = this.mNextObjectId;
        this.mNextObjectId = (int) (i + 1);
        return i;
    }

    private int getNextStorageId() {
        int i = this.mNextStorageId;
        this.mNextStorageId = i + 1;
        return i;
    }

    public synchronized List<MtpObject> getObjects(int i, int i2, int i3, boolean z) {
        boolean z2 = true;
        boolean z3 = i == 0;
        try {
            try {
                ArrayList arrayList = new ArrayList();
                if (i == -1) {
                    i = 0;
                }
                if (i3 == -1 && i == 0) {
                    Iterator<MtpObject> it = this.mRoots.values().iterator();
                    while (it.hasNext()) {
                        MtpStorageManager mtpStorageManager = this;
                        int i4 = i2;
                        boolean z4 = z;
                        z2 &= mtpStorageManager.getObjects(arrayList, it.next(), i4, z3, z4);
                        this = mtpStorageManager;
                        i2 = i4;
                        z = z4;
                    }
                    MtpStorageManager mtpStorageManager2 = this;
                    if (!z2) {
                        arrayList = null;
                    }
                    return arrayList;
                }
                MtpObject storageRoot = i == 0 ? getStorageRoot(i3) : getObject(i);
                if (storageRoot == null) {
                    return null;
                }
                if (!getObjects(arrayList, storageRoot, i2, z3, z)) {
                    arrayList = null;
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                MtpStorageManager mtpStorageManager3 = this;
                Throwable th2 = th;
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            Throwable th22 = th;
            throw th22;
        }
    }

    private synchronized boolean getObjects(List<MtpObject> list, MtpObject mtpObject, int i, boolean z, boolean z2) {
        MtpStorageManager mtpStorageManager;
        Throwable th;
        MtpStorageManager mtpStorageManager2;
        List<MtpObject> list2;
        int i2;
        boolean z3;
        try {
            try {
                Collection<MtpObject> children = getChildren(mtpObject, z2);
                if (children != null) {
                    for (MtpObject mtpObject2 : children) {
                        try {
                            if (i == 0 || mtpObject2.getFormat() == i) {
                                list.add(mtpObject2);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            mtpStorageManager = this;
                            throw th;
                        }
                    }
                    boolean z4 = true;
                    if (z) {
                        for (MtpObject mtpObject3 : children) {
                            if (mtpObject3.isDir()) {
                                mtpStorageManager2 = this;
                                list2 = list;
                                i2 = i;
                                z3 = z2;
                                z4 &= mtpStorageManager2.getObjects(list2, mtpObject3, i2, true, z3);
                            } else {
                                mtpStorageManager2 = this;
                                list2 = list;
                                i2 = i;
                                z3 = z2;
                            }
                            this = mtpStorageManager2;
                            list = list2;
                            i = i2;
                            z2 = z3;
                        }
                    }
                    return z4;
                }
            } catch (Throwable th3) {
                th = th3;
                mtpStorageManager = this;
                th = th;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            th = th;
            throw th;
        }
        return false;
    }

    private synchronized Collection<MtpObject> getChildren(MtpObject mtpObject, boolean z) {
        if (mtpObject != null) {
            if (mtpObject.isDir()) {
                if (!mtpObject.isVisited() && z) {
                    Path path = mtpObject.getPath();
                    if (mtpObject.getObserver() != null) {
                        Log.e(TAG, "Observer is not null!");
                    }
                    mtpObject.setObserver(new MtpObjectObserver(mtpObject));
                    mtpObject.getObserver().startWatching();
                    try {
                        DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path);
                        try {
                            for (Path path2 : newDirectoryStream) {
                                addObjectToCache(mtpObject, path2.getFileName().toString(), path2.toFile().isDirectory());
                            }
                            if (newDirectoryStream != null) {
                                newDirectoryStream.close();
                            }
                            mtpObject.setVisited(true);
                            if (mtpObject.isDir() && (mtpObject.isSkipObserving() || mtpObject.getName().startsWith(MediaMetrics.SEPARATOR))) {
                                mtpObject.getObserver().stopWatching();
                                mtpObject.setObserver(null);
                            }
                        } catch (Throwable th) {
                            if (newDirectoryStream != null) {
                                try {
                                    newDirectoryStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException | DirectoryIteratorException e) {
                        Log.e(TAG, e.toString());
                        mtpObject.getObserver().stopWatching();
                        mtpObject.setObserver(null);
                        return null;
                    }
                }
                return mtpObject.getChildren();
            }
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("Can't find children of ");
        sb.append(mtpObject == null ? PerfettoProtoLogImpl.NULL_STRING : Integer.valueOf(mtpObject.getId()));
        Log.w(str, sb.toString());
        return null;
    }

    private synchronized MtpObject addObjectToCache(MtpObject mtpObject, String str, boolean z) {
        if (!mtpObject.isRoot() && getObject(mtpObject.getId()) != mtpObject) {
            return null;
        }
        if (mtpObject.getChild(str) != null) {
            return null;
        }
        if (this.mSubdirectories != null && mtpObject.isRoot() && !this.mSubdirectories.contains(str)) {
            return null;
        }
        MtpObject mtpObject2 = new MtpObject(str, getNextObjectId(), mtpObject.mStorage, mtpObject, z);
        this.mObjects.put(Integer.valueOf(mtpObject2.getId()), mtpObject2);
        mtpObject.addChild(mtpObject2);
        return mtpObject2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
    
        if (r0 != false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0021 A[Catch: all -> 0x00a6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:18:0x005d, B:19:0x0066, B:21:0x006c, B:22:0x0077, B:25:0x007f, B:26:0x008c, B:28:0x0092, B:46:0x0048), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030 A[Catch: all -> 0x00a6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:18:0x005d, B:19:0x0066, B:21:0x006c, B:22:0x0077, B:25:0x007f, B:26:0x008c, B:28:0x0092, B:46:0x0048), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005d A[Catch: all -> 0x00a6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:18:0x005d, B:19:0x0066, B:21:0x006c, B:22:0x0077, B:25:0x007f, B:26:0x008c, B:28:0x0092, B:46:0x0048), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006c A[Catch: all -> 0x00a6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:18:0x005d, B:19:0x0066, B:21:0x006c, B:22:0x0077, B:25:0x007f, B:26:0x008c, B:28:0x0092, B:46:0x0048), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0092 A[Catch: all -> 0x00a6, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:11:0x002a, B:13:0x0030, B:18:0x005d, B:19:0x0066, B:21:0x006c, B:22:0x0077, B:25:0x007f, B:26:0x008c, B:28:0x0092, B:46:0x0048), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean removeObjectFromCache(android.mtp.MtpStorageManager.MtpObject r6, boolean r7, boolean r8) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r6.isRoot()     // Catch: java.lang.Throwable -> La6
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L1e
            android.mtp.MtpStorageManager$MtpObject r0 = r6.getParent()     // Catch: java.lang.Throwable -> La6
            java.util.HashMap r0 = android.mtp.MtpStorageManager.MtpObject.m3297$$Nest$fgetmChildren(r0)     // Catch: java.lang.Throwable -> La6
            java.lang.String r3 = r6.getName()     // Catch: java.lang.Throwable -> La6
            boolean r0 = r0.remove(r3, r6)     // Catch: java.lang.Throwable -> La6
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
            java.nio.file.Path r4 = r6.getPath()     // Catch: java.lang.Throwable -> La6
            r5.sDebugLog(r3, r4)     // Catch: java.lang.Throwable -> La6
        L2a:
            boolean r3 = r6.isRoot()     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto L46
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r5.mRoots     // Catch: java.lang.Throwable -> La6
            int r4 = r6.getId()     // Catch: java.lang.Throwable -> La6
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> La6
            boolean r3 = r3.remove(r4, r6)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto L44
            if (r0 == 0) goto L44
        L42:
            r0 = r2
            goto L5b
        L44:
            r0 = r1
            goto L5b
        L46:
            if (r7 == 0) goto L5b
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r5.mObjects     // Catch: java.lang.Throwable -> La6
            int r4 = r6.getId()     // Catch: java.lang.Throwable -> La6
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> La6
            boolean r3 = r3.remove(r4, r6)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto L44
            if (r0 == 0) goto L44
            goto L42
        L5b:
            if (r0 != 0) goto L66
            java.lang.String r3 = "Failed to remove from global cache "
            java.nio.file.Path r4 = r6.getPath()     // Catch: java.lang.Throwable -> La6
            r5.sDebugLog(r3, r4)     // Catch: java.lang.Throwable -> La6
        L66:
            android.os.FileObserver r3 = android.mtp.MtpStorageManager.MtpObject.m3306$$Nest$mgetObserver(r6)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto L77
            android.os.FileObserver r3 = android.mtp.MtpStorageManager.MtpObject.m3306$$Nest$mgetObserver(r6)     // Catch: java.lang.Throwable -> La6
            r3.stopWatching()     // Catch: java.lang.Throwable -> La6
            r3 = 0
            android.mtp.MtpStorageManager.MtpObject.m3313$$Nest$msetObserver(r6, r3)     // Catch: java.lang.Throwable -> La6
        L77:
            boolean r3 = r6.isDir()     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto La4
            if (r8 == 0) goto La4
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La6
            java.util.Collection r6 = android.mtp.MtpStorageManager.MtpObject.m3305$$Nest$mgetChildren(r6)     // Catch: java.lang.Throwable -> La6
            r8.<init>(r6)     // Catch: java.lang.Throwable -> La6
            java.util.Iterator r6 = r8.iterator()     // Catch: java.lang.Throwable -> La6
        L8c:
            boolean r8 = r6.hasNext()     // Catch: java.lang.Throwable -> La6
            if (r8 == 0) goto La4
            java.lang.Object r8 = r6.next()     // Catch: java.lang.Throwable -> La6
            android.mtp.MtpStorageManager$MtpObject r8 = (android.mtp.MtpStorageManager.MtpObject) r8     // Catch: java.lang.Throwable -> La6
            boolean r8 = r5.removeObjectFromCache(r8, r7, r2)     // Catch: java.lang.Throwable -> La6
            if (r8 == 0) goto La2
            if (r0 == 0) goto La2
            r0 = r2
            goto L8c
        La2:
            r0 = r1
            goto L8c
        La4:
            monitor-exit(r5)
            return r0
        La6:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> La6
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.removeObjectFromCache(android.mtp.MtpStorageManager$MtpObject, boolean, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleAddedObject(MtpObject mtpObject, String str, boolean z) {
        DirectoryStream<Path> newDirectoryStream;
        MtpOperation mtpOperation = MtpOperation.NONE;
        MtpObject child = mtpObject.getChild(str);
        if (child != null) {
            MtpObjectState state = child.getState();
            mtpOperation = child.getOperation();
            if (child.isDir() != z && state != MtpObjectState.FROZEN_REMOVED) {
                sDebugLog("Inconsistent directory info! ", child.getPath());
            }
            child.setDir(z);
            int ordinal = state.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    if (ordinal != 2) {
                        if (ordinal != 3) {
                            if (ordinal == 4) {
                                child.setState(MtpObjectState.NORMAL);
                            } else {
                                sDebugLog("Unexpected state in add " + state, str);
                            }
                            Log.i(TAG, state + " transitioned to " + child.getState() + " in op " + mtpOperation);
                        }
                    }
                }
                child.setState(MtpObjectState.FROZEN_ADDED);
                Log.i(TAG, state + " transitioned to " + child.getState() + " in op " + mtpOperation);
            }
            return;
        }
        child = addObjectToCache(mtpObject, str, z);
        if (child != null) {
            this.mMtpNotifier.sendObjectAdded(child.getId());
        } else {
            sDebugLog("object alraeady exists", str);
            return;
        }
        if (z) {
            if (mtpOperation == MtpOperation.RENAME) {
                return;
            }
            if (mtpOperation == MtpOperation.COPY && !child.isVisited()) {
                return;
            }
            if (child.getObserver() != null) {
                Log.e(TAG, "Observer is not null!");
                return;
            }
            child.setObserver(new MtpObjectObserver(child));
            child.getObserver().startWatching();
            child.setVisited(true);
            try {
                newDirectoryStream = Files.newDirectoryStream(child.getPath());
            } catch (IOException | DirectoryIteratorException e) {
                Log.e(TAG, e.toString());
                child.getObserver().stopWatching();
                child.setObserver(null);
            }
            try {
                for (Path path : newDirectoryStream) {
                    sDebugLog("Manually handling event for ", path.getFileName().toString());
                    handleAddedObject(child, path.getFileName().toString(), path.toFile().isDirectory());
                }
                if (newDirectoryStream != null) {
                    newDirectoryStream.close();
                }
                if (child.isSkipObserving() || child.getName().startsWith(MediaMetrics.SEPARATOR)) {
                    child.getObserver().stopWatching();
                    child.setObserver(null);
                }
            } catch (Throwable th) {
                if (newDirectoryStream != null) {
                    try {
                        newDirectoryStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemovedObject(MtpObject mtpObject) {
        MtpObjectState state = mtpObject.getState();
        MtpOperation operation = mtpObject.getOperation();
        int ordinal = state.ordinal();
        boolean z = true;
        if (ordinal != 0) {
            if (ordinal == 1) {
                mtpObject.setState(MtpObjectState.FROZEN_REMOVED);
            } else if (ordinal == 2) {
                mtpObject.setState(MtpObjectState.FROZEN_REMOVED);
            } else if (ordinal == 5) {
                if (operation == MtpOperation.RENAME) {
                    z = false;
                }
                removeObjectFromCache(mtpObject, z, false);
            } else {
                sDebugLog("Got unexpected object remove for", mtpObject.getName());
            }
        } else if (removeObjectFromCache(mtpObject, true, true)) {
            this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
        }
        Log.i(TAG, state + " transitioned to " + mtpObject.getState() + " in op " + operation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleChangedObject(MtpObject mtpObject, String str) {
        MtpOperation mtpOperation = MtpOperation.NONE;
        MtpObject child = mtpObject.getChild(str);
        if (child != null) {
            if (!child.isDir() && child.getSize() > 0) {
                child.getState();
                child.getOperation();
                this.mMtpNotifier.sendObjectInfoChanged(child.getId());
                Log.d(TAG, "sendObjectInfoChanged: id=" + child.getId() + ",size=" + child.getSize());
            }
        } else {
            sDebugLog("object is null", str);
        }
    }

    public void flushEvents() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException unused) {
        }
    }

    public synchronized void dump() {
        for (Integer num : this.mObjects.keySet()) {
            int intValue = num.intValue();
            MtpObject mtpObject = this.mObjects.get(num);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(intValue);
            sb.append(" | ");
            sb.append(mtpObject.getParent() == null ? Integer.valueOf(mtpObject.getParent().getId()) : PerfettoProtoLogImpl.NULL_STRING);
            sb.append(" | ");
            sb.append(mtpObject.getName());
            sb.append(" | ");
            sb.append(mtpObject.isDir() ? "dir" : "obj");
            sb.append(" | ");
            sb.append(mtpObject.isVisited() ? "v" : "nv");
            sb.append(" | ");
            sb.append(mtpObject.getState());
            Log.i(str, sb.toString());
        }
    }

    public synchronized boolean checkConsistency() {
        boolean z;
        ArrayList<MtpObject> arrayList = new ArrayList();
        arrayList.addAll(this.mRoots.values());
        arrayList.addAll(this.mObjects.values());
        z = true;
        for (MtpObject mtpObject : arrayList) {
            if (!mtpObject.exists()) {
                Log.w(TAG, "Object doesn't exist " + mtpObject.getPath() + " " + mtpObject.getId());
                z = false;
            }
            if (mtpObject.getState() != MtpObjectState.NORMAL) {
                Log.w(TAG, "Object " + mtpObject.getPath() + " in state " + mtpObject.getState());
                z = false;
            }
            if (mtpObject.getOperation() != MtpOperation.NONE) {
                Log.w(TAG, "Object " + mtpObject.getPath() + " in operation " + mtpObject.getOperation());
                z = false;
            }
            if (!mtpObject.isRoot() && this.mObjects.get(Integer.valueOf(mtpObject.getId())) != mtpObject) {
                Log.w(TAG, "Object " + mtpObject.getPath() + " is not in map correctly");
                z = false;
            }
            if (mtpObject.getParent() != null) {
                if (mtpObject.getParent().isRoot() && mtpObject.getParent() != this.mRoots.get(Integer.valueOf(mtpObject.getParent().getId()))) {
                    Log.w(TAG, "Root parent is not in root mapping " + mtpObject.getPath());
                    z = false;
                }
                if (!mtpObject.getParent().isRoot() && mtpObject.getParent() != this.mObjects.get(Integer.valueOf(mtpObject.getParent().getId()))) {
                    Log.w(TAG, "Parent is not in object mapping " + mtpObject.getPath());
                    z = false;
                }
                if (mtpObject.getParent().getChild(mtpObject.getName()) != mtpObject) {
                    Log.w(TAG, "Child does not exist in parent " + mtpObject.getPath());
                    z = false;
                }
            }
            if (mtpObject.isDir()) {
                if (mtpObject.isVisited() == (mtpObject.getObserver() == null)) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append(mtpObject.getPath());
                    sb.append(" is ");
                    sb.append(mtpObject.isVisited() ? "" : "not ");
                    sb.append(" visited but observer is ");
                    sb.append(mtpObject.getObserver());
                    Log.w(str, sb.toString());
                    z = false;
                }
                if (!mtpObject.isVisited() && mtpObject.getChildren().size() > 0) {
                    Log.w(TAG, mtpObject.getPath() + " is not visited but has children");
                    z = false;
                }
                try {
                    DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(mtpObject.getPath());
                    try {
                        HashSet hashSet = new HashSet();
                        for (Path path : newDirectoryStream) {
                            if (mtpObject.isVisited() && mtpObject.getChild(path.getFileName().toString()) == null && (this.mSubdirectories == null || !mtpObject.isRoot() || this.mSubdirectories.contains(path.getFileName().toString()))) {
                                Log.w(TAG, "File exists in fs but not in children " + path);
                                z = false;
                            }
                            hashSet.add(path.toString());
                        }
                        for (MtpObject mtpObject2 : mtpObject.getChildren()) {
                            if (!hashSet.contains(mtpObject2.getPath().toString())) {
                                Log.w(TAG, "File in children doesn't exist in fs " + mtpObject2.getPath());
                                z = false;
                            }
                            if (mtpObject2 != this.mObjects.get(Integer.valueOf(mtpObject2.getId()))) {
                                Log.w(TAG, "Child is not in object map " + mtpObject2.getPath());
                                z = false;
                            }
                        }
                        if (newDirectoryStream != null) {
                            newDirectoryStream.close();
                        }
                    } catch (Throwable th) {
                        if (newDirectoryStream != null) {
                            try {
                                newDirectoryStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException | DirectoryIteratorException e) {
                    Log.w(TAG, e.toString());
                    z = false;
                }
            }
        }
        return z;
    }

    public synchronized int beginSendObject(MtpObject mtpObject, String str, int i) {
        Set<String> set;
        sDebugLog("beginSendObject", str);
        if (!mtpObject.isDir()) {
            return -1;
        }
        if (mtpObject.isRoot() && (set = this.mSubdirectories) != null && !set.contains(str)) {
            return -1;
        }
        boolean z = true;
        getChildren(mtpObject, true);
        if (i != 12289) {
            z = false;
        }
        MtpObject addObjectToCache = addObjectToCache(mtpObject, str, z);
        if (addObjectToCache == null) {
            return -1;
        }
        addObjectToCache.setState(MtpObjectState.FROZEN);
        addObjectToCache.setOperation(MtpOperation.ADD);
        return addObjectToCache.getId();
    }

    public synchronized boolean endSendObject(MtpObject mtpObject, boolean z) {
        Log.v(TAG, "endSendObject " + z);
        return generalEndAddObject(mtpObject, z, true);
    }

    public synchronized boolean beginRenameObject(MtpObject mtpObject, String str) {
        sDebugLog("beginRenameObject", mtpObject.getName() + " " + str);
        if (mtpObject.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(mtpObject)) {
            return false;
        }
        if (mtpObject.getParent().getChild(str) != null) {
            return false;
        }
        MtpObject copy = mtpObject.copy(false);
        mtpObject.setName(str);
        mtpObject.getParent().addChild(mtpObject);
        copy.getParent().addChild(copy);
        return generalBeginRenameObject(copy, mtpObject);
    }

    public synchronized boolean endRenameObject(MtpObject mtpObject, String str, boolean z) {
        MtpObject child;
        Log.v(TAG, "endRenameObject " + z);
        MtpObject parent = mtpObject.getParent();
        child = parent.getChild(str);
        if (z) {
            child = mtpObject;
            mtpObject = child;
        } else {
            MtpObjectState state = child.getState();
            child.setName(mtpObject.getName());
            child.setState(mtpObject.getState());
            mtpObject.setName(str);
            mtpObject.setState(state);
            parent.addChild(child);
            parent.addChild(mtpObject);
        }
        return generalEndRenameObject(mtpObject, child, z);
    }

    public synchronized boolean beginRemoveObject(MtpObject mtpObject) {
        boolean z;
        sDebugLog("beginRemoveObject", mtpObject.getName());
        if (!mtpObject.isRoot() && !isSpecialSubDir(mtpObject)) {
            z = generalBeginRemoveObject(mtpObject, MtpOperation.DELETE);
        }
        return z;
    }

    public synchronized boolean endRemoveObject(MtpObject mtpObject, boolean z) {
        boolean z2;
        boolean z3;
        Log.v(TAG, "endRemoveObject " + z);
        z2 = false;
        if (mtpObject.isDir()) {
            Iterator it = new ArrayList(mtpObject.getChildren()).iterator();
            loop0: while (true) {
                z3 = true;
                while (it.hasNext()) {
                    MtpObject mtpObject2 = (MtpObject) it.next();
                    if (mtpObject2.getOperation() == MtpOperation.DELETE) {
                        if (!endRemoveObject(mtpObject2, z) || !z3) {
                            z3 = false;
                        }
                    }
                }
            }
        } else {
            z3 = true;
        }
        if (generalEndRemoveObject(mtpObject, z, true) && z3) {
            z2 = true;
        }
        return z2;
    }

    public synchronized boolean beginMoveObject(MtpObject mtpObject, MtpObject mtpObject2) {
        sDebugLog("beginMoveObject", mtpObject2.getPath());
        boolean z = false;
        if (mtpObject.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(mtpObject)) {
            return false;
        }
        getChildren(mtpObject2, true);
        if (mtpObject2.getChild(mtpObject.getName()) != null) {
            return false;
        }
        if (mtpObject.getStorageId() != mtpObject2.getStorageId()) {
            MtpObject copy = mtpObject.copy(true);
            copy.setParent(mtpObject2);
            mtpObject2.addChild(copy);
            if (generalBeginRemoveObject(mtpObject, MtpOperation.RENAME) && generalBeginCopyObject(copy, false)) {
                z = true;
            }
            return z;
        }
        MtpObject copy2 = mtpObject.copy(false);
        mtpObject.setParent(mtpObject2);
        copy2.getParent().addChild(copy2);
        mtpObject.getParent().addChild(mtpObject);
        return generalBeginRenameObject(copy2, mtpObject);
    }

    public synchronized boolean endMoveObject(MtpObject mtpObject, MtpObject mtpObject2, String str, boolean z) {
        Log.v(TAG, "endMoveObject " + z);
        MtpObject child = mtpObject.getChild(str);
        MtpObject child2 = mtpObject2.getChild(str);
        boolean z2 = false;
        if (child != null && child2 != null) {
            if (mtpObject.getStorageId() != child2.getStorageId()) {
                boolean endRemoveObject = endRemoveObject(child, z);
                if (generalEndCopyObject(child2, z, true) && endRemoveObject) {
                    z2 = true;
                }
                return z2;
            }
            if (!z) {
                MtpObjectState state = child.getState();
                child.setParent(child2.getParent());
                child.setState(child2.getState());
                child2.setParent(mtpObject);
                child2.setState(state);
                child.getParent().addChild(child);
                mtpObject.addChild(child2);
                child = child2;
                child2 = child;
            }
            return generalEndRenameObject(child, child2, z);
        }
        return false;
    }

    public synchronized int beginCopyObject(MtpObject mtpObject, MtpObject mtpObject2) {
        Set<String> set;
        sDebugLog("beginCopyObject", mtpObject.getName() + " " + mtpObject2.getPath());
        String name = mtpObject.getName();
        if (!mtpObject2.isDir()) {
            return -1;
        }
        if (mtpObject2.isRoot() && (set = this.mSubdirectories) != null && !set.contains(name)) {
            return -1;
        }
        getChildren(mtpObject2, true);
        if (mtpObject2.getChild(name) != null) {
            return -1;
        }
        MtpObject copy = mtpObject.copy(mtpObject.isDir());
        mtpObject2.addChild(copy);
        copy.setParent(mtpObject2);
        if (generalBeginCopyObject(copy, true)) {
            return copy.getId();
        }
        return -1;
    }

    public synchronized boolean endCopyObject(MtpObject mtpObject, boolean z) {
        sDebugLog("endCopyObject " + z, mtpObject.getName());
        return generalEndCopyObject(mtpObject, z, false);
    }

    private synchronized boolean generalEndAddObject(MtpObject mtpObject, boolean z, boolean z2) {
        int ordinal = mtpObject.getState().ordinal();
        if (ordinal != 1) {
            if (ordinal == 2) {
                mtpObject.setState(MtpObjectState.NORMAL);
                if (!z) {
                    MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                }
            } else {
                if (ordinal != 3) {
                    return false;
                }
                if (!removeObjectFromCache(mtpObject, z2, false)) {
                    return false;
                }
                if (z) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                }
            }
        } else if (z) {
            mtpObject.setState(MtpObjectState.FROZEN_ONESHOT_ADD);
        } else if (!removeObjectFromCache(mtpObject, z2, false)) {
            return false;
        }
        return true;
    }

    private synchronized boolean generalEndRemoveObject(MtpObject mtpObject, boolean z, boolean z2) {
        int ordinal = mtpObject.getState().ordinal();
        if (ordinal != 1) {
            if (ordinal == 2) {
                mtpObject.setState(MtpObjectState.NORMAL);
                if (z) {
                    MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                }
            } else {
                if (ordinal != 3) {
                    return false;
                }
                if (!removeObjectFromCache(mtpObject, z2, false)) {
                    return false;
                }
                if (!z) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                }
            }
        } else if (z) {
            mtpObject.setState(MtpObjectState.FROZEN_ONESHOT_DEL);
        } else {
            mtpObject.setState(MtpObjectState.NORMAL);
        }
        return true;
    }

    private synchronized boolean generalBeginRenameObject(MtpObject mtpObject, MtpObject mtpObject2) {
        mtpObject.setState(MtpObjectState.FROZEN);
        mtpObject2.setState(MtpObjectState.FROZEN);
        mtpObject.setOperation(MtpOperation.RENAME);
        mtpObject2.setOperation(MtpOperation.RENAME);
        return true;
    }

    private synchronized boolean generalEndRenameObject(MtpObject mtpObject, MtpObject mtpObject2, boolean z) {
        return generalEndAddObject(mtpObject2, z, z) && generalEndRemoveObject(mtpObject, z, z ^ true);
    }

    private synchronized boolean generalBeginRemoveObject(MtpObject mtpObject, MtpOperation mtpOperation) {
        mtpObject.setState(MtpObjectState.FROZEN);
        mtpObject.setOperation(mtpOperation);
        if (mtpObject.isDir()) {
            Iterator it = mtpObject.getChildren().iterator();
            while (it.hasNext()) {
                generalBeginRemoveObject((MtpObject) it.next(), mtpOperation);
            }
        }
        return true;
    }

    private synchronized boolean generalBeginCopyObject(MtpObject mtpObject, boolean z) {
        mtpObject.setState(MtpObjectState.FROZEN);
        mtpObject.setOperation(MtpOperation.COPY);
        if (z) {
            mtpObject.setId(getNextObjectId());
            this.mObjects.put(Integer.valueOf(mtpObject.getId()), mtpObject);
        }
        if (mtpObject.isDir()) {
            Iterator it = mtpObject.getChildren().iterator();
            while (it.hasNext()) {
                if (!generalBeginCopyObject((MtpObject) it.next(), z)) {
                    return false;
                }
            }
        }
        return true;
    }

    private synchronized boolean generalEndCopyObject(MtpObject mtpObject, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        if (z && z2) {
            this.mObjects.put(Integer.valueOf(mtpObject.getId()), mtpObject);
        }
        z3 = false;
        if (mtpObject.isDir()) {
            Iterator it = new ArrayList(mtpObject.getChildren()).iterator();
            loop0: while (true) {
                z4 = true;
                while (it.hasNext()) {
                    MtpObject mtpObject2 = (MtpObject) it.next();
                    if (mtpObject2.getOperation() == MtpOperation.COPY) {
                        if (!generalEndCopyObject(mtpObject2, z, z2) || !z4) {
                            z4 = false;
                        }
                    }
                }
            }
        } else {
            z4 = true;
        }
        if (!z && z2) {
            z5 = false;
            if (generalEndAddObject(mtpObject, z, z5) && z4) {
                z3 = true;
            }
        }
        z5 = true;
        if (generalEndAddObject(mtpObject, z, z5)) {
            z3 = true;
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sDebugLog(String str, String str2) {
        try {
            Log.i(TAG, str + " : " + Base64.encodeToString(str2.getBytes("UTF-8"), 2));
        } catch (UnsupportedEncodingException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sDebugLog(String str, Path path) {
        try {
            Log.i(TAG, str + " : " + Base64.encodeToString(path.toString().getBytes("UTF-8"), 2));
        } catch (UnsupportedEncodingException unused) {
        }
    }
}
