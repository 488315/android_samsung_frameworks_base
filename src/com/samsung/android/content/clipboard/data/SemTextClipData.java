package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.Context;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.util.HtmlUtils;
import android.sec.clipboard.util.Log;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SemTextClipData extends SemClipData {
    private static final String TAG = "SemTextClipData";
    private static final long serialVersionUID = 1;
    private int mNumberOfTrailingWhiteLines;
    private transient CharSequence mText;
    private String mValue;

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String str) {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return null;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String str) {
    }

    public SemTextClipData() {
        super(1);
        this.mValue = "";
        this.mText = "";
        this.mNumberOfTrailingWhiteLines = 0;
    }

    public SemTextClipData(Parcel parcel) {
        super(parcel);
        this.mValue = "";
        this.mText = "";
        this.mNumberOfTrailingWhiteLines = 0;
        readFromSource(parcel);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
        String str = this.mValue;
        if (str != null) {
            if (str.length() > 131072) {
                this.mText = "";
                return;
            }
            if (HtmlUtils.isHtml(this.mValue)) {
                this.mText = Html.fromHtml(this.mValue);
            } else {
                if (this.mValue.contains(HtmlUtils.HTML_LINE_FEED)) {
                    this.mValue = this.mValue.replaceAll(HtmlUtils.HTML_LINE_FEED, ShaderAssembler.NEWLINE);
                }
                this.mText = this.mValue;
            }
            int i = 0;
            for (int i2 = 1; i2 <= this.mText.length() - 1; i2++) {
                CharSequence charSequence = this.mText;
                if (charSequence.charAt(charSequence.length() - i2) != '\n') {
                    break;
                }
                i++;
            }
            int i3 = this.mNumberOfTrailingWhiteLines;
            if (i > i3) {
                int i4 = i - i3;
                CharSequence charSequence2 = this.mText;
                this.mText = charSequence2.subSequence(0, charSequence2.length() - i4);
            }
            Log.secD(TAG, "textclipdata toLoad called");
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toSave() {
        CharSequence charSequence = this.mText;
        if (charSequence != null) {
            if (charSequence instanceof Spanned) {
                this.mNumberOfTrailingWhiteLines = 0;
                for (int i = 1; i <= this.mText.length() - 1; i++) {
                    CharSequence charSequence2 = this.mText;
                    if (charSequence2.charAt(charSequence2.length() - i) != '\n') {
                        break;
                    }
                    this.mNumberOfTrailingWhiteLines++;
                }
                this.mValue = Html.toHtml((Spanned) this.mText);
            } else {
                this.mValue = charSequence.toString();
            }
            Log.secD(TAG, "textclipdata toSave called");
        }
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean setAlternateClipData(int i, SemClipData semClipData) {
        if (!super.setAlternateClipData(i, semClipData) || this.mText.length() < 1) {
            return false;
        }
        if (i == 1) {
            return ((SemTextClipData) semClipData).setText(this.mText);
        }
        if (i != 4) {
            return false;
        }
        return ((SemHtmlClipData) semClipData).setHtml(this.mText);
    }

    private void setClipData() {
        setClipData(new String[]{"text/plain"}, new ClipData.Item(this.mText));
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ClipData getClipData() {
        if (this.mClipData == null) {
            setClipData();
        }
        return this.mClipData;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected ClipData getClipDataInternal() {
        if (this.mClipData == null) {
            setClipData();
        }
        return this.mClipData;
    }

    public boolean setText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        if (charSequence.length() > 131072) {
            charSequence = charSequence.subSequence(0, 131072);
        }
        this.mText = charSequence;
        return true;
    }

    public CharSequence getText() {
        return this.mText;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object obj) {
        Log.secI(TAG, "text equals");
        return super.equals(obj) && (obj instanceof SemTextClipData) && this.mText.toString().compareTo(((SemTextClipData) obj).getText().toString()) == 0;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        Log.secI(TAG, "text write to parcel");
        parcel.writeInt(1);
        super.writeToParcel(parcel, i);
        parcel.writeValue(this.mText);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel parcel) {
        this.mText = (CharSequence) parcel.readValue(CharSequence.class.getClassLoader());
    }

    public String toString() {
        return "SemTextClipData class.";
    }
}
