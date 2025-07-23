package android.view.contentprotection;

import android.content.ContentCaptureOptions;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.util.Log;
import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.ViewNode;
import android.view.contentprotection.ContentProtectionEventProcessor;
import com.android.internal.util.RingBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class ContentProtectionEventProcessor {
    private static final int RESET_LOGIN_TOTAL_EVENTS_TO_PROCESS = 150;
    private static final String TAG = "ContentProtectionEventProcessor";
    private boolean mAnyGroupFound = false;
    private final IContentCaptureManager mContentCaptureManager;
    private final RingBuffer<ContentCaptureEvent> mEventBuffer;
    private final List<SearchGroup> mGroupsAll;
    private final List<SearchGroup> mGroupsOptional;
    private final List<SearchGroup> mGroupsRequired;
    private final Handler mHandler;
    public Instant mLastFlushTime;
    private final ContentCaptureOptions.ContentProtectionOptions mOptions;
    private final String mPackageName;
    private int mResetLoginRemainingEventsToProcess;
    private static final Duration MIN_DURATION_BETWEEN_FLUSHING = Duration.ofSeconds(3);
    private static final Set<Integer> EVENT_TYPES_TO_STORE = Set.of(1, 2, 3);

    public ContentProtectionEventProcessor(RingBuffer<ContentCaptureEvent> ringBuffer, Handler handler, IContentCaptureManager iContentCaptureManager, String str, ContentCaptureOptions.ContentProtectionOptions contentProtectionOptions) {
        this.mEventBuffer = ringBuffer;
        this.mHandler = handler;
        this.mContentCaptureManager = iContentCaptureManager;
        this.mPackageName = str;
        this.mOptions = contentProtectionOptions;
        List<SearchGroup> list = contentProtectionOptions.requiredGroups.stream().map(new Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new ContentProtectionEventProcessor.SearchGroup((List) obj);
            }
        }).toList();
        this.mGroupsRequired = list;
        List<SearchGroup> list2 = contentProtectionOptions.optionalGroups.stream().map(new Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new ContentProtectionEventProcessor.SearchGroup((List) obj);
            }
        }).toList();
        this.mGroupsOptional = list2;
        this.mGroupsAll = Stream.of((Object[]) new List[]{list, list2}).flatMap(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).toList();
    }

    public void processEvent(ContentCaptureEvent contentCaptureEvent) {
        if (EVENT_TYPES_TO_STORE.contains(Integer.valueOf(contentCaptureEvent.getType()))) {
            storeEvent(contentCaptureEvent);
        }
        if (contentCaptureEvent.getType() == 1) {
            processViewAppearedEvent(contentCaptureEvent);
        }
    }

    private void storeEvent(ContentCaptureEvent contentCaptureEvent) {
        ViewNode viewNode = contentCaptureEvent.getViewNode() != null ? contentCaptureEvent.getViewNode() : new ViewNode();
        viewNode.setTextIdEntry(this.mPackageName);
        contentCaptureEvent.setViewNode(viewNode);
        this.mEventBuffer.append(contentCaptureEvent);
    }

    private void processViewAppearedEvent(ContentCaptureEvent contentCaptureEvent) {
        ViewNode viewNode = contentCaptureEvent.getViewNode();
        final String eventTextLower = ContentProtectionUtils.getEventTextLower(contentCaptureEvent);
        final String viewNodeTextLower = ContentProtectionUtils.getViewNodeTextLower(viewNode);
        final String hintTextLower = ContentProtectionUtils.getHintTextLower(viewNode);
        this.mGroupsAll.stream().filter(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentProtectionEventProcessor.lambda$processViewAppearedEvent$0((ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).filter(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentProtectionEventProcessor.lambda$processViewAppearedEvent$1(eventTextLower, viewNodeTextLower, hintTextLower, (ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).findFirst().ifPresent(new Consumer() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ContentProtectionEventProcessor.this.lambda$processViewAppearedEvent$2((ContentProtectionEventProcessor.SearchGroup) obj);
            }
        });
        if (this.mGroupsRequired.stream().allMatch(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean z;
                z = ((ContentProtectionEventProcessor.SearchGroup) obj).mFound;
                return z;
            }
        }) && this.mGroupsOptional.stream().filter(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean z;
                z = ((ContentProtectionEventProcessor.SearchGroup) obj).mFound;
                return z;
            }
        }).count() >= this.mOptions.optionalGroupsThreshold) {
            loginDetected();
        } else {
            maybeResetLoginFlags();
        }
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$0(SearchGroup searchGroup) {
        return !searchGroup.mFound;
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$1(String str, String str2, String str3, SearchGroup searchGroup) {
        return searchGroup.matches(str) || searchGroup.matches(str2) || searchGroup.matches(str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processViewAppearedEvent$2(SearchGroup searchGroup) {
        searchGroup.mFound = true;
        this.mAnyGroupFound = true;
    }

    private void loginDetected() {
        if (this.mLastFlushTime == null || Instant.now().isAfter(this.mLastFlushTime.plus((TemporalAmount) MIN_DURATION_BETWEEN_FLUSHING))) {
            flush();
        }
        resetLoginFlags();
    }

    private void resetLoginFlags() {
        this.mGroupsAll.forEach(new Consumer() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ContentProtectionEventProcessor.SearchGroup) obj).mFound = false;
            }
        });
        this.mAnyGroupFound = false;
    }

    private void maybeResetLoginFlags() {
        if (this.mAnyGroupFound) {
            int i = this.mResetLoginRemainingEventsToProcess;
            if (i <= 0) {
                this.mResetLoginRemainingEventsToProcess = 150;
                return;
            }
            int i2 = i - 1;
            this.mResetLoginRemainingEventsToProcess = i2;
            if (i2 <= 0) {
                resetLoginFlags();
            }
        }
    }

    private void flush() {
        this.mLastFlushTime = Instant.now();
        final ParceledListSlice<ContentCaptureEvent> clearEvents = clearEvents();
        this.mHandler.post(new Runnable() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ContentProtectionEventProcessor.this.lambda$flush$6(clearEvents);
            }
        });
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        List asList = Arrays.asList(this.mEventBuffer.toArray());
        this.mEventBuffer.clear();
        return new ParceledListSlice<>(asList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlerOnLoginDetected, reason: merged with bridge method [inline-methods] */
    public void lambda$flush$6(ParceledListSlice<ContentCaptureEvent> parceledListSlice) {
        try {
            this.mContentCaptureManager.onLoginDetected(parceledListSlice);
        } catch (Exception e) {
            Log.e(TAG, "Failed to flush events for: " + this.mPackageName, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SearchGroup {
        public boolean mFound = false;
        private final List<String> mSearchStrings;

        SearchGroup(List<String> list) {
            this.mSearchStrings = list;
        }

        public boolean matches(final String str) {
            if (str == null) {
                return false;
            }
            Stream<String> stream = this.mSearchStrings.stream();
            Objects.requireNonNull(str);
            return stream.anyMatch(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$SearchGroup$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return str.contains((String) obj);
                }
            });
        }
    }
}
