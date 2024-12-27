package android.service.controls.templates;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

public final class ControlTemplateWrapper implements Parcelable {
    public static final Parcelable.Creator<ControlTemplateWrapper> CREATOR =
            new Parcelable.Creator<
                    ControlTemplateWrapper>() { // from class:
                                                // android.service.controls.templates.ControlTemplateWrapper.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ControlTemplateWrapper createFromParcel(Parcel source) {
                    return new ControlTemplateWrapper(
                            ControlTemplate.createTemplateFromBundle(source.readBundle()));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ControlTemplateWrapper[] newArray(int size) {
                    return new ControlTemplateWrapper[size];
                }
            };
    private final ControlTemplate mControlTemplate;

    public ControlTemplateWrapper(ControlTemplate template) {
        Preconditions.checkNotNull(template);
        this.mControlTemplate = template;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ControlTemplate getWrappedTemplate() {
        return this.mControlTemplate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mControlTemplate.getDataBundle());
    }
}
