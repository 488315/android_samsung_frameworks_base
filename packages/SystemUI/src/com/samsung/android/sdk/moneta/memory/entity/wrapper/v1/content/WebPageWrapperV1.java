package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.WebPage;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class WebPageWrapperV1 extends ContentWrapper {
    private final String id;
    private final String pageCategory;
    private final String searchKeyword;
    private final String title;
    private final String url;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<WebPageWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new WebPageWrapperV1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new WebPageWrapperV1[i];
        }
    }

    public WebPageWrapperV1(String str, String str2, String str3, String str4, String str5) {
        this.id = str;
        this.title = str2;
        this.url = str3;
        this.searchKeyword = str4;
        this.pageCategory = str5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getId() {
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.url);
        parcel.writeString(this.searchKeyword);
        parcel.writeString(this.pageCategory);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public WebPage toContent() {
        return new WebPage(this.id, this.title, this.url, this.searchKeyword, this.pageCategory);
    }
}
