import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SCAN implements IDiskAlgorithm {

    private List<DiskRequest> requests;
    private int headPosition;

    public SCAN(List<DiskRequest> requests, int headPosition) {
        this.requests = requests;
        this.headPosition = headPosition;
    }

    @Override
    public int calculateDistance() {
        List<DiskRequest> readyRequests = new ArrayList<>();
        for (DiskRequest request : requests) {
            if (request.timeOfArrival <= headPosition) {
                readyRequests.add(request);
            }
        }

        readyRequests.sort(Comparator.comparingInt(r -> r.track)); // Sort by track

        List<DiskRequest> lowerTracks = new ArrayList<>();
        List<DiskRequest> higherTracks = new ArrayList<>();

        for (DiskRequest request : readyRequests) {
            if (request.track < headPosition) {
                lowerTracks.add(request);
            } else {
                higherTracks.add(request);
            }
        }

        int movement = 0;
        int currentPosition = headPosition;

        for (DiskRequest request : higherTracks) {
            movement += Math.abs(currentPosition - request.track);
            currentPosition = request.track;
        }

        movement += Math.abs(4999 - currentPosition);
        currentPosition = 4999;

        for (int i = lowerTracks.size() - 1; i >= 0; i--) {
            DiskRequest request = lowerTracks.get(i);
            movement += Math.abs(currentPosition - request.track);
            currentPosition = request.track;
        }
        return movement;
    }
}