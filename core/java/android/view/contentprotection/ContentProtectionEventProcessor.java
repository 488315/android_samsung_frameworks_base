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

    public ContentProtectionEventProcessor(RingBuffer<ContentCaptureEvent> eventBuffer, Handler handler, IContentCaptureManager contentCaptureManager, String packageName, ContentCaptureOptions.ContentProtectionOptions options) {
        this.mEventBuffer = eventBuffer;
        this.mHandler = handler;
        this.mContentCaptureManager = contentCaptureManager;
        this.mPackageName = packageName;
        this.mOptions = options;
        this.mGroupsRequired = options.requiredGroups.stream().map(new Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new ContentProtectionEventProcessor.SearchGroup((List) obj);
            }
        }).toList();
        this.mGroupsOptional = options.optionalGroups.stream().map(new Function() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new ContentProtectionEventProcessor.SearchGroup((List) obj);
            }
        }).toList();
        this.mGroupsAll = Stream.of((Object[]) new List[]{this.mGroupsRequired, this.mGroupsOptional}).flatMap(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).toList();
    }

    public void processEvent(ContentCaptureEvent event) {
        if (EVENT_TYPES_TO_STORE.contains(Integer.valueOf(event.getType()))) {
            storeEvent(event);
        }
        if (event.getType() == 1) {
            processViewAppearedEvent(event);
        }
    }

    private void storeEvent(ContentCaptureEvent event) {
        ViewNode viewNode = event.getViewNode() != null ? event.getViewNode() : new ViewNode();
        viewNode.setTextIdEntry(this.mPackageName);
        event.setViewNode(viewNode);
        this.mEventBuffer.append(event);
    }

    private void processViewAppearedEvent(ContentCaptureEvent event) {
        ViewNode viewNode = event.getViewNode();
        final String eventText = ContentProtectionUtils.getEventTextLower(event);
        final String viewNodeText = ContentProtectionUtils.getViewNodeTextLower(viewNode);
        final String hintText = ContentProtectionUtils.getHintTextLower(viewNode);
        this.mGroupsAll.stream().filter(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentProtectionEventProcessor.lambda$processViewAppearedEvent$0((ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).filter(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentProtectionEventProcessor.lambda$processViewAppearedEvent$1(eventText, viewNodeText, hintText, (ContentProtectionEventProcessor.SearchGroup) obj);
            }
        }).findFirst().ifPresent(new Consumer() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ContentProtectionEventProcessor.this.lambda$processViewAppearedEvent$2((ContentProtectionEventProcessor.SearchGroup) obj);
            }
        });
        boolean loginDetected = this.mGroupsRequired.stream().allMatch(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda4
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
        }).count() >= ((long) this.mOptions.optionalGroupsThreshold);
        if (loginDetected) {
            loginDetected();
        } else {
            maybeResetLoginFlags();
        }
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$0(SearchGroup group) {
        return !group.mFound;
    }

    static /* synthetic */ boolean lambda$processViewAppearedEvent$1(String eventText, String viewNodeText, String hintText, SearchGroup group) {
        return group.matches(eventText) || group.matches(viewNodeText) || group.matches(hintText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processViewAppearedEvent$2(SearchGroup group) {
        group.mFound = true;
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
            if (this.mResetLoginRemainingEventsToProcess <= 0) {
                this.mResetLoginRemainingEventsToProcess = 150;
                return;
            }
            this.mResetLoginRemainingEventsToProcess--;
            if (this.mResetLoginRemainingEventsToProcess <= 0) {
                resetLoginFlags();
            }
        }
    }

    private void flush() {
        this.mLastFlushTime = Instant.now();
        final ParceledListSlice<ContentCaptureEvent> events = clearEvents();
        this.mHandler.post(new Runnable() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ContentProtectionEventProcessor.this.lambda$flush$6(events);
            }
        });
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        List<ContentCaptureEvent> events = Arrays.asList(this.mEventBuffer.toArray());
        this.mEventBuffer.clear();
        return new ParceledListSlice<>(events);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlerOnLoginDetected, reason: merged with bridge method [inline-methods] */
    public void lambda$flush$6(ParceledListSlice<ContentCaptureEvent> events) {
        try {
            this.mContentCaptureManager.onLoginDetected(events);
        } catch (Exception ex) {
            Log.e(TAG, "Failed to flush events for: " + this.mPackageName, ex);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SearchGroup {
        public boolean mFound = false;
        private final List<String> mSearchStrings;

        SearchGroup(List<String> searchStrings) {
            this.mSearchStrings = searchStrings;
        }

        public boolean matches(final String text) {
            if (text == null) {
                return false;
            }
            Stream<String> stream = this.mSearchStrings.stream();
            Objects.requireNonNull(text);
            return stream.anyMatch(new Predicate() { // from class: android.view.contentprotection.ContentProtectionEventProcessor$SearchGroup$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return text.contains((String) obj);
                }
            });
        }
    }
}
