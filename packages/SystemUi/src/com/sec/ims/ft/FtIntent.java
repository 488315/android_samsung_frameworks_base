package com.sec.ims.ft;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface FtIntent {
    public static final String CATEGORY_ACTION = "com.samsung.rcs.framework.filetransfer.category.ACTION";
    public static final String CATEGORY_NOTIFICATION = "com.samsung.rcs.framework.filetransfer.category.NOTIFICATION";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Actions {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface RequestIntentToApp {
            public static final String REQUEST_FILE_RESIZE = "com.samsung.rcs.framework.filetransfer.request.REQUEST_FILE_RESIZE";
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface RequestIntents {
            public static final String ATTACH_FILE = "com.samsung.rcs.framework.filetransfer.action.ATTACH_FILE";
            public static final String ATTACH_FILE_TO_GROUP_CHAT = "com.samsung.rcs.framework.filetransfer.action.ATTACH_FILE_TO_GROUP_CHAT";
            public static final String GRANT_FILE_PERMISSION = "com.samsung.rcs.framework.instantmessaging.action.GRANT_FILE_PERMISSION";
            public static final String MOVE_FILE_COMPLETE = "com.samsung.rcs.framework.instantmessaging.action.MOVE_FILE_COMPLETE";
            public static final String MOVE_FILE_FINAL_COMPLETE = "com.samsung.rcs.framework.instantmessaging.action.MOVE_FILE_FINAL_COMPLETE";
            public static final String RESUME_INCOMING_FILE = "com.samsung.rcs.framework.filetransfer.action.RESUME_INCOMING_FILE";
            public static final String RESUME_SENDING_FILE = "com.samsung.rcs.framework.filetransfer.action.RESUME_SENDING_FILE";
            public static final String SEND_FILE = "com.samsung.rcs.framework.filetransfer.action.SEND_FILE";
            public static final String SEND_FILE_TO_GROUP_CHAT = "com.samsung.rcs.framework.filetransfer.action.SEND_FILE_TO_GROUP_CHAT";
            public static final String SET_AUTO_ACCEPT_FT = "com.samsung.rcs.framework.filetransfer.action.SET_AUTO_ACCEPT_FT";
            public static final String TRANSFER_ACCEPT = "com.samsung.rcs.framework.filetransfer.action.TRANSFER_ACCEPT";
            public static final String TRANSFER_CANCEL = "com.samsung.rcs.framework.filetransfer.action.TRANSFER_CANCEL";
            public static final String TRANSFER_DECLINE = "com.samsung.rcs.framework.filetransfer.action.TRANSFER_DECLINE";
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface ResponseIntentFromApp {
            public static final String RESPONSE_FILE_RESIZE = "com.samsung.rcs.framework.filetransfer.response.RESPONSE_FILE_RESIZE";
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface ResponseIntents {
            public static final String GRANT_FILE_PERMISSION_RESPONSE = "com.samsung.rcs.framework.instantmessaging.action.GRANT_FILE_PERMISSION_RESPONSE";
            public static final String REQUEST_FAILED = "com.samsung.rcs.framework.filetransfer.notification.REQUEST_FAILED";
            public static final String TRANSFER_ATTACHED = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_ATTACHED";
            public static final String TRANSFER_CANCELED = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_CANCELED";
            public static final String TRANSFER_COMPLETED = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_COMPLETED";
            public static final String TRANSFER_CREATED = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_CREATED";
            public static final String TRANSFER_INCOMING = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_INCOMING";
            public static final String TRANSFER_PROGRESS = "com.samsung.rcs.framework.filetransfer.notification.TRANSFER_PROGRESS";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Extras {
        public static final String EXTRA_AUTO_ACCEPT_STATE = "autoAcceptState";
        public static final String EXTRA_BYTES_DONE = "bytesDone";
        public static final String EXTRA_BYTES_TOTAL = "bytesTotal";
        public static final String EXTRA_CHAT_ID = "chatId";
        public static final String EXTRA_CONTACT_URI = "contactUri";
        public static final String EXTRA_CONTENT_URI = "contentUri";
        public static final String EXTRA_DISPOSITION_NOTIFICATION = "disposition_notification";
        public static final String EXTRA_EXTRA_FT = "ismassfiletransfer";
        public static final String EXTRA_FILE_DISPOSITION = "file_disposition";
        public static final String EXTRA_FILE_EXPIRE = "file_expire";
        public static final String EXTRA_FILE_NAME = "fileName";
        public static final String EXTRA_FT_AUTODOWNLOAD = "ft_autodownload";
        public static final String EXTRA_FT_CONTENTTYPE = "ft_contenttype";
        public static final String EXTRA_FT_MECH = "ft_mech";
        public static final String EXTRA_INVOKING_ACTION = "invokingAction";
        public static final String EXTRA_IS_FTSMS = "isftsms";
        public static final String EXTRA_IS_PUBLICACCOUNT = "is_publicAccountMsg";
        public static final String EXTRA_IS_RESIZABLE = "is_resizable";
        public static final String EXTRA_IS_STANDALONE = "is_standalone";
        public static final String EXTRA_MAAP_TRAFFIC_TYPE = "maap_traffic_type";
        public static final String EXTRA_MESSAGE_DIRECTION = "message_direction";
        public static final String EXTRA_MESSAGE_IMDN_ID = "message_imdn_id";
        public static final String EXTRA_NOTIFICATION_STATUS = "notification_status";
        public static final String EXTRA_OUTGOING_REQUEST = "outgoing_request";
        public static final String EXTRA_PLAYING_LENGTH = "playing_length";
        public static final String EXTRA_PUBLICACCOUNT_DOMAIN = "publicAccount_Send_Domain";
        public static final String EXTRA_REASON = "reason";
        public static final String EXTRA_RELIABLE_MESSAGE = "reliable_message";
        public static final String EXTRA_REQUEST_MESSAGE_ID = "request_message_id";
        public static final String EXTRA_REQUEST_RESULT = "request_result";
        public static final String EXTRA_RESIZE_LIMIT = "resize_limit";
        public static final String EXTRA_RESUMABLE_OPTION_CODE = "resumable_option_code";
        public static final String EXTRA_THUMBNAIL_DATA = "thumbnailData";
        public static final String EXTRA_TIME_DURATION = "timeDuration";
        public static final String FILE_PATHS = "file_paths";
        public static final String FILE_URIS = "file_uris";
        public static final String FT_SMS_BRANDEDURL = "ftsms_brandedurl";
        public static final String FT_SMS_DATAURL = "ftsms_dataurl";
        public static final String ICON_PATHS = "icon_paths";
        public static final String ICON_URIS = "icon_uris";
        public static final String MESSAGE_TYPE = "message_type";
        public static final String THUMB_PATHS = "thumb_paths";
        public static final String THUMB_URIS = "thumb_uris";
    }
}
