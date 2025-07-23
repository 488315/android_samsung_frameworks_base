package android.app.cloudsearch;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class SearchResponse implements Parcelable {
    public static final Parcelable.Creator<SearchResponse> CREATOR = new Parcelable.Creator<SearchResponse>() { // from class: android.app.cloudsearch.SearchResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchResponse createFromParcel(Parcel parcel) {
            return new SearchResponse();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchResponse[] newArray(int i) {
            return new SearchResponse[i];
        }
    };
    public static final int SEARCH_STATUS_NO_INTERNET = 2;
    public static final int SEARCH_STATUS_OK = 0;
    public static final int SEARCH_STATUS_TIME_OUT = 1;
    public static final int SEARCH_STATUS_UNKNOWN = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchStatusCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public int getStatusCode() {
        return -1;
    }

    public int hashCode() {
        return 0;
    }

    public void setSource(String str) {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    private SearchResponse() {
    }

    public String getSource() {
        return "";
    }

    public List<SearchResult> getSearchResults() {
        return new ArrayList();
    }

    @SystemApi
    public static final class Builder {
        public Builder setSearchResults(List<SearchResult> list) {
            return this;
        }

        public Builder setSource(String str) {
            return this;
        }

        public Builder setStatusCode(int i) {
            return this;
        }

        @SystemApi
        public Builder(int i) {
        }

        public SearchResponse build() {
            return new SearchResponse();
        }
    }
}
