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
        public final void run() throws InterruptedException {
            this.f$0.lambda$new$0();
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

        @Override // android.os.FileObserver
        public void onEvent(int i, String str) {
            synchronized (MtpStorageManager.this) {
                if ((i & 16384) != 0) {
                    Log.e(MtpStorageManager.TAG, "Received Inotify overflow event!");
                }
                MtpObject child = this.mObject.getChild(str);
                if ((i & 128) != 0 || (i & 256) != 0) {
                    MtpStorageManager.this.sDebugLog("Got inotify added event for", str);
                    MtpStorageManager.this.handleAddedObject(this.mObject, str, (i & 1073741824) != 0);
                } else if ((i & 64) == 0 && (i & 512) == 0) {
                    if ((32768 & i) != 0) {
                        MtpStorageManager.this.sDebugLog("Got inotify deleted", this.mObject.getPath());
                        if (this.mObject.mObserver != null) {
                            this.mObject.mObserver.stopWatching();
                        }
                        this.mObject.mObserver = null;
                    } else if ((i & 8) != 0) {
                        MtpStorageManager.this.sDebugLog("Got inotify CLOSE_WRITE event for", str);
                        MtpStorageManager.this.handleChangedObject(this.mObject, str);
                    } else {
                        MtpStorageManager.this.sDebugLog("Got unrecognized event " + i, str);
                    }
                } else if (child == null) {
                    MtpStorageManager.this.sDebugLog("Object was null in event", str);
                } else {
                    MtpStorageManager.this.sDebugLog("Got inotify removed event for", str);
                    MtpStorageManager.this.handleRemovedObject(child);
                }
            }
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
                    MtpObject mtpObjectCopy = it.next().copy(true);
                    mtpObjectCopy.setParent(mtpObject);
                    mtpObject.addChild(mtpObjectCopy);
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
    public /* synthetic */ void lambda$new$0() throws InterruptedException {
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
        int i;
        String id = storageVolume.getId();
        if (id != null && id.startsWith("emulated;") && (i = Integer.parseInt(id.substring(id.lastIndexOf(NavigationBarInflaterView.GRAVITY_SEPARATOR) + 1))) >= 95 && i < 100) {
            MtpStorage mtpStorage = new MtpStorage(storageVolume, 65538, supplier);
            this.mRoots.put(65538, new MtpObject(mtpStorage.getPath(), 65538, mtpStorage, null, true));
            return mtpStorage;
        }
        int storageId = storageVolume.getStorageId();
        if (storageId != 65537) {
            storageId = ((getNextStorageId() & 65535) << 16) + 1;
        }
        int i2 = storageId;
        MtpStorage mtpStorage2 = new MtpStorage(storageVolume, i2, supplier);
        this.mRoots.put(Integer.valueOf(i2), new MtpObject(mtpStorage2.getPath(), i2, mtpStorage2, null, true));
        return mtpStorage2;
    }

    public synchronized void removeMtpStorage(MtpStorage mtpStorage) {
        removeObjectFromCache(getStorageRoot(mtpStorage.getStorageId()), true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean isSpecialSubDir(MtpObject mtpObject) {
        boolean z;
        Set<String> set;
        if (!mtpObject.getParent().isRoot() || (set = this.mSubdirectories) == null) {
            z = false;
        } else if (!set.contains(mtpObject.getName())) {
            z = true;
        }
        return z;
    }

    public synchronized MtpObject getByPath(String str) {
        MtpObject child = null;
        for (MtpObject mtpObject : this.mRoots.values()) {
            if (str.startsWith(mtpObject.getName())) {
                str = str.substring(mtpObject.getName().length());
                child = mtpObject;
            }
        }
        for (String str2 : str.split("/")) {
            if (child != null && child.isDir()) {
                if (!"".equals(str2)) {
                    if (!child.isVisited()) {
                        getChildren(child, true);
                    }
                    child = child.getChild(str2);
                }
            }
            return null;
        }
        return child;
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

    public synchronized List<MtpObject> getObjects(int i, int i2, int i3, boolean z) throws Throwable {
        boolean objects = true;
        boolean z2 = i == 0;
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
                        boolean z3 = z;
                        objects &= mtpStorageManager.getObjects(arrayList, it.next(), i4, z2, z3);
                        this = mtpStorageManager;
                        i2 = i4;
                        z = z3;
                    }
                    MtpStorageManager mtpStorageManager2 = this;
                    if (!objects) {
                        arrayList = null;
                    }
                    return arrayList;
                }
                MtpObject storageRoot = i == 0 ? getStorageRoot(i3) : getObject(i);
                if (storageRoot == null) {
                    return null;
                }
                if (!getObjects(arrayList, storageRoot, i2, z2, z)) {
                    arrayList = null;
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            MtpStorageManager mtpStorageManager3 = this;
            Throwable th22 = th;
            throw th22;
        }
    }

    private synchronized boolean getObjects(List<MtpObject> list, MtpObject mtpObject, int i, boolean z, boolean z2) throws Throwable {
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
                    boolean objects = true;
                    if (z) {
                        for (MtpObject mtpObject3 : children) {
                            if (mtpObject3.isDir()) {
                                mtpStorageManager2 = this;
                                list2 = list;
                                i2 = i;
                                z3 = z2;
                                objects &= mtpStorageManager2.getObjects(list2, mtpObject3, i2, true, z3);
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
                    return objects;
                }
            } catch (Throwable th3) {
                th = th3;
                th = th;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            mtpStorageManager = this;
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
                        DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(path);
                        try {
                            for (Path path2 : directoryStreamNewDirectoryStream) {
                                addObjectToCache(mtpObject, path2.getFileName().toString(), path2.toFile().isDirectory());
                            }
                            if (directoryStreamNewDirectoryStream != null) {
                                directoryStreamNewDirectoryStream.close();
                            }
                            mtpObject.setVisited(true);
                            if (mtpObject.isDir() && (mtpObject.isSkipObserving() || mtpObject.getName().startsWith(MediaMetrics.SEPARATOR))) {
                                mtpObject.getObserver().stopWatching();
                                mtpObject.setObserver(null);
                            }
                        } catch (Throwable th) {
                            if (directoryStreamNewDirectoryStream != null) {
                                try {
                                    directoryStreamNewDirectoryStream.close();
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

    private synchronized boolean removeObjectFromCache(MtpObject mtpObject, boolean z, boolean z2) {
        boolean z3;
        z3 = mtpObject.isRoot() || mtpObject.getParent().mChildren.remove(mtpObject.getName(), mtpObject);
        if (!z3) {
            sDebugLog("Failed to remove from parent ", mtpObject.getPath());
        }
        if (mtpObject.isRoot()) {
            z3 = this.mRoots.remove(Integer.valueOf(mtpObject.getId()), mtpObject) && z3;
        } else if (z) {
            if (this.mObjects.remove(Integer.valueOf(mtpObject.getId()), mtpObject) && z3) {
            }
        }
        if (!z3) {
            sDebugLog("Failed to remove from global cache ", mtpObject.getPath());
        }
        if (mtpObject.getObserver() != null) {
            mtpObject.getObserver().stopWatching();
            mtpObject.setObserver(null);
        }
        if (mtpObject.isDir() && z2) {
            Iterator it = new ArrayList(mtpObject.getChildren()).iterator();
            while (it.hasNext()) {
                z3 = removeObjectFromCache((MtpObject) it.next(), z, true) && z3;
            }
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0051 A[Catch: all -> 0x0148, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x000c, B:8:0x001a, B:10:0x001e, B:11:0x0027, B:20:0x003b, B:23:0x0056, B:31:0x008f, B:35:0x0095, B:37:0x0099, B:41:0x00a1, B:43:0x00a7, B:46:0x00b0, B:48:0x00c3, B:55:0x00fe, B:66:0x0122, B:68:0x0128, B:70:0x0134, B:63:0x010d, B:62:0x010a, B:65:0x010f, B:21:0x004b, B:22:0x0051, B:27:0x007e, B:29:0x0084, B:73:0x0140), top: B:81:0x0003, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void handleAddedObject(MtpObject mtpObject, String str, boolean z) {
        MtpOperation operation = MtpOperation.NONE;
        MtpObject child = mtpObject.getChild(str);
        if (child != null) {
            MtpObjectState state = child.getState();
            operation = child.getOperation();
            if (child.isDir() != z && state != MtpObjectState.FROZEN_REMOVED) {
                sDebugLog("Inconsistent directory info! ", child.getPath());
            }
            child.setDir(z);
            int iOrdinal = state.ordinal();
            if (iOrdinal != 0) {
                if (iOrdinal == 1) {
                    child.setState(MtpObjectState.FROZEN_ADDED);
                    Log.i(TAG, state + " transitioned to " + child.getState() + " in op " + operation);
                } else if (iOrdinal != 2) {
                    if (iOrdinal != 3) {
                        if (iOrdinal == 4) {
                            child.setState(MtpObjectState.NORMAL);
                        } else {
                            sDebugLog("Unexpected state in add " + state, str);
                        }
                    }
                    Log.i(TAG, state + " transitioned to " + child.getState() + " in op " + operation);
                }
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
            if (operation == MtpOperation.RENAME) {
                return;
            }
            if (operation == MtpOperation.COPY && !child.isVisited()) {
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
                DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(child.getPath());
                try {
                    for (Path path : directoryStreamNewDirectoryStream) {
                        sDebugLog("Manually handling event for ", path.getFileName().toString());
                        handleAddedObject(child, path.getFileName().toString(), path.toFile().isDirectory());
                    }
                    if (directoryStreamNewDirectoryStream != null) {
                        directoryStreamNewDirectoryStream.close();
                    }
                } catch (Throwable th) {
                    if (directoryStreamNewDirectoryStream != null) {
                        try {
                            directoryStreamNewDirectoryStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException | DirectoryIteratorException e) {
                Log.e(TAG, e.toString());
                child.getObserver().stopWatching();
                child.setObserver(null);
            }
            if (child.isSkipObserving() || child.getName().startsWith(MediaMetrics.SEPARATOR)) {
                child.getObserver().stopWatching();
                child.setObserver(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemovedObject(MtpObject mtpObject) {
        MtpObjectState state = mtpObject.getState();
        MtpOperation operation = mtpObject.getOperation();
        int iOrdinal = state.ordinal();
        boolean z = true;
        if (iOrdinal != 0) {
            if (iOrdinal == 1 || iOrdinal == 2) {
                mtpObject.setState(MtpObjectState.FROZEN_REMOVED);
            } else if (iOrdinal == 5) {
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

    public void flushEvents() throws InterruptedException {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException unused) {
        }
    }

    public synchronized void dump() {
        for (Integer num : this.mObjects.keySet()) {
            int iIntValue = num.intValue();
            MtpObject mtpObject = this.mObjects.get(num);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(iIntValue);
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
                    DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(mtpObject.getPath());
                    try {
                        HashSet hashSet = new HashSet();
                        for (Path path : directoryStreamNewDirectoryStream) {
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
                        if (directoryStreamNewDirectoryStream != null) {
                            directoryStreamNewDirectoryStream.close();
                        }
                    } catch (Throwable th) {
                        if (directoryStreamNewDirectoryStream != null) {
                            try {
                                directoryStreamNewDirectoryStream.close();
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
        MtpObject mtpObjectAddObjectToCache = addObjectToCache(mtpObject, str, z);
        if (mtpObjectAddObjectToCache == null) {
            return -1;
        }
        mtpObjectAddObjectToCache.setState(MtpObjectState.FROZEN);
        mtpObjectAddObjectToCache.setOperation(MtpOperation.ADD);
        return mtpObjectAddObjectToCache.getId();
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
        MtpObject mtpObjectCopy = mtpObject.copy(false);
        mtpObject.setName(str);
        mtpObject.getParent().addChild(mtpObject);
        mtpObjectCopy.getParent().addChild(mtpObjectCopy);
        return generalBeginRenameObject(mtpObjectCopy, mtpObject);
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean beginRemoveObject(MtpObject mtpObject) {
        boolean z;
        sDebugLog("beginRemoveObject", mtpObject.getName());
        if (mtpObject.isRoot() || isSpecialSubDir(mtpObject)) {
            z = false;
        } else if (generalBeginRemoveObject(mtpObject, MtpOperation.DELETE)) {
            z = true;
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
            MtpObject mtpObjectCopy = mtpObject.copy(true);
            mtpObjectCopy.setParent(mtpObject2);
            mtpObject2.addChild(mtpObjectCopy);
            if (generalBeginRemoveObject(mtpObject, MtpOperation.RENAME) && generalBeginCopyObject(mtpObjectCopy, false)) {
                z = true;
            }
            return z;
        }
        MtpObject mtpObjectCopy2 = mtpObject.copy(false);
        mtpObject.setParent(mtpObject2);
        mtpObjectCopy2.getParent().addChild(mtpObjectCopy2);
        mtpObject.getParent().addChild(mtpObject);
        return generalBeginRenameObject(mtpObjectCopy2, mtpObject);
    }

    public synchronized boolean endMoveObject(MtpObject mtpObject, MtpObject mtpObject2, String str, boolean z) {
        Log.v(TAG, "endMoveObject " + z);
        MtpObject child = mtpObject.getChild(str);
        MtpObject child2 = mtpObject2.getChild(str);
        boolean z2 = false;
        if (child != null && child2 != null) {
            if (mtpObject.getStorageId() != child2.getStorageId()) {
                boolean zEndRemoveObject = endRemoveObject(child, z);
                if (generalEndCopyObject(child2, z, true) && zEndRemoveObject) {
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
        MtpObject mtpObjectCopy = mtpObject.copy(mtpObject.isDir());
        mtpObject2.addChild(mtpObjectCopy);
        mtpObjectCopy.setParent(mtpObject2);
        if (generalBeginCopyObject(mtpObjectCopy, true)) {
            return mtpObjectCopy.getId();
        }
        return -1;
    }

    public synchronized boolean endCopyObject(MtpObject mtpObject, boolean z) {
        sDebugLog("endCopyObject " + z, mtpObject.getName());
        return generalEndCopyObject(mtpObject, z, false);
    }

    private synchronized boolean generalEndAddObject(MtpObject mtpObject, boolean z, boolean z2) {
        int iOrdinal = mtpObject.getState().ordinal();
        if (iOrdinal != 1) {
            if (iOrdinal == 2) {
                mtpObject.setState(MtpObjectState.NORMAL);
                if (!z) {
                    MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                }
            } else {
                if (iOrdinal != 3) {
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
        int iOrdinal = mtpObject.getState().ordinal();
        if (iOrdinal != 1) {
            if (iOrdinal == 2) {
                mtpObject.setState(MtpObjectState.NORMAL);
                if (z) {
                    MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                }
            } else {
                if (iOrdinal != 3) {
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a A[Catch: all -> 0x005b, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0012, B:8:0x001a, B:10:0x0028, B:12:0x002e, B:14:0x003c, B:25:0x0050), top: B:34:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean generalEndCopyObject(MtpObject mtpObject, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        if (z && z2) {
            this.mObjects.put(Integer.valueOf(mtpObject.getId()), mtpObject);
            z3 = false;
            if (mtpObject.isDir()) {
            }
            if (z) {
                if (generalEndAddObject(mtpObject, z, (z && z2) ? false : true)) {
                    z3 = true;
                }
            }
        } else {
            z3 = false;
            if (mtpObject.isDir()) {
                z4 = true;
            } else {
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
            }
            if (generalEndAddObject(mtpObject, z, (z && z2) ? false : true) && z4) {
                z3 = true;
            }
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
