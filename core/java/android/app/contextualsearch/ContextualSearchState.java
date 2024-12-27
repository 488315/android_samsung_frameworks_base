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
        public ContextualSearchState createFromParcel(Parcel source) {
            return new ContextualSearchState(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextualSearchState[] newArray(int size) {
            return new ContextualSearchState[size];
        }
    };
    private final AssistContent mContent;
    private final Bundle mExtras;
    private final AssistStructure mStructure;

    public ContextualSearchState(AssistStructure structure, AssistContent content, Bundle extras) {
        this.mStructure = structure;
        this.mContent = content;
        this.mExtras = extras;
    }

    private ContextualSearchState(Parcel source) {
        this.mStructure = (AssistStructure) source.readTypedObject(AssistStructure.CREATOR);
        this.mContent = (AssistContent) source.readTypedObject(AssistContent.CREATOR);
        Bundle extras = source.readBundle(getClass().getClassLoader());
        this.mExtras = extras != null ? extras : Bundle.EMPTY;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mStructure, flags);
        dest.writeTypedObject(this.mContent, flags);
        dest.writeBundle(this.mExtras);
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
