package android.provider;

import android.accounts.Account;
import android.annotation.SystemApi;
import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorEntityIterator;
import android.content.Entity;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.Contacts;
import android.provider.SyncStateContract;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import com.android.internal.R;
import com.google.android.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public final class ContactsContract {
    public static final String AUTHORITY = "com.android.contacts";
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.android.contacts");
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final String DEFERRED_SNIPPETING = "deferred_snippeting";
    public static final String DEFERRED_SNIPPETING_QUERY = "deferred_snippeting_query";
    public static final String DIRECTORY_PARAM_KEY = "directory";
    public static final String HIDDEN_COLUMN_PREFIX = "x_";
    public static final String LIMIT_PARAM_KEY = "limit";
    public static final String PRIMARY_ACCOUNT_NAME = "name_for_primary_account";
    public static final String PRIMARY_ACCOUNT_TYPE = "type_for_primary_account";
    public static final String REMOVE_DUPLICATE_ENTRIES = "remove_duplicate_entries";
    public static final String STREQUENT_PHONE_ONLY = "strequent_phone_only";

    public static final class Authorization {
        public static final String AUTHORIZATION_METHOD = "authorize";
        public static final String KEY_AUTHORIZED_URI = "authorized_uri";
        public static final String KEY_URI_TO_AUTHORIZE = "uri_to_authorize";
    }

    protected interface BaseSyncColumns {
        public static final String SYNC1 = "sync1";
        public static final String SYNC2 = "sync2";
        public static final String SYNC3 = "sync3";
        public static final String SYNC4 = "sync4";
    }

    interface ContactCounts {
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    }

    protected interface ContactNameColumns {
        public static final String DISPLAY_NAME_ALTERNATIVE = "display_name_alt";
        public static final String DISPLAY_NAME_PRIMARY = "display_name";
        public static final String DISPLAY_NAME_SOURCE = "display_name_source";
        public static final String PHONETIC_NAME = "phonetic_name";
        public static final String PHONETIC_NAME_STYLE = "phonetic_name_style";
        public static final String SORT_KEY_ALTERNATIVE = "sort_key_alt";
        public static final String SORT_KEY_PRIMARY = "sort_key";
    }

    protected interface ContactOptionsColumns {
        public static final String CUSTOM_RINGTONE = "custom_ringtone";

        @Deprecated
        public static final String LAST_TIME_CONTACTED = "last_time_contacted";
        public static final String LR_LAST_TIME_CONTACTED = "last_time_contacted";
        public static final String LR_TIMES_CONTACTED = "times_contacted";
        public static final String PINNED = "pinned";
        public static final String RAW_LAST_TIME_CONTACTED = "x_last_time_contacted";
        public static final String RAW_TIMES_CONTACTED = "x_times_contacted";
        public static final String SEND_TO_VOICEMAIL = "send_to_voicemail";
        public static final String STARRED = "starred";

        @Deprecated
        public static final String TIMES_CONTACTED = "times_contacted";
    }

    protected interface ContactStatusColumns {
        public static final String CONTACT_CHAT_CAPABILITY = "contact_chat_capability";
        public static final String CONTACT_PRESENCE = "contact_presence";
        public static final String CONTACT_STATUS = "contact_status";
        public static final String CONTACT_STATUS_ICON = "contact_status_icon";
        public static final String CONTACT_STATUS_LABEL = "contact_status_label";
        public static final String CONTACT_STATUS_RES_PACKAGE = "contact_status_res_package";
        public static final String CONTACT_STATUS_TIMESTAMP = "contact_status_ts";
    }

    protected interface ContactsColumns {
        public static final String CONTACT_LAST_UPDATED_TIMESTAMP = "contact_last_updated_timestamp";
        public static final String DISPLAY_NAME = "display_name";
        public static final String HAS_PHONE_NUMBER = "has_phone_number";
        public static final String IN_DEFAULT_DIRECTORY = "in_default_directory";
        public static final String IN_VISIBLE_GROUP = "in_visible_group";
        public static final String IS_USER_PROFILE = "is_user_profile";
        public static final String LOOKUP_KEY = "lookup";
        public static final String NAME_RAW_CONTACT_ID = "name_raw_contact_id";
        public static final String PHOTO_FILE_ID = "photo_file_id";
        public static final String PHOTO_ID = "photo_id";
        public static final String PHOTO_THUMBNAIL_URI = "photo_thumb_uri";
        public static final String PHOTO_URI = "photo_uri";
    }

    protected interface DataColumns {

        @Deprecated
        public static final String CARRIER_PRESENCE = "carrier_presence";

        @Deprecated
        public static final int CARRIER_PRESENCE_VT_CAPABLE = 1;
        public static final String DATA1 = "data1";
        public static final String DATA10 = "data10";
        public static final String DATA11 = "data11";
        public static final String DATA12 = "data12";
        public static final String DATA13 = "data13";
        public static final String DATA14 = "data14";
        public static final String DATA15 = "data15";
        public static final String DATA2 = "data2";
        public static final String DATA3 = "data3";
        public static final String DATA4 = "data4";
        public static final String DATA5 = "data5";
        public static final String DATA6 = "data6";
        public static final String DATA7 = "data7";
        public static final String DATA8 = "data8";
        public static final String DATA9 = "data9";
        public static final String DATA_VERSION = "data_version";

        @Deprecated
        public static final String HASH_ID = "hash_id";
        public static final String IS_PHONE_ACCOUNT_MIGRATION_PENDING = "is_preferred_phone_account_migration_pending";
        public static final String IS_PRIMARY = "is_primary";
        public static final String IS_READ_ONLY = "is_read_only";
        public static final String IS_SUPER_PRIMARY = "is_super_primary";
        public static final String MIMETYPE = "mimetype";
        public static final String PREFERRED_PHONE_ACCOUNT_COMPONENT_NAME = "preferred_phone_account_component_name";
        public static final String PREFERRED_PHONE_ACCOUNT_ID = "preferred_phone_account_id";
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        public static final String RES_PACKAGE = "res_package";
        public static final String SYNC1 = "data_sync1";
        public static final String SYNC2 = "data_sync2";
        public static final String SYNC3 = "data_sync3";
        public static final String SYNC4 = "data_sync4";
    }

    protected interface DataColumnsWithJoins extends BaseColumns, DataColumns, StatusColumns, RawContactsColumns, ContactsColumns, ContactNameColumns, ContactOptionsColumns, ContactStatusColumns, DataUsageStatColumns {
    }

    @Deprecated
    public static final class DataUsageFeedback {
        public static final String USAGE_TYPE = "type";
        public static final String USAGE_TYPE_CALL = "call";
        public static final String USAGE_TYPE_LONG_TEXT = "long_text";
        public static final String USAGE_TYPE_SHORT_TEXT = "short_text";
        public static final Uri FEEDBACK_URI = Uri.withAppendedPath(Data.CONTENT_URI, "usagefeedback");
        public static final Uri DELETE_USAGE_URI = Uri.withAppendedPath(Contacts.CONTENT_URI, "delete_usage");
    }

    protected interface DataUsageStatColumns {

        @Deprecated
        public static final String LAST_TIME_USED = "last_time_used";
        public static final String LR_LAST_TIME_USED = "last_time_used";
        public static final String LR_TIMES_USED = "times_used";
        public static final String RAW_LAST_TIME_USED = "x_last_time_used";
        public static final String RAW_TIMES_USED = "x_times_used";

        @Deprecated
        public static final String TIMES_USED = "times_used";
    }

    protected interface DeletedContactsColumns {
        public static final String CONTACT_DELETED_TIMESTAMP = "contact_deleted_timestamp";
        public static final String CONTACT_ID = "contact_id";
    }

    public interface DisplayNameSources {
        public static final int EMAIL = 10;
        public static final int NICKNAME = 35;
        public static final int ORGANIZATION = 30;
        public static final int PHONE = 20;
        public static final int STRUCTURED_NAME = 40;
        public static final int STRUCTURED_PHONETIC_NAME = 37;
        public static final int UNDEFINED = 0;
    }

    public interface FullNameStyle {
        public static final int CHINESE = 3;
        public static final int CJK = 2;
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int UNDEFINED = 0;
        public static final int WESTERN = 1;
    }

    protected interface GroupsColumns {
        public static final String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final String AUTO_ADD = "auto_add";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";
        public static final String FAVORITES = "favorites";
        public static final String GROUP_IS_READ_ONLY = "group_is_read_only";
        public static final String GROUP_VISIBLE = "group_visible";
        public static final String NOTES = "notes";
        public static final String PARAM_RETURN_GROUP_COUNT_PER_ACCOUNT = "return_group_count_per_account";
        public static final String RES_PACKAGE = "res_package";
        public static final String SHOULD_SYNC = "should_sync";
        public static final String SUMMARY_COUNT = "summ_count";
        public static final String SUMMARY_GROUP_COUNT_PER_ACCOUNT = "group_count_per_account";
        public static final String SUMMARY_WITH_PHONES = "summ_phones";
        public static final String SYSTEM_ID = "system_id";
        public static final String TITLE = "title";
        public static final String TITLE_RES = "title_res";
    }

    public static final class Intents {
        public static final String ACTION_GET_MULTIPLE_PHONES = "com.android.contacts.action.GET_MULTIPLE_PHONES";
        public static final String ACTION_PROFILE_CHANGED = "android.provider.Contacts.PROFILE_CHANGED";
        public static final String ACTION_VOICE_SEND_MESSAGE_TO_CONTACTS = "android.provider.action.VOICE_SEND_MESSAGE_TO_CONTACTS";
        public static final String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";
        public static final String CONTACTS_DATABASE_CREATED = "android.provider.Contacts.DATABASE_CREATED";
        public static final String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";

        @Deprecated
        public static final String EXTRA_EXCLUDE_MIMES = "exclude_mimes";
        public static final String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";

        @Deprecated
        public static final String EXTRA_MODE = "mode";
        public static final String EXTRA_PHONE_URIS = "com.android.contacts.extra.PHONE_URIS";
        public static final String EXTRA_RECIPIENT_CONTACT_CHAT_ID = "android.provider.extra.RECIPIENT_CONTACT_CHAT_ID";
        public static final String EXTRA_RECIPIENT_CONTACT_NAME = "android.provider.extra.RECIPIENT_CONTACT_NAME";
        public static final String EXTRA_RECIPIENT_CONTACT_URI = "android.provider.extra.RECIPIENT_CONTACT_URI";

        @Deprecated
        public static final String EXTRA_TARGET_RECT = "target_rect";
        public static final String INVITE_CONTACT = "com.android.contacts.action.INVITE_CONTACT";
        public static final String METADATA_ACCOUNT_TYPE = "android.provider.account_type";
        public static final String METADATA_MIMETYPE = "android.provider.mimetype";

        @Deprecated
        public static final int MODE_LARGE = 3;

        @Deprecated
        public static final int MODE_MEDIUM = 2;

        @Deprecated
        public static final int MODE_SMALL = 1;
        public static final String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";
        public static final String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";
        public static final String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";
        public static final String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";

        public static final class Insert {
            public static final String ACTION = "android.intent.action.INSERT";
            public static final String COMPANY = "company";
            public static final String DATA = "data";
            public static final String EMAIL = "email";
            public static final String EMAIL_ISPRIMARY = "email_isprimary";
            public static final String EMAIL_TYPE = "email_type";
            public static final String EXTRA_ACCOUNT = "android.provider.extra.ACCOUNT";
            public static final String EXTRA_DATA_SET = "android.provider.extra.DATA_SET";
            public static final String FULL_MODE = "full_mode";
            public static final String IM_HANDLE = "im_handle";
            public static final String IM_ISPRIMARY = "im_isprimary";
            public static final String IM_PROTOCOL = "im_protocol";
            public static final String JOB_TITLE = "job_title";
            public static final String NAME = "name";
            public static final String NOTES = "notes";
            public static final String PHONE = "phone";
            public static final String PHONETIC_NAME = "phonetic_name";
            public static final String PHONE_ISPRIMARY = "phone_isprimary";
            public static final String PHONE_TYPE = "phone_type";
            public static final String POSTAL = "postal";
            public static final String POSTAL_ISPRIMARY = "postal_isprimary";
            public static final String POSTAL_TYPE = "postal_type";
            public static final String SECONDARY_EMAIL = "secondary_email";
            public static final String SECONDARY_EMAIL_TYPE = "secondary_email_type";
            public static final String SECONDARY_PHONE = "secondary_phone";
            public static final String SECONDARY_PHONE_TYPE = "secondary_phone_type";
            public static final String TERTIARY_EMAIL = "tertiary_email";
            public static final String TERTIARY_EMAIL_TYPE = "tertiary_email_type";
            public static final String TERTIARY_PHONE = "tertiary_phone";
            public static final String TERTIARY_PHONE_TYPE = "tertiary_phone_type";
        }
    }

    @SystemApi
    @Deprecated
    protected interface MetadataSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA = "data";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";
        public static final String RAW_CONTACT_BACKUP_ID = "raw_contact_backup_id";
    }

    @SystemApi
    @Deprecated
    protected interface MetadataSyncStateColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA_SET = "data_set";
        public static final String STATE = "state";
    }

    protected interface PhoneLookupColumns {
        public static final String CONTACT_ID = "contact_id";
        public static final String DATA_ID = "data_id";
        public static final String LABEL = "label";
        public static final String NORMALIZED_NUMBER = "normalized_number";
        public static final String NUMBER = "number";
        public static final String TYPE = "type";
    }

    public interface PhoneticNameStyle {
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int PINYIN = 3;
        public static final int UNDEFINED = 0;
    }

    protected interface PhotoFilesColumns {
        public static final String FILESIZE = "filesize";
        public static final String HEIGHT = "height";
        public static final String WIDTH = "width";
    }

    protected interface PresenceColumns {
        public static final String CUSTOM_PROTOCOL = "custom_protocol";
        public static final String DATA_ID = "presence_data_id";
        public static final String IM_ACCOUNT = "im_account";
        public static final String IM_HANDLE = "im_handle";
        public static final String PROTOCOL = "protocol";
    }

    protected interface RawContactsColumns {
        public static final String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final String AGGREGATION_MODE = "aggregation_mode";
        public static final String BACKUP_ID = "backup_id";
        public static final String CONTACT_ID = "contact_id";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";

        @Deprecated
        public static final String METADATA_DIRTY = "metadata_dirty";
        public static final String RAW_CONTACT_IS_READ_ONLY = "raw_contact_is_read_only";
        public static final String RAW_CONTACT_IS_USER_PROFILE = "raw_contact_is_user_profile";
    }

    public static class SearchSnippets {
        public static final String DEFERRED_SNIPPETING_KEY = "deferred_snippeting";
        public static final String SNIPPET = "snippet";
        public static final String SNIPPET_ARGS_PARAM_KEY = "snippet_args";
    }

    protected interface SettingsColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String ANY_UNSYNCED = "any_unsynced";
        public static final String DATA_SET = "data_set";
        public static final String IS_DEFAULT = "x_is_default";
        public static final String SHOULD_SYNC = "should_sync";
        public static final String UNGROUPED_COUNT = "summ_count";
        public static final String UNGROUPED_VISIBLE = "ungrouped_visible";
        public static final String UNGROUPED_WITH_PHONES = "summ_phones";
    }

    protected interface StatusColumns {
        public static final int AVAILABLE = 5;
        public static final int AWAY = 2;
        public static final int CAPABILITY_HAS_CAMERA = 4;
        public static final int CAPABILITY_HAS_VIDEO = 2;
        public static final int CAPABILITY_HAS_VOICE = 1;
        public static final String CHAT_CAPABILITY = "chat_capability";
        public static final int DO_NOT_DISTURB = 4;
        public static final int IDLE = 3;
        public static final int INVISIBLE = 1;
        public static final int OFFLINE = 0;
        public static final String PRESENCE = "mode";

        @Deprecated
        public static final String PRESENCE_CUSTOM_STATUS = "status";

        @Deprecated
        public static final String PRESENCE_STATUS = "mode";
        public static final String STATUS = "status";
        public static final String STATUS_ICON = "status_icon";
        public static final String STATUS_LABEL = "status_label";
        public static final String STATUS_RES_PACKAGE = "status_res_package";
        public static final String STATUS_TIMESTAMP = "status_ts";
    }

    @Deprecated
    protected interface StreamItemPhotosColumns {

        @Deprecated
        public static final String PHOTO_FILE_ID = "photo_file_id";

        @Deprecated
        public static final String PHOTO_URI = "photo_uri";

        @Deprecated
        public static final String SORT_INDEX = "sort_index";

        @Deprecated
        public static final String STREAM_ITEM_ID = "stream_item_id";

        @Deprecated
        public static final String SYNC1 = "stream_item_photo_sync1";

        @Deprecated
        public static final String SYNC2 = "stream_item_photo_sync2";

        @Deprecated
        public static final String SYNC3 = "stream_item_photo_sync3";

        @Deprecated
        public static final String SYNC4 = "stream_item_photo_sync4";
    }

    @Deprecated
    protected interface StreamItemsColumns {

        @Deprecated
        public static final String ACCOUNT_NAME = "account_name";

        @Deprecated
        public static final String ACCOUNT_TYPE = "account_type";

        @Deprecated
        public static final String COMMENTS = "comments";

        @Deprecated
        public static final String CONTACT_ID = "contact_id";

        @Deprecated
        public static final String CONTACT_LOOKUP_KEY = "contact_lookup";

        @Deprecated
        public static final String DATA_SET = "data_set";

        @Deprecated
        public static final String RAW_CONTACT_ID = "raw_contact_id";

        @Deprecated
        public static final String RAW_CONTACT_SOURCE_ID = "raw_contact_source_id";

        @Deprecated
        public static final String RES_ICON = "icon";

        @Deprecated
        public static final String RES_LABEL = "label";

        @Deprecated
        public static final String RES_PACKAGE = "res_package";

        @Deprecated
        public static final String SYNC1 = "stream_item_sync1";

        @Deprecated
        public static final String SYNC2 = "stream_item_sync2";

        @Deprecated
        public static final String SYNC3 = "stream_item_sync3";

        @Deprecated
        public static final String SYNC4 = "stream_item_sync4";

        @Deprecated
        public static final String TEXT = "text";

        @Deprecated
        public static final String TIMESTAMP = "timestamp";
    }

    protected interface SyncColumns extends BaseSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DIRTY = "dirty";
        public static final String SOURCE_ID = "sourceid";
        public static final String VERSION = "version";
    }

    @Deprecated
    public interface SyncStateColumns extends SyncStateContract.Columns {
    }

    public static boolean isProfileId(long j) {
        return j >= Profile.MIN_ID;
    }

    public static final class Directory implements BaseColumns {
        public static final String ACCOUNT_NAME = "accountName";
        public static final String ACCOUNT_TYPE = "accountType";
        public static final String CALLER_PACKAGE_PARAM_KEY = "callerPackage";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_directory";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_directories";
        public static final long DEFAULT = 0;
        public static final String DIRECTORY_AUTHORITY = "authority";
        public static final String DISPLAY_NAME = "displayName";
        public static final long ENTERPRISE_DEFAULT = 1000000000;
        public static final long ENTERPRISE_DIRECTORY_ID_BASE = 1000000000;
        public static final long ENTERPRISE_LOCAL_INVISIBLE = 1000000001;
        public static final String EXPORT_SUPPORT = "exportSupport";
        public static final int EXPORT_SUPPORT_ANY_ACCOUNT = 2;
        public static final int EXPORT_SUPPORT_NONE = 0;
        public static final int EXPORT_SUPPORT_SAME_ACCOUNT_ONLY = 1;
        public static final long LOCAL_INVISIBLE = 1;
        public static final String PACKAGE_NAME = "packageName";
        public static final String PHOTO_SUPPORT = "photoSupport";
        public static final int PHOTO_SUPPORT_FULL = 3;
        public static final int PHOTO_SUPPORT_FULL_SIZE_ONLY = 2;
        public static final int PHOTO_SUPPORT_NONE = 0;
        public static final int PHOTO_SUPPORT_THUMBNAIL_ONLY = 1;
        public static final String SHORTCUT_SUPPORT = "shortcutSupport";
        public static final int SHORTCUT_SUPPORT_DATA_ITEMS_ONLY = 1;
        public static final int SHORTCUT_SUPPORT_FULL = 2;
        public static final int SHORTCUT_SUPPORT_NONE = 0;
        public static final String TYPE_RESOURCE_ID = "typeResourceId";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "directories");
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "directories_enterprise");
        public static final Uri ENTERPRISE_FILE_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "directory_file_enterprise");

        public static boolean isEnterpriseDirectoryId(long j) {
            return j >= 1000000000;
        }

        public static boolean isRemoteDirectoryId(long j) {
            return (j == 0 || j == 1 || j == 1000000000 || j == ENTERPRISE_LOCAL_INVISIBLE) ? false : true;
        }

        private Directory() {
        }

        public static boolean isRemoteDirectory(long j) {
            return isRemoteDirectoryId(j);
        }

        public static void notifyDirectoryChange(ContentResolver contentResolver) {
            contentResolver.update(CONTENT_URI, new ContentValues(), null, null);
        }
    }

    public static final class SyncState implements SyncStateContract.Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "syncstate");

        private SyncState() {
        }

        public static byte[] get(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static void set(ContentProviderClient contentProviderClient, Account account, byte[] bArr) throws RemoteException {
            SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, bArr);
        }

        public static ContentProviderOperation newSetOperation(Account account, byte[] bArr) {
            return SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, bArr);
        }
    }

    public static final class ProfileSyncState implements SyncStateContract.Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "syncstate");

        private ProfileSyncState() {
        }

        public static byte[] get(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static void set(ContentProviderClient contentProviderClient, Account account, byte[] bArr) throws RemoteException {
            SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, bArr);
        }

        public static ContentProviderOperation newSetOperation(Account account, byte[] bArr) {
            return SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, bArr);
        }
    }

    public static class Contacts implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns, ContactStatusColumns, ContactCounts {
        public static final Uri CONTENT_FILTER_URI;

        @Deprecated
        public static final Uri CONTENT_FREQUENT_URI;
        public static final Uri CONTENT_GROUP_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact";
        public static final Uri CONTENT_LOOKUP_URI;
        public static final Uri CONTENT_MULTI_VCARD_URI;
        public static final Uri CONTENT_STREQUENT_FILTER_URI;
        public static final Uri CONTENT_STREQUENT_URI;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact";
        public static final Uri CONTENT_URI;
        public static final String CONTENT_VCARD_TYPE = "text/x-vcard";
        public static final Uri CONTENT_VCARD_URI;
        public static final Uri CORP_CONTENT_URI;
        public static long ENTERPRISE_CONTACT_ID_BASE = 0;
        public static String ENTERPRISE_CONTACT_LOOKUP_PREFIX = null;
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
        public static final Uri ENTERPRISE_CONTENT_URI;
        public static final String QUERY_PARAMETER_VCARD_NO_PHOTO = "no_photo";

        @Deprecated
        public static void markAsContacted(ContentResolver contentResolver, long j) {
        }

        private Contacts() {
        }

        static {
            Uri uriWithAppendedPath = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, android.provider.Contacts.AUTHORITY);
            CONTENT_URI = uriWithAppendedPath;
            ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(uriWithAppendedPath, ApnSetting.TYPE_ENTERPRISE_STRING);
            CORP_CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "contacts_corp");
            CONTENT_LOOKUP_URI = Uri.withAppendedPath(uriWithAppendedPath, "lookup");
            CONTENT_VCARD_URI = Uri.withAppendedPath(uriWithAppendedPath, "as_vcard");
            CONTENT_MULTI_VCARD_URI = Uri.withAppendedPath(uriWithAppendedPath, "as_multi_vcard");
            CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter");
            ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter_enterprise");
            Uri uriWithAppendedPath2 = Uri.withAppendedPath(uriWithAppendedPath, "strequent");
            CONTENT_STREQUENT_URI = uriWithAppendedPath2;
            CONTENT_FREQUENT_URI = Uri.withAppendedPath(uriWithAppendedPath, CallLog.Calls.SEM_FREQUENT);
            CONTENT_STREQUENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath2, "filter");
            CONTENT_GROUP_URI = Uri.withAppendedPath(uriWithAppendedPath, "group");
            ENTERPRISE_CONTACT_ID_BASE = 1000000000L;
            ENTERPRISE_CONTACT_LOOKUP_PREFIX = "c-";
        }

        public static Uri getLookupUri(ContentResolver contentResolver, Uri uri) {
            Cursor cursorQuery = contentResolver.query(uri, new String[]{"lookup", "_id"}, null, null, null);
            if (cursorQuery == null) {
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst()) {
                    return null;
                }
                return getLookupUri(cursorQuery.getLong(1), cursorQuery.getString(0));
            } finally {
                cursorQuery.close();
            }
        }

        public static Uri getLookupUri(long j, String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return ContentUris.withAppendedId(Uri.withAppendedPath(CONTENT_LOOKUP_URI, str), j);
        }

        public static Uri lookupContact(ContentResolver contentResolver, Uri uri) {
            Cursor cursorQuery;
            if (uri == null || (cursorQuery = contentResolver.query(uri, new String[]{"_id"}, null, null, null)) == null) {
                return null;
            }
            try {
                if (cursorQuery.moveToFirst()) {
                    return ContentUris.withAppendedId(CONTENT_URI, cursorQuery.getLong(0));
                }
                return null;
            } finally {
                cursorQuery.close();
            }
        }

        public static boolean isEnterpriseContactId(long j) {
            return j >= ENTERPRISE_CONTACT_ID_BASE && j < Profile.MIN_ID;
        }

        public static final class Data implements BaseColumns, DataColumns {
            public static final String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class Entity implements BaseColumns, ContactsColumns, ContactNameColumns, RawContactsColumns, BaseSyncColumns, SyncColumns, DataColumns, StatusColumns, ContactOptionsColumns, ContactStatusColumns, DataUsageStatColumns {
            public static final String CONTENT_DIRECTORY = "entities";
            public static final String DATA_ID = "data_id";
            public static final String RAW_CONTACT_ID = "raw_contact_id";

            private Entity() {
            }
        }

        @Deprecated
        public static final class StreamItems implements StreamItemsColumns {

            @Deprecated
            public static final String CONTENT_DIRECTORY = "stream_items";

            @Deprecated
            private StreamItems() {
            }
        }

        public static final class AggregationSuggestions implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactStatusColumns {
            public static final String CONTENT_DIRECTORY = "suggestions";
            public static final String PARAMETER_MATCH_NAME = "name";

            private AggregationSuggestions() {
            }

            public static final class Builder {
                private long mContactId;
                private int mLimit;
                private final ArrayList<String> mValues = new ArrayList<>();

                public Builder setContactId(long j) {
                    this.mContactId = j;
                    return this;
                }

                public Builder addNameParameter(String str) {
                    this.mValues.add(str);
                    return this;
                }

                public Builder setLimit(int i) {
                    this.mLimit = i;
                    return this;
                }

                public Uri build() {
                    Uri.Builder builderBuildUpon = Contacts.CONTENT_URI.buildUpon();
                    builderBuildUpon.appendEncodedPath(String.valueOf(this.mContactId));
                    builderBuildUpon.appendPath(AggregationSuggestions.CONTENT_DIRECTORY);
                    int i = this.mLimit;
                    if (i != 0) {
                        builderBuildUpon.appendQueryParameter("limit", String.valueOf(i));
                    }
                    int size = this.mValues.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        builderBuildUpon.appendQueryParameter("query", "name:" + this.mValues.get(i2));
                    }
                    return builderBuildUpon.build();
                }
            }

            public static final Builder builder() {
                return new Builder();
            }
        }

        public static final class Photo implements BaseColumns, DataColumnsWithJoins {
            public static final String CONTENT_DIRECTORY = "photo";
            public static final String DISPLAY_PHOTO = "display_photo";
            public static final String PHOTO = "data15";
            public static final String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        public static InputStream openContactPhotoInputStream(ContentResolver contentResolver, Uri uri, boolean z) {
            if (z) {
                try {
                    AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(Uri.withAppendedPath(uri, "display_photo"), "r");
                    if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        return assetFileDescriptorOpenAssetFileDescriptor.createInputStream();
                    }
                } catch (IOException unused) {
                }
            }
            Uri uriWithAppendedPath = Uri.withAppendedPath(uri, "photo");
            if (uriWithAppendedPath == null) {
                return null;
            }
            Cursor cursorQuery = contentResolver.query(uriWithAppendedPath, new String[]{"data15"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        byte[] blob = cursorQuery.getBlob(0);
                        if (blob == null) {
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return null;
                        }
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(blob);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return byteArrayInputStream;
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        }

        public static InputStream openContactPhotoInputStream(ContentResolver contentResolver, Uri uri) {
            return openContactPhotoInputStream(contentResolver, uri, false);
        }

        public static Uri createCorpLookupUriFromEnterpriseLookupUri(Uri uri) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments != null && pathSegments.size() > 2) {
                String str = pathSegments.get(2);
                if (!TextUtils.isEmpty(str) && str.startsWith(ENTERPRISE_CONTACT_LOOKUP_PREFIX)) {
                    return Uri.withAppendedPath(CONTENT_LOOKUP_URI, str.substring(ENTERPRISE_CONTACT_LOOKUP_PREFIX.length()));
                }
            }
            return null;
        }
    }

    public static final class Profile implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns, ContactStatusColumns {
        public static final Uri CONTENT_RAW_CONTACTS_URI;
        public static final Uri CONTENT_URI;
        public static final Uri CONTENT_VCARD_URI;
        public static final long MIN_ID = 9223372034707292160L;

        private Profile() {
        }

        static {
            Uri uriWithAppendedPath = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "profile");
            CONTENT_URI = uriWithAppendedPath;
            CONTENT_VCARD_URI = Uri.withAppendedPath(uriWithAppendedPath, "as_vcard");
            CONTENT_RAW_CONTACTS_URI = Uri.withAppendedPath(uriWithAppendedPath, "raw_contacts");
        }
    }

    public static final class DeletedContacts implements DeletedContactsColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "deleted_contacts");
        private static final int DAYS_KEPT = 30;
        public static final long DAYS_KEPT_MILLISECONDS = 2592000000L;

        private DeletedContacts() {
        }
    }

    public static final class RawContacts implements BaseColumns, RawContactsColumns, ContactOptionsColumns, ContactNameColumns, SyncColumns {
        public static final int AGGREGATION_MODE_DEFAULT = 0;
        public static final int AGGREGATION_MODE_DISABLED = 3;

        @Deprecated
        public static final int AGGREGATION_MODE_IMMEDIATE = 1;
        public static final int AGGREGATION_MODE_SUSPENDED = 2;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/raw_contact";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "raw_contacts");

        private RawContacts() {
        }

        public static Uri getContactLookupUri(ContentResolver contentResolver, Uri uri) {
            Cursor cursorQuery = contentResolver.query(Uri.withAppendedPath(uri, "data"), new String[]{"contact_id", "lookup"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        Uri lookupUri = Contacts.getLookupUri(cursorQuery.getLong(0), cursorQuery.getString(1));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return lookupUri;
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        }

        public static String getLocalAccountName(Context context) {
            return TextUtils.nullIfEmpty(context.getString(R.string.config_rawContactsLocalAccountName));
        }

        public static String getLocalAccountType(Context context) {
            return TextUtils.nullIfEmpty(context.getString(R.string.config_rawContactsLocalAccountType));
        }

        public static final class DefaultAccount {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            public static final String ACTION_MOVE_CONTACTS_TO_DEFAULT_ACCOUNT = "android.provider.action.MOVE_CONTACTS_TO_DEFAULT_ACCOUNT";
            public static final String GET_NUMBER_OF_MOVABLE_LOCAL_CONTACTS_METHOD = "getNumberOfMovableLocalContacts";
            public static final String GET_NUMBER_OF_MOVABLE_SIM_CONTACTS_METHOD = "getNumberOfMovableSimContacts";
            public static final String KEY_DEFAULT_ACCOUNT_STATE = "key_default_account_state";
            public static final String KEY_ELIGIBLE_DEFAULT_ACCOUNTS = "key_eligible_default_accounts";
            public static final String KEY_NUMBER_OF_MOVABLE_LOCAL_CONTACTS = "key_number_of_movable_local_contacts";
            public static final String KEY_NUMBER_OF_MOVABLE_SIM_CONTACTS = "key_number_of_movable_sim_contacts";
            public static final String MOVE_LOCAL_CONTACTS_TO_CLOUD_DEFAULT_ACCOUNT_METHOD = "moveLocalContactsToCloudDefaultAccount";
            public static final String MOVE_SIM_CONTACTS_TO_CLOUD_DEFAULT_ACCOUNT_METHOD = "moveSimContactsToCloudDefaultAccount";
            public static final String QUERY_DEFAULT_ACCOUNT_FOR_NEW_CONTACTS_METHOD = "queryDefaultAccountForNewContacts";
            public static final String QUERY_ELIGIBLE_DEFAULT_ACCOUNTS_METHOD = "queryEligibleDefaultAccounts";
            public static final String SET_DEFAULT_ACCOUNT_FOR_NEW_CONTACTS_METHOD = "setDefaultAccountForNewContacts";

            private DefaultAccount() {
            }

            public static final class DefaultAccountAndState {
                public static final int DEFAULT_ACCOUNT_STATE_CLOUD = 3;
                public static final int DEFAULT_ACCOUNT_STATE_LOCAL = 2;
                public static final int DEFAULT_ACCOUNT_STATE_NOT_SET = 1;
                public static final int DEFAULT_ACCOUNT_STATE_SIM = 4;
                private final Account mAccount;
                private final int mState;

                @Retention(RetentionPolicy.SOURCE)
                public @interface DefaultAccountState {
                }

                public static boolean isCloudOrSimAccount(int i) {
                    return i == 3 || i == 4;
                }

                private static boolean isValidDefaultAccountState(int i) {
                    return i == 1 || i == 2 || i == 3 || i == 4;
                }

                private DefaultAccountAndState(int i, Account account) {
                    if (!isValidDefaultAccountState(i)) {
                        throw new IllegalArgumentException("Invalid default account state.");
                    }
                    if (isCloudOrSimAccount(i) != (account != null)) {
                        throw new IllegalArgumentException("Default account can be set to cloud or SIM if and only if the account is provided.");
                    }
                    this.mState = i;
                    this.mAccount = isCloudOrSimAccount(i) ? account : null;
                }

                public static DefaultAccountAndState ofCloud(Account account) {
                    return new DefaultAccountAndState(3, account);
                }

                public static DefaultAccountAndState ofSim(Account account) {
                    return new DefaultAccountAndState(4, account);
                }

                public static DefaultAccountAndState ofLocal() {
                    return new DefaultAccountAndState(2, null);
                }

                public static DefaultAccountAndState ofNotSet() {
                    return new DefaultAccountAndState(1, null);
                }

                public int getState() {
                    return this.mState;
                }

                public Account getAccount() {
                    return this.mAccount;
                }

                public int hashCode() {
                    return Objects.hash(Integer.valueOf(this.mState), this.mAccount);
                }

                public boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj instanceof DefaultAccountAndState) {
                        DefaultAccountAndState defaultAccountAndState = (DefaultAccountAndState) obj;
                        if (this.mState == defaultAccountAndState.mState && Objects.equals(this.mAccount, defaultAccountAndState.mAccount)) {
                            return true;
                        }
                    }
                    return false;
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            public static DefaultAccountAndState getDefaultAccountForNewContacts(ContentResolver contentResolver) {
                Object[] objArr = 0;
                Object[] objArr2 = 0;
                Bundle bundleNullSafeCall = ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, QUERY_DEFAULT_ACCOUNT_FOR_NEW_CONTACTS_METHOD, null, null);
                int i = bundleNullSafeCall.getInt(KEY_DEFAULT_ACCOUNT_STATE, -1);
                if (!DefaultAccountAndState.isCloudOrSimAccount(i)) {
                    if (i == 2 || i == 1) {
                        return new DefaultAccountAndState(i, objArr2 == true ? 1 : 0);
                    }
                    throw new IllegalStateException("Invalid default account state");
                }
                String string = bundleNullSafeCall.getString("account_name");
                String string2 = bundleNullSafeCall.getString("account_type");
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                    throw new IllegalStateException("account name and type cannot be null or empty");
                }
                return new DefaultAccountAndState(i, new Account(string, string2));
            }

            @SystemApi
            public static void setDefaultAccountForNewContacts(ContentResolver contentResolver, DefaultAccountAndState defaultAccountAndState) {
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_DEFAULT_ACCOUNT_STATE, defaultAccountAndState.getState());
                if (DefaultAccountAndState.isCloudOrSimAccount(defaultAccountAndState.getState())) {
                    Account account = defaultAccountAndState.getAccount();
                    bundle.putString("account_name", account.name);
                    bundle.putString("account_type", account.type);
                }
                ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, SET_DEFAULT_ACCOUNT_FOR_NEW_CONTACTS_METHOD, null, bundle);
            }

            @SystemApi
            public static List<Account> getEligibleCloudAccounts(ContentResolver contentResolver) {
                ArrayList parcelableArrayList = ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, QUERY_ELIGIBLE_DEFAULT_ACCOUNTS_METHOD, null, null).getParcelableArrayList(KEY_ELIGIBLE_DEFAULT_ACCOUNTS, Account.class);
                return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
            }

            @SystemApi
            public static void moveLocalContactsToCloudDefaultAccount(ContentResolver contentResolver) {
                ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, MOVE_LOCAL_CONTACTS_TO_CLOUD_DEFAULT_ACCOUNT_METHOD, null, new Bundle());
            }

            @SystemApi
            public static void moveSimContactsToCloudDefaultAccount(ContentResolver contentResolver) {
                ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, MOVE_SIM_CONTACTS_TO_CLOUD_DEFAULT_ACCOUNT_METHOD, null, null);
            }

            @SystemApi
            public static int getNumberOfMovableLocalContacts(ContentResolver contentResolver) {
                return ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, GET_NUMBER_OF_MOVABLE_LOCAL_CONTACTS_METHOD, null, null).getInt(KEY_NUMBER_OF_MOVABLE_LOCAL_CONTACTS, 0);
            }

            @SystemApi
            public static int getNumberOfMovableSimContacts(ContentResolver contentResolver) {
                return ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, GET_NUMBER_OF_MOVABLE_SIM_CONTACTS_METHOD, null, null).getInt(KEY_NUMBER_OF_MOVABLE_SIM_CONTACTS, 0);
            }
        }

        public static final class Data implements BaseColumns, DataColumns {
            public static final String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class Entity implements BaseColumns, DataColumns {
            public static final String CONTENT_DIRECTORY = "entity";
            public static final String DATA_ID = "data_id";

            private Entity() {
            }
        }

        @Deprecated
        public static final class StreamItems implements BaseColumns, StreamItemsColumns {

            @Deprecated
            public static final String CONTENT_DIRECTORY = "stream_items";

            @Deprecated
            private StreamItems() {
            }
        }

        public static final class DisplayPhoto {
            public static final String CONTENT_DIRECTORY = "display_photo";

            private DisplayPhoto() {
            }
        }

        public static EntityIterator newEntityIterator(Cursor cursor) {
            return new EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends CursorEntityIterator {
            private static final String[] DATA_KEYS = {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", DataColumns.DATA12, DataColumns.DATA13, "data14", "data15", DataColumns.SYNC1, DataColumns.SYNC2, DataColumns.SYNC3, DataColumns.SYNC4};

            public EntityIteratorImpl(Cursor cursor) {
                super(cursor);
            }

            @Override // android.content.CursorEntityIterator
            public android.content.Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException, IllegalArgumentException {
                int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_id");
                long j = cursor.getLong(columnIndexOrThrow);
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data_set");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "version");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sourceid");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync1");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync2");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync3");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync4");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "deleted");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "contact_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "starred");
                android.content.Entity entity = new android.content.Entity(contentValues);
                while (j == cursor.getLong(columnIndexOrThrow)) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("_id", Long.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("data_id"))));
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, "res_package");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, "mimetype");
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, DataColumns.IS_PRIMARY);
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, DataColumns.IS_SUPER_PRIMARY);
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, DataColumns.DATA_VERSION);
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, CommonDataKinds.GroupMembership.GROUP_SOURCE_ID);
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, DataColumns.DATA_VERSION);
                    for (String str : DATA_KEYS) {
                        int columnIndexOrThrow2 = cursor.getColumnIndexOrThrow(str);
                        int type = cursor.getType(columnIndexOrThrow2);
                        if (type != 0) {
                            if (type == 1 || type == 2 || type == 3) {
                                contentValues2.put(str, cursor.getString(columnIndexOrThrow2));
                            } else if (type == 4) {
                                contentValues2.put(str, cursor.getBlob(columnIndexOrThrow2));
                            } else {
                                throw new IllegalStateException("Invalid or unhandled data type");
                            }
                        }
                    }
                    entity.addSubValue(Data.CONTENT_URI, contentValues2);
                    if (!cursor.moveToNext()) {
                        break;
                    }
                }
                return entity;
            }
        }
    }

    @Deprecated
    public static final class StreamItems implements BaseColumns, StreamItemsColumns {

        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item";

        @Deprecated
        public static final Uri CONTENT_LIMIT_URI;

        @Deprecated
        public static final Uri CONTENT_PHOTO_URI;

        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item";

        @Deprecated
        public static final Uri CONTENT_URI;

        @Deprecated
        public static final String MAX_ITEMS = "max_items";

        @Deprecated
        private StreamItems() {
        }

        static {
            Uri uriWithAppendedPath = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "stream_items");
            CONTENT_URI = uriWithAppendedPath;
            CONTENT_PHOTO_URI = Uri.withAppendedPath(uriWithAppendedPath, "photo");
            CONTENT_LIMIT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "stream_items_limit");
        }

        @Deprecated
        public static final class StreamItemPhotos implements BaseColumns, StreamItemPhotosColumns {

            @Deprecated
            public static final String CONTENT_DIRECTORY = "photo";

            @Deprecated
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item_photo";

            @Deprecated
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item_photo";

            @Deprecated
            private StreamItemPhotos() {
            }
        }
    }

    @Deprecated
    public static final class StreamItemPhotos implements BaseColumns, StreamItemPhotosColumns {

        @Deprecated
        public static final String PHOTO = "photo";

        @Deprecated
        private StreamItemPhotos() {
        }
    }

    public static final class PhotoFiles implements BaseColumns, PhotoFilesColumns {
        private PhotoFiles() {
        }
    }

    public static final class Data implements DataColumnsWithJoins, ContactCounts {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/data";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "data");
        static final Uri ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "data_enterprise");
        public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";

        private Data() {
        }

        public static Uri getContactLookupUri(ContentResolver contentResolver, Uri uri) {
            Cursor cursorQuery = contentResolver.query(uri, new String[]{"contact_id", "lookup"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        Uri lookupUri = Contacts.getLookupUri(cursorQuery.getLong(0), cursorQuery.getString(1));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return lookupUri;
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        }
    }

    public static final class RawContactsEntity implements BaseColumns, DataColumns, RawContactsColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact_entity";
        public static final String DATA_ID = "data_id";
        public static final String FOR_EXPORT_ONLY = "for_export_only";
        private static final String TAG = "ContactsContract.RawContactsEntity";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "raw_contact_entities");
        public static final Uri CORP_CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "raw_contact_entities_corp");
        public static final Uri PROFILE_CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "raw_contact_entities");

        private RawContactsEntity() {
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static Map<String, List<ContentValues>> queryRawContactEntity(ContentResolver contentResolver, long j) {
            Uri uri = CONTENT_URI;
            if (Contacts.isEnterpriseContactId(j)) {
                uri = CORP_CONTENT_URI;
                j -= Contacts.ENTERPRISE_CONTACT_ID_BASE;
            }
            Uri uri2 = uri;
            HashMap map = new HashMap();
            try {
                EntityIterator entityIteratorNewEntityIterator = RawContacts.newEntityIterator(contentResolver.query(uri2, null, "contact_id=?", new String[]{String.valueOf(j)}, null));
                if (entityIteratorNewEntityIterator == null) {
                    Log.e(TAG, "EntityIterator is null");
                    if (entityIteratorNewEntityIterator != null) {
                        entityIteratorNewEntityIterator.close();
                        return map;
                    }
                } else if (!entityIteratorNewEntityIterator.hasNext()) {
                    Log.w(TAG, "Data does not exist. contactId: " + j);
                    if (entityIteratorNewEntityIterator != null) {
                        entityIteratorNewEntityIterator.close();
                        return map;
                    }
                } else {
                    while (entityIteratorNewEntityIterator.hasNext()) {
                        Iterator<Entity.NamedContentValues> it = entityIteratorNewEntityIterator.next().getSubValues().iterator();
                        while (it.hasNext()) {
                            ContentValues contentValues = it.next().values;
                            String asString = contentValues.getAsString("mimetype");
                            if (asString != null) {
                                List arrayList = (List) map.get(asString);
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    map.put(asString, arrayList);
                                }
                                arrayList.add(contentValues);
                            }
                        }
                    }
                    if (entityIteratorNewEntityIterator != null) {
                        entityIteratorNewEntityIterator.close();
                    }
                }
                return map;
            } finally {
            }
        }
    }

    public static final class PhoneLookup implements BaseColumns, PhoneLookupColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_lookup";
        public static final String QUERY_PARAMETER_SIP_ADDRESS = "sip";
        public static final Uri CONTENT_FILTER_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "phone_lookup");
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "phone_lookup_enterprise");

        private PhoneLookup() {
        }
    }

    public static class StatusUpdates implements StatusColumns, PresenceColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/status-update";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/status-update";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "status_updates");
        public static final Uri PROFILE_CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "status_updates");

        public static final int getPresenceIconResourceId(int i) {
            if (i == 1) {
                return 17301609;
            }
            if (i == 2 || i == 3) {
                return 17301607;
            }
            if (i != 4) {
                return i != 5 ? 17301610 : 17301611;
            }
            return 17301608;
        }

        public static final int getPresencePrecedence(int i) {
            return i;
        }

        private StatusUpdates() {
        }
    }

    @Deprecated
    public static final class Presence extends StatusUpdates {
        public Presence() {
            super();
        }
    }

    public static final class CommonDataKinds {
        public static final String PACKAGE_COMMON = "common";

        public interface BaseTypes {
            public static final int TYPE_CUSTOM = 0;
        }

        protected interface CommonColumns extends BaseTypes {
            public static final String DATA = "data1";
            public static final String LABEL = "data3";
            public static final String TYPE = "data2";
        }

        private CommonDataKinds() {
        }

        public static final class StructuredName implements DataColumnsWithJoins, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name";
            public static final String DISPLAY_NAME = "data1";
            public static final String FAMILY_NAME = "data3";
            public static final String FULL_NAME_STYLE = "data10";
            public static final String GIVEN_NAME = "data2";
            public static final String MIDDLE_NAME = "data5";
            public static final String PHONETIC_FAMILY_NAME = "data9";
            public static final String PHONETIC_GIVEN_NAME = "data7";
            public static final String PHONETIC_MIDDLE_NAME = "data8";
            public static final String PHONETIC_NAME_STYLE = "data11";
            public static final String PREFIX = "data4";
            public static final String SUFFIX = "data6";

            private StructuredName() {
            }
        }

        public static final class Nickname implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/nickname";
            public static final String NAME = "data1";
            public static final int TYPE_DEFAULT = 1;
            public static final int TYPE_INITIALS = 5;
            public static final int TYPE_MAIDEN_NAME = 3;

            @Deprecated
            public static final int TYPE_MAINDEN_NAME = 3;
            public static final int TYPE_OTHER_NAME = 2;
            public static final int TYPE_SHORT_NAME = 4;

            private Nickname() {
            }
        }

        public static final class Phone implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_v2";
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_v2";
            public static final Uri CONTENT_URI;
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
            public static final Uri ENTERPRISE_CONTENT_URI;
            public static final String NORMALIZED_NUMBER = "data4";
            public static final String NUMBER = "data1";
            public static final String SEARCH_DISPLAY_NAME_KEY = "search_display_name";
            public static final String SEARCH_PHONE_NUMBER_KEY = "search_phone_number";
            public static final int SEM_TYPE_CONFERENCE = 1021;
            public static final int TYPE_ASSISTANT = 19;
            public static final int TYPE_CALLBACK = 8;
            public static final int TYPE_CAR = 9;
            public static final int TYPE_COMPANY_MAIN = 10;
            public static final int TYPE_FAX_HOME = 5;
            public static final int TYPE_FAX_WORK = 4;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_ISDN = 11;
            public static final int TYPE_MAIN = 12;
            public static final int TYPE_MMS = 20;
            public static final int TYPE_MOBILE = 2;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_OTHER_FAX = 13;
            public static final int TYPE_PAGER = 6;
            public static final int TYPE_RADIO = 14;
            public static final int TYPE_TELEX = 15;
            public static final int TYPE_TTY_TDD = 16;
            public static final int TYPE_WORK = 3;
            public static final int TYPE_WORK_MOBILE = 17;
            public static final int TYPE_WORK_PAGER = 18;

            public static final int getTypeLabelResource(int i) {
                if (i == 1021) {
                    return R.string.phoneTypeConference;
                }
                switch (i) {
                    case 1:
                        return R.string.phoneTypeHome;
                    case 2:
                        return R.string.phoneTypeMobile;
                    case 3:
                        return R.string.phoneTypeWork;
                    case 4:
                        return R.string.phoneTypeFaxWork;
                    case 5:
                        return R.string.phoneTypeFaxHome;
                    case 6:
                        return R.string.phoneTypePager;
                    case 7:
                        return R.string.phoneTypeOther;
                    case 8:
                        return R.string.phoneTypeCallback;
                    case 9:
                        return R.string.phoneTypeCar;
                    case 10:
                        return R.string.phoneTypeCompanyMain;
                    case 11:
                        return R.string.phoneTypeIsdn;
                    case 12:
                        return R.string.phoneTypeMain;
                    case 13:
                        return R.string.phoneTypeOtherFax;
                    case 14:
                        return R.string.phoneTypeRadio;
                    case 15:
                        return R.string.phoneTypeTelex;
                    case 16:
                        return R.string.phoneTypeTtyTdd;
                    case 17:
                        return R.string.phoneTypeWorkMobile;
                    case 18:
                        return R.string.phoneTypeWorkPager;
                    case 19:
                        return R.string.phoneTypeAssistant;
                    case 20:
                        return R.string.phoneTypeMms;
                    default:
                        return R.string.phoneTypeCustom;
                }
            }

            private Phone() {
            }

            static {
                Uri uriWithAppendedPath = Uri.withAppendedPath(Data.CONTENT_URI, Contacts.People.Phones.CONTENT_DIRECTORY);
                CONTENT_URI = uriWithAppendedPath;
                ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(Data.ENTERPRISE_CONTENT_URI, Contacts.People.Phones.CONTENT_DIRECTORY);
                CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter_enterprise");
            }

            @Deprecated
            public static final CharSequence getDisplayLabel(Context context, int i, CharSequence charSequence, CharSequence[] charSequenceArr) {
                return getTypeLabel(context.getResources(), i, charSequence);
            }

            @Deprecated
            public static final CharSequence getDisplayLabel(Context context, int i, CharSequence charSequence) {
                return getTypeLabel(context.getResources(), i, charSequence);
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return ((i == 0 || i == 19) && !TextUtils.isEmpty(charSequence)) ? charSequence : resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class Email implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String ADDRESS = "data1";
            public static final Uri CONTENT_FILTER_URI;
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/email_v2";
            public static final Uri CONTENT_LOOKUP_URI;
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/email_v2";
            public static final Uri CONTENT_URI;
            public static final String DISPLAY_NAME = "data4";
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
            public static final Uri ENTERPRISE_CONTENT_LOOKUP_URI;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_MOBILE = 4;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            public static final int getTypeLabelResource(int i) {
                return i != 1 ? i != 2 ? i != 3 ? i != 4 ? R.string.emailTypeCustom : R.string.emailTypeMobile : R.string.emailTypeOther : R.string.emailTypeWork : R.string.emailTypeHome;
            }

            private Email() {
            }

            static {
                Uri uriWithAppendedPath = Uri.withAppendedPath(Data.CONTENT_URI, "emails");
                CONTENT_URI = uriWithAppendedPath;
                CONTENT_LOOKUP_URI = Uri.withAppendedPath(uriWithAppendedPath, "lookup");
                ENTERPRISE_CONTENT_LOOKUP_URI = Uri.withAppendedPath(uriWithAppendedPath, "lookup_enterprise");
                CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter_enterprise");
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }
        }

        public static final class StructuredPostal implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CITY = "data7";
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/postal-address_v2";
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/postal-address_v2";
            public static final Uri CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "postals");
            public static final String COUNTRY = "data10";
            public static final String FORMATTED_ADDRESS = "data1";
            public static final String NEIGHBORHOOD = "data6";
            public static final String POBOX = "data5";
            public static final String POSTCODE = "data9";
            public static final String REGION = "data8";
            public static final String STREET = "data4";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            public static final int getTypeLabelResource(int i) {
                return i != 1 ? i != 2 ? i != 3 ? R.string.postalTypeCustom : R.string.postalTypeOther : R.string.postalTypeWork : R.string.postalTypeHome;
            }

            private StructuredPostal() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }
        }

        @Deprecated
        public static final class Im implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im";
            public static final String CUSTOM_PROTOCOL = "data6";
            public static final String PROTOCOL = "data5";

            @Deprecated
            public static final int PROTOCOL_AIM = 0;
            public static final int PROTOCOL_CUSTOM = -1;

            @Deprecated
            public static final int PROTOCOL_GOOGLE_TALK = 5;

            @Deprecated
            public static final int PROTOCOL_ICQ = 6;

            @Deprecated
            public static final int PROTOCOL_JABBER = 7;

            @Deprecated
            public static final int PROTOCOL_MSN = 1;

            @Deprecated
            public static final int PROTOCOL_NETMEETING = 8;

            @Deprecated
            public static final int PROTOCOL_QQ = 4;

            @Deprecated
            public static final int PROTOCOL_SKYPE = 3;

            @Deprecated
            public static final int PROTOCOL_YAHOO = 2;
            public static final int SEM_PROTOCOL_FACEBOOK = 10;
            public static final int SEM_PROTOCOL_WHATSAPP = 9;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            public static final int getProtocolLabelResource(int i) {
                switch (i) {
                    case 0:
                        return R.string.imProtocolAim;
                    case 1:
                        return R.string.imProtocolMsn;
                    case 2:
                        return R.string.imProtocolYahoo;
                    case 3:
                        return R.string.imProtocolSkype;
                    case 4:
                        return R.string.imProtocolQq;
                    case 5:
                        return R.string.imProtocolGoogleTalk;
                    case 6:
                        return R.string.imProtocolIcq;
                    case 7:
                        return R.string.imProtocolJabber;
                    case 8:
                        return R.string.imProtocolNetMeeting;
                    case 9:
                        return R.string.imProtocolWhatsApp;
                    case 10:
                        return R.string.imProtocolFacebook;
                    default:
                        return R.string.imProtocolCustom;
                }
            }

            public static final int getTypeLabelResource(int i) {
                return i != 1 ? i != 2 ? i != 3 ? R.string.imTypeCustom : R.string.imTypeOther : R.string.imTypeWork : R.string.imTypeHome;
            }

            private Im() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }

            public static final CharSequence getProtocolLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != -1 || TextUtils.isEmpty(charSequence)) ? resources.getText(getProtocolLabelResource(i)) : charSequence;
            }
        }

        public static final class Organization implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String COMPANY = "data1";
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/organization";
            public static final String DEPARTMENT = "data5";
            public static final String JOB_DESCRIPTION = "data6";
            public static final String OFFICE_LOCATION = "data9";
            public static final String PHONETIC_NAME = "data8";
            public static final String PHONETIC_NAME_STYLE = "data10";
            public static final String SYMBOL = "data7";
            public static final String TITLE = "data4";
            public static final int TYPE_OTHER = 2;
            public static final int TYPE_WORK = 1;

            public static final int getTypeLabelResource(int i) {
                return i != 1 ? i != 2 ? R.string.orgTypeCustom : R.string.orgTypeOther : R.string.orgTypeWork;
            }

            private Organization() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }
        }

        public static final class Relation implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/relation";
            public static final String NAME = "data1";
            public static final int TYPE_ASSISTANT = 1;
            public static final int TYPE_BROTHER = 2;
            public static final int TYPE_CHILD = 3;
            public static final int TYPE_DOMESTIC_PARTNER = 4;
            public static final int TYPE_FATHER = 5;
            public static final int TYPE_FRIEND = 6;
            public static final int TYPE_MANAGER = 7;
            public static final int TYPE_MOTHER = 8;
            public static final int TYPE_PARENT = 9;
            public static final int TYPE_PARTNER = 10;
            public static final int TYPE_REFERRED_BY = 11;
            public static final int TYPE_RELATIVE = 12;
            public static final int TYPE_SISTER = 13;
            public static final int TYPE_SPOUSE = 14;

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return R.string.relationTypeAssistant;
                    case 2:
                        return R.string.relationTypeBrother;
                    case 3:
                        return R.string.relationTypeChild;
                    case 4:
                        return R.string.relationTypeDomesticPartner;
                    case 5:
                        return R.string.relationTypeFather;
                    case 6:
                        return R.string.relationTypeFriend;
                    case 7:
                        return R.string.relationTypeManager;
                    case 8:
                        return R.string.relationTypeMother;
                    case 9:
                        return R.string.relationTypeParent;
                    case 10:
                        return R.string.relationTypePartner;
                    case 11:
                        return R.string.relationTypeReferredBy;
                    case 12:
                        return R.string.relationTypeRelative;
                    case 13:
                        return R.string.relationTypeSister;
                    case 14:
                        return R.string.relationTypeSpouse;
                    default:
                        return R.string.orgTypeCustom;
                }
            }

            private Relation() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }
        }

        public static final class Event implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_event";
            public static final String START_DATE = "data1";
            public static final int TYPE_ANNIVERSARY = 1;
            public static final int TYPE_BIRTHDAY = 3;
            public static final int TYPE_OTHER = 2;

            private Event() {
            }

            public static int getTypeResource(Integer num) {
                if (num == null) {
                    return R.string.eventTypeOther;
                }
                int iIntValue = num.intValue();
                return iIntValue != 1 ? iIntValue != 2 ? iIntValue != 3 ? R.string.eventTypeCustom : R.string.eventTypeBirthday : R.string.eventTypeOther : R.string.eventTypeAnniversary;
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeResource(Integer.valueOf(i))) : charSequence;
            }
        }

        public static final class Photo implements DataColumnsWithJoins, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";
            public static final String PHOTO = "data15";
            public static final String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        public static final class Note implements DataColumnsWithJoins, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/note";
            public static final String NOTE = "data1";

            private Note() {
            }
        }

        public static final class GroupMembership implements DataColumnsWithJoins, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group_membership";
            public static final String GROUP_ROW_ID = "data1";
            public static final String GROUP_SOURCE_ID = "group_sourceid";

            private GroupMembership() {
            }
        }

        public static final class Website implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/website";
            public static final int TYPE_BLOG = 2;
            public static final int TYPE_FTP = 6;
            public static final int TYPE_HOME = 4;
            public static final int TYPE_HOMEPAGE = 1;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_PROFILE = 3;
            public static final int TYPE_WORK = 5;
            public static final String URL = "data1";

            private Website() {
            }
        }

        @Deprecated
        public static final class SipAddress implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sip_address";
            public static final String SIP_ADDRESS = "data1";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            public static final int getTypeLabelResource(int i) {
                return i != 1 ? i != 2 ? i != 3 ? R.string.sipAddressTypeCustom : R.string.sipAddressTypeOther : R.string.sipAddressTypeWork : R.string.sipAddressTypeHome;
            }

            private SipAddress() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int i, CharSequence charSequence) {
                return (i != 0 || TextUtils.isEmpty(charSequence)) ? resources.getText(getTypeLabelResource(i)) : charSequence;
            }
        }

        public static final class Identity implements DataColumnsWithJoins, ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/identity";
            public static final String IDENTITY = "data1";
            public static final String NAMESPACE = "data2";

            private Identity() {
            }
        }

        public static final class Callable implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final Uri CONTENT_URI;
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;

            static {
                Uri uriWithAppendedPath = Uri.withAppendedPath(Data.CONTENT_URI, "callables");
                CONTENT_URI = uriWithAppendedPath;
                CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter_enterprise");
            }
        }

        public static final class Contactables implements DataColumnsWithJoins, CommonColumns, ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final Uri CONTENT_URI;
            public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";

            static {
                Uri uriWithAppendedPath = Uri.withAppendedPath(Data.CONTENT_URI, "contactables");
                CONTENT_URI = uriWithAppendedPath;
                CONTENT_FILTER_URI = Uri.withAppendedPath(uriWithAppendedPath, "filter");
            }
        }
    }

    public static final class Groups implements BaseColumns, GroupsColumns, SyncColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/group";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "groups");
        public static final Uri CONTENT_SUMMARY_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "groups_summary");

        private Groups() {
        }

        public static EntityIterator newEntityIterator(Cursor cursor) {
            return new EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends CursorEntityIterator {
            public EntityIteratorImpl(Cursor cursor) {
                super(cursor);
            }

            @Override // android.content.CursorEntityIterator
            public Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "_id");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "version");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sourceid");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "res_package");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "title");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, GroupsColumns.TITLE_RES);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, GroupsColumns.GROUP_VISIBLE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync1");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync2");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync3");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync4");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "system_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "deleted");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "notes");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "should_sync");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, GroupsColumns.FAVORITES);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, GroupsColumns.AUTO_ADD);
                cursor.moveToNext();
                return new Entity(contentValues);
            }
        }
    }

    public static final class AggregationExceptions implements BaseColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/aggregation_exception";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/aggregation_exception";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "aggregation_exceptions");
        public static final String RAW_CONTACT_ID1 = "raw_contact_id1";
        public static final String RAW_CONTACT_ID2 = "raw_contact_id2";
        public static final String TYPE = "type";
        public static final int TYPE_AUTOMATIC = 0;
        public static final int TYPE_KEEP_SEPARATE = 2;
        public static final int TYPE_KEEP_TOGETHER = 1;

        private AggregationExceptions() {
        }
    }

    public static final class SimContacts {
        public static final String ACTION_SIM_ACCOUNTS_CHANGED = "android.provider.action.SIM_ACCOUNTS_CHANGED";
        public static final String ADD_SIM_ACCOUNT_METHOD = "addSimAccount";
        public static final String KEY_ACCOUNT_NAME = "key_sim_account_name";
        public static final String KEY_ACCOUNT_TYPE = "key_sim_account_type";
        public static final String KEY_SIM_ACCOUNTS = "key_sim_accounts";
        public static final String KEY_SIM_EF_TYPE = "key_sim_ef_type";
        public static final String KEY_SIM_SLOT_INDEX = "key_sim_slot_index";
        public static final String QUERY_SIM_ACCOUNTS_METHOD = "querySimAccounts";
        public static final String REMOVE_SIM_ACCOUNT_METHOD = "removeSimAccount";

        private SimContacts() {
        }

        @SystemApi
        public static void addSimAccount(ContentResolver contentResolver, String str, String str2, int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Sim slot is negative");
            }
            if (!SimAccount.getValidEfTypes().contains(Integer.valueOf(i2))) {
                throw new IllegalArgumentException("Invalid EF type");
            }
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("Account name or type is empty");
            }
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_SIM_SLOT_INDEX, i);
            bundle.putInt(KEY_SIM_EF_TYPE, i2);
            bundle.putString(KEY_ACCOUNT_NAME, str);
            bundle.putString(KEY_ACCOUNT_TYPE, str2);
            ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, ADD_SIM_ACCOUNT_METHOD, null, bundle);
        }

        @SystemApi
        public static void removeSimAccounts(ContentResolver contentResolver, int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Sim slot is negative");
            }
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_SIM_SLOT_INDEX, i);
            ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, REMOVE_SIM_ACCOUNT_METHOD, null, bundle);
        }

        public static List<SimAccount> getSimAccounts(ContentResolver contentResolver) {
            ArrayList parcelableArrayList = ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, QUERY_SIM_ACCOUNTS_METHOD, null, null).getParcelableArrayList(KEY_SIM_ACCOUNTS, SimAccount.class);
            return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
        }
    }

    public static final class SimAccount implements Parcelable {
        public static final int ADN_EF_TYPE = 1;
        public static final Parcelable.Creator<SimAccount> CREATOR = new Parcelable.Creator<SimAccount>() { // from class: android.provider.ContactsContract.SimAccount.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SimAccount createFromParcel(Parcel parcel) {
                return new SimAccount(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SimAccount[] newArray(int i) {
                return new SimAccount[i];
            }
        };
        public static final int FDN_EF_TYPE = 2;
        public static final int SDN_EF_TYPE = 3;
        public static final int UNKNOWN_EF_TYPE = 0;
        private final String mAccountName;
        private final String mAccountType;
        private final int mEfType;
        private final int mSimSlotIndex;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static Set<Integer> getValidEfTypes() {
            return Sets.newArraySet(1, 3, 2);
        }

        public SimAccount(String str, String str2, int i, int i2) {
            this.mAccountName = str;
            this.mAccountType = str2;
            this.mSimSlotIndex = i;
            this.mEfType = i2;
        }

        public String getAccountName() {
            return this.mAccountName;
        }

        public String getAccountType() {
            return this.mAccountType;
        }

        public int getSimSlotIndex() {
            return this.mSimSlotIndex;
        }

        public int getEfType() {
            return this.mEfType;
        }

        public int hashCode() {
            return Objects.hash(this.mAccountName, this.mAccountType, Integer.valueOf(this.mSimSlotIndex), Integer.valueOf(this.mEfType));
        }

        public boolean equals(Object obj) {
            SimAccount simAccount;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            try {
                simAccount = (SimAccount) obj;
            } catch (ClassCastException unused) {
            }
            return this.mSimSlotIndex == simAccount.mSimSlotIndex && this.mEfType == simAccount.mEfType && Objects.equals(this.mAccountName, simAccount.mAccountName) && Objects.equals(this.mAccountType, simAccount.mAccountType);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mAccountName);
            parcel.writeString(this.mAccountType);
            parcel.writeInt(this.mSimSlotIndex);
            parcel.writeInt(this.mEfType);
        }
    }

    public static final class Settings implements SettingsColumns {
        public static final String ACTION_SET_DEFAULT_ACCOUNT = "android.provider.action.SET_DEFAULT_ACCOUNT";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/setting";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/setting";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "settings");
        public static final String KEY_DEFAULT_ACCOUNT = "key_default_account";
        public static final String QUERY_DEFAULT_ACCOUNT_METHOD = "queryDefaultAccount";
        public static final String SET_DEFAULT_ACCOUNT_METHOD = "setDefaultAccount";

        private Settings() {
        }

        @Deprecated
        public static Account getDefaultAccount(ContentResolver contentResolver) {
            return (Account) contentResolver.call(ContactsContract.AUTHORITY_URI, QUERY_DEFAULT_ACCOUNT_METHOD, (String) null, (Bundle) null).getParcelable(KEY_DEFAULT_ACCOUNT, Account.class);
        }

        @SystemApi
        @Deprecated
        public static void setDefaultAccount(ContentResolver contentResolver, Account account) {
            Bundle bundle = new Bundle();
            if (account != null) {
                bundle.putString("account_name", account.name);
                bundle.putString("account_type", account.type);
            }
            contentResolver.call(ContactsContract.AUTHORITY_URI, SET_DEFAULT_ACCOUNT_METHOD, (String) null, bundle);
        }
    }

    public static final class ProviderStatus {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/provider_status";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "provider_status");
        public static final String DATABASE_CREATION_TIMESTAMP = "database_creation_timestamp";
        public static final String STATUS = "status";
        public static final int STATUS_BUSY = 1;
        public static final int STATUS_EMPTY = 2;
        public static final int STATUS_NORMAL = 0;

        private ProviderStatus() {
        }
    }

    public static final class PinnedPositions {
        public static final int DEMOTED = -1;
        public static final String UNDEMOTE_METHOD = "undemote";
        public static final int UNPINNED = 0;

        public static void undemote(ContentResolver contentResolver, long j) {
            ContactsContract.nullSafeCall(contentResolver, ContactsContract.AUTHORITY_URI, UNDEMOTE_METHOD, String.valueOf(j), null);
        }

        public static void pin(ContentResolver contentResolver, long j, int i) {
            Uri uriWithAppendedPath = Uri.withAppendedPath(Contacts.CONTENT_URI, String.valueOf(j));
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactOptionsColumns.PINNED, Integer.valueOf(i));
            contentResolver.update(uriWithAppendedPath, contentValues, null, null);
        }
    }

    public static final class QuickContact {
        public static final String ACTION_QUICK_CONTACT = "android.provider.action.QUICK_CONTACT";
        public static final String EXTRA_EXCLUDE_MIMES = "android.provider.extra.EXCLUDE_MIMES";
        public static final String EXTRA_MODE = "android.provider.extra.MODE";
        public static final String EXTRA_PRIORITIZED_MIMETYPE = "android.provider.extra.PRIORITIZED_MIMETYPE";

        @Deprecated
        public static final String EXTRA_TARGET_RECT = "android.provider.extra.TARGET_RECT";
        public static final int MODE_DEFAULT = 3;
        public static final int MODE_LARGE = 3;
        public static final int MODE_MEDIUM = 2;
        public static final int MODE_SMALL = 1;

        public static Intent composeQuickContactsIntent(Context context, View view, Uri uri, int i, String[] strArr) {
            float f = context.getResources().getCompatibilityInfo().applicationScale;
            view.getLocationOnScreen(new int[2]);
            Rect rect = new Rect();
            rect.left = (int) ((r1[0] * f) + 0.5f);
            rect.top = (int) ((r1[1] * f) + 0.5f);
            rect.right = (int) (((r1[0] + view.getWidth()) * f) + 0.5f);
            rect.bottom = (int) (((r1[1] + view.getHeight()) * f) + 0.5f);
            return composeQuickContactsIntent(context, rect, uri, i, strArr);
        }

        public static Intent composeQuickContactsIntent(Context context, Rect rect, Uri uri, int i, String[] strArr) {
            while ((context instanceof ContextWrapper) && !(context instanceof Activity)) {
                context = ((ContextWrapper) context).getBaseContext();
            }
            Intent intentAddFlags = new Intent(ACTION_QUICK_CONTACT).addFlags((context instanceof Activity ? 0 : 268468224) | 536870912);
            intentAddFlags.setData(uri);
            intentAddFlags.setSourceBounds(rect);
            intentAddFlags.putExtra(EXTRA_MODE, i);
            intentAddFlags.putExtra(EXTRA_EXCLUDE_MIMES, strArr);
            return intentAddFlags;
        }

        public static Intent rebuildManagedQuickContactsIntent(String str, long j, boolean z, long j2, Intent intent) {
            Uri uriBuild;
            Intent intent2 = new Intent(ACTION_QUICK_CONTACT);
            if (TextUtils.isEmpty(str)) {
                uriBuild = null;
            } else if (z) {
                uriBuild = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, str);
            } else {
                uriBuild = Contacts.getLookupUri(j, str);
            }
            if (uriBuild != null && j2 != 0) {
                uriBuild = uriBuild.buildUpon().appendQueryParameter("directory", String.valueOf(j2)).build();
            }
            intent2.setData(uriBuild);
            intent2.setFlags(intent.getFlags() | 268435456);
            intent2.setSourceBounds(intent.getSourceBounds());
            intent2.putExtra(EXTRA_MODE, intent.getIntExtra(EXTRA_MODE, 3));
            intent2.putExtra(EXTRA_EXCLUDE_MIMES, intent.getStringArrayExtra(EXTRA_EXCLUDE_MIMES));
            return intent2;
        }

        public static void showQuickContact(Context context, View view, Uri uri, int i, String[] strArr) {
            ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent(context, view, uri, i, strArr));
        }

        public static void showQuickContact(Context context, Rect rect, Uri uri, int i, String[] strArr) {
            ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent(context, rect, uri, i, strArr));
        }

        public static void showQuickContact(Context context, View view, Uri uri, String[] strArr, String str) {
            Intent intentComposeQuickContactsIntent = composeQuickContactsIntent(context, view, uri, 3, strArr);
            intentComposeQuickContactsIntent.putExtra(EXTRA_PRIORITIZED_MIMETYPE, str);
            ContactsInternal.startQuickContactWithErrorToast(context, intentComposeQuickContactsIntent);
        }

        public static void showQuickContact(Context context, Rect rect, Uri uri, String[] strArr, String str) {
            Intent intentComposeQuickContactsIntent = composeQuickContactsIntent(context, rect, uri, 3, strArr);
            intentComposeQuickContactsIntent.putExtra(EXTRA_PRIORITIZED_MIMETYPE, str);
            ContactsInternal.startQuickContactWithErrorToast(context, intentComposeQuickContactsIntent);
        }
    }

    public static final class DisplayPhoto {
        public static final String DISPLAY_MAX_DIM = "display_max_dim";
        public static final String THUMBNAIL_MAX_DIM = "thumbnail_max_dim";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo");
        public static final Uri CONTENT_MAX_DIMENSIONS_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "photo_dimensions");

        private DisplayPhoto() {
        }
    }

    @SystemApi
    @Deprecated
    public static final class MetadataSync implements BaseColumns, MetadataSyncColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata";
        public static final Uri CONTENT_URI;
        public static final String METADATA_AUTHORITY = "com.android.contacts.metadata";
        public static final Uri METADATA_AUTHORITY_URI;

        static {
            Uri uri = Uri.parse("content://com.android.contacts.metadata");
            METADATA_AUTHORITY_URI = uri;
            CONTENT_URI = Uri.withAppendedPath(uri, "metadata_sync");
        }

        private MetadataSync() {
        }
    }

    @SystemApi
    @Deprecated
    public static final class MetadataSyncState implements BaseColumns, MetadataSyncStateColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata_sync_state";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata_sync_state";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(MetadataSync.METADATA_AUTHORITY_URI, "metadata_sync_state");

        private MetadataSyncState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle nullSafeCall(ContentResolver contentResolver, Uri uri, String str, String str2, Bundle bundle) {
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = contentResolver.acquireContentProviderClient(uri);
            try {
                Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call(str, str2, bundle);
                if (contentProviderClientAcquireContentProviderClient != null) {
                    contentProviderClientAcquireContentProviderClient.close();
                }
                return bundleCall;
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
