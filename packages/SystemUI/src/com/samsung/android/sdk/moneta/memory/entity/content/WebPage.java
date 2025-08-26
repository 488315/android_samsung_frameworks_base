package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class WebPage extends Content {
    public static final Parcelable.Creator<WebPage> CREATOR = new Creator();
    private final String id;
    private final String pageCategory;
    private final String searchKeyword;
    private final String title;
    private final String url;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new WebPage(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new WebPage[i];
        }
    }

    public WebPage(String str, String str2, String str3, String str4, String str5) {
        this.id = str;
        this.title = str2;
        this.url = str3;
        this.searchKeyword = str4;
        this.pageCategory = str5;
    }

    public static /* synthetic */ WebPage copy$default(WebPage webPage, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = webPage.id;
        }
        if ((i & 2) != 0) {
            str2 = webPage.title;
        }
        if ((i & 4) != 0) {
            str3 = webPage.url;
        }
        if ((i & 8) != 0) {
            str4 = webPage.searchKeyword;
        }
        if ((i & 16) != 0) {
            str5 = webPage.pageCategory;
        }
        String str6 = str5;
        String str7 = str3;
        return webPage.copy(str, str2, str7, str4, str6);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.title;
    }

    public final String component3() {
        return this.url;
    }

    public final String component4() {
        return this.searchKeyword;
    }

    public final String component5() {
        return this.pageCategory;
    }

    public final WebPage copy(String str, String str2, String str3, String str4, String str5) {
        return new WebPage(str, str2, str3, str4, str5);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WebPage)) {
            return false;
        }
        WebPage webPage = (WebPage) obj;
        return Intrinsics.areEqual(this.id, webPage.id) && Intrinsics.areEqual(this.title, webPage.title) && Intrinsics.areEqual(this.url, webPage.url) && Intrinsics.areEqual(this.searchKeyword, webPage.searchKeyword) && Intrinsics.areEqual(this.pageCategory, webPage.pageCategory);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
        return this.id;
    }

    public final String getPageCategory() {
        return this.pageCategory;
    }

    public final String getSearchKeyword() {
        return this.searchKeyword;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        return this.pageCategory.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.title), 31, this.url), 31, this.searchKeyword);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WebPage(id=");
        sb.append(this.id);
        sb.append(", title=");
        sb.append(this.title);
        sb.append(", url=");
        sb.append(this.url);
        sb.append(", searchKeyword=");
        sb.append(this.searchKeyword);
        sb.append(", pageCategory=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.pageCategory, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.url);
        parcel.writeString(this.searchKeyword);
        parcel.writeString(this.pageCategory);
    }
}
