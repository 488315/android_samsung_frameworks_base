package com.samsung.android.content.clipboard.data;

import android.content.ClipData;
import android.content.Context;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.sec.clipboard.util.HtmlUtils;
import android.sec.clipboard.util.Log;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

/* loaded from: classes5.dex */
public class SemTextClipData extends SemClipData {
    private static final String TAG = "SemTextClipData";
    private static final long serialVersionUID = 1;
    private int mNumberOfTrailingWhiteLines;
    private transient CharSequence mText;
    private String mValue;

    public SemTextClipData() {
        super(1);
        this.mValue = "";
        this.mText = "";
        this.mNumberOfTrailingWhiteLines = 0;
        this.mNumberOfTrailingWhiteLines = 0;
    }

    public SemTextClipData(Parcel source) {
        super(source);
        this.mValue = "";
        this.mText = "";
        this.mNumberOfTrailingWhiteLines = 0;
        readFromSource(source);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void toLoad() {
        String str = this.mValue;
        if (str != null) {
            if (str.length() > 131072) {
                this.mText = "";
                return;
            }
            boolean bHtml = HtmlUtils.isHtml(this.mValue);
            if (bHtml) {
                this.mText = Html.fromHtml(this.mValue);
            } else {
                if (this.mValue.contains(HtmlUtils.HTML_LINE_FEED)) {
                    this.mValue = this.mValue.replaceAll(HtmlUtils.HTML_LINE_FEED, "\n");
                }
                this.mText = this.mValue;
            }
            int numNewLine = 0;
            for (int i = 1; i <= this.mText.length() - 1; i++) {
                CharSequence charSequence = this.mText;
                if (charSequence.charAt(charSequence.length() - i) != '\n') {
                    break;
                }
                numNewLine++;
            }
            int i2 = this.mNumberOfTrailingWhiteLines;
            if (numNewLine > i2) {
                int gap = numNewLine - i2;
                CharSequence charSequence2 = this.mText;
                this.mText = charSequence2.subSequence(0, charSequence2.length() - gap);
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
    public boolean setAlternateClipData(int type, SemClipData altData) {
        boolean result = super.setAlternateClipData(type, altData);
        if (!result || this.mText.length() < 1) {
            return false;
        }
        switch (type) {
            case 1:
                boolean result2 = ((SemTextClipData) altData).setText(this.mText);
                return result2;
            case 4:
                boolean result3 = ((SemHtmlClipData) altData).setHtml(this.mText);
                return result3;
            default:
                return false;
        }
    }

    private void setClipData() {
        String[] mimeType = {"text/plain"};
        ClipData.Item item = new ClipData.Item(this.mText);
        setClipData(mimeType, item);
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

    public boolean setText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (text.length() > 131072) {
            text = text.subSequence(0, 131072);
        }
        this.mText = text;
        return true;
    }

    public CharSequence getText() {
        return this.mText;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public boolean equals(Object o) {
        Log.secI(TAG, "text equals");
        if (!super.equals(o) || !(o instanceof SemTextClipData)) {
            return false;
        }
        SemTextClipData trgData = (SemTextClipData) o;
        return this.mText.toString().compareTo(trgData.getText().toString()) == 0;
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Log.secI(TAG, "text write to parcel");
        dest.writeInt(1);
        super.writeToParcel(dest, flags);
        dest.writeValue(this.mText);
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    protected void readFromSource(Parcel source) {
        this.mText = (CharSequence) source.readValue(CharSequence.class.getClassLoader());
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return null;
    }

    public String toString() {
        return "SemTextClipData class.";
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void convertForRemote() {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void insertContentUri(Context context, String path) {
    }

    @Override // com.samsung.android.content.clipboard.data.SemClipData
    public void deleteContentUri(Context context, String path) {
    }
}
