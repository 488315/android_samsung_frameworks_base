package com.android.internal.pm.pkg.parsing;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedIntentInfoImpl;
import com.android.internal.util.Parcelling;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsingUtils {
    public static final String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    public static final int DEFAULT_MAX_SDK_VERSION = Integer.MAX_VALUE;
    public static final int DEFAULT_MIN_SDK_VERSION = 1;
    public static final int DEFAULT_TARGET_SDK_VERSION = 0;
    public static final int NOT_SET = -1;
    public static final String TAG = "PackageParsing";

    public static String buildClassName(String pkg, CharSequence clsSeq) {
        if (clsSeq == null || clsSeq.length() <= 0) {
            return null;
        }
        String cls = clsSeq.toString();
        char c = cls.charAt(0);
        if (c == '.') {
            return pkg + cls;
        }
        if (cls.indexOf(46) < 0) {
            return pkg + '.' + cls;
        }
        return cls;
    }

    public static ParseResult unknownTag(String parentTag, ParsingPackage pkg, XmlResourceParser parser, ParseInput input) throws IOException, XmlPullParserException {
        Slog.w(TAG, "Unknown element under " + parentTag + ": " + parser.getName() + " at " + pkg.getBaseApkPath() + " " + parser.getPositionDescription());
        XmlUtils.skipCurrentTag(parser);
        return input.success(null);
    }

    public static <Interface, Impl extends Interface> List<Interface> createTypedInterfaceList(Parcel parcel, Parcelable.Creator<Impl> creator) {
        int size = parcel.readInt();
        if (size < 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(size);
        while (size > 0) {
            arrayList.add(parcel.readTypedObject(creator));
            size--;
        }
        return arrayList;
    }

    public static void writeParcelableList(Parcel parcel, List<?> list) {
        if (list == null) {
            parcel.writeInt(-1);
            return;
        }
        int size = list.size();
        parcel.writeInt(size);
        for (int index = 0; index < size; index++) {
            parcel.writeTypedObject((Parcelable) list.get(index), 0);
        }
    }

    public static class StringPairListParceler implements Parcelling<List<Pair<String, ParsedIntentInfo>>> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(List<Pair<String, ParsedIntentInfo>> item, Parcel dest, int parcelFlags) {
            if (item == null) {
                dest.writeInt(-1);
                return;
            }
            int size = item.size();
            dest.writeInt(size);
            for (int index = 0; index < size; index++) {
                Pair<String, ParsedIntentInfo> pair = item.get(index);
                dest.writeString(pair.first);
                dest.writeParcelable((Parcelable) pair.second, parcelFlags);
            }
        }

        @Override // com.android.internal.util.Parcelling
        public List<Pair<String, ParsedIntentInfo>> unparcel(Parcel source) {
            int size = source.readInt();
            if (size == -1) {
                return null;
            }
            if (size == 0) {
                return new ArrayList(0);
            }
            List<Pair<String, ParsedIntentInfo>> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(Pair.create(source.readString(), (ParsedIntentInfo) source.readParcelable(ParsedIntentInfoImpl.class.getClassLoader(), ParsedIntentInfo.class)));
            }
            return list;
        }
    }

    public static ParseResult<Set<String>> parseKnownActivityEmbeddingCerts(TypedArray sa, Resources res, int resourceId, ParseInput input) {
        if (!sa.hasValue(resourceId)) {
            return input.success(null);
        }
        int knownActivityEmbeddingCertsResource = sa.getResourceId(resourceId, 0);
        if (knownActivityEmbeddingCertsResource != 0) {
            Set<String> knownEmbeddingCertificates = null;
            String resourceType = res.getResourceTypeName(knownActivityEmbeddingCertsResource);
            if (resourceType.equals("array")) {
                String[] knownCerts = res.getStringArray(knownActivityEmbeddingCertsResource);
                if (knownCerts != null) {
                    knownEmbeddingCertificates = Set.of((Object[]) knownCerts);
                }
            } else {
                String knownCert = res.getString(knownActivityEmbeddingCertsResource);
                if (knownCert != null) {
                    knownEmbeddingCertificates = Set.of(knownCert);
                }
            }
            if (knownEmbeddingCertificates == null || knownEmbeddingCertificates.isEmpty()) {
                return input.error("Defined a knownActivityEmbeddingCerts attribute but the provided resource is null");
            }
            return input.success(knownEmbeddingCertificates);
        }
        String knownCert2 = sa.getString(resourceId);
        if (knownCert2 == null || knownCert2.isEmpty()) {
            return input.error("Defined a knownActivityEmbeddingCerts attribute but the provided string is empty");
        }
        return input.success(Set.of(knownCert2));
    }
}
