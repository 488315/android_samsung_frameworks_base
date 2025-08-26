package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class FactoryResetProtectionPolicy implements Parcelable {
    private static final String ATTR_VALUE = "value";
    public static final Parcelable.Creator<FactoryResetProtectionPolicy> CREATOR = new Parcelable.Creator<FactoryResetProtectionPolicy>() { // from class: android.app.admin.FactoryResetProtectionPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FactoryResetProtectionPolicy createFromParcel(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(parcel.readString());
            }
            return new FactoryResetProtectionPolicy(arrayList, parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FactoryResetProtectionPolicy[] newArray(int i) {
            return new FactoryResetProtectionPolicy[i];
        }
    };
    private static final String KEY_FACTORY_RESET_PROTECTION_ACCOUNT = "factory_reset_protection_account";
    private static final String KEY_FACTORY_RESET_PROTECTION_ENABLED = "factory_reset_protection_enabled";
    private static final String LOG_TAG = "FactoryResetProtectionPolicy";
    private final List<String> mFactoryResetProtectionAccounts;
    private final boolean mFactoryResetProtectionEnabled;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private FactoryResetProtectionPolicy(List<String> list, boolean z) {
        this.mFactoryResetProtectionAccounts = list;
        this.mFactoryResetProtectionEnabled = z;
    }

    public List<String> getFactoryResetProtectionAccounts() {
        return this.mFactoryResetProtectionAccounts;
    }

    public boolean isFactoryResetProtectionEnabled() {
        return this.mFactoryResetProtectionEnabled;
    }

    public static class Builder {
        private List<String> mFactoryResetProtectionAccounts;
        private boolean mFactoryResetProtectionEnabled = true;

        public Builder setFactoryResetProtectionAccounts(List<String> list) {
            this.mFactoryResetProtectionAccounts = new ArrayList(list);
            return this;
        }

        public Builder setFactoryResetProtectionEnabled(boolean z) {
            this.mFactoryResetProtectionEnabled = z;
            return this;
        }

        public FactoryResetProtectionPolicy build() {
            return new FactoryResetProtectionPolicy(this.mFactoryResetProtectionAccounts, this.mFactoryResetProtectionEnabled);
        }
    }

    public String toString() {
        return "FactoryResetProtectionPolicy{mFactoryResetProtectionAccounts=" + this.mFactoryResetProtectionAccounts + ", mFactoryResetProtectionEnabled=" + this.mFactoryResetProtectionEnabled + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mFactoryResetProtectionAccounts.size());
        Iterator<String> it = this.mFactoryResetProtectionAccounts.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
        parcel.writeBoolean(this.mFactoryResetProtectionEnabled);
    }

    public static FactoryResetProtectionPolicy readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean(null, KEY_FACTORY_RESET_PROTECTION_ENABLED, false);
            ArrayList arrayList = new ArrayList();
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(KEY_FACTORY_RESET_PROTECTION_ACCOUNT)) {
                    arrayList.add(typedXmlPullParser.getAttributeValue(null, "value"));
                }
            }
            return new FactoryResetProtectionPolicy(arrayList, attributeBoolean);
        } catch (IOException | XmlPullParserException e) {
            Log.w(LOG_TAG, "Reading from xml failed", e);
            return null;
        }
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attributeBoolean(null, KEY_FACTORY_RESET_PROTECTION_ENABLED, this.mFactoryResetProtectionEnabled);
        for (String str : this.mFactoryResetProtectionAccounts) {
            typedXmlSerializer.startTag(null, KEY_FACTORY_RESET_PROTECTION_ACCOUNT);
            typedXmlSerializer.attribute(null, "value", str);
            typedXmlSerializer.endTag(null, KEY_FACTORY_RESET_PROTECTION_ACCOUNT);
        }
    }

    public boolean isNotEmpty() {
        return !this.mFactoryResetProtectionAccounts.isEmpty() && this.mFactoryResetProtectionEnabled;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("factoryResetProtectionEnabled=");
        indentingPrintWriter.println(this.mFactoryResetProtectionEnabled);
        indentingPrintWriter.print("factoryResetProtectionAccounts=");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mFactoryResetProtectionAccounts.size(); i++) {
            indentingPrintWriter.println(this.mFactoryResetProtectionAccounts.get(i));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
