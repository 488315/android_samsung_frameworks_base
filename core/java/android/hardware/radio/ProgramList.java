package android.hardware.radio;

import android.annotation.SystemApi;
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
public final class ProgramList implements AutoCloseable {
    private boolean mIsClosed;
    private boolean mIsComplete;
    private OnCloseListener mOnCloseListener;
    private final Object mLock = new Object();
    private final ArrayMap<
                    ProgramSelector.Identifier,
                    ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo>>
            mPrograms = new ArrayMap<>();
    private final List<ListCallback> mListCallbacks = new ArrayList();
    private final List<OnCompleteListener> mOnCompleteListeners = new ArrayList();

    interface OnCloseListener {
        void onClose();
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    ProgramList() {}

    public abstract static class ListCallback {
        public void onItemChanged(ProgramSelector.Identifier id) {}

        public void onItemRemoved(ProgramSelector.Identifier id) {}
    }

    /* renamed from: android.hardware.radio.ProgramList$1, reason: invalid class name */
    class AnonymousClass1 extends ListCallback {
        final /* synthetic */ ListCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(Executor executor, ListCallback listCallback) {
            this.val$executor = executor;
            this.val$callback = listCallback;
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemChanged(final ProgramSelector.Identifier id) {
            Executor executor = this.val$executor;
            final ListCallback listCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProgramList.ListCallback.this.onItemChanged(id);
                        }
                    });
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemRemoved(final ProgramSelector.Identifier id) {
            Executor executor = this.val$executor;
            final ListCallback listCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProgramList.ListCallback.this.onItemRemoved(id);
                        }
                    });
        }
    }

    public void registerListCallback(Executor executor, ListCallback callback) {
        registerListCallback(new AnonymousClass1(executor, callback));
    }

    public void registerListCallback(ListCallback callback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.add((ListCallback) Objects.requireNonNull(callback));
        }
    }

    public void unregisterListCallback(ListCallback callback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.remove(Objects.requireNonNull(callback));
        }
    }

    static /* synthetic */ void lambda$addOnCompleteListener$0(
            Executor executor, final OnCompleteListener listener) {
        Objects.requireNonNull(listener);
        executor.execute(
                new Runnable() { // from class:
                    // android.hardware.radio.ProgramList$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProgramList.OnCompleteListener.this.onComplete();
                    }
                });
    }

    public void addOnCompleteListener(final Executor executor, final OnCompleteListener listener) {
        addOnCompleteListener(
                new OnCompleteListener() { // from class:
                    // android.hardware.radio.ProgramList$$ExternalSyntheticLambda1
                    @Override // android.hardware.radio.ProgramList.OnCompleteListener
                    public final void onComplete() {
                        ProgramList.lambda$addOnCompleteListener$0(executor, listener);
                    }
                });
    }

    public void addOnCompleteListener(OnCompleteListener listener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.add((OnCompleteListener) Objects.requireNonNull(listener));
            if (this.mIsComplete) {
                listener.onComplete();
            }
        }
    }

    public void removeOnCompleteListener(OnCompleteListener listener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.remove(Objects.requireNonNull(listener));
        }
    }

    void setOnCloseListener(OnCloseListener listener) {
        synchronized (this.mLock) {
            if (this.mOnCloseListener != null) {
                throw new IllegalStateException("Close callback is already set");
            }
            this.mOnCloseListener = listener;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        OnCloseListener onCompleteListenersCopied = null;
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsClosed = true;
            this.mPrograms.clear();
            this.mListCallbacks.clear();
            this.mOnCompleteListeners.clear();
            if (this.mOnCloseListener != null) {
                onCompleteListenersCopied = this.mOnCloseListener;
                this.mOnCloseListener = null;
            }
            if (onCompleteListenersCopied != null) {
                onCompleteListenersCopied.onClose();
            }
        }
    }

    void apply(Chunk chunk) {
        List<ProgramSelector.Identifier> removedList = new ArrayList<>();
        Set<ProgramSelector.Identifier> changedSet = new ArraySet<>();
        List<OnCompleteListener> onCompleteListenersCopied = new ArrayList<>();
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsComplete = false;
            List<ListCallback> listCallbacksCopied = new ArrayList<>(this.mListCallbacks);
            if (chunk.isPurge()) {
                Iterator<
                                Map.Entry<
                                        ProgramSelector.Identifier,
                                        ArrayMap<
                                                UniqueProgramIdentifier, RadioManager.ProgramInfo>>>
                        programsIterator = this.mPrograms.entrySet().iterator();
                while (programsIterator.hasNext()) {
                    Map.Entry<
                                    ProgramSelector.Identifier,
                                    ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo>>
                            removed = programsIterator.next();
                    if (removed.getValue() != null) {
                        removedList.add(removed.getKey());
                    }
                    programsIterator.remove();
                }
            }
            Iterator<UniqueProgramIdentifier> removedIterator = chunk.getRemoved().iterator();
            while (removedIterator.hasNext()) {
                removeLocked(removedIterator.next(), removedList);
            }
            Iterator<RadioManager.ProgramInfo> modifiedIterator = chunk.getModified().iterator();
            while (modifiedIterator.hasNext()) {
                putLocked(modifiedIterator.next(), changedSet);
            }
            if (chunk.isComplete()) {
                this.mIsComplete = true;
                onCompleteListenersCopied = new ArrayList<>(this.mOnCompleteListeners);
            }
            for (int i = 0; i < removedList.size(); i++) {
                for (int cbIndex = 0; cbIndex < listCallbacksCopied.size(); cbIndex++) {
                    listCallbacksCopied.get(cbIndex).onItemRemoved(removedList.get(i));
                }
            }
            for (ProgramSelector.Identifier changedId : changedSet) {
                for (int cbIndex2 = 0; cbIndex2 < listCallbacksCopied.size(); cbIndex2++) {
                    listCallbacksCopied.get(cbIndex2).onItemChanged(changedId);
                }
            }
            if (chunk.isComplete()) {
                for (int cbIndex3 = 0; cbIndex3 < onCompleteListenersCopied.size(); cbIndex3++) {
                    onCompleteListenersCopied.get(cbIndex3).onComplete();
                }
            }
        }
    }

    private void putLocked(
            RadioManager.ProgramInfo value, Set<ProgramSelector.Identifier> changedIdentifierSet) {
        UniqueProgramIdentifier key = new UniqueProgramIdentifier(value.getSelector());
        ProgramSelector.Identifier primaryKey =
                (ProgramSelector.Identifier) Objects.requireNonNull(key.getPrimaryId());
        if (!this.mPrograms.containsKey(primaryKey)) {
            this.mPrograms.put(primaryKey, new ArrayMap<>());
        }
        this.mPrograms.get(primaryKey).put(key, value);
        changedIdentifierSet.add(primaryKey);
    }

    private void removeLocked(
            UniqueProgramIdentifier key, List<ProgramSelector.Identifier> removedIdentifierList) {
        ProgramSelector.Identifier primaryKey =
                (ProgramSelector.Identifier) Objects.requireNonNull(key.getPrimaryId());
        if (!this.mPrograms.containsKey(primaryKey)) {
            return;
        }
        Map<UniqueProgramIdentifier, RadioManager.ProgramInfo> entries =
                this.mPrograms.get(primaryKey);
        RadioManager.ProgramInfo removed = entries.remove(Objects.requireNonNull(key));
        if (removed != null && entries.size() == 0) {
            removedIdentifierList.add(primaryKey);
        }
    }

    public List<RadioManager.ProgramInfo> toList() {
        List<RadioManager.ProgramInfo> list = new ArrayList<>();
        synchronized (this.mLock) {
            for (int index = 0; index < this.mPrograms.size(); index++) {
                ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo> entries =
                        this.mPrograms.valueAt(index);
                list.addAll(entries.values());
            }
        }
        return list;
    }

    @Deprecated
    public RadioManager.ProgramInfo get(ProgramSelector.Identifier id) {
        Map<UniqueProgramIdentifier, RadioManager.ProgramInfo> entries;
        synchronized (this.mLock) {
            entries =
                    this.mPrograms.get(
                            Objects.requireNonNull(id, "Primary identifier can not be null"));
        }
        if (entries == null) {
            return null;
        }
        return entries.entrySet().iterator().next().getValue();
    }

    public List<RadioManager.ProgramInfo> getProgramInfos(ProgramSelector.Identifier id) {
        ArrayMap<UniqueProgramIdentifier, RadioManager.ProgramInfo> entries;
        Objects.requireNonNull(id, "Primary identifier can not be null");
        synchronized (this.mLock) {
            entries = this.mPrograms.get(id);
        }
        if (entries == null) {
            return new ArrayList();
        }
        return new ArrayList(entries.values());
    }

    public static final class Filter implements Parcelable {
        public static final Parcelable.Creator<Filter> CREATOR =
                new Parcelable.Creator<
                        Filter>() { // from class: android.hardware.radio.ProgramList.Filter.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Filter createFromParcel(Parcel in) {
                        return new Filter(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Filter[] newArray(int size) {
                        return new Filter[size];
                    }
                };
        private final boolean mExcludeModifications;
        private final Set<Integer> mIdentifierTypes;
        private final Set<ProgramSelector.Identifier> mIdentifiers;
        private final boolean mIncludeCategories;
        private final Map<String, String> mVendorFilter;

        public Filter(
                Set<Integer> identifierTypes,
                Set<ProgramSelector.Identifier> identifiers,
                boolean includeCategories,
                boolean excludeModifications) {
            this.mIdentifierTypes = (Set) Objects.requireNonNull(identifierTypes);
            this.mIdentifiers = (Set) Objects.requireNonNull(identifiers);
            this.mIncludeCategories = includeCategories;
            this.mExcludeModifications = excludeModifications;
            this.mVendorFilter = null;
        }

        public Filter() {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = null;
        }

        public Filter(Map<String, String> vendorFilter) {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = vendorFilter;
        }

        private Filter(Parcel in) {
            this.mIdentifierTypes = Utils.createIntSet(in);
            this.mIdentifiers = Utils.createSet(in, ProgramSelector.Identifier.CREATOR);
            this.mIncludeCategories = in.readByte() != 0;
            this.mExcludeModifications = in.readByte() != 0;
            this.mVendorFilter = Utils.readStringMap(in);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            Utils.writeIntSet(parcel, this.mIdentifierTypes);
            Utils.writeSet(parcel, this.mIdentifiers);
            parcel.writeByte(this.mIncludeCategories ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mExcludeModifications ? (byte) 1 : (byte) 0);
            Utils.writeStringMap(parcel, this.mVendorFilter);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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
            return Objects.hash(
                    this.mIdentifierTypes,
                    this.mIdentifiers,
                    Boolean.valueOf(this.mIncludeCategories),
                    Boolean.valueOf(this.mExcludeModifications));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Filter)) {
                return false;
            }
            Filter other = (Filter) obj;
            return this.mIncludeCategories == other.mIncludeCategories
                    && this.mExcludeModifications == other.mExcludeModifications
                    && Objects.equals(this.mIdentifierTypes, other.mIdentifierTypes)
                    && Objects.equals(this.mIdentifiers, other.mIdentifiers);
        }

        public String toString() {
            return "Filter [mIdentifierTypes="
                    + this.mIdentifierTypes
                    + ", mIdentifiers="
                    + this.mIdentifiers
                    + ", mIncludeCategories="
                    + this.mIncludeCategories
                    + ", mExcludeModifications="
                    + this.mExcludeModifications
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class Chunk implements Parcelable {
        public static final Parcelable.Creator<Chunk> CREATOR =
                new Parcelable.Creator<
                        Chunk>() { // from class: android.hardware.radio.ProgramList.Chunk.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Chunk createFromParcel(Parcel in) {
                        return new Chunk(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Chunk[] newArray(int size) {
                        return new Chunk[size];
                    }
                };
        private final boolean mComplete;
        private final Set<RadioManager.ProgramInfo> mModified;
        private final boolean mPurge;
        private final Set<UniqueProgramIdentifier> mRemoved;

        public Chunk(
                boolean purge,
                boolean complete,
                Set<RadioManager.ProgramInfo> modified,
                Set<UniqueProgramIdentifier> removed) {
            this.mPurge = purge;
            this.mComplete = complete;
            this.mModified = modified != null ? modified : Collections.emptySet();
            this.mRemoved = removed != null ? removed : Collections.emptySet();
        }

        private Chunk(Parcel in) {
            this.mPurge = in.readByte() != 0;
            this.mComplete = in.readByte() != 0;
            this.mModified = Utils.createSet(in, RadioManager.ProgramInfo.CREATOR);
            this.mRemoved = Utils.createSet(in, UniqueProgramIdentifier.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mPurge ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mComplete ? (byte) 1 : (byte) 0);
            Utils.writeSet(parcel, this.mModified);
            Utils.writeSet(parcel, this.mRemoved);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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
            Chunk other = (Chunk) obj;
            return this.mPurge == other.mPurge
                    && this.mComplete == other.mComplete
                    && Objects.equals(this.mModified, other.mModified)
                    && Objects.equals(this.mRemoved, other.mRemoved);
        }

        public String toString() {
            return "Chunk [mPurge="
                    + this.mPurge
                    + ", mComplete="
                    + this.mComplete
                    + ", mModified="
                    + this.mModified
                    + ", mRemoved="
                    + this.mRemoved
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }
}
