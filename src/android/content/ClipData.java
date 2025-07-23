package android.content;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.textclassifier.TextLinks;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ClipData implements Parcelable {
    private static final String TAG = "ClipData";
    private String mCallingPackageName;
    private int mCallingUserId;
    final ClipDescription mClipDescription;
    final Bitmap mIcon;
    final ArrayList<Item> mItems;
    private boolean mParcelItemActivityInfos;
    static final String[] MIMETYPES_TEXT_PLAIN = {"text/plain"};
    static final String[] MIMETYPES_TEXT_HTML = {"text/html"};
    static final String[] MIMETYPES_TEXT_URILIST = {ClipDescription.MIMETYPE_TEXT_URILIST};
    static final String[] MIMETYPES_TEXT_INTENT = {ClipDescription.MIMETYPE_TEXT_INTENT};
    public static final Parcelable.Creator<ClipData> CREATOR = new Parcelable.Creator<ClipData>() { // from class: android.content.ClipData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClipData createFromParcel(Parcel parcel) {
            return new ClipData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClipData[] newArray(int i) {
            return new ClipData[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void prepareToLeaveProcess$ravenwood(boolean z, int i) {
    }

    public static class Item {
        private ActivityInfo mActivityInfo;
        final String mHtmlText;
        final Intent mIntent;
        final IntentSender mIntentSender;
        final CharSequence mText;
        private TextLinks mTextLinks;
        private boolean mTokenVerificationEnabled;
        Uri mUri;

        void setTokenVerificationEnabled() {
            this.mTokenVerificationEnabled = true;
        }

        public static final class Builder {
            private String mHtmlText;
            private Intent mIntent;
            private IntentSender mIntentSender;
            private CharSequence mText;
            private Uri mUri;

            public Builder setText(CharSequence charSequence) {
                this.mText = charSequence;
                return this;
            }

            public Builder setHtmlText(String str) {
                this.mHtmlText = str;
                return this;
            }

            public Builder setIntent(Intent intent) {
                this.mIntent = intent;
                return this;
            }

            public Builder setIntentSender(IntentSender intentSender) {
                if (intentSender != null && !intentSender.isImmutable()) {
                    throw new IllegalArgumentException("Expected intent sender to be immutable");
                }
                this.mIntentSender = intentSender;
                return this;
            }

            public Builder setUri(Uri uri) {
                this.mUri = uri;
                return this;
            }

            public Item build() {
                return new Item(this.mText, this.mHtmlText, this.mIntent, this.mIntentSender, this.mUri);
            }
        }

        public Item(Item item) {
            this.mText = item.mText;
            this.mHtmlText = item.mHtmlText;
            this.mIntent = item.mIntent;
            this.mIntentSender = item.mIntentSender;
            this.mUri = item.mUri;
            this.mActivityInfo = item.mActivityInfo;
            this.mTextLinks = item.mTextLinks;
        }

        public Item(CharSequence charSequence) {
            this(charSequence, null, null, null, null);
        }

        public Item(CharSequence charSequence, String str) {
            this(charSequence, str, null, null, null);
        }

        public Item(Intent intent) {
            this(null, null, intent, null, null);
        }

        public Item(Uri uri) {
            this(null, null, null, null, uri);
        }

        public Item(CharSequence charSequence, Intent intent, Uri uri) {
            this(charSequence, null, intent, null, uri);
        }

        public Item(CharSequence charSequence, String str, Intent intent, Uri uri) {
            this(charSequence, str, intent, null, uri);
        }

        private Item(CharSequence charSequence, String str, Intent intent, IntentSender intentSender, Uri uri) {
            if (str != null && charSequence == null) {
                throw new IllegalArgumentException("Plain text must be supplied if HTML text is supplied");
            }
            this.mText = charSequence;
            this.mHtmlText = str;
            this.mIntent = intent;
            this.mIntentSender = intentSender;
            this.mUri = uri;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public String getHtmlText() {
            return this.mHtmlText;
        }

        public Intent getIntent() {
            if (this.mTokenVerificationEnabled) {
                Intent.maybeMarkAsMissingCreatorToken(this.mIntent);
            }
            return this.mIntent;
        }

        public IntentSender getIntentSender() {
            return this.mIntentSender;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public ActivityInfo getActivityInfo() {
            return this.mActivityInfo;
        }

        public void setActivityInfo(ActivityInfo activityInfo) {
            this.mActivityInfo = activityInfo;
        }

        public TextLinks getTextLinks() {
            return this.mTextLinks;
        }

        public void setTextLinks(TextLinks textLinks) {
            this.mTextLinks = textLinks;
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.CharSequence coerceToText(android.content.Context r8) {
            /*
                Method dump skipped, instructions count: 217
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.ClipData.Item.coerceToText(android.content.Context):java.lang.CharSequence");
        }

        public CharSequence coerceToStyledText(Context context) {
            CharSequence text = getText();
            if (!(text instanceof Spanned)) {
                String htmlText = getHtmlText();
                if (htmlText != null) {
                    try {
                        Spanned fromHtml = Html.fromHtml(htmlText);
                        if (fromHtml != null) {
                            return fromHtml;
                        }
                    } catch (RuntimeException unused) {
                    }
                }
                if (text == null) {
                    return coerceToHtmlOrStyledText(context, true);
                }
            }
            return text;
        }

        public String coerceToHtmlText(Context context) {
            String htmlText = getHtmlText();
            if (htmlText != null) {
                return htmlText;
            }
            CharSequence text = getText();
            if (text != null) {
                if (text instanceof Spanned) {
                    return Html.toHtml((Spanned) text);
                }
                return Html.escapeHtml(text);
            }
            CharSequence coerceToHtmlOrStyledText = coerceToHtmlOrStyledText(context, false);
            if (coerceToHtmlOrStyledText != null) {
                return coerceToHtmlOrStyledText.toString();
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:104:0x00c9, code lost:
        
            if (0 == 0) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x0087, code lost:
        
            if (r1 != null) goto L116;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x0089, code lost:
        
            r1.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x0099, code lost:
        
            if (r1 != null) goto L116;
         */
        /* JADX WARN: Code restructure failed: missing block: B:97:0x00bd, code lost:
        
            if (0 != 0) goto L102;
         */
        /* JADX WARN: Code restructure failed: missing block: B:99:0x00bf, code lost:
        
            r1.close();
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v8, types: [android.text.Spanned] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.lang.CharSequence coerceToHtmlOrStyledText(android.content.Context r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 291
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.ClipData.Item.coerceToHtmlOrStyledText(android.content.Context, boolean):java.lang.CharSequence");
        }

        private String uriToHtml(String str) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("<a href=\"");
            sb.append(Html.escapeHtml(str));
            sb.append("\">");
            sb.append(Html.escapeHtml(str));
            sb.append("</a>");
            return sb.toString();
        }

        private CharSequence uriToStyledText(String str) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) str);
            spannableStringBuilder.setSpan(new URLSpan(str), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ClipData.Item { ");
            toShortString(sb, true);
            sb.append(" }");
            return sb.toString();
        }

        public void toShortString(StringBuilder sb, boolean z) {
            boolean z2;
            boolean z3 = false;
            if (this.mHtmlText != null) {
                if (z) {
                    sb.append("H(");
                    sb.append(this.mHtmlText.length());
                    sb.append(')');
                } else {
                    sb.append("H:");
                    sb.append(this.mHtmlText);
                }
                z2 = false;
            } else {
                z2 = true;
            }
            if (this.mText != null) {
                if (!z2) {
                    sb.append(' ');
                }
                if (z) {
                    sb.append("T(");
                    sb.append(this.mText.length());
                    sb.append(')');
                } else {
                    sb.append("T:");
                    sb.append(this.mText);
                }
                z2 = false;
            }
            if (this.mUri != null) {
                if (!z2) {
                    sb.append(' ');
                }
                if (z) {
                    sb.append("U(");
                    sb.append(this.mUri.getScheme());
                    sb.append(')');
                } else {
                    sb.append("U:");
                    sb.append(this.mUri);
                }
            } else {
                z3 = z2;
            }
            if (this.mIntent != null) {
                if (!z3) {
                    sb.append(' ');
                }
                sb.append("I:");
                this.mIntent.toShortString(sb, z, true, true, true);
            }
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            ProtoOutputStream protoOutputStream2;
            long start = protoOutputStream.start(j);
            String str = this.mHtmlText;
            if (str != null) {
                protoOutputStream.write(1138166333441L, str);
            } else {
                CharSequence charSequence = this.mText;
                if (charSequence != null) {
                    protoOutputStream.write(1138166333442L, charSequence.toString());
                } else {
                    Uri uri = this.mUri;
                    if (uri != null) {
                        protoOutputStream.write(1138166333443L, uri.toString());
                    } else {
                        Intent intent = this.mIntent;
                        if (intent != null) {
                            protoOutputStream2 = protoOutputStream;
                            intent.dumpDebug(protoOutputStream2, 1146756268036L, true, true, true, true);
                        } else {
                            protoOutputStream2 = protoOutputStream;
                            protoOutputStream2.write(1133871366149L, true);
                        }
                        protoOutputStream2.end(start);
                    }
                }
            }
            protoOutputStream2 = protoOutputStream;
            protoOutputStream2.end(start);
        }
    }

    public ClipData(CharSequence charSequence, String[] strArr, Item item) {
        this.mCallingUserId = -10000;
        ClipDescription clipDescription = new ClipDescription(charSequence, strArr);
        this.mClipDescription = clipDescription;
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        this.mIcon = null;
        ArrayList<Item> arrayList = new ArrayList<>();
        this.mItems = arrayList;
        arrayList.add(item);
        clipDescription.setIsStyledText(isStyledText());
    }

    public ClipData(ClipDescription clipDescription, Item item) {
        this.mCallingUserId = -10000;
        this.mClipDescription = clipDescription;
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        this.mIcon = null;
        ArrayList<Item> arrayList = new ArrayList<>();
        this.mItems = arrayList;
        arrayList.add(item);
        clipDescription.setIsStyledText(isStyledText());
    }

    public ClipData(ClipDescription clipDescription, ArrayList<Item> arrayList) {
        this.mCallingUserId = -10000;
        this.mClipDescription = clipDescription;
        if (arrayList == null) {
            throw new NullPointerException("item is null");
        }
        this.mIcon = null;
        this.mItems = arrayList;
    }

    public ClipData(ClipData clipData) {
        this.mCallingUserId = -10000;
        this.mClipDescription = clipData.mClipDescription;
        this.mIcon = clipData.mIcon;
        this.mItems = new ArrayList<>(clipData.mItems);
    }

    public ClipData copyForTransferWithActivityInfo() {
        ClipData clipData = new ClipData(this);
        clipData.mParcelItemActivityInfos = true;
        return clipData;
    }

    public boolean willParcelWithActivityInfo() {
        return this.mParcelItemActivityInfos;
    }

    public ClipData cloneOnlyUriItems() {
        int size = this.mItems.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Item item = this.mItems.get(i);
            if (item.getUri() != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size);
                }
                arrayList.add(new Item(item.getUri()));
            } else if (item.getIntent() != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size);
                }
                arrayList.add(new Item(item.getIntent().cloneForCreatorToken()));
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return new ClipData(new ClipDescription("", new String[0]), (ArrayList<Item>) arrayList);
    }

    public static ClipData newPlainText(CharSequence charSequence, CharSequence charSequence2) {
        return new ClipData(charSequence, MIMETYPES_TEXT_PLAIN, new Item(charSequence2));
    }

    public static ClipData newHtmlText(CharSequence charSequence, CharSequence charSequence2, String str) {
        return new ClipData(charSequence, MIMETYPES_TEXT_HTML, new Item(charSequence2, str));
    }

    public static ClipData newIntent(CharSequence charSequence, Intent intent) {
        return new ClipData(charSequence, MIMETYPES_TEXT_INTENT, new Item(intent));
    }

    public static ClipData newUri(ContentResolver contentResolver, CharSequence charSequence, Uri uri) {
        return new ClipData(charSequence, getMimeTypes(contentResolver, uri), new Item(uri));
    }

    private static String[] getMimeTypes(ContentResolver contentResolver, Uri uri) {
        String[] strArr;
        if ("content".equals(uri.getScheme())) {
            String type = contentResolver.getType(uri);
            strArr = contentResolver.getStreamTypes(uri, "*/*");
            if (type != null) {
                if (strArr == null) {
                    strArr = new String[]{type};
                } else if (!ArrayUtils.contains(strArr, type)) {
                    String[] strArr2 = new String[strArr.length + 1];
                    strArr2[0] = type;
                    System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
                    strArr = strArr2;
                }
            }
        } else {
            strArr = null;
        }
        return strArr == null ? MIMETYPES_TEXT_URILIST : strArr;
    }

    public static ClipData newRawUri(CharSequence charSequence, Uri uri) {
        return new ClipData(charSequence, MIMETYPES_TEXT_URILIST, new Item(uri));
    }

    public ClipDescription getDescription() {
        return this.mClipDescription;
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        this.mItems.add(item);
        if (this.mItems.size() == 1) {
            this.mClipDescription.setIsStyledText(isStyledText());
        }
    }

    public void addItem(ContentResolver contentResolver, Item item) {
        addItem(item);
        if (item.getHtmlText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_HTML);
        } else if (item.getText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_PLAIN);
        }
        if (item.getIntent() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_INTENT);
        }
        if (item.getUri() != null) {
            this.mClipDescription.addMimeTypes(getMimeTypes(contentResolver, item.getUri()));
        }
    }

    public Bitmap getIcon() {
        return this.mIcon;
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    public Item getItemAt(int i) {
        return this.mItems.get(i);
    }

    public void setItemAt(int i, Item item) {
        this.mItems.set(i, item);
    }

    public void prepareToLeaveProcess(boolean z) {
        prepareToLeaveProcess(z, 1);
    }

    public void prepareToLeaveProcess(boolean z, int i) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            Item item = this.mItems.get(i2);
            if (item.mIntent != null) {
                item.mIntent.prepareToLeaveProcess(z, false);
            }
            if (item.mUri != null && z) {
                if (StrictMode.vmFileUriExposureEnabled()) {
                    item.mUri.checkFileUriExposed("ClipData.Item.getUri()");
                }
                if (StrictMode.vmContentUriWithoutPermissionEnabled()) {
                    item.mUri.checkContentUriWithoutPermission("ClipData.Item.getUri()", i);
                }
            }
        }
    }

    public void prepareToEnterProcess(AttributionSource attributionSource) {
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            Item item = this.mItems.get(i);
            if (item.mIntent != null) {
                item.mIntent.prepareToEnterProcess(false, attributionSource);
            }
        }
    }

    public void fixUris(int i) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            Item item = this.mItems.get(i2);
            if (item.mIntent != null) {
                item.mIntent.fixUris(i);
            }
            if (item.mUri != null) {
                item.mUri = ContentProvider.maybeAddUserId(item.mUri, i);
            }
        }
    }

    public void fixUrisLight(int i) {
        Uri data;
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            Item item = this.mItems.get(i2);
            if (item.mIntent != null && (data = item.mIntent.getData()) != null) {
                item.mIntent.setData(ContentProvider.maybeAddUserId(data, i));
            }
            if (item.mUri != null) {
                item.mUri = ContentProvider.maybeAddUserId(item.mUri, i);
            }
        }
    }

    private boolean isStyledText() {
        if (this.mItems.isEmpty()) {
            return false;
        }
        CharSequence text = this.mItems.get(0).getText();
        return (text instanceof Spanned) && TextUtils.hasStyleSpan((Spanned) text);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("ClipData { ");
        toShortString(sb, true);
        sb.append(" }");
        return sb.toString();
    }

    public void toShortString(StringBuilder sb, boolean z) {
        boolean z2 = this.mClipDescription != null ? !r0.toShortString(sb, z) : true;
        if (this.mIcon != null) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append("I:");
            sb.append(this.mIcon.getWidth());
            sb.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            sb.append(this.mIcon.getHeight());
            z2 = false;
        }
        if (this.mItems.size() != 1) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append(this.mItems.size());
            sb.append(" items:");
            z2 = false;
        }
        int i = 0;
        while (i < this.mItems.size()) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append('{');
            this.mItems.get(i).toShortString(sb, z);
            sb.append('}');
            i++;
            z2 = false;
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        ClipDescription clipDescription = this.mClipDescription;
        if (clipDescription != null) {
            clipDescription.dumpDebug(protoOutputStream, 1146756268033L);
        }
        if (this.mIcon != null) {
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, this.mIcon.getWidth());
            protoOutputStream.write(1120986464258L, this.mIcon.getHeight());
            protoOutputStream.end(start2);
        }
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).dumpDebug(protoOutputStream, 2246267895811L);
        }
        protoOutputStream.end(start);
    }

    public void collectUris(List<Uri> list) {
        for (int i = 0; i < this.mItems.size(); i++) {
            Item itemAt = getItemAt(i);
            if (itemAt.getUri() != null) {
                list.add(itemAt.getUri());
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                if (intent.getData() != null) {
                    list.add(intent.getData());
                }
                if (intent.getClipData() != null) {
                    intent.getClipData().collectUris(list);
                }
            }
        }
    }

    void setTokenVerificationEnabled() {
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).setTokenVerificationEnabled();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mClipDescription.writeToParcel(parcel, i);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        int size = this.mItems.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Item item = this.mItems.get(i2);
            TextUtils.writeToParcel(item.mText, parcel, i);
            parcel.writeString8(item.mHtmlText);
            parcel.writeTypedObject(item.mIntent, i);
            parcel.writeTypedObject(item.mIntentSender, i);
            parcel.writeTypedObject(item.mUri, i);
            parcel.writeTypedObject(this.mParcelItemActivityInfos ? item.mActivityInfo : null, i);
            parcel.writeTypedObject(item.mTextLinks, i);
        }
        parcel.writeInt(this.mCallingUserId);
        parcel.writeString(this.mCallingPackageName);
    }

    ClipData(Parcel parcel) {
        this.mCallingUserId = -10000;
        this.mClipDescription = new ClipDescription(parcel);
        if (parcel.readInt() != 0) {
            this.mIcon = Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mItems = new ArrayList<>();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            CharSequence createFromParcel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            String readString8 = parcel.readString8();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
            TextLinks textLinks = (TextLinks) parcel.readTypedObject(TextLinks.CREATOR);
            Item item = new Item(createFromParcel, readString8, intent, intentSender, uri);
            item.setActivityInfo(activityInfo);
            item.setTextLinks(textLinks);
            this.mItems.add(item);
        }
        this.mCallingUserId = parcel.readInt();
        this.mCallingPackageName = parcel.readString();
    }

    public ArrayList<Item> semGetItems() {
        if (this.mItems == null) {
            Log.w(TAG, "get ClipData items - null");
        }
        return this.mItems;
    }

    public void setCallingUserId(int i) {
        this.mCallingUserId = i;
    }

    public int getCallingUserId() {
        return this.mCallingUserId;
    }

    public void setCallingPackageName(String str) {
        this.mCallingPackageName = str;
    }

    public String getCallingPackageName() {
        return this.mCallingPackageName;
    }
}
