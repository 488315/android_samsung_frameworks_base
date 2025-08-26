package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
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
    public static final Parcelable.Creator<WorkSource> CREATOR = new Parcelable.Creator<WorkSource>() { // from class: android.os.WorkSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkSource createFromParcel(Parcel parcel) {
            return new WorkSource(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkSource[] newArray(int i) {
            return new WorkSource[i];
        }
    };

    public static boolean isChainedBatteryAttributionEnabled$ravenwood(Context context) {
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WorkSource() {
        this.mUids = new int[0];
        this.mNum = 0;
        this.mChains = null;
    }

    public WorkSource(WorkSource workSource) {
        this.mUids = new int[0];
        if (workSource == null) {
            this.mNum = 0;
            this.mChains = null;
            return;
        }
        this.mNum = workSource.mNum;
        this.mUids = (int[]) workSource.mUids.clone();
        String[] strArr = workSource.mNames;
        this.mNames = strArr != null ? (String[]) strArr.clone() : null;
        if (workSource.mChains != null) {
            this.mChains = new ArrayList<>(workSource.mChains.size());
            Iterator<WorkChain> it = workSource.mChains.iterator();
            while (it.hasNext()) {
                this.mChains.add(new WorkChain(it.next()));
            }
            return;
        }
        this.mChains = null;
    }

    @SystemApi
    public WorkSource(int i) {
        this.mNum = 1;
        this.mUids = new int[]{i, 0};
        this.mNames = null;
        this.mChains = null;
    }

    @SystemApi
    public WorkSource(int i, String str) {
        this.mUids = new int[0];
        Objects.requireNonNull(str, "packageName can't be null");
        this.mNum = 1;
        this.mUids = new int[]{i, 0};
        this.mNames = new String[]{str, null};
        this.mChains = null;
    }

    WorkSource(Parcel parcel) {
        this.mUids = new int[0];
        this.mNum = parcel.readInt();
        this.mUids = (int[]) Objects.requireNonNullElse(parcel.createIntArray(), new int[0]);
        this.mNames = parcel.createStringArray();
        int i = parcel.readInt();
        if (i >= 0) {
            ArrayList<WorkChain> arrayList = new ArrayList<>(i);
            this.mChains = arrayList;
            parcel.readParcelableList(arrayList, WorkChain.class.getClassLoader(), WorkChain.class);
            return;
        }
        this.mChains = null;
    }

    public static boolean isChainedBatteryAttributionEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.CHAINED_BATTERY_ATTRIBUTION_ENABLED, 0) == 1;
    }

    @SystemApi
    public int size() {
        return this.mNum;
    }

    @Deprecated
    public int get(int i) {
        return getUid(i);
    }

    @SystemApi
    public int getUid(int i) {
        return this.mUids[i];
    }

    public int getAttributionUid() {
        if (isEmpty()) {
            return -1;
        }
        return this.mNum > 0 ? this.mUids[0] : this.mChains.get(0).getAttributionUid();
    }

    @Deprecated
    public String getName(int i) {
        return getPackageName(i);
    }

    @SystemApi
    public String getPackageName(int i) {
        String[] strArr = this.mNames;
        if (strArr != null) {
            return strArr[i];
        }
        return null;
    }

    private void clearNames() {
        if (this.mNames != null) {
            this.mNames = null;
            int i = this.mNum;
            int i2 = 1;
            for (int i3 = 1; i3 < this.mNum; i3++) {
                int[] iArr = this.mUids;
                int i4 = iArr[i3];
                if (i4 == iArr[i3 - 1]) {
                    i--;
                } else {
                    iArr[i2] = i4;
                    i2++;
                }
            }
            this.mNum = i;
        }
    }

    public void clear() {
        this.mNum = 0;
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof WorkSource)) {
            return false;
        }
        WorkSource workSource = (WorkSource) obj;
        if (diff(workSource)) {
            return false;
        }
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null && !arrayList.isEmpty()) {
            return this.mChains.equals(workSource.mChains);
        }
        ArrayList<WorkChain> arrayList2 = workSource.mChains;
        return arrayList2 == null || arrayList2.isEmpty();
    }

    public int hashCode() {
        int iHashCode = 0;
        for (int i = 0; i < this.mNum; i++) {
            iHashCode = ((iHashCode >>> 28) | (iHashCode << 4)) ^ this.mUids[i];
        }
        if (this.mNames != null) {
            for (int i2 = 0; i2 < this.mNum; i2++) {
                iHashCode = this.mNames[i2].hashCode() ^ ((iHashCode << 4) | (iHashCode >>> 28));
            }
        }
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList == null) {
            return iHashCode;
        }
        return arrayList.hashCode() ^ ((iHashCode << 4) | (iHashCode >>> 28));
    }

    public boolean diff(WorkSource workSource) {
        int i = this.mNum;
        if (i != workSource.mNum) {
            return true;
        }
        int[] iArr = this.mUids;
        int[] iArr2 = workSource.mUids;
        String[] strArr = this.mNames;
        String[] strArr2 = workSource.mNames;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return true;
            }
            if (strArr != null && strArr2 != null && !strArr[i2].equals(strArr2[i2])) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void set(WorkSource workSource) {
        if (workSource == null) {
            clear();
            return;
        }
        int i = workSource.mNum;
        this.mNum = i;
        int[] iArr = this.mUids;
        if (iArr.length >= i) {
            System.arraycopy(workSource.mUids, 0, iArr, 0, i);
        } else {
            this.mUids = (int[]) workSource.mUids.clone();
        }
        String[] strArr = workSource.mNames;
        if (strArr != null) {
            String[] strArr2 = this.mNames;
            if (strArr2 != null) {
                int length = strArr2.length;
                int i2 = this.mNum;
                if (length >= i2) {
                    System.arraycopy(strArr, 0, strArr2, 0, i2);
                } else {
                    this.mNames = (String[]) strArr.clone();
                }
            }
        } else {
            this.mNames = null;
        }
        if (workSource.mChains != null) {
            ArrayList<WorkChain> arrayList = this.mChains;
            if (arrayList != null) {
                arrayList.clear();
            } else {
                this.mChains = new ArrayList<>(workSource.mChains.size());
            }
            Iterator<WorkChain> it = workSource.mChains.iterator();
            while (it.hasNext()) {
                this.mChains.add(new WorkChain(it.next()));
            }
        }
    }

    public void set(int i) {
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
        }
        this.mUids[0] = i;
        this.mNames = null;
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void set(int i, String str) {
        if (str == null) {
            throw new NullPointerException("Name can't be null");
        }
        this.mNum = 1;
        if (this.mUids.length == 0) {
            this.mUids = new int[2];
            this.mNames = new String[2];
        }
        this.mUids[0] = i;
        this.mNames[0] = str;
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    @Deprecated
    public WorkSource[] setReturningDiffs(WorkSource workSource) {
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            sGoneWork = null;
            updateLocked(workSource, true, true);
            WorkSource workSource2 = sNewbWork;
            if (workSource2 == null && sGoneWork == null) {
                return null;
            }
            return new WorkSource[]{workSource2, sGoneWork};
        }
    }

    public boolean add(WorkSource workSource) {
        boolean z;
        boolean z2;
        synchronized (sTmpWorkSource) {
            boolean zUpdateLocked = updateLocked(workSource, false, false);
            if (workSource.mChains != null) {
                if (this.mChains == null) {
                    this.mChains = new ArrayList<>(workSource.mChains.size());
                }
                Iterator<WorkChain> it = workSource.mChains.iterator();
                z = false;
                while (it.hasNext()) {
                    WorkChain next = it.next();
                    if (!this.mChains.contains(next)) {
                        this.mChains.add(new WorkChain(next));
                        z = true;
                    }
                }
            } else {
                z = false;
            }
            z2 = zUpdateLocked || z;
        }
        return z2;
    }

    @SystemApi
    public WorkSource withoutNames() {
        WorkSource workSource = new WorkSource(this);
        workSource.clearNames();
        return workSource;
    }

    @Deprecated
    public WorkSource addReturningNewbs(WorkSource workSource) {
        WorkSource workSource2;
        synchronized (sTmpWorkSource) {
            sNewbWork = null;
            updateLocked(workSource, false, true);
            workSource2 = sNewbWork;
        }
        return workSource2;
    }

    public boolean add(int i) {
        int i2 = this.mNum;
        if (i2 <= 0) {
            this.mNames = null;
            insert(0, i);
            return true;
        }
        if (this.mNames != null) {
            throw new IllegalArgumentException("Adding without name to named " + this);
        }
        int iBinarySearch = Arrays.binarySearch(this.mUids, 0, i2, i);
        if (iBinarySearch >= 0) {
            return false;
        }
        insert((-iBinarySearch) - 1, i);
        return true;
    }

    public boolean add(int i, String str) {
        int i2;
        if (this.mNum <= 0) {
            insert(0, i, str);
            return true;
        }
        if (this.mNames == null) {
            throw new IllegalArgumentException("Adding name to unnamed " + this);
        }
        int i3 = 0;
        while (i3 < this.mNum && (i2 = this.mUids[i3]) <= i) {
            if (i2 == i) {
                int iCompareTo = this.mNames[i3].compareTo(str);
                if (iCompareTo > 0) {
                    break;
                }
                if (iCompareTo == 0) {
                    return false;
                }
            }
            i3++;
        }
        insert(i3, i, str);
        return true;
    }

    public boolean remove(WorkSource workSource) {
        boolean zRemoveUidsAndNames;
        ArrayList<WorkChain> arrayList;
        if (isEmpty() || workSource.isEmpty()) {
            return false;
        }
        String[] strArr = this.mNames;
        if (strArr == null && workSource.mNames == null) {
            zRemoveUidsAndNames = removeUids(workSource);
        } else {
            if (strArr == null) {
                throw new IllegalArgumentException("Other " + workSource + " has names, but target " + this + " does not");
            }
            if (workSource.mNames == null) {
                throw new IllegalArgumentException("Target " + this + " has names, but other " + workSource + " does not");
            }
            zRemoveUidsAndNames = removeUidsAndNames(workSource);
        }
        ArrayList<WorkChain> arrayList2 = workSource.mChains;
        return zRemoveUidsAndNames || ((arrayList2 == null || (arrayList = this.mChains) == null) ? false : arrayList.removeAll(arrayList2));
    }

    @SystemApi
    public WorkChain createWorkChain() {
        if (this.mChains == null) {
            this.mChains = new ArrayList<>(4);
        }
        WorkChain workChain = new WorkChain();
        this.mChains.add(workChain);
        return workChain;
    }

    @SystemApi
    public boolean isEmpty() {
        if (this.mNum != 0) {
            return false;
        }
        ArrayList<WorkChain> arrayList = this.mChains;
        return arrayList == null || arrayList.isEmpty();
    }

    @SystemApi
    public List<WorkChain> getWorkChains() {
        return this.mChains;
    }

    public void transferWorkChains(WorkSource workSource) {
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList<WorkChain> arrayList2 = workSource.mChains;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            return;
        }
        if (this.mChains == null) {
            this.mChains = new ArrayList<>(4);
        }
        this.mChains.addAll(workSource.mChains);
        workSource.mChains.clear();
    }

    private boolean removeUids(WorkSource workSource) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        while (i3 < i && i4 < i2) {
            int i5 = iArr2[i4];
            int i6 = iArr[i3];
            if (i5 == i6) {
                i--;
                if (i3 < i) {
                    System.arraycopy(iArr, i3 + 1, iArr, i3, i - i3);
                }
                i4++;
                z = true;
            } else if (i5 > i6) {
                i3++;
            } else {
                i4++;
            }
        }
        this.mNum = i;
        return z;
    }

    private boolean removeUidsAndNames(WorkSource workSource) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        String[] strArr = this.mNames;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        String[] strArr2 = workSource.mNames;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        while (i3 < i && i4 < i2) {
            if (iArr2[i4] == iArr[i3] && strArr2[i4].equals(strArr[i3])) {
                i--;
                if (i3 < i) {
                    int i5 = i3 + 1;
                    int i6 = i - i3;
                    System.arraycopy(iArr, i5, iArr, i3, i6);
                    System.arraycopy(strArr, i5, strArr, i3, i6);
                }
                i4++;
                z = true;
            } else {
                int i7 = iArr2[i4];
                int i8 = iArr[i3];
                if (i7 > i8 || (i7 == i8 && strArr2[i4].compareTo(strArr[i3]) > 0)) {
                    i3++;
                } else {
                    i4++;
                }
            }
        }
        this.mNum = i;
        return z;
    }

    private boolean updateLocked(WorkSource workSource, boolean z, boolean z2) {
        String[] strArr = this.mNames;
        if (strArr == null && workSource.mNames == null) {
            return updateUidsLocked(workSource, z, z2);
        }
        if (this.mNum > 0 && strArr == null) {
            throw new IllegalArgumentException("Other " + workSource + " has names, but target " + this + " does not");
        }
        if (workSource.mNum > 0 && workSource.mNames == null) {
            throw new IllegalArgumentException("Target " + this + " has names, but other " + workSource + " does not");
        }
        return updateUidsAndNamesLocked(workSource, z, z2);
    }

    private static WorkSource addWork(WorkSource workSource, int i) {
        if (workSource == null) {
            return new WorkSource(i);
        }
        workSource.insert(workSource.mNum, i);
        return workSource;
    }

    private boolean updateUidsLocked(WorkSource workSource, boolean z, boolean z2) {
        int i = this.mNum;
        int[] iArr = this.mUids;
        int i2 = workSource.mNum;
        int[] iArr2 = workSource.mUids;
        int i3 = 0;
        int i4 = 0;
        boolean z3 = false;
        while (true) {
            if (i3 < i || i4 < i2) {
                if (i3 >= i || (i4 < i2 && iArr2[i4] < iArr[i3])) {
                    if (iArr.length == 0) {
                        iArr = new int[4];
                        iArr[0] = iArr2[i4];
                    } else if (i >= iArr.length) {
                        int[] iArr3 = new int[(iArr.length * 3) / 2];
                        if (i3 > 0) {
                            System.arraycopy(iArr, 0, iArr3, 0, i3);
                        }
                        if (i3 < i) {
                            System.arraycopy(iArr, i3, iArr3, i3 + 1, i - i3);
                        }
                        iArr3[i3] = iArr2[i4];
                        iArr = iArr3;
                    } else {
                        if (i3 < i) {
                            System.arraycopy(iArr, i3, iArr, i3 + 1, i - i3);
                        }
                        iArr[i3] = iArr2[i4];
                    }
                    if (z2) {
                        sNewbWork = addWork(sNewbWork, iArr2[i4]);
                    }
                    i++;
                    i3++;
                    i4++;
                    z3 = true;
                } else if (!z) {
                    if (i4 < i2 && iArr2[i4] == iArr[i3]) {
                        i4++;
                    }
                    i3++;
                } else {
                    int i5 = i3;
                    while (i5 < i && (i4 >= i2 || iArr2[i4] > iArr[i5])) {
                        sGoneWork = addWork(sGoneWork, iArr[i5]);
                        i5++;
                    }
                    if (i3 < i5) {
                        System.arraycopy(iArr, i5, iArr, i3, i - i5);
                        i -= i5 - i3;
                    } else {
                        i3 = i5;
                    }
                    if (i3 < i && i4 < i2 && iArr2[i4] == iArr[i3]) {
                        i3++;
                        i4++;
                    }
                }
            } else {
                this.mNum = i;
                this.mUids = iArr;
                return z3;
            }
        }
    }

    private int compare(WorkSource workSource, int i, int i2) {
        int i3 = this.mUids[i] - workSource.mUids[i2];
        return i3 != 0 ? i3 : this.mNames[i].compareTo(workSource.mNames[i2]);
    }

    private static WorkSource addWork(WorkSource workSource, int i, String str) {
        if (workSource == null) {
            return new WorkSource(i, str);
        }
        workSource.insert(workSource.mNum, i, str);
        return workSource;
    }

    private boolean updateUidsAndNamesLocked(WorkSource workSource, boolean z, boolean z2) {
        int iCompare;
        int i = workSource.mNum;
        int[] iArr = workSource.mUids;
        String[] strArr = workSource.mNames;
        int i2 = 0;
        int i3 = 0;
        boolean z3 = false;
        while (true) {
            int i4 = this.mNum;
            if (i2 >= i4 && i3 >= i) {
                return z3;
            }
            if (i2 < i4) {
                if (i3 < i) {
                    iCompare = compare(workSource, i2, i3);
                    if (iCompare > 0) {
                    }
                } else {
                    iCompare = -1;
                }
                if (z) {
                    int i5 = i2;
                    while (iCompare < 0) {
                        sGoneWork = addWork(sGoneWork, this.mUids[i5], this.mNames[i5]);
                        i5++;
                        if (i5 >= this.mNum) {
                            break;
                        }
                        iCompare = i3 < i ? compare(workSource, i5, i3) : -1;
                    }
                    if (i2 < i5) {
                        int[] iArr2 = this.mUids;
                        System.arraycopy(iArr2, i5, iArr2, i2, this.mNum - i5);
                        String[] strArr2 = this.mNames;
                        System.arraycopy(strArr2, i5, strArr2, i2, this.mNum - i5);
                        this.mNum -= i5 - i2;
                    } else {
                        i2 = i5;
                    }
                    if (i2 < this.mNum && iCompare == 0) {
                        i2++;
                        i3++;
                    }
                } else {
                    if (i3 < i && iCompare == 0) {
                        i3++;
                    }
                    i2++;
                }
            }
            insert(i2, iArr[i3], strArr[i3]);
            if (z2) {
                sNewbWork = addWork(sNewbWork, iArr[i3], strArr[i3]);
            }
            i2++;
            i3++;
            z3 = true;
        }
    }

    private void insert(int i, int i2) {
        int[] iArr = this.mUids;
        if (iArr.length == 0) {
            int[] iArr2 = new int[4];
            this.mUids = iArr2;
            iArr2[0] = i2;
            this.mNum = 1;
            return;
        }
        int i3 = this.mNum;
        if (i3 >= iArr.length) {
            int[] iArr3 = new int[(i3 * 3) / 2];
            if (i > 0) {
                System.arraycopy(iArr, 0, iArr3, 0, i);
            }
            int i4 = this.mNum;
            if (i < i4) {
                System.arraycopy(this.mUids, i, iArr3, i + 1, i4 - i);
            }
            this.mUids = iArr3;
            iArr3[i] = i2;
            this.mNum++;
            return;
        }
        if (i < i3) {
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        }
        this.mUids[i] = i2;
        this.mNum++;
    }

    private void insert(int i, int i2, String str) {
        int i3 = this.mNum;
        if (i3 == 0) {
            int[] iArr = new int[4];
            this.mUids = iArr;
            iArr[0] = i2;
            String[] strArr = new String[4];
            this.mNames = strArr;
            strArr[0] = str;
            this.mNum = 1;
            return;
        }
        int[] iArr2 = this.mUids;
        if (i3 >= iArr2.length) {
            int[] iArr3 = new int[(i3 * 3) / 2];
            String[] strArr2 = new String[(i3 * 3) / 2];
            if (i > 0) {
                System.arraycopy(iArr2, 0, iArr3, 0, i);
                System.arraycopy(this.mNames, 0, strArr2, 0, i);
            }
            int i4 = this.mNum;
            if (i < i4) {
                int i5 = i + 1;
                System.arraycopy(this.mUids, i, iArr3, i5, i4 - i);
                System.arraycopy(this.mNames, i, strArr2, i5, this.mNum - i);
            }
            this.mUids = iArr3;
            this.mNames = strArr2;
            iArr3[i] = i2;
            strArr2[i] = str;
            this.mNum++;
            return;
        }
        if (i < i3) {
            int i6 = i + 1;
            System.arraycopy(iArr2, i, iArr2, i6, i3 - i);
            String[] strArr3 = this.mNames;
            System.arraycopy(strArr3, i, strArr3, i6, this.mNum - i);
        }
        this.mUids[i] = i2;
        this.mNames[i] = str;
        this.mNum++;
    }

    @SystemApi
    public static final class WorkChain implements Parcelable {
        public static final Parcelable.Creator<WorkChain> CREATOR = new Parcelable.Creator<WorkChain>() { // from class: android.os.WorkSource.WorkChain.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WorkChain createFromParcel(Parcel parcel) {
                return new WorkChain(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WorkChain[] newArray(int i) {
                return new WorkChain[i];
            }
        };
        private int mSize;
        private String[] mTags;
        private int[] mUids;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public WorkChain() {
            this.mSize = 0;
            this.mUids = new int[4];
            this.mTags = new String[4];
        }

        public WorkChain(WorkChain workChain) {
            this.mSize = workChain.mSize;
            this.mUids = (int[]) workChain.mUids.clone();
            this.mTags = (String[]) workChain.mTags.clone();
        }

        private WorkChain(Parcel parcel) {
            this.mSize = parcel.readInt();
            this.mUids = parcel.createIntArray();
            this.mTags = parcel.createStringArray();
        }

        public WorkChain addNode(int i, String str) {
            if (this.mSize == this.mUids.length) {
                resizeArrays();
            }
            int[] iArr = this.mUids;
            int i2 = this.mSize;
            iArr[i2] = i;
            this.mTags[i2] = str;
            this.mSize = i2 + 1;
            return this;
        }

        public int getAttributionUid() {
            if (this.mSize > 0) {
                return this.mUids[0];
            }
            return -1;
        }

        public String getAttributionTag() {
            String[] strArr = this.mTags;
            if (strArr.length > 0) {
                return strArr[0];
            }
            return null;
        }

        public int[] getUids() {
            int i = this.mSize;
            int[] iArr = new int[i];
            System.arraycopy(this.mUids, 0, iArr, 0, i);
            return iArr;
        }

        public String[] getTags() {
            int i = this.mSize;
            String[] strArr = new String[i];
            System.arraycopy(this.mTags, 0, strArr, 0, i);
            return strArr;
        }

        public int getSize() {
            return this.mSize;
        }

        private void resizeArrays() {
            int i = this.mSize;
            int i2 = i * 2;
            int[] iArr = new int[i2];
            String[] strArr = new String[i2];
            System.arraycopy(this.mUids, 0, iArr, 0, i);
            System.arraycopy(this.mTags, 0, strArr, 0, this.mSize);
            this.mUids = iArr;
            this.mTags = strArr;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("WorkChain{");
            for (int i = 0; i < this.mSize; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(this.mUids[i]);
                if (this.mTags[i] != null) {
                    sb.append(", ");
                    sb.append(this.mTags[i]);
                }
                sb.append(NavigationBarInflaterView.KEY_CODE_END);
            }
            sb.append("}");
            return sb.toString();
        }

        public int hashCode() {
            return ((this.mSize + (Arrays.hashCode(this.mUids) * 31)) * 31) + Arrays.hashCode(this.mTags);
        }

        public boolean equals(Object obj) {
            if (obj instanceof WorkChain) {
                WorkChain workChain = (WorkChain) obj;
                if (this.mSize == workChain.mSize && Arrays.equals(this.mUids, workChain.mUids) && Arrays.equals(this.mTags, workChain.mTags)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mSize);
            parcel.writeIntArray(this.mUids);
            parcel.writeStringArray(this.mTags);
        }
    }

    public static ArrayList<WorkChain>[] diffChains(WorkSource workSource, WorkSource workSource2) {
        ArrayList<WorkChain> arrayList;
        ArrayList<WorkChain> arrayList2;
        if (workSource.mChains != null) {
            arrayList = null;
            for (int i = 0; i < workSource.mChains.size(); i++) {
                WorkChain workChain = workSource.mChains.get(i);
                ArrayList<WorkChain> arrayList3 = workSource2.mChains;
                if (arrayList3 == null || !arrayList3.contains(workChain)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>(workSource.mChains.size());
                    }
                    arrayList.add(workChain);
                }
            }
        } else {
            arrayList = null;
        }
        if (workSource2.mChains != null) {
            arrayList2 = null;
            for (int i2 = 0; i2 < workSource2.mChains.size(); i2++) {
                WorkChain workChain2 = workSource2.mChains.get(i2);
                ArrayList<WorkChain> arrayList4 = workSource.mChains;
                if (arrayList4 == null || !arrayList4.contains(workChain2)) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>(workSource2.mChains.size());
                    }
                    arrayList2.add(workChain2);
                }
            }
        } else {
            arrayList2 = null;
        }
        if (arrayList2 == null && arrayList == null) {
            return null;
        }
        return new ArrayList[]{arrayList2, arrayList};
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mNum);
        parcel.writeIntArray(this.mUids);
        parcel.writeStringArray(this.mNames);
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(arrayList.size());
            parcel.writeParcelableList(this.mChains, i);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WorkSource{");
        for (int i = 0; i < this.mNum; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mUids[i]);
            if (this.mNames != null) {
                sb.append(" ");
                sb.append(this.mNames[i]);
            }
        }
        if (this.mChains != null) {
            sb.append(" chains=");
            for (int i2 = 0; i2 < this.mChains.size(); i2++) {
                if (i2 != 0) {
                    sb.append(", ");
                }
                sb.append(this.mChains.get(i2));
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long j2;
        long j3;
        long jStart = protoOutputStream.start(j);
        int i = 0;
        while (true) {
            j2 = 1120986464257L;
            j3 = 2246267895809L;
            if (i >= this.mNum) {
                break;
            }
            long jStart2 = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, this.mUids[i]);
            String[] strArr = this.mNames;
            if (strArr != null) {
                protoOutputStream.write(1138166333442L, strArr[i]);
            }
            protoOutputStream.end(jStart2);
            i++;
        }
        if (this.mChains != null) {
            int i2 = 0;
            while (i2 < this.mChains.size()) {
                WorkChain workChain = this.mChains.get(i2);
                long jStart3 = protoOutputStream.start(2246267895810L);
                String[] tags = workChain.getTags();
                int[] uids = workChain.getUids();
                int i3 = 0;
                while (i3 < tags.length) {
                    long jStart4 = protoOutputStream.start(j3);
                    protoOutputStream.write(j2, uids[i3]);
                    protoOutputStream.write(1138166333442L, tags[i3]);
                    protoOutputStream.end(jStart4);
                    i3++;
                    j2 = 1120986464257L;
                    j3 = 2246267895809L;
                }
                protoOutputStream.end(jStart3);
                i2++;
                j2 = 1120986464257L;
                j3 = 2246267895809L;
            }
        }
        protoOutputStream.end(jStart);
    }
}
