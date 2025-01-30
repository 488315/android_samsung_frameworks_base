package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class TaskTransitionSpec implements Parcelable {
  public static final Parcelable.Creator<TaskTransitionSpec> CREATOR =
      new Parcelable.Creator<
          TaskTransitionSpec>() { // from class: android.view.TaskTransitionSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskTransitionSpec createFromParcel(Parcel in) {
          return new TaskTransitionSpec(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskTransitionSpec[] newArray(int size) {
          return new TaskTransitionSpec[size];
        }
      };
  public final int backgroundColor;

  public TaskTransitionSpec(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public TaskTransitionSpec(Parcel in) {
    this.backgroundColor = in.readInt();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.backgroundColor);
  }
}
