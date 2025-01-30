package com.sec.ims.im;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImStickerData implements Parcelable {
    public static final Parcelable.Creator<ImStickerData> CREATOR = new Parcelable.Creator<ImStickerData>() { // from class: com.sec.ims.im.ImStickerData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImStickerData createFromParcel(Parcel parcel) {
            return new ImStickerData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImStickerData[] newArray(int i) {
            return new ImStickerData[i];
        }
    };
    private static final String contentType = "application/vnd.gsma.rcs-sticker+xml";
    private static final String innerUri = "content://com.samsung.rcs.im/getstickerfile/";
    private boolean isSticker;
    private String mStickerId;
    private String mStickerItemId;
    private String mStickerItemName;
    private String mStickerItemThumbnailUri;
    private String mStickerItemThumbnailUrl;
    private String mStickerItemUri;
    private String mStickerItemUrl;
    private String mStickerName;
    private String mStickerNums;
    private String mStickerThumbnail;
    private String mStickerUntil;

    public ImStickerData(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.mStickerName = str2;
        this.mStickerId = str;
        this.mStickerUntil = str3;
        this.mStickerThumbnail = str4;
        this.mStickerNums = str5;
        this.mStickerItemId = str6;
        this.mStickerItemName = str7;
        this.mStickerItemUrl = str8;
        this.mStickerItemThumbnailUrl = str9;
        this.isSticker = true;
    }

    public static String getContentType() {
        return contentType;
    }

    private String urlToUriParser(String str) {
        String[] split;
        String str2 = "";
        if (str == null || (split = str.split("[?&=]")) == null || split.length != 7) {
            return "";
        }
        String str3 = "";
        String str4 = str3;
        for (int i = 1; i < split.length; i += 2) {
            String str5 = split[i];
            str5.getClass();
            str5.hashCode();
            switch (str5) {
                case "folder":
                    str2 = split[i + 1];
                    break;
                case "file":
                    str4 = split[i + 1];
                    break;
                case "type":
                    str3 = "." + split[i + 1];
                    break;
            }
        }
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str2);
        m18m.append(File.separatorChar);
        m18m.append(str4);
        m18m.append(str3);
        return m18m.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getStickerId() {
        return this.mStickerId;
    }

    public String getStickerItemId() {
        return this.mStickerItemId;
    }

    public String getStickerItemName() {
        return this.mStickerItemName;
    }

    public String getStickerItemThumbnailUri() {
        return this.mStickerItemThumbnailUri;
    }

    public String getStickerItemThumbnailUrl() {
        return this.mStickerItemThumbnailUrl;
    }

    public String getStickerItemUri() {
        return this.mStickerItemUri;
    }

    public String getStickerItemUrl() {
        return this.mStickerItemUrl;
    }

    public String getStickerName() {
        return this.mStickerName;
    }

    public String getStickerNums() {
        return this.mStickerNums;
    }

    public String getStickerThumbnail() {
        return this.mStickerThumbnail;
    }

    public String getStickerUntil() {
        return this.mStickerUntil;
    }

    public boolean isSticker() {
        return this.isSticker;
    }

    public void setSticker(boolean z) {
        this.isSticker = z;
    }

    public void setStickerId(String str) {
        this.mStickerId = str;
    }

    public void setStickerItemId(String str) {
        this.mStickerItemId = str;
    }

    public void setStickerItemName(String str) {
        this.mStickerItemName = str;
    }

    public void setStickerItemThumbnailUri(String str) {
        this.mStickerItemThumbnailUri = str;
    }

    public void setStickerItemThumbnailUrl(String str) {
        this.mStickerItemThumbnailUrl = str;
    }

    public void setStickerItemUri(String str) {
        this.mStickerItemUri = str;
    }

    public void setStickerItemUrl(String str) {
        this.mStickerItemUrl = str;
    }

    public void setStickerName(String str) {
        this.mStickerName = str;
    }

    public void setStickerNums(String str) {
        this.mStickerNums = str;
    }

    public void setStickerThumbnail(String str) {
        this.mStickerThumbnail = str;
    }

    public void setStickerUntil(String str) {
        this.mStickerUntil = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ImStickerData [mStickerName=");
        sb.append(this.mStickerName);
        sb.append(", mStickerId=");
        sb.append(this.mStickerId);
        sb.append(", mStickerUntil=");
        sb.append(this.mStickerUntil);
        sb.append(", mStickerThumbnail=");
        sb.append(this.mStickerThumbnail);
        sb.append(", mStickerNums=");
        sb.append(this.mStickerNums);
        sb.append(", mStickerItemId=");
        sb.append(this.mStickerItemId);
        sb.append(", mStickerItemName=");
        sb.append(this.mStickerItemName);
        sb.append(", mStickerItemUrl=");
        sb.append(this.mStickerItemUrl);
        sb.append(", mStickerItemThumbnailUrl=");
        sb.append(this.mStickerItemThumbnailUrl);
        sb.append(", mStickerItemUri=");
        sb.append(this.mStickerItemUri);
        sb.append(", mStickerItemThumbnailUri=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mStickerItemThumbnailUri, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mStickerName);
        parcel.writeString(this.mStickerId);
        parcel.writeString(this.mStickerUntil);
        parcel.writeString(this.mStickerThumbnail);
        parcel.writeString(this.mStickerNums);
        parcel.writeString(this.mStickerItemId);
        parcel.writeString(this.mStickerItemName);
        parcel.writeString(this.mStickerItemUrl);
        parcel.writeString(this.mStickerItemThumbnailUrl);
        parcel.writeString(this.mStickerItemUri);
        parcel.writeString(this.mStickerItemThumbnailUri);
        parcel.writeByte(this.isSticker ? (byte) 1 : (byte) 0);
    }

    public ImStickerData(String str) {
        this.isSticker = false;
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new StringReader(str));
            String str2 = "";
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 2) {
                    String name = newPullParser.getName();
                    if ("set".equals(name)) {
                        this.mStickerId = newPullParser.getAttributeValue(null, "id");
                        this.mStickerUntil = newPullParser.getAttributeValue(null, "until");
                    } else if ("item".equals(name)) {
                        this.mStickerItemId = newPullParser.getAttributeValue(null, "id");
                        this.mStickerItemName = newPullParser.getAttributeValue(null, "name");
                        this.mStickerItemUrl = newPullParser.getAttributeValue(null, "url");
                        this.mStickerItemThumbnailUrl = newPullParser.getAttributeValue(null, "thumbnail");
                    }
                    str2 = name;
                } else if (eventType == 3) {
                    str2 = null;
                } else if (eventType == 4 && str2 != null) {
                    String text = newPullParser.getText();
                    if ("name".equals(str2)) {
                        this.mStickerName = text;
                    } else if ("thumbnail".equals(str2)) {
                        this.mStickerThumbnail = text;
                    } else if ("nums".equals(str2)) {
                        this.mStickerNums = text;
                    }
                }
            }
            this.mStickerItemUri = innerUri + urlToUriParser(this.mStickerItemUrl);
            this.mStickerItemThumbnailUri = innerUri + urlToUriParser(this.mStickerItemThumbnailUrl);
            this.isSticker = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public ImStickerData(Parcel parcel) {
        this.isSticker = false;
        this.mStickerName = parcel.readString();
        this.mStickerId = parcel.readString();
        this.mStickerUntil = parcel.readString();
        this.mStickerThumbnail = parcel.readString();
        this.mStickerNums = parcel.readString();
        this.mStickerItemId = parcel.readString();
        this.mStickerItemName = parcel.readString();
        this.mStickerItemUrl = parcel.readString();
        this.mStickerItemThumbnailUrl = parcel.readString();
        this.mStickerItemUri = parcel.readString();
        this.mStickerItemThumbnailUri = parcel.readString();
        this.isSticker = parcel.readByte() != 0;
    }
}
