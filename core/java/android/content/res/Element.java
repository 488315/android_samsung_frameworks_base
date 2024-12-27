package android.content.res;

import android.text.format.DateFormat;
import android.util.Pools;
import android.util.Slog;

import java.util.function.Supplier;

public class Element {
    private static final String BAD_COMPONENT_NAME_CHARS = ";,[](){}:?%^*|/\\";
    private static final int DEFAULT_MAX_STRING_ATTR_LENGTH = 32768;
    private static final int MAX_ATTR_LEN_MIMETYPE = 255;
    private static final int MAX_ATTR_LEN_NAME = 1024;
    private static final int MAX_ATTR_LEN_PACKAGE = 256;
    private static final int MAX_ATTR_LEN_PATH = 4000;
    private static final int MAX_ATTR_LEN_PERMISSION_GROUP = 256;
    private static final int MAX_ATTR_LEN_URL_COMPONENT = 256;
    private static final int MAX_ATTR_LEN_VALUE = 32768;
    private static final int MAX_POOL_SIZE = 128;
    private static final int MAX_TOTAL_META_DATA_SIZE = 262144;
    private static final String TAG = "PackageParsing";
    protected static final String TAG_ACTION = "action";
    protected static final String TAG_ACTIVITY = "activity";
    protected static final String TAG_ACTIVITY_ALIAS = "activity-alias";
    protected static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    protected static final String TAG_APPLICATION = "application";
    protected static final String TAG_ATTRIBUTION = "attribution";
    protected static final String TAG_ATTR_BACKUP_AGENT = "backupAgent";
    protected static final String TAG_ATTR_CATEGORY = "category";
    protected static final String TAG_ATTR_FRAGMENT = "fragment";
    protected static final String TAG_ATTR_FRAGMENT_ADVANCED_PATTERN = "fragmentAdvancedPattern";
    protected static final String TAG_ATTR_FRAGMENT_PATTERN = "fragmentPattern";
    protected static final String TAG_ATTR_FRAGMENT_PREFIX = "fragmentPrefix";
    protected static final String TAG_ATTR_FRAGMENT_SUFFIX = "fragmentSuffix";
    protected static final String TAG_ATTR_HOST = "host";
    protected static final String TAG_ATTR_MANAGE_SPACE_ACTIVITY = "manageSpaceActivity";
    protected static final String TAG_ATTR_MIMEGROUP = "mimeGroup";
    protected static final String TAG_ATTR_MIMETYPE = "mimeType";
    protected static final String TAG_ATTR_NAME = "name";
    protected static final String TAG_ATTR_PACKAGE = "package";
    protected static final String TAG_ATTR_PARENT_ACTIVITY_NAME = "parentActivityName";
    protected static final String TAG_ATTR_PATH = "path";
    protected static final String TAG_ATTR_PATH_ADVANCED_PATTERN = "pathAdvancedPattern";
    protected static final String TAG_ATTR_PATH_PATTERN = "pathPattern";
    protected static final String TAG_ATTR_PATH_PREFIX = "pathPrefix";
    protected static final String TAG_ATTR_PATH_SUFFIX = "pathSuffix";
    protected static final String TAG_ATTR_PERMISSION = "permission";
    protected static final String TAG_ATTR_PERMISSION_GROUP = "permissionGroup";
    protected static final String TAG_ATTR_PORT = "port";
    protected static final String TAG_ATTR_PROCESS = "process";
    protected static final String TAG_ATTR_QUERY = "query";
    protected static final String TAG_ATTR_QUERY_ADVANCED_PATTERN = "queryAdvancedPattern";
    protected static final String TAG_ATTR_QUERY_PATTERN = "queryPattern";
    protected static final String TAG_ATTR_QUERY_PREFIX = "queryPrefix";
    protected static final String TAG_ATTR_QUERY_SUFFIX = "querySuffix";
    protected static final String TAG_ATTR_READ_PERMISSION = "readPermission";
    protected static final String TAG_ATTR_REQUIRED_ACCOUNT_TYPE = "requiredAccountType";
    protected static final String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME =
            "requiredSystemPropertyName";
    protected static final String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE =
            "requiredSystemPropertyValue";
    protected static final String TAG_ATTR_RESTRICTED_ACCOUNT_TYPE = "restrictedAccountType";
    protected static final String TAG_ATTR_SCHEME = "scheme";
    protected static final String TAG_ATTR_SHARED_USER_ID = "sharedUserId";
    protected static final String TAG_ATTR_TARGET_ACTIVITY = "targetActivity";
    protected static final String TAG_ATTR_TARGET_NAME = "targetName";
    protected static final String TAG_ATTR_TARGET_PACKAGE = "targetPackage";
    protected static final String TAG_ATTR_TARGET_PROCESSES = "targetProcesses";
    protected static final String TAG_ATTR_TASK_AFFINITY = "taskAffinity";
    protected static final String TAG_ATTR_VALUE = "value";
    protected static final String TAG_ATTR_VERSION_NAME = "versionName";
    protected static final String TAG_ATTR_WRITE_PERMISSION = "writePermission";
    protected static final String TAG_ATTR_ZYGOTE_PRELOAD_NAME = "zygotePreloadName";
    protected static final String TAG_CATEGORY = "category";
    protected static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    protected static final String TAG_DATA = "data";
    protected static final String TAG_EAT_COMMENT = "eat-comment";
    protected static final String TAG_FEATURE_GROUP = "feature-group";
    protected static final String TAG_GRANT_URI_PERMISSION = "grant-uri-permission";
    protected static final String TAG_INSTRUMENTATION = "instrumentation";
    protected static final String TAG_INTENT = "intent";
    protected static final String TAG_INTENT_FILTER = "intent-filter";
    protected static final String TAG_KEY_SETS = "key-sets";
    protected static final String TAG_LAYOUT = "layout";
    protected static final String TAG_MANIFEST = "manifest";
    protected static final String TAG_META_DATA = "meta-data";
    protected static final String TAG_ORIGINAL_PACKAGE = "original-package";
    protected static final String TAG_OVERLAY = "overlay";
    protected static final String TAG_PACKAGE = "package";
    protected static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    protected static final String TAG_PATH_PERMISSION = "path-permission";
    protected static final String TAG_PERMISSION = "permission";
    protected static final String TAG_PERMISSION_GROUP = "permission-group";
    protected static final String TAG_PERMISSION_TREE = "permission-tree";
    protected static final String TAG_PROFILEABLE = "profileable";
    protected static final String TAG_PROPERTY = "property";
    protected static final String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    protected static final String TAG_PROVIDER = "provider";
    protected static final String TAG_QUERIES = "queries";
    protected static final String TAG_RECEIVER = "receiver";
    protected static final String TAG_RESTRICT_UPDATE = "restrict-update";
    protected static final String TAG_SCREEN = "screen";
    protected static final String TAG_SERVICE = "service";
    protected static final String TAG_SUPPORTS_GL_TEXTURE = "supports-gl-texture";
    protected static final String TAG_SUPPORTS_INPUT = "supports-input";
    protected static final String TAG_SUPPORTS_SCREENS = "supports-screens";
    protected static final String TAG_SUPPORT_SCREENS = "supports-screens";
    protected static final String TAG_URI_RELATIVE_FILTER_GROUP = "uri-relative-filter-group";
    protected static final String TAG_USES_CONFIGURATION = "uses-configuration";
    protected static final String TAG_USES_FEATURE = "uses-feature";
    protected static final String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    protected static final String TAG_USES_LIBRARY = "uses-library";
    protected static final String TAG_USES_NATIVE_LIBRARY = "uses-native-library";
    protected static final String TAG_USES_PERMISSION = "uses-permission";
    protected static final String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    protected static final String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    protected static final String TAG_USES_SDK = "uses-sdk";
    protected static final String TAG_USES_SPLIT = "uses-split";
    private static final ThreadLocal<Pools.SimplePool<Element>> sPool =
            ThreadLocal.withInitial(
                    new Supplier() { // from class:
                        // android.content.res.Element$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return Element.lambda$static$0();
                        }
                    });
    String mTag;
    private final TagCounter[] mTagCounters = new TagCounter[35];
    private long mChildTagMask = 0;
    private int mTotalComponentMetadataSize = 0;

    static /* synthetic */ Pools.SimplePool lambda$static$0() {
        return new Pools.SimplePool(128);
    }

    static Element obtain(String tag) {
        Element element = sPool.get().acquire();
        if (element == null) {
            element = new Element();
        }
        element.init(tag);
        return element;
    }

    void recycle() {
        this.mTag = null;
        sPool.get().release(this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getCounterIdx(String tag) {
        char c;
        switch (tag.hashCode()) {
            case -1814617695:
                if (tag.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1773650763:
                if (tag.equals("uses-configuration")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (tag.equals("permission-tree")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (tag.equals("activity")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (tag.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (tag.equals("action")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (tag.equals(TAG_USES_LIBRARY)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1194267734:
                if (tag.equals(TAG_URI_RELATIVE_FILTER_GROUP)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -1183762788:
                if (tag.equals("intent")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (tag.equals(TAG_META_DATA)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1109722326:
                if (tag.equals("layout")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (tag.equals("overlay")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1029793847:
                if (tag.equals(TAG_INTENT_FILTER)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (tag.equals("provider")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (tag.equals("receiver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (tag.equals("package")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (tag.equals("permission")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (tag.equals("attribution")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (tag.equals("uses-sdk")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (tag.equals("permission-group")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (tag.equals("data")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (tag.equals("category")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (tag.equals("profileable")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (tag.equals("instrumentation")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (tag.equals("uses-permission")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (tag.equals(TAG_PATH_PERMISSION)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (tag.equals("queries")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (tag.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (tag.equals("supports-screens")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 941426460:
                if (tag.equals(TAG_SUPPORTS_GL_TEXTURE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (tag.equals("uses-permission-sdk-23")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (tag.equals("application")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (tag.equals("uses-permission-sdk-m")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (tag.equals("uses-feature")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (tag.equals("compatible-screens")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (tag.equals("service")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case '\b':
                return 8;
            case '\t':
                return 9;
            case '\n':
                return 10;
            case 11:
                return 11;
            case '\f':
                return 12;
            case '\r':
                return 13;
            case 14:
                return 14;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            case 21:
                return 21;
            case 22:
                return 22;
            case 23:
                return 23;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            case 27:
                return 27;
            case 28:
            case 29:
            case 30:
                return 28;
            case 31:
                return 29;
            case ' ':
                return 30;
            case '!':
                return 31;
            case '\"':
                return 32;
            case '#':
                return 33;
            default:
                return 34;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static boolean shouldValidate(String tag) {
        char c;
        switch (tag.hashCode()) {
            case -1814617695:
                if (tag.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1773650763:
                if (tag.equals("uses-configuration")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (tag.equals("permission-tree")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (tag.equals("activity")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (tag.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (tag.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (tag.equals(TAG_USES_LIBRARY)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -1194267734:
                if (tag.equals(TAG_URI_RELATIVE_FILTER_GROUP)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1183762788:
                if (tag.equals("intent")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (tag.equals(TAG_META_DATA)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1109722326:
                if (tag.equals("layout")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (tag.equals("overlay")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1029793847:
                if (tag.equals(TAG_INTENT_FILTER)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (tag.equals(TAG_PROPERTY)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (tag.equals("provider")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -907689876:
                if (tag.equals(TAG_SCREEN)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (tag.equals("receiver")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (tag.equals("package")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (tag.equals("permission")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (tag.equals("attribution")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (tag.equals("uses-sdk")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (tag.equals("permission-group")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (tag.equals("data")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (tag.equals("category")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 130625071:
                if (tag.equals("manifest")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (tag.equals("profileable")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (tag.equals("instrumentation")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (tag.equals("uses-permission")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (tag.equals(TAG_PATH_PERMISSION)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (tag.equals("queries")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (tag.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (tag.equals("supports-screens")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 941426460:
                if (tag.equals(TAG_SUPPORTS_GL_TEXTURE)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (tag.equals("uses-permission-sdk-23")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (tag.equals("application")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (tag.equals("uses-permission-sdk-m")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (tag.equals("uses-feature")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (tag.equals("compatible-screens")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (tag.equals("service")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0095, code lost:

       if (r17.equals("activity") != false) goto L42;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void init(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.content.res.Element.init(java.lang.String):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getAttrStrMaxLen(String attrName) {
        char c;
        switch (attrName.hashCode()) {
            case -1674942688:
                if (attrName.equals(TAG_ATTR_FRAGMENT_PATTERN)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1650269616:
                if (attrName.equals(TAG_ATTR_FRAGMENT)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1643738640:
                if (attrName.equals(TAG_ATTR_PERMISSION_GROUP)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1392120434:
                if (attrName.equals(TAG_ATTR_MIMETYPE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1349642324:
                if (attrName.equals(TAG_ATTR_RESTRICTED_ACCOUNT_TYPE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1349189254:
                if (attrName.equals(TAG_ATTR_QUERY_PREFIX)) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -1285716734:
                if (attrName.equals(TAG_ATTR_FRAGMENT_PREFIX)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1260501447:
                if (attrName.equals(TAG_ATTR_QUERY_SUFFIX)) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -1197028927:
                if (attrName.equals(TAG_ATTR_FRAGMENT_SUFFIX)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1070534898:
                if (attrName.equals(TAG_ATTR_WRITE_PERMISSION)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -995070515:
                if (attrName.equals(TAG_ATTR_TASK_AFFINITY)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -984013045:
                if (attrName.equals(TAG_ATTR_SHARED_USER_ID)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -907987547:
                if (attrName.equals(TAG_ATTR_SCHEME)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (attrName.equals("package")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (attrName.equals("permission")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -309518737:
                if (attrName.equals(TAG_ATTR_PROCESS)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -218275157:
                if (attrName.equals(TAG_ATTR_MIMEGROUP)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -197982426:
                if (attrName.equals(TAG_ATTR_QUERY_ADVANCED_PATTERN)) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -134872600:
                if (attrName.equals(TAG_ATTR_REQUIRED_ACCOUNT_TYPE)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 3208616:
                if (attrName.equals(TAG_ATTR_HOST)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3373707:
                if (attrName.equals("name")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 3433509:
                if (attrName.equals("path")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 3446913:
                if (attrName.equals("port")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 37711876:
                if (attrName.equals(TAG_ATTR_PARENT_ACTIVITY_NAME)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (attrName.equals("category")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 107944136:
                if (attrName.equals("query")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 111972721:
                if (attrName.equals("value")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 161722318:
                if (attrName.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 223476894:
                if (attrName.equals(TAG_ATTR_FRAGMENT_ADVANCED_PATTERN)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 437417989:
                if (attrName.equals(TAG_ATTR_READ_PERMISSION)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 486420412:
                if (attrName.equals(TAG_ATTR_TARGET_NAME)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 652376488:
                if (attrName.equals(TAG_ATTR_QUERY_PATTERN)) {
                    c = DateFormat.QUOTE;
                    break;
                }
                c = 65535;
                break;
            case 658841808:
                if (attrName.equals(TAG_ATTR_MANAGE_SPACE_ACTIVITY)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 688906115:
                if (attrName.equals(TAG_ATTR_VERSION_NAME)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 725812366:
                if (attrName.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE)) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 748262167:
                if (attrName.equals(TAG_ATTR_PATH_PREFIX)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 836949974:
                if (attrName.equals(TAG_ATTR_PATH_SUFFIX)) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1046915008:
                if (attrName.equals(TAG_ATTR_TARGET_ACTIVITY)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1090202828:
                if (attrName.equals(TAG_ATTR_TARGET_PROCESSES)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1091642979:
                if (attrName.equals(TAG_ATTR_BACKUP_AGENT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1170994729:
                if (attrName.equals(TAG_ATTR_PATH_ADVANCED_PATTERN)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1248861099:
                if (attrName.equals(TAG_ATTR_PATH_PATTERN)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1496884597:
                if (attrName.equals(TAG_ATTR_TARGET_PACKAGE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2130173948:
                if (attrName.equals(TAG_ATTR_ZYGOTE_PRELOAD_NAME)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return 32768;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getResStrMaxLen(int index) {
        char c;
        String str = this.mTag;
        switch (str.hashCode()) {
            case -1814617695:
                if (str.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (str.equals("permission-tree")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (str.equals("activity")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (str.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (str.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals(TAG_USES_LIBRARY)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals(TAG_META_DATA)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (str.equals("overlay")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (str.equals(TAG_PROPERTY)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals("provider")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (str.equals("permission-group")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (str.equals("data")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 130625071:
                if (str.equals("manifest")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (str.equals("uses-permission")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (str.equals(TAG_PATH_PERMISSION)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (str.equals("uses-permission-sdk-23")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (str.equals("uses-permission-sdk-m")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (str.equals("uses-feature")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return getActionResStrMaxLen(index);
            case 1:
                return getActivityResStrMaxLen(index);
            case 2:
                return getActivityAliasResStrMaxLen(index);
            case 3:
                return getApplicationResStrMaxLen(index);
            case 4:
                return getDataResStrMaxLen(index);
            case 5:
                return getCategoryResStrMaxLen(index);
            case 6:
                return getGrantUriPermissionResStrMaxLen(index);
            case 7:
                return getInstrumentationResStrMaxLen(index);
            case '\b':
                return getManifestResStrMaxLen(index);
            case '\t':
                return getMetaDataResStrMaxLen(index);
            case '\n':
                return getOverlayResStrMaxLen(index);
            case 11:
                return getPathPermissionResStrMaxLen(index);
            case '\f':
                return getPermissionResStrMaxLen(index);
            case '\r':
                return getPermissionGroupResStrMaxLen(index);
            case 14:
                return getPermissionTreeResStrMaxLen(index);
            case 15:
                return getPropertyResStrMaxLen(index);
            case 16:
                return getProviderResStrMaxLen(index);
            case 17:
                return getReceiverResStrMaxLen(index);
            case 18:
                return getServiceResStrMaxLen(index);
            case 19:
                return getUsesFeatureResStrMaxLen(index);
            case 20:
                return getUsesLibraryResStrMaxLen(index);
            case 21:
                return getUsesNativeLibraryResStrMaxLen(index);
            case 22:
            case 23:
            case 24:
                return getUsesPermissionResStrMaxLen(index);
            default:
                return 32768;
        }
    }

    private static int getActionResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getActivityResStrMaxLen(int index) {
        switch (index) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 27:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getActivityAliasResStrMaxLen(int index) {
        switch (index) {
            case 2:
            case 3:
            case 7:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getApplicationResStrMaxLen(int index) {
        switch (index) {
            case 3:
            case 4:
            case 6:
            case 11:
            case 12:
            case 16:
            case 28:
            case 29:
            case 52:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getCategoryResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getDataResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 255;
            case 1:
            case 2:
            case 3:
                return 256;
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return 4000;
            case 8:
            case 9:
            case 10:
            case 13:
            case 15:
            default:
                return 32768;
            case 11:
                return 1024;
        }
    }

    private static int getGrantUriPermissionResStrMaxLen(int index) {
        switch (index) {
            case 0:
            case 1:
            case 2:
                return 4000;
            default:
                return 32768;
        }
    }

    private static int getInstrumentationResStrMaxLen(int index) {
        switch (index) {
            case 2:
            case 9:
                return 1024;
            case 3:
                return 256;
            default:
                return 32768;
        }
    }

    private static int getManifestResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 256;
            case 1:
            default:
                return 32768;
            case 2:
                return 1024;
        }
    }

    private static int getMetaDataResStrMaxLen(int index) {
        switch (index) {
        }
        return 32768;
    }

    private static int getOverlayResStrMaxLen(int index) {
        switch (index) {
            case 1:
                return 256;
            case 2:
            case 3:
            case 5:
                return 1024;
            case 4:
            default:
                return 32768;
            case 6:
                return 91;
        }
    }

    private static int getPathPermissionResStrMaxLen(int index) {
        switch (index) {
            case 0:
            case 1:
            case 2:
                return 1024;
            case 3:
            case 4:
            case 5:
                return 4000;
            default:
                return 32768;
        }
    }

    private static int getPermissionResStrMaxLen(int index) {
        switch (index) {
            case 2:
                return 1024;
            case 3:
            default:
                return 32768;
            case 4:
                return 256;
        }
    }

    private static int getPermissionGroupResStrMaxLen(int index) {
        switch (index) {
            case 2:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getPermissionTreeResStrMaxLen(int index) {
        switch (index) {
            case 2:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getPropertyResStrMaxLen(int index) {
        switch (index) {
        }
        return 32768;
    }

    private static int getProviderResStrMaxLen(int index) {
        switch (index) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
                return 1024;
            case 6:
            case 7:
            default:
                return 32768;
        }
    }

    private static int getReceiverResStrMaxLen(int index) {
        switch (index) {
            case 2:
            case 3:
            case 6:
                return 1024;
            case 4:
            case 5:
            default:
                return 32768;
        }
    }

    private static int getServiceResStrMaxLen(int index) {
        switch (index) {
            case 2:
            case 3:
            case 6:
                return 1024;
            case 4:
            case 5:
            default:
                return 32768;
        }
    }

    private static int getUsesFeatureResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesLibraryResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesNativeLibraryResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesPermissionResStrMaxLen(int index) {
        switch (index) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private void initializeCounter(String tag, int max) {
        int idx = getCounterIdx(tag);
        if (this.mTagCounters[idx] == null) {
            this.mTagCounters[idx] = new TagCounter();
        }
        this.mTagCounters[idx].reset(max);
        this.mChildTagMask |= 1 << idx;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0082, code lost:

       if (r7.equals(android.content.res.Element.TAG_ATTR_ZYGOTE_PRELOAD_NAME) != false) goto L52;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isComponentNameAttr(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = r6.mTag
            int r1 = r0.hashCode()
            r2 = 2
            r3 = 1
            r4 = 0
            r5 = -1
            switch(r1) {
                case -1655966961: goto L4d;
                case -987494927: goto L42;
                case -808719889: goto L37;
                case 544550766: goto L2d;
                case 790287890: goto L23;
                case 1554253136: goto L19;
                case 1984153269: goto Le;
                default: goto Ld;
            }
        Ld:
            goto L57
        Le:
            java.lang.String r1 = "service"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = 6
            goto L58
        L19:
            java.lang.String r1 = "application"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = r2
            goto L58
        L23:
            java.lang.String r1 = "activity-alias"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = r3
            goto L58
        L2d:
            java.lang.String r1 = "instrumentation"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = 3
            goto L58
        L37:
            java.lang.String r1 = "receiver"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = 5
            goto L58
        L42:
            java.lang.String r1 = "provider"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = 4
            goto L58
        L4d:
            java.lang.String r1 = "activity"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Ld
            r0 = r4
            goto L58
        L57:
            r0 = r5
        L58:
            java.lang.String r1 = "name"
            switch(r0) {
                case 0: goto Lb4;
                case 1: goto L9d;
                case 2: goto L73;
                case 3: goto L5f;
                case 4: goto L5f;
                case 5: goto L5f;
                case 6: goto L5f;
                default: goto L5e;
            }
        L5e:
            return r4
        L5f:
            int r0 = r7.hashCode()
            switch(r0) {
                case 3373707: goto L67;
                default: goto L66;
            }
        L66:
            goto L6e
        L67:
            boolean r0 = r7.equals(r1)
            if (r0 == 0) goto L66
            r5 = r4
        L6e:
            switch(r5) {
                case 0: goto L72;
                default: goto L71;
            }
        L71:
            return r4
        L72:
            return r3
        L73:
            int r0 = r7.hashCode()
            switch(r0) {
                case 3373707: goto L8f;
                case 1091642979: goto L85;
                case 2130173948: goto L7b;
                default: goto L7a;
            }
        L7a:
            goto L97
        L7b:
            java.lang.String r0 = "zygotePreloadName"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L7a
            goto L98
        L85:
            java.lang.String r0 = "backupAgent"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L7a
            r2 = r4
            goto L98
        L8f:
            boolean r0 = r7.equals(r1)
            if (r0 == 0) goto L7a
            r2 = r3
            goto L98
        L97:
            r2 = r5
        L98:
            switch(r2) {
                case 0: goto L9c;
                case 1: goto L9c;
                case 2: goto L9c;
                default: goto L9b;
            }
        L9b:
            return r4
        L9c:
            return r3
        L9d:
            int r0 = r7.hashCode()
            switch(r0) {
                case 1046915008: goto La5;
                default: goto La4;
            }
        La4:
            goto Laf
        La5:
            java.lang.String r0 = "targetActivity"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto La4
            r5 = r4
        Laf:
            switch(r5) {
                case 0: goto Lb3;
                default: goto Lb2;
            }
        Lb2:
            return r4
        Lb3:
            return r3
        Lb4:
            int r0 = r7.hashCode()
            switch(r0) {
                case 3373707: goto Lc7;
                case 37711876: goto Lbc;
                default: goto Lbb;
            }
        Lbb:
            goto Lce
        Lbc:
            java.lang.String r0 = "parentActivityName"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto Lbb
            r5 = r3
            goto Lce
        Lc7:
            boolean r0 = r7.equals(r1)
            if (r0 == 0) goto Lbb
            r5 = r4
        Lce:
            switch(r5) {
                case 0: goto Ld2;
                case 1: goto Ld2;
                default: goto Ld1;
            }
        Ld1:
            return r4
        Ld2:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.content.res.Element.isComponentNameAttr(java.lang.String):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean isComponentNameAttr(int index) {
        char c;
        String str = this.mTag;
        switch (str.hashCode()) {
            case -1655966961:
                if (str.equals("activity")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals("provider")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (index != 3 && index != 27) {
                    break;
                }
                break;
            case 1:
                if (index != 7) {
                    break;
                }
                break;
            case 2:
                if (index != 16 && index != 3 && index != 52) {
                    break;
                }
                break;
            case 3:
                if (index != 2) {
                    break;
                }
                break;
            case 4:
                if (index != 2) {
                    break;
                }
                break;
            case 5:
                if (index != 2) {
                    break;
                }
                break;
            case 6:
                if (index != 2) {
                    break;
                }
                break;
        }
        return false;
    }

    boolean hasChild(String tag) {
        return (this.mChildTagMask & ((long) (1 << getCounterIdx(tag)))) != 0;
    }

    void validateComponentName(CharSequence name) {
        for (int i = 0; i < name.length(); i++) {
            if (BAD_COMPONENT_NAME_CHARS.indexOf(name.charAt(i)) >= 0) {
                Slog.e("PackageParsing", ((Object) name) + " is not a valid Java class name");
                throw new SecurityException(((Object) name) + " is not a valid Java class name");
            }
        }
    }

    void validateStrAttr(String attrName, String attrValue) {
        if (attrValue != null && attrValue.length() > getAttrStrMaxLen(attrName)) {
            throw new SecurityException(
                    "String length limit exceeded for attribute " + attrName + " in " + this.mTag);
        }
        if (isComponentNameAttr(attrName)) {
            validateComponentName(attrValue);
        }
    }

    void validateResStrAttr(int index, CharSequence stringValue) {
        if (stringValue != null && stringValue.length() > getResStrMaxLen(index)) {
            throw new SecurityException(
                    "String length limit exceeded for attribute in " + this.mTag);
        }
        if (isComponentNameAttr(index)) {
            validateComponentName(stringValue);
        }
    }

    void validateComponentMetadata(String value) {
        this.mTotalComponentMetadataSize += value.length();
        if (this.mTotalComponentMetadataSize > 262144) {
            throw new SecurityException("Max total meta data size limit exceeded for " + this.mTag);
        }
    }

    void seen(Element element) {
        TagCounter counter = this.mTagCounters[getCounterIdx(element.mTag)];
        if (counter != null) {
            counter.increment();
            if (!counter.isValid()) {
                throw new SecurityException(
                        "The number of child "
                                + element.mTag
                                + " elements exceeded the max allowed in "
                                + this.mTag);
            }
        }
    }
}
