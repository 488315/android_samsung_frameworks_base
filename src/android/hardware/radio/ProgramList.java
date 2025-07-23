package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public final class ProgramList implements AutoCloseable {
    private boolean mIsClosed;
    private boolean mIsComplete;
    private OnCloseListener mOnCloseListener;
    private final Object mLock = new Object();
    private final ArrayMap<ProgramSelector.Identifier, ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo>> mPrograms = new ArrayMap<>();
    private final List<ListCallback> mListCallbacks = new ArrayList();
    private final List<OnCompleteListener> mOnCompleteListeners = new ArrayList();

    public static abstract class ListCallback {
        public void onItemChanged(ProgramSelector.Identifier identifier) {
        }

        public void onItemRemoved(ProgramSelector.Identifier identifier) {
        }
    }

    interface OnCloseListener {
        void onClose();
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    ProgramList() {
    }

    /* renamed from: android.hardware.radio.ProgramList$1, reason: invalid class name */
    class AnonymousClass1 extends ListCallback {
        final /* synthetic */ ListCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(ProgramList programList, Executor executor, ListCallback listCallback) {
            this.val$executor = executor;
            this.val$callback = listCallback;
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemChanged(final ProgramSelector.Identifier identifier) {
            Executor executor = this.val$executor;
            final ListCallback listCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ProgramList.ListCallback.this.onItemChanged(identifier);
                }
            });
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemRemoved(final ProgramSelector.Identifier identifier) {
            Executor executor = this.val$executor;
            final ListCallback listCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ProgramList.ListCallback.this.onItemRemoved(identifier);
                }
            });
        }
    }

    public void registerListCallback(Executor executor, ListCallback listCallback) {
        registerListCallback(new AnonymousClass1(this, executor, listCallback));
    }

    public void registerListCallback(ListCallback listCallback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.add((ListCallback) Objects.requireNonNull(listCallback));
        }
    }

    public void unregisterListCallback(ListCallback listCallback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.remove(Objects.requireNonNull(listCallback));
        }
    }

    static /* synthetic */ void lambda$addOnCompleteListener$0(Executor executor, final OnCompleteListener onCompleteListener) {
        Objects.requireNonNull(onCompleteListener);
        executor.execute(new Runnable() { // from class: android.hardware.radio.ProgramList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProgramList.OnCompleteListener.this.onComplete();
            }
        });
    }

    public void addOnCompleteListener(final Executor executor, final OnCompleteListener onCompleteListener) {
        addOnCompleteListener(new OnCompleteListener() { // from class: android.hardware.radio.ProgramList$$ExternalSyntheticLambda1
            @Override // android.hardware.radio.ProgramList.OnCompleteListener
            public final void onComplete() {
                ProgramList.lambda$addOnCompleteListener$0(executor, onCompleteListener);
            }
        });
    }

    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.add((OnCompleteListener) Objects.requireNonNull(onCompleteListener));
            if (this.mIsComplete) {
                onCompleteListener.onComplete();
            }
        }
    }

    public void removeOnCompleteListener(OnCompleteListener onCompleteListener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.remove(Objects.requireNonNull(onCompleteListener));
        }
    }

    void setOnCloseListener(OnCloseListener onCloseListener) {
        synchronized (this.mLock) {
            if (this.mOnCloseListener != null) {
                throw new IllegalStateException("Close callback is already set");
            }
            this.mOnCloseListener = onCloseListener;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsClosed = true;
            this.mPrograms.clear();
            this.mListCallbacks.clear();
            this.mOnCompleteListeners.clear();
            OnCloseListener onCloseListener = this.mOnCloseListener;
            if (onCloseListener != null) {
                this.mOnCloseListener = null;
            } else {
                onCloseListener = null;
            }
            if (onCloseListener != null) {
                onCloseListener.onClose();
            }
        }
    }

    void apply(Chunk chunk) {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList2 = new ArrayList();
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsComplete = false;
            ArrayList arrayList3 = new ArrayList(this.mListCallbacks);
            if (chunk.isPurge()) {
                Iterator<Map.Entry<ProgramSelector.Identifier, ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo>>> it = this.mPrograms.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<ProgramSelector.Identifier, ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo>> next = it.next();
                    if (next.getValue() != null) {
                        arrayList.add(next.getKey());
                    }
                    it.remove();
                }
            }
            Iterator<UniqueProgramIdentifier> it2 = chunk.getRemoved().iterator();
            while (it2.hasNext()) {
                removeLocked(it2.next(), arrayList);
            }
            Iterator<RadioManager.ProgramInfo> it3 = chunk.getModified().iterator();
            while (it3.hasNext()) {
                putLocked(it3.next(), arraySet);
            }
            if (chunk.isComplete()) {
                this.mIsComplete = true;
                arrayList2 = new ArrayList(this.mOnCompleteListeners);
            }
            for (int i = 0; i < arrayList.size(); i++) {
                for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                    ((ListCallback) arrayList3.get(i2)).onItemRemoved(arrayList.get(i));
                }
            }
            for (ProgramSelector.Identifier identifier : arraySet) {
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((ListCallback) arrayList3.get(i3)).onItemChanged(identifier);
                }
            }
            if (chunk.isComplete()) {
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    ((OnCompleteListener) arrayList2.get(i4)).onComplete();
                }
            }
        }
    }

    private void putLocked(RadioManager.ProgramInfo programInfo, Set<ProgramSelector.Identifier> set) {
        UniqueProgramIdentifier uniqueProgramIdentifier = new UniqueProgramIdentifier(programInfo.getSelector());
        ProgramSelector.Identifier identifier = (ProgramSelector.Identifier) Objects.requireNonNull(uniqueProgramIdentifier.getPrimaryId());
        if (!this.mPrograms.containsKey(identifier)) {
            this.mPrograms.put(identifier, new ArrayMap<>());
        }
        this.mPrograms.get(identifier).put(uniqueProgramIdentifier, programInfo);
        set.add(identifier);
    }

    private void removeLocked(UniqueProgramIdentifier uniqueProgramIdentifier, List<ProgramSelector.Identifier> list) {
        ProgramSelector.Identifier identifier = (ProgramSelector.Identifier) Objects.requireNonNull(uniqueProgramIdentifier.getPrimaryId());
        if (this.mPrograms.containsKey(identifier)) {
            ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo> arrayMap = this.mPrograms.get(identifier);
            if (arrayMap.remove(Objects.requireNonNull(uniqueProgramIdentifier)) != null && arrayMap.size() == 0) {
                list.add(identifier);
            }
        }
    }

    public List<RadioManager.ProgramInfo> toList() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mPrograms.size(); i++) {
                arrayList.addAll(this.mPrograms.valueAt(i).values());
            }
        }
        return arrayList;
    }

    @Deprecated
    public RadioManager.ProgramInfo get(ProgramSelector.Identifier identifier) {
        ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo> arrayMap;
        synchronized (this.mLock) {
            arrayMap = this.mPrograms.get(Objects.requireNonNull(identifier, "Primary identifier can not be null"));
        }
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.entrySet().iterator().next().getValue();
    }

    public List<RadioManager.ProgramInfo> getProgramInfos(ProgramSelector.Identifier identifier) {
        ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo> arrayMap;
        Objects.requireNonNull(identifier, "Primary identifier can not be null");
        synchronized (this.mLock) {
            arrayMap = this.mPrograms.get(identifier);
        }
        if (arrayMap == null) {
            return new ArrayList();
        }
        return new ArrayList(arrayMap.values());
    }

    public static final class Filter implements Parcelable {
        public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() { // from class: android.hardware.radio.ProgramList.Filter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Filter createFromParcel(Parcel parcel) {
                return new Filter(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Filter[] newArray(int i) {
                return new Filter[i];
            }
        };
        private final boolean mExcludeModifications;
        private final Set<Integer> mIdentifierTypes;
        private final Set<ProgramSelector.Identifier> mIdentifiers;
        private final boolean mIncludeCategories;
        private final Map<String, String> mVendorFilter;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Filter(Set<Integer> set, Set<ProgramSelector.Identifier> set2, boolean z, boolean z2) {
            this.mIdentifierTypes = (Set) Objects.requireNonNull(set);
            this.mIdentifiers = (Set) Objects.requireNonNull(set2);
            this.mIncludeCategories = z;
            this.mExcludeModifications = z2;
            this.mVendorFilter = null;
        }

        public Filter() {
            this.mIdentifierTypes = Collections.EMPTY_SET;
            this.mIdentifiers = Collections.EMPTY_SET;
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = null;
        }

        public Filter(Map<String, String> map) {
            this.mIdentifierTypes = Collections.EMPTY_SET;
            this.mIdentifiers = Collections.EMPTY_SET;
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = map;
        }

        private Filter(Parcel parcel) {
            this.mIdentifierTypes = Utils.createIntSet(parcel);
            this.mIdentifiers = Utils.createSet(parcel, ProgramSelector.Identifier.CREATOR);
            this.mIncludeCategories = parcel.readByte() != 0;
            this.mExcludeModifications = parcel.readByte() != 0;
            this.mVendorFilter = Utils.readStringMap(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            Utils.writeIntSet(parcel, this.mIdentifierTypes);
            Utils.writeSet(parcel, this.mIdentifiers);
            parcel.writeByte(this.mIncludeCategories ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mExcludeModifications ? (byte) 1 : (byte) 0);
            Utils.writeStringMap(parcel, this.mVendorFilter);
        }

        public Map<String, String> getVendorFilter() {
            return this.mVendorFilter;
        }

        public Set<Integer> getIdentifierTypes() {
            return this.mIdentifierTypes;
        }

        public Set<ProgramSelector.Identifier> getIdentifiers() {
            return this.mIdentifiers;
        }

        public boolean areCategoriesIncluded() {
            return this.mIncludeCategories;
        }

        public boolean areModificationsExcluded() {
            return this.mExcludeModifications;
        }

        public int hashCode() {
            return Objects.hash(this.mIdentifierTypes, this.mIdentifiers, Boolean.valueOf(this.mIncludeCategories), Boolean.valueOf(this.mExcludeModifications));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Filter)) {
                return false;
            }
            Filter filter = (Filter) obj;
            return this.mIncludeCategories == filter.mIncludeCategories && this.mExcludeModifications == filter.mExcludeModifications && Objects.equals(this.mIdentifierTypes, filter.mIdentifierTypes) && Objects.equals(this.mIdentifiers, filter.mIdentifiers);
        }

        public String toString() {
            return "Filter [mIdentifierTypes=" + this.mIdentifierTypes + ", mIdentifiers=" + this.mIdentifiers + ", mIncludeCategories=" + this.mIncludeCategories + ", mExcludeModifications=" + this.mExcludeModifications + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class Chunk implements Parcelable {
        public static final Parcelable.Creator<Chunk> CREATOR = new Parcelable.Creator<Chunk>() { // from class: android.hardware.radio.ProgramList.Chunk.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Chunk createFromParcel(Parcel parcel) {
                return new Chunk(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Chunk[] newArray(int i) {
                return new Chunk[i];
            }
        };
        private final boolean mComplete;
        private final Set<RadioManager.ProgramInfo> mModified;
        private final boolean mPurge;
        private final Set<UniqueProgramIdentifier> mRemoved;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Chunk(boolean z, boolean z2, Set<RadioManager.ProgramInfo> set, Set<UniqueProgramIdentifier> set2) {
            this.mPurge = z;
            this.mComplete = z2;
            this.mModified = set == null ? Collections.EMPTY_SET : set;
            this.mRemoved = set2 == null ? Collections.EMPTY_SET : set2;
        }

        private Chunk(Parcel parcel) {
            this.mPurge = parcel.readByte() != 0;
            this.mComplete = parcel.readByte() != 0;
            this.mModified = Utils.createSet(parcel, RadioManager.ProgramInfo.CREATOR);
            this.mRemoved = Utils.createSet(parcel, UniqueProgramIdentifier.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mPurge ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mComplete ? (byte) 1 : (byte) 0);
            Utils.writeSet(parcel, this.mModified);
            Utils.writeSet(parcel, this.mRemoved);
        }

        public boolean isPurge() {
            return this.mPurge;
        }

        public boolean isComplete() {
            return this.mComplete;
        }

        public Set<RadioManager.ProgramInfo> getModified() {
            return this.mModified;
        }

        public Set<UniqueProgramIdentifier> getRemoved() {
            return this.mRemoved;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Chunk)) {
                return false;
            }
            Chunk chunk = (Chunk) obj;
            return this.mPurge == chunk.mPurge && this.mComplete == chunk.mComplete && Objects.equals(this.mModified, chunk.mModified) && Objects.equals(this.mRemoved, chunk.mRemoved);
        }

        public String toString() {
            return "Chunk [mPurge=" + this.mPurge + ", mComplete=" + this.mComplete + ", mModified=" + this.mModified + ", mRemoved=" + this.mRemoved + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }
}
