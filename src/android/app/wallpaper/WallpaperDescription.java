package android.app.wallpaper;

import android.annotation.SystemApi;
import android.app.WallpaperManager;
import android.app.wallpaper.WallpaperDescription;
import android.content.ComponentName;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class WallpaperDescription implements Parcelable {
    public static final Parcelable.Creator<WallpaperDescription> CREATOR = new Parcelable.Creator<WallpaperDescription>() { // from class: android.app.wallpaper.WallpaperDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperDescription createFromParcel(Parcel parcel) {
            return new WallpaperDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperDescription[] newArray(int i) {
            return new WallpaperDescription[i];
        }
    };
    private static final String TAG = "WallpaperDescription";
    private static final String XML_TAG_CONTENT = "content";
    private static final String XML_TAG_DESCRIPTION = "description";
    private final ComponentName mComponent;
    private final PersistableBundle mContent;
    private final CharSequence mContextDescription;
    private final Uri mContextUri;
    private final SparseArray<Rect> mCropHints;
    private final List<CharSequence> mDescription;
    private final String mId;
    private final float mSampleSize;
    private final Uri mThumbnail;
    private final CharSequence mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private WallpaperDescription(ComponentName componentName, String str, Uri uri, CharSequence charSequence, List<CharSequence> list, Uri uri2, CharSequence charSequence2, PersistableBundle persistableBundle, SparseArray<Rect> sparseArray, float f) {
        this.mComponent = componentName;
        this.mId = str;
        this.mThumbnail = uri;
        this.mTitle = charSequence;
        this.mDescription = list == null ? new ArrayList<>() : list;
        this.mContextUri = uri2;
        this.mContextDescription = charSequence2;
        this.mContent = persistableBundle == null ? new PersistableBundle() : persistableBundle;
        this.mCropHints = sparseArray;
        this.mSampleSize = f;
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    public String getId() {
        return this.mId;
    }

    public Uri getThumbnail() {
        return this.mThumbnail;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public List<CharSequence> getDescription() {
        return this.mDescription;
    }

    public Uri getContextUri() {
        return this.mContextUri;
    }

    public CharSequence getContextDescription() {
        return this.mContextDescription;
    }

    public PersistableBundle getContent() {
        return this.mContent;
    }

    @SystemApi
    public SparseArray<Rect> getCropHints() {
        return this.mCropHints;
    }

    public float getSampleSize() {
        return this.mSampleSize;
    }

    public String toString() {
        ComponentName componentName = this.mComponent;
        return (componentName != null ? componentName.toString() : "{null}") + ":" + this.mId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WallpaperDescription) {
            WallpaperDescription wallpaperDescription = (WallpaperDescription) obj;
            if (Objects.equals(this.mComponent, wallpaperDescription.mComponent) && Objects.equals(this.mId, wallpaperDescription.mId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mComponent, this.mId);
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException, XmlPullParserException {
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            typedXmlSerializer.attribute(null, "component", componentName.flattenToShortString());
        }
        String str = this.mId;
        if (str != null) {
            typedXmlSerializer.attribute(null, "id", str);
        }
        Uri uri = this.mThumbnail;
        if (uri != null) {
            typedXmlSerializer.attribute(null, "thumbnail", uri.toString());
        }
        CharSequence charSequence = this.mTitle;
        if (charSequence != null) {
            typedXmlSerializer.attribute(null, "title", toHtml(charSequence));
        }
        Uri uri2 = this.mContextUri;
        if (uri2 != null) {
            typedXmlSerializer.attribute(null, "contexturi", uri2.toString());
        }
        CharSequence charSequence2 = this.mContextDescription;
        if (charSequence2 != null) {
            typedXmlSerializer.attribute(null, "contextdescription", toHtml(charSequence2));
        }
        for (Pair<Integer, String> pair : screenDimensionPairs()) {
            int intValue = pair.first.intValue();
            String str2 = pair.second;
            Rect rect = this.mCropHints.get(intValue);
            if (rect != null) {
                typedXmlSerializer.attributeInt(null, "cropLeft" + str2, rect.left);
                typedXmlSerializer.attributeInt(null, "cropTop" + str2, rect.top);
                typedXmlSerializer.attributeInt(null, "cropRight" + str2, rect.right);
                typedXmlSerializer.attributeInt(null, "cropBottom" + str2, rect.bottom);
            }
        }
        typedXmlSerializer.attributeFloat(null, "sampleSize", this.mSampleSize);
        typedXmlSerializer.startTag(null, "description");
        Iterator<CharSequence> it = this.mDescription.iterator();
        while (it.hasNext()) {
            typedXmlSerializer.attribute(null, "descriptionline", toHtml(it.next()));
        }
        typedXmlSerializer.endTag(null, "description");
        try {
            typedXmlSerializer.startTag(null, "content");
            this.mContent.saveToXml(typedXmlSerializer);
        } catch (XmlPullParserException unused) {
            Log.e(TAG, "unable to convert wallpaper content to XML");
        } finally {
            typedXmlSerializer.endTag(null, "content");
        }
    }

    public static WallpaperDescription restoreFromXml(final TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        String attributeValue = typedXmlPullParser.getAttributeValue(null, "component");
        ComponentName unflattenFromString = attributeValue != null ? ComponentName.unflattenFromString(attributeValue) : null;
        String attributeValue2 = typedXmlPullParser.getAttributeValue(null, "id");
        String attributeValue3 = typedXmlPullParser.getAttributeValue(null, "thumbnail");
        Uri parse = attributeValue3 != null ? Uri.parse(attributeValue3) : null;
        CharSequence fromHtml = fromHtml(typedXmlPullParser.getAttributeValue(null, "title"));
        String attributeValue4 = typedXmlPullParser.getAttributeValue(null, "contexturi");
        Uri parse2 = attributeValue4 != null ? Uri.parse(attributeValue4) : null;
        CharSequence fromHtml2 = fromHtml(typedXmlPullParser.getAttributeValue(null, "contextdescription"));
        final SparseArray sparseArray = new SparseArray();
        screenDimensionPairs().forEach(new Consumer() { // from class: android.app.wallpaper.WallpaperDescription$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WallpaperDescription.lambda$restoreFromXml$0(TypedXmlPullParser.this, sparseArray, (Pair) obj);
            }
        });
        float attributeFloat = typedXmlPullParser.getAttributeFloat(null, "sampleSize", 1.0f);
        ArrayList arrayList = new ArrayList();
        PersistableBundle persistableBundle = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if ("description".equals(name)) {
                    for (int i = 0; i < typedXmlPullParser.getAttributeCount(); i++) {
                        arrayList.add(fromHtml(typedXmlPullParser.getAttributeValue(i)));
                    }
                } else if ("content".equals(name)) {
                    persistableBundle = PersistableBundle.restoreFromXml(typedXmlPullParser);
                }
            }
        }
        return new WallpaperDescription(unflattenFromString, attributeValue2, parse, fromHtml, arrayList, parse2, fromHtml2, persistableBundle, sparseArray, attributeFloat);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$restoreFromXml$0(TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray, Pair pair) {
        int intValue = ((Integer) pair.first).intValue();
        String str = (String) pair.second;
        Rect rect = new Rect(typedXmlPullParser.getAttributeInt(null, "cropLeft" + str, 0), typedXmlPullParser.getAttributeInt(null, "cropTop" + str, 0), typedXmlPullParser.getAttributeInt(null, "cropRight" + str, 0), typedXmlPullParser.getAttributeInt(null, "cropBottom" + str, 0));
        if (rect.isEmpty()) {
            return;
        }
        sparseArray.put(intValue, rect);
    }

    private static String toHtml(CharSequence charSequence) {
        return Html.toHtml(charSequence instanceof Spanned ? (Spanned) charSequence : new SpannedString(charSequence), 1);
    }

    private static CharSequence fromHtml(String str) {
        if (str == null) {
            return null;
        }
        return removeTrailingWhitespace(Html.fromHtml(str, 63));
    }

    private static CharSequence removeTrailingWhitespace(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        while (length > 0 && Character.isWhitespace(charSequence.charAt(length - 1))) {
            length--;
        }
        return charSequence.subSequence(0, length);
    }

    WallpaperDescription(final Parcel parcel) {
        this.mComponent = ComponentName.readFromParcel(parcel);
        this.mId = parcel.readString8();
        this.mThumbnail = Uri.CREATOR.createFromParcel(parcel);
        this.mTitle = parcel.readCharSequence();
        this.mDescription = Arrays.stream(parcel.readCharSequenceArray()).toList();
        this.mContextUri = Uri.CREATOR.createFromParcel(parcel);
        this.mContextDescription = parcel.readCharSequence();
        this.mContent = PersistableBundle.CREATOR.createFromParcel(parcel);
        this.mCropHints = new SparseArray<>();
        screenDimensionPairs().forEach(new Consumer() { // from class: android.app.wallpaper.WallpaperDescription$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WallpaperDescription.this.lambda$new$1(parcel, (Pair) obj);
            }
        });
        this.mSampleSize = parcel.readFloat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$new$1(Parcel parcel, Pair pair) {
        int intValue = ((Integer) pair.first).intValue();
        Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        if (rect != null) {
            this.mCropHints.put(intValue, rect);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(final Parcel parcel, final int i) {
        ComponentName.writeToParcel(this.mComponent, parcel);
        parcel.writeString8(this.mId);
        Uri.writeToParcel(parcel, this.mThumbnail);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequenceArray((CharSequence[]) this.mDescription.toArray(new CharSequence[0]));
        Uri.writeToParcel(parcel, this.mContextUri);
        parcel.writeCharSequence(this.mContextDescription);
        parcel.writePersistableBundle(this.mContent);
        screenDimensionPairs().forEach(new Consumer() { // from class: android.app.wallpaper.WallpaperDescription$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WallpaperDescription.this.lambda$writeToParcel$2(parcel, i, (Pair) obj);
            }
        });
        parcel.writeFloat(this.mSampleSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$writeToParcel$2(Parcel parcel, int i, Pair pair) {
        parcel.writeTypedObject(this.mCropHints.get(((Integer) pair.first).intValue()), i);
    }

    public Builder toBuilder() {
        return new Builder().setComponent(this.mComponent).setId(this.mId).setThumbnail(this.mThumbnail).setTitle(this.mTitle).setDescription(this.mDescription).setContextUri(this.mContextUri).setContextDescription(this.mContextDescription).setContent(this.mContent).setCropHints(this.mCropHints).setSampleSize(this.mSampleSize);
    }

    public static final class Builder {
        private ComponentName mComponent;
        private CharSequence mContextDescription;
        private Uri mContextUri;
        private String mId;
        private Uri mThumbnail;
        private CharSequence mTitle;
        private List<CharSequence> mDescription = new ArrayList();
        private PersistableBundle mContent = new PersistableBundle();
        private SparseArray<Rect> mCropHints = new SparseArray<>();
        private float mSampleSize = 1.0f;

        public Builder setComponent(ComponentName componentName) {
            this.mComponent = componentName;
            return this;
        }

        public Builder setId(String str) {
            this.mId = str;
            return this;
        }

        public Builder setThumbnail(Uri uri) {
            this.mThumbnail = uri;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Builder setDescription(List<CharSequence> list) {
            this.mDescription = list;
            return this;
        }

        public Builder setContextUri(Uri uri) {
            this.mContextUri = uri;
            return this;
        }

        public Builder setContextDescription(CharSequence charSequence) {
            this.mContextDescription = charSequence;
            return this;
        }

        public Builder setContent(PersistableBundle persistableBundle) {
            this.mContent = persistableBundle;
            return this;
        }

        @SystemApi
        public Builder setCropHints(Map<Point, Rect> map) {
            this.mCropHints = new SparseArray<>();
            map.forEach(new BiConsumer() { // from class: android.app.wallpaper.WallpaperDescription$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    WallpaperDescription.Builder.this.lambda$setCropHints$0((Point) obj, (Rect) obj2);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setCropHints$0(Point point, Rect rect) {
            this.mCropHints.put(WallpaperManager.getOrientation(point), rect);
        }

        @SystemApi
        public Builder setCropHints(SparseArray<Rect> sparseArray) {
            this.mCropHints = sparseArray;
            return this;
        }

        public Builder setSampleSize(float f) {
            this.mSampleSize = f;
            return this;
        }

        public WallpaperDescription build() {
            return new WallpaperDescription(this.mComponent, this.mId, this.mThumbnail, this.mTitle, this.mDescription, this.mContextUri, this.mContextDescription, this.mContent, this.mCropHints, this.mSampleSize);
        }
    }

    private static List<Pair<Integer, String>> screenDimensionPairs() {
        return List.of(new Pair(0, "Portrait"), new Pair(1, "Landscape"), new Pair(2, "SquarePortrait"), new Pair(3, "SquareLandscape"));
    }
}
