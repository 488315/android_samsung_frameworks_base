package android.content.res;

import android.text.format.DateFormat;
import android.util.Pools;
import android.util.Slog;
import java.util.function.Supplier;

/* loaded from: classes.dex */
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
    protected static final String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME = "requiredSystemPropertyName";
    protected static final String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE = "requiredSystemPropertyValue";
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
    private static final ThreadLocal<Pools.SimplePool<Element>> sPool = ThreadLocal.withInitial(new Supplier() { // from class: android.content.res.Element$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return Element.lambda$static$0();
        }
    });
    String mTag;
    private final TagCounter[] mTagCounters = new TagCounter[35];
    private long mChildTagMask = 0;
    private int mTotalComponentMetadataSize = 0;

    private static int getActionResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getActivityAliasResStrMaxLen(int i) {
        return (i == 2 || i == 3 || i == 7) ? 1024 : 32768;
    }

    private static int getActivityResStrMaxLen(int i) {
        return (i == 3 || i == 4 || i == 7 || i == 8 || i == 27) ? 1024 : 32768;
    }

    private static int getApplicationResStrMaxLen(int i) {
        return (i == 3 || i == 4 || i == 6 || i == 16 || i == 52 || i == 11 || i == 12 || i == 28 || i == 29) ? 1024 : 32768;
    }

    private static int getCategoryResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getDataResStrMaxLen(int i) {
        switch (i) {
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

    private static int getGrantUriPermissionResStrMaxLen(int i) {
        return (i == 0 || i == 1 || i == 2) ? 4000 : 32768;
    }

    private static int getInstrumentationResStrMaxLen(int i) {
        if (i == 2) {
            return 1024;
        }
        if (i != 3) {
            return i != 9 ? 32768 : 1024;
        }
        return 256;
    }

    private static int getManifestResStrMaxLen(int i) {
        if (i != 0) {
            return i != 2 ? 32768 : 1024;
        }
        return 256;
    }

    private static int getMetaDataResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getOverlayResStrMaxLen(int i) {
        if (i == 1) {
            return 256;
        }
        if (i == 2 || i == 3 || i == 5) {
            return 1024;
        }
        return i != 6 ? 32768 : 91;
    }

    private static int getPathPermissionResStrMaxLen(int i) {
        if (i == 0 || i == 1 || i == 2) {
            return 1024;
        }
        return (i == 3 || i == 4 || i == 5) ? 4000 : 32768;
    }

    private static int getPermissionGroupResStrMaxLen(int i) {
        return i != 2 ? 32768 : 1024;
    }

    private static int getPermissionResStrMaxLen(int i) {
        if (i != 2) {
            return i != 4 ? 32768 : 256;
        }
        return 1024;
    }

    private static int getPermissionTreeResStrMaxLen(int i) {
        return i != 2 ? 32768 : 1024;
    }

    private static int getPropertyResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getProviderResStrMaxLen(int i) {
        return (i == 2 || i == 3 || i == 4 || i == 5 || i == 8) ? 1024 : 32768;
    }

    private static int getReceiverResStrMaxLen(int i) {
        return (i == 2 || i == 3 || i == 6) ? 1024 : 32768;
    }

    private static int getServiceResStrMaxLen(int i) {
        return (i == 2 || i == 3 || i == 6) ? 1024 : 32768;
    }

    private static int getUsesFeatureResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getUsesLibraryResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getUsesNativeLibraryResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    private static int getUsesPermissionResStrMaxLen(int i) {
        return i != 0 ? 32768 : 1024;
    }

    static /* synthetic */ Pools.SimplePool lambda$static$0() {
        return new Pools.SimplePool(128);
    }

    static Element obtain(String str) {
        Element acquire = sPool.get().acquire();
        if (acquire == null) {
            acquire = new Element();
        }
        acquire.init(str);
        return acquire;
    }

    void recycle() {
        this.mTag = null;
        sPool.get().release(this);
    }

    private static int getCounterIdx(String str) {
        str.hashCode();
        switch (str) {
            case "grant-uri-permission":
                return 29;
            case "uses-configuration":
                return 21;
            case "permission-tree":
                return 18;
            case "activity":
                return 10;
            case "uses-native-library":
                return 4;
            case "action":
                return 11;
            case "uses-library":
                return 8;
            case "uri-relative-filter-group":
                return 33;
            case "intent":
                return 32;
            case "meta-data":
                return 1;
            case "layout":
                return 0;
            case "overlay":
                return 15;
            case "intent-filter":
                return 2;
            case "provider":
                return 9;
            case "receiver":
                return 5;
            case "package":
                return 31;
            case "permission":
                return 27;
            case "attribution":
                return 25;
            case "uses-sdk":
                return 22;
            case "permission-group":
                return 17;
            case "data":
                return 13;
            case "category":
                return 12;
            case "profileable":
                return 3;
            case "instrumentation":
                return 16;
            case "uses-permission":
            case "uses-permission-sdk-23":
            case "uses-permission-sdk-m":
                return 28;
            case "path-permission":
                return 30;
            case "queries":
                return 24;
            case "activity-alias":
                return 7;
            case "supports-screens":
                return 20;
            case "supports-gl-texture":
                return 19;
            case "application":
                return 14;
            case "uses-feature":
                return 26;
            case "compatible-screens":
                return 23;
            case "service":
                return 6;
            default:
                return 34;
        }
    }

    static boolean shouldValidate(String str) {
        str.hashCode();
        switch (str) {
            case "grant-uri-permission":
            case "uses-configuration":
            case "permission-tree":
            case "activity":
            case "uses-native-library":
            case "action":
            case "uses-library":
            case "uri-relative-filter-group":
            case "intent":
            case "meta-data":
            case "layout":
            case "overlay":
            case "intent-filter":
            case "property":
            case "provider":
            case "screen":
            case "receiver":
            case "package":
            case "permission":
            case "attribution":
            case "uses-sdk":
            case "permission-group":
            case "data":
            case "category":
            case "manifest":
            case "profileable":
            case "instrumentation":
            case "uses-permission":
            case "path-permission":
            case "queries":
            case "activity-alias":
            case "supports-screens":
            case "supports-gl-texture":
            case "uses-permission-sdk-23":
            case "application":
            case "uses-permission-sdk-m":
            case "uses-feature":
            case "compatible-screens":
            case "service":
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a7, code lost:
    
        if (r17.equals("activity") == false) goto L4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void init(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.Element.init(java.lang.String):void");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static int getAttrStrMaxLen(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1674942688:
                if (str.equals(TAG_ATTR_FRAGMENT_PATTERN)) {
                    c = 0;
                    break;
                }
                break;
            case -1650269616:
                if (str.equals(TAG_ATTR_FRAGMENT)) {
                    c = 1;
                    break;
                }
                break;
            case -1643738640:
                if (str.equals(TAG_ATTR_PERMISSION_GROUP)) {
                    c = 2;
                    break;
                }
                break;
            case -1392120434:
                if (str.equals(TAG_ATTR_MIMETYPE)) {
                    c = 3;
                    break;
                }
                break;
            case -1349642324:
                if (str.equals(TAG_ATTR_RESTRICTED_ACCOUNT_TYPE)) {
                    c = 4;
                    break;
                }
                break;
            case -1349189254:
                if (str.equals(TAG_ATTR_QUERY_PREFIX)) {
                    c = 5;
                    break;
                }
                break;
            case -1285716734:
                if (str.equals(TAG_ATTR_FRAGMENT_PREFIX)) {
                    c = 6;
                    break;
                }
                break;
            case -1260501447:
                if (str.equals(TAG_ATTR_QUERY_SUFFIX)) {
                    c = 7;
                    break;
                }
                break;
            case -1197028927:
                if (str.equals(TAG_ATTR_FRAGMENT_SUFFIX)) {
                    c = '\b';
                    break;
                }
                break;
            case -1070534898:
                if (str.equals(TAG_ATTR_WRITE_PERMISSION)) {
                    c = '\t';
                    break;
                }
                break;
            case -995070515:
                if (str.equals(TAG_ATTR_TASK_AFFINITY)) {
                    c = '\n';
                    break;
                }
                break;
            case -984013045:
                if (str.equals(TAG_ATTR_SHARED_USER_ID)) {
                    c = 11;
                    break;
                }
                break;
            case -907987547:
                if (str.equals(TAG_ATTR_SCHEME)) {
                    c = '\f';
                    break;
                }
                break;
            case -807062458:
                if (str.equals("package")) {
                    c = '\r';
                    break;
                }
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = 14;
                    break;
                }
                break;
            case -309518737:
                if (str.equals(TAG_ATTR_PROCESS)) {
                    c = 15;
                    break;
                }
                break;
            case -218275157:
                if (str.equals(TAG_ATTR_MIMEGROUP)) {
                    c = 16;
                    break;
                }
                break;
            case -197982426:
                if (str.equals(TAG_ATTR_QUERY_ADVANCED_PATTERN)) {
                    c = 17;
                    break;
                }
                break;
            case -134872600:
                if (str.equals(TAG_ATTR_REQUIRED_ACCOUNT_TYPE)) {
                    c = 18;
                    break;
                }
                break;
            case 3208616:
                if (str.equals(TAG_ATTR_HOST)) {
                    c = 19;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 20;
                    break;
                }
                break;
            case 3433509:
                if (str.equals("path")) {
                    c = 21;
                    break;
                }
                break;
            case 3446913:
                if (str.equals("port")) {
                    c = 22;
                    break;
                }
                break;
            case 37711876:
                if (str.equals(TAG_ATTR_PARENT_ACTIVITY_NAME)) {
                    c = 23;
                    break;
                }
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = 24;
                    break;
                }
                break;
            case 107944136:
                if (str.equals("query")) {
                    c = 25;
                    break;
                }
                break;
            case 161722318:
                if (str.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME)) {
                    c = 26;
                    break;
                }
                break;
            case 223476894:
                if (str.equals(TAG_ATTR_FRAGMENT_ADVANCED_PATTERN)) {
                    c = 27;
                    break;
                }
                break;
            case 437417989:
                if (str.equals(TAG_ATTR_READ_PERMISSION)) {
                    c = 28;
                    break;
                }
                break;
            case 486420412:
                if (str.equals(TAG_ATTR_TARGET_NAME)) {
                    c = 29;
                    break;
                }
                break;
            case 652376488:
                if (str.equals(TAG_ATTR_QUERY_PATTERN)) {
                    c = 30;
                    break;
                }
                break;
            case 658841808:
                if (str.equals(TAG_ATTR_MANAGE_SPACE_ACTIVITY)) {
                    c = 31;
                    break;
                }
                break;
            case 688906115:
                if (str.equals(TAG_ATTR_VERSION_NAME)) {
                    c = ' ';
                    break;
                }
                break;
            case 725812366:
                if (str.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE)) {
                    c = '!';
                    break;
                }
                break;
            case 748262167:
                if (str.equals(TAG_ATTR_PATH_PREFIX)) {
                    c = '\"';
                    break;
                }
                break;
            case 836949974:
                if (str.equals(TAG_ATTR_PATH_SUFFIX)) {
                    c = '#';
                    break;
                }
                break;
            case 1046915008:
                if (str.equals(TAG_ATTR_TARGET_ACTIVITY)) {
                    c = '$';
                    break;
                }
                break;
            case 1090202828:
                if (str.equals(TAG_ATTR_TARGET_PROCESSES)) {
                    c = '%';
                    break;
                }
                break;
            case 1091642979:
                if (str.equals(TAG_ATTR_BACKUP_AGENT)) {
                    c = '&';
                    break;
                }
                break;
            case 1170994729:
                if (str.equals(TAG_ATTR_PATH_ADVANCED_PATTERN)) {
                    c = DateFormat.QUOTE;
                    break;
                }
                break;
            case 1248861099:
                if (str.equals(TAG_ATTR_PATH_PATTERN)) {
                    c = '(';
                    break;
                }
                break;
            case 1496884597:
                if (str.equals(TAG_ATTR_TARGET_PACKAGE)) {
                    c = ')';
                    break;
                }
                break;
            case 2130173948:
                if (str.equals(TAG_ATTR_ZYGOTE_PRELOAD_NAME)) {
                    c = '*';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 5:
            case 6:
            case 7:
            case '\b':
            case 17:
            case 21:
            case 25:
            case 27:
            case 30:
            case '\"':
            case '#':
            case '\'':
            case '(':
                return 4000;
            case 2:
                return 256;
            case 3:
                return 255;
            case 4:
            case '\t':
            case '\n':
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 23:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case ' ':
            case '$':
            case '%':
            case '&':
            case '*':
                return 1024;
            case 11:
            case '\f':
            case '\r':
            case 19:
            case 22:
            case ')':
                return 256;
            case '!':
                return 91;
            default:
                return 32768;
        }
    }

    private int getResStrMaxLen(int i) {
        String str = this.mTag;
        str.hashCode();
        switch (str) {
            case "grant-uri-permission":
                return getGrantUriPermissionResStrMaxLen(i);
            case "permission-tree":
                return getPermissionTreeResStrMaxLen(i);
            case "activity":
                return getActivityResStrMaxLen(i);
            case "uses-native-library":
                return getUsesNativeLibraryResStrMaxLen(i);
            case "action":
                return getActionResStrMaxLen(i);
            case "uses-library":
                return getUsesLibraryResStrMaxLen(i);
            case "meta-data":
                return getMetaDataResStrMaxLen(i);
            case "overlay":
                return getOverlayResStrMaxLen(i);
            case "property":
                return getPropertyResStrMaxLen(i);
            case "provider":
                return getProviderResStrMaxLen(i);
            case "receiver":
                return getReceiverResStrMaxLen(i);
            case "permission":
                return getPermissionResStrMaxLen(i);
            case "permission-group":
                return getPermissionGroupResStrMaxLen(i);
            case "data":
                return getDataResStrMaxLen(i);
            case "category":
                return getCategoryResStrMaxLen(i);
            case "manifest":
                return getManifestResStrMaxLen(i);
            case "instrumentation":
                return getInstrumentationResStrMaxLen(i);
            case "uses-permission":
            case "uses-permission-sdk-23":
            case "uses-permission-sdk-m":
                return getUsesPermissionResStrMaxLen(i);
            case "path-permission":
                return getPathPermissionResStrMaxLen(i);
            case "activity-alias":
                return getActivityAliasResStrMaxLen(i);
            case "application":
                return getApplicationResStrMaxLen(i);
            case "uses-feature":
                return getUsesFeatureResStrMaxLen(i);
            case "service":
                return getServiceResStrMaxLen(i);
            default:
                return 32768;
        }
    }

    private void initializeCounter(String str, int i) {
        int counterIdx = getCounterIdx(str);
        TagCounter[] tagCounterArr = this.mTagCounters;
        if (tagCounterArr[counterIdx] == null) {
            tagCounterArr[counterIdx] = new TagCounter();
        }
        this.mTagCounters[counterIdx].reset(i);
        this.mChildTagMask = (1 << counterIdx) | this.mChildTagMask;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x007b, code lost:
    
        if (r6.equals(android.content.res.Element.TAG_ATTR_ZYGOTE_PRELOAD_NAME) == false) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isComponentNameAttr(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = r5.mTag
            r5.hashCode()
            int r0 = r5.hashCode()
            r1 = 2
            r2 = 1
            r3 = 0
            r4 = -1
            switch(r0) {
                case -1655966961: goto L57;
                case -987494927: goto L4b;
                case -808719889: goto L3f;
                case 544550766: goto L34;
                case 790287890: goto L29;
                case 1554253136: goto L1e;
                case 1984153269: goto L12;
                default: goto L10;
            }
        L10:
            r5 = r4
            goto L61
        L12:
            java.lang.String r0 = "service"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L1c
            goto L10
        L1c:
            r5 = 6
            goto L61
        L1e:
            java.lang.String r0 = "application"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L27
            goto L10
        L27:
            r5 = 5
            goto L61
        L29:
            java.lang.String r0 = "activity-alias"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L32
            goto L10
        L32:
            r5 = 4
            goto L61
        L34:
            java.lang.String r0 = "instrumentation"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L3d
            goto L10
        L3d:
            r5 = 3
            goto L61
        L3f:
            java.lang.String r0 = "receiver"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L49
            goto L10
        L49:
            r5 = r1
            goto L61
        L4b:
            java.lang.String r0 = "provider"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L55
            goto L10
        L55:
            r5 = r2
            goto L61
        L57:
            java.lang.String r0 = "activity"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L60
            goto L10
        L60:
            r5 = r3
        L61:
            java.lang.String r0 = "name"
            switch(r5) {
                case 0: goto Laf;
                case 1: goto La4;
                case 2: goto La4;
                case 3: goto La4;
                case 4: goto L96;
                case 5: goto L68;
                case 6: goto La4;
                default: goto L67;
            }
        L67:
            return r3
        L68:
            r6.hashCode()
            int r5 = r6.hashCode()
            switch(r5) {
                case 3373707: goto L89;
                case 1091642979: goto L7e;
                case 2130173948: goto L74;
                default: goto L72;
            }
        L72:
            r1 = r4
            goto L91
        L74:
            java.lang.String r5 = "zygotePreloadName"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto L91
            goto L72
        L7e:
            java.lang.String r5 = "backupAgent"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto L87
            goto L72
        L87:
            r1 = r2
            goto L91
        L89:
            boolean r5 = r6.equals(r0)
            if (r5 != 0) goto L90
            goto L72
        L90:
            r1 = r3
        L91:
            switch(r1) {
                case 0: goto L95;
                case 1: goto L95;
                case 2: goto L95;
                default: goto L94;
            }
        L94:
            return r3
        L95:
            return r2
        L96:
            r6.hashCode()
            java.lang.String r5 = "targetActivity"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto La3
            return r3
        La3:
            return r2
        La4:
            r6.hashCode()
            boolean r5 = r6.equals(r0)
            if (r5 != 0) goto Lae
            return r3
        Lae:
            return r2
        Laf:
            r6.hashCode()
            boolean r5 = r6.equals(r0)
            if (r5 != 0) goto Lc2
            java.lang.String r5 = "parentActivityName"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto Lc2
            return r3
        Lc2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.Element.isComponentNameAttr(java.lang.String):boolean");
    }

    private boolean isComponentNameAttr(int i) {
        String str = this.mTag;
        str.hashCode();
        switch (str) {
            case "activity":
                return i == 3 || i == 27;
            case "provider":
                return i == 2;
            case "receiver":
                return i == 2;
            case "instrumentation":
                return i == 2;
            case "activity-alias":
                return i == 7;
            case "application":
                return i == 16 || i == 3 || i == 52;
            case "service":
                return i == 2;
            default:
                return false;
        }
    }

    boolean hasChild(String str) {
        return (this.mChildTagMask & ((long) (1 << getCounterIdx(str)))) != 0;
    }

    void validateComponentName(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (BAD_COMPONENT_NAME_CHARS.indexOf(charSequence.charAt(i)) >= 0) {
                Slog.e("PackageParsing", ((Object) charSequence) + " is not a valid Java class name");
                throw new SecurityException(((Object) charSequence) + " is not a valid Java class name");
            }
        }
    }

    void validateStrAttr(String str, String str2) {
        if (str2 != null && str2.length() > getAttrStrMaxLen(str)) {
            throw new SecurityException("String length limit exceeded for attribute " + str + " in " + this.mTag);
        }
        if (isComponentNameAttr(str)) {
            validateComponentName(str2);
        }
    }

    void validateResStrAttr(int i, CharSequence charSequence) {
        if (charSequence != null && charSequence.length() > getResStrMaxLen(i)) {
            throw new SecurityException("String length limit exceeded for attribute in " + this.mTag);
        }
        if (isComponentNameAttr(i)) {
            validateComponentName(charSequence);
        }
    }

    void validateComponentMetadata(String str) {
        int length = this.mTotalComponentMetadataSize + str.length();
        this.mTotalComponentMetadataSize = length;
        if (length <= 262144) {
            return;
        }
        throw new SecurityException("Max total meta data size limit exceeded for " + this.mTag);
    }

    void seen(Element element) {
        TagCounter tagCounter = this.mTagCounters[getCounterIdx(element.mTag)];
        if (tagCounter != null) {
            tagCounter.increment();
            if (tagCounter.isValid()) {
                return;
            }
            throw new SecurityException("The number of child " + element.mTag + " elements exceeded the max allowed in " + this.mTag);
        }
    }
}
