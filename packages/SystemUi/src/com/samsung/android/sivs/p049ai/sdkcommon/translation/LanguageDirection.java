package com.samsung.android.sivs.p049ai.sdkcommon.translation;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class LanguageDirection implements Parcelable {
    public static final Parcelable.Creator<LanguageDirection> CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sivs.ai.sdkcommon.translation.LanguageDirection.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new LanguageDirection(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new LanguageDirection[i];
        }
    };
    private String sourceLanguage;
    private String targetLanguage;

    public /* synthetic */ LanguageDirection(Parcel parcel, int i) {
        this(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.sourceLanguage = parcel.readString();
        this.targetLanguage = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LanguageDirection languageDirection = (LanguageDirection) obj;
        return Objects.equals(this.sourceLanguage, languageDirection.sourceLanguage) && Objects.equals(this.targetLanguage, languageDirection.targetLanguage);
    }

    public String getSourceLanguage() {
        return this.sourceLanguage;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public int hashCode() {
        return Objects.hash(this.sourceLanguage, this.targetLanguage);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sourceLanguage);
        parcel.writeString(this.targetLanguage);
    }

    public LanguageDirection(String str, String str2) {
        this.sourceLanguage = str;
        this.targetLanguage = str2;
    }

    private LanguageDirection(Parcel parcel) {
        readFromParcel(parcel);
    }
}
