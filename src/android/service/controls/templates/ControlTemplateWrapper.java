package android.service.controls.templates;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ControlTemplateWrapper implements Parcelable {
    public static final Parcelable.Creator<ControlTemplateWrapper> CREATOR = new Parcelable.Creator<ControlTemplateWrapper>() { // from class: android.service.controls.templates.ControlTemplateWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlTemplateWrapper createFromParcel(Parcel parcel) {
            return new ControlTemplateWrapper(ControlTemplate.createTemplateFromBundle(parcel.readBundle()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlTemplateWrapper[] newArray(int i) {
            return new ControlTemplateWrapper[i];
        }
    };
    private final ControlTemplate mControlTemplate;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ControlTemplateWrapper(ControlTemplate controlTemplate) {
        Preconditions.checkNotNull(controlTemplate);
        this.mControlTemplate = controlTemplate;
    }

    public ControlTemplate getWrappedTemplate() {
        return this.mControlTemplate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mControlTemplate.getDataBundle());
    }
}
