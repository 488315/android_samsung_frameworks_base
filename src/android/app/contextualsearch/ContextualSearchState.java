package android.app.contextualsearch;

import android.annotation.SystemApi;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class ContextualSearchState implements Parcelable {
    public static final Parcelable.Creator<ContextualSearchState> CREATOR = new Parcelable.Creator<ContextualSearchState>() { // from class: android.app.contextualsearch.ContextualSearchState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextualSearchState createFromParcel(Parcel parcel) {
            return new ContextualSearchState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextualSearchState[] newArray(int i) {
            return new ContextualSearchState[i];
        }
    };
    private final AssistContent mContent;
    private final Bundle mExtras;
    private final AssistStructure mStructure;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContextualSearchState(AssistStructure assistStructure, AssistContent assistContent, Bundle bundle) {
        this.mStructure = assistStructure;
        this.mContent = assistContent;
        this.mExtras = bundle;
    }

    private ContextualSearchState(Parcel parcel) {
        this.mStructure = (AssistStructure) parcel.readTypedObject(AssistStructure.CREATOR);
        this.mContent = (AssistContent) parcel.readTypedObject(AssistContent.CREATOR);
        Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
        this.mExtras = readBundle == null ? Bundle.EMPTY : readBundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mStructure, i);
        parcel.writeTypedObject(this.mContent, i);
        parcel.writeBundle(this.mExtras);
    }

    public AssistContent getContent() {
        return this.mContent;
    }

    public AssistStructure getStructure() {
        return this.mStructure;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }
}
