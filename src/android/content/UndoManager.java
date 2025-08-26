package android.content;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class UndoManager {
    public static final int MERGE_MODE_ANY = 2;
    public static final int MERGE_MODE_NONE = 0;
    public static final int MERGE_MODE_UNIQUE = 1;
    private boolean mInUndo;
    private boolean mMerged;
    private int mNextSavedIdx;
    private UndoOwner[] mStateOwners;
    private int mStateSeq;
    private int mUpdateCount;
    private UndoState mWorking;
    private final ArrayMap<String, UndoOwner> mOwners = new ArrayMap<>(1);
    private final ArrayList<UndoState> mUndos = new ArrayList<>();
    private final ArrayList<UndoState> mRedos = new ArrayList<>();
    private int mHistorySize = 20;
    private int mCommitId = 1;

    void removeOwner(UndoOwner undoOwner) {
    }

    public UndoOwner getOwner(String str, Object obj) {
        if (str == null) {
            throw new NullPointerException("tag can't be null");
        }
        if (obj == null) {
            throw new NullPointerException("data can't be null");
        }
        UndoOwner undoOwner = this.mOwners.get(str);
        if (undoOwner != null) {
            if (undoOwner.mData == obj) {
                return undoOwner;
            }
            if (undoOwner.mData != null) {
                throw new IllegalStateException("Owner " + undoOwner + " already exists with data " + undoOwner.mData + " but giving different data " + obj);
            }
            undoOwner.mData = obj;
            return undoOwner;
        }
        UndoOwner undoOwner2 = new UndoOwner(str, this);
        undoOwner2.mData = obj;
        this.mOwners.put(str, undoOwner2);
        return undoOwner2;
    }

    public void saveInstanceState(Parcel parcel) {
        if (this.mUpdateCount > 0) {
            throw new IllegalStateException("Can't save state while updating");
        }
        int i = this.mStateSeq + 1;
        this.mStateSeq = i;
        if (i <= 0) {
            this.mStateSeq = 0;
        }
        this.mNextSavedIdx = 0;
        parcel.writeInt(this.mHistorySize);
        parcel.writeInt(this.mOwners.size());
        int size = this.mUndos.size();
        while (size > 0) {
            parcel.writeInt(1);
            size--;
            this.mUndos.get(size).writeToParcel(parcel);
        }
        int size2 = this.mRedos.size();
        while (size2 > 0) {
            parcel.writeInt(2);
            size2--;
            this.mRedos.get(size2).writeToParcel(parcel);
        }
        parcel.writeInt(0);
    }

    void saveOwner(UndoOwner undoOwner, Parcel parcel) {
        int i = undoOwner.mStateSeq;
        int i2 = this.mStateSeq;
        if (i == i2) {
            parcel.writeInt(undoOwner.mSavedIdx);
            return;
        }
        undoOwner.mStateSeq = i2;
        undoOwner.mSavedIdx = this.mNextSavedIdx;
        parcel.writeInt(undoOwner.mSavedIdx);
        parcel.writeString(undoOwner.mTag);
        parcel.writeInt(undoOwner.mOpCount);
        this.mNextSavedIdx++;
    }

    public void restoreInstanceState(Parcel parcel, ClassLoader classLoader) {
        if (this.mUpdateCount > 0) {
            throw new IllegalStateException("Can't save state while updating");
        }
        forgetUndos(null, -1);
        forgetRedos(null, -1);
        this.mHistorySize = parcel.readInt();
        this.mStateOwners = new UndoOwner[parcel.readInt()];
        while (true) {
            int i = parcel.readInt();
            if (i == 0) {
                return;
            }
            UndoState undoState = new UndoState(this, parcel, classLoader);
            if (i == 1) {
                this.mUndos.add(0, undoState);
            } else {
                this.mRedos.add(0, undoState);
            }
        }
    }

    UndoOwner restoreOwner(Parcel parcel) {
        int i = parcel.readInt();
        UndoOwner undoOwner = this.mStateOwners[i];
        if (undoOwner != null) {
            return undoOwner;
        }
        String string = parcel.readString();
        int i2 = parcel.readInt();
        UndoOwner undoOwner2 = new UndoOwner(string, this);
        undoOwner2.mOpCount = i2;
        this.mStateOwners[i] = undoOwner2;
        this.mOwners.put(string, undoOwner2);
        return undoOwner2;
    }

    public void setHistorySize(int i) {
        this.mHistorySize = i;
        if (i < 0 || countUndos(null) <= this.mHistorySize) {
            return;
        }
        forgetUndos(null, countUndos(null) - this.mHistorySize);
    }

    public int getHistorySize() {
        return this.mHistorySize;
    }

    public int undo(UndoOwner[] undoOwnerArr, int i) {
        if (this.mWorking != null) {
            throw new IllegalStateException("Can't be called during an update");
        }
        this.mInUndo = true;
        UndoState topUndo = getTopUndo(null);
        if (topUndo != null) {
            topUndo.makeExecuted();
        }
        int iFindPrevState = -1;
        int i2 = 0;
        while (i > 0) {
            iFindPrevState = findPrevState(this.mUndos, undoOwnerArr, iFindPrevState);
            if (iFindPrevState < 0) {
                break;
            }
            UndoState undoStateRemove = this.mUndos.remove(iFindPrevState);
            undoStateRemove.undo();
            this.mRedos.add(undoStateRemove);
            i--;
            i2++;
        }
        this.mInUndo = false;
        return i2;
    }

    public int redo(UndoOwner[] undoOwnerArr, int i) {
        if (this.mWorking != null) {
            throw new IllegalStateException("Can't be called during an update");
        }
        this.mInUndo = true;
        int iFindPrevState = -1;
        int i2 = 0;
        while (i > 0) {
            iFindPrevState = findPrevState(this.mRedos, undoOwnerArr, iFindPrevState);
            if (iFindPrevState < 0) {
                break;
            }
            UndoState undoStateRemove = this.mRedos.remove(iFindPrevState);
            undoStateRemove.redo();
            this.mUndos.add(undoStateRemove);
            i--;
            i2++;
        }
        this.mInUndo = false;
        return i2;
    }

    public boolean isInUndo() {
        return this.mInUndo;
    }

    public int forgetUndos(UndoOwner[] undoOwnerArr, int i) {
        if (i < 0) {
            i = this.mUndos.size();
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mUndos.size() && i3 < i) {
            UndoState undoState = this.mUndos.get(i2);
            if (i <= 0 || !matchOwners(undoState, undoOwnerArr)) {
                i2++;
            } else {
                undoState.destroy();
                this.mUndos.remove(i2);
                i3++;
            }
        }
        return i3;
    }

    public int forgetRedos(UndoOwner[] undoOwnerArr, int i) {
        if (i < 0) {
            i = this.mRedos.size();
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mRedos.size() && i3 < i) {
            UndoState undoState = this.mRedos.get(i2);
            if (i <= 0 || !matchOwners(undoState, undoOwnerArr)) {
                i2++;
            } else {
                undoState.destroy();
                this.mRedos.remove(i2);
                i3++;
            }
        }
        return i3;
    }

    public int countUndos(UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return this.mUndos.size();
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int iFindNextState = findNextState(this.mUndos, undoOwnerArr, i);
            if (iFindNextState < 0) {
                return i2;
            }
            i2++;
            i = iFindNextState + 1;
        }
    }

    public int countRedos(UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return this.mRedos.size();
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int iFindNextState = findNextState(this.mRedos, undoOwnerArr, i);
            if (iFindNextState < 0) {
                return i2;
            }
            i2++;
            i = iFindNextState + 1;
        }
    }

    public CharSequence getUndoLabel(UndoOwner[] undoOwnerArr) {
        UndoState topUndo = getTopUndo(undoOwnerArr);
        if (topUndo != null) {
            return topUndo.getLabel();
        }
        return null;
    }

    public CharSequence getRedoLabel(UndoOwner[] undoOwnerArr) {
        UndoState topRedo = getTopRedo(undoOwnerArr);
        if (topRedo != null) {
            return topRedo.getLabel();
        }
        return null;
    }

    public void beginUpdate(CharSequence charSequence) {
        if (this.mInUndo) {
            throw new IllegalStateException("Can't being update while performing undo/redo");
        }
        if (this.mUpdateCount <= 0) {
            createWorkingState();
            this.mMerged = false;
            this.mUpdateCount = 0;
        }
        this.mWorking.updateLabel(charSequence);
        this.mUpdateCount++;
    }

    private void createWorkingState() {
        int i = this.mCommitId;
        this.mCommitId = i + 1;
        this.mWorking = new UndoState(this, i);
        if (this.mCommitId < 0) {
            this.mCommitId = 1;
        }
    }

    public boolean isInUpdate() {
        return this.mUpdateCount > 0;
    }

    public void setUndoLabel(CharSequence charSequence) {
        UndoState undoState = this.mWorking;
        if (undoState == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        undoState.setLabel(charSequence);
    }

    public void suggestUndoLabel(CharSequence charSequence) {
        UndoState undoState = this.mWorking;
        if (undoState == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        undoState.updateLabel(charSequence);
    }

    public int getUpdateNestingLevel() {
        return this.mUpdateCount;
    }

    public boolean hasOperation(UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        return undoState.hasOperation(undoOwner);
    }

    public UndoOperation<?> getLastOperation(int i) {
        return getLastOperation(null, null, i);
    }

    public UndoOperation<?> getLastOperation(UndoOwner undoOwner, int i) {
        return getLastOperation(null, undoOwner, i);
    }

    public <T extends UndoOperation> T getLastOperation(Class<T> cls, UndoOwner undoOwner, int i) {
        UndoState topUndo;
        T t;
        UndoState undoState = this.mWorking;
        if (undoState == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        if (i != 0 && !this.mMerged && !undoState.hasData() && (topUndo = getTopUndo(null)) != null && ((i == 2 || !topUndo.hasMultipleOwners()) && topUndo.canMerge() && (t = (T) topUndo.getLastOperation(cls, undoOwner)) != null && t.allowMerge())) {
            this.mWorking.destroy();
            this.mWorking = topUndo;
            this.mUndos.remove(topUndo);
            this.mMerged = true;
            return t;
        }
        return (T) this.mWorking.getLastOperation(cls, undoOwner);
    }

    public void addOperation(UndoOperation<?> undoOperation, int i) {
        UndoState topUndo;
        if (this.mWorking == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        if (undoOperation.getOwner().mManager != this) {
            throw new IllegalArgumentException("Given operation's owner is not in this undo manager.");
        }
        if (i != 0 && !this.mMerged && !this.mWorking.hasData() && (topUndo = getTopUndo(null)) != null && ((i == 2 || !topUndo.hasMultipleOwners()) && topUndo.canMerge() && topUndo.hasOperation(undoOperation.getOwner()))) {
            this.mWorking.destroy();
            this.mWorking = topUndo;
            this.mUndos.remove(topUndo);
            this.mMerged = true;
        }
        this.mWorking.addOperation(undoOperation);
    }

    public void endUpdate() {
        if (this.mWorking == null) {
            throw new IllegalStateException("Must be called during an update");
        }
        int i = this.mUpdateCount - 1;
        this.mUpdateCount = i;
        if (i == 0) {
            pushWorkingState();
        }
    }

    private void pushWorkingState() {
        int size = this.mUndos.size();
        int i = size + 1;
        if (this.mWorking.hasData()) {
            this.mUndos.add(this.mWorking);
            forgetRedos(null, -1);
            this.mWorking.commit();
            if (i >= 2) {
                this.mUndos.get(size - 1).makeExecuted();
            }
        } else {
            this.mWorking.destroy();
        }
        this.mWorking = null;
        int i2 = this.mHistorySize;
        if (i2 < 0 || i <= i2) {
            return;
        }
        forgetUndos(null, i - i2);
    }

    public int commitState(UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState != null && undoState.hasData()) {
            if (undoOwner != null && !this.mWorking.hasOperation(undoOwner)) {
                return -1;
            }
            this.mWorking.setCanMerge(false);
            int commitId = this.mWorking.getCommitId();
            pushWorkingState();
            createWorkingState();
            this.mMerged = true;
            return commitId;
        }
        UndoState topUndo = getTopUndo(null);
        if (topUndo == null) {
            return -1;
        }
        if (undoOwner != null && !topUndo.hasOperation(undoOwner)) {
            return -1;
        }
        topUndo.setCanMerge(false);
        return topUndo.getCommitId();
    }

    public boolean uncommitState(int i, UndoOwner undoOwner) {
        UndoState undoState = this.mWorking;
        if (undoState != null && undoState.getCommitId() == i) {
            if (undoOwner == null || this.mWorking.hasOperation(undoOwner)) {
                return this.mWorking.setCanMerge(true);
            }
            return false;
        }
        UndoState topUndo = getTopUndo(null);
        if (topUndo == null) {
            return false;
        }
        if ((undoOwner == null || topUndo.hasOperation(undoOwner)) && topUndo.getCommitId() == i) {
            return topUndo.setCanMerge(true);
        }
        return false;
    }

    UndoState getTopUndo(UndoOwner[] undoOwnerArr) {
        int iFindPrevState;
        if (this.mUndos.size() > 0 && (iFindPrevState = findPrevState(this.mUndos, undoOwnerArr, -1)) >= 0) {
            return this.mUndos.get(iFindPrevState);
        }
        return null;
    }

    UndoState getTopRedo(UndoOwner[] undoOwnerArr) {
        int iFindPrevState;
        if (this.mRedos.size() > 0 && (iFindPrevState = findPrevState(this.mRedos, undoOwnerArr, -1)) >= 0) {
            return this.mRedos.get(iFindPrevState);
        }
        return null;
    }

    boolean matchOwners(UndoState undoState, UndoOwner[] undoOwnerArr) {
        if (undoOwnerArr == null) {
            return true;
        }
        for (UndoOwner undoOwner : undoOwnerArr) {
            if (undoState.matchOwner(undoOwner)) {
                return true;
            }
        }
        return false;
    }

    int findPrevState(ArrayList<UndoState> arrayList, UndoOwner[] undoOwnerArr, int i) {
        int size = arrayList.size();
        if (i == -1) {
            i = size - 1;
        }
        if (i >= size) {
            return -1;
        }
        if (undoOwnerArr == null) {
            return i;
        }
        while (i >= 0) {
            if (matchOwners(arrayList.get(i), undoOwnerArr)) {
                return i;
            }
            i--;
        }
        return -1;
    }

    int findNextState(ArrayList<UndoState> arrayList, UndoOwner[] undoOwnerArr, int i) {
        int size = arrayList.size();
        if (i < 0) {
            i = 0;
        }
        if (i >= size) {
            return -1;
        }
        if (undoOwnerArr == null) {
            return i;
        }
        while (i < size) {
            if (matchOwners(arrayList.get(i), undoOwnerArr)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    static final class UndoState {
        private boolean mCanMerge;
        private final int mCommitId;
        private boolean mExecuted;
        private CharSequence mLabel;
        private final UndoManager mManager;
        private final ArrayList<UndoOperation<?>> mOperations;
        private ArrayList<UndoOperation<?>> mRecent;

        UndoState(UndoManager undoManager, int i) {
            this.mOperations = new ArrayList<>();
            this.mCanMerge = true;
            this.mManager = undoManager;
            this.mCommitId = i;
        }

        UndoState(UndoManager undoManager, Parcel parcel, ClassLoader classLoader) {
            this.mOperations = new ArrayList<>();
            this.mCanMerge = true;
            this.mManager = undoManager;
            this.mCommitId = parcel.readInt();
            this.mCanMerge = parcel.readInt() != 0;
            this.mExecuted = parcel.readInt() != 0;
            this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                UndoOwner undoOwnerRestoreOwner = this.mManager.restoreOwner(parcel);
                UndoOperation<?> undoOperation = (UndoOperation) parcel.readParcelable(classLoader, UndoOperation.class);
                undoOperation.mOwner = undoOwnerRestoreOwner;
                this.mOperations.add(undoOperation);
            }
        }

        void writeToParcel(Parcel parcel) {
            if (this.mRecent != null) {
                throw new IllegalStateException("Can't save state before committing");
            }
            parcel.writeInt(this.mCommitId);
            parcel.writeInt(this.mCanMerge ? 1 : 0);
            parcel.writeInt(this.mExecuted ? 1 : 0);
            TextUtils.writeToParcel(this.mLabel, parcel, 0);
            int size = this.mOperations.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                UndoOperation<?> undoOperation = this.mOperations.get(i);
                this.mManager.saveOwner(undoOperation.mOwner, parcel);
                parcel.writeParcelable(undoOperation, 0);
            }
        }

        int getCommitId() {
            return this.mCommitId;
        }

        void setLabel(CharSequence charSequence) {
            this.mLabel = charSequence;
        }

        void updateLabel(CharSequence charSequence) {
            if (this.mLabel != null) {
                this.mLabel = charSequence;
            }
        }

        CharSequence getLabel() {
            return this.mLabel;
        }

        boolean setCanMerge(boolean z) {
            if (z && this.mExecuted) {
                return false;
            }
            this.mCanMerge = z;
            return true;
        }

        void makeExecuted() {
            this.mExecuted = true;
        }

        boolean canMerge() {
            return this.mCanMerge && !this.mExecuted;
        }

        int countOperations() {
            return this.mOperations.size();
        }

        boolean hasOperation(UndoOwner undoOwner) {
            int size = this.mOperations.size();
            if (undoOwner == null) {
                return size != 0;
            }
            for (int i = 0; i < size; i++) {
                if (this.mOperations.get(i).getOwner() == undoOwner) {
                    return true;
                }
            }
            return false;
        }

        boolean hasMultipleOwners() {
            int size = this.mOperations.size();
            if (size <= 1) {
                return false;
            }
            UndoOwner owner = this.mOperations.get(0).getOwner();
            for (int i = 1; i < size; i++) {
                if (this.mOperations.get(i).getOwner() != owner) {
                    return true;
                }
            }
            return false;
        }

        void addOperation(UndoOperation<?> undoOperation) {
            if (this.mOperations.contains(undoOperation)) {
                throw new IllegalStateException("Already holds " + undoOperation);
            }
            this.mOperations.add(undoOperation);
            if (this.mRecent == null) {
                ArrayList<UndoOperation<?>> arrayList = new ArrayList<>();
                this.mRecent = arrayList;
                arrayList.add(undoOperation);
            }
            undoOperation.mOwner.mOpCount++;
        }

        <T extends UndoOperation> T getLastOperation(Class<T> cls, UndoOwner undoOwner) {
            int size = this.mOperations.size();
            if (cls == null && undoOwner == null) {
                if (size > 0) {
                    return this.mOperations.get(size - 1);
                }
                return null;
            }
            for (int i = size - 1; i >= 0; i--) {
                UndoOperation<?> undoOperation = this.mOperations.get(i);
                if (undoOwner == null || undoOperation.getOwner() == undoOwner) {
                    if (cls == null || undoOperation.getClass() == cls) {
                        return undoOperation;
                    }
                    return null;
                }
            }
            return null;
        }

        boolean matchOwner(UndoOwner undoOwner) {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                if (this.mOperations.get(size).matchOwner(undoOwner)) {
                    return true;
                }
            }
            return false;
        }

        boolean hasData() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                if (this.mOperations.get(size).hasData()) {
                    return true;
                }
            }
            return false;
        }

        void commit() {
            ArrayList<UndoOperation<?>> arrayList = this.mRecent;
            int size = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < size; i++) {
                this.mRecent.get(i).commit();
            }
            this.mRecent = null;
        }

        void undo() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                this.mOperations.get(size).undo();
            }
        }

        void redo() {
            int size = this.mOperations.size();
            for (int i = 0; i < size; i++) {
                this.mOperations.get(i).redo();
            }
        }

        void destroy() {
            for (int size = this.mOperations.size() - 1; size >= 0; size--) {
                UndoOwner undoOwner = this.mOperations.get(size).mOwner;
                undoOwner.mOpCount--;
                if (undoOwner.mOpCount <= 0) {
                    if (undoOwner.mOpCount < 0) {
                        throw new IllegalStateException("Underflow of op count on owner " + undoOwner + " in op " + this.mOperations.get(size));
                    }
                    this.mManager.removeOwner(undoOwner);
                }
            }
        }
    }
}
