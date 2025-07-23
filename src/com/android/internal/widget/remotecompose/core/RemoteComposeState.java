package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.DataMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntFloatMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntIntMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.NanMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class RemoteComposeState implements CollectionsAccess {
    private static final int MAX_DATA = 1000;
    public static final int START_ID = 42;
    private static int sMaxColors = 200;
    private final IntMap<Object> mIntDataMap = new IntMap<>();
    private final IntMap<Boolean> mIntWrittenMap = new IntMap<>();
    private final HashMap<Object, Integer> mDataIntMap = new HashMap<>();
    private final IntFloatMap mFloatMap = new IntFloatMap();
    private final IntIntMap mIntegerMap = new IntIntMap();
    private final IntIntMap mColorMap = new IntIntMap();
    private final IntMap<DataMap> mDataMapMap = new IntMap<>();
    private final IntMap<Object> mObjectMap = new IntMap<>();
    private final IntMap<Object> mPathMap = new IntMap<>();
    private final IntMap<float[]> mPathData = new IntMap<>();
    private boolean[] mColorOverride = new boolean[sMaxColors];
    private final IntMap<ArrayAccess> mCollectionMap = new IntMap<>();
    private final boolean[] mDataOverride = new boolean[1000];
    private final boolean[] mIntegerOverride = new boolean[1000];
    private final boolean[] mFloatOverride = new boolean[1000];
    private int mNextId = 42;
    private int[] mIdMaps = {42, NanMap.START_VAR, NanMap.START_ARRAY};
    private RemoteContext mRemoteContext = null;
    IntMap<ArrayList<VariableSupport>> mVarListeners = new IntMap<>();
    ArrayList<VariableSupport> mAllVarListeners = new ArrayList<>();

    public Object getFromId(int i) {
        return this.mIntDataMap.get(i);
    }

    public boolean containsId(int i) {
        return this.mIntDataMap.get(i) != null;
    }

    public int dataGetId(Object obj) {
        Integer num = this.mDataIntMap.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public int cacheData(Object obj) {
        int nextId = nextId();
        this.mDataIntMap.put(obj, Integer.valueOf(nextId));
        this.mIntDataMap.put(nextId, obj);
        return nextId;
    }

    public int cacheData(Object obj, int i) {
        int nextId = nextId(i);
        this.mDataIntMap.put(obj, Integer.valueOf(nextId));
        this.mIntDataMap.put(nextId, obj);
        return nextId;
    }

    public void cacheData(int i, Object obj) {
        this.mDataIntMap.put(obj, Integer.valueOf(i));
        this.mIntDataMap.put(i, obj);
    }

    public void updateData(int i, Object obj) {
        Object obj2;
        if (this.mDataOverride[i] || (obj2 = this.mIntDataMap.get(i)) == obj) {
            return;
        }
        this.mDataIntMap.remove(obj2);
        this.mDataIntMap.put(obj, Integer.valueOf(i));
        this.mIntDataMap.put(i, obj);
        updateListeners(i);
    }

    public Object getPath(int i) {
        return this.mPathMap.get(i);
    }

    public void putPath(int i, Object obj) {
        this.mPathMap.put(i, obj);
    }

    public void putPathData(int i, float[] fArr) {
        this.mPathData.put(i, fArr);
        this.mPathMap.remove(i);
    }

    public float[] getPathData(int i) {
        return this.mPathData.get(i);
    }

    public void overrideData(int i, Object obj) {
        Object obj2 = this.mIntDataMap.get(i);
        if (obj2 != obj) {
            this.mDataIntMap.remove(obj2);
            this.mDataIntMap.put(obj, Integer.valueOf(i));
            this.mIntDataMap.put(i, obj);
            this.mDataOverride[i] = true;
            updateListeners(i);
        }
    }

    public int cacheFloat(float f) {
        int nextId = nextId();
        this.mFloatMap.put(nextId, f);
        this.mIntegerMap.put(nextId, (int) f);
        return nextId;
    }

    public void cacheFloat(int i, float f) {
        this.mFloatMap.put(i, f);
    }

    public void updateFloat(int i, float f) {
        if (this.mFloatOverride[i] || this.mFloatMap.get(i) == f) {
            return;
        }
        this.mFloatMap.put(i, f);
        this.mIntegerMap.put(i, (int) f);
        updateListeners(i);
    }

    public void overrideFloat(int i, float f) {
        if (this.mFloatMap.get(i) != f) {
            this.mFloatMap.put(i, f);
            this.mIntegerMap.put(i, (int) f);
            this.mFloatOverride[i] = true;
            updateListeners(i);
        }
    }

    public int cacheInteger(int i) {
        int nextId = nextId();
        this.mIntegerMap.put(nextId, i);
        this.mFloatMap.put(nextId, i);
        return nextId;
    }

    public void updateInteger(int i, int i2) {
        if (this.mIntegerOverride[i] || this.mIntegerMap.get(i) == i2) {
            return;
        }
        this.mFloatMap.put(i, i2);
        this.mIntegerMap.put(i, i2);
        updateListeners(i);
    }

    public void overrideInteger(int i, int i2) {
        if (this.mIntegerMap.get(i) != i2) {
            this.mIntegerMap.put(i, i2);
            this.mFloatMap.put(i, i2);
            this.mIntegerOverride[i] = true;
            updateListeners(i);
        }
    }

    public float getFloat(int i) {
        return this.mFloatMap.get(i);
    }

    public int getInteger(int i) {
        return this.mIntegerMap.get(i);
    }

    public int getColor(int i) {
        return this.mColorMap.get(i);
    }

    public void updateColor(int i, int i2) {
        if (i >= sMaxColors || !this.mColorOverride[i]) {
            this.mColorMap.put(i, i2);
            updateListeners(i);
        }
    }

    private void updateListeners(int i) {
        ArrayList<VariableSupport> arrayList = this.mVarListeners.get(i);
        if (arrayList == null || this.mRemoteContext == null) {
            return;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList.get(i2).markDirty();
        }
    }

    public void overrideColor(int i, int i2) {
        int i3 = sMaxColors;
        if (i >= i3) {
            int i4 = i3 * 2;
            sMaxColors = i4;
            this.mColorOverride = Arrays.copyOf(this.mColorOverride, i4);
        }
        this.mColorOverride[i] = true;
        this.mColorMap.put(i, i2);
        updateListeners(i);
    }

    public void clearColorOverride() {
        int i = 0;
        while (true) {
            boolean[] zArr = this.mColorOverride;
            if (i >= zArr.length) {
                return;
            }
            zArr[i] = false;
            i++;
        }
    }

    public void clearDataOverride(int i) {
        this.mDataOverride[i] = false;
        updateListeners(i);
    }

    public void clearIntegerOverride(int i) {
        this.mIntegerOverride[i] = false;
        updateListeners(i);
    }

    public void clearFloatOverride(int i) {
        this.mFloatOverride[i] = false;
        updateListeners(i);
    }

    public boolean wasNotWritten(int i) {
        return !this.mIntWrittenMap.get(i).booleanValue();
    }

    public void markWritten(int i) {
        this.mIntWrittenMap.put(i, true);
    }

    public void reset() {
        this.mIntWrittenMap.clear();
        this.mDataIntMap.clear();
    }

    public int nextId() {
        int i = this.mNextId;
        this.mNextId = i + 1;
        return i;
    }

    public int nextId(int i) {
        if (i == 0) {
            int i2 = this.mNextId;
            this.mNextId = i2 + 1;
            return i2;
        }
        int[] iArr = this.mIdMaps;
        int i3 = iArr[i];
        iArr[i] = i3 + 1;
        return i3;
    }

    public void setNextId(int i) {
        this.mNextId = i;
    }

    private void add(int i, VariableSupport variableSupport) {
        ArrayList<VariableSupport> arrayList = this.mVarListeners.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mVarListeners.put(i, arrayList);
        }
        arrayList.add(variableSupport);
        this.mAllVarListeners.add(variableSupport);
    }

    public void listenToVar(int i, VariableSupport variableSupport) {
        add(i, variableSupport);
    }

    public boolean hasListener(int i) {
        return this.mVarListeners.get(i) != null;
    }

    public int getOpsToUpdate(RemoteContext remoteContext) {
        if (this.mVarListeners.get(1) != null) {
            return 1;
        }
        if (this.mVarListeners.get(2) != null) {
            return 1000;
        }
        return this.mVarListeners.get(3) != null ? 60000 : -1;
    }

    public void setWindowWidth(float f) {
        updateFloat(5, f);
    }

    public void setWindowHeight(float f) {
        updateFloat(6, f);
    }

    public void addCollection(int i, ArrayAccess arrayAccess) {
        this.mCollectionMap.put(i & 1048575, arrayAccess);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess
    public float getFloatValue(int i, int i2) {
        return this.mCollectionMap.get(i & 1048575).getFloatValue(i2);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess
    public float[] getFloats(int i) {
        return this.mCollectionMap.get(i & 1048575).getFloats();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess
    public int getId(int i, int i2) {
        return this.mCollectionMap.get(i & 1048575).getId(i2);
    }

    public void putDataMap(int i, DataMap dataMap) {
        this.mDataMapMap.put(i, dataMap);
    }

    public DataMap getDataMap(int i) {
        return this.mDataMapMap.get(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess
    public int getListLength(int i) {
        return this.mCollectionMap.get(i & 1048575).getLength();
    }

    public void setContext(RemoteContext remoteContext) {
        this.mRemoteContext = remoteContext;
        remoteContext.clearLastOpCount();
    }

    public void updateObject(int i, Object obj) {
        this.mObjectMap.put(i, obj);
    }

    public Object getObject(int i) {
        return this.mObjectMap.get(i);
    }
}
