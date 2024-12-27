package android.app.cloudsearch;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class SearchResponse implements Parcelable {
    public static final Parcelable.Creator<SearchResponse> CREATOR =
            new Parcelable.Creator<
                    SearchResponse>() { // from class: android.app.cloudsearch.SearchResponse.1
                @Override // android.os.Parcelable.Creator
                public SearchResponse createFromParcel(Parcel p) {
                    return new SearchResponse();
                }

                @Override // android.os.Parcelable.Creator
                public SearchResponse[] newArray(int size) {
                    return new SearchResponse[size];
                }
            };
    public static final int SEARCH_STATUS_NO_INTERNET = 2;
    public static final int SEARCH_STATUS_OK = 0;
    public static final int SEARCH_STATUS_TIME_OUT = 1;
    public static final int SEARCH_STATUS_UNKNOWN = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchStatusCode {}

    private SearchResponse() {}

    public int getStatusCode() {
        return -1;
    }

    public String getSource() {
        return "";
    }

    public List<SearchResult> getSearchResults() {
        return new ArrayList();
    }

    public void setSource(String source) {}

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @SystemApi
    public static final class Builder {
        @SystemApi
        public Builder(int statusCode) {}

        public Builder setStatusCode(int statusCode) {
            return this;
        }

        public Builder setSource(String source) {
            return this;
        }

        public Builder setSearchResults(List<SearchResult> searchResults) {
            return this;
        }

        public SearchResponse build() {
            return new SearchResponse();
        }
    }
}
