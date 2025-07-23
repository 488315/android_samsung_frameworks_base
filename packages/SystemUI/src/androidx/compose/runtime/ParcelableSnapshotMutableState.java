package androidx.compose.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParcelableSnapshotMutableState<T> extends SnapshotMutableStateImpl<T> implements Parcelable {
    public static final Parcelable.Creator<ParcelableSnapshotMutableState<Object>> CREATOR;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        CREATOR = new Parcelable.ClassLoaderCreator<ParcelableSnapshotMutableState<Object>>() { // from class: androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final /* bridge */ /* synthetic */ ParcelableSnapshotMutableState<Object> createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return createFromParcel2(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new ParcelableSnapshotMutableState[i];
            }

            /* renamed from: createFromParcel, reason: avoid collision after fix types in other method */
            public static ParcelableSnapshotMutableState createFromParcel2(Parcel parcel, ClassLoader classLoader) {
                SnapshotMutationPolicy snapshotMutationPolicy;
                if (classLoader == null) {
                    classLoader = ParcelableSnapshotMutableState$Companion$CREATOR$1.class.getClassLoader();
                }
                Object readValue = parcel.readValue(classLoader);
                int readInt = parcel.readInt();
                if (readInt == 0) {
                    snapshotMutationPolicy = NeverEqualPolicy.INSTANCE;
                } else if (readInt == 1) {
                    snapshotMutationPolicy = StructuralEqualityPolicy.INSTANCE;
                } else {
                    if (readInt != 2) {
                        throw new IllegalStateException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(readInt, "Unsupported MutableState policy ", " was restored"));
                    }
                    snapshotMutationPolicy = ReferentialEqualityPolicy.INSTANCE;
                }
                return new ParcelableSnapshotMutableState(readValue, snapshotMutationPolicy);
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return createFromParcel2(parcel, (ClassLoader) null);
            }
        };
    }

    public ParcelableSnapshotMutableState(T t, SnapshotMutationPolicy<T> snapshotMutationPolicy) {
        super(t, snapshotMutationPolicy);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int i2;
        parcel.writeValue(getValue());
        NeverEqualPolicy neverEqualPolicy = NeverEqualPolicy.INSTANCE;
        SnapshotMutationPolicy snapshotMutationPolicy = this.policy;
        if (Intrinsics.areEqual(snapshotMutationPolicy, neverEqualPolicy)) {
            i2 = 0;
        } else if (Intrinsics.areEqual(snapshotMutationPolicy, StructuralEqualityPolicy.INSTANCE)) {
            i2 = 1;
        } else {
            if (!Intrinsics.areEqual(snapshotMutationPolicy, ReferentialEqualityPolicy.INSTANCE)) {
                throw new IllegalStateException("Only known types of MutableState's SnapshotMutationPolicy are supported");
            }
            i2 = 2;
        }
        parcel.writeInt(i2);
    }
}
