package com.android.server.wm;

import android.content.ComponentName;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import android.view.DisplayInfo;
import android.view.Surface;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.pm.PackageList;
import com.samsung.android.rune.CoreRune;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class LaunchParamsPersister {
  public final SparseArray mLaunchParamsMap;
  public PackageList mPackageList;
  public final PersisterQueue mPersisterQueue;
  public final ActivityTaskSupervisor mSupervisor;
  public final IntFunction mUserFolderGetter;
  public final ArrayMap mWindowLayoutAffinityMap;

  public LaunchParamsPersister(
      PersisterQueue persisterQueue, ActivityTaskSupervisor activityTaskSupervisor) {
    this(
        persisterQueue,
        activityTaskSupervisor,
        new IntFunction() { // from class:
                            // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda2
          @Override // java.util.function.IntFunction
          public final Object apply(int i) {
            return Environment.getDataSystemCeDirectory(i);
          }
        });
  }

  public LaunchParamsPersister(
      PersisterQueue persisterQueue,
      ActivityTaskSupervisor activityTaskSupervisor,
      IntFunction intFunction) {
    this.mLaunchParamsMap = new SparseArray();
    this.mWindowLayoutAffinityMap = new ArrayMap();
    this.mPersisterQueue = persisterQueue;
    this.mSupervisor = activityTaskSupervisor;
    this.mUserFolderGetter = intFunction;
  }

  public void onSystemReady() {
    this.mPackageList =
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
            .getPackageList(new PackageListObserver());
  }

  public void onUnlockUser(int i) {
    loadLaunchParams(i);
  }

  public void onCleanupUser(int i) {
    this.mLaunchParamsMap.remove(i);
  }

  public final void loadLaunchParams(int i) {
    File file;
    FileInputStream fileInputStream;
    ArrayList arrayList = new ArrayList();
    File launchParamFolder = getLaunchParamFolder(i);
    if (!launchParamFolder.isDirectory()) {
      Slog.i("LaunchParamsPersister", "Didn't find launch param folder for user " + i);
      return;
    }
    ArraySet arraySet = new ArraySet(this.mPackageList.getPackageNames());
    File[] listFiles = launchParamFolder.listFiles();
    ArrayMap arrayMap = new ArrayMap(listFiles.length);
    this.mLaunchParamsMap.put(i, arrayMap);
    int length = listFiles.length;
    int i2 = 0;
    int i3 = 0;
    while (i3 < length) {
      File file2 = listFiles[i3];
      if (!file2.isFile()) {
        Slog.w("LaunchParamsPersister", file2.getAbsolutePath() + " is not a file.");
      } else if (!file2.getName().endsWith(".xml")) {
        Slog.w("LaunchParamsPersister", "Unexpected params file name: " + file2.getName());
        arrayList.add(file2);
      } else {
        String name = file2.getName();
        int indexOf = name.indexOf(95);
        if (indexOf != -1) {
          if (name.indexOf(95, indexOf + 1) != -1) {
            arrayList.add(file2);
          } else {
            name = name.replace('_', '-');
            File file3 = new File(launchParamFolder, name);
            if (file2.renameTo(file3)) {
              file2 = file3;
            } else {
              arrayList.add(file2);
            }
          }
        }
        ComponentName unflattenFromString =
            ComponentName.unflattenFromString(
                name.substring(i2, name.length() - 4).replace('-', '/'));
        if (unflattenFromString == null) {
          Slog.w("LaunchParamsPersister", "Unexpected file name: " + name);
          arrayList.add(file2);
        } else if (!arraySet.contains(unflattenFromString.getPackageName())) {
          arrayList.add(file2);
        } else {
          try {
            fileInputStream = new FileInputStream(file2);
          } catch (Exception e) {
            e = e;
            file = launchParamFolder;
          }
          try {
            PersistableLaunchParams persistableLaunchParams = new PersistableLaunchParams();
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
            while (true) {
              int next = resolvePullParser.next();
              if (next == 1 || next == 3) {
                break;
              }
              if (next == 2) {
                String name2 = resolvePullParser.getName();
                if (!"launch_params".equals(name2)) {
                  StringBuilder sb = new StringBuilder();
                  file = launchParamFolder;
                  try {
                    sb.append("Unexpected tag name: ");
                    sb.append(name2);
                    Slog.w("LaunchParamsPersister", sb.toString());
                  } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    try {
                      fileInputStream.close();
                    } catch (Throwable th3) {
                      th2.addSuppressed(th3);
                    }
                    throw th2;
                  }
                } else {
                  file = launchParamFolder;
                  persistableLaunchParams.restore(file2, resolvePullParser);
                }
                launchParamFolder = file;
              }
            }
            file = launchParamFolder;
            arrayMap.put(unflattenFromString, persistableLaunchParams);
            addComponentNameToLaunchParamAffinityMapIfNotNull(
                unflattenFromString, persistableLaunchParams.mWindowLayoutAffinity);
            try {
              fileInputStream.close();
            } catch (Exception e2) {
              e = e2;
              Slog.w(
                  "LaunchParamsPersister",
                  "Failed to restore launch params for " + unflattenFromString,
                  e);
              arrayList.add(file2);
              i3++;
              launchParamFolder = file;
              i2 = 0;
            }
            i3++;
            launchParamFolder = file;
            i2 = 0;
          } catch (Throwable th4) {
            th = th4;
            file = launchParamFolder;
          }
        }
      }
      file = launchParamFolder;
      i3++;
      launchParamFolder = file;
      i2 = 0;
    }
    if (arrayList.isEmpty()) {
      return;
    }
    this.mPersisterQueue.addItem(new CleanUpComponentQueueItem(arrayList), true);
  }

  public void saveTask(Task task, DisplayContent displayContent) {
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
                new Function() { // from class:
                                 // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda1
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    LaunchParamsPersister.PersistableLaunchParams lambda$saveTask$0;
                    lambda$saveTask$0 =
                        LaunchParamsPersister.this.lambda$saveTask$0((ComponentName) obj);
                    return lambda$saveTask$0;
                  }
                });
    boolean saveTaskToLaunchParam =
        saveTaskToLaunchParam(task, displayContent, persistableLaunchParams);
    addComponentNameToLaunchParamAffinityMapIfNotNull(
        componentName, persistableLaunchParams.mWindowLayoutAffinity);
    if (saveTaskToLaunchParam) {
      this.mPersisterQueue.updateLastOrAddItem(
          new LaunchParamsWriteQueueItem(i, componentName, persistableLaunchParams), false);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ PersistableLaunchParams lambda$saveTask$0(ComponentName componentName) {
    return new PersistableLaunchParams();
  }

  public final boolean saveTaskToLaunchParam(
      Task task, DisplayContent displayContent, PersistableLaunchParams persistableLaunchParams) {
    boolean z;
    Rect rect;
    DisplayInfo displayInfo = new DisplayInfo();
    displayContent.mDisplay.getDisplayInfo(displayInfo);
    boolean z2 = !Objects.equals(persistableLaunchParams.mDisplayUniqueId, displayInfo.uniqueId);
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
      boolean z5 = z4 | (dexPersistBoundsParams.mDexWindowingMode != task.getWindowingMode());
      dexPersistBoundsParams.mDexWindowingMode = task.getWindowingMode();
      if (task.getDisplayId() == 2) {
        rect = dexPersistBoundsParams.mDexDualBounds;
      } else {
        rect = dexPersistBoundsParams.mDexStandAloneBounds;
      }
      if (!task.mHasTopFullscreenWindow) {
        boolean z6 = z5 | (!rect.isEmpty());
        rect.setEmpty();
        return z6;
      }
      if (task.mLastNonFullscreenBounds == null) {
        return z5;
      }
      boolean z7 = z5 | (!Objects.equals(rect, r7));
      rect.set(task.mLastNonFullscreenBounds);
      return z7;
    }
    if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS && task.isNewDexMode()) {
      NewDexPersistBoundsParams newDexPersistBoundsParams =
          persistableLaunchParams.mNewDexPersistBoundsParams;
      boolean z8 = z4 | (newDexPersistBoundsParams.mNewDexWindowingMode != task.getWindowingMode());
      newDexPersistBoundsParams.mNewDexWindowingMode = task.getWindowingMode();
      Rect rect2 = newDexPersistBoundsParams.mNewDexNextGenBounds;
      if (!task.mHasTopFullscreenWindow) {
        boolean z9 = z8 | (!rect2.isEmpty());
        rect2.setEmpty();
        return z9;
      }
      if (task.mLastNonFullscreenBounds == null) {
        return z8;
      }
      boolean z10 = z8 | (!Objects.equals(rect2, r7));
      rect2.set(task.mLastNonFullscreenBounds);
      return z10;
    }
    if (task.mLastNonFullscreenBounds != null) {
      z = z4 | (!Objects.equals(persistableLaunchParams.mBounds, r6));
      persistableLaunchParams.mBounds.set(task.mLastNonFullscreenBounds);
    } else {
      z = z4 | (!persistableLaunchParams.mBounds.isEmpty());
      persistableLaunchParams.mBounds.setEmpty();
    }
    String str = task.mWindowLayoutAffinity;
    boolean equals = z | Objects.equals(str, persistableLaunchParams.mWindowLayoutAffinity);
    persistableLaunchParams.mWindowLayoutAffinity = str;
    if (equals) {
      persistableLaunchParams.mTimestamp = System.currentTimeMillis();
    }
    return equals;
  }

  public static /* synthetic */ ArraySet lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1(
      String str) {
    return new ArraySet();
  }

  public final void addComponentNameToLaunchParamAffinityMapIfNotNull(
      ComponentName componentName, String str) {
    if (str == null) {
      return;
    }
    ((ArraySet)
            this.mWindowLayoutAffinityMap.computeIfAbsent(
                str,
                new Function() { // from class:
                                 // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda4
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    ArraySet lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1;
                    lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1 =
                        LaunchParamsPersister
                            .lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1(
                                (String) obj);
                    return lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1;
                  }
                }))
        .add(componentName);
  }

  public void getLaunchParams(
      Task task, ActivityRecord activityRecord, LaunchParamsController.LaunchParams launchParams) {
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
                || persistableLaunchParams2.mTimestamp > persistableLaunchParams.mTimestamp)) {
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
    launchParams.mFreeformPersistBoundsParam.set(
        persistableLaunchParams.mFreeformPersistBoundsParams);
    launchParams.mDexPersistBoundsParam.set(persistableLaunchParams.mDexPersistBoundsParams);
    if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
      launchParams.mNewDexPersistBoundsParam.set(
          persistableLaunchParams.mNewDexPersistBoundsParams);
    }
  }

  public void removeRecordForPackage(final String str) {
    ArrayList arrayList = new ArrayList();
    for (int i = 0; i < this.mLaunchParamsMap.size(); i++) {
      File launchParamFolder = getLaunchParamFolder(this.mLaunchParamsMap.keyAt(i));
      ArrayMap arrayMap = (ArrayMap) this.mLaunchParamsMap.valueAt(i);
      for (int size = arrayMap.size() - 1; size >= 0; size--) {
        ComponentName componentName = (ComponentName) arrayMap.keyAt(size);
        if (componentName.getPackageName().equals(str)) {
          arrayMap.removeAt(size);
          arrayList.add(getParamFile(launchParamFolder, componentName));
        }
      }
    }
    synchronized (this.mPersisterQueue) {
      this.mPersisterQueue.removeItems(
          new Predicate() { // from class:
                            // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeRecordForPackage$2;
              lambda$removeRecordForPackage$2 =
                  LaunchParamsPersister.lambda$removeRecordForPackage$2(
                      str, (LaunchParamsPersister.LaunchParamsWriteQueueItem) obj);
              return lambda$removeRecordForPackage$2;
            }
          },
          LaunchParamsWriteQueueItem.class);
      this.mPersisterQueue.addItem(new CleanUpComponentQueueItem(arrayList), true);
    }
  }

  public static /* synthetic */ boolean lambda$removeRecordForPackage$2(
      String str, LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
    return launchParamsWriteQueueItem.mComponentName.getPackageName().equals(str);
  }

  public final File getParamFile(File file, ComponentName componentName) {
    return new File(file, componentName.flattenToShortString().replace('/', '-') + ".xml");
  }

  public final File getLaunchParamFolder(int i) {
    return new File((File) this.mUserFolderGetter.apply(i), "launch_params");
  }

  public void saveFreeformBounds(Task task, DisplayContent displayContent) {
    ComponentName componentName = task.realActivity;
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
                new Function() { // from class:
                                 // com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda3
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    LaunchParamsPersister.PersistableLaunchParams lambda$saveFreeformBounds$3;
                    lambda$saveFreeformBounds$3 =
                        LaunchParamsPersister.this.lambda$saveFreeformBounds$3((ComponentName) obj);
                    return lambda$saveFreeformBounds$3;
                  }
                });
    boolean saveFreeformBoundsToLaunchParams =
        saveFreeformBoundsToLaunchParams(task, displayContent, persistableLaunchParams);
    addComponentNameToLaunchParamAffinityMapIfNotNull(
        componentName, persistableLaunchParams.mWindowLayoutAffinity);
    if (saveFreeformBoundsToLaunchParams) {
      this.mPersisterQueue.updateLastOrAddItem(
          new LaunchParamsWriteQueueItem(i, componentName, persistableLaunchParams), false);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ PersistableLaunchParams lambda$saveFreeformBounds$3(
      ComponentName componentName) {
    return new PersistableLaunchParams();
  }

  public final boolean saveFreeformBoundsToLaunchParams(
      Task task, DisplayContent displayContent, PersistableLaunchParams persistableLaunchParams) {
    boolean z;
    int i;
    boolean z2 = true;
    if (!CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY
        || persistableLaunchParams.mDisplayDeviceType
            == (i = displayContent.getConfiguration().semDisplayDeviceType)) {
      z = false;
    } else {
      persistableLaunchParams.mDisplayDeviceType = i;
      Slog.d(
          "LaunchParamsPersister",
          "saveDisplayDeviceType " + persistableLaunchParams.mDisplayDeviceType);
      z = true;
    }
    FreeformPersistBoundsParams freeformPersistBoundsParams =
        persistableLaunchParams.mFreeformPersistBoundsParams;
    Rect rect = freeformPersistBoundsParams.mFreeformBounds;
    Rect rect2 = task.mLastNonFullscreenBounds;
    if (rect2 != null && !rect.equals(rect2)) {
      rect.set(task.mLastNonFullscreenBounds);
      Slog.d("LaunchParamsPersister", "saveFreeformBounds " + rect);
      z = true;
    }
    Rect rect3 = freeformPersistBoundsParams.mDisplayBounds;
    Rect rect4 = new Rect();
    displayContent.getBaseDisplayRect(rect4);
    if (!rect3.equals(rect4)) {
      rect3.set(rect4);
      Slog.d("LaunchParamsPersister", "saveDisplayBounds " + rect3);
      z = true;
    }
    if (freeformPersistBoundsParams.mRotation != task.getRotation()) {
      freeformPersistBoundsParams.mRotation = task.getRotation();
      Slog.d(
          "LaunchParamsPersister",
          "saveRotation " + Surface.rotationToString(freeformPersistBoundsParams.mRotation));
    } else {
      z2 = z;
    }
    if (z2) {
      if (persistableLaunchParams.mDisplayUniqueId == null) {
        persistableLaunchParams.mDisplayUniqueId = "";
      }
      persistableLaunchParams.mTimestamp = System.currentTimeMillis();
    }
    return z2;
  }

  public class PackageListObserver implements PackageManagerInternal.PackageListObserver {
    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public void onPackageAdded(String str, int i) {}

    public PackageListObserver() {}

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public void onPackageRemoved(String str, int i) {
      WindowManagerGlobalLock globalLock =
          LaunchParamsPersister.this.mSupervisor.mService.getGlobalLock();
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (globalLock) {
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

  public class LaunchParamsWriteQueueItem implements PersisterQueue.WriteQueueItem {
    public final ComponentName mComponentName;
    public PersistableLaunchParams mLaunchParams;
    public final int mUserId;

    public LaunchParamsWriteQueueItem(
        int i, ComponentName componentName, PersistableLaunchParams persistableLaunchParams) {
      this.mUserId = i;
      this.mComponentName = componentName;
      this.mLaunchParams = persistableLaunchParams;
    }

    public final byte[] saveParamsToXml() {
      try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, "launch_params");
        this.mLaunchParams.saveToXml(resolveSerializer);
        resolveSerializer.endTag((String) null, "launch_params");
        resolveSerializer.endDocument();
        resolveSerializer.flush();
        return byteArrayOutputStream.toByteArray();
      } catch (IOException unused) {
        return null;
      }
    }

    @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
    public void process() {
      FileOutputStream fileOutputStream;
      byte[] saveParamsToXml = saveParamsToXml();
      File launchParamFolder = LaunchParamsPersister.this.getLaunchParamFolder(this.mUserId);
      if (!launchParamFolder.isDirectory() && !launchParamFolder.mkdirs()) {
        Slog.w("LaunchParamsPersister", "Failed to create folder for " + this.mUserId);
        return;
      }
      AtomicFile atomicFile =
          new AtomicFile(
              LaunchParamsPersister.this.getParamFile(launchParamFolder, this.mComponentName));
      try {
        fileOutputStream = atomicFile.startWrite();
      } catch (Exception e) {
        e = e;
        fileOutputStream = null;
      }
      try {
        fileOutputStream.write(saveParamsToXml);
        atomicFile.finishWrite(fileOutputStream);
      } catch (Exception e2) {
        e = e2;
        Slog.e("LaunchParamsPersister", "Failed to write param file for " + this.mComponentName, e);
        if (fileOutputStream != null) {
          atomicFile.failWrite(fileOutputStream);
        }
      }
    }

    @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
    public boolean matches(LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
      return this.mUserId == launchParamsWriteQueueItem.mUserId
          && this.mComponentName.equals(launchParamsWriteQueueItem.mComponentName);
    }

    @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
    public void updateFrom(LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
      this.mLaunchParams = launchParamsWriteQueueItem.mLaunchParams;
    }
  }

  public class CleanUpComponentQueueItem implements PersisterQueue.WriteQueueItem {
    public final List mComponentFiles;

    public CleanUpComponentQueueItem(List list) {
      this.mComponentFiles = list;
    }

    @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
    public void process() {
      for (File file : this.mComponentFiles) {
        if (!file.delete()) {
          Slog.w("LaunchParamsPersister", "Failed to delete " + file.getAbsolutePath());
        }
      }
    }
  }

  public class PersistableLaunchParams {
    public final Rect mBounds;
    public final DexPersistBoundsParams mDexPersistBoundsParams;
    public int mDisplayDeviceType;
    public String mDisplayUniqueId;
    public final FreeformPersistBoundsParams mFreeformPersistBoundsParams;
    public final NewDexPersistBoundsParams mNewDexPersistBoundsParams;
    public int mOrientation;
    public long mTimestamp;
    public String mWindowLayoutAffinity;
    public int mWindowingMode;

    public PersistableLaunchParams() {
      this.mBounds = new Rect();
      this.mDexPersistBoundsParams = new DexPersistBoundsParams();
      this.mFreeformPersistBoundsParams = new FreeformPersistBoundsParams();
      this.mNewDexPersistBoundsParams = new NewDexPersistBoundsParams();
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
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
            (String) null, "display_device_type", Integer.toString(this.mDisplayDeviceType));
      }
      this.mFreeformPersistBoundsParams.saveToXml(typedXmlSerializer);
      this.mDexPersistBoundsParams.saveToXml(typedXmlSerializer);
      if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
        this.mNewDexPersistBoundsParams.saveToXml(typedXmlSerializer);
      }
    }

    public void restore(File file, TypedXmlPullParser typedXmlPullParser) {
      String attributeValue;
      for (int i = 0; i < typedXmlPullParser.getAttributeCount(); i++) {
        attributeValue = typedXmlPullParser.getAttributeValue(i);
        String attributeName = typedXmlPullParser.getAttributeName(i);
        attributeName.hashCode();
        switch (attributeName) {
          case "display_device_type":
            this.mDisplayDeviceType = Integer.parseInt(attributeValue);
            break;
          case "display_unique_id":
            this.mDisplayUniqueId = attributeValue;
            break;
          case "orientation":
            this.mOrientation = Integer.parseInt(attributeValue);
            break;
          case "bounds":
            Rect unflattenFromString = Rect.unflattenFromString(attributeValue);
            if (unflattenFromString != null) {
              this.mBounds.set(unflattenFromString);
              break;
            } else {
              break;
            }
          case "windowing_mode":
            this.mWindowingMode = Integer.parseInt(attributeValue);
            break;
          case "window_layout_affinity":
            this.mWindowLayoutAffinity = attributeValue;
            break;
          default:
            this.mFreeformPersistBoundsParams.restore(
                typedXmlPullParser.getAttributeName(i), attributeValue);
            this.mDexPersistBoundsParams.restore(
                typedXmlPullParser.getAttributeName(i), attributeValue);
            if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
              this.mNewDexPersistBoundsParams.restore(
                  typedXmlPullParser.getAttributeName(i), attributeValue);
              break;
            } else {
              break;
            }
        }
      }
      this.mTimestamp = file.lastModified();
    }

    public String toString() {
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
      if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
        sb.append(this.mNewDexPersistBoundsParams);
      }
      sb.append(" }");
      return sb.toString();
    }
  }
}
