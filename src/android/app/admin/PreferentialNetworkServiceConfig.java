package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class PreferentialNetworkServiceConfig implements Parcelable {
    private static final String ATTR_VALUE = "value";
    private static final String LOG_TAG = "PreferentialNetworkServiceConfig";
    public static final int PREFERENTIAL_NETWORK_ID_1 = 1;
    public static final int PREFERENTIAL_NETWORK_ID_2 = 2;
    public static final int PREFERENTIAL_NETWORK_ID_3 = 3;
    public static final int PREFERENTIAL_NETWORK_ID_4 = 4;
    public static final int PREFERENTIAL_NETWORK_ID_5 = 5;
    private static final String TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION = "allow_fallback_to_default_connection";
    private static final String TAG_BLOCK_NON_MATCHING_NETWORKS = "block_non_matching_networks";
    private static final String TAG_CONFIG_ENABLED = "preferential_network_service_config_enabled";
    private static final String TAG_EXCLUDED_UIDS = "excluded_uids";
    private static final String TAG_INCLUDED_UIDS = "included_uids";
    private static final String TAG_NETWORK_ID = "preferential_network_service_network_id";
    private static final String TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG = "preferential_network_service_config";
    private static final String TAG_UID = "uid";
    final boolean mAllowFallbackToDefaultConnection;
    final int[] mExcludedUids;
    final int[] mIncludedUids;
    final boolean mIsEnabled;
    final int mNetworkId;
    final boolean mShouldBlockNonMatchingNetworks;
    public static final PreferentialNetworkServiceConfig DEFAULT = new Builder().build();
    public static final Parcelable.Creator<PreferentialNetworkServiceConfig> CREATOR = new Parcelable.Creator<PreferentialNetworkServiceConfig>() { // from class: android.app.admin.PreferentialNetworkServiceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PreferentialNetworkServiceConfig[] newArray(int i) {
            return new PreferentialNetworkServiceConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PreferentialNetworkServiceConfig createFromParcel(Parcel parcel) {
            return new PreferentialNetworkServiceConfig(parcel);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface PreferentialNetworkPreferenceId {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PreferentialNetworkServiceConfig(boolean z, boolean z2, boolean z3, int[] iArr, int[] iArr2, int i) {
        this.mIsEnabled = z;
        this.mAllowFallbackToDefaultConnection = z2;
        this.mShouldBlockNonMatchingNetworks = z3;
        this.mIncludedUids = iArr;
        this.mExcludedUids = iArr2;
        this.mNetworkId = i;
    }

    private PreferentialNetworkServiceConfig(Parcel parcel) {
        this.mIsEnabled = parcel.readBoolean();
        this.mAllowFallbackToDefaultConnection = parcel.readBoolean();
        this.mShouldBlockNonMatchingNetworks = parcel.readBoolean();
        this.mNetworkId = parcel.readInt();
        this.mIncludedUids = parcel.createIntArray();
        this.mExcludedUids = parcel.createIntArray();
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isFallbackToDefaultConnectionAllowed() {
        return this.mAllowFallbackToDefaultConnection;
    }

    public boolean shouldBlockNonMatchingNetworks() {
        return this.mShouldBlockNonMatchingNetworks;
    }

    public int[] getIncludedUids() {
        return this.mIncludedUids;
    }

    public int[] getExcludedUids() {
        return this.mExcludedUids;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public String toString() {
        return "PreferentialNetworkServiceConfig{mIsEnabled=" + isEnabled() + "mAllowFallbackToDefaultConnection=" + isFallbackToDefaultConnectionAllowed() + "mBlockNonMatchingNetworks=" + shouldBlockNonMatchingNetworks() + "mIncludedUids=" + Arrays.toString(this.mIncludedUids) + "mExcludedUids=" + Arrays.toString(this.mExcludedUids) + "mNetworkId=" + this.mNetworkId + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PreferentialNetworkServiceConfig preferentialNetworkServiceConfig = (PreferentialNetworkServiceConfig) obj;
            if (this.mIsEnabled == preferentialNetworkServiceConfig.mIsEnabled && this.mAllowFallbackToDefaultConnection == preferentialNetworkServiceConfig.mAllowFallbackToDefaultConnection && this.mShouldBlockNonMatchingNetworks == preferentialNetworkServiceConfig.mShouldBlockNonMatchingNetworks && this.mNetworkId == preferentialNetworkServiceConfig.mNetworkId && Arrays.equals(this.mIncludedUids, preferentialNetworkServiceConfig.mIncludedUids) && Arrays.equals(this.mExcludedUids, preferentialNetworkServiceConfig.mExcludedUids)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsEnabled), Boolean.valueOf(this.mAllowFallbackToDefaultConnection), Boolean.valueOf(this.mShouldBlockNonMatchingNetworks), Integer.valueOf(Arrays.hashCode(this.mIncludedUids)), Integer.valueOf(Arrays.hashCode(this.mExcludedUids)), Integer.valueOf(this.mNetworkId));
    }

    public static final class Builder {
        boolean mIsEnabled = false;
        int mNetworkId = 0;
        boolean mAllowFallbackToDefaultConnection = true;
        boolean mShouldBlockNonMatchingNetworks = false;
        int[] mIncludedUids = new int[0];
        int[] mExcludedUids = new int[0];

        public Builder setEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public Builder setFallbackToDefaultConnectionAllowed(boolean z) {
            this.mAllowFallbackToDefaultConnection = z;
            return this;
        }

        public Builder setShouldBlockNonMatchingNetworks(boolean z) {
            this.mShouldBlockNonMatchingNetworks = z;
            return this;
        }

        public Builder setIncludedUids(int[] iArr) {
            Objects.requireNonNull(iArr);
            this.mIncludedUids = iArr;
            return this;
        }

        public Builder setExcludedUids(int[] iArr) {
            Objects.requireNonNull(iArr);
            this.mExcludedUids = iArr;
            return this;
        }

        public PreferentialNetworkServiceConfig build() {
            if (this.mIncludedUids.length > 0 && this.mExcludedUids.length > 0) {
                throw new IllegalStateException("Both includedUids and excludedUids cannot be nonempty");
            }
            if (this.mShouldBlockNonMatchingNetworks && this.mAllowFallbackToDefaultConnection) {
                throw new IllegalStateException("A config cannot both allow fallback and block non-matching networks");
            }
            return new PreferentialNetworkServiceConfig(this.mIsEnabled, this.mAllowFallbackToDefaultConnection, this.mShouldBlockNonMatchingNetworks, this.mIncludedUids, this.mExcludedUids, this.mNetworkId);
        }

        public Builder setNetworkId(int i) {
            if (i < 1 || i > 5) {
                throw new IllegalArgumentException("Invalid preference identifier");
            }
            this.mNetworkId = i;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEnabled);
        parcel.writeBoolean(this.mAllowFallbackToDefaultConnection);
        parcel.writeBoolean(this.mShouldBlockNonMatchingNetworks);
        parcel.writeInt(this.mNetworkId);
        parcel.writeIntArray(this.mIncludedUids);
        parcel.writeIntArray(this.mExcludedUids);
    }

    private void writeAttributeValueToXml(TypedXmlSerializer typedXmlSerializer, String str, int i) throws IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeInt(null, "value", i);
        typedXmlSerializer.endTag(null, str);
    }

    private void writeAttributeValueToXml(TypedXmlSerializer typedXmlSerializer, String str, boolean z) throws IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeBoolean(null, "value", z);
        typedXmlSerializer.endTag(null, str);
    }

    private void writeAttributeValuesToXml(TypedXmlSerializer typedXmlSerializer, String str, String str2, Collection<String> collection) throws IOException {
        typedXmlSerializer.startTag(null, str);
        for (String str3 : collection) {
            typedXmlSerializer.startTag(null, str2);
            typedXmlSerializer.attribute(null, "value", str3);
            typedXmlSerializer.endTag(null, str2);
        }
        typedXmlSerializer.endTag(null, str);
    }

    private static void readAttributeValues(TypedXmlPullParser typedXmlPullParser, String str, Collection<String> collection) throws XmlPullParserException, IOException {
        collection.clear();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (str.equals(name)) {
                    collection.add(typedXmlPullParser.getAttributeValue(null, "value"));
                } else {
                    Log.e(LOG_TAG, "Expected tag " + str + " but found " + name);
                }
            }
        }
    }

    private List<String> intArrayToStringList(int[] iArr) {
        return (List) Arrays.stream(iArr).mapToObj(new IntFunction() { // from class: android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return String.valueOf(i);
            }
        }).collect(Collectors.toList());
    }

    private static int[] readStringListToIntArray(TypedXmlPullParser typedXmlPullParser, String str) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        readAttributeValues(typedXmlPullParser, str, arrayList);
        return arrayList.stream().map(new Function() { // from class: android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(Integer.parseInt((String) obj));
            }
        }).mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    public static PreferentialNetworkServiceConfig getPreferentialNetworkServiceConfig(TypedXmlPullParser typedXmlPullParser, String str) throws XmlPullParserException, IOException {
        int depth = typedXmlPullParser.getDepth();
        Builder builder = new Builder();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (TAG_CONFIG_ENABLED.equals(name)) {
                    builder.setEnabled(typedXmlPullParser.getAttributeBoolean(null, "value", false));
                } else if (TAG_NETWORK_ID.equals(name)) {
                    int attributeInt = typedXmlPullParser.getAttributeInt(null, "value", 0);
                    if (attributeInt != 0) {
                        builder.setNetworkId(attributeInt);
                    }
                } else if (TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION.equals(name)) {
                    builder.setFallbackToDefaultConnectionAllowed(typedXmlPullParser.getAttributeBoolean(null, "value", true));
                } else if (TAG_BLOCK_NON_MATCHING_NETWORKS.equals(name)) {
                    builder.setShouldBlockNonMatchingNetworks(typedXmlPullParser.getAttributeBoolean(null, "value", false));
                } else if (TAG_INCLUDED_UIDS.equals(name)) {
                    builder.setIncludedUids(readStringListToIntArray(typedXmlPullParser, "uid"));
                } else if (TAG_EXCLUDED_UIDS.equals(name)) {
                    builder.setExcludedUids(readStringListToIntArray(typedXmlPullParser, "uid"));
                } else {
                    Log.w(LOG_TAG, "Unknown tag under " + str + ": " + name);
                }
            }
        }
        return builder.build();
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG);
        writeAttributeValueToXml(typedXmlSerializer, TAG_CONFIG_ENABLED, isEnabled());
        writeAttributeValueToXml(typedXmlSerializer, TAG_NETWORK_ID, getNetworkId());
        writeAttributeValueToXml(typedXmlSerializer, TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION, isFallbackToDefaultConnectionAllowed());
        writeAttributeValueToXml(typedXmlSerializer, TAG_BLOCK_NON_MATCHING_NETWORKS, shouldBlockNonMatchingNetworks());
        writeAttributeValuesToXml(typedXmlSerializer, TAG_INCLUDED_UIDS, "uid", intArrayToStringList(getIncludedUids()));
        writeAttributeValuesToXml(typedXmlSerializer, TAG_EXCLUDED_UIDS, "uid", intArrayToStringList(getExcludedUids()));
        typedXmlSerializer.endTag(null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("networkId=");
        indentingPrintWriter.println(this.mNetworkId);
        indentingPrintWriter.print("isEnabled=");
        indentingPrintWriter.println(this.mIsEnabled);
        indentingPrintWriter.print("allowFallbackToDefaultConnection=");
        indentingPrintWriter.println(this.mAllowFallbackToDefaultConnection);
        indentingPrintWriter.print("blockNonMatchingNetworks=");
        indentingPrintWriter.println(this.mShouldBlockNonMatchingNetworks);
        indentingPrintWriter.print("includedUids=");
        indentingPrintWriter.println(Arrays.toString(this.mIncludedUids));
        indentingPrintWriter.print("excludedUids=");
        indentingPrintWriter.println(Arrays.toString(this.mExcludedUids));
    }
}
