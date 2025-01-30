package com.sec.ims.scab;

import android.content.ContentUris;
import android.net.Uri;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CABContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.jansky.cab.provider");
    public static final int CAB_ADD_PENDING_CONSUMER_CONTACTS = 22;
    public static final int CAB_BUSINESS_CONTACTS = 10;
    public static final int CAB_BUSINESS_CONTACTS_REQUESTS = 11;
    public static final int CAB_BUSINESS_CONTACT_ADDRESSES = 120;
    public static final int CAB_BUSINESS_CONTACT_ADDRESSES_BY_CONTACT = 122;
    public static final int CAB_BUSINESS_CONTACT_ADDRESSES_BY_NOTIFY = 121;
    public static final int CAB_BUSINESS_CONTACT_EMAILS = 100;
    public static final int CAB_BUSINESS_CONTACT_EMAILS_BY_CONTACT = 102;
    public static final int CAB_BUSINESS_CONTACT_EMAILS_BY_NOTIFY = 101;
    public static final int CAB_BUSINESS_CONTACT_ORGANS = 130;
    public static final int CAB_BUSINESS_CONTACT_ORGANS_BY_CONTACT = 132;
    public static final int CAB_BUSINESS_CONTACT_ORGANS_BY_NOTIFY = 131;
    public static final int CAB_BUSINESS_CONTACT_PHONES = 110;
    public static final int CAB_BUSINESS_CONTACT_PHONES_BY_CONTACT = 112;
    public static final int CAB_BUSINESS_CONTACT_PHONES_BY_NOTIFY = 111;
    public static final int CAB_BUSINESS_PENDING_REQUESTS = 12;
    public static final int CAB_CONSUMER_CONTACTS = 20;
    public static final int CAB_CONSUMER_CONTACTS_REQUESTS = 21;
    public static final int CAB_DELETE_PENDING_CONSUMER_CONTACTS = 23;
    public static final int CAB_STATUS = 0;
    public static final int CAB_UPDATE_PENDING_CONSUMER_CONTACTS = 24;
    public static final int COMPLETED = 3;
    public static final int DISABLED = 0;
    public static final int DISABLE_CAB = 2;
    public static final int ENABLED = 1;
    public static final int ENABLE_CAB = 1;
    public static final String PACKAGE_CONTEXT = "com.sec.imsservice";
    public static final String PROVIDER_NAME = "com.samsung.jansky.cab.provider";
    public static final String SERVICE_CLASS_NAME = "com.sec.internal.ims.imsservice.CABService";
    public static final int SYNCED = 2;
    public static final int SYNCING = 1;
    public static final int UNCOMPLETED = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContact {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "bscontacts");
        public static final String DISPLAY_NAME = "display_name";
        public static final String FAMILY_NAME = "family_name";
        public static final String GIVEN_NAME = "given_name";

        /* renamed from: ID */
        public static final String f640ID = "_id";
        public static final String MIDDLE_NAME = "middle_name";
        public static final String MSISDN = "msisdn";
        public static final String NOTIFY_ID = "notify_id";
        public static final String PREFIX = "prefix";
        public static final String SUFFIX = "suffix";
        public static final String SYNC_ACTION = "sync_action";
        public static final String SYNC_COMPLETE = "sync_complete";
        public static final String XDM_CONTACT_ID = "xdm_contact_id";

        private CABBusinessContact() {
        }

        public static final Uri buildBusinessContactsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "contacts");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContactAddress {
        public static final String CITY = "city";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "addresses");
        public static final String COUNTRY = "country";
        public static final String FORMATTED_ADDRESS = "formatted_address";

        /* renamed from: ID */
        public static final String f641ID = "_id";
        public static final String LABEL = "label";
        public static final String POSTCODE = "postcode";
        public static final String PREF = "preference";
        public static final String STATE = "state";
        public static final String STREET = "street";
        public static final String TYPE = "type";

        private CABBusinessContactAddress() {
        }

        public static final Uri buildBusinessContactAddressesUri() {
            return Uri.withAppendedPath(CONTENT_URI, "contact_addresses");
        }

        public static final Uri buildBusinessContactAddressesUriByContact(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "contact_addresses");
        }

        public static final Uri buildBusinessContactAddressesUriByNotify(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "notify_addresses");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContactEmail {
        public static final String ADDRESS = "address";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "emails");

        /* renamed from: ID */
        public static final String f642ID = "_id";
        public static final String LABEL = "label";
        public static final String PREF = "preference";
        public static final String TYPE = "type";

        private CABBusinessContactEmail() {
        }

        public static final Uri buildBusinessContactEmailsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "contact_emails");
        }

        public static final Uri buildBusinessContactEmailsUriByContact(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "contact_emails");
        }

        public static final Uri buildBusinessContactEmailsUriByNotify(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "notify_emails");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContactOrgan {
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "organizations");
        public static final String DISPLAY_NAME = "display_name";
        public static final String ENTITY = "entity";

        /* renamed from: ID */
        public static final String f643ID = "_id";
        public static final String ROLE = "role";
        public static final String UNIT = "unit";

        private CABBusinessContactOrgan() {
        }

        public static final Uri buildBusinessContactOrganizationsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "contact_organs");
        }

        public static final Uri buildBusinessContactOrganizationsUriByContact(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "contact_organs");
        }

        public static final Uri buildBusinessContactOrganizationsUriByNotify(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "notify_organs");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContactPhone {
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "phones");

        /* renamed from: ID */
        public static final String f644ID = "_id";
        public static final String LABEL = "label";
        public static final String NUMBER = "number";
        public static final String PREF = "preference";
        public static final String TYPE = "type";

        private CABBusinessContactPhone() {
        }

        public static final Uri buildBusinessContactPhonesUri() {
            return Uri.withAppendedPath(CONTENT_URI, "contact_phones");
        }

        public static final Uri buildBusinessContactPhonesUriByContact(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "contact_phones");
        }

        public static final Uri buildBusinessContactPhonesUriByNotify(long j) {
            return Uri.withAppendedPath(ContentUris.withAppendedId(CONTENT_URI, j), "notify_phones");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABBusinessContactRequest {
        public static final String COMPLETE = "complete";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "bsrequests");
        public static final String FILE_NAME = "file_name";

        /* renamed from: ID */
        public static final String f645ID = "_id";
        public static final String IMPU = "impu";
        public static final String LINE_MSISDN = "msisdn";
        public static final String REQUEST_ID = "request_id";
        public static final String SYNC_ACTION = "sync_action";
        public static final String XDM_ACTION = "xdm_action";
        public static final String XDM_CONTACT_ID = "xdm_contact_id";
        public static final String XDM_CONTACT_POSITION = "xdm_position";

        private CABBusinessContactRequest() {
        }

        public static final Uri buildGetUnsyncedDownloadRequests() {
            return Uri.withAppendedPath(CONTENT_URI, "pending");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABConfig {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "config");

        /* renamed from: ID */
        public static final String f646ID = "_id";
        public static final String STATUS = "enabled";

        private CABConfig() {
        }

        public static final Uri buildDisableCabUri() {
            return Uri.withAppendedPath(CONTENT_URI, "disable");
        }

        public static final Uri buildEnableCabUri() {
            return Uri.withAppendedPath(CONTENT_URI, "enable");
        }

        public static final Uri buildGetCabStatusUri() {
            return Uri.withAppendedPath(CONTENT_URI, IMSParameter.CALL.STATUS);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABConsumerContact {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "crcontacts");

        /* renamed from: ID */
        public static final String f647ID = "_id";
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        public static final String REQUEST_ID = "request_id";
        public static final String SYNC_ACTION = "sync_action";
        public static final String SYNC_COMPLETE = "sync_complete";

        private CABConsumerContact() {
        }

        public static final Uri buildUnprocessedAddConsumerContactsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "pending_add");
        }

        public static final Uri buildUnprocessedDeleteConsumerContactsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "pending_delete");
        }

        public static final Uri buildUnprocessedUpdateConsumerContactsUri() {
            return Uri.withAppendedPath(CONTENT_URI, "pending_update");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class CABConsumerContactRequest {
        public static final String CLIENT_REQUEST_UID = "client_uid";
        public static final String COMPLETE = "complete";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CABContract.AUTHORITY_URI, "crrequests");
        public static final String HIGH_RAW_CONTACT_ID = "high_contact_id";

        /* renamed from: ID */
        public static final String f648ID = "_id";
        public static final String LOW_RAW_CONTACT_ID = "low_contact_id";
        public static final String REQUEST_ID = "request_id";
        public static final String SYNC_ACTION = "sync_action";

        private CABConsumerContactRequest() {
        }
    }
}
