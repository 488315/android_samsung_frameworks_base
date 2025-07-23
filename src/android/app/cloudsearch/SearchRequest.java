package android.app.cloudsearch;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class SearchRequest implements Parcelable {
    public static final String CONSTRAINT_IS_PRESUBMIT_SUGGESTION = "android.app.cloudsearch.IS_PRESUBMIT_SUGGESTION";
    public static final String CONSTRAINT_SEARCH_PROVIDER_FILTER = "android.app.cloudsearch.SEARCH_PROVIDER_FILTER";
    public static final Parcelable.Creator<SearchRequest> CREATOR = new Parcelable.Creator<SearchRequest>() { // from class: android.app.cloudsearch.SearchRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchRequest createFromParcel(Parcel parcel) {
            return new SearchRequest();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchRequest[] newArray(int i) {
            return new SearchRequest[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchConstraintKey {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public float getMaxLatencyMillis() {
        return 0.0f;
    }

    public int getResultNumber() {
        return 0;
    }

    public int getResultOffset() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    public void setCallerPackageName(String str) {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    private SearchRequest() {
    }

    public String getQuery() {
        return "";
    }

    public Bundle getSearchConstraints() {
        return Bundle.EMPTY;
    }

    public String getCallerPackageName() {
        return "";
    }

    public String getRequestId() {
        return "";
    }

    public String toString() {
        return "";
    }

    @SystemApi
    public static final class Builder {
        public Builder setCallerPackageName(String str) {
            return this;
        }

        public Builder setMaxLatencyMillis(float f) {
            return this;
        }

        public Builder setQuery(String str) {
            return this;
        }

        public Builder setResultNumber(int i) {
            return this;
        }

        public Builder setResultOffset(int i) {
            return this;
        }

        public Builder setSearchConstraints(Bundle bundle) {
            return this;
        }

        @SystemApi
        public Builder(String str) {
        }

        public SearchRequest build() {
            return new SearchRequest();
        }
    }
}
