package android.view.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.AssistStructure;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class ViewNode extends AssistStructure.ViewNode {
    private static final long FLAGS_ACCESSIBILITY_FOCUSED = 131072;
    private static final long FLAGS_ACTIVATED = 2097152;
    private static final long FLAGS_ASSIST_BLOCKED = 1024;
    private static final long FLAGS_CHECKABLE = 262144;
    private static final long FLAGS_CHECKED = 524288;
    private static final long FLAGS_CLICKABLE = 4096;
    private static final long FLAGS_CONTEXT_CLICKABLE = 16384;
    private static final long FLAGS_DISABLED = 2048;
    private static final long FLAGS_FOCUSABLE = 32768;
    private static final long FLAGS_FOCUSED = 65536;
    private static final long FLAGS_HAS_AUTOFILL_HINTS = 8589934592L;
    private static final long FLAGS_HAS_AUTOFILL_ID = 32;
    private static final long FLAGS_HAS_AUTOFILL_OPTIONS = 17179869184L;
    private static final long FLAGS_HAS_AUTOFILL_PARENT_ID = 64;
    private static final long FLAGS_HAS_AUTOFILL_TYPE = 2147483648L;
    private static final long FLAGS_HAS_AUTOFILL_VALUE = 4294967296L;
    private static final long FLAGS_HAS_CLASSNAME = 16;
    private static final long FLAGS_HAS_COMPLEX_TEXT = 2;
    private static final long FLAGS_HAS_CONTENT_DESCRIPTION = 8388608;
    private static final long FLAGS_HAS_EXTRAS = 16777216;
    private static final long FLAGS_HAS_HINT_ID_ENTRY = 34359738368L;
    private static final long FLAGS_HAS_ID = 128;
    private static final long FLAGS_HAS_INPUT_TYPE = 67108864;
    private static final long FLAGS_HAS_LARGE_COORDS = 256;
    private static final long FLAGS_HAS_LOCALE_LIST = 33554432;
    private static final long FLAGS_HAS_MAX_TEXT_EMS = 268435456;
    private static final long FLAGS_HAS_MAX_TEXT_LENGTH = 536870912;
    private static final long FLAGS_HAS_MIME_TYPES = 68719476736L;
    private static final long FLAGS_HAS_MIN_TEXT_EMS = 134217728;
    private static final long FLAGS_HAS_SCROLL = 512;
    private static final long FLAGS_HAS_TEXT = 1;
    private static final long FLAGS_HAS_TEXT_ID_ENTRY = 1073741824;
    private static final long FLAGS_LONG_CLICKABLE = 8192;
    private static final long FLAGS_OPAQUE = 4194304;
    private static final long FLAGS_SELECTED = 1048576;
    private static final long FLAGS_VISIBILITY_MASK = 12;
    private static final String TAG = "ViewNode";
    private String[] mAutofillHints;
    private AutofillId mAutofillId;
    private CharSequence[] mAutofillOptions;
    private int mAutofillType;
    private AutofillValue mAutofillValue;
    private String mClassName;
    private CharSequence mContentDescription;
    private Bundle mExtras;
    private long mFlags;
    private int mHeight;
    private String mHintIdEntry;
    private int mId;
    private String mIdEntry;
    private String mIdPackage;
    private String mIdType;
    private int mInputType;
    private LocaleList mLocaleList;
    private int mMaxEms;
    private int mMaxLength;
    private int mMinEms;
    private AutofillId mParentAutofillId;
    private String[] mReceiveContentMimeTypes;
    private int mScrollX;
    private int mScrollY;
    private ViewNodeText mText;
    private String mTextIdEntry;
    private int mWidth;
    private int mX;
    private int mY;

    public ViewNode() {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        this.mAutofillType = 0;
    }

    private ViewNode(long j, Parcel parcel) {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        this.mAutofillType = 0;
        this.mFlags = j;
        if ((32 & j) != 0) {
            this.mAutofillId = (AutofillId) parcel.readParcelable(null, AutofillId.class);
        }
        if ((64 & j) != 0) {
            this.mParentAutofillId = (AutofillId) parcel.readParcelable(null, AutofillId.class);
        }
        if ((1 & j) != 0) {
            this.mText = new ViewNodeText(parcel, (2 & j) == 0);
        }
        if ((16 & j) != 0) {
            this.mClassName = parcel.readString();
        }
        if ((128 & j) != 0) {
            int i = parcel.readInt();
            this.mId = i;
            if (i != -1) {
                String string = parcel.readString();
                this.mIdEntry = string;
                if (string != null) {
                    this.mIdType = parcel.readString();
                    this.mIdPackage = parcel.readString();
                }
            }
        }
        if ((256 & j) != 0) {
            this.mX = parcel.readInt();
            this.mY = parcel.readInt();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
        } else {
            int i2 = parcel.readInt();
            this.mX = i2 & 32767;
            this.mY = (i2 >> 16) & 32767;
            int i3 = parcel.readInt();
            this.mWidth = i3 & 32767;
            this.mHeight = (i3 >> 16) & 32767;
        }
        if ((512 & j) != 0) {
            this.mScrollX = parcel.readInt();
            this.mScrollY = parcel.readInt();
        }
        if ((8388608 & j) != 0) {
            this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if ((16777216 & j) != 0) {
            this.mExtras = parcel.readBundle();
        }
        if ((33554432 & j) != 0) {
            this.mLocaleList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
        }
        if ((68719476736L & j) != 0) {
            this.mReceiveContentMimeTypes = parcel.readStringArray();
        }
        if ((67108864 & j) != 0) {
            this.mInputType = parcel.readInt();
        }
        if ((134217728 & j) != 0) {
            this.mMinEms = parcel.readInt();
        }
        if ((268435456 & j) != 0) {
            this.mMaxEms = parcel.readInt();
        }
        if ((536870912 & j) != 0) {
            this.mMaxLength = parcel.readInt();
        }
        if ((1073741824 & j) != 0) {
            this.mTextIdEntry = parcel.readString();
        }
        if ((2147483648L & j) != 0) {
            this.mAutofillType = parcel.readInt();
        }
        if ((8589934592L & j) != 0) {
            this.mAutofillHints = parcel.readStringArray();
        }
        if ((4294967296L & j) != 0) {
            this.mAutofillValue = (AutofillValue) parcel.readParcelable(null, AutofillValue.class);
        }
        if ((17179869184L & j) != 0) {
            this.mAutofillOptions = parcel.readCharSequenceArray();
        }
        if ((j & 34359738368L) != 0) {
            this.mHintIdEntry = parcel.readString();
        }
    }

    public AutofillId getParentAutofillId() {
        return this.mParentAutofillId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public CharSequence getText() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mText;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getClassName() {
        return this.mClassName;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getId() {
        return this.mId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getIdPackage() {
        return this.mIdPackage;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getIdType() {
        return this.mIdType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getIdEntry() {
        return this.mIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getLeft() {
        return this.mX;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTop() {
        return this.mY;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getScrollX() {
        return this.mScrollX;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getScrollY() {
        return this.mScrollY;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getHeight() {
        return this.mHeight;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isAssistBlocked() {
        return (this.mFlags & 1024) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isEnabled() {
        return (this.mFlags & 2048) == 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isClickable() {
        return (this.mFlags & 4096) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isLongClickable() {
        return (this.mFlags & 8192) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isContextClickable() {
        return (this.mFlags & 16384) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isFocusable() {
        return (this.mFlags & 32768) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isFocused() {
        return (this.mFlags & 65536) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isAccessibilityFocused() {
        return (this.mFlags & 131072) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isCheckable() {
        return (this.mFlags & 262144) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isChecked() {
        return (this.mFlags & 524288) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isSelected() {
        return (this.mFlags & 1048576) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isActivated() {
        return (this.mFlags & 2097152) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isOpaque() {
        return (this.mFlags & 4194304) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getHint() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mHint;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getHintIdEntry() {
        return this.mHintIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextSelectionStart() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextSelectionStart;
        }
        return -1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextSelectionEnd() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextSelectionEnd;
        }
        return -1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextColor() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextColor;
        }
        return 1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextBackgroundColor() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextBackgroundColor;
        }
        return 1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public float getTextSize() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextSize;
        }
        return 0.0f;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextStyle() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mTextStyle;
        }
        return 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int[] getTextLineCharOffsets() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mLineCharOffsets;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int[] getTextLineBaselines() {
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            return viewNodeText.mLineBaselines;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getVisibility() {
        return (int) (this.mFlags & 12);
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getInputType() {
        return this.mInputType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMinTextEms() {
        return this.mMinEms;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMaxTextEms() {
        return this.mMaxEms;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMaxTextLength() {
        return this.mMaxLength;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String getTextIdEntry() {
        return this.mTextIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getAutofillType() {
        return this.mAutofillType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public AutofillValue getAutofillValue() {
        return this.mAutofillValue;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public String[] getReceiveContentMimeTypes() {
        return this.mReceiveContentMimeTypes;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public LocaleList getLocaleList() {
        return this.mLocaleList;
    }

    public void setTextIdEntry(String str) {
        this.mTextIdEntry = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:157:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeSelfToParcel(Parcel parcel, int i) {
        long j;
        long j2 = this.mFlags;
        if (this.mAutofillId != null) {
            j2 |= 32;
        }
        if (this.mParentAutofillId != null) {
            j2 |= 64;
        }
        ViewNodeText viewNodeText = this.mText;
        if (viewNodeText != null) {
            j2 = !viewNodeText.isSimple() ? j2 | 3 : j2 | 1;
        }
        if (this.mClassName != null) {
            j2 |= 16;
        }
        if (this.mId != -1) {
            j2 |= 128;
        }
        if ((this.mX & (-32768)) == 0 && (this.mY & (-32768)) == 0) {
            j = 64;
            if (((this.mWidth & (-32768)) != 0) | ((this.mHeight & (-32768)) != 0)) {
            }
            if (this.mScrollX == 0 || this.mScrollY != 0) {
                j2 |= 512;
            }
            if (this.mContentDescription != null) {
                j2 |= 8388608;
            }
            if (this.mExtras != null) {
                j2 |= 16777216;
            }
            if (this.mLocaleList != null) {
                j2 |= 33554432;
            }
            if (this.mReceiveContentMimeTypes != null) {
                j2 |= 68719476736L;
            }
            if (this.mInputType != 0) {
                j2 |= 67108864;
            }
            if (this.mMinEms > -1) {
                j2 |= 134217728;
            }
            if (this.mMaxEms > -1) {
                j2 |= 268435456;
            }
            if (this.mMaxLength > -1) {
                j2 |= 536870912;
            }
            if (this.mTextIdEntry != null) {
                j2 |= 1073741824;
            }
            if (this.mAutofillValue != null) {
                j2 |= 4294967296L;
            }
            if (this.mAutofillType != 0) {
                j2 |= 2147483648L;
            }
            if (this.mAutofillHints != null) {
                j2 |= 8589934592L;
            }
            if (this.mAutofillOptions != null) {
                j2 |= 17179869184L;
            }
            if (this.mHintIdEntry != null) {
                j2 |= 34359738368L;
            }
            parcel.writeLong(j2);
            if ((j2 & 32) != 0) {
                parcel.writeParcelable(this.mAutofillId, i);
            }
            if ((j2 & j) != 0) {
                parcel.writeParcelable(this.mParentAutofillId, i);
            }
            if ((1 & j2) != 0) {
                this.mText.writeToParcel(parcel, (2 & j2) == 0);
            }
            if ((j2 & 16) != 0) {
                parcel.writeString(this.mClassName);
            }
            if ((j2 & 128) != 0) {
                parcel.writeInt(this.mId);
                if (this.mId != -1) {
                    parcel.writeString(this.mIdEntry);
                    if (this.mIdEntry != null) {
                        parcel.writeString(this.mIdType);
                        parcel.writeString(this.mIdPackage);
                    }
                }
            }
            if ((j2 & 256) == 0) {
                parcel.writeInt(this.mX);
                parcel.writeInt(this.mY);
                parcel.writeInt(this.mWidth);
                parcel.writeInt(this.mHeight);
            } else {
                parcel.writeInt((this.mY << 16) | this.mX);
                parcel.writeInt((this.mHeight << 16) | this.mWidth);
            }
            if ((j2 & 512) != 0) {
                parcel.writeInt(this.mScrollX);
                parcel.writeInt(this.mScrollY);
            }
            if ((j2 & 8388608) != 0) {
                TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
            }
            if ((j2 & 16777216) != 0) {
                parcel.writeBundle(this.mExtras);
            }
            if ((j2 & 33554432) != 0) {
                parcel.writeParcelable(this.mLocaleList, 0);
            }
            if ((j2 & 68719476736L) != 0) {
                parcel.writeStringArray(this.mReceiveContentMimeTypes);
            }
            if ((j2 & 67108864) != 0) {
                parcel.writeInt(this.mInputType);
            }
            if ((j2 & 134217728) != 0) {
                parcel.writeInt(this.mMinEms);
            }
            if ((j2 & 268435456) != 0) {
                parcel.writeInt(this.mMaxEms);
            }
            if ((j2 & 536870912) != 0) {
                parcel.writeInt(this.mMaxLength);
            }
            if ((j2 & 1073741824) != 0) {
                parcel.writeString(this.mTextIdEntry);
            }
            if ((2147483648L & j2) != 0) {
                parcel.writeInt(this.mAutofillType);
            }
            if ((8589934592L & j2) != 0) {
                parcel.writeStringArray(this.mAutofillHints);
            }
            if ((j2 & 4294967296L) != 0) {
                parcel.writeParcelable(this.mAutofillValue, 0);
            }
            if ((17179869184L & j2) != 0) {
                parcel.writeCharSequenceArray(this.mAutofillOptions);
            }
            if ((j2 & 34359738368L) == 0) {
                parcel.writeString(this.mHintIdEntry);
                return;
            }
            return;
        }
        j = 64;
        j2 |= 256;
        if (this.mScrollX == 0) {
            j2 |= 512;
        }
        if (this.mContentDescription != null) {
        }
        if (this.mExtras != null) {
        }
        if (this.mLocaleList != null) {
        }
        if (this.mReceiveContentMimeTypes != null) {
        }
        if (this.mInputType != 0) {
        }
        if (this.mMinEms > -1) {
        }
        if (this.mMaxEms > -1) {
        }
        if (this.mMaxLength > -1) {
        }
        if (this.mTextIdEntry != null) {
        }
        if (this.mAutofillValue != null) {
        }
        if (this.mAutofillType != 0) {
        }
        if (this.mAutofillHints != null) {
        }
        if (this.mAutofillOptions != null) {
        }
        if (this.mHintIdEntry != null) {
        }
        parcel.writeLong(j2);
        if ((j2 & 32) != 0) {
        }
        if ((j2 & j) != 0) {
        }
        if ((1 & j2) != 0) {
        }
        if ((j2 & 16) != 0) {
        }
        if ((j2 & 128) != 0) {
        }
        if ((j2 & 256) == 0) {
        }
        if ((j2 & 512) != 0) {
        }
        if ((j2 & 8388608) != 0) {
        }
        if ((j2 & 16777216) != 0) {
        }
        if ((j2 & 33554432) != 0) {
        }
        if ((j2 & 68719476736L) != 0) {
        }
        if ((j2 & 67108864) != 0) {
        }
        if ((j2 & 134217728) != 0) {
        }
        if ((j2 & 268435456) != 0) {
        }
        if ((j2 & 536870912) != 0) {
        }
        if ((j2 & 1073741824) != 0) {
        }
        if ((2147483648L & j2) != 0) {
        }
        if ((8589934592L & j2) != 0) {
        }
        if ((j2 & 4294967296L) != 0) {
        }
        if ((17179869184L & j2) != 0) {
        }
        if ((j2 & 34359738368L) == 0) {
        }
    }

    public static void writeToParcel(Parcel parcel, ViewNode viewNode, int i) {
        if (viewNode == null) {
            parcel.writeLong(0L);
        } else {
            viewNode.writeSelfToParcel(parcel, i);
        }
    }

    public static ViewNode readFromParcel(Parcel parcel) {
        long j = parcel.readLong();
        if (j == 0) {
            return null;
        }
        return new ViewNode(j, parcel);
    }

    public static final class ViewStructureImpl extends ViewStructure {
        final ViewNode mNode;

        public ViewStructureImpl(View view) {
            ViewNode viewNode = new ViewNode();
            this.mNode = viewNode;
            viewNode.mAutofillId = ((View) Objects.requireNonNull(view)).getAutofillId();
            Object parent = view.getParent();
            if (parent instanceof View) {
                viewNode.mParentAutofillId = ((View) parent).getAutofillId();
            }
        }

        public ViewStructureImpl(AutofillId autofillId, long j, int i) {
            ViewNode viewNode = new ViewNode();
            this.mNode = viewNode;
            viewNode.mParentAutofillId = (AutofillId) Objects.requireNonNull(autofillId);
            viewNode.mAutofillId = new AutofillId(autofillId, j, i);
        }

        public ViewNode getNode() {
            return this.mNode;
        }

        @Override // android.view.ViewStructure
        public void setId(int i, String str, String str2, String str3) {
            this.mNode.mId = i;
            this.mNode.mIdPackage = str;
            this.mNode.mIdType = str2;
            this.mNode.mIdEntry = str3;
        }

        @Override // android.view.ViewStructure
        public void setDimens(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mNode.mX = i;
            this.mNode.mY = i2;
            this.mNode.mScrollX = i3;
            this.mNode.mScrollY = i4;
            this.mNode.mWidth = i5;
            this.mNode.mHeight = i6;
        }

        @Override // android.view.ViewStructure
        public void setTransformation(Matrix matrix) {
            Log.w(ViewNode.TAG, "setTransformation() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setElevation(float f) {
            Log.w(ViewNode.TAG, "setElevation() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setAlpha(float f) {
            Log.w(ViewNode.TAG, "setAlpha() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setVisibility(int i) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-13)) | (i & 12);
        }

        @Override // android.view.ViewStructure
        public void setAssistBlocked(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-1025)) | (z ? 1024L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setEnabled(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-2049)) | (z ? 0L : 2048L);
        }

        @Override // android.view.ViewStructure
        public void setClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-4097)) | (z ? 4096L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setLongClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-8193)) | (z ? 8192L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setContextClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-16385)) | (z ? 16384L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setFocusable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-32769)) | (z ? 32768L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setFocused(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-65537)) | (z ? 65536L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setAccessibilityFocused(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-131073)) | (z ? 131072L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setCheckable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-262145)) | (z ? 262144L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setChecked(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-524289)) | (z ? 524288L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setSelected(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-1048577)) | (z ? 1048576L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setActivated(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-2097153)) | (z ? 2097152L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setOpaque(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (viewNode.mFlags & (-4194305)) | (z ? 4194304L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setClassName(String str) {
            this.mNode.mClassName = str;
        }

        @Override // android.view.ViewStructure
        public void setContentDescription(CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        @Override // android.view.ViewStructure
        public void setText(CharSequence charSequence) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            nodeText.mTextSelectionEnd = -1;
            nodeText.mTextSelectionStart = -1;
        }

        @Override // android.view.ViewStructure
        public void setText(CharSequence charSequence, int i, int i2) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            nodeText.mTextSelectionStart = i;
            nodeText.mTextSelectionEnd = i2;
        }

        @Override // android.view.ViewStructure
        public void setTextStyle(float f, int i, int i2, int i3) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mTextColor = i;
            nodeText.mTextBackgroundColor = i2;
            nodeText.mTextSize = f;
            nodeText.mTextStyle = i3;
        }

        @Override // android.view.ViewStructure
        public void setTextLines(int[] iArr, int[] iArr2) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mLineCharOffsets = iArr;
            nodeText.mLineBaselines = iArr2;
        }

        @Override // android.view.ViewStructure
        public void setTextIdEntry(String str) {
            this.mNode.mTextIdEntry = (String) Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public void setHint(CharSequence charSequence) {
            getNodeText().mHint = charSequence != null ? charSequence.toString() : null;
        }

        @Override // android.view.ViewStructure
        public void setHintIdEntry(String str) {
            this.mNode.mHintIdEntry = (String) Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public CharSequence getText() {
            return this.mNode.getText();
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionStart() {
            return this.mNode.getTextSelectionStart();
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionEnd() {
            return this.mNode.getTextSelectionEnd();
        }

        @Override // android.view.ViewStructure
        public CharSequence getHint() {
            return this.mNode.getHint();
        }

        @Override // android.view.ViewStructure
        public Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new Bundle();
            return this.mNode.mExtras;
        }

        @Override // android.view.ViewStructure
        public boolean hasExtras() {
            return this.mNode.mExtras != null;
        }

        @Override // android.view.ViewStructure
        public void setChildCount(int i) {
            Log.w(ViewNode.TAG, "setChildCount() is not supported");
        }

        @Override // android.view.ViewStructure
        public int addChildCount(int i) {
            Log.w(ViewNode.TAG, "addChildCount() is not supported");
            return 0;
        }

        @Override // android.view.ViewStructure
        public int getChildCount() {
            Log.w(ViewNode.TAG, "getChildCount() is not supported");
            return 0;
        }

        @Override // android.view.ViewStructure
        public ViewStructure newChild(int i) {
            Log.w(ViewNode.TAG, "newChild() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public ViewStructure asyncNewChild(int i) {
            Log.w(ViewNode.TAG, "asyncNewChild() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(AutofillId autofillId) {
            this.mNode.mAutofillId = (AutofillId) Objects.requireNonNull(autofillId);
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(AutofillId autofillId, int i) {
            this.mNode.mParentAutofillId = (AutofillId) Objects.requireNonNull(autofillId);
            this.mNode.mAutofillId = new AutofillId(autofillId, i);
        }

        @Override // android.view.ViewStructure
        public void setAutofillType(int i) {
            this.mNode.mAutofillType = i;
        }

        @Override // android.view.ViewStructure
        public void setReceiveContentMimeTypes(String[] strArr) {
            this.mNode.mReceiveContentMimeTypes = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillHints(String[] strArr) {
            this.mNode.mAutofillHints = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillValue(AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override // android.view.ViewStructure
        public void setAutofillOptions(CharSequence[] charSequenceArr) {
            this.mNode.mAutofillOptions = charSequenceArr;
        }

        @Override // android.view.ViewStructure
        public void setInputType(int i) {
            this.mNode.mInputType = i;
        }

        @Override // android.view.ViewStructure
        public void setMinTextEms(int i) {
            this.mNode.mMinEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextEms(int i) {
            this.mNode.mMaxEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextLength(int i) {
            this.mNode.mMaxLength = i;
        }

        @Override // android.view.ViewStructure
        public void setDataIsSensitive(boolean z) {
            Log.w(ViewNode.TAG, "setDataIsSensitive() is not supported");
        }

        @Override // android.view.ViewStructure
        public void asyncCommit() {
            Log.w(ViewNode.TAG, "asyncCommit() is not supported");
        }

        @Override // android.view.ViewStructure
        public Rect getTempRect() {
            Log.w(ViewNode.TAG, "getTempRect() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public void setWebDomain(String str) {
            Log.w(ViewNode.TAG, "setWebDomain() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setLocaleList(LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override // android.view.ViewStructure
        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String str) {
            Log.w(ViewNode.TAG, "newHtmlInfoBuilder() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            Log.w(ViewNode.TAG, "setHtmlInfo() is not supported");
        }

        private ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
        }
    }

    static final class ViewNodeText {
        String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        CharSequence mText;
        int mTextBackgroundColor;
        int mTextColor;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
        }

        boolean isSimple() {
            return this.mTextBackgroundColor == 1 && this.mTextSelectionStart == 0 && this.mTextSelectionEnd == 0 && this.mLineCharOffsets == null && this.mLineBaselines == null && this.mHint == null;
        }

        ViewNodeText(Parcel parcel, boolean z) {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
            this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (z) {
                return;
            }
            this.mTextBackgroundColor = parcel.readInt();
            this.mTextSelectionStart = parcel.readInt();
            this.mTextSelectionEnd = parcel.readInt();
            this.mLineCharOffsets = parcel.createIntArray();
            this.mLineBaselines = parcel.createIntArray();
            this.mHint = parcel.readString();
        }

        void writeToParcel(Parcel parcel, boolean z) {
            int iMin;
            int iMin2;
            CharSequence charSequenceTrimToParcelableSize = TextUtils.trimToParcelableSize(this.mText);
            TextUtils.writeToParcel(charSequenceTrimToParcelableSize, parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (z) {
                return;
            }
            if (charSequenceTrimToParcelableSize != null) {
                iMin = Math.min(this.mTextSelectionStart, charSequenceTrimToParcelableSize.length());
            } else {
                iMin = this.mTextSelectionStart;
            }
            if (charSequenceTrimToParcelableSize != null) {
                iMin2 = Math.min(this.mTextSelectionEnd, charSequenceTrimToParcelableSize.length());
            } else {
                iMin2 = this.mTextSelectionEnd;
            }
            parcel.writeInt(this.mTextBackgroundColor);
            parcel.writeInt(iMin);
            parcel.writeInt(iMin2);
            parcel.writeIntArray(this.mLineCharOffsets);
            parcel.writeIntArray(this.mLineBaselines);
            parcel.writeString(this.mHint);
        }
    }
}
