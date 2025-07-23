package android.content;

import android.annotation.SystemApi;
import android.hardware.scontext.SContextConstants;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class IntentFilter implements Parcelable {
    private static final String ACTION_STR = "action";
    private static final String AGLOB_STR = "aglob";
    private static final String AUTH_STR = "auth";
    private static final String AUTO_VERIFY_STR = "autoVerify";
    public static final long BLOCK_NULL_ACTION_INTENTS = 293560872;
    private static final String CAT_STR = "cat";
    private static final String EXTRAS_STR = "extras";
    private static final String GROUP_STR = "group";
    private static final String HOST_STR = "host";
    private static final String LITERAL_STR = "literal";
    public static final int MATCH_ADJUSTMENT_MASK = 65535;
    public static final int MATCH_ADJUSTMENT_NORMAL = 32768;
    public static final int MATCH_CATEGORY_EMPTY = 1048576;
    public static final int MATCH_CATEGORY_HOST = 3145728;
    public static final int MATCH_CATEGORY_MASK = 268369920;
    public static final int MATCH_CATEGORY_PATH = 5242880;
    public static final int MATCH_CATEGORY_PORT = 4194304;
    public static final int MATCH_CATEGORY_SCHEME = 2097152;
    public static final int MATCH_CATEGORY_SCHEME_SPECIFIC_PART = 5767168;
    public static final int MATCH_CATEGORY_TYPE = 6291456;
    private static final String NAME_STR = "name";
    public static final int NO_MATCH_ACTION = -3;
    public static final int NO_MATCH_CATEGORY = -4;
    public static final int NO_MATCH_DATA = -2;
    public static final int NO_MATCH_EXTRAS = -5;
    public static final int NO_MATCH_TYPE = -1;
    private static final String PATH_STR = "path";
    private static final String PORT_STR = "port";
    private static final String PREFIX_STR = "prefix";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final String SCHEME_PACKAGE = "package";
    private static final String SCHEME_STR = "scheme";
    private static final String SGLOB_STR = "sglob";
    private static final String SSP_STR = "ssp";
    private static final int STATE_NEED_VERIFY = 16;
    private static final int STATE_NEED_VERIFY_CHECKED = 256;
    private static final int STATE_VERIFIED = 4096;
    private static final int STATE_VERIFY_AUTO = 1;
    private static final String STATIC_TYPE_STR = "staticType";
    private static final String SUFFIX_STR = "suffix";
    public static final int SYSTEM_HIGH_PRIORITY = 1000;
    public static final int SYSTEM_LOW_PRIORITY = -1000;
    private static final String TAG = "IntentFilter";
    private static final String TYPE_STR = "type";
    private static final String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    public static final int VISIBILITY_EXPLICIT = 1;
    public static final int VISIBILITY_IMPLICIT = 2;
    public static final int VISIBILITY_NONE = 0;
    public static final String WILDCARD = "*";
    public static final String WILDCARD_PATH = "/*";
    private final ArraySet<String> mActions;
    private ArrayList<String> mCategories;
    private ArrayList<AuthorityEntry> mDataAuthorities;
    private ArrayList<PatternMatcher> mDataPaths;
    private ArrayList<PatternMatcher> mDataSchemeSpecificParts;
    private ArrayList<String> mDataSchemes;
    private ArrayList<String> mDataTypes;
    private PersistableBundle mExtras;
    private boolean mHasDynamicPartialTypes;
    private boolean mHasStaticPartialTypes;
    private int mInstantAppVisibility;
    private ArrayList<String> mMimeGroups;
    private int mOrder;
    private int mPriority;
    private ArrayList<String> mStaticDataTypes;
    private ArrayList<UriRelativeFilterGroup> mUriRelativeFilterGroups;
    private int mVerifyState;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Parcelable.Creator<IntentFilter> CREATOR = new Parcelable.Creator<IntentFilter>() { // from class: android.content.IntentFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilter createFromParcel(Parcel parcel) {
            return new IntentFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilter[] newArray(int i) {
            return new IntentFilter[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstantAppVisibility {
    }

    public boolean debugCheck() {
        return true;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    private static int findStringInSet(String[] strArr, String str, int[] iArr, int i) {
        if (strArr == null) {
            return -1;
        }
        int i2 = iArr[i];
        for (int i3 = 0; i3 < i2; i3++) {
            if (strArr[i3].equals(str)) {
                return i3;
            }
        }
        return -1;
    }

    private static String[] addStringToSet(String[] strArr, String str, int[] iArr, int i) {
        if (findStringInSet(strArr, str, iArr, i) >= 0) {
            return strArr;
        }
        if (strArr == null) {
            String[] strArr2 = new String[2];
            strArr2[0] = str;
            iArr[i] = 1;
            return strArr2;
        }
        int i2 = iArr[i];
        if (i2 < strArr.length) {
            strArr[i2] = str;
            iArr[i] = i2 + 1;
            return strArr;
        }
        String[] strArr3 = new String[((i2 * 3) / 2) + 2];
        System.arraycopy(strArr, 0, strArr3, 0, i2);
        strArr3[i2] = str;
        iArr[i] = i2 + 1;
        return strArr3;
    }

    private static String[] removeStringFromSet(String[] strArr, String str, int[] iArr, int i) {
        int findStringInSet = findStringInSet(strArr, str, iArr, i);
        if (findStringInSet < 0) {
            return strArr;
        }
        int i2 = iArr[i];
        if (i2 > strArr.length / 4) {
            int i3 = findStringInSet + 1;
            int i4 = i2 - i3;
            if (i4 > 0) {
                System.arraycopy(strArr, i3, strArr, findStringInSet, i4);
            }
            int i5 = i2 - 1;
            strArr[i5] = null;
            iArr[i] = i5;
            return strArr;
        }
        String[] strArr2 = new String[strArr.length / 3];
        if (findStringInSet > 0) {
            System.arraycopy(strArr, 0, strArr2, 0, findStringInSet);
        }
        int i6 = findStringInSet + 1;
        if (i6 < i2) {
            System.arraycopy(strArr, i6, strArr2, findStringInSet, i2 - i6);
        }
        return strArr2;
    }

    public static class MalformedMimeTypeException extends AndroidException {
        public MalformedMimeTypeException() {
        }

        public MalformedMimeTypeException(String str) {
            super(str);
        }
    }

    public static IntentFilter create(String str, String str2) {
        try {
            return new IntentFilter(str, str2);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Bad MIME type", e);
        }
    }

    public IntentFilter() {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
    }

    public IntentFilter(String str) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
        addAction(str);
    }

    public IntentFilter(String str, String str2) throws MalformedMimeTypeException {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
        addAction(str);
        addDataType(str2);
    }

    public IntentFilter(IntentFilter intentFilter) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = intentFilter.mPriority;
        this.mOrder = intentFilter.mOrder;
        this.mActions = new ArraySet<>((ArraySet) intentFilter.mActions);
        if (intentFilter.mCategories != null) {
            this.mCategories = new ArrayList<>(intentFilter.mCategories);
        }
        if (intentFilter.mStaticDataTypes != null) {
            this.mStaticDataTypes = new ArrayList<>(intentFilter.mStaticDataTypes);
        }
        if (intentFilter.mDataTypes != null) {
            this.mDataTypes = new ArrayList<>(intentFilter.mDataTypes);
        }
        if (intentFilter.mDataSchemes != null) {
            this.mDataSchemes = new ArrayList<>(intentFilter.mDataSchemes);
        }
        if (intentFilter.mDataSchemeSpecificParts != null) {
            this.mDataSchemeSpecificParts = new ArrayList<>(intentFilter.mDataSchemeSpecificParts);
        }
        if (intentFilter.mDataAuthorities != null) {
            this.mDataAuthorities = new ArrayList<>(intentFilter.mDataAuthorities);
        }
        if (intentFilter.mDataPaths != null) {
            this.mDataPaths = new ArrayList<>(intentFilter.mDataPaths);
        }
        if (intentFilter.mUriRelativeFilterGroups != null) {
            this.mUriRelativeFilterGroups = new ArrayList<>(intentFilter.mUriRelativeFilterGroups);
        }
        if (intentFilter.mMimeGroups != null) {
            this.mMimeGroups = new ArrayList<>(intentFilter.mMimeGroups);
        }
        if (intentFilter.mExtras != null) {
            this.mExtras = new PersistableBundle(intentFilter.mExtras);
        }
        this.mHasStaticPartialTypes = intentFilter.mHasStaticPartialTypes;
        this.mHasDynamicPartialTypes = intentFilter.mHasDynamicPartialTypes;
        this.mVerifyState = intentFilter.mVerifyState;
        this.mInstantAppVisibility = intentFilter.mInstantAppVisibility;
    }

    public String toLongString() {
        StringBuilder sb = new StringBuilder("IntentFilter { pri=");
        sb.append(this.mPriority);
        if (countActions() > 0) {
            sb.append(" act=");
            sb.append(this.mActions.toString());
        }
        if (countCategories() > 0) {
            sb.append(" cat=");
            sb.append(this.mCategories.toString());
        }
        if (countDataSchemes() > 0) {
            sb.append(" sch=");
            sb.append(this.mDataSchemes.toString());
        }
        if (Flags.relativeReferenceIntentFilters() && countUriRelativeFilterGroups() > 0) {
            sb.append(" grp=");
            sb.append(this.mUriRelativeFilterGroups.toString());
        }
        sb.append(" }");
        return sb.toString();
    }

    public final void setPriority(int i) {
        this.mPriority = i;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    @SystemApi
    public final void setOrder(int i) {
        this.mOrder = i;
    }

    @SystemApi
    public final int getOrder() {
        return this.mOrder;
    }

    public final void setAutoVerify(boolean z) {
        int i = this.mVerifyState & (-2);
        this.mVerifyState = i;
        if (z) {
            this.mVerifyState = i | 1;
        }
    }

    public final boolean getAutoVerify() {
        return (this.mVerifyState & 1) == 1;
    }

    public final boolean handleAllWebDataURI() {
        if (hasCategory(Intent.CATEGORY_APP_BROWSER)) {
            return true;
        }
        return handlesWebUris(false) && countDataAuthorities() == 0;
    }

    public final boolean handlesWebUris(boolean z) {
        ArrayList<String> arrayList;
        if (!hasAction("android.intent.action.VIEW") || !hasCategory(Intent.CATEGORY_BROWSABLE) || (arrayList = this.mDataSchemes) == null || arrayList.size() == 0) {
            return false;
        }
        int size = this.mDataSchemes.size();
        for (int i = 0; i < size; i++) {
            String str = this.mDataSchemes.get(i);
            boolean z2 = SCHEME_HTTP.equals(str) || SCHEME_HTTPS.equals(str);
            if (z) {
                if (!z2) {
                    return false;
                }
            } else if (z2) {
                return true;
            }
        }
        return z;
    }

    public final boolean needsVerification() {
        return getAutoVerify() && handlesWebUris(true);
    }

    public final boolean isVerified() {
        int i = this.mVerifyState;
        return (i & 256) == 256 && (i & 16) == 16;
    }

    public void setVerified(boolean z) {
        int i = (this.mVerifyState | 256) & (-4097);
        this.mVerifyState = i;
        if (z) {
            this.mVerifyState = i | 4096;
        }
    }

    public void setVisibilityToInstantApp(int i) {
        this.mInstantAppVisibility = i;
    }

    public int getVisibilityToInstantApp() {
        return this.mInstantAppVisibility;
    }

    public boolean isVisibleToInstantApp() {
        return this.mInstantAppVisibility != 0;
    }

    public boolean isExplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 1;
    }

    public boolean isImplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 2;
    }

    public final void addAction(String str) {
        this.mActions.add(str.intern());
    }

    public final void removeAction(String str) {
        if (this.mActions.contains(str)) {
            this.mActions.remove(str.intern());
        }
    }

    public final int countActions() {
        return this.mActions.size();
    }

    public final int safeCountActions() {
        ArraySet<String> arraySet = this.mActions;
        if (arraySet == null) {
            return 0;
        }
        return arraySet.size();
    }

    public final String getAction(int i) {
        return this.mActions.valueAt(i);
    }

    public final boolean hasAction(String str) {
        return str != null && this.mActions.contains(str);
    }

    public final boolean matchAction(String str) {
        return matchAction(str, false, null);
    }

    private boolean matchAction(String str, boolean z, Collection<String> collection) {
        if (!z || !"*".equals(str)) {
            if (collection == null || !collection.contains(str)) {
                return hasAction(str);
            }
            return false;
        }
        if (collection == null) {
            return !this.mActions.isEmpty();
        }
        if (this.mActions.size() > collection.size()) {
            return true;
        }
        for (int size = this.mActions.size() - 1; size >= 0; size--) {
            if (!collection.contains(this.mActions.valueAt(size))) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<String> actionsIterator() {
        ArraySet<String> arraySet = this.mActions;
        if (arraySet != null) {
            return arraySet.iterator();
        }
        return null;
    }

    public final void addDataType(String str) throws MalformedMimeTypeException {
        processMimeType(str, new BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IntentFilter.this.lambda$addDataType$0((String) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDataType$0(String str, Boolean bool) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new ArrayList<>();
        }
        if (this.mStaticDataTypes == null) {
            this.mStaticDataTypes = new ArrayList<>();
        }
        if (this.mDataTypes.contains(str)) {
            return;
        }
        this.mDataTypes.add(str.intern());
        this.mStaticDataTypes.add(str.intern());
        this.mHasStaticPartialTypes = this.mHasStaticPartialTypes || bool.booleanValue();
    }

    public final void addDynamicDataType(String str) throws MalformedMimeTypeException {
        processMimeType(str, new BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IntentFilter.this.lambda$addDynamicDataType$1((String) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDynamicDataType$1(String str, Boolean bool) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new ArrayList<>();
        }
        if (this.mDataTypes.contains(str)) {
            return;
        }
        this.mDataTypes.add(str.intern());
        this.mHasDynamicPartialTypes = this.mHasDynamicPartialTypes || bool.booleanValue();
    }

    private void processMimeType(String str, BiConsumer<String, Boolean> biConsumer) throws MalformedMimeTypeException {
        int i;
        int indexOf = str.indexOf(47);
        int length = str.length();
        if (indexOf <= 0 || length < (i = indexOf + 2)) {
            throw new MalformedMimeTypeException(str);
        }
        boolean z = false;
        if (length == i && str.charAt(indexOf + 1) == '*') {
            str = str.substring(0, indexOf);
            z = true;
        }
        biConsumer.accept(str, Boolean.valueOf(z));
    }

    public final void clearDynamicDataTypes() {
        ArrayList<String> arrayList = this.mDataTypes;
        if (arrayList == null) {
            return;
        }
        if (this.mStaticDataTypes != null) {
            arrayList.clear();
            this.mDataTypes.addAll(this.mStaticDataTypes);
        } else {
            this.mDataTypes = null;
        }
        this.mHasDynamicPartialTypes = false;
    }

    public int countStaticDataTypes() {
        ArrayList<String> arrayList = this.mStaticDataTypes;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final boolean hasDataType(String str) {
        return this.mDataTypes != null && findMimeType(str);
    }

    public final boolean hasExactDataType(String str) {
        ArrayList<String> arrayList = this.mDataTypes;
        return arrayList != null && arrayList.contains(str);
    }

    public final boolean hasExactDynamicDataType(String str) {
        return hasExactDataType(str) && !hasExactStaticDataType(str);
    }

    public final boolean hasExactStaticDataType(String str) {
        ArrayList<String> arrayList = this.mStaticDataTypes;
        return arrayList != null && arrayList.contains(str);
    }

    public final int countDataTypes() {
        ArrayList<String> arrayList = this.mDataTypes;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final String getDataType(int i) {
        return this.mDataTypes.get(i);
    }

    public final Iterator<String> typesIterator() {
        ArrayList<String> arrayList = this.mDataTypes;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final List<String> dataTypes() {
        if (this.mDataTypes != null) {
            return new ArrayList(this.mDataTypes);
        }
        return null;
    }

    public final void addMimeGroup(String str) {
        if (this.mMimeGroups == null) {
            this.mMimeGroups = new ArrayList<>();
        }
        if (this.mMimeGroups.contains(str)) {
            return;
        }
        this.mMimeGroups.add(str);
    }

    public final boolean hasMimeGroup(String str) {
        ArrayList<String> arrayList = this.mMimeGroups;
        return arrayList != null && arrayList.contains(str);
    }

    public final String getMimeGroup(int i) {
        return this.mMimeGroups.get(i);
    }

    public final int countMimeGroups() {
        ArrayList<String> arrayList = this.mMimeGroups;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final Iterator<String> mimeGroupsIterator() {
        ArrayList<String> arrayList = this.mMimeGroups;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final void addDataScheme(String str) {
        if (this.mDataSchemes == null) {
            this.mDataSchemes = new ArrayList<>();
        }
        if (this.mDataSchemes.contains(str)) {
            return;
        }
        this.mDataSchemes.add(str.intern());
    }

    public final int countDataSchemes() {
        ArrayList<String> arrayList = this.mDataSchemes;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final String getDataScheme(int i) {
        return this.mDataSchemes.get(i);
    }

    public final boolean hasDataScheme(String str) {
        ArrayList<String> arrayList = this.mDataSchemes;
        return arrayList != null && arrayList.contains(str);
    }

    public final Iterator<String> schemesIterator() {
        ArrayList<String> arrayList = this.mDataSchemes;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public static final class AuthorityEntry {
        private final String mHost;
        private final String mOrigHost;
        private final int mPort;
        private final boolean mWild;

        public AuthorityEntry(String str, String str2) {
            this.mOrigHost = str;
            boolean z = false;
            if (str.length() > 0 && str.charAt(0) == '*') {
                z = true;
            }
            this.mWild = z;
            this.mHost = z ? str.substring(1).intern() : str;
            this.mPort = str2 != null ? Integer.parseInt(str2) : -1;
        }

        AuthorityEntry(Parcel parcel) {
            this.mOrigHost = parcel.readString();
            this.mHost = parcel.readString();
            this.mWild = parcel.readInt() != 0;
            this.mPort = parcel.readInt();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mOrigHost);
            parcel.writeString(this.mHost);
            parcel.writeInt(this.mWild ? 1 : 0);
            parcel.writeInt(this.mPort);
        }

        void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mHost);
            protoOutputStream.write(1133871366146L, this.mWild);
            protoOutputStream.write(1120986464259L, this.mPort);
            protoOutputStream.end(start);
        }

        public String getHost() {
            return this.mOrigHost;
        }

        public int getPort() {
            return this.mPort;
        }

        public boolean match(AuthorityEntry authorityEntry) {
            return this.mWild == authorityEntry.mWild && this.mHost.equals(authorityEntry.mHost) && this.mPort == authorityEntry.mPort;
        }

        public boolean equals(Object obj) {
            if (obj instanceof AuthorityEntry) {
                return match((AuthorityEntry) obj);
            }
            return false;
        }

        public int match(Uri uri) {
            return match(uri, false);
        }

        public int match(Uri uri, boolean z) {
            int i;
            String host = uri.getHost();
            if (host == null) {
                return (z && this.mWild && this.mHost.isEmpty()) ? 3145728 : -2;
            }
            if (!z || !"*".equals(host)) {
                if (this.mWild) {
                    if (host.length() < this.mHost.length()) {
                        return -2;
                    }
                    host = host.substring(host.length() - this.mHost.length());
                }
                if (host.compareToIgnoreCase(this.mHost) != 0) {
                    return -2;
                }
            }
            if (z || (i = this.mPort) < 0) {
                return 3145728;
            }
            return i != uri.getPort() ? -2 : 4194304;
        }
    }

    public final void addDataSchemeSpecificPart(String str, int i) {
        addDataSchemeSpecificPart(new PatternMatcher(str, i));
    }

    public final void addDataSchemeSpecificPart(PatternMatcher patternMatcher) {
        if (this.mDataSchemeSpecificParts == null) {
            this.mDataSchemeSpecificParts = new ArrayList<>();
        }
        this.mDataSchemeSpecificParts.add(patternMatcher);
    }

    public final int countDataSchemeSpecificParts() {
        ArrayList<PatternMatcher> arrayList = this.mDataSchemeSpecificParts;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final PatternMatcher getDataSchemeSpecificPart(int i) {
        return this.mDataSchemeSpecificParts.get(i);
    }

    public final boolean hasDataSchemeSpecificPart(String str) {
        return hasDataSchemeSpecificPart(str, false);
    }

    private boolean hasDataSchemeSpecificPart(String str, boolean z) {
        if (this.mDataSchemeSpecificParts == null) {
            return false;
        }
        if (z && "*".equals(str) && this.mDataSchemeSpecificParts.size() > 0) {
            return true;
        }
        int size = this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataSchemeSpecificParts.get(i).match(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataSchemeSpecificPart(PatternMatcher patternMatcher) {
        ArrayList<PatternMatcher> arrayList = this.mDataSchemeSpecificParts;
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            PatternMatcher patternMatcher2 = this.mDataSchemeSpecificParts.get(i);
            if (patternMatcher2.getType() == patternMatcher.getType() && patternMatcher2.getPath().equals(patternMatcher.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<PatternMatcher> schemeSpecificPartsIterator() {
        ArrayList<PatternMatcher> arrayList = this.mDataSchemeSpecificParts;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final void addDataAuthority(String str, String str2) {
        if (str2 != null) {
            str2 = str2.intern();
        }
        addDataAuthority(new AuthorityEntry(str.intern(), str2));
    }

    public final void addDataAuthority(AuthorityEntry authorityEntry) {
        if (this.mDataAuthorities == null) {
            this.mDataAuthorities = new ArrayList<>();
        }
        this.mDataAuthorities.add(authorityEntry);
    }

    public final int countDataAuthorities() {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final AuthorityEntry getDataAuthority(int i) {
        return this.mDataAuthorities.get(i);
    }

    public final boolean hasDataAuthority(Uri uri) {
        return matchDataAuthority(uri) >= 0;
    }

    public final boolean hasDataAuthority(AuthorityEntry authorityEntry) {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataAuthorities.get(i).match(authorityEntry)) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<AuthorityEntry> authoritiesIterator() {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final void addDataPath(String str, int i) {
        addDataPath(new PatternMatcher(str.intern(), i));
    }

    public final void addDataPath(PatternMatcher patternMatcher) {
        if (this.mDataPaths == null) {
            this.mDataPaths = new ArrayList<>();
        }
        this.mDataPaths.add(patternMatcher);
    }

    public final int countDataPaths() {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final PatternMatcher getDataPath(int i) {
        return this.mDataPaths.get(i);
    }

    public final boolean hasDataPath(String str) {
        return hasDataPath(str, false);
    }

    private boolean hasDataPath(String str, boolean z) {
        if (this.mDataPaths == null) {
            return false;
        }
        if (z && WILDCARD_PATH.equals(str)) {
            return true;
        }
        int size = this.mDataPaths.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataPaths.get(i).match(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataPath(PatternMatcher patternMatcher) {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            PatternMatcher patternMatcher2 = this.mDataPaths.get(i);
            if (patternMatcher2.getType() == patternMatcher.getType() && patternMatcher2.getPath().equals(patternMatcher.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<PatternMatcher> pathsIterator() {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final void addUriRelativeFilterGroup(UriRelativeFilterGroup uriRelativeFilterGroup) {
        Objects.requireNonNull(uriRelativeFilterGroup);
        if (this.mUriRelativeFilterGroups == null) {
            this.mUriRelativeFilterGroups = new ArrayList<>();
        }
        this.mUriRelativeFilterGroups.add(uriRelativeFilterGroup);
    }

    public final int countUriRelativeFilterGroups() {
        ArrayList<UriRelativeFilterGroup> arrayList = this.mUriRelativeFilterGroups;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public final UriRelativeFilterGroup getUriRelativeFilterGroup(int i) {
        return this.mUriRelativeFilterGroups.get(i);
    }

    public final void clearUriRelativeFilterGroups() {
        this.mUriRelativeFilterGroups = null;
    }

    public final int matchDataAuthority(Uri uri) {
        return matchDataAuthority(uri, false);
    }

    public final int matchDataAuthority(Uri uri, boolean z) {
        ArrayList<AuthorityEntry> arrayList;
        if (uri != null && (arrayList = this.mDataAuthorities) != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                int match = this.mDataAuthorities.get(i).match(uri, z);
                if (match >= 0) {
                    return match;
                }
            }
        }
        return -2;
    }

    public final int matchData(String str, String str2, Uri uri) {
        return matchData(str, str2, uri, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x008a, code lost:
    
        if (hasDataPath(r10.getPath(), r11) != false) goto L55;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int matchData(java.lang.String r8, java.lang.String r9, android.net.Uri r10, boolean r11) {
        /*
            r7 = this;
            if (r11 == 0) goto La
            int r0 = r7.countMimeGroups()
            if (r0 == 0) goto La
            r0 = 1
            goto Lb
        La:
            r0 = 0
        Lb:
            java.util.ArrayList<java.lang.String> r1 = r7.mDataTypes
            java.util.ArrayList<java.lang.String> r2 = r7.mDataSchemes
            r3 = -2
            if (r0 != 0) goto L1f
            if (r1 != 0) goto L1f
            if (r2 != 0) goto L1f
            if (r8 != 0) goto L1e
            if (r10 != 0) goto L1e
            r7 = 1081344(0x108000, float:1.515286E-39)
            return r7
        L1e:
            return r3
        L1f:
            java.lang.String r4 = "*"
            java.lang.String r5 = ""
            if (r2 == 0) goto L92
            if (r9 == 0) goto L28
            r5 = r9
        L28:
            boolean r2 = r2.contains(r5)
            if (r2 != 0) goto L38
            if (r11 == 0) goto L37
            boolean r9 = r4.equals(r9)
            if (r9 == 0) goto L37
            goto L38
        L37:
            return r3
        L38:
            java.util.ArrayList<android.os.PatternMatcher> r9 = r7.mDataSchemeSpecificParts
            r2 = 5767168(0x580000, float:8.081524E-39)
            if (r9 == 0) goto L4e
            if (r10 == 0) goto L4e
            java.lang.String r9 = r10.getSchemeSpecificPart()
            boolean r9 = r7.hasDataSchemeSpecificPart(r9, r11)
            if (r9 == 0) goto L4c
            r9 = r2
            goto L50
        L4c:
            r9 = r3
            goto L50
        L4e:
            r9 = 2097152(0x200000, float:2.938736E-39)
        L50:
            if (r9 == r2) goto L8f
            java.util.ArrayList<android.content.IntentFilter$AuthorityEntry> r2 = r7.mDataAuthorities
            if (r2 == 0) goto L8f
            int r9 = r7.matchDataAuthority(r10, r11)
            if (r9 < 0) goto L8e
            java.util.ArrayList<android.os.PatternMatcher> r2 = r7.mDataPaths
            java.util.ArrayList<android.content.UriRelativeFilterGroup> r4 = r7.mUriRelativeFilterGroups
            boolean r5 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.relativeReferenceIntentFilters()
            r6 = 5242880(0x500000, float:7.34684E-39)
            if (r5 == 0) goto L7f
            if (r2 != 0) goto L6d
            if (r4 != 0) goto L6d
            goto L8f
        L6d:
            java.lang.String r9 = r10.getPath()
            boolean r9 = r7.hasDataPath(r9, r11)
            if (r9 != 0) goto L8c
            boolean r9 = r7.matchRelRefGroups(r10)
            if (r9 == 0) goto L7e
            goto L8c
        L7e:
            return r3
        L7f:
            if (r2 != 0) goto L82
            goto L8f
        L82:
            java.lang.String r9 = r10.getPath()
            boolean r9 = r7.hasDataPath(r9, r11)
            if (r9 == 0) goto L8e
        L8c:
            r9 = r6
            goto L8f
        L8e:
            return r3
        L8f:
            if (r9 != r3) goto Lb5
            return r3
        L92:
            if (r9 == 0) goto Lb3
            boolean r10 = r5.equals(r9)
            if (r10 != 0) goto Lb3
            java.lang.String r10 = "content"
            boolean r10 = r10.equals(r9)
            if (r10 != 0) goto Lb3
            java.lang.String r10 = "file"
            boolean r10 = r10.equals(r9)
            if (r10 != 0) goto Lb3
            if (r11 == 0) goto Lb2
            boolean r9 = r4.equals(r9)
            if (r9 != 0) goto Lb3
        Lb2:
            return r3
        Lb3:
            r9 = 1048576(0x100000, float:1.469368E-39)
        Lb5:
            r10 = 6291456(0x600000, float:8.816208E-39)
            if (r0 == 0) goto Lba
            return r10
        Lba:
            r11 = -1
            if (r1 == 0) goto Lc6
            boolean r7 = r7.findMimeType(r8)
            if (r7 == 0) goto Lc5
            r9 = r10
            goto Lc9
        Lc5:
            return r11
        Lc6:
            if (r8 == 0) goto Lc9
            return r11
        Lc9:
            r7 = 32768(0x8000, float:4.5918E-41)
            int r9 = r9 + r7
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.IntentFilter.matchData(java.lang.String, java.lang.String, android.net.Uri, boolean):int");
    }

    private boolean matchRelRefGroups(Uri uri) {
        ArrayList<UriRelativeFilterGroup> arrayList = this.mUriRelativeFilterGroups;
        if (arrayList == null) {
            return false;
        }
        return UriRelativeFilterGroup.matchGroupsToUri(arrayList, uri);
    }

    public final void addCategory(String str) {
        if (this.mCategories == null) {
            this.mCategories = new ArrayList<>();
        }
        if (this.mCategories.contains(str)) {
            return;
        }
        this.mCategories.add(str.intern());
    }

    public final int countCategories() {
        ArrayList<String> arrayList = this.mCategories;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final String getCategory(int i) {
        return this.mCategories.get(i);
    }

    public final boolean hasCategory(String str) {
        ArrayList<String> arrayList = this.mCategories;
        return arrayList != null && arrayList.contains(str);
    }

    public final Iterator<String> categoriesIterator() {
        ArrayList<String> arrayList = this.mCategories;
        if (arrayList != null) {
            return arrayList.iterator();
        }
        return null;
    }

    public final String matchCategories(Set<String> set) {
        if (set == null) {
            return null;
        }
        Iterator<String> it = set.iterator();
        if (this.mCategories == null) {
            if (it.hasNext()) {
                return it.next();
            }
            return null;
        }
        while (it.hasNext()) {
            String next = it.next();
            if (!this.mCategories.contains(next)) {
                return next;
            }
        }
        return null;
    }

    private String matchExtras(Bundle bundle) {
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return null;
        }
        for (String str : persistableBundle.keySet()) {
            if (bundle != null) {
                Object obj = this.mExtras.get(str);
                Object obj2 = bundle.get(str);
                if (obj2 != null && obj.getClass() == obj2.getClass() && Objects.deepEquals(obj, obj2)) {
                }
            }
            return str;
        }
        return null;
    }

    public final void addExtra(String str, int i) {
        Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putInt(str, i);
    }

    public final int getIntExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return 0;
        }
        return persistableBundle.getInt(str);
    }

    public final void addExtra(String str, int[] iArr) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(iArr);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putIntArray(str, iArr);
    }

    public final int[] getIntArrayExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? EMPTY_INT_ARRAY : persistableBundle.getIntArray(str);
    }

    public final void addExtra(String str, long j) {
        Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putLong(str, j);
    }

    public final long getLongExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return 0L;
        }
        return persistableBundle.getLong(str);
    }

    public final void addExtra(String str, long[] jArr) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(jArr);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putLongArray(str, jArr);
    }

    public final long[] getLongArrayExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? EMPTY_LONG_ARRAY : persistableBundle.getLongArray(str);
    }

    public final void addExtra(String str, double d) {
        Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putDouble(str, d);
    }

    public final double getDoubleExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : persistableBundle.getDouble(str);
    }

    public final void addExtra(String str, double[] dArr) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(dArr);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putDoubleArray(str, dArr);
    }

    public final double[] getDoubleArrayExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? EMPTY_DOUBLE_ARRAY : persistableBundle.getDoubleArray(str);
    }

    public final void addExtra(String str, String str2) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putString(str, str2);
    }

    public final String getStringExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return null;
        }
        return persistableBundle.getString(str);
    }

    public final void addExtra(String str, String[] strArr) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(strArr);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putStringArray(str, strArr);
    }

    public final String[] getStringArrayExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? EMPTY_STRING_ARRAY : persistableBundle.getStringArray(str);
    }

    public final void addExtra(String str, boolean z) {
        Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putBoolean(str, z);
    }

    public final boolean getBooleanExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return false;
        }
        return persistableBundle.getBoolean(str);
    }

    public final void addExtra(String str, boolean[] zArr) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(zArr);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putBooleanArray(str, zArr);
    }

    public final boolean[] getBooleanArrayExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? EMPTY_BOOLEAN_ARRAY : persistableBundle.getBooleanArray(str);
    }

    public final boolean hasExtra(String str) {
        Objects.requireNonNull(str);
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle == null) {
            return false;
        }
        return persistableBundle.containsKey(str);
    }

    public final void setExtras(PersistableBundle persistableBundle) {
        this.mExtras = persistableBundle;
    }

    public final PersistableBundle getExtras() {
        PersistableBundle persistableBundle = this.mExtras;
        return persistableBundle == null ? new PersistableBundle() : persistableBundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicate$2(Intent intent) {
        return match(null, intent, false, TAG) >= 0;
    }

    public Predicate<Intent> asPredicate() {
        return new Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$asPredicate$2;
                lambda$asPredicate$2 = IntentFilter.this.lambda$asPredicate$2((Intent) obj);
                return lambda$asPredicate$2;
            }
        };
    }

    public Predicate<Intent> asPredicateWithTypeResolution(final ContentResolver contentResolver) {
        Objects.requireNonNull(contentResolver);
        return new Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$asPredicateWithTypeResolution$3;
                lambda$asPredicateWithTypeResolution$3 = IntentFilter.this.lambda$asPredicateWithTypeResolution$3(contentResolver, (Intent) obj);
                return lambda$asPredicateWithTypeResolution$3;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicateWithTypeResolution$3(ContentResolver contentResolver, Intent intent) {
        return match(contentResolver, intent, true, TAG) >= 0;
    }

    public final int match(ContentResolver contentResolver, Intent intent, boolean z, String str) {
        return match(intent.getAction(), z ? intent.resolveType(contentResolver) : intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), str, false, null, intent.getExtras());
    }

    public final int match(String str, String str2, String str3, Uri uri, Set<String> set, String str4) {
        return match(str, str2, str3, uri, set, str4, false, null);
    }

    public final int match(String str, String str2, String str3, Uri uri, Set<String> set, String str4, boolean z, Collection<String> collection) {
        return match(str, str2, str3, uri, set, str4, z, collection, null);
    }

    public final int match(String str, String str2, String str3, Uri uri, Set<String> set, String str4, boolean z, Collection<String> collection, Bundle bundle) {
        if (str != null && !matchAction(str, z, collection)) {
            return -3;
        }
        int matchData = matchData(str2, str3, uri, z);
        if (matchData >= 0) {
            if (matchCategories(set) != null) {
                return -4;
            }
            if (matchExtras(bundle) != null) {
                return -5;
            }
        }
        return matchData;
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        String str;
        String str2;
        String str3 = null;
        if (getAutoVerify()) {
            xmlSerializer.attribute(null, AUTO_VERIFY_STR, Boolean.toString(true));
        }
        int countActions = countActions();
        for (int i = 0; i < countActions; i++) {
            xmlSerializer.startTag(null, "action");
            xmlSerializer.attribute(null, "name", this.mActions.valueAt(i));
            xmlSerializer.endTag(null, "action");
        }
        int countCategories = countCategories();
        for (int i2 = 0; i2 < countCategories; i2++) {
            xmlSerializer.startTag(null, CAT_STR);
            xmlSerializer.attribute(null, "name", this.mCategories.get(i2));
            xmlSerializer.endTag(null, CAT_STR);
        }
        writeDataTypesToXml(xmlSerializer);
        int countMimeGroups = countMimeGroups();
        for (int i3 = 0; i3 < countMimeGroups; i3++) {
            xmlSerializer.startTag(null, GROUP_STR);
            xmlSerializer.attribute(null, "name", this.mMimeGroups.get(i3));
            xmlSerializer.endTag(null, GROUP_STR);
        }
        int countDataSchemes = countDataSchemes();
        for (int i4 = 0; i4 < countDataSchemes; i4++) {
            xmlSerializer.startTag(null, SCHEME_STR);
            xmlSerializer.attribute(null, "name", this.mDataSchemes.get(i4));
            xmlSerializer.endTag(null, SCHEME_STR);
        }
        int countDataSchemeSpecificParts = countDataSchemeSpecificParts();
        int i5 = 0;
        while (i5 < countDataSchemeSpecificParts) {
            xmlSerializer.startTag(str3, SSP_STR);
            PatternMatcher patternMatcher = this.mDataSchemeSpecificParts.get(i5);
            int type = patternMatcher.getType();
            if (type == 0) {
                str2 = null;
                xmlSerializer.attribute(null, LITERAL_STR, patternMatcher.getPath());
            } else if (type == 1) {
                str2 = null;
                xmlSerializer.attribute(null, PREFIX_STR, patternMatcher.getPath());
            } else if (type == 2) {
                str2 = null;
                xmlSerializer.attribute(null, SGLOB_STR, patternMatcher.getPath());
            } else if (type == 3) {
                str2 = null;
                xmlSerializer.attribute(null, AGLOB_STR, patternMatcher.getPath());
            } else if (type != 4) {
                str2 = null;
            } else {
                str2 = null;
                xmlSerializer.attribute(null, SUFFIX_STR, patternMatcher.getPath());
            }
            xmlSerializer.endTag(str2, SSP_STR);
            i5++;
            str3 = str2;
        }
        int countDataAuthorities = countDataAuthorities();
        for (int i6 = 0; i6 < countDataAuthorities; i6++) {
            xmlSerializer.startTag(str3, "auth");
            AuthorityEntry authorityEntry = this.mDataAuthorities.get(i6);
            xmlSerializer.attribute(str3, HOST_STR, authorityEntry.getHost());
            if (authorityEntry.getPort() >= 0) {
                xmlSerializer.attribute(str3, "port", Integer.toString(authorityEntry.getPort()));
            }
            xmlSerializer.endTag(str3, "auth");
        }
        int countDataPaths = countDataPaths();
        int i7 = 0;
        while (i7 < countDataPaths) {
            xmlSerializer.startTag(str3, "path");
            PatternMatcher patternMatcher2 = this.mDataPaths.get(i7);
            int type2 = patternMatcher2.getType();
            if (type2 == 0) {
                str = null;
                xmlSerializer.attribute(null, LITERAL_STR, patternMatcher2.getPath());
            } else if (type2 == 1) {
                str = null;
                xmlSerializer.attribute(null, PREFIX_STR, patternMatcher2.getPath());
            } else if (type2 == 2) {
                str = null;
                xmlSerializer.attribute(null, SGLOB_STR, patternMatcher2.getPath());
            } else if (type2 == 3) {
                str = null;
                xmlSerializer.attribute(null, AGLOB_STR, patternMatcher2.getPath());
            } else if (type2 != 4) {
                str = null;
            } else {
                str = null;
                xmlSerializer.attribute(null, SUFFIX_STR, patternMatcher2.getPath());
            }
            xmlSerializer.endTag(str, "path");
            i7++;
            str3 = str;
        }
        String str4 = str3;
        if (this.mExtras != null) {
            xmlSerializer.startTag(str4, "extras");
            try {
                this.mExtras.saveToXml(xmlSerializer);
                xmlSerializer.endTag(str4, "extras");
            } catch (XmlPullParserException e) {
                throw new IllegalStateException("Failed to write extras: " + this.mExtras.toString(), e);
            }
        }
        if (Flags.relativeReferenceIntentFilters()) {
            int countUriRelativeFilterGroups = countUriRelativeFilterGroups();
            for (int i8 = 0; i8 < countUriRelativeFilterGroups; i8++) {
                this.mUriRelativeFilterGroups.get(i8).writeToXml(xmlSerializer);
            }
        }
    }

    private void writeDataTypesToXml(XmlSerializer xmlSerializer) throws IOException {
        ArrayList<String> arrayList = this.mStaticDataTypes;
        if (arrayList == null) {
            return;
        }
        Iterator<String> it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            String next = it.next();
            while (!this.mDataTypes.get(i).equals(next)) {
                writeDataTypeToXml(xmlSerializer, this.mDataTypes.get(i), "type");
                i++;
            }
            writeDataTypeToXml(xmlSerializer, next, STATIC_TYPE_STR);
            i++;
        }
        while (i < this.mDataTypes.size()) {
            writeDataTypeToXml(xmlSerializer, this.mDataTypes.get(i), "type");
            i++;
        }
    }

    private void writeDataTypeToXml(XmlSerializer xmlSerializer, String str, String str2) throws IOException {
        xmlSerializer.startTag(null, str2);
        if (str.indexOf(47) < 0) {
            str = str + WILDCARD_PATH;
        }
        xmlSerializer.attribute(null, "name", str);
        xmlSerializer.endTag(null, str2);
    }

    public void readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, AUTO_VERIFY_STR);
        setAutoVerify(TextUtils.isEmpty(attributeValue) ? false : Boolean.getBoolean(attributeValue));
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("action")) {
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue2 != null) {
                        addAction(attributeValue2);
                    }
                } else if (name.equals(CAT_STR)) {
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue3 != null) {
                        addCategory(attributeValue3);
                    }
                } else if (name.equals(STATIC_TYPE_STR)) {
                    String attributeValue4 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue4 != null) {
                        try {
                            addDataType(attributeValue4);
                        } catch (MalformedMimeTypeException unused) {
                        }
                    }
                } else if (name.equals("type")) {
                    String attributeValue5 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue5 != null) {
                        addDynamicDataType(attributeValue5);
                    }
                } else if (name.equals(GROUP_STR)) {
                    String attributeValue6 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue6 != null) {
                        addMimeGroup(attributeValue6);
                    }
                } else if (name.equals(SCHEME_STR)) {
                    String attributeValue7 = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue7 != null) {
                        addDataScheme(attributeValue7);
                    }
                } else if (name.equals(SSP_STR)) {
                    String attributeValue8 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                    if (attributeValue8 != null) {
                        addDataSchemeSpecificPart(attributeValue8, 0);
                    } else {
                        String attributeValue9 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                        if (attributeValue9 != null) {
                            addDataSchemeSpecificPart(attributeValue9, 1);
                        } else {
                            String attributeValue10 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                            if (attributeValue10 != null) {
                                addDataSchemeSpecificPart(attributeValue10, 2);
                            } else {
                                String attributeValue11 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                                if (attributeValue11 != null) {
                                    addDataSchemeSpecificPart(attributeValue11, 3);
                                } else {
                                    String attributeValue12 = xmlPullParser.getAttributeValue(null, SUFFIX_STR);
                                    if (attributeValue12 != null) {
                                        addDataSchemeSpecificPart(attributeValue12, 4);
                                    }
                                }
                            }
                        }
                    }
                } else if (name.equals("auth")) {
                    String attributeValue13 = xmlPullParser.getAttributeValue(null, HOST_STR);
                    String attributeValue14 = xmlPullParser.getAttributeValue(null, "port");
                    if (attributeValue13 != null) {
                        addDataAuthority(attributeValue13, attributeValue14);
                    }
                } else if (name.equals("path")) {
                    String attributeValue15 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                    if (attributeValue15 != null) {
                        addDataPath(attributeValue15, 0);
                    } else {
                        String attributeValue16 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                        if (attributeValue16 != null) {
                            addDataPath(attributeValue16, 1);
                        } else {
                            String attributeValue17 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                            if (attributeValue17 != null) {
                                addDataPath(attributeValue17, 2);
                            } else {
                                String attributeValue18 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                                if (attributeValue18 != null) {
                                    addDataPath(attributeValue18, 3);
                                } else {
                                    String attributeValue19 = xmlPullParser.getAttributeValue(null, SUFFIX_STR);
                                    if (attributeValue19 != null) {
                                        addDataPath(attributeValue19, 4);
                                    }
                                }
                            }
                        }
                    }
                } else if (name.equals("extras")) {
                    this.mExtras = PersistableBundle.restoreFromXml(xmlPullParser);
                } else if (Flags.relativeReferenceIntentFilters() && URI_RELATIVE_FILTER_GROUP_STR.equals(name)) {
                    addUriRelativeFilterGroup(new UriRelativeFilterGroup(xmlPullParser));
                } else {
                    Log.w(TAG, "Unknown tag parsing IntentFilter: " + name);
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        ArrayList<UriRelativeFilterGroup> arrayList;
        long start = protoOutputStream.start(j);
        if (this.mActions.size() > 0) {
            Iterator<String> it = this.mActions.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(2237677961217L, it.next());
            }
        }
        ArrayList<String> arrayList2 = this.mCategories;
        if (arrayList2 != null) {
            Iterator<String> it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                protoOutputStream.write(2237677961218L, it2.next());
            }
        }
        ArrayList<String> arrayList3 = this.mDataSchemes;
        if (arrayList3 != null) {
            Iterator<String> it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                protoOutputStream.write(2237677961219L, it3.next());
            }
        }
        ArrayList<PatternMatcher> arrayList4 = this.mDataSchemeSpecificParts;
        if (arrayList4 != null) {
            Iterator<PatternMatcher> it4 = arrayList4.iterator();
            while (it4.hasNext()) {
                it4.next().dumpDebug(protoOutputStream, 2246267895812L);
            }
        }
        ArrayList<AuthorityEntry> arrayList5 = this.mDataAuthorities;
        if (arrayList5 != null) {
            Iterator<AuthorityEntry> it5 = arrayList5.iterator();
            while (it5.hasNext()) {
                it5.next().dumpDebug(protoOutputStream, 2246267895813L);
            }
        }
        ArrayList<PatternMatcher> arrayList6 = this.mDataPaths;
        if (arrayList6 != null) {
            Iterator<PatternMatcher> it6 = arrayList6.iterator();
            while (it6.hasNext()) {
                it6.next().dumpDebug(protoOutputStream, 2246267895814L);
            }
        }
        ArrayList<String> arrayList7 = this.mDataTypes;
        if (arrayList7 != null) {
            Iterator<String> it7 = arrayList7.iterator();
            while (it7.hasNext()) {
                protoOutputStream.write(2237677961223L, it7.next());
            }
        }
        ArrayList<String> arrayList8 = this.mMimeGroups;
        if (arrayList8 != null) {
            Iterator<String> it8 = arrayList8.iterator();
            while (it8.hasNext()) {
                protoOutputStream.write(2237677961227L, it8.next());
            }
        }
        if (this.mPriority != 0 || hasPartialTypes()) {
            protoOutputStream.write(1120986464264L, this.mPriority);
            protoOutputStream.write(1133871366153L, hasPartialTypes());
        }
        protoOutputStream.write(1133871366154L, getAutoVerify());
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle != null) {
            persistableBundle.dumpDebug(protoOutputStream, 1146756268044L);
        }
        if (Flags.relativeReferenceIntentFilters() && (arrayList = this.mUriRelativeFilterGroups) != null) {
            Iterator<UriRelativeFilterGroup> it9 = arrayList.iterator();
            while (it9.hasNext()) {
                it9.next().dumpDebug(protoOutputStream, 2246267895821L);
            }
        }
        protoOutputStream.end(start);
    }

    public void dump(Printer printer, String str) {
        StringBuilder sb = new StringBuilder(256);
        if (this.mActions.size() > 0) {
            Iterator<String> it = this.mActions.iterator();
            while (it.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Action: \"");
                sb.append(it.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<String> arrayList = this.mCategories;
        if (arrayList != null) {
            Iterator<String> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Category: \"");
                sb.append(it2.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<String> arrayList2 = this.mDataSchemes;
        if (arrayList2 != null) {
            Iterator<String> it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Scheme: \"");
                sb.append(it3.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<PatternMatcher> arrayList3 = this.mDataSchemeSpecificParts;
        if (arrayList3 != null) {
            Iterator<PatternMatcher> it4 = arrayList3.iterator();
            while (it4.hasNext()) {
                PatternMatcher next = it4.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Ssp: \"");
                sb.append(next);
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<AuthorityEntry> arrayList4 = this.mDataAuthorities;
        if (arrayList4 != null) {
            Iterator<AuthorityEntry> it5 = arrayList4.iterator();
            while (it5.hasNext()) {
                AuthorityEntry next2 = it5.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Authority: \"");
                sb.append(next2.mHost);
                sb.append("\": ");
                sb.append(next2.mPort);
                if (next2.mWild) {
                    sb.append(" WILD");
                }
                printer.println(sb.toString());
            }
        }
        ArrayList<PatternMatcher> arrayList5 = this.mDataPaths;
        if (arrayList5 != null) {
            Iterator<PatternMatcher> it6 = arrayList5.iterator();
            while (it6.hasNext()) {
                PatternMatcher next3 = it6.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Path: \"");
                sb.append(next3);
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<UriRelativeFilterGroup> arrayList6 = this.mUriRelativeFilterGroups;
        if (arrayList6 != null) {
            Iterator<UriRelativeFilterGroup> it7 = arrayList6.iterator();
            while (it7.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("UriRelativeFilterGroup: \"");
                sb.append(it7.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<String> arrayList7 = this.mStaticDataTypes;
        if (arrayList7 != null) {
            Iterator<String> it8 = arrayList7.iterator();
            while (it8.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("StaticType: \"");
                sb.append(it8.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        ArrayList<String> arrayList8 = this.mDataTypes;
        if (arrayList8 != null) {
            Iterator<String> it9 = arrayList8.iterator();
            while (it9.hasNext()) {
                String next4 = it9.next();
                if (!hasExactStaticDataType(next4)) {
                    sb.setLength(0);
                    sb.append(str);
                    sb.append("Type: \"");
                    sb.append(next4);
                    sb.append("\"");
                    printer.println(sb.toString());
                }
            }
        }
        ArrayList<String> arrayList9 = this.mMimeGroups;
        if (arrayList9 != null) {
            Iterator<String> it10 = arrayList9.iterator();
            while (it10.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("MimeGroup: \"");
                sb.append(it10.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mPriority != 0 || this.mOrder != 0 || hasPartialTypes()) {
            sb.setLength(0);
            sb.append(str);
            sb.append("mPriority=");
            sb.append(this.mPriority);
            sb.append(", mOrder=");
            sb.append(this.mOrder);
            sb.append(", mHasStaticPartialTypes=");
            sb.append(this.mHasStaticPartialTypes);
            sb.append(", mHasDynamicPartialTypes=");
            sb.append(this.mHasDynamicPartialTypes);
            printer.println(sb.toString());
        }
        if (getAutoVerify()) {
            sb.setLength(0);
            sb.append(str);
            sb.append("AutoVerify=");
            sb.append(getAutoVerify());
            printer.println(sb.toString());
        }
        if (this.mExtras != null) {
            sb.setLength(0);
            sb.append(str);
            sb.append("mExtras=");
            sb.append(this.mExtras.toString());
            printer.println(sb.toString());
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ArrayList<UriRelativeFilterGroup> arrayList;
        ArraySet<String> arraySet = this.mActions;
        parcel.writeStringArray((String[]) arraySet.toArray(new String[arraySet.size()]));
        if (this.mCategories != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mCategories);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataSchemes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStaticDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mStaticDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mMimeGroups != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mMimeGroups);
        } else {
            parcel.writeInt(0);
        }
        ArrayList<PatternMatcher> arrayList2 = this.mDataSchemeSpecificParts;
        if (arrayList2 != null) {
            int size = arrayList2.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.mDataSchemeSpecificParts.get(i2).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        ArrayList<AuthorityEntry> arrayList3 = this.mDataAuthorities;
        if (arrayList3 != null) {
            int size2 = arrayList3.size();
            parcel.writeInt(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                this.mDataAuthorities.get(i3).writeToParcel(parcel);
            }
        } else {
            parcel.writeInt(0);
        }
        ArrayList<PatternMatcher> arrayList4 = this.mDataPaths;
        if (arrayList4 != null) {
            int size3 = arrayList4.size();
            parcel.writeInt(size3);
            for (int i4 = 0; i4 < size3; i4++) {
                this.mDataPaths.get(i4).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.mHasStaticPartialTypes ? 1 : 0);
        parcel.writeInt(this.mHasDynamicPartialTypes ? 1 : 0);
        parcel.writeInt(getAutoVerify() ? 1 : 0);
        parcel.writeInt(this.mInstantAppVisibility);
        parcel.writeInt(this.mOrder);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            this.mExtras.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (Flags.relativeReferenceIntentFilters() && (arrayList = this.mUriRelativeFilterGroups) != null) {
            int size4 = arrayList.size();
            parcel.writeInt(size4);
            for (int i5 = 0; i5 < size4; i5++) {
                this.mUriRelativeFilterGroups.get(i5).writeToParcel(parcel, i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public boolean checkDataPathAndSchemeSpecificParts() {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        int size = arrayList == null ? 0 : arrayList.size();
        ArrayList<PatternMatcher> arrayList2 = this.mDataSchemeSpecificParts;
        int size2 = arrayList2 == null ? 0 : arrayList2.size();
        for (int i = 0; i < size; i++) {
            if (!this.mDataPaths.get(i).check()) {
                return false;
            }
        }
        for (int i2 = 0; i2 < size2; i2++) {
            if (!this.mDataSchemeSpecificParts.get(i2).check()) {
                return false;
            }
        }
        return true;
    }

    public IntentFilter(Parcel parcel) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        List<String> arrayList = new ArrayList<>();
        parcel.readStringList(arrayList);
        this.mActions = new ArraySet<>(arrayList);
        if (parcel.readInt() != 0) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            this.mCategories = arrayList2;
            parcel.readStringList(arrayList2);
        }
        if (parcel.readInt() != 0) {
            ArrayList<String> arrayList3 = new ArrayList<>();
            this.mDataSchemes = arrayList3;
            parcel.readStringList(arrayList3);
        }
        if (parcel.readInt() != 0) {
            ArrayList<String> arrayList4 = new ArrayList<>();
            this.mStaticDataTypes = arrayList4;
            parcel.readStringList(arrayList4);
        }
        if (parcel.readInt() != 0) {
            ArrayList<String> arrayList5 = new ArrayList<>();
            this.mDataTypes = arrayList5;
            parcel.readStringList(arrayList5);
        }
        if (parcel.readInt() != 0) {
            ArrayList<String> arrayList6 = new ArrayList<>();
            this.mMimeGroups = arrayList6;
            parcel.readStringList(arrayList6);
        }
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mDataSchemeSpecificParts = new ArrayList<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mDataSchemeSpecificParts.add(new PatternMatcher(parcel));
            }
        }
        int readInt2 = parcel.readInt();
        if (readInt2 > 0) {
            this.mDataAuthorities = new ArrayList<>(readInt2);
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.mDataAuthorities.add(new AuthorityEntry(parcel));
            }
        }
        int readInt3 = parcel.readInt();
        if (readInt3 > 0) {
            this.mDataPaths = new ArrayList<>(readInt3);
            for (int i3 = 0; i3 < readInt3; i3++) {
                this.mDataPaths.add(new PatternMatcher(parcel));
            }
        }
        this.mPriority = parcel.readInt();
        this.mHasStaticPartialTypes = parcel.readInt() > 0;
        this.mHasDynamicPartialTypes = parcel.readInt() > 0;
        setAutoVerify(parcel.readInt() > 0);
        setVisibilityToInstantApp(parcel.readInt());
        this.mOrder = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mExtras = PersistableBundle.CREATOR.createFromParcel(parcel);
        }
        int readInt4 = parcel.readInt();
        if (!Flags.relativeReferenceIntentFilters() || readInt4 <= 0) {
            return;
        }
        this.mUriRelativeFilterGroups = new ArrayList<>(readInt4);
        for (int i4 = 0; i4 < readInt4; i4++) {
            this.mUriRelativeFilterGroups.add(new UriRelativeFilterGroup(parcel));
        }
    }

    private boolean hasPartialTypes() {
        return this.mHasStaticPartialTypes || this.mHasDynamicPartialTypes;
    }

    private final boolean findMimeType(String str) {
        ArrayList<String> arrayList = this.mDataTypes;
        if (str == null) {
            return false;
        }
        if (arrayList.contains(str)) {
            return true;
        }
        int length = str.length();
        if (length == 3 && str.equals("*/*")) {
            return !arrayList.isEmpty();
        }
        if (hasPartialTypes() && arrayList.contains("*")) {
            return true;
        }
        int indexOf = str.indexOf(47);
        if (indexOf > 0) {
            if (hasPartialTypes() && arrayList.contains(str.substring(0, indexOf))) {
                return true;
            }
            if (length == indexOf + 2) {
                int i = indexOf + 1;
                if (str.charAt(i) == '*') {
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (str.regionMatches(0, arrayList.get(i2), 0, i)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<String> getHostsList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<AuthorityEntry> authoritiesIterator = authoritiesIterator();
        if (authoritiesIterator != null) {
            while (authoritiesIterator.hasNext()) {
                arrayList.add(authoritiesIterator.next().getHost());
            }
        }
        return arrayList;
    }

    public String[] getHosts() {
        ArrayList<String> hostsList = getHostsList();
        return (String[]) hostsList.toArray(new String[hostsList.size()]);
    }

    public static boolean filterEquals(IntentFilter intentFilter, IntentFilter intentFilter2) {
        int countActions = intentFilter.countActions();
        if (countActions != intentFilter2.countActions()) {
            return false;
        }
        for (int i = 0; i < countActions; i++) {
            if (!intentFilter2.hasAction(intentFilter.getAction(i))) {
                return false;
            }
        }
        int countCategories = intentFilter.countCategories();
        if (countCategories != intentFilter2.countCategories()) {
            return false;
        }
        for (int i2 = 0; i2 < countCategories; i2++) {
            if (!intentFilter2.hasCategory(intentFilter.getCategory(i2))) {
                return false;
            }
        }
        int countDataTypes = intentFilter.countDataTypes();
        if (countDataTypes != intentFilter2.countDataTypes()) {
            return false;
        }
        for (int i3 = 0; i3 < countDataTypes; i3++) {
            if (!intentFilter2.hasExactDataType(intentFilter.getDataType(i3))) {
                return false;
            }
        }
        int countDataSchemes = intentFilter.countDataSchemes();
        if (countDataSchemes != intentFilter2.countDataSchemes()) {
            return false;
        }
        for (int i4 = 0; i4 < countDataSchemes; i4++) {
            if (!intentFilter2.hasDataScheme(intentFilter.getDataScheme(i4))) {
                return false;
            }
        }
        int countDataAuthorities = intentFilter.countDataAuthorities();
        if (countDataAuthorities != intentFilter2.countDataAuthorities()) {
            return false;
        }
        for (int i5 = 0; i5 < countDataAuthorities; i5++) {
            if (!intentFilter2.hasDataAuthority(intentFilter.getDataAuthority(i5))) {
                return false;
            }
        }
        int countDataPaths = intentFilter.countDataPaths();
        if (countDataPaths != intentFilter2.countDataPaths()) {
            return false;
        }
        for (int i6 = 0; i6 < countDataPaths; i6++) {
            if (!intentFilter2.hasDataPath(intentFilter.getDataPath(i6))) {
                return false;
            }
        }
        int countDataSchemeSpecificParts = intentFilter.countDataSchemeSpecificParts();
        if (countDataSchemeSpecificParts != intentFilter2.countDataSchemeSpecificParts()) {
            return false;
        }
        for (int i7 = 0; i7 < countDataSchemeSpecificParts; i7++) {
            if (!intentFilter2.hasDataSchemeSpecificPart(intentFilter.getDataSchemeSpecificPart(i7))) {
                return false;
            }
        }
        return true;
    }
}
