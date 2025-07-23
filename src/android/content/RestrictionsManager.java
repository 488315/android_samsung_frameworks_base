package android.content;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class RestrictionsManager {
    public static final String ACTION_PERMISSION_RESPONSE_RECEIVED = "android.content.action.PERMISSION_RESPONSE_RECEIVED";
    public static final String ACTION_REQUEST_LOCAL_APPROVAL = "android.content.action.REQUEST_LOCAL_APPROVAL";
    public static final String ACTION_REQUEST_PERMISSION = "android.content.action.REQUEST_PERMISSION";
    public static final String EXTRA_PACKAGE_NAME = "android.content.extra.PACKAGE_NAME";
    public static final String EXTRA_REQUEST_BUNDLE = "android.content.extra.REQUEST_BUNDLE";
    public static final String EXTRA_REQUEST_ID = "android.content.extra.REQUEST_ID";
    public static final String EXTRA_REQUEST_TYPE = "android.content.extra.REQUEST_TYPE";
    public static final String EXTRA_RESPONSE_BUNDLE = "android.content.extra.RESPONSE_BUNDLE";
    public static final String META_DATA_APP_RESTRICTIONS = "android.content.APP_RESTRICTIONS";
    public static final String REQUEST_KEY_APPROVE_LABEL = "android.request.approve_label";
    public static final String REQUEST_KEY_DATA = "android.request.data";
    public static final String REQUEST_KEY_DENY_LABEL = "android.request.deny_label";
    public static final String REQUEST_KEY_ICON = "android.request.icon";
    public static final String REQUEST_KEY_ID = "android.request.id";
    public static final String REQUEST_KEY_MESSAGE = "android.request.mesg";
    public static final String REQUEST_KEY_NEW_REQUEST = "android.request.new_request";
    public static final String REQUEST_KEY_TITLE = "android.request.title";
    public static final String REQUEST_TYPE_APPROVAL = "android.request.type.approval";
    public static final String RESPONSE_KEY_ERROR_CODE = "android.response.errorcode";
    public static final String RESPONSE_KEY_MESSAGE = "android.response.msg";
    public static final String RESPONSE_KEY_RESPONSE_TIMESTAMP = "android.response.timestamp";
    public static final String RESPONSE_KEY_RESULT = "android.response.result";
    public static final int RESULT_APPROVED = 1;
    public static final int RESULT_DENIED = 2;
    public static final int RESULT_ERROR = 5;
    public static final int RESULT_ERROR_BAD_REQUEST = 1;
    public static final int RESULT_ERROR_INTERNAL = 3;
    public static final int RESULT_ERROR_NETWORK = 2;
    public static final int RESULT_NO_RESPONSE = 3;
    public static final int RESULT_UNKNOWN_REQUEST = 4;
    private static final String TAG = "RestrictionsManager";
    private static final String TAG_RESTRICTION = "restriction";
    private final Context mContext;
    private final IRestrictionsManager mService;

    public RestrictionsManager(Context context, IRestrictionsManager iRestrictionsManager) {
        this.mContext = context;
        this.mService = iRestrictionsManager;
    }

    public Bundle getApplicationRestrictions() {
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager != null) {
                return iRestrictionsManager.getApplicationRestrictions(this.mContext.getPackageName());
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Bundle> getApplicationRestrictionsPerAdmin() {
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager != null) {
                return iRestrictionsManager.getApplicationRestrictionsPerAdminForUser(this.mContext.getUserId(), this.mContext.getPackageName());
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasRestrictionsProvider() {
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager != null) {
                return iRestrictionsManager.hasRestrictionsProvider();
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(String str, String str2, PersistableBundle persistableBundle) {
        if (str == null) {
            throw new NullPointerException("requestType cannot be null");
        }
        if (str2 == null) {
            throw new NullPointerException("requestId cannot be null");
        }
        if (persistableBundle == null) {
            throw new NullPointerException("request cannot be null");
        }
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager != null) {
                iRestrictionsManager.requestPermission(this.mContext.getPackageName(), str, str2, persistableBundle);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent createLocalApprovalIntent() {
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager == null) {
                return null;
            }
            Intent createLocalApprovalIntent = iRestrictionsManager.createLocalApprovalIntent();
            if (createLocalApprovalIntent != null) {
                createLocalApprovalIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
            }
            return createLocalApprovalIntent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPermissionResponse(String str, PersistableBundle persistableBundle) {
        if (str == null) {
            throw new NullPointerException("packageName cannot be null");
        }
        if (persistableBundle == null) {
            throw new NullPointerException("request cannot be null");
        }
        if (!persistableBundle.containsKey(REQUEST_KEY_ID)) {
            throw new IllegalArgumentException("REQUEST_KEY_ID must be specified");
        }
        if (!persistableBundle.containsKey(RESPONSE_KEY_RESULT)) {
            throw new IllegalArgumentException("RESPONSE_KEY_RESULT must be specified");
        }
        try {
            IRestrictionsManager iRestrictionsManager = this.mService;
            if (iRestrictionsManager != null) {
                iRestrictionsManager.notifyPermissionResponse(str, persistableBundle);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<RestrictionEntry> getManifestRestrictions(String str) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo == null || !applicationInfo.metaData.containsKey(META_DATA_APP_RESTRICTIONS)) {
                return null;
            }
            return loadManifestRestrictions(str, applicationInfo.loadXmlMetaData(this.mContext.getPackageManager(), META_DATA_APP_RESTRICTIONS));
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException("No such package " + str);
        }
    }

    private List<RestrictionEntry> loadManifestRestrictions(String str, XmlResourceParser xmlResourceParser) {
        try {
            Context createPackageContext = this.mContext.createPackageContext(str, 0);
            ArrayList arrayList = new ArrayList();
            try {
                int next = xmlResourceParser.next();
                while (next != 1) {
                    if (next == 2) {
                        RestrictionEntry loadRestrictionElement = loadRestrictionElement(createPackageContext, xmlResourceParser);
                        if (loadRestrictionElement != null) {
                            arrayList.add(loadRestrictionElement);
                        }
                    }
                    next = xmlResourceParser.next();
                }
                return arrayList;
            } catch (IOException e) {
                Log.w(TAG, "Reading restriction metadata for " + str, e);
                return null;
            } catch (XmlPullParserException e2) {
                Log.w(TAG, "Reading restriction metadata for " + str, e2);
                return null;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    private RestrictionEntry loadRestrictionElement(Context context, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        AttributeSet asAttributeSet;
        if (!xmlResourceParser.getName().equals(TAG_RESTRICTION) || (asAttributeSet = Xml.asAttributeSet(xmlResourceParser)) == null) {
            return null;
        }
        return loadRestriction(context, context.obtainStyledAttributes(asAttributeSet, R.styleable.RestrictionEntry), xmlResourceParser);
    }

    private RestrictionEntry loadRestriction(Context context, TypedArray typedArray, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        String string = typedArray.getString(3);
        int i = typedArray.getInt(6, -1);
        String string2 = typedArray.getString(2);
        String string3 = typedArray.getString(0);
        int resourceId = typedArray.getResourceId(1, 0);
        int resourceId2 = typedArray.getResourceId(5, 0);
        if (i == -1) {
            Log.w(TAG, "restrictionType cannot be omitted");
            return null;
        }
        if (string == null) {
            Log.w(TAG, "key cannot be omitted");
            return null;
        }
        RestrictionEntry restrictionEntry = new RestrictionEntry(i, string);
        restrictionEntry.setTitle(string2);
        restrictionEntry.setDescription(string3);
        if (resourceId != 0) {
            restrictionEntry.setChoiceEntries(context, resourceId);
        }
        if (resourceId2 != 0) {
            restrictionEntry.setChoiceValues(context, resourceId2);
        }
        switch (i) {
            case 0:
            case 2:
            case 6:
                restrictionEntry.setSelectedString(typedArray.getString(4));
                break;
            case 1:
                restrictionEntry.setSelectedState(typedArray.getBoolean(4, false));
                break;
            case 3:
            default:
                Log.w(TAG, "Unknown restriction type " + i);
                break;
            case 4:
                int resourceId3 = typedArray.getResourceId(4, 0);
                if (resourceId3 != 0) {
                    restrictionEntry.setAllSelectedStrings(context.getResources().getStringArray(resourceId3));
                    break;
                }
                break;
            case 5:
                restrictionEntry.setIntValue(typedArray.getInt(4, 0));
                break;
            case 7:
            case 8:
                int depth = xmlResourceParser.getDepth();
                ArrayList arrayList = new ArrayList();
                while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
                    RestrictionEntry loadRestrictionElement = loadRestrictionElement(context, xmlResourceParser);
                    if (loadRestrictionElement == null) {
                        Log.w(TAG, "Child entry cannot be loaded for bundle restriction " + string);
                    } else {
                        arrayList.add(loadRestrictionElement);
                        if (i == 8 && loadRestrictionElement.getType() != 7) {
                            Log.w(TAG, "bundle_array " + string + " can only contain entries of type bundle");
                        }
                    }
                }
                restrictionEntry.setRestrictions((RestrictionEntry[]) arrayList.toArray(new RestrictionEntry[arrayList.size()]));
                break;
        }
        return null;
    }

    public static Bundle convertRestrictionsToBundle(List<RestrictionEntry> list) {
        Bundle bundle = new Bundle();
        Iterator<RestrictionEntry> it = list.iterator();
        while (it.hasNext()) {
            addRestrictionToBundle(bundle, it.next());
        }
        return bundle;
    }

    private static Bundle addRestrictionToBundle(Bundle bundle, RestrictionEntry restrictionEntry) {
        switch (restrictionEntry.getType()) {
            case 0:
            case 6:
                bundle.putString(restrictionEntry.getKey(), restrictionEntry.getSelectedString());
                return bundle;
            case 1:
                bundle.putBoolean(restrictionEntry.getKey(), restrictionEntry.getSelectedState());
                return bundle;
            case 2:
            case 3:
            case 4:
                bundle.putStringArray(restrictionEntry.getKey(), restrictionEntry.getAllSelectedStrings());
                return bundle;
            case 5:
                bundle.putInt(restrictionEntry.getKey(), restrictionEntry.getIntValue());
                return bundle;
            case 7:
                bundle.putBundle(restrictionEntry.getKey(), convertRestrictionsToBundle(Arrays.asList(restrictionEntry.getRestrictions())));
                return bundle;
            case 8:
                RestrictionEntry[] restrictions = restrictionEntry.getRestrictions();
                Bundle[] bundleArr = new Bundle[restrictions.length];
                for (int i = 0; i < restrictions.length; i++) {
                    RestrictionEntry[] restrictions2 = restrictions[i].getRestrictions();
                    if (restrictions2 == null) {
                        Log.w(TAG, "addRestrictionToBundle: Non-bundle entry found in bundle array");
                        bundleArr[i] = new Bundle();
                    } else {
                        bundleArr[i] = convertRestrictionsToBundle(Arrays.asList(restrictions2));
                    }
                }
                bundle.putParcelableArray(restrictionEntry.getKey(), bundleArr);
                return bundle;
            default:
                throw new IllegalArgumentException("Unsupported restrictionEntry type: " + restrictionEntry.getType());
        }
    }
}
