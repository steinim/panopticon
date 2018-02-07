package no.panopticon.api.external.sns;

import no.panopticon.storage.RunningUnit;
import no.panopticon.storage.StatusSnapshot;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SnsMessage {

    public String Type;
    public String MessageId;
    public String TopicArn;
    public String Subject;
    public String Message;
    public String SignatureVersion;
    public String Signature;
    public String SigningCertURL;
    public String SubscribeURL;
    public String UnsubscribeURL;
    public List<UpdatedMeasurement> measurements;

    @Override
    public String toString() {
        return "SnsMessage{" +
                "Type='" + Type + '\'' +
                ", MessageId='" + MessageId + '\'' +
                ", TopicArn='" + TopicArn + '\'' +
                ", Subject='" + Subject + '\'' +
                ", Message='" + Message + '\'' +
                ", SignatureVersion='" + SignatureVersion + '\'' +
                ", Signature='" + Signature + '\'' +
                ", SigningCertURL='" + SigningCertURL + '\'' +
                ", SubscribeURL='" + SubscribeURL + '\'' +
                ", UnsubscribeURL=" + UnsubscribeURL +
                '}';
    }

    public RunningUnit toRunningUnit() {
        String topic = TopicArn.substring(TopicArn.lastIndexOf(':') + 1).trim();
        String environment = topic.substring(0, topic.indexOf("-")).toUpperCase();
        return new RunningUnit(environment, topic, topic, topic);
    }

    public StatusSnapshot toStatusSnapshot() {
        return new StatusSnapshot(LocalDateTime.now(),
                measurements.stream()
                        .map(m -> new StatusSnapshot.Measurement(m.key, m.status, m.displayValue, m.numericValue))
                        .collect(toList())
        );
    }

    public static class UpdatedMeasurement {
        public String key;
        public String status;
        public String displayValue;
        public long numericValue;

        @Override
        public String toString() {
            return "UpdatedMeasurement{" +
                    "key='" + key + '\'' +
                    ", status='" + status + '\'' +
                    ", displayValue='" + displayValue + '\'' +
                    ", numericValue=" + numericValue +
                    '}';
        }
    }
}
