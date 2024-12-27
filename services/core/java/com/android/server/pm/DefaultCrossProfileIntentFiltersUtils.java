package com.android.server.pm;

import android.content.IntentFilter;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class DefaultCrossProfileIntentFiltersUtils {
    public static final DefaultCrossProfileIntentFilter ACTION_PICK_DATA;
    public static final DefaultCrossProfileIntentFilter ACTION_PICK_IMAGES;
    public static final DefaultCrossProfileIntentFilter ACTION_PICK_IMAGES_WITH_DATA_TYPES;
    public static final DefaultCrossProfileIntentFilter ACTION_PICK_RAW;
    public static final DefaultCrossProfileIntentFilter ACTION_SEND;
    public static final DefaultCrossProfileIntentFilter CALL_BUTTON;
    public static final DefaultCrossProfileIntentFilter CALL_BUTTON_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter CALL_MANAGED_PROFILE;
    public static final DefaultCrossProfileIntentFilter CALL_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_ACTION_PICK_IMAGES;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_ACTION_PICK_IMAGES_WITH_DATA_TYPES;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_DIAL_DATA;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_MEDIA_CAPTURE;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_PHOTOPICKER_SELECTION;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_PICK_INSERT_ACTION;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_SEND_ACTION;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_SMS_MMS;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_VIEW_ACTION;
    public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_WEB_VIEW_ACTION;
    public static final DefaultCrossProfileIntentFilter DIAL_DATA;
    public static final DefaultCrossProfileIntentFilter DIAL_DATA_MANAGED_PROFILE;
    public static final DefaultCrossProfileIntentFilter DIAL_DATA_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter DIAL_MIME;
    public static final DefaultCrossProfileIntentFilter DIAL_MIME_MANAGED_PROFILE;
    public static final DefaultCrossProfileIntentFilter DIAL_MIME_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter DIAL_RAW;
    public static final DefaultCrossProfileIntentFilter DIAL_RAW_MANAGED_PROFILE;
    public static final DefaultCrossProfileIntentFilter DIAL_RAW_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter EMERGENCY_CALL_DATA;
    public static final DefaultCrossProfileIntentFilter EMERGENCY_CALL_MIME;
    public static final DefaultCrossProfileIntentFilter GET_CONTENT;
    public static final DefaultCrossProfileIntentFilter HOME;
    public static final DefaultCrossProfileIntentFilter MEDIA_CAPTURE;
    public static final DefaultCrossProfileIntentFilter MOBILE_NETWORK_SETTINGS;
    public static final DefaultCrossProfileIntentFilter OPEN_DOCUMENT;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_DIAL_DATA;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_NFC_NDEF_DISCOVERED;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_NFC_TAG_DISCOVERED;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_NFC_TECH_DISCOVERED;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_PICK_INSERT_ACTION;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_SEND_ACTION;
    public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_WEB_VIEW_ACTION;
    public static final DefaultCrossProfileIntentFilter RECOGNIZE_SPEECH;
    public static final DefaultCrossProfileIntentFilter SET_ALARM;
    public static final DefaultCrossProfileIntentFilter SMS_MMS;
    public static final DefaultCrossProfileIntentFilter SMS_MMS_MANAGED_PROFILE;
    public static final DefaultCrossProfileIntentFilter SMS_MMS_PRIVATE_PROFILE;
    public static final DefaultCrossProfileIntentFilter USB_DEVICE_ATTACHED;

    static {
        WatchedIntentFilter watchedIntentFilter = new WatchedIntentFilter();
        watchedIntentFilter.addAction("android.intent.action.CALL_EMERGENCY");
        watchedIntentFilter.addAction("android.intent.action.CALL_PRIVILEGED");
        watchedIntentFilter.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter.addCategory("android.intent.category.BROWSABLE");
        try {
            watchedIntentFilter.addDataType("vnd.android.cursor.item/phone");
        } catch (IntentFilter.MalformedMimeTypeException unused) {
        }
        try {
            watchedIntentFilter.addDataType("vnd.android.cursor.item/phone_v2");
        } catch (IntentFilter.MalformedMimeTypeException unused2) {
        }
        try {
            watchedIntentFilter.addDataType("vnd.android.cursor.item/person");
        } catch (IntentFilter.MalformedMimeTypeException unused3) {
        }
        try {
            watchedIntentFilter.addDataType("vnd.android.cursor.dir/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused4) {
        }
        try {
            watchedIntentFilter.addDataType("vnd.android.cursor.item/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused5) {
        }
        EMERGENCY_CALL_MIME = new DefaultCrossProfileIntentFilter(watchedIntentFilter, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter2 = new WatchedIntentFilter();
        watchedIntentFilter2.addAction("android.intent.action.CALL_EMERGENCY");
        watchedIntentFilter2.addAction("android.intent.action.CALL_PRIVILEGED");
        watchedIntentFilter2.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter2.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter2.addDataScheme("tel");
        watchedIntentFilter2.addDataScheme("sip");
        watchedIntentFilter2.addDataScheme("voicemail");
        EMERGENCY_CALL_DATA = new DefaultCrossProfileIntentFilter(watchedIntentFilter2, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter3 = new WatchedIntentFilter();
        watchedIntentFilter3.addAction("android.intent.action.DIAL");
        watchedIntentFilter3.addAction("android.intent.action.VIEW");
        watchedIntentFilter3.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter3.addCategory("android.intent.category.BROWSABLE");
        try {
            watchedIntentFilter3.addDataType("vnd.android.cursor.item/phone");
        } catch (IntentFilter.MalformedMimeTypeException unused6) {
        }
        try {
            watchedIntentFilter3.addDataType("vnd.android.cursor.item/phone_v2");
        } catch (IntentFilter.MalformedMimeTypeException unused7) {
        }
        try {
            watchedIntentFilter3.addDataType("vnd.android.cursor.item/person");
        } catch (IntentFilter.MalformedMimeTypeException unused8) {
        }
        try {
            watchedIntentFilter3.addDataType("vnd.android.cursor.dir/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused9) {
        }
        try {
            watchedIntentFilter3.addDataType("vnd.android.cursor.item/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused10) {
        }
        DIAL_MIME = new DefaultCrossProfileIntentFilter(watchedIntentFilter3, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter4 = new WatchedIntentFilter();
        watchedIntentFilter4.addAction("android.intent.action.DIAL");
        watchedIntentFilter4.addAction("android.intent.action.VIEW");
        watchedIntentFilter4.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter4.addCategory("android.intent.category.BROWSABLE");
        try {
            watchedIntentFilter4.addDataType("vnd.android.cursor.item/phone");
        } catch (IntentFilter.MalformedMimeTypeException unused11) {
        }
        try {
            watchedIntentFilter4.addDataType("vnd.android.cursor.item/phone_v2");
        } catch (IntentFilter.MalformedMimeTypeException unused12) {
        }
        try {
            watchedIntentFilter4.addDataType("vnd.android.cursor.item/person");
        } catch (IntentFilter.MalformedMimeTypeException unused13) {
        }
        try {
            watchedIntentFilter4.addDataType("vnd.android.cursor.dir/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused14) {
        }
        try {
            watchedIntentFilter4.addDataType("vnd.android.cursor.item/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused15) {
        }
        DIAL_MIME_MANAGED_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter4, 2, 1, false);
        WatchedIntentFilter watchedIntentFilter5 = new WatchedIntentFilter();
        watchedIntentFilter5.addAction("android.intent.action.DIAL");
        watchedIntentFilter5.addAction("android.intent.action.VIEW");
        watchedIntentFilter5.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter5.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter5.addDataScheme("tel");
        watchedIntentFilter5.addDataScheme("sip");
        watchedIntentFilter5.addDataScheme("voicemail");
        DIAL_DATA = new DefaultCrossProfileIntentFilter(watchedIntentFilter5, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter6 = new WatchedIntentFilter();
        watchedIntentFilter6.addAction("android.intent.action.DIAL");
        watchedIntentFilter6.addAction("android.intent.action.VIEW");
        watchedIntentFilter6.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter6.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter6.addDataScheme("tel");
        watchedIntentFilter6.addDataScheme("sip");
        watchedIntentFilter6.addDataScheme("voicemail");
        DIAL_DATA_MANAGED_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter6, 2, 1, false);
        WatchedIntentFilter watchedIntentFilter7 = new WatchedIntentFilter();
        watchedIntentFilter7.addAction("android.intent.action.DIAL");
        watchedIntentFilter7.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter7.addCategory("android.intent.category.BROWSABLE");
        DIAL_RAW = new DefaultCrossProfileIntentFilter(watchedIntentFilter7, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter8 = new WatchedIntentFilter();
        watchedIntentFilter8.addAction("android.intent.action.DIAL");
        watchedIntentFilter8.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter8.addCategory("android.intent.category.BROWSABLE");
        DIAL_RAW_MANAGED_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter8, 2, 1, false);
        WatchedIntentFilter watchedIntentFilter9 = new WatchedIntentFilter();
        watchedIntentFilter9.addAction("android.intent.action.CALL_BUTTON");
        watchedIntentFilter9.addCategory("android.intent.category.DEFAULT");
        CALL_BUTTON = new DefaultCrossProfileIntentFilter(watchedIntentFilter9, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter10 = new WatchedIntentFilter();
        watchedIntentFilter10.addAction("android.intent.action.VIEW");
        watchedIntentFilter10.addAction("android.intent.action.SENDTO");
        watchedIntentFilter10.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter10.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter10.addDataScheme("sms");
        watchedIntentFilter10.addDataScheme("smsto");
        watchedIntentFilter10.addDataScheme("mms");
        watchedIntentFilter10.addDataScheme("mmsto");
        SMS_MMS = new DefaultCrossProfileIntentFilter(watchedIntentFilter10, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter11 = new WatchedIntentFilter();
        watchedIntentFilter11.addAction("android.intent.action.VIEW");
        watchedIntentFilter11.addAction("android.intent.action.SENDTO");
        watchedIntentFilter11.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter11.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter11.addDataScheme("sms");
        watchedIntentFilter11.addDataScheme("smsto");
        watchedIntentFilter11.addDataScheme("mms");
        watchedIntentFilter11.addDataScheme("mmsto");
        SMS_MMS_MANAGED_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter11, 2, 1, false);
        WatchedIntentFilter watchedIntentFilter12 = new WatchedIntentFilter();
        watchedIntentFilter12.addAction("android.settings.DATA_ROAMING_SETTINGS");
        watchedIntentFilter12.addAction("android.settings.NETWORK_OPERATOR_SETTINGS");
        watchedIntentFilter12.addCategory("android.intent.category.DEFAULT");
        MOBILE_NETWORK_SETTINGS = new DefaultCrossProfileIntentFilter(watchedIntentFilter12, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter13 = new WatchedIntentFilter();
        watchedIntentFilter13.addAction("android.intent.action.MAIN");
        watchedIntentFilter13.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter13.addCategory("android.intent.category.HOME");
        HOME = new DefaultCrossProfileIntentFilter(watchedIntentFilter13, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter14 = new WatchedIntentFilter();
        watchedIntentFilter14.addAction("android.intent.action.GET_CONTENT");
        watchedIntentFilter14.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter14.addCategory("android.intent.category.OPENABLE");
        try {
            watchedIntentFilter14.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused16) {
        }
        GET_CONTENT = new DefaultCrossProfileIntentFilter(watchedIntentFilter14, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter15 = new WatchedIntentFilter();
        watchedIntentFilter15.addAction("android.provider.action.PICK_IMAGES");
        watchedIntentFilter15.addCategory("android.intent.category.DEFAULT");
        ACTION_PICK_IMAGES = new DefaultCrossProfileIntentFilter(watchedIntentFilter15, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter16 = new WatchedIntentFilter();
        watchedIntentFilter16.addAction("android.provider.action.PICK_IMAGES");
        watchedIntentFilter16.addCategory("android.intent.category.DEFAULT");
        try {
            watchedIntentFilter16.addDataType("image/*");
        } catch (IntentFilter.MalformedMimeTypeException unused17) {
        }
        try {
            watchedIntentFilter16.addDataType("video/*");
        } catch (IntentFilter.MalformedMimeTypeException unused18) {
        }
        ACTION_PICK_IMAGES_WITH_DATA_TYPES = new DefaultCrossProfileIntentFilter(watchedIntentFilter16, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter17 = new WatchedIntentFilter();
        watchedIntentFilter17.addAction("android.intent.action.OPEN_DOCUMENT");
        watchedIntentFilter17.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter17.addCategory("android.intent.category.OPENABLE");
        try {
            watchedIntentFilter17.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused19) {
        }
        OPEN_DOCUMENT = new DefaultCrossProfileIntentFilter(watchedIntentFilter17, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter18 = new WatchedIntentFilter();
        watchedIntentFilter18.addAction("android.intent.action.PICK");
        watchedIntentFilter18.addCategory("android.intent.category.DEFAULT");
        try {
            watchedIntentFilter18.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused20) {
        }
        ACTION_PICK_DATA = new DefaultCrossProfileIntentFilter(watchedIntentFilter18, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter19 = new WatchedIntentFilter();
        watchedIntentFilter19.addAction("android.intent.action.PICK");
        watchedIntentFilter19.addCategory("android.intent.category.DEFAULT");
        ACTION_PICK_RAW = new DefaultCrossProfileIntentFilter(watchedIntentFilter19, 0, 0, true);
        WatchedIntentFilter watchedIntentFilter20 = new WatchedIntentFilter();
        watchedIntentFilter20.addAction("android.speech.action.RECOGNIZE_SPEECH");
        watchedIntentFilter20.addCategory("android.intent.category.DEFAULT");
        RECOGNIZE_SPEECH = new DefaultCrossProfileIntentFilter(watchedIntentFilter20, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter21 = new WatchedIntentFilter();
        watchedIntentFilter21.addAction("android.media.action.IMAGE_CAPTURE");
        watchedIntentFilter21.addAction("android.media.action.IMAGE_CAPTURE_SECURE");
        watchedIntentFilter21.addAction("android.media.action.VIDEO_CAPTURE");
        watchedIntentFilter21.addAction("android.provider.MediaStore.RECORD_SOUND");
        watchedIntentFilter21.addAction("android.media.action.STILL_IMAGE_CAMERA");
        watchedIntentFilter21.addAction("android.media.action.STILL_IMAGE_CAMERA_SECURE");
        watchedIntentFilter21.addAction("android.media.action.VIDEO_CAMERA");
        watchedIntentFilter21.addCategory("android.intent.category.DEFAULT");
        MEDIA_CAPTURE = new DefaultCrossProfileIntentFilter(watchedIntentFilter21, 4, 0, true);
        WatchedIntentFilter watchedIntentFilter22 = new WatchedIntentFilter();
        watchedIntentFilter22.addAction("android.intent.action.SET_ALARM");
        watchedIntentFilter22.addAction("android.intent.action.SHOW_ALARMS");
        watchedIntentFilter22.addAction("android.intent.action.SET_TIMER");
        watchedIntentFilter22.addCategory("android.intent.category.DEFAULT");
        SET_ALARM = new DefaultCrossProfileIntentFilter(watchedIntentFilter22, 0, 0, false);
        WatchedIntentFilter watchedIntentFilter23 = new WatchedIntentFilter();
        watchedIntentFilter23.addAction("android.intent.action.SEND");
        watchedIntentFilter23.addAction("android.intent.action.SEND_MULTIPLE");
        watchedIntentFilter23.addCategory("android.intent.category.DEFAULT");
        try {
            watchedIntentFilter23.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused21) {
        }
        ACTION_SEND = new DefaultCrossProfileIntentFilter(watchedIntentFilter23, 0, 1, true);
        WatchedIntentFilter watchedIntentFilter24 = new WatchedIntentFilter();
        watchedIntentFilter24.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        watchedIntentFilter24.addAction("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        watchedIntentFilter24.addCategory("android.intent.category.DEFAULT");
        USB_DEVICE_ATTACHED = new DefaultCrossProfileIntentFilter(watchedIntentFilter24, 0, 1, false);
        WatchedIntentFilter watchedIntentFilter25 = new WatchedIntentFilter();
        watchedIntentFilter25.addAction("android.intent.action.CALL");
        watchedIntentFilter25.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter25.addDataScheme("tel");
        CALL_MANAGED_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter25, 2, 1, false);
        WatchedIntentFilter watchedIntentFilter26 = new WatchedIntentFilter();
        watchedIntentFilter26.addAction("android.media.action.IMAGE_CAPTURE");
        watchedIntentFilter26.addAction("android.media.action.IMAGE_CAPTURE_SECURE");
        watchedIntentFilter26.addAction("android.media.action.VIDEO_CAPTURE");
        watchedIntentFilter26.addAction("android.provider.MediaStore.RECORD_SOUND");
        watchedIntentFilter26.addAction("android.media.action.STILL_IMAGE_CAMERA");
        watchedIntentFilter26.addAction("android.media.action.STILL_IMAGE_CAMERA_SECURE");
        watchedIntentFilter26.addAction("android.media.action.VIDEO_CAMERA");
        watchedIntentFilter26.addCategory("android.intent.category.DEFAULT");
        CLONE_TO_PARENT_MEDIA_CAPTURE = new DefaultCrossProfileIntentFilter(watchedIntentFilter26, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter27 = new WatchedIntentFilter();
        watchedIntentFilter27.addAction("android.provider.action.USER_SELECT_IMAGES_FOR_APP");
        watchedIntentFilter27.addCategory("android.intent.category.DEFAULT");
        CLONE_TO_PARENT_PHOTOPICKER_SELECTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter27, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter28 = new WatchedIntentFilter();
        watchedIntentFilter28.addAction("android.intent.action.SEND");
        watchedIntentFilter28.addAction("android.intent.action.SEND_MULTIPLE");
        watchedIntentFilter28.addAction("android.intent.action.SENDTO");
        try {
            watchedIntentFilter28.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused22) {
        }
        CLONE_TO_PARENT_SEND_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter28, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter29 = new WatchedIntentFilter();
        watchedIntentFilter29.addAction("android.intent.action.SEND");
        watchedIntentFilter29.addAction("android.intent.action.SEND_MULTIPLE");
        watchedIntentFilter29.addAction("android.intent.action.SENDTO");
        try {
            watchedIntentFilter29.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused23) {
        }
        PARENT_TO_CLONE_SEND_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter29, 24, 1, false);
        WatchedIntentFilter watchedIntentFilter30 = new WatchedIntentFilter();
        watchedIntentFilter30.addAction("android.intent.action.VIEW");
        watchedIntentFilter30.addDataScheme("https");
        watchedIntentFilter30.addDataScheme("http");
        CLONE_TO_PARENT_WEB_VIEW_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter30, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter31 = new WatchedIntentFilter();
        watchedIntentFilter31.addAction("android.intent.action.VIEW");
        watchedIntentFilter31.addDataScheme("https");
        watchedIntentFilter31.addDataScheme("http");
        PARENT_TO_CLONE_WEB_VIEW_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter31, 24, 1, false);
        WatchedIntentFilter watchedIntentFilter32 = new WatchedIntentFilter();
        watchedIntentFilter32.addAction("android.intent.action.VIEW");
        try {
            watchedIntentFilter32.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused24) {
        }
        CLONE_TO_PARENT_VIEW_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter32, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter33 = new WatchedIntentFilter();
        watchedIntentFilter33.addAction("android.intent.action.PICK");
        watchedIntentFilter33.addAction("android.intent.action.GET_CONTENT");
        watchedIntentFilter33.addAction("android.intent.action.EDIT");
        watchedIntentFilter33.addAction("android.intent.action.INSERT");
        watchedIntentFilter33.addAction("android.intent.action.INSERT_OR_EDIT");
        watchedIntentFilter33.addAction("android.intent.action.OPEN_DOCUMENT");
        try {
            watchedIntentFilter33.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused25) {
        }
        watchedIntentFilter33.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter33.addCategory("android.intent.category.OPENABLE");
        CLONE_TO_PARENT_PICK_INSERT_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter33, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter34 = new WatchedIntentFilter();
        watchedIntentFilter34.addAction("android.intent.action.PICK");
        watchedIntentFilter34.addAction("android.intent.action.GET_CONTENT");
        watchedIntentFilter34.addAction("android.intent.action.EDIT");
        watchedIntentFilter34.addAction("android.intent.action.INSERT");
        watchedIntentFilter34.addAction("android.intent.action.INSERT_OR_EDIT");
        watchedIntentFilter34.addAction("android.intent.action.OPEN_DOCUMENT");
        try {
            watchedIntentFilter34.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException unused26) {
        }
        watchedIntentFilter34.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter34.addCategory("android.intent.category.OPENABLE");
        PARENT_TO_CLONE_PICK_INSERT_ACTION = new DefaultCrossProfileIntentFilter(watchedIntentFilter34, 24, 1, false);
        WatchedIntentFilter watchedIntentFilter35 = new WatchedIntentFilter();
        watchedIntentFilter35.addAction("android.intent.action.DIAL");
        watchedIntentFilter35.addAction("android.intent.action.VIEW");
        watchedIntentFilter35.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter35.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter35.addDataScheme("tel");
        watchedIntentFilter35.addDataScheme("sip");
        watchedIntentFilter35.addDataScheme("voicemail");
        PARENT_TO_CLONE_DIAL_DATA = new DefaultCrossProfileIntentFilter(watchedIntentFilter35, 24, 1, false);
        WatchedIntentFilter watchedIntentFilter36 = new WatchedIntentFilter();
        watchedIntentFilter36.addAction("android.intent.action.DIAL");
        watchedIntentFilter36.addAction("android.intent.action.VIEW");
        watchedIntentFilter36.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter36.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter36.addDataScheme("tel");
        watchedIntentFilter36.addDataScheme("sip");
        watchedIntentFilter36.addDataScheme("voicemail");
        CLONE_TO_PARENT_DIAL_DATA = new DefaultCrossProfileIntentFilter(watchedIntentFilter36, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter37 = new WatchedIntentFilter();
        watchedIntentFilter37.addAction("android.intent.action.VIEW");
        watchedIntentFilter37.addAction("android.intent.action.SENDTO");
        watchedIntentFilter37.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter37.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter37.addDataScheme("sms");
        watchedIntentFilter37.addDataScheme("smsto");
        watchedIntentFilter37.addDataScheme("mms");
        watchedIntentFilter37.addDataScheme("mmsto");
        CLONE_TO_PARENT_SMS_MMS = new DefaultCrossProfileIntentFilter(watchedIntentFilter37, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter38 = new WatchedIntentFilter();
        watchedIntentFilter38.addAction("android.provider.action.PICK_IMAGES");
        watchedIntentFilter38.addCategory("android.intent.category.DEFAULT");
        CLONE_TO_PARENT_ACTION_PICK_IMAGES = new DefaultCrossProfileIntentFilter(watchedIntentFilter38, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter39 = new WatchedIntentFilter();
        watchedIntentFilter39.addAction("android.provider.action.PICK_IMAGES");
        watchedIntentFilter39.addCategory("android.intent.category.DEFAULT");
        try {
            watchedIntentFilter39.addDataType("image/*");
        } catch (IntentFilter.MalformedMimeTypeException unused27) {
        }
        try {
            watchedIntentFilter39.addDataType("video/*");
        } catch (IntentFilter.MalformedMimeTypeException unused28) {
        }
        CLONE_TO_PARENT_ACTION_PICK_IMAGES_WITH_DATA_TYPES = new DefaultCrossProfileIntentFilter(watchedIntentFilter39, 24, 0, false);
        WatchedIntentFilter watchedIntentFilter40 = new WatchedIntentFilter();
        watchedIntentFilter40.addAction("android.nfc.action.TAG_DISCOVERED");
        PARENT_TO_CLONE_NFC_TAG_DISCOVERED = new DefaultCrossProfileIntentFilter(watchedIntentFilter40, 0, 1, false);
        WatchedIntentFilter watchedIntentFilter41 = new WatchedIntentFilter();
        watchedIntentFilter41.addAction("android.nfc.action.TECH_DISCOVERED");
        PARENT_TO_CLONE_NFC_TECH_DISCOVERED = new DefaultCrossProfileIntentFilter(watchedIntentFilter41, 0, 1, false);
        WatchedIntentFilter watchedIntentFilter42 = new WatchedIntentFilter();
        watchedIntentFilter42.addAction("android.nfc.action.NDEF_DISCOVERED");
        PARENT_TO_CLONE_NFC_NDEF_DISCOVERED = new DefaultCrossProfileIntentFilter(watchedIntentFilter42, 0, 1, false);
        WatchedIntentFilter watchedIntentFilter43 = new WatchedIntentFilter();
        watchedIntentFilter43.addAction("android.intent.action.CALL");
        watchedIntentFilter43.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter43.addDataScheme("tel");
        watchedIntentFilter43.addDataScheme("sip");
        watchedIntentFilter43.addDataScheme("voicemail");
        CALL_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter43, 2, 0, false);
        WatchedIntentFilter watchedIntentFilter44 = new WatchedIntentFilter();
        watchedIntentFilter44.addAction("android.intent.action.CALL_BUTTON");
        watchedIntentFilter44.addCategory("android.intent.category.DEFAULT");
        CALL_BUTTON_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter44, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter45 = new WatchedIntentFilter();
        watchedIntentFilter45.addAction("android.intent.action.DIAL");
        watchedIntentFilter45.addAction("android.intent.action.VIEW");
        watchedIntentFilter45.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter45.addCategory("android.intent.category.BROWSABLE");
        try {
            watchedIntentFilter45.addDataType("vnd.android.cursor.item/phone");
        } catch (IntentFilter.MalformedMimeTypeException unused29) {
        }
        try {
            watchedIntentFilter45.addDataType("vnd.android.cursor.item/phone_v2");
        } catch (IntentFilter.MalformedMimeTypeException unused30) {
        }
        try {
            watchedIntentFilter45.addDataType("vnd.android.cursor.item/person");
        } catch (IntentFilter.MalformedMimeTypeException unused31) {
        }
        try {
            watchedIntentFilter45.addDataType("vnd.android.cursor.dir/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused32) {
        }
        try {
            watchedIntentFilter45.addDataType("vnd.android.cursor.item/calls");
        } catch (IntentFilter.MalformedMimeTypeException unused33) {
        }
        DIAL_MIME_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter45, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter46 = new WatchedIntentFilter();
        watchedIntentFilter46.addAction("android.intent.action.DIAL");
        watchedIntentFilter46.addAction("android.intent.action.VIEW");
        watchedIntentFilter46.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter46.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter46.addDataScheme("tel");
        watchedIntentFilter46.addDataScheme("sip");
        watchedIntentFilter46.addDataScheme("voicemail");
        DIAL_DATA_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter46, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter47 = new WatchedIntentFilter();
        watchedIntentFilter47.addAction("android.intent.action.DIAL");
        watchedIntentFilter47.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter47.addCategory("android.intent.category.BROWSABLE");
        DIAL_RAW_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter47, 4, 0, false);
        WatchedIntentFilter watchedIntentFilter48 = new WatchedIntentFilter();
        watchedIntentFilter48.addAction("android.intent.action.VIEW");
        watchedIntentFilter48.addAction("android.intent.action.SENDTO");
        watchedIntentFilter48.addCategory("android.intent.category.DEFAULT");
        watchedIntentFilter48.addCategory("android.intent.category.BROWSABLE");
        watchedIntentFilter48.addDataScheme("sms");
        watchedIntentFilter48.addDataScheme("smsto");
        watchedIntentFilter48.addDataScheme("mms");
        watchedIntentFilter48.addDataScheme("mmsto");
        SMS_MMS_PRIVATE_PROFILE = new DefaultCrossProfileIntentFilter(watchedIntentFilter48, 2, 0, false);
    }

    public static List getDefaultCrossProfileTelephonyIntentFilters(boolean z) {
        if (z) {
            return Arrays.asList(DIAL_DATA_MANAGED_PROFILE, DIAL_MIME_MANAGED_PROFILE, DIAL_RAW_MANAGED_PROFILE, CALL_MANAGED_PROFILE, SMS_MMS_MANAGED_PROFILE);
        }
        return Arrays.asList(DIAL_DATA, DIAL_MIME, DIAL_RAW, SMS_MMS);
    }
}
