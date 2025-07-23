package android.provider;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.telecom.CallerInfo;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.telecom.flags.Flags;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class CallLog {
    public static final String AUTHORITY = "call_log";
    public static final Uri CALL_COMPOSER_PICTURE_URI;
    public static final String CALL_COMPOSER_SEGMENT = "call_composer";
    public static final Uri CONTENT_URI;
    private static final String LOG_TAG = "CallLog";
    public static final String SHADOW_AUTHORITY = "call_log_shadow";
    public static final Uri SHADOW_CALL_COMPOSER_PICTURE_URI;
    private static final boolean VERBOSE_LOG = false;

    static {
        Uri parse = Uri.parse("content://call_log");
        CONTENT_URI = parse;
        Uri build = parse.buildUpon().appendPath(CALL_COMPOSER_SEGMENT).build();
        CALL_COMPOSER_PICTURE_URI = build;
        SHADOW_CALL_COMPOSER_PICTURE_URI = build.buildUpon().authority(SHADOW_AUTHORITY).build();
    }

    @SystemApi
    public static class CallComposerLoggingException extends Throwable {
        public static final int ERROR_INPUT_CLOSED = 3;
        public static final int ERROR_REMOTE_END_CLOSED = 1;
        public static final int ERROR_STORAGE_FULL = 2;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallComposerLoggingError {
        }

        public CallComposerLoggingException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // java.lang.Throwable
        public String toString() {
            String str;
            int i = this.mErrorCode;
            if (i == 0) {
                str = "UNKNOWN";
            } else if (i == 1) {
                str = "REMOTE_END_CLOSED";
            } else if (i == 2) {
                str = "STORAGE_FULL";
            } else if (i == 3) {
                str = "INPUT_CLOSED";
            } else {
                str = "[[" + this.mErrorCode + "]]";
            }
            return "CallComposerLoggingException: " + str;
        }
    }

    @SystemApi
    public static void storeCallComposerPicture(final Context context, final InputStream inputStream, Executor executor, final OutcomeReceiver<Uri, CallComposerLoggingException> outcomeReceiver) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        executor.execute(new Runnable() { // from class: android.provider.CallLog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CallLog.lambda$storeCallComposerPicture$0(inputStream, outcomeReceiver, context);
            }
        });
    }

    static /* synthetic */ void lambda$storeCallComposerPicture$0(InputStream inputStream, OutcomeReceiver outcomeReceiver, Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                } else {
                    byteArrayOutputStream.write(bArr, 0, read);
                }
            } catch (IOException e) {
                Log.e("CallLog", "IOException while reading call composer pic from input: " + e);
                outcomeReceiver.onError(new CallComposerLoggingException(3));
                return;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        UserHandle user = context.getUser();
        if (UserHandle.CURRENT.equals(user)) {
            user = Process.myUserHandle();
        }
        if (user != UserHandle.ALL) {
            Uri maybeAddUserId = ContentProvider.maybeAddUserId(userManager.isUserUnlocked(user) ? CALL_COMPOSER_PICTURE_URI : SHADOW_CALL_COMPOSER_PICTURE_URI, user.getIdentifier());
            Log.i("CallLog", "Inserting call composer for single user at " + maybeAddUserId);
            try {
                outcomeReceiver.onResult(storeCallComposerPictureAtUri(context, maybeAddUserId, false, byteArray));
                return;
            } catch (CallComposerLoggingException e2) {
                outcomeReceiver.onError(e2);
                return;
            }
        }
        if (!userManager.isUserUnlocked(UserHandle.SYSTEM)) {
            Uri maybeAddUserId2 = ContentProvider.maybeAddUserId(SHADOW_CALL_COMPOSER_PICTURE_URI, UserHandle.SYSTEM.getIdentifier());
            Log.i("CallLog", "Inserting call composer for all users, but system locked at " + maybeAddUserId2);
            try {
                outcomeReceiver.onResult(storeCallComposerPictureAtUri(context, maybeAddUserId2, true, byteArray));
                return;
            } catch (CallComposerLoggingException e3) {
                outcomeReceiver.onError(e3);
                return;
            }
        }
        try {
            Uri storeCallComposerPictureAtUri = storeCallComposerPictureAtUri(context, ContentProvider.maybeAddUserId(CALL_COMPOSER_PICTURE_URI, UserHandle.SYSTEM.getIdentifier()), true, byteArray);
            Log.i("CallLog", "Inserting call composer for all users, succeeded with system, result is " + storeCallComposerPictureAtUri);
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(storeCallComposerPictureAtUri);
            Iterator<UserInfo> it = userManager.getAliveUsers().iterator();
            while (it.hasNext()) {
                UserHandle userHandle = it.next().getUserHandle();
                if (!userHandle.isSystem() && Calls.shouldHaveSharedCallLogEntries(context, userManager, userHandle.getIdentifier()) && userManager.isUserRunning(userHandle) && userManager.isUserUnlocked(userHandle)) {
                    Uri maybeAddUserId3 = ContentProvider.maybeAddUserId(uriWithoutUserId, userHandle.getIdentifier());
                    Log.i("CallLog", "Inserting call composer for all users, now on user " + userHandle + " inserting at " + maybeAddUserId3);
                    try {
                        storeCallComposerPictureAtUri(context, maybeAddUserId3, false, byteArray);
                    } catch (CallComposerLoggingException e4) {
                        Log.e("CallLog", "Error writing for user " + userHandle.getIdentifier() + ": " + e4);
                    }
                }
            }
            outcomeReceiver.onResult(uriWithoutUserId);
        } catch (CallComposerLoggingException e5) {
            outcomeReceiver.onError(e5);
        }
    }

    private static Uri storeCallComposerPictureAtUri(Context context, Uri uri, boolean z, byte[] bArr) throws CallComposerLoggingException {
        ParcelFileDescriptor openFileDescriptor;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Calls.ADD_FOR_ALL_USERS, Integer.valueOf(z ? 1 : 0));
            Uri insert = context.getContentResolver().insert(uri, contentValues);
            if (insert == null) {
                throw new CallComposerLoggingException(2);
            }
            try {
                openFileDescriptor = context.getContentResolver().openFileDescriptor(insert, "w");
            } catch (FileNotFoundException unused) {
                throw new CallComposerLoggingException(0);
            } catch (IOException e) {
                Log.e("CallLog", "Got IOException closing remote descriptor: " + e);
            }
            try {
                try {
                    new FileOutputStream(openFileDescriptor.getFileDescriptor()).write(bArr);
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                        return insert;
                    }
                    return insert;
                } catch (IOException e2) {
                    Log.e("CallLog", "Got IOException writing to remote end: " + e2);
                    context.getContentResolver().delete(insert, null);
                    throw new CallComposerLoggingException(1);
                }
            } finally {
            }
        } catch (ParcelableException unused2) {
            throw new CallComposerLoggingException(0);
        }
    }

    private static void sendCallComposerError(OutcomeReceiver<?, CallComposerLoggingException> outcomeReceiver, int i) {
        outcomeReceiver.onError(new CallComposerLoggingException(i));
    }

    public static class AddCallParams {
        private PhoneAccountHandle mAccountHandle;
        private boolean mAddForAllUsers;
        private String mAssertedDisplayName;
        private int mCallBlockReason;
        private CharSequence mCallScreeningAppName;
        private String mCallScreeningComponentName;
        private int mCallType;
        private CallerInfo mCallerInfo;
        private long mDataUsage;
        private int mDuration;
        private int mFeatures;
        private boolean mIsBusinessCall;
        private int mIsPhoneAccountMigrationPending;
        private boolean mIsRead;
        private double mLatitude;
        private double mLongitude;
        private long mMissedReason;
        private String mNumber;
        private Uri mPictureUri;
        private String mPostDialDigits;
        private int mPresentation;
        private int mPriority;
        private long mStart;
        private String mSubject;
        private UserHandle mUserToBeInsertedTo;
        private String mViaNumber;

        public static final class AddCallParametersBuilder {
            public static final int MAX_NUMBER_OF_CHARACTERS = 256;
            private PhoneAccountHandle mAccountHandle;
            private boolean mAddForAllUsers;
            private String mAssertedDisplayName;
            private CharSequence mCallScreeningAppName;
            private String mCallScreeningComponentName;
            private CallerInfo mCallerInfo;
            private int mDuration;
            private int mFeatures;
            private boolean mIsBusinessCall;
            private int mIsPhoneAccountMigrationPending;
            private boolean mIsRead;
            private String mNumber;
            private Uri mPictureUri;
            private String mPostDialDigits;
            private long mStart;
            private String mSubject;
            private UserHandle mUserToBeInsertedTo;
            private String mViaNumber;
            private int mPresentation = 3;
            private int mCallType = 1;
            private Long mDataUsage = Long.MIN_VALUE;
            private int mCallBlockReason = 0;
            private long mMissedReason = 0;
            private int mPriority = 0;
            private double mLatitude = Double.NaN;
            private double mLongitude = Double.NaN;

            public AddCallParametersBuilder setCallerInfo(CallerInfo callerInfo) {
                this.mCallerInfo = callerInfo;
                return this;
            }

            public AddCallParametersBuilder setNumber(String str) {
                this.mNumber = str;
                return this;
            }

            public AddCallParametersBuilder setPostDialDigits(String str) {
                this.mPostDialDigits = str;
                return this;
            }

            public AddCallParametersBuilder setViaNumber(String str) {
                this.mViaNumber = str;
                return this;
            }

            public AddCallParametersBuilder setPresentation(int i) {
                this.mPresentation = i;
                return this;
            }

            public AddCallParametersBuilder setCallType(int i) {
                this.mCallType = i;
                return this;
            }

            public AddCallParametersBuilder setFeatures(int i) {
                this.mFeatures = i;
                return this;
            }

            public AddCallParametersBuilder setAccountHandle(PhoneAccountHandle phoneAccountHandle) {
                this.mAccountHandle = phoneAccountHandle;
                return this;
            }

            public AddCallParametersBuilder setStart(long j) {
                this.mStart = j;
                return this;
            }

            public AddCallParametersBuilder setDuration(int i) {
                this.mDuration = i;
                return this;
            }

            public AddCallParametersBuilder setDataUsage(long j) {
                this.mDataUsage = Long.valueOf(j);
                return this;
            }

            public AddCallParametersBuilder setAddForAllUsers(boolean z) {
                this.mAddForAllUsers = z;
                return this;
            }

            public AddCallParametersBuilder setUserToBeInsertedTo(UserHandle userHandle) {
                this.mUserToBeInsertedTo = userHandle;
                return this;
            }

            public AddCallParametersBuilder setIsRead(boolean z) {
                this.mIsRead = z;
                return this;
            }

            public AddCallParametersBuilder setCallBlockReason(int i) {
                this.mCallBlockReason = i;
                return this;
            }

            public AddCallParametersBuilder setCallScreeningAppName(CharSequence charSequence) {
                this.mCallScreeningAppName = charSequence;
                return this;
            }

            public AddCallParametersBuilder setCallScreeningComponentName(String str) {
                this.mCallScreeningComponentName = str;
                return this;
            }

            public AddCallParametersBuilder setMissedReason(long j) {
                this.mMissedReason = j;
                return this;
            }

            public AddCallParametersBuilder setPriority(int i) {
                this.mPriority = i;
                return this;
            }

            public AddCallParametersBuilder setSubject(String str) {
                this.mSubject = str;
                return this;
            }

            public AddCallParametersBuilder setLatitude(double d) {
                this.mLatitude = d;
                return this;
            }

            public AddCallParametersBuilder setLongitude(double d) {
                this.mLongitude = d;
                return this;
            }

            public AddCallParametersBuilder setPictureUri(Uri uri) {
                this.mPictureUri = uri;
                return this;
            }

            public AddCallParametersBuilder setIsPhoneAccountMigrationPending(int i) {
                this.mIsPhoneAccountMigrationPending = i;
                return this;
            }

            public AddCallParametersBuilder setIsBusinessCall(boolean z) {
                this.mIsBusinessCall = z;
                return this;
            }

            public AddCallParametersBuilder setAssertedDisplayName(String str) {
                if (str != null && str.length() > 256) {
                    throw new IllegalArgumentException("assertedDisplayName exceeds the character limit of 256.");
                }
                this.mAssertedDisplayName = str;
                return this;
            }

            public AddCallParams build() {
                if (Flags.businessCallComposer()) {
                    return new AddCallParams(this.mCallerInfo, this.mNumber, this.mPostDialDigits, this.mViaNumber, this.mPresentation, this.mCallType, this.mFeatures, this.mAccountHandle, this.mStart, this.mDuration, this.mDataUsage.longValue(), this.mAddForAllUsers, this.mUserToBeInsertedTo, this.mIsRead, this.mCallBlockReason, this.mCallScreeningAppName, this.mCallScreeningComponentName, this.mMissedReason, this.mPriority, this.mSubject, this.mLatitude, this.mLongitude, this.mPictureUri, this.mIsPhoneAccountMigrationPending, this.mIsBusinessCall, this.mAssertedDisplayName);
                }
                return new AddCallParams(this.mCallerInfo, this.mNumber, this.mPostDialDigits, this.mViaNumber, this.mPresentation, this.mCallType, this.mFeatures, this.mAccountHandle, this.mStart, this.mDuration, this.mDataUsage.longValue(), this.mAddForAllUsers, this.mUserToBeInsertedTo, this.mIsRead, this.mCallBlockReason, this.mCallScreeningAppName, this.mCallScreeningComponentName, this.mMissedReason, this.mPriority, this.mSubject, this.mLatitude, this.mLongitude, this.mPictureUri, this.mIsPhoneAccountMigrationPending);
            }
        }

        private AddCallParams(CallerInfo callerInfo, String str, String str2, String str3, int i, int i2, int i3, PhoneAccountHandle phoneAccountHandle, long j, int i4, long j2, boolean z, UserHandle userHandle, boolean z2, int i5, CharSequence charSequence, String str4, long j3, int i6, String str5, double d, double d2, Uri uri, int i7) {
            this.mCallerInfo = callerInfo;
            this.mNumber = str;
            this.mPostDialDigits = str2;
            this.mViaNumber = str3;
            this.mPresentation = i;
            this.mCallType = i2;
            this.mFeatures = i3;
            this.mAccountHandle = phoneAccountHandle;
            this.mStart = j;
            this.mDuration = i4;
            this.mDataUsage = j2;
            this.mAddForAllUsers = z;
            this.mUserToBeInsertedTo = userHandle;
            this.mIsRead = z2;
            this.mCallBlockReason = i5;
            this.mCallScreeningAppName = charSequence;
            this.mCallScreeningComponentName = str4;
            this.mMissedReason = j3;
            this.mPriority = i6;
            this.mSubject = str5;
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mPictureUri = uri;
            this.mIsPhoneAccountMigrationPending = i7;
        }

        private AddCallParams(CallerInfo callerInfo, String str, String str2, String str3, int i, int i2, int i3, PhoneAccountHandle phoneAccountHandle, long j, int i4, long j2, boolean z, UserHandle userHandle, boolean z2, int i5, CharSequence charSequence, String str4, long j3, int i6, String str5, double d, double d2, Uri uri, int i7, boolean z3, String str6) {
            this.mCallerInfo = callerInfo;
            this.mNumber = str;
            this.mPostDialDigits = str2;
            this.mViaNumber = str3;
            this.mPresentation = i;
            this.mCallType = i2;
            this.mFeatures = i3;
            this.mAccountHandle = phoneAccountHandle;
            this.mStart = j;
            this.mDuration = i4;
            this.mDataUsage = j2;
            this.mAddForAllUsers = z;
            this.mUserToBeInsertedTo = userHandle;
            this.mIsRead = z2;
            this.mCallBlockReason = i5;
            this.mCallScreeningAppName = charSequence;
            this.mCallScreeningComponentName = str4;
            this.mMissedReason = j3;
            this.mPriority = i6;
            this.mSubject = str5;
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mPictureUri = uri;
            this.mIsPhoneAccountMigrationPending = i7;
            this.mIsBusinessCall = z3;
            this.mAssertedDisplayName = str6;
        }
    }

    public static class Calls implements BaseColumns {
        public static final String ADD_FOR_ALL_USERS = "add_for_all_users";
        public static final String ALLOW_VOICEMAILS_PARAM_KEY = "allow_voicemails";
        public static final int ANSWERED_EXTERNALLY_TYPE = 7;
        public static final String ASSERTED_DISPLAY_NAME = "asserted_display_name";
        public static final long AUTO_MISSED_EMERGENCY_CALL = 1;
        public static final long AUTO_MISSED_MAXIMUM_DIALING = 4;
        public static final long AUTO_MISSED_MAXIMUM_RINGING = 2;
        public static final int BLOCKED_TYPE = 6;
        public static final String BLOCK_REASON = "block_reason";
        public static final int BLOCK_REASON_BLOCKED_NUMBER = 3;
        public static final int BLOCK_REASON_CALL_SCREENING_SERVICE = 1;
        public static final int BLOCK_REASON_DIRECT_TO_VOICEMAIL = 2;
        public static final int BLOCK_REASON_NOT_BLOCKED = 0;
        public static final int BLOCK_REASON_NOT_IN_CONTACTS = 7;
        public static final int BLOCK_REASON_PAY_PHONE = 6;
        public static final int BLOCK_REASON_RESTRICTED_NUMBER = 5;
        public static final int BLOCK_REASON_UNKNOWN_NUMBER = 4;
        public static final String CACHED_FORMATTED_NUMBER = "formatted_number";
        public static final String CACHED_LOOKUP_URI = "lookup_uri";
        public static final String CACHED_MATCHED_NUMBER = "matched_number";
        public static final String CACHED_NAME = "name";
        public static final String CACHED_NORMALIZED_NUMBER = "normalized_number";
        public static final String CACHED_NUMBER_LABEL = "numberlabel";
        public static final String CACHED_NUMBER_TYPE = "numbertype";
        public static final String CACHED_PHOTO_ID = "photo_id";
        public static final String CACHED_PHOTO_URI = "photo_uri";
        public static final String CALL_SCREENING_APP_NAME = "call_screening_app_name";
        public static final String CALL_SCREENING_COMPONENT_NAME = "call_screening_component_name";
        public static final String COMPOSER_PHOTO_URI = "composer_photo_uri";
        public static final Uri CONTENT_FILTER_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/calls";
        public static final Uri CONTENT_URI;
        private static final Uri CONTENT_URI_LIMIT_1;
        public static final Uri CONTENT_URI_WITH_VOICEMAIL;
        public static final String COUNTRY_ISO = "countryiso";
        public static final String DATA_USAGE = "data_usage";
        public static final String DATE = "date";
        private static final int DEFAULT_MAX_CALL_LOG_SIZE = 500;
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        public static final String DURATION = "duration";
        public static final String EXTRA_CALL_TYPE_FILTER = "android.provider.extra.CALL_TYPE_FILTER";
        public static final String FEATURES = "features";
        public static final int FEATURES_ASSISTED_DIALING_USED = 16;
        public static final int FEATURES_HD_CALL = 4;
        public static final int FEATURES_PULLED_EXTERNALLY = 2;
        public static final int FEATURES_RTT = 32;
        public static final int FEATURES_SIM2 = 128;
        public static final int FEATURES_VERIFIED_NUMBER = 256;
        public static final int FEATURES_VIDEO = 1;
        public static final int FEATURES_VOLTE = 64;
        public static final int FEATURES_WIFI = 8;
        public static final String GEOCODED_LOCATION = "geocoded_location";
        public static final int INCOMING_TYPE = 1;
        public static final String IS_BUSINESS_CALL = "is_business_call";
        public static final String IS_PHONE_ACCOUNT_MIGRATION_PENDING = "is_call_log_phone_account_migration_pending";
        public static final String IS_READ = "is_read";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String LIMIT_PARAM_KEY = "limit";
        public static final String LOCATION = "location";
        public static final int LOW_RING_VOLUME = 0;
        private static final int MIN_DURATION_FOR_NORMALIZED_NUMBER_UPDATE_MS = 10000;
        public static final String MISSED_REASON = "missed_reason";
        public static final long MISSED_REASON_NOT_MISSED = 0;
        public static final int MISSED_TYPE = 3;
        public static final String NEW = "new";
        public static final String NUMBER = "number";
        public static final String NUMBER_PRESENTATION = "presentation";
        public static final String OFFSET_PARAM_KEY = "offset";
        public static final int OUTGOING_TYPE = 2;
        public static final String PHONE_ACCOUNT_ADDRESS = "phone_account_address";
        public static final String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final String PHONE_ACCOUNT_HIDDEN = "phone_account_hidden";
        public static final String PHONE_ACCOUNT_ID = "subscription_id";
        public static final String POST_DIAL_DIGITS = "post_dial_digits";
        public static final int PRESENTATION_ALLOWED = 1;
        public static final int PRESENTATION_PAYPHONE = 4;
        public static final int PRESENTATION_RESTRICTED = 2;
        public static final int PRESENTATION_UNAVAILABLE = 5;
        public static final int PRESENTATION_UNKNOWN = 3;
        public static final String PRIORITY = "priority";
        public static final int PRIORITY_NORMAL = 0;
        public static final int PRIORITY_URGENT = 1;
        public static final int REJECTED_TYPE = 5;
        public static final String SEM_3RD_END_CALL = "sec_3rd_end_call";
        public static final String SEM_ACCOUNT_ID = "account_id";
        public static final String SEM_ACCOUNT_NAME = "account_name";
        public static final String SEM_ADDRESS = "address";
        public static final int SEM_ANSWERED_EXTERNALLY_TYPE_SHARED_CALL = 1400;
        public static final String SEM_BUSINESS_NAME = "bname";
        public static final String SEM_CALL_OUT_DURATION = "call_out_duration";
        public static final String SEM_CALL_PLUS = "callplus";
        public static final String SEM_CDNIP_NUMBER = "cdnip_number";
        public static final String SEM_CITY_ID = "cityid";
        public static final String SEM_CMC_DEVICE = "sec_cmc_device";
        public static final String SEM_CNAP_NAME = "cnap_name";
        public static final String SEM_CONTACT_ID = "contactid";
        public static final String SEM_CONTENTS_VALUE_KEY_CONTACT_ID = "ci_contact_id";
        public static final String SEM_CONTENTS_VALUE_KEY_NORMALIZED_NUMBER = "ci_normalizedNumber";
        public static final String SEM_CONTENTS_VALUE_KEY_PHONE_NUMBER = "ci_phoneNumber";
        public static final String SEM_COUNTRY_CODE = "country_code";
        public static final String SEM_CUSTOM1 = "sec_custom1";
        public static final String SEM_CUSTOM2 = "sec_custom2";
        public static final String SEM_CUSTOM3 = "sec_custom3";
        public static final String SEM_DORMANT_SET = "dormant_set";
        public static final String SEM_E164_NUMBER = "e164_number";
        public static final String SEM_END_TYPE = "sec_end_type";
        public static final String SEM_FIRST_NAME = "fname";
        public static final String SEM_FREQUENT = "frequent";
        public static final String SEM_GROUP_CALL = "sec_group_call";
        public static final String SEM_GROUP_CALL_ID = "data2";
        public static final String SEM_GROUP_CALL_MEMBER_IDENTIFIER = "data4";
        public static final String SEM_GROUP_ID = "sec_groupid";
        public static final int SEM_INCOMING_TYPE_VISITOR_ROAMING = 1200;
        public static final String SEM_LAST_NAME = "lname";
        public static final String SEM_LINE_STATUS = "sec_line_status";
        public static final String SEM_LOG_TYPE = "logtype";
        public static final int SEM_LOG_TYPE_CALL_CONFERECNCE = 1350;
        public static final int SEM_LOG_TYPE_CALL_HD = 150;
        public static final int SEM_LOG_TYPE_CALL_KOETAKU = 110;
        public static final int SEM_LOG_TYPE_CALL_SATELLITE = 1550;
        public static final int SEM_LOG_TYPE_CALL_SWIS = 1400;
        public static final int SEM_LOG_TYPE_CALL_VIDEO = 500;
        public static final int SEM_LOG_TYPE_CALL_VIDEO_EPDG_WIFI = 1450;
        public static final int SEM_LOG_TYPE_CALL_VIDEO_VOLTE = 1050;
        public static final int SEM_LOG_TYPE_CALL_VOICE = 100;
        public static final int SEM_LOG_TYPE_CALL_VOICE_VOLTE = 1000;
        public static final int SEM_LOG_TYPE_CALL_VOIP = 800;
        public static final int SEM_LOG_TYPE_CALL_VOWIFI = 1150;
        public static final int SEM_LOG_TYPE_EMAIL = 400;
        public static final int SEM_LOG_TYPE_FAKE_BASE_STATION = 1500;
        public static final int SEM_LOG_TYPE_IM = 700;
        public static final int SEM_LOG_TYPE_IM_CTC = 350;
        public static final int SEM_LOG_TYPE_MMS = 200;
        public static final int SEM_LOG_TYPE_MMS_SATELLITE = 1650;
        public static final int SEM_LOG_TYPE_ON_DEVICE_VOICEMAIL = 1800;
        public static final int SEM_LOG_TYPE_RCS_CHAT = 1250;
        public static final int SEM_LOG_TYPE_RCS_CHAT_SATELLITE = 1700;
        public static final int SEM_LOG_TYPE_RCS_FT = 1300;
        public static final int SEM_LOG_TYPE_RCS_FT_CTC = 250;
        public static final int SEM_LOG_TYPE_RCS_FT_SATELLITE = 1750;
        public static final int SEM_LOG_TYPE_RCS_GROUP_CHAT = 1200;
        public static final int SEM_LOG_TYPE_RCS_SHARED_CONTENT = 1100;
        public static final int SEM_LOG_TYPE_SMS = 300;
        public static final int SEM_LOG_TYPE_SMS_SATELLITE = 1600;
        public static final int SEM_LOG_TYPE_SNS = 600;
        public static final int SEM_LOG_TYPE_VOICEMAIL = 900;
        public static final int SEM_LOG_TYPE_VVM = 950;
        public static final String SEM_MEMO = "sec_memo";
        public static final String SEM_MESSAGE_CONTENT = "m_content";
        public static final String SEM_MESSAGE_ID = "messageid";
        public static final String SEM_MESSAGE_SUBJECT = "m_subject";
        public static final int SEM_MISSED_TYPE_ROAMING = 1250;
        public static final String SEM_MSG_ID = "sec_msg_id";
        public static final int SEM_OUTGOING_TYPE_CONFERECNCE = 1600;
        public static final int SEM_OUTGOING_TYPE_HOME_ROAMING = 1150;
        public static final int SEM_OUTGOING_TYPE_VISITOR_ROAMING = 1100;
        public static final String SEM_PHOTORING_URI = "photoring_uri";
        public static final String SEM_PINYIN_NAME = "pinyin_name";
        public static final String SEM_PLACES_INFO = "sec_places_info";
        public static final int SEM_PULLED_TYPE_SHARED_CALL = 1500;
        public static final String SEM_QUANTUM_ENCRYPTION = "sec_quantum_encryption";
        public static final String SEM_RAW_CONTACT_ID = "raw_contact_id";
        public static final String SEM_RECORD = "sec_record";
        public static final int SEM_REJECTED_TYPE_ROAMING = 1300;
        public static final int SEM_REJECTED_TYPE_SHARED_CALL = 1450;
        public static final String SEM_REJECT_FLAG = "reject_flag";
        public static final String SEM_REMIND_ME_LATER_SET = "remind_me_later_set";
        public static final String SEM_RINGING_TIME = "sec_ringing_time";
        public static final String SEM_ROAMING_AUTO_DIALER_QUERY_PARAM = "ROAMING_AUTO_DIALER";
        public static final String SEM_RTT = "sec_rtt";
        public static final String SEM_SAMSUNG_OWN_NUM = "samsung_ownnum";
        public static final String SEM_SERVICE_PROVIDER_TYPE = "sp_type";
        public static final String SEM_SERVICE_TYPE = "service_type";
        public static final int SEM_SERVICE_TYPE_CMF_CALL = 10100;
        public static final int SEM_SERVICE_TYPE_EMERGENCY_ALERT = 10500;
        public static final int SEM_SERVICE_TYPE_FMM_CONTACT_OWNER = 10350;
        public static final int SEM_SERVICE_TYPE_FMM_LOST_DEVICE = 10300;
        public static final int SEM_SERVICE_TYPE_MESSAGE_CALL = 10000;
        public static final int SEM_SERVICE_TYPE_SWITCH_CALL = 10250;
        public static final int SEM_SERVICE_TYPE_SWITCH_CALL_DISCONNECTED = 10200;
        public static final int SEM_SERVICE_TYPE_YELLOW_PAGE = 10400;
        public static final String SEM_SIMNUM = "simnum";
        public static final String SEM_SIM_ID = "sim_id";
        public static final String SEM_SMART_CALL = "sec_smartcall";
        public static final String SEM_SPAM_REPORT = "spam_report";
        public static final String SEM_STIR_SHAKEN = "sec_stir_shaken";
        public static final String SEM_SUBID = "sec_subid";
        public static final String SEM_VVM_ID = "vvm_id";
        public static final Uri SHADOW_CONTENT_URI;
        public static final long SHORT_RING_THRESHOLD = 5000;
        public static final String SUBJECT = "subject";
        public static final String SUB_ID = "sub_id";
        private static final ComponentName TELEPHONY_COMPONENT_NAME;
        public static final String TRANSCRIPTION = "transcription";
        public static final String TRANSCRIPTION_STATE = "transcription_state";
        public static final String TYPE = "type";
        public static final long USER_MISSED_CALL_FILTERS_TIMEOUT = 4194304;
        public static final long USER_MISSED_CALL_SCREENING_SERVICE_SILENCED = 2097152;
        public static final long USER_MISSED_DND_MODE = 262144;
        public static final long USER_MISSED_LOW_RING_VOLUME = 524288;
        public static final long USER_MISSED_NEVER_RANG = 8388608;
        public static final long USER_MISSED_NOT_RUNNING = 16777216;
        public static final long USER_MISSED_NO_ANSWER = 65536;
        public static final long USER_MISSED_NO_VIBRATE = 1048576;
        public static final long USER_MISSED_SHORT_RING = 131072;
        public static final String VIA_NUMBER = "via_number";
        public static final int VOICEMAIL_TYPE = 4;
        public static final String VOICEMAIL_URI = "voicemail_uri";

        @Retention(RetentionPolicy.SOURCE)
        public @interface MissedReason {
        }

        public static boolean isUserMissed(long j) {
            return j >= 65536;
        }

        static {
            Uri parse = Uri.parse("content://call_log/calls");
            CONTENT_URI = parse;
            SHADOW_CONTENT_URI = Uri.parse("content://call_log_shadow/calls");
            CONTENT_FILTER_URI = Uri.parse("content://call_log/calls/filter");
            CONTENT_URI_LIMIT_1 = parse.buildUpon().appendQueryParameter("limit", "1").build();
            CONTENT_URI_WITH_VOICEMAIL = parse.buildUpon().appendQueryParameter(ALLOW_VOICEMAILS_PARAM_KEY, "true").build();
            TELEPHONY_COMPONENT_NAME = new ComponentName("com.android.phone", "com.android.services.telephony.TelephonyConnectionService");
        }

        public static Uri addCall(CallerInfo callerInfo, Context context, String str, int i, int i2, int i3, PhoneAccountHandle phoneAccountHandle, long j, int i4, Long l, long j2, int i5) {
            return addCall(callerInfo, context, str, "", "", i, i2, i3, phoneAccountHandle, j, i4, l, false, null, false, 0, null, null, j2, i5);
        }

        public static Uri addCall(CallerInfo callerInfo, Context context, String str, String str2, String str3, int i, int i2, int i3, PhoneAccountHandle phoneAccountHandle, long j, int i4, Long l, boolean z, UserHandle userHandle, long j2, int i5) {
            return addCall(callerInfo, context, str, str2, str3, i, i2, i3, phoneAccountHandle, j, i4, l, z, userHandle, false, 0, null, null, j2, i5);
        }

        public static Uri addCall(CallerInfo callerInfo, Context context, String str, String str2, String str3, int i, int i2, int i3, PhoneAccountHandle phoneAccountHandle, long j, int i4, Long l, boolean z, UserHandle userHandle, boolean z2, int i5, CharSequence charSequence, String str4, long j2, int i6) {
            AddCallParams.AddCallParametersBuilder addCallParametersBuilder = new AddCallParams.AddCallParametersBuilder();
            addCallParametersBuilder.setCallerInfo(callerInfo);
            addCallParametersBuilder.setNumber(str);
            addCallParametersBuilder.setPostDialDigits(str2);
            addCallParametersBuilder.setViaNumber(str3);
            addCallParametersBuilder.setPresentation(i);
            addCallParametersBuilder.setCallType(i2);
            addCallParametersBuilder.setFeatures(i3);
            addCallParametersBuilder.setAccountHandle(phoneAccountHandle);
            addCallParametersBuilder.setStart(j);
            addCallParametersBuilder.setDuration(i4);
            addCallParametersBuilder.setDataUsage(l == null ? Long.MIN_VALUE : l.longValue());
            addCallParametersBuilder.setAddForAllUsers(z);
            addCallParametersBuilder.setUserToBeInsertedTo(userHandle);
            addCallParametersBuilder.setIsRead(z2);
            addCallParametersBuilder.setCallBlockReason(i5);
            addCallParametersBuilder.setCallScreeningAppName(charSequence);
            addCallParametersBuilder.setCallScreeningComponentName(str4);
            addCallParametersBuilder.setMissedReason(j2);
            addCallParametersBuilder.setIsPhoneAccountMigrationPending(i6);
            return addCall(context, addCallParametersBuilder.build());
        }

        public static Uri addCall(Context context, AddCallParams addCallParams) {
            return addCall(context, addCallParams, null);
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
        
            if (r14.mCallerInfo != null) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static android.net.Uri addCall(android.content.Context r13, android.provider.CallLog.AddCallParams r14, android.content.ContentValues r15) {
            /*
                Method dump skipped, instructions count: 650
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.provider.CallLog.Calls.addCall(android.content.Context, android.provider.CallLog$AddCallParams, android.content.ContentValues):android.net.Uri");
        }

        private static String charSequenceToString(CharSequence charSequence) {
            if (charSequence == null) {
                return null;
            }
            return charSequence.toString();
        }

        public static boolean shouldHaveSharedCallLogEntries(Context context, UserManager userManager, int i) {
            UserInfo userInfo;
            return (userManager.hasUserRestriction(UserManager.DISALLOW_OUTGOING_CALLS, UserHandle.of(i)) || (userInfo = userManager.getUserInfo(i)) == null || userInfo.isProfile() || userInfo.isCloneProfile()) ? false : true;
        }

        public static String getLastOutgoingCall(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(CONTENT_URI_LIMIT_1, new String[]{"number"}, "type = 2", null, "date DESC");
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
                return "";
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private static Uri addEntryAndRemoveExpiredEntries(Context context, UserManager userManager, UserHandle userHandle, ContentValues contentValues) {
            String str;
            ContentResolver contentResolver = context.getContentResolver();
            Uri parse = Uri.parse("content://logs/call");
            if (!userManager.isUserUnlocked(userHandle)) {
                parse = SHADOW_CONTENT_URI;
            }
            Uri maybeAddUserId = ContentProvider.maybeAddUserId(parse, userHandle.getIdentifier());
            if (contentValues.containsKey(SEM_ROAMING_AUTO_DIALER_QUERY_PARAM)) {
                str = contentValues.getAsString(SEM_ROAMING_AUTO_DIALER_QUERY_PARAM);
                contentValues.remove(SEM_ROAMING_AUTO_DIALER_QUERY_PARAM);
            } else {
                str = null;
            }
            if (!TextUtils.isEmpty(str)) {
                maybeAddUserId = maybeAddUserId.buildUpon().appendQueryParameter(SEM_ROAMING_AUTO_DIALER_QUERY_PARAM, str).build();
            }
            try {
                Log.d("CallLog", "Provider called! Insert callLog as a uri");
                Uri insert = contentResolver.insert(maybeAddUserId, contentValues);
                if (insert != null) {
                    String lastPathSegment = insert.getLastPathSegment();
                    if (lastPathSegment != null && lastPathSegment.equals("0")) {
                        Log.w("CallLog", "Failed to insert into call log due to appops denial; resultUri=" + insert);
                    }
                } else {
                    Log.w("CallLog", "Failed to insert into call log; null result uri.");
                }
                if (!TextUtils.isEmpty(str)) {
                    contentValues.put(SEM_ROAMING_AUTO_DIALER_QUERY_PARAM, str);
                }
                return insert;
            } catch (IllegalArgumentException e) {
                Log.e("CallLog", "Failed to insert calllog", e);
                return null;
            }
        }

        private static Uri maybeInsertLocation(AddCallParams addCallParams, ContentResolver contentResolver, UserHandle userHandle) {
            if (!Double.isNaN(addCallParams.mLatitude) && !Double.isNaN(addCallParams.mLongitude)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("latitude", Double.valueOf(addCallParams.mLatitude));
                contentValues.put("longitude", Double.valueOf(addCallParams.mLongitude));
                try {
                    return contentResolver.insert(ContentProvider.maybeAddUserId(Locations.CONTENT_URI, userHandle.getIdentifier()), contentValues);
                } catch (SecurityException unused) {
                    Log.w("CallLog", "Skipping inserting location because caller lacks ACCESS_FINE_LOCATION.");
                }
            }
            return null;
        }

        private static void updateDataUsageStatForData(ContentResolver contentResolver, String str) {
            contentResolver.update(ContactsContract.DataUsageFeedback.FEEDBACK_URI.buildUpon().appendPath(str).appendQueryParameter("type", "call").build(), new ContentValues(), null, null);
        }

        private static void updateNormalizedNumber(Context context, ContentResolver contentResolver, String str, String str2) {
            if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                return;
            }
            String currentCountryIso = getCurrentCountryIso(context);
            if (TextUtils.isEmpty(currentCountryIso)) {
                return;
            }
            String formatNumberToE164 = PhoneNumberUtils.formatNumberToE164(str2, currentCountryIso);
            if (TextUtils.isEmpty(formatNumberToE164)) {
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("data4", formatNumberToE164);
            contentResolver.update(ContactsContract.Data.CONTENT_URI, contentValues, "_id=?", new String[]{str});
        }

        private static int getLogNumberPresentation(String str, int i) {
            if (i == 2 || i == 4) {
                return i;
            }
            if (i == 5) {
                return 5;
            }
            return (TextUtils.isEmpty(str) || i == 3) ? 3 : 1;
        }

        private static String getLogAccountAddress(Context context, PhoneAccountHandle phoneAccountHandle) {
            TelecomManager telecomManager;
            PhoneAccount phoneAccount;
            Uri subscriptionAddress;
            try {
                telecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
            } catch (UnsupportedOperationException unused) {
                telecomManager = null;
            }
            if (telecomManager == null || phoneAccountHandle == null || (phoneAccount = telecomManager.getPhoneAccount(phoneAccountHandle)) == null || (subscriptionAddress = phoneAccount.getSubscriptionAddress()) == null) {
                return null;
            }
            return subscriptionAddress.getSchemeSpecificPart();
        }

        private static String getCurrentCountryIso(Context context) {
            Country detectCountry;
            CountryDetector countryDetector = (CountryDetector) context.getSystemService(Context.COUNTRY_DETECTOR);
            if (countryDetector == null || (detectCountry = countryDetector.detectCountry()) == null) {
                return null;
            }
            return detectCountry.getCountryIso();
        }
    }

    public static class Locations implements BaseColumns {
        public static final String AUTHORITY = "call_composer_locations";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/call_composer_location";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/call_composer_location";
        public static final Uri CONTENT_URI = Uri.parse("content://call_composer_locations");
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";

        private Locations() {
        }
    }
}
