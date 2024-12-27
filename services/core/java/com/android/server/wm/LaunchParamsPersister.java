package com.android.server.wm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManagerInternal;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import android.view.DisplayInfo;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageList;

import com.samsung.android.rune.CoreRune;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LaunchParamsPersister {
    public PackageList mPackageList;
    public final PersisterQueue mPersisterQueue;
    public final ActivityTaskSupervisor mSupervisor;
    public final IntFunction mUserFolderGetter;
    public final SparseArray mLaunchParamsMap = new SparseArray();
    public final ArrayMap mWindowLayoutAffinityMap = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CleanUpComponentQueueItem implements PersisterQueue.WriteQueueItem {
        public final List mComponentFiles;

        public CleanUpComponentQueueItem(List list) {
            this.mComponentFiles = list;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            for (File file : this.mComponentFiles) {
                if (!file.delete()) {
                    Slog.w("LaunchParamsPersister", "Failed to delete " + file.getAbsolutePath());
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LaunchParamsWriteQueueItem implements PersisterQueue.WriteQueueItem {
        public final ComponentName mComponentName;
        public PersistableLaunchParams mLaunchParams;
        public final int mUserId;

        public LaunchParamsWriteQueueItem(
                int i,
                ComponentName componentName,
                PersistableLaunchParams persistableLaunchParams) {
            this.mUserId = i;
            this.mComponentName = componentName;
            this.mLaunchParams = persistableLaunchParams;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final boolean matches(PersisterQueue.WriteQueueItem writeQueueItem) {
            LaunchParamsWriteQueueItem launchParamsWriteQueueItem =
                    (LaunchParamsWriteQueueItem) writeQueueItem;
            return this.mUserId == launchParamsWriteQueueItem.mUserId
                    && this.mComponentName.equals(launchParamsWriteQueueItem.mComponentName);
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            byte[] bArr;
            FileOutputStream fileOutputStream = null;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "launch_params");
                this.mLaunchParams.saveToXml(resolveSerializer);
                resolveSerializer.endTag((String) null, "launch_params");
                resolveSerializer.endDocument();
                resolveSerializer.flush();
                bArr = byteArrayOutputStream.toByteArray();
            } catch (IOException unused) {
                bArr = null;
            }
            IntFunction intFunction = LaunchParamsPersister.this.mUserFolderGetter;
            int i = this.mUserId;
            File file = new File((File) intFunction.apply(i), "launch_params");
            if (!file.isDirectory() && !file.mkdir()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Failed to create folder for ", "LaunchParamsPersister");
                return;
            }
            AtomicFile atomicFile =
                    new AtomicFile(LaunchParamsPersister.getParamFile(file, this.mComponentName));
            try {
                fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(bArr);
                atomicFile.finishWrite(fileOutputStream);
            } catch (Exception e) {
                Slog.e(
                        "LaunchParamsPersister",
                        "Failed to write param file for " + this.mComponentName,
                        e);
                if (fileOutputStream != null) {
                    atomicFile.failWrite(fileOutputStream);
                }
            }
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void updateFrom(PersisterQueue.WriteQueueItem writeQueueItem) {
            this.mLaunchParams = ((LaunchParamsWriteQueueItem) writeQueueItem).mLaunchParams;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageListObserver implements PackageManagerInternal.PackageListObserver {
        public PackageListObserver() {}

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public final void onPackageAdded(String str, int i) {}

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public final void onPackageRemoved(String str, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    LaunchParamsPersister.this.mSupervisor.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    LaunchParamsPersister.this.removeRecordForPackage(str);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PersistableLaunchParams {
        public int mDisplayDeviceType;
        public String mDisplayUniqueId;
        public int mOrientation;
        public long mTimestamp;
        public String mWindowLayoutAffinity;
        public int mWindowingMode;
        public final Rect mBounds = new Rect();
        public final DexPersistBoundsParams mDexPersistBoundsParams = new DexPersistBoundsParams();
        public final FreeformPersistBoundsParams mFreeformPersistBoundsParams =
                new FreeformPersistBoundsParams();

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final void restore(File file, TypedXmlPullParser typedXmlPullParser) {
            char c;
            boolean z;
            boolean z2;
            for (int i = 0; i < typedXmlPullParser.getAttributeCount(); i++) {
                String attributeValue = typedXmlPullParser.getAttributeValue(i);
                String attributeName = typedXmlPullParser.getAttributeName(i);
                attributeName.getClass();
                switch (attributeName.hashCode()) {
                    case -1694362746:
                        if (attributeName.equals("display_device_type")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1499361012:
                        if (attributeName.equals("display_unique_id")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1439500848:
                        if (attributeName.equals("orientation")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1383205195:
                        if (attributeName.equals("bounds")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 748872656:
                        if (attributeName.equals("windowing_mode")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1999609934:
                        if (attributeName.equals("window_layout_affinity")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        this.mDisplayDeviceType = Integer.parseInt(attributeValue);
                        break;
                    case 1:
                        this.mDisplayUniqueId = attributeValue;
                        break;
                    case 2:
                        this.mOrientation = Integer.parseInt(attributeValue);
                        break;
                    case 3:
                        Rect unflattenFromString = Rect.unflattenFromString(attributeValue);
                        if (unflattenFromString != null) {
                            this.mBounds.set(unflattenFromString);
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        this.mWindowingMode = Integer.parseInt(attributeValue);
                        break;
                    case 5:
                        this.mWindowLayoutAffinity = attributeValue;
                        break;
                    default:
                        String attributeName2 = typedXmlPullParser.getAttributeName(i);
                        FreeformPersistBoundsParams freeformPersistBoundsParams =
                                this.mFreeformPersistBoundsParams;
                        freeformPersistBoundsParams.getClass();
                        attributeName2.getClass();
                        switch (attributeName2.hashCode()) {
                            case -40300674:
                                if (attributeName2.equals("rotation")) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case 1284627666:
                                if (attributeName2.equals("display_bounds")) {
                                    z = true;
                                    break;
                                }
                                z = -1;
                                break;
                            case 1413918884:
                                if (attributeName2.equals("freeform_bounds")) {
                                    z = 2;
                                    break;
                                }
                                z = -1;
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                freeformPersistBoundsParams.mRotation =
                                        Integer.parseInt(attributeValue);
                                break;
                            case true:
                                Rect unflattenFromString2 =
                                        Rect.unflattenFromString(attributeValue);
                                if (unflattenFromString2 != null) {
                                    freeformPersistBoundsParams.mDisplayBounds.set(
                                            unflattenFromString2);
                                    break;
                                }
                                break;
                            case true:
                                Rect unflattenFromString3 =
                                        Rect.unflattenFromString(attributeValue);
                                if (unflattenFromString3 != null) {
                                    freeformPersistBoundsParams.mFreeformBounds.set(
                                            unflattenFromString3);
                                    break;
                                }
                                break;
                        }
                        String attributeName3 = typedXmlPullParser.getAttributeName(i);
                        DexPersistBoundsParams dexPersistBoundsParams =
                                this.mDexPersistBoundsParams;
                        dexPersistBoundsParams.getClass();
                        attributeName3.getClass();
                        switch (attributeName3.hashCode()) {
                            case -579846920:
                                if (attributeName3.equals("dex_windowing_mode")) {
                                    z2 = false;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case -44109904:
                                if (attributeName3.equals("dex_dual_bounds")) {
                                    z2 = true;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case 109011399:
                                if (attributeName3.equals("dex_standalone_bounds")) {
                                    z2 = 2;
                                    break;
                                }
                                z2 = -1;
                                break;
                            default:
                                z2 = -1;
                                break;
                        }
                        switch (z2) {
                            case false:
                                dexPersistBoundsParams.mDexWindowingMode =
                                        Integer.parseInt(attributeValue);
                                break;
                            case true:
                                Rect unflattenFromString4 =
                                        Rect.unflattenFromString(attributeValue);
                                if (unflattenFromString4 != null) {
                                    dexPersistBoundsParams.mDexDualBounds.set(unflattenFromString4);
                                    break;
                                } else {
                                    break;
                                }
                            case true:
                                Rect unflattenFromString5 =
                                        Rect.unflattenFromString(attributeValue);
                                if (unflattenFromString5 != null) {
                                    dexPersistBoundsParams.mDexStandAloneBounds.set(
                                            unflattenFromString5);
                                    break;
                                } else {
                                    break;
                                }
                        }
                }
            }
            this.mTimestamp = file.lastModified();
        }

        public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.attribute((String) null, "display_unique_id", this.mDisplayUniqueId);
            typedXmlSerializer.attributeInt((String) null, "windowing_mode", this.mWindowingMode);
            typedXmlSerializer.attribute((String) null, "bounds", this.mBounds.flattenToString());
            String str = this.mWindowLayoutAffinity;
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "window_layout_affinity", str);
            }
            typedXmlSerializer.attribute(
                    (String) null, "orientation", Integer.toString(this.mOrientation));
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                typedXmlSerializer.attribute(
                        (String) null,
                        "display_device_type",
                        Integer.toString(this.mDisplayDeviceType));
            }
            FreeformPersistBoundsParams freeformPersistBoundsParams =
                    this.mFreeformPersistBoundsParams;
            typedXmlSerializer.attribute(
                    (String) null,
                    "freeform_bounds",
                    freeformPersistBoundsParams.mFreeformBounds.flattenToString());
            typedXmlSerializer.attribute(
                    (String) null,
                    "display_bounds",
                    freeformPersistBoundsParams.mDisplayBounds.flattenToString());
            typedXmlSerializer.attribute(
                    (String) null,
                    "rotation",
                    Integer.toString(freeformPersistBoundsParams.mRotation));
            DexPersistBoundsParams dexPersistBoundsParams = this.mDexPersistBoundsParams;
            typedXmlSerializer.attribute(
                    (String) null,
                    "dex_windowing_mode",
                    Integer.toString(dexPersistBoundsParams.mDexWindowingMode));
            typedXmlSerializer.attribute(
                    (String) null,
                    "dex_dual_bounds",
                    dexPersistBoundsParams.mDexDualBounds.flattenToString());
            typedXmlSerializer.attribute(
                    (String) null,
                    "dex_standalone_bounds",
                    dexPersistBoundsParams.mDexStandAloneBounds.flattenToString());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("PersistableLaunchParams{");
            sb.append(" windowingMode=" + this.mWindowingMode);
            sb.append(" displayUniqueId=" + this.mDisplayUniqueId);
            sb.append(" bounds=" + this.mBounds);
            if (this.mWindowLayoutAffinity != null) {
                sb.append(" launchParamsAffinity=" + this.mWindowLayoutAffinity);
            }
            sb.append(" timestamp=" + this.mTimestamp);
            sb.append(" orientation=" + this.mOrientation);
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                sb.append(" displayDeviceType=" + this.mDisplayDeviceType);
            }
            sb.append(this.mFreeformPersistBoundsParams);
            sb.append(this.mDexPersistBoundsParams);
            sb.append(" }");
            return sb.toString();
        }
    }

    public LaunchParamsPersister(
            PersisterQueue persisterQueue,
            ActivityTaskSupervisor activityTaskSupervisor,
            IntFunction intFunction) {
        this.mPersisterQueue = persisterQueue;
        this.mSupervisor = activityTaskSupervisor;
        this.mUserFolderGetter = intFunction;
    }

    public static File getParamFile(File file, ComponentName componentName) {
        return new File(
                file,
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                        componentName.flattenToShortString().replace('/', '-'), ".xml"));
    }

    public final void addComponentNameToLaunchParamAffinityMapIfNotNull(
            ComponentName componentName, String str) {
        if (str == null) {
            return;
        }
        ((ArraySet)
                        this.mWindowLayoutAffinityMap.computeIfAbsent(
                                str, new LaunchParamsPersister$$ExternalSyntheticLambda3()))
                .add(componentName);
    }

    public final void getLaunchParams(
            Task task,
            ActivityRecord activityRecord,
            LaunchParamsController.LaunchParams launchParams) {
        String str;
        ComponentName componentName =
                task != null ? task.realActivity : activityRecord.mActivityComponent;
        int i = task != null ? task.mUserId : activityRecord.mUserId;
        if (task != null) {
            str = task.mWindowLayoutAffinity;
        } else {
            ActivityInfo.WindowLayout windowLayout = activityRecord.info.windowLayout;
            str = windowLayout == null ? null : windowLayout.windowLayoutAffinity;
        }
        launchParams.reset();
        Map map = (Map) this.mLaunchParamsMap.get(i);
        if (map == null) {
            return;
        }
        PersistableLaunchParams persistableLaunchParams =
                (PersistableLaunchParams) map.get(componentName);
        if (str != null && this.mWindowLayoutAffinityMap.get(str) != null) {
            ArraySet arraySet = (ArraySet) this.mWindowLayoutAffinityMap.get(str);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                PersistableLaunchParams persistableLaunchParams2 =
                        (PersistableLaunchParams) map.get((ComponentName) arraySet.valueAt(i2));
                if (persistableLaunchParams2 != null
                        && (persistableLaunchParams == null
                                || persistableLaunchParams2.mTimestamp
                                        > persistableLaunchParams.mTimestamp)) {
                    persistableLaunchParams = persistableLaunchParams2;
                }
            }
        }
        if (persistableLaunchParams == null) {
            return;
        }
        DisplayContent displayContent =
                this.mSupervisor.mRootWindowContainer.getDisplayContent(
                        persistableLaunchParams.mDisplayUniqueId);
        if (displayContent != null) {
            launchParams.mPreferredTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        }
        launchParams.mWindowingMode = persistableLaunchParams.mWindowingMode;
        launchParams.mBounds.set(persistableLaunchParams.mBounds);
        launchParams.mOrientation = persistableLaunchParams.mOrientation;
        if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
            launchParams.mDisplayDeviceType = persistableLaunchParams.mDisplayDeviceType;
        }
        FreeformPersistBoundsParams freeformPersistBoundsParams =
                launchParams.mFreeformPersistBoundsParam;
        Rect rect = freeformPersistBoundsParams.mFreeformBounds;
        FreeformPersistBoundsParams freeformPersistBoundsParams2 =
                persistableLaunchParams.mFreeformPersistBoundsParams;
        rect.set(freeformPersistBoundsParams2.mFreeformBounds);
        freeformPersistBoundsParams.mDisplayBounds.set(freeformPersistBoundsParams2.mDisplayBounds);
        freeformPersistBoundsParams.mRotation = freeformPersistBoundsParams2.mRotation;
        DexPersistBoundsParams dexPersistBoundsParams = launchParams.mDexPersistBoundsParam;
        dexPersistBoundsParams.getClass();
        DexPersistBoundsParams dexPersistBoundsParams2 =
                persistableLaunchParams.mDexPersistBoundsParams;
        dexPersistBoundsParams.mDexWindowingMode = dexPersistBoundsParams2.mDexWindowingMode;
        dexPersistBoundsParams.mDexDualBounds.set(dexPersistBoundsParams2.mDexDualBounds);
        dexPersistBoundsParams.mDexStandAloneBounds.set(
                dexPersistBoundsParams2.mDexStandAloneBounds);
    }

    public final void removeRecordForPackage(final String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mLaunchParamsMap.size(); i++) {
            File file =
                    new File(
                            (File) this.mUserFolderGetter.apply(this.mLaunchParamsMap.keyAt(i)),
                            "launch_params");
            ArrayMap arrayMap = (ArrayMap) this.mLaunchParamsMap.valueAt(i);
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                ComponentName componentName = (ComponentName) arrayMap.keyAt(size);
                if (componentName.getPackageName().equals(str)) {
                    arrayMap.removeAt(size);
                    arrayList.add(getParamFile(file, componentName));
                }
            }
        }
        synchronized (this.mPersisterQueue) {
            this.mPersisterQueue.removeItems(
                    new Predicate() { // from class:
                                      // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return ((LaunchParamsPersister.LaunchParamsWriteQueueItem) obj)
                                    .mComponentName
                                    .getPackageName()
                                    .equals(str);
                        }
                    },
                    LaunchParamsWriteQueueItem.class);
            this.mPersisterQueue.addItem(new CleanUpComponentQueueItem(arrayList), true);
        }
    }

    public final void saveTask(Task task, DisplayContent displayContent) {
        boolean z;
        boolean equals;
        ComponentName componentName = task.realActivity;
        if (componentName == null) {
            return;
        }
        int i = task.mUserId;
        ArrayMap arrayMap = (ArrayMap) this.mLaunchParamsMap.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mLaunchParamsMap.put(i, arrayMap);
        }
        PersistableLaunchParams persistableLaunchParams =
                (PersistableLaunchParams)
                        arrayMap.computeIfAbsent(
                                componentName,
                                new LaunchParamsPersister$$ExternalSyntheticLambda1(this, 0));
        DisplayInfo displayInfo = new DisplayInfo();
        displayContent.mDisplay.getDisplayInfo(displayInfo);
        boolean z2 =
                !Objects.equals(persistableLaunchParams.mDisplayUniqueId, displayInfo.uniqueId);
        persistableLaunchParams.mDisplayUniqueId = displayInfo.uniqueId;
        boolean z3 = (persistableLaunchParams.mWindowingMode != task.getWindowingMode()) | z2;
        persistableLaunchParams.mWindowingMode = task.getWindowingMode();
        boolean z4 =
                z3
                        | (persistableLaunchParams.mOrientation
                                != displayContent.getConfiguration().orientation);
        persistableLaunchParams.mOrientation = displayContent.getConfiguration().orientation;
        if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && task.getDisplayId() == 0) {
            z4 |=
                    persistableLaunchParams.mDisplayDeviceType
                            != displayContent.getConfiguration().semDisplayDeviceType;
            persistableLaunchParams.mDisplayDeviceType =
                    displayContent.getConfiguration().semDisplayDeviceType;
        }
        if (task.isDexMode()) {
            DexPersistBoundsParams dexPersistBoundsParams =
                    persistableLaunchParams.mDexPersistBoundsParams;
            equals = z4 | (dexPersistBoundsParams.mDexWindowingMode != task.getWindowingMode());
            dexPersistBoundsParams.mDexWindowingMode = task.getWindowingMode();
            Rect rect =
                    task.getDisplayId() == 2
                            ? dexPersistBoundsParams.mDexDualBounds
                            : dexPersistBoundsParams.mDexStandAloneBounds;
            if (task.mHasTopFullscreenWindow) {
                if (task.mLastNonFullscreenBounds != null) {
                    equals |= !Objects.equals(rect, r4);
                    rect.set(task.mLastNonFullscreenBounds);
                }
            } else {
                equals |= !rect.isEmpty();
                rect.setEmpty();
            }
        } else {
            if (task.mLastNonFullscreenBounds != null) {
                z = (!Objects.equals(persistableLaunchParams.mBounds, r10)) | z4;
                persistableLaunchParams.mBounds.set(task.mLastNonFullscreenBounds);
            } else {
                z = (!persistableLaunchParams.mBounds.isEmpty()) | z4;
                persistableLaunchParams.mBounds.setEmpty();
            }
            String str = task.mWindowLayoutAffinity;
            equals = Objects.equals(str, persistableLaunchParams.mWindowLayoutAffinity) | z;
            persistableLaunchParams.mWindowLayoutAffinity = str;
            if (equals) {
                persistableLaunchParams.mTimestamp = System.currentTimeMillis();
            }
        }
        addComponentNameToLaunchParamAffinityMapIfNotNull(
                componentName, persistableLaunchParams.mWindowLayoutAffinity);
        if (equals) {
            this.mPersisterQueue.updateLastOrAddItem(
                    new LaunchParamsWriteQueueItem(i, componentName, persistableLaunchParams));
        }
    }
}
