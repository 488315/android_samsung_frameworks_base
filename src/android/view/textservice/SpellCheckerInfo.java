package android.view.textservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class SpellCheckerInfo implements Parcelable {
    public static final Parcelable.Creator<SpellCheckerInfo> CREATOR = new Parcelable.Creator<SpellCheckerInfo>() { // from class: android.view.textservice.SpellCheckerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpellCheckerInfo createFromParcel(Parcel parcel) {
            return new SpellCheckerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpellCheckerInfo[] newArray(int i) {
            return new SpellCheckerInfo[i];
        }
    };
    private static final String TAG = "SpellCheckerInfo";
    private final String mId;
    private final int mLabel;
    private final ResolveInfo mService;
    private final String mSettingsActivityName;
    private final ArrayList<SpellCheckerSubtype> mSubtypes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SpellCheckerInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        int next;
        int i;
        this.mSubtypes = new ArrayList<>();
        this.mService = resolveInfo;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mId = new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        PackageManager packageManager = context.getPackageManager();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, SpellCheckerSession.SERVICE_META_DATA);
                if (loadXmlMetaData == null) {
                    throw new XmlPullParserException("No android.view.textservice.scs meta-data");
                }
                Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    i = 1;
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"spell-checker".equals(loadXmlMetaData.getName())) {
                    throw new XmlPullParserException("Meta-data does not start with spell-checker tag");
                }
                TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.SpellChecker);
                int resourceId = obtainAttributes.getResourceId(0, 0);
                String string = obtainAttributes.getString(1);
                obtainAttributes.recycle();
                int depth = loadXmlMetaData.getDepth();
                while (true) {
                    int next2 = loadXmlMetaData.next();
                    if ((next2 != 3 || loadXmlMetaData.getDepth() > depth) && next2 != i) {
                        if (next2 == 2) {
                            if (!"subtype".equals(loadXmlMetaData.getName())) {
                                throw new XmlPullParserException("Meta-data in spell-checker does not start with subtype tag");
                            }
                            TypedArray obtainAttributes2 = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.SpellChecker_Subtype);
                            SpellCheckerSubtype spellCheckerSubtype = new SpellCheckerSubtype(obtainAttributes2.getResourceId(0, 0), obtainAttributes2.getString(i), obtainAttributes2.getString(4), obtainAttributes2.getString(2), obtainAttributes2.getInt(3, 0));
                            obtainAttributes2.recycle();
                            this.mSubtypes.add(spellCheckerSubtype);
                            i = 1;
                        }
                    }
                }
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                this.mLabel = resourceId;
                this.mSettingsActivityName = string;
            } catch (Exception e) {
                Slog.e(TAG, "Caught exception: " + e);
                throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public SpellCheckerInfo(Parcel parcel) {
        ArrayList<SpellCheckerSubtype> arrayList = new ArrayList<>();
        this.mSubtypes = arrayList;
        this.mLabel = parcel.readInt();
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        parcel.readTypedList(arrayList, SpellCheckerSubtype.CREATOR);
    }

    public String getId() {
        return this.mId;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLabel);
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        this.mService.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSubtypes);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        if (this.mLabel == 0 || packageManager == null) {
            return "";
        }
        return packageManager.getText(getPackageName(), this.mLabel, this.mService.serviceInfo.applicationInfo);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public int getSubtypeCount() {
        return this.mSubtypes.size();
    }

    public SpellCheckerSubtype getSubtypeAt(int i) {
        return this.mSubtypes.get(i);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "mId=" + this.mId);
        printWriter.println(str + "mSettingsActivityName=" + this.mSettingsActivityName);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("Service:");
        printWriter.println(sb.toString());
        this.mService.dump(new PrintWriterPrinter(printWriter), str + "  ");
        int subtypeCount = getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            SpellCheckerSubtype subtypeAt = getSubtypeAt(i);
            printWriter.println(str + "  Subtype #" + i + ":");
            printWriter.println(str + "    locale=" + subtypeAt.getLocale() + " languageTag=" + subtypeAt.getLanguageTag());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("    extraValue=");
            sb2.append(subtypeAt.getExtraValue());
            printWriter.println(sb2.toString());
        }
    }
}
