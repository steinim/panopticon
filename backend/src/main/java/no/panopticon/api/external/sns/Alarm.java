package no.panopticon.api.external.sns;

import no.panopticon.storage.RunningUnit;
import no.panopticon.storage.StatusSnapshot;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Alarm {

    public String AlarmName;
    public String AlarmDescription;
    public String AWSAccountId;
    public String NewStateValue;
    public String NewStateReason;
    public String StateChangeTime;
    public String Region;
    public String OldStateValue;
    public Trigger Trigger;
    public List<UpdatedMeasurement> measurements;

    @Override
    public String toString() {
        return "Alarm{" +
                "AlarmName='" + AlarmName + '\'' +
                ", AlarmDescription='" + AlarmDescription + '\'' +
                ", AWSAccountId='" + AWSAccountId + '\'' +
                ", NewStateValue='" + NewStateValue + '\'' +
                ", NewStateReason='" + NewStateReason + '\'' +
                ", StateChangeTime='" + StateChangeTime + '\'' +
                ", Region='" + Region + '\'' +
                ", OldStateValue='" + OldStateValue + '\'' +
                ", Trigger=" + Trigger +
                '}';
    }

    public RunningUnit toRunningUnit() {
        String environment = AlarmName.substring(0, AlarmName.indexOf("-")).toUpperCase();
        return new RunningUnit(environment, AlarmName, AlarmName, AlarmName);
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
