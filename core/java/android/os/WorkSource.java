package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Settings;
import android.util.proto.ProtoOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class WorkSource implements Parcelable {
    static final boolean DEBUG = false;
    static final String TAG = "WorkSource";
    static WorkSource sGoneWork;
    static WorkSource sNewbWork;
    private ArrayList<WorkChain> mChains;
    String[] mNames;
    int mNum;
    int[] mUids;
    static final WorkSource sTmpWorkSource = new WorkSource(0);
    public static final Parcelable.Creator<WorkSource> CREATOR =
            new Parcelable.Creator<WorkSource>() { // from class: android.os.WorkSource.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WorkSource createFromParcel(Parcel in) {
                    return new WorkSource(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WorkSource[] newArray(int size) {
                    return new WorkSource[size];
                }
            };

    public WorkSource() {
        this.mUids = new int[0];
        this.mNum = 0;
        this.mChains = null;
    }

    public WorkSource(WorkSource orig) {
        this.mUids = new int[0];
        if (orig == null) {
            this.mNum = 0;
            this.mChains = null;
            return;
        }
        this.mNum = orig.mNum;
        this.mUids = (int[]) orig.mUids.clone();
        this.mNames = orig.mNames != null ? (String[]) orig.mNames.clone() : null;
        if (orig.mChains != null) {
            this.mChains = new ArrayList<>(orig.mChains.size());
            Iterator<WorkChain> it = orig.mChains.iterator();
            while (it.hasNext()) {
                WorkChain chain = it.next();
                this.mChains.add(new WorkChain(chain));
            }
            return;
        }
        this.mChains = null;
    }

    @SystemApi
    public WorkSource(int uid) {
        this.mUids = new int[0];
        this.mNum = 1;
        this.mUids = new int[] {uid, 0};
        this.mNames = null;
        this.mChains = null;
    }

    @SystemApi
    public WorkSource(int uid, String packageName) {
        this.mUids = new int[0];
        Objects.requireNonNull(packageName, "packageName can't be null");
        this.mNum = 1;
        this.mUids = new int[] {uid, 0};
        this.mNames = new String[] {packageName, null};
        this.mChains = null;
    }

    WorkSource(Parcel in) {
        this.mUids = new int[0];
        this.mNum = in.readInt();
        this.mUids = (int[]) Objects.requireNonNullElse(in.createIntArray(), new int[0]);
        this.mNames = in.createStringArray();
        int numChains = in.readInt();
        if (numChains >= 0) {
            this.mChains = new ArrayList<>(numChains);
            in.readParcelableList(this.mChains, WorkChain.class.getClassLoader(), WorkChain.class);
        } else {
            this.mChains = null;
        }
    }

    public static boolean isChainedBatteryAttributionEnabled(Context context) {
        return Settings.Global.getInt(
                        context.getContentResolver(),
                        Settings.Global.CHAINED_BATTERY_ATTRIBUTION_ENABLED,
                        0)
                == 1;
    }

    public static boolean isChainedBatteryAttributionEnabled$ravenwood(Context context) {
        return false;
    }

    @SystemApi
    public int size() {
        return this.mNum;
    }

    @Deprecated
    public int get(int index) {
        return getUid(index);
    }

    @SystemApi
    public int getUid(int index) {
        return this.mUids[index];
    }

    public int getAttributionUid() {
        if (isEmpty()) {
            return -1;
        }
        return this.mNum > 0 ? this.mUids[0] : this.mChains.get(0).getAttributionUid();
    }

    @Deprecated
    public String getName(int index) {
        return getPackageName(index);
    }

    @SystemApi
    public String getPackageName(int index) {
        if (this.mNames != null) {
            return this.mNames[index];
        }
        return null;
    }

    private void clearNames() {
        if (this.mNames != null) {
            this.mNames = null;
            int destIndex = 1;
            int newNum = this.mNum;
            for (int sourceIndex = 1; sourceIndex < this.mNum; sourceIndex++) {
                if (this.mUids[sourceIndex] == this.mUids[sourceIndex - 1]) {
                    newNum--;
                } else {
                    this.mUids[destIndex] = this.mUids[sourceIndex];
                    destIndex++;
                }
            }
            this.mNum = newNum;
        }
    }

    public void clear() {
        this.mNum = 0;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof WorkSource)) {
            return false;
        }
        WorkSource other = (WorkSource) o;
        if (diff(other)) {
            return false;
        }
        if (this.mChains == null || this.mChains.isEmpty()) {
            return other.mChains == null || other.mChains.isEmpty();
        }
        return this.mChains.equals(other.mChains);
    }

    public int hashCode() {
        int result = 0;
        for (int i = 0; i < this.mNum; i++) {
            result = ((result << 4) | (result >>> 28)) ^ this.mUids[i];
        }
        if (this.mNames != null) {
            for (int i2 = 0; i2 < this.mNum; i2++) {
                result = ((result << 4) | (result >>> 28)) ^ this.mNames[i2].hashCode();
            }
        }
        if (this.mChains != null) {
            return ((result << 4) | (result >>> 28)) ^ this.mChains.hashCode();
        }
        return result;
    }

    public boolean diff(WorkSource other) {
        int N = this.mNum;
        if (N != other.mNum) {
            return true;
        }
        int[] uids1 = this.mUids;
        int[] uids2 = other.mUids;
        String[] names1 = this.mNames;
        String[] names2 = other.mNames;
        for (int i = 0; i < N; i++) {
            if (uids1[i] != uids2[i]) {
                return true;
            }
            if (names1 != null && names2 != null && !names1[i].equals(names2[i])) {
                return true;
            }
        }
        return false;
    }

    public void set(WorkSource other) {
        if (other == null) {
            clear();
            return;
        }
        this.mNum = other.mNum;
        if (this.mUids.length >= this.mNum) {
            System.arraycopy(other.mUids, 0, this.mUids, 0, this.mNum);
        } else {
            this.mUids = (int[]) other.mUids.clone();
        }
        if (other.mNames != null) {
            if (this.mNames != null && this.mNames.length >= this.mNum) {
                System.arraycopy(other.mNames, 0, this.mNames, 0, this.mNum);
            } else {
                this.mNames = (String[]) other.mNames.clone();
            }
        } else {
            this.mNames = null;
        }
        if (other.mChains != null) {
            if (this.mChains != null) {
                this.mChains.clear();
            } else {
                this.mChains = new ArrayList<>(other.mChains.size());
            }
            Iterator<WorkChain> it = other.mChains.iterator();
            while (it.hasNext()) {
                WorkChain chain = it.next();
                this.mChains.add(new WorkChain(chain));
            }
        }
    }

    public void set(int uid) {
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
        }
        this.mUids[0] = uid;
        this.mNames = null;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    public void set(int uid, String name) {
        if (name == null) {
            throw new NullPointerException("Name can't be null");
        }
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
            this.mNames = new String[2];
        }
        this.mUids[0] = uid;
        this.mNames[0] = name;
        if (this.mChains != null) {
            this.mChains.clear();
        }
    }

    @Deprecated
    public WorkSource[] setReturningDiffs(WorkSource other) {
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            sGoneWork = null;
            updateLocked(other, true, true);
            if (sNewbWork == null && sGoneWork == null) {
                return null;
            }
            WorkSource[] diffs = {sNewbWork, sGoneWork};
            return diffs;
        }
    }

    public boolean add(WorkSource other) {
        boolean z;
        synchronized (sTmpWorkSource) {
            boolean uidAdded = updateLocked(other, false, false);
            boolean chainAdded = false;
            if (other.mChains != null) {
                if (this.mChains == null) {
                    this.mChains = new ArrayList<>(other.mChains.size());
                }
                Iterator<WorkChain> it = other.mChains.iterator();
                while (it.hasNext()) {
                    WorkChain wc = it.next();
                    if (!this.mChains.contains(wc)) {
                        this.mChains.add(new WorkChain(wc));
                        chainAdded = true;
                    }
                }
            }
            z = uidAdded || chainAdded;
        }
        return z;
    }

    @SystemApi
    public WorkSource withoutNames() {
        WorkSource copy = new WorkSource(this);
        copy.clearNames();
        return copy;
    }

    @Deprecated
    public WorkSource addReturningNewbs(WorkSource other) {
        WorkSource workSource;
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            updateLocked(other, false, true);
            workSource = sNewbWork;
        }
        return workSource;
    }

    public boolean add(int uid) {
        if (this.mNum <= 0) {
            this.mNames = null;
            insert(0, uid);
            return true;
        }
        if (this.mNames != null) {
            throw new IllegalArgumentException("Adding without name to named " + this);
        }
        int i = Arrays.binarySearch(this.mUids, 0, this.mNum, uid);
        if (i >= 0) {
            return false;
        }
        insert((-i) - 1, uid);
        return true;
    }

    public boolean add(int uid, String name) {
        if (this.mNum <= 0) {
            insert(0, uid, name);
            return true;
        }
        if (this.mNames == null) {
            throw new IllegalArgumentException("Adding name to unnamed " + this);
        }
        int i = 0;
        while (i < this.mNum && this.mUids[i] <= uid) {
            if (this.mUids[i] == uid) {
                int diff = this.mNames[i].compareTo(name);
                if (diff > 0) {
                    break;
                }
                if (diff == 0) {
                    return false;
                }
            }
            i++;
        }
        insert(i, uid, name);
        return true;
    }

    public boolean remove(WorkSource other) {
        boolean uidRemoved;
        if (isEmpty() || other.isEmpty()) {
            return false;
        }
        if (this.mNames == null && other.mNames == null) {
            uidRemoved = removeUids(other);
        } else {
            if (this.mNames == null) {
                throw new IllegalArgumentException(
                        "Other " + other + " has names, but target " + this + " does not");
            }
            if (other.mNames == null) {
                throw new IllegalArgumentException(
                        "Target " + this + " has names, but other " + other + " does not");
            }
            uidRemoved = removeUidsAndNames(other);
        }
        boolean chainRemoved = false;
        if (other.mChains != null && this.mChains != null) {
            chainRemoved = this.mChains.removeAll(other.mChains);
        }
        return uidRemoved || chainRemoved;
    }

    @SystemApi
    public WorkChain createWorkChain() {
        if (this.mChains == null) {
            this.mChains = new ArrayList<>(4);
        }
        WorkChain wc = new WorkChain();
        this.mChains.add(wc);
        return wc;
    }

    @SystemApi
    public boolean isEmpty() {
        return this.mNum == 0 && (this.mChains == null || this.mChains.isEmpty());
    }

    @SystemApi
    public List<WorkChain> getWorkChains() {
        return this.mChains;
    }

    public void transferWorkChains(WorkSource other) {
        if (this.mChains != null) {
            this.mChains.clear();
        }
        if (other.mChains == null || other.mChains.isEmpty()) {
            return;
        }
        if (this.mChains == null) {
            this.mChains = new ArrayList<>(4);
        }
        this.mChains.addAll(other.mChains);
        other.mChains.clear();
    }

    private boolean removeUids(WorkSource other) {
        int N1 = this.mNum;
        int[] uids1 = this.mUids;
        int N2 = other.mNum;
        int[] uids2 = other.mUids;
        boolean changed = false;
        int i1 = 0;
        int i2 = 0;
        while (i1 < N1 && i2 < N2) {
            if (uids2[i2] == uids1[i1]) {
                N1--;
                changed = true;
                if (i1 < N1) {
                    System.arraycopy(uids1, i1 + 1, uids1, i1, N1 - i1);
                }
                i2++;
            } else if (uids2[i2] > uids1[i1]) {
                i1++;
            } else {
                i2++;
            }
        }
        this.mNum = N1;
        return changed;
    }

    private boolean removeUidsAndNames(WorkSource other) {
        int N1 = this.mNum;
        int[] uids1 = this.mUids;
        String[] names1 = this.mNames;
        int N2 = other.mNum;
        int[] uids2 = other.mUids;
        String[] names2 = other.mNames;
        boolean changed = false;
        int i1 = 0;
        int i2 = 0;
        while (i1 < N1 && i2 < N2) {
            if (uids2[i2] == uids1[i1] && names2[i2].equals(names1[i1])) {
                N1--;
                changed = true;
                if (i1 < N1) {
                    System.arraycopy(uids1, i1 + 1, uids1, i1, N1 - i1);
                    System.arraycopy(names1, i1 + 1, names1, i1, N1 - i1);
                }
                i2++;
            } else if (uids2[i2] > uids1[i1]
                    || (uids2[i2] == uids1[i1] && names2[i2].compareTo(names1[i1]) > 0)) {
                i1++;
            } else {
                i2++;
            }
        }
        this.mNum = N1;
        return changed;
    }

    private boolean updateLocked(WorkSource other, boolean set, boolean returnNewbs) {
        if (this.mNames == null && other.mNames == null) {
            return updateUidsLocked(other, set, returnNewbs);
        }
        if (this.mNum > 0 && this.mNames == null) {
            throw new IllegalArgumentException(
                    "Other " + other + " has names, but target " + this + " does not");
        }
        if (other.mNum > 0 && other.mNames == null) {
            throw new IllegalArgumentException(
                    "Target " + this + " has names, but other " + other + " does not");
        }
        return updateUidsAndNamesLocked(other, set, returnNewbs);
    }

    private static WorkSource addWork(WorkSource cur, int newUid) {
        if (cur == null) {
            return new WorkSource(newUid);
        }
        cur.insert(cur.mNum, newUid);
        return cur;
    }

    private boolean updateUidsLocked(WorkSource other, boolean set, boolean returnNewbs) {
        int N1 = this.mNum;
        int[] uids1 = this.mUids;
        int N2 = other.mNum;
        int[] uids2 = other.mUids;
        boolean changed = false;
        int i1 = 0;
        int i2 = 0;
        while (true) {
            if (i1 < N1 || i2 < N2) {
                if (i1 >= N1 || (i2 < N2 && uids2[i2] < uids1[i1])) {
                    changed = true;
                    if (uids1.length == 0) {
                        uids1 = new int[4];
                        uids1[0] = uids2[i2];
                    } else if (N1 >= uids1.length) {
                        int[] newuids = new int[(uids1.length * 3) / 2];
                        if (i1 > 0) {
                            System.arraycopy(uids1, 0, newuids, 0, i1);
                        }
                        if (i1 < N1) {
                            System.arraycopy(uids1, i1, newuids, i1 + 1, N1 - i1);
                        }
                        uids1 = newuids;
                        uids1[i1] = uids2[i2];
                    } else {
                        if (i1 < N1) {
                            System.arraycopy(uids1, i1, uids1, i1 + 1, N1 - i1);
                        }
                        uids1[i1] = uids2[i2];
                    }
                    if (returnNewbs) {
                        sNewbWork = addWork(sNewbWork, uids2[i2]);
                    }
                    N1++;
                    i1++;
                    i2++;
                } else if (!set) {
                    if (i2 < N2 && uids2[i2] == uids1[i1]) {
                        i2++;
                    }
                    i1++;
                } else {
                    int start = i1;
                    while (i1 < N1 && (i2 >= N2 || uids2[i2] > uids1[i1])) {
                        sGoneWork = addWork(sGoneWork, uids1[i1]);
                        i1++;
                    }
                    if (start < i1) {
                        System.arraycopy(uids1, i1, uids1, start, N1 - i1);
                        N1 -= i1 - start;
                        i1 = start;
                    }
                    if (i1 < N1 && i2 < N2 && uids2[i2] == uids1[i1]) {
                        i1++;
                        i2++;
                    }
                }
            } else {
                this.mNum = N1;
                this.mUids = uids1;
                return changed;
            }
        }
    }

    private int compare(WorkSource other, int i1, int i2) {
        int diff = this.mUids[i1] - other.mUids[i2];
        if (diff != 0) {
            return diff;
        }
        return this.mNames[i1].compareTo(other.mNames[i2]);
    }

    private static WorkSource addWork(WorkSource cur, int newUid, String newName) {
        if (cur == null) {
            return new WorkSource(newUid, newName);
        }
        cur.insert(cur.mNum, newUid, newName);
        return cur;
    }

    private boolean updateUidsAndNamesLocked(WorkSource other, boolean set, boolean returnNewbs) {
        int N2 = other.mNum;
        int[] uids2 = other.mUids;
        String[] names2 = other.mNames;
        boolean changed = false;
        int i1 = 0;
        int i2 = 0;
        while (true) {
            if (i1 < this.mNum || i2 < N2) {
                int diff = -1;
                if (i1 < this.mNum) {
                    if (i2 < N2) {
                        int compare = compare(other, i1, i2);
                        diff = compare;
                        if (compare > 0) {}
                    }
                    if (!set) {
                        if (i2 < N2 && diff == 0) {
                            i2++;
                        }
                        i1++;
                    } else {
                        int start = i1;
                        while (diff < 0) {
                            sGoneWork = addWork(sGoneWork, this.mUids[i1], this.mNames[i1]);
                            i1++;
                            if (i1 >= this.mNum) {
                                break;
                            }
                            diff = i2 < N2 ? compare(other, i1, i2) : -1;
                        }
                        if (start < i1) {
                            System.arraycopy(this.mUids, i1, this.mUids, start, this.mNum - i1);
                            System.arraycopy(this.mNames, i1, this.mNames, start, this.mNum - i1);
                            this.mNum -= i1 - start;
                            i1 = start;
                        }
                        if (i1 < this.mNum && diff == 0) {
                            i1++;
                            i2++;
                        }
                    }
                }
                changed = true;
                insert(i1, uids2[i2], names2[i2]);
                if (returnNewbs) {
                    sNewbWork = addWork(sNewbWork, uids2[i2], names2[i2]);
                }
                i1++;
                i2++;
            } else {
                return changed;
            }
        }
    }

    private void insert(int index, int uid) {
        if (this.mUids.length == 0) {
            this.mUids = new int[4];
            this.mUids[0] = uid;
            this.mNum = 1;
        } else {
            if (this.mNum >= this.mUids.length) {
                int[] newuids = new int[(this.mNum * 3) / 2];
                if (index > 0) {
                    System.arraycopy(this.mUids, 0, newuids, 0, index);
                }
                if (index < this.mNum) {
                    System.arraycopy(this.mUids, index, newuids, index + 1, this.mNum - index);
                }
                this.mUids = newuids;
                this.mUids[index] = uid;
                this.mNum++;
                return;
            }
            if (index < this.mNum) {
                System.arraycopy(this.mUids, index, this.mUids, index + 1, this.mNum - index);
            }
            this.mUids[index] = uid;
            this.mNum++;
        }
    }

    private void insert(int index, int uid, String name) {
        if (this.mNum == 0) {
            this.mUids = new int[4];
            this.mUids[0] = uid;
            this.mNames = new String[4];
            this.mNames[0] = name;
            this.mNum = 1;
            return;
        }
        if (this.mNum >= this.mUids.length) {
            int[] newuids = new int[(this.mNum * 3) / 2];
            String[] newnames = new String[(this.mNum * 3) / 2];
            if (index > 0) {
                System.arraycopy(this.mUids, 0, newuids, 0, index);
                System.arraycopy(this.mNames, 0, newnames, 0, index);
            }
            if (index < this.mNum) {
                System.arraycopy(this.mUids, index, newuids, index + 1, this.mNum - index);
                System.arraycopy(this.mNames, index, newnames, index + 1, this.mNum - index);
            }
            this.mUids = newuids;
            this.mNames = newnames;
            this.mUids[index] = uid;
            this.mNames[index] = name;
            this.mNum++;
            return;
        }
        if (index < this.mNum) {
            System.arraycopy(this.mUids, index, this.mUids, index + 1, this.mNum - index);
            System.arraycopy(this.mNames, index, this.mNames, index + 1, this.mNum - index);
        }
        this.mUids[index] = uid;
        this.mNames[index] = name;
        this.mNum++;
    }

    @SystemApi
    public static final class WorkChain implements Parcelable {
        public static final Parcelable.Creator<WorkChain> CREATOR =
                new Parcelable.Creator<
                        WorkChain>() { // from class: android.os.WorkSource.WorkChain.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public WorkChain createFromParcel(Parcel in) {
                        return new WorkChain(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public WorkChain[] newArray(int size) {
                        return new WorkChain[size];
                    }
                };
        private int mSize;
        private String[] mTags;
        private int[] mUids;

        public WorkChain() {
            this.mSize = 0;
            this.mUids = new int[4];
            this.mTags = new String[4];
        }

        public WorkChain(WorkChain other) {
            this.mSize = other.mSize;
            this.mUids = (int[]) other.mUids.clone();
            this.mTags = (String[]) other.mTags.clone();
        }

        private WorkChain(Parcel in) {
            this.mSize = in.readInt();
            this.mUids = in.createIntArray();
            this.mTags = in.createStringArray();
        }

        public WorkChain addNode(int uid, String tag) {
            if (this.mSize == this.mUids.length) {
                resizeArrays();
            }
            this.mUids[this.mSize] = uid;
            this.mTags[this.mSize] = tag;
            this.mSize++;
            return this;
        }

        public int getAttributionUid() {
            if (this.mSize > 0) {
                return this.mUids[0];
            }
            return -1;
        }

        public String getAttributionTag() {
            if (this.mTags.length > 0) {
                return this.mTags[0];
            }
            return null;
        }

        public int[] getUids() {
            int[] uids = new int[this.mSize];
            System.arraycopy(this.mUids, 0, uids, 0, this.mSize);
            return uids;
        }

        public String[] getTags() {
            String[] tags = new String[this.mSize];
            System.arraycopy(this.mTags, 0, tags, 0, this.mSize);
            return tags;
        }

        public int getSize() {
            return this.mSize;
        }

        private void resizeArrays() {
            int newSize = this.mSize * 2;
            int[] uids = new int[newSize];
            String[] tags = new String[newSize];
            System.arraycopy(this.mUids, 0, uids, 0, this.mSize);
            System.arraycopy(this.mTags, 0, tags, 0, this.mSize);
            this.mUids = uids;
            this.mTags = tags;
        }

        public String toString() {
            StringBuilder result = new StringBuilder("WorkChain{");
            for (int i = 0; i < this.mSize; i++) {
                if (i != 0) {
                    result.append(", ");
                }
                result.append(NavigationBarInflaterView.KEY_CODE_START);
                result.append(this.mUids[i]);
                if (this.mTags[i] != null) {
                    result.append(", ");
                    result.append(this.mTags[i]);
                }
                result.append(NavigationBarInflaterView.KEY_CODE_END);
            }
            result.append("}");
            return result.toString();
        }

        public int hashCode() {
            return ((this.mSize + (Arrays.hashCode(this.mUids) * 31)) * 31)
                    + Arrays.hashCode(this.mTags);
        }

        public boolean equals(Object o) {
            if (!(o instanceof WorkChain)) {
                return false;
            }
            WorkChain other = (WorkChain) o;
            return this.mSize == other.mSize
                    && Arrays.equals(this.mUids, other.mUids)
                    && Arrays.equals(this.mTags, other.mTags);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mSize);
            dest.writeIntArray(this.mUids);
            dest.writeStringArray(this.mTags);
        }
    }

    public static ArrayList<WorkChain>[] diffChains(WorkSource oldWs, WorkSource newWs) {
        ArrayList<WorkChain> newChains = null;
        ArrayList<WorkChain> goneChains = null;
        if (oldWs.mChains != null) {
            for (int i = 0; i < oldWs.mChains.size(); i++) {
                WorkChain wc = oldWs.mChains.get(i);
                if (newWs.mChains == null || !newWs.mChains.contains(wc)) {
                    if (goneChains == null) {
                        goneChains = new ArrayList<>(oldWs.mChains.size());
                    }
                    goneChains.add(wc);
                }
            }
        }
        if (newWs.mChains != null) {
            for (int i2 = 0; i2 < newWs.mChains.size(); i2++) {
                WorkChain wc2 = newWs.mChains.get(i2);
                if (oldWs.mChains == null || !oldWs.mChains.contains(wc2)) {
                    if (newChains == null) {
                        newChains = new ArrayList<>(newWs.mChains.size());
                    }
                    newChains.add(wc2);
                }
            }
        }
        if (newChains != null || goneChains != null) {
            return new ArrayList[] {newChains, goneChains};
        }
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mNum);
        dest.writeIntArray(this.mUids);
        dest.writeStringArray(this.mNames);
        if (this.mChains == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(this.mChains.size());
            dest.writeParcelableList(this.mChains, flags);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("WorkSource{");
        for (int i = 0; i < this.mNum; i++) {
            if (i != 0) {
                result.append(", ");
            }
            result.append(this.mUids[i]);
            if (this.mNames != null) {
                result.append(" ");
                result.append(this.mNames[i]);
            }
        }
        if (this.mChains != null) {
            result.append(" chains=");
            for (int i2 = 0; i2 < this.mChains.size(); i2++) {
                if (i2 != 0) {
                    result.append(", ");
                }
                result.append(this.mChains.get(i2));
            }
        }
        result.append("}");
        return result.toString();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long j;
        long workSourceToken = proto.start(fieldId);
        int i = 0;
        while (true) {
            j = 2246267895809L;
            if (i >= this.mNum) {
                break;
            }
            long contentProto = proto.start(2246267895809L);
            proto.write(1120986464257L, this.mUids[i]);
            if (this.mNames != null) {
                proto.write(1138166333442L, this.mNames[i]);
            }
            proto.end(contentProto);
            i++;
        }
        if (this.mChains != null) {
            int i2 = 0;
            while (i2 < this.mChains.size()) {
                WorkChain wc = this.mChains.get(i2);
                long workChain = proto.start(2246267895810L);
                String[] tags = wc.getTags();
                int[] uids = wc.getUids();
                int j2 = 0;
                while (j2 < tags.length) {
                    long contentProto2 = proto.start(j);
                    proto.write(1120986464257L, uids[j2]);
                    proto.write(1138166333442L, tags[j2]);
                    proto.end(contentProto2);
                    j2++;
                    j = 2246267895809L;
                }
                proto.end(workChain);
                i2++;
                j = 2246267895809L;
            }
        }
        proto.end(workSourceToken);
    }
}
