package com.android.modules.utils;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public class ParceledListSlice<T extends Parcelable> extends BaseParceledListSlice<T> {
  public static final Parcelable.ClassLoaderCreator<ParceledListSlice> CREATOR =
      new Parcelable.ClassLoaderCreator<
          ParceledListSlice>() { // from class: com.android.modules.utils.ParceledListSlice.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Parcelable.Creator
        public ParceledListSlice createFromParcel(Parcel parcel) {
          return new ParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public ParceledListSlice createFromParcel(Parcel in, ClassLoader loader) {
          return new ParceledListSlice(in, loader);
        }

        @Override // android.os.Parcelable.Creator
        public ParceledListSlice[] newArray(int size) {
          return new ParceledListSlice[size];
        }
      };

  @Override // com.android.modules.utils.BaseParceledListSlice
  public /* bridge */ /* synthetic */ List getList() {
    return super.getList();
  }

  @Override // com.android.modules.utils.BaseParceledListSlice
  public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
    super.setInlineCountLimit(i);
  }

  @Override // com.android.modules.utils.BaseParceledListSlice, android.p009os.Parcelable
  public /* bridge */ /* synthetic */ void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
  }

  public ParceledListSlice(List<T> list) {
    super(list);
  }

  private ParceledListSlice(Parcel in, ClassLoader loader) {
    super(in, loader);
  }

  public static <T extends Parcelable> ParceledListSlice<T> emptyList() {
    return new ParceledListSlice<>(Collections.emptyList());
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    int contents = 0;
    List<T> list = getList();
    for (int i = 0; i < list.size(); i++) {
      contents |= list.get(i).describeContents();
    }
    return contents;
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // com.android.modules.utils.BaseParceledListSlice
  public void writeElement(T parcelable, Parcel dest, int callFlags) {
    parcelable.writeToParcel(dest, callFlags);
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // com.android.modules.utils.BaseParceledListSlice
  public void writeParcelableCreator(T parcelable, Parcel dest) {
    dest.writeParcelableCreator(parcelable);
  }

  @Override // com.android.modules.utils.BaseParceledListSlice
  protected Parcelable.Creator<?> readParcelableCreator(Parcel from, ClassLoader loader) {
    return from.readParcelableCreator(loader);
  }
}
