package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class RemoteComposeState {
    private static final int MAX_FLOATS = 500;
    public static final int START_ID = 42;
    ArrayList<VariableSupport> mAllVarListeners;
    IntMap<ArrayList<VariableSupport>> mVarListeners;
    private final IntMap<Object> mIntDataMap = new IntMap<>();
    private final IntMap<Boolean> mIntWrittenMap = new IntMap<>();
    private final HashMap<Object, Integer> mDataIntMap = new HashMap<>();
    private final float[] mFloatMap = new float[500];
    private final int[] mColorMap = new int[500];
    private int mNextId = 42;

    public RemoteComposeState() {
        for (int i = 0; i < this.mFloatMap.length; i++) {
            this.mFloatMap[i] = Float.NaN;
        }
        this.mVarListeners = new IntMap<>();
        this.mAllVarListeners = new ArrayList<>();
    }

    public Object getFromId(int id) {
        return this.mIntDataMap.get(id);
    }

    public boolean containsId(int id) {
        return this.mIntDataMap.get(id) != null;
    }

    public int dataGetId(Object image) {
        Integer res = this.mDataIntMap.get(image);
        if (res == null) {
            return -1;
        }
        return res.intValue();
    }

    public int cache(Object image) {
        int id = nextId();
        this.mDataIntMap.put(image, Integer.valueOf(id));
        this.mIntDataMap.put(id, image);
        return id;
    }

    public void cache(int id, Object item) {
        this.mDataIntMap.put(item, Integer.valueOf(id));
        this.mIntDataMap.put(id, item);
    }

    public void update(int id, Object item) {
        this.mDataIntMap.remove(this.mIntDataMap.get(id));
        this.mDataIntMap.put(item, Integer.valueOf(id));
        this.mIntDataMap.put(id, item);
    }

    public int cacheFloat(float item) {
        int id = nextId();
        this.mFloatMap[id] = item;
        return id;
    }

    public void cacheFloat(int id, float item) {
        this.mFloatMap[id] = item;
    }

    public void updateFloat(int id, float item) {
        this.mFloatMap[id] = item;
    }

    public float getFloat(int id) {
        return this.mFloatMap[id];
    }

    public int getColor(int id) {
        return this.mColorMap[id];
    }

    public void updateColor(int id, int color) {
        this.mColorMap[id] = color;
    }

    public boolean wasNotWritten(int id) {
        return !this.mIntWrittenMap.get(id).booleanValue();
    }

    public void markWritten(int id) {
        this.mIntWrittenMap.put(id, true);
    }

    void reset() {
        this.mIntWrittenMap.clear();
    }

    public int nextId() {
        int i = this.mNextId;
        this.mNextId = i + 1;
        return i;
    }

    public void setNextId(int id) {
        this.mNextId = id;
    }

    private void add(int id, VariableSupport variableSupport) {
        ArrayList<VariableSupport> v = this.mVarListeners.get(id);
        if (v == null) {
            v = new ArrayList<>();
            this.mVarListeners.put(id, v);
        }
        v.add(variableSupport);
        this.mAllVarListeners.add(variableSupport);
    }

    public void listenToVar(int id, VariableSupport variableSupport) {
        add(id, variableSupport);
    }

    public int getOpsToUpdate(RemoteContext context) {
        Iterator<VariableSupport> it = this.mAllVarListeners.iterator();
        while (it.hasNext()) {
            VariableSupport vs = it.next();
            vs.updateVariables(context);
        }
        if (this.mVarListeners.get(1) != null) {
            return 1;
        }
        if (this.mVarListeners.get(2) != null) {
            return 1000;
        }
        if (this.mVarListeners.get(3) != null) {
            return 60000;
        }
        return -1;
    }

    public void setWindowWidth(float width) {
        updateFloat(5, width);
    }

    public void setWindowHeight(float height) {
        updateFloat(6, height);
    }
}
