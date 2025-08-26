package android.content;

import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import libcore.io.IoUtils;

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

        /* JADX WARN: Removed duplicated region for block: B:45:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public CharSequence coerceToText(Context context) throws Throwable {
            ContentResolver contentResolver;
            FileInputStream fileInputStream;
            InputStreamReader inputStreamReader;
            AssetFileDescriptor assetFileDescriptorOpenTypedAssetFileDescriptor;
            IOException e;
            FileInputStream fileInputStreamCreateInputStream;
            Throwable th;
            String string;
            CharSequence text = getText();
            if (text != null) {
                return text;
            }
            AssetFileDescriptor assetFileDescriptor = null;
            try {
                contentResolver = context.getContentResolver();
            } catch (Exception e2) {
                Log.w(ClipData.TAG, "Failed to obtain ContentResolver: " + e2);
                contentResolver = null;
            }
            Uri uri = getUri();
            if (uri != null) {
                try {
                    if (contentResolver != null) {
                        try {
                            assetFileDescriptorOpenTypedAssetFileDescriptor = contentResolver.openTypedAssetFileDescriptor(uri, "text/*", null);
                        } catch (FileNotFoundException | RuntimeException unused) {
                            assetFileDescriptorOpenTypedAssetFileDescriptor = null;
                            if (assetFileDescriptorOpenTypedAssetFileDescriptor != null) {
                            }
                        } catch (SecurityException e3) {
                            Log.w(ClipData.TAG, "Failure opening stream", e3);
                            assetFileDescriptorOpenTypedAssetFileDescriptor = null;
                            if (assetFileDescriptorOpenTypedAssetFileDescriptor != null) {
                            }
                        }
                        if (assetFileDescriptorOpenTypedAssetFileDescriptor != null) {
                            try {
                                fileInputStreamCreateInputStream = assetFileDescriptorOpenTypedAssetFileDescriptor.createInputStream();
                                try {
                                    inputStreamReader = new InputStreamReader(fileInputStreamCreateInputStream, "UTF-8");
                                    try {
                                        try {
                                            StringBuilder sb = new StringBuilder(128);
                                            char[] cArr = new char[8192];
                                            while (true) {
                                                int i = inputStreamReader.read(cArr);
                                                if (i <= 0) {
                                                    break;
                                                }
                                                sb.append(cArr, 0, i);
                                            }
                                            string = sb.toString();
                                        } catch (IOException e4) {
                                            e = e4;
                                            Log.w(ClipData.TAG, "Failure loading text", e);
                                            string = e.toString();
                                            IoUtils.closeQuietly(assetFileDescriptorOpenTypedAssetFileDescriptor);
                                            IoUtils.closeQuietly(fileInputStreamCreateInputStream);
                                            IoUtils.closeQuietly(inputStreamReader);
                                            return string;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        assetFileDescriptor = assetFileDescriptorOpenTypedAssetFileDescriptor;
                                        fileInputStream = fileInputStreamCreateInputStream;
                                        th = th;
                                        IoUtils.closeQuietly(assetFileDescriptor);
                                        IoUtils.closeQuietly(fileInputStream);
                                        IoUtils.closeQuietly(inputStreamReader);
                                        throw th;
                                    }
                                } catch (IOException e5) {
                                    inputStreamReader = null;
                                    e = e5;
                                } catch (Throwable th3) {
                                    th = th3;
                                    inputStreamReader = null;
                                    assetFileDescriptor = assetFileDescriptorOpenTypedAssetFileDescriptor;
                                    fileInputStream = fileInputStreamCreateInputStream;
                                    th = th;
                                    IoUtils.closeQuietly(assetFileDescriptor);
                                    IoUtils.closeQuietly(fileInputStream);
                                    IoUtils.closeQuietly(inputStreamReader);
                                    throw th;
                                }
                            } catch (IOException e6) {
                                inputStreamReader = null;
                                e = e6;
                                fileInputStreamCreateInputStream = null;
                            } catch (Throwable th4) {
                                th = th4;
                                inputStreamReader = null;
                                assetFileDescriptor = assetFileDescriptorOpenTypedAssetFileDescriptor;
                                fileInputStream = null;
                                IoUtils.closeQuietly(assetFileDescriptor);
                                IoUtils.closeQuietly(fileInputStream);
                                IoUtils.closeQuietly(inputStreamReader);
                                throw th;
                            }
                            IoUtils.closeQuietly(assetFileDescriptorOpenTypedAssetFileDescriptor);
                            IoUtils.closeQuietly(fileInputStreamCreateInputStream);
                            IoUtils.closeQuietly(inputStreamReader);
                            return string;
                        }
                        IoUtils.closeQuietly(assetFileDescriptorOpenTypedAssetFileDescriptor);
                        IoUtils.closeQuietly((AutoCloseable) null);
                        IoUtils.closeQuietly((AutoCloseable) null);
                    }
                } catch (Throwable th5) {
                    th = th5;
                    fileInputStream = null;
                    inputStreamReader = null;
                }
            }
            if (uri != null) {
                String scheme = uri.getScheme();
                if ("content".equals(scheme) || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme) || "file".equals(scheme)) {
                    return "";
                }
                return uri.toString();
            }
            Intent intent = getIntent();
            if (intent == null) {
                return "";
            }
            return intent.toUri(1);
        }

        public CharSequence coerceToStyledText(Context context) {
            CharSequence text = getText();
            if (!(text instanceof Spanned)) {
                String htmlText = getHtmlText();
                if (htmlText != null) {
                    try {
                        Spanned spannedFromHtml = Html.fromHtml(htmlText);
                        if (spannedFromHtml != null) {
                            return spannedFromHtml;
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

        public String coerceToHtmlText(Context context) throws IOException {
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
            CharSequence charSequenceCoerceToHtmlOrStyledText = coerceToHtmlOrStyledText(context, false);
            if (charSequenceCoerceToHtmlOrStyledText != null) {
                return charSequenceCoerceToHtmlOrStyledText.toString();
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:102:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r13v8, types: [android.text.Spanned] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private CharSequence coerceToHtmlOrStyledText(Context context, boolean z) throws IOException {
            String[] streamTypes;
            boolean z2;
            boolean z3;
            if (this.mUri == null) {
                Intent intent = this.mIntent;
                return intent != null ? z ? uriToStyledText(intent.toUri(1)) : uriToHtml(intent.toUri(1)) : "";
            }
            FileInputStream fileInputStream = null;
            try {
                streamTypes = context.getContentResolver().getStreamTypes(this.mUri, "text/*");
            } catch (SecurityException unused) {
                streamTypes = null;
            }
            String str = "text/html";
            if (streamTypes != null) {
                z2 = false;
                z3 = false;
                for (String str2 : streamTypes) {
                    if ("text/html".equals(str2)) {
                        z2 = true;
                    } else if (str2.startsWith("text/")) {
                        z3 = true;
                    }
                }
            } else {
                z2 = false;
                z3 = false;
            }
            if (z2 || z3) {
                try {
                    try {
                        try {
                            ContentResolver contentResolver = context.getContentResolver();
                            Uri uri = this.mUri;
                            if (!z2) {
                                str = "text/plain";
                            }
                            FileInputStream fileInputStreamCreateInputStream = contentResolver.openTypedAssetFileDescriptor(uri, str, null).createInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStreamCreateInputStream, "UTF-8");
                            StringBuilder sb = new StringBuilder(128);
                            char[] cArr = new char[8192];
                            while (true) {
                                int i = inputStreamReader.read(cArr);
                                if (i <= 0) {
                                    break;
                                }
                                sb.append(cArr, 0, i);
                            }
                            String string = sb.toString();
                            if (z2) {
                                if (!z) {
                                    String string2 = string.toString();
                                    if (fileInputStreamCreateInputStream != null) {
                                        try {
                                            fileInputStreamCreateInputStream.close();
                                        } catch (IOException unused2) {
                                        }
                                    }
                                    return string2;
                                }
                                try {
                                    ?? FromHtml = Html.fromHtml(string);
                                    if (FromHtml != 0) {
                                        string = FromHtml;
                                    }
                                    if (fileInputStreamCreateInputStream != null) {
                                        try {
                                            fileInputStreamCreateInputStream.close();
                                        } catch (IOException unused3) {
                                        }
                                    }
                                    return string;
                                } catch (RuntimeException unused4) {
                                    if (fileInputStreamCreateInputStream != null) {
                                    }
                                }
                            } else {
                                if (!z) {
                                    String strEscapeHtml = Html.escapeHtml(string);
                                    if (fileInputStreamCreateInputStream != null) {
                                        try {
                                            fileInputStreamCreateInputStream.close();
                                        } catch (IOException unused5) {
                                        }
                                    }
                                    return strEscapeHtml;
                                }
                                if (fileInputStreamCreateInputStream != null) {
                                    try {
                                        fileInputStreamCreateInputStream.close();
                                    } catch (IOException unused6) {
                                    }
                                }
                            }
                            return string;
                        } catch (Throwable th) {
                            if (0 != 0) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused7) {
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        Log.w(ClipData.TAG, "Failure loading text", e);
                        String strEscapeHtml2 = Html.escapeHtml(e.toString());
                        if (0 != 0) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused8) {
                            }
                        }
                        return strEscapeHtml2;
                    }
                } catch (FileNotFoundException unused9) {
                    if (0 != 0) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused10) {
                        }
                    }
                } catch (SecurityException e2) {
                    Log.w(ClipData.TAG, "Failure opening stream", e2);
                    if (0 != 0) {
                    }
                }
            }
            String scheme = this.mUri.getScheme();
            return ("content".equals(scheme) || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme) || "file".equals(scheme)) ? "" : z ? uriToStyledText(this.mUri.toString()) : uriToHtml(this.mUri.toString());
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
            long jStart = protoOutputStream.start(j);
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
                        protoOutputStream2.end(jStart);
                    }
                }
            }
            protoOutputStream2 = protoOutputStream;
            protoOutputStream2.end(jStart);
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
        String[] streamTypes;
        if ("content".equals(uri.getScheme())) {
            String type = contentResolver.getType(uri);
            streamTypes = contentResolver.getStreamTypes(uri, "*/*");
            if (type != null) {
                if (streamTypes == null) {
                    streamTypes = new String[]{type};
                } else if (!ArrayUtils.contains(streamTypes, type)) {
                    String[] strArr = new String[streamTypes.length + 1];
                    strArr[0] = type;
                    System.arraycopy(streamTypes, 0, strArr, 1, streamTypes.length);
                    streamTypes = strArr;
                }
            }
        } else {
            streamTypes = null;
        }
        return streamTypes == null ? MIMETYPES_TEXT_URILIST : streamTypes;
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
        long jStart = protoOutputStream.start(j);
        ClipDescription clipDescription = this.mClipDescription;
        if (clipDescription != null) {
            clipDescription.dumpDebug(protoOutputStream, 1146756268033L);
        }
        if (this.mIcon != null) {
            long jStart2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, this.mIcon.getWidth());
            protoOutputStream.write(1120986464258L, this.mIcon.getHeight());
            protoOutputStream.end(jStart2);
        }
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).dumpDebug(protoOutputStream, 2246267895811L);
        }
        protoOutputStream.end(jStart);
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
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            CharSequence charSequenceCreateFromParcel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            String string8 = parcel.readString8();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
            TextLinks textLinks = (TextLinks) parcel.readTypedObject(TextLinks.CREATOR);
            Item item = new Item(charSequenceCreateFromParcel, string8, intent, intentSender, uri);
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
