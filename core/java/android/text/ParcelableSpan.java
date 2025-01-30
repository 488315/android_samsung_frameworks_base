package android.text;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes3.dex */
public interface ParcelableSpan extends Parcelable {
  int getSpanTypeId();

  int getSpanTypeIdInternal();

  void writeToParcelInternal(Parcel parcel, int i);
}
